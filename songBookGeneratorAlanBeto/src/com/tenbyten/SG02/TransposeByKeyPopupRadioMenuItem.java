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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TransposeByKeyPopupRadioMenuItem
/*    */   extends JRadioButtonMenuItem
/*    */ {
/*    */   private Chord m_keySignature;
/*    */   private PrintTable m_list;
/*    */   
/*    */   TransposeByKeyPopupRadioMenuItem(PrintTable paramPrintTable, Chord paramChord) {
/* 24 */     super(paramChord.getName());
/* 25 */     this.m_list = paramPrintTable;
/* 26 */     this.m_keySignature = paramChord;
/* 27 */     setSelected((this.m_list.getSelectedValue().getKeySignature() == this.m_keySignature));
/*    */     
/* 29 */     boolean bool = ('y' == SG02App.props.getProperty("print.chords.doremi").charAt(0)) ? true : false;
/* 30 */     if (bool)
/*    */     {
/* 32 */       setText(paramChord.getDoReMiName());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 38 */     super.fireActionPerformed(paramActionEvent);
/* 39 */     SongFile songFile = this.m_list.getSelectedValue();
/* 40 */     songFile.setTranspose(this.m_keySignature);
/* 41 */     this.m_list.fireSongChanged(songFile);
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\TransposeByKeyPopupRadioMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */