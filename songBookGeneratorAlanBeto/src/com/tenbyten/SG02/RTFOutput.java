/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ListIterator;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RTFOutput
/*     */   extends SongPlaintexter
/*     */ {
/*     */   protected boolean m_bTableOpen;
/*     */   protected boolean m_bInChorus;
/*     */   protected boolean m_b1stLine;
/*     */   protected int m_nTOCNumber;
/*     */   protected StringBuffer m_strChordLine;
/*     */   protected StringBuffer m_strLyricLine;
/*     */   protected Font m_fontNormal;
/*     */   protected Font m_fontTitle;
/*     */   protected Font m_fontSubtitle;
/*     */   protected Font m_fontChord;
/*     */   protected Font m_fontComment;
/*     */   protected Font m_fontGuitarComment;
/*     */   protected Font m_fontTab;
/*     */   protected Font m_fontFooter;
/*     */   protected Font m_fontGrid;
/*     */   protected FontMetrics m_fontMetricsNormal;
/*     */   protected FontMetrics m_fontMetricsChord;
/*     */   protected String m_rtfFontNormal;
/*     */   protected String m_rtfFontTitle;
/*     */   protected String m_rtfFontSubtitle;
/*     */   protected String m_rtfFontChord;
/*     */   protected String m_rtfFontComment;
/*     */   protected String m_rtfFontGuitarComment;
/*     */   protected String m_rtfFontTab;
/*     */   protected String m_rtfFontFooter;
/*     */   protected String m_rtfFontGrid;
/*     */   
/*     */   public RTFOutput(File paramFile) {
/*  54 */     super(paramFile);
/*  55 */     this.m_strChordLine = new StringBuffer(200);
/*  56 */     this.m_strLyricLine = new StringBuffer(200);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean printSongs() throws IOException {
/*  62 */     boolean bool = false;
/*     */     
/*  64 */     this.m_bTableOpen = false;
/*  65 */     this.m_bInChorus = false;
/*  66 */     this.m_bInTab = false;
/*  67 */     this.m_b1stLine = true;
/*     */     
/*  69 */     this.m_out = new OutputStreamWriter(new FileOutputStream(this.m_outputPath), "US-ASCII");
/*     */     
/*  71 */     this.m_nTOCNumber = 0;
/*     */ 
/*     */     
/*     */     try {
/*  75 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */ 
/*     */       
/*  78 */       this.m_out.write("{\\rtf1\\ansicpg1252" + m_strNewline);
/*     */ 
/*     */       
/*  81 */       this.m_out.write("{\\info ");
/*  82 */       this.m_out.write("{\\author " + resourceBundle.getString("Songsheet Generator") + " (" + resourceBundle.getString("Version") + ")}");
/*  83 */       this.m_out.write("{\\company Ten by Ten}");
/*  84 */       this.m_out.write("{\\doccomm http://www.tenbyten.com/software/songsgen}");
/*  85 */       this.m_out.write("}" + m_strNewline);
/*     */       
/*  87 */       defineFontsAndColors();
/*     */ 
/*     */       
/*  90 */       this.m_out.write("\\margt" + (int)(this.m_nMarginTop * 72.0D * 20.0D));
/*  91 */       this.m_out.write("\\margb" + (int)(this.m_nMarginBottom * 72.0D * 20.0D));
/*  92 */       this.m_out.write("\\margl" + (int)(this.m_nMarginLeft * 72.0D * 20.0D));
/*  93 */       this.m_out.write("\\margr" + (int)(this.m_nMarginRight * 72.0D * 20.0D) + m_strNewline);
/*     */ 
/*     */       
/*  96 */       if (20 == this.m_nSongsPerPage) {
/*     */         
/*  98 */         float f = Float.parseFloat(this.m_props.getProperty("print.spacing.column"));
/*  99 */         this.m_out.write("\\cols2\\colsx" + (int)(f * 72.0D * 20.0D));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 104 */       this.m_out.write("\\sprstsp\\sprsbsp");
/*     */       
/* 106 */       if (this.m_bPrintTOC || this.m_bOnlyTOC) {
/*     */         
/* 108 */         this.m_bInTOC = !this.m_bOnlyTOC;
/*     */ 
/*     */ 
/*     */         
/* 112 */         SongTOC songTOC = new SongTOC();
/* 113 */         ListIterator<SongFile> listIterator = this.m_qSongFiles.listIterator();
/* 114 */         while (listIterator.hasNext()) {
/*     */           
/* 116 */           SongFile songFile = listIterator.next();
/* 117 */           songTOC.addSongFile(new SongFileTOC(songFile));
/*     */         } 
/*     */         
/* 120 */         songTOC.print(this);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 125 */       this.m_bInTOC = false;
/*     */       
/* 127 */       this.m_nTOCNumber = 0;
/*     */       
/* 129 */       boolean bool1 = this.m_bCloseOutput;
/* 130 */       this.m_bCloseOutput = false;
/*     */       
/* 132 */       if (!this.m_bOnlyTOC) {
/* 133 */         bool = super.printSongs();
/*     */       }
/* 135 */       this.m_bCloseOutput = bool1;
/*     */ 
/*     */       
/* 138 */       this.m_out.write("}" + m_strNewline);
/*     */       
/* 140 */       this.m_out.flush();
/*     */       
/* 142 */       if (this.m_bCloseOutput) {
/* 143 */         this.m_out.close();
/*     */       }
/* 145 */     } catch (Exception exception) {
/*     */       
/* 147 */       System.out.println("SongHTMLer.printSongs() caught exception " + exception.toString());
/* 148 */       bool = false;
/*     */     } 
/*     */     
/* 151 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void defineFontsAndColors() throws IOException {
/* 157 */     String str = "print";
/*     */     
/* 159 */     this.m_fontTitle = deriveFont("title", str);
/* 160 */     this.m_fontSubtitle = deriveFont("subtitle", str);
/* 161 */     this.m_fontNormal = deriveFont("normal", str);
/* 162 */     this.m_fontChord = deriveFont("chord", str);
/* 163 */     this.m_fontComment = deriveFont("comment", str);
/* 164 */     this.m_fontGuitarComment = deriveFont("comment.guitar", str);
/* 165 */     this.m_fontGrid = deriveFont("grid", str);
/* 166 */     this.m_fontTab = deriveFont("tab", str);
/* 167 */     this.m_fontFooter = deriveFont("footer", str);
/*     */ 
/*     */     
/* 170 */     byte b = 0;
/* 171 */     this.m_rtfFontNormal = makeRTFFontString(b++, this.m_fontNormal);
/* 172 */     this.m_rtfFontTitle = makeRTFFontString(b++, this.m_fontTitle);
/* 173 */     this.m_rtfFontSubtitle = makeRTFFontString(b++, this.m_fontSubtitle);
/* 174 */     this.m_rtfFontChord = makeRTFFontString(b++, this.m_fontChord);
/* 175 */     this.m_rtfFontComment = makeRTFFontString(b++, this.m_fontComment);
/* 176 */     this.m_rtfFontGuitarComment = makeRTFFontString(b++, this.m_fontGuitarComment);
/* 177 */     this.m_rtfFontGrid = makeRTFFontString(b++, this.m_fontGrid);
/* 178 */     this.m_rtfFontTab = makeRTFFontString(b++, this.m_fontTab);
/* 179 */     this.m_rtfFontFooter = makeRTFFontString(b++, this.m_fontFooter);
/*     */ 
/*     */     
/* 182 */     b = 0;
/* 183 */     this.m_out.write("{\\fonttbl");
/* 184 */     this.m_out.write("\\f0\\fttruetype\\fcharset0 " + this.m_fontNormal.getName() + ";");
/* 185 */     this.m_out.write("\\f1\\fttruetype\\fcharset0 " + this.m_fontTitle.getName() + ";");
/* 186 */     this.m_out.write("\\f2\\fttruetype\\fcharset0 " + this.m_fontSubtitle.getName() + ";");
/* 187 */     this.m_out.write("\\f3\\fttruetype\\fcharset0 " + this.m_fontChord.getName() + ";");
/* 188 */     this.m_out.write("\\f4\\fttruetype\\fcharset0 " + this.m_fontComment.getName() + ";");
/* 189 */     this.m_out.write("\\f5\\fttruetype\\fcharset0 " + this.m_fontGuitarComment.getName() + ";");
/* 190 */     this.m_out.write("\\f6\\fttruetype\\fcharset0 " + this.m_fontGrid.getName() + ";");
/* 191 */     this.m_out.write("\\f7\\fttruetype\\fcharset0 " + this.m_fontTab.getName() + ";");
/* 192 */     this.m_out.write("\\f8\\fttruetype\\fcharset0 " + this.m_fontFooter.getName() + ";");
/* 193 */     this.m_out.write("}" + m_strNewline);
/*     */ 
/*     */     
/* 196 */     Color color1 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.title"), 16));
/* 197 */     Color color2 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.subtitle"), 16));
/* 198 */     Color color3 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.normal"), 16));
/* 199 */     Color color4 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.chord"), 16));
/* 200 */     Color color5 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.comment"), 16));
/* 201 */     Color color6 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.comment.guitar"), 16));
/* 202 */     Color color7 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.grid"), 16));
/* 203 */     Color color8 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.tab"), 16));
/* 204 */     Color color9 = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.footer"), 16));
/*     */     
/* 206 */     this.m_out.write("{\\colortbl");
/* 207 */     this.m_out.write(makeColorDefString(color3));
/* 208 */     this.m_out.write(makeColorDefString(color1));
/* 209 */     this.m_out.write(makeColorDefString(color2));
/* 210 */     this.m_out.write(makeColorDefString(color4));
/* 211 */     this.m_out.write(makeColorDefString(color5));
/* 212 */     this.m_out.write(makeColorDefString(color6));
/* 213 */     this.m_out.write(makeColorDefString(color7));
/* 214 */     this.m_out.write(makeColorDefString(color8));
/* 215 */     this.m_out.write(makeColorDefString(color9));
/* 216 */     this.m_out.write("}" + m_strNewline);
/*     */ 
/*     */     
/* 219 */     PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 220 */     PageFormat pageFormat = printerJob.defaultPage();
/* 221 */     Paper paper = pageFormat.getPaper();
/* 222 */     paper.setImageableArea(0.0D, 0.0D, paper.getWidth(), paper.getHeight());
/* 223 */     pageFormat.setPaper(paper);
/* 224 */     pageFormat = printerJob.validatePage(pageFormat);
/*     */     
/* 226 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 227 */     BufferedImage bufferedImage = new BufferedImage((int)pageFormat.getPaper().getImageableWidth(), (int)pageFormat.getPaper().getImageableHeight(), 11);
/* 228 */     Graphics2D graphics2D = graphicsEnvironment.createGraphics(bufferedImage);
/*     */     
/* 230 */     this.m_fontMetricsNormal = graphics2D.getFontMetrics(this.m_fontNormal);
/* 231 */     this.m_fontMetricsChord = graphics2D.getFontMetrics(this.m_fontChord);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Font deriveFont(String paramString1, String paramString2) {
/* 237 */     Font font = null;
/*     */ 
/*     */     
/*     */     try {
/* 241 */       int i = Integer.parseInt(this.m_props.getProperty(paramString2 + ".font.size." + paramString1));
/* 242 */       int j = 0;
/*     */       
/* 244 */       if ('y' == this.m_props.getProperty(paramString2 + ".font.style." + paramString1 + ".bold").charAt(0))
/* 245 */         j |= 0x1; 
/* 246 */       if ('y' == this.m_props.getProperty(paramString2 + ".font.style." + paramString1 + ".italic").charAt(0)) {
/* 247 */         j |= 0x2;
/*     */       }
/* 249 */       font = new Font(this.m_props.getProperty(paramString2 + ".font.family." + paramString1), j, i);
/*     */     }
/* 251 */     catch (Exception exception) {
/*     */       
/* 253 */       System.err.println("SongPrinter:deriveFont: unable to set font for " + paramString1 + ";\n" + exception.toString());
/*     */     } 
/*     */     
/* 256 */     return font;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String makeRTFFontString(int paramInt, Font paramFont) {
/* 262 */     String str = "";
/* 263 */     if (paramFont == this.m_fontChord) {
/*     */       
/* 265 */       float f = Float.parseFloat(this.m_props.getProperty("print.spacing.chord"));
/* 266 */       str = "\\lisa0\\lisb" + (int)(paramFont.getSize() * (f - 1.0D) * 10.0D);
/*     */     }
/* 268 */     else if (paramFont == this.m_fontTitle || paramFont == this.m_fontSubtitle) {
/*     */       
/* 270 */       float f = Float.parseFloat(this.m_props.getProperty("print.spacing.title"));
/*     */       
/* 272 */       str = "\\lisb0\\lisa" + (int)(paramFont.getSize() * (f - 1.0D) * 10.0D);
/*     */     }
/*     */     else {
/*     */       
/* 276 */       float f = Float.parseFloat(this.m_props.getProperty("print.spacing.lyric"));
/* 277 */       str = "\\lisa0\\lisb" + (int)(paramFont.getSize() * (f - 1.0D) * 10.0D);
/*     */     } 
/*     */     
/* 280 */     return "\\plain\\f" + paramInt + "\\fs" + (paramFont
/* 281 */       .getSize() * 2) + "\\cf" + paramInt + (
/*     */       
/* 283 */       paramFont.isBold() ? "\\b" : "") + (
/* 284 */       paramFont.isItalic() ? "\\i" : "") + str + " ";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String makeColorDefString(Color paramColor) {
/* 292 */     return "\\red" + paramColor.getRed() + "\\green" + paramColor
/* 293 */       .getGreen() + "\\blue" + paramColor
/* 294 */       .getBlue() + ";";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printTitle(String paramString) {
/* 301 */     this.m_bInChorus = false;
/*     */ 
/*     */     
/*     */     try {
/* 305 */       this.m_out.write(this.m_rtfFontTitle);
/*     */       
/* 307 */       if (!this.m_bInTOC && 'y' == this.m_props.getProperty("songs.number").charAt(0)) {
/* 308 */         this.m_out.write(String.valueOf(++this.m_nTOCNumber) + ". ");
/*     */       }
/* 310 */       paramString = escapem(paramString);
/*     */       
/* 312 */       this.m_out.write(paramString + "\\" + m_strNewline);
/*     */       
/* 314 */       this.m_b1stLine = true;
/*     */       
/* 316 */       return 2;
/*     */     }
/* 318 */     catch (IOException iOException) {
/*     */       
/* 320 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printSubtitle(String paramString) {
/*     */     try {
/* 329 */       this.m_out.write(this.m_rtfFontSubtitle);
/*     */       
/* 331 */       paramString = escapem(paramString);
/*     */       
/* 333 */       this.m_out.write(paramString + "\\" + m_strNewline);
/* 334 */       return 2;
/*     */     }
/* 336 */     catch (IOException iOException) {
/*     */       
/* 338 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChord(Chord paramChord, String paramString) {
/* 348 */     String str = this.m_bDoReMi ? paramChord.getDoReMiName() : paramChord.getName();
/*     */ 
/*     */ 
/*     */     
/* 352 */     if (0 != paramString.length()) {
/*     */       
/* 354 */       int i = this.m_fontMetricsNormal.stringWidth(paramString);
/* 355 */       int j = this.m_fontMetricsChord.stringWidth(this.m_strChordLine.toString());
/*     */       
/* 357 */       while (j < i) {
/*     */         
/* 359 */         this.m_strChordLine.append(" ");
/* 360 */         j = this.m_fontMetricsChord.stringWidth(this.m_strChordLine.toString());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 365 */     this.m_strChordLine.append(str + " ");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 374 */     this.m_strPrevLyric = paramString;
/*     */     
/* 376 */     return 2;
/*     */   }
/*     */   
/*     */   public int printChordSpaceAbove() {
/* 380 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int printChordNewLine() {
/*     */     try {
/* 387 */       if (0 != this.m_strChordLine.length())
/* 388 */         this.m_out.write(this.m_rtfFontChord + this.m_strChordLine + "\\" + m_strNewline); 
/* 389 */       this.m_strChordLine.setLength(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       boolean bool = true;
/*     */       
/* 397 */       for (byte b = 0; b < this.m_strPrevLyric.length(); b++) {
/* 398 */         if (!Character.isWhitespace(this.m_strPrevLyric.charAt(b))) {
/*     */           
/* 400 */           bool = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 404 */       if (bool) {
/*     */         
/* 406 */         this.m_strPrevLyric = "";
/* 407 */         this.m_strChordLine.setLength(0);
/*     */       } 
/*     */ 
/*     */       
/* 411 */       return 2;
/*     */     }
/* 413 */     catch (IOException iOException) {
/*     */       
/* 415 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printLyric(String paramString) {
/*     */     try {
/* 424 */       if (this.m_bInTab) {
/* 425 */         this.m_out.write(this.m_rtfFontTab);
/*     */       } else {
/* 427 */         this.m_out.write(this.m_rtfFontNormal);
/*     */       } 
/* 429 */       paramString = escapem(paramString);
/*     */       
/* 431 */       if (this.m_bInTOC)
/*     */       {
/* 433 */         if ('y' == this.m_props.getProperty("songs.number").charAt(0)) {
/* 434 */           this.m_out.write(String.valueOf(++this.m_nTOCNumber) + ". ");
/*     */         }
/*     */       }
/* 437 */       this.m_out.write(paramString + "\\" + m_strNewline);
/* 438 */       this.m_strPrevLyric = "";
/*     */       
/* 440 */       this.m_strChordLine.setLength(0);
/*     */       
/* 442 */       return 2;
/*     */     }
/* 444 */     catch (IOException iOException) {
/*     */       
/* 446 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int printComment(String paramString) {
/*     */     try {
/* 455 */       this.m_out.write(this.m_rtfFontComment);
/* 456 */       this.m_out.write(paramString + "\\" + m_strNewline);
/* 457 */       return 2;
/*     */     }
/* 459 */     catch (IOException iOException) {
/*     */       
/* 461 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int printNormalSpace() {
/* 466 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int markStartOfChorus() {
/* 471 */     this.m_bInChorus = true;
/*     */     
/* 473 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int markEndOfChorus() {
/* 479 */     this.m_bInChorus = false;
/*     */     
/* 481 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int markStartOfTab() {
/* 487 */     this.m_bInTab = true;
/*     */     
/* 489 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int markEndOfTab() {
/* 495 */     this.m_bInTab = false;
/*     */     
/* 497 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int markNewSong() {
/*     */     try {
/* 505 */       this.m_out.write("\\sect" + m_strNewline);
/*     */     }
/* 507 */     catch (IOException iOException) {
/*     */       
/* 509 */       return 0;
/*     */     } 
/*     */     
/* 512 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
private String escapem(String string) {
    StringBuffer stringBuffer = new StringBuffer(string);
    try {
        int n = 0;
        while (-1 != (n = stringBuffer.indexOf("\\", n))) {
            stringBuffer.insert(++n, "\\");
            ++n;
        }
        n = 0;
        while (-1 != (n = stringBuffer.indexOf("{", n))) {
            stringBuffer.insert(n, "\\");
            n += 2;
        }
        n = 0;
        while (-1 != (n = stringBuffer.indexOf("}", n))) {
            stringBuffer.insert(n, "\\");
            n += 2;
        }
        for (n = 0; stringBuffer.length() > n; ++n) {
            char c = stringBuffer.charAt(n);
            if ('\u0080' >= c) continue;
            String string2 = "\\ud{\\u" + Integer.valueOf(c) + "}";
            stringBuffer.replace(n, n + 1, string2);
            n += string2.length() - 1;
        }
    }
    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
        return string;
    }
    return stringBuffer.toString();
}
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\RTFOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */