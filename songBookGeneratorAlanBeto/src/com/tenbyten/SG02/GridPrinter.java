/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GridPrinter
/*     */ {
/*     */   Graphics2D m_graphics;
/*     */   Font m_fontGrid;
/*     */   Color m_colorGrid;
/*     */   int m_fontGridAscent;
/*     */   int m_fontGridSpaceDown;
/*     */   boolean m_bDrawName;
/*     */   boolean m_bDoReMi;
/*     */   boolean m_bCenterGrids;
/*     */   
/*     */   public GridPrinter(Graphics2D paramGraphics2D, Font paramFont, Color paramColor) {
/*  30 */     this.m_graphics = paramGraphics2D;
/*  31 */     this.m_fontGrid = paramFont;
/*  32 */     this.m_colorGrid = paramColor;
/*     */     
/*  34 */     this.m_fontGridAscent = this.m_graphics.getFontMetrics(this.m_fontGrid).getAscent();
/*  35 */     this.m_fontGridSpaceDown = this.m_graphics.getFontMetrics(this.m_fontGrid).getHeight();
/*     */     
/*  37 */     this.m_bDrawName = true;
/*  38 */     this.m_bDoReMi = false;
/*  39 */     this.m_bCenterGrids = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoReMi(boolean paramBoolean) {
/*  45 */     this.m_bDoReMi = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawName(boolean paramBoolean) {
/*  51 */     this.m_bDrawName = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCenterGrids(boolean paramBoolean) {
/*  57 */     this.m_bCenterGrids = paramBoolean;
/*     */   }
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
/*     */   public void drawGrid(Chord paramChord, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  77 */     this.m_graphics.setFont(this.m_fontGrid);
/*  78 */     this.m_graphics.setColor(this.m_colorGrid);
/*     */     
/*  80 */     if (SG02App.isDebug) {
/*     */       
/*  82 */       Graphics2D graphics2D = this.m_graphics;
/*  83 */       BasicStroke basicStroke = (BasicStroke)graphics2D.getStroke();
/*  84 */       float f = basicStroke.getLineWidth();
/*  85 */       System.err.println("line width:" + String.valueOf(f));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  90 */     FontMetrics fontMetrics = this.m_graphics.getFontMetrics();
/*     */     
/*  92 */     float f1 = paramFloat3 - 3.0F * this.m_fontGridSpaceDown;
/*  93 */     float f2 = paramFloat1;
/*  94 */     if (this.m_bCenterGrids)
/*  95 */       f2 = paramFloat1 + (paramFloat3 - f1) / 2.0F; 
/*  96 */     float f3 = paramFloat2 + 1.5F * this.m_fontGridSpaceDown;
/*  97 */     float f4 = f1 / 5.0F * 5.0F;
/*  98 */     float f5 = f1 / 4.0F * 4.0F;
/*  99 */     byte b1 = (byte) (paramChord.isUkulele() ? 4 : 6);
/*     */     
/* 101 */     if (this.m_bDrawName) {
/*     */       
/* 103 */       String str = this.m_bDoReMi ? paramChord.getDoReMiName() : paramChord.getName();
/* 104 */       Rectangle2D rectangle2D = fontMetrics.getStringBounds(str, this.m_graphics);
/* 105 */       float f = (float)((f1 - rectangle2D.getWidth()) / 2.0D + paramFloat1);
/* 106 */       if (this.m_bCenterGrids)
/* 107 */         f = (float)((paramFloat3 - rectangle2D.getWidth()) / 2.0D + paramFloat1); 
/* 108 */       this.m_graphics.drawString(str, f, paramFloat2 + this.m_fontGridAscent);
/*     */     } 
/*     */     
/*     */     byte b2;
/* 112 */     for (b2 = 0; b2 < b1; b2++) {
/*     */       
/* 114 */       float f = f2 + b2 * f4 / (b1 - 1);
/* 115 */       this.m_graphics.draw(new Line2D.Float(f, f3, f, f3 + f5));
/*     */     } 
/*     */ 
/*     */     
/* 119 */     for (b2 = 0; b2 < 5; b2++) {
/*     */       
/* 121 */       float f = f3 + b2 * f5 / 4.0F;
/* 122 */       this.m_graphics.draw(new Line2D.Float(f2, f, f2 + f4, f));
/*     */     } 
/*     */ 
/*     */     
/* 126 */     for (b2 = 0; b2 < b1; b2++) {
/* 127 */       drawGridFingering(b2, b1, paramChord, f4, f5, f2, f3);
/*     */     }
/* 129 */     if (1 < paramChord.getBaseFret()) {
/*     */       
/* 131 */       String str = String.valueOf(paramChord.getBaseFret());
/* 132 */       Rectangle2D rectangle2D = fontMetrics.getStringBounds(str, this.m_graphics);
/* 133 */       this.m_graphics.drawString(str, (float)((f2 - f4 / 5.0F) - rectangle2D.getWidth()), f3 + this.m_fontGridAscent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawGridFingering(int paramInt1, int paramInt2, Chord paramChord, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 142 */     float f1 = paramFloat2 / 14.0F;
/* 143 */     float f2 = paramFloat3 + paramInt1 * paramFloat1 / (paramInt2 - 1) - f1;
/*     */ 
/*     */     
/* 146 */     byte b1 = paramChord.getFretForString(paramInt1);
/* 147 */     if (0 == b1) {
/*     */       
/* 149 */       float f = paramFloat4 + -1.0F * paramFloat2 / 8.0F - f1;
/* 150 */       this.m_graphics.draw(new Ellipse2D.Float(f2, f, f1 * 2.0F, f1 * 2.0F));
/*     */     }
/* 152 */     else if (15 == b1) {
/*     */       
/* 154 */       float f = paramFloat4 + paramFloat2 / -4.0F + paramFloat2 / 8.0F - f1;
/* 155 */       this.m_graphics.draw(new Line2D.Float(f2, f, f2 + f1 * 2.0F, f + f1 * 2.0F));
/* 156 */       this.m_graphics.draw(new Line2D.Float(f2 + f1 * 2.0F, f, f2, f + f1 * 2.0F));
/*     */     }
/*     */     else {
/*     */       
/* 160 */       float f = paramFloat4 + (b1 - 1) * paramFloat2 / 4.0F + paramFloat2 / 8.0F - f1;
/* 161 */       Ellipse2D.Float float_ = new Ellipse2D.Float(f2, f, f1 * 2.0F, f1 * 2.0F);
/* 162 */       this.m_graphics.fill(float_);
/* 163 */       this.m_graphics.draw(float_);
/*     */     } 
/*     */ 
/*     */     
/* 167 */     byte b2 = paramChord.getFingeringForString(paramInt1);
/* 168 */     if (0 != b2) {
/*     */       
/* 170 */       String str = String.valueOf(b2);
/* 171 */       Rectangle2D rectangle2D = this.m_graphics.getFontMetrics().getStringBounds(str, this.m_graphics);
/* 172 */       f2 = paramFloat3 + paramInt1 * paramFloat1 / (paramInt2 - 1) - (float)(rectangle2D.getWidth() / 2.0D);
/* 173 */       this.m_graphics.drawString(str, f2, paramFloat4 + paramFloat2 + this.m_fontGridSpaceDown);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\GridPrinter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */