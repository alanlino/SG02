/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SongPrinterFS
/*    */   extends SongPrinter
/*    */ {
/*    */   protected boolean m_bBlankFirst;
/*    */   protected boolean m_bBlankLast;
/*    */   protected boolean m_bBlankBetween;
/*    */   
/*    */   public SongPrinterFS() {
/* 22 */     overrideCenterTitleOnPage(true);
/* 23 */     overridePrintTOC(false);
/* 24 */     overrideSongsPerPage(Integer.parseInt(this.m_props.getProperty("fullscreen.songs.per.page")));
/* 25 */     override2ndColumnSpacing(false);
/* 26 */     overrideAutoNumberSongs(false);
/* 27 */     overrideTabCharWidth(0.0F);
/*    */     
/* 29 */     this.m_bBlankFirst = ('y' == SG02App.props.getProperty("fullscreen.blank.first").charAt(0));
/* 30 */     this.m_bBlankLast = ('y' == SG02App.props.getProperty("fullscreen.blank.last").charAt(0));
/* 31 */     this.m_bBlankBetween = ('y' == SG02App.props.getProperty("fullscreen.blank.between").charAt(0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getPropertyCategory() {
/* 39 */     return "fullscreen";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void startSong() {
/*    */     try {
/* 47 */       overrideTabCharWidth(Float.parseFloat(SG02App.props.getProperty("fullscreen.chars.tab.percent")) * (float)this.m_pageFormat.getImageableWidth() / 100.0F);
/*    */     }
/* 49 */     catch (Exception exception) {
/*    */       
/* 51 */       System.err.println("exception: startSong(): " + exception.toString());
/*    */     } 
/*    */ 
/*    */     
/* 55 */     if (this.m_nCurrentPage == 1 && this.m_bBlankFirst) {
/*    */       
/* 57 */       startPage();
/* 58 */       endPage();
/*    */     } 
/*    */     
/* 61 */     super.startSong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void endSong() {
/* 69 */     this.m_nSongOfPage += 100;
/* 70 */     super.endSong();
/*    */ 
/*    */     
/* 73 */     if (this.m_bBlankBetween && this.m_CurrentSong != this.m_qSongFiles.lastElement()) {
/*    */       
/* 75 */       startPage();
/* 76 */       endPage();
/*    */     } 
/*    */ 
/*    */     
/* 80 */     if (this.m_bBlankLast && this.m_CurrentSong == this.m_qSongFiles.lastElement()) {
/*    */       
/* 82 */       startPage();
/* 83 */       endPage();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongPrinterFS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */