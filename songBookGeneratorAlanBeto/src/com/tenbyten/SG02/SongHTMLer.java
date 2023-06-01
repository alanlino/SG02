/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class SongHTMLer
/*     */   extends SongPlaintexter {
/*     */   protected boolean m_bTableOpen;
/*     */   protected boolean m_bInChorus;
/*     */   protected boolean m_b1stLine;
/*     */   protected int m_nTOCNumber;
/*     */   protected StringBuffer m_strChordLine;
/*     */   protected StringBuffer m_strLyricLine;
/*     */   
/*     */   public SongHTMLer(File paramFile) {
/*  19 */     super(paramFile);
/*  20 */     this.m_strChordLine = new StringBuffer(200);
/*  21 */     this.m_strLyricLine = new StringBuffer(200);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean printSongs() throws IOException {
/*  27 */     boolean bool = false;
/*     */     
/*  29 */     this.m_bTableOpen = false;
/*  30 */     this.m_bInChorus = false;
/*  31 */     this.m_bInTab = false;
/*  32 */     this.m_b1stLine = true;
/*     */     
/*  34 */     this.m_out = new OutputStreamWriter(new FileOutputStream(this.m_outputPath), "UTF-8");
/*     */     
/*  36 */     this.m_nTOCNumber = 0;
/*     */ 
/*     */     
/*     */     try {
/*  40 */       this.m_out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n    \"http://www.w3.org/TR/html4/strict.dtd\">" + m_strNewline);
/*     */       
/*  42 */       this.m_out.write("<html>" + m_strNewline);
/*  43 */       this.m_out.write("<head>" + m_strNewline);
/*  44 */       this.m_out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + m_strNewline);
/*     */       
/*  46 */       this.m_out.write("<meta name=\"Generator\" content=\"Songsheet Generator Java 0.1\">" + m_strNewline);
/*     */       
/*  48 */       this.m_out.write("<title>Songsheet Generator Output</title>" + m_strNewline);
/*     */       
/*  50 */       if ('y' == this.m_props.getProperty("html.css.link").charAt(0)) {
/*     */         
/*  52 */         this.m_out.write("<link href=\"" + this.m_props.getProperty("html.css.link.name"));
/*  53 */         this.m_out.write("\" rel=\"stylesheet\" type=\"text/css\">" + m_strNewline);
/*     */       }
/*     */       else {
/*     */         
/*  57 */         this.m_out.write("<style type=\"text/css\">" + m_strNewline);
/*  58 */         this.m_out.write(".sg_song {" + this.m_props.getProperty("html.style.song") + "}" + m_strNewline);
/*  59 */         this.m_out.write(".sg_title {" + this.m_props.getProperty("html.style.title") + "}" + m_strNewline);
/*  60 */         this.m_out.write(".sg_subtitle {" + this.m_props.getProperty("html.style.subtitle") + "}" + m_strNewline);
/*  61 */         this.m_out.write(".sg_lyric {" + this.m_props.getProperty("html.style.lyric") + "}" + m_strNewline);
/*  62 */         this.m_out.write(".sg_chord {" + this.m_props.getProperty("html.style.chord") + "}" + m_strNewline);
/*  63 */         this.m_out.write(".sg_chorus {" + this.m_props.getProperty("html.style.chorus") + "}" + m_strNewline);
/*  64 */         this.m_out.write(".sg_chorus_all {" + this.m_props.getProperty("html.style.chorus.overall") + "}" + m_strNewline);
/*  65 */         this.m_out.write(".sg_comment {" + this.m_props.getProperty("html.style.comment") + "}" + m_strNewline);
/*  66 */         this.m_out.write(".sg_tab {" + this.m_props.getProperty("html.style.tab") + "}" + m_strNewline);
/*  67 */         this.m_out.write(".sg_newsong {" + this.m_props.getProperty("html.style.newsong") + "}" + m_strNewline);
/*  68 */         this.m_out.write(".sg_toc {" + this.m_props.getProperty("html.style.toc") + "}" + m_strNewline);
/*  69 */         this.m_out.write(".sg_toc_header {" + this.m_props.getProperty("html.style.toc.header") + "}" + m_strNewline);
/*  70 */         this.m_out.write(".sg_toc_contents {" + this.m_props.getProperty("html.style.toc.contents") + "}" + m_strNewline);
/*  71 */         this.m_out.write("</style>" + m_strNewline);
/*     */       } 
/*     */       
/*  74 */       this.m_out.write("</head>" + m_strNewline);
/*  75 */       this.m_out.write("<body>" + m_strNewline);
/*     */       
/*  77 */       if (this.m_bPrintTOC || this.m_bOnlyTOC) {
/*     */         
/*  79 */         this.m_bInTOC = true;
/*  80 */         this.m_nTOCNumber = 0;
/*  81 */         this.m_out.write("<div class=sg_toc>" + m_strNewline);
/*     */         
/*  83 */         SongTOC songTOC = new SongTOC();
/*  84 */         ListIterator<SongFile> listIterator = this.m_qSongFiles.listIterator();
/*  85 */         while (listIterator.hasNext()) {
/*     */           
/*  87 */           SongFile songFile = listIterator.next();
/*  88 */           songTOC.addSongFile(new SongFileTOC(songFile));
/*     */         } 
/*     */         
/*  91 */         songTOC.print(this);
/*     */         
/*  93 */         this.m_out.write("</div>" + m_strNewline);
/*  94 */         this.m_out.write("<p class=sg_comment> </p>" + m_strNewline);
/*     */       } 
/*  96 */       this.m_bInTOC = false;
/*     */       
/*  98 */       this.m_nTOCNumber = 1;
/*     */       
/* 100 */       boolean bool1 = this.m_bCloseOutput;
/* 101 */       this.m_bCloseOutput = false;
/*     */       
/* 103 */       if (!this.m_bOnlyTOC) {
/*     */         
/* 105 */         bool = super.printSongs();
/* 106 */         if (this.m_bInChorus)
/* 107 */           markEndOfChorus(); 
/* 108 */         if (this.m_bInTab)
/* 109 */           markEndOfTab(); 
/* 110 */         this.m_out.write("</div>" + m_strNewline);
/*     */       } 
/*     */       
/* 113 */       this.m_bCloseOutput = bool1;
/*     */       
/* 115 */       this.m_out.write("</body>" + m_strNewline);
/* 116 */       this.m_out.write("</html>" + m_strNewline);
/*     */       
/* 118 */       this.m_out.flush();
/*     */       
/* 120 */       if (this.m_bCloseOutput) {
/* 121 */         this.m_out.close();
/*     */       }
/* 123 */     } catch (Exception exception) {
/*     */       
/* 125 */       System.out.println("SongHTMLer.printSongs() caught exception " + exception.toString());
/* 126 */       bool = false;
/*     */     } 
/*     */     
/* 129 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printTitle(String paramString) {
/*     */     try {
/* 137 */       this.m_bInChorus = false;
/*     */       
/* 139 */       if (this.m_bInTOC) {
/* 140 */         this.m_out.write("<div class=sg_toc_header");
/*     */       } else {
/* 142 */         this.m_out.write("<div class=sg_song");
/*     */       } 
/* 144 */       this.m_out.write(" id=\"s" + String.valueOf(this.m_nTOCNumber++) + "\"");
/*     */       
/* 146 */       if (this.m_bInTOC) {
/* 147 */         this.m_out.write("><h1 class=sg_toc_header>");
/*     */       } else {
/*     */         
/* 150 */         this.m_out.write("><h1 class=sg_title>");
/*     */         
/* 152 */         if ('y' == this.m_props.getProperty("songs.number").charAt(0)) {
/* 153 */           this.m_out.write(String.valueOf(this.m_nTOCNumber - 1) + ". ");
/*     */         }
/*     */       } 
/* 156 */       this.m_out.write(paramString + "</h1>" + m_strNewline);
/*     */       
/* 158 */       if (this.m_bInTOC) {
/* 159 */         this.m_out.write("</div><div class=sg_toc_contents>");
/*     */       }
/* 161 */       this.m_b1stLine = true;
/*     */       
/* 163 */       return 2;
/*     */     }
/* 165 */     catch (IOException iOException) {
/*     */       
/* 167 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printSubtitle(String paramString) {
/*     */     try {
/* 176 */       this.m_out.write("<h2 class=sg_subtitle>" + paramString + "</h2>" + m_strNewline);
/* 177 */       return 2;
/*     */     }
/* 179 */     catch (IOException iOException) {
/*     */       
/* 181 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChord(Chord paramChord, String paramString) {
/*     */     try {
/* 191 */       if (!this.m_bTableOpen) {
/* 192 */         openTable("");
/*     */       }
/* 194 */     } catch (IOException iOException) {
/*     */       
/* 196 */       return 0;
/*     */     } 
/*     */     
/* 199 */     if (0 == this.m_strChordLine.length()) {
/*     */       
/* 201 */       this.m_strChordLine = new StringBuffer("<tr class=sg_chord>");
/* 202 */       if (0 != paramString.length())
/* 203 */         this.m_strChordLine.append("<td></td>"); 
/* 204 */       this.m_strLyricLine = new StringBuffer("<tr class=sg_lyric>");
/*     */     } 
/*     */     
/* 207 */     this.m_strChordLine.append("<td>" + (this.m_bDoReMi ? paramChord.getDoReMiName() : paramChord.getName()) + "&nbsp;</td>");
/*     */     
/* 209 */     if (0 != paramString.length()) {
/* 210 */       this.m_strLyricLine.append("<td>" + paramString.substring(this.m_strPrevLyric.length()) + "</td>");
/*     */     }
/*     */     
/* 213 */     this.m_strPrevLyric = paramString;
/*     */     
/* 215 */     return 2;
/*     */   }
/*     */   
/*     */   public int printChordSpaceAbove() {
/* 219 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChordNewLine() {
/*     */     try {
/* 226 */       if (0 != this.m_strChordLine.length()) {
/* 227 */         this.m_out.write(this.m_strChordLine + "</tr>" + m_strNewline);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 233 */       boolean bool = true;
/*     */       
/* 235 */       for (byte b = 0; b < this.m_strPrevLyric.length(); b++) {
/* 236 */         if (!Character.isWhitespace(this.m_strPrevLyric.charAt(b))) {
/*     */           
/* 238 */           bool = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 242 */       if (bool)
/*     */       {
/* 244 */         this.m_strPrevLyric = "";
/* 245 */         this.m_strChordLine.setLength(0);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 250 */     catch (IOException iOException) {
/*     */       
/* 252 */       return 0;
/*     */     } 
/*     */     
/* 255 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printLyric(String paramString) {
/* 261 */     boolean bool = this.m_bTableOpen;
/*     */ 
/*     */     
/*     */     try {
/* 265 */       if (this.m_bTableOpen) {
/*     */         
/* 267 */         if (0 != this.m_strLyricLine.length()) {
/*     */           
/* 269 */           this.m_strLyricLine.append("<td>" + paramString.substring(this.m_strPrevLyric.length()) + "</td></tr>");
/* 270 */           String str = escapem(this.m_strLyricLine.toString());
/* 271 */           this.m_out.write(str + m_strNewline);
/*     */         } 
/*     */         
/* 274 */         closeTable();
/*     */       } 
/*     */       
/* 277 */       if (!this.m_bTableOpen && 0 == this.m_strLyricLine.length())
/*     */       {
/* 279 */         if (0 != paramString.length()) {
/*     */           
/* 281 */           paramString = escapem(paramString);
/* 282 */           if (this.m_bInTOC)
/*     */           {
/* 284 */             this.m_out.write("<a href=\"#s" + String.valueOf(this.m_nTOCNumber++) + "\">");
/* 285 */             if ('y' == this.m_props.getProperty("songs.number").charAt(0))
/* 286 */               this.m_out.write(String.valueOf(this.m_nTOCNumber - 1) + ". "); 
/* 287 */             this.m_out.write(paramString + "</a><br>" + m_strNewline);
/*     */           }
/* 289 */           else if (this.m_bInTab)
/*     */           {
/* 291 */             this.m_out.write(paramString + m_strNewline);
/*     */           }
/* 293 */           else if (this.m_b1stLine)
/*     */           {
/* 295 */             this.m_out.write("<p class=sg_lyric>" + paramString + "<br>" + m_strNewline);
/*     */           }
/*     */           else
/*     */           {
/* 299 */             this.m_out.write(paramString + "<br>" + m_strNewline);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 304 */         else if (this.m_bInTab) {
/* 305 */           this.m_out.write(m_strNewline);
/*     */         } else {
/* 307 */           this.m_out.write("<p class=sg_lyric>" + m_strNewline);
/*     */         }
/*     */       
/*     */       }
/* 311 */     } catch (IOException iOException) {
/*     */       
/* 313 */       return 0;
/*     */     } 
/*     */     
/* 316 */     this.m_strChordLine.setLength(0);
/* 317 */     this.m_strLyricLine.setLength(0);
/*     */ 
/*     */     
/* 320 */     this.m_strPrevLyric = "";
/*     */     
/* 322 */     if (bool) {
/* 323 */       this.m_b1stLine = true;
/*     */     } else {
/* 325 */       this.m_b1stLine = false;
/*     */     } 
/* 327 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printComment(String paramString) {
/*     */     try {
/* 335 */       if (this.m_bTableOpen) {
/* 336 */         closeTable();
/*     */       }
/* 338 */       paramString = escapem(paramString);
/*     */       
/* 340 */       this.m_out.write("<p class=sg_comment>" + paramString + "</p>" + m_strNewline);
/*     */     }
/* 342 */     catch (IOException iOException) {
/*     */       
/* 344 */       return 0;
/*     */     } 
/*     */     
/* 347 */     return 2;
/*     */   }
/*     */   
/*     */   public int printNormalSpace() {
/* 351 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int markStartOfChorus() {
/* 356 */     this.m_bInChorus = true;
/*     */ 
/*     */     
/*     */     try {
/* 360 */       this.m_out.write("<div class=sg_chorus_all>");
/*     */       
/* 362 */       if (this.m_bTableOpen)
/* 363 */         closeTable(); 
/* 364 */       openTable("chorus");
/*     */     }
/* 366 */     catch (IOException iOException) {
/*     */       
/* 368 */       return 0;
/*     */     } 
/*     */     
/* 371 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markEndOfChorus() {
/*     */     try {
/* 379 */       if (this.m_bTableOpen) {
/* 380 */         closeTable();
/*     */       }
/* 382 */       this.m_out.write("</div>");
/*     */     }
/* 384 */     catch (IOException iOException) {
/*     */       
/* 386 */       return 0;
/*     */     } 
/*     */     
/* 389 */     this.m_bInChorus = false;
/*     */     
/* 391 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int markStartOfTab() {
/* 397 */     this.m_bInTab = true;
/*     */ 
/*     */     
/*     */     try {
/* 401 */       this.m_out.write("<pre class=sg_tab>");
/*     */     }
/* 403 */     catch (IOException iOException) {
/*     */       
/* 405 */       return 0;
/*     */     } 
/*     */     
/* 408 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markEndOfTab() {
/*     */     try {
/* 416 */       this.m_out.write("</pre>");
/*     */     }
/* 418 */     catch (IOException iOException) {
/*     */       
/* 420 */       return 0;
/*     */     } 
/*     */     
/* 423 */     this.m_bInTab = false;
/*     */     
/* 425 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markNewSong() {
/*     */     try {
/* 433 */       if (this.m_bInChorus)
/* 434 */         markEndOfChorus(); 
/* 435 */       if (this.m_bInTab) {
/* 436 */         markEndOfTab();
/*     */       }
/* 438 */       if (this.m_bTableOpen) {
/* 439 */         closeTable();
/*     */       }
/* 441 */       if (!this.m_bInTOC)
/*     */       {
/* 443 */         this.m_out.write("<div class=sg_newsong> </div>" + m_strNewline);
/* 444 */         this.m_out.write("</div>" + m_strNewline);
/*     */       }
/*     */     
/* 447 */     } catch (IOException iOException) {
/*     */       
/* 449 */       return 0;
/*     */     } 
/*     */     
/* 452 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void openTable(String paramString) throws IOException {
/* 458 */     this.m_out.write("<table ");
/* 459 */     if (this.m_bInChorus) {
/* 460 */       this.m_out.write("class=sg_chorus ");
/*     */     }
/* 462 */     this.m_out.write("summary=\"" + paramString + "\" ");
/*     */     
/* 464 */     this.m_out.write("cellpadding=0 cellspacing=0 border=0>" + m_strNewline);
/* 465 */     this.m_bTableOpen = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeTable() throws IOException {
/* 471 */     this.m_out.write("</table>" + m_strNewline);
/* 472 */     this.m_bTableOpen = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String escapem(String paramString) {
/* 478 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/*     */ 
/*     */     
/*     */     try {
/* 482 */       int i = 0;
/*     */       
/* 484 */       while (-1 != (i = stringBuffer.indexOf("&", i))) {
/*     */         
/* 486 */         stringBuffer.insert(++i, "amp;");
/* 487 */         i += 4;
/*     */       } 
/*     */       
/* 490 */       i = 0;
/* 491 */       boolean bool = false;
/* 492 */       while (stringBuffer.length() > i) {
/*     */         
/* 494 */         if ('<' == stringBuffer.charAt(i)) {
/* 495 */           bool = true;
/* 496 */         } else if ('>' == stringBuffer.charAt(i)) {
/* 497 */           bool = false;
/* 498 */         } else if (!bool && ' ' == stringBuffer.charAt(i)) {
/* 499 */           stringBuffer.replace(i, i + 1, "&nbsp;");
/*     */         } 
/* 501 */         i++;
/*     */       } 
/*     */       
/* 504 */       i = 0;
/* 505 */       while (-1 != (i = paramString.indexOf("\"", i))) {
/*     */         
/* 507 */         stringBuffer.replace(i, i + 1, "&quot;");
/* 508 */         i += 5;
/*     */       } 
/*     */       
/* 511 */       i = 0;
/* 512 */       while (-1 != (i = paramString.indexOf("‘", i))) {
/*     */         
/* 514 */         stringBuffer.replace(i, i + 1, "&lsquo;");
/* 515 */         i += 6;
/*     */       } 
/*     */       
/* 518 */       i = 0;
/* 519 */       while (-1 != (i = paramString.indexOf("’", i))) {
/*     */         
/* 521 */         stringBuffer.replace(i, i + 1, "&rsquo;");
/* 522 */         i += 6;
/*     */       } 
/*     */       
/* 525 */       i = 0;
/* 526 */       while (-1 != (i = paramString.indexOf("“", i))) {
/*     */         
/* 528 */         stringBuffer.replace(i, i + 1, "&ldquo;");
/* 529 */         i += 6;
/*     */       } 
/*     */       
/* 532 */       i = 0;
/* 533 */       while (-1 != (i = paramString.indexOf("”", i)))
/*     */       {
/* 535 */         stringBuffer.replace(i, i + 1, "&rdquo;");
/* 536 */         i += 6;
/*     */       }
/*     */     
/* 539 */     } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/*     */       
/* 541 */       return paramString;
/*     */     } 
/*     */     
/* 544 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongHTMLer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */