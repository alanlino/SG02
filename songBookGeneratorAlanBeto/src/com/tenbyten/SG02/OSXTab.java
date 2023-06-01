/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.util.ResourceBundle;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPanel;
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
/*    */ public class OSXTab
/*    */   extends JPanel
/*    */ {
/*    */   protected Class<?> jcomponentClass;
/*    */   protected Class<?> jtextfieldClass;
/* 26 */   protected ResourceBundle m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*    */ 
/*    */ 
/*    */   
/*    */   public void setVisible(boolean paramBoolean) {
/* 31 */     if (SG02App.isMac && paramBoolean && !SG02App.isQuaqua) {
/*    */       
/*    */       try {
/*    */         
/* 35 */         this.jcomponentClass = Class.forName("javax.swing.JComponent");
/* 36 */         this.jtextfieldClass = Class.forName("javax.swing.JTextField");
/* 37 */         fixOSXBackground(this);
/*    */       }
/* 39 */       catch (ClassNotFoundException classNotFoundException) {}
/*    */     }
/* 41 */     super.setVisible(paramBoolean);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fixOSXBackground(JComponent paramJComponent) throws ClassNotFoundException {
/* 46 */     paramJComponent.setOpaque(false);
/* 47 */     Component[] arrayOfComponent = paramJComponent.getComponents();
/* 48 */     for (byte b = 0; b < arrayOfComponent.length; b++) {
/*    */       
/* 50 */       if (this.jcomponentClass.isInstance(arrayOfComponent[b]))
/*    */       {
/* 52 */         if (!this.jtextfieldClass.isInstance(arrayOfComponent[b]))
/*    */         {
/* 54 */           fixOSXBackground((JComponent)arrayOfComponent[b]);
/*    */         }
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension getControlSpacing() {
/* 62 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*    */     
/* 64 */     if (SG02App.isQuaqua) {
/* 65 */       return (Dimension)resourceBundle.getObject("Control.Spacing.Quaqua");
/*    */     }
/* 67 */     return (Dimension)resourceBundle.getObject("Control.Spacing");
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\OSXTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */