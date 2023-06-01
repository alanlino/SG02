/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.util.ArrayList;
/*    */ import java.util.ResourceBundle;
/*    */ import javax.swing.DefaultListModel;
/*    */ import javax.swing.RowSorter;
/*    */ import javax.swing.SortOrder;
/*    */ import javax.swing.SwingUtilities;
/*    */ import javax.swing.table.TableColumn;
/*    */ 
/*    */ 
/*    */ class SongTable
/*    */   extends SG02Table
/*    */ {
/*    */   SongTable(DefaultListModel<SongFile> paramDefaultListModel) {
/* 19 */     super(new SongTableModel(paramDefaultListModel));
/*    */     
/* 21 */     setAutoCreateRowSorter(true);
/*    */     
/* 23 */     TableColumn tableColumn = getColumnModel().getColumn(0);
/* 24 */     String str = SG02App.props.getProperty("window.songsTable.column.0");
/* 25 */     if (str != null) {
/* 26 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*    */     } else {
/* 28 */       tableColumn.setPreferredWidth(200);
/*    */     } 
/* 30 */     tableColumn = getColumnModel().getColumn(1);
/* 31 */     str = SG02App.props.getProperty("window.songsTable.column.1");
/* 32 */     if (str != null) {
/* 33 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*    */     } else {
/* 35 */       tableColumn.setPreferredWidth(5);
/*    */     } 
/* 37 */     tableColumn = getColumnModel().getColumn(2);
/* 38 */     str = SG02App.props.getProperty("window.songsTable.column.2");
/* 39 */     if (str != null) {
/* 40 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*    */     } else {
/* 42 */       tableColumn.setPreferredWidth(40);
/*    */     }
			
			tableColumn = getColumnModel().getColumn(3);
/* 38 */     str = SG02App.props.getProperty("window.songsTable.column.3");
/* 39 */     if (str != null) {
/* 40 */       tableColumn.setPreferredWidth(Integer.valueOf(str).intValue());
/*    */     } else {
/* 42 */       tableColumn.setPreferredWidth(80);
/*    */     } 


/* 44 */     addMouseListener(new MouseAdapter()
/*    */         {
/*    */           public void mouseClicked(MouseEvent param1MouseEvent)
/*    */           {
/* 48 */             if (1 == param1MouseEvent.getButton() && param1MouseEvent.getClickCount() == 2)
/*    */             {
/* 50 */               if ('y' == SG02App.props.getProperty("window.list.songs.doubleclick").charAt(0)) {
/*    */                 
/* 52 */                 ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 53 */                 SG02Frame sG02Frame = (SG02Frame)SwingUtilities.windowForComponent(SongTable.this.getParent());
/* 54 */                 ActionEvent actionEvent = new ActionEvent(sG02Frame, 0, resourceBundle.getString("Command.Add"));
/* 55 */                 sG02Frame.actionPerformed(actionEvent);
/*    */               } 
/*    */             }
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void saveColumns() {
/* 66 */     TableColumn tableColumn = getColumnModel().getColumn(0);
/* 67 */     SG02App.props.setProperty("window.songsTable.column.0", String.valueOf(tableColumn.getWidth()));
/* 68 */     tableColumn = getColumnModel().getColumn(1);
/* 69 */     SG02App.props.setProperty("window.songsTable.column.1", String.valueOf(tableColumn.getWidth()));
/* 70 */     tableColumn = getColumnModel().getColumn(2);
/* 71 */     SG02App.props.setProperty("window.songsTable.column.2", String.valueOf(tableColumn.getWidth()));
			tableColumn = getColumnModel().getColumn(3);
/* 71 */     SG02App.props.setProperty("window.songsTable.column.3", String.valueOf(tableColumn.getWidth()));
/*    */   }
/*    */ 
/*    */   
/*    */   void clearSongs() {
/* 76 */     SongTableModel songTableModel = (SongTableModel)getModel();
/* 77 */     songTableModel.m_modelSongs.clear();
/* 78 */     songTableModel.fireTableDataChanged();
/*    */   }
/*    */ 
/*    */   
/*    */   void addSong(SongFile paramSongFile) {
/* 83 */     SongTableModel songTableModel = (SongTableModel)getModel();
/* 84 */     int i = songTableModel.m_modelSongs.size();
/* 85 */     songTableModel.m_modelSongs.insertElementAt(paramSongFile, i);
/* 86 */     songTableModel.fireTableRowsInserted(i, i);
/*    */     
/* 88 */     ArrayList<RowSorter.SortKey> arrayList = new ArrayList();
/* 89 */     arrayList.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
/* 90 */     arrayList.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
/* 91 */     getRowSorter().setSortKeys(arrayList);
/*    */   }
/*    */ 
/*    */   
/*    */   SongFile getSongAt(int paramInt) {
/* 96 */     PrintTableModel printTableModel = (PrintTableModel)getModel();
/* 97 */     return printTableModel.m_modelSongs.getElementAt(paramInt);
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */