/*     */ package com.tenbyten.SG02;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class SongPlaintexter extends SongOutput {
/*     */   protected boolean m_bCloseOutput;
/*     */   protected Vector<SongFile> m_qSongFiles;
/*     */   protected SongFile m_curSongFile;
/*     */   protected String m_strPrevChord;
/*     */   protected String m_strPrevLyric;
/*     */   protected File m_outputPath;
/*     */   protected OutputStreamWriter m_out;
/*     */   protected boolean m_bUTF8;
/*     */   protected boolean m_bUTF16;
/*     */   
/*     */   public SongPlaintexter(File paramFile) {
/*  22 */     this.m_outputPath = paramFile;
/*  23 */     this.m_bUTF8 = (0 == this.m_props.getProperty("songs.encoding").compareTo("UTF-8"));
/*  24 */     this.m_bUTF16 = (0 == this.m_props.getProperty("songs.encoding").compareTo("UTF-16"));
/*  25 */     this.m_bCloseOutput = true;
/*  26 */     this.m_strPrevChord = new String();
/*  27 */     this.m_strPrevLyric = new String();
/*  28 */     this.m_qSongFiles = new Vector<SongFile>();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean printSongs() throws IOException {
/*  33 */     if (null == this.m_out)
/*     */     {
/*  35 */       if (this.m_bUTF16) {
/*  36 */         this.m_out = new OutputStreamWriter(new FileOutputStream(this.m_outputPath), "UTF-16");
/*  37 */       } else if (this.m_bUTF8) {
/*     */         
/*  39 */         this.m_out = new OutputStreamWriter(new FileOutputStream(this.m_outputPath), "UTF-8");
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/*  46 */         this.m_out = new FileWriter(this.m_outputPath);
/*     */       } 
/*     */     }
/*  49 */     this.m_nTOCNumber = 0;
/*     */     
/*  51 */     byte b = 0;
/*     */ 
/*     */     
/*     */     try {
/*  55 */       ListIterator<SongFile> listIterator = this.m_qSongFiles.listIterator();
/*     */       
/*  57 */       while (listIterator.hasNext()) {
/*     */         
/*  59 */         if (null != this.m_progressMonitor) {
/*  60 */           this.m_progressMonitor.setProgress(++b);
/*     */         }
/*  62 */         this.m_curSongFile = listIterator.next();
/*  63 */         this.m_curSongFile.printSong(this);
/*     */         
/*  65 */         if (listIterator.hasNext()) {
/*  66 */           markNewSong();
/*     */         }
/*     */       } 
/*  69 */       this.m_out.flush();
/*     */       
/*  71 */       if (this.m_bCloseOutput) {
/*  72 */         this.m_out.close();
/*     */       }
/*  74 */     } catch (Exception exception) {
/*     */       
/*  76 */       return false;
/*     */     } 
/*     */     
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSongFile(SongFile paramSongFile) {
/*     */     try {
/*  86 */       this.m_qSongFiles.add((SongFile)paramSongFile.clone());
/*  87 */       if (paramSongFile.isUTF16()) {
/*  88 */         this.m_bUTF16 = true;
/*     */       }
/*  90 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSongFiles() {
/*  97 */     this.m_qSongFiles.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printTitle(String paramString) {
/*     */     try {
/* 104 */       if ('y' == this.m_props.getProperty("songs.number").charAt(0)) {
/* 105 */         this.m_out.write(String.valueOf(++this.m_nTOCNumber) + ". ");
/*     */       }
/* 107 */       this.m_out.write(paramString + m_strNewline);
/* 108 */       return 2;
/*     */     }
/* 110 */     catch (IOException iOException) {
/*     */       
/* 112 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printSubtitle(String paramString) {
/*     */     try {
/* 120 */       this.m_out.write("(" + paramString + ")" + m_strNewline);
/* 121 */       return 2;
/*     */     }
/* 123 */     catch (IOException iOException) {
/*     */       
/* 125 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChord(Chord paramChord, String paramString) {
/* 132 */     String str = null;
/*     */ 
/*     */     
/*     */     try {
/* 136 */       if (0 != paramString.length()) {
/*     */         
/* 138 */         StringBuffer stringBuffer = new StringBuffer(paramString);
/*     */         
/* 140 */         int i = this.m_strPrevLyric.length();
/* 141 */         if (stringBuffer.length() > i)
/*     */         {
/* 143 */           if (0 == i || stringBuffer.substring(0, i).equals(this.m_strPrevLyric.substring(0, i))) {
/*     */             
/* 145 */             i = stringBuffer.length() - i;
/* 146 */             i -= this.m_strPrevChord.length();
/* 147 */             if (0 < this.m_strPrevChord.length())
/* 148 */               i--; 
/* 149 */             for (byte b = 0; b < i; b++) {
/* 150 */               this.m_out.write(" ");
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 155 */       str = this.m_bDoReMi ? paramChord.getDoReMiName() : paramChord.getName();
/*     */       
/* 157 */       this.m_out.write(str + " ");
/*     */     }
/* 159 */     catch (IOException iOException) {
/*     */       
/* 161 */       return 0;
/*     */     } 
/*     */     
/* 164 */     this.m_strPrevChord = str;
/* 165 */     this.m_strPrevLyric = paramString;
/*     */     
/* 167 */     return 2;
/*     */   }
/*     */   public int printChordSpaceAbove() {
/* 170 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int printChordNewLine() {
/*     */     try {
/* 176 */       this.m_out.write(m_strNewline);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       boolean bool = true;
/*     */       
/* 184 */       for (byte b = 0; b < this.m_strPrevLyric.length(); b++) {
/* 185 */         if (!Character.isWhitespace(this.m_strPrevLyric.charAt(b))) {
/*     */           
/* 187 */           bool = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 191 */       if (bool) {
/*     */         
/* 193 */         this.m_strPrevLyric = "";
/* 194 */         this.m_strPrevChord = "";
/*     */       } 
/*     */ 
/*     */       
/* 198 */       return 2;
/*     */     }
/* 200 */     catch (IOException iOException) {
/*     */       
/* 202 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printLyric(String paramString) {
/*     */     try {
/* 210 */       this.m_out.write(paramString + m_strNewline);
/* 211 */       this.m_strPrevLyric = "";
/* 212 */       this.m_strPrevChord = "";
/* 213 */       return 2;
/*     */     }
/* 215 */     catch (IOException iOException) {
/*     */       
/* 217 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printComment(String paramString) {
/*     */     try {
/* 225 */       this.m_out.write(paramString + m_strNewline);
/* 226 */       return 2;
/*     */     }
/* 228 */     catch (IOException iOException) {
/*     */       
/* 230 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int printGuitarComment(String paramString) {
/* 236 */     return printComment(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printNormalSpace() {
/*     */     try {
/* 243 */       this.m_out.write(m_strNewline);
/* 244 */       return 2;
/*     */     }
/* 246 */     catch (IOException iOException) {
/*     */       
/* 248 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/* 252 */   public int markStartOfChorus() { return 2; }
/* 253 */   public int markEndOfChorus() { return 2; }
/* 254 */   public int markStartOfTab() { return 2; } public int markEndOfTab() {
/* 255 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int markNewSong() {
/*     */     try {
/* 261 */       this.m_out.write(m_strNewline + m_strNewline);
/* 262 */       return 2;
/*     */     }
/* 264 */     catch (IOException iOException) {
/*     */       
/* 266 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/* 270 */   public int newColumn() { return 2; }
/* 271 */   public int newPage() { return 2; } public int newPhysicalPage() {
/* 272 */     return 2;
/*     */   }
/*     */
@Override
public int printTag(String paramString) {
	// TODO Auto-generated method stub
	return 0;
} }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongPlaintexter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */