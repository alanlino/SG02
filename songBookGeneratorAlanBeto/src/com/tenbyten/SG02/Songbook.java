/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ class Songbook
/*     */ {
/*     */   private DefaultListModel<SongFile> m_modelSongs;
/*     */   private PrintTable m_printTable;
/*     */   
/*     */   Songbook(DefaultListModel<SongFile> paramDefaultListModel, PrintTable paramPrintTable, File paramFile) {
/*  22 */     this.m_modelSongs = paramDefaultListModel;
/*  23 */     this.m_printTable = paramPrintTable;
/*  24 */     this.m_songCount = 0;
/*  25 */     this.m_missingSongCount = 0;
/*  26 */     load(paramFile);
/*     */   }
/*     */   private File m_file; private int m_songCount;
/*     */   private int m_missingSongCount;
/*     */   
/*     */   Songbook(DefaultListModel<SongFile> paramDefaultListModel, PrintTable paramPrintTable) {
/*  32 */     this.m_modelSongs = paramDefaultListModel;
/*  33 */     this.m_printTable = paramPrintTable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  39 */     if (null != this.m_file) {
/*  40 */       return this.m_file.getName();
/*     */     }
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSongCount() {
/*  48 */     return this.m_songCount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMissingSongCount() {
/*  54 */     return this.m_missingSongCount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   File getFile() {
/*  60 */     return this.m_file;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean save(File paramFile) {
/*  66 */     boolean bool = true;
/*  67 */     this.m_file = paramFile;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  73 */       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(paramFile), "UTF-16LE");
/*  74 */       outputStreamWriter.write("﻿");
/*     */       
/*  76 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*  77 */       outputStreamWriter.write("# " + resourceBundle.getString("Songsheet Generator") + " " + resourceBundle.getString("Version"));
/*  78 */       outputStreamWriter.write(SongOutput.m_strNewline);
/*     */       
/*  80 */       this.m_songCount = this.m_printTable.getModel().getRowCount();
/*  81 */       this.m_missingSongCount = 0;
/*     */       
/*  83 */       for (byte b = 0; b < this.m_songCount; b++) {
/*     */         
/*  85 */         SongFile songFile = this.m_printTable.getSongAt(b);
/*     */         
/*  87 */         outputStreamWriter.write("\"");
/*  88 */         outputStreamWriter.write(songFile.getPath().getName());
/*  89 */         outputStreamWriter.write("\"");
/*     */         
/*  91 */         outputStreamWriter.write(" Transpose=");
/*  92 */         outputStreamWriter.write(String.valueOf(songFile.getRawTranspose()));
/*     */         
/*  94 */         outputStreamWriter.write(" PrintChords=");
/*  95 */         if (songFile.getPrintChords()) {
/*  96 */           outputStreamWriter.write("yes");
/*     */         } else {
/*  98 */           outputStreamWriter.write("no");
/*     */         } 
/* 100 */         outputStreamWriter.write(SongOutput.m_strNewline);
/*     */       } 
/*     */       
/* 103 */       outputStreamWriter.close();
/*     */     
/*     */     }
/* 106 */     catch (IOException iOException) {
/*     */       
/* 108 */       System.err.println("Songbook.save(): caught exception.");
/* 109 */       bool = false;
/*     */     } 
/*     */     
/* 112 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean load(File paramFile) {
/* 118 */     boolean bool = true;
/* 119 */     this.m_file = paramFile;
/*     */     
/*     */     try {
/*     */       InputStreamReader inputStreamReader;
/* 123 */       boolean bool1 = false;
/*     */ 
/*     */       
/* 126 */       FileInputStream fileInputStream = new FileInputStream(paramFile);
/* 127 */       int i = fileInputStream.read();
/* 128 */       int j = fileInputStream.read();
/* 129 */       int k = fileInputStream.read();
/* 130 */       fileInputStream.close();
/*     */       
/* 132 */       boolean bool2 = (i == 239 && j == 187 && k == 191) ? true : false;
/* 133 */       boolean bool3 = ((i == 254 && j == 255) || (i == 255 && j == 254)) ? true : false;
/*     */ 
/*     */       
/* 136 */       if (bool3) {
/* 137 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile), "UTF-16");
/* 138 */       } else if (bool2) {
/* 139 */         inputStreamReader = new InputStreamReader(new FileInputStream(paramFile), "UTF-8");
/*     */       } else {
/* 141 */         inputStreamReader = new FileReader(paramFile);
/*     */       } 
/* 143 */       LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
/*     */ 
/*     */ 
/*     */       
/* 147 */       this.m_printTable.clearSongs();
/* 148 */       this.m_songCount = 0;
/*     */       String str;
/* 150 */       while ((str = lineNumberReader.readLine()) != null) {
/*     */         
/* 152 */         if ('#' == str.charAt(0)) {
/*     */           continue;
/*     */         }
/* 155 */         int m = str.length();
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 160 */           byte b1 = 1;
/* 161 */           while (b1 < m && str.charAt(b1) != '"')
/* 162 */             b1++; 
/* 163 */           String str1 = str.substring(1, b1);
/*     */           
/* 165 */           this.m_songCount++;
/* 166 */           boolean bool4 = true;
/* 167 */           for (byte b2 = 0; b2 < this.m_modelSongs.getSize(); b2++) {
/*     */             
/* 169 */             SongFile songFile = this.m_modelSongs.getElementAt(b2);
/* 170 */             if (songFile.getPath().getName().equalsIgnoreCase(str1)) {
/*     */               
/* 172 */               bool4 = false;
/* 173 */               songFile = (SongFile)songFile.clone();
/*     */               
/* 175 */               this.m_printTable.addSong(songFile);
/* 176 */               songFile.setTranspose(0);
/* 177 */               songFile.setPrintChords(true);
/*     */ 
/*     */               
/*     */               try {
/* 181 */                 b1++;
/* 182 */                 while (b1 < m) {
/*     */                   
/* 184 */                   while (b1 < m && !Character.isWhitespace(str.charAt(b1)))
/* 185 */                     b1++; 
/* 186 */                   while (b1 < m && Character.isWhitespace(str.charAt(b1))) {
/* 187 */                     b1++;
/*     */                   }
/* 189 */                   if (b1 >= m) {
/*     */                     break;
/*     */                   }
/* 192 */                   if (str.charAt(b1) == 't' || str.charAt(b1) == 'T') {
/*     */                     
/* 194 */                     while (b1 < m && '=' != str.charAt(b1)) {
/* 195 */                       b1++;
/*     */                     }
/* 197 */                     byte b = ++b1;
/* 198 */                     while (b1 < m && !Character.isWhitespace(str.charAt(b1)))
/* 199 */                       b1++; 
/* 200 */                     songFile.setTranspose(Integer.parseInt(str.substring(b, b1))); continue;
/*     */                   } 
/* 202 */                   if (str.charAt(b1) == 'p' || str.charAt(b1) == 'P')
/*     */                   {
/* 204 */                     while (b1 < m && '=' != str.charAt(b1))
/* 205 */                       b1++; 
/* 206 */                     b1++;
/* 207 */                     songFile.setPrintChords((str.charAt(b1) == 'y' || str.charAt(b1) == 'Y'));
/*     */                   }
/*     */                 
/*     */                 } 
/* 211 */               } catch (Exception exception) {
/*     */                 
/* 213 */                 bool1 = true;
/*     */               } 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 219 */           if (bool4) {
/* 220 */             this.m_missingSongCount++;
/*     */           }
/* 222 */         } catch (Exception exception) {
/*     */           
/* 224 */           bool1 = true;
/*     */         } 
/*     */       } 
/*     */       
/* 228 */       lineNumberReader.close();
/* 229 */       inputStreamReader.close();
/*     */       
/* 231 */       if (bool1) {
/* 232 */         throw new IOException();
/*     */       }
/* 234 */     } catch (IOException iOException) {
/*     */       
/* 236 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 237 */       JOptionPane.showMessageDialog(null, resourceBundle
/* 238 */           .getString("Text.Error.SGBOpen"), resourceBundle
/* 239 */           .getString("Title.Dialog.Error"), 0);
/*     */       
/* 241 */       System.err.println("Songbook.load(): caught exception.");
/* 242 */       bool = false;
/*     */     } 
/*     */     
/* 245 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\Songbook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */