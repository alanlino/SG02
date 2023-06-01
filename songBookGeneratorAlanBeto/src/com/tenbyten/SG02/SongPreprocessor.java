/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SongPreprocessor
/*     */   extends SongOutput
/*     */ {
/*     */   private String m_strTitle;
/*     */   private String m_strSubtitle;
/*  22 */   private ArrayList<Chord> m_keys = new ArrayList<Chord>();
/*  23 */   private Hashtable<String, Integer> m_tblChords = new Hashtable<String, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSong() {
/*  30 */     return (null != this.m_strTitle);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  35 */     return this.m_strTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSubtitle() {
/*  40 */     return this.m_strSubtitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Chord> getKeys() {
/*  47 */     if (0 == this.m_keys.size())
/*     */     {
/*     */       
/*  50 */       return null;
/*     */     }
/*     */     
/*  53 */     if (SG02App.isDebug) {
/*     */       
/*  55 */       System.err.println(this.m_strTitle);
/*  56 */       System.err.println(this.m_paragraphs);
/*     */     } 
/*     */     
/*  59 */     return this.m_keys;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<SongOutput.Paragraph> getParagraphs() {
/*  64 */     return this.m_paragraphs;
/*     */   }
/*     */ 
/*     */   
/*     */   public int printTitle(String paramString) {
/*  69 */     this.m_strTitle = paramString;
/*  70 */     this.m_curParagraph = null;
/*  71 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int printSubtitle(String paramString) {
/*  76 */     this.m_strSubtitle = paramString;
/*  77 */     this.m_curParagraph = null;
/*  78 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChord(Chord paramChord, String paramString) {
/*  97 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int printKey(Chord paramChord) {
/* 102 */     this.m_keys.add(paramChord);
/* 103 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addParagraph() {
			 try{
				 /* 108 */     this.m_curParagraph = new SongOutput.Paragraph();
				 
				 if(this.m_paragraphs!=null){
					 /* 109 */     this.m_paragraphs.add(this.m_curParagraph);
				 }
				 
				 
			 }catch(Exception e){
				 e.printStackTrace();
			 }

/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChordNewLine() {
/* 115 */     if (null == this.m_curParagraph)
/* 116 */       addParagraph(); 
/* 117 */     this.m_curParagraph.m_nNumChordLines++;
/*     */     
/* 119 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int printLyric(String paramString) {
/* 124 */     if (0 == paramString.trim().length()) {
/* 125 */       this.m_curParagraph = null;
/*     */     } else {
/*     */       
/* 128 */       if (null == this.m_curParagraph)
/* 129 */         addParagraph(); 
/* 130 */       if (this.m_bInTab) {
/* 131 */         this.m_curParagraph.m_nNumChordLines++;
/*     */       } else {
/* 133 */         this.m_curParagraph.m_nNumLyricLines++;
/*     */       } 
/*     */     } 
/* 136 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int printComment(String paramString) {
/* 141 */     return printLyric(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public int printGuitarComment(String paramString) {
/* 146 */     return printChordNewLine();
/*     */   }
/*     */ 
/*     */   
/*     */   public int printNormalSpace() {
/* 151 */     this.m_curParagraph = null;
/* 152 */     return 2;
/*     */   }
/*     */   
/* 155 */   public int markStartOfChorus() { return printNormalSpace(); } public int markEndOfChorus() {
/* 156 */     return printNormalSpace();
/*     */   }
/*     */   
/*     */   public int markStartOfTab() {
/* 160 */     this.m_bInTab = true;
/* 161 */     return printNormalSpace();
/*     */   }
/*     */ 
/*     */   
/*     */   public int markEndOfTab() {
/* 166 */     this.m_bInTab = false;
/* 167 */     return printNormalSpace();
/*     */   }
/*     */   public int markNewSong() {
/* 170 */     return printNormalSpace();
/*     */   }
/* 172 */   public int newColumn() { return printNormalSpace(); }
/* 173 */   public int newPage() { return printNormalSpace(); } public int newPhysicalPage() {
/* 174 */     return printNormalSpace();
/*     */   }
/*     */   public void addSongFile(SongFile paramSongFile) {}
/*     */   public void clearSongFiles() {}
/*     */   
/*     */   public int printChordSpaceAbove() {
/* 180 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongPreprocessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */