/*     */ package com.tenbyten.SG02;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
import java.awt.Point;
/*     */ import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class AboutBox extends JDialog {
/*   9 */   protected static int aboutWidth = 280;
/*  10 */   protected static int aboutHeight = 230;
/*     */   protected Font titleFont;
/*     */   protected Font bodyFont;
/*     */   
/*     */   public AboutBox(JFrame paramJFrame) {
/*  15 */     super(paramJFrame);
/*     */     
/*  17 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */ 
/*     */     
/*  20 */     if (!SG02App.isMac) {
/*  21 */       setTitle(resourceBundle.getString("Title.Dialog.About"));
/*     */     }
/*     */     
/*  24 */     this.titleFont = new Font("Lucida Grande", 1, 14);
/*     */     
/*  26 */     if (this.titleFont == null) {
/*  27 */       this.titleFont = new Font(null, 1, 14);
/*     */     }
/*  29 */     this.bodyFont = new Font("Lucida Grande", 0, 10);
/*  30 */     if (this.bodyFont == null) {
/*  31 */       this.bodyFont = new Font(null, 0, 10);
/*     */     }
/*     */     
/*  34 */     JPanel jPanel = new JPanel();
/*  35 */     jPanel.setLayout(new BoxLayout(jPanel, 1));
/*  36 */     jPanel.setBorder(BorderFactory.createEmptyBorder(8, 24, 20, 24));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     ImageIcon imageIcon = SG02App.loadImageIcon("grid64.png");
/*     */     
/*  43 */     JLabel jLabel = new JLabel(imageIcon);
/*  44 */     setComponentAlignment(jLabel);
/*  45 */     jPanel.add(jLabel);
/*     */     
/*  47 */     jPanel.add(Box.createRigidArea(new Dimension(1, 12)));
/*     */     
/*  49 */     jLabel = new JLabel(resourceBundle.getString("Songsheet Generator"));
/*  50 */     jLabel.setFont(this.titleFont);
/*  51 */     setComponentAlignment(jLabel);
/*  52 */     jPanel.add(jLabel);
/*     */     
/*  54 */     jPanel.add(Box.createRigidArea(new Dimension(1, 8)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     Registration registration = new Registration();
/*  61 */     if (registration.isRegistered()) {
/*  62 */       jPanel.add(getBodyLabel(resourceBundle.getString("Text.Message.Registered")));
/*     */     } else {
/*  64 */       jPanel.add(getBodyLabel(resourceBundle.getString("Text.Message.Unregistered")));
/*     */     } 
/*  66 */     jPanel.add(Box.createRigidArea(new Dimension(1, 8)));
/*     */     
/*  68 */     jPanel.add(getBodyLabel(resourceBundle.getString("Version")));
/*     */     
/*  70 */     jPanel.add(getBodyLabel("JDK " + System.getProperty("java.version")));
/*  71 */     jPanel.add(Box.createRigidArea(new Dimension(1, 8)));
/*     */     
/*  73 */     jPanel.add(getBodyLabel(resourceBundle.getString("Text.About")));
/*  74 */     jPanel.add(Box.createRigidArea(new Dimension(1, 8)));
/*     */     
/*  76 */     jPanel.add(getBodyLabel(resourceBundle.getString("Copyright")));
/*     */     
/*  78 */     getContentPane().add(jPanel, "Center");
/*     */ 
/*     */     
/*  81 */     setSize(aboutWidth, aboutHeight);
/*  82 */     setResizable(false);
/*  83 */     pack();
/*     */     
/*  85 */     if (SG02App.isMac) {
/*     */ 
/*     */       
/*  88 */       Dimension dimension = getToolkit().getScreenSize();
/*  89 */       setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 3);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  94 */       Point point = paramJFrame.getLocation();
/*  95 */       setLocation(point.x + (paramJFrame.getWidth() - getWidth()) / 2, point.y + (paramJFrame.getHeight() - getHeight()) / 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setComponentAlignment(JLabel paramJLabel) {
/* 101 */     paramJLabel.setHorizontalAlignment(0);
/* 102 */     paramJLabel.setAlignmentX(0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   private JLabel getBodyLabel(String paramString) {
/* 107 */     JLabel jLabel = new JLabel(paramString);
/* 108 */     jLabel.setFont(this.bodyFont);
/* 109 */     setComponentAlignment(jLabel);
/* 110 */     return jLabel;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\AboutBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */