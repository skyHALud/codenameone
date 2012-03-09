#ifndef __ORG_XMLVM_IPHONE_UIPICKERVIEWDELEGATE__
#define __ORG_XMLVM_IPHONE_UIPICKERVIEWDELEGATE__

#include "xmlvm.h"

// Preprocessor constants for interfaces:
// Implemented interfaces:
// Super Class:
#include "org_xmlvm_iphone_NSObject.h"

// Circular references:
#ifndef XMLVM_FORWARD_DECL_java_lang_String
#define XMLVM_FORWARD_DECL_java_lang_String
XMLVM_FORWARD_DECL(java_lang_String)
#endif
#ifndef XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIPickerView
#define XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIPickerView
XMLVM_FORWARD_DECL(org_xmlvm_iphone_UIPickerView)
#endif
#ifndef XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIView
#define XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIView
XMLVM_FORWARD_DECL(org_xmlvm_iphone_UIView)
#endif
// Class declarations for org.xmlvm.iphone.UIPickerViewDelegate
XMLVM_DEFINE_CLASS(org_xmlvm_iphone_UIPickerViewDelegate, 7, XMLVM_ITABLE_SIZE_org_xmlvm_iphone_UIPickerViewDelegate)

extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIPickerViewDelegate;
extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIPickerViewDelegate_1ARRAY;
extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIPickerViewDelegate_2ARRAY;
extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIPickerViewDelegate_3ARRAY;
//XMLVM_BEGIN_DECLARATIONS
#define __ADDITIONAL_INSTANCE_FIELDS_org_xmlvm_iphone_UIPickerViewDelegate
//XMLVM_END_DECLARATIONS

#define __INSTANCE_FIELDS_org_xmlvm_iphone_UIPickerViewDelegate \
    __INSTANCE_FIELDS_org_xmlvm_iphone_NSObject; \
    struct { \
        __ADDITIONAL_INSTANCE_FIELDS_org_xmlvm_iphone_UIPickerViewDelegate \
    } org_xmlvm_iphone_UIPickerViewDelegate

struct org_xmlvm_iphone_UIPickerViewDelegate {
    __TIB_DEFINITION_org_xmlvm_iphone_UIPickerViewDelegate* tib;
    struct {
        __INSTANCE_FIELDS_org_xmlvm_iphone_UIPickerViewDelegate;
    } fields;
};
#ifndef XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIPickerViewDelegate
#define XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIPickerViewDelegate
typedef struct org_xmlvm_iphone_UIPickerViewDelegate org_xmlvm_iphone_UIPickerViewDelegate;
#endif

#define XMLVM_VTABLE_SIZE_org_xmlvm_iphone_UIPickerViewDelegate 7

void __INIT_org_xmlvm_iphone_UIPickerViewDelegate();
void __INIT_IMPL_org_xmlvm_iphone_UIPickerViewDelegate();
void __DELETE_org_xmlvm_iphone_UIPickerViewDelegate(void* me, void* client_data);
void __INIT_INSTANCE_MEMBERS_org_xmlvm_iphone_UIPickerViewDelegate(JAVA_OBJECT me, int derivedClassWillRegisterFinalizer);
JAVA_OBJECT __NEW_org_xmlvm_iphone_UIPickerViewDelegate();
JAVA_OBJECT __NEW_INSTANCE_org_xmlvm_iphone_UIPickerViewDelegate();
void org_xmlvm_iphone_UIPickerViewDelegate___INIT___(JAVA_OBJECT me);
JAVA_FLOAT org_xmlvm_iphone_UIPickerViewDelegate_rowHeightForComponent___org_xmlvm_iphone_UIPickerView_int(JAVA_OBJECT me, JAVA_OBJECT n1, JAVA_INT n2);
JAVA_FLOAT org_xmlvm_iphone_UIPickerViewDelegate_widthForComponent___org_xmlvm_iphone_UIPickerView_int(JAVA_OBJECT me, JAVA_OBJECT n1, JAVA_INT n2);
JAVA_OBJECT org_xmlvm_iphone_UIPickerViewDelegate_viewForRow___org_xmlvm_iphone_UIPickerView_int_int_org_xmlvm_iphone_UIView(JAVA_OBJECT me, JAVA_OBJECT n1, JAVA_INT n2, JAVA_INT n3, JAVA_OBJECT n4);
JAVA_OBJECT org_xmlvm_iphone_UIPickerViewDelegate_titleForRow___org_xmlvm_iphone_UIPickerView_int_int(JAVA_OBJECT me, JAVA_OBJECT n1, JAVA_INT n2, JAVA_INT n3);
void org_xmlvm_iphone_UIPickerViewDelegate_didSelectRow___org_xmlvm_iphone_UIPickerView_int_int(JAVA_OBJECT me, JAVA_OBJECT n1, JAVA_INT n2, JAVA_INT n3);

#endif
