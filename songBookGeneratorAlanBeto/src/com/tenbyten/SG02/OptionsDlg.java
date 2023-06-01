/*      */ package com.tenbyten.SG02;
import java.awt.BorderLayout;
/*      */ import java.awt.Color;
import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*      */ import java.io.File;
/*      */ import java.text.DecimalFormat;
import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
import java.util.ResourceBundle;

/*      */ import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JColorChooser;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
import javax.swing.JList;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ 
/*      */ class OptionsDlg {
/*      */   SG02Frame m_frame;
/*      */   GeneralTab m_tabGeneral;
/*      */   ChordsTab m_tabChords;
/*      */   PrinterTab m_tabPrinter;
/*      */   
/*      */   public OptionsDlg(SG02Frame paramSG02Frame, int paramInt) {
/*   43 */     this.m_frame = paramSG02Frame;
/*   44 */     this.m_props = (Properties)SG02App.props.clone();
/*      */     
/*   46 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*      */     
/*   48 */     this.m_rescanSongs = false;
/*      */ 
/*      */     
/*   51 */     this.m_tabGeneral = new GeneralTab();
/*   52 */     this.m_tabChords = new ChordsTab();
/*   53 */     this.m_tabPrinter = new PrinterTab();
/*   54 */     this.m_tabHTML = new HTMLTab();
/*   55 */     this.m_tabFullScreen = new FullScreenTab();
/*   56 */     this.m_tabDataValues = new DataValuesTab();
/*      */     
/*   58 */     this.m_paneTabs = new JTabbedPane();
/*   59 */     this.m_paneTabs.addTab(this.m_resources.getString("Title.Tab.General"), this.m_tabGeneral);
/*   60 */     this.m_paneTabs.addTab(this.m_resources.getString("Title.Tab.Chords"), this.m_tabChords);
/*   61 */     this.m_paneTabs.addTab(this.m_resources.getString("Title.Tab.Printer"), this.m_tabPrinter);
/*   62 */     this.m_paneTabs.addTab(this.m_resources.getString("Title.Tab.HTML"), this.m_tabHTML);
/*   63 */     this.m_paneTabs.addTab(this.m_resources.getString("Title.Tab.FullScreen"), this.m_tabFullScreen);
/*   64 */     this.m_paneTabs.addTab(this.m_resources.getString("Title.Tab.DataValues"), this.m_tabDataValues);
/*      */     
/*   66 */     this.m_paneTabs.setSelectedIndex(paramInt);
/*      */   }
/*      */   HTMLTab m_tabHTML; FullScreenTab m_tabFullScreen; DataValuesTab m_tabDataValues; JTabbedPane m_paneTabs; Properties m_props; private ResourceBundle m_resources; boolean m_rescanSongs; String m_miniPreviewPrevious; static final int GENERAL = 0; static final int CHORDS = 1; static final int PRINTER = 2; static final int HTML = 3; static final int FULLSCREEN = 4;
/*      */   static final int DATAVALUES = 5;
/*      */   
/*      */   private void setupFormatComboBox(JComboBox<String> paramJComboBox, String[] paramArrayOfString, String paramString) {
/*   72 */     paramJComboBox.setEditable(true);
/*   73 */     paramJComboBox.setPrototypeDisplayValue("WWWWWWWWWW");
/*   74 */     String str = this.m_props.getProperty(paramString);
/*   75 */     if (!Arrays.<String>asList(paramArrayOfString).contains(str))
/*   76 */       paramJComboBox.insertItemAt(str, 0); 
/*   77 */     paramJComboBox.setSelectedItem(str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int show() {
/*   83 */     int i = JOptionPane.showOptionDialog(this.m_frame, this.m_paneTabs, this.m_resources
/*      */ 
/*      */         
/*   86 */         .getString("Title.Dialog.Options"), 2, -1, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   94 */     if (0 == i) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  100 */       this.m_props.setProperty("toc.text", this.m_tabGeneral.m_textfieldTOCText.getText());
/*  101 */       this.m_props.setProperty("songs.editor", this.m_tabGeneral.m_textfieldSongsEditor.getText());
/*      */       
/*  103 */       this.m_props.setProperty("songs.path", this.m_tabGeneral.m_textfieldSongsPath.getText());
/*  104 */       this.m_rescanSongs = this.m_tabGeneral.m_checkboxRescan.isSelected();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  109 */       Properties properties = SG02App.getDefaultProperties();
/*      */ 
/*      */       
/*  112 */       boolean bool2 = (this.m_props.getProperty("units").charAt(0) != 'i') ? true : false;
/*  113 */       if (bool2)
/*      */       {
/*  115 */         this.m_tabChords.displayProperUnits(false);
/*      */       }
/*      */       
/*  118 */       this.m_props.setProperty("keys.grids.friendly", this.m_tabChords.m_textfieldFriendlyKeys.getText());
/*  119 */       this.m_props.setProperty("grids.chords.no.grids", this.m_tabChords.m_textfieldChordsNoGrids.getText());
/*      */ 
/*      */       
/*      */       try {
/*  123 */         int j = Integer.parseInt(this.m_tabChords.m_textfieldCapoMax.getText());
/*  124 */         if (j < 0 || j > 12)
/*  125 */           j = 7; 
/*  126 */         this.m_props.setProperty("capo.max", String.valueOf(j));
/*      */       }
/*  128 */       catch (Exception exception) {
/*      */         
/*  130 */         this.m_props.setProperty("capo.max", properties.getProperty("capo.max"));
/*      */       } 
/*      */       
/*  133 */       this.m_props.setProperty("capo.keys", this.m_tabChords.m_textfieldCapoKeys.getText());
/*      */ 
/*      */       
/*      */       try {
/*  137 */         float f = Float.parseFloat(this.m_tabChords.m_textfieldGridMin.getText());
/*  138 */         this.m_props.setProperty("grids.min", String.valueOf(f));
/*      */       }
/*  140 */       catch (Exception exception) {
/*      */         
/*  142 */         this.m_props.setProperty("grids.min", properties.getProperty("grids.min"));
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  147 */         float f = Float.parseFloat(this.m_tabChords.m_textfieldGridMax.getText());
/*  148 */         this.m_props.setProperty("grids.max", String.valueOf(f));
/*      */       }
/*  150 */       catch (Exception exception) {
/*      */         
/*  152 */         this.m_props.setProperty("grids.max", properties.getProperty("grids.max"));
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  157 */         float f = Float.parseFloat(this.m_tabChords.m_textfieldGridSpacing.getText());
/*  158 */         this.m_props.setProperty("grids.spacing", String.valueOf(f));
/*      */       }
/*  160 */       catch (Exception exception) {
/*      */         
/*  162 */         this.m_props.setProperty("grids.spacing", properties.getProperty("grids.spacing"));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  169 */       this.m_props.setProperty("print.songs.per.page", this.m_tabPrinter.getSongsPerPage());
/*  170 */       this.m_props.setProperty("print.chorus.mark", this.m_tabPrinter.getChorusMark());
/*      */ 
/*      */       
/*  173 */       boolean bool1 = (this.m_props.getProperty("units").charAt(0) != 'i') ? true : false;
/*  174 */       if (bool1)
/*      */       {
/*  176 */         this.m_tabPrinter.displayProperUnits(false);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/*  181 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldSpacingNormal.getText());
/*  182 */         this.m_props.setProperty("print.spacing.lyric", String.valueOf(f));
/*      */       }
/*  184 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  188 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldSpacingChord.getText());
/*  189 */         this.m_props.setProperty("print.spacing.chord", String.valueOf(f));
/*      */       }
/*  191 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  195 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldMarginTop.getText());
/*  196 */         this.m_props.setProperty("print.margin.top", String.valueOf(f));
/*      */       }
/*  198 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  202 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldMarginBottom.getText());
/*  203 */         this.m_props.setProperty("print.margin.bottom", String.valueOf(f));
/*      */       }
/*  205 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  209 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldMarginLeft.getText());
/*  210 */         this.m_props.setProperty("print.margin.left", String.valueOf(f));
/*      */       }
/*  212 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  216 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldMarginRight.getText());
/*  217 */         this.m_props.setProperty("print.margin.right", String.valueOf(f));
/*      */       }
/*  219 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  223 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldMarginColumn.getText());
/*  224 */         this.m_props.setProperty("print.spacing.column", String.valueOf(f));
/*      */       }
/*  226 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  230 */         float f = Float.parseFloat(this.m_tabPrinter.m_textfieldMargin2Column.getText());
/*  231 */         this.m_props.setProperty("print.spacing.column.2nd", String.valueOf(f));
/*      */       }
/*  233 */       catch (Exception exception) {}
/*      */       
/*  235 */       this.m_props.setProperty("print.format.title", (String)this.m_tabPrinter.m_comboTitleFormat.getEditor().getItem());
/*  236 */       this.m_props.setProperty("print.format.subtitle", (String)this.m_tabPrinter.m_comboSubtitleFormat.getEditor().getItem());
/*  237 */       this.m_props.setProperty("print.footer.text", (String)this.m_tabPrinter.m_comboFooterFormat.getEditor().getItem());
/*  238 */       this.m_props.setProperty("print.chars.tab.inches", this.m_tabPrinter.m_textfieldTabCharExpansion.getText());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  243 */       if ('y' == this.m_props.getProperty("html.css.link").charAt(0)) {
/*  244 */         this.m_props.setProperty("html.css.link.name", this.m_tabHTML.m_textfieldLinkName.getText());
/*      */       } else {
/*      */         
/*  247 */         this.m_props.setProperty("html.style.song", this.m_tabHTML.m_textfieldSongBody.getText());
/*  248 */         this.m_props.setProperty("html.style.title", this.m_tabHTML.m_textfieldTitle.getText());
/*  249 */         this.m_props.setProperty("html.style.subtitle", this.m_tabHTML.m_textfieldSubtitle.getText());
/*  250 */         this.m_props.setProperty("html.style.lyric", this.m_tabHTML.m_textfieldLyric.getText());
/*  251 */         this.m_props.setProperty("html.style.chord", this.m_tabHTML.m_textfieldChord.getText());
/*  252 */         this.m_props.setProperty("html.style.comment", this.m_tabHTML.m_textfieldComment.getText());
/*  253 */         this.m_props.setProperty("html.style.chorus", this.m_tabHTML.m_textfieldChorus.getText());
/*  254 */         this.m_props.setProperty("html.style.chorus.overall", this.m_tabHTML.m_textfieldChorusOverall.getText());
/*  255 */         this.m_props.setProperty("html.style.newsong", this.m_tabHTML.m_textfieldNewSong.getText());
/*  256 */         this.m_props.setProperty("html.style.tab", this.m_tabHTML.m_textfieldTab.getText());
/*  257 */         this.m_props.setProperty("html.style.toc", this.m_tabHTML.m_textfieldTOC.getText());
/*  258 */         this.m_props.setProperty("html.style.toc.header", this.m_tabHTML.m_textfieldTOCHeader.getText());
/*  259 */         this.m_props.setProperty("html.style.toc.contents", this.m_tabHTML.m_textfieldTOCContents.getText());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  269 */         float f = Float.parseFloat(this.m_tabFullScreen.m_textfieldMarginHorz.getText());
/*  270 */         this.m_props.setProperty("fullscreen.margin.percent.horz", String.valueOf(f));
/*      */       }
/*  272 */       catch (Exception exception) {}
/*      */ 
/*      */       
/*      */       try {
/*  276 */         float f = Float.parseFloat(this.m_tabFullScreen.m_textfieldMarginVert.getText());
/*  277 */         this.m_props.setProperty("fullscreen.margin.percent.vert", String.valueOf(f));
/*      */       }
/*  279 */       catch (Exception exception) {}
/*      */       
/*  281 */       this.m_props.setProperty("fullscreen.device", String.valueOf(this.m_tabFullScreen.m_comboDevice.getSelectedIndex()));
/*      */       
/*  283 */       this.m_props.setProperty("fullscreen.image.background", this.m_tabFullScreen.m_textfieldImage.getText());
/*  284 */       this.m_props.setProperty("fullscreen.footer.text", (String)this.m_tabFullScreen.m_comboFooterFormat.getEditor().getItem());
/*  285 */       this.m_props.setProperty("fullscreen.chars.tab.percent", this.m_tabFullScreen.m_textfieldTabCharExpansion.getText());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  292 */         SG02App.replaceKeyValueMappings(this.m_tabDataValues.m_data.m_mapDataKeysToValues);
/*      */       }
/*  294 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  301 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   class GeneralTab
/*      */     extends OSXTab
/*      */     implements KeyListener
/*      */   {
/*      */     public JTextField m_textfieldSongsEditor;
/*      */     
/*      */     public JTextField m_textfieldSongsPath;
/*      */     
/*      */     public JTextField m_textfieldTOCText;
/*      */     
/*      */     public JCheckBox m_checkboxRescan;
/*      */     
/*      */     public JCheckBox m_checkboxTOCPageNumbers;
/*      */     public JCheckBox m_checkboxTOCNPP;
/*      */     
/*      */     GeneralTab() {
/*  321 */       PrintTOCPropertyAction printTOCPropertyAction = new PrintTOCPropertyAction();
/*      */       
/*  323 */       Dimension dimension = getControlSpacing();
/*      */       
/*  325 */       GridBagLayout gridBagLayout = new GridBagLayout();
/*  326 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  327 */       setLayout(gridBagLayout);
/*  328 */       gridBagConstraints.weightx = 0.0D;
/*      */       
/*  330 */       Insets insets1 = new Insets(dimension.height, dimension.width, 0, 0);
/*  331 */       Insets insets2 = new Insets(dimension.height, dimension.width, 0, dimension.width);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  336 */       GenericPropertyAction genericPropertyAction3 = new GenericPropertyAction(OptionsDlg.this.m_props, "output");
/*      */ 
/*      */       
/*  339 */       JLabel jLabel3 = new JLabel(this.m_resources.getString("Label.Tab.General.Output"));
/*  340 */       gridBagConstraints.anchor = 13;
/*  341 */       gridBagConstraints.fill = 0;
/*  342 */       gridBagConstraints.gridx = 0;
/*  343 */       gridBagConstraints.gridy = 0;
/*  344 */       gridBagConstraints.gridwidth = 1;
/*  345 */       gridBagConstraints.insets = insets1;
/*  346 */       gridBagLayout.setConstraints(jLabel3, gridBagConstraints);
/*  347 */       add(jLabel3);
/*      */ 
/*      */       
/*  350 */       FlowLayout flowLayout = new FlowLayout(0);
/*  351 */       flowLayout.setHgap(flowLayout.getHgap() * 2);
/*      */       
/*  353 */       JPanel jPanel2 = new JPanel(flowLayout);
/*  354 */       gridBagConstraints.anchor = 17;
/*  355 */       gridBagConstraints.gridx++;
/*  356 */       gridBagConstraints.gridwidth = 3;
/*  357 */       gridBagConstraints.insets = new Insets(dimension.height, 0, 0, 0);
/*  358 */       gridBagLayout.setConstraints(jPanel2, gridBagConstraints);
/*      */       
/*  360 */       ButtonGroup buttonGroup = new ButtonGroup();
/*  361 */       String str = this.m_resources.getString("Menu.Output.Printer");
/*  362 */       StringPropertyRadioButton stringPropertyRadioButton = new StringPropertyRadioButton(genericPropertyAction3, str);
/*  363 */       stringPropertyRadioButton.setText(str);
/*  364 */       if (!SG02App.isMac)
/*  365 */         stringPropertyRadioButton.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.Printer.Mn")).intValue()); 
/*  366 */       buttonGroup.add(stringPropertyRadioButton);
/*  367 */       jPanel2.add(stringPropertyRadioButton);
/*      */       
/*  369 */       str = this.m_resources.getString("Menu.Output.Plaintext");
/*  370 */       stringPropertyRadioButton = new StringPropertyRadioButton(genericPropertyAction3, str);
/*  371 */       stringPropertyRadioButton.setText(str);
/*  372 */       if (!SG02App.isMac)
/*  373 */         stringPropertyRadioButton.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.Plaintext.Mn")).intValue()); 
/*  374 */       buttonGroup.add(stringPropertyRadioButton);
/*  375 */       jPanel2.add(stringPropertyRadioButton);
/*      */       
/*  377 */       str = this.m_resources.getString("Menu.Output.HTML");
/*  378 */       stringPropertyRadioButton = new StringPropertyRadioButton(genericPropertyAction3, str);
/*  379 */       stringPropertyRadioButton.setText(str);
/*  380 */       if (!SG02App.isMac)
/*  381 */         stringPropertyRadioButton.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.HTML.Mn")).intValue()); 
/*  382 */       buttonGroup.add(stringPropertyRadioButton);
/*  383 */       jPanel2.add(stringPropertyRadioButton);
/*      */       
/*  385 */       str = this.m_resources.getString("Menu.Output.ChordPro");
/*  386 */       stringPropertyRadioButton = new StringPropertyRadioButton(genericPropertyAction3, str);
/*  387 */       stringPropertyRadioButton.setText(str);
/*  388 */       if (!SG02App.isMac)
/*  389 */         stringPropertyRadioButton.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.ChordPro.Mn")).intValue()); 
/*  390 */       buttonGroup.add(stringPropertyRadioButton);
/*  391 */       jPanel2.add(stringPropertyRadioButton);
/*      */       
/*  393 */       add(jPanel2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  399 */       genericPropertyAction3 = new GenericPropertyAction(OptionsDlg.this.m_props, "units");
/*      */ 
/*      */       
/*  402 */       jLabel3 = new JLabel(this.m_resources.getString("Label.Tab.General.Units"));
/*  403 */       gridBagConstraints.anchor = 13;
/*  404 */       gridBagConstraints.fill = 0;
/*  405 */       gridBagConstraints.gridx = 0;
/*  406 */       gridBagConstraints.gridy++;
/*  407 */       gridBagConstraints.gridwidth = 1;
/*  408 */       gridBagConstraints.insets = insets1;
/*  409 */       gridBagLayout.setConstraints(jLabel3, gridBagConstraints);
/*  410 */       add(jLabel3);
/*      */ 
/*      */       
/*  413 */       flowLayout = new FlowLayout(0);
/*  414 */       flowLayout.setHgap(flowLayout.getHgap() * 2);
/*      */       
/*  416 */       jPanel2 = new JPanel(flowLayout);
/*  417 */       gridBagConstraints.anchor = 17;
/*  418 */       gridBagConstraints.gridx++;
/*  419 */       gridBagConstraints.gridwidth = 3;
/*  420 */       gridBagConstraints.insets = new Insets(dimension.height, 0, 0, 0);
/*  421 */       gridBagLayout.setConstraints(jPanel2, gridBagConstraints);
/*      */       
/*  423 */       buttonGroup = new ButtonGroup();
/*  424 */       str = this.m_resources.getString("Label.inches");
/*  425 */       stringPropertyRadioButton = new StringPropertyRadioButton(genericPropertyAction3, str);
/*  426 */       stringPropertyRadioButton.setText(str);
/*      */ 
/*      */       
/*  429 */       buttonGroup.add(stringPropertyRadioButton);
/*  430 */       jPanel2.add(stringPropertyRadioButton);
/*  431 */       stringPropertyRadioButton.addActionListener(new ActionListener()
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/*  435 */               OptionsDlg.this.m_tabPrinter.displayProperUnits(false);
/*  436 */               OptionsDlg.this.m_tabChords.displayProperUnits(false);
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  441 */       str = this.m_resources.getString("Label.mm");
/*  442 */       stringPropertyRadioButton = new StringPropertyRadioButton(genericPropertyAction3, str);
/*  443 */       stringPropertyRadioButton.setText(str);
/*      */ 
/*      */       
/*  446 */       buttonGroup.add(stringPropertyRadioButton);
/*  447 */       jPanel2.add(stringPropertyRadioButton);
/*  448 */       stringPropertyRadioButton.addActionListener(new ActionListener()
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/*  452 */               OptionsDlg.this.m_tabPrinter.displayProperUnits(true);
/*  453 */               OptionsDlg.this.m_tabChords.displayProperUnits(true);
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  458 */       add(jPanel2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  464 */       genericPropertyAction3 = new GenericPropertyAction(OptionsDlg.this.m_props, "songs.titles.reorder.aanthe");
/*      */       
/*  466 */       StringPropertyCheckBox stringPropertyCheckBox4 = new StringPropertyCheckBox(genericPropertyAction3, "yes", "no");
/*  467 */       stringPropertyCheckBox4.setText(this.m_resources.getString("Command.Tab.General.ReorderAAnThe"));
/*  468 */       if (!SG02App.isMac)
/*  469 */         stringPropertyCheckBox4.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.ReorderAAnThe.Mn")).intValue()); 
/*  470 */       gridBagConstraints.anchor = 17;
/*  471 */       gridBagConstraints.gridx = 1;
/*  472 */       gridBagConstraints.gridy++;
/*  473 */       gridBagConstraints.gridwidth = 4;
/*  474 */       gridBagConstraints.insets = insets1;
/*  475 */       gridBagLayout.setConstraints(stringPropertyCheckBox4, gridBagConstraints);
/*  476 */       add(stringPropertyCheckBox4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  482 */       StringPropertyCheckBox stringPropertyCheckBox1 = new StringPropertyCheckBox(printTOCPropertyAction, "yes", "no");
/*  483 */       stringPropertyCheckBox1.setText(this.m_resources.getString("Command.Tab.General.PrintTOC"));
/*  484 */       if (!SG02App.isMac)
/*  485 */         stringPropertyCheckBox1.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.PrintTOC.Mn")).intValue()); 
/*  486 */       gridBagConstraints.anchor = 17;
/*  487 */       gridBagConstraints.gridx = 1;
/*  488 */       gridBagConstraints.gridy++;
/*  489 */       gridBagConstraints.gridwidth = 4;
/*  490 */       gridBagConstraints.insets = insets1;
/*  491 */       gridBagLayout.setConstraints(stringPropertyCheckBox1, gridBagConstraints);
/*  492 */       add(stringPropertyCheckBox1);
/*      */ 
/*      */       
/*  495 */       JLabel jLabel2 = new JLabel("     ");
/*  496 */       gridBagConstraints.gridx = 1;
/*  497 */       gridBagConstraints.gridy++;
/*  498 */       gridBagConstraints.gridwidth = 1;
/*  499 */       gridBagConstraints.insets = insets1;
/*  500 */       gridBagLayout.setConstraints(jLabel2, gridBagConstraints);
/*  501 */       add(jLabel2);
/*      */ 
/*      */       
/*  504 */       JPanel jPanel1 = new JPanel(new BorderLayout());
/*  505 */       gridBagConstraints.anchor = 13;
/*  506 */       gridBagConstraints.fill = 2;
/*  507 */       gridBagConstraints.gridx = 2;
/*      */       
/*  509 */       gridBagConstraints.gridwidth = 3;
/*  510 */       gridBagConstraints.insets = insets2;
/*  511 */       gridBagLayout.setConstraints(jPanel1, gridBagConstraints);
/*      */       
/*  513 */       JLabel jLabel4 = new JLabel(this.m_resources.getString("Label.Tab.General.TOCText"));
/*  514 */       jPanel1.add(jLabel4, "West");
/*      */       
/*  516 */       this.m_textfieldTOCText = new JTextField();
/*  517 */       this.m_textfieldTOCText.setText(OptionsDlg.this.m_props.getProperty("toc.text"));
/*  518 */       jPanel1.add(this.m_textfieldTOCText, "Center");
/*      */       
/*  520 */       add(jPanel1);
/*      */ 
/*      */ 
/*      */       
/*  524 */       GenericPropertyAction genericPropertyAction4 = new GenericPropertyAction(OptionsDlg.this.m_props, "toc.page.numbers");
/*      */       
/*  526 */       this.m_checkboxTOCPageNumbers = new StringPropertyCheckBox(genericPropertyAction4, "yes", "no");
/*  527 */       this.m_checkboxTOCPageNumbers.setText(this.m_resources.getString("Command.Tab.General.TOCPageNumbers"));
/*  528 */       if (!SG02App.isMac)
/*  529 */         this.m_checkboxTOCPageNumbers.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.TOCPageNumbers.Mn")).intValue()); 
/*  530 */       gridBagConstraints.anchor = 17;
/*  531 */       gridBagConstraints.fill = 0;
/*  532 */       gridBagConstraints.gridx = 2;
/*  533 */       gridBagConstraints.gridy++;
/*  534 */       gridBagConstraints.gridwidth = 3;
/*  535 */       gridBagConstraints.insets = insets1;
/*  536 */       gridBagLayout.setConstraints(this.m_checkboxTOCPageNumbers, gridBagConstraints);
/*  537 */       add(this.m_checkboxTOCPageNumbers);
/*      */ 
/*      */       
/*  540 */       genericPropertyAction4 = new GenericPropertyAction(OptionsDlg.this.m_props, "toc.print.npp");
/*      */       
/*  542 */       this.m_checkboxTOCNPP = new StringPropertyCheckBox(genericPropertyAction4, "yes", "no");
/*  543 */       this.m_checkboxTOCNPP.setText(this.m_resources.getString("Command.Tab.General.PrintTOC.NPP"));
/*      */ 
/*      */       
/*  546 */       gridBagConstraints.anchor = 17;
/*  547 */       gridBagConstraints.fill = 0;
/*  548 */       gridBagConstraints.gridx = 2;
/*  549 */       gridBagConstraints.gridy++;
/*  550 */       gridBagConstraints.gridwidth = 3;
/*  551 */       gridBagConstraints.insets = insets1;
/*  552 */       gridBagLayout.setConstraints(this.m_checkboxTOCNPP, gridBagConstraints);
/*  553 */       add(this.m_checkboxTOCNPP);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  560 */       GenericPropertyAction genericPropertyAction2 = new GenericPropertyAction(OptionsDlg.this.m_props, "songs.number");
/*      */       
/*  562 */       StringPropertyCheckBox stringPropertyCheckBox3 = new StringPropertyCheckBox(genericPropertyAction2, "yes", "no");
/*  563 */       stringPropertyCheckBox3.setText(this.m_resources.getString("Command.Tab.General.AutoNumberSongs"));
/*  564 */       if (!SG02App.isMac)
/*  565 */         stringPropertyCheckBox3.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.AutoNumberSongs.Mn")).intValue()); 
/*  566 */       gridBagConstraints.anchor = 17;
/*  567 */       gridBagConstraints.gridx = 1;
/*  568 */       gridBagConstraints.gridy++;
/*  569 */       gridBagConstraints.gridwidth = 4;
/*  570 */       gridBagConstraints.insets = insets1;
/*  571 */       gridBagLayout.setConstraints(stringPropertyCheckBox3, gridBagConstraints);
/*  572 */       add(stringPropertyCheckBox3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  589 */       JLabel jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.General.SongsEditor"));
/*  590 */       gridBagConstraints.anchor = 13;
/*  591 */       gridBagConstraints.fill = 0;
/*  592 */       gridBagConstraints.gridx = 0;
/*  593 */       gridBagConstraints.gridy++;
/*  594 */       gridBagConstraints.gridwidth = 1;
/*  595 */       gridBagConstraints.insets = insets1;
/*  596 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/*  597 */       add(jLabel1);
/*      */       
/*  599 */       this.m_textfieldSongsEditor = new JTextField();
/*  600 */       this.m_textfieldSongsEditor.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldSongsPath")).intValue());
/*  601 */       this.m_textfieldSongsEditor.setText(OptionsDlg.this.m_props.getProperty("songs.editor"));
/*      */       
/*  603 */       gridBagConstraints.anchor = 13;
/*  604 */       gridBagConstraints.fill = 2;
/*  605 */       gridBagConstraints.gridx++;
/*  606 */       gridBagConstraints.gridwidth = 3;
/*  607 */       gridBagConstraints.insets = insets1;
/*  608 */       gridBagLayout.setConstraints(this.m_textfieldSongsEditor, gridBagConstraints);
/*  609 */       add(this.m_textfieldSongsEditor);
/*      */       
/*  611 */       final JButton browseButton = new JButton();
/*  612 */       browseButton.setAction(new AbstractAction(this.m_resources
/*  613 */             .getString("Command.Tab.General.Browse.SongPath"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/*  617 */               JFileChooser jFileChooser = new JFileChooser();
/*  618 */               String str = SG02App.props.getProperty("songs.editor");
/*  619 */               if (null != str) {
/*      */                 
/*  621 */                 jFileChooser.setSelectedFile(new File(str));
/*  622 */                 jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */               } 
/*      */               
/*  625 */               int i = jFileChooser.showOpenDialog(browseButton);
/*      */               
/*  627 */               if (0 == i) {
/*  628 */                 OptionsDlg.GeneralTab.this.m_textfieldSongsEditor.setText(jFileChooser.getSelectedFile().toString());
/*      */               }
/*      */             }
/*      */           });
/*  632 */       gridBagConstraints.anchor = 10;
/*  633 */       gridBagConstraints.fill = 0;
/*  634 */       gridBagConstraints.gridx += gridBagConstraints.gridwidth;
/*  635 */       gridBagConstraints.gridwidth = 1;
/*  636 */       gridBagConstraints.insets = insets1;
/*  637 */       gridBagLayout.setConstraints(browseButton, gridBagConstraints);
/*  638 */       add(browseButton);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  643 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.General.SongsPath"));
/*  644 */       gridBagConstraints.anchor = 13;
/*  645 */       gridBagConstraints.fill = 0;
/*  646 */       gridBagConstraints.gridx = 0;
/*  647 */       gridBagConstraints.gridy++;
/*  648 */       gridBagConstraints.gridwidth = 1;
/*  649 */       gridBagConstraints.insets = insets1;
/*  650 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/*  651 */       add(jLabel1);
/*      */       
/*  653 */       this.m_textfieldSongsPath = new JTextField();
/*  654 */       this.m_textfieldSongsPath.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldSongsPath")).intValue());
/*  655 */       this.m_textfieldSongsPath.setText(OptionsDlg.this.m_props.getProperty("songs.path"));
/*  656 */       this.m_textfieldSongsPath.addKeyListener(this);
/*  657 */       gridBagConstraints.anchor = 13;
/*  658 */       gridBagConstraints.fill = 2;
/*  659 */       gridBagConstraints.gridx++;
/*  660 */       gridBagConstraints.gridwidth = 3;
/*  661 */       gridBagConstraints.insets = insets1;
/*  662 */       gridBagLayout.setConstraints(this.m_textfieldSongsPath, gridBagConstraints);
/*  663 */       add(this.m_textfieldSongsPath);
/*      */       
/*  665 */       final JButton browseButton2 = new JButton();
/*  666 */       browseButton2.setAction(new AbstractAction(this.m_resources
/*  667 */             .getString("Command.Tab.General.Browse.SongPath"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/*  671 */               JFileChooser jFileChooser = new JFileChooser();
/*  672 */               String str = SG02App.props.getProperty("songs.path");
/*  673 */               if (null != str) {
/*      */                 
/*  675 */                 jFileChooser.setSelectedFile(new File(str));
/*  676 */                 jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */               } 
/*  678 */               jFileChooser.setFileSelectionMode(1);
/*      */               
/*  680 */               int i = jFileChooser.showOpenDialog(browseButton2);
/*      */               
/*  682 */               if (0 == i) {
/*      */                 
/*  684 */                 OptionsDlg.GeneralTab.this.m_textfieldSongsPath.setText(jFileChooser.getSelectedFile().toString());
/*  685 */                 OptionsDlg.GeneralTab.this.m_checkboxRescan.setSelected(true);
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/*  690 */       gridBagConstraints.anchor = 10;
/*  691 */       gridBagConstraints.fill = 0;
/*  692 */       gridBagConstraints.gridx += gridBagConstraints.gridwidth;
/*  693 */       gridBagConstraints.gridwidth = 1;
/*  694 */       gridBagConstraints.insets = insets1;
/*  695 */       gridBagLayout.setConstraints(browseButton, gridBagConstraints);
/*  696 */       add(browseButton);
/*      */       
/*  698 */       this.m_checkboxRescan = new JCheckBox(this.m_resources.getString("Command.Tab.General.Rescan"));
/*  699 */       gridBagConstraints.anchor = 17;
/*  700 */       gridBagConstraints.gridx++;
/*  701 */       gridBagConstraints.gridwidth = 1;
/*  702 */       gridBagConstraints.insets = insets2;
/*  703 */       gridBagLayout.setConstraints(this.m_checkboxRescan, gridBagConstraints);
/*  704 */       add(this.m_checkboxRescan);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  710 */       GenericPropertyAction genericPropertyAction1 = new GenericPropertyAction(OptionsDlg.this.m_props, "songs.path.recurse");
/*      */       
/*  712 */       StringPropertyCheckBox stringPropertyCheckBox2 = new StringPropertyCheckBox(genericPropertyAction1, "yes", "no");
/*  713 */       stringPropertyCheckBox2.setText(this.m_resources.getString("Command.Tab.General.SongsPathRecurse"));
/*  714 */       if (!SG02App.isMac)
/*  715 */         stringPropertyCheckBox2.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.SongsPathRecurse.Mn")).intValue()); 
/*  716 */       gridBagConstraints.anchor = 17;
/*  717 */       gridBagConstraints.gridx = 1;
/*  718 */       gridBagConstraints.gridy++;
/*  719 */       gridBagConstraints.gridwidth = 4;
/*  720 */       gridBagConstraints.insets = insets1;
/*  721 */       gridBagLayout.setConstraints(stringPropertyCheckBox2, gridBagConstraints);
/*  722 */       add(stringPropertyCheckBox2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  728 */       genericPropertyAction1 = new GenericPropertyAction(OptionsDlg.this.m_props, "songs.encoding");
/*      */       
/*  730 */       stringPropertyCheckBox2 = new StringPropertyCheckBox(genericPropertyAction1, "UTF-8", "");
/*  731 */       stringPropertyCheckBox2.setText(this.m_resources.getString("Command.Tab.General.EncodingUTF8"));
/*  732 */       if (!SG02App.isMac)
/*  733 */         stringPropertyCheckBox2.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.EncodingUTF8.Mn")).intValue()); 
/*  734 */       gridBagConstraints.anchor = 17;
/*  735 */       gridBagConstraints.gridx = 1;
/*  736 */       gridBagConstraints.gridy++;
/*  737 */       gridBagConstraints.gridwidth = 4;
/*  738 */       gridBagConstraints.insets = insets1;
/*  739 */       gridBagLayout.setConstraints(stringPropertyCheckBox2, gridBagConstraints);
/*  740 */       add(stringPropertyCheckBox2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  775 */       genericPropertyAction1 = new GenericPropertyAction(OptionsDlg.this.m_props, "reminder.update");
/*      */       
/*  777 */       stringPropertyCheckBox2 = new StringPropertyCheckBox(genericPropertyAction1, "yes", "no");
/*  778 */       stringPropertyCheckBox2.setText(this.m_resources.getString("Command.Reminder.UpdateCheck"));
/*  779 */       gridBagConstraints.anchor = 17;
/*  780 */       gridBagConstraints.gridx = 1;
/*  781 */       gridBagConstraints.gridy++;
/*  782 */       gridBagConstraints.gridwidth = 4;
/*      */ 
/*      */       
/*  785 */       gridBagConstraints.insets = new Insets(dimension.height, dimension.width, dimension.height, dimension.width);
/*  786 */       gridBagLayout.setConstraints(stringPropertyCheckBox2, gridBagConstraints);
/*  787 */       add(stringPropertyCheckBox2);
/*      */       
/*  789 */       Registration registration = new Registration();
/*  790 */       if (!registration.isRegistered()) {
/*  791 */         stringPropertyCheckBox2.setEnabled(false);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  796 */       printTOCPropertyAction.updateFromNewProps(OptionsDlg.this.m_props);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*      */ 
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/*  809 */       if (param1KeyEvent.getSource() == this.m_textfieldSongsPath) {
/*  810 */         this.m_checkboxRescan.setSelected(true);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected class PrintTOCPropertyAction
/*      */       extends GenericPropertyAction
/*      */     {
/*      */       public PrintTOCPropertyAction() {
/*  819 */         super(OptionsDlg.this.m_props, "toc.print");
/*      */       }
/*      */ 
/*      */       
/*      */       public void actionPerformed(ActionEvent param2ActionEvent) {
/*  824 */         super.actionPerformed(param2ActionEvent);
/*  825 */         OptionsDlg.GeneralTab.this.m_checkboxTOCPageNumbers.setEnabled(this.m_booleanValue);
/*  826 */         OptionsDlg.GeneralTab.this.m_checkboxTOCNPP.setEnabled(this.m_booleanValue);
/*  827 */         OptionsDlg.GeneralTab.this.m_textfieldTOCText.setEditable(this.m_booleanValue);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class PrinterTab
/*      */     extends OSXTab
/*      */   {
/*      */     protected JComboBox<PageLayoutItem> m_comboPageLayout;
/*      */     
/*      */     protected ImageIcon[] m_imagesPageLayout;
/*      */     
/*      */     protected JComboBox<ChorusMarkItem> m_comboChorusMark;
/*      */     
/*      */     protected JLabel m_labelPrinterMargin;
/*      */     
/*      */     protected JLabel m_labelUnits;
/*      */     
/*      */     public JTextField m_textfieldSpacingNormal;
/*      */     
/*      */     public JTextField m_textfieldSpacingChord;
/*      */     public JTextField m_textfieldMarginBottom;
/*      */     public JTextField m_textfieldMarginLeft;
/*      */     public JTextField m_textfieldMarginRight;
/*      */     public JTextField m_textfieldMarginTop;
/*      */     public JTextField m_textfieldMarginColumn;
/*      */     public JTextField m_textfieldMargin2Column;
/*      */     public JTextField m_textfieldTabCharExpansion;
/*      */     public JComboBox<String> m_comboTitleFormat;
/*      */     public JComboBox<String> m_comboSubtitleFormat;
/*      */     public JComboBox<String> m_comboFooterFormat;
/*      */     
/*      */     PrinterTab() {
/*  861 */       loadImages();
/*      */       
/*  863 */       Dimension dimension = getControlSpacing();
/*      */       
/*  865 */       setLayout(new BoxLayout(this, 1));
/*      */       
/*  867 */       Insets insets1 = new Insets(dimension.height, dimension.width, 0, 0);
/*  868 */       Insets insets2 = new Insets(dimension.height, dimension.width, 0, dimension.width);
/*      */ 
/*      */       
/*  871 */       JPanel jPanel = new JPanel();
/*  872 */       add(jPanel);
/*      */       
/*  874 */       GridBagLayout gridBagLayout = new GridBagLayout();
/*  875 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  876 */       jPanel.setLayout(gridBagLayout);
/*  877 */       gridBagConstraints.weightx = 0.0D;
/*      */ 
/*      */ 
/*      */       
/*  881 */       JLabel jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.Printer.PageLayout"));
/*  882 */       gridBagConstraints.anchor = 13;
/*  883 */       gridBagConstraints.fill = 0;
/*  884 */       gridBagConstraints.gridx = 0;
/*  885 */       gridBagConstraints.gridy = 0;
/*  886 */       gridBagConstraints.gridwidth = 1;
/*  887 */       gridBagConstraints.insets = insets1;
/*  888 */       gridBagLayout.setConstraints(jLabel2, gridBagConstraints);
/*  889 */       jPanel.add(jLabel2);
/*      */       
/*  891 */       this.m_comboPageLayout = new JComboBox<PageLayoutItem>();
/*  892 */       this.m_comboPageLayout.setRenderer(new PageLayoutCellRenderer());
/*      */       
/*  894 */       PageLayoutItem pageLayoutItem = new PageLayoutItem(1);
/*  895 */       this.m_comboPageLayout.setPrototypeDisplayValue(pageLayoutItem);
/*      */       
/*  897 */       this.m_comboPageLayout.addItem(pageLayoutItem);
/*  898 */       this.m_comboPageLayout.addItem(new PageLayoutItem(2));
/*  899 */       this.m_comboPageLayout.addItem(new PageLayoutItem(4));
/*  900 */       this.m_comboPageLayout.addItem(new PageLayoutItem(10));
/*  901 */       this.m_comboPageLayout.addItem(new PageLayoutItem(20));
/*      */       
/*  903 */       int i = Integer.parseInt(OptionsDlg.this.m_props.getProperty("print.songs.per.page"));
/*  904 */       for (byte b = 0; b < this.m_comboPageLayout.getItemCount(); b++) {
/*      */         
/*  906 */         if (i == ((PageLayoutItem)this.m_comboPageLayout.getItemAt(b)).getValue()) {
/*      */           
/*  908 */           this.m_comboPageLayout.setSelectedIndex(b);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  913 */       gridBagConstraints.anchor = 13;
/*  914 */       gridBagConstraints.fill = 2;
/*  915 */       gridBagConstraints.gridx++;
/*  916 */       gridBagConstraints.gridwidth = 3;
/*  917 */       gridBagConstraints.insets = insets1;
/*  918 */       gridBagLayout.setConstraints(this.m_comboPageLayout, gridBagConstraints);
/*  919 */       jPanel.add(this.m_comboPageLayout);
/*      */ 
/*      */ 
/*      */       
/*  923 */       GenericPropertyAction genericPropertyAction2 = new GenericPropertyAction(OptionsDlg.this.m_props, "print.songs.npp");
/*      */       
/*  925 */       StringPropertyCheckBox stringPropertyCheckBox2 = new StringPropertyCheckBox(genericPropertyAction2, "yes", "no");
/*  926 */       stringPropertyCheckBox2.setText(this.m_resources.getString("Command.Tab.Print.NPP"));
/*  927 */       if (!SG02App.isMac)
/*  928 */         stringPropertyCheckBox2.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Print.NPP.Mn")).intValue()); 
/*  929 */       gridBagConstraints.anchor = 17;
/*  930 */       gridBagConstraints.gridy++;
/*  931 */       gridBagConstraints.gridwidth = 3;
/*  932 */       gridBagConstraints.insets = insets1;
/*  933 */       gridBagLayout.setConstraints(stringPropertyCheckBox2, gridBagConstraints);
/*  934 */       jPanel.add(stringPropertyCheckBox2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  943 */       jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.Printer.MarkChorus"));
/*  944 */       gridBagConstraints.anchor = 13;
/*  945 */       gridBagConstraints.fill = 0;
/*  946 */       gridBagConstraints.gridx = 0;
/*  947 */       gridBagConstraints.gridy++;
/*  948 */       gridBagConstraints.gridwidth = 1;
/*  949 */       gridBagConstraints.insets = insets1;
/*  950 */       gridBagLayout.setConstraints(jLabel2, gridBagConstraints);
/*  951 */       jPanel.add(jLabel2);
/*      */       
/*  953 */       this.m_comboChorusMark = new JComboBox<ChorusMarkItem>();
/*      */       
/*  955 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("line"));
/*  956 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("line.thick"));
/*  957 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("box"));
/*  958 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("box.light"));
/*  959 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("box.fill.0.15"));
/*  960 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("indent.tab.1"));
/*  961 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("indent.tab.2"));
/*  962 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("line.tab.1"));
/*  963 */       this.m_comboChorusMark.addItem(new ChorusMarkItem("none"));
/*      */       
/*  965 */       String str = OptionsDlg.this.m_props.getProperty("print.chorus.mark");
/*  966 */       for (i = 0; i < this.m_comboChorusMark.getItemCount(); i++) {
/*      */         
/*  968 */         if (0 == str.compareTo(((ChorusMarkItem)this.m_comboChorusMark.getItemAt(i)).getValue())) {
/*      */           
/*  970 */           this.m_comboChorusMark.setSelectedIndex(i);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  975 */       gridBagConstraints.anchor = 13;
/*  976 */       gridBagConstraints.fill = 2;
/*  977 */       gridBagConstraints.gridx++;
/*  978 */       gridBagConstraints.gridwidth = 2;
/*  979 */       gridBagConstraints.insets = insets1;
/*  980 */       gridBagLayout.setConstraints(this.m_comboChorusMark, gridBagConstraints);
/*  981 */       jPanel.add(this.m_comboChorusMark);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       GenericPropertyAction genericPropertyAction1 = new GenericPropertyAction(OptionsDlg.this.m_props, "print.overflow.title");
/*      */       
/*  988 */       StringPropertyCheckBox stringPropertyCheckBox1 = new StringPropertyCheckBox(genericPropertyAction1, "yes", "no");
/*  989 */       stringPropertyCheckBox1.setText(this.m_resources.getString("Command.Tab.Print.OverflowTitle"));
/*  990 */       if (!SG02App.isMac)
/*  991 */         stringPropertyCheckBox1.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Print.OverflowTitle.Mn")).intValue()); 
/*  992 */       gridBagConstraints.anchor = 17;
/*  993 */       gridBagConstraints.gridx = 1;
/*  994 */       gridBagConstraints.gridy++;
/*  995 */       gridBagConstraints.gridwidth = 3;
/*  996 */       gridBagConstraints.insets = insets1;
/*  997 */       gridBagLayout.setConstraints(stringPropertyCheckBox1, gridBagConstraints);
/*  998 */       jPanel.add(stringPropertyCheckBox1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1003 */       genericPropertyAction1 = new GenericPropertyAction(OptionsDlg.this.m_props, "print.wrap.titles");
/*      */       
/* 1005 */       stringPropertyCheckBox1 = new StringPropertyCheckBox(genericPropertyAction1, "yes", "no");
/* 1006 */       stringPropertyCheckBox1.setText(this.m_resources.getString("Command.Tab.Print.Wrap.Titles"));
/* 1007 */       if (!SG02App.isMac)
/* 1008 */         stringPropertyCheckBox1.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Print.Wrap.Titles.Mn")).intValue()); 
/* 1009 */       gridBagConstraints.anchor = 17;
/* 1010 */       gridBagConstraints.gridx = 1;
/* 1011 */       gridBagConstraints.gridy++;
/* 1012 */       gridBagConstraints.gridwidth = 3;
/* 1013 */       gridBagConstraints.insets = insets1;
/* 1014 */       gridBagLayout.setConstraints(stringPropertyCheckBox1, gridBagConstraints);
/* 1015 */       jPanel.add(stringPropertyCheckBox1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1039 */       jPanel = new JPanel();
/* 1040 */       add(jPanel);
/*      */       
/* 1042 */       gridBagLayout = new GridBagLayout();
/* 1043 */       gridBagConstraints = new GridBagConstraints();
/* 1044 */       jPanel.setLayout(gridBagLayout);
/* 1045 */       gridBagConstraints.weightx = 0.0D;
/*      */ 
/*      */ 
/*      */       
/* 1049 */       JLabel jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Spacing.Normal"));
/* 1050 */       gridBagConstraints.anchor = 13;
/* 1051 */       gridBagConstraints.fill = 0;
/* 1052 */       gridBagConstraints.gridx = 0;
/* 1053 */       gridBagConstraints.gridy = 0;
/* 1054 */       gridBagConstraints.gridwidth = 1;
/* 1055 */       gridBagConstraints.insets = insets1;
/* 1056 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1057 */       jPanel.add(jLabel1);
/*      */       
/* 1059 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Spacing.Chord"));
/* 1060 */       gridBagConstraints.gridy++;
/* 1061 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1062 */       jPanel.add(jLabel1);
/*      */       
/* 1064 */       this.m_textfieldSpacingNormal = new JTextField();
/* 1065 */       this.m_textfieldSpacingNormal.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1066 */       this.m_textfieldSpacingNormal.setText(OptionsDlg.this.m_props.getProperty("print.spacing.lyric"));
/* 1067 */       gridBagConstraints.fill = 2;
/* 1068 */       gridBagConstraints.gridx++;
/* 1069 */       gridBagConstraints.gridy = 0;
/* 1070 */       gridBagConstraints.gridwidth = 1;
/* 1071 */       gridBagConstraints.insets = insets1;
/* 1072 */       gridBagLayout.setConstraints(this.m_textfieldSpacingNormal, gridBagConstraints);
/* 1073 */       jPanel.add(this.m_textfieldSpacingNormal);
/*      */       
/* 1075 */       this.m_textfieldSpacingChord = new JTextField();
/* 1076 */       this.m_textfieldSpacingChord.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1077 */       this.m_textfieldSpacingChord.setText(OptionsDlg.this.m_props.getProperty("print.spacing.chord"));
/* 1078 */       gridBagConstraints.gridy++;
/* 1079 */       gridBagLayout.setConstraints(this.m_textfieldSpacingChord, gridBagConstraints);
/* 1080 */       jPanel.add(this.m_textfieldSpacingChord);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1085 */       this.m_labelPrinterMargin = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.in"));
/* 1086 */       gridBagConstraints.anchor = 13;
/* 1087 */       gridBagConstraints.fill = 0;
/* 1088 */       gridBagConstraints.gridwidth = 1;
/* 1089 */       gridBagConstraints.gridx++;
/* 1090 */       gridBagConstraints.gridy = 0;
/* 1091 */       gridBagConstraints.insets = insets1;
/* 1092 */       gridBagLayout.setConstraints(this.m_labelPrinterMargin, gridBagConstraints);
/* 1093 */       jPanel.add(this.m_labelPrinterMargin);
/*      */       
/* 1095 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.Top"));
/* 1096 */       gridBagConstraints.gridx++;
/* 1097 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1098 */       jPanel.add(jLabel1);
/*      */       
/* 1100 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.Bottom"));
/* 1101 */       gridBagConstraints.gridy++;
/* 1102 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1103 */       jPanel.add(jLabel1);
/*      */       
/* 1105 */       this.m_textfieldMarginTop = new JTextField();
/* 1106 */       this.m_textfieldMarginTop.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1107 */       this.m_textfieldMarginTop.setText(OptionsDlg.this.m_props.getProperty("print.margin.top"));
/* 1108 */       gridBagConstraints.fill = 2;
/* 1109 */       gridBagConstraints.gridx++;
/* 1110 */       gridBagConstraints.gridy = 0;
/* 1111 */       gridBagConstraints.insets = insets1;
/* 1112 */       gridBagLayout.setConstraints(this.m_textfieldMarginTop, gridBagConstraints);
/* 1113 */       jPanel.add(this.m_textfieldMarginTop);
/*      */       
/* 1115 */       this.m_textfieldMarginBottom = new JTextField();
/* 1116 */       this.m_textfieldMarginBottom.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1117 */       this.m_textfieldMarginBottom.setText(OptionsDlg.this.m_props.getProperty("print.margin.bottom"));
/* 1118 */       gridBagConstraints.gridy++;
/* 1119 */       gridBagLayout.setConstraints(this.m_textfieldMarginBottom, gridBagConstraints);
/* 1120 */       jPanel.add(this.m_textfieldMarginBottom);
/*      */       
/* 1122 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.Left"));
/* 1123 */       gridBagConstraints.anchor = 13;
/* 1124 */       gridBagConstraints.fill = 0;
/* 1125 */       gridBagConstraints.gridx++;
/* 1126 */       gridBagConstraints.gridy = 0;
/* 1127 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1128 */       jPanel.add(jLabel1);
/*      */       
/* 1130 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.Right"));
/* 1131 */       gridBagConstraints.gridy++;
/* 1132 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1133 */       jPanel.add(jLabel1);
/*      */       
/* 1135 */       this.m_textfieldMarginLeft = new JTextField();
/* 1136 */       this.m_textfieldMarginLeft.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1137 */       this.m_textfieldMarginLeft.setText(OptionsDlg.this.m_props.getProperty("print.margin.left"));
/* 1138 */       gridBagConstraints.fill = 2;
/* 1139 */       gridBagConstraints.gridx++;
/* 1140 */       gridBagConstraints.gridy = 0;
/* 1141 */       gridBagConstraints.insets = insets2;
/* 1142 */       gridBagLayout.setConstraints(this.m_textfieldMarginLeft, gridBagConstraints);
/* 1143 */       jPanel.add(this.m_textfieldMarginLeft);
/*      */       
/* 1145 */       this.m_textfieldMarginRight = new JTextField();
/* 1146 */       this.m_textfieldMarginRight.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1147 */       this.m_textfieldMarginRight.setText(OptionsDlg.this.m_props.getProperty("print.margin.right"));
/* 1148 */       gridBagConstraints.gridy++;
/* 1149 */       gridBagLayout.setConstraints(this.m_textfieldMarginRight, gridBagConstraints);
/* 1150 */       jPanel.add(this.m_textfieldMarginRight);
/*      */ 
/*      */ 
/*      */       
/* 1154 */       this.m_textfieldMarginColumn = new JTextField();
/* 1155 */       this.m_textfieldMarginColumn.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1156 */       this.m_textfieldMarginColumn.setText(OptionsDlg.this.m_props.getProperty("print.spacing.column"));
/* 1157 */       gridBagConstraints.fill = 2;
/* 1158 */       gridBagConstraints.gridy++;
/* 1159 */       gridBagConstraints.insets = insets2;
/* 1160 */       gridBagLayout.setConstraints(this.m_textfieldMarginColumn, gridBagConstraints);
/* 1161 */       jPanel.add(this.m_textfieldMarginColumn);
/*      */       
/* 1163 */       this.m_textfieldMargin2Column = new JTextField();
/* 1164 */       this.m_textfieldMargin2Column.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1165 */       this.m_textfieldMargin2Column.setText(OptionsDlg.this.m_props.getProperty("print.spacing.column.2nd"));
/* 1166 */       gridBagConstraints.gridy++;
/* 1167 */       gridBagLayout.setConstraints(this.m_textfieldMargin2Column, gridBagConstraints);
/* 1168 */       jPanel.add(this.m_textfieldMargin2Column);
/*      */       
/* 1170 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.Column"));
/* 1171 */       gridBagConstraints.anchor = 13;
/* 1172 */       gridBagConstraints.fill = 0;
/* 1173 */       gridBagConstraints.gridx -= 4;
/* 1174 */       gridBagConstraints.gridy--;
/* 1175 */       gridBagConstraints.gridwidth = 4;
/* 1176 */       gridBagConstraints.insets = insets1;
/* 1177 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1178 */       jPanel.add(jLabel1);
/*      */       
/* 1180 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Margin.2Column"));
/* 1181 */       gridBagConstraints.gridy++;
/* 1182 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1183 */       jPanel.add(jLabel1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1188 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Tab.Chars"));
/* 1189 */       gridBagConstraints.anchor = 13;
/* 1190 */       gridBagConstraints.fill = 0;
/* 1191 */       gridBagConstraints.gridx = 0;
/* 1192 */       gridBagConstraints.gridy++;
/* 1193 */       gridBagConstraints.gridwidth = 1;
/* 1194 */       gridBagConstraints.insets = insets1;
/* 1195 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 1196 */       jPanel.add(jLabel1);
/*      */       
/* 1198 */       this.m_textfieldTabCharExpansion = new JTextField();
/* 1199 */       this.m_textfieldTabCharExpansion.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 1200 */       this.m_textfieldTabCharExpansion.setText(OptionsDlg.this.m_props.getProperty("print.chars.tab.inches"));
/* 1201 */       gridBagConstraints.fill = 2;
/* 1202 */       gridBagConstraints.gridx++;
/* 1203 */       gridBagConstraints.gridwidth = 1;
/* 1204 */       gridBagConstraints.insets = insets1;
/* 1205 */       gridBagLayout.setConstraints(this.m_textfieldTabCharExpansion, gridBagConstraints);
/* 1206 */       jPanel.add(this.m_textfieldTabCharExpansion);
/*      */       
/* 1208 */       this.m_labelUnits = new JLabel(this.m_resources.getString("Label.inches"));
/* 1209 */       gridBagConstraints.anchor = 17;
/* 1210 */       gridBagConstraints.fill = 0;
/* 1211 */       gridBagConstraints.gridwidth = 1;
/* 1212 */       gridBagConstraints.gridx++;
/* 1213 */       gridBagConstraints.insets = insets1;
/* 1214 */       gridBagLayout.setConstraints(this.m_labelUnits, gridBagConstraints);
/* 1215 */       jPanel.add(this.m_labelUnits);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1221 */       final JButton setFontsButton = new JButton();
/* 1222 */       setFontsButton.setAction(new AbstractAction(this.m_resources
/* 1223 */             .getString("Command.Tab.Print.SetFonts"))
/*      */           {
/*      */             
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 1228 */               FontSettingsDialog.showFontSettingsDialog(setFontsButton, "print", "fullscreen", OptionsDlg.this.m_props, Color.WHITE);
/*      */             }
/*      */           });
/*      */       
/* 1232 */       gridBagConstraints.anchor = 13;
/* 1233 */       gridBagConstraints.fill = 2;
/* 1234 */       gridBagConstraints.gridx = 2;
/* 1235 */       gridBagConstraints.gridy++;
/* 1236 */       gridBagConstraints.gridwidth = 2;
/* 1237 */       gridBagConstraints.insets = insets2;
/* 1238 */       gridBagLayout.setConstraints(setFontsButton, gridBagConstraints);
/* 1239 */       jPanel.add(setFontsButton);
/*      */ 
/*      */ 
/*      */       
/* 1243 */       addSpecialFormatBox(dimension);
/*      */       
/* 1245 */       boolean bool = (OptionsDlg.this.m_props.getProperty("units").charAt(0) != 'i') ? true : false;
/* 1246 */       if (bool) {
/* 1247 */         displayProperUnits(true);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void addSpecialFormatBox(Dimension param1Dimension) {
/* 1254 */       Insets insets = new Insets(param1Dimension.height, param1Dimension.width, param1Dimension.height, 0);
/*      */       
/* 1256 */       JPanel jPanel = new JPanel();
/* 1257 */       add(jPanel);
/* 1258 */       jPanel.setBorder(new TitledBorder(this.m_resources.getString("Label.Tab.Printer.FormatBox")));
/*      */       
/* 1260 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 1261 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 1262 */       jPanel.setLayout(gridBagLayout);
/* 1263 */       gridBagConstraints.weightx = 0.0D;
/*      */       
/* 1265 */       JLabel jLabel = new JLabel(this.m_resources.getString("Label.Tab.Printer.Title"));
/* 1266 */       gridBagConstraints.anchor = 13;
/* 1267 */       gridBagConstraints.fill = 0;
/* 1268 */       gridBagConstraints.gridx = 0;
/* 1269 */       gridBagConstraints.gridy = 0;
/* 1270 */       gridBagConstraints.gridwidth = 1;
/* 1271 */       gridBagConstraints.insets = insets;
/* 1272 */       gridBagConstraints.weightx = 0.0D;
/* 1273 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1274 */       jPanel.add(jLabel);
/*      */       
/* 1276 */       String[] arrayOfString1 = { "%c%t", "%l%t", "%c%T", "%l%T %?A\"(%A)\"" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1282 */       this.m_comboTitleFormat = new JComboBox<String>(arrayOfString1);
/* 1283 */       OptionsDlg.this.setupFormatComboBox(this.m_comboTitleFormat, arrayOfString1, "print.format.title");
/* 1284 */       gridBagConstraints.anchor = 17;
/* 1285 */       gridBagConstraints.fill = 2;
/* 1286 */       gridBagConstraints.gridx++;
/* 1287 */       gridBagConstraints.gridwidth = 2;
/* 1288 */       gridBagConstraints.weightx = 1.0D;
/* 1289 */       gridBagLayout.setConstraints(this.m_comboTitleFormat, gridBagConstraints);
/* 1290 */       jPanel.add(this.m_comboTitleFormat);
/*      */       
/* 1292 */       JButton jButton = SimpleHTMLDialog.HelpButton("Menu.Help.FormattingCodes", "FormattingCodes.html");
/* 1293 */       gridBagConstraints.anchor = 10;
/* 1294 */       gridBagConstraints.fill = 0;
/* 1295 */       gridBagConstraints.gridx += 2;
/* 1296 */       gridBagConstraints.weightx = 0.0D;
/* 1297 */       gridBagConstraints.gridwidth = 1;
/* 1298 */       gridBagLayout.setConstraints(jButton, gridBagConstraints);
/* 1299 */       jPanel.add(jButton);
/*      */       
/* 1301 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.Printer.Subtitle"));
/* 1302 */       gridBagConstraints.anchor = 13;
/* 1303 */       gridBagConstraints.fill = 0;
/* 1304 */       gridBagConstraints.gridx = 0;
/* 1305 */       gridBagConstraints.gridy++;
/* 1306 */       gridBagConstraints.gridwidth = 1;
/* 1307 */       gridBagConstraints.insets = insets;
/* 1308 */       gridBagConstraints.weightx = 0.0D;
/* 1309 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1310 */       jPanel.add(jLabel);
/*      */       
/* 1312 */       String[] arrayOfString2 = { "", "%c%s", "%l%s" };
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       this.m_comboSubtitleFormat = new JComboBox<String>(arrayOfString2);
/* 1318 */       OptionsDlg.this.setupFormatComboBox(this.m_comboSubtitleFormat, arrayOfString2, "print.format.subtitle");
/* 1319 */       gridBagConstraints.anchor = 17;
/* 1320 */       gridBagConstraints.fill = 2;
/* 1321 */       gridBagConstraints.gridx++;
/* 1322 */       gridBagConstraints.gridwidth = 2;
/* 1323 */       gridBagLayout.setConstraints(this.m_comboSubtitleFormat, gridBagConstraints);
/* 1324 */       jPanel.add(this.m_comboSubtitleFormat);
/*      */       
/* 1326 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.Printer.Footer"));
/* 1327 */       gridBagConstraints.anchor = 13;
/* 1328 */       gridBagConstraints.fill = 0;
/* 1329 */       gridBagConstraints.gridx = 0;
/* 1330 */       gridBagConstraints.gridy++;
/* 1331 */       gridBagConstraints.gridwidth = 1;
/* 1332 */       gridBagConstraints.insets = insets;
/* 1333 */       gridBagConstraints.weightx = 0.0D;
/* 1334 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1335 */       jPanel.add(jLabel);
/*      */       
/* 1337 */       String[] arrayOfString3 = { "", "%l%T %cPage %P of %N %r%dM %dj, %dY", "%l%A %r%G" };
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1342 */       this.m_comboFooterFormat = new JComboBox<String>(arrayOfString3);
/* 1343 */       OptionsDlg.this.setupFormatComboBox(this.m_comboFooterFormat, arrayOfString3, "print.footer.text");
/* 1344 */       gridBagConstraints.anchor = 17;
/* 1345 */       gridBagConstraints.fill = 2;
/* 1346 */       gridBagConstraints.gridx++;
/* 1347 */       gridBagConstraints.gridwidth = 2;
/* 1348 */       gridBagLayout.setConstraints(this.m_comboFooterFormat, gridBagConstraints);
/* 1349 */       jPanel.add(this.m_comboFooterFormat);
/*      */       
/* 1351 */       GenericPropertyAction genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "print.footer.physical.only");
/*      */       
/* 1353 */       StringPropertyCheckBox stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 1354 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.Print.Footer.Physical.Only"));
/*      */ 
/*      */       
/* 1357 */       gridBagConstraints.anchor = 17;
/* 1358 */       gridBagConstraints.gridx += 2;
/* 1359 */       gridBagConstraints.gridwidth = 1;
/* 1360 */       gridBagConstraints.insets = new Insets(param1Dimension.height, param1Dimension.width, param1Dimension.height, param1Dimension.width);
/* 1361 */       gridBagConstraints.weightx = 0.0D;
/* 1362 */       gridBagLayout.setConstraints(stringPropertyCheckBox, gridBagConstraints);
/* 1363 */       jPanel.add(stringPropertyCheckBox);
/*      */       
/* 1365 */       add(jPanel);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void displayProperUnits(boolean param1Boolean) {
/* 1373 */       this.m_labelPrinterMargin.setText(this.m_resources.getString(param1Boolean ? "Label.Tab.Printer.Margin.mm" : "Label.Tab.Printer.Margin.in"));
/*      */       
/* 1375 */       DecimalFormat decimalFormat = new DecimalFormat("0.##");
/*      */       
/* 1377 */       float f = Float.parseFloat(this.m_textfieldMarginTop.getText());
/* 1378 */       if (param1Boolean) {
/* 1379 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1381 */         f = (float)(f / 25.4D);
/* 1382 */       }  this.m_textfieldMarginTop.setText(decimalFormat.format(f));
/*      */       
/* 1384 */       f = Float.parseFloat(this.m_textfieldMarginBottom.getText());
/* 1385 */       if (param1Boolean) {
/* 1386 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1388 */         f = (float)(f / 25.4D);
/* 1389 */       }  this.m_textfieldMarginBottom.setText(decimalFormat.format(f));
/*      */       
/* 1391 */       f = Float.parseFloat(this.m_textfieldMarginLeft.getText());
/* 1392 */       if (param1Boolean) {
/* 1393 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1395 */         f = (float)(f / 25.4D);
/* 1396 */       }  this.m_textfieldMarginLeft.setText(decimalFormat.format(f));
/*      */       
/* 1398 */       f = Float.parseFloat(this.m_textfieldMarginRight.getText());
/* 1399 */       if (param1Boolean) {
/* 1400 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1402 */         f = (float)(f / 25.4D);
/* 1403 */       }  this.m_textfieldMarginRight.setText(decimalFormat.format(f));
/*      */       
/* 1405 */       f = Float.parseFloat(this.m_textfieldMarginColumn.getText());
/* 1406 */       if (param1Boolean) {
/* 1407 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1409 */         f = (float)(f / 25.4D);
/* 1410 */       }  this.m_textfieldMarginColumn.setText(decimalFormat.format(f));
/*      */       
/* 1412 */       f = Float.parseFloat(this.m_textfieldMargin2Column.getText());
/* 1413 */       if (param1Boolean) {
/* 1414 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1416 */         f = (float)(f / 25.4D);
/* 1417 */       }  this.m_textfieldMargin2Column.setText(decimalFormat.format(f));
/*      */       
/* 1419 */       f = Float.parseFloat(this.m_textfieldTabCharExpansion.getText());
/* 1420 */       if (param1Boolean) {
/* 1421 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 1423 */         f = (float)(f / 25.4D);
/* 1424 */       }  this.m_textfieldTabCharExpansion.setText(decimalFormat.format(f));
/*      */       
/* 1426 */       this.m_labelUnits.setText(this.m_resources.getString(param1Boolean ? "Label.mm" : "Label.inches"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSongsPerPage() {
/* 1433 */       return String.valueOf(((PageLayoutItem)this.m_comboPageLayout.getSelectedItem()).getValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getChorusMark() {
/* 1440 */       return ((ChorusMarkItem)this.m_comboChorusMark.getSelectedItem()).getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void loadImages() {
/* 1446 */       this.m_imagesPageLayout = new ImageIcon[5];
/*      */       
/* 1448 */       this.m_imagesPageLayout[0] = SG02App.loadImageIcon("kOne.gif");
/* 1449 */       this.m_imagesPageLayout[1] = SG02App.loadImageIcon("kTwo.gif");
/* 1450 */       this.m_imagesPageLayout[2] = SG02App.loadImageIcon("kFour.gif");
/* 1451 */       this.m_imagesPageLayout[3] = SG02App.loadImageIcon("kContinuousOneCol.gif");
/* 1452 */       this.m_imagesPageLayout[4] = SG02App.loadImageIcon("kContinuousTwoCol.gif");
/*      */     }
/*      */ 
/*      */     
/*      */     protected class PageLayoutItem
/*      */     {
/*      */       String m_name;
/*      */       
/*      */       ImageIcon m_image;
/*      */       int m_value;
/*      */       
/*      */       PageLayoutItem(int param2Int) {
/* 1464 */         this.m_value = param2Int;
/*      */         
/* 1466 */         switch (param2Int) {
/*      */           
/*      */           case 2:
/* 1469 */             this.m_name = OptionsDlg.PrinterTab.this.m_resources.getString("Menu.Tab.Printer.kTwo");
/* 1470 */             this.m_image = OptionsDlg.PrinterTab.this.m_imagesPageLayout[1];
/*      */             return;
/*      */           case 4:
/* 1473 */             this.m_name = OptionsDlg.PrinterTab.this.m_resources.getString("Menu.Tab.Printer.kFour");
/* 1474 */             this.m_image = OptionsDlg.PrinterTab.this.m_imagesPageLayout[2];
/*      */             return;
/*      */           case 10:
/* 1477 */             this.m_name = OptionsDlg.PrinterTab.this.m_resources.getString("Menu.Tab.Printer.kOneColumn");
/* 1478 */             this.m_image = OptionsDlg.PrinterTab.this.m_imagesPageLayout[3];
/*      */             return;
/*      */           case 20:
/* 1481 */             this.m_name = OptionsDlg.PrinterTab.this.m_resources.getString("Menu.Tab.Printer.kTwoColumn");
/* 1482 */             this.m_image = OptionsDlg.PrinterTab.this.m_imagesPageLayout[4];
/*      */             return;
/*      */         } 
/*      */         
/* 1486 */         this.m_name = OptionsDlg.PrinterTab.this.m_resources.getString("Menu.Tab.Printer.kOne");
/* 1487 */         this.m_image = OptionsDlg.PrinterTab.this.m_imagesPageLayout[0];
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String toString() {
/* 1494 */         return this.m_name;
/*      */       }
/*      */ 
/*      */       
/*      */       public int getValue() {
/* 1499 */         return this.m_value;
/*      */       }
/*      */ 
/*      */       
/*      */       public ImageIcon getImage() {
/* 1504 */         return this.m_image;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected class ChorusMarkItem
/*      */     {
/*      */       String m_name;
/*      */       
/*      */       String m_value;
/*      */       
/*      */       ChorusMarkItem(String param2String) {
/* 1516 */         this.m_value = param2String;
/* 1517 */         this.m_name = OptionsDlg.PrinterTab.this.m_resources.getString("Text.ChorusMark." + param2String);
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/* 1522 */         return this.m_name;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getValue() {
/* 1527 */         return this.m_value;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class PageLayoutCellRenderer
/*      */       extends DefaultListCellRenderer
/*      */     {
/*      */       public Component getListCellRendererComponent(JList<?> param2JList, Object param2Object, int param2Int, boolean param2Boolean1, boolean param2Boolean2) {
/* 1546 */         super.getListCellRendererComponent(param2JList, param2Object, param2Int, param2Boolean1, param2Boolean2);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1551 */           setIcon(((OptionsDlg.PrinterTab.PageLayoutItem)param2Object).getImage());
/*      */         }
/* 1553 */         catch (Exception exception) {}
/*      */         
/* 1555 */         return this;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class HTMLTab
/*      */     extends OSXTab
/*      */   {
/*      */     public JTextField m_textfieldLinkName;
/*      */     
/*      */     public JTextField m_textfieldSongBody;
/*      */     
/*      */     public JTextField m_textfieldTitle;
/*      */     
/*      */     public JTextField m_textfieldSubtitle;
/*      */     
/*      */     public JTextField m_textfieldChord;
/*      */     public JTextField m_textfieldLyric;
/*      */     public JTextField m_textfieldComment;
/*      */     public JTextField m_textfieldChorus;
/*      */     public JTextField m_textfieldChorusOverall;
/*      */     public JTextField m_textfieldTab;
/*      */     public JTextField m_textfieldNewSong;
/*      */     public JTextField m_textfieldTOC;
/*      */     public JTextField m_textfieldTOCHeader;
/*      */     public JTextField m_textfieldTOCContents;
/*      */     
/*      */     HTMLTab() {
/* 1584 */       Dimension dimension = getControlSpacing();
/*      */       
/* 1586 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 1587 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 1588 */       setLayout(gridBagLayout);
/*      */       
/* 1590 */       Insets insets1 = new Insets(dimension.height, dimension.width, 0, 0);
/* 1591 */       Insets insets2 = new Insets(dimension.height, dimension.width, 0, dimension.width);
/*      */       
/* 1593 */       ButtonGroup buttonGroup = new ButtonGroup();
/*      */       
/* 1595 */       CSSPropertyAction cSSPropertyAction = new CSSPropertyAction();
/*      */       
/* 1597 */       StringPropertyRadioButton stringPropertyRadioButton = new StringPropertyRadioButton(cSSPropertyAction, "yes");
/* 1598 */       stringPropertyRadioButton.setText(this.m_resources.getString("Radio.Tab.HTML.Linked"));
/* 1599 */       if (!SG02App.isMac)
/* 1600 */         stringPropertyRadioButton.setMnemonic(((Integer)this.m_resources.getObject("Radio.Tab.HTML.Linked.Mn")).intValue()); 
/* 1601 */       gridBagConstraints.anchor = 17;
/* 1602 */       gridBagConstraints.fill = 2;
/* 1603 */       gridBagConstraints.gridx = 0;
/* 1604 */       gridBagConstraints.gridy = 0;
/* 1605 */       gridBagConstraints.gridwidth = 5;
/* 1606 */       gridBagConstraints.insets = insets1;
/* 1607 */       gridBagConstraints.weightx = 0.0D;
/* 1608 */       gridBagConstraints.weighty = 0.0D;
/* 1609 */       gridBagLayout.setConstraints(stringPropertyRadioButton, gridBagConstraints);
/* 1610 */       buttonGroup.add(stringPropertyRadioButton);
/* 1611 */       add(stringPropertyRadioButton);
/*      */ 
/*      */       
/* 1614 */       JLabel jLabel = new JLabel("     ");
/* 1615 */       gridBagConstraints.fill = 0;
/* 1616 */       gridBagConstraints.gridx = 0;
/* 1617 */       gridBagConstraints.gridy++;
/* 1618 */       gridBagConstraints.gridwidth = 1;
/* 1619 */       gridBagConstraints.insets = insets1;
/* 1620 */       gridBagConstraints.weightx = 0.0D;
/* 1621 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1622 */       add(jLabel);
/*      */       
/* 1624 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.LinkName"));
/* 1625 */       gridBagConstraints.anchor = 13;
/* 1626 */       gridBagConstraints.fill = 0;
/* 1627 */       gridBagConstraints.gridx = 1;
/* 1628 */       gridBagConstraints.gridwidth = 1;
/* 1629 */       gridBagConstraints.insets = insets1;
/* 1630 */       gridBagConstraints.weightx = 0.0D;
/* 1631 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1632 */       add(jLabel);
/*      */       
/* 1634 */       this.m_textfieldLinkName = new JTextField();
/* 1635 */       this.m_textfieldLinkName.setText(OptionsDlg.this.m_props.getProperty("html.css.link.name"));
/* 1636 */       gridBagConstraints.anchor = 17;
/* 1637 */       gridBagConstraints.fill = 2;
/* 1638 */       gridBagConstraints.gridx = 2;
/* 1639 */       gridBagConstraints.gridwidth = 3;
/* 1640 */       gridBagConstraints.insets = insets2;
/* 1641 */       gridBagConstraints.weightx = 1.0D;
/* 1642 */       gridBagLayout.setConstraints(this.m_textfieldLinkName, gridBagConstraints);
/* 1643 */       add(this.m_textfieldLinkName);
/*      */       
/* 1645 */       stringPropertyRadioButton = new StringPropertyRadioButton(cSSPropertyAction, "no");
/* 1646 */       stringPropertyRadioButton.setText(this.m_resources.getString("Radio.Tab.HTML.Embedded"));
/* 1647 */       if (!SG02App.isMac)
/* 1648 */         stringPropertyRadioButton.setMnemonic(((Integer)this.m_resources.getObject("Radio.Tab.HTML.Embedded.Mn")).intValue()); 
/* 1649 */       gridBagConstraints.anchor = 17;
/* 1650 */       gridBagConstraints.gridx = 0;
/* 1651 */       gridBagConstraints.gridy++;
/* 1652 */       gridBagConstraints.gridwidth = 5;
/* 1653 */       gridBagConstraints.insets = insets1;
/* 1654 */       gridBagConstraints.weightx = 0.0D;
/* 1655 */       gridBagLayout.setConstraints(stringPropertyRadioButton, gridBagConstraints);
/* 1656 */       buttonGroup.add(stringPropertyRadioButton);
/* 1657 */       add(stringPropertyRadioButton);
/*      */       
/* 1659 */       int i = gridBagConstraints.gridy;
/*      */       
/* 1661 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.SongBody"));
/* 1662 */       gridBagConstraints.anchor = 13;
/* 1663 */       gridBagConstraints.fill = 0;
/* 1664 */       gridBagConstraints.gridx = 1;
/* 1665 */       gridBagConstraints.gridy++;
/* 1666 */       gridBagConstraints.gridwidth = 1;
/* 1667 */       gridBagConstraints.insets = insets1;
/* 1668 */       gridBagConstraints.weightx = 0.0D;
/* 1669 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1670 */       add(jLabel);
/*      */       
/* 1672 */       this.m_textfieldSongBody = new JTextField();
/* 1673 */       this.m_textfieldSongBody.setText(OptionsDlg.this.m_props.getProperty("html.style.song"));
/* 1674 */       gridBagConstraints.anchor = 17;
/* 1675 */       gridBagConstraints.fill = 2;
/* 1676 */       gridBagConstraints.gridx = 2;
/* 1677 */       gridBagConstraints.gridwidth = 1;
/* 1678 */       gridBagConstraints.insets = insets2;
/* 1679 */       gridBagConstraints.weightx = 1.0D;
/* 1680 */       gridBagLayout.setConstraints(this.m_textfieldSongBody, gridBagConstraints);
/* 1681 */       add(this.m_textfieldSongBody);
/*      */       
/* 1683 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Title"));
/* 1684 */       gridBagConstraints.anchor = 13;
/* 1685 */       gridBagConstraints.fill = 0;
/* 1686 */       gridBagConstraints.gridx = 1;
/* 1687 */       gridBagConstraints.gridy++;
/* 1688 */       gridBagConstraints.gridwidth = 1;
/* 1689 */       gridBagConstraints.insets = insets1;
/* 1690 */       gridBagConstraints.weightx = 0.0D;
/* 1691 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1692 */       add(jLabel);
/*      */       
/* 1694 */       this.m_textfieldTitle = new JTextField();
/* 1695 */       this.m_textfieldTitle.setText(OptionsDlg.this.m_props.getProperty("html.style.title"));
/* 1696 */       gridBagConstraints.anchor = 17;
/* 1697 */       gridBagConstraints.fill = 2;
/* 1698 */       gridBagConstraints.gridx = 2;
/* 1699 */       gridBagConstraints.gridwidth = 1;
/* 1700 */       gridBagConstraints.insets = insets2;
/* 1701 */       gridBagConstraints.weightx = 1.0D;
/* 1702 */       gridBagLayout.setConstraints(this.m_textfieldTitle, gridBagConstraints);
/* 1703 */       add(this.m_textfieldTitle);
/*      */       
/* 1705 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Subtitle"));
/* 1706 */       gridBagConstraints.anchor = 13;
/* 1707 */       gridBagConstraints.fill = 0;
/* 1708 */       gridBagConstraints.gridx = 1;
/* 1709 */       gridBagConstraints.gridy++;
/* 1710 */       gridBagConstraints.gridwidth = 1;
/* 1711 */       gridBagConstraints.insets = insets1;
/* 1712 */       gridBagConstraints.weightx = 0.0D;
/* 1713 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1714 */       add(jLabel);
/*      */       
/* 1716 */       this.m_textfieldSubtitle = new JTextField();
/* 1717 */       this.m_textfieldSubtitle.setText(OptionsDlg.this.m_props.getProperty("html.style.subtitle"));
/* 1718 */       gridBagConstraints.anchor = 17;
/* 1719 */       gridBagConstraints.fill = 2;
/* 1720 */       gridBagConstraints.gridx = 2;
/* 1721 */       gridBagConstraints.gridwidth = 1;
/* 1722 */       gridBagConstraints.insets = insets2;
/* 1723 */       gridBagConstraints.weightx = 1.0D;
/* 1724 */       gridBagLayout.setConstraints(this.m_textfieldSubtitle, gridBagConstraints);
/* 1725 */       add(this.m_textfieldSubtitle);
/*      */       
/* 1727 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Chord"));
/* 1728 */       gridBagConstraints.anchor = 13;
/* 1729 */       gridBagConstraints.fill = 0;
/* 1730 */       gridBagConstraints.gridx = 1;
/* 1731 */       gridBagConstraints.gridy++;
/* 1732 */       gridBagConstraints.gridwidth = 1;
/* 1733 */       gridBagConstraints.insets = insets1;
/* 1734 */       gridBagConstraints.weightx = 0.0D;
/* 1735 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1736 */       add(jLabel);
/*      */       
/* 1738 */       this.m_textfieldChord = new JTextField();
/* 1739 */       this.m_textfieldChord.setText(OptionsDlg.this.m_props.getProperty("html.style.chord"));
/* 1740 */       gridBagConstraints.anchor = 17;
/* 1741 */       gridBagConstraints.fill = 2;
/* 1742 */       gridBagConstraints.gridx = 2;
/* 1743 */       gridBagConstraints.gridwidth = 1;
/* 1744 */       gridBagConstraints.insets = insets2;
/* 1745 */       gridBagConstraints.weightx = 1.0D;
/* 1746 */       gridBagLayout.setConstraints(this.m_textfieldChord, gridBagConstraints);
/* 1747 */       add(this.m_textfieldChord);
/*      */       
/* 1749 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Lyric"));
/* 1750 */       gridBagConstraints.anchor = 13;
/* 1751 */       gridBagConstraints.fill = 0;
/* 1752 */       gridBagConstraints.gridx = 1;
/* 1753 */       gridBagConstraints.gridy++;
/* 1754 */       gridBagConstraints.gridwidth = 1;
/* 1755 */       gridBagConstraints.insets = insets1;
/* 1756 */       gridBagConstraints.weightx = 0.0D;
/* 1757 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1758 */       add(jLabel);
/*      */       
/* 1760 */       this.m_textfieldLyric = new JTextField();
/* 1761 */       this.m_textfieldLyric.setText(OptionsDlg.this.m_props.getProperty("html.style.lyric"));
/* 1762 */       gridBagConstraints.anchor = 17;
/* 1763 */       gridBagConstraints.fill = 2;
/* 1764 */       gridBagConstraints.gridx = 2;
/* 1765 */       gridBagConstraints.gridwidth = 1;
/* 1766 */       gridBagConstraints.insets = insets2;
/* 1767 */       gridBagConstraints.weightx = 1.0D;
/* 1768 */       gridBagLayout.setConstraints(this.m_textfieldLyric, gridBagConstraints);
/* 1769 */       add(this.m_textfieldLyric);
/*      */       
/* 1771 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Comment"));
/* 1772 */       gridBagConstraints.anchor = 13;
/* 1773 */       gridBagConstraints.fill = 0;
/* 1774 */       gridBagConstraints.gridx = 1;
/* 1775 */       gridBagConstraints.gridy++;
/* 1776 */       gridBagConstraints.gridwidth = 1;
/* 1777 */       gridBagConstraints.insets = insets1;
/* 1778 */       gridBagConstraints.weightx = 0.0D;
/* 1779 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1780 */       add(jLabel);
/*      */       
/* 1782 */       this.m_textfieldComment = new JTextField();
/* 1783 */       this.m_textfieldComment.setText(OptionsDlg.this.m_props.getProperty("html.style.comment"));
/* 1784 */       gridBagConstraints.anchor = 17;
/* 1785 */       gridBagConstraints.fill = 2;
/* 1786 */       gridBagConstraints.gridx = 2;
/* 1787 */       gridBagConstraints.gridwidth = 1;
/* 1788 */       gridBagConstraints.insets = insets2;
/* 1789 */       gridBagConstraints.weightx = 1.0D;
/* 1790 */       gridBagLayout.setConstraints(this.m_textfieldComment, gridBagConstraints);
/* 1791 */       add(this.m_textfieldComment);
/*      */       
/* 1793 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Tab"));
/* 1794 */       gridBagConstraints.anchor = 13;
/* 1795 */       gridBagConstraints.fill = 0;
/* 1796 */       gridBagConstraints.gridx = 1;
/* 1797 */       gridBagConstraints.gridy++;
/* 1798 */       gridBagConstraints.gridwidth = 1;
/*      */ 
/*      */       
/* 1801 */       gridBagConstraints.insets = new Insets(dimension.height, dimension.width, dimension.height, 0);
/* 1802 */       gridBagConstraints.weightx = 0.0D;
/* 1803 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1804 */       add(jLabel);
/*      */       
/* 1806 */       this.m_textfieldTab = new JTextField();
/* 1807 */       this.m_textfieldTab.setText(OptionsDlg.this.m_props.getProperty("html.style.tab"));
/* 1808 */       gridBagConstraints.anchor = 17;
/* 1809 */       gridBagConstraints.fill = 2;
/* 1810 */       gridBagConstraints.gridx = 2;
/* 1811 */       gridBagConstraints.gridwidth = 1;
/*      */ 
/*      */       
/* 1814 */       gridBagConstraints.insets = new Insets(dimension.height, dimension.width, dimension.height, dimension.width);
/* 1815 */       gridBagConstraints.weightx = 1.0D;
/* 1816 */       gridBagLayout.setConstraints(this.m_textfieldTab, gridBagConstraints);
/* 1817 */       add(this.m_textfieldTab);
/*      */ 
/*      */ 
/*      */       
/* 1821 */       gridBagConstraints.gridy = i;
/* 1822 */       gridBagConstraints.weighty = 0.0D;
/*      */       
/* 1824 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Chorus"));
/* 1825 */       gridBagConstraints.anchor = 13;
/* 1826 */       gridBagConstraints.fill = 0;
/* 1827 */       gridBagConstraints.gridx = 3;
/* 1828 */       gridBagConstraints.gridy++;
/* 1829 */       gridBagConstraints.gridwidth = 1;
/* 1830 */       gridBagConstraints.insets = insets1;
/* 1831 */       gridBagConstraints.weightx = 0.0D;
/* 1832 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1833 */       add(jLabel);
/*      */       
/* 1835 */       this.m_textfieldChorus = new JTextField();
/* 1836 */       this.m_textfieldChorus.setText(OptionsDlg.this.m_props.getProperty("html.style.chorus"));
/* 1837 */       gridBagConstraints.anchor = 17;
/* 1838 */       gridBagConstraints.fill = 2;
/* 1839 */       gridBagConstraints.gridx = 4;
/* 1840 */       gridBagConstraints.gridwidth = 1;
/* 1841 */       gridBagConstraints.insets = insets2;
/* 1842 */       gridBagConstraints.weightx = 1.0D;
/* 1843 */       gridBagLayout.setConstraints(this.m_textfieldChorus, gridBagConstraints);
/* 1844 */       add(this.m_textfieldChorus);
/*      */       
/* 1846 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.Chorus.Overall"));
/* 1847 */       gridBagConstraints.anchor = 13;
/* 1848 */       gridBagConstraints.fill = 0;
/* 1849 */       gridBagConstraints.gridx = 3;
/* 1850 */       gridBagConstraints.gridy++;
/* 1851 */       gridBagConstraints.gridwidth = 1;
/* 1852 */       gridBagConstraints.insets = insets1;
/* 1853 */       gridBagConstraints.weightx = 0.0D;
/* 1854 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1855 */       add(jLabel);
/*      */       
/* 1857 */       this.m_textfieldChorusOverall = new JTextField();
/* 1858 */       this.m_textfieldChorusOverall.setText(OptionsDlg.this.m_props.getProperty("html.style.chorus.overall"));
/* 1859 */       gridBagConstraints.anchor = 17;
/* 1860 */       gridBagConstraints.fill = 2;
/* 1861 */       gridBagConstraints.gridx = 4;
/* 1862 */       gridBagConstraints.gridwidth = 1;
/* 1863 */       gridBagConstraints.insets = insets2;
/* 1864 */       gridBagConstraints.weightx = 1.0D;
/* 1865 */       gridBagLayout.setConstraints(this.m_textfieldChorusOverall, gridBagConstraints);
/* 1866 */       add(this.m_textfieldChorusOverall);
/*      */ 
/*      */ 
/*      */       
/* 1870 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.TOC"));
/* 1871 */       gridBagConstraints.anchor = 13;
/* 1872 */       gridBagConstraints.fill = 0;
/* 1873 */       gridBagConstraints.gridx = 3;
/* 1874 */       gridBagConstraints.gridy++;
/* 1875 */       gridBagConstraints.gridwidth = 1;
/* 1876 */       gridBagConstraints.insets = insets1;
/* 1877 */       gridBagConstraints.weightx = 0.0D;
/* 1878 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1879 */       add(jLabel);
/*      */       
/* 1881 */       this.m_textfieldTOC = new JTextField();
/* 1882 */       this.m_textfieldTOC.setText(OptionsDlg.this.m_props.getProperty("html.style.toc"));
/* 1883 */       gridBagConstraints.anchor = 17;
/* 1884 */       gridBagConstraints.fill = 2;
/* 1885 */       gridBagConstraints.gridx = 4;
/* 1886 */       gridBagConstraints.gridwidth = 1;
/* 1887 */       gridBagConstraints.insets = insets2;
/* 1888 */       gridBagConstraints.weightx = 1.0D;
/* 1889 */       gridBagLayout.setConstraints(this.m_textfieldTOC, gridBagConstraints);
/* 1890 */       add(this.m_textfieldTOC);
/*      */       
/* 1892 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.TOC.Header"));
/* 1893 */       gridBagConstraints.anchor = 13;
/* 1894 */       gridBagConstraints.fill = 0;
/* 1895 */       gridBagConstraints.gridx = 3;
/* 1896 */       gridBagConstraints.gridy++;
/* 1897 */       gridBagConstraints.gridwidth = 1;
/* 1898 */       gridBagConstraints.insets = insets1;
/* 1899 */       gridBagConstraints.weightx = 0.0D;
/* 1900 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1901 */       add(jLabel);
/*      */       
/* 1903 */       this.m_textfieldTOCHeader = new JTextField();
/* 1904 */       this.m_textfieldTOCHeader.setText(OptionsDlg.this.m_props.getProperty("html.style.toc.header"));
/* 1905 */       gridBagConstraints.anchor = 17;
/* 1906 */       gridBagConstraints.fill = 2;
/* 1907 */       gridBagConstraints.gridx = 4;
/* 1908 */       gridBagConstraints.gridwidth = 1;
/* 1909 */       gridBagConstraints.insets = insets2;
/* 1910 */       gridBagConstraints.weightx = 1.0D;
/* 1911 */       gridBagLayout.setConstraints(this.m_textfieldTOCHeader, gridBagConstraints);
/* 1912 */       add(this.m_textfieldTOCHeader);
/*      */       
/* 1914 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.TOC.Contents"));
/* 1915 */       gridBagConstraints.anchor = 13;
/* 1916 */       gridBagConstraints.fill = 0;
/* 1917 */       gridBagConstraints.gridx = 3;
/* 1918 */       gridBagConstraints.gridy++;
/* 1919 */       gridBagConstraints.gridwidth = 1;
/* 1920 */       gridBagConstraints.insets = insets1;
/* 1921 */       gridBagConstraints.weightx = 0.0D;
/* 1922 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1923 */       add(jLabel);
/*      */       
/* 1925 */       this.m_textfieldTOCContents = new JTextField();
/* 1926 */       this.m_textfieldTOCContents.setText(OptionsDlg.this.m_props.getProperty("html.style.toc.contents"));
/* 1927 */       gridBagConstraints.anchor = 17;
/* 1928 */       gridBagConstraints.fill = 2;
/* 1929 */       gridBagConstraints.gridx = 4;
/* 1930 */       gridBagConstraints.gridwidth = 1;
/* 1931 */       gridBagConstraints.insets = insets2;
/* 1932 */       gridBagConstraints.weightx = 1.0D;
/* 1933 */       gridBagLayout.setConstraints(this.m_textfieldTOCContents, gridBagConstraints);
/* 1934 */       add(this.m_textfieldTOCContents);
/*      */ 
/*      */       
/* 1937 */       jLabel = new JLabel(this.m_resources.getString("Label.Tab.HTML.NewSong"));
/* 1938 */       gridBagConstraints.anchor = 13;
/* 1939 */       gridBagConstraints.fill = 0;
/* 1940 */       gridBagConstraints.gridx = 3;
/* 1941 */       gridBagConstraints.gridy++;
/* 1942 */       gridBagConstraints.gridwidth = 1;
/* 1943 */       gridBagConstraints.insets = insets1;
/* 1944 */       gridBagConstraints.weightx = 0.0D;
/* 1945 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 1946 */       add(jLabel);
/*      */       
/* 1948 */       this.m_textfieldNewSong = new JTextField();
/* 1949 */       this.m_textfieldNewSong.setText(OptionsDlg.this.m_props.getProperty("html.style.newsong"));
/* 1950 */       gridBagConstraints.anchor = 17;
/* 1951 */       gridBagConstraints.fill = 2;
/* 1952 */       gridBagConstraints.gridx = 4;
/* 1953 */       gridBagConstraints.gridwidth = 1;
/* 1954 */       gridBagConstraints.insets = insets2;
/* 1955 */       gridBagConstraints.weightx = 1.0D;
/* 1956 */       gridBagLayout.setConstraints(this.m_textfieldNewSong, gridBagConstraints);
/* 1957 */       add(this.m_textfieldNewSong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2028 */       cSSPropertyAction.updateFromNewProps(OptionsDlg.this.m_props);
/*      */ 
/*      */       
/* 2031 */       this.m_textfieldLinkName.setColumns(10);
/* 2032 */       this.m_textfieldSongBody.setColumns(10);
/* 2033 */       this.m_textfieldTitle.setColumns(10);
/* 2034 */       this.m_textfieldSubtitle.setColumns(10);
/* 2035 */       this.m_textfieldChord.setColumns(10);
/* 2036 */       this.m_textfieldLyric.setColumns(10);
/* 2037 */       this.m_textfieldComment.setColumns(10);
/* 2038 */       this.m_textfieldChorus.setColumns(10);
/* 2039 */       this.m_textfieldChorusOverall.setColumns(10);
/* 2040 */       this.m_textfieldTab.setColumns(10);
/* 2041 */       this.m_textfieldNewSong.setColumns(10);
/* 2042 */       this.m_textfieldTOC.setColumns(10);
/* 2043 */       this.m_textfieldTOCHeader.setColumns(10);
/* 2044 */       this.m_textfieldTOCContents.setColumns(10);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class CSSPropertyAction
/*      */       extends GenericPropertyAction
/*      */     {
/*      */       public CSSPropertyAction() {
/* 2055 */         super(OptionsDlg.this.m_props, "html.css.link");
/*      */       }
/*      */ 
/*      */       
/*      */       public void actionPerformed(ActionEvent param2ActionEvent) {
/* 2060 */         super.actionPerformed(param2ActionEvent);
/*      */         
/* 2062 */         OptionsDlg.HTMLTab.this.m_textfieldLinkName.setEditable(this.m_booleanValue);
/*      */         
/* 2064 */         OptionsDlg.HTMLTab.this.m_textfieldTitle.setEditable(!this.m_booleanValue);
/* 2065 */         OptionsDlg.HTMLTab.this.m_textfieldSongBody.setEditable(!this.m_booleanValue);
/* 2066 */         OptionsDlg.HTMLTab.this.m_textfieldSubtitle.setEditable(!this.m_booleanValue);
/* 2067 */         OptionsDlg.HTMLTab.this.m_textfieldChord.setEditable(!this.m_booleanValue);
/* 2068 */         OptionsDlg.HTMLTab.this.m_textfieldLyric.setEditable(!this.m_booleanValue);
/* 2069 */         OptionsDlg.HTMLTab.this.m_textfieldComment.setEditable(!this.m_booleanValue);
/* 2070 */         OptionsDlg.HTMLTab.this.m_textfieldChorus.setEditable(!this.m_booleanValue);
/* 2071 */         OptionsDlg.HTMLTab.this.m_textfieldChorusOverall.setEditable(!this.m_booleanValue);
/* 2072 */         OptionsDlg.HTMLTab.this.m_textfieldTab.setEditable(!this.m_booleanValue);
/* 2073 */         OptionsDlg.HTMLTab.this.m_textfieldNewSong.setEditable(!this.m_booleanValue);
/* 2074 */         OptionsDlg.HTMLTab.this.m_textfieldTOC.setEditable(!this.m_booleanValue);
/* 2075 */         OptionsDlg.HTMLTab.this.m_textfieldTOCHeader.setEditable(!this.m_booleanValue);
/* 2076 */         OptionsDlg.HTMLTab.this.m_textfieldTOCContents.setEditable(!this.m_booleanValue);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class FullScreenTab
/*      */     extends OSXTab
/*      */   {
/*      */     protected JCheckBox m_checkboxBlankFirst;
/*      */     
/*      */     protected JCheckBox m_checkboxBlankBetween;
/*      */     
/*      */     protected JCheckBox m_checkboxBlankLast;
/*      */     
/*      */     protected JCheckBox m_checkboxBlankProjector;
/*      */     
/*      */     protected JCheckBox m_checkboxControlMouse;
/*      */     
/*      */     protected JCheckBox m_checkboxControlShow;
/*      */     
/*      */     protected JComboBox<String> m_comboDevice;
/*      */     
/*      */     public JTextField m_textfieldMarginHorz;
/*      */     public JTextField m_textfieldMarginVert;
/*      */     public JTextField m_textfieldImage;
/*      */     public JTextField m_textfieldTabCharExpansion;
/*      */     public JComboBox<String> m_comboFooterFormat;
/*      */     
/*      */     FullScreenTab() {
/* 2106 */       Dimension dimension = getControlSpacing();
/*      */       
/* 2108 */       Insets insets1 = new Insets(dimension.height, dimension.width, 0, 0);
/* 2109 */       Insets insets2 = new Insets(dimension.height, dimension.width, 0, dimension.width);
/*      */       
/* 2111 */       setLayout(new BoxLayout(this, 1));
/*      */ 
/*      */ 
/*      */       
/* 2115 */       JPanel jPanel1 = new JPanel();
/* 2116 */       add(jPanel1);
/*      */       
/* 2118 */       GridBagLayout gridBagLayout2 = new GridBagLayout();
/* 2119 */       GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
/* 2120 */       jPanel1.setLayout(gridBagLayout2);
/*      */ 
/*      */       
/* 2123 */       GenericPropertyAction genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.songs.per.page");
/*      */ 
/*      */       
/* 2126 */       JLabel jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Columns"));
/* 2127 */       gridBagConstraints2.anchor = 13;
/* 2128 */       gridBagConstraints2.fill = 0;
/* 2129 */       gridBagConstraints2.gridx = 0;
/* 2130 */       gridBagConstraints2.gridy = 0;
/* 2131 */       gridBagConstraints2.gridwidth = 2;
/* 2132 */       gridBagConstraints2.insets = insets1;
/* 2133 */       gridBagLayout2.setConstraints(jLabel2, gridBagConstraints2);
/* 2134 */       jPanel1.add(jLabel2);
/*      */       
/* 2136 */       ButtonGroup buttonGroup = new ButtonGroup();
/* 2137 */       IntegerPropertyRadioButton integerPropertyRadioButton = new IntegerPropertyRadioButton(genericPropertyAction, 1);
/* 2138 */       integerPropertyRadioButton.setText(this.m_resources.getString("Radio.Tab.FullScreen.Columns.One"));
/*      */ 
/*      */       
/* 2141 */       buttonGroup.add(integerPropertyRadioButton);
/* 2142 */       gridBagConstraints2.anchor = 17;
/* 2143 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2144 */       gridBagConstraints2.gridwidth = 1;
/* 2145 */       gridBagLayout2.setConstraints(integerPropertyRadioButton, gridBagConstraints2);
/* 2146 */       jPanel1.add(integerPropertyRadioButton);
/*      */       
/* 2148 */       integerPropertyRadioButton = new IntegerPropertyRadioButton(genericPropertyAction, 2);
/* 2149 */       integerPropertyRadioButton.setText(this.m_resources.getString("Radio.Tab.FullScreen.Columns.Two"));
/*      */ 
/*      */       
/* 2152 */       buttonGroup.add(integerPropertyRadioButton);
/* 2153 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2154 */       gridBagLayout2.setConstraints(integerPropertyRadioButton, gridBagConstraints2);
/* 2155 */       jPanel1.add(integerPropertyRadioButton);
/*      */       
/* 2157 */       int i = ++gridBagConstraints2.gridy;
/*      */ 
/*      */       
/* 2160 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.blank.first");
/* 2161 */       StringPropertyCheckBox stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2162 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.Blank.First"));
/* 2163 */       if (!SG02App.isMac)
/* 2164 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.Blank.First.Mn")).intValue()); 
/* 2165 */       gridBagConstraints2.anchor = 17;
/* 2166 */       gridBagConstraints2.gridx = 1;
/* 2167 */       gridBagConstraints2.gridy = i;
/* 2168 */       gridBagConstraints2.gridwidth = 2;
/* 2169 */       gridBagConstraints2.insets = insets1;
/* 2170 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2171 */       jPanel1.add(stringPropertyCheckBox);
/*      */       
/* 2173 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.blank.last");
/* 2174 */       stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2175 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.Blank.Last"));
/* 2176 */       if (!SG02App.isMac)
/* 2177 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.Blank.Last.Mn")).intValue()); 
/* 2178 */       gridBagConstraints2.anchor = 17;
/* 2179 */       gridBagConstraints2.gridy++;
/* 2180 */       gridBagConstraints2.insets = insets1;
/* 2181 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2182 */       jPanel1.add(stringPropertyCheckBox);
/*      */       
/* 2184 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.control.mouse");
/* 2185 */       stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2186 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.Control.Mouse"));
/* 2187 */       if (!SG02App.isMac)
/* 2188 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.Control.Mouse.Mn")).intValue()); 
/* 2189 */       gridBagConstraints2.anchor = 17;
/* 2190 */       gridBagConstraints2.gridy++;
/* 2191 */       gridBagConstraints2.insets = insets1;
/* 2192 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2193 */       jPanel1.add(stringPropertyCheckBox);
/*      */ 
/*      */       
/* 2196 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.blank.between");
/* 2197 */       stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2198 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.Blank.Between"));
/* 2199 */       if (!SG02App.isMac)
/* 2200 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.Blank.Between.Mn")).intValue()); 
/* 2201 */       gridBagConstraints2.anchor = 17;
/* 2202 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2203 */       gridBagConstraints2.gridy = i;
/* 2204 */       gridBagConstraints2.gridwidth = 2;
/* 2205 */       gridBagConstraints2.insets = insets2;
/* 2206 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2207 */       jPanel1.add(stringPropertyCheckBox);
/*      */       
/* 2209 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.blank.projector");
/* 2210 */       stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2211 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.Blank.Projector"));
/* 2212 */       if (!SG02App.isMac)
/* 2213 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.Blank.Projector.Mn")).intValue()); 
/* 2214 */       gridBagConstraints2.anchor = 17;
/* 2215 */       gridBagConstraints2.gridy++;
/* 2216 */       gridBagConstraints2.insets = insets2;
/* 2217 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2218 */       jPanel1.add(stringPropertyCheckBox);
/*      */       
/* 2220 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.control.show");
/* 2221 */       stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2222 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.Control.Show"));
/* 2223 */       if (!SG02App.isMac)
/* 2224 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.Control.Show.Mn")).intValue()); 
/* 2225 */       gridBagConstraints2.anchor = 17;
/* 2226 */       gridBagConstraints2.gridy++;
/* 2227 */       gridBagConstraints2.insets = insets2;
/* 2228 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2229 */       jPanel1.add(stringPropertyCheckBox);
/*      */       
/* 2231 */       genericPropertyAction = new GenericPropertyAction(OptionsDlg.this.m_props, "fullscreen.start.@.selected");
/* 2232 */       stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "yes", "no");
/* 2233 */       stringPropertyCheckBox.setText(this.m_resources.getString("Command.Tab.FullScreen.StartFromSelected"));
/* 2234 */       if (!SG02App.isMac)
/* 2235 */         stringPropertyCheckBox.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.FullScreen.StartFromSelected.Mn")).intValue()); 
/* 2236 */       gridBagConstraints2.anchor = 17;
/* 2237 */       gridBagConstraints2.gridy++;
/* 2238 */       gridBagConstraints2.insets = insets2;
/* 2239 */       gridBagLayout2.setConstraints(stringPropertyCheckBox, gridBagConstraints2);
/* 2240 */       jPanel1.add(stringPropertyCheckBox);
/*      */       
/* 2242 */       i = ++gridBagConstraints2.gridy;
/*      */ 
/*      */       
/* 2245 */       JPanel jPanel2 = new JPanel();
/* 2246 */       gridBagConstraints2.gridx = 1;
/* 2247 */       gridBagConstraints2.gridy++;
/* 2248 */       gridBagConstraints2.gridwidth = 4;
/* 2249 */       gridBagLayout2.setConstraints(jPanel2, gridBagConstraints2);
/* 2250 */       jPanel1.add(jPanel2);
/*      */       
/* 2252 */       GridBagLayout gridBagLayout3 = new GridBagLayout();
/*      */       
/* 2254 */       jPanel2.setLayout(gridBagLayout3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2259 */       jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Device"));
/* 2260 */       gridBagConstraints2.anchor = 13;
/* 2261 */       gridBagConstraints2.fill = 0;
/* 2262 */       gridBagConstraints2.gridx = 1;
/* 2263 */       gridBagConstraints2.gridy++;
/* 2264 */       gridBagConstraints2.gridwidth = 1;
/* 2265 */       gridBagConstraints2.insets = insets1;
/* 2266 */       gridBagLayout3.setConstraints(jLabel2, gridBagConstraints2);
/* 2267 */       jPanel2.add(jLabel2);
/*      */       
/* 2269 */       this.m_comboDevice = new JComboBox<String>();
/*      */       
/* 2271 */       GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 2272 */       GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
/* 2273 */       GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*      */       int j;
/* 2275 */       for (j = 0; j < arrayOfGraphicsDevice.length; j++) {
/*      */         
/* 2277 */         if (arrayOfGraphicsDevice[j] == graphicsDevice) {
/* 2278 */           this.m_comboDevice.addItem(this.m_resources.getString("Label.Tab.FullScreen.MainScreen"));
/*      */         } else {
/*      */           
/* 2281 */           String str = this.m_resources.getString("Label.Tab.FullScreen.SecondaryScreen");
/* 2282 */           str = str + " ";
/* 2283 */           str = str + arrayOfGraphicsDevice[j].getIDstring();
/* 2284 */           this.m_comboDevice.addItem(str);
/*      */         } 
/*      */       } 
/*      */       
/* 2288 */       j = Integer.parseInt(SG02App.props.getProperty("fullscreen.device"));
/* 2289 */       if (arrayOfGraphicsDevice.length < j + 1) {
/* 2290 */         j = 0;
/*      */       }
/* 2292 */       this.m_comboDevice.setSelectedIndex(j);
/*      */       
/* 2294 */       gridBagConstraints2.anchor = 17;
/* 2295 */       gridBagConstraints2.fill = 2;
/* 2296 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2297 */       gridBagConstraints2.gridwidth = 3;
/* 2298 */       gridBagConstraints2.insets = insets2;
/* 2299 */       gridBagLayout3.setConstraints(this.m_comboDevice, gridBagConstraints2);
/* 2300 */       jPanel2.add(this.m_comboDevice);
/*      */ 
/*      */ 
/*      */       
/* 2304 */       jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Margins"));
/* 2305 */       gridBagConstraints2.anchor = 13;
/* 2306 */       gridBagConstraints2.fill = 0;
/* 2307 */       gridBagConstraints2.gridx = 0;
/* 2308 */       gridBagConstraints2.gridy++;
/* 2309 */       gridBagConstraints2.gridwidth = 2;
/* 2310 */       gridBagConstraints2.insets = insets1;
/* 2311 */       gridBagLayout3.setConstraints(jLabel2, gridBagConstraints2);
/* 2312 */       jPanel2.add(jLabel2);
/*      */       
/* 2314 */       jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Horz"));
/* 2315 */       gridBagConstraints2.anchor = 13;
/* 2316 */       gridBagConstraints2.gridx = 1;
/* 2317 */       gridBagConstraints2.gridwidth = 1;
/* 2318 */       gridBagConstraints2.gridy++;
/* 2319 */       gridBagConstraints2.insets = insets1;
/* 2320 */       gridBagLayout3.setConstraints(jLabel2, gridBagConstraints2);
/* 2321 */       jPanel2.add(jLabel2);
/*      */       
/* 2323 */       this.m_textfieldMarginHorz = new JTextField();
/* 2324 */       this.m_textfieldMarginHorz.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2325 */       this.m_textfieldMarginHorz.setText(OptionsDlg.this.m_props.getProperty("fullscreen.margin.percent.horz"));
/* 2326 */       gridBagConstraints2.anchor = 17;
/* 2327 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2328 */       gridBagConstraints2.gridwidth = 1;
/* 2329 */       gridBagLayout3.setConstraints(this.m_textfieldMarginHorz, gridBagConstraints2);
/* 2330 */       jPanel2.add(this.m_textfieldMarginHorz);
/*      */       
/* 2332 */       jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Vert"));
/* 2333 */       gridBagConstraints2.anchor = 13;
/* 2334 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2335 */       gridBagLayout3.setConstraints(jLabel2, gridBagConstraints2);
/* 2336 */       jPanel2.add(jLabel2);
/*      */       
/* 2338 */       this.m_textfieldMarginVert = new JTextField();
/* 2339 */       this.m_textfieldMarginVert.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2340 */       this.m_textfieldMarginVert.setText(OptionsDlg.this.m_props.getProperty("fullscreen.margin.percent.vert"));
/* 2341 */       gridBagConstraints2.anchor = 17;
/* 2342 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2343 */       gridBagConstraints2.insets = insets2;
/* 2344 */       gridBagLayout3.setConstraints(this.m_textfieldMarginVert, gridBagConstraints2);
/* 2345 */       jPanel2.add(this.m_textfieldMarginVert);
/*      */ 
/*      */       
/* 2348 */       jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.Printer.Tab.Chars"));
/* 2349 */       gridBagConstraints2.anchor = 13;
/* 2350 */       gridBagConstraints2.fill = 0;
/* 2351 */       gridBagConstraints2.gridx = 1;
/* 2352 */       gridBagConstraints2.gridwidth = 1;
/* 2353 */       gridBagConstraints2.gridy++;
/* 2354 */       gridBagConstraints2.insets = insets1;
/* 2355 */       gridBagLayout3.setConstraints(jLabel2, gridBagConstraints2);
/* 2356 */       jPanel2.add(jLabel2);
/*      */       
/* 2358 */       this.m_textfieldTabCharExpansion = new JTextField();
/* 2359 */       this.m_textfieldTabCharExpansion.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2360 */       this.m_textfieldTabCharExpansion.setText(OptionsDlg.this.m_props.getProperty("fullscreen.chars.tab.percent"));
/* 2361 */       gridBagConstraints2.fill = 2;
/* 2362 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2363 */       gridBagConstraints2.gridwidth = 1;
/* 2364 */       gridBagLayout3.setConstraints(this.m_textfieldTabCharExpansion, gridBagConstraints2);
/* 2365 */       jPanel2.add(this.m_textfieldTabCharExpansion);
/*      */       
/* 2367 */       jLabel2 = new JLabel(this.m_resources.getString("Label.percent.screen"));
/* 2368 */       gridBagConstraints2.anchor = 17;
/* 2369 */       gridBagConstraints2.fill = 0;
/* 2370 */       gridBagConstraints2.gridx += gridBagConstraints2.gridwidth;
/* 2371 */       gridBagConstraints2.gridwidth = 2;
/* 2372 */       gridBagConstraints2.insets = insets1;
/* 2373 */       gridBagLayout3.setConstraints(jLabel2, gridBagConstraints2);
/* 2374 */       jPanel2.add(jLabel2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2380 */       jPanel1 = new JPanel(new FlowLayout(1));
/* 2381 */       add(jPanel1);
/*      */       
/* 2383 */       final JButton setFontsButton = new JButton();
/* 2384 */       setFontsButton.setAction(new AbstractAction(this.m_resources
/* 2385 */             .getString("Command.Tab.Print.SetFonts"))
/*      */           {
/*      */             
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 2390 */               Color color = new Color(Integer.parseInt(OptionsDlg.this.m_props.getProperty("fullscreen.color.background"), 16));
/* 2391 */               FontSettingsDialog.showFontSettingsDialog(setFontsButton, "fullscreen", "print", OptionsDlg.this.m_props, color);
/*      */             }
/*      */           });
/*      */       
/* 2395 */       jPanel1.add(setFontsButton);
/*      */       
/* 2397 */       final JButton fgButton = new JButton();
/* 2398 */       fgButton.setAction(new AbstractAction(this.m_resources
/* 2399 */             .getString("Command.Tab.FullScreen.Foreground"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 2403 */               Color color1 = new Color(Integer.parseInt(OptionsDlg.this.m_props.getProperty("fullscreen.font.color.normal"), 16));
/* 2404 */               Color color2 = JColorChooser.showDialog(fgButton, OptionsDlg.FullScreenTab.this.m_resources.getString("Command.Tab.FullScreen.Foreground"), color1);
/* 2405 */               if (null != color2) {
/*      */                 
/* 2407 */                 String str = Integer.toHexString(color2.getRGB() & 0xFFFFFF);
/* 2408 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.chord", str);
/* 2409 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.comment", str);
/* 2410 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.footer", str);
/* 2411 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.grid", str);
/* 2412 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.normal", str);
/* 2413 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.subtitle", str);
/* 2414 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.title", str);
/* 2415 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.tab", str);
/* 2416 */                 OptionsDlg.this.m_props.setProperty("fullscreen.font.color.comment.guitar", str);
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/* 2421 */       jPanel1.add(fgButton);
/*      */       
/* 2423 */       final JButton bgButton = new JButton();
/* 2424 */       bgButton.setAction(new AbstractAction(this.m_resources
/* 2425 */             .getString("Command.Tab.FullScreen.Background"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 2429 */               Color color1 = new Color(Integer.parseInt(OptionsDlg.this.m_props.getProperty("fullscreen.color.background"), 16));
/* 2430 */               Color color2 = JColorChooser.showDialog(bgButton, OptionsDlg.FullScreenTab.this.m_resources.getString("Command.Tab.FullScreen.Background"), color1);
/* 2431 */               if (null != color2)
/*      */               {
/* 2433 */                 OptionsDlg.this.m_props.setProperty("fullscreen.color.background", Integer.toHexString(color2.getRGB() & 0xFFFFFF));
/*      */               }
/*      */             }
/*      */           });
/*      */       
/* 2438 */       jPanel1.add(bgButton);
/*      */ 
/*      */ 
/*      */       
/* 2442 */       jPanel1 = new JPanel();
/* 2443 */       add(jPanel1);
/*      */       
/* 2445 */       GridBagLayout gridBagLayout1 = new GridBagLayout();
/* 2446 */       GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
/* 2447 */       jPanel1.setLayout(gridBagLayout1);
/*      */       
/* 2449 */       gridBagConstraints1.gridx = 0;
/* 2450 */       gridBagConstraints1.gridy = 0;
/*      */ 
/*      */       
/* 2453 */       JLabel jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Image"));
/* 2454 */       gridBagConstraints1.anchor = 13;
/* 2455 */       gridBagConstraints1.fill = 0;
/* 2456 */       gridBagConstraints1.gridx = 0;
/* 2457 */       gridBagConstraints1.gridy++;
/* 2458 */       gridBagConstraints1.gridwidth = 2;
/* 2459 */       gridBagConstraints1.insets = insets1;
/* 2460 */       gridBagLayout1.setConstraints(jLabel1, gridBagConstraints1);
/* 2461 */       jPanel1.add(jLabel1);
/*      */       
/* 2463 */       this.m_textfieldImage = new JTextField();
/* 2464 */       this.m_textfieldImage.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldSongsPath")).intValue());
/* 2465 */       this.m_textfieldImage.setText(OptionsDlg.this.m_props.getProperty("fullscreen.image.background"));
/* 2466 */       gridBagConstraints1.anchor = 17;
/* 2467 */       gridBagConstraints1.fill = 2;
/* 2468 */       gridBagConstraints1.gridx += gridBagConstraints1.gridwidth;
/* 2469 */       gridBagConstraints1.gridwidth = 2;
/* 2470 */       gridBagConstraints1.insets = insets1;
/* 2471 */       gridBagLayout1.setConstraints(this.m_textfieldImage, gridBagConstraints1);
/* 2472 */       jPanel1.add(this.m_textfieldImage);
/*      */       
/* 2474 */       final JButton browseButton = new JButton();
/* 2475 */       browseButton.setAction(new AbstractAction(this.m_resources
/* 2476 */             .getString("Command.Tab.General.Browse.SongPath"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 2480 */               JFileChooser jFileChooser = new JFileChooser();
/* 2481 */               String str = SG02App.props.getProperty("fullscreen.image.background");
/* 2482 */               if (null != str) {
/*      */                 
/* 2484 */                 jFileChooser.setSelectedFile(new File(str));
/* 2485 */                 jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */               } 
/*      */               
/* 2488 */               int i = jFileChooser.showOpenDialog(browseButton);
/*      */               
/* 2490 */               if (0 == i) {
/* 2491 */                 OptionsDlg.FullScreenTab.this.m_textfieldImage.setText(jFileChooser.getSelectedFile().toString());
/*      */               }
/*      */             }
/*      */           });
/* 2495 */       gridBagConstraints1.anchor = 17;
/* 2496 */       gridBagConstraints1.fill = 0;
/* 2497 */       gridBagConstraints1.gridx += gridBagConstraints1.gridwidth;
/* 2498 */       gridBagConstraints1.gridwidth = 1;
/* 2499 */       gridBagConstraints1.insets = insets2;
/* 2500 */       gridBagLayout1.setConstraints(browseButton, gridBagConstraints1);
/* 2501 */       jPanel1.add(browseButton);
/*      */ 
/*      */       
/* 2504 */       jLabel1 = new JLabel(this.m_resources.getString("Label.Tab.FullScreen.Footer"));
/* 2505 */       gridBagConstraints1.anchor = 13;
/* 2506 */       gridBagConstraints1.fill = 0;
/* 2507 */       gridBagConstraints1.gridx = 0;
/* 2508 */       gridBagConstraints1.gridy++;
/* 2509 */       gridBagConstraints1.gridwidth = 2;
/* 2510 */       gridBagConstraints1.insets = insets1;
/* 2511 */       gridBagLayout1.setConstraints(jLabel1, gridBagConstraints1);
/* 2512 */       jPanel1.add(jLabel1);
/*      */       
/* 2514 */       String[] arrayOfString = { "", "%l%T %r%A", "%l%A %r%G" };
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2519 */       this.m_comboFooterFormat = new JComboBox<String>(arrayOfString);
/* 2520 */       OptionsDlg.this.setupFormatComboBox(this.m_comboFooterFormat, arrayOfString, "fullscreen.footer.text");
/* 2521 */       gridBagConstraints1.anchor = 17;
/* 2522 */       gridBagConstraints1.fill = 2;
/* 2523 */       gridBagConstraints1.gridx += gridBagConstraints1.gridwidth;
/* 2524 */       gridBagConstraints1.gridwidth = 2;
/* 2525 */       gridBagLayout1.setConstraints(this.m_comboFooterFormat, gridBagConstraints1);
/* 2526 */       jPanel1.add(this.m_comboFooterFormat);
/*      */       
/* 2528 */       JButton jButton5 = SimpleHTMLDialog.HelpButton("Menu.Help.FormattingCodes", "FormattingCodes.html");
/* 2529 */       gridBagConstraints1.anchor = 10;
/* 2530 */       gridBagConstraints1.fill = 0;
/* 2531 */       gridBagConstraints1.gridx += 2;
/* 2532 */       gridBagConstraints1.weightx = 0.0D;
/* 2533 */       gridBagConstraints1.gridwidth = 1;
/* 2534 */       gridBagLayout1.setConstraints(jButton5, gridBagConstraints1);
/* 2535 */       jPanel1.add(jButton5);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class ChordsTab
/*      */     extends OSXTab
/*      */   {
/*      */     public JCheckBox m_checkboxGrids;
/*      */     
/*      */     public JCheckBox m_checkboxDoReMi;
/*      */     
/*      */     public StringPropertyRadioButton m_radioGuitar;
/*      */     
/*      */     public StringPropertyRadioButton m_radioUkulele;
/*      */     public StringPropertyCheckBox m_checkboxUnfriendlyKeys;
/*      */     public JTextField m_textfieldFriendlyKeys;
/*      */     public JTextField m_textfieldChordsNoGrids;
/*      */     public JLabel m_labelGridSize;
/*      */     public JTextField m_textfieldGridMin;
/*      */     public JTextField m_textfieldGridMax;
/*      */     public JTextField m_textfieldGridSpacing;
/*      */     public JTextField m_textfieldCapoMax;
/*      */     public JTextField m_textfieldCapoKeys;
/*      */     
/*      */     ChordsTab() {
/* 2561 */       PrintChordsPropertyAction printChordsPropertyAction = new PrintChordsPropertyAction();
/* 2562 */       PrintGridsPropertyAction printGridsPropertyAction = new PrintGridsPropertyAction(printChordsPropertyAction);
/* 2563 */       UseCapoPropertyAction useCapoPropertyAction = new UseCapoPropertyAction();
/*      */       
/* 2565 */       Dimension dimension = getControlSpacing();
/*      */       
/* 2567 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2568 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 2569 */       setLayout(gridBagLayout);
/* 2570 */       gridBagConstraints.weightx = 0.0D;
/*      */       
/* 2572 */       Insets insets = new Insets(dimension.height, dimension.width, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2577 */       StringPropertyCheckBox stringPropertyCheckBox2 = new StringPropertyCheckBox(printChordsPropertyAction, "yes", "no");
/* 2578 */       stringPropertyCheckBox2.setText(this.m_resources.getString("Command.Tab.General.PrintChords"));
/* 2579 */       if (!SG02App.isMac)
/* 2580 */         stringPropertyCheckBox2.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.PrintChords.Mn")).intValue()); 
/* 2581 */       gridBagConstraints.anchor = 17;
/* 2582 */       gridBagConstraints.gridx = 0;
/* 2583 */       gridBagConstraints.gridy++;
/* 2584 */       gridBagConstraints.gridwidth = 4;
/* 2585 */       gridBagConstraints.insets = insets;
/* 2586 */       gridBagLayout.setConstraints(stringPropertyCheckBox2, gridBagConstraints);
/* 2587 */       add(stringPropertyCheckBox2);
/*      */ 
/*      */       
/* 2590 */       this.m_checkboxGrids = new StringPropertyCheckBox(printGridsPropertyAction, "yes", "no");
/* 2591 */       this.m_checkboxGrids.setText(this.m_resources.getString("Command.Tab.General.PrintGrids"));
/* 2592 */       if (!SG02App.isMac)
/* 2593 */         this.m_checkboxGrids.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.PrintGrids.Mn")).intValue()); 
/* 2594 */       gridBagConstraints.anchor = 17;
/* 2595 */       gridBagConstraints.gridx = 1;
/* 2596 */       gridBagConstraints.gridy++;
/* 2597 */       gridBagConstraints.gridwidth = 4;
/* 2598 */       gridBagConstraints.insets = insets;
/* 2599 */       gridBagLayout.setConstraints(this.m_checkboxGrids, gridBagConstraints);
/* 2600 */       add(this.m_checkboxGrids);
/*      */ 
/*      */ 
/*      */       
/* 2604 */       ButtonGroup buttonGroup = new ButtonGroup();
/*      */       
/* 2606 */       GuitarUkulelePropertyAction guitarUkulelePropertyAction = new GuitarUkulelePropertyAction();
/*      */       
/* 2608 */       this.m_radioGuitar = new StringPropertyRadioButton(guitarUkulelePropertyAction, "no");
/* 2609 */       this.m_radioGuitar.setText(this.m_resources.getString("Command.Tab.Chords.Guitar"));
/* 2610 */       if (!SG02App.isMac)
/* 2611 */         this.m_radioGuitar.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Chords.Guitar.Mn")).intValue()); 
/* 2612 */       gridBagConstraints.anchor = 17;
/* 2613 */       gridBagConstraints.fill = 2;
/* 2614 */       gridBagConstraints.gridx = 2;
/* 2615 */       gridBagConstraints.gridy++;
/* 2616 */       gridBagConstraints.gridwidth = 1;
/* 2617 */       gridBagConstraints.insets = insets;
/* 2618 */       gridBagLayout.setConstraints(this.m_radioGuitar, gridBagConstraints);
/* 2619 */       buttonGroup.add(this.m_radioGuitar);
/* 2620 */       add(this.m_radioGuitar);
/*      */ 
/*      */       
/* 2623 */       this.m_radioUkulele = new StringPropertyRadioButton(guitarUkulelePropertyAction, "yes");
/* 2624 */       this.m_radioUkulele.setText(this.m_resources.getString("Command.Tab.Chords.Ukulele"));
/* 2625 */       if (!SG02App.isMac)
/* 2626 */         this.m_radioUkulele.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Chords.Ukulele.Mn")).intValue()); 
/* 2627 */       gridBagConstraints.gridx += gridBagConstraints.gridwidth;
/* 2628 */       gridBagLayout.setConstraints(this.m_radioUkulele, gridBagConstraints);
/* 2629 */       buttonGroup.add(this.m_radioUkulele);
/* 2630 */       add(this.m_radioUkulele);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2635 */       GenericPropertyAction genericPropertyAction2 = new GenericPropertyAction(OptionsDlg.this.m_props, "grids.print.unfriendly");
/* 2636 */       this.m_checkboxUnfriendlyKeys = new StringPropertyCheckBox(genericPropertyAction2, "no", "yes");
/* 2637 */       this.m_checkboxUnfriendlyKeys.setText(this.m_resources.getString("Command.Tab.Chord.FriendlyGrids"));
/* 2638 */       if (!SG02App.isMac)
/* 2639 */         this.m_checkboxUnfriendlyKeys.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Chord.FriendlyGrids.Mn")).intValue()); 
/* 2640 */       gridBagConstraints.anchor = 17;
/* 2641 */       gridBagConstraints.fill = 0;
/* 2642 */       gridBagConstraints.gridx = 2;
/* 2643 */       gridBagConstraints.gridy++;
/* 2644 */       gridBagConstraints.gridwidth = 2;
/* 2645 */       gridBagConstraints.insets = insets;
/* 2646 */       gridBagLayout.setConstraints(this.m_checkboxUnfriendlyKeys, gridBagConstraints);
/* 2647 */       add(this.m_checkboxUnfriendlyKeys);
/*      */       
/* 2649 */       this.m_textfieldFriendlyKeys = new JTextField();
/* 2650 */       this.m_textfieldFriendlyKeys.setText(OptionsDlg.this.m_props.getProperty("keys.grids.friendly"));
/* 2651 */       this.m_textfieldFriendlyKeys.setEnabled(false);
/* 2652 */       gridBagConstraints.fill = 2;
/* 2653 */       gridBagConstraints.gridx++;
/* 2654 */       gridBagConstraints.gridy++;
/* 2655 */       gridBagConstraints.gridwidth = 1;
/* 2656 */       gridBagLayout.setConstraints(this.m_textfieldFriendlyKeys, gridBagConstraints);
/* 2657 */       add(this.m_textfieldFriendlyKeys);
/*      */       
/* 2659 */       JLabel jLabel2 = new JLabel(this.m_resources.getString("Label.Tab.Chord.ChordsNoGrids"));
/* 2660 */       gridBagConstraints.fill = 0;
/* 2661 */       gridBagConstraints.gridx = 2;
/* 2662 */       gridBagConstraints.gridy++;
/* 2663 */       gridBagConstraints.gridwidth = 2;
/* 2664 */       gridBagLayout.setConstraints(jLabel2, gridBagConstraints);
/* 2665 */       add(jLabel2);
/*      */       
/* 2667 */       this.m_textfieldChordsNoGrids = new JTextField();
/* 2668 */       this.m_textfieldChordsNoGrids.setText(OptionsDlg.this.m_props.getProperty("grids.chords.no.grids"));
/* 2669 */       gridBagConstraints.fill = 2;
/* 2670 */       gridBagConstraints.gridx++;
/* 2671 */       gridBagConstraints.gridy++;
/* 2672 */       gridBagConstraints.gridwidth = 1;
/* 2673 */       gridBagLayout.setConstraints(this.m_textfieldChordsNoGrids, gridBagConstraints);
/* 2674 */       add(this.m_textfieldChordsNoGrids);
/*      */ 
/*      */ 
/*      */       
/* 2678 */       JPanel jPanel1 = new JPanel();
/* 2679 */       gridBagConstraints.anchor = 17;
/* 2680 */       gridBagConstraints.gridx = 1;
/* 2681 */       gridBagConstraints.gridy++;
/* 2682 */       gridBagConstraints.gridwidth = 3;
/* 2683 */       gridBagConstraints.insets = new Insets(0, 0, 0, 0);
/* 2684 */       gridBagConstraints.fill = 2;
/* 2685 */       gridBagLayout.setConstraints(jPanel1, gridBagConstraints);
/*      */       
/* 2687 */       FlowLayout flowLayout1 = new FlowLayout(3);
/* 2688 */       jPanel1.setLayout(flowLayout1);
/*      */       
/* 2690 */       this.m_labelGridSize = new JLabel(this.m_resources.getString("Label.Tab.Chord.Grids.in"));
/* 2691 */       jPanel1.add(this.m_labelGridSize);
/*      */       
/* 2693 */       JLabel jLabel3 = new JLabel(this.m_resources.getString("Label.Tab.Chord.Grids.min"));
/* 2694 */       jPanel1.add(jLabel3);
/*      */       
/* 2696 */       this.m_textfieldGridMin = new JTextField();
/* 2697 */       this.m_textfieldGridMin.setText(OptionsDlg.this.m_props.getProperty("grids.min"));
/* 2698 */       this.m_textfieldGridMin.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2699 */       jPanel1.add(this.m_textfieldGridMin);
/*      */       
/* 2701 */       jLabel3 = new JLabel(this.m_resources.getString("Label.Tab.Chord.Grids.max"));
/* 2702 */       jPanel1.add(jLabel3);
/*      */       
/* 2704 */       this.m_textfieldGridMax = new JTextField();
/* 2705 */       this.m_textfieldGridMax.setText(OptionsDlg.this.m_props.getProperty("grids.max"));
/* 2706 */       this.m_textfieldGridMax.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2707 */       jPanel1.add(this.m_textfieldGridMax);
/*      */       
/* 2709 */       jLabel3 = new JLabel(this.m_resources.getString("Label.Tab.Chord.Grids.spacing"));
/* 2710 */       jPanel1.add(jLabel3);
/*      */       
/* 2712 */       this.m_textfieldGridSpacing = new JTextField();
/* 2713 */       this.m_textfieldGridSpacing.setText(OptionsDlg.this.m_props.getProperty("grids.spacing"));
/* 2714 */       this.m_textfieldGridSpacing.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2715 */       jPanel1.add(this.m_textfieldGridSpacing);
/*      */       
/* 2717 */       add(jPanel1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2722 */       GenericPropertyAction genericPropertyAction1 = new GenericPropertyAction(OptionsDlg.this.m_props, "print.chords.doremi");
/*      */       
/* 2724 */       this.m_checkboxDoReMi = new StringPropertyCheckBox(genericPropertyAction1, "yes", "no");
/* 2725 */       this.m_checkboxDoReMi.setText(this.m_resources.getString("Command.Tab.General.PrintDoReMi"));
/* 2726 */       if (!SG02App.isMac)
/* 2727 */         this.m_checkboxDoReMi.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.General.PrintDoReMi.Mn")).intValue()); 
/* 2728 */       gridBagConstraints.anchor = 17;
/* 2729 */       gridBagConstraints.gridx = 1;
/* 2730 */       gridBagConstraints.gridy++;
/* 2731 */       gridBagConstraints.gridwidth = 3;
/* 2732 */       gridBagConstraints.insets = insets;
/* 2733 */       gridBagLayout.setConstraints(this.m_checkboxDoReMi, gridBagConstraints);
/* 2734 */       add(this.m_checkboxDoReMi);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2739 */       StringPropertyCheckBox stringPropertyCheckBox1 = new StringPropertyCheckBox(useCapoPropertyAction, "yes", "no");
/* 2740 */       stringPropertyCheckBox1.setText(this.m_resources.getString("Command.Tab.Chord.Capo.Use"));
/* 2741 */       if (!SG02App.isMac)
/* 2742 */         stringPropertyCheckBox1.setMnemonic(((Integer)this.m_resources.getObject("Command.Tab.Chord.Capo.Use.Mn")).intValue()); 
/* 2743 */       gridBagConstraints.anchor = 17;
/* 2744 */       gridBagConstraints.gridx = 0;
/* 2745 */       gridBagConstraints.gridy++;
/* 2746 */       gridBagConstraints.gridwidth = 4;
/* 2747 */       gridBagConstraints.insets = insets;
/* 2748 */       gridBagLayout.setConstraints(stringPropertyCheckBox1, gridBagConstraints);
/* 2749 */       add(stringPropertyCheckBox1);
/*      */ 
/*      */       
/* 2752 */       JPanel jPanel2 = new JPanel();
/* 2753 */       gridBagConstraints.anchor = 17;
/* 2754 */       gridBagConstraints.gridx = 1;
/* 2755 */       gridBagConstraints.gridy++;
/* 2756 */       gridBagConstraints.gridwidth = 4;
/* 2757 */       gridBagConstraints.insets = new Insets(0, 0, 0, 0);
/* 2758 */       gridBagConstraints.fill = 2;
/* 2759 */       gridBagLayout.setConstraints(jPanel2, gridBagConstraints);
/*      */       
/* 2761 */       FlowLayout flowLayout2 = new FlowLayout(3);
/* 2762 */       jPanel2.setLayout(flowLayout2);
/*      */       
/* 2764 */       JLabel jLabel4 = new JLabel(this.m_resources.getString("Label.Tab.Chord.Capo.MaxFret"));
/* 2765 */       jPanel2.add(jLabel4);
/*      */       
/* 2767 */       this.m_textfieldCapoMax = new JTextField();
/* 2768 */       this.m_textfieldCapoMax.setText(OptionsDlg.this.m_props.getProperty("capo.max"));
/* 2769 */       this.m_textfieldCapoMax.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue());
/* 2770 */       jPanel2.add(this.m_textfieldCapoMax);
/*      */       
/* 2772 */       add(jPanel2);
/*      */ 
/*      */       
/* 2775 */       jPanel2 = new JPanel();
/* 2776 */       gridBagConstraints.anchor = 17;
/* 2777 */       gridBagConstraints.gridx = 1;
/* 2778 */       gridBagConstraints.gridy++;
/* 2779 */       gridBagConstraints.gridwidth = 4;
/* 2780 */       gridBagConstraints.insets = new Insets(0, 0, 0, 0);
/* 2781 */       gridBagConstraints.fill = 2;
/* 2782 */       gridBagLayout.setConstraints(jPanel2, gridBagConstraints);
/*      */       
/* 2784 */       flowLayout2 = new FlowLayout(3);
/* 2785 */       jPanel2.setLayout(flowLayout2);
/*      */       
/* 2787 */       jLabel4 = new JLabel(this.m_resources.getString("Label.Tab.Chord.Capo.PreferKeys"));
/* 2788 */       jPanel2.add(jLabel4);
/*      */       
/* 2790 */       this.m_textfieldCapoKeys = new JTextField();
/* 2791 */       this.m_textfieldCapoKeys.setText(OptionsDlg.this.m_props.getProperty("capo.keys"));
/* 2792 */       this.m_textfieldCapoKeys.setColumns(((Integer)this.m_resources.getObject("Control.Columns.textfieldFont")).intValue() * 2);
/* 2793 */       jPanel2.add(this.m_textfieldCapoKeys);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2802 */       JLabel jLabel1 = new JLabel("     ");
/* 2803 */       gridBagConstraints.gridx = 0;
/* 2804 */       gridBagConstraints.gridy++;
/* 2805 */       gridBagConstraints.gridwidth = 1;
/* 2806 */       gridBagConstraints.insets = insets;
/* 2807 */       gridBagLayout.setConstraints(jLabel1, gridBagConstraints);
/* 2808 */       add(jLabel1);
/*      */       
/* 2810 */       JButton jButton = new JButton();
/* 2811 */       jButton.setAction(new AbstractAction(this.m_resources
/* 2812 */             .getString("Command.Chordrc.Edit"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 2816 */               boolean bool = ('y' == OptionsDlg.this.m_props.getProperty("print.chords.ukulele").charAt(0)) ? true : false;
/* 2817 */               ChordrcEditor.editChordrc(OptionsDlg.this.m_paneTabs, bool);
/*      */             }
/*      */           });
/*      */       
/* 2821 */       gridBagConstraints.anchor = 17;
/* 2822 */       gridBagConstraints.fill = 0;
/* 2823 */       gridBagConstraints.gridx = 0;
/* 2824 */       gridBagConstraints.gridy++;
/* 2825 */       gridBagConstraints.gridwidth = 5;
/* 2826 */       gridBagConstraints.gridheight = 1;
/* 2827 */       gridBagConstraints.insets = insets;
/* 2828 */       gridBagLayout.setConstraints(jButton, gridBagConstraints);
/* 2829 */       add(jButton);
/*      */ 
/*      */ 
/*      */       
/* 2833 */       printChordsPropertyAction.updateFromNewProps(OptionsDlg.this.m_props);
/* 2834 */       useCapoPropertyAction.updateFromNewProps(OptionsDlg.this.m_props);
/*      */       
/* 2836 */       boolean bool = (OptionsDlg.this.m_props.getProperty("units").charAt(0) != 'i') ? true : false;
/* 2837 */       if (bool) {
/* 2838 */         displayProperUnits(true);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void displayProperUnits(boolean param1Boolean) {
/* 2847 */       this.m_labelGridSize.setText(this.m_resources.getString(param1Boolean ? "Label.Tab.Chord.Grids.mm" : "Label.Tab.Chord.Grids.in"));
/*      */       
/* 2849 */       DecimalFormat decimalFormat = new DecimalFormat("0.##");
/*      */       
/* 2851 */       float f = Float.parseFloat(this.m_textfieldGridMin.getText());
/* 2852 */       if (param1Boolean) {
/* 2853 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 2855 */         f = (float)(f / 25.4D);
/* 2856 */       }  this.m_textfieldGridMin.setText(decimalFormat.format(f));
/*      */       
/* 2858 */       f = Float.parseFloat(this.m_textfieldGridMax.getText());
/* 2859 */       if (param1Boolean) {
/* 2860 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 2862 */         f = (float)(f / 25.4D);
/* 2863 */       }  this.m_textfieldGridMax.setText(decimalFormat.format(f));
/*      */       
/* 2865 */       f = Float.parseFloat(this.m_textfieldGridSpacing.getText());
/* 2866 */       if (param1Boolean) {
/* 2867 */         f = (float)(f * 25.4D);
/*      */       } else {
/* 2869 */         f = (float)(f / 25.4D);
/* 2870 */       }  this.m_textfieldGridSpacing.setText(decimalFormat.format(f));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class PrintChordsPropertyAction
/*      */       extends GenericPropertyAction
/*      */     {
/*      */       public PrintChordsPropertyAction() {
/* 2880 */         super(OptionsDlg.this.m_props, "print.chords");
/*      */       }
/*      */ 
/*      */       
/*      */       public void actionPerformed(ActionEvent param2ActionEvent) {
/* 2885 */         super.actionPerformed(param2ActionEvent);
/* 2886 */         enableControls();
/*      */       }
/*      */ 
/*      */       
/*      */       public void enableControls() {
/* 2891 */         OptionsDlg.ChordsTab.this.m_checkboxGrids.setEnabled(this.m_booleanValue);
/* 2892 */         OptionsDlg.ChordsTab.this.m_checkboxDoReMi.setEnabled(this.m_booleanValue);
/*      */         
/* 2894 */         boolean bool = (OptionsDlg.ChordsTab.this.m_checkboxGrids.isSelected() && this.m_booleanValue) ? true : false;
/* 2895 */         OptionsDlg.ChordsTab.this.m_radioGuitar.setEnabled(bool);
/* 2896 */         OptionsDlg.ChordsTab.this.m_radioUkulele.setEnabled(bool);
/* 2897 */         OptionsDlg.ChordsTab.this.m_checkboxUnfriendlyKeys.setEnabled(bool);
/* 2898 */         OptionsDlg.ChordsTab.this.m_textfieldChordsNoGrids.setEnabled(bool);
/* 2899 */         OptionsDlg.ChordsTab.this.m_textfieldFriendlyKeys.setEnabled(bool);
/* 2900 */         OptionsDlg.ChordsTab.this.m_textfieldGridMax.setEnabled(bool);
/* 2901 */         OptionsDlg.ChordsTab.this.m_textfieldGridMin.setEnabled(bool);
/* 2902 */         OptionsDlg.ChordsTab.this.m_textfieldGridSpacing.setEnabled(bool);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected class PrintGridsPropertyAction
/*      */       extends GenericPropertyAction
/*      */     {
/*      */       public OptionsDlg.ChordsTab.PrintChordsPropertyAction m_chordsAction;
/*      */       
/*      */       public PrintGridsPropertyAction(OptionsDlg.ChordsTab.PrintChordsPropertyAction param2PrintChordsPropertyAction) {
/* 2913 */         super(OptionsDlg.this.m_props, "grids.print");
/* 2914 */         this.m_chordsAction = param2PrintChordsPropertyAction;
/*      */       }
/*      */ 
/*      */       
/*      */       public void actionPerformed(ActionEvent param2ActionEvent) {
/* 2919 */         super.actionPerformed(param2ActionEvent);
/* 2920 */         this.m_chordsAction.enableControls();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class GuitarUkulelePropertyAction
/*      */       extends GenericPropertyAction
/*      */     {
/*      */       public GuitarUkulelePropertyAction() {
/* 2930 */         super(OptionsDlg.this.m_props, "print.chords.ukulele");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class UseCapoPropertyAction
/*      */       extends GenericPropertyAction
/*      */     {
/*      */       public UseCapoPropertyAction() {
/* 2940 */         super(OptionsDlg.this.m_props, "capo.use");
/*      */       }
/*      */ 
/*      */       
/*      */       public void actionPerformed(ActionEvent param2ActionEvent) {
/* 2945 */         super.actionPerformed(param2ActionEvent);
/* 2946 */         OptionsDlg.ChordsTab.this.m_textfieldCapoMax.setEnabled(this.m_booleanValue);
/*      */         
/* 2948 */         OptionsDlg.ChordsTab.this.m_textfieldCapoKeys.setEnabled(false);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class DataValuesTab
/*      */     extends OSXTab
/*      */   {
/*      */     JTable m_table;
/*      */     
/*      */     DataValuesTableModel m_data;
/*      */ 
/*      */     
/*      */     DataValuesTab() {
/* 2964 */       Dimension dimension = getControlSpacing();
/*      */       
/* 2966 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2967 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 2968 */       setLayout(gridBagLayout);
/* 2969 */       setBorder(BorderFactory.createEmptyBorder(0, 0, dimension.height, dimension.width));
/* 2970 */       gridBagConstraints.weightx = 0.0D;
/*      */       
/* 2972 */       Insets insets = new Insets(dimension.height, dimension.width, 0, 0);
/*      */       
/* 2974 */       JLabel jLabel = new JLabel(this.m_resources.getString("Label.Tab.DataValues.InfoLabel"));
/* 2975 */       gridBagConstraints.anchor = 17;
/* 2976 */       gridBagConstraints.fill = 0;
/* 2977 */       gridBagConstraints.gridx = 0;
/* 2978 */       gridBagConstraints.gridy++;
/* 2979 */       gridBagConstraints.gridwidth = 5;
/* 2980 */       gridBagConstraints.insets = insets;
/* 2981 */       gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 2982 */       add(jLabel);
/*      */       
/* 2984 */       this.m_data = new DataValuesTableModel();
/* 2985 */       this.m_table = new JTable(this.m_data);
/*      */       
/* 2987 */       if (SG02App.isQuaqua) {
/* 2988 */         this.m_table.putClientProperty("Quaqua.Table.style", "striped");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2993 */       this.m_table.sizeColumnsToFit(2);
/* 2994 */       TableColumnModel tableColumnModel = this.m_table.getColumnModel();
/* 2995 */       tableColumnModel.getColumn(0).setPreferredWidth(20000);
/* 2996 */       tableColumnModel.getColumn(1).setPreferredWidth(60000);
/* 2997 */       tableColumnModel.getColumn(2).setPreferredWidth(20000);
/*      */ 
/*      */       
/* 3000 */       JScrollPane jScrollPane = new JScrollPane(this.m_table);
/* 3001 */       gridBagConstraints.anchor = 17;
/* 3002 */       gridBagConstraints.fill = 1;
/* 3003 */       gridBagConstraints.gridy++;
/* 3004 */       gridBagConstraints.gridwidth = 5;
/* 3005 */       gridBagConstraints.weightx = 1.0D;
/* 3006 */       gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
/* 3007 */       add(jScrollPane);
/*      */       
/* 3009 */       JButton jButton1 = new JButton();
/* 3010 */       jButton1.setAction(new AbstractAction(this.m_resources
/* 3011 */             .getString("Command.Tab.DataValues.Add"))
/*      */           {
/*      */             
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 3016 */               JTextField jTextField1 = new JTextField();
/* 3017 */               JTextField jTextField2 = new JTextField();
/* 3018 */               Object[] arrayOfObject = { "Key:", jTextField1, "Value:", jTextField2 };
/*      */               
/* 3020 */               JOptionPane jOptionPane = new JOptionPane(arrayOfObject, 3, 2, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3027 */               JDialog jDialog = jOptionPane.createDialog(OptionsDlg.this.m_frame, "Data Key and Value");
/* 3028 */               jDialog.setVisible(true);
/*      */               
/* 3030 */               int i = 0;
/*      */ 
/*      */               
/*      */               try {
/* 3034 */                 i = ((Integer)jOptionPane.getValue()).intValue();
/*      */               }
/* 3036 */               catch (Exception exception) {}
/*      */               
/* 3038 */               if (i == 0)
/*      */               {
/* 3040 */                 OptionsDlg.DataValuesTab.this.m_data.AddRow(jTextField1.getText(), jTextField2.getText());
/*      */               }
/*      */             }
/*      */           });
/*      */ 
/*      */       
/* 3046 */       gridBagConstraints.gridy++;
/* 3047 */       gridBagConstraints.gridwidth = 1;
/* 3048 */       gridBagConstraints.weightx = 0.0D;
/* 3049 */       gridBagLayout.setConstraints(jButton1, gridBagConstraints);
/* 3050 */       add(jButton1);
/*      */       
/* 3052 */       final JButton editButton = new JButton();
/* 3053 */       editButton.setAction(new AbstractAction(this.m_resources
/* 3054 */             .getString("Command.Tab.DataValues.Edit"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 3058 */               int i = OptionsDlg.DataValuesTab.this.m_table.getSelectedRow();
/*      */               
/* 3060 */               OptionsDlg.DataValuesTab.this.m_table.editCellAt(i, 1);
/*      */             }
/*      */           });
/*      */       
/* 3064 */       gridBagConstraints.gridx++;
/* 3065 */       gridBagLayout.setConstraints(editButton, gridBagConstraints);
/* 3066 */       add(editButton);
/* 3067 */       editButton.setEnabled(false);
/*      */       
/* 3069 */       final JButton deleteButton = new JButton();
/* 3070 */       deleteButton.setAction(new AbstractAction(this.m_resources
/* 3071 */             .getString("Command.Tab.DataValues.Delete"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param2ActionEvent)
/*      */             {
/* 3075 */               int i = OptionsDlg.DataValuesTab.this.m_table.getSelectedRow();
/* 3076 */               OptionsDlg.DataValuesTab.this.m_data.DeleteRow(i);
/*      */             }
/*      */           });
/*      */       
/* 3080 */       gridBagConstraints.gridx++;
/* 3081 */       gridBagLayout.setConstraints(deleteButton, gridBagConstraints);
/* 3082 */       add(deleteButton);
/* 3083 */       deleteButton.setEnabled(false);
/*      */       
/* 3085 */       ListSelectionListener listSelectionListener = new ListSelectionListener()
/*      */         {
/*      */           public void valueChanged(ListSelectionEvent param2ListSelectionEvent)
/*      */           {
/* 3089 */             boolean bool = !OptionsDlg.DataValuesTab.this.m_table.getSelectionModel().isSelectionEmpty() ? true : false;
/* 3090 */             editButton.setEnabled(bool);
/* 3091 */             deleteButton.setEnabled(bool);
/*      */           }
/*      */         };
/* 3094 */       this.m_table.getSelectionModel().addListSelectionListener(listSelectionListener);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class DataValuesTableModel
/*      */       extends AbstractTableModel
/*      */     {
/* 3106 */       HashMap<String, String> m_mapDataKeysToValues = SG02App.cloneKeyValueMap();
/* 3107 */       Map.Entry<String, String>[] m_arrayEntries = (Map.Entry<String, String>[])this.m_mapDataKeysToValues.entrySet().toArray((Object[])new Map.Entry[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void AddRow(String param2String1, String param2String2) {
/* 3113 */         StringBuffer stringBuffer = new StringBuffer(param2String1);
/* 3114 */         for (byte b = 0; b < stringBuffer.length(); b++) {
/*      */           
/* 3116 */           if (Character.isWhitespace(stringBuffer.charAt(b))) {
/*      */             
/* 3118 */             stringBuffer.deleteCharAt(b);
/* 3119 */             b--;
/*      */           } 
/*      */         } 
/* 3122 */         this.m_mapDataKeysToValues.put(stringBuffer.toString(), param2String2);
/* 3123 */         this.m_arrayEntries = (Map.Entry<String, String>[])this.m_mapDataKeysToValues.entrySet().toArray((Object[])new Map.Entry[0]);
/* 3124 */         fireTableRowsInserted(this.m_arrayEntries.length, this.m_arrayEntries.length);
/*      */       }
/*      */ 
/*      */       
/*      */       public void DeleteRow(int param2Int) {
/* 3129 */         this.m_mapDataKeysToValues.remove(this.m_arrayEntries[param2Int].getKey());
/* 3130 */         this.m_arrayEntries = (Map.Entry<String, String>[])this.m_mapDataKeysToValues.entrySet().toArray((Object[])new Map.Entry[0]);
/* 3131 */         fireTableRowsDeleted(param2Int, param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       public String getColumnName(int param2Int) {
/* 3136 */         switch (param2Int) {
/*      */           
/*      */           case 0:
/* 3139 */             return OptionsDlg.DataValuesTab.this.m_resources.getString("Label.Tab.DataValues.Column.Key");
/*      */           case 1:
/* 3141 */             return OptionsDlg.DataValuesTab.this.m_resources.getString("Label.Tab.DataValues.Column.Value");
/*      */           case 2:
/* 3143 */             return OptionsDlg.DataValuesTab.this.m_resources.getString("Label.Tab.DataValues.Column.Usage");
/*      */         } 
/* 3145 */         return "";
/*      */       }
/*      */ 
/*      */       
/*      */       public int getRowCount() {
/* 3150 */         return this.m_mapDataKeysToValues.size();
/*      */       }
/*      */ 
/*      */       
/*      */       public int getColumnCount() {
/* 3155 */         return 3;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Object getValueAt(int param2Int1, int param2Int2) {
/* 3161 */         switch (param2Int2) {
/*      */           
/*      */           case 0:
/* 3164 */             return this.m_arrayEntries[param2Int1].getKey();
/*      */           case 1:
/* 3166 */             return this.m_arrayEntries[param2Int1].getValue();
/*      */           case 2:
/* 3168 */             return "%'" + (String)this.m_arrayEntries[param2Int1].getKey() + "'";
/*      */         } 
/* 3170 */         return "";
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isCellEditable(int param2Int1, int param2Int2) {
/* 3175 */         return (1 == param2Int2);
/*      */       }
/*      */       
/*      */       public void setValueAt(Object param2Object, int param2Int1, int param2Int2)
/*      */       {
/* 3180 */         this.m_mapDataKeysToValues.put(this.m_arrayEntries[param2Int1].getKey(), (String)param2Object);
/* 3181 */         fireTableCellUpdated(param2Int1, param2Int2); } } } protected class DataValuesTableModel extends AbstractTableModel { HashMap<String, String> m_mapDataKeysToValues; Map.Entry<String, String>[] m_arrayEntries; public void setValueAt(Object param1Object, int param1Int1, int param1Int2) { this.m_mapDataKeysToValues.put(this.m_arrayEntries[param1Int1].getKey(), (String)param1Object); fireTableCellUpdated(param1Int1, param1Int2); }
/*      */ 
/*      */     
/*      */     DataValuesTableModel() {
/*      */       this.m_mapDataKeysToValues = SG02App.cloneKeyValueMap();
/*      */       this.m_arrayEntries = (Map.Entry<String, String>[])this.m_mapDataKeysToValues.entrySet().toArray((Object[])new Map.Entry[0]);
/*      */     }
/*      */     
/*      */     public void AddRow(String param1String1, String param1String2) {
/*      */       StringBuffer stringBuffer = new StringBuffer(param1String1);
/*      */       for (byte b = 0; b < stringBuffer.length(); b++) {
/*      */         if (Character.isWhitespace(stringBuffer.charAt(b))) {
/*      */           stringBuffer.deleteCharAt(b);
/*      */           b--;
/*      */         } 
/*      */       } 
/*      */       this.m_mapDataKeysToValues.put(stringBuffer.toString(), param1String2);
/*      */       this.m_arrayEntries = (Map.Entry<String, String>[])this.m_mapDataKeysToValues.entrySet().toArray((Object[])new Map.Entry[0]);
/*      */       fireTableRowsInserted(this.m_arrayEntries.length, this.m_arrayEntries.length);
/*      */     }
/*      */     
/*      */     public void DeleteRow(int param1Int) {
/*      */       this.m_mapDataKeysToValues.remove(this.m_arrayEntries[param1Int].getKey());
/*      */       this.m_arrayEntries = (Map.Entry<String, String>[])this.m_mapDataKeysToValues.entrySet().toArray((Object[])new Map.Entry[0]);
/*      */       fireTableRowsDeleted(param1Int, param1Int);
/*      */     }
/*      */     
/*      */     public String getColumnName(int param1Int) {
/*      */       switch (param1Int) {
/*      */         case 0:
/*      */           return new DataValuesTab().m_resources.getString("Label.Tab.DataValues.Column.Key");
/*      */         case 1:
/*      */           return new DataValuesTab().m_resources.getString("Label.Tab.DataValues.Column.Value");
/*      */         case 2:
/*      */           return new DataValuesTab().m_resources.getString("Label.Tab.DataValues.Column.Usage");
/*      */       } 
/*      */       return "";
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/*      */       return this.m_mapDataKeysToValues.size();
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/*      */       return 3;
/*      */     }
/*      */     
/*      */     public Object getValueAt(int param1Int1, int param1Int2) {
/*      */       switch (param1Int2) {
/*      */         case 0:
/*      */           return this.m_arrayEntries[param1Int1].getKey();
/*      */         case 1:
/*      */           return this.m_arrayEntries[param1Int1].getValue();
/*      */         case 2:
/*      */           return "%'" + (String)this.m_arrayEntries[param1Int1].getKey() + "'";
/*      */       } 
/*      */       return "";
/*      */     }
/*      */     
/*      */     public boolean isCellEditable(int param1Int1, int param1Int2) {
/*      */       return (1 == param1Int2);
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\OptionsDlg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */