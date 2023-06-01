/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SongFileTOC
/*    */ {
/*    */   public int m_nPageNumber;
/*    */   public int m_nPageCount;
/*    */   public int m_nSongOfPage;
/*    */   public SongFile m_SongFile;
/*    */   public float m_songPrinter_yLoc;
/*    */   
/*    */   public SongFileTOC(SongFile paramSongFile) throws CloneNotSupportedException {
/* 17 */     this.m_SongFile = (SongFile)paramSongFile.clone();
/* 18 */     paginateInit();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paginateInit() {
/* 24 */     this.m_nPageNumber = 0;
/* 25 */     this.m_nPageCount = 0;
/* 26 */     this.m_nSongOfPage = 0;
/*    */     
/* 28 */     this.m_songPrinter_yLoc = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongFileTOC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */