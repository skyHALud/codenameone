/* Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */
package org.xmlvm.ant.xcode;

import org.xmlvm.ant.utils.FileUtilities;

/**
 * This is the template of an XCode project
 */
public class XCodeProjectSource {

    public static final String Source =
            "// !$*UTF8*$!\n"
            + "{\n"
            + "	archiveVersion = 1;\n"
            + "	classes = {\n"
            + "	};\n"
            + "	objectVersion = 45;\n"
            + "	objects = {\n"
            + "\n"
            + "/* Begin PBXBuildFile section */\n"
            + "__BUILDREFS__/* End PBXBuildFile section */\n"
            + "\n"
            + "/* Begin PBXFileReference section */\n"
            + "		1 /* __PROJNAME__.app */ = {isa = PBXFileReference; explicitFileType = wrapper.application; includeInIndex = 0; path = __PROJNAME__.app; sourceTree = BUILT_PRODUCTS_DIR; };\n"
            + "		8 /* __PROJNAME__-Info.plist */ = {isa = PBXFileReference; fileEncoding = 4; lastKnownFileType = text.plist.xml; name = \"__PROJNAME__-Info.plist\" ; path = \"__PROJNAME__-Info.plist\"; plistStructureDefinitionIdentifier = \"com.apple.xcode.plist.structure-definition.iphone.info-plist\"; sourceTree = \"<group>\"; };\n"
            + "__FILEREFS__/* End PBXFileReference section */\n"
            + "\n"
            + "/* Begin PBXFrameworksBuildPhase section */\n"
            + "		9 /* Frameworks */ = {\n"
            + "			isa = PBXFrameworksBuildPhase;\n"
            + "			buildActionMask = 2147483647;\n"
            + "			files = (\n"
            + "__BUILDFRAMS__			);\n"
            + "			runOnlyForDeploymentPostprocessing = 0;\n"
            + "		};\n"
            + "/* End PBXFrameworksBuildPhase section */\n"
            + "\n"
            + "/* Begin PBXGroup section */\n"
            + "		24 /* Application */ = {\n"
            + "			isa = PBXGroup;\n"
            + "			children = (\n"
            + "__APPSRC__			);\n"
            + "			name = Application;\n"
            + "			path = ../" + FileUtilities.SRCLOC + "trim;\n"
            + "			sourceTree = \"<group>\";\n"
            + "		};\n"
            + "		11 /* MainGroup */ = {\n"
            + "			isa = PBXGroup;\n"
            + "			children = (\n"
            + "				24 /* 	Application */,\n"
            + "				12 /* Resources */,\n"
            + "				13 /* Frameworks */,\n"
            + "				10 /* Products */,\n"
            + "			);\n"
            + "			name = MainGroup;\n"
            + "			sourceTree = \"<group>\";\n"
            + "		};\n"
            + "		12 /* Resources */ = {\n"
            + "			isa = PBXGroup;\n"
            + "			children = (\n"
            + "				8 /* __PROJNAME__-Info.plist */,\n"
            + "			);\n"
            + "			name = Resources;\n"
            + "			path = ../build/xcode/sys;\n"
            + "			sourceTree = \"<group>\";\n"
            + "		};\n"
            + "		13 /* Frameworks */ = {\n"
            + "			isa = PBXGroup;\n"
            + "			children = (\n"
            + "__FRAMEWORKS__			);\n"
            + "			name = Frameworks;\n"
            + "			sourceTree = \"<group>\";\n"
            + "		};\n"
            + "		10 /* Products */ = {\n"
            + "			isa = PBXGroup;\n"
            + "			children = (\n"
            + "				1 /* __PROJNAME__.app */,\n"
            + "			);\n"
            + "			name = Products;\n"
            + "			sourceTree = \"<group>\";\n"
            + "		};\n"
            + "/* End PBXGroup section */\n"
            + "\n"
            + "/* Begin PBXNativeTarget section */\n"
            + "		14 /* __PROJNAME__ */ = {\n"
            + "			isa = PBXNativeTarget;\n"
            + "			buildConfigurationList = 15 /* Build configuration list for PBXNativeTarget \"__PROJNAME__\" */;\n"
            + "			buildPhases = (\n"
            + "				16 /* Other Resources */,\n"
            + "				17 /* Sources */,\n"
            + "				9 /* Frameworks */,\n"
            + "				34 /* Resources */,\n"
            + "			);\n"
            + "			buildRules = (\n"
            + "			);\n"
            + "			dependencies = (\n"
            + "			);\n"
            + "			name = __PROJNAME__;\n"
            + "			productName = __PROJNAME__;\n"
            + "			productReference = 1 /* __PROJNAME__.app */;\n"
            + "			productType = \"com.apple.product-type.application\";\n"
            + "		};\n"
            + "/* End PBXNativeTarget section */\n"
            + "\n"
            + "/* Begin PBXProject section */\n"
            + "		18 /* Project object */ = {\n"
            + "			isa = PBXProject;\n"
            + "			buildConfigurationList = 19 /* Build configuration list for PBXProject \"__PROJNAME__\" */;\n"
            + "			compatibilityVersion = \"Xcode 3.1\";\n"
            + "			hasScannedForEncodings = 1;\n"
            + "			mainGroup = 11 /* MainGroup */;\n"
            + "			projectDirPath = \"\";\n"
            + "			projectRoot = \"\";\n"
            + "			targets = (\n"
            + "				14 /* __PROJNAME__ */,\n"
            + "			);\n"
            + "		};\n"
            + "/* End PBXProject section */\n"
            + "\n"
            + "/* Begin PBXResourcesBuildPhase section */\n"
            + "		16 /* Resources */ = {\n"
            + "			isa = PBXResourcesBuildPhase;\n"
            + "			buildActionMask = 2147483647;\n"
            + "			files = (\n"
            + "__RESOURCES_BUILD__			);\n"
            + "			runOnlyForDeploymentPostprocessing = 0;\n"
            + "		};\n"
            + "/* End PBXResourcesBuildPhase section */\n"
            + "\n"
            + "/* Begin PBXShellScriptBuildPhase section */\n"
            + "		34 /* ShellScript */ = {\n"
            + "			isa = PBXShellScriptBuildPhase;\n"
            + "			buildActionMask = 2147483647;\n"
            + "			files = (\n"
            + "			);\n"
            + "			inputPaths = (\n"
            + "			);\n"
            + "			outputPaths = (\n"
            + "			);\n"
            + "			runOnlyForDeploymentPostprocessing = 0;\n"
            + "			shellPath = /bin/bash;\n"
            + "			shellScript = \"RESOURCES=\\\"__RESOURCE_DIR__\\\"\\nfor i in __RESOURCE_LIST__; do\\n\\tcase $i in\\n\\t\\t/*)\\tFROM=$i ;;\\n\\t\\t*)\\tFROM=${RESOURCES}/$i ;;\\n\\tesac\\n\\tif [ -e \\\"$FROM\\\" ] ; then\\n\\t\\tcp -r \\\"$FROM\\\" \\\"${BUILT_PRODUCTS_DIR}/${WRAPPER_NAME}\\\"\\n\\tfi\\ndone\\n\";\n"
            + "		};\n"
            + "/* End PBXShellScriptBuildPhase section */\n"
            + "\n"
            + "/* Begin PBXSourcesBuildPhase section */\n"
            + "		17 /* Sources */ = {\n"
            + "			isa = PBXSourcesBuildPhase;\n"
            + "			buildActionMask = 2147483647;\n"
            + "			files = (\n"
            + "__SRC_BUILD__			);\n"
            + "			runOnlyForDeploymentPostprocessing = 0;\n"
            + "		};\n"
            + "/* End PBXSourcesBuildPhase section */\n"
            + "\n"
            + "/* Begin XCBuildConfiguration section */\n"
            + "        29 /* AppStore */ = {\n"
            + "            isa = XCBuildConfiguration;\n"
            + "            buildSettings = {\n"
            + "                ARCHS = \"$(__ARCHITECTURE__)\";\n"
            + "                \"CODE_SIGN_IDENTITY[sdk=iphoneos*]\" = \"iPhone Distribution\";\n"
            + "                DEPLOYMENT_POSTPROCESSING = YES;\n"
            + "                GCC_C_LANGUAGE_STANDARD = c99;\n"
            + "                GCC_GENERATE_DEBUGGING_SYMBOLS = YES;\n"
            + "                GCC_WARN_ABOUT_RETURN_TYPE = YES;\n"
            + "                GCC_WARN_UNUSED_VARIABLE = NO;\n"
            + "                GCC_VERSION = com.apple.compilers.llvmgcc42;\n"
            + "                \"PROVISIONING_PROFILE[sdk=iphoneos*]\" = \"\";\n"
            + "                SEPARATE_STRIP = YES;\n"
            + "                IPHONEOS_DEPLOYMENT_TARGET = 3.1;\n"
            + "                TARGETED_DEVICE_FAMILY = \"__SDK_TARGET__\";\n"
            + "                SDKROOT = __SDK_ROOT__;\n"
            + "                OTHER_CFLAGS = \"-DNS_BLOCK_ASSERTIONS=1\";\n"
            + "            };\n"
            + "            name = AppStore;\n"
            + "        };\n"
            + "        22 /* Debug */ = {\n"
            + "            isa = XCBuildConfiguration;\n"
            + "            buildSettings = {\n"
            + "                ARCHS = \"$(__ARCHITECTURE__)\";\n"
            + "                \"CODE_SIGN_IDENTITY[sdk=iphoneos*]\" = \"iPhone Developer\";\n"
            + "                GCC_C_LANGUAGE_STANDARD = c99;\n"
            + "                GCC_WARN_ABOUT_RETURN_TYPE = YES;\n"
            + "                GCC_WARN_UNUSED_VARIABLE = NO;\n"
            + "                GCC_VERSION = com.apple.compilers.llvmgcc42;\n"
            + "                IPHONEOS_DEPLOYMENT_TARGET = 3.1;\n"
            + "                TARGETED_DEVICE_FAMILY = \"__SDK_TARGET__\";\n"
            + "                SDKROOT = __SDK_ROOT__;\n"
            + "            };\n"
            + "            name = Debug;\n"
            + "        };\n"
            + "        23 /* Release */ = {\n"
            + "            isa = XCBuildConfiguration;\n"
            + "            buildSettings = {\n"
            + "                ARCHS = \"$(__ARCHITECTURE__)\";\n"
            + "                \"CODE_SIGN_IDENTITY[sdk=iphoneos*]\" = \"iPhone Developer\";\n"
            + "                DEPLOYMENT_POSTPROCESSING = YES;\n"
            + "                GCC_C_LANGUAGE_STANDARD = c99;\n"
            + "                GCC_GENERATE_DEBUGGING_SYMBOLS = YES;\n"
            + "                GCC_WARN_ABOUT_RETURN_TYPE = YES;\n"
            + "                GCC_WARN_UNUSED_VARIABLE = NO;\n"
            + "                GCC_VERSION = com.apple.compilers.llvmgcc42;\n"
            + "                SEPARATE_STRIP = YES;\n"
            + "                IPHONEOS_DEPLOYMENT_TARGET = 3.1;\n"
            + "                TARGETED_DEVICE_FAMILY = \"__SDK_TARGET__\";\n"
            + "                SDKROOT = __SDK_ROOT__;\n"
            + "                OTHER_CFLAGS = \"-DNS_BLOCK_ASSERTIONS=1\";\n"
            + "            };\n"
            + "            name = Release;\n"
            + "        };\n"
            + "        28 /* AppStore */ = {\n"
            + "            isa = XCBuildConfiguration;\n"
            + "            buildSettings = {\n"
            + "                ALWAYS_SEARCH_USER_PATHS = NO;\n"
            + "                COPY_PHASE_STRIP = YES;\n"
            + "                GCC_PRECOMPILE_PREFIX_HEADER = YES;\n"
            + "                INFOPLIST_FILE = \"../build/xcode/sys/__PROJNAME__-Info.plist\";\n"
            + "                PRODUCT_NAME = __PROJNAME__;\n"
            + "            };\n"
            + "            name = AppStore;\n"
            + "        };\n"
            + "        20 /* Debug */ = {\n"
            + "            isa = XCBuildConfiguration;\n"
            + "            buildSettings = {\n"
            + "                ALWAYS_SEARCH_USER_PATHS = NO;\n"
            + "                COPY_PHASE_STRIP = NO;\n"
            + "                GCC_DYNAMIC_NO_PIC = NO;\n"
            + "                GCC_OPTIMIZATION_LEVEL = 0;\n"
            + "                GCC_PRECOMPILE_PREFIX_HEADER = YES;\n"
            + "                INFOPLIST_FILE = \"../build/xcode/sys/__PROJNAME__-Info.plist\";\n"
            + "                PRODUCT_NAME = __PROJNAME__;\n"
            + "            };\n"
            + "            name = Debug;\n"
            + "        };\n"
            + "        21 /* Release */ = {\n"
            + "            isa = XCBuildConfiguration;\n"
            + "            buildSettings = {\n"
            + "                ALWAYS_SEARCH_USER_PATHS = NO;\n"
            + "                COPY_PHASE_STRIP = YES;\n"
            + "                GCC_PRECOMPILE_PREFIX_HEADER = YES;\n"
            + "                INFOPLIST_FILE = \"../build/xcode/sys/__PROJNAME__-Info.plist\";\n"
            + "                PRODUCT_NAME = __PROJNAME__;\n"
            + "            };\n"
            + "            name = Release;\n"
            + "        };\n"
            + "/* End XCBuildConfiguration section */\n"
            + "\n"
            + "/* Begin XCConfigurationList section */\n"
            + "        19 /* Build configuration list for PBXProject \"__PROJNAME__\" */ = {\n"
            + "            isa = XCConfigurationList;\n"
            + "            buildConfigurations = (\n"
            + "                22 /* Debug */,\n"
            + "                23 /* Release */,\n"
            + "                29 /* AppStore */,\n"
            + "            );\n"
            + "            defaultConfigurationIsVisible = 0;\n"
            + "            defaultConfigurationName = Release;\n"
            + "        };\n"
            + "        15 /* Build configuration list for PBXNativeTarget \"__PROJNAME__\" */ = {\n"
            + "            isa = XCConfigurationList;\n"
            + "            buildConfigurations = (\n"
            + "                20 /* Debug */,\n"
            + "                21 /* Release */,\n"
            + "                28 /* AppStore */,\n"
            + "            );\n"
            + "            defaultConfigurationIsVisible = 0;\n"
            + "            defaultConfigurationName = Release;\n"
            + "        };\n"
            + "/* End XCConfigurationList section */\n"
            + "\n"
            + "	};\n"
            + "	rootObject = 18 /* Project object */;\n"
            + "}\n";
}
