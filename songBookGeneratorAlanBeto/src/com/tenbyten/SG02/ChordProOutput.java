/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChordProOutput
/*     */   extends SongPlaintexter
/*     */ {
/*     */   protected boolean m_bEatFirstNewline = true;
/*     */   protected boolean m_bLineHasLyric = false;
/*     */   
/*     */   public ChordProOutput(File paramFile) {
/*  20 */     super(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printTitle(String paramString) {
/*     */     try {
/*  28 */       this.m_out.write("{t:" + paramString + "}" + m_strNewline);
/*     */ 
/*     */ 
/*     */       
/*  32 */       ChordMap chordMap = this.m_curSongFile.getSongDefinedChords();
/*  33 */       Iterator<Chord> iterator = chordMap.iterator();
/*  34 */       while (iterator.hasNext()) {
/*     */         
/*  36 */         Chord chord = iterator.next();
/*  37 */         this.m_out.write(chord.toString() + m_strNewline);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  43 */       Map.Entry[] arrayOfEntry = (Map.Entry[])this.m_curSongFile.getKeyValueMapEntries();
/*     */       
/*  45 */       for (byte b = 0; b < arrayOfEntry.length; b++)
/*     */       {
/*  47 */         this.m_out.write("{d_" + (String)arrayOfEntry[b].getKey() + ":" + (String)arrayOfEntry[b].getValue() + "}" + m_strNewline);
/*     */       }
/*     */ 
/*     */       
/*  51 */       this.m_bEatFirstNewline = true;
/*     */       
/*  53 */       return 2;
/*     */     }
/*  55 */     catch (IOException iOException) {
/*     */       
/*  57 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printSubtitle(String paramString) {
/*     */     try {
/*  66 */       this.m_out.write("{st:" + paramString + "}" + m_strNewline);
/*  67 */       return 2;
/*     */     }
/*  69 */     catch (IOException iOException) {
/*     */       
/*  71 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChord(Chord paramChord, String paramString) {
/*     */     try {
/*  80 */       int i = 0;
/*     */       
/*  82 */       if (null != this.m_strPrevLyric) {
/*  83 */         i = this.m_strPrevLyric.length();
/*     */       }
/*  85 */       this.m_strPrevLyric = paramString;
/*  86 */       this.m_bLineHasLyric = (0 != paramString.trim().length());
/*     */       
/*  88 */       if (0 != i && i <= paramString.length()) {
/*  89 */         paramString = paramString.substring(i);
/*     */       }
/*  91 */       this.m_out.write(paramString + "[" + paramChord.getName() + "]");
/*     */       
/*  93 */       return 2;
/*     */     }
/*  95 */     catch (IOException iOException) {
/*     */       
/*  97 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChordSpaceAbove() {
/* 104 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChordNewLine() {
/*     */     try {
/* 112 */       if (!this.m_bLineHasLyric)
/*     */       {
/* 114 */         this.m_out.write(m_strNewline);
/*     */       }
/* 116 */       return 2;
/*     */     }
/* 118 */     catch (IOException iOException) {
/*     */       
/* 120 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printLyric(String paramString) {
/*     */     try {
/* 132 */       if (null != this.m_strPrevLyric && this.m_strPrevLyric.length() <= paramString.length()) {
/* 133 */         paramString = paramString.substring(this.m_strPrevLyric.length());
/*     */       }
/* 135 */       this.m_strPrevLyric = null;
/* 136 */       this.m_bLineHasLyric = true;
/*     */       
/* 138 */       this.m_out.write(paramString + m_strNewline);
/*     */       
/* 140 */       return 2;
/*     */     }
/* 142 */     catch (IOException iOException) {
/*     */       
/* 144 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printComment(String paramString) {
/*     */     try {
/* 153 */       this.m_out.write("{c:" + paramString + "}" + m_strNewline);
/* 154 */       return 2;
/*     */     }
/* 156 */     catch (IOException iOException) {
/*     */       
/* 158 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printGuitarComment(String paramString) {
/*     */     try {
/* 167 */       this.m_out.write("{gc:" + paramString + "}" + m_strNewline);
/* 168 */       return 2;
/*     */     }
/* 170 */     catch (IOException iOException) {
/*     */       
/* 172 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printNormalSpace() {
/*     */     try {
/* 181 */       if (!this.m_bEatFirstNewline) {
/* 182 */         this.m_out.write(m_strNewline);
/*     */       } else {
/* 184 */         this.m_bEatFirstNewline = false;
/*     */       } 
/* 186 */       return 2;
/*     */     }
/* 188 */     catch (IOException iOException) {
/*     */       
/* 190 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markStartOfChorus() {
/*     */     try {
/* 199 */       this.m_out.write("{soc}" + m_strNewline);
/* 200 */       return 2;
/*     */     }
/* 202 */     catch (IOException iOException) {
/*     */       
/* 204 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markEndOfChorus() {
/*     */     try {
/* 213 */       this.m_out.write("{eoc}" + m_strNewline);
/* 214 */       return 2;
/*     */     }
/* 216 */     catch (IOException iOException) {
/*     */       
/* 218 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int markStartOfTab() {
/*     */     try {
/* 226 */       this.m_out.write("{sot}" + m_strNewline);
/* 227 */       return 2;
/*     */     }
/* 229 */     catch (IOException iOException) {
/*     */       
/* 231 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int markEndOfTab() {
/*     */     try {
/* 239 */       this.m_out.write("{eot}" + m_strNewline);
/* 240 */       return 2;
/*     */     }
/* 242 */     catch (IOException iOException) {
/*     */       
/* 244 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markNewSong() {
/*     */     try {
/* 253 */       this.m_out.write("{ns}" + m_strNewline);
/* 254 */       return 2;
/*     */     }
/* 256 */     catch (IOException iOException) {
/*     */       
/* 258 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int newColumn() {
/*     */     try {
/* 266 */       this.m_out.write("{colb}" + m_strNewline);
/* 267 */       return 2;
/*     */     }
/* 269 */     catch (IOException iOException) {
/*     */       
/* 271 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int newPage() {
/*     */     try {
/* 279 */       this.m_out.write("{np}" + m_strNewline);
/* 280 */       return 2;
/*     */     }
/* 282 */     catch (IOException iOException) {
/*     */       
/* 284 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int newPhysicalPage() {
/*     */     try {
/* 292 */       this.m_out.write("{npp}" + m_strNewline);
/* 293 */       return 2;
/*     */     }
/* 295 */     catch (IOException iOException) {
/*     */       
/* 297 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printKey(Chord paramChord) {
/*     */     try {
/* 306 */       this.m_out.write("{key:" + paramChord.getName() + "}" + m_strNewline);
/* 307 */       return 2;
/*     */     }
/* 309 */     catch (IOException iOException) {
/*     */       
/* 311 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printArtist(String paramString) {
/*     */     try {
/* 319 */       this.m_out.write("{artist:" + paramString + "}" + m_strNewline);
/* 320 */       return 2;
/*     */     }
/* 322 */     catch (IOException iOException) {
/*     */       
/* 324 */       return 0;
/*     */     } 
/*     */   }

			public int printTag(String paramString) {
/*     */     try {
/* 319 */       this.m_out.write("{tag:" + paramString + "}" + m_strNewline);
/* 320 */       return 2;
/*     */     }
/* 322 */     catch (IOException iOException) {
/*     */       
/* 324 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printCopyright(String paramString) {
/*     */     try {
/* 332 */       this.m_out.write("{copyright:" + paramString + "}" + m_strNewline);
/* 333 */       return 2;
/*     */     }
/* 335 */     catch (IOException iOException) {
/*     */       
/* 337 */       return 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\ChordProOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */