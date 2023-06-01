/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.io.File;
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
/*     */ public class SongRubyHTMLer
/*     */   extends SongHTMLer
/*     */ {
/*     */   String m_strPrevChord;
/*     */   int m_nHyphenBegan;
/*     */   int m_nHyphenEnded;
/*     */   boolean m_bNeedsNewline;
/*     */   
/*     */   public SongRubyHTMLer(File paramFile) {
/*  24 */     super(paramFile);
/*  25 */     this.m_props.setProperty("html.css.link.name", "Ruby.css");
/*  26 */     this.m_props.setProperty("html.css.link", "yes");
/*     */     
/*  28 */     this.m_nHyphenBegan = -1;
/*  29 */     this.m_nHyphenEnded = -1;
/*  30 */     this.m_bNeedsNewline = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChordNewLine() {
/*  36 */     this.m_bNeedsNewline = true;
/*  37 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChord(Chord paramChord, String paramString) {
/*     */     try {
/*  46 */       if (this.m_bNeedsNewline && null != this.m_strLyricLine) {
/*     */         
/*  48 */         this.m_out.write(this.m_strLyricLine.toString());
/*  49 */         this.m_strPrevLyric = null;
/*  50 */         this.m_bNeedsNewline = false;
/*     */       } 
/*     */       
/*  53 */       if (null == this.m_strPrevLyric) {
/*     */         
/*  55 */         this.m_nHyphenBegan = -1;
/*  56 */         this.m_nHyphenEnded = -1;
/*  57 */         this.m_strLyricLine = new StringBuffer("<p class=sg_lyric>");
/*  58 */         this.m_strPrevLyric = "";
/*     */       } 
/*     */       
/*  61 */       this.m_strLyricLine.append("<ruby><rt>");
/*  62 */       if (null != this.m_strPrevChord)
/*  63 */         this.m_strLyricLine.append(this.m_strPrevChord); 
/*  64 */       this.m_strLyricLine.append("&nbsp;</rt><rb>");
/*     */       
/*  66 */       int i = paramString.length();
/*  67 */       int j = this.m_strPrevLyric.length();
/*     */       
/*  69 */       if (0 == i || i == j) {
/*     */         
/*  71 */         this.m_strLyricLine.append("&nbsp;");
/*     */       }
/*     */       else {
/*     */         
/*  75 */         String str = paramString.substring(j);
/*     */         
/*  77 */         if (Character.isWhitespace(str.charAt(0))) {
/*     */           
/*  79 */           if (this.m_nHyphenBegan != -1) {
/*     */             
/*  81 */             this.m_strLyricLine.delete(this.m_nHyphenBegan, this.m_nHyphenEnded);
/*  82 */             this.m_nHyphenBegan = -1;
/*  83 */             this.m_nHyphenEnded = -1;
/*     */           } 
/*     */           
/*  86 */           this.m_strLyricLine.append("&nbsp;");
/*     */         } 
/*     */         
/*  89 */         this.m_strLyricLine.append(str);
/*     */         
/*  91 */         if (Character.isWhitespace(str.charAt(str.length() - 1))) {
/*     */           
/*  93 */           this.m_strLyricLine.append("&nbsp;");
/*     */         
/*     */         }
/*  96 */         else if (j > 0 && i - j < this.m_strPrevChord.length()) {
/*     */ 
/*     */ 
/*     */           
/* 100 */           this.m_nHyphenBegan = this.m_strLyricLine.length();
/* 101 */           this.m_strLyricLine.append("&nbsp;-&nbsp;");
/* 102 */           this.m_nHyphenEnded = this.m_strLyricLine.length();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       this.m_strLyricLine.append("</rb></ruby>");
/*     */       
/* 112 */       this.m_strPrevLyric = paramString;
/* 113 */       this.m_strPrevChord = paramChord.getName();
/*     */     
/*     */     }
/* 116 */     catch (Exception exception) {
/*     */       
/* 118 */       exception.printStackTrace();
/* 119 */       return 0;
/*     */     } 
/*     */     
/* 122 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printLyric(String paramString) {
/*     */     try {
/* 131 */       if (0 != this.m_strLyricLine.length()) {
/*     */         
/* 133 */         boolean bool = false;
/*     */         
/* 135 */         if (paramString.startsWith(this.m_strPrevLyric, 0)) {
/*     */           
/* 137 */           paramString = paramString.substring(this.m_strPrevLyric.length());
/*     */           
/* 139 */           if (this.m_nHyphenBegan != -1)
/*     */           {
/* 141 */             if (paramString.length() == 0 || Character.isWhitespace(paramString.charAt(0)))
/*     */             {
/* 143 */               bool = true;
/*     */             }
/*     */           }
/*     */         } else {
/*     */           
/* 148 */           bool = true;
/*     */         } 
/* 150 */         if (bool) {
/*     */           
/* 152 */           this.m_strLyricLine.delete(this.m_nHyphenBegan, this.m_nHyphenEnded);
/* 153 */           this.m_nHyphenBegan = -1;
/* 154 */           this.m_nHyphenEnded = -1;
/*     */         } 
/*     */         
/* 157 */         this.m_out.write(this.m_strLyricLine.toString());
/*     */       } else {
/*     */         
/* 160 */         this.m_out.write("<p class=sg_lyric>");
/*     */       } 
/* 162 */       this.m_out.write("<ruby><rt>");
/* 163 */       if (null != this.m_strPrevChord) {
/* 164 */         this.m_out.write(this.m_strPrevChord);
/*     */       }
/* 166 */       this.m_out.write("&nbsp;</rt><rb>");
/*     */       
/* 168 */       this.m_out.write(paramString);
/* 169 */       this.m_out.write("&nbsp;</rb></ruby></p>\n");
/*     */       
/* 171 */       this.m_strLyricLine.setLength(0);
/* 172 */       this.m_strPrevLyric = null;
/* 173 */       this.m_strPrevChord = null;
/* 174 */       this.m_bNeedsNewline = false;
/*     */     }
/* 176 */     catch (Exception exception) {
/*     */       
/* 178 */       exception.printStackTrace();
/* 179 */       return 0;
/*     */     } 
/*     */     
/* 182 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongRubyHTMLer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */