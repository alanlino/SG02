/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.table.AbstractTableModel;
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
/*     */ class PrintTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private ResourceBundle m_resources;
/*     */   protected DefaultListModel<SongFile> m_modelSongs;
/*     */   
/*     */   PrintTableModel(DefaultListModel<SongFile> paramDefaultListModel) {
/* 161 */     this.m_modelSongs = paramDefaultListModel;
/* 162 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int paramInt) {
/* 167 */     switch (paramInt) {
/*     */       case 0:
/* 169 */         return this.m_resources.getString("Title.Column.Title");
/* 170 */       case 1: return this.m_resources.getString("Title.Column.Key");
				case 2: return this.m_resources.getString("Title.Column.Tag");
/*     */     } 
/* 172 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 177 */     return this.m_modelSongs.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 182 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int paramInt1, int paramInt2) {
/* 187 */     SongFile songFile = this.m_modelSongs.getElementAt(paramInt1);
/* 188 */     switch (paramInt2) {
/*     */       
/*     */       case 0:
/* 191 */         return songFile.toString();
/*     */       case 1:
/* 193 */         return songFile.getKeySignaturesString();

				case 2:
/* 193 */         return songFile.getTag();
/*     */     } 
/* 195 */     return "";
/*     */   }
/*     */   public boolean isCellEditable(int paramInt1, int paramInt2) {
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   void moveUp(int[] paramArrayOfint) {
/* 202 */     if (paramArrayOfint[0] > 0) {
/*     */       
/* 204 */       for (byte b = 0; b < paramArrayOfint.length; b++) {
/*     */         
/* 206 */         int i = paramArrayOfint[b];
/* 207 */         int j = i - 1;
/* 208 */         SongFile songFile1 = this.m_modelSongs.getElementAt(i);
/* 209 */         SongFile songFile2 = this.m_modelSongs.getElementAt(j);
/* 210 */         this.m_modelSongs.set(i, songFile2);
/* 211 */         this.m_modelSongs.set(j, songFile1);
/*     */       } 
/* 213 */       fireTableRowsUpdated(paramArrayOfint[0] - 1, paramArrayOfint[paramArrayOfint.length - 1]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void moveDown(int[] paramArrayOfint) {
/* 219 */     if (paramArrayOfint[paramArrayOfint.length - 1] < this.m_modelSongs.size() - 1) {
/*     */       
/* 221 */       for (int i = paramArrayOfint.length; i > 0; i--) {
/*     */         
/* 223 */         int j = paramArrayOfint[i - 1];
/* 224 */         int k = j + 1;
/* 225 */         SongFile songFile1 = this.m_modelSongs.getElementAt(j);
/* 226 */         SongFile songFile2 = this.m_modelSongs.getElementAt(k);
/* 227 */         this.m_modelSongs.set(j, songFile2);
/* 228 */         this.m_modelSongs.set(k, songFile1);
/*     */       } 
/* 230 */       fireTableRowsUpdated(paramArrayOfint[0], paramArrayOfint[paramArrayOfint.length - 1] + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
/* 236 */     fireTableCellUpdated(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   void fireSongChanged(SongFile paramSongFile) {
/* 241 */     int i = this.m_modelSongs.indexOf(paramSongFile);
/* 242 */     fireTableRowsUpdated(i, i);
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\PrintTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */