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
/*     */ class SongTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private ResourceBundle m_resources;
/*     */   protected DefaultListModel<SongFile> m_modelSongs;
/*     */   
/*     */   SongTableModel(DefaultListModel<SongFile> paramDefaultListModel) {
/* 111 */     this.m_modelSongs = paramDefaultListModel;
/* 112 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int paramInt) {
/* 117 */     switch (paramInt) {
/*     */       case 0:
/* 119 */         return this.m_resources.getString("Title.Column.Title");
/* 120 */       case 1: return this.m_resources.getString("Title.Column.Key");
/* 121 */       case 2: return this.m_resources.getString("Title.Column.Artist");
				case 3: return this.m_resources.getString("Title.Column.Tag");
/*     */     } 
/* 123 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> getColumnClass(int paramInt) {
/* 128 */     return String.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 133 */     return this.m_modelSongs.getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 138 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int paramInt1, int paramInt2) {
/* 143 */     SongFile songFile = getSongFileAt(paramInt1);
/* 144 */     switch (paramInt2) {
/*     */       
/*     */       case 0:
/* 147 */         return songFile.toString();
/*     */       case 1:
/* 149 */         return songFile.getKeySignaturesString();
/*     */       case 2:
/* 151 */         return songFile.getArtist();
/*     */       case 3:
/* 151 */         return songFile.getTag();
/*     */     } 
/* 153 */     return "";
/*     */   }
/*     */   public boolean isCellEditable(int paramInt1, int paramInt2) {
/* 156 */     return false;
/*     */   }
/*     */   
/*     */   public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
				SongFile songFile = getSongFileAt(paramInt1);
	 			switch (paramInt2) {
		 /*     */       
		 /*     */       
		 /*     */    
		 /*     */       case 3:
		 /* 151 */         songFile.setTag((String)paramObject);
		 /*     */     } 
	
	
/* 160 */     fireTableCellUpdated(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public SongFile getSongFileAt(int paramInt) {
/* 165 */     return this.m_modelSongs.getElementAt(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */