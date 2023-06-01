/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ public class SongImporter {
/*     */   public static final int kError = 0;
/*     */   public static final int kGood = 1;
/*     */   public static final int kMaybeOK = 2;
/*  15 */   private static final char[] k_chordChars1stPass = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', '2', '7', '9', '#', ' ', 'b', 'm', '/' };
/*  16 */   private static final char[] k_chordChars2ndPass = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', '2', '7', '9', '#', ' ', '\t', 'b', 'm', '/', 'a', 'c', 'd', 'e', 'f', 'g', 'i', 'j', 's', 'u', 'M', 'N', '.', '1', '3', '4', '5', '6', '8', '0', '+', '-', '(', ')' };
/*  17 */   private static final char[] k_chordLettersOnly = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String m_pPrevChordLine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String m_strImportedSong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private Properties m_props = (Properties)SG02App.props.clone();
/*     */   
/*  40 */   private int m_minDashes = Integer.parseInt(this.m_props.getProperty("import.tab.dashes.min"));
/*  41 */   private int m_minDigits = Integer.parseInt(this.m_props.getProperty("import.tab.digits.min"));
/*  42 */   private int m_minNotes = Integer.parseInt(this.m_props.getProperty("import.tab.notes.min"));
/*  43 */   private int m_maxNotes = Integer.parseInt(this.m_props.getProperty("import.tab.notes.max"));
/*  44 */   private int m_minStrokes = Integer.parseInt(this.m_props.getProperty("import.tab.strokes.min"));
/*  45 */   private int m_maxStrokes = Integer.parseInt(this.m_props.getProperty("import.tab.strokes.max"));
/*  46 */   private int m_withinDashes = Integer.parseInt(this.m_props.getProperty("import.tab.dashes.within"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int importFile(File paramFile) {
/*  52 */     int i = 0;
/*     */ 
/*     */     
/*  55 */     this.m_pPrevChordLine = null;
/*  56 */     this.m_strImportedSong = "";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  62 */       boolean bool = false;
/*     */       
/*     */       try {
/*  65 */         SongFile songFile = new SongFile(paramFile);
/*  66 */         bool = songFile.isSongFile();
/*     */       }
/*  68 */       catch (Exception exception) {}
/*     */       
/*  70 */       FileReader fileReader = new FileReader(paramFile);
/*  71 */       LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
/*     */       
/*  73 */       if (!bool)
/*     */       {
/*  75 */         printTitle(paramFile.getName());
/*     */       }
/*  77 */       i = importFile(lineNumberReader);
/*     */       
/*  79 */       lineNumberReader.close();
/*     */     }
/*  81 */     catch (Exception exception) {
/*     */       
/*  83 */       System.err.println("SongImporter.importFile(): caught exception.");
/*  84 */       i = 0;
/*     */     } 
/*     */     
/*  87 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int importFile(LineNumberReader paramLineNumberReader) throws IOException {
/*  93 */     int i = 1;
/*     */     
/*  95 */     boolean bool = false;
/*     */     
/*     */     String str;
/*  98 */     while ((str = paramLineNumberReader.readLine()) != null) {
/*     */       
/* 100 */       char[] arrayOfChar = str.toCharArray();
/* 101 */       int j = arrayOfChar.length;
/*     */ 
/*     */       
/* 104 */       while (0 != j && Character.isWhitespace(arrayOfChar[j - 1])) {
/*     */         
/* 106 */         arrayOfChar[j - 1] = Character.MIN_VALUE;
/* 107 */         j--;
/*     */       } 
/*     */       
/* 110 */       if (j != str.length()) {
/*     */         
/* 112 */         char[] arrayOfChar1 = new char[j];
/* 113 */         System.arraycopy(arrayOfChar, 0, arrayOfChar1, 0, j);
/* 114 */         arrayOfChar = arrayOfChar1;
/*     */       } 
/*     */       
/* 117 */       if (0 == j) {
/*     */ 
/*     */         
/* 120 */         if (null != this.m_pPrevChordLine) {
/*     */           
/* 122 */           printChordsWithLyric(this.m_pPrevChordLine, "");
/* 123 */           this.m_pPrevChordLine = null;
/*     */         } 
/*     */ 
/*     */         
/* 127 */         this.m_strImportedSong += SongOutput.m_strNewline;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 132 */       int k = isTabLine(arrayOfChar);
/*     */       
/* 134 */       if (0 != k) {
/*     */         
/* 136 */         if (!bool)
/* 137 */           markStartOfTab(); 
/* 138 */         bool = true;
/*     */         
/* 140 */         this.m_strImportedSong += new String(arrayOfChar);
/* 141 */         this.m_strImportedSong += SongOutput.m_strNewline;
/*     */         
/*     */         continue;
/*     */       } 
/* 145 */       if (bool) {
/*     */         
/* 147 */         markEndOfTab();
/* 148 */         bool = false;
/*     */       } 
/*     */       
/* 151 */       int m = isChordLine(arrayOfChar);
/*     */       
/* 153 */       if (0 == m) {
/* 154 */         m = isRhythmAndNotesLine(str, arrayOfChar);
/*     */       }
/*     */       
/* 157 */       if (0 != m) {
/*     */         
/* 159 */         if (null != this.m_pPrevChordLine) {
/*     */ 
/*     */ 
/*     */           
/* 163 */           printChordsWithLyric(this.m_pPrevChordLine, "");
/* 164 */           this.m_pPrevChordLine = null;
/*     */         }
/* 166 */         else if (1 != m) {
/* 167 */           i = m;
/*     */         } 
/*     */         
/* 170 */         this.m_pPrevChordLine = new String(arrayOfChar);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 175 */       stripBadCharacters(arrayOfChar);
/*     */       
/* 177 */       if (null != this.m_pPrevChordLine) {
/*     */         
/* 179 */         printChordsWithLyric(this.m_pPrevChordLine, new String(arrayOfChar));
/* 180 */         this.m_pPrevChordLine = null;
/*     */         
/*     */         continue;
/*     */       } 
/* 184 */       this.m_strImportedSong += new String(arrayOfChar);
/* 185 */       this.m_strImportedSong += SongOutput.m_strNewline;
/*     */     } 
/*     */ 
/*     */     
/* 189 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean save(File paramFile) {
/* 195 */     boolean bool = false;
/*     */ 
/*     */     
/*     */     try {
/* 199 */       FileWriter fileWriter = new FileWriter(paramFile);
/* 200 */       fileWriter.write(this.m_strImportedSong);
/* 201 */       fileWriter.close();
/*     */       
/* 203 */       bool = true;
/*     */     }
/* 205 */     catch (Exception exception) {
/*     */       
/* 207 */       bool = false;
/*     */     } 
/*     */     
/* 210 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int isChordLine(char[] paramArrayOfchar) {
/* 216 */     byte b1 = 0;
/*     */ 
/*     */     
/* 219 */     boolean bool = false; byte b2;
/* 220 */     for (b2 = 0; b2 < paramArrayOfchar.length && !bool; b2++) {
/*     */ 
/*     */       
/* 223 */       for (byte b = 0; b < k_chordChars1stPass.length; b++) {
/*     */         
/* 225 */         bool = true;
/* 226 */         if (paramArrayOfchar[b2] == k_chordChars1stPass[b]) {
/*     */           
/* 228 */           bool = false;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 233 */     if (!bool) {
/* 234 */       b1 = 1;
/*     */     } else {
/*     */       
/* 237 */       bool = false;
/*     */       
/* 239 */       for (b2 = 0; b2 < paramArrayOfchar.length && !bool; b2++) {
/*     */         
/* 241 */         for (byte b = 0; b < k_chordChars2ndPass.length; b++) {
/*     */           
/* 243 */           bool = true;
/* 244 */           if (paramArrayOfchar[b2] == k_chordChars2ndPass[b]) {
/*     */             
/* 246 */             bool = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 252 */       if (!bool) {
/* 253 */         b1 = 2;
/*     */       }
/*     */     } 
/* 256 */     if (b1 != 0) {
/*     */       
/* 258 */       bool = false;
/*     */       
/* 260 */       for (b2 = 0; b2 < paramArrayOfchar.length && !bool; b2++) {
/*     */         
/* 262 */         for (byte b = 0; b < k_chordLettersOnly.length && !bool; b++) {
/*     */           
/* 264 */           if (paramArrayOfchar[b2] == k_chordLettersOnly[b]) {
/*     */             
/* 266 */             bool = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 272 */       if (!bool) {
/* 273 */         b1 = 0;
/*     */       }
/*     */     } 
/* 276 */     return b1;
/*     */   }
/*     */ 
/*     */ 
/*     */   

			protected int isRhythmAndNotesLine(String string, char[] arrc) {
			    int n = 0;
			    if (-1 != string.indexOf(" / ") || -1 != string.indexOf(" | ") || -1 != string.indexOf(" . ") || -1 != string.indexOf("   ") && -1 != string.indexOf(40) && -1 != string.indexOf(41)) {
			        n = 1;
			    }
		    	return n;
			}

/*     */ 
/*     */ 
/*     */   
			protected int isTabLine(char[] arrc) {
		        int n;
		        int n2 = 0;
		        int n3 = 0;
		        int n4 = 0;
		        int n5 = 0;
		        int n6 = 0;
		        for (n = 0; n < arrc.length && '\u0000' != arrc[n]; ++n) {
		            if ('-' == arrc[n]) {
		                ++n3;
		                continue;
		            }
		            if (Character.isDigit(arrc[n])) {
		                ++n4;
		                continue;
		            }
		            if ('E' == arrc[n] || 'A' == arrc[n] || 'D' == arrc[n] || 'G' == arrc[n] || 'B' == arrc[n] || 'e' == arrc[n]) {
		                ++n5;
		                continue;
		            }
		            if ('d' != arrc[n] && 'u' != arrc[n] && 'h' != arrc[n] && 'p' != arrc[n] && 'b' != arrc[n] && 'r' != arrc[n] && '/' != arrc[n] && '\\' != arrc[n] && 'v' != arrc[n] && 't' != arrc[n] && 'x' != arrc[n]) continue;
		            ++n6;
		        }
		        if (this.m_minDashes <= n3) {
		            n = 0;
		            int n7 = Integer.MAX_VALUE;
		            for (int i = 0; i < arrc.length; ++i) {
		                if (Character.isWhitespace(arrc[i])) continue;
		                if (Integer.MAX_VALUE == n7) {
		                    n7 = i;
		                }
		                if ('-' != arrc[i]) continue;
		                if (this.m_withinDashes <= i - n7) break;
		                n = 1;
		                break;
		            }
		            if (n != 0) {
		                if (this.m_minDigits <= n4) {
		                    n2 = 1;
		                } else if (this.m_minNotes <= n5 && this.m_maxNotes >= n5 && this.m_minStrokes <= n6 && this.m_maxStrokes >= n6) {
		                    n2 = 1;
		                }
		            }
		        }
		        return n2;
		    }
			/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void stripBadCharacters(char[] paramArrayOfchar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void printTitle(String paramString) {
/* 406 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 407 */     this.m_strImportedSong += "{title:";
/* 408 */     this.m_strImportedSong += resourceBundle.getString("Text.Imported");
/* 409 */     this.m_strImportedSong += " ";
/* 410 */     this.m_strImportedSong += paramString;
/* 411 */     this.m_strImportedSong += "}";
/* 412 */     this.m_strImportedSong += SongOutput.m_strNewline;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void printChordsWithLyric(String paramString1, String paramString2) {
/* 418 */     char[] arrayOfChar = paramString1.toCharArray();
/*     */     
/* 420 */     int i = paramString2.length();
/* 421 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     boolean bool = (-1 != paramString1.indexOf(" / ") || -1 != paramString1.indexOf(" | ") || -1 != paramString1.indexOf(" . ") || (-1 != paramString1.indexOf("   ") && -1 != paramString1.indexOf('(') && -1 != paramString1.indexOf(')'))) ? true : false;
/*     */     
/* 428 */     for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
/*     */       
/* 430 */       if (' ' == arrayOfChar[b2]) {
/*     */         
/* 432 */         if (b1 < i && 0 != i) {
/* 433 */           this.m_strImportedSong += paramString2.charAt(b1++);
/*     */         }
/* 435 */       } else if ('\t' == arrayOfChar[b2]) {
/*     */ 
/*     */         
/* 438 */         if (b1 < i && 0 != i)
/*     */         {
/* 440 */           for (int j = b1 + 8 - b1 % 8; b1 < j; b1++) {
/* 441 */             this.m_strImportedSong += paramString2.charAt(b1);
/*     */           }
/*     */         }
/*     */       } else {
/*     */         
/* 446 */         this.m_strImportedSong += "[";
/*     */         
/* 448 */         String str = "";
/*     */         
/* 450 */         boolean bool1 = false;
/*     */         
/* 452 */         while (b2 < arrayOfChar.length && Character.MIN_VALUE != arrayOfChar[b2]) {
/*     */           
/* 454 */           if (bool) {
/*     */             
/* 456 */             if ('(' == arrayOfChar[b2])
/*     */             {
/* 458 */               bool1 = true;
/*     */             }
/* 460 */             else if (!bool1 && Character.isWhitespace(arrayOfChar[b2]))
/*     */             {
/*     */               
/*     */               break;
/*     */             }
/*     */           
/*     */           }
/* 467 */           else if (Character.isWhitespace(arrayOfChar[b2])) {
/*     */             break;
/*     */           } 
/*     */           
/* 471 */           this.m_strImportedSong += arrayOfChar[b2++];
/*     */           
/* 473 */           if (b1 < i && 0 != i) {
/* 474 */             str = str + paramString2.charAt(b1++);
/*     */           }
/*     */         } 
/* 477 */         this.m_strImportedSong += "]";
/*     */         
/* 479 */         this.m_strImportedSong += str;
/*     */         
/* 481 */         b2--;
/*     */       } 
/*     */     } 
/*     */     
/* 485 */     if (b1 < i && 0 != i) {
/* 486 */       this.m_strImportedSong += paramString2.substring(b1);
/*     */     }
/* 488 */     this.m_strImportedSong += SongOutput.m_strNewline;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void markStartOfTab() {
/* 494 */     this.m_strImportedSong += "{start_of_tab}";
/* 495 */     this.m_strImportedSong += SongOutput.m_strNewline;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void markEndOfTab() {
/* 501 */     this.m_strImportedSong += "{end_of_tab}";
/* 502 */     this.m_strImportedSong += SongOutput.m_strNewline;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongImporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */