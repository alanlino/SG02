/*      */ package com.tenbyten.SG02;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SongPrinter
/*      */   extends SongOutput
/*      */   implements Printable
/*      */ {
/*  115 */   protected float m_fontGridLastSize = 0.0F;
/*      */ 
/*      */ 
/*      */   
/*  119 */   protected float m_widthTabChar = Float.parseFloat(this.m_props.getProperty("print.chars.tab.inches")) * 72.0F;
/*      */   
/*      */   protected boolean m_bFooterManuallyTurnedOff = false;
/*      */   
/*  123 */   protected Vector m_qSongFiles = new Vector();
/*      */   
/*  125 */   protected Vector m_qChordPlacement = new Vector();
/*      */   
/*      */   protected boolean m_bChordNewLine = true;
/*  128 */   protected Point2D.Float m_ptSongMin = new Point2D.Float();
/*  129 */   protected Point2D.Float m_ptSongMax = new Point2D.Float();
/*  130 */   protected Point2D.Float m_ptLastChord = new Point2D.Float();
/*  131 */   protected Point2D.Float m_ptChorusStart = new Point2D.Float();
/*      */   
/*  133 */   protected int m_nPageCount = 0; protected boolean m_bPaginating = false; protected float m_yLoc; protected boolean m_bCurrentSongPaused; protected boolean m_bCurrentSongFinished;
/*      */   protected boolean m_bPageIsOpen;
/*      */   protected boolean m_bInChorus;
/*      */   protected boolean m_bNewColumn;
/*      */   protected boolean m_bPrintNothing;
/*      */   protected int m_nSongOfPage;
/*  139 */   protected boolean m_bCenterTitleOnPage = ('y' == this.m_props.getProperty("print.format.title.centeronpage").charAt(0)); protected boolean m_bChorus1stLine; protected boolean m_bChorus1stLineHasChords; protected SongFileTOC m_CurrentSong; protected int m_nCurrentPage; protected int m_nLastFooterPage; protected boolean m_bUse2ndColumnSpacing; protected Font m_fontNormal;
/*      */   protected Font m_fontTitle;
/*  141 */   protected boolean m_bCenterGrids = ('c' == this.m_props.getProperty("print.format.grids.align").charAt(0)); protected Font m_fontSubtitle; protected Font m_fontChord; protected Font m_fontComment; protected Font m_fontGuitarComment; protected Font m_fontTab; protected Font m_fontFooter; protected int m_fontNormalAscent; protected int m_fontTitleAscent; protected int m_fontSubtitleAscent; protected int m_fontChordAscent; protected int m_fontCommentAscent; protected int m_fontGuitarCommentAscent; protected int m_fontTabAscent; protected int m_fontNormalSpaceDown; protected int m_fontTitleSpaceDown; protected int m_fontSubtitleSpaceDown; protected int m_fontChordSpaceDown; protected int m_fontCommentSpaceDown; protected int m_fontGuitarCommentSpaceDown;
/*      */   protected int m_fontTabSpaceDown;
/*      */   protected int m_fontFooterSpaceDown;
/*      */   protected Color m_colorNormal;
/*      */   protected Color m_colorTitle;
/*      */   
/*      */   protected String getPropertyCategory() {
/*  148 */     return "print";
/*      */   }
/*      */   protected Color m_colorSubtitle; protected Color m_colorChord; protected Color m_colorComment; protected Color m_colorGuitarComment; protected Color m_colorGrid; protected Color m_colorTab; protected Color m_colorFooter;
/*      */   protected FooterParser m_FooterParser;
/*      */   
/*      */   protected void initFonts() {
/*  154 */     String str = getPropertyCategory();
/*      */     
/*  156 */     this.m_fontTitle = deriveFont("title", str);
/*  157 */     this.m_fontSubtitle = deriveFont("subtitle", str);
/*  158 */     this.m_fontNormal = deriveFont("normal", str);
/*  159 */     this.m_fontChord = deriveFont("chord", str);
/*  160 */     this.m_fontComment = deriveFont("comment", str);
/*  161 */     this.m_fontGuitarComment = deriveFont("comment.guitar", str);
/*  162 */     this.m_fontGrid = deriveFont("grid", str);
/*  163 */     this.m_fontTab = deriveFont("tab", str);
/*  164 */     this.m_fontFooter = deriveFont("footer", str);
/*      */   }
/*      */   protected Font m_fontGrid; protected String m_currentSongTitle; protected Graphics m_graphics; protected PageFormat m_pageFormat; protected int m_nPageIndex; protected SongTOC m_TOC; public static final String kChorusMarkNone = "none"; public static final String kChorusMarkLine = "line"; public static final String kChorusMarkLineThick = "line.thick"; public static final String kChorusMarkBox = "box"; public static final String kChorusMarkBoxLight = "box.light"; public static final String kChorusMarkBoxFill15 = "box.fill.0.15"; public static final String kChorusMarkIndentTab = ".tab."; public static final String kChorusMarkIndentTab1 = "indent.tab.1"; public static final String kChorusMarkIndentTab2 = "indent.tab.2";
/*      */   public static final String kChorusMarkLineTab1 = "line.tab.1";
/*      */   
/*      */   private Font deriveFont(String paramString1, String paramString2) {
/*  170 */     Font font = null;
/*      */ 
/*      */     
/*      */     try {
/*  174 */       int i = Integer.parseInt(this.m_props.getProperty(paramString2 + ".font.size." + paramString1));
/*  175 */       int j = 0;
/*      */       
/*  177 */       if ('y' == this.m_props.getProperty(paramString2 + ".font.style." + paramString1 + ".bold").charAt(0))
/*  178 */         j |= 0x1; 
/*  179 */       if ('y' == this.m_props.getProperty(paramString2 + ".font.style." + paramString1 + ".italic").charAt(0)) {
/*  180 */         j |= 0x2;
/*      */       }
/*  182 */       font = new Font(this.m_props.getProperty(paramString2 + ".font.family." + paramString1), j, i);
/*      */     }
/*  184 */     catch (Exception exception) {
/*      */       
/*  186 */       System.err.println("SongPrinter:deriveFont: unable to set font for " + paramString1 + ";\n" + exception.toString());
/*      */     } 
/*      */     
/*  189 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initColors() {
/*  195 */     String str = getPropertyCategory();
/*      */     
/*  197 */     this.m_colorTitle = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.title"), 16));
/*  198 */     this.m_colorSubtitle = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.subtitle"), 16));
/*  199 */     this.m_colorNormal = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.normal"), 16));
/*  200 */     this.m_colorChord = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.chord"), 16));
/*  201 */     this.m_colorComment = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.comment"), 16));
/*  202 */     this.m_colorGuitarComment = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.comment.guitar"), 16));
/*  203 */     this.m_colorGrid = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.grid"), 16));
/*  204 */     this.m_colorTab = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.tab"), 16));
/*  205 */     this.m_colorFooter = new Color(Integer.parseInt(this.m_props.getProperty(str + ".font.color.footer"), 16));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void overrideFooter(boolean paramBoolean) {
/*  211 */     this.m_bFooterManuallyTurnedOff = !paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void overrideCenterTitleOnPage(boolean paramBoolean) {
/*  217 */     this.m_bCenterTitleOnPage = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void override2ndColumnSpacing(boolean paramBoolean) {
/*  223 */     this.m_bUse2ndColumnSpacing = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void overrideTabCharWidth(float paramFloat) {
/*  229 */     this.m_widthTabChar = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paginate(Graphics paramGraphics, PageFormat paramPageFormat) {
/*  235 */     if (SG02App.isDebug) {
/*  236 */       System.err.println("paginating - start");
/*      */     }
/*  238 */     this.m_bPaginating = true;
/*  239 */     this.m_nPageCount = 0;
/*      */ 
/*      */ 
/*      */     
/*  243 */     if (this.m_bPrintTOC || this.m_bOnlyTOC) {
/*      */       
/*  245 */       this.m_TOC = new SongTOC();
/*  246 */       ListIterator<SongFileTOC> listIterator = this.m_qSongFiles.listIterator();
/*  247 */       while (listIterator.hasNext()) {
/*      */         
/*  249 */         SongFileTOC songFileTOC = listIterator.next();
/*  250 */         this.m_TOC.addSongFile(songFileTOC);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  257 */       print(paramGraphics, paramPageFormat, 0);
/*      */       
/*  259 */       if (SG02App.isDebug) {
/*  260 */         System.err.println("Total number of pages = " + String.valueOf(this.m_nPageCount));
/*      */       }
/*  262 */     } catch (Exception exception) {
/*      */       
/*  264 */       System.err.println("SongPrinter.paginate(): caught exception");
/*      */     } 
/*      */     
/*  267 */     this.m_bPaginating = false;
/*      */     
/*  269 */     if (SG02App.isDebug) {
/*  270 */       System.err.println("paginating - end");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPageCount() {
/*  276 */     return this.m_nPageCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) throws PrinterException {
/*  285 */     if (null != this.m_progressMonitor && this.m_progressMonitor.isCanceled()) {
/*  286 */       return 1;
/*      */     }
/*  288 */     if (SG02App.isDebug) {
/*  289 */       System.err.println("print page " + paramInt + ": \ngraphics is " + paramGraphics.toString());
/*      */     }
/*  291 */     this.m_bCurrentSongPaused = false;
/*  292 */     this.m_bCurrentSongFinished = false;
/*  293 */     this.m_bPageIsOpen = false;
/*  294 */     this.m_bInChorus = false;
/*  295 */     this.m_bInTab = false;
/*  296 */     this.m_bNewColumn = true;
/*  297 */     this.m_bPrintNothing = false;
/*  298 */     this.m_nSongOfPage = 0;
/*  299 */     this.m_nCurrentPage = 1;
/*  300 */     this.m_nLastFooterPage = 0;
/*  301 */     this.m_nTOCNumber = 0;
/*      */ 
/*      */     
/*  304 */     if (this.m_bPaginating || !(paramGraphics instanceof sun.print.PeekGraphics)) {
/*      */ 
/*      */       
/*  307 */       if (!this.m_bPaginating && null != this.m_progressMonitor) {
/*  308 */         this.m_progressMonitor.setProgress(paramInt);
/*      */       }
/*  310 */       this.m_graphics = paramGraphics;
/*  311 */       this.m_pageFormat = paramPageFormat;
/*  312 */       this.m_nPageIndex = paramInt + 1;
/*      */       
/*  314 */       if (null == this.m_fontNormal) {
/*      */         
/*  316 */         initFonts();
/*  317 */         initColors();
/*      */         
/*  319 */         this.m_fontTitleAscent = paramGraphics.getFontMetrics(this.m_fontTitle).getAscent();
/*  320 */         this.m_fontSubtitleAscent = paramGraphics.getFontMetrics(this.m_fontSubtitle).getAscent();
/*  321 */         this.m_fontNormalAscent = paramGraphics.getFontMetrics(this.m_fontNormal).getAscent();
/*  322 */         this.m_fontChordAscent = paramGraphics.getFontMetrics(this.m_fontChord).getAscent();
/*  323 */         this.m_fontCommentAscent = paramGraphics.getFontMetrics(this.m_fontComment).getAscent();
/*  324 */         this.m_fontGuitarCommentAscent = paramGraphics.getFontMetrics(this.m_fontGuitarComment).getAscent();
/*  325 */         this.m_fontTabAscent = paramGraphics.getFontMetrics(this.m_fontTab).getAscent();
/*      */ 
/*      */         
/*  328 */         this.m_fontTitleSpaceDown = paramGraphics.getFontMetrics(this.m_fontTitle).getHeight();
/*  329 */         this.m_fontSubtitleSpaceDown = paramGraphics.getFontMetrics(this.m_fontSubtitle).getHeight();
/*  330 */         this.m_fontNormalSpaceDown = paramGraphics.getFontMetrics(this.m_fontNormal).getHeight();
/*  331 */         this.m_fontChordSpaceDown = paramGraphics.getFontMetrics(this.m_fontChord).getHeight();
/*  332 */         this.m_fontCommentSpaceDown = paramGraphics.getFontMetrics(this.m_fontComment).getHeight();
/*  333 */         this.m_fontGuitarCommentSpaceDown = paramGraphics.getFontMetrics(this.m_fontGuitarComment).getHeight();
/*  334 */         this.m_fontTabSpaceDown = paramGraphics.getFontMetrics(this.m_fontTab).getHeight();
/*  335 */         this.m_fontFooterSpaceDown = paramGraphics.getFontMetrics(this.m_fontFooter).getHeight();
/*      */       } 
/*      */ 
/*      */       
/*  339 */       if (paramGraphics instanceof Graphics2D) {
/*      */ 
/*      */         
/*  342 */         if (1 == ((Graphics2D)paramGraphics).getDeviceConfiguration().getDevice().getType()) {
/*      */           
/*  344 */           BasicStroke basicStroke = new BasicStroke(Float.parseFloat(this.m_props.getProperty("print.stroke.printer.normal")));
/*  345 */           ((Graphics2D)paramGraphics).setStroke(basicStroke);
/*      */         } 
/*      */         
/*  348 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  349 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
/*  350 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
/*  351 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*  352 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
/*  353 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
/*  354 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*  355 */         ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*      */       } 
/*      */       
/*  358 */       if (!this.m_bFooterManuallyTurnedOff) {
/*  359 */         this.m_FooterParser = new FooterParser(this.m_props.getProperty(getPropertyCategory() + ".footer.text"));
/*      */       }
/*  361 */       if (this.m_bPrintTOC || this.m_bOnlyTOC) {
/*      */         
/*  363 */         this.m_bInTOC = true;
/*  364 */         this.m_TOC.print(this);
/*  365 */         this.m_bInTOC = false;
/*      */         
/*  367 */         if (!this.m_bOnlyTOC && 'y' == this.m_props.getProperty("toc.print.npp").charAt(0)) {
/*      */ 
/*      */           
/*  370 */           endPage();
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  375 */         else if (this.m_bPageIsOpen && (this.m_nSongsPerPage == 10 || this.m_nSongsPerPage == 20)) {
/*  376 */           this.m_yLoc += this.m_fontTitleSpaceDown;
/*      */         } 
/*      */       } 
/*      */       
/*  380 */       this.m_nTOCNumber = 0;
/*      */       
/*  382 */       if (!this.m_bOnlyTOC) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  400 */         ListIterator<SongFileTOC> listIterator = this.m_qSongFiles.listIterator();
/*  401 */         while (listIterator.hasNext()) {
/*      */           
/*  403 */           this.m_CurrentSong = listIterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  424 */           startSong();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  437 */           this.m_CurrentSong.m_SongFile.printSong(this);
/*      */ 
/*      */ 
/*      */           
/*  441 */           drawFooterOnCurrentPage();
/*      */           
/*  443 */           endSong();
/*      */ 
/*      */           
/*  446 */           if (!this.m_bPaginating)
/*      */           {
/*      */             
/*  449 */             if (this.m_CurrentSong.m_nPageNumber > this.m_nPageIndex)
/*      */               break; 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  455 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSongFile(SongFile paramSongFile) {
/*      */     try {
/*  463 */       this.m_qSongFiles.add(new SongFileTOC(paramSongFile));
/*      */     }
/*  465 */     catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSongFiles() {
/*  473 */     this.m_qSongFiles.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void startSong() {
/*  481 */     if (!this.m_bPageIsOpen) {
/*  482 */       startPage();
/*      */     }
/*  484 */     float f1 = 72.0F;
/*  485 */     float f2 = 72.0F;
/*      */     
/*  487 */     float f3 = (float)this.m_pageFormat.getHeight();
/*  488 */     float f4 = (float)this.m_pageFormat.getWidth();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  494 */     float f5 = (float)this.m_pageFormat.getImageableWidth();
/*  495 */     float f6 = (float)this.m_pageFormat.getImageableHeight();
/*      */     
/*  497 */     float f7 = Float.parseFloat(this.m_props.getProperty("print.spacing.column"));
/*  498 */     float f8 = Float.parseFloat(this.m_props.getProperty("print.spacing.column.2nd"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  506 */     if (f3 == 0.0F && f4 == 0.0F) {
/*      */       
/*  508 */       f3 = f6;
/*  509 */       f4 = f5;
/*      */     } 
/*      */     
/*  512 */     switch (this.m_nSongsPerPage) {
/*      */       
/*      */       case 1:
/*      */       case 10:
/*  516 */         this.m_ptSongMin.x = this.m_nMarginLeft * f1;
/*  517 */         this.m_ptSongMin.y = this.m_nMarginTop * f2;
/*  518 */         this.m_ptSongMax.x = f4 - this.m_nMarginRight * f1;
/*  519 */         this.m_ptSongMax.y = f3 - this.m_nMarginBottom * f2;
/*      */         break;
/*      */       
/*      */       case 2:
/*      */       case 20:
/*  524 */         switch (this.m_nSongOfPage) {
/*      */           
/*      */           case 0:
/*  527 */             this.m_ptSongMin.x = this.m_nMarginLeft * f1;
/*  528 */             this.m_ptSongMin.y = this.m_nMarginTop * f2;
/*  529 */             this.m_ptSongMax.x = (f4 - f7 * f1) / 2.0F;
/*  530 */             this.m_ptSongMax.y = f3 - this.m_nMarginBottom * f2;
/*      */             break;
/*      */           case 1:
/*  533 */             this.m_ptSongMin.x = (f4 + f7 * f1) / 2.0F;
/*  534 */             if (this.m_bUse2ndColumnSpacing && 2 == this.m_nSongsPerPage)
/*  535 */               this.m_ptSongMin.x += f8 * f1; 
/*  536 */             this.m_ptSongMin.y = this.m_nMarginTop * f2;
/*  537 */             this.m_ptSongMax.x = f4 - this.m_nMarginRight * f1;
/*  538 */             this.m_ptSongMax.y = f3 - this.m_nMarginBottom * f2;
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       case 4:
/*  544 */         switch (this.m_nSongOfPage) {
/*      */           
/*      */           case 0:
/*  547 */             this.m_ptSongMin.x = this.m_nMarginLeft * f1;
/*  548 */             this.m_ptSongMin.y = this.m_nMarginTop * f2;
/*  549 */             this.m_ptSongMax.x = (f4 - f7 * f1) / 2.0F;
/*  550 */             this.m_ptSongMax.y = (f3 - f7 * f2) / 2.0F;
/*      */             break;
/*      */           case 1:
/*  553 */             this.m_ptSongMin.x = (f4 + f7 * f1) / 2.0F;
/*  554 */             this.m_ptSongMin.y = this.m_nMarginTop * f2;
/*  555 */             this.m_ptSongMax.x = f4 - this.m_nMarginRight * f1;
/*  556 */             this.m_ptSongMax.y = (f3 - f7 * f2) / 2.0F;
/*      */             break;
/*      */           case 2:
/*  559 */             this.m_ptSongMin.x = this.m_nMarginLeft * f1;
/*  560 */             this.m_ptSongMin.y = (f3 + f7 * f2) / 2.0F;
/*  561 */             this.m_ptSongMax.x = (f4 - f7 * f1) / 2.0F;
/*  562 */             this.m_ptSongMax.y = f3 - this.m_nMarginBottom * f2;
/*      */             break;
/*      */           case 3:
/*  565 */             this.m_ptSongMin.x = (f4 + f7 * f1) / 2.0F;
/*  566 */             this.m_ptSongMin.y = (f3 + f7 * f2) / 2.0F;
/*  567 */             this.m_ptSongMax.x = f4 - this.m_nMarginRight * f1;
/*  568 */             this.m_ptSongMax.y = f3 - this.m_nMarginBottom * f2;
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  578 */     if (this.m_nSongsPerPage == 10 || this.m_nSongsPerPage == 20) {
/*      */       
/*  580 */       if (this.m_bNewColumn) {
/*  581 */         this.m_yLoc = this.m_ptSongMin.y;
/*      */       }
/*      */     } else {
/*      */       
/*  585 */       this.m_yLoc = this.m_ptSongMin.y;
/*      */     } 
/*      */     
/*  588 */     this.m_ptLastChord.setLocation(0.0F, 0.0F);
/*      */     
/*  590 */     if (!this.m_bCurrentSongPaused) {
/*      */       
/*  592 */       this.m_bInChorus = false;
/*  593 */       this.m_bInTab = false;
/*      */     } 
/*  595 */     this.m_bNewColumn = false;
/*  596 */     this.m_bChordNewLine = false;
/*      */ 
/*      */ 
/*      */     
/*  600 */     if (this.m_bPaginating && null != this.m_CurrentSong && 0 == this.m_CurrentSong.m_nPageCount) {
/*      */       
/*  602 */       this.m_CurrentSong.m_nPageNumber = this.m_nCurrentPage;
/*  603 */       this.m_CurrentSong.m_nSongOfPage = this.m_nSongOfPage;
/*  604 */       this.m_CurrentSong.m_songPrinter_yLoc = this.m_yLoc;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void endSong() {
/*  616 */     if ('y' == this.m_props.getProperty("print.songs.npp").charAt(0) || this.m_bCenterTitleOnPage) {
/*  617 */       this.m_nSongOfPage += 100;
/*      */     }
/*  619 */     if (this.m_nSongsPerPage == 10 || this.m_nSongsPerPage == 20) {
/*  620 */       this.m_yLoc += this.m_fontTitleSpaceDown;
/*      */     } else {
/*  622 */       this.m_nSongOfPage++;
/*      */     } 
/*  624 */     drawSongLooseEnds();
/*      */     
/*  626 */     if (this.m_CurrentSong == this.m_qSongFiles.lastElement()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  631 */       if (0 == this.m_CurrentSong.m_nPageCount) {
/*  632 */         this.m_CurrentSong.m_nPageCount = 1;
/*      */       }
/*  634 */     } else if (this.m_nSongOfPage >= this.m_nSongsPerPage) {
/*  635 */       endPage();
/*      */     } 
/*  637 */     if (!this.m_bCurrentSongPaused) {
/*      */       
/*  639 */       this.m_bInChorus = false;
/*  640 */       this.m_bInTab = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawSongLooseEnds() {
/*  647 */     if (this.m_bPageIsOpen && !this.m_qChordPlacement.isEmpty())
/*  648 */       drawNormalText("", this.m_fontChord, this.m_colorChord, 0, 0, 0.0F, true); 
/*  649 */     this.m_qChordPlacement.clear();
/*      */     
/*  651 */     if (this.m_bPageIsOpen && this.m_bInChorus && !this.m_bChorus1stLine) {
/*  652 */       markEndOfChorus();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void startPage() {
/*  664 */     this.m_bPrintNothing = (this.m_bPaginating || this.m_nCurrentPage != this.m_nPageIndex);
/*      */     
/*  666 */     this.m_nSongOfPage = 0;
/*  667 */     this.m_bPageIsOpen = true;
/*  668 */     this.m_bNewColumn = true;
/*      */ 
/*      */     
/*  671 */     if (this.m_bPaginating) {
/*  672 */       this.m_nPageCount++;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void endPage() {
/*  686 */     if (this.m_bPageIsOpen) {
/*      */       
/*  688 */       this.m_nCurrentPage++;
/*  689 */       this.m_nSongOfPage = 0;
/*      */     } 
/*  691 */     this.m_bPageIsOpen = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int printTitle(String paramString) {
/*  697 */     if (!this.m_bInTOC && 0 != paramString.length() && 'y' == this.m_props.getProperty("songs.number").charAt(0)) {
/*  698 */       paramString = new String(String.valueOf(++this.m_nTOCNumber) + ". " + paramString);
/*      */     }
/*  700 */     this.m_currentSongTitle = paramString;
/*      */     
/*  702 */     boolean bool = (!this.m_bInTOC && 'y' == this.m_props.getProperty("print.format.title.line").charAt(0)) ? true : false;
/*  703 */     return drawTitle(paramString, this.m_fontTitle, this.m_colorTitle, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int printSubtitle(String paramString) {
/*  709 */     boolean bool = (!this.m_bInTOC && 'y' == this.m_props.getProperty("print.format.title.line").charAt(0)) ? true : false;
/*  710 */     return drawTitle(paramString, this.m_fontSubtitle, this.m_colorSubtitle, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   

public int printChord(Chord chord, String string) {
    if (this.m_bChordNewLine) {
        this.drawNormalText("", this.m_fontChord, this.m_colorChord, 0, 0, 0.0f, true);
    }
    if ('y' == this.m_props.getProperty("print.fix.u2019").charAt(0)) {
        string = string.replace('\u2018', '\'');
        string = string.replace('\u2019', '\'');
    }
    ChordPlacement chordPlacement = new ChordPlacement(chord, string);
    this.m_qChordPlacement.add(chordPlacement);
    return 2;
}
/*      */ 
/*      */ 
/*      */   
/*      */   public int printChordSpaceAbove() {
/*  743 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int printChordNewLine() {
/*  749 */     this.m_bChordNewLine = true;
/*  750 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int printLyric(String paramString) {
/*  756 */     if (this.m_bInTOC && 'y' == this.m_props.getProperty("songs.number").charAt(0)) {
/*  757 */       paramString = new String(String.valueOf(++this.m_nTOCNumber) + ". " + paramString);
/*      */     }
/*  759 */     if (this.m_bInTab) {
/*  760 */       return drawNormalText(paramString, this.m_fontTab, this.m_colorTab, this.m_fontTabAscent, this.m_fontTabSpaceDown, Float.parseFloat(this.m_props.getProperty("print.spacing.lyric")), true);
/*      */     }
/*  762 */     return drawNormalText(paramString, this.m_fontNormal, this.m_colorNormal, this.m_fontNormalAscent, this.m_fontNormalSpaceDown, Float.parseFloat(this.m_props.getProperty("print.spacing.lyric")), true);
/*      */   }
/*      */ 
/*      */ 
			public int printTag(String paramString) {
/*  768 */     int i = 2;
/*      */     
/*  770 */     if (this.m_bPageIsOpen && !this.m_qChordPlacement.isEmpty())
/*  771 */       i = drawNormalText("", this.m_fontChord, this.m_colorChord, 0, 0, 0.0F, true); 
/*  772 */     this.m_qChordPlacement.clear();
/*      */     
/*  774 */     if (2 == i) {
/*      */       
/*  776 */       if ('y' == this.m_props.getProperty("print.parse.tags").charAt(0)) {
/*      */ 			
/*      */         
/*  779 */         FooterParser footerParser = new FooterParser(paramString);
/*  780 */         footerParser.setSong(this.m_CurrentSong.m_SongFile);
/*  781 */         footerParser.setSongTitle(this.m_CurrentSong.m_SongFile.getTitle());
/*  782 */         int j = paramString.length();
/*  783 */         paramString = footerParser.getLeftString();
/*  784 */         if (paramString.length() != j) {
/*      */           
/*  786 */           paramString = paramString + " ";
/*  787 */           j = paramString.length();
/*      */         } 
/*  789 */         paramString = paramString + footerParser.getCenterString();
/*  790 */         if (paramString.length() != j)
/*      */         {
/*  792 */           paramString = paramString + " ";
/*      */         }
/*  794 */         paramString = paramString + footerParser.getRightString();
/*      */       } 
/*      */       
/*  797 */       i = drawNormalText(paramString, this.m_fontComment, this.m_colorComment, this.m_fontCommentAscent, this.m_fontCommentSpaceDown, Float.parseFloat(this.m_props.getProperty("print.spacing.lyric")), false);
/*      */     } 
/*      */     
/*  800 */     return i;
/*      */   }
/*      */ 


/*      */   
/*      */   public int printComment(String paramString) {
/*  768 */     int i = 2;
/*      */     
/*  770 */     if (this.m_bPageIsOpen && !this.m_qChordPlacement.isEmpty())
/*  771 */       i = drawNormalText("", this.m_fontChord, this.m_colorChord, 0, 0, 0.0F, true); 
/*  772 */     this.m_qChordPlacement.clear();
/*      */     
/*  774 */     if (2 == i) {
/*      */       
/*  776 */       if ('y' == this.m_props.getProperty("print.parse.comments").charAt(0)) {
/*      */ 
/*      */         
/*  779 */         FooterParser footerParser = new FooterParser(paramString);
/*  780 */         footerParser.setSong(this.m_CurrentSong.m_SongFile);
/*  781 */         footerParser.setSongTitle(this.m_CurrentSong.m_SongFile.getTitle());
/*  782 */         int j = paramString.length();
/*  783 */         paramString = footerParser.getLeftString();
/*  784 */         if (paramString.length() != j) {
/*      */           
/*  786 */           paramString = paramString + " ";
/*  787 */           j = paramString.length();
/*      */         } 
/*  789 */         paramString = paramString + footerParser.getCenterString();
/*  790 */         if (paramString.length() != j)
/*      */         {
/*  792 */           paramString = paramString + " ";
/*      */         }
/*  794 */         paramString = paramString + footerParser.getRightString();
/*      */       } 
/*      */       
/*  797 */       i = drawNormalText(paramString, this.m_fontComment, this.m_colorComment, this.m_fontCommentAscent, this.m_fontCommentSpaceDown, Float.parseFloat(this.m_props.getProperty("print.spacing.lyric")), false);
/*      */     } 
/*      */     
/*  800 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int printGuitarComment(String paramString) {
/*  806 */     int i = 2;
/*      */     
/*  808 */     if (this.m_bPageIsOpen && !this.m_qChordPlacement.isEmpty())
/*  809 */       i = drawNormalText("", this.m_fontChord, this.m_colorChord, 0, 0, 0.0F, true); 
/*  810 */     this.m_qChordPlacement.clear();
/*      */     
/*  812 */     if (2 == i) {
/*      */       
/*  814 */       if ('y' == this.m_props.getProperty("print.parse.comments").charAt(0)) {
/*      */ 
/*      */         
/*  817 */         FooterParser footerParser = new FooterParser(paramString);
/*  818 */         footerParser.setSong(this.m_CurrentSong.m_SongFile);
/*  819 */         footerParser.setSongTitle(this.m_CurrentSong.m_SongFile.getTitle());
/*  820 */         int j = paramString.length();
/*  821 */         paramString = footerParser.getLeftString();
/*  822 */         if (paramString.length() != j) {
/*      */           
/*  824 */           paramString = paramString + " ";
/*  825 */           j = paramString.length();
/*      */         } 
/*  827 */         paramString = paramString + footerParser.getCenterString();
/*  828 */         if (paramString.length() != j)
/*      */         {
/*  830 */           paramString = paramString + " ";
/*      */         }
/*  832 */         paramString = paramString + footerParser.getRightString();
/*      */       } 
/*      */       
/*  835 */       i = drawNormalText(paramString, this.m_fontGuitarComment, this.m_colorGuitarComment, this.m_fontGuitarCommentAscent, this.m_fontGuitarCommentSpaceDown, Float.parseFloat(this.m_props.getProperty("print.spacing.lyric")), false);
/*      */     } 
/*      */     
/*  838 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int printNormalSpace() {
/*  844 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int markStartOfChorus() {
/*  850 */     this.m_ptChorusStart = new Point2D.Float(this.m_ptSongMin.x, this.m_yLoc);
/*  851 */     this.m_ptChorusStart.x += -7.2000003F;
/*  852 */     this.m_bInChorus = true;
/*  853 */     this.m_bChorus1stLine = true;
/*  854 */     this.m_bChorus1stLineHasChords = false;
/*      */ 
/*      */     
/*  857 */     String str = this.m_props.getProperty("print.chorus.mark");
/*  858 */     if (-1 != str.indexOf(".tab.")) {
/*      */       
/*  860 */       int i = str.lastIndexOf('.');
/*      */ 
/*      */       
/*      */       try {
/*  864 */         i = Integer.parseInt(str.substring(i + 1));
/*      */       }
/*  866 */       catch (Exception exception) {
/*      */         
/*  868 */         i = 1;
/*      */       } 
/*      */       
/*  871 */       this.m_ptSongMin.x += this.m_widthTabChar * i;
/*      */     } 
/*      */     
/*  874 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int markEndOfChorus() {
/*  880 */     Point2D.Float float_ = new Point2D.Float(this.m_ptChorusStart.x, this.m_yLoc);
/*      */     
/*  882 */     String str = this.m_props.getProperty("print.chorus.mark");
/*      */ 
/*      */ 
/*      */     
/*  886 */     if (-1 != str.indexOf(".tab.")) {
/*      */       
/*  888 */       int i = str.lastIndexOf('.');
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  893 */         i = Integer.parseInt(str.substring(i + 1));
/*      */       }
/*  895 */       catch (Exception exception) {
/*      */         
/*  897 */         i = 1;
/*      */       } 
/*      */       
/*  900 */       this.m_ptSongMin.x -= this.m_widthTabChar * i;
/*      */     } 
/*      */ 
/*      */     
/*  904 */     if (str.startsWith("line")) {
/*      */ 
/*      */       
/*  907 */       Stroke stroke = null;
/*  908 */       if (str.equals("line.thick"))
/*      */       {
/*  910 */         if (this.m_graphics instanceof Graphics2D) {
/*      */           
/*  912 */           stroke = ((Graphics2D)this.m_graphics).getStroke();
/*      */           
/*  914 */           if (1 == ((Graphics2D)this.m_graphics).getDeviceConfiguration().getDevice().getType()) {
/*      */             
/*  916 */             BasicStroke basicStroke = new BasicStroke(Float.parseFloat(this.m_props.getProperty("print.stroke.printer.thick")));
/*  917 */             ((Graphics2D)this.m_graphics).setStroke(basicStroke);
/*      */           }
/*  919 */           else if (stroke instanceof BasicStroke) {
/*      */             
/*  921 */             BasicStroke basicStroke = new BasicStroke(2.0F * ((BasicStroke)stroke).getLineWidth());
/*  922 */             ((Graphics2D)this.m_graphics).setStroke(basicStroke);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  927 */       if (!this.m_bPrintNothing) {
/*      */ 
/*      */ 
/*      */         
/*  931 */         if ('y' == this.m_props.getProperty("print.chords").charAt(0) && this.m_bChorus1stLineHasChords) {
/*  932 */           this.m_ptChorusStart.y += this.m_fontChordSpaceDown * Float.parseFloat(this.m_props.getProperty("print.spacing.chord"));
/*      */         }
/*  934 */         this.m_graphics.setColor(this.m_colorNormal);
/*  935 */         this.m_graphics.drawLine((int)this.m_ptChorusStart.x, (int)this.m_ptChorusStart.y, (int)float_.x, (int)float_.y);
/*      */       } 
/*      */       
/*  938 */       if (null != stroke) {
/*  939 */         ((Graphics2D)this.m_graphics).setStroke(stroke);
/*      */       }
/*      */     } 
/*  942 */     if (str.startsWith("box")) {
/*      */       
/*  944 */       float_.x = this.m_ptSongMax.x;
/*  945 */       float_.y += this.m_fontNormalSpaceDown / 2.0F;
/*      */       
/*  947 */       if (!this.m_bPrintNothing) {
/*      */         
/*  949 */         this.m_graphics.setColor(this.m_colorNormal);
/*      */ 
/*      */         
/*  952 */         if (0 == str.compareTo("box")) {
/*      */           
/*  954 */           this.m_graphics.drawRect((int)this.m_ptChorusStart.x, (int)this.m_ptChorusStart.y, (int)(float_.x - this.m_ptChorusStart.x), (int)(float_.y - this.m_ptChorusStart.y));
/*      */         }
/*  956 */         else if (0 == str.compareTo("box.light")) {
/*      */           
/*  958 */           if (this.m_graphics instanceof Graphics2D)
/*      */           {
/*  960 */             Graphics2D graphics2D = (Graphics2D)this.m_graphics;
/*  961 */             Composite composite = graphics2D.getComposite();
/*  962 */             AlphaComposite alphaComposite = AlphaComposite.getInstance(3, 0.25F);
/*  963 */             graphics2D.setComposite(alphaComposite);
/*  964 */             this.m_graphics.drawRect((int)this.m_ptChorusStart.x, (int)this.m_ptChorusStart.y, (int)(float_.x - this.m_ptChorusStart.x), (int)(float_.y - this.m_ptChorusStart.y));
/*  965 */             graphics2D.setComposite(composite);
/*      */           }
/*      */           else
/*      */           {
/*  969 */             this.m_graphics.setColor(Color.GRAY);
/*  970 */             this.m_graphics.drawRect((int)this.m_ptChorusStart.x, (int)this.m_ptChorusStart.y, (int)(float_.x - this.m_ptChorusStart.x), (int)(float_.y - this.m_ptChorusStart.y));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  975 */           int i = str.lastIndexOf('.');
/*      */           
/*  977 */           float f = 0.25F;
/*      */ 
/*      */           
/*      */           try {
/*  981 */             f = Float.parseFloat(str.substring(i - 1));
/*      */           }
/*  983 */           catch (Exception exception) {}
/*      */           
/*  985 */           if (this.m_graphics instanceof Graphics2D) {
/*      */             
/*  987 */             Graphics2D graphics2D = (Graphics2D)this.m_graphics;
/*  988 */             Composite composite = graphics2D.getComposite();
/*  989 */             AlphaComposite alphaComposite = AlphaComposite.getInstance(3, f);
/*  990 */             graphics2D.setComposite(alphaComposite);
/*  991 */             graphics2D.fillRect((int)this.m_ptChorusStart.x, (int)this.m_ptChorusStart.y, (int)(float_.x - this.m_ptChorusStart.x), (int)(float_.y - this.m_ptChorusStart.y));
/*  992 */             graphics2D.setComposite(composite);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  997 */       this.m_yLoc += this.m_fontNormalSpaceDown;
/*      */     } 
/*      */     
/* 1000 */     this.m_bInChorus = false;
/*      */     
/* 1002 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int markStartOfTab() {
/* 1008 */     this.m_bInTab = true;
/*      */     
/* 1010 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int markEndOfTab() {
/* 1017 */     this.m_bInTab = false;
/*      */     
/* 1019 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int markNewSong() {
/* 1025 */     if (this.m_bPageIsOpen && (this.m_nSongsPerPage == 10 || this.m_nSongsPerPage == 20)) {
/* 1026 */       return 2;
/*      */     }
/* 1028 */     return pageOverflow(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newColumn() {
/* 1035 */     if (this.m_bPageIsOpen && (this.m_nSongsPerPage == 10 || this.m_nSongsPerPage == 20)) {
/* 1036 */       return pageOverflow(false);
/*      */     }
/* 1038 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newPage() {
/* 1045 */     if (this.m_bPageIsOpen) {
/* 1046 */       return pageOverflow(false);
/*      */     }
/* 1048 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newPhysicalPage() {
/* 1056 */     if (this.m_bPageIsOpen) {
/*      */ 
/*      */       
/* 1059 */       this.m_yLoc = (float)this.m_pageFormat.getHeight();
/* 1060 */       this.m_nSongOfPage = this.m_nSongsPerPage - 1;
/* 1061 */       return pageOverflow(false);
/*      */     } 
/*      */     
/* 1064 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int drawTitle(String paramString, Font paramFont, Color paramColor, boolean paramBoolean) {
/*      */     try {
/* 1074 */       boolean bool1 = (paramFont == this.m_fontSubtitle) ? true : false;
/*      */       
/* 1076 */       if (this.m_yLoc + (this.m_fontTitleSpaceDown * 3) > this.m_ptSongMax.y) {
/*      */         
/* 1078 */         int j = pageOverflow((20 == this.m_nSongsPerPage || 10 == this.m_nSongsPerPage));
/* 1079 */         if (2 != j) {
/* 1080 */           return j;
/*      */         }
/*      */       } 
/* 1083 */       this.m_graphics.setFont(paramFont);
/* 1084 */       this.m_graphics.setColor(paramColor);
/*      */       
/* 1086 */       float f1 = this.m_ptSongMin.x;
/* 1087 */       float f2 = this.m_ptSongMax.x;
/*      */ 
/*      */       
/* 1090 */       if (this.m_bCenterTitleOnPage) {
/*      */         
/* 1092 */         float f = 72.0F;
/* 1093 */         f1 = this.m_nMarginLeft * f;
/* 1094 */         f2 = (float)this.m_pageFormat.getWidth() - this.m_nMarginRight * f;
/*      */       } 
/*      */       
/* 1097 */       FontMetrics fontMetrics = this.m_graphics.getFontMetrics();
/* 1098 */       int i = fontMetrics.getAscent();
/* 1099 */       Rectangle2D.Float float_1 = getStringBoundsWithTabCharacters(fontMetrics, paramString, this.m_graphics);
/* 1100 */       Font font = paramFont;
/*      */       
/* 1102 */       float f3 = Float.parseFloat(this.m_props.getProperty("print.spacing.title"));
/*      */ 
/*      */       
/* 1105 */       String str = this.m_props.getProperty("print.format.title");
/*      */ 
/*      */       
/* 1108 */       if (bool1) {
/*      */         
/* 1110 */         this.m_yLoc -= this.m_fontTitleSpaceDown * (f3 - 1.0F);
/* 1111 */         str = this.m_props.getProperty("print.format.subtitle");
/*      */       } 
/*      */ 
/*      */       
/* 1115 */       boolean bool2 = (str.startsWith("%c") && 'y' == this.m_props.getProperty("print.wrap.titles").charAt(0)) ? true : false;
/* 1116 */       ArrayList arrayList = null;
/* 1117 */       Rectangle2D.Float float_2 = null;
/* 1118 */       StringBuffer stringBuffer = null;
/*      */       
/* 1120 */       if (bool2) {
/*      */ 
/*      */         
/* 1123 */         arrayList = new ArrayList();
/* 1124 */         float_2 = new Rectangle2D.Float(f1, this.m_yLoc + i, f2 - f1, this.m_ptSongMax.y);
/* 1125 */         stringBuffer = new StringBuffer(paramString);
/*      */         
/* 1127 */         drawWrappingFooter(true, stringBuffer, arrayList, 2, this.m_graphics, fontMetrics, float_2, this.m_fontTitleSpaceDown);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1133 */         byte b = 0;
/* 1134 */         while (float_1.getWidth() > (f2 - f1) && b < 5) {
/*      */           
/* 1136 */           font = font.deriveFont(font.getSize2D() * 0.9F);
/*      */           
/* 1138 */           this.m_graphics.setFont(font);
/*      */           
/* 1140 */           fontMetrics = this.m_graphics.getFontMetrics();
/* 1141 */           i = fontMetrics.getAscent();
/* 1142 */           float_1 = getStringBoundsWithTabCharacters(fontMetrics, paramString, this.m_graphics);
/*      */           
/* 1144 */           b++;
/*      */         } 
/*      */       } 
/*      */       
/* 1148 */       boolean bool = paramString.equals(this.m_props.getProperty("toc.text"));
/*      */       
/* 1150 */       if (bool) {
/*      */         
/* 1152 */         int j = (int)(((f2 - f1) - float_1.getWidth()) / 2.0D + f1);
/* 1153 */         if (!str.startsWith("%c"))
/* 1154 */           j = (int)f1; 
/* 1155 */         if (!this.m_bPrintNothing) {
/* 1156 */           drawStringWithTabCharacters(this.m_graphics, fontMetrics, paramString, j, (int)this.m_yLoc + i);
/*      */         }
/*      */       } else {
/*      */         
/* 1160 */         FooterParser footerParser = new FooterParser(str);
/* 1161 */         footerParser.setSong(this.m_CurrentSong.m_SongFile);
/* 1162 */         footerParser.setSongTitle(paramString);
/*      */         
/* 1164 */         if (!this.m_bPrintNothing) {
/* 1165 */           drawStringWithTabCharacters(this.m_graphics, fontMetrics, footerParser.getLeftString(), (int)f1, (int)this.m_yLoc + i);
/*      */         }
/* 1167 */         paramString = footerParser.getRightString();
/* 1168 */         float_1 = getStringBoundsWithTabCharacters(fontMetrics, paramString, this.m_graphics);
/* 1169 */         int j = (int)(f2 - float_1.getWidth());
/* 1170 */         if (!this.m_bPrintNothing) {
/* 1171 */           drawStringWithTabCharacters(this.m_graphics, fontMetrics, paramString, j, (int)this.m_yLoc + i);
/*      */         }
/* 1173 */         paramString = footerParser.getCenterString();
/* 1174 */         if (bool2) {
/*      */           
/* 1176 */           int k = bool1 ? this.m_fontSubtitleSpaceDown : this.m_fontTitleSpaceDown;
/* 1177 */           float_2 = new Rectangle2D.Float(f1, this.m_yLoc + i, f2 - f1, this.m_ptSongMax.y);
/* 1178 */           this.m_yLoc = drawWrappingFooter(this.m_bPrintNothing, stringBuffer, arrayList, 2, this.m_graphics, fontMetrics, float_2, k);
/* 1179 */           this.m_yLoc -= (k + i);
/*      */         }
/* 1181 */         else if (!this.m_bPrintNothing) {
/*      */           
/* 1183 */           float_1 = getStringBoundsWithTabCharacters(fontMetrics, paramString, this.m_graphics);
/* 1184 */           j = (int)(((f2 - f1) - float_1.getWidth()) / 2.0D + f1);
/* 1185 */           drawStringWithTabCharacters(this.m_graphics, fontMetrics, paramString, j, (int)this.m_yLoc + i);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1190 */       this.m_yLoc += this.m_fontTitleSpaceDown * f3;
/*      */       
/* 1192 */       if (paramBoolean && !this.m_bInTOC && 0 != paramString.length())
/*      */       {
/* 1194 */         if (bool1 || null == this.m_CurrentSong.m_SongFile.getSubtitle()) {
/*      */           
/* 1196 */           if (!this.m_bPrintNothing) {
/*      */ 
/*      */             
/* 1199 */             int j = (int)(this.m_yLoc - this.m_fontTitleSpaceDown * f3);
/*      */             
/* 1201 */             if (bool1) {
/* 1202 */               j = (int)(j + this.m_fontSubtitleSpaceDown * 1.25D);
/*      */             } else {
/* 1204 */               j = (int)(j + this.m_fontTitleSpaceDown * 1.15D);
/*      */             } 
/* 1206 */             this.m_graphics.drawLine((int)f1, j, (int)f2, j);
/*      */           } 
/*      */           
/* 1209 */           this.m_yLoc = (float)(this.m_yLoc + this.m_fontTitleSpaceDown * 0.25D);
/*      */         } 
/*      */       }
/*      */       
/* 1213 */       return 2;
/*      */     }
/* 1215 */     catch (Exception exception) {
/*      */       
/* 1217 */       System.err.println("exception: drawTitle(): " + exception.toString());
/* 1218 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float drawWrappingText(String paramString, Font paramFont, Color paramColor, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) throws Exception {
/* 1226 */     float f = this.m_yLoc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1233 */       this.m_graphics.setFont(paramFont);
/* 1234 */       this.m_graphics.setColor(paramColor);
/*      */       
/* 1236 */       FontMetrics fontMetrics = this.m_graphics.getFontMetrics();
/* 1237 */       Rectangle2D.Float float_ = getStringBoundsWithTabCharacters(fontMetrics, paramString, this.m_graphics);
/*      */ 
/*      */       
/* 1240 */       if (!paramBoolean && (float_.getWidth() + this.m_ptSongMin.x <= this.m_ptSongMax.x || this.m_bInTab)) {
/* 1241 */         return this.m_yLoc + paramInt2 * paramFloat;
/*      */       }
/*      */ 
/*      */       
/* 1245 */       byte b1 = 5;
/* 1246 */       char[][] arrayOfChar = new char[b1][];
/* 1247 */       byte b2 = 0;
/* 1248 */       arrayOfChar[b2] = paramString.toCharArray();
/* 1249 */       int i = (arrayOfChar[b2]).length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1256 */       while (!this.m_bInTab && float_.getWidth() + this.m_ptSongMin.x > this.m_ptSongMax.x) {
/*      */         
/* 1258 */         int j = i - 1;
/* 1259 */         char c = arrayOfChar[b2][j];
/* 1260 */         while (float_.getWidth() + this.m_ptSongMin.x > this.m_ptSongMax.x) {
/*      */           
/* 1262 */           j--;
/* 1263 */           arrayOfChar[b2][j + 1] = c;
/* 1264 */           for (; 0 != j && !Character.isWhitespace(arrayOfChar[b2][j]); j--);
/* 1265 */           if (0 == j)
/*      */             break; 
/* 1267 */           c = arrayOfChar[b2][j];
/* 1268 */           float_ = getStringBoundsWithTabCharacters(fontMetrics, arrayOfChar[b2], 0, j, this.m_graphics);
/*      */         } 
/*      */ 
/*      */         
/* 1272 */         if (paramBoolean && !this.m_bPrintNothing && 0 != (arrayOfChar[b2]).length) {
/*      */           
/* 1274 */           int k = (int)this.m_ptSongMin.x;
/* 1275 */           if (0 != b2) {
/* 1276 */             k = (int)(this.m_ptSongMax.x - float_.getWidth());
/*      */           }
/* 1278 */           drawStringWithTabCharacters(this.m_graphics, fontMetrics, arrayOfChar[b2], 0, j, k, (int)(f + paramInt2 * paramFloat - (paramInt2 - paramInt1)));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1283 */         f += paramInt2 * paramFloat;
/*      */         
/* 1285 */         if (b2 + 1 == b1 || 0 == j) {
/*      */           break;
/*      */         }
/*      */         
/* 1289 */         arrayOfChar[b2 + 1] = new char[(arrayOfChar[b2]).length - j - 1];
/* 1290 */         System.arraycopy(arrayOfChar[b2], j + 1, arrayOfChar[b2 + 1], 0, (arrayOfChar[b2]).length - j - 1);
/* 1291 */         i -= j + 1;
/*      */         
/* 1293 */         b2++;
/*      */         
/* 1295 */         float_ = getStringBoundsWithTabCharacters(fontMetrics, arrayOfChar[b2], 0, (arrayOfChar[b2]).length, this.m_graphics);
/*      */       } 
/*      */ 
/*      */       
/* 1299 */       if (paramBoolean && !this.m_bPrintNothing && 0 != (arrayOfChar[b2]).length) {
/*      */         
/* 1301 */         int j = (int)this.m_ptSongMin.x;
/* 1302 */         if (0 != b2) {
/* 1303 */           j = (int)(this.m_ptSongMax.x - float_.getWidth());
/*      */         }
/* 1305 */         drawStringWithTabCharacters(this.m_graphics, fontMetrics, arrayOfChar[b2], 0, (arrayOfChar[b2]).length, j, (int)(f + paramInt2 * paramFloat - (paramInt2 - paramInt1)));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1310 */       f += paramInt2 * paramFloat;
/*      */     }
/* 1312 */     catch (Exception exception) {
/*      */       
/* 1314 */       System.err.println("exception: drawWrappingText(): " + exception.toString());
/* 1315 */       throw exception;
/*      */     } 
/*      */     
/* 1318 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int drawWrappingTextAndChords(String paramString, Font paramFont, Color paramColor, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) throws Exception {
/* 1328 */     float f1 = Float.parseFloat(this.m_props.getProperty("print.spacing.chord"));
/*      */ 
/*      */     
/* 1331 */     float f2 = this.m_yLoc + f1 * this.m_fontChordSpaceDown;
/* 1332 */     if (paramBoolean) {
/* 1333 */       f2 += paramInt2;
/*      */     }
/* 1335 */     if (f2 > this.m_ptSongMax.y) {
/*      */       
/* 1337 */       int i2 = pageOverflow(false);
/* 1338 */       if (2 != i2) {
/*      */         
/* 1340 */         this.m_qChordPlacement.clear();
/* 1341 */         this.m_bChordNewLine = false;
/* 1342 */         return i2;
/*      */       } 
/*      */     } 
/*      */     
/* 1346 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/* 1347 */     int i = Integer.parseInt(this.m_props.getProperty("print.spacing.chord.between"));
/*      */ 
/*      */     
/* 1350 */     if (0 == stringBuffer.length() && 'y' == this.m_props.getProperty("print.wrap.chords.alone").charAt(0)) {
/*      */       
/* 1352 */       paramBoolean = true;
/*      */       
/* 1354 */       ListIterator<ChordPlacement> listIterator1 = this.m_qChordPlacement.listIterator();
/* 1355 */       listIterator1.next();
/*      */       
/* 1357 */       while (listIterator1.hasNext()) {
/*      */         
/* 1359 */         ChordPlacement chordPlacement = listIterator1.next();
/*      */         
/* 1361 */         if (0 == chordPlacement.m_strLyric.length()) {
/*      */           
/* 1363 */           stringBuffer.append("  ");
/* 1364 */           chordPlacement.m_strLyric = stringBuffer.toString();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1369 */       if (0 == stringBuffer.length()) {
/*      */         
/* 1371 */         ChordPlacement chordPlacement = (ChordPlacement) this.m_qChordPlacement.lastElement();
/* 1372 */         stringBuffer.append(chordPlacement.m_strLyric);
/* 1373 */         stringBuffer.append("  ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1380 */     ListIterator<ChordPlacement> listIterator = this.m_qChordPlacement.listIterator(this.m_qChordPlacement.size());
/*      */ 
/*      */     
/* 1383 */     ChordPlacement chordPlacement1 = listIterator.previous();
/* 1384 */     ChordPlacement chordPlacement2 = chordPlacement1;
/*      */     
/* 1386 */     this.m_graphics.setFont(paramFont);
/* 1387 */     this.m_graphics.setColor(paramColor);
/* 1388 */     FontMetrics fontMetrics1 = this.m_graphics.getFontMetrics();
/*      */     
/* 1390 */     chordPlacement1.m_nLyricLen = chordPlacement1.m_strLyric.length();
/* 1391 */     Rectangle2D.Float float_ = getStringBoundsWithTabCharacters(fontMetrics1, chordPlacement1.m_strLyric, this.m_graphics);
/* 1392 */     int j = (int)(float_.getWidth() + 0.5D);
/* 1393 */     int k = (int)(float_.getHeight() + 0.5D);
/* 1394 */     chordPlacement1.m_sizeLyric.setSize((int)(float_.getWidth() + 0.5D), (int)(float_.getHeight() + 0.5D));
/* 1395 */     chordPlacement1.m_sizeLyric.setSize(j, k);
/*      */     
/* 1397 */     while (listIterator.hasPrevious()) {
/*      */       
/* 1399 */       chordPlacement1 = listIterator.previous();
/*      */       try{
	

/* 1401 */       chordPlacement1.m_nLyricLen = chordPlacement1.m_strLyric.length();
/* 1402 */       if (0 != chordPlacement2.m_strLyric.length() && chordPlacement2.m_strLyric.length()>= chordPlacement1.m_nLyricLen)
/* 1403 */         chordPlacement2.m_strSubLyric = chordPlacement2.m_strLyric.substring(chordPlacement1.m_nLyricLen); 
/* 1404 */       chordPlacement2.m_nSubLyricLen = chordPlacement2.m_strSubLyric.length();
				}catch(Exception e){
					e.printStackTrace();///xixixi
				}
/*      */       
/* 1406 */       if (0 != chordPlacement1.m_nLyricLen) {
/*      */         
/* 1408 */         float_ = getStringBoundsWithTabCharacters(fontMetrics1, chordPlacement1.m_strLyric, this.m_graphics);
/* 1409 */         chordPlacement1.m_sizeLyric.setSize((int)(float_.getWidth() + 0.5D), (int)(float_.getHeight() + 0.5D));
/*      */       } 
/* 1411 */       chordPlacement2.m_nSubLyricWidth = (float)(chordPlacement2.m_sizeLyric.getWidth() - chordPlacement1.m_sizeLyric.getWidth());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1416 */       chordPlacement2 = chordPlacement1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1427 */     listIterator = this.m_qChordPlacement.listIterator();
/*      */ 
/*      */     
/* 1430 */     chordPlacement1 = listIterator.next();
/* 1431 */     chordPlacement2 = chordPlacement1;
/*      */     
/* 1433 */     this.m_graphics.setFont(this.m_fontChord);
/* 1434 */     this.m_graphics.setColor(this.m_colorChord);
/* 1435 */     fontMetrics1 = this.m_graphics.getFontMetrics();
/*      */     
/* 1437 */     Rectangle2D rectangle2D = fontMetrics1.getStringBounds(this.m_bDoReMi ? chordPlacement1.m_chord.getDoReMiName() : chordPlacement1.m_chord.getName(), this.m_graphics);
/* 1438 */     chordPlacement1.m_sizeChord.setSize((int)(rectangle2D.getWidth() + 0.5D), (int)(rectangle2D.getHeight() + 0.5D));
/*      */     
/* 1440 */     while (listIterator.hasNext()) {
/*      */       
/* 1442 */       chordPlacement1 = listIterator.next();
/*      */       
/* 1444 */       rectangle2D = fontMetrics1.getStringBounds(this.m_bDoReMi ? chordPlacement1.m_chord.getDoReMiName() : chordPlacement1.m_chord.getName(), this.m_graphics);
/* 1445 */       chordPlacement1.m_sizeChord.setSize((int)(rectangle2D.getWidth() + 0.5D), (int)(rectangle2D.getHeight() + 0.5D));
/*      */       
/* 1447 */       if ((chordPlacement2.m_sizeChord.width + i) > chordPlacement1.m_nSubLyricWidth) {
/* 1448 */         chordPlacement2.m_bOverlapsNext = true;
/*      */       }
/* 1450 */       chordPlacement2 = chordPlacement1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1457 */     listIterator = this.m_qChordPlacement.listIterator();
/*      */ 
/*      */     
/* 1460 */     chordPlacement1 = listIterator.next();
/* 1461 */     chordPlacement2 = chordPlacement1;
/*      */     
/* 1463 */     String str = "   ";
/* 1464 */     int b = 3;
/*      */     
/* 1466 */     this.m_graphics.setFont(paramFont);
/* 1467 */     this.m_graphics.setColor(paramColor);
/* 1468 */     FontMetrics fontMetrics2 = this.m_graphics.getFontMetrics();
/*      */     
/* 1470 */     double d1 = fontMetrics2.getStringBounds(str, this.m_graphics).getWidth();
/* 1471 */     double d2 = fontMetrics2.getStringBounds(" ", this.m_graphics).getWidth();
/* 1472 */     double d3 = fontMetrics2.getStringBounds("-", this.m_graphics).getWidth();
/*      */     
/* 1474 */     int m = chordPlacement1.m_nLyricLen;
/*      */ 
/*      */     
/* 1477 */     int n = chordPlacement1.m_sizeLyric.width;
/*      */     
/* 1479 */     while (listIterator.hasNext()) {
/*      */       
/* 1481 */       chordPlacement1 = listIterator.next();
/*      */       
/* 1483 */       chordPlacement2.m_xDraw = n;
/*      */       
/* 1485 */       chordPlacement2.m_nLyricLen += chordPlacement1.m_nSubLyricLen;
/*      */       
/* 1487 */       m += chordPlacement1.m_nSubLyricLen;
/*      */       
/* 1489 */       if (chordPlacement2.m_bOverlapsNext) {
/*      */         
/* 1491 */         boolean bool1 = ('y' == this.m_props.getProperty("print.chars.hyphens").charAt(0)) ? true : false;
/* 1492 */         if (bool1) {
/*      */           
/* 1494 */           bool1 = (0 != chordPlacement1.m_nSubLyricLen) ? true : false;
/* 1495 */           if (bool1) {
/*      */             
/* 1497 */             bool1 = !Character.isWhitespace(chordPlacement1.m_strSubLyric.charAt(chordPlacement1.m_nSubLyricLen - 1)) ? true : false;
/* 1498 */             if (bool1)
/*      */             {
/*      */               
/* 1501 */               if (listIterator.hasNext()) {
/*      */                 
/* 1503 */                 ChordPlacement chordPlacement = (ChordPlacement) this.m_qChordPlacement.get(listIterator.nextIndex());
/* 1504 */                 bool1 = (0 != chordPlacement.m_nSubLyricLen && !Character.isWhitespace(chordPlacement.m_strSubLyric.charAt(0))) ? true : false;
/*      */               } else {
/*      */                 
/* 1507 */                 bool1 = (chordPlacement1.m_nLyricLen < stringBuffer.length() && !Character.isWhitespace(stringBuffer.charAt(chordPlacement1.m_nLyricLen))) ? true : false;
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */           }
/* 1513 */           else if (0 != chordPlacement1.m_nLyricLen && chordPlacement1.m_nLyricLen < stringBuffer.length()) {
/* 1514 */             bool1 = !Character.isWhitespace(stringBuffer.charAt(chordPlacement1.m_nLyricLen - 1)) ? true : false;
/*      */           } 
/*      */         } 
/*      */         
/* 1518 */         int b1 = 0;
/*      */         
/*      */         do {
/* 1521 */           if (paramBoolean)
/* 1522 */             chordPlacement1.m_strSubLyric += str; 
/* 1523 */           chordPlacement1.m_nSubLyricWidth = (float)(chordPlacement1.m_nSubLyricWidth + d1);
/* 1524 */           chordPlacement1.m_nSubLyricLen += b;
/* 1525 */           chordPlacement1.m_nLyricLen += b;
/*      */           
/* 1527 */           b1++;
/*      */           
/* 1529 */           if (paramBoolean && m < stringBuffer.length())
/* 1530 */             stringBuffer.insert(m, str); 
/* 1531 */           m += b;
/*      */         
/*      */         }
/* 1534 */         while (chordPlacement2.m_sizeChord.getWidth() + i > chordPlacement1.m_nSubLyricWidth);
/*      */         
/* 1536 */         if (bool1) {
/*      */           
/* 1538 */           chordPlacement1.m_nSubLyricWidth = (float)(chordPlacement1.m_nSubLyricWidth - d2);
/* 1539 */           chordPlacement1.m_nSubLyricWidth = (float)(chordPlacement1.m_nSubLyricWidth + d3);
				try{
					stringBuffer.append(" ");
					stringBuffer.setCharAt(stringBuffer.length()-1, '-');	//xixi
				}catch(Exception e){
					e.printStackTrace();
				}
/* 1540 */           
/*      */         } 
/*      */       } 
/*      */       
/* 1544 */       n = (int)(n + chordPlacement1.m_nSubLyricWidth);
/*      */       
/* 1546 */       chordPlacement2 = chordPlacement1;
/*      */     } 
/*      */     
/* 1549 */     chordPlacement2.m_xDraw = n;
/*      */ 
/*      */ 
/*      */     
/* 1553 */     float f3 = (float)getStringBoundsWithTabCharacters(fontMetrics2, stringBuffer.toString(), this.m_graphics).getWidth();
/* 1554 */     int i1 = (int)((f3 / (this.m_ptSongMax.x - this.m_ptSongMin.x)) + 1.0D);
/*      */     
/* 1556 */     if (i1 > 1)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1561 */       if (this.m_yLoc + (f1 * this.m_fontChordSpaceDown + this.m_fontNormalSpaceDown) * i1 > this.m_ptSongMax.y) {
/*      */         
/* 1563 */         int i2 = pageOverflow(false);
/* 1564 */         if (2 != i2) {
/*      */           
/* 1566 */           this.m_qChordPlacement.clear();
/* 1567 */           this.m_bChordNewLine = false;
/* 1568 */           return i2;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1574 */     boolean bool = true;
/* 1575 */     while (i1 > 0) {
/*      */       
/* 1577 */       m = stringBuffer.length();
/*      */ 
/*      */       
/* 1580 */       while (f3 > this.m_ptSongMax.x - this.m_ptSongMin.x) {
/*      */         
/* 1582 */         m--;
/* 1583 */         while (!Character.isWhitespace(stringBuffer.charAt(m)))
/* 1584 */           m--; 
/* 1585 */         f3 = (float)getStringBoundsWithTabCharacters(fontMetrics2, stringBuffer.substring(0, m), this.m_graphics).getWidth();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1590 */       this.m_graphics.setFont(this.m_fontChord);
/* 1591 */       this.m_graphics.setColor(this.m_colorChord);
/* 1592 */       fontMetrics2 = this.m_graphics.getFontMetrics();
/*      */       
/* 1594 */       listIterator = this.m_qChordPlacement.listIterator();
/* 1595 */       while (listIterator.hasNext()) {
/*      */         
/* 1597 */         chordPlacement1 = listIterator.next();
/*      */         
/* 1599 */         if (m <= chordPlacement1.m_nLyricLen && i1 != 1) {
/*      */           break;
/*      */         }
/* 1602 */         if (!this.m_bPrintNothing) {
/*      */           
/* 1604 */           n = chordPlacement1.m_xDraw + (int)this.m_ptSongMin.x;
/* 1605 */           if (!bool) {
/* 1606 */             n += (int)(this.m_ptSongMax.x - this.m_ptSongMin.x - f3);
/*      */           }
/* 1608 */           drawStringWithTabCharacters(this.m_graphics, fontMetrics2, this.m_bDoReMi ? chordPlacement1.m_chord
/* 1609 */               .getDoReMiName() : chordPlacement1.m_chord.getName(), n, (int)(this.m_yLoc + this.m_fontChordSpaceDown * f1 - (this.m_fontChordSpaceDown - this.m_fontChordAscent)));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1614 */         chordPlacement1 = null;
/*      */       } 
/*      */       
/* 1617 */       if (!this.m_qChordPlacement.isEmpty()) {
/* 1618 */         this.m_yLoc += this.m_fontChordSpaceDown * f1;
/*      */       }
/*      */       
/* 1621 */       if (!this.m_bPrintNothing) {
/*      */         
/* 1623 */         this.m_graphics.setFont(paramFont);
/* 1624 */         this.m_graphics.setColor(paramColor);
/* 1625 */         fontMetrics2 = this.m_graphics.getFontMetrics();
/*      */         
/* 1627 */         if (bool) {
/* 1628 */           n = (int)this.m_ptSongMin.x;
/*      */         } else {
/* 1630 */           n = (int)(this.m_ptSongMax.x - f3);
/*      */         } 
/* 1632 */         drawStringWithTabCharacters(this.m_graphics, fontMetrics2, stringBuffer
/* 1633 */             .substring(0, m), n, (int)(this.m_yLoc + paramInt2 * paramFloat - (paramInt2 - paramInt1)));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1639 */       this.m_yLoc += paramInt2 * paramFloat;
/*      */       
/* 1641 */       i1--;
/*      */       
/* 1643 */       if (0 != i1) {
/*      */ 
/*      */         
/* 1646 */         bool = false;
/*      */         
/* 1648 */         if (null == chordPlacement1) {
/*      */           
/* 1650 */           this.m_qChordPlacement.clear();
/*      */         }
/*      */         else {
/*      */           
/* 1654 */           ListIterator<ChordPlacement> listIterator1 = this.m_qChordPlacement.listIterator();
/* 1655 */           while (listIterator1.next() != chordPlacement1) {
/*      */             
/* 1657 */             listIterator1.remove();
/* 1658 */             listIterator1 = this.m_qChordPlacement.listIterator();
/*      */           } 
/* 1660 */           listIterator = this.m_qChordPlacement.listIterator();
/*      */           
/* 1662 */           while (listIterator.hasNext()) {
/*      */             
/* 1664 */             chordPlacement1 = listIterator.next();
/*      */             
/* 1666 */             chordPlacement1.m_nLyricLen -= m;
/* 1667 */             chordPlacement1.m_xDraw = (int)(chordPlacement1.m_xDraw - f3);
/*      */           } 
/*      */         } 
/*      */         
/* 1671 */         stringBuffer.delete(0, m);
/* 1672 */         f3 = (float)getStringBoundsWithTabCharacters(fontMetrics2, stringBuffer.toString(), this.m_graphics).getWidth();
/*      */         
/* 1674 */         printChordSpaceAbove();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1681 */     this.m_qChordPlacement.clear();
/*      */     
/* 1683 */     this.m_bChordNewLine = false;
/*      */     
/* 1685 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int drawNormalText(String paramString, Font paramFont, Color paramColor, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean) {
/* 1692 */     int i = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1698 */       if (!this.m_qChordPlacement.isEmpty()) {
/*      */         
/* 1700 */         if (this.m_bInChorus && this.m_bChorus1stLine) {
/* 1701 */           this.m_bChorus1stLineHasChords = true;
/*      */         }
/*      */         try{
	/* 1704 */         i = drawWrappingTextAndChords(paramString, paramFont, paramColor, paramInt1, paramInt2, paramFloat, paramBoolean);
				   }catch(Exception e){
						e.printStackTrace();
				   }

/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1710 */         float f = drawWrappingText(paramString, paramFont, paramColor, paramInt1, paramInt2, paramFloat, false);
/*      */ 
/*      */         
/* 1713 */         if (f >= this.m_ptSongMax.y) {
/* 1714 */           i = pageOverflow(false);
/*      */         }
/* 1716 */         if (2 == i)
/*      */         {
/* 1718 */           this.m_yLoc = drawWrappingText(paramString, paramFont, paramColor, paramInt1, paramInt2, paramFloat, true);
/*      */         }
/*      */       } 
/* 1721 */       this.m_bChorus1stLine = false;
/*      */     }
/* 1723 */     catch (Exception exception) {
/*      */       
/* 1725 */       System.err.println("exception: drawNormalText(" + paramString + "): " + exception.toString());
/* 1726 */       i = 0;
/*      */     } 
/*      */     
/* 1729 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int pageOverflow(boolean paramBoolean) {
/* 1735 */     if (SG02App.isDebug) {
/* 1736 */       System.err.println("pageOverflow: " + String.valueOf(paramBoolean));
/*      */     }
/* 1738 */     int i = 2;
/*      */     
/* 1740 */     boolean bool1 = this.m_bInChorus;
/* 1741 */     boolean bool2 = this.m_bInTab;
/*      */     
/* 1743 */     if (this.m_bPageIsOpen) {
/*      */       
/* 1745 */       if (!this.m_bInTOC) {
/* 1746 */         drawFooterOnCurrentPage();
/*      */       }
/* 1748 */       this.m_nSongOfPage++;
/*      */ 
/*      */ 
/*      */       
/* 1752 */       if (bool1 && !this.m_bChorus1stLine) {
/* 1753 */         i = markEndOfChorus();
/*      */       }
/*      */     } else {
/*      */       
/* 1757 */       this.m_nSongOfPage = this.m_nSongsPerPage;
/*      */     } 
/*      */     
/* 1760 */     boolean bool = (this.m_nSongOfPage == this.m_nSongsPerPage || this.m_nSongOfPage * 10 == this.m_nSongsPerPage) ? true : false;
/*      */ 
/*      */     
/* 1763 */     if (this.m_bPaginating && !paramBoolean && null != this.m_CurrentSong) {
/*      */       
/* 1765 */       if (0 == this.m_CurrentSong.m_nPageCount) {
/* 1766 */         this.m_CurrentSong.m_nPageCount = 1;
/*      */       }
/* 1768 */       this.m_CurrentSong.m_nPageCount++;
/*      */     } 
/*      */     
/* 1771 */     if (bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1776 */       if (!this.m_bInTOC && ((2 != this.m_nSongsPerPage && 4 != this.m_nSongsPerPage) || 'y' == this.m_props
/*      */         
/* 1778 */         .getProperty("print.footer.physical.only").charAt(0))) {
/* 1779 */         drawFooterOnCurrentPage();
/*      */       }
/* 1781 */       this.m_nSongOfPage = 0;
/* 1782 */       endPage();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1819 */       startPage();
/*      */     } 
/*      */     
/* 1822 */     this.m_bNewColumn = true;
/*      */     
/* 1824 */     startSong();
/*      */     
/* 1826 */     if (!paramBoolean) {
/*      */       
/* 1828 */       if (this.m_bCenterTitleOnPage)
/*      */       {
/* 1830 */         if (!bool) {
/*      */ 
/*      */           
/* 1833 */           drawTitle("", this.m_fontTitle, this.m_colorTitle, false);
/* 1834 */           if (null != this.m_CurrentSong.m_SongFile.getSubtitle()) {
/* 1835 */             printSubtitle("");
/*      */           }
/*      */         } 
/*      */       }
/* 1839 */       if (!this.m_bOnlyTOC && (!this.m_bCenterTitleOnPage || bool) && 'n' != this.m_props
/*      */         
/* 1841 */         .getProperty("print.overflow.title").charAt(0))
/*      */       {
/*      */ 
/*      */         
/* 1845 */         if ('l' == this.m_props.getProperty("print.overflow.title").charAt(0)) {
/*      */           
/* 1847 */           boolean bool3 = (!this.m_bInTOC && 'y' == this.m_props.getProperty("print.format.title.line").charAt(0)) ? true : false;
/* 1848 */           drawTitle(this.m_CurrentSong.m_SongFile.getTitle(), this.m_fontTitle, this.m_colorTitle, bool3);
/* 1849 */           drawTitle(this.m_CurrentSong.m_SongFile.getSubtitle(), this.m_fontSubtitle, this.m_colorSubtitle, bool3);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1854 */           if (!this.m_bPrintNothing) {
/*      */             
/* 1856 */             this.m_graphics.setFont(this.m_fontComment);
/* 1857 */             this.m_graphics.setColor(this.m_colorTitle);
/*      */             
/* 1859 */             FontMetrics fontMetrics = this.m_graphics.getFontMetrics();
/* 1860 */             int j = fontMetrics.getAscent();
/* 1861 */             Rectangle2D.Float float_ = getStringBoundsWithTabCharacters(fontMetrics, this.m_currentSongTitle, this.m_graphics);
/*      */             
/* 1863 */             drawStringWithTabCharacters(this.m_graphics, fontMetrics, this.m_currentSongTitle, (int)(this.m_ptSongMax.x - float_.getWidth()), (int)this.m_yLoc + j);
/*      */           } 
/*      */           
/* 1866 */           this.m_yLoc += (this.m_fontCommentSpaceDown * 2);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1871 */     if (bool1)
/* 1872 */       markStartOfChorus(); 
/* 1873 */     if (bool2) {
/* 1874 */       markStartOfTab();
/*      */     }
/* 1876 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   

/*      */ 
/*      */   
/*      */   protected class ChordPlacement
/*      */   {
/*      */     public Chord m_chord;
/*      */     
/*      */     public String m_strLyric;
/*      */     
/*      */     public String m_strSubLyric;
/*      */     
/*      */     public int m_nLyricLen;
/*      */     public int m_nSubLyricLen;
/*      */     public float m_nSubLyricWidth;
/*      */     public int m_xDraw;
/*      */     public Dimension m_sizeLyric;
/*      */     public Dimension m_sizeChord;
/*      */     public boolean m_bOverlapsNext;
/*      */     
/*      */     public ChordPlacement(Chord param1Chord, String param1String) {
/* 2043 */       this.m_chord = param1Chord;
/* 2044 */       this.m_strLyric = param1String;
/* 2045 */       this.m_strSubLyric = "";
/* 2046 */       this.m_nLyricLen = this.m_strLyric.length();
/* 2047 */       this.m_nSubLyricLen = 0;
/* 2048 */       this.m_nSubLyricWidth = 0.0F;
/* 2049 */       this.m_xDraw = 0;
/* 2050 */       this.m_sizeLyric = new Dimension(0, 0);
/* 2051 */       this.m_sizeChord = new Dimension(0, 0);
/* 2052 */       this.m_bOverlapsNext = false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int drawFooterOnCurrentPage() {
/* 2064 */     if (this.m_bFooterManuallyTurnedOff) {
/* 2065 */       return 2;
/*      */     }
/* 2067 */     if (!this.m_bPrintNothing)
/*      */     {
/* 2069 */       if (this.m_nLastFooterPage < this.m_nCurrentPage) {
/* 2070 */         this.m_nLastFooterPage = this.m_nCurrentPage;
/*      */       } else {
/* 2072 */         return 2;
/*      */       } 
/*      */     }
/* 2075 */     if (SG02App.isDebug) {
/* 2076 */       System.err.println("drawFooterOnCurrentPage: " + String.valueOf(this.m_nCurrentPage));
/*      */     }
/* 2078 */     float f1 = (float)this.m_pageFormat.getHeight();
/* 2079 */     float f2 = (float)this.m_pageFormat.getWidth();
/*      */     
/* 2081 */     if (f1 == 0.0F && f2 == 0.0F) {
/*      */       
/* 2083 */       f1 = (float)this.m_pageFormat.getImageableHeight();
/* 2084 */       f2 = (float)this.m_pageFormat.getImageableWidth();
/*      */     } 
/*      */     
/* 2087 */     Rectangle2D.Float float_ = new Rectangle2D.Float();
/*      */     
/* 2089 */     if ((2 != this.m_nSongsPerPage && 4 != this.m_nSongsPerPage) || 'y' == this.m_props
/* 2090 */       .getProperty("print.footer.physical.only").charAt(0)) {
/*      */       
/* 2092 */       float f3 = 72.0F;
/* 2093 */       float f4 = 72.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2098 */       float_.x = this.m_nMarginLeft * f3;
/* 2099 */       float_.width = f2 - this.m_nMarginRight * f3 - float_.x;
/* 2100 */       float_.y = f1 - this.m_nMarginBottom * f4;
/* 2101 */       float_.height = f1 - float_.y;
/*      */     }
/*      */     else {
/*      */       
/* 2105 */       float_.x = this.m_ptSongMin.x;
/* 2106 */       float_.width = this.m_ptSongMax.x - this.m_ptSongMin.x;
/* 2107 */       float_.y = this.m_ptSongMax.y;
/* 2108 */       float_.height = f1 - float_.y;
/*      */       
/* 2110 */       if (4 == this.m_nSongsPerPage && (0 == this.m_nSongOfPage || 1 == this.m_nSongOfPage))
/*      */       {
/* 2112 */         float_.height *= 2.0F;
/*      */       }
/*      */     } 
/*      */     
/* 2116 */     return drawFooter(float_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int drawFooter(Rectangle2D.Float paramFloat) {
/* 2122 */     if (this.m_bPrintNothing) {
/* 2123 */       return 2;
/*      */     }
/* 2125 */     if (SG02App.isDebug) {
/* 2126 */       this.m_graphics.drawRect((int)paramFloat.x, (int)paramFloat.y, (int)paramFloat.width, (int)paramFloat.height);
/*      */     }
/*      */ 
/*      */     
/* 2130 */     float f = paramFloat.y + this.m_fontCommentSpaceDown + this.m_fontCommentAscent;
/*      */     
/* 2132 */     if (f > paramFloat.y + paramFloat.height) {
/* 2133 */       f = paramFloat.y + paramFloat.height;
/*      */     }
/* 2135 */     if (paramFloat.y + paramFloat.height == f && 0.0D == this.m_pageFormat.getWidth()) {
/* 2136 */       f -= (this.m_fontCommentSpaceDown - this.m_fontCommentAscent);
/*      */     }
/* 2138 */     paramFloat.y = f;
/*      */     
/* 2140 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 2141 */     StringBuffer stringBuffer2 = new StringBuffer();
/* 2142 */     StringBuffer stringBuffer3 = new StringBuffer();
/* 2143 */     getFooterText(stringBuffer1, stringBuffer2, stringBuffer3);
/*      */ 
/*      */     
/* 2146 */     if (0 == stringBuffer1.length() && 0 == stringBuffer2.length() && stringBuffer3.length() == 0) {
/* 2147 */       return 2;
/*      */     }
/* 2149 */     this.m_graphics.setFont(this.m_fontFooter);
/* 2150 */     this.m_graphics.setColor(this.m_colorFooter);
/* 2151 */     FontMetrics fontMetrics = this.m_graphics.getFontMetrics();
/*      */     
/* 2153 */     Rectangle2D.Float float_1 = new Rectangle2D.Float();
/* 2154 */     Rectangle2D.Float float_2 = new Rectangle2D.Float();
/* 2155 */     Rectangle2D.Float float_3 = new Rectangle2D.Float();
/* 2156 */     float_1.setRect(paramFloat);
/* 2157 */     float_2.setRect(paramFloat);
/* 2158 */     float_3.setRect(paramFloat);
/*      */     
/* 2160 */     ArrayList arrayList1 = new ArrayList();
/* 2161 */     ArrayList arrayList2 = new ArrayList();
/* 2162 */     ArrayList arrayList3 = new ArrayList();
/*      */ 
/*      */     
/* 2165 */     if (0 == stringBuffer2.length() && stringBuffer3.length() == 0) {
/*      */       
/* 2167 */       drawWrappingFooter(false, stringBuffer1, arrayList1, 1, this.m_graphics, fontMetrics, float_1, this.m_fontFooterSpaceDown);
/* 2168 */       return 2;
/*      */     } 
/* 2170 */     if (0 == stringBuffer1.length() && stringBuffer3.length() == 0) {
/*      */       
/* 2172 */       drawWrappingFooter(false, stringBuffer2, arrayList2, 2, this.m_graphics, fontMetrics, float_2, this.m_fontFooterSpaceDown);
/* 2173 */       return 2;
/*      */     } 
/* 2175 */     if (0 == stringBuffer1.length() && 0 == stringBuffer2.length()) {
/*      */       
/* 2177 */       drawWrappingFooter(false, stringBuffer3, arrayList3, 3, this.m_graphics, fontMetrics, float_3, this.m_fontFooterSpaceDown);
/* 2178 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 2182 */     drawWrappingFooter(true, stringBuffer1, arrayList1, 1, this.m_graphics, fontMetrics, float_1, this.m_fontFooterSpaceDown);
/* 2183 */     drawWrappingFooter(true, stringBuffer2, arrayList2, 2, this.m_graphics, fontMetrics, float_2, this.m_fontFooterSpaceDown);
/* 2184 */     drawWrappingFooter(true, stringBuffer3, arrayList3, 3, this.m_graphics, fontMetrics, float_3, this.m_fontFooterSpaceDown);
/*      */     
/* 2186 */     Rectangle2D.Float float_4 = new Rectangle2D.Float();
/* 2187 */     Rectangle2D.Float float_5 = new Rectangle2D.Float();
/* 2188 */     Rectangle2D.Float float_6 = new Rectangle2D.Float();
/* 2189 */     Rectangle2D.intersect(float_1, float_2, float_4);
/* 2190 */     Rectangle2D.intersect(float_2, float_3, float_5);
/* 2191 */     Rectangle2D.intersect(float_1, float_3, float_6);
/*      */ 
/*      */     
/* 2194 */     if (float_4.width > 0.0F || float_5.width > 0.0F || float_6.width > 0.0F) {
/*      */ 
/*      */       
/* 2197 */       float f1 = float_4.width;
/* 2198 */       if (float_5.width > float_4.width) {
/* 2199 */         f1 = float_5.width;
/*      */       }
/* 2201 */       if (f1 > 0.0D) {
/*      */ 
/*      */         
/* 2204 */         f1 = (float)(f1 / 2.0D);
/*      */         
/* 2206 */         if (float_4.width > 0.0D) {
/* 2207 */           float_1.width -= f1;
/*      */         }
/* 2209 */         if (float_5.width > 0.0D) {
/*      */           
/* 2211 */           float_3.x += f1;
/* 2212 */           float_3.width -= f1;
/*      */         } 
/*      */         
/* 2215 */         float_2.x += f1;
/* 2216 */         float_2.width -= f1;
/* 2217 */         float_2.width -= f1;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2222 */         f1 = float_6.width / 2.0F;
/* 2223 */         float_1.width -= f1;
/* 2224 */         float_3.x += f1;
/* 2225 */         float_3.width -= f1;
/*      */       } 
/*      */       
/* 2228 */       arrayList1.clear();
/* 2229 */       arrayList2.clear();
/* 2230 */       arrayList3.clear();
/*      */       
/* 2232 */       drawWrappingFooter(false, stringBuffer1, arrayList1, 1, this.m_graphics, fontMetrics, float_1, this.m_fontFooterSpaceDown);
/* 2233 */       drawWrappingFooter(false, stringBuffer2, arrayList2, 2, this.m_graphics, fontMetrics, float_2, this.m_fontFooterSpaceDown);
/* 2234 */       drawWrappingFooter(false, stringBuffer3, arrayList3, 3, this.m_graphics, fontMetrics, float_3, this.m_fontFooterSpaceDown);
/*      */     }
/*      */     else {
/*      */       
/* 2238 */       drawWrappingFooter(false, stringBuffer1, arrayList1, 1, this.m_graphics, fontMetrics, float_1, this.m_fontFooterSpaceDown);
/* 2239 */       drawWrappingFooter(false, stringBuffer2, arrayList2, 2, this.m_graphics, fontMetrics, float_2, this.m_fontFooterSpaceDown);
/* 2240 */       drawWrappingFooter(false, stringBuffer3, arrayList3, 3, this.m_graphics, fontMetrics, float_3, this.m_fontFooterSpaceDown);
/*      */     } 
/*      */     
/* 2243 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void getFooterText(StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2, StringBuffer paramStringBuffer3) {
/* 2249 */     int i = this.m_nCurrentPage;
/* 2250 */     int j = this.m_nPageCount;
/*      */     
/* 2252 */     if ('y' != this.m_props.getProperty("print.footer.physical.only").charAt(0)) {
/*      */       
/* 2254 */       SongFileTOC songFileTOC = (SongFileTOC) this.m_qSongFiles.lastElement();
/*      */       
/* 2256 */       if (2 == this.m_nSongsPerPage) {
/*      */         
/* 2258 */         i = (this.m_nCurrentPage - 1) * 2 + this.m_nSongOfPage + 1;
/* 2259 */         j = (songFileTOC.m_nPageNumber - 1) * 2 + songFileTOC.m_nSongOfPage + songFileTOC.m_nPageCount;
/*      */       }
/* 2261 */       else if (4 == this.m_nSongsPerPage) {
/*      */         
/* 2263 */         i = (this.m_nCurrentPage - 1) * 4 + this.m_nSongOfPage + 1;
/* 2264 */         j = (songFileTOC.m_nPageNumber - 1) * 4 + songFileTOC.m_nSongOfPage + songFileTOC.m_nPageCount;
/*      */       } 
/*      */     } 
/*      */     
/* 2268 */     int k = this.m_CurrentSong.m_nPageCount;
/* 2269 */     int m = i - this.m_CurrentSong.m_nPageNumber + 1;
/* 2270 */     if (0 == k)
/* 2271 */       k = 1; 
/* 2272 */     if (0 == m) {
/* 2273 */       m = 1;
/*      */     }
/* 2275 */     FooterParser footerParser = this.m_FooterParser;
/*      */     
/* 2277 */     String str = this.m_CurrentSong.m_SongFile.getFooterFormatString();
/* 2278 */     if (null != str) {
/* 2279 */       footerParser = new FooterParser(str);
/*      */     }
/* 2281 */     footerParser.setSongbookCurrentPage(i);
/* 2282 */     footerParser.setSongbookTotalPages(j);
/* 2283 */     footerParser.setSongCurrentPage(m);
/* 2284 */     footerParser.setSongTotalPages(k);
/* 2285 */     footerParser.setSong(this.m_CurrentSong.m_SongFile);
/*      */     
/* 2287 */     paramStringBuffer1.append(footerParser.getLeftString());
/* 2288 */     paramStringBuffer2.append(footerParser.getCenterString());
/* 2289 */     paramStringBuffer3.append(footerParser.getRightString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   float drawWrappingFooter(boolean paramBoolean, StringBuffer paramStringBuffer, ArrayList<String> paramArrayList, int paramInt1, Graphics paramGraphics, FontMetrics paramFontMetrics, Rectangle2D.Float paramFloat, int paramInt2) {
/* 2299 */     if (!paramBoolean && SG02App.isDebug) {
/* 2300 */       this.m_graphics.drawRect((int)paramFloat.x, (int)paramFloat.y, (int)paramFloat.width, (int)paramFloat.height);
/*      */     }
/* 2302 */     float f = paramFloat.y;
/*      */     
/* 2304 */     if (paramArrayList.size() == 0) {
/*      */       
/* 2306 */       float f1 = 0.0F;
/*      */       
/* 2308 */       boolean bool = !paramBoolean ? true : false;
/*      */ 
/*      */       
/* 2311 */       String[] arrayOfString = paramStringBuffer.toString().split("\\n");
/*      */ 
/*      */       
/* 2314 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*      */         
/* 2316 */         Rectangle2D.Float float_ = getStringBoundsWithTabCharacters(paramFontMetrics, arrayOfString[b], paramGraphics);
/* 2317 */         float f2 = float_.width / paramFloat.width;
/* 2318 */         if (f2 > 1.0D) {
/*      */ 
/*      */           
/* 2321 */           int i = arrayOfString[b].length();
/* 2322 */           int j = i / (int)f2;
/*      */           
/* 2324 */           if (bool) {
/*      */             
/* 2326 */             j = (int)(i / f2);
/*      */           }
/*      */           else {
/*      */             
/* 2330 */             f2 = (float)(f2 + 1.0D);
/* 2331 */             j = i / (int)f2;
/*      */           } 
/*      */           
/* 2334 */           while (!Character.isWhitespace(arrayOfString[b].charAt(j)) && j < i) {
/* 2335 */             j++;
/*      */           }
/* 2337 */           String str = arrayOfString[b].substring(0, j);
/*      */           
/* 2339 */           float_ = getStringBoundsWithTabCharacters(paramFontMetrics, str, paramGraphics);
/*      */           
/* 2341 */           for (byte b1 = 0; b1 < 5 && float_.width > paramFloat.width; b1++) {
/*      */             
/* 2343 */             j--;
/* 2344 */             while (!Character.isWhitespace(arrayOfString[b].charAt(j)) && j > 0)
/* 2345 */               j--; 
/* 2346 */             str = arrayOfString[b].substring(0, j);
/* 2347 */             float_ = getStringBoundsWithTabCharacters(paramFontMetrics, str, paramGraphics);
/*      */           } 
/*      */           
/* 2350 */           if (float_.width > f1) {
/* 2351 */             f1 = float_.width;
/*      */           }
/* 2353 */           paramArrayList.add(str);
/*      */           
/* 2355 */           str = arrayOfString[b].substring(j + 1);
/* 2356 */           arrayOfString[b] = str;
/* 2357 */           b--;
/*      */         }
/*      */         else {
/*      */           
/* 2361 */           paramArrayList.add(arrayOfString[b]);
/* 2362 */           if (float_.width > f1) {
/* 2363 */             f1 = float_.width;
/*      */           }
/*      */         } 
/*      */       } 
/* 2367 */       switch (paramInt1) {
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 2372 */           paramFloat.x += (paramFloat.width - f1) / 2.0F;
/*      */           break;
/*      */         case 3:
/* 2375 */           paramFloat.x = paramFloat.x + paramFloat.width - f1;
/*      */           break;
/*      */       } 
/* 2378 */       paramFloat.width = f1;
/*      */     } 
/*      */ 
/*      */     
/* 2382 */     ListIterator<String> listIterator = paramArrayList.listIterator();
/*      */     
/* 2384 */     while (listIterator.hasNext()) {
/*      */       
/* 2386 */       int i = (int)paramFloat.x;
/* 2387 */       String str = listIterator.next();
/*      */       
/* 2389 */       switch (paramInt1) {
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 2394 */           paramFloat.x += (paramFloat.width - (getStringBoundsWithTabCharacters(paramFontMetrics, str, paramGraphics)).width) / 2.0F;
/*      */           break;
/*      */         case 3:
/* 2397 */           i = (int)(paramFloat.x + paramFloat.width - (getStringBoundsWithTabCharacters(paramFontMetrics, str, paramGraphics)).width);
/*      */           break;
/*      */       } 
/*      */       
/* 2401 */       if (!paramBoolean)
/* 2402 */         drawStringWithTabCharacters(paramGraphics, paramFontMetrics, str, (int)paramFloat.x, (int)f); 
/* 2403 */       f += paramInt2;
/*      */     } 
/*      */     
/* 2406 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle2D.Float getStringBoundsWithTabCharacters(FontMetrics paramFontMetrics, String paramString, Graphics paramGraphics) {
/* 2412 */     Rectangle2D.Float float_ = new Rectangle2D.Float();
/* 2413 */     float_.setRect(paramFontMetrics.getStringBounds(paramString, paramGraphics));
/*      */     
/* 2415 */     byte b = 0;
/* 2416 */     int i = 0;
/* 2417 */     while (-1 != (i = paramString.indexOf('\t', i))) {
/*      */       
/* 2419 */       b++;
/* 2420 */       i++;
/*      */     } 
/*      */ 
/*      */     
/* 2424 */     if (0 != b) {
/* 2425 */       float_.width = (float)(float_.width + (this.m_widthTabChar - paramFontMetrics.getStringBounds("\t", paramGraphics).getX()) * b);
/*      */     }
/* 2427 */     return float_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle2D.Float getStringBoundsWithTabCharacters(FontMetrics paramFontMetrics, char[] paramArrayOfchar, int paramInt1, int paramInt2, Graphics paramGraphics) {
/* 2433 */     Rectangle2D.Float float_ = new Rectangle2D.Float();
/* 2434 */     float_.setRect(paramFontMetrics.getStringBounds(paramArrayOfchar, paramInt1, paramInt2, paramGraphics));
/*      */     
/* 2436 */     byte b = 0;
/* 2437 */     for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
/*      */       
/* 2439 */       if (paramArrayOfchar[i] == '\t') {
/* 2440 */         b++;
/*      */       }
/*      */     } 
/*      */     
/* 2444 */     if (0 != b) {
/* 2445 */       float_.width = (float)(float_.width + (this.m_widthTabChar - paramFontMetrics.getStringBounds("\t", paramGraphics).getX()) * b);
/*      */     }
/* 2447 */     return float_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void drawStringWithTabCharacters(Graphics paramGraphics, FontMetrics paramFontMetrics, String paramString, int paramInt1, int paramInt2) {
/* 2453 */     char[] arrayOfChar = paramString.toCharArray();
/* 2454 */     drawStringWithTabCharacters(paramGraphics, paramFontMetrics, arrayOfChar, 0, arrayOfChar.length, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void drawStringWithTabCharacters(Graphics paramGraphics, FontMetrics paramFontMetrics, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2460 */     int i = paramInt1;
/* 2461 */     int j = paramInt1;
/* 2462 */     for (j = paramInt1; j < paramInt1 + paramInt2; j++) {
/*      */       
/* 2464 */       if (paramArrayOfchar[j] == '\t') {
/*      */         
/* 2466 */         paramGraphics.drawChars(paramArrayOfchar, i, j - i, paramInt3, paramInt4);
/* 2467 */         paramInt3 = (int)(paramInt3 + paramFontMetrics.getStringBounds(paramArrayOfchar, i, j, paramGraphics).getWidth() + this.m_widthTabChar);
/* 2468 */         i = j;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2473 */     j = paramInt1 + paramInt2;
/* 2474 */     if (i < j) {
/* 2475 */       paramGraphics.drawChars(paramArrayOfchar, i, j - i, paramInt3, paramInt4);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPageNumberForSong(int paramInt) {
/* 2481 */     SongFileTOC songFileTOC = (SongFileTOC) this.m_qSongFiles.elementAt(paramInt);
/* 2482 */     if (SG02App.isDebug)
/* 2483 */       System.err.println(getClass().getName() + " - page " + songFileTOC.m_nPageNumber + " - " + songFileTOC.m_SongFile); 
/* 2484 */     return songFileTOC.m_nPageNumber;
/*      */   }
/*      */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongPrinter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */