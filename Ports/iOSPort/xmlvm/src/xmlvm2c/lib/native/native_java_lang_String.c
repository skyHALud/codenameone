
#include "xmlvm.h"
#include "java_lang_String.h"


//XMLVM_BEGIN_NATIVE_IMPLEMENTATION
#include <wctype.h>
//XMLVM_END_NATIVE_IMPLEMENTATION

JAVA_INT java_lang_String_toLowerCaseImpl___int(JAVA_OBJECT me, JAVA_INT n1)
{
    //XMLVM_BEGIN_NATIVE[java_lang_String_toLowerCaseImpl___int]
    return towlower(n1);
    //XMLVM_END_NATIVE
}

JAVA_INT java_lang_String_toUpperCaseImpl___int(JAVA_OBJECT me, JAVA_INT n1)
{
    //XMLVM_BEGIN_NATIVE[java_lang_String_toUpperCaseImpl___int]
    return towupper(n1);
    //XMLVM_END_NATIVE
}


void xmlvm_init_native_java_lang_String()
{
#ifdef XMLVM_VTABLE_IDX_java_lang_String_toLowerCaseImpl___int
    __TIB_java_lang_String.vtable[XMLVM_VTABLE_IDX_java_lang_String_toLowerCaseImpl___int] = 
        (VTABLE_PTR) java_lang_String_toLowerCaseImpl___int;
#endif
#ifdef XMLVM_VTABLE_IDX_java_lang_String_toUpperCaseImpl___int
    __TIB_java_lang_String.vtable[XMLVM_VTABLE_IDX_java_lang_String_toUpperCaseImpl___int] = 
        (VTABLE_PTR) java_lang_String_toUpperCaseImpl___int;
#endif
}
