/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.Image;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.BufferStrategy;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Paper;
/*     */ import java.util.ListIterator;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class FullScreenView
/*     */   extends Frame
/*     */   implements KeyListener, MouseListener, MouseMotionListener
/*     */ {
/*     */   private GraphicsDevice m_device;
/*     */   private RenderThread m_renderThread;
/*     */   protected PageFormat m_pageFormat;
/*     */   protected int m_nArrowSize;
/*  38 */   static int ARROW_PIXEL_MARGIN = 6; protected Dimension m_size; private boolean m_bShowArrows; private boolean m_bCursorShown; private Cursor m_invisibleCursor;
/*  39 */   static float ARROW_SCREEN_PERCENT = 2.5F;
/*     */ 
/*     */ 
/*     */   
/*     */   public FullScreenView(GraphicsDevice paramGraphicsDevice) {
/*  44 */     super(paramGraphicsDevice.getDefaultConfiguration());
/*     */     
/*  46 */     this.m_device = paramGraphicsDevice;
/*     */     
/*  48 */     this.m_bShowArrows = ('y' == SG02App.props.getProperty("fullscreen.control.show").charAt(0));
/*     */ 
/*     */     
/*  51 */     int[] arrayOfInt = new int[256];
/*  52 */     Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, arrayOfInt, 0, 16));
/*  53 */     this.m_invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisiblecursor");
/*     */     
/*  55 */     setBackground(new Color(Integer.parseInt(SG02App.props.getProperty("fullscreen.color.background"), 16)));
/*     */     
/*  57 */     this.m_renderThread = new RenderThread();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSongFile(SongFile paramSongFile) {
/*  63 */     this.m_renderThread.addSongFile(paramSongFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showFullScreen(int paramInt) {
/*  70 */     boolean bool = true;
/*     */     
/*  72 */     if (SG02App.isMac) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/*  77 */         String str = System.getProperty("java.version");
/*  78 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "._", false);
/*  79 */         boolean bool1 = false;
/*     */         
/*  81 */         bool1 = (1 > Integer.parseInt(stringTokenizer.nextToken(), 10)) ? true : false;
/*  82 */         if (5 > Integer.parseInt(stringTokenizer.nextToken(), 10)) {
/*     */           
/*  84 */           bool1 = (bool1 || 2 > Integer.parseInt(stringTokenizer.nextToken(), 10)) ? true : false;
/*  85 */           bool1 = (bool1 || 5 > Integer.parseInt(stringTokenizer.nextToken(), 10)) ? true : false;
/*     */         } 
/*     */         
/*  88 */         if (bool1)
/*     */         {
/*  90 */           ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*  91 */           JOptionPane.showMessageDialog(null, resourceBundle
/*  92 */               .getString("Text.Error.FullScreen.OSXJavaVer"), resourceBundle
/*  93 */               .getString("Title.Dialog.Error"), 0);
/*     */           
/*  95 */           bool = false;
/*     */         }
/*     */       
/*  98 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (!this.m_device.isFullScreenSupported()) {
/*     */       
/* 106 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 107 */       JOptionPane.showMessageDialog(null, resourceBundle
/* 108 */           .getString("Text.Error.FullScreen.Unsupported"), resourceBundle
/* 109 */           .getString("Title.Dialog.Error"), 0);
/*     */       
/* 111 */       bool = false;
/*     */     } 
/*     */     
/* 114 */     if (bool) {
/*     */       
/* 116 */       setUndecorated(true);
/* 117 */       setLayout((LayoutManager)null);
/*     */       
/* 119 */       this.m_device.setFullScreenWindow(this);
/*     */       
/* 121 */       this.m_renderThread.start();
/* 122 */       this.m_renderThread.advanceToSong(paramInt);
/*     */ 
/*     */ 
/*     */       
/* 126 */       addKeyListener(this);
/* 127 */       addMouseListener(this);
/* 128 */       addMouseMotionListener(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/* 135 */     super.setVisible(paramBoolean);
/*     */     
/* 137 */     if (!paramBoolean) {
/*     */       
/* 139 */       if (null != this.m_renderThread) {
/* 140 */         this.m_renderThread.pleaseStop();
/*     */       }
/* 142 */       setCursor(Cursor.getDefaultCursor());
/* 143 */       this.m_device.setFullScreenWindow(null);
/*     */     } 
/*     */     
/* 146 */     if (null != this.m_renderThread) {
/* 147 */       this.m_renderThread.setDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyEvent paramKeyEvent) {
/* 155 */     switch (paramKeyEvent.getKeyCode()) {
/*     */       
/*     */       case 27:
/* 158 */         setVisible(false);
/* 159 */         paramKeyEvent.consume();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 10:
/*     */       case 32:
/*     */       case 34:
/*     */       case 39:
/*     */       case 40:
/* 168 */         this.m_renderThread.advance();
/* 169 */         paramKeyEvent.consume();
/*     */         break;
/*     */       
/*     */       case 36:
/* 173 */         this.m_renderThread.home();
/* 174 */         paramKeyEvent.consume();
/*     */         break;
/*     */       
/*     */       case 35:
/* 178 */         this.m_renderThread.end();
/* 179 */         paramKeyEvent.consume();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/*     */       case 33:
/*     */       case 37:
/*     */       case 38:
/* 187 */         this.m_renderThread.retreat();
/* 188 */         paramKeyEvent.consume();
/*     */         break;
/*     */       
/*     */       case 61:
/*     */       case 107:
/*     */       case 521:
/* 194 */         if ((paramKeyEvent.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0) {
/*     */           
/* 196 */           FontSettingsDialog.adjustFonts(SG02App.props, "fullscreen", 1);
/* 197 */           SG02App.writeProperties();
/* 198 */           this.m_renderThread.reset();
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 45:
/* 203 */         if ((paramKeyEvent.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0) {
/*     */           
/* 205 */           FontSettingsDialog.adjustFonts(SG02App.props, "fullscreen", -1);
/* 206 */           SG02App.writeProperties();
/* 207 */           this.m_renderThread.reset();
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyEvent paramKeyEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyEvent paramKeyEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {
/* 223 */     handleArrows(paramMouseEvent);
/*     */     
/* 225 */     if (!paramMouseEvent.isConsumed()) {
/* 226 */       handleButtons(paramMouseEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent) {
/* 249 */     Point point = paramMouseEvent.getPoint();
/*     */     
/* 251 */     if (point != null && this.m_size != null)
/*     */     {
/* 253 */       if (this.m_bShowArrows)
/*     */       {
/* 255 */         if (point.y >= this.m_size.height - ARROW_PIXEL_MARGIN - this.m_nArrowSize) {
/*     */           
/* 257 */           if (!this.m_bCursorShown)
/* 258 */             setCursor(Cursor.getDefaultCursor()); 
/* 259 */           this.m_bCursorShown = true;
/*     */         }
/*     */         else {
/*     */           
/* 263 */           if (this.m_bCursorShown)
/* 264 */             setCursor(this.m_invisibleCursor); 
/* 265 */           this.m_bCursorShown = false;
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 275 */     this.m_renderThread.setDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleArrows(MouseEvent paramMouseEvent) {
/* 281 */     if (this.m_bShowArrows) {
/*     */       
/* 283 */       Point point = paramMouseEvent.getPoint();
/* 284 */       int i = this.m_size.height - ARROW_PIXEL_MARGIN;
/* 285 */       int j = this.m_size.width - ARROW_PIXEL_MARGIN;
/*     */       
/* 287 */       if (point.y >= i - this.m_nArrowSize) {
/*     */ 
/*     */         
/* 290 */         if (point.x >= ARROW_PIXEL_MARGIN + this.m_nArrowSize * 4 && point.x < ARROW_PIXEL_MARGIN + this.m_nArrowSize * 5) {
/*     */           
/* 292 */           setVisible(false);
/* 293 */           paramMouseEvent.consume();
/*     */         } 
/*     */ 
/*     */         
/* 297 */         if (!paramMouseEvent.isConsumed()) {
/*     */           
/* 299 */           if (point.x < ARROW_PIXEL_MARGIN + this.m_nArrowSize / 2 + this.m_nArrowSize) {
/*     */             
/* 301 */             this.m_renderThread.home();
/* 302 */             paramMouseEvent.consume();
/*     */           } 
/*     */           
/* 305 */           if (point.x >= ARROW_PIXEL_MARGIN + this.m_nArrowSize * 2 && point.x < ARROW_PIXEL_MARGIN + this.m_nArrowSize * 3) {
/*     */             
/* 307 */             this.m_renderThread.retreat();
/* 308 */             paramMouseEvent.consume();
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 313 */         if (!paramMouseEvent.isConsumed() && point.x >= j - this.m_nArrowSize) {
/*     */           
/* 315 */           this.m_renderThread.advance();
/* 316 */           paramMouseEvent.consume();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleButtons(MouseEvent paramMouseEvent) {
/* 325 */     if ('y' == SG02App.props.getProperty("fullscreen.control.mouse").charAt(0)) {
/*     */       
/* 327 */       int i = this.m_size.height - ARROW_PIXEL_MARGIN;
/*     */ 
/*     */       
/* 330 */       if ((paramMouseEvent.getPoint()).y < i - this.m_nArrowSize)
/*     */       {
/* 332 */         if (paramMouseEvent.getButton() == 1) {
/*     */           
/* 334 */           this.m_renderThread.retreat();
/* 335 */           paramMouseEvent.consume();
/*     */         }
/* 337 */         else if (paramMouseEvent.getButton() == 3) {
/*     */           
/* 339 */           this.m_renderThread.advance();
/* 340 */           paramMouseEvent.consume();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private class RenderThread
/*     */     extends Thread
/*     */   {
/*     */     private boolean m_stop = false;
/*     */     private boolean m_dirty = true;
/* 351 */     private int m_nCurrentSlide = 0;
/* 352 */     private int m_nInitialSongIndex = 0;
/* 353 */     private SongPrinterFS m_songPrinter = new SongPrinterFS();
/*     */     private PageFormat m_pageFormat;
/* 355 */     protected Vector<SongFile> m_qSongFiles = new Vector<SongFile>();
/*     */     
/* 357 */     private boolean m_bSlideProjector = ('y' == SG02App.props.getProperty("fullscreen.blank.projector").charAt(0));
/*     */ 
/*     */     
/*     */     private boolean m_bSlideProjectorSliding = true;
/*     */     
/*     */     private ImageIcon m_image;
/*     */ 
/*     */     
/*     */     public void setDirty() {
/* 366 */       this.m_dirty = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void addSongFile(SongFile param1SongFile) {
/* 372 */       this.m_qSongFiles.add(param1SongFile);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void advanceToSong(int param1Int) {
/* 378 */       this.m_nInitialSongIndex = param1Int;
/* 379 */       this.m_nCurrentSlide = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 385 */       reset();
/*     */ 
/*     */       
/*     */       try {
/* 389 */         Window window = FullScreenView.this.m_device.getFullScreenWindow();
/*     */         
/* 391 */         if (null != window)
/*     */         {
/* 393 */           int i = Integer.parseInt(SG02App.props.getProperty("fullscreen.refresh.sleep"));
/*     */ 
/*     */ 
/*     */           
/* 397 */           boolean bool = false;
/* 398 */           while (!this.m_stop && !bool) {
/*     */ 
/*     */             
/*     */             try {
/* 402 */               window.createBufferStrategy(2);
/* 403 */               bool = true;
/*     */             }
/* 405 */             catch (IllegalStateException illegalStateException) {
/*     */               
/* 407 */               System.err.println(illegalStateException);
/* 408 */               System.err.println(getClass().getName() + " - Retrying");
/* 409 */               sleep(i);
/*     */             } 
/*     */           } 
/*     */           
/* 413 */           BufferStrategy bufferStrategy = window.getBufferStrategy();
/*     */           
/* 415 */           if (null == this.m_image) {
/*     */             
/* 417 */             String str = SG02App.props.getProperty("fullscreen.image.background");
/* 418 */             if (0 != str.length()) {
/* 419 */               this.m_image = new ImageIcon(str);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 424 */           FullScreenView.this.m_size = FullScreenView.this.getSize();
/*     */           
/* 426 */           FullScreenView.this.m_nArrowSize = (int)((FullScreenView.this.m_size.height * FullScreenView.ARROW_SCREEN_PERCENT) / 100.0D);
/* 427 */           if (0 == FullScreenView.this.m_nArrowSize % 2) {
/* 428 */             FullScreenView.this.m_nArrowSize++;
/*     */           }
/*     */           
/* 431 */           while (!this.m_stop) {
/*     */             
/* 433 */             if (this.m_dirty) {
/*     */               
/* 435 */               Graphics graphics = bufferStrategy.getDrawGraphics();
/* 436 */               if (null != graphics) {
/*     */                 
/* 438 */                 render(graphics);
/* 439 */                 graphics.dispose();
/* 440 */                 bufferStrategy.show();
/*     */               } 
/*     */             } 
/*     */             
/* 444 */             sleep(i);
/*     */           } 
/*     */ 
/*     */           
/* 448 */           window.dispose();
/*     */         }
/*     */       
/* 451 */       } catch (Exception exception) {
/*     */         
/* 453 */         System.err.println("RenderThread caught exception: " + exception);
/* 454 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void pleaseStop() {
/* 461 */       this.m_stop = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void home() {
/* 468 */       this.m_nCurrentSlide = 0;
/* 469 */       this.m_dirty = true;
/*     */     }
/*     */     
/*     */     public void end() {
/* 473 */       this.m_nCurrentSlide = this.m_songPrinter.getPageCount() - 1;
/* 474 */       this.m_dirty = true;
/*     */     }
/*     */     
/*     */     public void advance() {
/* 478 */       if (this.m_nCurrentSlide < this.m_songPrinter.getPageCount() - 1)
/* 479 */         this.m_nCurrentSlide++; 
/* 480 */       this.m_dirty = true;
/*     */     }
/*     */     
/*     */     public void retreat() {
/* 484 */       if (this.m_nCurrentSlide > 0)
/* 485 */         this.m_nCurrentSlide--; 
/* 486 */       this.m_dirty = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset() {
/* 492 */       this.m_songPrinter = new SongPrinterFS();
/* 493 */       ListIterator<SongFile> listIterator = this.m_qSongFiles.listIterator();
/* 494 */       while (listIterator.hasNext())
/*     */       {
/* 496 */         this.m_songPrinter.addSongFile(listIterator.next());
/*     */       }
/* 498 */       this.m_pageFormat = null;
/* 499 */       this.m_dirty = true;
/* 500 */       this.m_nInitialSongIndex = this.m_nCurrentSlide;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void render(Graphics param1Graphics) throws InterruptedException {
/* 506 */       if (SG02App.isDebug) {
/*     */         
/* 508 */         System.err.println(getClass().getName() + " - Rendering");
/* 509 */         System.err.println(param1Graphics);
/* 510 */         System.err.println(Toolkit.getDefaultToolkit().getScreenInsets(FullScreenView.this.m_device.getDefaultConfiguration()));
/* 511 */         System.err.println(FullScreenView.this.m_device.getFullScreenWindow());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 517 */         param1Graphics.setColor(new Color(Integer.parseInt(SG02App.props.getProperty("fullscreen.color.background"), 16)));
/* 518 */         param1Graphics.fillRect(0, 0, FullScreenView.this.m_size.width, FullScreenView.this.m_size.height);
/*     */ 
/*     */         
/* 521 */         if (null != this.m_image)
/*     */         {
/* 523 */           if (8 == this.m_image.getImageLoadStatus()) {
/* 524 */             this.m_image.paintIcon(null, param1Graphics, (FullScreenView.this.m_size.width - this.m_image.getIconWidth()) / 2, (FullScreenView.this.m_size.height - this.m_image.getIconHeight()) / 2);
/*     */           }
/*     */         }
/* 527 */         if (null == this.m_pageFormat) {
/*     */           
/* 529 */           initSongPrinter(param1Graphics);
/* 530 */           if (0 != this.m_nInitialSongIndex) {
/* 531 */             this.m_nCurrentSlide = this.m_songPrinter.getPageNumberForSong(this.m_nInitialSongIndex) - 1;
/*     */           }
/*     */         } 
/* 534 */         boolean bool = true;
/*     */ 
/*     */ 
/*     */         
/* 538 */         if (this.m_bSlideProjector && this.m_bSlideProjectorSliding) {
/*     */           
/* 540 */           bool = false;
/* 541 */           this.m_bSlideProjectorSliding = false;
/* 542 */           sleep(Integer.parseInt(SG02App.props.getProperty("fullscreen.blank.projector.sleep")));
/*     */         } 
/*     */         
/* 545 */         if (bool) {
/*     */           
/* 547 */           param1Graphics.setColor(new Color(Integer.parseInt(SG02App.props.getProperty("fullscreen.color.foreground"), 16)));
/*     */           
/* 549 */           this.m_songPrinter.print(param1Graphics, this.m_pageFormat, this.m_nCurrentSlide);
/* 550 */           drawArrows(param1Graphics);
/*     */           
/* 552 */           this.m_dirty = false;
/*     */           
/* 554 */           if (this.m_bSlideProjector) {
/* 555 */             this.m_bSlideProjectorSliding = !this.m_bSlideProjectorSliding;
/*     */           }
/*     */         } 
/* 558 */       } catch (Exception exception) {
/*     */         
/* 560 */         System.err.println("Printing error: " + exception);
/* 561 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void initSongPrinter(Graphics param1Graphics) {
/* 568 */       this.m_pageFormat = new PageFormat();
/* 569 */       Paper paper = new Paper();
/*     */ 
/*     */       
/* 572 */       double d1 = 72.0D;
/* 573 */       double d2 = FullScreenView.this.m_size.width / d1 * 72.0D;
/* 574 */       double d3 = (FullScreenView.this.m_size.height - FullScreenView.this.m_nArrowSize) / d1 * 72.0D;
/* 575 */       paper.setSize(d2, d3);
/* 576 */       paper.setImageableArea(0.0D, 0.0D, d2, d3);
/* 577 */       this.m_pageFormat.setPaper(paper);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 582 */       float f1 = FullScreenView.this.m_size.height * Float.parseFloat(SG02App.props.getProperty("fullscreen.margin.percent.vert")) / 100.0F;
/* 583 */       f1 = (float)(f1 / 72.0D);
/* 584 */       float f2 = FullScreenView.this.m_size.width * Float.parseFloat(SG02App.props.getProperty("fullscreen.margin.percent.horz")) / 100.0F;
/* 585 */       f2 = (float)(f2 / 72.0D);
/* 586 */       this.m_songPrinter.overrideMargins(f1, f1, f2, f2);
/*     */       
/* 588 */       this.m_nCurrentSlide = 0;
/*     */ 
/*     */ 
/*     */       
/* 592 */       FullScreenView.this.setCursor(FullScreenView.this.m_invisibleCursor);
/*     */       
/* 594 */       this.m_songPrinter.paginate(param1Graphics, this.m_pageFormat);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void drawArrows(Graphics param1Graphics) {
/* 600 */       if (FullScreenView.this.m_bShowArrows) {
/*     */         
/* 602 */         int i = FullScreenView.this.m_size.height - FullScreenView.ARROW_PIXEL_MARGIN;
/* 603 */         int j = FullScreenView.this.m_size.width - FullScreenView.ARROW_PIXEL_MARGIN;
/*     */ 
/*     */         
/* 606 */         if (this.m_nCurrentSlide != 0) {
/*     */ 
/*     */           
/* 609 */           drawArrowRetreat(param1Graphics, FullScreenView.ARROW_PIXEL_MARGIN, i, FullScreenView.this.m_nArrowSize);
/* 610 */           drawArrowRetreat(param1Graphics, FullScreenView.ARROW_PIXEL_MARGIN + FullScreenView.this.m_nArrowSize / 2, i, FullScreenView.this.m_nArrowSize);
/*     */           
/* 612 */           drawArrowRetreat(param1Graphics, FullScreenView.ARROW_PIXEL_MARGIN + FullScreenView.this.m_nArrowSize * 2, i, FullScreenView.this.m_nArrowSize);
/*     */         } 
/*     */ 
/*     */         
/* 616 */         if (this.m_nCurrentSlide + 1 < this.m_songPrinter.getPageCount()) {
/* 617 */           drawArrowAdvance(param1Graphics, j - FullScreenView.this.m_nArrowSize, i, FullScreenView.this.m_nArrowSize);
/*     */         }
/*     */         
/* 620 */         drawEscX(param1Graphics, FullScreenView.ARROW_PIXEL_MARGIN + FullScreenView.this.m_nArrowSize * 4, i, FullScreenView.this.m_nArrowSize);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void drawArrowAdvance(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3) {
/* 628 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1, param1Int2 - param1Int3);
/* 629 */       param1Graphics.drawLine(param1Int1 + param1Int3, param1Int2 - param1Int3 / 2, param1Int1, param1Int2 - param1Int3);
/* 630 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1 + param1Int3, param1Int2 - param1Int3 / 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void drawArrowRetreat(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3) {
/* 637 */       param1Graphics.drawLine(param1Int1 + param1Int3, param1Int2, param1Int1 + param1Int3, param1Int2 - param1Int3);
/* 638 */       param1Graphics.drawLine(param1Int1, param1Int2 - param1Int3 / 2, param1Int1 + param1Int3, param1Int2 - param1Int3);
/* 639 */       param1Graphics.drawLine(param1Int1 + param1Int3, param1Int2, param1Int1, param1Int2 - param1Int3 / 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void drawEscX(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3) {
/* 646 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 - 1, param1Int1 + param1Int3, param1Int2 - param1Int3);
/* 647 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 - param1Int3 + 1, param1Int1 + param1Int3, param1Int2);
/*     */     }
/*     */     
/*     */     private RenderThread() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\FullScreenView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */