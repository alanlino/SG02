/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ class FooterParser {
/*     */   List<Object> m_argsLeft;
/*     */   List<Object> m_argsCenter;
/*     */   List<Object> m_argsRight;
/*     */   int m_nSongbookCurrentPage;
/*     */   int m_nSongbookTotalPages;
/*     */   int m_nSongCurrentPage;
/*     */   int m_nSongTotalPages;
/*     */   SongFile m_Song;
/*     */   String m_SongTitle;
/*     */   
/*     */   void setSongbookCurrentPage(int paramInt) {
/*  22 */     this.m_nSongbookCurrentPage = paramInt;
/*  23 */   } void setSongbookTotalPages(int paramInt) { this.m_nSongbookTotalPages = paramInt; }
/*  24 */   void setSongCurrentPage(int paramInt) { this.m_nSongCurrentPage = paramInt; }
/*  25 */   void setSongTotalPages(int paramInt) { this.m_nSongTotalPages = paramInt; }
/*  26 */   void setSong(SongFile paramSongFile) { this.m_Song = paramSongFile; } void setSongTitle(String paramString) {
/*  27 */     this.m_SongTitle = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public FooterParser(String paramString) {
/*  32 */     this.m_nSongbookCurrentPage = 0;
/*  33 */     this.m_nSongbookTotalPages = 0;
/*  34 */     this.m_nSongCurrentPage = 0;
/*  35 */     this.m_nSongTotalPages = 0;
/*     */     
/*  37 */     this.m_argsLeft = new ArrayList();
/*  38 */     this.m_argsCenter = new ArrayList();
/*  39 */     this.m_argsRight = new ArrayList();
/*     */     
/*  41 */     if (0 != paramString.length()) {
/*  42 */       parse(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLeftString() {
/*  48 */     StringBuffer stringBuffer = new StringBuffer();
/*  49 */     ListIterator<Object> listIterator = this.m_argsLeft.listIterator();
/*  50 */     while (listIterator.hasNext())
/*  51 */       stringBuffer.append(listIterator.next().toString()); 
/*  52 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCenterString() {
/*  58 */     StringBuffer stringBuffer = new StringBuffer();
/*  59 */     ListIterator<Object> listIterator = this.m_argsCenter.listIterator();
/*  60 */     while (listIterator.hasNext())
/*  61 */       stringBuffer.append(listIterator.next().toString()); 
/*  62 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRightString() {
/*  68 */     StringBuffer stringBuffer = new StringBuffer();
/*  69 */     ListIterator<Object> listIterator = this.m_argsRight.listIterator();
/*  70 */     while (listIterator.hasNext())
/*  71 */       stringBuffer.append(listIterator.next().toString()); 
/*  72 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parse(String paramString) {
/*  78 */     boolean bool = true;
/*     */     
/*  80 */     this.m_argsLeft.clear();
/*  81 */     this.m_argsCenter.clear();
/*  82 */     this.m_argsRight.clear();
/*     */     
/*  84 */     List<Object> list = this.m_argsLeft;
/*     */     
/*  86 */     Date date = new Date();
/*  87 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
/*     */ 
/*     */     
/*     */     try {
/*  91 */       String str = "";
/*     */       
/*  93 */       StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
/*  94 */       char c = stringCharacterIterator.first();
/*  95 */       while (Character.MAX_VALUE != c) {
/*     */         
/*  97 */         if ('%' == c) {
/*     */           int i; String str1; int j, k; ArgDataValue argDataValue; ArgArtist argArtist; ArgCapo argCapo; ArgCopyright argCopyright; ArgKey argKey; ArgSubtitle argSubtitle; ArgTranspose argTranspose=null; int m, n; String str2;
/*  99 */           if (0 != str.length()) {
/*     */             
/* 101 */             list.add(str);
/* 102 */             str = "";
/*     */           } 
/*     */           
/* 105 */           c = stringCharacterIterator.next();
/*     */           
/* 107 */           switch (c) {
/*     */             
/*     */             case 'l':
/* 110 */               list = this.m_argsLeft;
/*     */               break;
/*     */             case 'c':
/* 113 */               list = this.m_argsCenter;
/*     */               break;
/*     */             case 'r':
/* 116 */               list = this.m_argsRight;
/*     */               break;
/*     */             
/*     */             case 'A':
/* 120 */               list.add(new ArgArtist());
/*     */               break;
/*     */             case 'C':
/* 123 */               list.add(new ArgCapo());
/*     */               break;
/*     */             case 'f':
/* 126 */               list.add(new ArgFilename());
/*     */               break;
/*     */             case 'F':
/* 129 */               list.add(new ArgPath());
/*     */               break;
/*     */             case 'G':
/* 132 */               list.add(new ArgCopyright());
/*     */               break;
/*     */             
/*     */             case 'k':
/* 136 */               i = 0;
/*     */               
/* 138 */               c = stringCharacterIterator.next();
/* 139 */               if (Character.MAX_VALUE != c && ('-' == c || '+' == c)) {
/*     */                 
/* 141 */                 c = stringCharacterIterator.next();
/* 142 */                 while (Character.MAX_VALUE != c && Character.isDigit(c)) {
/*     */                   
/* 144 */                   i *= 10;
/* 145 */                   i += Character.digit(c, 10) - 1;
/* 146 */                   c = stringCharacterIterator.next();
/*     */                 } 
/*     */               } 
/* 149 */               c = stringCharacterIterator.previous();
/*     */               
/* 151 */               list.add(new ArgKey(i));
/*     */               break;
/*     */             
/*     */             case 'p':
/* 155 */               list.add(new ArgSongbookCurrentPage());
/*     */               break;
/*     */             case 'P':
/* 158 */               list.add(new ArgSongCurrentPage());
/*     */               break;
/*     */             case 'n':
/* 161 */               list.add(new ArgSongbookTotalPages());
/*     */               break;
/*     */             case 'N':
/* 164 */               list.add(new ArgSongTotalPages());
/*     */               break;
/*     */             
/*     */             case 's':
/* 168 */               list.add(new ArgSubtitle());
/*     */               break;
/*     */             case 't':
/* 171 */               list.add(new ArgTitle());
/*     */               break;
/*     */             case 'T':
/* 174 */               list.add(new ArgTitleAndSubtitle());
/*     */               break;
/*     */             case 'x':
/* 177 */               list.add(new ArgTranspose());
/*     */               break;
/*     */             
/*     */             case '%':
/* 181 */               str1 = "%";
/* 182 */               list.add(str1);
/*     */               break;
/*     */ 
/*     */             
/*     */             case '\'':
/*     */             case '\u2018':
/*     */             case '\u2019':
/* 189 */               c = stringCharacterIterator.next();
/* 190 */               j = stringCharacterIterator.getIndex();
/* 191 */               c = stringCharacterIterator.next();
/* 192 */               while (c != '\'' && c != '\u2018' && c != '\u2019' && c != Character.MAX_VALUE)
/* 193 */                 c = stringCharacterIterator.next(); 
/* 194 */               k = stringCharacterIterator.getIndex();
/* 195 */               list.add(new ArgDataValue(paramString.substring(j, k)));
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case '?':
/* 201 */               j = 0;
/*     */               
/* 203 */               c = stringCharacterIterator.next();
/*     */               
/* 205 */               if (c == '!') {
/*     */                 
/* 207 */                 j = 1;
/* 208 */                 c = stringCharacterIterator.next();
/*     */               } 
/*     */               
/* 211 */               argDataValue = null;
/*     */               
/* 213 */               switch (c) {
/*     */ 
/*     */                 
/*     */                 case '\'':
/*     */                 case '\u2018':
/*     */                 case '\u2019':
/* 219 */                   c = stringCharacterIterator.next();
/* 220 */                   m = stringCharacterIterator.getIndex();
/* 221 */                   c = stringCharacterIterator.next();
/* 222 */                   while (c != '\'' && c != '\u2018' && c != '\u2019' && c != Character.MAX_VALUE)
/* 223 */                     c = stringCharacterIterator.next(); 
/* 224 */                   n = stringCharacterIterator.getIndex();
/* 225 */                   argDataValue = new ArgDataValue(paramString.substring(m, n));
/*     */                   break;
/*     */                 
/*     */                 case 'A':
/* 229 */                   argArtist = new ArgArtist();
/*     */                   break;
/*     */                 case 'C':
/* 232 */                   argCapo = new ArgCapo();
/*     */                   break;
/*     */                 case 'G':
/* 235 */                   argCopyright = new ArgCopyright();
/*     */                   break;
/*     */                 
/*     */                 case 'k':
/* 239 */                   m = 0;
/*     */                   
/* 241 */                   c = stringCharacterIterator.next();
/* 242 */                   if (Character.MAX_VALUE != c && ('-' == c || '+' == c)) {
/*     */                     
/* 244 */                     c = stringCharacterIterator.next();
/* 245 */                     while (Character.MAX_VALUE != c && Character.isDigit(c)) {
/*     */                       
/* 247 */                       m *= 10;
/* 248 */                       m += Character.digit(c, 10) - 1;
/* 249 */                       c = stringCharacterIterator.next();
/*     */                     } 
/*     */                   } 
/* 252 */                   c = stringCharacterIterator.previous();
/*     */                   
/* 254 */                   argKey = new ArgKey(m);
/*     */                   break;
/*     */                 
/*     */                 case 's':
/* 258 */                   argSubtitle = new ArgSubtitle();
/*     */                   break;
/*     */                 case 'x':
/* 261 */                   argTranspose = new ArgTranspose();
/*     */                   break;
/*     */               } 
/*     */               
/* 265 */               c = stringCharacterIterator.next();
/*     */ 
/*     */               
/* 268 */               while (c != '"' && c != '\u201c'  && c != '\u201d' && c != Character.MAX_VALUE) {
/* 269 */                 c = stringCharacterIterator.next();
/*     */               }
/* 271 */               m = stringCharacterIterator.getIndex() + 1;
/*     */               
/* 273 */               c = stringCharacterIterator.next();
/*     */ 
/*     */               
/* 276 */               while (c != '"' && c != '\u201c' && c != '\u201d' && c != Character.MAX_VALUE) {
/* 277 */                 c = stringCharacterIterator.next();
/*     */               }
/* 279 */               n = stringCharacterIterator.getIndex();
/*     */               
/* 281 */               str2 = paramString.substring(m, n);
/*     */               
/* 283 */               list.add(new ArgDataQuery(argTranspose, j==0?false:true, str2));

/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case 'd':
/* 289 */               c = stringCharacterIterator.next();
/*     */ 
/*     */               
/* 292 */               switch (c) {
/*     */                 
/*     */                 case 'y':
/* 295 */                   simpleDateFormat.applyPattern("yy");
/* 296 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'Y':
/* 299 */                   simpleDateFormat.applyPattern("yyyy");
/* 300 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'm':
/* 303 */                   simpleDateFormat.applyPattern("MM");
/* 304 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'n':
/* 307 */                   simpleDateFormat.applyPattern("M");
/* 308 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'M':
/* 311 */                   simpleDateFormat.applyPattern("MMM");
/* 312 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'F':
/* 315 */                   simpleDateFormat.applyPattern("MMMM");
/* 316 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'd':
/* 319 */                   simpleDateFormat.applyPattern("dd");
/* 320 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'j':
/* 323 */                   simpleDateFormat.applyPattern("d");
/* 324 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'D':
/* 327 */                   simpleDateFormat.applyPattern("EEE");
/* 328 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'l':
/* 331 */                   simpleDateFormat.applyPattern("EEEE");
/* 332 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */                 case 'r':
/* 335 */                   simpleDateFormat.applyPattern("EEE, d MMM yyyy HH:mm:ss Z");
/* 336 */                   list.add(simpleDateFormat.format(date));
/*     */                   break;
/*     */               } 
/* 339 */               c = stringCharacterIterator.previous();
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             default:
/* 345 */               c = stringCharacterIterator.previous();
/*     */               break;
/*     */           } 
/*     */         
/* 349 */         } else if ('\\' == c) {
/*     */           
/* 351 */           c = stringCharacterIterator.next();
/*     */           
/* 353 */           switch (c) {
/*     */             
/*     */             case 'n':
/* 356 */               str = str + "\n";
/*     */               break;
/*     */           } 
/*     */ 
/*     */         
/*     */         } else {
/* 362 */           str = str + c;
/*     */         } 
/*     */         
/* 365 */         c = stringCharacterIterator.next();
/*     */       } 
/*     */       
/* 368 */       list.add(str);
/*     */     }
/* 370 */     catch (Exception exception) {
/*     */       
/* 372 */       bool = false;
/*     */     } 
/*     */     
/* 375 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ArgSongbookTotalPages
/*     */   {
/*     */     private ArgSongbookTotalPages() {}
/*     */ 
/*     */     
/*     */     public String toString() {
/* 386 */       return String.valueOf(FooterParser.this.m_nSongbookTotalPages);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgSongbookCurrentPage
/*     */   {
/*     */     private ArgSongbookCurrentPage() {}
/*     */     
/*     */     public String toString() {
/* 395 */       return String.valueOf(FooterParser.this.m_nSongbookCurrentPage);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgSongTotalPages
/*     */   {
/*     */     private ArgSongTotalPages() {}
/*     */     
/*     */     public String toString() {
/* 404 */       return String.valueOf(FooterParser.this.m_nSongTotalPages);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgSongCurrentPage
/*     */   {
/*     */     private ArgSongCurrentPage() {}
/*     */     
/*     */     public String toString() {
/* 413 */       return String.valueOf(FooterParser.this.m_nSongCurrentPage);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgArtist
/*     */   {
/*     */     private ArgArtist() {}
/*     */     
/*     */     public String toString() {
/* 422 */       if (null == FooterParser.this.m_Song.getArtist()) {
/* 423 */         return "";
/*     */       }
/* 425 */       return String.valueOf(FooterParser.this.m_Song.getArtist());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgCapo
/*     */   {
/*     */     private ArgCapo() {}
/*     */     
/*     */     public String toString() {
/* 434 */       if (0 == FooterParser.this.m_Song.getCapo()) {
/* 435 */         return "";
/*     */       }
/* 437 */       return String.valueOf(FooterParser.this.m_Song.getCapo());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgCopyright
/*     */   {
/*     */     private ArgCopyright() {}
/*     */     
/*     */     public String toString() {
/* 446 */       if (null == FooterParser.this.m_Song.getCopyright()) {
/* 447 */         return "";
/*     */       }
/* 449 */       return String.valueOf(FooterParser.this.m_Song.getCopyright());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ArgKey
/*     */   {
/*     */     int m_keyIdx;
/*     */ 
/*     */     
/*     */     public ArgKey(int param1Int) {
/* 460 */       this.m_keyIdx = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 465 */       if (FooterParser.this.m_Song.getKeySignatureCount() > this.m_keyIdx) {
/* 466 */         return FooterParser.this.m_Song.getKeySignature(this.m_keyIdx).getName();
/*     */       }
/* 468 */       return "";
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgSubtitle
/*     */   {
/*     */     private ArgSubtitle() {}
/*     */     
/*     */     public String toString() {
/* 477 */       if (null != FooterParser.this.m_Song.getSubtitle()) {
/* 478 */         return FooterParser.this.m_Song.getSubtitle();
/*     */       }
/* 480 */       return "";
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgTitle
/*     */   {
/*     */     private ArgTitle() {}
/*     */     
/*     */     public String toString() {
/* 489 */       if (null != FooterParser.this.m_SongTitle && 0 != FooterParser.this.m_SongTitle.length()) {
/* 490 */         return FooterParser.this.m_SongTitle;
/*     */       }
/* 492 */       return FooterParser.this.m_Song.getTitle();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgTitleAndSubtitle
/*     */   {
/*     */     private ArgTitleAndSubtitle() {}
/*     */     
/*     */     public String toString() {
/* 501 */       if (null != FooterParser.this.m_Song.getSubtitle()) {
/* 502 */         return FooterParser.this.m_Song.getTitle() + " (" + FooterParser.this.m_Song.getSubtitle() + ")";
/*     */       }
/* 504 */       return FooterParser.this.m_Song.getTitle();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgFilename
/*     */   {
/*     */     private ArgFilename() {}
/*     */     
/*     */     public String toString() {
/* 513 */       return FooterParser.this.m_Song.getPath().getName();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgPath
/*     */   {
/*     */     private ArgPath() {}
/*     */     
/*     */     public String toString() {
/* 522 */       return FooterParser.this.m_Song.getPath().toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ArgTranspose
/*     */   {
/*     */     private ArgTranspose() {}
/*     */     
/*     */     public String toString() {
/* 531 */       if (0 == FooterParser.this.m_Song.getTranspose()) {
/* 532 */         return "";
/*     */       }
/* 534 */       return String.valueOf(FooterParser.this.m_Song.getTranspose());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ArgDataValue
/*     */   {
/*     */     String m_key;
/*     */ 
/*     */     
/*     */     public ArgDataValue(String param1String) {
/* 545 */       this.m_key = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 550 */       String str = FooterParser.this.m_Song.getValueForKey(this.m_key);
/* 551 */       if (null != str) {
/* 552 */         return str;
/*     */       }
/* 554 */       return "";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ArgDataQuery
/*     */   {
/*     */     Object m_queryArg;
/*     */     
/*     */     boolean m_invertQuery;
/*     */     FooterParser m_parser;
/*     */     
/*     */     public ArgDataQuery(Object param1Object, boolean param1Boolean, String param1String) {
/* 567 */       this.m_queryArg = param1Object;
/* 568 */       this.m_invertQuery = param1Boolean;
/*     */       
/* 570 */       this.m_parser = new FooterParser(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 575 */       boolean bool = (this.m_queryArg.toString().length() != 0) ? true : false;
/*     */       
/* 577 */       if (this.m_invertQuery) {
/* 578 */         bool = !bool ? true : false;
/*     */       }
/* 580 */       if (bool) {
/*     */         
/* 582 */         this.m_parser.setSongbookCurrentPage(FooterParser.this.m_nSongbookCurrentPage);
/* 583 */         this.m_parser.setSongbookTotalPages(FooterParser.this.m_nSongbookTotalPages);
/* 584 */         this.m_parser.setSongCurrentPage(FooterParser.this.m_nSongCurrentPage);
/* 585 */         this.m_parser.setSongTotalPages(FooterParser.this.m_nSongTotalPages);
/* 586 */         this.m_parser.setSong(FooterParser.this.m_Song);
/*     */         
/* 588 */         String str = this.m_parser.getLeftString().toString();
/*     */ 
/*     */         
/* 591 */         FooterParser footerParser = new FooterParser(str);
/* 592 */         footerParser.setSongbookCurrentPage(FooterParser.this.m_nSongbookCurrentPage);
/* 593 */         footerParser.setSongbookTotalPages(FooterParser.this.m_nSongbookTotalPages);
/* 594 */         footerParser.setSongCurrentPage(FooterParser.this.m_nSongCurrentPage);
/* 595 */         footerParser.setSongTotalPages(FooterParser.this.m_nSongTotalPages);
/* 596 */         footerParser.setSong(FooterParser.this.m_Song);
/*     */         
/* 598 */         return footerParser.getLeftString().toString();
/*     */       } 
/*     */       
/* 601 */       return "";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\FooterParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */