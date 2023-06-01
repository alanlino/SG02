/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
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
/*     */ class FontSettingsPane
/*     */   extends JPanel
/*     */ {
/*     */   protected String m_propertyCategory;
/*     */   protected String m_copyFromPropertyCategory;
/*     */   protected Properties m_props;
/*     */   protected ResourceBundle m_resources;
/*     */   protected String[] m_strings;
/*     */   protected Font[] m_fonts;
/*     */   protected Color[] m_colors;
/*     */   protected Color m_colorBackground;
/*     */   protected JComboBox<String> m_comboFonts;
/*     */   protected FontsDisplay m_samples;
/*     */   private OSXFontChooser m_fontChooser;
/*     */   
/*     */   FontSettingsPane(String paramString1, String paramString2, Properties paramProperties, Color paramColor) {
/*  83 */     super(new GridBagLayout());
/*     */     
/*  85 */     this.m_propertyCategory = paramString1;
/*  86 */     this.m_copyFromPropertyCategory = paramString2;
/*  87 */     this.m_props = paramProperties;
/*  88 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */ 
/*     */     
/*  91 */     initStrings();
/*  92 */     initFonts(paramString1);
/*  93 */     initColors(paramString1, paramColor);
/*     */     
/*  95 */     Dimension dimension1 = (Dimension)this.m_resources.getObject("Control.Spacing");
/*  96 */     Dimension dimension2 = new Dimension(150, 100);
/*  97 */     Insets insets1 = new Insets(dimension1.height, dimension1.width, 0, 0);
/*  98 */     Insets insets2 = new Insets(dimension1.height, dimension1.width, 0, dimension1.width);
/*     */     
/* 100 */     FontSettingsPane fontSettingsPane = this;
/* 101 */     GridBagLayout gridBagLayout = (GridBagLayout)getLayout();
/* 102 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     JLabel jLabel = new JLabel(this.m_resources.getString("Label.FontSettings.Font"));
/* 108 */     gridBagConstraints.anchor = 17;
/* 109 */     gridBagConstraints.fill = 0;
/* 110 */     gridBagConstraints.weightx = 0.0D;
/* 111 */     gridBagConstraints.weighty = 0.0D;
/* 112 */     gridBagConstraints.gridx = 0;
/* 113 */     gridBagConstraints.gridy = 0;
/* 114 */     gridBagConstraints.gridwidth = 1;
/* 115 */     gridBagConstraints.insets = insets1;
/* 116 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 117 */     fontSettingsPane.add(jLabel);
/*     */     
/* 119 */     this.m_comboFonts = new JComboBox<String>(this.m_strings);
/* 120 */     this.m_comboFonts.setMaximumRowCount(20);
/* 121 */     this.m_comboFonts.setSelectedIndex(2);
/* 122 */     gridBagConstraints.fill = 2;
/* 123 */     gridBagConstraints.gridy++;
/* 124 */     gridBagLayout.setConstraints(this.m_comboFonts, gridBagConstraints);
/* 125 */     fontSettingsPane.add(this.m_comboFonts);
/*     */     
/* 127 */     final JButton setFontButton = new JButton();
/* 128 */     gridBagConstraints.fill = 0;
/* 129 */     gridBagConstraints.gridx = 1;
/* 130 */     gridBagLayout.setConstraints(setFontButton, gridBagConstraints);
/* 131 */     fontSettingsPane.add(setFontButton);
/* 132 */     setFontButton.setAction(new AbstractAction(this.m_resources
/* 133 */           .getString("Command.FontSettings.Font"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 137 */             if (null == FontSettingsPane.this.m_fontChooser) {
/* 138 */               FontSettingsPane.this.m_fontChooser = new OSXFontChooser(null, Color.BLACK, FontSettingsPane.this.m_colorBackground);
/*     */             }
/*     */             
/* 141 */             int i = FontSettingsPane.this.m_comboFonts.getSelectedIndex();
/*     */             
/* 143 */             FontSettingsPane.this.m_fontChooser.setSelectedFont(FontSettingsPane.this.m_fonts[i]);
/* 144 */             FontSettingsPane.this.m_fontChooser.setFgColor(FontSettingsPane.this.m_colors[i]);
/*     */             
/* 146 */             int j = FontSettingsPane.this.m_fontChooser.showOSXFontChooser(setFontButton);
/*     */             
/* 148 */             if (1 == j) {
/*     */ 
/*     */               
/* 151 */               FontSettingsPane.this.m_fonts[i] = FontSettingsPane.this.m_fontChooser.getSelectedFont();
/* 152 */               FontSettingsPane.this.m_colors[i] = FontSettingsPane.this.m_fontChooser.getFgColor();
/* 153 */               FontSettingsPane.this.m_samples.repaint();
/*     */             } 
/*     */             
/* 156 */             if (SG02App.isMac && FontSettingsPane.this.m_fontChooser.didShowColorChooser()) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 161 */               Window window = SwingUtilities.getWindowAncestor(setFontButton);
/* 162 */               if (null != window) {
/*     */                 
/* 164 */                 window.setVisible(false);
/* 165 */                 window.setVisible(true);
/* 166 */                 window.toFront();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     jLabel = new JLabel(this.m_resources.getString("Label.FontSettings.Adjust"));
/* 178 */     jLabel.setPreferredSize(dimension2);
/* 179 */     gridBagConstraints.fill = 1;
/* 180 */     gridBagConstraints.gridx = 0;
/* 181 */     gridBagConstraints.gridy++;
/* 182 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 183 */     fontSettingsPane.add(jLabel);
/*     */     
/* 185 */     JButton jButton2 = new JButton();
/* 186 */     gridBagConstraints.fill = 0;
/* 187 */     gridBagConstraints.gridx = 1;
/* 188 */     gridBagLayout.setConstraints(jButton2, gridBagConstraints);
/* 189 */     fontSettingsPane.add(jButton2);
/* 190 */     jButton2.setAction(new AbstractAction(this.m_resources
/* 191 */           .getString("Command.FontSettings.Adjust"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 195 */             FontSettingsPane.this.adjustFonts();
/* 196 */             FontSettingsPane.this.m_samples.repaint();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     jLabel = new JLabel(this.m_resources.getString("Label.FontSettings.Copy.Begin") + this.m_resources.getString("Label.FontSettings.Copy." + paramString2) + this.m_resources.getString("Label.FontSettings.Copy.End"));
/* 205 */     jLabel.setPreferredSize(dimension2);
/* 206 */     gridBagConstraints.fill = 1;
/* 207 */     gridBagConstraints.gridx = 0;
/* 208 */     gridBagConstraints.gridy++;
/* 209 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 210 */     fontSettingsPane.add(jLabel);
/*     */     
/* 212 */     jButton2 = new JButton();
/* 213 */     gridBagConstraints.fill = 0;
/* 214 */     gridBagConstraints.gridx = 1;
/* 215 */     gridBagLayout.setConstraints(jButton2, gridBagConstraints);
/* 216 */     fontSettingsPane.add(jButton2);
/* 217 */     jButton2.setAction(new AbstractAction(this.m_resources
/* 218 */           .getString("Command.FontSettings.Copy"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 222 */             FontSettingsPane.this.initFonts(FontSettingsPane.this.m_copyFromPropertyCategory);
/* 223 */             FontSettingsPane.this.m_samples.repaint();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 228 */     int i = gridBagConstraints.gridy;
/*     */ 
/*     */     
/* 231 */     this.m_samples = new FontsDisplay();
/* 232 */     gridBagConstraints.anchor = 10;
/* 233 */     gridBagConstraints.fill = 1;
/* 234 */     gridBagConstraints.weightx = 1.0D;
/* 235 */     gridBagConstraints.weighty = 1.0D;
/* 236 */     gridBagConstraints.gridx = 2;
/* 237 */     gridBagConstraints.gridy = 0;
/* 238 */     gridBagConstraints.gridwidth = 1;
/* 239 */     gridBagConstraints.gridheight = i + 1;
/* 240 */     gridBagConstraints.insets = insets2;
/* 241 */     gridBagLayout.setConstraints(this.m_samples, gridBagConstraints);
/* 242 */     fontSettingsPane.add(this.m_samples);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initStrings() {
/* 248 */     String str1 = this.m_resources.getString("Label.FontSettings.Title");
/* 249 */     String str2 = this.m_resources.getString("Label.FontSettings.Subtitle");
/* 250 */     String str3 = this.m_resources.getString("Label.FontSettings.Normal");
/* 251 */     String str4 = this.m_resources.getString("Label.FontSettings.Chord");
/* 252 */     String str5 = this.m_resources.getString("Label.FontSettings.Comment");
/* 253 */     String str6 = this.m_resources.getString("Label.FontSettings.Comment.Guitar");
/* 254 */     String str7 = this.m_resources.getString("Label.FontSettings.Grid");
/* 255 */     String str8 = this.m_resources.getString("Label.FontSettings.Tab");
/* 256 */     String str9 = this.m_resources.getString("Label.FontSettings.Footer");
/*     */     
/* 258 */     this.m_strings = new String[] { str1, str2, str3, str4, str5, str6, str7, str8, str9 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initColors(String paramString, Color paramColor) {
/* 264 */     if (null != paramColor) {
/* 265 */       this.m_colorBackground = paramColor;
/*     */     } else {
/* 267 */       this.m_colorBackground = Color.WHITE;
/*     */     } 
/* 269 */     Color color1 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.title"), 16));
/* 270 */     Color color2 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.subtitle"), 16));
/* 271 */     Color color3 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.normal"), 16));
/* 272 */     Color color4 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.chord"), 16));
/* 273 */     Color color5 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.comment"), 16));
/* 274 */     Color color6 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.comment.guitar"), 16));
/* 275 */     Color color7 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.grid"), 16));
/* 276 */     Color color8 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.tab"), 16));
/* 277 */     Color color9 = new Color(Integer.parseInt(this.m_props.getProperty(paramString + ".font.color.footer"), 16));
/*     */     
/* 279 */     this.m_colors = new Color[] { color1, color2, color3, color4, color5, color6, color7, color8, color9 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initFonts(String paramString) {
/* 285 */     Font font1 = deriveFont(paramString, "title");
/* 286 */     Font font2 = deriveFont(paramString, "subtitle");
/* 287 */     Font font3 = deriveFont(paramString, "normal");
/* 288 */     Font font4 = deriveFont(paramString, "chord");
/* 289 */     Font font5 = deriveFont(paramString, "comment");
/* 290 */     Font font6 = deriveFont(paramString, "comment.guitar");
/* 291 */     Font font7 = deriveFont(paramString, "grid");
/* 292 */     Font font8 = deriveFont(paramString, "tab");
/* 293 */     Font font9 = deriveFont(paramString, "footer");
/*     */     
/* 295 */     this.m_fonts = new Font[] { font1, font2, font3, font4, font5, font6, font7, font8, font9 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Font deriveFont(String paramString1, String paramString2) {
/* 301 */     Font font = null;
/*     */ 
/*     */     
/*     */     try {
/* 305 */       int i = Integer.parseInt(this.m_props.getProperty(paramString1 + ".font.size." + paramString2));
/* 306 */       int j = 0;
/*     */       
/* 308 */       if ('y' == this.m_props.getProperty(paramString1 + ".font.style." + paramString2 + ".bold").charAt(0))
/* 309 */         j |= 0x1; 
/* 310 */       if ('y' == this.m_props.getProperty(paramString1 + ".font.style." + paramString2 + ".italic").charAt(0)) {
/* 311 */         j |= 0x2;
/*     */       }
/* 313 */       font = new Font(this.m_props.getProperty(paramString1 + ".font.family." + paramString2), j, i);
/*     */     }
/* 315 */     catch (Exception exception) {
/*     */       
/* 317 */       System.err.println("FontSettingsDialog:deriveFont: unable to set font for " + paramString2 + ";\n" + exception.toString());
/*     */     } 
/*     */     
/* 320 */     return font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void adjustFonts() {
/* 328 */     float f = this.m_fonts[2].getSize2D();
/* 329 */     System.err.println("font normal Size2D=" + f + " Size=" + this.m_fonts[2].getSize());
/*     */     
/* 331 */     byte b = 0;
/* 332 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 1.5F);
/* 333 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 1.25F);
/* 334 */     b++;
/* 335 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 0.9F);
/* 336 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 0.9F);
/* 337 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 0.9F);
/* 338 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 0.7F);
/* 339 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 0.8F);
/* 340 */     this.m_fonts[b] = this.m_fonts[b++].deriveFont(f * 0.8F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProps() {
/* 349 */     byte b = 0;
/* 350 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.title", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 351 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.subtitle", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 352 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.normal", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 353 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.chord", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 354 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.comment", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 355 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.comment.guitar", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 356 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.grid", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 357 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.tab", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/* 358 */     this.m_props.setProperty(this.m_propertyCategory + ".font.color.footer", Integer.toHexString(this.m_colors[b++].getRGB() & 0xFFFFFF));
/*     */     
/* 360 */     b = 0;
/* 361 */     setFontProp("title", this.m_fonts[b++]);
/* 362 */     setFontProp("subtitle", this.m_fonts[b++]);
/* 363 */     setFontProp("normal", this.m_fonts[b++]);
/* 364 */     setFontProp("chord", this.m_fonts[b++]);
/* 365 */     setFontProp("comment", this.m_fonts[b++]);
/* 366 */     setFontProp("comment.guitar", this.m_fonts[b++]);
/* 367 */     setFontProp("grid", this.m_fonts[b++]);
/* 368 */     setFontProp("tab", this.m_fonts[b++]);
/* 369 */     setFontProp("footer", this.m_fonts[b++]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFontProp(String paramString, Font paramFont) {
/* 377 */     this.m_props.setProperty(this.m_propertyCategory + ".font.size." + paramString, String.valueOf(paramFont.getSize()));
/* 378 */     this.m_props.setProperty(this.m_propertyCategory + ".font.family." + paramString, paramFont.getFamily());
/* 379 */     this.m_props.setProperty(this.m_propertyCategory + ".font.style." + paramString + ".bold", paramFont.isBold() ? "yes" : "no");
/* 380 */     this.m_props.setProperty(this.m_propertyCategory + ".font.style." + paramString + ".italic", paramFont.isItalic() ? "yes" : "no");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected class FontsDisplay
/*     */     extends JComponent
/*     */   {
/* 388 */     private final int kSampleSpacing = 5;
/* 389 */     private final float kSampleWidthMultiplier = 1.5F;
/*     */     
/*     */     protected Dimension m_fontBounds;
/*     */     
/*     */     public Dimension getMinimumSize() {
/* 394 */       return getPreferredSize();
/*     */     }
/*     */ 
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 399 */       computeFontBounds(getGraphics());
/* 400 */       return this.m_fontBounds;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void computeFontBounds(Graphics param1Graphics) {
/* 405 */       if (null == this.m_fontBounds) {
/* 406 */         this.m_fontBounds = new Dimension();
/*     */       }
/* 408 */       this.m_fontBounds.setSize(0, 0);
/*     */ 
/*     */ 
/*     */       
/* 412 */       for (byte b = 0; b < FontSettingsPane.this.m_fonts.length; b++) {
/*     */         
/* 414 */         param1Graphics.setFont(FontSettingsPane.this.m_fonts[b]);
/* 415 */         FontMetrics fontMetrics = param1Graphics.getFontMetrics();
/* 416 */         Rectangle2D rectangle2D = fontMetrics.getStringBounds(FontSettingsPane.this.m_strings[b], param1Graphics);
/*     */         
/* 418 */         this.m_fontBounds.width = Math.max(this.m_fontBounds.width, (int)rectangle2D.getWidth());
/* 419 */         this.m_fontBounds.height = (int)(this.m_fontBounds.height + rectangle2D.getHeight());
/* 420 */         this.m_fontBounds.height += 5;
/*     */       } 
/*     */       
/* 423 */       this.m_fontBounds.width = (int)(this.m_fontBounds.width * 1.5F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/*     */       try {
/* 430 */         Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 431 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 432 */         graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
/*     */         
/* 434 */         Dimension dimension = getSize();
/*     */         
/* 436 */         param1Graphics.setColor(FontSettingsPane.this.m_colorBackground);
/* 437 */         param1Graphics.fillRect(0, 0, dimension.width, dimension.height);
/*     */         
/* 439 */         computeFontBounds(param1Graphics);
/*     */         
/* 441 */         int i = (dimension.height - this.m_fontBounds.height) / 2;
/*     */ 
/*     */ 
/*     */         
/* 445 */         for (byte b = 0; b < FontSettingsPane.this.m_fonts.length; b++)
/*     */         {
/* 447 */           param1Graphics.setFont(FontSettingsPane.this.m_fonts[b]);
/* 448 */           param1Graphics.setColor(FontSettingsPane.this.m_colors[b]);
/*     */           
/* 450 */           FontMetrics fontMetrics = param1Graphics.getFontMetrics();
/* 451 */           Rectangle2D rectangle2D = fontMetrics.getStringBounds(FontSettingsPane.this.m_strings[b], param1Graphics);
/*     */           
/* 453 */           i = (int)(i + rectangle2D.getHeight());
/*     */           
/* 455 */           param1Graphics.drawString(FontSettingsPane.this.m_strings[b], 
/* 456 */               (int)((dimension.width - rectangle2D.getWidth()) / 2.0D), i);
/*     */ 
/*     */           
/* 459 */           i += 5;
/*     */         }
/*     */       
/* 462 */       } catch (Exception exception) {
/*     */         
/* 464 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\FontSettingsPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */