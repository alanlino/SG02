/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.JRadioButtonMenuItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TransposePopupRadioMenuItem
/*    */   extends JRadioButtonMenuItem
/*    */ {
/*    */   private int m_transpose;
/*    */   private PrintTable m_list;
/*    */   
/*    */   TransposePopupRadioMenuItem(PrintTable paramPrintTable, int paramInt) {
/* 18 */     this.m_list = paramPrintTable;
/* 19 */     this.m_transpose = paramInt;
/* 20 */     SongFile songFile = this.m_list.getSelectedValue();
/* 21 */     if (songFile != null) {
/* 22 */       setSelected((paramInt == this.m_list.getSelectedValue().getRawTranspose()));
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 27 */     super.fireActionPerformed(paramActionEvent);
/* 28 */     SongFile songFile = this.m_list.getSelectedValue();
/* 29 */     if (songFile != null) {
/*    */       
/* 31 */       songFile.setTranspose(this.m_transpose);
/* 32 */       this.m_list.fireSongChanged(songFile);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\TransposePopupRadioMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */