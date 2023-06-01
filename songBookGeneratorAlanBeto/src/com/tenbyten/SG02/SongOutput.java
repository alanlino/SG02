/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.swing.ProgressMonitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SongOutput
/*     */ {
/*     */   public static final int kError = 0;
/*     */   public static final int kFinished = 1;
/*     */   public static final int kContinue = 2;
/*     */   public static final int kPause = 3;
/*     */   public static final int kOne = 1;
/*     */   public static final int kTwo = 2;
/*     */   public static final int kFour = 4;
/*     */   public static final int kOneColumn = 10;
/*     */   public static final int kTwoColumn = 20;
/*     */   public static final int kAlignLeft = 1;
/*     */   public static final int kAlignCenter = 2;
/*     */   public static final int kAlignRight = 3;
/*  26 */   public static final String m_strNewline = System.getProperty("line.separator");
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
/*  54 */   protected Properties m_props = (Properties)SG02App.props.clone();
/*     */   
/*  56 */   protected int m_nSongsPerPage = Integer.parseInt(this.m_props.getProperty("print.songs.per.page"));
/*  57 */   protected float m_nMarginTop = Float.parseFloat(this.m_props.getProperty("print.margin.top"));
/*  58 */   protected float m_nMarginBottom = Float.parseFloat(this.m_props.getProperty("print.margin.bottom"));
/*  59 */   protected float m_nMarginLeft = Float.parseFloat(this.m_props.getProperty("print.margin.left"));
/*  60 */   protected float m_nMarginRight = Float.parseFloat(this.m_props.getProperty("print.margin.right"));
/*  61 */   protected boolean m_bPrintTOC = ('y' == this.m_props.getProperty("toc.print").charAt(0));
/*     */   protected boolean m_bOnlyTOC = false;
/*     */   protected boolean m_bInTOC = false;
/*  64 */   protected int m_nTOCNumber = 0;
/*  65 */   protected boolean m_bDoReMi = ('y' == this.m_props.getProperty("print.chords.doremi").charAt(0));
/*     */   protected boolean m_bInTab = false;
/*     */   protected ProgressMonitor m_progressMonitor;
/*  68 */   protected ArrayList<Paragraph> m_paragraphs = null;
/*  68 */   protected ArrayList<String> m_tags = null;
/*  69 */   protected Paragraph m_curParagraph = null;
/*     */   
/*     */   public Properties getProps() {
/*  72 */     return this.m_props;
/*     */   }
/*     */   public abstract void addSongFile(SongFile paramSongFile);
/*     */   public abstract void clearSongFiles();
/*     */   public int getSongsPerPage() {
/*  77 */     return this.m_nSongsPerPage;
/*     */   } public void overrideSongsPerPage(int paramInt) {
/*  79 */     this.m_nSongsPerPage = paramInt;
/*     */   }
/*     */   public void overrideMargins(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  82 */     this.m_nMarginTop = paramFloat1; this.m_nMarginBottom = paramFloat2; this.m_nMarginLeft = paramFloat3; this.m_nMarginRight = paramFloat4;
/*     */   }
/*  84 */   public void overridePrintTOC(boolean paramBoolean) { this.m_bPrintTOC = paramBoolean; } public void overridePrintTOCOnly(boolean paramBoolean) {
/*  85 */     this.m_bOnlyTOC = paramBoolean;
/*     */   }
/*     */   public void overrideAutoNumberSongs(boolean paramBoolean) {
/*  88 */     if (paramBoolean) {
/*  89 */       this.m_props.setProperty("songs.number", "yes");
/*     */     } else {
/*  91 */       this.m_props.setProperty("songs.number", "no");
/*     */     } 
/*     */   }
/*     */   public abstract int printTitle(String paramString);
/*     */   public abstract int printSubtitle(String paramString);
/*     */   
/*  97 */   public void setProgressMonitor(ProgressMonitor paramProgressMonitor) { this.m_progressMonitor = paramProgressMonitor; } public abstract int printChord(Chord paramChord, String paramString); public abstract int printChordSpaceAbove(); public abstract int printChordNewLine(); public abstract int printLyric(String paramString); public abstract int printComment(String paramString); public abstract int printGuitarComment(String paramString); public void setParagraphs(ArrayList<Paragraph> paramArrayList) {
/*  98 */     this.m_paragraphs = paramArrayList;
/*     */   }

	public void setTags(ArrayList<String> paramArrayList) {
/*  98 */     this.m_tags = paramArrayList;
/*     */   }
/*     */   public abstract int printNormalSpace();
/*     */   
/*     */   public abstract int markStartOfChorus();
/*     */   
/*     */   public abstract int markEndOfChorus();
/*     */   
/*     */   public abstract int markStartOfTab();
/*     */   
/*     */   public abstract int markEndOfTab();
/*     */   
/*     */   public abstract int markNewSong();
/*     */   
/*     */   public int printFinalChordGrid(Set<Chord> paramSet) {
/* 113 */     return 2;
/*     */   } public abstract int newColumn();
/*     */   public abstract int newPage();
/*     */   public abstract int newPhysicalPage();
/* 117 */   public int printKey(Chord paramChord) { return 2; }
/* 117 */   public int printTag(String paramChord) { return 2; }

/* 118 */   public int printArtist(String paramString) { return 2; } public int printCopyright(String paramString) {
/* 119 */     return 2;
/*     */   }
/*     */   
/*     */   public int error(String paramString) {
/* 123 */     if (SG02App.doPrintSongOutputErrors)
/* 124 */       System.err.println(getClass().getName() + " Warning: " + paramString); 
/* 125 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected class Paragraph
/*     */   {
/*     */     int m_nLineNumber;
/*     */     
/*     */     int m_nNumChordLines;
/*     */     int m_nNumLyricLines;
/*     */     
/*     */     public String toString() {
/* 137 */       return "chord lines: " + this.m_nNumChordLines + " lyric lines: " + this.m_nNumLyricLines;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */