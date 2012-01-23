/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.impl.android;

import android.content.Intent;

/**
 *
 * @author Chen
 */
public interface IntentResultListener {
    
    public int CAPTURE_IMAGE = 1;
    public int CAPTURE_VIDEO = 2;
    public int CAPTURE_AUDIO = 3;
    
    
    public void onActivityResult (int requestCode, int resultCode, Intent data);
}
