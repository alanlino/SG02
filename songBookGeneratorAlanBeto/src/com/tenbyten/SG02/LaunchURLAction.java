/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import com.apple.eio.FileManager;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.io.IOException;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.JFileChooser;
/*    */ 
/*    */ public class LaunchURLAction
/*    */   extends AbstractAction
/*    */ {
/*    */   String m_url;
/*    */   
/*    */   public LaunchURLAction(String paramString) {
/* 15 */     this.m_url = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionPerformed(ActionEvent paramActionEvent) {
/*    */     try {
/* 22 */       String str = System.getProperty("os.name").toLowerCase();
/* 23 */       if (str.indexOf("mac") != -1) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 28 */         FileManager.openURL(this.m_url);
/*    */         
/*    */         return;
/*    */       } 
/*    */       
/* 33 */       if (str.indexOf("windows") != -1) {
/*    */         
/* 35 */         String str1 = "cmd /c start " + this.m_url;
/* 36 */         Runtime.getRuntime().exec(str1);
/*    */         
/*    */         return;
/*    */       } 
/* 40 */     } catch (IOException iOException) {
/*    */ 
/*    */     
/*    */     }
/* 44 */     catch (NoSuchMethodError noSuchMethodError) {
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 49 */     catch (NoClassDefFoundError noClassDefFoundError) {}
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
/* 62 */     JFileChooser jFileChooser = new JFileChooser();
/*    */     
/* 64 */     if (0 == jFileChooser.showOpenDialog(null))
/*    */     {
/* 66 */       if (jFileChooser.getSelectedFile() != null) {
/*    */         
/* 68 */         String str = jFileChooser.getSelectedFile().toString();
/*    */ 
/*    */ 
/*    */         
/*    */         try {
/* 73 */           Runtime.getRuntime().exec(new String[] { str, this.m_url });
/*    */         }
/* 75 */         catch (IOException iOException) {
/*    */           
/* 77 */           iOException.printStackTrace();
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\LaunchURLAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */