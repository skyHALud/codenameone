package com.codename1.impl.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Style;

/**
 *
 * @author lior.gonnen
 *
 */
public class InPlaceEditView extends FrameLayout {

    private static final String TAG = "InPlaceEditView";
    public static final int REASON_UNDEFINED = 0;
    public static final int REASON_IME_ACTION = 1;
    public static final int REASON_TOUCH_OUTSIDE = 2;
    public static final int REASON_SYSTEM_KEY = 3;
    // The native Android edit-box to place over Codename One's edit-component
    private EditView mEditText = null;
    private String mLastEditText = "";
    // The Codename One edit-component we're editing
    private TextArea mTextArea = null;
    // The EditText's layout parameters
    private FrameLayout.LayoutParams mEditLayoutParams;
    // Reference to the system's input method manager
    private InputMethodManager mInputManager;
    // True while editing is in progress
    private static boolean mIsEditing = false;
    // Maps Codename One's input-types to Android input-types
    private SparseIntArray mInputTypeMap = new SparseIntArray(10);
    // Receives results from the InputMethodManager after calling show/hide soft-keyboard methods
    private ResultReceiver mResultReceiver;
    private int mLastEndEditReason = REASON_UNDEFINED;
    private Resources mResources;
    // Only a single instance of this class can exist
    private static InPlaceEditView sInstance = null;
    private static TextArea nextTextArea = null;
    private AndroidImplementation impl;

    /**
     * Private constructor
     * To use this class, call the static 'edit' method.
     * @param activity The current running activity
     */
    private InPlaceEditView(AndroidImplementation impl) {
        super(impl.activity);
        this.impl = impl;
        mResources = impl.activity.getResources();
        mResultReceiver = new DebugResultReceiver(getHandler());
        mInputManager = (InputMethodManager) impl.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        impl.activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // We place this view as an overlay that takes up the entire screen
        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setFocusableInTouchMode(true);
        initInputTypeMap();
    }

    /**
     * Prepare an int-to-int map that maps Codename One input-types to
     * Android input types
     */
    private void initInputTypeMap() {
        mInputTypeMap.append(TextArea.ANY, InputType.TYPE_CLASS_TEXT);
        mInputTypeMap.append(TextArea.DECIMAL, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        mInputTypeMap.append(TextArea.EMAILADDR, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mInputTypeMap.append(TextArea.INITIAL_CAPS_SENTENCE, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mInputTypeMap.append(TextArea.INITIAL_CAPS_WORD, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        mInputTypeMap.append(TextArea.NON_PREDICTIVE, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mInputTypeMap.append(TextArea.NUMERIC, InputType.TYPE_CLASS_NUMBER);
        mInputTypeMap.append(TextArea.PASSWORD, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mInputTypeMap.append(TextArea.PHONENUMBER, InputType.TYPE_CLASS_PHONE);
        mInputTypeMap.append(TextArea.URL, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
    }

    /**
     * Get the Android equivalent input type for a given Codename One input-type
     * @param codenameOneInputType One of the com.codename1.ui.TextArea input type constants
     * @return The Android equivalent of the given input type
     */
    private int getAndroidInputType(int codenameOneInputType) {
        int type = mInputTypeMap.get(codenameOneInputType, InputType.TYPE_CLASS_TEXT);

        // If we're editing standard text, disable auto complete.
        // The name of the flag is a little misleading. From the docs:
        // the text editor is performing auto-completion of the text being entered
        // based on its own semantics, which it will present to the user as they type.
        // This generally means that the input method should not be showing candidates itself,
        // but can expect for the editor to supply its own completions/candidates from
        // InputMethodSession.displayCompletions().
        if ((type & InputType.TYPE_CLASS_TEXT) != 0) {
            type |= InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
        }
        return type;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        Component c = mTextArea.getComponentForm().getComponentAt((int)event.getX(), (int)event.getY());
        boolean leaveVKBOpen = false;
        if(c != null && c instanceof TextArea && ((TextArea)c).isEditable() && ((TextArea)c).isEnabled())  {
            leaveVKBOpen = true;
        }
        // When the user touches the screen outside the text-area, finish editing
        endEditing(REASON_TOUCH_OUTSIDE, leaveVKBOpen);

        // Return false so that the event will propagate to the underlying view
        // We don't want to consume this event
        return false;
    }

    /**
     * Show or hide the virtual keyboard if necessary
     * @param show Show the keyboard if true, hide it otherwise
     */
    private void showVirtualKeyboard(boolean show) {
        Log.i(TAG, "showVirtualKeyboard show=" + show);

        boolean result;
        if (show) {
            // If we're in landscape, Android will not show the soft
            // keyboard unless SHOW_FORCED is requested
            Configuration config = mResources.getConfiguration();

            boolean isLandscape = (config.orientation == Configuration.ORIENTATION_LANDSCAPE);
            int showFlags = isLandscape ? InputMethodManager.SHOW_FORCED : InputMethodManager.SHOW_IMPLICIT;

            mInputManager.restartInput(mEditText);
            result = mInputManager.showSoftInput(mEditText, showFlags, mResultReceiver);
        } else {
            result = mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0, mResultReceiver);
        }

        Log.d(TAG, "InputMethodManager returned " + Boolean.toString(result).toUpperCase());
    }

    /**
     * Start editing the given text-area
     * This method is executed on the UI thread, so UI manipulation is safe here.
     * @param activity Current running activity
     * @param textArea The TextArea instance our internal EditText needs to overlap
     * @param codenameOneInputType One of the input type constants in com.codename1.ui.TextArea
     * @param initialText The text that appears in the Codename One text are before the call to startEditing
     */
    private synchronized void startEditing(Activity activity, TextArea textArea, String initialText, int codenameOneInputType) {
        if (mEditText != null) {
            throw new IllegalArgumentException("Cannot edit - already in edit mode");
        }
        final Style taStyle = textArea.getStyle();
        Font font = taStyle.getFont();
        int txty = textArea.getAbsoluteY();
        int txtx = textArea.getAbsoluteX();
        int paddingTop = 0;
        int paddingLeft = taStyle.getPadding(textArea.isRTL(), Component.LEFT);
        int paddingRight = taStyle.getPadding(textArea.isRTL(), Component.RIGHT);
        int paddingBottom = taStyle.getPadding(Component.BOTTOM);

        if (textArea instanceof TextField) {
            switch (textArea.getVerticalAlignment()) {
                case Component.BOTTOM:
                    paddingTop = textArea.getHeight() - taStyle.getPadding(false, Component.BOTTOM) - font.getHeight();
                    break;
                case Component.CENTER:
                    paddingTop = textArea.getHeight() / 2 - font.getHeight() / 2;
                    break;
                default:
                    paddingTop = taStyle.getPadding(false, Component.TOP);
                    break;
            }
        } else {
            paddingTop = taStyle.getPadding(false, Component.TOP);
        }

        mLastEditText = initialText;
        mEditText = new EditView(activity, this);
        
        mEditText.setFocusableInTouchMode(true);
        mEditLayoutParams = new FrameLayout.LayoutParams(0, 0);
        // Set the appropriate gravity so that the left and top margins will be
        // taken into account
        mEditLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mEditLayoutParams.setMargins(txtx, txty, 0, 0);
        mEditLayoutParams.width = textArea.getWidth();
        mEditLayoutParams.height = textArea.getHeight();

        mEditText.setLayoutParams(mEditLayoutParams);

        // Align text to top
        mEditText.setGravity(Gravity.LEFT | Gravity.TOP);
        mEditText.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        addView(mEditText, mEditLayoutParams);

        mTextArea = textArea;
        Component nextDown = mTextArea.getNextFocusDown();
        if(nextDown == null){
            nextDown = textArea.getComponentForm().findNextFocusVertical(true);
        }
        if (mTextArea.isSingleLineTextArea()) {
            if (nextDown != null && nextDown instanceof TextArea && ((TextArea)nextDown).isEditable() && ((TextArea)nextDown).isEnabled()) {
                mEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            } else {
                mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
        }
        mEditText.setSingleLine(textArea.isSingleLineTextArea());
        mEditText.setAdapter((ArrayAdapter<String>) null);
        mEditText.setText(initialText);
        invalidate();
        setVisibility(VISIBLE);
        bringToFront();

        mEditText.requestFocus();

        Object nativeFont = font.getNativeFont();
        if (nativeFont == null) {
            nativeFont = impl.getDefaultFont();
        }
        Paint p = (Paint) ((AndroidImplementation.NativeFont) nativeFont).font;
        mEditText.setTypeface(p.getTypeface());
        mEditText.setTextScaleX(p.getTextScaleX());
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, p.getTextSize());

        int fgColor = taStyle.getFgColor();
        mEditText.setTextColor(Color.rgb(fgColor >> 16, (fgColor & 0x00ff00) >> 8, (fgColor & 0x0000ff)));
        boolean password = false;
        if((codenameOneInputType & TextArea.PASSWORD) == TextArea.PASSWORD){
            codenameOneInputType = codenameOneInputType ^ TextArea.PASSWORD;
            password = true;
        }
        
        if (mTextArea.isSingleLineTextArea()) {
            mEditText.setInputType(getAndroidInputType(codenameOneInputType));
        }
        if(password){
            mEditText.setTransformationMethod(new MyPasswordTransformationMethod());
        }
        
        int maxLength = mTextArea.getMaxSize();
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        mEditText.setFilters(FilterArray);
        
        showVirtualKeyboard(true);
    }

    /**
     * Calculate the font height in pixels according to the text area
     * @param textArea The Codename One text-area to get the font height from
     * @return The font height in pixels, or -1 if a font height could not be determined.
     */
    private int getFontHeight(Style style) {
        if (style == null) {
            return -1;
        }

        Font font = style.getFont();
        if (font == null) {
            return -1;
        }

        return font.getHeight();
    }

    /**
     * Finish the in-place editing of the given text area, release the edit lock, and allow the synchronous call
     * to 'edit' to return.
     */
    private synchronized void endEditing(int reason, boolean forceVKBOpen) {

        if (mEditText == null) {
            return;
        }
        setVisibility(GONE);
        mLastEndEditReason = reason;

        // If the IME action is set to NEXT, do not hide the virtual keyboard
        boolean isNextActionFlagSet = (mEditText.getImeOptions() == EditorInfo.IME_ACTION_NEXT);
        boolean leaveKeyboardShowing = (reason == REASON_IME_ACTION) && isNextActionFlagSet || forceVKBOpen;
        if (!leaveKeyboardShowing) {
            showVirtualKeyboard(false);
        }
        mIsEditing = false;
        mTextArea = null;
        mLastEditText = mEditText.getText().toString();
        removeView(mEditText);
        mEditText = null;
    }

    /**
     * This method waits until the user leaves the EditText
     * It must not access sInstance since it might not have been created yet.
     */
    private static void waitForEditCompletion() {
        Display.getInstance().invokeAndBlock(new Runnable() {

            public void run() {
                while (mIsEditing);
            }
        });
        Log.d(TAG, "waitForEditCompletion - Waiting for lock");
    }

    /**
     * Returns the current text in the internal edit-area
     */
    private String getText() {
        return mLastEditText;
    }

    /**
     * This method will be called by our EditText control when the action
     * key (Enter/Go/Send) on the soft keyboard will be pressed.
     * @param actionCode
     */
    void onEditorAction(int actionCode) {
        if (actionCode == EditorInfo.IME_ACTION_NEXT) {
            Component next = mTextArea.getNextFocusDown();
            if (next == null) {
                next = mTextArea.getComponentForm().findNextFocusVertical(true);
            }

            if (next != null && next instanceof TextArea && ((TextArea)next).isEditable() && ((TextArea)next).isEnabled()) {
                nextTextArea = (TextArea) next;
            }
        }
        endEditing(REASON_IME_ACTION, false);
    }

    /**
     * Returns true if an edit is currently in progress, false otherwise
     */
    public static boolean isEditing() {
        return (sInstance == null) ? false : sInstance.mIsEditing;
    }

    public static int lastEditEndReason() {
        return (sInstance == null) ? REASON_UNDEFINED : sInstance.mLastEndEditReason;
    }

    public static void endEdit() {
        if (sInstance != null) {
            sInstance.endEditing(REASON_UNDEFINED, false);
            ViewParent p = sInstance.getParent();
            if (p != null) {
                ((ViewGroup) p).removeView(sInstance);
            }
            sInstance = null;
        }
    }

    public static void reLayoutEdit() {
        if (sInstance != null) {

            TextArea txt = sInstance.mTextArea;
            if (txt != null) {
                int txty = txt.getAbsoluteY();
                int txtx = txt.getAbsoluteX();
                int w = txt.getWidth();
                int h = txt.getHeight();

                sInstance.mEditLayoutParams.setMargins(txtx, txty, 0, 0);
                sInstance.mEditLayoutParams.width = w;
                sInstance.mEditLayoutParams.height = h;
                sInstance.mEditText.requestLayout();
                sInstance.invalidate();
                sInstance.setVisibility(VISIBLE);
                sInstance.bringToFront();
            }
        }

    }

    /**
     * Entry point for using this class
     * @param activity The current running activity
     * @param component Any subclass of com.codename1.ui.TextArea
     * @param inputType One of the TextArea's input-type constants
     */
    public static String edit(final AndroidImplementation impl, final Component component, final int inputType) {
        if (impl.activity == null) {
            throw new IllegalArgumentException("activity is null");
        }

        if (component == null) {
            throw new IllegalArgumentException("component is null");
        }

        if (!(component instanceof TextArea)) {
            throw new IllegalArgumentException("component must be instance of TextArea");
        }

        final TextArea textArea = (TextArea) component;
        final String initialText = textArea.getText();
        Dimension prefSize = textArea.getPreferredSize();
        //textArea.setText("");
        
        textArea.setPreferredSize(prefSize);
        if (textArea instanceof TextField) {
            ((TextField) textArea).setEditable(false);
        }
        mIsEditing = true;

        impl.activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (sInstance == null) {
                    sInstance = new InPlaceEditView(impl);
                    impl.activity.addContentView(sInstance, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                }
                sInstance.startEditing(impl.activity, textArea, initialText, inputType);
            }
        });

        // Make this call synchronous
        waitForEditCompletion();
        if (textArea instanceof TextField) {
            ((TextField) textArea).setEditable(true);
        }
        textArea.setPreferredSize(null);

        if (nextTextArea != null) {
            final TextArea next = nextTextArea;
            nextTextArea = null;
            next.requestFocus();
            Display.getInstance().callSerially(new Runnable() {

                public void run() {
                    Display.getInstance().editString(next,
                            next.getMaxSize(),
                            next.getConstraint(),
                            next.getText());
                }
            });
        }

        if (sInstance != null) {
            return sInstance.getText();
        } else {
            return "";
        }
    }

    private class DebugResultReceiver extends ResultReceiver {

        private static final String TAG = "InPlaceEditView.ResultReceiver";
        private SparseArray<String> mResultToStringMap = new SparseArray<String>();

        public DebugResultReceiver(Handler handler) {
            super(handler);
            mResultToStringMap.append(InputMethodManager.RESULT_HIDDEN, "RESULT_HIDDEN");
            mResultToStringMap.append(InputMethodManager.RESULT_SHOWN, "RESULT_SHOWN");
            mResultToStringMap.append(InputMethodManager.RESULT_UNCHANGED_HIDDEN, "RESULT_UNCHANGED_HIDDEN");
            mResultToStringMap.append(InputMethodManager.RESULT_UNCHANGED_SHOWN, "RESULT_UNCHANGED_SHOWN");
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String resultStr = mResultToStringMap.get(resultCode, "Unknown");

            Log.i(TAG, "resultCode = " + resultStr);
        }
    }

    private class EditView extends AutoCompleteTextView {

        private InPlaceEditView mInPlaceEditView;
        private TextWatcher mTextWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mInPlaceEditView.mIsEditing && mInPlaceEditView.mTextArea != null) {
                    try {
                        mInPlaceEditView.mTextArea.setText(s.toString());
                        //mInPlaceEditView.mTextArea.setText("");
                        //mInPlaceEditView.mTextArea.commitChange();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString() + " " + Log.getStackTraceString(e));
                    }
                }
            }
        };

        protected void onWindowVisibilityChanged(int visibility) {
            if (visibility == View.VISIBLE) {
                addTextChangedListener(mTextWatcher);
            } else {
                removeTextChangedListener(mTextWatcher);
            }
        }

        /**
         * Constructor
         * @param context
         * @param inPlaceEditView
         */
        public EditView(Context context, InPlaceEditView inPlaceEditView) {
            super(context);
            mInPlaceEditView = inPlaceEditView;

            setBackgroundColor(Color.TRANSPARENT);
        }

        @Override
        public void onEditorAction(int actionCode) {
            super.onEditorAction(actionCode);

            mInPlaceEditView.onEditorAction(actionCode);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            // If the user presses the back button, or the menu button
            // we must terminate editing, to allow EDT to handle events
            // again
            if (keyCode == KeyEvent.KEYCODE_BACK
                    || keyCode == KeyEvent.KEYCODE_MENU) {
                endEditing(InPlaceEditView.REASON_SYSTEM_KEY, false);
            }

            return super.onKeyDown(keyCode, event);
        }
    }

    public class MyPasswordTransformationMethod extends PasswordTransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {

            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '\u25CF'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    };
}
