/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.event.KeyEvent;
/*    */ import java.awt.event.KeyListener;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.AbstractTableModel;
/*    */ import javax.swing.table.TableModel;
/*    */ import javax.swing.table.TableRowSorter;
/*    */ 
/*    */ abstract class SG02Table
/*    */   extends JTable
/*    */ {
/*    */   SG02Table(TableModel paramTableModel) {
/* 15 */     super(paramTableModel);
/*    */     
/* 17 */     setFillsViewportHeight(true);
/*    */ 
/*    */     
/* 20 */     setRowHeight(getRowHeight() + 2);
/* 21 */     Dimension dimension = getIntercellSpacing();
/* 22 */     dimension.width += 2;
/* 23 */     setIntercellSpacing(dimension);
/*    */ 
/*    */     
/* 26 */     addKeyListener(new KeyListener()
/*    */         {
/*    */           private String m_key;
/*    */           private long m_time;
/*    */           
/*    */           public void keyTyped(KeyEvent param1KeyEvent) {
/* 32 */             char c = param1KeyEvent.getKeyChar();
/*    */ 
/*    */             
/* 35 */             if (!Character.isLetterOrDigit(c)) {
/*    */               return;
/*    */             }
/*    */ 
/*    */ 
/*    */             
/* 41 */             if (this.m_time + 1000L < System.currentTimeMillis()) {
/* 42 */               this.m_key = "";
/*    */             }
/*    */             
/* 45 */             this.m_time = System.currentTimeMillis();
/* 46 */             this.m_key += Character.toLowerCase(c);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 55 */             AbstractTableModel abstractTableModel = (AbstractTableModel)SG02Table.this.getModel();
/* 56 */             int i = abstractTableModel.getRowCount();
/* 57 */             TableRowSorter tableRowSorter = (TableRowSorter)SG02Table.this.getRowSorter();
/* 58 */             for (byte b = 0; b < i; b++) {
/*    */               
/* 60 */               int j = tableRowSorter.convertRowIndexToModel(b);
/* 61 */               String str = ((String)abstractTableModel.getValueAt(j, 0)).toLowerCase();
/* 62 */               if (str.startsWith(this.m_key)) {
/* 63 */                 SG02Table.this.getSelectionModel().setSelectionInterval(b, b);
/* 64 */                 SG02Table.this.scrollRectToVisible(SG02Table.this.getCellRect(b, 0, true));
/*    */                 break;
/*    */               } 
/*    */             } 
/*    */           }
/*    */           
/*    */           public void keyPressed(KeyEvent param1KeyEvent) {}
/*    */           
/*    */           public void keyReleased(KeyEvent param1KeyEvent) {}
/*    */         });
/*    */   }
/*    */   
/*    */   abstract void clearSongs();
/*    */   
/*    */   abstract void addSong(SongFile paramSongFile);
/*    */   
/*    */   abstract SongFile getSongAt(int paramInt);
/*    */   
/*    */   abstract void saveColumns();
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SG02Table.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */