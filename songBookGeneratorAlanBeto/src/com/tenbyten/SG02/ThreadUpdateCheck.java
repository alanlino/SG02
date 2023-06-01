/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ThreadUpdateCheck
/*     */   extends Thread
/*     */ {
/*     */   private SG02Frame m_frame;
/*     */   
/*     */   public ThreadUpdateCheck(SG02Frame paramSG02Frame) {
/*  25 */     this.m_frame = paramSG02Frame;
/*  26 */     setDaemon(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  33 */       URL uRL = new URL("https://tenbyten.com/software/songsgen/updatecheck.php");
/*  34 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));
/*     */ 
/*     */       
/*  37 */       String str2 = "https://tenbyten.com/software/songsgen/";
/*     */       
/*  39 */       boolean bool = false;
/*     */       String str1;
/*  41 */       while (!bool && (str1 = bufferedReader.readLine()) != null) {
/*     */         
/*  43 */         if (SG02App.isDebug) {
/*  44 */           System.err.println(str1);
/*     */         }
/*  46 */         boolean bool1 = false;
/*  47 */         if (SG02App.isMac && str1.startsWith("macjava")) {
/*     */           
/*  49 */           bool1 = true;
/*     */         }
/*  51 */         else if (!SG02App.isMac && str1.startsWith("java")) {
/*     */           
/*  53 */           bool1 = true;
/*     */         } 
/*     */         
/*  56 */         if (bool1) {
/*     */           
/*  58 */           String[] arrayOfString = str1.split("\\|");
/*     */           
/*  60 */           if (arrayOfString.length >= 3) {
/*     */             
/*  62 */             String[] arrayOfString1 = arrayOfString[1].split("\\.");
/*  63 */             String[] arrayOfString2 = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle").getString("Version.Number").split("\\.");
/*     */             
/*  65 */             for (byte b = 0; !bool && b < arrayOfString1.length && b < arrayOfString2.length; b++) {
/*     */               
/*  67 */               int i = Integer.parseInt(arrayOfString1[b]);
/*  68 */               int j = Integer.parseInt(arrayOfString2[b]);
/*     */               
/*  70 */               if (i > j) {
/*     */                 
/*  72 */                 bool = true;
/*  73 */                 str2 = arrayOfString[2];
/*     */               }
/*  75 */               else if (i < j) {
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  85 */       bufferedReader.close();
/*     */       
/*  87 */       if (bool)
/*     */       {
/*  89 */         EventQueue.invokeLater(new UpdateNotifier(str2));
/*     */       }
/*     */     }
/*  92 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private final class UpdateNotifier
/*     */     implements Runnable
/*     */   {
/*     */     String m_url;
/*     */ 
/*     */     
/*     */     public UpdateNotifier(String param1String) {
/* 103 */       this.m_url = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 108 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */       
/* 110 */       JOptionPane jOptionPane = new JOptionPane(resourceBundle.getString("Text.Message.UpdateCheck.New"), 3, 0);
/*     */ 
/*     */       
/* 113 */       JDialog jDialog = jOptionPane.createDialog(ThreadUpdateCheck.this.m_frame, resourceBundle.getString("Title.Dialog.Confirm"));
/*     */       
/* 115 */       GenericPropertyAction genericPropertyAction = new GenericPropertyAction(SG02App.props, "reminder.update");
/* 116 */       StringPropertyCheckBox stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "no", "yes");
/* 117 */       stringPropertyCheckBox.setText(resourceBundle.getString("Text.Message.UpdateCheck.Stop"));
/*     */       
/* 119 */       JPanel jPanel = new JPanel();
/* 120 */       BoxLayout boxLayout = new BoxLayout(jPanel, 1);
/* 121 */       jPanel.setLayout(boxLayout);
/* 122 */       jPanel.add(jDialog.getContentPane());
/* 123 */       jPanel.add(stringPropertyCheckBox);
/* 124 */       jPanel.add(new JLabel("     "));
/* 125 */       stringPropertyCheckBox.setAlignmentX(boxLayout.getLayoutAlignmentX(jPanel));
/*     */       
/* 127 */       jDialog.setContentPane(jPanel);
/* 128 */       jDialog.pack();
/* 129 */       jDialog.setVisible(true);
/*     */       
/* 131 */       if (0 == String.valueOf(0).compareTo(String.valueOf(jOptionPane.getValue()))) {
/*     */         
/* 133 */         LaunchURLAction launchURLAction = new LaunchURLAction(this.m_url);
/* 134 */         launchURLAction.actionPerformed(null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\ThreadUpdateCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */