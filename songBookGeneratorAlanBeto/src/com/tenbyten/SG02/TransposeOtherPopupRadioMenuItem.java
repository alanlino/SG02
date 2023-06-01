/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.util.ResourceBundle;
/*    */ import javax.swing.JOptionPane;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class TransposeOtherPopupRadioMenuItem
/*    */   extends JRadioButtonMenuItem
/*    */ {
/*    */   private PrintTable m_list;
/*    */   private ResourceBundle m_resources;
/*    */   
/*    */   TransposeOtherPopupRadioMenuItem(PrintTable paramPrintTable) {
/* 29 */     this.m_list = paramPrintTable;
/* 30 */     SongFile songFile = this.m_list.getSelectedValue();
/* 31 */     if (songFile != null) {
/* 32 */       setSelected((songFile.getTranspose() > 3 || songFile.getTranspose() < -3));
/*    */     }
/* 34 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 35 */     setText(this.m_resources.getString("Menu.Transpose.Other"));
/* 36 */     if (!SG02App.isMac) {
/* 37 */       setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.Other.Mn")).intValue());
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 42 */     super.fireActionPerformed(paramActionEvent);
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 47 */       String str = JOptionPane.showInputDialog(this, this.m_resources
/* 48 */           .getString("Text.Transpose"), this.m_resources
/* 49 */           .getString("Title.Dialog.Transpose"), -1);
/*    */ 
/*    */       
/* 52 */       if (null != str) {
/*    */         
/* 54 */         int i = Integer.parseInt(str.toString());
/* 55 */         SongFile songFile = this.m_list.getSelectedValue();
/* 56 */         if (songFile != null)
/*    */         {
/* 58 */           songFile.setTranspose(i);
/* 59 */           this.m_list.fireSongChanged(songFile);
/* 60 */           setSelected(true);
/*    */         }
/*    */       
/*    */       } 
/* 64 */     } catch (Exception exception) {
/*    */       
/* 66 */       System.err.println("Transpose Dialog: caught exception " + exception.toString());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\TransposeOtherPopupRadioMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */