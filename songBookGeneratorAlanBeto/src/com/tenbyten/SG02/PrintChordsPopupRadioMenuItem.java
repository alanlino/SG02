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
/*    */ class PrintChordsPopupRadioMenuItem
/*    */   extends JRadioButtonMenuItem
/*    */ {
/*    */   private PrintTable m_list;
/*    */   
/*    */   PrintChordsPopupRadioMenuItem(PrintTable paramPrintTable) {
/* 17 */     this.m_list = paramPrintTable;
/* 18 */     setSelected(this.m_list.getSelectedValue().getPrintChords());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 23 */     super.fireActionPerformed(paramActionEvent);
/* 24 */     SongFile songFile = this.m_list.getSelectedValue();
/* 25 */     songFile.setPrintChords(isSelected());
/* 26 */     this.m_list.fireSongChanged(songFile);
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\PrintChordsPopupRadioMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */