/*      */ package com.tenbyten.SG02;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.LineNumberReader;
/*      */ import java.text.ParseException;
/*      */ import java.util.Iterator;
/*      */ import java.util.ResourceBundle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Chord
/*      */   implements Comparable<Object>, Cloneable
/*      */ {
/*      */   public static final byte kBuiltin = 1;
/*      */   public static final byte kUserDefined = 2;
/*      */   public static final byte kSongDefined = 4;
/*      */   public static final byte kStringOpen = 0;
/*      */   public static final byte kStringNotPlayed = 15;
/*      */   public static final byte kStringUkuleleMask = 14;
/*      */   private String m_strName;
/*      */   private String m_strKeyName;
/*      */   private String m_strDoReMiName;
/*      */   private int m_nEBGDAE;
/*      */   private byte m_base_fret;
/*      */   private byte m_source;
/*      */   private boolean m_bDifficult;
/*      */   private boolean m_bMajor;
/*      */   private int m_nFingerings;
/*   37 */   private static final String[] k_notesUp = new String[] { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
/*   38 */   private static final String[] k_notesDown = new String[] { "G", "Gb", "F", "E", "Eb", "D", "Db", "C", "B", "Bb", "A", "Ab" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chord(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte paramByte, boolean paramBoolean) {
/*   52 */     this.m_strName = paramString.intern();
/*   53 */     this.m_strKeyName = null;
/*   54 */     this.m_strDoReMiName = null;
/*   55 */     this.m_nEBGDAE = paramInt6 << 20 | paramInt5 << 16 | paramInt4 << 12 | paramInt3 << 8 | paramInt2 << 4 | paramInt1;
/*   56 */     this.m_base_fret = (byte)paramInt7;
/*   57 */     this.m_source = paramByte;
/*   58 */     this.m_bDifficult = paramBoolean;
/*   59 */     this.m_nFingerings = 0;
/*   60 */     this.m_bMajor = (-1 == this.m_strName.indexOf('m') || -1 != this.m_strName.indexOf("maj"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chord(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte paramByte, boolean paramBoolean, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13) {
/*   81 */     this.m_strName = paramString.intern();
/*   82 */     this.m_strKeyName = null;
/*   83 */     this.m_strDoReMiName = null;
/*   84 */     this.m_nEBGDAE = paramInt6 << 20 | paramInt5 << 16 | paramInt4 << 12 | paramInt3 << 8 | paramInt2 << 4 | paramInt1;
/*   85 */     this.m_base_fret = (byte)paramInt7;
/*   86 */     this.m_source = paramByte;
/*   87 */     this.m_bDifficult = paramBoolean;
/*   88 */     this.m_nFingerings = paramInt13 << 20 | paramInt12 << 16 | paramInt11 << 12 | paramInt10 << 8 | paramInt9 << 4 | paramInt8;
/*      */     
/*   90 */     this.m_bMajor = (-1 == this.m_strName.indexOf('m') || -1 != this.m_strName.indexOf("maj"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chord(String paramString1, String paramString2, byte paramByte, boolean paramBoolean) throws ParseException {
/*   98 */     this.m_strName = paramString1.intern();
/*   99 */     this.m_strDoReMiName = null;
/*  100 */     this.m_nEBGDAE = 0;
/*  101 */     this.m_base_fret = 0;
/*  102 */     this.m_source = paramByte;
/*  103 */     this.m_bDifficult = paramBoolean;
/*  104 */     this.m_nFingerings = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  117 */     if (-1 != paramString1.indexOf("bas")) {
/*      */       
/*  119 */       paramString2 = paramString1;
/*  120 */       byte b1 = 1;
/*  121 */       while (!Character.isWhitespace(paramString2.charAt(b1)))
/*  122 */         b1++; 
/*  123 */       this.m_strName = paramString2.substring(0, b1);
/*      */       
/*  125 */       paramString2 = paramString2.substring(b1);
/*      */     } 
/*      */ 
/*      */     
/*  129 */     char[] arrayOfChar = paramString2.toCharArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  135 */     byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  140 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */     
/*  142 */     if ('b' != arrayOfChar[b] || 'a' != arrayOfChar[b + 1] || 's' != arrayOfChar[b + 2]) {
/*  143 */       throw new ParseException("base-fret", b);
/*      */     }
/*      */ 
/*      */     
/*  147 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  148 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */     
/*  150 */     this.m_base_fret = 0;
/*  151 */     if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*      */ 
/*      */       
/*  154 */       if (!Character.isWhitespace(arrayOfChar[b + 1]))
/*  155 */         this.m_base_fret = (byte)((arrayOfChar[b++] - 48) * 10); 
/*  156 */       this.m_base_fret = (byte)(this.m_base_fret + (byte)(arrayOfChar[b] - 48));
/*      */     } 
/*      */ 
/*      */     
/*  160 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  166 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */     
/*  168 */     if ('f' != arrayOfChar[b] || 'r' != arrayOfChar[b + 1] || 'e' != arrayOfChar[b + 2]) {
/*  169 */       throw new ParseException("frets", b);
/*      */     }
/*      */ 
/*      */     
/*  173 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  174 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */     
/*  177 */     if ('-' == arrayOfChar[b] || 'x' == arrayOfChar[b]) {
/*  178 */       this.m_nEBGDAE = 15;
/*      */     } else {
/*  180 */       this.m_nEBGDAE = arrayOfChar[b] - 48;
/*      */     } 
/*  182 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  183 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */     
/*  186 */     if ('-' == arrayOfChar[b] || 'x' == arrayOfChar[b]) {
/*  187 */       this.m_nEBGDAE |= 0xF0;
/*      */     } else {
/*  189 */       this.m_nEBGDAE |= arrayOfChar[b] - 48 << 4;
/*      */     } 
/*  191 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  192 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */     
/*  195 */     if ('-' == arrayOfChar[b] || 'x' == arrayOfChar[b]) {
/*  196 */       this.m_nEBGDAE |= 0xF00;
/*      */     } else {
/*  198 */       this.m_nEBGDAE |= arrayOfChar[b] - 48 << 8;
/*      */     } 
/*  200 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  201 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */     
/*  204 */     if ('-' == arrayOfChar[b] || 'x' == arrayOfChar[b]) {
/*  205 */       this.m_nEBGDAE |= 0xF000;
/*      */     } else {
/*  207 */       this.m_nEBGDAE |= arrayOfChar[b] - 48 << 12;
/*      */     } 
/*  209 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  210 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */     
/*  213 */     if ('-' == arrayOfChar[b] || 'x' == arrayOfChar[b]) {
/*  214 */       this.m_nEBGDAE |= 0xF0000;
/*  215 */     } else if ('u' == arrayOfChar[b]) {
/*  216 */       this.m_nEBGDAE |= 0xE0000;
/*      */     } else {
/*  218 */       this.m_nEBGDAE |= arrayOfChar[b] - 48 << 16;
/*      */     } 
/*  220 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  221 */     for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */     
/*  224 */     if ('-' == arrayOfChar[b] || 'x' == arrayOfChar[b]) {
/*  225 */       this.m_nEBGDAE |= 0xF00000;
/*  226 */     } else if ('u' == arrayOfChar[b]) {
/*  227 */       this.m_nEBGDAE |= 0xE00000;
/*      */     } else {
/*  229 */       this.m_nEBGDAE |= arrayOfChar[b] - 48 << 20;
/*      */     } 
/*      */     
/*  232 */     for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */ 
/*      */     
/*  236 */     if (b < arrayOfChar.length) {
/*      */ 
/*      */       
/*  239 */       for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */       
/*  241 */       if (b < arrayOfChar.length && 'f' == arrayOfChar[b] && 'i' == arrayOfChar[b + 1] && 'n' == arrayOfChar[b + 2]) {
/*      */ 
/*      */ 
/*      */         
/*  245 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  246 */         for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */         
/*  249 */         if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*  250 */           this.m_nFingerings = arrayOfChar[b] - 48;
/*      */         }
/*  252 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  253 */         for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */         
/*  256 */         if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*  257 */           this.m_nFingerings |= arrayOfChar[b] - 48 << 4;
/*      */         }
/*  259 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  260 */         for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */         
/*  263 */         if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*  264 */           this.m_nFingerings |= arrayOfChar[b] - 48 << 8;
/*      */         }
/*  266 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  267 */         for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */         
/*  270 */         if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*  271 */           this.m_nFingerings |= arrayOfChar[b] - 48 << 12;
/*      */         }
/*  273 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  274 */         for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */         
/*  277 */         if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*  278 */           this.m_nFingerings |= arrayOfChar[b] - 48 << 16;
/*      */         }
/*  280 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*  281 */         for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */ 
/*      */         
/*  284 */         if ('-' != arrayOfChar[b] && 'x' != arrayOfChar[b]) {
/*  285 */           this.m_nFingerings |= arrayOfChar[b] - 48 << 20;
/*      */         }
/*      */         
/*  288 */         for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  293 */     if (b < arrayOfChar.length) {
/*      */ 
/*      */       
/*  296 */       for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++);
/*      */       
/*  298 */       if (b < arrayOfChar.length && 'k' == arrayOfChar[b] && 'e' == arrayOfChar[b + 1] && 'y' == arrayOfChar[b + 2])
/*      */       {
/*  300 */         this.m_strKeyName = paramString2.substring(b + 4).trim().intern();
/*      */       }
/*      */     } 
/*      */     
/*  304 */     this.m_bMajor = (-1 == this.m_strName.indexOf('m') || -1 != this.m_strName.indexOf("maj"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/*  310 */     return super.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String paramString) {
/*  317 */     this.m_strName = paramString;
/*  318 */     this.m_strDoReMiName = null;
/*  319 */     this.m_bMajor = (-1 == this.m_strName.indexOf('m') || -1 != this.m_strName.indexOf("maj"));
/*      */   }
/*      */ 
/*      */   
/*      */   public String getName() {
/*  324 */     return this.m_strName;
/*      */   }
/*      */   
/*      */   public String getDoReMiName() {
/*  328 */     if (null == this.m_strDoReMiName && null != this.m_strName) {
/*      */       
/*  330 */       StringBuffer stringBuffer = new StringBuffer(this.m_strName);
/*  331 */       for (byte b = 0; b < stringBuffer.length(); b++) {
/*      */         
/*  333 */         char c = stringBuffer.charAt(b);
/*  334 */         switch (c) {
/*      */           
/*      */           case 'C':
/*  337 */             stringBuffer.replace(b, b + 1, "Do");
/*      */             break;
/*      */           case 'D':
/*  340 */             stringBuffer.replace(b, b + 1, "Re");
/*      */             break;
/*      */           case 'E':
/*  343 */             stringBuffer.replace(b, b + 1, "Mi");
/*      */             break;
/*      */           case 'F':
/*  346 */             stringBuffer.replace(b, b + 1, "Fa");
/*      */             break;
/*      */           case 'G':
/*  349 */             stringBuffer.replace(b, b + 1, "Sol");
/*      */             break;
/*      */           case 'A':
/*  352 */             stringBuffer.replace(b, b + 1, "La");
/*      */             break;
/*      */           case 'B':
/*  355 */             stringBuffer.replace(b, b + 1, "Si");
/*      */             break;
/*      */           default:
/*  358 */             b--;
/*      */             break;
/*      */         } 
/*  361 */         b++;
/*      */       } 
/*  363 */       this.m_strDoReMiName = stringBuffer.toString().intern();
/*      */     } 
/*      */     
/*  366 */     return this.m_strDoReMiName;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTransposeValue() {
/*  372 */     if (isMajor()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  378 */       if (this.m_strName.startsWith("C#"))
/*  379 */         return 1; 
/*  380 */       if (this.m_strName.startsWith("F#"))
/*  381 */         return 6; 
/*  382 */       if (this.m_strName.startsWith("Bb") || this.m_strName.startsWith("A#"))
/*  383 */         return -2; 
/*  384 */       if (this.m_strName.startsWith("Eb") || this.m_strName.startsWith("D#"))
/*  385 */         return -9; 
/*  386 */       if (this.m_strName.startsWith("Ab") || this.m_strName.startsWith("G#"))
/*  387 */         return -4; 
/*  388 */       if (this.m_strName.startsWith("Db"))
/*  389 */         return -11; 
/*  390 */       if (this.m_strName.startsWith("Gb"))
/*  391 */         return -6; 
/*  392 */       if (this.m_strName.startsWith("Cb"))
/*  393 */         return -1; 
/*  394 */       if (this.m_strName.charAt(0) == 'C')
/*  395 */         return -12; 
/*  396 */       if (this.m_strName.charAt(0) == 'G')
/*  397 */         return 7; 
/*  398 */       if (this.m_strName.charAt(0) == 'D')
/*  399 */         return 2; 
/*  400 */       if (this.m_strName.charAt(0) == 'A')
/*  401 */         return 9; 
/*  402 */       if (this.m_strName.charAt(0) == 'E' || this.m_strName.startsWith("Fb"))
/*  403 */         return 4; 
/*  404 */       if (this.m_strName.charAt(0) == 'B' || this.m_strName.startsWith("Cb"))
/*  405 */         return 11; 
/*  406 */       if (this.m_strName.charAt(0) == 'F') {
/*  407 */         return -7;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  415 */       if (this.m_strName.startsWith("F#") || this.m_strName.startsWith("Gb"))
/*  416 */         return 9; 
/*  417 */       if (this.m_strName.startsWith("C#") || this.m_strName.startsWith("Db"))
/*  418 */         return 4; 
/*  419 */       if (this.m_strName.startsWith("G#"))
/*  420 */         return 11; 
/*  421 */       if (this.m_strName.startsWith("D#"))
/*  422 */         return 6; 
/*  423 */       if (this.m_strName.startsWith("A#"))
/*  424 */         return 1; 
/*  425 */       if (this.m_strName.startsWith("Bb"))
/*  426 */         return -11; 
/*  427 */       if (this.m_strName.startsWith("Eb"))
/*  428 */         return -6; 
/*  429 */       if (this.m_strName.startsWith("Ab"))
/*  430 */         return -1; 
/*  431 */       if (this.m_strName.charAt(0) == 'A')
/*  432 */         return 0; 
/*  433 */       if (this.m_strName.charAt(0) == 'E' || this.m_strName.startsWith("Fb"))
/*  434 */         return 7; 
/*  435 */       if (this.m_strName.charAt(0) == 'B' || this.m_strName.startsWith("Cb"))
/*  436 */         return 2; 
/*  437 */       if (this.m_strName.charAt(0) == 'D')
/*  438 */         return -7; 
/*  439 */       if (this.m_strName.charAt(0) == 'G')
/*  440 */         return -2; 
/*  441 */       if (this.m_strName.charAt(0) == 'C' || this.m_strName.startsWith("B#"))
/*  442 */         return -9; 
/*  443 */       if (this.m_strName.charAt(0) == 'F') {
/*  444 */         return -4;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  454 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String transposeChord(String paramString, int paramInt) {
/*  460 */     if (0 == paramInt)
/*  461 */       return paramString; 
/*  462 */     return transposeChord(paramString, 0, paramString.length(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String transposeChord(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  472 */     StringBuffer stringBuffer = new StringBuffer(16);
/*      */     
/*  474 */     if (0 != paramInt3) {
/*      */       String[] arrayOfString;
/*      */ 
/*      */       
/*  478 */       int i = paramInt1;
/*  479 */       int j = paramInt3;
/*  480 */       byte b = 1;
/*      */       
/*  482 */       if (paramInt3 < 0) {
/*      */         
/*  484 */         arrayOfString = k_notesDown;
/*  485 */         j *= -1;
/*  486 */         b = -1;
/*      */       }
/*      */       else {
/*      */         
/*  490 */         arrayOfString = k_notesUp;
/*      */       } 
/*      */       
/*  493 */       while (paramInt2 + paramInt1 > i)
/*      */       {
/*  495 */         char c = paramString.charAt(i);
/*  496 */         if (c >= 'A' && c <= 'G') {
/*      */           
/*  498 */           int k = 0;
/*      */           
/*  500 */           for (k = 0; k != 12; k++) {
/*      */             
/*  502 */             if (arrayOfString[k].charAt(0) == paramString.charAt(i)) {
/*      */               
/*  504 */               if (i + 1 < paramInt2 + paramInt1) {
/*      */                 
/*  506 */                 if ('#' == paramString.charAt(i + 1)) {
/*      */                   
/*  508 */                   k += b;
/*  509 */                   i++; break;
/*      */                 } 
/*  511 */                 if ('b' == paramString.charAt(i + 1)) {
/*      */                   
/*  513 */                   k -= b;
/*  514 */                   i++;
/*      */                 } 
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*  521 */           k += j;
/*      */           
/*  523 */           while (k < 0)
/*  524 */             k += 12; 
/*  525 */           while (k > 11) {
/*  526 */             k -= 12;
/*      */           }
/*  528 */           stringBuffer.append(arrayOfString[k]);
/*      */         }
/*      */         else {
/*      */           
/*  532 */           stringBuffer.append(c);
/*      */         } 
/*      */         
/*  535 */         i++;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  541 */       return paramString.substring(paramInt1, paramInt1 + paramInt2).intern();
/*      */     } 
/*      */     
/*  544 */     return stringBuffer.toString().intern();
/*      */   }
/*      */   public byte getSource() {
/*  547 */     return this.m_source;
/*      */   }
/*  549 */   public byte getLowE() { return getFretForString(0); }
/*  550 */   public byte getB() { return getFretForString(1); }
/*  551 */   public byte getG() { return getFretForString(2); }
/*  552 */   public byte getD() { return getFretForString(3); }
/*  553 */   public byte getA() { return getFretForString(4); } public byte getHighE() {
/*  554 */     return getFretForString(5);
/*      */   } public byte getBaseFret() {
/*  556 */     return this.m_base_fret;
/*      */   } public String getKeySignature() {
/*  558 */     return this.m_strKeyName;
/*      */   }
/*  560 */   public boolean isDifficult() { return this.m_bDifficult; }
/*  561 */   public boolean isMajor() { return this.m_bMajor; } public boolean isMinor() {
/*  562 */     return !this.m_bMajor;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUkulele() {
/*  567 */     return (14 == (this.m_nEBGDAE >> 16 & 0xF) && 14 == (this.m_nEBGDAE >> 20 & 0xF));
/*      */   }
/*      */   
/*  570 */   public byte getFingeringLowE() { return getFingeringForString(0); }
/*  571 */   public byte getFingeringB() { return getFingeringForString(1); }
/*  572 */   public byte getFingeringG() { return getFingeringForString(2); }
/*  573 */   public byte getFingeringD() { return getFingeringForString(3); }
/*  574 */   public byte getFingeringA() { return getFingeringForString(4); } public byte getFingeringHighE() {
/*  575 */     return getFingeringForString(5);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getFretForString(int paramInt) {
/*  580 */     int i = 15 << paramInt * 4;
/*  581 */     return (byte)((this.m_nEBGDAE & i) >> paramInt * 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getFingeringForString(int paramInt) {
/*  589 */     int i = 15 << paramInt * 4;
/*  590 */     return (byte)((this.m_nFingerings & i) >> paramInt * 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFrets(char paramChar1, char paramChar2, char paramChar3, char paramChar4, char paramChar5, char paramChar6) {
/*  597 */     if ('o' == paramChar1)
/*  598 */       paramChar1 = '0'; 
/*  599 */     if ('-' == paramChar1 || 'x' == paramChar1) {
/*  600 */       this.m_nEBGDAE = 15;
/*      */     } else {
/*  602 */       this.m_nEBGDAE = paramChar1 - 48;
/*      */     } 
/*      */     
/*  605 */     if ('o' == paramChar2)
/*  606 */       paramChar2 = '0'; 
/*  607 */     if ('-' == paramChar2 || 'x' == paramChar2) {
/*  608 */       this.m_nEBGDAE |= 0xF0;
/*      */     } else {
/*  610 */       this.m_nEBGDAE |= paramChar2 - 48 << 4;
/*      */     } 
/*      */     
/*  613 */     if ('o' == paramChar3)
/*  614 */       paramChar3 = '0'; 
/*  615 */     if ('-' == paramChar3 || 'x' == paramChar3) {
/*  616 */       this.m_nEBGDAE |= 0xF00;
/*      */     } else {
/*  618 */       this.m_nEBGDAE |= paramChar3 - 48 << 8;
/*      */     } 
/*      */     
/*  621 */     if ('o' == paramChar4)
/*  622 */       paramChar4 = '0'; 
/*  623 */     if ('-' == paramChar4 || 'x' == paramChar4) {
/*  624 */       this.m_nEBGDAE |= 0xF000;
/*      */     } else {
/*  626 */       this.m_nEBGDAE |= paramChar4 - 48 << 12;
/*      */     } 
/*      */     
/*  629 */     if ('o' == paramChar5)
/*  630 */       paramChar5 = '0'; 
/*  631 */     if ('-' == paramChar5 || 'x' == paramChar5) {
/*  632 */       this.m_nEBGDAE |= 0xF0000;
/*      */     } else {
/*  634 */       this.m_nEBGDAE |= paramChar5 - 48 << 16;
/*      */     } 
/*      */     
/*  637 */     if ('o' == paramChar6)
/*  638 */       paramChar6 = '0'; 
/*  639 */     if ('-' == paramChar6 || 'x' == paramChar6) {
/*  640 */       this.m_nEBGDAE |= 0xF00000;
/*      */     } else {
/*  642 */       this.m_nEBGDAE |= paramChar6 - 48 << 20;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFingerings(char paramChar1, char paramChar2, char paramChar3, char paramChar4, char paramChar5, char paramChar6) {
/*  649 */     this.m_nFingerings = 0;
/*      */ 
/*      */     
/*  652 */     if ('-' != paramChar1 && 'x' != paramChar1) {
/*  653 */       this.m_nFingerings = paramChar1 - 48;
/*      */     }
/*      */     
/*  656 */     if ('-' != paramChar2 && 'x' != paramChar2) {
/*  657 */       this.m_nFingerings |= paramChar2 - 48 << 4;
/*      */     }
/*      */     
/*  660 */     if ('-' != paramChar3 && 'x' != paramChar3) {
/*  661 */       this.m_nFingerings |= paramChar3 - 48 << 8;
/*      */     }
/*      */     
/*  664 */     if ('-' != paramChar4 && 'x' != paramChar4) {
/*  665 */       this.m_nFingerings |= paramChar4 - 48 << 12;
/*      */     }
/*      */     
/*  668 */     if ('-' != paramChar5 && 'x' != paramChar5) {
/*  669 */       this.m_nFingerings |= paramChar5 - 48 << 16;
/*      */     }
/*      */     
/*  672 */     if ('-' != paramChar6 && 'x' != paramChar6) {
/*  673 */       this.m_nFingerings |= paramChar6 - 48 << 20;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUkulele(boolean paramBoolean) {
/*  680 */     if (paramBoolean) {
/*      */       
/*  682 */       this.m_nEBGDAE &= 0xFFFF;
/*  683 */       this.m_nFingerings &= 0xFFFF;
/*  684 */       this.m_nEBGDAE |= 0xEE0000;
/*      */ 
/*      */     
/*      */     }
/*  688 */     else if ((this.m_nEBGDAE & 0xFF0000) == 15597568) {
/*      */       
/*  690 */       this.m_nEBGDAE &= 0xFFFF;
/*  691 */       this.m_nFingerings &= 0xFFFF;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseFret(byte paramByte) {
/*  699 */     this.m_base_fret = paramByte;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeySignature(String paramString) {
/*  705 */     this.m_strKeyName = paramString;
/*  706 */     if (null != paramString && 0 == paramString.length()) {
/*  707 */       this.m_strKeyName = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  718 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  720 */     boolean bool = isUkulele();
/*      */     
/*  722 */     stringBuffer.append("{define: ");
/*  723 */     stringBuffer.append(this.m_strName);
/*  724 */     stringBuffer.append(" base-fret ");
/*  725 */     stringBuffer.append(String.valueOf(this.m_base_fret));
/*  726 */     stringBuffer.append(" frets");
/*      */     
/*  728 */     byte b1 = (byte) (bool ? 4 : 6); byte b2;
/*  729 */     for (b2 = 0; b2 < b1; b2++) {
/*      */       
/*  731 */       if (15 == getFretForString(b2)) {
/*  732 */         stringBuffer.append(" -");
/*      */       } else {
/*      */         
/*  735 */         stringBuffer.append(" ");
/*  736 */         stringBuffer.append(String.valueOf(getFretForString(b2)));
/*      */       } 
/*      */     } 
/*  739 */     if (bool) {
/*  740 */       stringBuffer.append(" u u");
/*      */     }
/*  742 */     if (0 != this.m_nFingerings) {
/*      */       
/*  744 */       stringBuffer.append(" fingers");
/*      */       
/*  746 */       for (b2 = 0; b2 < 6; b2++) {
/*      */         
/*  748 */         stringBuffer.append(" ");
/*  749 */         stringBuffer.append(String.valueOf(getFingeringForString(b2)));
/*      */       } 
/*      */     } 
/*      */     
/*  753 */     if (null != this.m_strKeyName) {
/*      */       
/*  755 */       stringBuffer.append(" key ");
/*  756 */       stringBuffer.append(this.m_strKeyName);
/*      */     } 
/*      */     
/*  759 */     stringBuffer.append("}");
/*      */     
/*  761 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initializeBuiltinChords(ChordMap paramChordMap) {
/*  769 */     addChord(paramChordMap, "Ab", 1, 3, 3, 2, 1, 1, 4, true);
/*  770 */     addChord(paramChordMap, "Ab+", 15, 15, 2, 1, 1, 0, 1, true);
/*  771 */     addChord(paramChordMap, "Ab4", 15, 15, 1, 1, 2, 4, 1, true);
/*  772 */     addChord(paramChordMap, "Ab7", 15, 15, 1, 1, 1, 2, 1, true);
/*  773 */     addChord(paramChordMap, "Ab11", 1, 3, 1, 3, 1, 1, 4, true);
/*  774 */     addChord(paramChordMap, "Absus", 15, 15, 1, 1, 2, 4, 1, true);
/*  775 */     addChord(paramChordMap, "Absus4", 15, 15, 1, 1, 2, 4, 1, true);
/*  776 */     addChord(paramChordMap, "Abdim", 15, 15, 0, 1, 0, 1, 1, true);
/*  777 */     addChord(paramChordMap, "Abmaj", 1, 3, 3, 2, 1, 1, 4, true);
/*  778 */     addChord(paramChordMap, "Abmaj7", 15, 15, 1, 1, 1, 3, 1, true);
/*  779 */     addChord(paramChordMap, "Abmin", 1, 3, 3, 1, 1, 1, 4, true);
/*  780 */     addChord(paramChordMap, "Abm", 1, 3, 3, 1, 1, 1, 4, true);
/*  781 */     addChord(paramChordMap, "Abm7", 15, 15, 1, 1, 1, 1, 4, true);
/*      */     
/*  783 */     addChord(paramChordMap, "A", 15, 0, 2, 2, 2, 0, 1, false);
/*  784 */     addChord(paramChordMap, "A+", 15, 0, 3, 2, 2, 1, 1, true);
/*  785 */     addChord(paramChordMap, "A4", 0, 0, 2, 2, 0, 0, 1, true);
/*  786 */     addChord(paramChordMap, "A6", 15, 15, 2, 2, 2, 2, 1, true);
/*  787 */     addChord(paramChordMap, "A7", 15, 0, 2, 0, 2, 0, 1, false);
/*  788 */     addChord(paramChordMap, "A7+", 15, 15, 3, 2, 2, 1, 1, true);
/*  789 */     addChord(paramChordMap, "A7(9+)", 15, 2, 2, 2, 2, 3, 1, true);
/*  790 */     addChord(paramChordMap, "A9", 15, 0, 2, 1, 0, 0, 1, true);
/*  791 */     addChord(paramChordMap, "A11", 15, 4, 2, 4, 3, 3, 1, true);
/*  792 */     addChord(paramChordMap, "A13", 15, 0, 1, 2, 3, 1, 5, true);
/*  793 */     addChord(paramChordMap, "A7sus4", 0, 0, 2, 0, 3, 0, 1, true);
/*  794 */     addChord(paramChordMap, "A9sus", 15, 0, 2, 1, 0, 0, 1, true);
/*  795 */     addChord(paramChordMap, "Asus", 15, 15, 2, 2, 3, 0, 1, true);
/*  796 */     addChord(paramChordMap, "Asus2", 0, 0, 2, 2, 0, 0, 1, true);
/*  797 */     addChord(paramChordMap, "Asus4", 15, 15, 2, 2, 3, 0, 1, true);
/*  798 */     addChord(paramChordMap, "Adim", 15, 15, 1, 2, 1, 2, 1, true);
/*  799 */     addChord(paramChordMap, "Amaj", 15, 0, 2, 2, 2, 0, 1, true);
/*  800 */     addChord(paramChordMap, "Amaj7", 15, 0, 2, 1, 2, 0, 1, true);
/*  801 */     addChord(paramChordMap, "Adim", 15, 15, 1, 2, 1, 2, 1, true);
/*  802 */     addChord(paramChordMap, "Amin", 15, 0, 2, 2, 1, 0, 1, true);
/*  803 */     addChord(paramChordMap, "A/D", 15, 15, 0, 0, 2, 2, 1, true);
/*  804 */     addChord(paramChordMap, "A/F#", 2, 0, 2, 2, 2, 0, 1, true);
/*  805 */     addChord(paramChordMap, "A/G#", 4, 0, 2, 2, 2, 0, 1, true);
/*      */     
/*  807 */     addChord(paramChordMap, "Am", 15, 0, 2, 2, 1, 0, 1, false);
/*  808 */     addChord(paramChordMap, "Am#7", 15, 15, 2, 1, 1, 0, 1, true);
/*  809 */     addChord(paramChordMap, "Am(7#)", 15, 0, 2, 2, 1, 4, 1, true);
/*  810 */     addChord(paramChordMap, "Am6", 15, 0, 2, 2, 1, 2, 1, true);
/*  811 */     addChord(paramChordMap, "Am7", 15, 0, 2, 2, 1, 3, 1, false);
/*  812 */     addChord(paramChordMap, "Am7sus4", 0, 0, 0, 0, 3, 0, 1, true);
/*  813 */     addChord(paramChordMap, "Am9", 15, 0, 1, 1, 1, 3, 5, true);
/*  814 */     addChord(paramChordMap, "Am/G", 3, 0, 2, 2, 1, 0, 1, true);
/*  815 */     addChord(paramChordMap, "Amadd9", 0, 2, 2, 2, 1, 0, 1, true);
/*  816 */     addChord(paramChordMap, "Am(add9)", 0, 2, 2, 2, 1, 0, 1, true);
/*      */     
/*  818 */     addChord(paramChordMap, "A#", 15, 1, 3, 3, 3, 1, 1, true);
/*  819 */     addChord(paramChordMap, "A#+", 15, 15, 0, 3, 3, 2, 1, true);
/*  820 */     addChord(paramChordMap, "A#4", 15, 15, 3, 3, 4, 1, 1, true);
/*  821 */     addChord(paramChordMap, "A#7", 15, 15, 1, 1, 1, 2, 3, true);
/*  822 */     addChord(paramChordMap, "A#sus", 15, 15, 3, 3, 4, 1, 1, true);
/*  823 */     addChord(paramChordMap, "A#sus4", 15, 15, 3, 3, 4, 1, 1, true);
/*  824 */     addChord(paramChordMap, "A#maj", 15, 1, 3, 3, 3, 1, 1, true);
/*  825 */     addChord(paramChordMap, "A#maj7", 15, 1, 3, 2, 3, 15, 1, true);
/*  826 */     addChord(paramChordMap, "A#dim", 15, 15, 2, 3, 2, 3, 1, true);
/*  827 */     addChord(paramChordMap, "A#min", 15, 1, 3, 3, 2, 1, 1, true);
/*  828 */     addChord(paramChordMap, "A#m", 15, 1, 3, 3, 2, 1, 1, true);
/*  829 */     addChord(paramChordMap, "A#m7", 15, 1, 3, 1, 2, 1, 1, true);
/*      */     
/*  831 */     addChord(paramChordMap, "Bb", 15, 1, 3, 3, 3, 1, 1, false);
/*  832 */     addChord(paramChordMap, "Bb+", 15, 15, 0, 3, 3, 2, 1, true);
/*  833 */     addChord(paramChordMap, "Bb4", 15, 15, 3, 3, 4, 1, 1, true);
/*  834 */     addChord(paramChordMap, "Bb6", 15, 15, 3, 3, 3, 3, 1, true);
/*  835 */     addChord(paramChordMap, "Bb7", 15, 15, 1, 1, 1, 2, 3, true);
/*  836 */     addChord(paramChordMap, "Bb9", 1, 3, 1, 2, 1, 3, 6, true);
/*  837 */     addChord(paramChordMap, "Bb11", 1, 3, 1, 3, 4, 1, 6, true);
/*  838 */     addChord(paramChordMap, "Bbsus", 15, 15, 3, 3, 4, 1, 1, true);
/*  839 */     addChord(paramChordMap, "Bbsus4", 15, 15, 3, 3, 4, 1, 1, true);
/*  840 */     addChord(paramChordMap, "Bbmaj", 15, 1, 3, 3, 3, 1, 1, true);
/*  841 */     addChord(paramChordMap, "Bbmaj7", 15, 1, 3, 2, 3, 15, 1, true);
/*  842 */     addChord(paramChordMap, "Bbdim", 15, 15, 2, 3, 2, 3, 1, true);
/*  843 */     addChord(paramChordMap, "Bbmin", 15, 1, 3, 3, 2, 1, 1, false);
/*  844 */     addChord(paramChordMap, "Bbm", 15, 1, 3, 3, 2, 1, 1, true);
/*  845 */     addChord(paramChordMap, "Bbm7", 15, 1, 3, 1, 2, 1, 1, true);
/*  846 */     addChord(paramChordMap, "Bbm9", 15, 15, 15, 1, 1, 3, 6, true);
/*      */     
/*  848 */     addChord(paramChordMap, "B", 15, 2, 4, 4, 4, 2, 1, false);
/*  849 */     addChord(paramChordMap, "B+", 15, 15, 1, 0, 0, 4, 1, true);
/*  850 */     addChord(paramChordMap, "B4", 15, 15, 3, 3, 4, 1, 2, true);
/*  851 */     addChord(paramChordMap, "B7", 0, 2, 1, 2, 0, 2, 1, false);
/*  852 */     addChord(paramChordMap, "B7+", 15, 2, 1, 2, 0, 3, 1, true);
/*  853 */     addChord(paramChordMap, "B7+5", 15, 2, 1, 2, 0, 3, 1, true);
/*  854 */     addChord(paramChordMap, "B7#9", 15, 2, 1, 2, 3, 15, 1, true);
/*  855 */     addChord(paramChordMap, "B7(#9)", 15, 2, 1, 2, 3, 15, 1, true);
/*  856 */     addChord(paramChordMap, "B9", 1, 3, 1, 2, 1, 3, 7, true);
/*  857 */     addChord(paramChordMap, "B11", 1, 3, 3, 2, 0, 0, 7, true);
/*  858 */     addChord(paramChordMap, "B11/13", 15, 1, 1, 1, 1, 3, 2, true);
/*  859 */     addChord(paramChordMap, "B13", 15, 2, 1, 2, 0, 4, 1, true);
/*  860 */     addChord(paramChordMap, "Bsus", 15, 15, 3, 3, 4, 1, 2, true);
/*  861 */     addChord(paramChordMap, "Bsus4", 15, 15, 3, 3, 4, 1, 2, true);
/*  862 */     addChord(paramChordMap, "Bmaj", 15, 2, 4, 3, 4, 15, 1, true);
/*  863 */     addChord(paramChordMap, "Bmaj7", 15, 2, 4, 3, 4, 15, 1, true);
/*  864 */     addChord(paramChordMap, "Bdim", 15, 15, 0, 1, 0, 1, 1, true);
/*  865 */     addChord(paramChordMap, "Bmin", 15, 2, 4, 4, 3, 2, 1, true);
/*  866 */     addChord(paramChordMap, "B/F#", 0, 2, 2, 2, 0, 0, 2, true);
/*  867 */     addChord(paramChordMap, "BaddE", 15, 2, 4, 4, 0, 0, 1, true);
/*  868 */     addChord(paramChordMap, "B(addE)", 15, 2, 4, 4, 0, 0, 1, true);
/*  869 */     addChord(paramChordMap, "BaddE/F#", 2, 15, 4, 4, 0, 0, 1, true);
/*      */     
/*  871 */     addChord(paramChordMap, "Bm", 15, 2, 4, 4, 3, 2, 1, false);
/*  872 */     addChord(paramChordMap, "Bm6", 15, 15, 4, 4, 3, 4, 1, true);
/*  873 */     addChord(paramChordMap, "Bm7", 15, 1, 3, 1, 2, 1, 2, false);
/*  874 */     addChord(paramChordMap, "Bmmaj7", 15, 1, 4, 4, 3, 15, 1, true);
/*  875 */     addChord(paramChordMap, "Bm(maj7)", 15, 1, 4, 4, 3, 15, 1, true);
/*  876 */     addChord(paramChordMap, "Bmsus9", 15, 15, 4, 4, 2, 2, 1, true);
/*  877 */     addChord(paramChordMap, "Bm(sus9)", 15, 15, 4, 4, 2, 2, 1, true);
/*  878 */     addChord(paramChordMap, "Bm7b5", 1, 2, 4, 2, 3, 1, 1, true);
/*      */     
/*  880 */     addChord(paramChordMap, "C", 15, 3, 2, 0, 1, 0, 1, false);
/*  881 */     addChord(paramChordMap, "C+", 15, 15, 2, 1, 1, 0, 1, true);
/*  882 */     addChord(paramChordMap, "C4", 15, 15, 3, 0, 1, 3, 1, true);
/*  883 */     addChord(paramChordMap, "C6", 15, 0, 2, 2, 1, 3, 1, true);
/*  884 */     addChord(paramChordMap, "C7", 0, 3, 2, 3, 1, 0, 1, false);
/*  885 */     addChord(paramChordMap, "C9", 1, 3, 1, 2, 1, 3, 8, true);
/*  886 */     addChord(paramChordMap, "C9(11)", 15, 3, 3, 3, 3, 15, 1, true);
/*  887 */     addChord(paramChordMap, "C11", 15, 1, 3, 1, 4, 1, 3, true);
/*  888 */     addChord(paramChordMap, "Csus", 15, 15, 3, 0, 1, 3, 1, true);
/*  889 */     addChord(paramChordMap, "Csus2", 15, 3, 0, 0, 1, 15, 1, true);
/*  890 */     addChord(paramChordMap, "Csus4", 15, 15, 3, 0, 1, 3, 1, true);
/*  891 */     addChord(paramChordMap, "Csus9", 15, 15, 4, 1, 2, 4, 7, true);
/*  892 */     addChord(paramChordMap, "Cmaj", 0, 3, 2, 0, 1, 0, 1, true);
/*  893 */     addChord(paramChordMap, "Cmaj7", 15, 3, 2, 0, 0, 0, 1, true);
/*  894 */     addChord(paramChordMap, "Cmin", 15, 1, 3, 3, 2, 1, 3, true);
/*  895 */     addChord(paramChordMap, "Cdim", 15, 15, 1, 2, 1, 2, 1, true);
/*  896 */     addChord(paramChordMap, "C/B", 15, 2, 2, 0, 1, 0, 1, true);
/*  897 */     addChord(paramChordMap, "Cadd2/B", 15, 2, 0, 0, 1, 0, 1, true);
/*  898 */     addChord(paramChordMap, "CaddD", 15, 3, 2, 0, 3, 0, 1, true);
/*  899 */     addChord(paramChordMap, "C(addD)", 15, 3, 2, 0, 3, 0, 1, true);
/*  900 */     addChord(paramChordMap, "Cadd9", 15, 3, 2, 0, 3, 0, 1, true);
/*  901 */     addChord(paramChordMap, "C(add9)", 15, 3, 2, 0, 3, 0, 1, true);
/*      */     
/*  903 */     addChord(paramChordMap, "Cm", 15, 1, 3, 3, 2, 1, 3, false);
/*  904 */     addChord(paramChordMap, "Cm7", 15, 1, 3, 1, 2, 1, 3, false);
/*  905 */     addChord(paramChordMap, "Cm11", 15, 1, 3, 1, 4, 15, 3, true);
/*      */     
/*  907 */     addChord(paramChordMap, "C#", 15, 15, 3, 1, 2, 1, 1, true);
/*  908 */     addChord(paramChordMap, "C#+", 15, 15, 3, 2, 2, 1, 1, true);
/*  909 */     addChord(paramChordMap, "C#4", 15, 15, 3, 3, 4, 1, 4, true);
/*  910 */     addChord(paramChordMap, "C#7", 15, 15, 3, 4, 2, 4, 1, true);
/*  911 */     addChord(paramChordMap, "C#7(b5)", 15, 2, 1, 2, 1, 2, 1, true);
/*  912 */     addChord(paramChordMap, "C#sus", 15, 15, 3, 3, 4, 1, 4, true);
/*  913 */     addChord(paramChordMap, "C#sus4", 15, 15, 3, 3, 4, 1, 4, true);
/*  914 */     addChord(paramChordMap, "C#maj", 15, 4, 3, 1, 1, 1, 1, true);
/*  915 */     addChord(paramChordMap, "C#maj7", 15, 4, 3, 1, 1, 1, 1, true);
/*  916 */     addChord(paramChordMap, "C#dim", 15, 15, 2, 3, 2, 3, 1, true);
/*  917 */     addChord(paramChordMap, "C#min", 15, 15, 2, 1, 2, 0, 1, true);
/*  918 */     addChord(paramChordMap, "C#add9", 15, 1, 3, 3, 1, 1, 4, true);
/*  919 */     addChord(paramChordMap, "C#(add9)", 15, 1, 3, 3, 1, 1, 4, true);
/*  920 */     addChord(paramChordMap, "C#m", 15, 15, 2, 1, 2, 0, 1, true);
/*  921 */     addChord(paramChordMap, "C#m7", 15, 15, 2, 4, 2, 4, 1, true);
/*      */     
/*  923 */     addChord(paramChordMap, "Db", 15, 15, 3, 1, 2, 1, 1, true);
/*  924 */     addChord(paramChordMap, "Db+", 15, 15, 3, 2, 2, 1, 1, true);
/*  925 */     addChord(paramChordMap, "Db7", 15, 15, 3, 4, 2, 4, 1, true);
/*  926 */     addChord(paramChordMap, "Dbsus", 15, 15, 3, 3, 4, 1, 4, true);
/*  927 */     addChord(paramChordMap, "Dbsus4", 15, 15, 3, 3, 4, 1, 4, true);
/*  928 */     addChord(paramChordMap, "Dbmaj", 15, 15, 3, 1, 2, 1, 1, true);
/*  929 */     addChord(paramChordMap, "Dbmaj7", 15, 4, 3, 1, 1, 1, 1, true);
/*  930 */     addChord(paramChordMap, "Dbdim", 15, 15, 2, 3, 2, 3, 1, true);
/*  931 */     addChord(paramChordMap, "Dbmin", 15, 15, 2, 1, 2, 0, 1, true);
/*  932 */     addChord(paramChordMap, "Dbm", 15, 15, 2, 1, 2, 0, 1, true);
/*  933 */     addChord(paramChordMap, "Dbm7", 15, 15, 2, 4, 2, 4, 1, true);
/*      */     
/*  935 */     addChord(paramChordMap, "D", 15, 15, 0, 2, 3, 2, 1, false);
/*  936 */     addChord(paramChordMap, "D+", 15, 15, 0, 3, 3, 2, 1, true);
/*  937 */     addChord(paramChordMap, "D4", 15, 15, 0, 2, 3, 3, 1, true);
/*  938 */     addChord(paramChordMap, "D6", 15, 0, 0, 2, 0, 2, 1, true);
/*  939 */     addChord(paramChordMap, "D7", 15, 15, 0, 2, 1, 2, 1, false);
/*  940 */     addChord(paramChordMap, "D7#9", 15, 2, 1, 2, 3, 3, 4, true);
/*  941 */     addChord(paramChordMap, "D7(#9)", 15, 2, 1, 2, 3, 3, 4, true);
/*  942 */     addChord(paramChordMap, "D9", 1, 3, 1, 2, 1, 3, 10, true);
/*  943 */     addChord(paramChordMap, "D11", 3, 0, 0, 2, 1, 0, 1, true);
/*  944 */     addChord(paramChordMap, "Dsus", 15, 15, 0, 2, 3, 3, 1, true);
/*  945 */     addChord(paramChordMap, "Dsus2", 0, 0, 0, 2, 3, 0, 1, true);
/*  946 */     addChord(paramChordMap, "Dsus4", 15, 15, 0, 2, 3, 3, 1, true);
/*  947 */     addChord(paramChordMap, "D7sus2", 15, 0, 0, 2, 1, 0, 1, true);
/*  948 */     addChord(paramChordMap, "D7sus4", 15, 0, 0, 2, 1, 3, 1, true);
/*  949 */     addChord(paramChordMap, "Dmaj", 15, 15, 0, 2, 3, 2, 1, true);
/*  950 */     addChord(paramChordMap, "Dmaj7", 15, 15, 0, 2, 2, 2, 1, true);
/*  951 */     addChord(paramChordMap, "Ddim", 15, 15, 0, 1, 0, 1, 1, true);
/*  952 */     addChord(paramChordMap, "Dmin", 15, 15, 0, 2, 3, 1, 1, false);
/*  953 */     addChord(paramChordMap, "D/A", 15, 0, 0, 2, 3, 2, 1, true);
/*  954 */     addChord(paramChordMap, "D/B", 15, 2, 0, 2, 3, 2, 1, true);
/*  955 */     addChord(paramChordMap, "D/C", 15, 3, 0, 2, 3, 2, 1, true);
/*  956 */     addChord(paramChordMap, "D/C#", 15, 4, 0, 2, 3, 2, 1, true);
/*  957 */     addChord(paramChordMap, "D/E", 15, 1, 1, 1, 1, 15, 7, true);
/*  958 */     addChord(paramChordMap, "D/G", 3, 15, 0, 2, 3, 2, 1, true);
/*  959 */     addChord(paramChordMap, "D5/E", 0, 1, 1, 1, 15, 15, 7, true);
/*  960 */     addChord(paramChordMap, "Dadd9", 0, 0, 0, 2, 3, 2, 1, true);
/*  961 */     addChord(paramChordMap, "D(add9)", 0, 0, 0, 2, 3, 2, 1, true);
/*  962 */     addChord(paramChordMap, "D9add6", 1, 3, 3, 2, 0, 0, 10, true);
/*  963 */     addChord(paramChordMap, "D9(add6)", 1, 3, 3, 2, 0, 0, 10, true);
/*      */     
/*  965 */     addChord(paramChordMap, "Dm", 15, 15, 0, 2, 3, 1, 1, false);
/*  966 */     addChord(paramChordMap, "Dm6(5b)", 15, 15, 0, 1, 0, 1, 1, true);
/*  967 */     addChord(paramChordMap, "Dm7", 15, 15, 0, 2, 1, 1, 1, false);
/*  968 */     addChord(paramChordMap, "Dm#5", 15, 15, 0, 3, 3, 2, 1, true);
/*  969 */     addChord(paramChordMap, "Dm(#5)", 15, 15, 0, 3, 3, 2, 1, true);
/*  970 */     addChord(paramChordMap, "Dm#7", 15, 15, 0, 2, 2, 1, 1, true);
/*  971 */     addChord(paramChordMap, "Dm(#7)", 15, 15, 0, 2, 2, 1, 1, true);
/*  972 */     addChord(paramChordMap, "Dm/A", 15, 0, 0, 2, 3, 1, 1, true);
/*  973 */     addChord(paramChordMap, "Dm/B", 15, 2, 0, 2, 3, 1, 1, true);
/*  974 */     addChord(paramChordMap, "Dm/C", 15, 3, 0, 2, 3, 1, 1, true);
/*  975 */     addChord(paramChordMap, "Dm/C#", 15, 4, 0, 2, 3, 1, 1, true);
/*  976 */     addChord(paramChordMap, "Dm9", 15, 15, 3, 2, 1, 0, 1, true);
/*      */     
/*  978 */     addChord(paramChordMap, "D#", 15, 15, 3, 1, 2, 1, 3, true);
/*  979 */     addChord(paramChordMap, "D#+", 15, 15, 1, 0, 0, 4, 1, true);
/*  980 */     addChord(paramChordMap, "D#4", 15, 15, 1, 3, 4, 4, 1, true);
/*  981 */     addChord(paramChordMap, "D#7", 15, 15, 1, 3, 2, 3, 1, true);
/*  982 */     addChord(paramChordMap, "D#sus", 15, 15, 1, 3, 4, 4, 1, true);
/*  983 */     addChord(paramChordMap, "D#sus4", 15, 15, 1, 3, 4, 4, 1, true);
/*  984 */     addChord(paramChordMap, "D#maj", 15, 15, 3, 1, 2, 1, 3, true);
/*  985 */     addChord(paramChordMap, "D#maj7", 15, 15, 1, 3, 3, 3, 1, true);
/*  986 */     addChord(paramChordMap, "D#dim", 15, 15, 1, 2, 1, 2, 1, true);
/*  987 */     addChord(paramChordMap, "D#min", 15, 15, 4, 3, 4, 2, 1, true);
/*  988 */     addChord(paramChordMap, "D#m", 15, 15, 4, 3, 4, 2, 1, true);
/*  989 */     addChord(paramChordMap, "D#m7", 15, 15, 1, 3, 2, 2, 1, true);
/*      */     
/*  991 */     addChord(paramChordMap, "Eb", 15, 15, 3, 1, 2, 1, 3, true);
/*  992 */     addChord(paramChordMap, "Eb+", 15, 15, 1, 0, 0, 4, 1, true);
/*  993 */     addChord(paramChordMap, "Eb4", 15, 15, 1, 3, 4, 4, 1, true);
/*  994 */     addChord(paramChordMap, "Eb7", 15, 15, 1, 3, 2, 3, 1, true);
/*  995 */     addChord(paramChordMap, "Ebsus", 15, 15, 1, 3, 4, 4, 1, true);
/*  996 */     addChord(paramChordMap, "Ebsus4", 15, 15, 1, 3, 4, 4, 1, true);
/*  997 */     addChord(paramChordMap, "Ebmaj", 15, 15, 1, 3, 3, 3, 1, true);
/*  998 */     addChord(paramChordMap, "Ebmaj7", 15, 15, 1, 3, 3, 3, 1, true);
/*  999 */     addChord(paramChordMap, "Ebdim", 15, 15, 1, 2, 1, 2, 1, true);
/* 1000 */     addChord(paramChordMap, "Ebadd9", 15, 1, 1, 3, 4, 1, 1, true);
/* 1001 */     addChord(paramChordMap, "Eb(add9)", 15, 1, 1, 3, 4, 1, 1, true);
/* 1002 */     addChord(paramChordMap, "Ebmin", 15, 15, 4, 3, 4, 2, 1, true);
/* 1003 */     addChord(paramChordMap, "Ebm", 15, 15, 4, 3, 4, 2, 1, true);
/* 1004 */     addChord(paramChordMap, "Ebm7", 15, 15, 1, 3, 2, 2, 1, true);
/*      */     
/* 1006 */     addChord(paramChordMap, "E", 0, 2, 2, 1, 0, 0, 1, false);
/* 1007 */     addChord(paramChordMap, "E+", 15, 15, 2, 1, 1, 0, 1, true);
/* 1008 */     addChord(paramChordMap, "E5", 0, 1, 3, 3, 15, 15, 7, true);
/* 1009 */     addChord(paramChordMap, "E6", 15, 15, 3, 3, 3, 3, 9, true);
/* 1010 */     addChord(paramChordMap, "E7", 0, 2, 2, 1, 3, 0, 1, false);
/* 1011 */     addChord(paramChordMap, "E7#9", 0, 2, 2, 1, 3, 3, 1, true);
/* 1012 */     addChord(paramChordMap, "E7(#9)", 0, 2, 2, 1, 3, 3, 1, true);
/* 1013 */     addChord(paramChordMap, "E7(5b)", 15, 1, 0, 1, 3, 0, 1, true);
/* 1014 */     addChord(paramChordMap, "E7b9", 0, 2, 0, 1, 3, 2, 1, true);
/* 1015 */     addChord(paramChordMap, "E7(b9)", 0, 2, 0, 1, 3, 2, 1, true);
/* 1016 */     addChord(paramChordMap, "E7(11)", 0, 2, 2, 2, 3, 0, 1, true);
/* 1017 */     addChord(paramChordMap, "E9", 1, 3, 1, 2, 1, 3, 1, true);
/* 1018 */     addChord(paramChordMap, "E11", 1, 1, 1, 1, 2, 2, 1, true);
/* 1019 */     addChord(paramChordMap, "Esus", 0, 2, 2, 2, 0, 0, 1, true);
/* 1020 */     addChord(paramChordMap, "Esus4", 0, 2, 2, 2, 0, 0, 0, true);
/* 1021 */     addChord(paramChordMap, "Emaj", 0, 2, 2, 1, 0, 0, 1, true);
/* 1022 */     addChord(paramChordMap, "Emaj7", 0, 2, 1, 1, 0, 15, 1, true);
/* 1023 */     addChord(paramChordMap, "Edim", 15, 15, 2, 3, 2, 3, 1, true);
/* 1024 */     addChord(paramChordMap, "Emin", 0, 2, 2, 0, 0, 0, 1, true);
/*      */     
/* 1026 */     addChord(paramChordMap, "Em", 0, 2, 2, 0, 0, 0, 1, false);
/* 1027 */     addChord(paramChordMap, "Em6", 0, 2, 2, 0, 2, 0, 1, true);
/* 1028 */     addChord(paramChordMap, "Em7", 0, 2, 2, 0, 3, 0, 1, false);
/* 1029 */     addChord(paramChordMap, "Em/B", 15, 2, 2, 0, 0, 0, 1, true);
/* 1030 */     addChord(paramChordMap, "Em/D", 15, 15, 0, 0, 0, 0, 1, true);
/* 1031 */     addChord(paramChordMap, "Em7/D", 15, 15, 0, 0, 0, 0, 1, true);
/* 1032 */     addChord(paramChordMap, "Emsus4", 0, 0, 2, 0, 0, 0, 1, true);
/* 1033 */     addChord(paramChordMap, "Em(sus4)", 0, 0, 2, 0, 0, 0, 1, true);
/* 1034 */     addChord(paramChordMap, "Emadd9", 0, 2, 4, 0, 0, 0, 1, true);
/* 1035 */     addChord(paramChordMap, "Em(add9)", 0, 2, 4, 0, 0, 0, 1, true);
/*      */     
/* 1037 */     addChord(paramChordMap, "F", 1, 3, 3, 2, 1, 1, 1, false);
/* 1038 */     addChord(paramChordMap, "F+", 15, 15, 3, 2, 2, 1, 1, true);
/* 1039 */     addChord(paramChordMap, "F+7+11", 1, 3, 3, 2, 0, 0, 1, true);
/* 1040 */     addChord(paramChordMap, "F4", 15, 15, 3, 3, 1, 1, 1, true);
/* 1041 */     addChord(paramChordMap, "F6", 15, 3, 3, 2, 3, 15, 1, true);
/* 1042 */     addChord(paramChordMap, "F7", 1, 3, 1, 2, 1, 1, 1, false);
/* 1043 */     addChord(paramChordMap, "F9", 2, 4, 2, 3, 2, 4, 1, true);
/* 1044 */     addChord(paramChordMap, "F11", 1, 3, 1, 3, 1, 1, 1, true);
/* 1045 */     addChord(paramChordMap, "Fsus", 15, 15, 3, 3, 1, 1, 1, true);
/* 1046 */     addChord(paramChordMap, "Fsus4", 15, 15, 3, 3, 1, 1, 1, true);
/* 1047 */     addChord(paramChordMap, "Fmaj", 1, 3, 3, 2, 1, 1, 1, true);
/* 1048 */     addChord(paramChordMap, "Fmaj7", 15, 3, 3, 2, 1, 0, 1, true);
/* 1049 */     addChord(paramChordMap, "Fdim", 15, 15, 0, 1, 0, 1, 1, true);
/* 1050 */     addChord(paramChordMap, "Fmin", 1, 3, 3, 1, 1, 1, 1, false);
/* 1051 */     addChord(paramChordMap, "F/A", 15, 0, 3, 2, 1, 1, 1, true);
/* 1052 */     addChord(paramChordMap, "F/C", 15, 15, 3, 2, 1, 1, 1, true);
/* 1053 */     addChord(paramChordMap, "F/D", 15, 15, 0, 2, 1, 1, 1, true);
/* 1054 */     addChord(paramChordMap, "F/G", 3, 3, 3, 2, 1, 1, 1, true);
/* 1055 */     addChord(paramChordMap, "F7/A", 15, 0, 3, 0, 1, 1, 1, true);
/* 1056 */     addChord(paramChordMap, "Fmaj7/A", 15, 0, 3, 2, 1, 0, 1, true);
/* 1057 */     addChord(paramChordMap, "Fmaj7/C", 15, 3, 3, 2, 1, 0, 1, true);
/* 1058 */     addChord(paramChordMap, "Fmaj7(+5)", 15, 15, 3, 2, 2, 0, 1, true);
/* 1059 */     addChord(paramChordMap, "Fadd9", 3, 0, 3, 2, 1, 1, 1, true);
/* 1060 */     addChord(paramChordMap, "F(add9)", 3, 0, 3, 2, 1, 1, 1, true);
/* 1061 */     addChord(paramChordMap, "FaddG", 1, 15, 3, 2, 1, 3, 1, true);
/* 1062 */     addChord(paramChordMap, "FaddG", 1, 15, 3, 2, 1, 3, 1, true);
/*      */     
/* 1064 */     addChord(paramChordMap, "Fm", 1, 3, 3, 1, 1, 1, 1, false);
/* 1065 */     addChord(paramChordMap, "Fm6", 15, 15, 0, 1, 1, 1, 1, true);
/* 1066 */     addChord(paramChordMap, "Fm7", 1, 3, 1, 1, 1, 1, 1, false);
/* 1067 */     addChord(paramChordMap, "Fmmaj7", 15, 3, 3, 1, 1, 0, 1, true);
/*      */     
/* 1069 */     addChord(paramChordMap, "F#", 2, 4, 4, 3, 2, 2, 1, false);
/* 1070 */     addChord(paramChordMap, "F#+", 15, 15, 4, 3, 3, 2, 1, true);
/* 1071 */     addChord(paramChordMap, "F#7", 15, 15, 4, 3, 2, 0, 1, false);
/* 1072 */     addChord(paramChordMap, "F#9", 15, 1, 2, 1, 2, 2, 1, true);
/* 1073 */     addChord(paramChordMap, "F#11", 2, 4, 2, 4, 2, 2, 1, true);
/* 1074 */     addChord(paramChordMap, "F#sus", 15, 15, 4, 4, 2, 2, 1, true);
/* 1075 */     addChord(paramChordMap, "F#sus4", 15, 15, 4, 4, 2, 2, 1, true);
/* 1076 */     addChord(paramChordMap, "F#maj", 2, 4, 4, 3, 2, 2, 0, true);
/* 1077 */     addChord(paramChordMap, "F#maj7", 15, 15, 4, 3, 2, 1, 1, true);
/* 1078 */     addChord(paramChordMap, "F#dim", 15, 15, 1, 2, 1, 2, 1, true);
/* 1079 */     addChord(paramChordMap, "F#min", 2, 4, 4, 2, 2, 2, 1, false);
/* 1080 */     addChord(paramChordMap, "F#/E", 0, 4, 4, 3, 2, 2, 1, true);
/* 1081 */     addChord(paramChordMap, "F#4", 15, 15, 4, 4, 2, 2, 1, true);
/* 1082 */     addChord(paramChordMap, "F#m", 2, 4, 4, 2, 2, 2, 1, false);
/* 1083 */     addChord(paramChordMap, "F#m6", 15, 15, 1, 2, 2, 2, 1, true);
/* 1084 */     addChord(paramChordMap, "F#m7", 15, 15, 2, 2, 2, 2, 1, false);
/* 1085 */     addChord(paramChordMap, "F#m7-5", 1, 0, 2, 3, 3, 3, 2, true);
/* 1086 */     addChord(paramChordMap, "F#m/C#m", 15, 15, 4, 2, 2, 2, 1, true);
/*      */     
/* 1088 */     addChord(paramChordMap, "Gb", 2, 4, 4, 3, 2, 2, 1, false);
/* 1089 */     addChord(paramChordMap, "Gb+", 15, 15, 4, 3, 3, 2, 1, true);
/* 1090 */     addChord(paramChordMap, "Gb7", 15, 15, 4, 3, 2, 0, 1, false);
/* 1091 */     addChord(paramChordMap, "Gb9", 15, 1, 2, 1, 2, 2, 1, true);
/* 1092 */     addChord(paramChordMap, "Gbsus", 15, 15, 4, 4, 2, 2, 1, true);
/* 1093 */     addChord(paramChordMap, "Gbsus4", 15, 15, 4, 4, 2, 2, 1, true);
/* 1094 */     addChord(paramChordMap, "Gbmaj", 2, 4, 4, 3, 2, 2, 1, true);
/* 1095 */     addChord(paramChordMap, "Gbmaj7", 15, 15, 4, 3, 2, 1, 1, true);
/* 1096 */     addChord(paramChordMap, "Gbdim", 15, 15, 1, 2, 1, 2, 1, true);
/* 1097 */     addChord(paramChordMap, "Gbmin", 2, 4, 4, 2, 2, 2, 1, true);
/* 1098 */     addChord(paramChordMap, "Gbm", 2, 4, 4, 2, 2, 2, 1, false);
/* 1099 */     addChord(paramChordMap, "Gbm7", 15, 15, 2, 2, 2, 2, 1, true);
/*      */     
/* 1101 */     addChord(paramChordMap, "G", 3, 2, 0, 0, 0, 3, 1, false);
/* 1102 */     addChord(paramChordMap, "G+", 15, 15, 1, 0, 0, 4, 1, true);
/* 1103 */     addChord(paramChordMap, "G4", 15, 15, 0, 0, 1, 3, 1, true);
/* 1104 */     addChord(paramChordMap, "G6", 3, 15, 0, 0, 0, 0, 1, true);
/* 1105 */     addChord(paramChordMap, "G7", 3, 2, 0, 0, 0, 1, 1, false);
/* 1106 */     addChord(paramChordMap, "G7+", 15, 15, 4, 3, 3, 2, 1, true);
/* 1107 */     addChord(paramChordMap, "G7b9", 15, 15, 0, 1, 0, 1, 1, true);
/* 1108 */     addChord(paramChordMap, "G7(b9)", 15, 15, 0, 1, 0, 1, 1, true);
/* 1109 */     addChord(paramChordMap, "G7#9", 1, 3, 15, 2, 4, 4, 3, true);
/* 1110 */     addChord(paramChordMap, "G7(#9)", 1, 3, 15, 2, 4, 4, 3, true);
/* 1111 */     addChord(paramChordMap, "G9", 3, 15, 0, 2, 0, 1, 1, true);
/* 1112 */     addChord(paramChordMap, "G9(11)", 1, 3, 1, 3, 1, 3, 3, true);
/* 1113 */     addChord(paramChordMap, "G11", 3, 15, 0, 2, 1, 1, 1, true);
/* 1114 */     addChord(paramChordMap, "Gsus", 15, 15, 0, 0, 1, 3, 1, true);
/* 1115 */     addChord(paramChordMap, "Gsus4", 15, 15, 0, 0, 1, 1, 1, true);
/* 1116 */     addChord(paramChordMap, "G6sus4", 0, 2, 0, 0, 1, 0, 1, true);
/* 1117 */     addChord(paramChordMap, "G6(sus4)", 0, 2, 0, 0, 1, 0, 1, true);
/* 1118 */     addChord(paramChordMap, "G7sus4", 3, 3, 0, 0, 1, 1, 1, true);
/* 1119 */     addChord(paramChordMap, "G7(sus4)", 3, 3, 0, 0, 1, 1, 1, true);
/* 1120 */     addChord(paramChordMap, "Gmaj", 3, 2, 0, 0, 0, 3, 1, true);
/* 1121 */     addChord(paramChordMap, "Gmaj7", 15, 15, 4, 3, 2, 1, 2, true);
/* 1122 */     addChord(paramChordMap, "Gmaj7sus4", 15, 15, 0, 0, 1, 2, 1, true);
/* 1123 */     addChord(paramChordMap, "Gmaj9", 1, 1, 4, 1, 2, 1, 2, true);
/* 1124 */     addChord(paramChordMap, "Gmin", 1, 3, 3, 1, 1, 1, 3, false);
/* 1125 */     addChord(paramChordMap, "Gdim", 15, 15, 2, 3, 2, 3, 1, true);
/* 1126 */     addChord(paramChordMap, "Gadd9", 1, 3, 15, 2, 1, 3, 3, true);
/* 1127 */     addChord(paramChordMap, "G(add9)", 1, 3, 15, 2, 1, 3, 3, true);
/* 1128 */     addChord(paramChordMap, "G/A", 15, 0, 0, 0, 0, 3, 1, true);
/* 1129 */     addChord(paramChordMap, "G/B", 15, 2, 0, 0, 0, 3, 1, true);
/* 1130 */     addChord(paramChordMap, "G/D", 15, 2, 2, 1, 0, 0, 4, true);
/* 1131 */     addChord(paramChordMap, "G/F#", 2, 2, 0, 0, 0, 3, 1, true);
/*      */     
/* 1133 */     addChord(paramChordMap, "Gm", 1, 3, 3, 1, 1, 1, 3, false);
/* 1134 */     addChord(paramChordMap, "Gm6", 15, 15, 2, 3, 3, 3, 1, true);
/* 1135 */     addChord(paramChordMap, "Gm7", 1, 3, 1, 1, 1, 1, 3, false);
/* 1136 */     addChord(paramChordMap, "Gm/Bb", 3, 2, 2, 1, 15, 15, 4, true);
/*      */     
/* 1138 */     addChord(paramChordMap, "G#", 1, 3, 3, 2, 1, 1, 4, false);
/* 1139 */     addChord(paramChordMap, "G#+", 15, 15, 2, 1, 1, 0, 1, true);
/* 1140 */     addChord(paramChordMap, "G#4", 1, 3, 3, 1, 1, 1, 4, true);
/* 1141 */     addChord(paramChordMap, "G#7", 15, 15, 1, 1, 1, 2, 1, false);
/* 1142 */     addChord(paramChordMap, "G#sus", 15, 15, 1, 1, 2, 4, 1, true);
/* 1143 */     addChord(paramChordMap, "G#sus4", 15, 15, 1, 1, 2, 4, 1, true);
/* 1144 */     addChord(paramChordMap, "G#maj", 1, 3, 3, 2, 1, 1, 4, true);
/* 1145 */     addChord(paramChordMap, "G#maj7", 15, 15, 1, 1, 1, 3, 1, true);
/* 1146 */     addChord(paramChordMap, "G#dim", 15, 15, 0, 1, 0, 1, 1, true);
/* 1147 */     addChord(paramChordMap, "G#min", 1, 3, 3, 1, 1, 1, 4, true);
/* 1148 */     addChord(paramChordMap, "G#m", 1, 3, 3, 1, 1, 1, 4, false);
/* 1149 */     addChord(paramChordMap, "G#m6", 15, 15, 1, 1, 0, 1, 1, true);
/* 1150 */     addChord(paramChordMap, "G#m7", 15, 15, 1, 1, 1, 1, 4, false);
/* 1151 */     addChord(paramChordMap, "G#m9maj7", 15, 15, 1, 3, 0, 3, 1, true);
/* 1152 */     addChord(paramChordMap, "G#m9(maj7)", 15, 15, 1, 3, 0, 3, 1, true);
/*      */     
/* 1154 */     addUkuleleChord(paramChordMap, "A", 2, 1, 0, 0, 1, false);
/* 1155 */     addUkuleleChord(paramChordMap, "A#", 3, 2, 1, 1, 1, false);
/* 1156 */     addUkuleleChord(paramChordMap, "A#+", 2, 1, 1, 4, 1, false);
/* 1157 */     addUkuleleChord(paramChordMap, "A#6", 0, 2, 1, 1, 1, false);
/* 1158 */     addUkuleleChord(paramChordMap, "A#7", 1, 2, 1, 1, 1, false);
/* 1159 */     addUkuleleChord(paramChordMap, "A#9", 1, 2, 1, 3, 1, false);
/* 1160 */     addUkuleleChord(paramChordMap, "A#aug", 2, 1, 1, 4, 1, false);
/* 1161 */     addUkuleleChord(paramChordMap, "A#dim", 0, 1, 0, 1, 1, false);
/* 1162 */     addUkuleleChord(paramChordMap, "A#m", 3, 1, 1, 1, 1, false);
/* 1163 */     addUkuleleChord(paramChordMap, "A#m6", 1, 3, 1, 2, 2, false);
/* 1164 */     addUkuleleChord(paramChordMap, "A#m7", 1, 1, 1, 1, 1, false);
/* 1165 */     addUkuleleChord(paramChordMap, "A#maj", 3, 2, 1, 1, 1, false);
/* 1166 */     addUkuleleChord(paramChordMap, "A#maj7", 2, 2, 1, 1, 1, false);
/* 1167 */     addUkuleleChord(paramChordMap, "A#min", 3, 1, 1, 1, 1, false);
/* 1168 */     addUkuleleChord(paramChordMap, "A#min6", 1, 3, 1, 2, 2, false);
/* 1169 */     addUkuleleChord(paramChordMap, "A#min7", 1, 1, 1, 1, 1, false);
/* 1170 */     addUkuleleChord(paramChordMap, "A+", 2, 1, 1, 4, 1, false);
/* 1171 */     addUkuleleChord(paramChordMap, "A6", 2, 4, 2, 4, 1, false);
/* 1172 */     addUkuleleChord(paramChordMap, "A7", 0, 1, 0, 0, 1, false);
/* 1173 */     addUkuleleChord(paramChordMap, "A9", 0, 1, 0, 2, 1, false);
/* 1174 */     addUkuleleChord(paramChordMap, "Aaug", 2, 1, 1, 4, 1, false);
/* 1175 */     addUkuleleChord(paramChordMap, "Ab", 3, 1, 2, 1, 2, false);
/* 1176 */     addUkuleleChord(paramChordMap, "Ab+", 1, 0, 0, 3, 1, false);
/* 1177 */     addUkuleleChord(paramChordMap, "Ab6", 1, 3, 1, 3, 1, false);
/* 1178 */     addUkuleleChord(paramChordMap, "Ab7", 1, 3, 2, 3, 1, false);
/* 1179 */     addUkuleleChord(paramChordMap, "Ab9", 3, 3, 2, 3, 1, false);
/* 1180 */     addUkuleleChord(paramChordMap, "Abaug", 1, 0, 0, 3, 1, false);
/* 1181 */     addUkuleleChord(paramChordMap, "Abdim", 1, 2, 1, 2, 1, false);
/* 1182 */     addUkuleleChord(paramChordMap, "Abm", 1, 3, 4, 2, 1, false);
/* 1183 */     addUkuleleChord(paramChordMap, "Abm6", 1, 3, 1, 2, 1, false);
/* 1184 */     addUkuleleChord(paramChordMap, "Abm7", 1, 3, 2, 2, 1, false);
/* 1185 */     addUkuleleChord(paramChordMap, "Abmaj", 3, 1, 2, 1, 2, false);
/* 1186 */     addUkuleleChord(paramChordMap, "Abmaj7", 1, 3, 3, 3, 1, false);
/* 1187 */     addUkuleleChord(paramChordMap, "Abmin", 1, 3, 4, 2, 1, false);
/* 1188 */     addUkuleleChord(paramChordMap, "Abmin6", 1, 3, 1, 2, 1, false);
/* 1189 */     addUkuleleChord(paramChordMap, "Abmin7", 1, 3, 2, 2, 1, false);
/* 1190 */     addUkuleleChord(paramChordMap, "Adim", 2, 3, 2, 3, 1, false);
/* 1191 */     addUkuleleChord(paramChordMap, "Am", 2, 0, 0, 0, 1, false);
/* 1192 */     addUkuleleChord(paramChordMap, "Am6", 2, 4, 2, 3, 1, false);
/* 1193 */     addUkuleleChord(paramChordMap, "Am7", 0, 0, 0, 0, 1, false);
/* 1194 */     addUkuleleChord(paramChordMap, "Amaj", 2, 1, 0, 0, 1, false);
/* 1195 */     addUkuleleChord(paramChordMap, "Amaj7", 1, 1, 0, 0, 1, false);
/* 1196 */     addUkuleleChord(paramChordMap, "Amin", 2, 0, 0, 0, 1, false);
/* 1197 */     addUkuleleChord(paramChordMap, "Amin6", 2, 4, 2, 3, 1, false);
/* 1198 */     addUkuleleChord(paramChordMap, "Amin7", 0, 0, 0, 0, 1, false);
/* 1199 */     addUkuleleChord(paramChordMap, "B", 4, 3, 2, 2, 1, false);
/* 1200 */     addUkuleleChord(paramChordMap, "B+", 0, 3, 3, 2, 1, false);
/* 1201 */     addUkuleleChord(paramChordMap, "B6", 1, 3, 2, 2, 1, false);
/* 1202 */     addUkuleleChord(paramChordMap, "B7", 2, 3, 2, 2, 1, false);
/* 1203 */     addUkuleleChord(paramChordMap, "B9", 2, 3, 2, 4, 1, false);
/* 1204 */     addUkuleleChord(paramChordMap, "Baug", 0, 3, 3, 2, 1, false);
/* 1205 */     addUkuleleChord(paramChordMap, "Bb", 3, 2, 1, 1, 1, false);
/* 1206 */     addUkuleleChord(paramChordMap, "Bb+", 2, 1, 1, 4, 1, false);
/* 1207 */     addUkuleleChord(paramChordMap, "Bb6", 0, 2, 1, 1, 1, false);
/* 1208 */     addUkuleleChord(paramChordMap, "Bb7", 1, 2, 1, 1, 1, false);
/* 1209 */     addUkuleleChord(paramChordMap, "Bb9", 1, 2, 1, 3, 1, false);
/* 1210 */     addUkuleleChord(paramChordMap, "Bbaug", 2, 1, 1, 4, 1, false);
/* 1211 */     addUkuleleChord(paramChordMap, "Bbdim", 0, 1, 0, 1, 1, false);
/* 1212 */     addUkuleleChord(paramChordMap, "Bbm", 3, 1, 1, 1, 1, false);
/* 1213 */     addUkuleleChord(paramChordMap, "Bbm6", 1, 3, 1, 2, 2, false);
/* 1214 */     addUkuleleChord(paramChordMap, "Bbm7", 1, 1, 1, 1, 1, false);
/* 1215 */     addUkuleleChord(paramChordMap, "Bbmaj", 3, 2, 1, 1, 1, false);
/* 1216 */     addUkuleleChord(paramChordMap, "Bbmaj7", 2, 2, 1, 1, 1, false);
/* 1217 */     addUkuleleChord(paramChordMap, "Bbmin", 3, 1, 1, 1, 1, false);
/* 1218 */     addUkuleleChord(paramChordMap, "Bbmin6", 1, 3, 1, 2, 2, false);
/* 1219 */     addUkuleleChord(paramChordMap, "Bbmin7", 1, 1, 1, 1, 1, false);
/* 1220 */     addUkuleleChord(paramChordMap, "Bdim", 1, 2, 1, 2, 1, false);
/* 1221 */     addUkuleleChord(paramChordMap, "Bm", 4, 2, 2, 2, 1, false);
/* 1222 */     addUkuleleChord(paramChordMap, "Bm6", 1, 2, 2, 2, 1, false);
/* 1223 */     addUkuleleChord(paramChordMap, "Bm7", 2, 2, 2, 2, 1, false);
/* 1224 */     addUkuleleChord(paramChordMap, "Bmaj", 4, 3, 2, 2, 1, false);
/* 1225 */     addUkuleleChord(paramChordMap, "Bmaj7", 3, 3, 2, 2, 1, false);
/* 1226 */     addUkuleleChord(paramChordMap, "Bmin", 4, 2, 2, 2, 1, false);
/* 1227 */     addUkuleleChord(paramChordMap, "Bmin6", 1, 2, 2, 2, 1, false);
/* 1228 */     addUkuleleChord(paramChordMap, "Bmin7", 2, 2, 2, 2, 1, false);
/* 1229 */     addUkuleleChord(paramChordMap, "C", 0, 0, 0, 3, 1, false);
/* 1230 */     addUkuleleChord(paramChordMap, "C#", 1, 1, 1, 4, 1, false);
/* 1231 */     addUkuleleChord(paramChordMap, "C#+", 2, 1, 1, 4, 1, false);
/* 1232 */     addUkuleleChord(paramChordMap, "C#6", 1, 1, 1, 1, 1, false);
/* 1233 */     addUkuleleChord(paramChordMap, "C#7", 1, 1, 1, 2, 1, false);
/* 1234 */     addUkuleleChord(paramChordMap, "C#9", 1, 3, 1, 2, 1, false);
/* 1235 */     addUkuleleChord(paramChordMap, "C#aug", 2, 1, 1, 4, 1, false);
/* 1236 */     addUkuleleChord(paramChordMap, "C#dim", 0, 1, 0, 1, 1, false);
/* 1237 */     addUkuleleChord(paramChordMap, "C#m", 3, 1, 1, 1, 3, false);
/* 1238 */     addUkuleleChord(paramChordMap, "C#m6", 3, 4, 4, 4, 1, false);
/* 1239 */     addUkuleleChord(paramChordMap, "C#m7", 1, 1, 1, 1, 3, false);
/* 1240 */     addUkuleleChord(paramChordMap, "C#maj", 1, 1, 1, 4, 1, false);
/* 1241 */     addUkuleleChord(paramChordMap, "C#maj7", 1, 1, 1, 3, 1, false);
/* 1242 */     addUkuleleChord(paramChordMap, "C#min", 3, 1, 1, 1, 3, false);
/* 1243 */     addUkuleleChord(paramChordMap, "C#min6", 3, 4, 4, 4, 1, false);
/* 1244 */     addUkuleleChord(paramChordMap, "C#min7", 1, 1, 1, 1, 3, false);
/* 1245 */     addUkuleleChord(paramChordMap, "C+", 1, 0, 0, 3, 1, false);
/* 1246 */     addUkuleleChord(paramChordMap, "C6", 0, 0, 0, 0, 1, false);
/* 1247 */     addUkuleleChord(paramChordMap, "C7", 0, 0, 0, 1, 1, false);
/* 1248 */     addUkuleleChord(paramChordMap, "C9", 0, 2, 0, 1, 1, false);
/* 1249 */     addUkuleleChord(paramChordMap, "Caug", 1, 0, 0, 3, 1, false);
/* 1250 */     addUkuleleChord(paramChordMap, "Cdim", 2, 3, 2, 3, 1, false);
/* 1251 */     addUkuleleChord(paramChordMap, "Cm", 3, 1, 1, 1, 2, false);
/* 1252 */     addUkuleleChord(paramChordMap, "Cm6", 2, 3, 3, 3, 1, false);
/* 1253 */     addUkuleleChord(paramChordMap, "Cm7", 3, 3, 3, 3, 1, false);
/* 1254 */     addUkuleleChord(paramChordMap, "Cmaj", 0, 0, 0, 3, 1, false);
/* 1255 */     addUkuleleChord(paramChordMap, "Cmaj7", 0, 0, 0, 2, 1, false);
/* 1256 */     addUkuleleChord(paramChordMap, "Cmin", 3, 1, 1, 1, 2, false);
/* 1257 */     addUkuleleChord(paramChordMap, "Cmin6", 2, 3, 3, 3, 1, false);
/* 1258 */     addUkuleleChord(paramChordMap, "Cmin7", 3, 3, 3, 3, 1, false);
/* 1259 */     addUkuleleChord(paramChordMap, "D", 2, 2, 2, 0, 1, false);
/* 1260 */     addUkuleleChord(paramChordMap, "D#", 0, 3, 3, 1, 1, false);
/* 1261 */     addUkuleleChord(paramChordMap, "D#+", 0, 3, 3, 2, 1, false);
/* 1262 */     addUkuleleChord(paramChordMap, "D#6", 3, 3, 3, 3, 1, false);
/* 1263 */     addUkuleleChord(paramChordMap, "D#7", 3, 3, 3, 4, 1, false);
/* 1264 */     addUkuleleChord(paramChordMap, "D#9", 0, 1, 1, 1, 1, false);
/* 1265 */     addUkuleleChord(paramChordMap, "D#aug", 0, 3, 3, 2, 1, false);
/* 1266 */     addUkuleleChord(paramChordMap, "D#dim", 2, 3, 2, 3, 1, false);
/* 1267 */     addUkuleleChord(paramChordMap, "D#m", 3, 3, 2, 1, 1, false);
/* 1268 */     addUkuleleChord(paramChordMap, "D#m6", 3, 3, 2, 3, 1, false);
/* 1269 */     addUkuleleChord(paramChordMap, "D#m7", 3, 3, 2, 4, 1, false);
/* 1270 */     addUkuleleChord(paramChordMap, "D#maj", 0, 3, 3, 1, 1, false);
/* 1271 */     addUkuleleChord(paramChordMap, "D#maj7", 1, 1, 1, 3, 2, false);
/* 1272 */     addUkuleleChord(paramChordMap, "D#min", 3, 3, 2, 1, 1, false);
/* 1273 */     addUkuleleChord(paramChordMap, "D#min6", 3, 3, 2, 3, 1, false);
/* 1274 */     addUkuleleChord(paramChordMap, "D#min7", 3, 3, 2, 4, 1, false);
/* 1275 */     addUkuleleChord(paramChordMap, "D+", 2, 1, 1, 4, 1, false);
/* 1276 */     addUkuleleChord(paramChordMap, "D6", 2, 2, 2, 2, 1, false);
/* 1277 */     addUkuleleChord(paramChordMap, "D7", 2, 2, 2, 3, 1, false);
/* 1278 */     addUkuleleChord(paramChordMap, "D9", 2, 4, 2, 3, 1, false);
/* 1279 */     addUkuleleChord(paramChordMap, "Daug", 2, 1, 1, 4, 1, false);
/* 1280 */     addUkuleleChord(paramChordMap, "Db", 1, 1, 1, 4, 1, false);
/* 1281 */     addUkuleleChord(paramChordMap, "Db+", 2, 1, 1, 4, 1, false);
/* 1282 */     addUkuleleChord(paramChordMap, "Db6", 1, 1, 1, 1, 1, false);
/* 1283 */     addUkuleleChord(paramChordMap, "Db7", 1, 1, 1, 2, 1, false);
/* 1284 */     addUkuleleChord(paramChordMap, "Db9", 1, 3, 1, 2, 1, false);
/* 1285 */     addUkuleleChord(paramChordMap, "Dbaug", 2, 1, 1, 4, 1, false);
/* 1286 */     addUkuleleChord(paramChordMap, "Dbdim", 0, 1, 0, 1, 1, false);
/* 1287 */     addUkuleleChord(paramChordMap, "Dbm", 3, 1, 1, 1, 3, false);
/* 1288 */     addUkuleleChord(paramChordMap, "Dbm6", 3, 4, 4, 4, 1, false);
/* 1289 */     addUkuleleChord(paramChordMap, "Dbm7", 1, 1, 1, 1, 3, false);
/* 1290 */     addUkuleleChord(paramChordMap, "Dbmaj", 1, 1, 1, 4, 1, false);
/* 1291 */     addUkuleleChord(paramChordMap, "Dbmaj7", 1, 1, 1, 3, 1, false);
/* 1292 */     addUkuleleChord(paramChordMap, "Dbmin", 3, 1, 1, 1, 3, false);
/* 1293 */     addUkuleleChord(paramChordMap, "Dbmin6", 3, 4, 4, 4, 1, false);
/* 1294 */     addUkuleleChord(paramChordMap, "Dbmin7", 1, 1, 1, 1, 3, false);
/* 1295 */     addUkuleleChord(paramChordMap, "Ddim", 1, 2, 1, 2, 1, false);
/* 1296 */     addUkuleleChord(paramChordMap, "Dm", 2, 2, 1, 0, 1, false);
/* 1297 */     addUkuleleChord(paramChordMap, "Dm6", 2, 2, 1, 2, 1, false);
/* 1298 */     addUkuleleChord(paramChordMap, "Dm7", 2, 2, 1, 3, 1, false);
/* 1299 */     addUkuleleChord(paramChordMap, "Dmaj", 2, 2, 2, 0, 1, false);
/* 1300 */     addUkuleleChord(paramChordMap, "Dmaj7", 2, 2, 2, 4, 1, false);
/* 1301 */     addUkuleleChord(paramChordMap, "Dmin", 2, 2, 1, 0, 1, false);
/* 1302 */     addUkuleleChord(paramChordMap, "Dmin6", 2, 2, 1, 2, 1, false);
/* 1303 */     addUkuleleChord(paramChordMap, "Dmin7", 2, 2, 1, 3, 1, false);
/* 1304 */     addUkuleleChord(paramChordMap, "E", 4, 4, 4, 2, 1, false);
/* 1305 */     addUkuleleChord(paramChordMap, "E+", 1, 0, 0, 3, 1, false);
/* 1306 */     addUkuleleChord(paramChordMap, "E6", 1, 1, 0, 2, 1, false);
/* 1307 */     addUkuleleChord(paramChordMap, "E7", 1, 2, 0, 2, 1, false);
/* 1308 */     addUkuleleChord(paramChordMap, "E9", 1, 2, 2, 2, 1, false);
/* 1309 */     addUkuleleChord(paramChordMap, "Eaug", 1, 0, 0, 3, 1, false);
/* 1310 */     addUkuleleChord(paramChordMap, "Eb", 0, 3, 3, 1, 1, false);
/* 1311 */     addUkuleleChord(paramChordMap, "Eb+", 0, 3, 3, 2, 1, false);
/* 1312 */     addUkuleleChord(paramChordMap, "Eb6", 3, 3, 3, 3, 1, false);
/* 1313 */     addUkuleleChord(paramChordMap, "Eb7", 3, 3, 3, 4, 1, false);
/* 1314 */     addUkuleleChord(paramChordMap, "Eb9", 0, 1, 1, 1, 1, false);
/* 1315 */     addUkuleleChord(paramChordMap, "Ebaug", 0, 3, 3, 2, 1, false);
/* 1316 */     addUkuleleChord(paramChordMap, "Ebdim", 2, 3, 2, 3, 1, false);
/* 1317 */     addUkuleleChord(paramChordMap, "Ebm", 3, 3, 2, 1, 1, false);
/* 1318 */     addUkuleleChord(paramChordMap, "Ebm6", 3, 3, 2, 3, 1, false);
/* 1319 */     addUkuleleChord(paramChordMap, "Ebm7", 3, 3, 2, 4, 1, false);
/* 1320 */     addUkuleleChord(paramChordMap, "Ebmaj", 0, 3, 3, 1, 1, false);
/* 1321 */     addUkuleleChord(paramChordMap, "Ebmaj7", 1, 1, 1, 3, 2, false);
/* 1322 */     addUkuleleChord(paramChordMap, "Ebmin", 3, 3, 2, 1, 1, false);
/* 1323 */     addUkuleleChord(paramChordMap, "Ebmin6", 3, 3, 2, 3, 1, false);
/* 1324 */     addUkuleleChord(paramChordMap, "Ebmin7", 3, 3, 2, 4, 1, false);
/* 1325 */     addUkuleleChord(paramChordMap, "Edim", 0, 1, 0, 1, 1, false);
/* 1326 */     addUkuleleChord(paramChordMap, "Em", 4, 4, 3, 2, 1, false);
/* 1327 */     addUkuleleChord(paramChordMap, "Em6", 4, 4, 3, 4, 1, false);
/* 1328 */     addUkuleleChord(paramChordMap, "Em7", 0, 2, 0, 2, 1, false);
/* 1329 */     addUkuleleChord(paramChordMap, "Emaj", 4, 4, 4, 2, 1, false);
/* 1330 */     addUkuleleChord(paramChordMap, "Emaj7", 1, 3, 0, 2, 1, false);
/* 1331 */     addUkuleleChord(paramChordMap, "Emin", 4, 4, 3, 2, 1, false);
/* 1332 */     addUkuleleChord(paramChordMap, "Emin6", 4, 4, 3, 4, 1, false);
/* 1333 */     addUkuleleChord(paramChordMap, "Emin7", 0, 2, 0, 2, 1, false);
/* 1334 */     addUkuleleChord(paramChordMap, "F", 2, 0, 1, 0, 1, false);
/* 1335 */     addUkuleleChord(paramChordMap, "F#", 3, 1, 2, 1, 1, false);
/* 1336 */     addUkuleleChord(paramChordMap, "F#+", 2, 1, 1, 4, 1, false);
/* 1337 */     addUkuleleChord(paramChordMap, "F#6", 3, 3, 2, 4, 1, false);
/* 1338 */     addUkuleleChord(paramChordMap, "F#7", 3, 4, 2, 4, 1, false);
/* 1339 */     addUkuleleChord(paramChordMap, "F#9", 1, 1, 0, 1, 1, false);
/* 1340 */     addUkuleleChord(paramChordMap, "F#aug", 2, 1, 1, 4, 1, false);
/* 1341 */     addUkuleleChord(paramChordMap, "F#dim", 2, 3, 2, 3, 1, false);
/* 1342 */     addUkuleleChord(paramChordMap, "F#m", 2, 1, 2, 4, 1, false);
/* 1343 */     addUkuleleChord(paramChordMap, "F#m6", 2, 3, 2, 4, 1, false);
/* 1344 */     addUkuleleChord(paramChordMap, "F#m7", 2, 4, 2, 4, 1, false);
/* 1345 */     addUkuleleChord(paramChordMap, "F#maj", 3, 1, 2, 1, 1, false);
/* 1346 */     addUkuleleChord(paramChordMap, "F#maj7", 2, 4, 1, 3, 1, false);
/* 1347 */     addUkuleleChord(paramChordMap, "F#min", 2, 1, 2, 4, 1, false);
/* 1348 */     addUkuleleChord(paramChordMap, "F#min6", 2, 3, 2, 4, 1, false);
/* 1349 */     addUkuleleChord(paramChordMap, "F#min7", 2, 4, 2, 4, 1, false);
/* 1350 */     addUkuleleChord(paramChordMap, "F+", 2, 1, 1, 4, 1, false);
/* 1351 */     addUkuleleChord(paramChordMap, "F6", 2, 2, 1, 3, 1, false);
/* 1352 */     addUkuleleChord(paramChordMap, "F7", 2, 3, 1, 0, 1, false);
/* 1353 */     addUkuleleChord(paramChordMap, "F9", 2, 3, 3, 3, 1, false);
/* 1354 */     addUkuleleChord(paramChordMap, "Faug", 2, 1, 1, 4, 1, false);
/* 1355 */     addUkuleleChord(paramChordMap, "Fdim", 1, 2, 1, 2, 1, false);
/* 1356 */     addUkuleleChord(paramChordMap, "Fm", 1, 0, 1, 3, 1, false);
/* 1357 */     addUkuleleChord(paramChordMap, "Fm6", 1, 2, 1, 3, 1, false);
/* 1358 */     addUkuleleChord(paramChordMap, "Fm7", 1, 3, 1, 3, 1, false);
/* 1359 */     addUkuleleChord(paramChordMap, "Fmaj", 2, 0, 1, 0, 1, false);
/* 1360 */     addUkuleleChord(paramChordMap, "Fmaj7", 2, 4, 1, 3, 1, false);
/* 1361 */     addUkuleleChord(paramChordMap, "Fmin", 1, 0, 1, 3, 1, false);
/* 1362 */     addUkuleleChord(paramChordMap, "Fmin6", 1, 2, 1, 3, 1, false);
/* 1363 */     addUkuleleChord(paramChordMap, "Fmin7", 1, 3, 1, 3, 1, false);
/* 1364 */     addUkuleleChord(paramChordMap, "G", 0, 2, 3, 2, 1, false);
/* 1365 */     addUkuleleChord(paramChordMap, "G#", 3, 1, 2, 1, 2, false);
/* 1366 */     addUkuleleChord(paramChordMap, "G#+", 1, 0, 0, 3, 1, false);
/* 1367 */     addUkuleleChord(paramChordMap, "G#6", 1, 3, 1, 3, 1, false);
/* 1368 */     addUkuleleChord(paramChordMap, "G#7", 1, 3, 2, 3, 1, false);
/* 1369 */     addUkuleleChord(paramChordMap, "G#9", 3, 3, 2, 3, 1, false);
/* 1370 */     addUkuleleChord(paramChordMap, "G#aug", 1, 0, 0, 3, 1, false);
/* 1371 */     addUkuleleChord(paramChordMap, "G#dim", 1, 2, 1, 2, 1, false);
/* 1372 */     addUkuleleChord(paramChordMap, "G#m", 1, 3, 4, 2, 1, false);
/* 1373 */     addUkuleleChord(paramChordMap, "G#m6", 1, 3, 1, 2, 1, false);
/* 1374 */     addUkuleleChord(paramChordMap, "G#m7", 1, 3, 2, 2, 1, false);
/* 1375 */     addUkuleleChord(paramChordMap, "G#maj", 3, 1, 2, 1, 2, false);
/* 1376 */     addUkuleleChord(paramChordMap, "G#maj7", 1, 3, 3, 3, 1, false);
/* 1377 */     addUkuleleChord(paramChordMap, "G#min", 1, 3, 4, 2, 1, false);
/* 1378 */     addUkuleleChord(paramChordMap, "G#min6", 1, 3, 1, 2, 1, false);
/* 1379 */     addUkuleleChord(paramChordMap, "G#min7", 1, 3, 2, 2, 1, false);
/* 1380 */     addUkuleleChord(paramChordMap, "G+", 0, 3, 3, 2, 1, false);
/* 1381 */     addUkuleleChord(paramChordMap, "G6", 0, 2, 0, 2, 1, false);
/* 1382 */     addUkuleleChord(paramChordMap, "G7", 0, 2, 1, 2, 1, false);
/* 1383 */     addUkuleleChord(paramChordMap, "G9", 2, 2, 1, 2, 1, false);
/* 1384 */     addUkuleleChord(paramChordMap, "Gaug", 0, 3, 3, 2, 1, false);
/* 1385 */     addUkuleleChord(paramChordMap, "Gb", 3, 1, 2, 1, 1, false);
/* 1386 */     addUkuleleChord(paramChordMap, "Gb+", 2, 1, 1, 4, 1, false);
/* 1387 */     addUkuleleChord(paramChordMap, "Gb6", 3, 3, 2, 4, 1, false);
/* 1388 */     addUkuleleChord(paramChordMap, "Gb7", 3, 4, 2, 4, 1, false);
/* 1389 */     addUkuleleChord(paramChordMap, "Gb9", 1, 1, 0, 1, 1, false);
/* 1390 */     addUkuleleChord(paramChordMap, "Gbaug", 2, 1, 1, 4, 1, false);
/* 1391 */     addUkuleleChord(paramChordMap, "Gbdim", 2, 3, 2, 3, 1, false);
/* 1392 */     addUkuleleChord(paramChordMap, "Gbm", 2, 1, 2, 4, 1, false);
/* 1393 */     addUkuleleChord(paramChordMap, "Gbm6", 2, 3, 2, 4, 1, false);
/* 1394 */     addUkuleleChord(paramChordMap, "Gbm7", 2, 4, 2, 4, 1, false);
/* 1395 */     addUkuleleChord(paramChordMap, "Gbmaj", 3, 1, 2, 1, 1, false);
/* 1396 */     addUkuleleChord(paramChordMap, "Gbmaj7", 2, 4, 1, 3, 1, false);
/* 1397 */     addUkuleleChord(paramChordMap, "Gbmin", 2, 1, 2, 4, 1, false);
/* 1398 */     addUkuleleChord(paramChordMap, "Gbmin6", 2, 3, 2, 4, 1, false);
/* 1399 */     addUkuleleChord(paramChordMap, "Gbmin7", 2, 4, 2, 4, 1, false);
/* 1400 */     addUkuleleChord(paramChordMap, "Gdim", 0, 1, 0, 1, 1, false);
/* 1401 */     addUkuleleChord(paramChordMap, "Gm", 0, 2, 3, 1, 1, false);
/* 1402 */     addUkuleleChord(paramChordMap, "Gm6", 0, 2, 0, 1, 1, false);
/* 1403 */     addUkuleleChord(paramChordMap, "Gm7", 0, 2, 1, 1, 1, false);
/* 1404 */     addUkuleleChord(paramChordMap, "Gmaj", 0, 2, 3, 2, 1, false);
/* 1405 */     addUkuleleChord(paramChordMap, "Gmaj7", 0, 2, 2, 2, 1, false);
/* 1406 */     addUkuleleChord(paramChordMap, "Gmin", 0, 2, 3, 1, 1, false);
/* 1407 */     addUkuleleChord(paramChordMap, "Gmin6", 0, 2, 0, 1, 1, false);
/* 1408 */     addUkuleleChord(paramChordMap, "Gmin7", 0, 2, 1, 1, 1, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addChord(ChordMap paramChordMap, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean) {
/* 1414 */     Chord chord = new Chord(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, (byte)1, paramBoolean);
/* 1415 */     paramChordMap.add(chord);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addUkuleleChord(ChordMap paramChordMap, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) {
/* 1421 */     Chord chord = new Chord(paramString, paramInt1, paramInt2, paramInt3, paramInt4, 0, 0, paramInt5, (byte)1, paramBoolean);
/* 1422 */     chord.setUkulele(true);
/* 1423 */     paramChordMap.addUkulele(chord);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void readChordrc(ChordMap paramChordMap, File paramFile) {
/*      */     try {
/* 1431 */       FileReader fileReader = new FileReader(paramFile);
/* 1432 */       LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
/*      */       
/*      */       String str;
/* 1435 */       while ((str = lineNumberReader.readLine()) != null) {
/*      */         
/* 1437 */         if (!str.startsWith("{def")) {
/*      */           continue;
/*      */         }
/* 1440 */         char[] arrayOfChar = str.toCharArray();
/*      */         
/* 1442 */         byte b1 = 4;
/*      */         
/* 1444 */         for (; b1 < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b1]); b1++);
/* 1445 */         for (; b1 < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b1]); b1++);
/*      */         
/* 1447 */         byte b2 = b1;
/*      */         
/* 1449 */         for (; b1 < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b1]); b1++);
/* 1450 */         if (b1 >= arrayOfChar.length) {
/*      */           continue;
/*      */         }
/* 1453 */         int i = b1 - b2;
/* 1454 */         if (':' == arrayOfChar[b1 - 1]) {
/* 1455 */           i--;
/*      */         }
/* 1457 */         b1++;
/*      */         
/* 1459 */         for (; b1 < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b1]); b1++);
/*      */         
/* 1461 */         byte b3 = b1;
/*      */ 
/*      */         
/* 1464 */         for (; b1 < arrayOfChar.length && '}' != arrayOfChar[b1]; b1++);
/*      */         
/* 1466 */         if (b1 >= arrayOfChar.length) {
/*      */           continue;
/*      */         }
/*      */         
/* 1470 */         Chord chord = new Chord(new String(arrayOfChar, b2, i), new String(arrayOfChar, b3, b1 - b3), (byte)2, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1475 */         if (chord.isUkulele()) {
/* 1476 */           paramChordMap.addUkulele(chord); continue;
/*      */         } 
/* 1478 */         paramChordMap.add(chord);
/*      */       } 
/*      */       
/* 1481 */       lineNumberReader.close();
/* 1482 */       fileReader.close();
/*      */     }
/* 1484 */     catch (Exception exception) {
/*      */       
/* 1486 */       System.err.println("Chord: unable to read chordrc (first run?)");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void writeChordrc(byte paramByte, ChordMap paramChordMap, File paramFile) {
/*      */     try {
/* 1495 */       FileWriter fileWriter = new FileWriter(paramFile);
/*      */       
/* 1497 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 1498 */       fileWriter.write("# " + resourceBundle.getString("Songsheet Generator") + " " + resourceBundle.getString("Version") + " chordrc file - generated - do not edit!\n");
/*      */ 
/*      */       
/* 1501 */       Iterator<Chord> iterator = paramChordMap.iterator();
/*      */       
/* 1503 */       while (iterator.hasNext()) {
/*      */         
/* 1505 */         Chord chord = iterator.next();
/*      */         
/* 1507 */         if (paramByte == chord.getSource()) {
/*      */           
/* 1509 */           fileWriter.write(chord.toString());
/* 1510 */           fileWriter.write("\n");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1515 */       iterator = paramChordMap.iteratorUkulele();
/*      */       
/* 1517 */       while (iterator.hasNext()) {
/*      */         
/* 1519 */         Chord chord = iterator.next();
/*      */         
/* 1521 */         if (paramByte == chord.getSource()) {
/*      */           
/* 1523 */           fileWriter.write(chord.toString());
/* 1524 */           fileWriter.write("\n");
/*      */         } 
/*      */       } 
/*      */       
/* 1528 */       fileWriter.close();
/*      */     }
/* 1530 */     catch (Exception exception) {
/*      */       
/* 1532 */       System.err.println("Chord: unable to write chordrc");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(Object paramObject) {
/* 1540 */     return getName().compareTo(((Chord)paramObject).getName());
/*      */   }
/*      */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\Chord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */