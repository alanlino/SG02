/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.util.Properties;
/*    */ import java.util.ResourceBundle;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class FontSettingsDialog
/*    */ {
/*    */   public static void showFontSettingsDialog(Component paramComponent, String paramString1, String paramString2, Properties paramProperties, Color paramColor) {
/* 18 */     FontSettingsPane fontSettingsPane = new FontSettingsPane(paramString1, paramString2, paramProperties, paramColor);
/* 19 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*    */     
/* 21 */     int i = JOptionPane.showOptionDialog(paramComponent, fontSettingsPane, resourceBundle
/*    */ 
/*    */         
/* 24 */         .getString("Title.Dialog.FontSettings." + paramString1), 2, -1, null, null, null);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     if (0 == i)
/*    */     {
/* 34 */       fontSettingsPane.setProps();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void adjustFonts(Properties paramProperties, String paramString, int paramInt) {
/* 42 */     adjustFont(paramProperties, paramString, "title", paramInt);
/* 43 */     adjustFont(paramProperties, paramString, "subtitle", paramInt);
/* 44 */     adjustFont(paramProperties, paramString, "normal", paramInt);
/* 45 */     adjustFont(paramProperties, paramString, "chord", paramInt);
/* 46 */     adjustFont(paramProperties, paramString, "comment", paramInt);
/* 47 */     adjustFont(paramProperties, paramString, "comment.guitar", paramInt);
/* 48 */     adjustFont(paramProperties, paramString, "grid", paramInt);
/* 49 */     adjustFont(paramProperties, paramString, "tab", paramInt);
/* 50 */     adjustFont(paramProperties, paramString, "footer", paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void adjustFont(Properties paramProperties, String paramString1, String paramString2, int paramInt) {
/* 55 */     int i = Integer.parseInt(paramProperties.getProperty(paramString1 + ".font.size." + paramString2));
/* 56 */     i += paramInt;
/* 57 */     paramProperties.setProperty(paramString1 + ".font.size." + paramString2, String.valueOf(i));
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\FontSettingsDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */