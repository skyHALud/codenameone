/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.niochar.charset.additional;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

import org.apache.harmony.nio.AddressUtil;
import org.apache.harmony.niochar.CharsetProviderImpl;

public class TIS_620 extends Charset {

        public TIS_620(String csName, String[] aliases) {
            super(csName, aliases);
        }

        public boolean contains(Charset cs) {
            return cs.name().equalsIgnoreCase("IBM367") || cs.name().equalsIgnoreCase("TIS-620") || cs.name().equalsIgnoreCase("US-ASCII") ;
        }

        public CharsetDecoder newDecoder() {
            return new Decoder(this);
        }

        public CharsetEncoder newEncoder() {
            return new Encoder(this);
        }

	private static final class Decoder extends CharsetDecoder{
		private Decoder(Charset cs){
			super(cs, 1, 1);

		}

		private native int nDecode(char[] array, int arrPosition, int remaining, long outAddr, int absolutePos);


		protected CoderResult decodeLoop(ByteBuffer bb, CharBuffer cb){
                        int cbRemaining = cb.remaining();
		        if(CharsetProviderImpl.hasLoadedNatives() && bb.isDirect() && bb.hasRemaining() && cb.hasArray()){
		            int toProceed = bb.remaining();
		            int cbPos = cb.position();
		            int bbPos = bb.position();
		            boolean throwOverflow = false; 
		            if( cbRemaining < toProceed ) { 
		                toProceed = cbRemaining;
                                throwOverflow = true;
                            }
                            int res = nDecode(cb.array(), cb.arrayOffset()+cbPos, toProceed, AddressUtil.getDirectBufferAddress(bb), bbPos);
                            if( res <= 0 ) {
                                bb.position(bbPos-res);
                                cb.position(cbPos-res);
                                return CoderResult.unmappableForLength(1);
                            }else{
                                cb.position(cbPos+res);
                                bb.position(bbPos+res);
                                if(throwOverflow) return CoderResult.OVERFLOW;
                            } 
                        }else{
                            if(bb.hasArray() && cb.hasArray()) {
                                int rem = bb.remaining();
                                rem = cbRemaining >= rem ? rem : cbRemaining;
                                byte[] bArr = bb.array();
                                char[] cArr = cb.array();
                                int bStart = bb.position();
                                int cStart = cb.position();
                                int i;
                                for(i=bStart; i<bStart+rem; i++) {
                                    char in = (char)(bArr[i] & 0xFF);
                                    if(in >= 26){
                                        int index = (int)in - 26;
                                        if(arr[index] != 0x0000){ 
                                            cArr[cStart++] = (char)arr[index];
                                        }else{
                                            bb.position(i); cb.position(cStart);
                                            return CoderResult.unmappableForLength(1);
                                        } 
                                    }else {
                                        cArr[cStart++] = (char)(in & 0xFF);
                                    }
                                }
                                bb.position(i);
                                cb.position(cStart);
                                if(rem == cbRemaining && bb.hasRemaining()) return CoderResult.OVERFLOW;
                            } else {
                                while(bb.hasRemaining()){
                                    if( cbRemaining == 0 ) return CoderResult.OVERFLOW;
                                    char in = (char)(bb.get() & 0xFF);
                                    if(in >= 26){
                                        int index = (int)in - 26;
                                        if(arr[index] != 0x0000){ 
                                            cb.put(arr[index]);
                                        }else{
                                            bb.position(bb.position()-1);
                                            return CoderResult.unmappableForLength(1);
                                        } 
                                    }else {
                                        cb.put((char)(in & 0xFF));
                                    }
                                cbRemaining--;
                                }
                            }
			} 
                        return CoderResult.UNDERFLOW;
		}

		final static char[] arr = {
                0x001C,0x001B,0x007F,0x001D,0x001E,0x001F,
                0x0020,0x0021,0x0022,0x0023,0x0024,0x0025,0x0026,0x0027,
                0x0028,0x0029,0x002A,0x002B,0x002C,0x002D,0x002E,0x002F,
                0x0030,0x0031,0x0032,0x0033,0x0034,0x0035,0x0036,0x0037,
                0x0038,0x0039,0x003A,0x003B,0x003C,0x003D,0x003E,0x003F,
                0x0040,0x0041,0x0042,0x0043,0x0044,0x0045,0x0046,0x0047,
                0x0048,0x0049,0x004A,0x004B,0x004C,0x004D,0x004E,0x004F,
                0x0050,0x0051,0x0052,0x0053,0x0054,0x0055,0x0056,0x0057,
                0x0058,0x0059,0x005A,0x005B,0x005C,0x005D,0x005E,0x005F,
                0x0060,0x0061,0x0062,0x0063,0x0064,0x0065,0x0066,0x0067,
                0x0068,0x0069,0x006A,0x006B,0x006C,0x006D,0x006E,0x006F,
                0x0070,0x0071,0x0072,0x0073,0x0074,0x0075,0x0076,0x0077,
                0x0078,0x0079,0x007A,0x007B,0x007C,0x007D,0x007E,0x001A,
                0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,
                0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,
                0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,
                0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,
                0x0E48,0x0E01,0x0E02,0x0E03,0x0E04,0x0E05,0x0E06,0x0E07,
                0x0E08,0x0E09,0x0E0A,0x0E0B,0x0E0C,0x0E0D,0x0E0E,0x0E0F,
                0x0E10,0x0E11,0x0E12,0x0E13,0x0E14,0x0E15,0x0E16,0x0E17,
                0x0E18,0x0E19,0x0E1A,0x0E1B,0x0E1C,0x0E1D,0x0E1E,0x0E1F,
                0x0E20,0x0E21,0x0E22,0x0E23,0x0E24,0x0E25,0x0E26,0x0E27,
                0x0E28,0x0E29,0x0E2A,0x0E2B,0x0E2C,0x0E2D,0x0E2E,0x0E2F,
                0x0E30,0x0E31,0x0E32,0x0E33,0x0E34,0x0E35,0x0E36,0x0E37,
                0x0E38,0x0E39,0x0E3A,0x0E49,0x0E4A,0x0E4B,0x0E4C,0x0E3F,
                0x0E40,0x0E41,0x0E42,0x0E43,0x0E44,0x0E45,0x0E46,0x0E47,
                0x0E48,0x0E49,0x0E4A,0x0E4B,0x0E4C,0x0E4D,0x0E4E,0x0E4F,
                0x0E50,0x0E51,0x0E52,0x0E53,0x0E54,0x0E55,0x0E56,0x0E57,
                0x0E58,0x0E59,0x0E5A,0x0E5B,0x00A2,0x00AC,0x00A6,0x00A0
		};
        }

	private static final class Encoder extends CharsetEncoder{
		private Encoder(Charset cs){
			super(cs, 1, 1);
		}
                   
		private native void nEncode(long outAddr, int absolutePos, char[] array, int arrPosition, int[] res);
                                                                                                                          
		protected CoderResult encodeLoop(CharBuffer cb, ByteBuffer bb){
                        int bbRemaining = bb.remaining();
                        if(CharsetProviderImpl.hasLoadedNatives() && bb.isDirect() && cb.hasRemaining() && cb.hasArray()){
		            int toProceed = cb.remaining();
		            int cbPos = cb.position();
		            int bbPos = bb.position();
		            boolean throwOverflow = false; 
		            if( bbRemaining < toProceed ) { 
		                toProceed = bbRemaining; 
                                throwOverflow = true;
                            }
                            int[] res = {toProceed, 0};
                            nEncode(AddressUtil.getDirectBufferAddress(bb), bbPos, cb.array(), cb.arrayOffset()+cbPos, res);
                            if( res[0] <= 0 ) {
                                bb.position(bbPos-res[0]);
                                cb.position(cbPos-res[0]);
                                if(res[1]!=0) {
                                    if(res[1] < 0)
                                        return CoderResult.malformedForLength(-res[1]);
                                    else
                                        return CoderResult.unmappableForLength(res[1]);
                                }
                            }else{
                                bb.position(bbPos+res[0]);
                                cb.position(cbPos+res[0]);
                                if(throwOverflow) return CoderResult.OVERFLOW;
                            }
                        }else{
                            if(bb.hasArray() && cb.hasArray()) {
                                byte[] byteArr = bb.array();
                                char[] charArr = cb.array();
                                int rem = cb.remaining();
                                int byteArrStart = bb.position();
                                rem = bbRemaining <= rem ? bbRemaining : rem;
                                int x;
                                for(x = cb.position(); x < cb.position()+rem; x++) {
                                    char c = charArr[x];
                                    if(c > (char)0x0E5B){
                                        if (c >= 0xD800 && c <= 0xDFFF) {
                                            if(x+1 < cb.limit()) {
                                                char c1 = charArr[x+1];
                                                if(c1 >= 0xD800 && c1 <= 0xDFFF) {
                                                    cb.position(x); bb.position(byteArrStart);
                                                    return CoderResult.unmappableForLength(2);
                                                }
                                            } else {
                                                cb.position(x); bb.position(byteArrStart);
                                                return CoderResult.UNDERFLOW;
                                            }
                                            cb.position(x); bb.position(byteArrStart);
                                            return CoderResult.malformedForLength(1);
                                        }
                                        cb.position(x); bb.position(byteArrStart);
                                        return CoderResult.unmappableForLength(1);
                                    }else{
                                        if(c < 0x1A) {
                                            byteArr[byteArrStart++] = (byte)c;
                                        } else {
                                            int index = (int)c >> 8;
                                            index = encodeIndex[index];
                                            if(index < 0) {
                                                cb.position(x); bb.position(byteArrStart);
                                                return CoderResult.unmappableForLength(1);
                                            }
                                            index <<= 8;
                                            index += (int)c & 0xFF;
                                            if((byte)arr[index] != 0){
                                                byteArr[byteArrStart++] = (byte)arr[index];
                                            }else{
                                                cb.position(x); bb.position(byteArrStart);
                                                return CoderResult.unmappableForLength(1);
                                            }
                                        }
                                    }
                                }
                                cb.position(x);
                                bb.position(byteArrStart);
                                if(rem == bbRemaining && cb.hasRemaining()) {
                                    return CoderResult.OVERFLOW;
                                }
                            } else {
                                while(cb.hasRemaining()){
                                    if( bbRemaining == 0 ) return CoderResult.OVERFLOW;
                                    char c = cb.get();
                                    if(c > (char)0x0E5B){   
                                        if (c >= 0xD800 && c <= 0xDFFF) {
                                            if(cb.hasRemaining()) {
                                                char c1 = cb.get();
                                                if(c1 >= 0xD800 && c1 <= 0xDFFF) {
                                                    cb.position(cb.position()-2);
                                                    return CoderResult.unmappableForLength(2);
                                                } else {
                                                    cb.position(cb.position()-1);
                                                }
                                            } else {
                                                cb.position(cb.position()-1);
                                                return CoderResult.UNDERFLOW;
                                            }
                                            cb.position(cb.position()-1);
                                            return CoderResult.malformedForLength(1);
                                        }
                                        cb.position(cb.position()-1);
                                        return CoderResult.unmappableForLength(1);
                                    }else{
                                        if(c < 0x1A) {
                                            bb.put((byte)c);
                                        } else {
                                            int index = (int)c >> 8;
                                            index = encodeIndex[index];
                                            if(index < 0) {
                                                cb.position(cb.position()-1);
                                                return CoderResult.unmappableForLength(1);
                                            }
                                            index <<= 8;
                                            index += (int)c & 0xFF;
                                            if((byte)arr[index] != 0){
                                                bb.put((byte)arr[index]);
                                            }else{
                                                cb.position(cb.position()-1);
                                                return CoderResult.unmappableForLength(1);
                                            }
                                        }
                                        bbRemaining--;
                                    }
                                }
			    }
			}
			return CoderResult.UNDERFLOW;
		}

                final static char arr[] = {
                 
                0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,
                0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x7F,0x1B,0x1A,0x1D,0x1E,0x1F,
                0x20,0x21,0x22,0x23,0x24,0x25,0x26,0x27,0x28,0x29,0x2A,0x2B,0x2C,0x2D,0x2E,0x2F,
                0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x3A,0x3B,0x3C,0x3D,0x3E,0x3F,
                0x40,0x41,0x42,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x4B,0x4C,0x4D,0x4E,0x4F,
                0x50,0x51,0x52,0x53,0x54,0x55,0x56,0x57,0x58,0x59,0x5A,0x5B,0x5C,0x5D,0x5E,0x5F,
                0x60,0x61,0x62,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x6B,0x6C,0x6D,0x6E,0x6F,
                0x70,0x71,0x72,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x7B,0x7C,0x7D,0x7E,0x1C,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0xFF,0x00,0xFC,0x00,0x00,0x00,0xFE,0x00,0x00,0x00,0x00,0x00,0xFD,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                 
                0x00,0xA1,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xAB,0xAC,0xAD,0xAE,0xAF,
                0xB0,0xB1,0xB2,0xB3,0xB4,0xB5,0xB6,0xB7,0xB8,0xB9,0xBA,0xBB,0xBC,0xBD,0xBE,0xBF,
                0xC0,0xC1,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xCB,0xCC,0xCD,0xCE,0xCF,
                0xD0,0xD1,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,0x00,0x00,0x00,0x00,0xDF,
                0xE0,0xE1,0xE2,0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xEB,0xEC,0xED,0xEE,0xEF,
                0xF0,0xF1,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA,0xFB,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
                };

                final static int[] encodeIndex = {
                 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                };
	}         
}
