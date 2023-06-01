/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.io.File;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ThreadScan
/*     */   extends Thread
/*     */ {
/*     */   private volatile boolean m_bStopSignal;
/*     */   private SG02Frame m_frame;
/*     */   private ResourceBundle m_resources;
/*     */   private int m_numErrors;
/*     */   private int m_numSongs;
/*     */   
/*     */   ThreadScan(SG02Frame paramSG02Frame) {
/*  24 */     this.m_bStopSignal = false;
/*  25 */     this.m_frame = paramSG02Frame;
/*  26 */     this.m_resources = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*  27 */     setDaemon(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  32 */     scan(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void runSynchronousScan() {
/*  37 */     scan(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void scan(final boolean isAsync) {
/*  42 */     setSongListLabel(isAsync, this.m_frame.m_labelSongs, 0);
/*     */     
/*  44 */     this.m_numSongs = 0;
/*  45 */     this.m_numErrors = 0;
/*     */ 
/*     */     
/*     */     try {
/*  49 */       String str = SG02App.props.getProperty("songs.path");
/*  50 */       File file = new File(str);
/*     */       
/*  52 */       if (!file.exists()) {
/*     */         
/*  54 */         runRunnable(isAsync, new Runnable() {
/*     */               public void run() {
/*  56 */                 JOptionPane.showMessageDialog(ThreadScan.this.m_frame, ThreadScan.this
/*  57 */                     .m_resources.getString("Text.Error.NoSongsPath"), ThreadScan.this
/*  58 */                     .m_resources.getString("Title.Dialog.Error"), 0);
/*     */               }
/*     */             });
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  65 */       processDirectory(isAsync, file);
/*     */       
/*  67 */       if (!this.m_bStopSignal)
/*     */       {
/*  69 */         runRunnable(isAsync, new Runnable() {
/*     */               public void run() {
/*  71 */                 ThreadScan.this.setSongListLabel(isAsync, ThreadScan.this.m_frame.m_labelSongs, ThreadScan.this.m_numSongs);
/*     */                 
/*  73 */                 ThreadScan.this.m_frame.repopulateSongsAfterRescan();
/*     */               }
/*     */             });
/*     */       }
/*  77 */       if (0 != this.m_numErrors) {
/*  78 */         throw new Exception();
/*     */       }
/*  80 */     } catch (Exception exception) {
/*     */       
/*  82 */       runRunnable(isAsync, new Runnable() {
/*     */             public void run() {
/*  84 */               JOptionPane.showMessageDialog(ThreadScan.this.m_frame, ThreadScan.this
/*  85 */                   .m_resources.getString("Text.Error.Scan"), ThreadScan.this
/*  86 */                   .m_resources.getString("Title.Dialog.Error"), 0);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  91 */     if (!this.m_bStopSignal) {
/*     */       
/*  93 */       Registration registration = new Registration();
/*  94 */       if (registration.isNagTime())
/*     */       {
/*  96 */         runRunnable(isAsync, new RegisterNagDialog(this.m_frame));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processDirectory(final boolean isAsync, File paramFile) {
/*     */     try {
/* 106 */       String[] arrayOfString = paramFile.list();
/* 107 */       String str = paramFile.toString();
/*     */       
/* 109 */       for (int b = 0; b < arrayOfString.length && !this.m_bStopSignal; b++) {
/*     */         
/* 111 */         File file = new File(str, arrayOfString[b]);
/*     */ 
/*     */         
/* 114 */         if (file.isHidden()) {
/*     */           continue;
/*     */         }
/* 117 */         if (file.isDirectory())
/*     */         {
/* 119 */           if (SG02App.props.getProperty("songs.path.recurse").charAt(0) == 'y') {
/* 120 */             processDirectory(isAsync, file);
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */         }
/*     */         
/* 126 */         final SongFile songFile = new SongFile(file);
/* 127 */         if (songFile.isSongFile()) {
/*     */           
/* 129 */           runRunnable(isAsync, new Runnable() {
/*     */                 public void run() {
/* 131 */                   ThreadScan.this.m_frame.m_songsTable.addSong(songFile);
/* 132 */                   if (0 == ThreadScan.this.m_frame.m_modelSongs.size() % 10)
/* 133 */                     ThreadScan.this.setSongListLabel(isAsync, ThreadScan.this.m_frame.m_labelSongs, ThreadScan.this.m_frame.m_modelSongs.size()); 
/*     */                 }
/*     */               });
/* 136 */           this.m_numSongs++;
/*     */         }
/*     */         else {
/*     */           
/* 140 */           System.err.println("Not a song file: " + file.toString());
/*     */         } 
/*     */         continue;
/*     */       } 
/* 144 */     } catch (Exception exception) {
/*     */       
/* 146 */       this.m_numErrors++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSongListLabel(boolean paramBoolean, final JComponent label, final int numSongs) {
/* 152 */     runRunnable(paramBoolean, new Runnable() {
/*     */           public void run() {
/* 154 */             if (label.getClass() == JButton.class) {
/* 155 */               ((JButton)label).setText(ThreadScan.this.m_resources.getString("Label.SongsAvailable.Begin") + String.valueOf(numSongs) + ThreadScan.this.m_resources.getString("Label.SongsAvailable.End"));
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   void pleaseStop() {
/* 162 */     this.m_bStopSignal = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void runRunnable(boolean paramBoolean, Runnable paramRunnable) {
/* 167 */     if (paramBoolean) {
/* 168 */       EventQueue.invokeLater(paramRunnable);
/*     */     } else {
/* 170 */       paramRunnable.run();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\ThreadScan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */