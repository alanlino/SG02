/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OSXFontChooser
/*     */   extends JPanel
/*     */   implements KeyListener
/*     */ {
/*     */   protected TextSample m_sampleText;
/*     */   protected String[] m_fontFamilies;
/*     */   protected JList<String> m_listFontFamilies;
/*     */   protected JCheckBox m_checkboxItalic;
/*     */   protected JCheckBox m_checkboxBold;
/*     */   protected JTextField m_textfieldSize;
/*     */   protected JList<String> m_listFontSizes;
/*     */   
/*     */   public int showOSXFontChooser(Component paramComponent) {
/*  50 */     this.m_colorChooserShown = false;
/*     */     
/*  52 */     int i = JOptionPane.showOptionDialog(paramComponent, this, this.m_resources
/*     */ 
/*     */         
/*  55 */         .getString("Title.Dialog.OSXFontChooser"), 2, -1, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (0 == i)
/*     */     {
/*  65 */       return 1;
/*     */     }
/*     */     
/*  68 */     return 0;
/*     */   }
/*     */   protected Font m_font; protected Color m_bgColor; protected Color m_fgColor; private boolean m_colorChooserShown; private ResourceBundle m_resources; static final int OK_OPTION = 1;
/*     */   static final int CANCEL_OPTION = 0;
/*     */   
/*     */   public OSXFontChooser(Font paramFont, Color paramColor1, Color paramColor2) {
/*  74 */     super(new GridBagLayout());
/*     */     
/*  76 */     this.m_font = paramFont;
/*  77 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */     
/*  79 */     if (null != paramColor1) {
/*  80 */       this.m_fgColor = paramColor1;
/*     */     } else {
/*  82 */       this.m_fgColor = Color.BLACK;
/*     */     } 
/*  84 */     if (null != paramColor2) {
/*  85 */       this.m_bgColor = paramColor2;
/*     */     } else {
/*  87 */       this.m_bgColor = Color.WHITE;
/*     */     } 
/*  89 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  90 */     Dimension dimension1 = (Dimension)this.m_resources.getObject("Control.Spacing");
/*  91 */     Dimension dimension2 = new Dimension(150, 150);
/*  92 */     Dimension dimension3 = new Dimension(75, 150);
/*  93 */     Dimension dimension4 = new Dimension(300, 100);
/*  94 */     Insets insets = new Insets(1, 1, 1, 1);
/*     */     
/*  96 */     OSXFontChooser oSXFontChooser = this;
/*  97 */     GridBagLayout gridBagLayout = (GridBagLayout)getLayout();
/*  98 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */ 
/*     */     
/* 101 */     this.m_sampleText = new TextSample();
/* 102 */     this.m_sampleText.setPreferredSize(dimension4);
/* 103 */     gridBagConstraints.anchor = 10;
/* 104 */     gridBagConstraints.fill = 1;
/* 105 */     gridBagConstraints.weightx = 1.0D;
/* 106 */     gridBagConstraints.weighty = 1.0D;
/* 107 */     gridBagConstraints.gridx = 0;
/* 108 */     gridBagConstraints.gridy = 0;
/* 109 */     gridBagConstraints.gridwidth = 3;
/* 110 */     gridBagConstraints.insets = new Insets(0, 0, dimension1.height, 0);
/* 111 */     gridBagLayout.setConstraints(this.m_sampleText, gridBagConstraints);
/* 112 */     oSXFontChooser.add(this.m_sampleText);
/*     */ 
/*     */     
/* 115 */     JLabel jLabel = new JLabel(this.m_resources.getString("Label.OSXFontChooser.Family"));
/* 116 */     gridBagConstraints.anchor = 17;
/* 117 */     gridBagConstraints.fill = 0;
/* 118 */     gridBagConstraints.weightx = 0.0D;
/* 119 */     gridBagConstraints.weighty = 0.0D;
/* 120 */     gridBagConstraints.gridx = 0;
/* 121 */     gridBagConstraints.gridy = 1;
/* 122 */     gridBagConstraints.gridwidth = 1;
/* 123 */     gridBagConstraints.insets = insets;
/* 124 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 125 */     oSXFontChooser.add(jLabel);
/*     */     
/* 127 */     jLabel = new JLabel(this.m_resources.getString("Label.OSXFontChooser.Style"));
/* 128 */     gridBagConstraints.gridx = 1;
/* 129 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 130 */     oSXFontChooser.add(jLabel);
/*     */     
/* 132 */     jLabel = new JLabel(this.m_resources.getString("Label.OSXFontChooser.Size"));
/* 133 */     gridBagConstraints.gridx = 2;
/* 134 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 135 */     oSXFontChooser.add(jLabel);
/*     */     
/* 137 */     this.m_fontFamilies = graphicsEnvironment.getAvailableFontFamilyNames();
/* 138 */     this.m_listFontFamilies = new JList<String>(this.m_fontFamilies);
/* 139 */     this.m_listFontFamilies.setSelectionMode(0);
/* 140 */     JScrollPane jScrollPane = new JScrollPane(this.m_listFontFamilies);
/* 141 */     jScrollPane.setAlignmentX(0.0F);
/* 142 */     jScrollPane.setPreferredSize(dimension2);
/* 143 */     gridBagConstraints.anchor = 17;
/* 144 */     gridBagConstraints.fill = 1;
/* 145 */     gridBagConstraints.weightx = 1.0D;
/* 146 */     gridBagConstraints.weighty = 0.8D;
/* 147 */     gridBagConstraints.gridx = 0;
/* 148 */     gridBagConstraints.gridy = 2;
/* 149 */     gridBagConstraints.gridwidth = 1;
/* 150 */     gridBagConstraints.insets = insets;
/* 151 */     gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
/* 152 */     oSXFontChooser.add(jScrollPane);
/*     */     
/* 154 */     Box box1 = Box.createVerticalBox();
/* 155 */     this.m_checkboxItalic = new JCheckBox();
/* 156 */     this.m_checkboxBold = new JCheckBox();
/* 157 */     final JButton fgButton = new JButton();
/* 158 */     box1.add(this.m_checkboxItalic);
/* 159 */     box1.add(this.m_checkboxBold);
/* 160 */     box1.add(fgButton);
/* 161 */     gridBagConstraints.anchor = 17;
/* 162 */     gridBagConstraints.fill = 1;
/* 163 */     gridBagConstraints.weightx = 0.5D;
/* 164 */     gridBagConstraints.weighty = 0.0D;
/* 165 */     gridBagConstraints.gridx = 1;
/* 166 */     gridBagConstraints.gridy = 2;
/* 167 */     gridBagConstraints.gridwidth = 1;
/* 168 */     gridBagConstraints.insets = insets;
/* 169 */     gridBagLayout.setConstraints(box1, gridBagConstraints);
/* 170 */     oSXFontChooser.add(box1);
/*     */     
/* 172 */     Box box2 = Box.createVerticalBox();
/* 173 */     this.m_textfieldSize = new JTextField();
/* 174 */     this.m_textfieldSize.setColumns(6);
/* 175 */     this.m_textfieldSize.setText("12");
/* 176 */     String[] arrayOfString = { "8", "9", "10", "11", "12", "14", "16", "18", "24", "36", "48", "72" };
/* 177 */     this.m_listFontSizes = new JList<String>(arrayOfString);
/* 178 */     this.m_listFontSizes.setSelectionMode(0);
/* 179 */     jScrollPane = new JScrollPane(this.m_listFontSizes);
/* 180 */     jScrollPane.setPreferredSize(dimension3);
/* 181 */     box2.add(this.m_textfieldSize);
/* 182 */     box2.add(jScrollPane);
/* 183 */     gridBagConstraints.anchor = 17;
/* 184 */     gridBagConstraints.fill = 1;
/* 185 */     gridBagConstraints.weightx = 0.5D;
/* 186 */     gridBagConstraints.weighty = 0.0D;
/* 187 */     gridBagConstraints.gridx = 2;
/* 188 */     gridBagConstraints.gridy = 2;
/* 189 */     gridBagConstraints.gridwidth = 1;
/* 190 */     gridBagConstraints.insets = insets;
/* 191 */     gridBagLayout.setConstraints(box2, gridBagConstraints);
/* 192 */     oSXFontChooser.add(box2);
/*     */ 
/*     */     
/* 195 */     this.m_textfieldSize.addKeyListener(this);
/*     */     
/* 197 */     this.m_listFontSizes.addListSelectionListener(new ListSelectionListener()
/*     */         {
/*     */           public void valueChanged(ListSelectionEvent param1ListSelectionEvent)
/*     */           {
/* 201 */             if (param1ListSelectionEvent.getSource() instanceof JList) {
/*     */               
/* 203 */               String str = OSXFontChooser.this.m_listFontSizes.getSelectedValue();
/* 204 */               if (null != str) {
/*     */                 
/* 206 */                 OSXFontChooser.this.m_textfieldSize.setText(str);
/* 207 */                 OSXFontChooser.this.m_sampleText.repaint();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 214 */     this.m_listFontFamilies.addListSelectionListener(new ListSelectionListener()
/*     */         {
/*     */           public void valueChanged(ListSelectionEvent param1ListSelectionEvent)
/*     */           {
/* 218 */             if (param1ListSelectionEvent.getSource() instanceof JList)
/*     */             {
/* 220 */               OSXFontChooser.this.m_sampleText.repaint();
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 226 */     if (!SG02App.isMac)
/* 227 */       this.m_checkboxBold.setMnemonic(((Integer)this.m_resources.getObject("Command.OSXFontChooser.Bold.Mn")).intValue()); 
/* 228 */     this.m_checkboxBold.setAction(new AbstractAction(this.m_resources
/* 229 */           .getString("Command.OSXFontChooser.Bold"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 233 */             OSXFontChooser.this.m_sampleText.repaint();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 238 */     if (!SG02App.isMac)
/* 239 */       this.m_checkboxItalic.setMnemonic(((Integer)this.m_resources.getObject("Command.OSXFontChooser.Italic.Mn")).intValue()); 
/* 240 */     this.m_checkboxItalic.setAction(new AbstractAction(this.m_resources
/* 241 */           .getString("Command.OSXFontChooser.Italic"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 245 */             OSXFontChooser.this.m_sampleText.repaint();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 250 */     if (!SG02App.isMac)
/* 251 */       fgButton.setMnemonic(((Integer)this.m_resources.getObject("Command.OSXFontChooser.Color.Mn")).intValue()); 
/* 252 */     fgButton.setAction(new AbstractAction(this.m_resources
/* 253 */           .getString("Command.OSXFontChooser.Color"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 257 */             Color color = JColorChooser.showDialog(fgButton, OSXFontChooser.this.m_resources.getString("Command.OSXFontChooser.Color"), OSXFontChooser.this.m_fgColor);
/* 258 */             if (null != color)
/* 259 */               OSXFontChooser.this.m_fgColor = color; 
/* 260 */             OSXFontChooser.this.m_sampleText.repaint();
/*     */             
/* 262 */             if (SG02App.isMac) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 267 */               Window window = SwingUtilities.getWindowAncestor(fgButton);
/* 268 */               if (null != window) {
/*     */                 
/* 270 */                 window.setVisible(false);
/* 271 */                 window.setVisible(true);
/* 272 */                 window.toFront();
/*     */               } 
/*     */               
/* 275 */               OSXFontChooser.this.m_colorChooserShown = true;
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 282 */     setSelectedFont(paramFont);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyEvent paramKeyEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyEvent paramKeyEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyEvent paramKeyEvent) {
/* 296 */     if (paramKeyEvent.getSource() == this.m_textfieldSize) {
/*     */       
/* 298 */       this.m_sampleText.repaint();
/* 299 */       this.m_listFontSizes.clearSelection();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected class TextSample
/*     */     extends JComponent
/*     */   {
/* 307 */     String m_sampleText = new String("Sample");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/*     */       try {
/* 326 */         Dimension dimension = getSize();
/*     */         
/* 328 */         param1Graphics.setColor(OSXFontChooser.this.m_bgColor);
/* 329 */         param1Graphics.fillRect(0, 0, dimension.width, dimension.height);
/*     */ 
/*     */         
/*     */         try {
/* 333 */           float f = Float.parseFloat(OSXFontChooser.this.m_textfieldSize.getText());
/*     */           
/* 335 */           OSXFontChooser.this
/* 336 */             .m_font = new Font(OSXFontChooser.this.m_listFontFamilies.getSelectedValue(), (OSXFontChooser.this.m_checkboxItalic.isSelected() ? 2 : 0) + (OSXFontChooser.this.m_checkboxBold.isSelected() ? 1 : 0), (int)f);
/*     */           
/* 338 */           if (f != (int)f)
/*     */           {
/* 340 */             OSXFontChooser.this.m_font = OSXFontChooser.this.m_font.deriveFont(f);
/*     */           }
/*     */ 
/*     */           
/* 344 */           param1Graphics.setFont(OSXFontChooser.this.m_font);
/*     */         }
/* 346 */         catch (Exception exception) {
/*     */           
/* 348 */           System.err.println("OSXFontChooser:TextSample: unable to set font;\n" + exception.toString());
/*     */         } 
/*     */         
/* 351 */         FontMetrics fontMetrics = param1Graphics.getFontMetrics();
/* 352 */         int i = fontMetrics.getAscent();
/* 353 */         Rectangle2D rectangle2D = fontMetrics.getStringBounds(this.m_sampleText, param1Graphics);
/*     */         
/* 355 */         param1Graphics.setColor(OSXFontChooser.this.m_fgColor);
/* 356 */         param1Graphics.drawString(this.m_sampleText, 
/* 357 */             (int)((dimension.width - rectangle2D.getWidth()) / 2.0D), 
/* 358 */             (int)((dimension.height - rectangle2D.getHeight()) / 2.0D) + i);
/*     */       }
/* 360 */       catch (Exception exception) {
/*     */         
/* 362 */         System.err.println("OSXFontChooser:TextSample: unable to paint;\n" + exception.toString());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedFont(Font paramFont) {
/* 371 */     this.m_font = paramFont;
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (null != paramFont) {
/*     */       
/* 377 */       this.m_checkboxBold.setSelected(paramFont.isBold());
/* 378 */       this.m_checkboxItalic.setSelected(paramFont.isItalic());
/* 379 */       this.m_listFontFamilies.setSelectedValue(paramFont.getFamily(), true);
/*     */       
/* 381 */       this.m_textfieldSize.setText(String.valueOf(paramFont.getSize()));
/*     */ 
/*     */       
/* 384 */       this.m_listFontSizes.setSelectedValue(String.valueOf(paramFont.getSize()), true);
/*     */     } 
/*     */     
/* 387 */     this.m_sampleText.repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setFgColor(Color paramColor) {
/* 393 */     this.m_fgColor = paramColor;
/* 394 */     this.m_sampleText.repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setBgColor(Color paramColor) {
/* 400 */     this.m_bgColor = paramColor;
/* 401 */     this.m_sampleText.repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getSelectedFont() {
/* 407 */     return this.m_font;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getFgColor() {
/* 413 */     return this.m_fgColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean didShowColorChooser() {
/* 419 */     return this.m_colorChooserShown;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\OSXFontChooser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */