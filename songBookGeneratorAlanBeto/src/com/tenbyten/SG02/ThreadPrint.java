/*     */ package com.tenbyten.SG02;
import java.awt.EventQueue;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.Book;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.util.ResourceBundle;

/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFileChooser;
import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.ProgressMonitor;
/*     */ 
/*     */ class ThreadPrint extends Thread {
/*     */   public ThreadPrint(SG02Frame paramSG02Frame, ProgressMonitor paramProgressMonitor) {
/*  21 */     this.m_frame = paramSG02Frame;
/*  22 */     this.m_monitor = paramProgressMonitor;
/*     */     
/*  24 */     setDaemon(true);
/*     */   }
/*     */   private SG02Frame m_frame;
/*     */   private ProgressMonitor m_monitor;
/*     */   
/*     */   public void run() {
/*     */     try {
/*  31 */       String str = SG02App.props.getProperty("output");
/*     */       
/*  33 */       if (0 == str.compareToIgnoreCase("printer")) {
/*     */         
/*  35 */         if (SG02App.isDebug) {
/*  36 */           System.err.println("ThreadPrint: output to printer");
/*     */         }
/*  38 */         SongPrinter songPrinter = new SongPrinter();
/*  39 */         for (int b = 0; b < this.m_frame.m_modelPrint.getSize(); b++) {
	
					 SongFile songFile = this.m_frame.m_modelPrint.getElementAt(b);
					 songPrinter.addSongFile(songFile);
/*  41 */          
/*     */         } 
/*  44 */         songPrinter.overridePrintTOCOnly(this.m_frame.m_printTOCOnly);
/*     */         
/*  46 */         PrinterJob printerJob = PrinterJob.getPrinterJob();
/*  47 */         Book book = new Book();
/*     */ 
/*     */         
/*  50 */         PageFormat pageFormat = this.m_frame.m_pageFormat;
/*     */ 
/*     */         
/*  53 */         printerJob.setJobName("Songsheet Generator");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         Paper paper = pageFormat.getPaper();
/*  59 */         paper.setImageableArea(0.0D, 0.0D, paper.getWidth(), paper.getHeight());
/*  60 */         pageFormat.setPaper(paper);
/*  61 */         pageFormat = printerJob.validatePage(pageFormat);
/*     */ 
/*     */ 
/*     */         
/*  65 */         GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  66 */         BufferedImage bufferedImage = new BufferedImage((int)pageFormat.getPaper().getImageableWidth(), (int)pageFormat.getPaper().getImageableHeight(), 11);
/*  67 */         Graphics2D graphics2D = graphicsEnvironment.createGraphics(bufferedImage);
/*     */         
/*  69 */         if (SG02App.isDebug) {
/*  70 */           System.err.println("ThreadPrint: paginating (begin)");
/*     */         }
/*  72 */         songPrinter.paginate(graphics2D, pageFormat);
/*     */         
/*  74 */         if (SG02App.isDebug) {
/*  75 */           System.err.println("ThreadPrint: paginating (complete)");
/*     */         }
/*  77 */         printerJob.setPageable(book);
/*     */         
/*  79 */         book.append(songPrinter, pageFormat, songPrinter.getPageCount());
/*     */         
/*  81 */         if (SG02App.isDebug) {
/*  82 */           System.err.println("ThreadPrint: printDialog");
/*     */         }
/*  84 */         graphics2D.dispose();
/*     */ 
/*     */         
/*  87 */         if (printerJob.printDialog())
/*     */         {
/*  89 */           if (SG02App.isDebug) {
/*  90 */             System.err.println("ThreadPrint: printing after printDialog");
/*     */           }
/*     */           
/*     */           try {
/*  94 */             songPrinter.setProgressMonitor(this.m_monitor);
/*  95 */             if (null != this.m_monitor) {
/*  96 */               this.m_monitor.setMaximum(songPrinter.getPageCount());
/*     */             }
/*  98 */             if (SG02App.isDebug) {
/*  99 */               System.err.println("ThreadPrint: printing (begin) num pages = " + String.valueOf(songPrinter.getPageCount()));
/*     */             }
/* 101 */             printerJob.print();
/*     */             
/* 103 */             if (SG02App.isDebug) {
/* 104 */               System.err.println("ThreadPrint: printing (complete)");
/*     */             }
/* 106 */           } catch (PrinterException printerException) {
/*     */             
/* 108 */             System.err.println("Printing error: " + printerException);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 115 */         ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */         
/* 117 */         if (0 == str.compareToIgnoreCase(resourceBundle.getString("Menu.Output.RTF")) && shouldAbortRTF()) {
/*     */           return;
/*     */         }
/* 120 */         JFileChooser jFileChooser = new JFileChooser();
/* 121 */         String str1 = SG02App.props.getProperty("path.recent.print");
/* 122 */         if (null != str1) {
/*     */           
/* 124 */           jFileChooser.setSelectedFile(new File(str1));
/* 125 */           jFileChooser.setCurrentDirectory(jFileChooser.getSelectedFile());
/*     */         } 
/* 127 */         if (0 == str.compareToIgnoreCase("html")) {
/* 128 */           jFileChooser.setFileFilter(new FileFilterHTML());
/* 129 */         } else if (0 == str.compareToIgnoreCase(resourceBundle.getString("Menu.Output.RTF"))) {
/* 130 */           jFileChooser.setFileFilter(new FileFilterRTF());
/*     */         } else {
/* 132 */           jFileChooser.setFileFilter(new FileFilterTXT());
/*     */         } 
/* 134 */         int i = jFileChooser.showSaveDialog(this.m_frame);
/*     */         
/* 136 */         if (0 == i) {
/*     */           SongPlaintexter songPlaintexter;
/*     */           
/* 139 */           File file = jFileChooser.getSelectedFile();
/* 140 */           SG02App.props.setProperty("path.recent.print", file.getParent());
/*     */ 
/*     */           
/* 143 */           if (0 == str.compareToIgnoreCase("html")) {
/*     */             
/* 145 */             if (!(new FileFilterHTML()).accept(file)) {
/* 146 */               file = new File(file.toString() + ".html");
/*     */             }
/* 148 */           } else if (0 == str.compareToIgnoreCase(resourceBundle.getString("Menu.Output.RTF"))) {
/*     */             
/* 150 */             if (!(new FileFilterRTF()).accept(file)) {
/* 151 */               file = new File(file.toString() + ".rtf");
/*     */             
/*     */             }
/*     */           }
/* 155 */           else if (!(new FileFilterTXT()).accept(file)) {
/* 156 */             file = new File(file.toString() + ".txt");
/*     */           } 
/*     */ 
/*     */           
/* 160 */           if (file.exists()) {
/*     */             
/* 162 */             boolean bool = false;
/*     */ 
/*     */             
/* 165 */             for (byte b1 = 0; b1 < this.m_frame.m_modelPrint.getSize() && !bool; b1++) {
/*     */               
/* 167 */               SongFile songFile = this.m_frame.m_modelPrint.getElementAt(b1);
/* 168 */               if (songFile.getPath().equals(file)) {
/* 169 */                 bool = true;
/*     */               }
/*     */             } 
/* 172 */             if (bool) {
/*     */               
/* 174 */               JOptionPane.showMessageDialog(this.m_frame, resourceBundle
/* 175 */                   .getString("Text.Error.Song.Overwrite"), resourceBundle
/* 176 */                   .getString("Title.Dialog.Error"), 0, null);
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 182 */             if (0 != JOptionPane.showConfirmDialog(this.m_frame, resourceBundle
/* 183 */                 .getString("Text.Message.Overwrite"), resourceBundle
/* 184 */                 .getString("Title.Dialog.Confirm"), 0)) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 191 */           if (0 == str.compareToIgnoreCase("html")) {
/* 192 */             songPlaintexter = new SongHTMLer(file);
/* 193 */           } else if (0 == str.compareToIgnoreCase("chordpro")) {
/* 194 */             songPlaintexter = new ChordProOutput(file);
/* 195 */           } else if (0 == str.compareToIgnoreCase(resourceBundle.getString("Menu.Output.RTF"))) {
/* 196 */             songPlaintexter = new RTFOutput(file);
/*     */           } else {
/* 198 */             songPlaintexter = new SongPlaintexter(file);
/*     */           } 
/* 200 */           for (byte b = 0; b < this.m_frame.m_modelPrint.getSize(); b++) {
/*     */             
/* 202 */             SongFile songFile = this.m_frame.m_modelPrint.getElementAt(b);
/* 203 */             songPlaintexter.addSongFile(songFile);
/*     */           } 
/* 205 */           songPlaintexter.overridePrintTOCOnly(this.m_frame.m_printTOCOnly);
/*     */           
/* 207 */           songPlaintexter.setProgressMonitor(this.m_monitor);
/* 208 */           if (null != this.m_monitor) {
/* 209 */             this.m_monitor.setMaximum(this.m_frame.m_modelPrint.getSize());
/*     */           }
/* 211 */           songPlaintexter.printSongs();
/*     */         }
/*     */       
/*     */       } 
/* 215 */     } catch (Exception exception) {
/*     */       
/* 217 */       System.out.println("ThreadPrint.run(): caught exception:");
/* 218 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 221 */     if (null != this.m_monitor) {
/* 222 */       this.m_monitor.close();
/*     */     }
/* 224 */     Registration registration = new Registration();
/* 225 */     registration.incrementOutputCount();
/*     */     
/* 227 */     if (registration.isNagTime())
/*     */     {
/* 229 */       EventQueue.invokeLater(new RegisterNagDialog(this.m_frame));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldAbortRTF() {
/* 236 */     if ('y' == SG02App.props.getProperty("reminder.rtf.bad").charAt(0)) {
/*     */       
/* 238 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */       
/* 240 */       JOptionPane jOptionPane = new JOptionPane(resourceBundle.getString("Text.Message.Reminder.RTF.Bad"), 2, 0);
/*     */ 
/*     */       
/* 243 */       JDialog jDialog = jOptionPane.createDialog(this.m_frame, resourceBundle.getString("Title.Dialog.Confirm"));
/*     */       
/* 245 */       GenericPropertyAction genericPropertyAction = new GenericPropertyAction(SG02App.props, "reminder.rtf.bad");
/* 246 */       StringPropertyCheckBox stringPropertyCheckBox = new StringPropertyCheckBox(genericPropertyAction, "no", "yes");
/* 247 */       stringPropertyCheckBox.setText(resourceBundle.getString("Text.Message.DoNotShowAgain"));
/*     */       
/* 249 */       JPanel jPanel = new JPanel();
/* 250 */       BoxLayout boxLayout = new BoxLayout(jPanel, 1);
/* 251 */       jPanel.setLayout(boxLayout);
/* 252 */       jPanel.add(jDialog.getContentPane());
/* 253 */       jPanel.add(stringPropertyCheckBox);
/* 254 */       jPanel.add(new JLabel("     "));
/* 255 */       stringPropertyCheckBox.setAlignmentX(boxLayout.getLayoutAlignmentX(jPanel));
/*     */       
/* 257 */       jDialog.setContentPane(jPanel);
/* 258 */       jDialog.pack();
/* 259 */       jDialog.setVisible(true);
/*     */       
/* 261 */       if (0 != String.valueOf(0).compareTo(String.valueOf(jOptionPane.getValue()))) {
/* 262 */         return true;
/*     */       }
/*     */     } 
/* 265 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\ThreadPrint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */