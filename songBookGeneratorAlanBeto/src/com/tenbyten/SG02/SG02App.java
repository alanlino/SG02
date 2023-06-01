/*     */ package com.tenbyten.SG02;
import java.awt.EventQueue;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.TreeSet;

/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.tenbyten.Util.CopyFile;
/*     */ 
/*     */ class SG02App {
/*     */   public static Properties props;
/*  17 */   public static final ChordMap chords = new ChordMap(500, 0.8F); public static boolean isDebug; public static boolean doPrintSongOutputErrors;
/*  18 */   private static final HashMap<String, String> dataKeysToValues = new HashMap<String, String>();
/*     */   
/*     */   private static TreeSet<Chord> majorTransposeSet;
/*     */   
/*     */   private static TreeSet<Chord> minorTransposeSet;
/*  23 */   public static final boolean isMac = (System.getProperty("os.name").toLowerCase().indexOf("mac") != -1);
/*  24 */   public static final boolean isWindows = (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1);
/*     */ 
/*     */   
/*     */   public static boolean isQuaqua = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*  32 */     if (isMac) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  37 */       System.setProperty("apple.awt.window.position.forceSafeUserPositioning", "true");
/*     */       
/*  39 */       if (isQuaqua) {
/*     */         
/*     */         try {
/*     */           
/*  43 */           UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
/*  44 */           System.setProperty("Quaqua.List.style", "striped");
/*     */         }
/*  46 */         catch (Exception exception) {
/*     */           
/*  48 */           isQuaqua = false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  53 */     readProperties();
/*     */     
/*  55 */     isDebug = (props.getProperty("debug").charAt(0) == 'y');
/*  56 */     doPrintSongOutputErrors = (props.getProperty("debug.printSongOutputErrors").charAt(0) == 'y');
/*     */ 
/*     */     
/*  59 */     Registration registration = new Registration();
/*  60 */     registration.incrementRunCount();
/*     */     
/*  62 */     initializeChords();
/*     */     
/*  64 */     EventQueue.invokeLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  68 */             SG02Frame sG02Frame = new SG02Frame();
/*  69 */             sG02Frame.setVisible(true);
/*     */             
/*  71 */             String str = SG02App.props.getProperty("songs.path");
/*  72 */             File file = new File(str);
/*  73 */             if (!file.exists() && str.equals(System.getProperty("user.home") + "/Documents/Songsheet Generator Songs")) {
/*     */               
/*  75 */               ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/*     */               
/*  77 */               if (0 == JOptionPane.showConfirmDialog(sG02Frame, resourceBundle
/*  78 */                   .getString("Text.Error.NoSongsPath.MakeDefault"), resourceBundle
/*  79 */                   .getString("Title.Dialog.Confirm"), 0, 3)) {
/*     */                 
/*     */                 try {
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*  86 */                   File file1 = new File("twinkle.txt");
/*     */ 
/*     */ 
/*     */                   
/*  90 */                   if (!file1.exists()) {
/*     */                     
/*  92 */                     File file3 = new File(SG02App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
/*  93 */                     file1 = new File(file3.getParent() + "/../Resources/twinkle.txt");
/*  94 */                     if (!file1.exists()) {
/*  95 */                       file1 = new File("../twinkle.txt");
/*     */                     }
/*     */                   } 
/*  98 */                   file.mkdir();
/*     */                   
/* 100 */                   File file2 = new File(file, "twinkle.txt");
/* 101 */                   CopyFile.simpleCopy(file1, file2);
/*     */                 }
/* 103 */                 catch (Exception exception) {}
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readProperties() {
/* 113 */     if (null == props) {
/*     */       
/* 115 */       Properties properties = createDefaultProperties();
/* 116 */       props = new Properties(properties);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 121 */       FileInputStream fileInputStream = new FileInputStream(getPropsFile());
/* 122 */       props.load(fileInputStream);
/* 123 */       fileInputStream.close();
/*     */     }
/* 125 */     catch (Exception exception) {
/*     */       
/* 127 */       System.err.println("SG02App: unable to read properties (first run?)");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 133 */       FileInputStream fileInputStream = new FileInputStream(getKeyValueMapFile());
/* 134 */       ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 135 */       HashMap<? extends String, ? extends String> hashMap = (HashMap)objectInputStream.readObject();
/* 136 */       dataKeysToValues.putAll(hashMap);
/* 137 */       objectInputStream.close();
/* 138 */       fileInputStream.close();
/*     */     }
/* 140 */     catch (Exception exception) {
/*     */       
/* 142 */       System.err.println("SG02App: unable to read data key/value map (first run?)");
/*     */     } 
/*     */ 
/*     */     
/* 146 */     if (null == dataKeysToValues.get("example")) {
/* 147 */       dataKeysToValues.put("example", "An example value");
/*     */     }
/*     */     
/* 150 */     props.setProperty("print.autospace", "yes");
/*     */     
/* 152 */     props.setProperty("transpose", "0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeProperties() {
/*     */     try {
/* 160 */       FileOutputStream fileOutputStream = new FileOutputStream(getPropsFile());
/*     */       
/* 162 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 163 */       props.store(fileOutputStream, resourceBundle.getString("Songsheet Generator") + " " + resourceBundle.getString("Version"));
/* 164 */       fileOutputStream.close();
/*     */     }
/* 166 */     catch (Exception exception) {
/*     */       
/* 168 */       System.err.println("SG02App: unable to write properties");
/*     */     } 
/*     */     
/* 171 */     writeChordrc();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 176 */       FileOutputStream fileOutputStream = new FileOutputStream(getKeyValueMapFile());
/* 177 */       ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 178 */       objectOutputStream.writeObject(dataKeysToValues);
/* 179 */       objectOutputStream.close();
/* 180 */       fileOutputStream.close();
/*     */     }
/* 182 */     catch (Exception exception) {
/*     */       
/* 184 */       System.err.println("SG02App: unable to write data key/value map");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeChordrc() {
/* 191 */     Chord.writeChordrc((byte)2, chords, getChordrcFile());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Properties getDefaultProperties() {
/* 197 */     Properties properties = new Properties();
/*     */     
/* 199 */     properties.setProperty("debug", "no");
/* 200 */     properties.setProperty("debug.disablePrintProgress", "no");
/* 201 */     properties.setProperty("debug.printSongOutputErrors", "no");
/*     */     
/* 203 */     if (isMac) {
/*     */ 
/*     */       
/* 206 */       properties.setProperty("songs.path", System.getProperty("user.home") + "/Documents/Songsheet Generator Songs");
/*     */     } else {
/*     */       
/* 209 */       properties.setProperty("songs.path", "Songsheet Generator Songs");
/*     */     } 
/* 211 */     properties.setProperty("songs.path.recurse", "no");
/*     */     
/* 213 */     if (isMac) {
/* 214 */       properties.setProperty("songs.editor", "/Applications/Text Edit.app");
/*     */     } else {
/* 216 */       properties.setProperty("songs.editor", "");
/*     */     } 
/* 218 */     properties.setProperty("songs.number", "no");
/* 219 */     properties.setProperty("songs.titles.reorder.aanthe", "yes");
/* 220 */     properties.setProperty("songs.encoding", "UTF-8");
/* 221 */     properties.setProperty("songs.template", "NewSong.txt");
/* 222 */     properties.setProperty("output", "Printer");
/* 223 */     properties.setProperty("print.chords", "yes");
/* 224 */     properties.setProperty("print.chords.doremi", "no");
/* 225 */     properties.setProperty("print.chords.ukulele", "no");
/* 226 */     properties.setProperty("print.parse.comments", "yes");
/* 226 */     properties.setProperty("print.parse.tags", "yes");
/* 227 */     properties.setProperty("toc.print", "no");
/* 228 */     properties.setProperty("toc.text", "Table of Contents");
/* 229 */     properties.setProperty("toc.text.separator", "   ... ");
/* 230 */     properties.setProperty("toc.page.numbers", "yes");
/* 231 */     properties.setProperty("toc.print.npp", "yes");
/* 232 */     properties.setProperty("transpose", "0");
/* 233 */     properties.setProperty("units", "inches");
/*     */     
/* 235 */     properties.setProperty("keys.grids.friendly", "C G D A E B F Am Em Bm F#m C#m Dm Gm Cm");
/* 236 */     properties.setProperty("keys.transpose", "C G D A E B F# C# F Bb Eb Ab Db Gb Cb Am Em Bm F#m C#m G#m D#m A#m Dm Gm Cm Fm Bbm Ebm Abm");
/*     */     
/* 238 */     properties.setProperty("capo.keys", "C D E G A B");
/* 239 */     properties.setProperty("capo.max", "7");
/* 240 */     properties.setProperty("capo.use", "yes");
/*     */     
/* 242 */     properties.setProperty("reminder.update", "");
/* 243 */     properties.setProperty("reminder.rtf.bad", "yes");
/* 244 */     properties.setProperty("reminder.options.to.printlist", "yes");
/* 245 */     properties.setProperty("warn.chords.unknown", "yes");
/*     */     
/* 247 */     properties.setProperty("window.x", "10");
/* 248 */     properties.setProperty("window.y", "10");
/* 249 */     properties.setProperty("window.width", "700");
/* 250 */     properties.setProperty("window.height", "500");
/*     */ 
/*     */ 
/*     */     
/* 254 */     properties.setProperty("window.list.songs.doubleclick", "yes");
/* 255 */     properties.setProperty("window.list.print.doubleclick", "no");
/*     */     
/* 257 */     properties.setProperty("print.songs.per.page", String.valueOf(1));
/* 258 */     properties.setProperty("print.songs.npp", "no");
/* 259 */     properties.setProperty("print.autospace", "yes");
/* 260 */     properties.setProperty("print.margin.top", "1.0");
/* 261 */     properties.setProperty("print.margin.bottom", "1.0");
/* 262 */     properties.setProperty("print.margin.left", "1.0");
/* 263 */     properties.setProperty("print.margin.right", "1.0");
/* 264 */     properties.setProperty("print.spacing.column", "1.0");
/* 265 */     properties.setProperty("print.spacing.column.2nd", "0.0");
/* 266 */     properties.setProperty("print.spacing.chord", "1.2");
/* 267 */     properties.setProperty("print.spacing.chord.between", "2");
/* 268 */     properties.setProperty("print.spacing.lyric", "1.1");
/* 269 */     properties.setProperty("print.spacing.title", "1.5");
/* 270 */     properties.setProperty("print.chorus.mark", "box");
/* 271 */     properties.setProperty("print.overflow.title", "yes");
/* 272 */     properties.setProperty("print.footer.text", "%l%A %r%G");
/* 273 */     properties.setProperty("print.footer.physical.only", "yes");
/* 274 */     properties.setProperty("print.chars.hyphens", "yes");
/* 275 */     properties.setProperty("print.chars.tab.inches", "0.25");
/* 276 */     properties.setProperty("print.stroke.printer.normal", "0.25");
/* 277 */     properties.setProperty("print.stroke.printer.thick", "2.0");
/* 278 */     properties.setProperty("print.wrap.chords.alone", "yes");
/* 279 */     properties.setProperty("print.wrap.titles", "no");
/* 280 */     properties.setProperty("print.fix.u2019", "yes");
/*     */     
/* 282 */     properties.setProperty("print.font.size.chord", "11");
/* 283 */     properties.setProperty("print.font.size.comment", "11");
/* 284 */     properties.setProperty("print.font.size.comment.guitar", "11");
/* 285 */     properties.setProperty("print.font.size.footer", "11");
/* 286 */     properties.setProperty("print.font.size.grid", "9");
/* 287 */     properties.setProperty("print.font.size.normal", "12");
/* 288 */     properties.setProperty("print.font.size.subtitle", "15");
/* 289 */     properties.setProperty("print.font.size.title", "18");
/* 290 */     properties.setProperty("print.font.size.tab", "10");
/*     */     
/* 292 */     if (isMac) {
/*     */       
/* 294 */       properties.setProperty("print.font.family.chord", "Helvetica");
/* 295 */       properties.setProperty("print.font.family.comment", "Helvetica");
/* 296 */       properties.setProperty("print.font.family.comment.guitar", "Helvetica");
/* 297 */       properties.setProperty("print.font.family.footer", "Lucida Grande");
/* 298 */       properties.setProperty("print.font.family.grid", "Helvetica");
/* 299 */       properties.setProperty("print.font.family.normal", "Helvetica");
/* 300 */       properties.setProperty("print.font.family.subtitle", "Helvetica");
/* 301 */       properties.setProperty("print.font.family.title", "Helvetica");
/* 302 */       properties.setProperty("print.font.family.tab", "Monaco");
/*     */     }
/*     */     else {
/*     */       
/* 306 */       properties.setProperty("print.font.family.chord", "SansSerif");
/* 307 */       properties.setProperty("print.font.family.comment", "SansSerif");
/* 308 */       properties.setProperty("print.font.family.comment.guitar", "SansSerif");
/* 309 */       properties.setProperty("print.font.family.footer", "SansSerif");
/* 310 */       properties.setProperty("print.font.family.grid", "SansSerif");
/* 311 */       properties.setProperty("print.font.family.normal", "Serif");
/* 312 */       properties.setProperty("print.font.family.subtitle", "Serif");
/* 313 */       properties.setProperty("print.font.family.title", "Serif");
/* 314 */       properties.setProperty("print.font.family.tab", "SansSerif");
/*     */     } 
/*     */     
/* 317 */     properties.setProperty("print.font.style.chord.bold", "no");
/* 318 */     properties.setProperty("print.font.style.chord.italic", "yes");
/* 319 */     properties.setProperty("print.font.style.comment.bold", "no");
/* 320 */     properties.setProperty("print.font.style.comment.italic", "yes");
/* 321 */     properties.setProperty("print.font.style.comment.guitar.bold", "no");
/* 322 */     properties.setProperty("print.font.style.comment.guitar.italic", "yes");
/* 323 */     properties.setProperty("print.font.style.footer.bold", "no");
/* 324 */     properties.setProperty("print.font.style.footer.italic", "yes");
/* 325 */     properties.setProperty("print.font.style.grid.bold", "no");
/* 326 */     properties.setProperty("print.font.style.grid.italic", "yes");
/* 327 */     properties.setProperty("print.font.style.normal.bold", "no");
/* 328 */     properties.setProperty("print.font.style.normal.italic", "no");
/* 329 */     properties.setProperty("print.font.style.subtitle.bold", "yes");
/* 330 */     properties.setProperty("print.font.style.subtitle.italic", "no");
/* 331 */     properties.setProperty("print.font.style.title.bold", "yes");
/* 332 */     properties.setProperty("print.font.style.title.italic", "no");
/* 333 */     properties.setProperty("print.font.style.tab.bold", "yes");
/* 334 */     properties.setProperty("print.font.style.tab.italic", "no");
/*     */     
/* 336 */     properties.setProperty("print.font.color.chord", "0");
/* 337 */     properties.setProperty("print.font.color.comment", "0");
/* 338 */     properties.setProperty("print.font.color.comment.guitar", "0");
/* 339 */     properties.setProperty("print.font.color.footer", "0");
/* 340 */     properties.setProperty("print.font.color.grid", "0");
/* 341 */     properties.setProperty("print.font.color.normal", "0");
/* 342 */     properties.setProperty("print.font.color.subtitle", "0");
/* 343 */     properties.setProperty("print.font.color.tab", "0");
/* 344 */     properties.setProperty("print.font.color.title", "0");
/*     */     
/* 346 */     properties.setProperty("print.format.title", "%c%t");
/* 347 */     properties.setProperty("print.format.title.line", "no");
/* 348 */     properties.setProperty("print.format.title.centeronpage", "no");
/* 349 */     properties.setProperty("print.format.subtitle", "%c%s");
/* 350 */     properties.setProperty("print.format.subtitle.line", "no");
/* 351 */     properties.setProperty("print.format.grids.align", "center");
/*     */     
/* 353 */     properties.setProperty("fullscreen.device", "0");
/* 354 */     properties.setProperty("fullscreen.margin.percent.horz", "10.0");
/* 355 */     properties.setProperty("fullscreen.margin.percent.vert", "5.0");
/* 356 */     properties.setProperty("fullscreen.color.foreground", "ffffff");
/* 357 */     properties.setProperty("fullscreen.color.background", "0");
/* 358 */     properties.setProperty("fullscreen.blank.first", "yes");
/* 359 */     properties.setProperty("fullscreen.blank.last", "yes");
/* 360 */     properties.setProperty("fullscreen.blank.between", "yes");
/* 361 */     properties.setProperty("fullscreen.blank.projector", "no");
/* 362 */     properties.setProperty("fullscreen.blank.projector.sleep", "75");
/* 363 */     properties.setProperty("fullscreen.control.show", "yes");
/* 364 */     properties.setProperty("fullscreen.control.mouse", "yes");
/* 365 */     properties.setProperty("fullscreen.songs.per.page", String.valueOf(2));
/* 366 */     properties.setProperty("fullscreen.orphan.lines", "4");
/* 367 */     properties.setProperty("fullscreen.footer.text", "%l%A %r%G");
/* 368 */     properties.setProperty("fullscreen.refresh.sleep", "50");
/* 369 */     properties.setProperty("fullscreen.image.background", "");
/* 370 */     properties.setProperty("fullscreen.chars.tab.percent", "5");
/* 371 */     properties.setProperty("fullscreen.start.@.selected", "no");
/* 372 */     properties.setProperty("fullscreen.font.size.chord", "18");
/* 373 */     properties.setProperty("fullscreen.font.size.comment", "18");
/* 374 */     properties.setProperty("fullscreen.font.size.comment.guitar", "18");
/* 375 */     properties.setProperty("fullscreen.font.size.footer", "16");
/* 376 */     properties.setProperty("fullscreen.font.size.grid", "12");
/* 377 */     properties.setProperty("fullscreen.font.size.normal", "20");
/* 378 */     properties.setProperty("fullscreen.font.size.subtitle", "24");
/* 379 */     properties.setProperty("fullscreen.font.size.title", "28");
/* 380 */     properties.setProperty("fullscreen.font.size.tab", "16");
/*     */     
/* 382 */     properties.setProperty("fullscreen.font.family.chord", "SansSerif");
/* 383 */     properties.setProperty("fullscreen.font.family.comment", "SansSerif");
/* 384 */     properties.setProperty("fullscreen.font.family.comment.guitar", "SansSerif");
/* 385 */     properties.setProperty("fullscreen.font.family.footer", "SansSerif");
/* 386 */     properties.setProperty("fullscreen.font.family.grid", "SansSerif");
/* 387 */     properties.setProperty("fullscreen.font.family.normal", "Serif");
/* 388 */     properties.setProperty("fullscreen.font.family.subtitle", "Serif");
/* 389 */     properties.setProperty("fullscreen.font.family.title", "Serif");
/* 390 */     properties.setProperty("fullscreen.font.family.tab", "SansSerif");
/*     */     
/* 392 */     properties.setProperty("fullscreen.font.style.chord.bold", "no");
/* 393 */     properties.setProperty("fullscreen.font.style.chord.italic", "yes");
/* 394 */     properties.setProperty("fullscreen.font.style.comment.bold", "no");
/* 395 */     properties.setProperty("fullscreen.font.style.comment.italic", "yes");
/* 396 */     properties.setProperty("fullscreen.font.style.comment.guitar.bold", "no");
/* 397 */     properties.setProperty("fullscreen.font.style.comment.guitar.italic", "yes");
/* 398 */     properties.setProperty("fullscreen.font.style.footer.bold", "no");
/* 399 */     properties.setProperty("fullscreen.font.style.footer.italic", "yes");
/* 400 */     properties.setProperty("fullscreen.font.style.grid.bold", "no");
/* 401 */     properties.setProperty("fullscreen.font.style.grid.italic", "yes");
/* 402 */     properties.setProperty("fullscreen.font.style.normal.bold", "no");
/* 403 */     properties.setProperty("fullscreen.font.style.normal.italic", "no");
/* 404 */     properties.setProperty("fullscreen.font.style.subtitle.bold", "yes");
/* 405 */     properties.setProperty("fullscreen.font.style.subtitle.italic", "no");
/* 406 */     properties.setProperty("fullscreen.font.style.title.bold", "yes");
/* 407 */     properties.setProperty("fullscreen.font.style.title.italic", "no");
/* 408 */     properties.setProperty("fullscreen.font.style.tab.bold", "yes");
/* 409 */     properties.setProperty("fullscreen.font.style.tab.italic", "no");
/*     */     
/* 411 */     properties.setProperty("fullscreen.font.color.chord", "ffffff");
/* 412 */     properties.setProperty("fullscreen.font.color.comment", "ffffff");
/* 413 */     properties.setProperty("fullscreen.font.color.comment.guitar", "ffffff");
/* 414 */     properties.setProperty("fullscreen.font.color.footer", "ffffff");
/* 415 */     properties.setProperty("fullscreen.font.color.grid", "ffffff");
/* 416 */     properties.setProperty("fullscreen.font.color.normal", "ffffff");
/* 417 */     properties.setProperty("fullscreen.font.color.subtitle", "ffffff");
/* 418 */     properties.setProperty("fullscreen.font.color.title", "ffffff");
/* 419 */     properties.setProperty("fullscreen.font.color.tab", "ffffff");
/*     */     
/* 421 */     properties.setProperty("grids.print", "yes");
/* 422 */     properties.setProperty("grids.print.unfriendly", "yes");
/* 423 */     properties.setProperty("grids.print.unused", "yes");
/* 424 */     properties.setProperty("grids.fullscreen", "yes");
/* 425 */     properties.setProperty("grids.min", "0.7");
/* 426 */     properties.setProperty("grids.max", "1.0");
/* 427 */     properties.setProperty("grids.spacing", "0.2");
/* 428 */     properties.setProperty("grids.chords.no.grids", "");
/*     */     
/* 430 */     properties.setProperty("html.css.link", "yes");
/* 431 */     properties.setProperty("html.css.link.name", "sg.css");
/* 432 */     properties.setProperty("html.style.title", "");
/* 433 */     properties.setProperty("html.style.song", "");
/* 434 */     properties.setProperty("html.style.subtitle", "");
/* 435 */     properties.setProperty("html.style.lyric", "");
/* 436 */     properties.setProperty("html.style.chord", "");
/* 437 */     properties.setProperty("html.style.comment", "");
/* 438 */     properties.setProperty("html.style.chorus", "");
/* 439 */     properties.setProperty("html.style.chorus.overall", "");
/* 440 */     properties.setProperty("html.style.newsong", "");
/* 441 */     properties.setProperty("html.style.tab", "");
/* 442 */     properties.setProperty("html.style.toc", "");
/* 443 */     properties.setProperty("html.style.toc.header", "");
/* 444 */     properties.setProperty("html.style.toc.contents", "");
/* 445 */     properties.setProperty("html.style.footer", "");
/* 446 */     properties.setProperty("html.footer.text", "");
/*     */     
/* 448 */     properties.setProperty("import.tab.dashes.min", "4");
/* 449 */     properties.setProperty("import.tab.digits.min", "1");
/* 450 */     properties.setProperty("import.tab.notes.min", "1");
/* 451 */     properties.setProperty("import.tab.notes.max", "255");
/* 452 */     properties.setProperty("import.tab.strokes.min", "0");
/* 453 */     properties.setProperty("import.tab.strokes.max", "255");
/* 454 */     properties.setProperty("import.tab.dashes.within", "4");
/*     */     
/* 456 */     properties.setProperty("registration.b", "0");
/*     */ 
/*     */     
/* 459 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties createDefaultProperties() {
/* 465 */     Properties properties = getDefaultProperties();
/*     */ 
/*     */     
/*     */     try {
/* 469 */       FileOutputStream fileOutputStream = new FileOutputStream(getDefaultPropsFile());
/*     */       
/* 471 */       ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
/* 472 */       properties.store(fileOutputStream, "defaults " + resourceBundle.getString("Songsheet Generator") + " " + resourceBundle.getString("Version"));
/* 473 */       fileOutputStream.close();
/*     */     }
/* 475 */     catch (Exception exception) {
/*     */       
/* 477 */       System.err.println("SG02App: unable to write default properties");
/*     */     } 
/*     */     
/* 480 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static File getKeyValueMapFile() {
/*     */     File file;
/* 488 */     if (isMac || isWindows) {
/* 489 */       file = new File(getPropsPath(), "com.tenbyten.SG02.keyvalue");
/*     */     } else {
/* 491 */       file = new File(getPropsPath(), ".sg02.keyvalue");
/*     */     } 
/* 493 */     if (isDebug) {
/* 494 */       System.err.println("Key value map is stored in " + file.toString());
/*     */     }
/* 496 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static File getPropsFile() {
/*     */     File file;
/* 504 */     if (isMac || isWindows) {
/* 505 */       file = new File(getPropsPath(), "com.tenbyten.SG02.props");
/*     */     } else {
/* 507 */       file = new File(getPropsPath(), ".sg02.props");
/*     */     } 
/* 509 */     if (isDebug) {
/* 510 */       System.err.println("Properties are stored in " + file.toString());
/*     */     }
/* 512 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static File getDefaultPropsFile() {
/* 518 */     if (isMac || isWindows) {
/* 519 */       return new File(getPropsPath(), "com.tenbyten.SG02.props.default");
/*     */     }
/* 521 */     return new File(getPropsPath(), ".sg02.props.default");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getPropsPath() {
/* 527 */     File file = new File(System.getProperty("user.home"));
/* 528 */     if (!file.exists()) {
/* 529 */       file = new File("./");
/*     */     
/*     */     }
/* 532 */     else if (isMac) {
/* 533 */       file = new File(file, "/Library/Preferences/");
/* 534 */     } else if (isWindows) {
/*     */ 
/*     */       
/* 537 */       File file1 = new File(file, "Application Data");
/* 538 */       if (file1.exists()) {
/* 539 */         file = file1;
/*     */       }
/*     */     } 
/*     */     
/* 543 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageIcon loadImageIcon(String paramString) {
/* 549 */     ImageIcon imageIcon = null;
/*     */ 
/*     */     
/* 552 */     InputStream inputStream = SG02App.class.getResourceAsStream(paramString);
/* 553 */     byte[] arrayOfByte = new byte[800];
/* 554 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 559 */     if (null == inputStream) {
/*     */       
/*     */       try {
/* 562 */         inputStream = new FileInputStream("../" + paramString);
/*     */       }
/* 564 */       catch (FileNotFoundException fileNotFoundException) {
/*     */         
/* 566 */         inputStream = null;
/*     */       } 
/*     */     }
/* 569 */     if (null == inputStream) {
/* 570 */       imageIcon = new ImageIcon();
/*     */     } else {
/*     */       
/*     */       try {
/* 574 */         int j = 0;
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/* 579 */           if (i >= arrayOfByte.length) {
/*     */             
/* 581 */             byte[] arrayOfByte1 = new byte[arrayOfByte.length * 2];
/* 582 */             System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, arrayOfByte.length);
/* 583 */             arrayOfByte = arrayOfByte1;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 589 */           j = inputStream.read(arrayOfByte, i, arrayOfByte.length - i);
/* 590 */           i += j;
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 595 */         while (0 < j || i >= arrayOfByte.length);
/*     */ 
/*     */         
/* 598 */         imageIcon = new ImageIcon(arrayOfByte);
/*     */         
/* 600 */         inputStream.close();
/*     */       }
/* 602 */       catch (IOException iOException) {}
/*     */     } 
/*     */     
/* 605 */     return imageIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initializeChords() {
/* 612 */     Chord.initializeBuiltinChords(chords);
/* 613 */     Chord.readChordrc(chords, getChordrcFile());
/*     */     
/* 615 */     if (isDebug) {
/* 616 */       System.err.println(chords.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static File getChordrcFile() {
/* 622 */     if (isMac || isWindows) {
/* 623 */       return new File(getPropsPath(), "com.tenbyten.SG02.chordrc");
/*     */     }
/* 625 */     return new File(getPropsPath(), ".sg02.chordrc");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getValueForKey(String paramString) {
/* 631 */     return dataKeysToValues.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> cloneKeyValueMap() {
/* 637 */     return (HashMap<String, String>)dataKeysToValues.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void replaceKeyValueMappings(HashMap<String, String> paramHashMap) {
/* 643 */     dataKeysToValues.clear();
/* 644 */     dataKeysToValues.putAll(paramHashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TreeSet<Chord> getMajorTransposeChordSet() {
/* 650 */     if (null == majorTransposeSet) {
/*     */ 
/*     */       
/* 653 */       majorTransposeSet = new TreeSet<Chord>();
/* 654 */       minorTransposeSet = new TreeSet<Chord>();
/* 655 */       chords.stringToChordSet(props.getProperty("keys.transpose"), majorTransposeSet, minorTransposeSet);
/*     */     } 
/* 657 */     return majorTransposeSet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TreeSet<Chord> getMinorTransposeChordSet() {
/* 663 */     getMajorTransposeChordSet();
/* 664 */     return minorTransposeSet;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SG02App.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */