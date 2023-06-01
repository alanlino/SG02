/*      */ package com.tenbyten.SG02;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.print.Book;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
import java.net.MalformedURLException;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.util.Iterator;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.TreeSet;

/*      */ import javax.swing.AbstractAction;
import javax.swing.Action;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JEditorPane;
/*      */ import javax.swing.JFileChooser;
import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.ProgressMonitor;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;

/*      */ import com.apple.eawt.Application;
/*      */ import com.apple.eawt.ApplicationAdapter;
/*      */ import com.apple.eawt.ApplicationEvent;
/*      */ import com.tenbyten.Util.CopyFile;
/*      */ 
/*      */ class SG02Frame extends JFrame implements ActionListener {
/*      */   JComponent m_labelSongs;
/*      */   JComponent m_labelPrint;
/*      */   SongTable m_songsTable;
/*      */   DefaultListModel<SongFile> m_modelSongs;
/*      */   private PrintTable m_printTable;
/*      */   DefaultListModel<SongFile> m_modelPrint;
/*      */   private SongPreview m_miniPreview;
/*      */   PageFormat m_pageFormat;
/*      */   private ResourceBundle m_resources;
/*      */   boolean m_printTOCOnly;
/*      */   private Songbook m_songbook;
/*      */   private Songbook m_rescanTempSongbook;
/*      */   private JButton m_buttonPrint;
/*      */   private JButton m_buttonMoveUp;
/*      */   private JButton m_buttonMoveDown;
/*      */   private JButton m_buttonFullScreen;
/*      */   private EditSongMenuItem m_menuItemEdit;
/*      */   
/*      */   SG02Frame() {
/*   83 */     this.m_thisFrame = this;
/*      */     
/*   85 */     this.m_bApplyPropertiesToPrintList = true;
/*   86 */     setDefaultCloseOperation(0);
/*      */     
/*   88 */     if (SG02App.isMac) {
/*      */       
/*   90 */       if (!SG02App.isQuaqua) {
/*   91 */         UIManager.put("PopupMenu.border", new BorderUIResource.EmptyBorderUIResource(4, 8, 4, 8));
/*      */       }
/*      */ 
/*      */       
/*   95 */       Application application = new Application();
/*   96 */       application.setEnabledPreferencesMenu(true);
/*      */       
/*      */       try {
/*   99 */         application.addApplicationListener(new ApplicationAdapter()
/*      */             {
/*      */               public void handleAbout(ApplicationEvent param1ApplicationEvent)
/*      */               {
/*  103 */                 SG02Frame.this.m_thisFrame.handleAbout();
/*  104 */                 param1ApplicationEvent.setHandled(true);
/*      */               }
/*      */ 
/*      */               
/*      */               public void handleOpenApplication(ApplicationEvent param1ApplicationEvent) {}
/*      */ 
/*      */               
/*      */               public void handleOpenFile(ApplicationEvent param1ApplicationEvent) {
/*  112 */                 SG02Frame.this.m_openFile = new File(param1ApplicationEvent.getFilename());
/*  113 */                 if (!SG02Frame.this.m_threadScan.isAlive()) {
/*      */                   
/*  115 */                   SG02Frame.this.openSongbook(SG02Frame.this.m_openFile);
/*  116 */                   SG02Frame.this.m_openFile = null;
/*      */                 } 
/*  118 */                 param1ApplicationEvent.setHandled(true);
/*      */               }
/*      */               
/*      */               public void handlePreferences(ApplicationEvent param1ApplicationEvent) {
/*  122 */                 SG02Frame.this.handlePrefs();
/*  123 */                 param1ApplicationEvent.setHandled(true);
/*      */               }
/*      */ 
/*      */               
/*      */               public void handlePrintFile(ApplicationEvent param1ApplicationEvent) {}
/*      */ 
/*      */               
/*      */               public void handleQuit(ApplicationEvent param1ApplicationEvent) {
/*  131 */                 SG02Frame.this.m_thisFrame.handleQuit();
/*      */               }
/*      */             });
/*      */       
/*      */       }
/*  136 */       catch (NoSuchMethodError noSuchMethodError) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  141 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*      */     
/*  143 */     setTitle();
/*      */     
/*  145 */     ThreadScan threadScan = new ThreadScan(this);
/*  146 */     this.m_threadScan = threadScan;
/*      */     
/*  148 */     addWindowListener(new WindowAdapter()
/*      */         {
/*      */           public void windowClosing(WindowEvent param1WindowEvent)
/*      */           {
/*  152 */             if (null != SG02Frame.this.m_songbook)
/*      */             {
/*  154 */               if (Boolean.TRUE == SG02Frame.this.getRootPane().getClientProperty("Window.documentModified")) {
/*      */                 
/*  156 */                 int i = JOptionPane.showConfirmDialog(SG02Frame.this.m_thisFrame, SG02Frame.this
/*  157 */                     .m_resources.getString("Text.Message.Songbook.Exit"), SG02Frame.this
/*  158 */                     .m_resources.getString("Title.Dialog.Confirm"), 1, 3);
/*      */ 
/*      */ 
/*      */                 
/*  162 */                 if (i == 0) {
/*      */                   
/*  164 */                   if (!SG02Frame.this.m_songbook.save(SG02Frame.this.m_songbook.getFile())) {
/*      */                     return;
/*      */                   }
/*  167 */                 } else if (i == 2) {
/*      */                   return;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */ 
/*      */             
/*  174 */             SG02App.props.setProperty("window.x", String.valueOf(SG02Frame.this.m_thisFrame.getX()));
/*  175 */             SG02App.props.setProperty("window.y", String.valueOf(SG02Frame.this.m_thisFrame.getY()));
/*  176 */             SG02App.props.setProperty("window.width", String.valueOf(SG02Frame.this.m_thisFrame.getWidth()));
/*  177 */             SG02App.props.setProperty("window.height", String.valueOf(SG02Frame.this.m_thisFrame.getHeight()));
/*      */             
/*  179 */             SG02Frame.this.m_songsTable.saveColumns();
/*  180 */             SG02Frame.this.m_printTable.saveColumns();
/*      */             
/*  182 */             SG02App.writeProperties();
/*  183 */             SG02Frame.this.dispose();
/*  184 */             System.exit(0);
/*      */           }
/*      */ 
/*      */           
/*      */           public void windowOpened(WindowEvent param1WindowEvent) {
/*  189 */             SG02Frame.this.m_threadScan.start();
/*  190 */             SG02Frame.this.m_songsTable.requestFocus();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  195 */     this.m_modelSongs = new DefaultListModel<SongFile>();
/*  196 */     this.m_songsTable = new SongTable(this.m_modelSongs);
/*      */     
/*  198 */     this.m_labelSongs = new JButton();
/*      */     
/*  200 */     this.m_modelPrint = new DefaultListModel<SongFile>();
/*  201 */     this.m_printTable = new PrintTable(this.m_modelPrint);
/*      */     
/*  203 */     this.m_labelPrint = new JButton();
/*      */     
/*  205 */     this.m_miniPreview = new SongPreview();
/*  206 */     this.m_miniPreview.listenToTable(this.m_songsTable, this.m_modelSongs);
/*  207 */     this.m_miniPreview.listenToTable(this.m_printTable, this.m_modelPrint);
/*      */     
/*  209 */     if (SG02App.isQuaqua) {
/*      */       
/*  211 */       this.m_labelSongs.putClientProperty("Quaqua.Button.style", "tableHeader");
/*  212 */       this.m_labelPrint.putClientProperty("Quaqua.Button.style", "tableHeader");
/*  213 */       this.m_printTable.putClientProperty("Quaqua.Table.style", "striped");
/*  214 */       this.m_songsTable.putClientProperty("Quaqua.Table.style", "striped");
/*      */     } 
/*      */     
/*  217 */     createActions();
/*      */     
/*  219 */     setupMenu();
/*      */     
/*  221 */     layoutControls();
/*      */     
/*  223 */     setBounds(Integer.parseInt(SG02App.props.getProperty("window.x")), 
/*  224 */         Integer.parseInt(SG02App.props.getProperty("window.y")), 
/*  225 */         Integer.parseInt(SG02App.props.getProperty("window.width")), 
/*  226 */         Integer.parseInt(SG02App.props.getProperty("window.height")));
/*      */     
/*  228 */     resetPageOrientation();
/*      */     
/*  230 */     onRemoveAll();
/*      */     
/*  232 */     checkForUpdate();
/*      */   }
/*      */   private DeleteSongMenuItem m_menuItemDeleteSong; private JMenuItem m_menuItemPrint; private JMenuItem m_menuItemPrintTOC; private JMenuItem m_menuItemSaveSongbook; private JMenuItem m_menuItemSaveSongbookAs; private JMenuItem m_menuItemFullScreen; private JComboBox<String> m_fastOutputPicker;
/*      */   private PrintChordsPropertyAction m_actionPrintChords;
/*      */   private SongsPerPagePropertyAction m_actionSongsPerPage;
/*      */   
/*      */   private void createActions() {
			   try{
	
/*  239 */     this.m_actionDoPrint = new AbstractAction()
/*      */       {
/*      */         
/*      */         public void actionPerformed(ActionEvent param1ActionEvent)
/*      */         {
/*      */           try {
/*  245 */             SG02Frame.this.m_thisFrame.m_printTOCOnly = false;
/*      */             
/*  247 */             ProgressMonitor progressMonitor = new ProgressMonitor(SG02Frame.this.m_thisFrame, "", "", 0, 0);
/*      */             
/*  249 */             ThreadPrint threadPrint = new ThreadPrint(SG02Frame.this.m_thisFrame, progressMonitor);
/*      */             
/*  251 */             EventQueue.invokeLater(threadPrint);
						System.out.println("xixiixixixixixixi");
/*      */           }
/*  253 */           catch (Exception exception) {
							exception.printStackTrace();
						}
/*      */         }
/*      */       };
/*      */ 
/*      */     System.out.println("ddsadasfdsaf asdfasdfsad");//xixi
/*  258 */     this.m_actionDoFullScreen = new AbstractAction(this.m_resources.getString("Menu.View.FullScreen"))
/*      */       {
/*      */         
/*      */         public void actionPerformed(ActionEvent param1ActionEvent)
/*      */         {
/*      */           try {
/*  264 */             GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  265 */             GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*      */             
/*  267 */             int i = Integer.parseInt(SG02App.props.getProperty("fullscreen.device"));
/*  268 */             if (arrayOfGraphicsDevice.length < i + 1) {
/*  269 */               i = 0;
/*      */             }
/*  271 */             FullScreenView fullScreenView = new FullScreenView(arrayOfGraphicsDevice[i]);
/*      */             int j;
/*  273 */             for (j = 0; j < SG02Frame.this.m_modelPrint.getSize(); j++) {
/*      */               
/*  275 */               SongFile songFile = SG02Frame.this.m_modelPrint.getElementAt(j);
/*  276 */               fullScreenView.addSongFile(songFile);
/*      */             } 
/*      */             
/*  279 */             j = 0;
/*      */             
/*  281 */             if ('y' == SG02App.props.getProperty("fullscreen.start.@.selected").charAt(0)) {
/*      */               
/*  283 */               int[] arrayOfInt = SG02Frame.this.m_printTable.getSelectedRows();
/*  284 */               if (arrayOfInt.length > 0)
/*  285 */                 j = arrayOfInt[0]; 
/*  286 */               if (j < 0) {
/*  287 */                 j = 0;
/*      */               }
/*      */             } 
/*  290 */             fullScreenView.showFullScreen(j);
/*      */           }
/*  292 */           catch (Exception exception) {
						exception.printStackTrace();
}
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  297 */     ListDataListener listDataListener = new ListDataListener()
/*      */       {
/*      */         public void contentsChanged(ListDataEvent param1ListDataEvent)
/*      */         {
/*  301 */           SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/*  302 */           SG02Frame.this.setVisible(true);
/*      */         }
/*      */         public void intervalAdded(ListDataEvent param1ListDataEvent) {}
/*      */         public void intervalRemoved(ListDataEvent param1ListDataEvent) {}
/*      */       };
/*  307 */     this.m_modelPrint.addListDataListener(listDataListener);


	
				}catch(Exception e){
						e.printStackTrace();
				}

/*      */   }
/*      */   private TransposePropertyAction m_actionTranspose; private TransposeOtherPropertyAction m_actionTransposeOther; private OutputDestinationPropertyAction m_actionOutputDestination;
/*      */   private boolean m_bApplyPropertiesToPrintList;
/*      */   
/*      */   private void setTitle() {
/*  313 */     if (null != this.m_songbook) {
/*  314 */       setTitle(this.m_songbook.toString() + " - " + this.m_resources.getString("Title.Dialog.App"));
/*      */     } else {
/*  316 */       setTitle(this.m_resources.getString("Title.Dialog.App"));
/*      */     } 
/*      */   }
/*      */   private AbstractAction m_actionDoPrint; private AbstractAction m_actionDoFullScreen; private SG02Frame m_thisFrame; private ThreadScan m_threadScan; private File m_openFile;
/*      */   
/*      */   private void layoutControls() {
/*  322 */     Dimension dimension = (Dimension)this.m_resources.getObject("Control.Spacing");
/*  323 */     Insets insets1 = new Insets(0, 0, 0, 0);
/*  324 */     Insets insets2 = insets1;
/*      */ 
/*      */     
/*  327 */     JScrollPane jScrollPane1 = new JScrollPane(this.m_songsTable);
/*  328 */     jScrollPane1.setPreferredSize(new Dimension(this.m_songsTable.getColumnModel().getTotalColumnWidth(), 150));
/*  329 */     jScrollPane1.setMinimumSize((Dimension)this.m_resources.getObject("Control.Size.Min.listboxSongs"));
/*  330 */     jScrollPane1.setAlignmentX(0.0F);
/*      */     
/*  332 */     JScrollPane jScrollPane2 = new JScrollPane(this.m_printTable);
/*  333 */     jScrollPane2.setPreferredSize(new Dimension(this.m_printTable.getColumnModel().getTotalColumnWidth(), 150));
/*  334 */     jScrollPane2.setMinimumSize((Dimension)this.m_resources.getObject("Control.Size.Min.listboxPrint"));
/*  335 */     jScrollPane2.setAlignmentX(0.0F);
/*      */     
/*  337 */     if (SG02App.isMac) {
/*      */       
/*  339 */       jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190)));
/*  340 */       jScrollPane2.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190)));
/*      */     } 
/*      */     
/*  343 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */     
/*  345 */     JPanel jPanel1 = new JPanel();
/*  346 */     GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  347 */     jPanel1.setLayout(gridBagLayout1);
/*      */     
/*  349 */     gridBagConstraints.anchor = 17;
/*  350 */     gridBagConstraints.fill = 2;
/*  351 */     gridBagConstraints.gridx = 0;
/*  352 */     gridBagConstraints.gridy = 0;
/*  353 */     gridBagConstraints.gridwidth = 1;
/*  354 */     gridBagConstraints.gridheight = 1;
/*  355 */     gridBagConstraints.weightx = 1.0D;
/*  356 */     gridBagConstraints.weighty = 0.0D;
/*  357 */     gridBagConstraints.insets = insets2;
/*  358 */     gridBagLayout1.setConstraints(this.m_labelSongs, gridBagConstraints);
/*  359 */     jPanel1.add(this.m_labelSongs);
/*      */     
/*  361 */     gridBagConstraints.fill = 1;
/*  362 */     gridBagConstraints.gridy = 1;
/*  363 */     gridBagConstraints.gridwidth = 1;
/*  364 */     gridBagConstraints.weightx = 1.0D;
/*  365 */     gridBagConstraints.weighty = 1.0D;
/*  366 */     gridBagConstraints.insets = insets1;
/*  367 */     gridBagLayout1.setConstraints(jScrollPane1, gridBagConstraints);
/*  368 */     jPanel1.add(jScrollPane1);
/*      */ 
/*      */     
/*  371 */     JButton jButton1 = new JButton(this.m_resources.getString("Command.Add"));
/*  372 */     if (!SG02App.isMac)
/*  373 */       jButton1.setMnemonic(((Integer)this.m_resources.getObject("Command.Add.Mn")).intValue()); 
/*  374 */     jButton1.addActionListener(this);
/*  375 */     JButton jButton2 = new JButton(this.m_resources.getString("Command.Remove"));
/*  376 */     if (!SG02App.isMac)
/*  377 */       jButton2.setMnemonic(((Integer)this.m_resources.getObject("Command.Remove.Mn")).intValue()); 
/*  378 */     jButton2.addActionListener(this);
/*  379 */     JButton jButton3 = new JButton(this.m_resources.getString("Command.RemoveAll"));
/*  380 */     if (!SG02App.isMac)
/*  381 */       jButton3.setMnemonic(((Integer)this.m_resources.getObject("Command.RemoveAll.Mn")).intValue()); 
/*  382 */     jButton3.addActionListener(this);
/*      */     
/*  384 */     JPanel jPanel2 = new JPanel();
/*  385 */     jPanel2.setLayout(new BoxLayout(jPanel2, 1));
/*  386 */     jPanel2.add(jButton1);
/*  387 */     jPanel2.add(Box.createRigidArea(dimension));
/*  388 */     jPanel2.add(jButton2);
/*  389 */     jPanel2.add(Box.createRigidArea(dimension));
/*  390 */     jPanel2.add(jButton3);
/*  391 */     jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, dimension.width));
/*      */     
/*  393 */     if (null == this.m_buttonMoveUp)
/*  394 */       this.m_buttonMoveUp = new JButton(this.m_resources.getString("Command.MoveUp")); 
/*  395 */     if (!SG02App.isMac)
/*  396 */       this.m_buttonMoveUp.setMnemonic(((Integer)this.m_resources.getObject("Command.MoveUp.Mn")).intValue()); 
/*  397 */     this.m_buttonMoveUp.setAction(new AbstractAction(this.m_resources
/*  398 */           .getString("Command.MoveUp"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  402 */             int[] arrayOfInt = SG02Frame.this.m_printTable.getSelectedRows();
/*  403 */             if (arrayOfInt.length > 0) {
/*      */               
/*  405 */               SG02Frame.this.m_printTable.moveUp(arrayOfInt);
/*  406 */               SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  412 */     if (null == this.m_buttonMoveDown)
/*  413 */       this.m_buttonMoveDown = new JButton(this.m_resources.getString("Command.MoveDown")); 
/*  414 */     if (!SG02App.isMac)
/*  415 */       this.m_buttonMoveDown.setMnemonic(((Integer)this.m_resources.getObject("Command.MoveDown.Mn")).intValue()); 
/*  416 */     this.m_buttonMoveDown.setAction(new AbstractAction(this.m_resources
/*  417 */           .getString("Command.MoveDown"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  421 */             int[] arrayOfInt = SG02Frame.this.m_printTable.getSelectedRows();
/*  422 */             if (arrayOfInt.length > 0) {
/*      */               
/*  424 */               SG02Frame.this.m_printTable.moveDown(arrayOfInt);
/*  425 */               SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  431 */     JPanel jPanel3 = new JPanel();
/*  432 */     jPanel3.setLayout(new BoxLayout(jPanel3, 1));
/*  433 */     jPanel3.add(this.m_buttonMoveUp);
/*  434 */     jPanel3.add(Box.createRigidArea(dimension));
/*  435 */     jPanel3.add(this.m_buttonMoveDown);
/*  436 */     jPanel3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, dimension.width));
/*      */     
/*  438 */     JPanel jPanel4 = new JPanel();
/*  439 */     GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  440 */     jPanel4.setLayout(gridBagLayout2);
/*      */     
/*  442 */     gridBagConstraints.fill = 2;
/*  443 */     gridBagConstraints.gridx = 1;
/*  444 */     gridBagConstraints.gridy = 0;
/*  445 */     gridBagConstraints.gridwidth = 1;
/*  446 */     gridBagConstraints.gridheight = 1;
/*  447 */     gridBagConstraints.weightx = 1.0D;
/*  448 */     gridBagConstraints.weighty = 0.0D;
/*  449 */     gridBagConstraints.insets = insets2;
/*  450 */     gridBagLayout2.setConstraints(this.m_labelPrint, gridBagConstraints);
/*  451 */     jPanel4.add(this.m_labelPrint);
/*      */     
/*  453 */     gridBagConstraints.gridx = 0;
/*  454 */     gridBagConstraints.gridy = 1;
/*  455 */     gridBagConstraints.gridwidth = 1;
/*  456 */     gridBagConstraints.gridheight = 1;
/*  457 */     gridBagConstraints.weightx = 0.0D;
/*  458 */     gridBagConstraints.weighty = 0.0D;
/*  459 */     gridBagLayout2.setConstraints(jPanel2, gridBagConstraints);
/*  460 */     jPanel4.add(jPanel2);
/*      */     
/*  462 */     gridBagConstraints.fill = 1;
/*  463 */     gridBagConstraints.gridx = 1;
/*  464 */     gridBagConstraints.gridy = 1;
/*  465 */     gridBagConstraints.gridwidth = 1;
/*  466 */     gridBagConstraints.gridheight = 1;
/*  467 */     gridBagConstraints.weightx = 1.0D;
/*  468 */     gridBagConstraints.weighty = 1.0D;
/*  469 */     gridBagConstraints.insets = insets1;
/*  470 */     gridBagLayout2.setConstraints(jScrollPane2, gridBagConstraints);
/*  471 */     jPanel4.add(jScrollPane2);
/*      */     
/*  473 */     gridBagConstraints.fill = 0;
/*  474 */     gridBagConstraints.gridx = 2;
/*  475 */     gridBagConstraints.weightx = 0.0D;
/*  476 */     gridBagConstraints.weighty = 0.0D;
/*  477 */     gridBagConstraints.insets = insets2;
/*  478 */     gridBagLayout2.setConstraints(jPanel3, gridBagConstraints);
/*  479 */     jPanel4.add(jPanel3);
/*      */     
/*  481 */     JScrollPane jScrollPane3 = new JScrollPane(this.m_miniPreview);
/*  482 */     jScrollPane3.setVerticalScrollBarPolicy(22);
/*  483 */     jScrollPane3.setHorizontalScrollBarPolicy(32);
/*      */     
/*  485 */     jScrollPane3.setPreferredSize(new Dimension(200, 100));
/*  486 */     jScrollPane3.setMinimumSize(new Dimension(0, 0));
/*  487 */     if (SG02App.isQuaqua) {
/*  488 */       jScrollPane3.putClientProperty("Quaqua.Component.visualMargin", insets1);
/*      */     }
/*  490 */     JSplitPane jSplitPane1 = new JSplitPane(0, jPanel4, jScrollPane3);
/*  491 */     jSplitPane1.setResizeWeight(0.4D);
/*  492 */     jSplitPane1.setBorder((Border)null);
/*  493 */     if (SG02App.isQuaqua) {
/*  494 */       jSplitPane1.putClientProperty("Quaqua.SplitPane.style", "bar");
/*      */     }
/*  496 */     boolean bool = true;
/*  497 */     JSplitPane jSplitPane2 = null;
/*  498 */     JPanel jPanel5 = null;
/*      */     
/*  500 */     if (bool) {
/*      */       
/*  502 */       jSplitPane2 = new JSplitPane(1, jPanel1, jSplitPane1);
/*  503 */       jSplitPane2.setResizeWeight(0.7D);
/*  504 */       jSplitPane2.setBorder((Border)null);
/*  505 */       if (SG02App.isQuaqua) {
/*  506 */         jSplitPane2.putClientProperty("Quaqua.SplitPane.style", "bar");
/*      */       }
/*      */     } else {
/*      */       
/*  510 */       GridBagLayout gridBagLayout = new GridBagLayout();
/*  511 */       jPanel5 = new JPanel(gridBagLayout);
/*  512 */       gridBagConstraints.fill = 1;
/*  513 */       gridBagConstraints.gridx = 0;
/*  514 */       gridBagConstraints.gridy = 0;
/*  515 */       gridBagConstraints.gridwidth = 1;
/*  516 */       gridBagConstraints.gridheight = 1;
/*  517 */       gridBagConstraints.weightx = 4.0D;
/*  518 */       gridBagConstraints.weighty = 1.0D;
/*  519 */       gridBagLayout.setConstraints(jPanel1, gridBagConstraints);
/*  520 */       jPanel5.add(jPanel1);
/*      */       
/*  522 */       gridBagConstraints.gridx++;
/*  523 */       gridBagConstraints.weightx = 1.0D;
/*  524 */       gridBagLayout.setConstraints(jSplitPane1, gridBagConstraints);
/*  525 */       jPanel5.add(jSplitPane1);
/*      */     } 
/*      */ 
/*      */     
/*  529 */     if (null == this.m_buttonPrint)
/*  530 */       this.m_buttonPrint = new JButton(this.m_actionDoPrint); 
/*  531 */     this.m_actionOutputDestination.actionPerformed(new ActionEvent(this, 0, ""));
/*      */     
/*  533 */     if (null == this.m_buttonFullScreen) {
/*  534 */       this.m_buttonFullScreen = new JButton(this.m_actionDoFullScreen);
/*      */     }
/*  536 */     setupFastOutputPicker();
/*      */     
/*  538 */     JPanel jPanel6 = new JPanel();
/*  539 */     jPanel6.setLayout(new BoxLayout(jPanel6, 0));
/*  540 */     jPanel6.add(new JLabel(this.m_resources.getString("Label.Tab.General.Output")));
/*  541 */     jPanel6.add(this.m_fastOutputPicker);
/*  542 */     jPanel6.add(Box.createHorizontalGlue());
/*  543 */     jPanel6.add(Box.createHorizontalStrut(dimension.width));
/*  544 */     jPanel6.add(this.m_buttonFullScreen);
/*  545 */     jPanel6.add(Box.createHorizontalStrut(dimension.width));
/*  546 */     jPanel6.add(this.m_buttonPrint);
/*  547 */     jPanel6.add(Box.createHorizontalStrut(dimension.width));
/*  548 */     jPanel6.setBorder(BorderFactory.createEmptyBorder(dimension.height, dimension.width, dimension.height, dimension.width));
/*      */     
/*  550 */     GridBagLayout gridBagLayout3 = new GridBagLayout();
/*  551 */     JPanel jPanel7 = new JPanel();
/*      */     
/*  553 */     jPanel7.setLayout(gridBagLayout3);
/*      */     
/*  555 */     gridBagConstraints.fill = 1;
/*  556 */     gridBagConstraints.gridx = 0;
/*  557 */     gridBagConstraints.gridy = 0;
/*  558 */     gridBagConstraints.gridwidth = 1;
/*  559 */     gridBagConstraints.gridheight = 1;
/*  560 */     gridBagConstraints.weightx = 1.0D;
/*  561 */     gridBagConstraints.weighty = 1.0D;
/*  562 */     if (bool) {
/*      */       
/*  564 */       gridBagLayout3.setConstraints(jSplitPane2, gridBagConstraints);
/*  565 */       jPanel7.add(jSplitPane2);
/*      */     }
/*      */     else {
/*      */       
/*  569 */       gridBagLayout3.setConstraints(jPanel5, gridBagConstraints);
/*  570 */       jPanel7.add(jPanel5);
/*      */     } 
/*      */     
/*  573 */     gridBagConstraints.fill = 2;
/*  574 */     gridBagConstraints.gridy = 1;
/*  575 */     gridBagConstraints.weighty = 0.0D;
/*  576 */     gridBagConstraints.gridwidth = 1;
/*  577 */     gridBagLayout3.setConstraints(jPanel6, gridBagConstraints);
/*  578 */     jPanel7.add(jPanel6);
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
/*  610 */     setContentPane(jPanel7);
/*  611 */     pack();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFastOutputPicker() {
/*  617 */     if (null == this.m_fastOutputPicker) {
/*      */       
/*  619 */       this.m_fastOutputPicker = new JComboBox<String>();
/*      */       
/*  621 */       String str = this.m_resources.getString("Menu.Output.Printer");
/*  622 */       this.m_fastOutputPicker.addItem(str);
/*      */       
/*  624 */       str = this.m_resources.getString("Menu.Output.Plaintext");
/*  625 */       this.m_fastOutputPicker.addItem(str);
/*      */       
/*  627 */       str = this.m_resources.getString("Menu.Output.HTML");
/*  628 */       this.m_fastOutputPicker.addItem(str);
/*      */       
/*  630 */       str = this.m_resources.getString("Menu.Output.ChordPro");
/*  631 */       this.m_fastOutputPicker.addItem(str);
/*      */       
/*  633 */       str = this.m_resources.getString("Menu.Output.RTF");
/*  634 */       this.m_fastOutputPicker.addItem(str);
/*      */       
/*  636 */       this.m_fastOutputPicker.setMaximumSize(this.m_fastOutputPicker.getPreferredSize());
/*      */       
/*  638 */       this.m_fastOutputPicker.setSelectedItem(this.m_actionOutputDestination.getStringPropertyValue());
/*      */       
/*  640 */       ItemListener itemListener = new ItemListener()
/*      */         {
/*      */           public void itemStateChanged(ItemEvent param1ItemEvent)
/*      */           {
/*  644 */             if (param1ItemEvent.getStateChange() == 1) {
/*      */               
/*  646 */               SG02Frame.this.m_actionOutputDestination.getProps().setProperty(SG02Frame.this.m_actionOutputDestination.getPropertyName(), (String)param1ItemEvent.getItem());
/*  647 */               SG02Frame.this.m_actionOutputDestination.actionPerformed(new ActionEvent(this, 0, ""));
/*      */             } 
/*      */           }
/*      */         };
/*  651 */       this.m_fastOutputPicker.addItemListener(itemListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupMenu() {
/*  658 */     JMenuBar jMenuBar = new JMenuBar();
/*  659 */     setJMenuBar(jMenuBar);
/*      */     
/*  661 */     setupFileMenu(jMenuBar);
/*  662 */     setupOptionsMenu(jMenuBar);
/*  663 */     setupViewMenu(jMenuBar);
/*  664 */     setupHelpMenu(jMenuBar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFileMenu(JMenuBar paramJMenuBar) {
/*  673 */     JMenu jMenu = new JMenu(this.m_resources.getString("Menu.File"));
/*  674 */     if (!SG02App.isMac)
/*  675 */       jMenu.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Mn")).intValue()); 
/*  676 */     paramJMenuBar.add(jMenu);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  682 */     JMenuItem jMenuItem = new JMenuItem();
/*  683 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/*  684 */           .getString("Menu.File.New"))
/*      */         {
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*      */             try {
/*  690 */               File file1 = new File(SG02App.props.getProperty("songs.path"));
/*  691 */               File file2 = File.createTempFile("Sng", ".txt", file1);
/*  692 */               File file3 = new File(SG02App.props.getProperty("songs.template"));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  697 */               if (!file3.exists()) {
/*      */                 
/*  699 */                 File file = new File(SG02App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
/*  700 */                 file3 = new File(file.getParent() + "/../Resources/" + SG02App.props.getProperty("songs.template"));
/*  701 */                 if (!file3.exists()) {
/*  702 */                   file3 = new File("../" + SG02App.props.getProperty("songs.template"));
/*      */                 }
/*      */               } 
/*      */               
/*      */               try {
/*  707 */                 CopyFile.simpleCopy(file3, file2);
/*      */               }
/*  709 */               catch (Exception exception) {}
/*      */ 
/*      */               
/*  712 */               SG02Frame.this.m_menuItemEdit.editSong(file2.getCanonicalPath());
/*      */               
/*  714 */               SG02Frame.this.rescanSongs();
/*      */             }
/*  716 */             catch (URISyntaxException uRISyntaxException) {
/*      */             
/*  718 */             } catch (Exception exception) {}
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  723 */     if (!SG02App.isMac)
/*  724 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.New.Mn")).intValue()); 
/*  725 */     jMenuItem.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.File.New.Acc"));
/*  726 */     jMenu.add(jMenuItem);
/*      */     
/*  728 */     this.m_menuItemEdit = new EditSongMenuItem();
/*  729 */     jMenu.add(this.m_menuItemEdit);
/*      */     
/*  731 */     this.m_menuItemDeleteSong = new DeleteSongMenuItem();
/*  732 */     jMenu.add(this.m_menuItemDeleteSong);
/*      */     
/*  734 */     jMenuItem = new ImportSongMenuItem();
/*  735 */     jMenu.add(jMenuItem);
/*      */ 
/*      */     
/*  738 */     if (SG02App.isMac) {
/*      */       
/*  740 */       RevealSongsMenuItem revealSongsMenuItem = new RevealSongsMenuItem();
/*  741 */       jMenu.add(revealSongsMenuItem);
/*      */     } 
/*      */ 
/*      */     
/*  745 */     jMenuItem = new JMenuItem();
/*  746 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/*  747 */           .getString("Menu.File.RescanSongs"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  751 */             SG02Frame.this.rescanSongs();
/*      */           }
/*      */         });
/*      */     
/*  755 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.RescanSongs.Mn")).intValue());
/*  756 */     jMenu.add(jMenuItem);
/*      */     
/*  758 */     jMenu.add(new JSeparator());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     jMenuItem = new JMenuItem();
/*  766 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/*  767 */           .getString("Menu.File.Songbook.New"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  771 */             SG02Frame.this.m_songbook = null;
/*  772 */             SG02Frame.this.m_thisFrame.setTitle();
/*  773 */             SG02Frame.this.m_thisFrame.onRemoveAll();
/*      */           }
/*      */         });
/*      */     
/*  777 */     if (!SG02App.isMac)
/*  778 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Songbook.New.Mn")).intValue()); 
/*  779 */     jMenuItem.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.File.Songbook.New.Acc"));
/*  780 */     jMenu.add(jMenuItem);
/*      */     
/*  782 */     jMenuItem = new JMenuItem();
/*  783 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/*  784 */           .getString("Menu.File.Songbook.Open"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  788 */             JFileChooser jFileChooser = new JFileChooser();
/*  789 */             String str = SG02App.props.getProperty("path.recent.songbook");
/*  790 */             if (null != str) {
/*      */               
/*  792 */               jFileChooser.setSelectedFile(new File(str));
/*  793 */               jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */             } 
/*  795 */             jFileChooser.setFileFilter(new FileFilterSGB());
/*  796 */             int i = jFileChooser.showOpenDialog(SG02Frame.this.m_thisFrame);
/*      */             
/*  798 */             if (0 == i) {
/*      */               
/*  800 */               File file = jFileChooser.getSelectedFile();
/*  801 */               SG02Frame.this.openSongbook(file);
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  806 */     if (!SG02App.isMac)
/*  807 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Songbook.Open.Mn")).intValue()); 
/*  808 */     jMenuItem.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.File.Songbook.Open.Acc"));
/*  809 */     jMenu.add(jMenuItem);
/*      */     
/*  811 */     this.m_menuItemSaveSongbook = new JMenuItem();
/*  812 */     this.m_menuItemSaveSongbook.setAction(new AbstractAction(this.m_resources
/*  813 */           .getString("Menu.File.Songbook.Save"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  817 */             if (null != SG02Frame.this.m_songbook) {
/*      */               
/*  819 */               if (null == SG02Frame.this.m_songbook.getFile()) {
/*  820 */                 SG02Frame.this.m_menuItemSaveSongbookAs.doClick();
/*      */               } else {
/*  822 */                 SG02Frame.this.m_songbook.save(SG02Frame.this.m_songbook.getFile());
/*      */               } 
/*  824 */               SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.FALSE);
/*  825 */               SG02Frame.this.setVisible(true);
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  830 */     if (!SG02App.isMac)
/*  831 */       this.m_menuItemSaveSongbook.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Songbook.Save.Mn")).intValue()); 
/*  832 */     this.m_menuItemSaveSongbook.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.File.Songbook.Save.Acc"));
/*  833 */     this.m_menuItemSaveSongbook.setEnabled(false);
/*  834 */     jMenu.add(this.m_menuItemSaveSongbook);
/*      */     
/*  836 */     this.m_menuItemSaveSongbookAs = new JMenuItem();
/*  837 */     this.m_menuItemSaveSongbookAs.setAction(new AbstractAction(this.m_resources
/*  838 */           .getString("Menu.File.Songbook.SaveAs"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  842 */             if (null == SG02Frame.this.m_songbook) {
/*  843 */               SG02Frame.this.m_songbook = new Songbook(SG02Frame.this.m_modelSongs, SG02Frame.this.m_printTable);
/*      */             }
/*  845 */             JFileChooser jFileChooser = new JFileChooser();
/*  846 */             String str = SG02App.props.getProperty("path.recent.songbook");
/*  847 */             if (null != str) {
/*      */               
/*  849 */               jFileChooser.setSelectedFile(new File(str));
/*  850 */               jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */             } 
/*  852 */             jFileChooser.setFileFilter(new FileFilterSGB());
/*      */             
/*  854 */             int i = jFileChooser.showSaveDialog(SG02Frame.this.m_thisFrame);
/*      */             
/*  856 */             if (0 == i) {
/*      */               
/*  858 */               File file = jFileChooser.getSelectedFile();
/*  859 */               SG02App.props.setProperty("path.recent.songbook", file.getParent());
/*      */               
/*  861 */               boolean bool = true;
/*      */               
/*  863 */               if (file.exists())
/*      */               {
/*  865 */                 if (0 != JOptionPane.showConfirmDialog(null, SG02Frame.this
/*  866 */                     .m_resources.getString("Text.Message.Overwrite"), SG02Frame.this
/*  867 */                     .m_resources.getString("Title.Dialog.Confirm"), 0))
/*      */                 {
/*  869 */                   bool = false;
/*      */                 }
/*      */               }
/*  872 */               if (bool) {
/*      */                 
/*  874 */                 if (!(new FileFilterSGB()).accept(file)) {
/*  875 */                   file = new File(file.toString() + ".sgb");
/*      */                 }
/*  877 */                 SG02Frame.this.m_songbook.save(file);
/*  878 */                 SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.FALSE);
/*  879 */                 SG02Frame.this.m_thisFrame.setTitle();
/*  880 */                 SG02Frame.this.m_menuItemSaveSongbook.setEnabled(true);
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  887 */     if (!SG02App.isMac)
/*  888 */       this.m_menuItemSaveSongbookAs.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Songbook.SaveAs.Mn")).intValue()); 
/*  889 */     this.m_menuItemSaveSongbookAs.setEnabled(false);
/*  890 */     jMenu.add(this.m_menuItemSaveSongbookAs);
/*      */     
/*  892 */     jMenu.add(new JSeparator());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  897 */     jMenuItem = new JMenuItem();
/*  898 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/*  899 */           .getString("Command.PageSetup"))
/*      */         {
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*      */             try {
/*  905 */               SG02Frame.this.resetPageOrientation();
/*      */               
/*  907 */               PrinterJob printerJob = PrinterJob.getPrinterJob();
/*  908 */               Book book = new Book();
/*  909 */               printerJob.setPageable(book);
/*      */ 
/*      */               
/*  912 */               SG02Frame.this.m_pageFormat = printerJob.pageDialog(SG02Frame.this.m_pageFormat);
/*      */             }
/*  914 */             catch (Exception exception) {}
/*      */           }
/*      */         });
/*      */     
/*  918 */     if (!SG02App.isMac)
/*  919 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Command.PageSetup.Mn")).intValue()); 
/*  920 */     jMenuItem.setAccelerator((KeyStroke)this.m_resources.getObject("Command.PageSetup.Acc"));
/*  921 */     jMenu.add(jMenuItem);
/*      */ 
/*      */     
/*  924 */     this.m_menuItemPrint = new JMenuItem(this.m_actionDoPrint);
/*  925 */     this.m_menuItemPrint.setAccelerator((KeyStroke)this.m_resources.getObject("Command.Print.Acc"));
/*  926 */     this.m_menuItemPrint.setEnabled(false);
/*  927 */     jMenu.add(this.m_menuItemPrint);
/*      */     
/*  929 */     this.m_menuItemPrintTOC = new JMenuItem();
/*  930 */     this.m_menuItemPrintTOC.setAction(new AbstractAction(this.m_resources
/*  931 */           .getString("Menu.File.Print.TOC"))
/*      */         {
/*      */ 
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*      */             try {
/*  938 */               SG02Frame.this.m_thisFrame.m_printTOCOnly = true;
/*  939 */               ProgressMonitor progressMonitor = new ProgressMonitor(SG02Frame.this.m_thisFrame, "", "", 0, 0);
/*  940 */               ThreadPrint threadPrint = new ThreadPrint(SG02Frame.this.m_thisFrame, progressMonitor);
/*  941 */               EventQueue.invokeLater(threadPrint);
/*      */             }
/*  943 */             catch (Exception exception) {}
/*      */           }
/*      */         });
/*      */     
/*  947 */     if (!SG02App.isMac)
/*  948 */       this.m_menuItemPrintTOC.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Print.TOC.Mn")).intValue()); 
/*  949 */     this.m_menuItemPrintTOC.setEnabled(false);
/*  950 */     jMenu.add(this.m_menuItemPrintTOC);
/*      */ 
/*      */     
/*  953 */     if (!SG02App.isMac) {
/*      */       
/*  955 */       jMenu.add(new JSeparator());
/*      */ 
/*      */       
/*  958 */       jMenuItem = new JMenuItem();
/*  959 */       jMenuItem.setAction(new AbstractAction(this.m_resources
/*  960 */             .getString("Menu.File.Exit"))
/*      */           {
/*      */             public void actionPerformed(ActionEvent param1ActionEvent)
/*      */             {
/*  964 */               SG02Frame.this.handleQuit();
/*      */             }
/*      */           });
/*      */       
/*  968 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.File.Exit.Mn")).intValue());
/*  969 */       jMenu.add(jMenuItem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupOptionsMenu(JMenuBar paramJMenuBar) {
/*  980 */     JMenu jMenu1 = new JMenu(this.m_resources.getString("Menu.Options"));
/*  981 */     if (!SG02App.isMac)
/*  982 */       jMenu1.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Mn")).intValue()); 
/*  983 */     paramJMenuBar.add(jMenu1);
/*      */     
/*  985 */     JMenuItem jMenuItem = new JMenuItem();
/*  986 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/*  987 */           .getString("Menu.Options.SetSongsPath"))
/*      */         {
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*  992 */             JFileChooser jFileChooser = new JFileChooser();
/*  993 */             String str = SG02App.props.getProperty("songs.path");
/*  994 */             if (null != str) {
/*      */               
/*  996 */               jFileChooser.setSelectedFile(new File(str));
/*  997 */               jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */             } 
/*  999 */             jFileChooser.setFileSelectionMode(1);
/*      */             
/* 1001 */             int i = jFileChooser.showOpenDialog(SG02Frame.this.m_thisFrame);
/* 1002 */             if (0 == i) {
/*      */               
/* 1004 */               SG02App.props.setProperty("songs.path", jFileChooser.getSelectedFile().toString());
/* 1005 */               SG02App.writeProperties();
/* 1006 */               SG02Frame.this.rescanSongs();
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 1012 */     if (!SG02App.isMac)
/* 1013 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.SetSongsPath.Mn")).intValue()); 
/* 1014 */     jMenu1.add(jMenuItem);
/*      */     
/* 1016 */     jMenu1.add(new JSeparator());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     JMenu jMenu2 = new JMenu(this.m_resources.getString("Menu.PageLayout"));
/* 1022 */     if (!SG02App.isMac)
/* 1023 */       jMenu2.setMnemonic(((Integer)this.m_resources.getObject("Menu.PageLayout.Mn")).intValue()); 
/* 1024 */     jMenu1.add(jMenu2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1029 */     this.m_actionSongsPerPage = new SongsPerPagePropertyAction();
/*      */     
/* 1031 */     ButtonGroup buttonGroup = new ButtonGroup();
/*      */     
/* 1033 */     jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionSongsPerPage, 1);
/* 1034 */     jMenuItem.setText(this.m_resources.getString("Menu.PageLayout.kOne"));
/* 1035 */     if (!SG02App.isMac)
/* 1036 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.PageLayout.kOne.Mn")).intValue()); 
/* 1037 */     buttonGroup.add(jMenuItem);
/* 1038 */     jMenu2.add(jMenuItem);
/*      */     
/* 1040 */     jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionSongsPerPage, 2);
/* 1041 */     jMenuItem.setText(this.m_resources.getString("Menu.PageLayout.kTwo"));
/* 1042 */     if (!SG02App.isMac)
/* 1043 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.PageLayout.kTwo.Mn")).intValue()); 
/* 1044 */     buttonGroup.add(jMenuItem);
/* 1045 */     jMenu2.add(jMenuItem);
/*      */     
/* 1047 */     jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionSongsPerPage, 4);
/* 1048 */     jMenuItem.setText(this.m_resources.getString("Menu.PageLayout.kFour"));
/* 1049 */     if (!SG02App.isMac)
/* 1050 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.PageLayout.kFour.Mn")).intValue()); 
/* 1051 */     buttonGroup.add(jMenuItem);
/* 1052 */     jMenu2.add(jMenuItem);
/*      */     
/* 1054 */     jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionSongsPerPage, 10);
/* 1055 */     jMenuItem.setText(this.m_resources.getString("Menu.PageLayout.kOneColumn"));
/* 1056 */     if (!SG02App.isMac)
/* 1057 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.PageLayout.kOneColumn.Mn")).intValue()); 
/* 1058 */     buttonGroup.add(jMenuItem);
/* 1059 */     jMenu2.add(jMenuItem);
/*      */     
/* 1061 */     jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionSongsPerPage, 20);
/* 1062 */     jMenuItem.setText(this.m_resources.getString("Menu.PageLayout.kTwoColumn"));
/* 1063 */     if (!SG02App.isMac)
/* 1064 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.PageLayout.kTwoColumn.Mn")).intValue()); 
/* 1065 */     buttonGroup.add(jMenuItem);
/* 1066 */     jMenu2.add(jMenuItem);
/*      */ 
/*      */ 
/*      */     
/* 1070 */     jMenu2 = new JMenu(this.m_resources.getString("Menu.AllSongs"));
/* 1071 */     if (!SG02App.isMac)
/* 1072 */       jMenu2.setMnemonic(((Integer)this.m_resources.getObject("Menu.AllSongs.Mn")).intValue()); 
/* 1073 */     jMenu1.add(jMenu2);
/*      */ 
/*      */ 
/*      */     
/* 1077 */     this.m_actionPrintChords = new PrintChordsPropertyAction();
/*      */     
/* 1079 */     buttonGroup = new ButtonGroup();
/*      */     
/* 1081 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionPrintChords, "yes");
/* 1082 */     jMenuItem.setText(this.m_resources.getString("Menu.AllSongs.PrintChords"));
/* 1083 */     if (!SG02App.isMac)
/* 1084 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.AllSongs.PrintChords.Mn")).intValue()); 
/* 1085 */     buttonGroup.add(jMenuItem);
/* 1086 */     jMenu2.add(jMenuItem);
/*      */     
/* 1088 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionPrintChords, "no");
/* 1089 */     jMenuItem.setText(this.m_resources.getString("Menu.AllSongs.DontPrintChords"));
/* 1090 */     if (!SG02App.isMac)
/* 1091 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.AllSongs.DontPrintChords.Mn")).intValue()); 
/* 1092 */     buttonGroup.add(jMenuItem);
/* 1093 */     jMenu2.add(jMenuItem);
/*      */     
/* 1095 */     JMenu jMenu3 = new JMenu(this.m_resources.getString("Menu.Transpose"));
/* 1096 */     if (!SG02App.isMac)
/* 1097 */       jMenu3.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.Mn")).intValue()); 
/* 1098 */     jMenu2.add(jMenu3);
/*      */ 
/*      */ 
/*      */     
/* 1102 */     this.m_actionTranspose = new TransposePropertyAction();
/* 1103 */     this.m_actionTransposeOther = new TransposeOtherPropertyAction();
/*      */     
/* 1105 */     buttonGroup = new ButtonGroup();
/*      */     
/* 1107 */     jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionTranspose, 0);
/* 1108 */     jMenuItem.setText(this.m_resources.getString("Menu.Transpose.None"));
/* 1109 */     if (!SG02App.isMac)
/* 1110 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.None.Mn")).intValue()); 
/* 1111 */     buttonGroup.add(jMenuItem);
/* 1112 */     jMenu3.add(jMenuItem);
/*      */     
/* 1114 */     JMenu jMenu4 = new JMenu(this.m_resources.getString("Menu.Transpose.ByHalfStep"));
/* 1115 */     if (!SG02App.isMac)
/* 1116 */       jMenu4.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.ByHalfStep.Mn")).intValue()); 
/* 1117 */     jMenu3.add(jMenu4);
/*      */     
/* 1119 */     for (byte b = -3; b <= 3; b++) {
/*      */       
/* 1121 */       if (0 != b) {
/*      */         
/* 1123 */         jMenuItem = new IntegerPropertyRadioMenuItem(this.m_actionTranspose, b);
/* 1124 */         jMenuItem.setText(String.valueOf(b));
/* 1125 */         buttonGroup.add(jMenuItem);
/* 1126 */         jMenu4.add(jMenuItem);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1131 */     jMenu4.add(new JSeparator());
/*      */     
/* 1133 */     jMenuItem = new TransposeOtherMenuItem(this.m_actionTransposeOther);
/* 1134 */     buttonGroup.add(jMenuItem);
/* 1135 */     jMenu4.add(jMenuItem);
/*      */ 
/*      */     
/* 1138 */     jMenu4 = new JMenu(this.m_resources.getString("Menu.Transpose.ToKey"));
/* 1139 */     if (!SG02App.isMac)
/* 1140 */       jMenu4.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.ToKey.Mn")).intValue()); 
/* 1141 */     jMenu3.add(jMenu4);
/*      */     
/* 1143 */     jMenuItem = new JMenuItem(this.m_resources.getString("Menu.Transpose.ToKey.NotThisMenu"));
/* 1144 */     jMenuItem.setEnabled(false);
/* 1145 */     jMenu4.add(jMenuItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     jMenu2 = new JMenu(this.m_resources.getString("Menu.Output"));
/* 1152 */     if (!SG02App.isMac)
/* 1153 */       jMenu2.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.Mn")).intValue()); 
/* 1154 */     jMenu1.add(jMenu2);
/*      */ 
/*      */ 
/*      */     
/* 1158 */     this.m_actionOutputDestination = new OutputDestinationPropertyAction();
/*      */     
/* 1160 */     buttonGroup = new ButtonGroup();
/*      */     
/* 1162 */     String str = this.m_resources.getString("Menu.Output.Printer");
/* 1163 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionOutputDestination, str);
/* 1164 */     jMenuItem.setText(str);
/* 1165 */     if (!SG02App.isMac)
/* 1166 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.Printer.Mn")).intValue()); 
/* 1167 */     buttonGroup.add(jMenuItem);
/* 1168 */     jMenu2.add(jMenuItem);
/*      */     
/* 1170 */     str = this.m_resources.getString("Menu.Output.Plaintext");
/* 1171 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionOutputDestination, str);
/* 1172 */     jMenuItem.setText(str);
/* 1173 */     if (!SG02App.isMac)
/* 1174 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.Plaintext.Mn")).intValue()); 
/* 1175 */     buttonGroup.add(jMenuItem);
/* 1176 */     jMenu2.add(jMenuItem);
/*      */     
/* 1178 */     str = this.m_resources.getString("Menu.Output.HTML");
/* 1179 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionOutputDestination, str);
/* 1180 */     jMenuItem.setText(str);
/* 1181 */     if (!SG02App.isMac)
/* 1182 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.HTML.Mn")).intValue()); 
/* 1183 */     buttonGroup.add(jMenuItem);
/* 1184 */     jMenu2.add(jMenuItem);
/*      */     
/* 1186 */     str = this.m_resources.getString("Menu.Output.ChordPro");
/* 1187 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionOutputDestination, str);
/* 1188 */     jMenuItem.setText(str);
/* 1189 */     if (!SG02App.isMac)
/* 1190 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.ChordPro.Mn")).intValue()); 
/* 1191 */     buttonGroup.add(jMenuItem);
/* 1192 */     jMenu2.add(jMenuItem);
/*      */     
/* 1194 */     str = this.m_resources.getString("Menu.Output.RTF");
/* 1195 */     jMenuItem = new StringPropertyRadioMenuItem(this.m_actionOutputDestination, str);
/* 1196 */     jMenuItem.setText(str);
/* 1197 */     if (!SG02App.isMac)
/* 1198 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Output.RTF.Mn")).intValue()); 
/* 1199 */     buttonGroup.add(jMenuItem);
/* 1200 */     jMenu2.add(jMenuItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1206 */     setupFontsMenuItems(jMenu1);
/*      */     
/* 1208 */     jMenu1.add(new JSeparator());
/*      */ 
/*      */     
/* 1211 */     jMenuItem = new JMenuItem();
/* 1212 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1213 */           .getString("Menu.Options.EnterCode"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1217 */             RegisterNagDialog.enterRegistrationCode();
/*      */           }
/*      */         });
/*      */     
/* 1221 */     if (!SG02App.isMac)
/* 1222 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.EnterCode.Mn")).intValue()); 
/* 1223 */     jMenu1.add(jMenuItem);
/*      */     
/* 1225 */     jMenu1.add(new JSeparator());
/*      */     
/* 1227 */     setupOptionsDlgMenuItems(jMenu1);
/*      */ 
/*      */     
/* 1230 */     jMenu1.add(new JSeparator());
/*      */     
/* 1232 */     jMenuItem = new JMenuItem();
/* 1233 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1234 */           .getString("Menu.Options.Chordrc"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1238 */             boolean bool = ('y' == SG02App.props.getProperty("print.chords.ukulele").charAt(0)) ? true : false;
/* 1239 */             ChordrcEditor.editChordrc(SG02Frame.this.m_thisFrame, bool);
/*      */           }
/*      */         });
/*      */     
/* 1243 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Chordrc.Mn")).intValue());
/* 1244 */     jMenu1.add(jMenuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFontsMenuItems(JMenu paramJMenu) {
/* 1250 */     JMenu jMenu = new JMenu(this.m_resources.getString("Menu.Options.Fonts"));
/* 1251 */     jMenu.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Fonts.Mn")).intValue());
/* 1252 */     paramJMenu.add(jMenu);
/*      */ 
/*      */ 
/*      */     
/* 1256 */     JMenuItem jMenuItem = new JMenuItem();
/* 1257 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1258 */           .getString("Menu.Options.Fonts.Bigger"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1262 */             FontSettingsDialog.adjustFonts(SG02App.props, "print", 1);
/* 1263 */             SG02Frame.this.m_miniPreview.setSongFile(SG02Frame.this.m_miniPreview.getSongFile());
/* 1264 */             SG02App.writeProperties();
/*      */           }
/*      */         });
/*      */     
/* 1268 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Fonts.Bigger.Mn")).intValue());
/* 1269 */     jMenuItem.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.Options.Fonts.Bigger.Acc"));
/* 1270 */     jMenu.add(jMenuItem);
/*      */     
/* 1272 */     jMenuItem.getInputMap(2).put(KeyStroke.getKeyStroke(107, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "makeFontsBigger");
/* 1273 */     jMenuItem.getInputMap(2).put(KeyStroke.getKeyStroke(61, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "makeFontsBigger");
/* 1274 */     jMenuItem.getInputMap(2).put(KeyStroke.getKeyStroke(61, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40), "makeFontsBigger");
/* 1275 */     jMenuItem.getActionMap().put("makeFontsBigger", jMenuItem.getAction());
/*      */     
/* 1277 */     jMenuItem = new JMenuItem();
/* 1278 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1279 */           .getString("Menu.Options.Fonts.Smaller"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1283 */             FontSettingsDialog.adjustFonts(SG02App.props, "print", -1);
/* 1284 */             SG02Frame.this.m_miniPreview.setSongFile(SG02Frame.this.m_miniPreview.getSongFile());
/* 1285 */             SG02App.writeProperties();
/*      */           }
/*      */         });
/*      */     
/* 1289 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Fonts.Smaller.Mn")).intValue());
/* 1290 */     jMenuItem.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.Options.Fonts.Smaller.Acc"));
/* 1291 */     jMenu.add(jMenuItem);
/*      */     
/* 1293 */     jMenuItem = new JMenuItem();
/* 1294 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1295 */           .getString("Menu.Options.Printer"))
/*      */         {
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1300 */             FontSettingsDialog.showFontSettingsDialog(SG02Frame.this.m_thisFrame, "print", "fullscreen", SG02App.props, Color.WHITE);
/* 1301 */             SG02Frame.this.m_miniPreview.setSongFile(SG02Frame.this.m_miniPreview.getSongFile());
/* 1302 */             SG02App.writeProperties();
/*      */           }
/*      */         });
/*      */     
/* 1306 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Printer.Mn")).intValue());
/* 1307 */     jMenu.add(jMenuItem);
/*      */     
/* 1309 */     jMenuItem = new JMenuItem();
/* 1310 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1311 */           .getString("Menu.Options.FullScreen"))
/*      */         {
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1316 */             Color color = new Color(Integer.parseInt(SG02App.props.getProperty("fullscreen.color.background"), 16));
/* 1317 */             FontSettingsDialog.showFontSettingsDialog(SG02Frame.this.m_thisFrame, "fullscreen", "print", SG02App.props, color);
/* 1318 */             SG02App.writeProperties();
/*      */           }
/*      */         });
/*      */     
/* 1322 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.FullScreen.Mn")).intValue());
/* 1323 */     jMenu.add(jMenuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupOptionsDlgMenuItems(JMenu paramJMenu) {
/* 1333 */     OptionsDlgAction optionsDlgAction = new OptionsDlgAction(this);
/*      */     
/* 1335 */     JMenuItem jMenuItem = new JMenuItem(optionsDlgAction);
/* 1336 */     jMenuItem.setText(this.m_resources.getString("Menu.Options.General"));
/* 1337 */     if (!SG02App.isMac) {
/* 1338 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.General.Mn")).intValue());
/*      */     }
/*      */ 
/*      */     
/* 1342 */     paramJMenu.add(jMenuItem);
/*      */     
/* 1344 */     jMenuItem = new JMenuItem(optionsDlgAction);
/* 1345 */     jMenuItem.setText(this.m_resources.getString("Menu.Options.Chords"));
/* 1346 */     if (!SG02App.isMac)
/* 1347 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Chords.Mn")).intValue()); 
/* 1348 */     paramJMenu.add(jMenuItem);
/*      */     
/* 1350 */     jMenuItem = new JMenuItem(optionsDlgAction);
/* 1351 */     jMenuItem.setText(this.m_resources.getString("Menu.Options.Printer"));
/* 1352 */     if (!SG02App.isMac)
/* 1353 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.Printer.Mn")).intValue()); 
/* 1354 */     paramJMenu.add(jMenuItem);
/*      */     
/* 1356 */     jMenuItem = new JMenuItem(optionsDlgAction);
/* 1357 */     jMenuItem.setText(this.m_resources.getString("Menu.Options.HTML"));
/* 1358 */     if (!SG02App.isMac)
/* 1359 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.HTML.Mn")).intValue()); 
/* 1360 */     paramJMenu.add(jMenuItem);
/*      */     
/* 1362 */     jMenuItem = new JMenuItem(optionsDlgAction);
/* 1363 */     jMenuItem.setText(this.m_resources.getString("Menu.Options.FullScreen"));
/* 1364 */     if (!SG02App.isMac)
/* 1365 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.FullScreen.Mn")).intValue()); 
/* 1366 */     paramJMenu.add(jMenuItem);
/*      */     
/* 1368 */     jMenuItem = new JMenuItem(optionsDlgAction);
/* 1369 */     jMenuItem.setText(this.m_resources.getString("Menu.Options.DataValues"));
/* 1370 */     if (!SG02App.isMac)
/* 1371 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Options.DataValues.Mn")).intValue()); 
/* 1372 */     paramJMenu.add(jMenuItem);
/*      */   }
/*      */ 
/*      */   
/*      */   class OptionsDlgAction
/*      */     extends AbstractAction
/*      */   {
/*      */     SG02Frame m_parentFrame;
/*      */ 
/*      */     
/*      */     OptionsDlgAction(SG02Frame param1SG02Frame1) {
/* 1383 */       this.m_parentFrame = param1SG02Frame1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1388 */       byte b = 0;
/*      */       
/* 1390 */       if (0 == param1ActionEvent.getActionCommand().compareTo(SG02Frame.this.m_resources.getString("Menu.Options.Chords"))) {
/* 1391 */         b = 1;
/* 1392 */       } else if (0 == param1ActionEvent.getActionCommand().compareTo(SG02Frame.this.m_resources.getString("Menu.Options.Printer"))) {
/* 1393 */         b = 2;
/* 1394 */       } else if (0 == param1ActionEvent.getActionCommand().compareTo(SG02Frame.this.m_resources.getString("Menu.Options.HTML"))) {
/* 1395 */         b = 3;
/* 1396 */       } else if (0 == param1ActionEvent.getActionCommand().compareTo(SG02Frame.this.m_resources.getString("Menu.Options.FullScreen"))) {
/* 1397 */         b = 4;
/* 1398 */       } else if (0 == param1ActionEvent.getActionCommand().compareTo(SG02Frame.this.m_resources.getString("Menu.Options.DataValues"))) {
/* 1399 */         b = 5;
/*      */       } 
/* 1401 */       OptionsDlg optionsDlg = new OptionsDlg(this.m_parentFrame, b);
/*      */       
/* 1403 */       if (0 == optionsDlg.show()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1410 */         SG02App.props = optionsDlg.m_props;
/* 1411 */         SG02App.writeProperties();
/*      */ 
/*      */         
/* 1414 */         SG02Frame.this.m_actionOutputDestination.updateFromNewProps(optionsDlg.m_props);
/* 1415 */         SG02Frame.this.m_actionPrintChords.updateFromNewProps(optionsDlg.m_props);
/* 1416 */         SG02Frame.this.m_actionSongsPerPage.updateFromNewProps(optionsDlg.m_props);
/*      */         
/* 1418 */         SG02Frame.this.m_bApplyPropertiesToPrintList = true;
/*      */         
/* 1420 */         if (optionsDlg.m_rescanSongs) {
/* 1421 */           SG02Frame.this.rescanSongs();
/*      */         }
/* 1423 */         SG02Frame.this.m_miniPreview.setSongFile(SG02Frame.this.m_miniPreview.getSongFile());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isOkToReplaceCurrentSongbook() {
/* 1431 */     if (null != this.m_songbook && Boolean.TRUE == getRootPane().getClientProperty("Window.documentModified")) {
/*      */       
/* 1433 */       int i = JOptionPane.showConfirmDialog(this.m_thisFrame, this.m_resources
/* 1434 */           .getString("Text.Message.Songbook.Replace"), this.m_resources
/* 1435 */           .getString("Title.Dialog.Confirm"), 1, 3);
/*      */ 
/*      */       
/* 1438 */       if (i == 0)
/*      */       {
/* 1440 */         return this.m_songbook.save(this.m_songbook.getFile());
/*      */       }
/* 1442 */       if (i == 2)
/*      */       {
/* 1444 */         return false;
/*      */       }
/*      */     } 
/* 1447 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean openSongbook(File paramFile) {
/* 1453 */     if (isOkToReplaceCurrentSongbook()) {
/*      */       
/* 1455 */       SG02App.props.setProperty("path.recent.songbook", paramFile.getParent());
/*      */       
/* 1457 */       this.m_songbook = new Songbook(this.m_modelSongs, this.m_printTable, paramFile);
/*      */ 
/*      */ 
/*      */       
/* 1461 */       this.m_songsTable.clearSelection();
/* 1462 */       onAdd();
/*      */ 
/*      */ 
/*      */       
/* 1466 */       this.m_menuItemSaveSongbook.setEnabled(true);
/*      */       
/* 1468 */       int i = this.m_songbook.getSongCount();
/* 1469 */       int j = this.m_songbook.getMissingSongCount();
/* 1470 */       if (j == 0) {
/* 1471 */         getRootPane().putClientProperty("Window.documentModified", Boolean.FALSE);
/*      */       } else {
/*      */         
/* 1474 */         getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/* 1475 */         String str1 = this.m_resources.getString("Text.Error.SGBMissingSongs");
/* 1476 */         String str2 = String.format(str1, new Object[] { Integer.valueOf(i - j), Integer.valueOf(i) });
/* 1477 */         JOptionPane.showMessageDialog(this.m_thisFrame, str2, this.m_resources
/*      */             
/* 1479 */             .getString("Title.Dialog.Warning"), 2);
/*      */       } 
/*      */       
/* 1482 */       this.m_thisFrame.setTitle();
/*      */       
/* 1484 */       return true;
/*      */     } 
/* 1486 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetPageOrientation() {
/*      */     try {
/* 1495 */       if (null == this.m_pageFormat) {
/*      */         
/* 1497 */         PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 1498 */         this.m_pageFormat = printerJob.defaultPage();
/*      */       } 
/*      */       
/* 1501 */       if (Integer.parseInt(SG02App.props.getProperty("print.songs.per.page")) == 2) {
/* 1502 */         this.m_pageFormat.setOrientation(0);
/*      */       } else {
/* 1504 */         this.m_pageFormat.setOrientation(1);
/*      */       } 
/* 1506 */     } catch (Exception exception) {
/*      */       
/* 1508 */       System.err.println("resetPageOrientation caught exception " + exception.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rescanSongs() {
/*      */     try {
/* 1518 */       this.m_rescanTempSongbook = new Songbook(this.m_modelSongs, this.m_printTable);
/* 1519 */       this.m_rescanTempSongbook.save(File.createTempFile("sg02", null));
/*      */     }
/* 1521 */     catch (IOException iOException) {
/*      */       
/* 1523 */       this.m_rescanTempSongbook = null;
/*      */     } 
/*      */ 
/*      */     
/* 1527 */     if (null != this.m_threadScan)
/*      */     {
/* 1529 */       while (this.m_threadScan.isAlive()) {
/*      */         
/* 1531 */         this.m_threadScan.pleaseStop();
/*      */         
/*      */         try {
/* 1534 */           Thread.sleep(100L);
/*      */         }
/* 1536 */         catch (InterruptedException interruptedException) {}
/*      */       } 
/*      */     }
/*      */     
/* 1540 */     onRemoveAll();
/* 1541 */     this.m_songsTable.clearSongs();
/*      */     
/* 1543 */     this.m_miniPreview.setSongFile(null);
/*      */     
/* 1545 */     ThreadScan threadScan = new ThreadScan(this);
/* 1546 */     this.m_threadScan = threadScan;
/* 1547 */     threadScan.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void repopulateSongsAfterRescan() {
/* 1555 */     repaint(); revalidate();
/*      */     
/* 1557 */     if (this.m_openFile != null) {
/*      */       
/* 1559 */       File file = this.m_openFile;
/* 1560 */       this.m_openFile = null;
/* 1561 */       openSongbook(file);
/*      */     }
/* 1563 */     else if (null != this.m_rescanTempSongbook) {
/*      */       
/* 1565 */       this.m_rescanTempSongbook.load(this.m_rescanTempSongbook.getFile());
/* 1566 */       Boolean bool = (Boolean)getRootPane().getClientProperty("Window.documentModified");
/* 1567 */       onAdd();
/* 1568 */       getRootPane().putClientProperty("Window.documentModified", bool);
/*      */     } 
/*      */     
/* 1571 */     this.m_rescanTempSongbook = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkForUpdate() {
/* 1579 */     Registration registration = new Registration();
/* 1580 */     if (registration.isRegistered()) {
/*      */       
/* 1582 */       if (0 == SG02App.props.getProperty("reminder.update").length())
/*      */       {
/* 1584 */         if (0 == JOptionPane.showConfirmDialog(this.m_thisFrame, this.m_resources
/* 1585 */             .getString("Text.Message.UpdateCheck.Nag"), this.m_resources
/* 1586 */             .getString("Title.Dialog.Confirm"), 0, 3)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1591 */           SG02App.props.setProperty("reminder.update", "yes");
/*      */         }
/*      */         else {
/*      */           
/* 1595 */           SG02App.props.setProperty("reminder.update", "no");
/*      */         } 
/*      */       }
/*      */       
/* 1599 */       if ('y' == SG02App.props.getProperty("reminder.update").charAt(0)) {
/*      */         
/* 1601 */         ThreadUpdateCheck threadUpdateCheck = new ThreadUpdateCheck(this);
/* 1602 */         threadUpdateCheck.start();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupViewMenu(JMenuBar paramJMenuBar) {
/* 1610 */     JMenu jMenu = new JMenu(this.m_resources.getString("Menu.View"));
/* 1611 */     if (!SG02App.isMac)
/* 1612 */       jMenu.setMnemonic(((Integer)this.m_resources.getObject("Menu.View.Mn")).intValue()); 
/* 1613 */     paramJMenuBar.add(jMenu);
/*      */     
/* 1615 */     this.m_menuItemFullScreen = new JMenuItem(this.m_actionDoFullScreen);
/* 1616 */     if (!SG02App.isMac)
/* 1617 */       this.m_menuItemFullScreen.setMnemonic(((Integer)this.m_resources.getObject("Menu.View.FullScreen.Mn")).intValue()); 
/* 1618 */     this.m_menuItemFullScreen.setAccelerator((KeyStroke)this.m_resources.getObject("Menu.View.FullScreen.Acc"));
/* 1619 */     this.m_menuItemFullScreen.setEnabled(false);
/* 1620 */     jMenu.add(this.m_menuItemFullScreen);
/*      */ 
/*      */     
/* 1623 */     jMenu.add(new FullScreenNowMenuItem());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupHelpMenu(JMenuBar paramJMenuBar) {
/* 1632 */     JMenu jMenu1 = new JMenu(this.m_resources.getString("Menu.Help"));
/* 1633 */     if (!SG02App.isMac)
/* 1634 */       jMenu1.setMnemonic(((Integer)this.m_resources.getObject("Menu.Help.Mn")).intValue()); 
/* 1635 */     paramJMenuBar.add(jMenu1);
/*      */ 
/*      */     
/* 1638 */     if (SG02App.isMac) {
/*      */       
/*      */       try {
/*      */         
/* 1642 */         JMenuItem jMenuItem1 = new JMenuItem();
/* 1643 */         File file1 = new File(SG02App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
/* 1644 */         File file2 = new File(file1.getParent(), "../Resources/HtmlHelp/index.html");
/* 1645 */         if (!file2.exists())
/* 1646 */           file2 = new File(System.getProperty("user.dir"), "../HtmlHelp/index.html"); 
/* 1647 */         jMenuItem1.setAction(new LaunchURLAction(file2.toURI().toURL().toExternalForm()));
/* 1648 */         jMenuItem1.setText(this.m_resources.getString("Menu.Help.SGHelp"));
/* 1649 */         jMenu1.add(jMenuItem1);
/*      */       }
/* 1651 */       catch (URISyntaxException uRISyntaxException) {
/*      */       
/* 1653 */       } catch (MalformedURLException malformedURLException) {}
/*      */     }
/*      */ 
/*      */     
/* 1657 */     JMenuItem jMenuItem = new JMenuItem();
/* 1658 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1659 */           .getString("Menu.Help.FormattingCodes"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1663 */             new SimpleHTMLDialog("Menu.Help.FormattingCodes", "FormattingCodes.html");
/*      */           }
/*      */         });
/*      */     
/* 1667 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Help.FormattingCodes.Mn")).intValue());
/* 1668 */     jMenu1.add(jMenuItem);
/*      */     
/* 1670 */     JMenu jMenu2 = new JMenu(this.m_resources.getString("Menu.Help.Web"));
/* 1671 */     if (!SG02App.isMac)
/* 1672 */       jMenu2.setMnemonic(((Integer)this.m_resources.getObject("Menu.Help.Web.Mn")).intValue()); 
/* 1673 */     jMenu1.add(jMenu2);
/*      */     
/* 1675 */     jMenuItem = new JMenuItem();
/* 1676 */     jMenuItem.setAction(new LaunchURLAction("https://www.tenbyten.com/software/songsgen/help/"));
/* 1677 */     jMenuItem.setText(this.m_resources.getString("Menu.Help.Web.Help"));
/* 1678 */     if (!SG02App.isMac)
/* 1679 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Help.Web.Help.Mn")).intValue()); 
/* 1680 */     jMenu2.add(jMenuItem);
/*      */     
/* 1682 */     jMenuItem = new JMenuItem();
/* 1683 */     jMenuItem.setAction(new LaunchURLAction("https://www.tenbyten.com/software/songsgen/"));
/* 1684 */     jMenuItem.setText(this.m_resources.getString("Menu.Help.Web.Home"));
/* 1685 */     if (!SG02App.isMac)
/* 1686 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Help.Web.Home.Mn")).intValue()); 
/* 1687 */     jMenu2.add(jMenuItem);
/*      */ 
/*      */ 
/*      */     
/* 1691 */     jMenu1.add(new JSeparator());
/*      */     
/* 1693 */     jMenuItem = new JMenuItem();
/* 1694 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1695 */           .getString("Menu.Help.ReleaseNotes"))
/*      */         {
/*      */ 
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/*      */             try {
/* 1702 */               String str = "ReleaseNotes_java.html";
/* 1703 */               URL uRL = SG02App.class.getResource(str);
/* 1704 */               if (null == uRL) {
/*      */                 
/* 1706 */                 File file = new File(System.getProperty("user.dir"), "../" + str);
/* 1707 */                 uRL = file.toURI().toURL();
/*      */               } 
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
/* 1719 */               JEditorPane jEditorPane = new JEditorPane(uRL);
/* 1720 */               jEditorPane.setEditable(false);
/* 1721 */               JScrollPane jScrollPane = new JScrollPane(jEditorPane);
/* 1722 */               jScrollPane.setVerticalScrollBarPolicy(22);
/*      */               
/* 1724 */               Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/* 1725 */               dimension.height /= 2;
/* 1726 */               dimension.width = (int)(dimension.width / 1.5D);
/* 1727 */               jScrollPane.setPreferredSize(dimension);
/*      */               
/* 1729 */               JOptionPane.showMessageDialog(null, jScrollPane, SG02Frame.this
/*      */ 
/*      */                   
/* 1732 */                   .m_resources.getString("Menu.Help.ReleaseNotes"), -1);
/*      */ 
/*      */             
/*      */             }
/* 1736 */             catch (Exception exception) {
/*      */               
/* 1738 */               System.err.println(exception);
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/* 1743 */     if (!SG02App.isMac)
/* 1744 */       jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Help.ReleaseNotes.Mn")).intValue()); 
/* 1745 */     jMenu1.add(jMenuItem);
/*      */ 
/*      */     
/* 1748 */     jMenu1.add(new JSeparator());
/*      */     
/* 1750 */     jMenuItem = new JMenuItem();
/* 1751 */     jMenuItem.setAction(new AbstractAction(this.m_resources
/* 1752 */           .getString("Command.About"))
/*      */         {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1756 */             SG02Frame.this.handleAbout();
/*      */           }
/*      */         });
/*      */     
/* 1760 */     jMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Command.About.Mn")).intValue());
/* 1761 */     jMenu1.add(jMenuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JPopupMenu createPopup() {
/* 1769 */     JPopupMenu jPopupMenu = new JPopupMenu();
/*      */     
/* 1771 */     int[] arrayOfInt = this.m_printTable.getSelectedRows();
/* 1772 */     SongFile songFile = this.m_modelPrint.getElementAt(arrayOfInt[0]);
/*      */ 
/*      */ 
/*      */     
/* 1776 */     PrintChordsPopupRadioMenuItem printChordsPopupRadioMenuItem = new PrintChordsPopupRadioMenuItem(this.m_printTable);
/* 1777 */     printChordsPopupRadioMenuItem.setText(this.m_resources.getString("Menu.AllSongs.PrintChords"));
/*      */     
/* 1779 */     jPopupMenu.add(printChordsPopupRadioMenuItem);
/*      */     
/* 1781 */     JMenu jMenu1 = new JMenu(this.m_resources.getString("Menu.Transpose"));
/* 1782 */     if (!SG02App.isMac)
/* 1783 */       jMenu1.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.Mn")).intValue()); 
/* 1784 */     jPopupMenu.add(jMenu1);
/*      */     
/* 1786 */     ButtonGroup buttonGroup = new ButtonGroup();
/*      */     
/* 1788 */     TransposePopupRadioMenuItem transposePopupRadioMenuItem = new TransposePopupRadioMenuItem(this.m_printTable, 0);
/* 1789 */     transposePopupRadioMenuItem.setText(this.m_resources.getString("Menu.Transpose.None"));
/* 1790 */     if (!SG02App.isMac)
/* 1791 */       transposePopupRadioMenuItem.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.None.Mn")).intValue()); 
/* 1792 */     buttonGroup.add(transposePopupRadioMenuItem);
/* 1793 */     jMenu1.add(transposePopupRadioMenuItem);
/*      */     
/* 1795 */     JMenu jMenu2 = new JMenu(this.m_resources.getString("Menu.Transpose.ByHalfStep"));
/* 1796 */     if (!SG02App.isMac)
/* 1797 */       jMenu2.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.ByHalfStep.Mn")).intValue()); 
/* 1798 */     jMenu1.add(jMenu2);
/*      */     
/* 1800 */     for (byte b = -3; b <= 3; b++) {
/*      */       
/* 1802 */       if (0 != b) {
/*      */         
/* 1804 */         transposePopupRadioMenuItem = new TransposePopupRadioMenuItem(this.m_printTable, b);
/* 1805 */         transposePopupRadioMenuItem.setText(String.valueOf(b));
/* 1806 */         buttonGroup.add(transposePopupRadioMenuItem);
/* 1807 */         jMenu2.add(transposePopupRadioMenuItem);
/*      */       } 
/*      */     } 
/*      */     
/* 1811 */     jMenu2.add(new JSeparator());
/*      */ 
/*      */     
/* 1814 */     TransposeOtherPopupRadioMenuItem transposeOtherPopupRadioMenuItem = new TransposeOtherPopupRadioMenuItem(this.m_printTable);
/* 1815 */     buttonGroup.add(transposeOtherPopupRadioMenuItem);
/* 1816 */     jMenu2.add(transposeOtherPopupRadioMenuItem);
/*      */ 
/*      */     
/* 1819 */     jMenu2 = new JMenu(this.m_resources.getString("Menu.Transpose.ToKey"));
/* 1820 */     if (!SG02App.isMac)
/* 1821 */       jMenu2.setMnemonic(((Integer)this.m_resources.getObject("Menu.Transpose.ToKey.Mn")).intValue()); 
/* 1822 */     jMenu1.add(jMenu2);
/*      */     
/* 1824 */     Chord chord = songFile.getKeySignature();
/* 1825 */     if (chord != null)
/*      */     {
/* 1827 */       chord = SG02App.chords.find(chord.getName(), null);
/*      */     }
/* 1829 */     if (chord == null) {
/*      */       
/* 1831 */       JMenuItem jMenuItem = new JMenuItem(this.m_resources.getString("Menu.Transpose.ToKey.SongNoKey"));
/* 1832 */       jMenuItem.setEnabled(false);
/* 1833 */       jMenu2.add(jMenuItem);
/*      */     }
/*      */     else {
/*      */       
/* 1837 */       TreeSet<Chord> treeSet = null;
/*      */       
/* 1839 */       if (songFile.getKeySignature().isMajor()) {
/* 1840 */         treeSet = SG02App.getMajorTransposeChordSet();
/*      */       } else {
/* 1842 */         treeSet = SG02App.getMinorTransposeChordSet();
/*      */       } 
/* 1844 */       Iterator<Chord> iterator = treeSet.iterator();
/*      */       
/* 1846 */       while (iterator.hasNext()) {
/*      */         
/* 1848 */         TransposeByKeyPopupRadioMenuItem transposeByKeyPopupRadioMenuItem = new TransposeByKeyPopupRadioMenuItem(this.m_printTable, SG02App.chords.find(((Chord)iterator.next()).getName(), null));
/* 1849 */         jMenu2.add(transposeByKeyPopupRadioMenuItem);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1854 */     Dimension dimension2 = jMenu1.getPreferredSize();
/* 1855 */     if (150 > dimension2.width) {
/*      */       
/* 1857 */       dimension2.width = 150;
/* 1858 */       jMenu1.setPreferredSize(dimension2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1863 */     Dimension dimension1 = jPopupMenu.getPreferredSize();
/* 1864 */     if (150 > dimension1.width) {
/*      */       
/* 1866 */       dimension1.width = 150;
/* 1867 */       jPopupMenu.setPreferredSize(dimension1);
/*      */     } 
/*      */     
/* 1870 */     return jPopupMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handlePrefs() {
/* 1877 */     OptionsDlgAction optionsDlgAction = new OptionsDlgAction(this);
/* 1878 */     optionsDlgAction.actionPerformed(new ActionEvent(this, 0, ""));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void handleAbout() {
/* 1884 */     AboutBox aboutBox = new AboutBox(this);
/* 1885 */     aboutBox.setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void handleQuit() {
/* 1891 */     WindowEvent windowEvent = new WindowEvent(this, 201);
/* 1892 */     dispatchEvent(windowEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 1901 */     if (0 == paramActionEvent.getActionCommand().compareTo(this.m_resources.getString("Command.Add"))) {
/* 1902 */       onAdd();
/* 1903 */     } else if (0 == paramActionEvent.getActionCommand().compareTo(this.m_resources.getString("Command.Remove"))) {
/* 1904 */       onRemove();
/* 1905 */     } else if (0 == paramActionEvent.getActionCommand().compareTo(this.m_resources.getString("Command.RemoveAll"))) {
/* 1906 */       onRemoveAll();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onAdd() {
/* 1914 */     int[] arrayOfInt = this.m_songsTable.getSelectedRows();
/* 1915 */     for (int b = 0; b < arrayOfInt.length; b++) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
					if(arrayOfInt.length<=0 || b<0){
						return;
					}
					System.out.println("teste sssssssssssssssssss");//xixi
					try{
						
					
	/* 1920 */         int i = this.m_songsTable.convertRowIndexToModel(arrayOfInt[b]);
	/* 1921 */         SongFile songFile = (SongFile)((SongFile)this.m_modelSongs.get(i)).clone();
	/*      */         
	/* 1923 */         songFile.setTranspose(Integer.parseInt(SG02App.props.getProperty("transpose")));
	/* 1924 */         songFile.setPrintChords(('y' == SG02App.props.getProperty("print.chords").charAt(0)));
	/* 1925 */         songFile.setPrintGrids(('y' == SG02App.props.getProperty("grids.print").charAt(0)));
	/*      */         
	/* 1927 */         this.m_printTable.addSong(songFile);

					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
					}
/*      */       }
/* 1929 */       catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1934 */     if (this.m_modelPrint.getSize() != 0) {
/*      */       
/* 1936 */       this.m_buttonMoveUp.setEnabled(true);
/* 1937 */       this.m_buttonMoveDown.setEnabled(true);
/* 1938 */       this.m_buttonPrint.setEnabled(true);
/*      */       
/* 1940 */       this.m_menuItemPrint.setEnabled(true);
/*      */       
/* 1942 */       if (null != this.m_songbook)
/* 1943 */         this.m_menuItemSaveSongbookAs.setEnabled(true); 
/* 1944 */       this.m_menuItemSaveSongbookAs.setEnabled(true);
/*      */       
/* 1946 */       if (0 != SG02App.props.getProperty("output").compareToIgnoreCase("plain text")) {
/* 1947 */         this.m_menuItemPrintTOC.setEnabled(true);
/*      */       }
/* 1949 */       this.m_buttonFullScreen.setEnabled(true);
/* 1950 */       this.m_menuItemFullScreen.setEnabled(true);
/*      */     } 
/*      */     
/* 1953 */     setPrintListLabel();
/*      */     
/* 1955 */     getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/* 1956 */     setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void onRemove() {
/* 1962 */     int[] arrayOfInt = this.m_printTable.getSelectedRows();
/* 1963 */     this.m_printTable.removeSongs(arrayOfInt);
/*      */     
/* 1965 */     if (this.m_modelPrint.getSize() == 0) {
/* 1966 */       onRemoveAll();
/*      */     }
/* 1968 */     setPrintListLabel();
/*      */     
/* 1970 */     getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/* 1971 */     setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void onRemoveAll() {
/* 1977 */     this.m_printTable.clearSongs();
/*      */     
/* 1979 */     this.m_buttonMoveUp.setEnabled(false);
/* 1980 */     this.m_buttonMoveDown.setEnabled(false);
/* 1981 */     this.m_buttonPrint.setEnabled(false);
/*      */     
/* 1983 */     this.m_menuItemPrint.setEnabled(false);
/* 1984 */     this.m_menuItemPrintTOC.setEnabled(false);
/*      */     
/* 1986 */     if (null == this.m_songbook) {
/*      */       
/* 1988 */       this.m_menuItemSaveSongbook.setEnabled(false);
/* 1989 */       this.m_menuItemSaveSongbookAs.setEnabled(false);
/*      */     } 
/*      */     
/* 1992 */     this.m_buttonFullScreen.setEnabled(false);
/* 1993 */     this.m_menuItemFullScreen.setEnabled(false);
/*      */     
/* 1995 */     setPrintListLabel();
/*      */     
/* 1997 */     if (null == this.m_songbook) {
/* 1998 */       getRootPane().putClientProperty("Window.documentModified", Boolean.FALSE);
/*      */     } else {
/* 2000 */       getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/* 2001 */     }  setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setPrintListLabel() {
/* 2007 */     String str = "Label.SongsToPrint.End";
/*      */     
/* 2009 */     int i = this.m_modelPrint.getSize();
/* 2010 */     if (0 == i) {
/*      */       
/* 2012 */       str = "Label.SongsToPrint.End.0";
/* 2013 */       if (this.m_labelPrint.getClass() == JButton.class) {
/* 2014 */         ((JButton)this.m_labelPrint).setText(this.m_resources.getString("Label.SongsToPrint.Begin") + this.m_resources.getString(str));
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2020 */       if (1 == i)
/* 2021 */         str = "Label.SongsToPrint.End.1"; 
/* 2022 */       if (this.m_labelPrint.getClass() == JButton.class) {
/* 2023 */         ((JButton)this.m_labelPrint).setText(this.m_resources.getString("Label.SongsToPrint.Begin") + i + this.m_resources.getString(str));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class PrintChordsPropertyAction
/*      */     extends GenericPropertyAction
/*      */   {
/*      */     PrintChordsPropertyAction() {
/* 2036 */       super(SG02App.props, "print.chords");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2045 */       super.actionPerformed(param1ActionEvent);
/*      */       
/* 2047 */       boolean bool = ('y' == SG02App.props.getProperty("grids.print").charAt(0)) ? true : false;
/*      */       
/*      */       byte b;
/* 2050 */       for (b = 0; b < SG02Frame.this.m_modelSongs.getSize(); b++) {
/*      */         
/* 2052 */         SongFile songFile = SG02Frame.this.m_modelSongs.getElementAt(b);
/* 2053 */         songFile.setPrintChords(this.m_booleanValue);
/* 2054 */         songFile.setPrintGrids(bool);
/*      */       } 
/*      */       
/* 2057 */       if (SG02Frame.this.m_bApplyPropertiesToPrintList) {
/*      */ 
/*      */         
/* 2060 */         for (b = 0; b < SG02Frame.this.m_modelPrint.getSize(); b++) {
/*      */           
/* 2062 */           SongFile songFile = SG02Frame.this.m_modelPrint.getElementAt(b);
/* 2063 */           songFile.setPrintChords(this.m_booleanValue);
/* 2064 */           songFile.setPrintGrids(bool);
/*      */         } 
/* 2066 */         SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/*      */       } 
/*      */       
/* 2069 */       SG02Frame.this.m_miniPreview.setSongFile(SG02Frame.this.m_miniPreview.getSongFile());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class TransposePropertyAction
/*      */     extends GenericPropertyAction
/*      */   {
/*      */     TransposePropertyAction() {
/* 2079 */       super(SG02App.props, "transpose");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2084 */       super.actionPerformed(param1ActionEvent);
/*      */       
/*      */       byte b;
/* 2087 */       for (b = 0; b < SG02Frame.this.m_modelSongs.getSize(); b++) {
/*      */         
/* 2089 */         SongFile songFile = SG02Frame.this.m_modelSongs.getElementAt(b);
/* 2090 */         songFile.setTranspose(this.m_intValue);
/*      */       } 
/*      */       
/* 2093 */       if (SG02Frame.this.m_bApplyPropertiesToPrintList) {
/*      */ 
/*      */         
/* 2096 */         for (b = 0; b < SG02Frame.this.m_modelPrint.getSize(); b++) {
/*      */           
/* 2098 */           SongFile songFile = SG02Frame.this.m_modelPrint.getElementAt(b);
/* 2099 */           songFile.setTranspose(this.m_intValue);
/* 2100 */           SG02Frame.this.m_modelPrint.set(b, songFile);
/*      */         } 
/* 2102 */         SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/*      */       } 
/*      */       
/* 2105 */       SG02Frame.this.m_miniPreview.repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class TransposeOtherPropertyAction
/*      */     extends GenericPropertyAction
/*      */   {
/*      */     TransposeOtherPropertyAction() {
/* 2119 */       super(SG02App.props, "transpose");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2124 */       if (this != param1ActionEvent.getSource()) {
/*      */         
/* 2126 */         super.actionPerformed(param1ActionEvent);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 2131 */           String str = JOptionPane.showInputDialog(SG02Frame.this.m_thisFrame, SG02Frame.this
/* 2132 */               .m_resources.getString("Text.Transpose"), SG02Frame.this
/* 2133 */               .m_resources.getString("Title.Dialog.Transpose"), -1);
/*      */ 
/*      */           
/* 2136 */           if (null != str)
/*      */           {
/* 2138 */             int i = Integer.parseInt(str.toString());
/* 2139 */             SG02App.props.setProperty("transpose", String.valueOf(i));
/* 2140 */             SG02Frame.this.m_actionTranspose.updateFromNewProps(SG02App.props);
/* 2141 */             SG02Frame.this.getRootPane().putClientProperty("Window.documentModified", Boolean.TRUE);
/*      */           }
/*      */         
/* 2144 */         } catch (Exception exception) {
/*      */           
/* 2146 */           System.err.println("Transpose Dialog: caught exception " + exception.toString());
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class OutputDestinationPropertyAction
/*      */     extends GenericPropertyAction
/*      */   {
/*      */     OutputDestinationPropertyAction() {
/* 2158 */       super(SG02App.props, "output");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*      */       String str2;
/*      */       int i;
/* 2163 */       super.actionPerformed(param1ActionEvent);
/*      */       
/* 2165 */       String str1 = getStringPropertyValue();
/*      */ 
/*      */ 
/*      */       
/* 2169 */       boolean bool = true;
/*      */       
/* 2171 */       if (0 == str1.compareToIgnoreCase("plain text")) {
/*      */         
/* 2173 */         str2 = SG02Frame.this.m_resources.getString("Command.Save.Plaintext");
/* 2174 */         i = ((Integer)SG02Frame.this.m_resources.getObject("Command.Save.Plaintext.Mn")).intValue();
/* 2175 */         bool = false;
/*      */       }
/* 2177 */       else if (0 == str1.compareToIgnoreCase("html")) {
/*      */         
/* 2179 */         str2 = SG02Frame.this.m_resources.getString("Command.Save.HTML");
/* 2180 */         i = ((Integer)SG02Frame.this.m_resources.getObject("Command.Save.HTML.Mn")).intValue();
/*      */       }
/* 2182 */       else if (0 == str1.compareToIgnoreCase("chordpro")) {
/*      */         
/* 2184 */         str2 = SG02Frame.this.m_resources.getString("Command.Save.ChordPro");
/* 2185 */         i = ((Integer)SG02Frame.this.m_resources.getObject("Command.Save.ChordPro.Mn")).intValue();
/*      */       }
/* 2187 */       else if (0 == str1.compareToIgnoreCase(SG02Frame.this.m_resources.getString("Menu.Output.RTF"))) {
/*      */         
/* 2189 */         str2 = SG02Frame.this.m_resources.getString("Command.Save.RTF");
/* 2190 */         i = ((Integer)SG02Frame.this.m_resources.getObject("Command.Save.RTF.Mn")).intValue();
/*      */       }
/*      */       else {
/*      */         
/* 2194 */         str2 = SG02Frame.this.m_resources.getString("Command.Print");
/* 2195 */         i = ((Integer)SG02Frame.this.m_resources.getObject("Command.Print.Mn")).intValue();
/*      */       } 
/*      */       
/* 2198 */       SG02Frame.this.m_buttonPrint.setText(str2);
/* 2199 */       if (!SG02App.isMac)
/* 2200 */         SG02Frame.this.m_buttonPrint.setMnemonic(i); 
/* 2201 */       SG02Frame.this.m_menuItemPrint.setText(str2);
/* 2202 */       if (!SG02App.isMac) {
/* 2203 */         SG02Frame.this.m_menuItemPrint.setMnemonic(i);
/*      */       }
/* 2205 */       if (SG02Frame.this.m_modelPrint.getSize() != 0) {
/* 2206 */         SG02Frame.this.m_menuItemPrintTOC.setEnabled(bool);
/*      */       }
/* 2208 */       if (null != SG02Frame.this.m_fastOutputPicker) {
/* 2209 */         SG02Frame.this.m_fastOutputPicker.setSelectedItem(str1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class SongsPerPagePropertyAction
/*      */     extends GenericPropertyAction
/*      */   {
/*      */     SongsPerPagePropertyAction() {
/* 2219 */       super(SG02App.props, "print.songs.per.page");
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2224 */       super.actionPerformed(param1ActionEvent);
/* 2225 */       SG02Frame.this.resetPageOrientation();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class EditSongMenuItem
/*      */     extends JMenuItem
/*      */   {
/*      */     EditSongMenuItem() {
/* 2235 */       setEnabled(false);
/*      */       
/* 2237 */       ListSelectionListener listSelectionListener = new ListSelectionListener()
/*      */         {
/*      */           public void valueChanged(ListSelectionEvent param2ListSelectionEvent)
/*      */           {
/* 2241 */             if (param2ListSelectionEvent.getSource() instanceof ListSelectionModel) {
/*      */               
/* 2243 */               ListSelectionModel listSelectionModel = (ListSelectionModel)param2ListSelectionEvent.getSource();
/* 2244 */               SG02Frame.EditSongMenuItem.this.setEnabled(!listSelectionModel.isSelectionEmpty());
/* 2245 */               if (listSelectionModel.getMaxSelectionIndex() != listSelectionModel.getMinSelectionIndex()) {
/*      */                 
/* 2247 */                 SG02Frame.EditSongMenuItem.this.setText(SG02Frame.this.m_resources.getString("Menu.File.EditSongs"));
/*      */               }
/*      */               else {
/*      */                 
/* 2251 */                 SG02Frame.EditSongMenuItem.this.setText(SG02Frame.this.m_resources.getString("Menu.File.EditSong"));
/*      */               } 
/*      */             } 
/*      */           }
/*      */         };
/* 2256 */       SG02Frame.this.m_songsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
/*      */       
/* 2258 */       setText(SG02Frame.this.m_resources.getString("Menu.File.EditSong"));
/* 2259 */       if (!SG02App.isMac)
/* 2260 */         setMnemonic(((Integer)SG02Frame.this.m_resources.getObject("Menu.File.EditSong.Mn")).intValue()); 
/* 2261 */       setAccelerator((KeyStroke)SG02Frame.this.m_resources.getObject("Menu.File.EditSong.Acc"));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void fireActionPerformed(ActionEvent param1ActionEvent) {
/* 2266 */       super.fireActionPerformed(param1ActionEvent);
/*      */ 
/*      */       
/*      */       try {
/* 2270 */         int[] arrayOfInt = SG02Frame.this.m_songsTable.getSelectedRows();
/* 2271 */         for (byte b = 0; b < arrayOfInt.length; b++)
/*      */         {
/* 2273 */           int i = SG02Frame.this.m_songsTable.convertRowIndexToModel(arrayOfInt[b]);
/* 2274 */           SongFile songFile = SG02Frame.this.m_modelSongs.get(i);
/* 2275 */           editSong(songFile.getPath().toString());
/*      */         }
/*      */       
/* 2278 */       } catch (Exception exception) {
/*      */         
/* 2280 */         JOptionPane.showMessageDialog(SG02Frame.this.m_thisFrame, SG02Frame.this
/* 2281 */             .m_resources.getString("Text.Error.NoSongEditor"), SG02Frame.this
/* 2282 */             .m_resources.getString("Title.Dialog.Error"), 0);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void editSong(String param1String) throws NullPointerException, IOException {
/* 2290 */       File file = new File(SG02App.props.getProperty("songs.editor"));
/*      */ 
/*      */       
/* 2293 */       if (!SG02App.isMac && (!file.exists() || !file.canRead())) {
/* 2294 */         throw new NullPointerException();
/*      */       }
/* 2296 */       if (SG02App.isMac) {
/*      */         
/* 2298 */         if (!file.exists() || !file.canRead())
/*      */         {
/* 2300 */           String[] arrayOfString = new String[3];
/* 2301 */           arrayOfString[0] = "open";
/* 2302 */           arrayOfString[1] = "-e";
/* 2303 */           arrayOfString[2] = param1String;
/* 2304 */           Runtime.getRuntime().exec(arrayOfString);
/*      */         }
/*      */         else
/*      */         {
/* 2308 */           String[] arrayOfString = new String[4];
/* 2309 */           arrayOfString[0] = "open";
/* 2310 */           arrayOfString[1] = "-a";
/* 2311 */           arrayOfString[2] = file.toString();
/* 2312 */           arrayOfString[3] = param1String;
/* 2313 */           Runtime.getRuntime().exec(arrayOfString);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2318 */         String[] arrayOfString = new String[2];
/* 2319 */         arrayOfString[0] = file.toString();
/* 2320 */         arrayOfString[1] = param1String;
/* 2321 */         Runtime.getRuntime().exec(arrayOfString);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class DeleteSongMenuItem
/*      */     extends JMenuItem
/*      */   {
/*      */     DeleteSongMenuItem() {
/* 2331 */       setEnabled(false);
/*      */       
/* 2333 */       ListSelectionListener listSelectionListener = new ListSelectionListener()
/*      */         {
/*      */           public void valueChanged(ListSelectionEvent param2ListSelectionEvent)
/*      */           {
/* 2337 */             if (param2ListSelectionEvent.getSource() instanceof ListSelectionModel) {
/*      */               
/* 2339 */               ListSelectionModel listSelectionModel = (ListSelectionModel)param2ListSelectionEvent.getSource();
/* 2340 */               SG02Frame.DeleteSongMenuItem.this.setEnabled(!listSelectionModel.isSelectionEmpty());
/* 2341 */               if (listSelectionModel.getMaxSelectionIndex() != listSelectionModel.getMinSelectionIndex()) {
/*      */                 
/* 2343 */                 SG02Frame.DeleteSongMenuItem.this.setText(SG02Frame.this.m_resources.getString("Menu.File.DeleteSongs"));
/*      */               }
/*      */               else {
/*      */                 
/* 2347 */                 SG02Frame.DeleteSongMenuItem.this.setText(SG02Frame.this.m_resources.getString("Menu.File.DeleteSong"));
/*      */               } 
/*      */             } 
/*      */           }
/*      */         };
/* 2352 */       SG02Frame.this.m_songsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
/*      */       
/* 2354 */       setText(SG02Frame.this.m_resources.getString("Menu.File.DeleteSong"));
/* 2355 */       if (!SG02App.isMac) {
/* 2356 */         setMnemonic(((Integer)SG02Frame.this.m_resources.getObject("Menu.File.DeleteSong.Mn")).intValue());
/*      */       }
/*      */     }
/*      */     
/*      */     protected void fireActionPerformed(ActionEvent param1ActionEvent) {
/* 2361 */       super.fireActionPerformed(param1ActionEvent);
/*      */ 
/*      */       
/*      */       try {
/* 2365 */         int[] arrayOfInt = SG02Frame.this.m_songsTable.getSelectedRows();
/*      */         
/* 2367 */         String str = (arrayOfInt.length == 1) ? SG02Frame.this.m_resources.getString("Text.Message.DeleteSong") : SG02Frame.this.m_resources.getString("Text.Message.DeleteSongs");
/* 2368 */         if (0 == JOptionPane.showConfirmDialog(SG02Frame.this.m_thisFrame, str, SG02Frame.this
/*      */             
/* 2370 */             .m_resources.getString("Title.Dialog.Confirm"), 0)) {
/*      */ 
/*      */           
/* 2373 */           Object[] arrayOfObject = { SG02Frame.this.m_resources.getString("Command.Cancel"), SG02Frame.this.m_resources.getString("Command.DeleteFiles") };
/*      */           
/* 2375 */           int i = 1;
/*      */           
/* 2377 */           if (!SG02App.isMac) {
/* 2378 */             i = JOptionPane.showOptionDialog(null, SG02Frame.this
/* 2379 */                 .m_resources.getString("Text.Message.DeleteSongs.Double"), SG02Frame.this
/* 2380 */                 .m_resources.getString("Title.Dialog.Warning"), -1, 2, null, arrayOfObject, arrayOfObject[0]);
/*      */           }
/*      */ 
/*      */           
/* 2384 */           if (i == 1)
/*      */           {
/* 2386 */             for (byte b = 0; b < arrayOfInt.length; b++) {
/*      */               
/* 2388 */               int j = SG02Frame.this.m_songsTable.convertRowIndexToModel(arrayOfInt[b]);
/* 2389 */               SongFile songFile = SG02Frame.this.m_modelSongs.get(j);
/* 2390 */               File file = songFile.getPath();
/* 2391 */               if (SG02App.isMac) {
/*      */                 
/* 2393 */                 File file1 = new File(System.getProperty("user.home") + "/.Trash", file.getName());
/* 2394 */                 CopyFile.simpleCopy(file, file1);
/*      */               } 
/*      */               
/* 2397 */               file.delete();
/*      */             } 
/*      */             
/* 2400 */             SG02Frame.this.rescanSongs();
/*      */           }
/*      */         
/*      */         } 
/* 2404 */       } catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class RevealSongsMenuItem
/*      */     extends JMenuItem
/*      */   {
/*      */     RevealSongsMenuItem() {
/* 2417 */       setText(SG02Frame.this.m_resources.getString("Menu.File.Reveal"));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void fireActionPerformed(ActionEvent param1ActionEvent) {
/* 2423 */       super.fireActionPerformed(param1ActionEvent);
/*      */ 
/*      */       
/*      */       try {
/* 2427 */         if (SG02App.isMac)
/*      */         {
/* 2429 */           String str = SG02App.props.getProperty("songs.path");
/*      */           
/* 2431 */           String[] arrayOfString = new String[2];
/* 2432 */           arrayOfString[0] = "open";
/* 2433 */           arrayOfString[1] = str;
/* 2434 */           Runtime.getRuntime().exec(arrayOfString);
/*      */         }
/*      */       
/* 2437 */       } catch (Exception exception) {
/*      */         
/* 2439 */         JOptionPane.showMessageDialog(SG02Frame.this.m_thisFrame, SG02Frame.this
/* 2440 */             .m_resources.getString("Text.Error.NoSongEditor"), SG02Frame.this
/* 2441 */             .m_resources.getString("Title.Dialog.Error"), 0);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class ImportSongMenuItem
/*      */     extends JMenuItem
/*      */   {
/*      */     ImportSongMenuItem() {
/* 2453 */       setText(SG02Frame.this.m_resources.getString("Menu.File.Import"));
/* 2454 */       if (!SG02App.isMac) {
/* 2455 */         setMnemonic(((Integer)SG02Frame.this.m_resources.getObject("Menu.File.Import.Mn")).intValue());
/*      */       }
/*      */     }
/*      */     
/*      */     protected void fireActionPerformed(ActionEvent param1ActionEvent) {
/* 2460 */       super.fireActionPerformed(param1ActionEvent);
/*      */       
/* 2462 */       JFileChooser jFileChooser = new JFileChooser();
/* 2463 */       String str = SG02App.props.getProperty("path.recent.import");
/* 2464 */       if (null != str) {
/*      */         
/* 2466 */         jFileChooser.setSelectedFile(new File(str));
/* 2467 */         jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */       } 
/* 2469 */       jFileChooser.setMultiSelectionEnabled(true);
/*      */       
/* 2471 */       int i = jFileChooser.showOpenDialog(SG02Frame.this.m_thisFrame);
/*      */       
/* 2473 */       if (0 == i) {
/*      */         
/* 2475 */         SongImporter songImporter = new SongImporter();
/* 2476 */         File[] arrayOfFile = jFileChooser.getSelectedFiles();
/* 2477 */         boolean bool = (arrayOfFile.length > 1) ? true : false;
/* 2478 */         byte b1 = 0;
/*      */         
/* 2480 */         SG02App.props.setProperty("path.recent.import", arrayOfFile[0].getParent());
/*      */         
/* 2482 */         if (bool) {
/*      */           
/* 2484 */           i = JOptionPane.showConfirmDialog(SG02Frame.this.m_thisFrame, SG02Frame.this
/* 2485 */               .m_resources.getString("Text.Message.ImportMulti"), SG02Frame.this
/* 2486 */               .m_resources.getString("Title.Dialog.Confirm"), 0, 3);
/*      */ 
/*      */ 
/*      */           
/* 2490 */           if (0 != i) {
/*      */             return;
/*      */           }
/*      */         } 
/* 2494 */         for (byte b2 = 0; b2 < arrayOfFile.length; b2++) {
/*      */ 
/*      */           
/*      */           try {
/* 2498 */             i = songImporter.importFile(arrayOfFile[b2]);
/*      */             
/* 2500 */             if (0 != i)
/*      */             {
/* 2502 */               if (!bool)
/*      */               {
/* 2504 */                 if (2 == i)
/*      */                 {
/* 2506 */                   JOptionPane.showMessageDialog(SG02Frame.this.m_thisFrame, SG02Frame.this
/* 2507 */                       .m_resources.getString("Text.Message.ImportMaybeOK"), SG02Frame.this
/* 2508 */                       .m_resources.getString("Title.Dialog.Warning"), 2);
/*      */                 }
/*      */ 
/*      */                 
/* 2512 */                 jFileChooser = new JFileChooser();
/* 2513 */                 str = SG02App.props.getProperty("songs.path");
/* 2514 */                 if (null != str) {
/*      */                   
/* 2516 */                   jFileChooser.setSelectedFile(new File(str));
/* 2517 */                   jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*      */                 } 
/* 2519 */                 jFileChooser.setFileFilter(new FileFilterTXT());
/* 2520 */                 i = jFileChooser.showSaveDialog(SG02Frame.this.m_thisFrame);
/*      */                 
/* 2522 */                 if (0 == i)
/*      */                 {
/* 2524 */                   boolean bool1 = true;
/*      */                   
/* 2526 */                   if (jFileChooser.getSelectedFile().exists())
/*      */                   {
/* 2528 */                     if (0 != JOptionPane.showConfirmDialog(null, SG02Frame.this
/* 2529 */                         .m_resources.getString("Text.Message.Overwrite"), SG02Frame.this
/* 2530 */                         .m_resources.getString("Title.Dialog.Confirm"), 0))
/*      */                     {
/* 2532 */                       bool1 = false;
/*      */                     }
/*      */                   }
/* 2535 */                   if (bool1)
/*      */                   {
/* 2537 */                     songImporter.save(jFileChooser.getSelectedFile());
/* 2538 */                     b1++;
/*      */                   }
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/* 2544 */               else if (0 != i)
/*      */               {
/* 2546 */                 File file = new File(SG02App.props.getProperty("songs.path"), arrayOfFile[b2].getName() + "_imp.txt");
/* 2547 */                 songImporter.save(file);
/* 2548 */                 b1++;
/*      */               }
/*      */             
/*      */             }
/*      */           }
/* 2553 */           catch (Exception exception) {}
/*      */         } 
/*      */         
/* 2556 */         if (b1 > 0) {
/*      */           
/* 2558 */           String str1 = SG02Frame.this.m_resources.getString("Text.Message.ImportRescanA");
/* 2559 */           str1 = str1 + String.valueOf(b1);
/* 2560 */           str1 = str1 + SG02Frame.this.m_resources.getString("Text.Message.ImportRescanB");
/*      */           
/* 2562 */           i = JOptionPane.showConfirmDialog(SG02Frame.this.m_thisFrame, str1, SG02Frame.this
/*      */               
/* 2564 */               .m_resources.getString("Title.Dialog.Confirm"), 0, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2569 */           if (0 == i) {
/* 2570 */             SG02Frame.this.rescanSongs();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class TransposeOtherMenuItem
/*      */     extends IntegerPropertyRadioMenuItem
/*      */   {
/*      */     TransposeOtherMenuItem(SG02Frame.TransposeOtherPropertyAction param1TransposeOtherPropertyAction) {
/* 2586 */       super(param1TransposeOtherPropertyAction, 999);
/*      */       
/* 2588 */       setText(SG02Frame.this.m_resources.getString("Menu.Transpose.Other"));
/* 2589 */       if (!SG02App.isMac) {
/* 2590 */         setMnemonic(((Integer)SG02Frame.this.m_resources.getObject("Menu.Transpose.Other.Mn")).intValue());
/*      */       }
/* 2592 */       int i = Integer.parseInt(SG02App.props.getProperty("transpose"));
/* 2593 */       setSelected((i > 3 || i < -3));
/*      */     }
/*      */ 
/*      */     
/*      */     protected PropertyChangeListener createActionPropertyChangeListener(Action param1Action) {
/* 2598 */       return new PropertyChangeListener()
/*      */         {
/*      */           public void propertyChange(PropertyChangeEvent param2PropertyChangeEvent)
/*      */           {
/* 2602 */             if (param2PropertyChangeEvent.getPropertyName() == "transpose")
/*      */             {
/* 2604 */               SG02Frame.TransposeOtherMenuItem.this.setSelected((((GenericPropertyAction)SG02Frame.TransposeOtherMenuItem.this
/* 2605 */                   .getAction()).getIntPropertyValue() > 3 || ((GenericPropertyAction)SG02Frame.TransposeOtherMenuItem.this
/* 2606 */                   .getAction()).getIntPropertyValue() < -3));
/*      */             }
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class FullScreenNowMenuItem
/*      */     extends JMenuItem
/*      */   {
/*      */     FullScreenNowMenuItem() {
/* 2621 */       setEnabled(false);
/*      */       
/* 2623 */       ListSelectionListener listSelectionListener = new ListSelectionListener()
/*      */         {
/*      */           public void valueChanged(ListSelectionEvent param2ListSelectionEvent)
/*      */           {
/* 2627 */             if (param2ListSelectionEvent.getSource() instanceof ListSelectionModel)
/* 2628 */               SG02Frame.FullScreenNowMenuItem.this.setEnabled(!((ListSelectionModel)param2ListSelectionEvent.getSource()).isSelectionEmpty()); 
/*      */           }
/*      */         };
/* 2631 */       SG02Frame.this.m_songsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
/*      */       
/* 2633 */       setText(SG02Frame.this.m_resources.getString("Menu.View.FullScreen.Now"));
/* 2634 */       if (!SG02App.isMac)
/* 2635 */         setMnemonic(((Integer)SG02Frame.this.m_resources.getObject("Menu.View.FullScreen.Now.Mn")).intValue()); 
/* 2636 */       setAccelerator((KeyStroke)SG02Frame.this.m_resources.getObject("Menu.View.FullScreen.Now.Acc"));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void fireActionPerformed(ActionEvent param1ActionEvent) {
/* 2641 */       super.fireActionPerformed(param1ActionEvent);
/*      */ 
/*      */       
/*      */       try {
/* 2645 */         GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 2646 */         GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*      */         
/* 2648 */         int i = Integer.parseInt(SG02App.props.getProperty("fullscreen.device"));
/* 2649 */         if (arrayOfGraphicsDevice.length < i + 1) {
/* 2650 */           i = 0;
/*      */         }
/* 2652 */         FullScreenView fullScreenView = new FullScreenView(arrayOfGraphicsDevice[i]);
/*      */         
/* 2654 */         int[] arrayOfInt = SG02Frame.this.m_songsTable.getSelectedRows();
/* 2655 */         for (byte b = 0; b < arrayOfInt.length; b++) {
/*      */           
/* 2657 */           int j = SG02Frame.this.m_songsTable.convertRowIndexToModel(arrayOfInt[b]);
/* 2658 */           SongFile songFile = SG02Frame.this.m_modelSongs.get(j);
/* 2659 */           fullScreenView.addSongFile(songFile);
/*      */         } 
/*      */         
/* 2662 */         fullScreenView.showFullScreen(0);
/*      */       }
/* 2664 */       catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SG02Frame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */