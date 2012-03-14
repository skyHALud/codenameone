#ifndef __ORG_XMLVM_IPHONE_UIAPPLICATIONDELEGATE__
#define __ORG_XMLVM_IPHONE_UIAPPLICATIONDELEGATE__

#include "xmlvm.h"

// Preprocessor constants for interfaces:
// Implemented interfaces:
// Super Class:
#include "org_xmlvm_iphone_NSObject.h"

// Circular references:
#ifndef XMLVM_FORWARD_DECL_java_lang_Object
#define XMLVM_FORWARD_DECL_java_lang_Object
XMLVM_FORWARD_DECL(java_lang_Object)
#endif
#ifndef XMLVM_FORWARD_DECL_java_lang_String
#define XMLVM_FORWARD_DECL_java_lang_String
XMLVM_FORWARD_DECL(java_lang_String)
#endif
#ifndef XMLVM_FORWARD_DECL_org_xmlvm_iphone_NSURL
#define XMLVM_FORWARD_DECL_org_xmlvm_iphone_NSURL
XMLVM_FORWARD_DECL(org_xmlvm_iphone_NSURL)
#endif
#ifndef XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIApplication
#define XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIApplication
XMLVM_FORWARD_DECL(org_xmlvm_iphone_UIApplication)
#endif
// Class declarations for org.xmlvm.iphone.UIApplicationDelegate
XMLVM_DEFINE_CLASS(org_xmlvm_iphone_UIApplicationDelegate, 7, XMLVM_ITABLE_SIZE_org_xmlvm_iphone_UIApplicationDelegate)

extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIApplicationDelegate;
extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIApplicationDelegate_1ARRAY;
extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIApplicationDelegate_2ARRAY;
extern JAVA_OBJECT __CLASS_org_xmlvm_iphone_UIApplicationDelegate_3ARRAY;
//XMLVM_BEGIN_DECLARATIONS
#define __ADDITIONAL_INSTANCE_FIELDS_org_xmlvm_iphone_UIApplicationDelegate
//XMLVM_END_DECLARATIONS

#define __INSTANCE_FIELDS_org_xmlvm_iphone_UIApplicationDelegate \
    __INSTANCE_FIELDS_org_xmlvm_iphone_NSObject; \
    struct { \
        __ADDITIONAL_INSTANCE_FIELDS_org_xmlvm_iphone_UIApplicationDelegate \
    } org_xmlvm_iphone_UIApplicationDelegate

struct org_xmlvm_iphone_UIApplicationDelegate {
    __TIB_DEFINITION_org_xmlvm_iphone_UIApplicationDelegate* tib;
    struct {
        __INSTANCE_FIELDS_org_xmlvm_iphone_UIApplicationDelegate;
    } fields;
};
#ifndef XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIApplicationDelegate
#define XMLVM_FORWARD_DECL_org_xmlvm_iphone_UIApplicationDelegate
typedef struct org_xmlvm_iphone_UIApplicationDelegate org_xmlvm_iphone_UIApplicationDelegate;
#endif

#define XMLVM_VTABLE_SIZE_org_xmlvm_iphone_UIApplicationDelegate 7

void __INIT_org_xmlvm_iphone_UIApplicationDelegate();
void __INIT_IMPL_org_xmlvm_iphone_UIApplicationDelegate();
void __DELETE_org_xmlvm_iphone_UIApplicationDelegate(void* me, void* client_data);
void __INIT_INSTANCE_MEMBERS_org_xmlvm_iphone_UIApplicationDelegate(JAVA_OBJECT me, int derivedClassWillRegisterFinalizer);
JAVA_OBJECT __NEW_org_xmlvm_iphone_UIApplicationDelegate();
JAVA_OBJECT __NEW_INSTANCE_org_xmlvm_iphone_UIApplicationDelegate();
void org_xmlvm_iphone_UIApplicationDelegate___INIT___(JAVA_OBJECT me);
void org_xmlvm_iphone_UIApplicationDelegate_applicationDidFinishLaunching___org_xmlvm_iphone_UIApplication(JAVA_OBJECT me, JAVA_OBJECT n1);
void org_xmlvm_iphone_UIApplicationDelegate_applicationWillTerminate___org_xmlvm_iphone_UIApplication(JAVA_OBJECT me, JAVA_OBJECT n1);
void org_xmlvm_iphone_UIApplicationDelegate_applicationDidBecomeActive___org_xmlvm_iphone_UIApplication(JAVA_OBJECT me, JAVA_OBJECT n1);
void org_xmlvm_iphone_UIApplicationDelegate_applicationWillResignActive___org_xmlvm_iphone_UIApplication(JAVA_OBJECT me, JAVA_OBJECT n1);
void org_xmlvm_iphone_UIApplicationDelegate_applicationDidReceiveMemoryWarning___org_xmlvm_iphone_UIApplication(JAVA_OBJECT me, JAVA_OBJECT n1);
JAVA_BOOLEAN org_xmlvm_iphone_UIApplicationDelegate_openURL___org_xmlvm_iphone_UIApplication_org_xmlvm_iphone_NSURL_java_lang_String_java_lang_Object(JAVA_OBJECT me, JAVA_OBJECT n1, JAVA_OBJECT n2, JAVA_OBJECT n3, JAVA_OBJECT n4);

#endif