/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.util.ListIterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SongTOC
/*     */ {
/*  13 */   protected Vector<SongFileTOC> m_qSongFiles = new Vector<SongFileTOC>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int print(SongOutput paramSongOutput) {
/*  19 */     Properties properties = paramSongOutput.getProps();
/*     */     
/*  21 */     paramSongOutput.markNewSong();
/*  22 */     paramSongOutput.printTitle(properties.getProperty("toc.text"));
/*     */     
/*  24 */     String str = properties.getProperty("toc.text.separator");
/*  25 */     boolean bool1 = ('y' == properties.getProperty("toc.page.numbers").charAt(0)) ? true : false;
/*  26 */     boolean bool2 = ('y' != properties.getProperty("print.footer.physical.only").charAt(0)) ? true : false;
/*     */     
/*  28 */     ListIterator<SongFileTOC> listIterator = this.m_qSongFiles.listIterator();
/*  29 */     while (listIterator.hasNext()) {
/*     */       
/*  31 */       SongFileTOC songFileTOC = listIterator.next();
/*  32 */       String str1 = songFileTOC.m_SongFile.toString() +" - "+ (songFileTOC.m_SongFile.getTag()!=null?songFileTOC.m_SongFile.getTag():" ");
/*     */       
/*  34 */       if (bool1) {
/*     */         
/*     */         try {
/*     */           
/*  38 */           int i = songFileTOC.m_nPageNumber;
/*     */ 
/*     */           
/*  41 */           if (bool2)
/*     */           {
/*  43 */             if (2 == paramSongOutput.getSongsPerPage()) {
/*  44 */               i = (songFileTOC.m_nPageNumber - 1) * 2 + songFileTOC.m_nSongOfPage + 1;
/*  45 */             } else if (4 == paramSongOutput.getSongsPerPage()) {
/*  46 */               i = (songFileTOC.m_nPageNumber - 1) * 4 + songFileTOC.m_nSongOfPage + 1;
/*     */             } 
/*     */           }
/*  49 */           if (0 != i) {
/*  50 */             str1 = str1 + str + String.valueOf(i);
/*     */           
/*     */           }
/*     */         }
/*  54 */         catch (Exception exception) {}
/*     */       }
/*     */       
/*  57 */       paramSongOutput.printLyric(str1);
/*  58 */       paramSongOutput.printNormalSpace();
/*     */     } 
/*     */     
/*  61 */     paramSongOutput.markNewSong();
/*     */     
/*  63 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSongFile(SongFileTOC paramSongFileTOC) {
/*     */     try {
/*  71 */       this.m_qSongFiles.add(paramSongFileTOC);
/*     */     }
/*  73 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String makeTitleString(SongFile paramSongFile) {
/*  82 */     StringBuffer stringBuffer = new StringBuffer(paramSongFile.getTitle());
/*     */     
/*  84 */     byte b = 0;
/*  85 */     int i = stringBuffer.length();
/*     */     
/*  87 */     if (0 == i) {
/*  88 */       return "";
/*     */     }
/*  90 */     char c = stringBuffer.charAt(0);
/*     */     
/*  92 */     if ('y' == SG02App.props.getProperty("songs.titles.reorder.aanthe").charAt(0))
/*     */     {
/*  94 */       if (c == 'T' || c == 't' || c == 'A' || c == 'a')
/*     */       {
/*  96 */         if (i > 4 && stringBuffer.substring(0, 4).toLowerCase().equals("the ")) {
/*     */           
/*  98 */           b = 4;
/*  99 */           stringBuffer.append(", The");
/*     */         }
/* 101 */         else if (i > 3 && stringBuffer.substring(0, 3).toLowerCase().equals("an ")) {
/*     */           
/* 103 */           b = 3;
/* 104 */           stringBuffer.append(", An");
/*     */         }
/* 106 */         else if (i > 2 && stringBuffer.substring(0, 2).toLowerCase().equals("a ")) {
/*     */           
/* 108 */           b = 2;
/* 109 */           stringBuffer.append(", A");
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 114 */     if (null != paramSongFile.getSubtitle()) {
/*     */       
/* 116 */       stringBuffer.append(" (");
/* 117 */       stringBuffer.append(paramSongFile.getSubtitle());
/* 118 */       stringBuffer.append(")");
/*     */     } 
/*     */ 
/*     */     
/* 122 */     int j = b;
/* 123 */     while (-1 != (j = stringBuffer.indexOf("\t", j)))
/*     */     {
/* 125 */       stringBuffer.setCharAt(j, ' ');
/*     */     }
/*     */     
/* 128 */     return stringBuffer.substring(b);
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongTOC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */