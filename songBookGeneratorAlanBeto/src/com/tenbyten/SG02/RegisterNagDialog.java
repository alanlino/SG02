/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ class RegisterNagDialog
/*     */   implements Runnable
/*     */ {
/*     */   private SG02Frame m_frame;
/*     */   
/*     */   public RegisterNagDialog(SG02Frame paramSG02Frame) {
/*  14 */     this.m_frame = paramSG02Frame;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  20 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */ 
/*     */ 
/*     */     
/*  24 */     Object[] arrayOfObject = { resourceBundle.getString("Command.Register.Donate"), resourceBundle.getString("Command.Register.EnterCode"), resourceBundle.getString("Command.Register.JustRun") };
/*     */     
/*  26 */     Object object = UIManager.get("OptionPane.messageLabelWidth");
/*  27 */     if (SG02App.isQuaqua) {
/*  28 */       UIManager.put("OptionPane.messageLabelWidth", new Integer(480));
/*     */     }
/*  30 */     int i = JOptionPane.showOptionDialog(this.m_frame, resourceBundle
/*  31 */         .getString("Text.Message.Register.Nag"), resourceBundle
/*  32 */         .getString("Title.Dialog.Register.Nag"), 1, -1, null, arrayOfObject, arrayOfObject[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     switch (i) {
/*     */       
/*     */       case 0:
/*  42 */         donateAndRegister();
/*     */         break;
/*     */       case 1:
/*  45 */         enterRegistrationCode();
/*     */         break;
/*     */     } 
/*     */     
/*  49 */     if (SG02App.isQuaqua) {
/*  50 */       UIManager.put("OptionPane.messageLabelWidth", object);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void donateAndRegister() {
/*  56 */     LaunchURLAction launchURLAction = new LaunchURLAction("https://www.tenbyten.com/software/songsgen/register.html");
/*  57 */     launchURLAction.actionPerformed(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enterRegistrationCode() {
/*  63 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */     
/*  65 */     boolean bool = true;
/*     */ 
/*     */     
/*  68 */     Registration registration = new Registration();
/*  69 */     if (registration.isRegistered()) {
/*     */       
/*  71 */       int i = JOptionPane.showConfirmDialog(null, resourceBundle
/*  72 */           .getString("Text.Message.Register.Already"), resourceBundle
/*  73 */           .getString("Title.Dialog.Register.EnterCode"), 0, 2, null);
/*     */ 
/*     */ 
/*     */       
/*  77 */       if (0 != i) {
/*  78 */         bool = false;
/*     */       }
/*     */     } 
/*  81 */     if (bool) {
/*     */       
/*  83 */       String str = JOptionPane.showInputDialog(null, resourceBundle
/*  84 */           .getString("Text.Message.Register.EnterCode"), resourceBundle
/*  85 */           .getString("Title.Dialog.Register.EnterCode"), -1);
/*     */ 
/*     */       
/*  88 */       if (null != str && 0 < str.length()) {
/*     */         
/*  90 */         registration.setCode(str);
/*     */         
/*  92 */         if (!registration.isRegistered()) {
/*     */           
/*  94 */           int i = JOptionPane.showConfirmDialog(null, resourceBundle
/*  95 */               .getString("Text.Message.Register.Error"), resourceBundle
/*  96 */               .getString("Title.Dialog.Register.EnterCode"), 0, 2, null);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 101 */           if (0 == i)
/*     */           {
/*     */             
/* 104 */             enterRegistrationCode();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\RegisterNagDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */