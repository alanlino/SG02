/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.PrinterException;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.event.TableModelEvent;
/*     */ import javax.swing.event.TableModelListener;
/*     */ 
/*     */ class SongPreview
/*     */   extends Component {
/*     */   protected SongFile m_songFile;
/*     */   
/*     */   SongPreview() {
/*  21 */     this.m_pageFormat = new PageFormat();
/*  22 */     this.m_pageFormat.setOrientation(0);
/*     */   }
/*     */   protected SongPrinter m_songPrinter;
/*     */   protected PageFormat m_pageFormat;
/*     */   
/*     */   void listenToTable(SG02Table paramSG02Table, DefaultListModel<SongFile> paramDefaultListModel) {
/*  28 */     paramSG02Table.getSelectionModel().addListSelectionListener(new TableListener(paramSG02Table, paramDefaultListModel));
/*  29 */     paramSG02Table.getModel().addTableModelListener(new TableListener(paramSG02Table, paramDefaultListModel));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SongFile getSongFile() {
/*  35 */     return this.m_songFile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setSongFile(SongFile paramSongFile) {
/*  41 */     this.m_songFile = paramSongFile;
/*     */     
/*  43 */     this.m_songPrinter = new SongPrinter();
/*  44 */     this.m_songPrinter.overrideSongsPerPage(2);
/*  45 */     this.m_songPrinter.overrideMargins(0.12F, 0.12F, 0.12F, 0.12F);
/*  46 */     this.m_songPrinter.overridePrintTOC(false);
/*  47 */     this.m_songPrinter.overrideAutoNumberSongs(false);
/*  48 */     this.m_songPrinter.overrideFooter(false);
/*     */ 
/*     */ 
/*     */     
/*  52 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*  61 */     Dimension dimension = getPreferredSize();
/*  62 */     paramGraphics.setColor(Color.white);
/*  63 */     paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/*  64 */     paramGraphics.setColor(Color.black);
/*     */     
/*  66 */     if (null != this.m_songPrinter && null != this.m_songFile) {
/*     */       
/*  68 */       this.m_songPrinter.clearSongFiles();
/*  69 */       this.m_songPrinter.addSongFile(this.m_songFile);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  74 */         this.m_songPrinter.paginate(paramGraphics, this.m_pageFormat);
/*  75 */         this.m_songPrinter.print(paramGraphics, this.m_pageFormat, 0);
/*     */       }
/*  77 */       catch (PrinterException printerException) {
/*     */         
/*  79 */         System.err.println("Printing error: " + printerException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/*  90 */     return new Dimension((int)this.m_pageFormat.getWidth(), (int)this.m_pageFormat.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   class TableListener
/*     */     implements ListSelectionListener, TableModelListener
/*     */   {
/*     */     SG02Table m_table;
/*     */     
/*     */     DefaultListModel<SongFile> m_songs;
/*     */     
/*     */     TableListener(SG02Table param1SG02Table, DefaultListModel<SongFile> param1DefaultListModel) {
/* 102 */       this.m_table = param1SG02Table;
/* 103 */       this.m_songs = param1DefaultListModel;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 110 */       Object object = param1ListSelectionEvent.getSource();
/*     */       
/* 112 */       if (object instanceof ListSelectionModel) {
/*     */         
/* 114 */         ListSelectionModel listSelectionModel = (ListSelectionModel)object;
/* 115 */         if (listSelectionModel.isSelectionEmpty()) {
/* 116 */           SongPreview.this.setSongFile(null);
/*     */         } else {
/*     */           
/* 119 */           int i = this.m_table.convertRowIndexToModel(listSelectionModel.getMaxSelectionIndex());
/* 120 */           if (i < this.m_songs.size()) {
/* 121 */             SongPreview.this.setSongFile(this.m_songs.get(i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void tableChanged(TableModelEvent param1TableModelEvent) {
/* 131 */       if (this.m_table.getSelectedRowCount() > 0) {
/*     */         
/* 133 */         int i = param1TableModelEvent.getLastRow();
/* 134 */         if (i < this.m_songs.size())
/* 135 */           SongPreview.this.setSongFile(this.m_songs.get(i)); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongPreview.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */