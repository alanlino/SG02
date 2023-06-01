/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import java.util.ResourceBundle;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JEditorPane;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.SwingUtilities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SimpleHTMLDialog
/*    */   extends JDialog
/*    */ {
/*    */   public SimpleHTMLDialog(Component paramComponent, String paramString1, String paramString2) {
/* 27 */     super(SwingUtilities.getWindowAncestor(paramComponent));
/* 28 */     showDialog(paramString1, paramString2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SimpleHTMLDialog(String paramString1, String paramString2) {
/* 34 */     showDialog(paramString1, paramString2);
/* 35 */     setAlwaysOnTop(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public static JButton HelpButton(String paramString1, String paramString2) {
/* 40 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*    */     
/* 42 */     final JButton helpButton = new JButton();
/* 43 */     helpButton.setAction(new AbstractAction(resourceBundle
/* 44 */           .getString("Command.Help"))
/*    */         {
/*    */           public void actionPerformed(ActionEvent param1ActionEvent)
/*    */           {
/* 48 */             new SimpleHTMLDialog(helpButton, "Menu.Help.FormattingCodes", "FormattingCodes.html");
/*    */           }
/*    */         });
/*    */     
/* 52 */     return helpButton;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void showDialog(String paramString1, String paramString2) {
/* 57 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*    */     
/* 59 */     setTitle(resourceBundle.getString(paramString1));
/* 60 */     JPanel jPanel = new JPanel();
/* 61 */     jPanel.setLayout(new BoxLayout(jPanel, 1));
/* 62 */     jPanel.setBorder(BorderFactory.createEmptyBorder(8, 24, 20, 24));
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 67 */       String str = paramString2;
/* 68 */       URL uRL = SG02App.class.getResource(str);
/* 69 */       if (null == uRL) {
/*    */         
/* 71 */         File file = new File(System.getProperty("user.dir"), "../" + str);
/* 72 */         uRL = file.toURI().toURL();
/*    */       } 
/* 74 */       JEditorPane jEditorPane = new JEditorPane(uRL);
/* 75 */       jEditorPane.setEditable(false);
/* 76 */       JScrollPane jScrollPane = new JScrollPane(jEditorPane);
/* 77 */       jScrollPane.setVerticalScrollBarPolicy(22);
/* 78 */       jPanel.add(jScrollPane);
/*    */     }
/* 80 */     catch (Exception exception) {
/*    */       
/* 82 */       System.err.println(exception);
/*    */     } 
/* 84 */     add(jPanel);
/*    */     
/* 86 */     Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/* 87 */     dimension.height /= 3;
/* 88 */     dimension.width /= 3;
/* 89 */     setSize(dimension);
/*    */ 
/*    */ 
/*    */     
/* 93 */     setVisible(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SimpleHTMLDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */