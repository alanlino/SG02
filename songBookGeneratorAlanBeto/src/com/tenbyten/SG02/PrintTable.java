/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.table.TableColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PrintTable
/*     */   extends SG02Table
/*     */ {
/*     */   PrintTable(DefaultListModel<SongFile> paramDefaultListModel) {
/*  18 */     super(new PrintTableModel(paramDefaultListModel));
/*     */     
/*  20 */     TableColumn tableColumn = getColumnModel().getColumn(0);
/*  21 */     String str = SG02App.props.getProperty("window.printTable.column.0");
/*  22 */     if (str != null) {
/*  23 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*     */     } else {
/*  25 */       tableColumn.setPreferredWidth(200);
/*     */     } 
/*  27 */     tableColumn = getColumnModel().getColumn(1);
/*  28 */     str = SG02App.props.getProperty("window.printTable.column.1");
/*  29 */     if (str != null) {
/*  30 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*     */     } else {
/*  32 */       tableColumn.setPreferredWidth(5);
/*     */     }

			  tableColumn = getColumnModel().getColumn(2);
/*  28 */     str = SG02App.props.getProperty("window.printTable.column.2");
/*  29 */     if (str != null) {
/*  30 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*     */     } else {
/*  32 */       tableColumn.setPreferredWidth(20);
/*     */     } 	

/*  34 */     setSelectionMode(0);
/*     */     
/*  36 */     addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent param1MouseEvent)
/*     */           {
/*  40 */             if (1 == param1MouseEvent.getButton() && param1MouseEvent.getClickCount() == 2)
/*     */             {
/*  42 */               if ('y' == SG02App.props.getProperty("window.list.print.doubleclick").charAt(0)) {
/*     */                 
/*  44 */                 ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*  45 */                 SG02Frame sG02Frame = (SG02Frame)SwingUtilities.windowForComponent(PrintTable.this.getParent());
/*  46 */                 ActionEvent actionEvent = new ActionEvent(sG02Frame, 0, resourceBundle.getString("Command.Remove"));
/*  47 */                 sG02Frame.actionPerformed(actionEvent);
/*     */               } 
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent param1MouseEvent) {
/*  54 */             evaluatePopup(param1MouseEvent);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent param1MouseEvent) {
/*  59 */             evaluatePopup(param1MouseEvent);
/*     */           }
/*     */ 
/*     */           
/*     */           private void evaluatePopup(MouseEvent param1MouseEvent) {
/*  64 */             if (param1MouseEvent.isPopupTrigger()) {
/*     */               
/*  66 */               int i = PrintTable.this.rowAtPoint(param1MouseEvent.getPoint());
/*  67 */               if (-1 != i) {
/*     */ 
/*     */                 
/*  70 */                 PrintTable.this.setRowSelectionInterval(i, i);
/*     */ 
/*     */                 
/*  73 */                 SG02Frame sG02Frame = (SG02Frame)SwingUtilities.windowForComponent(PrintTable.this.getParent());
/*  74 */                 sG02Frame.createPopup().show(param1MouseEvent.getComponent(), param1MouseEvent.getX(), param1MouseEvent.getY());
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void saveColumns() {
/*  85 */     TableColumn tableColumn = getColumnModel().getColumn(0);
/*  86 */     SG02App.props.setProperty("window.printTable.column.0", String.valueOf(tableColumn.getWidth()));
/*  87 */     tableColumn = getColumnModel().getColumn(1);
/*  88 */     SG02App.props.setProperty("window.printTable.column.1", String.valueOf(tableColumn.getWidth()));

/*  87 */     tableColumn = getColumnModel().getColumn(2);
/*  88 */     SG02App.props.setProperty("window.printTable.column.2", String.valueOf(tableColumn.getWidth()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SongFile getSelectedValue() {
/*  94 */     int i = getSelectedRow();
/*  95 */     if (i < 0)
/*  96 */       return null; 
/*  97 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/*  98 */     return printTableModel.m_modelSongs.getElementAt(i);
/*     */   }
/*     */ 
/*     */   
/*     */   void moveUp(int[] paramArrayOfint) {
/* 103 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 104 */     printTableModel.moveUp(paramArrayOfint);
/* 105 */     if (paramArrayOfint.length > 0 && paramArrayOfint[0] > 0) {
/* 106 */       setRowSelectionInterval(paramArrayOfint[0] - 1, paramArrayOfint[paramArrayOfint.length - 1] - 1);
/*     */     }
/*     */   }
/*     */   
/*     */   void moveDown(int[] paramArrayOfint) {
/* 111 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 112 */     printTableModel.moveDown(paramArrayOfint);
/* 113 */     if (paramArrayOfint.length > 0 && paramArrayOfint[paramArrayOfint.length - 1] < printTableModel.m_modelSongs.size() - 1) {
/* 114 */       setRowSelectionInterval(paramArrayOfint[0] + 1, paramArrayOfint[paramArrayOfint.length - 1] + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   void clearSongs() {
/* 119 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 120 */     printTableModel.m_modelSongs.clear();
/* 121 */     printTableModel.fireTableDataChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   void addSong(SongFile paramSongFile) {
/* 126 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 127 */     printTableModel.m_modelSongs.addElement(paramSongFile);
/* 128 */     printTableModel.fireTableRowsInserted(printTableModel.m_modelSongs.size() - 1, printTableModel.m_modelSongs.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   void removeSongs(int[] paramArrayOfint) {
/* 133 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 134 */     printTableModel.m_modelSongs.removeRange(paramArrayOfint[0], paramArrayOfint[paramArrayOfint.length - 1]);
/* 135 */     printTableModel.fireTableRowsDeleted(paramArrayOfint[0], paramArrayOfint[paramArrayOfint.length - 1]);
/*     */   }
/*     */ 
/*     */   
/*     */   SongFile getSongAt(int paramInt) {
/* 140 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 141 */     return printTableModel.m_modelSongs.getElementAt(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   void fireSongChanged(SongFile paramSongFile) {
/* 146 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 147 */     printTableModel.fireSongChanged(paramSongFile);
/* 148 */     getRootPane().putClientProperty("windowModified", Boolean.TRUE);
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\PrintTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */