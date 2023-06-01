/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Iterator;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.text.MaskFormatter;
/*     */ 
/*     */ public class ChordrcEditor
/*     */   extends OSXTab
/*     */   implements PropertyChangeListener
/*     */ {
/*     */   protected DefaultListModel<Chord> m_modelChords;
/*     */   protected JList<Chord> m_listChords;
/*     */   protected Chord m_displayChord;
/*     */   protected JFormattedTextField m_textfieldName;
/*     */   protected JFormattedTextField[] m_textfieldFrets;
/*     */   protected JFormattedTextField[] m_textfieldFingers;
/*     */   
/*     */   public static void editChordrc(Component paramComponent, boolean paramBoolean) {
/*  46 */     ChordrcEditor chordrcEditor = new ChordrcEditor(paramBoolean);
/*     */     
/*  48 */     int i = JOptionPane.showOptionDialog(paramComponent, chordrcEditor, chordrcEditor.m_resources
/*     */ 
/*     */         
/*  51 */         .getString("Title.Dialog.Chordrc"), 2, -1, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (0 == i)
/*     */     {
/*  61 */       chordrcEditor.updateSG02Chords(); } 
/*     */   }
/*     */   protected JFormattedTextField m_textfieldBaseFret; protected JFormattedTextField m_textfieldKeySig; protected JTextField m_textfieldDefinition; protected GridDisplay m_gridDisplay; boolean m_bUkulele; boolean m_amPushingChordToTextfields;
/*     */   protected ChordrcEditor(boolean paramBoolean) {
/*     */     Iterator<Chord> iterator;
/*     */     MaskFormatter maskFormatter;
/*  67 */     this.m_amPushingChordToTextfields = false;
/*     */     
/*  69 */     this.m_displayChord = null;
/*  70 */     this.m_modelChords = new DefaultListModel<Chord>();
/*     */     
/*  72 */     this.m_bUkulele = paramBoolean;
/*     */ 
/*     */     
/*  75 */     if (this.m_bUkulele) {
/*  76 */       iterator = SG02App.chords.iteratorUkulele();
/*     */     } else {
/*  78 */       iterator = SG02App.chords.iterator();
/*     */     } 
/*  80 */     while (iterator.hasNext()) {
/*     */       
/*  82 */       Chord chord = iterator.next();
/*     */       
/*  84 */       if (2 == chord.getSource()) {
/*     */         
/*     */         try {
/*  87 */           this.m_modelChords.addElement((Chord)chord.clone());
/*     */         }
/*  89 */         catch (CloneNotSupportedException cloneNotSupportedException) {}
/*     */       }
/*     */     } 
/*     */     
/*  93 */     this.m_listChords = new JList<Chord>(this.m_modelChords);
/*  94 */     this.m_listChords.setCellRenderer(new ChordListCellRenderer());
/*  95 */     if (SG02App.isQuaqua)
/*  96 */       this.m_listChords.putClientProperty("Quaqua.List.style", "striped"); 
/*  97 */     this.m_listChords.setSelectionMode(0);
/*  98 */     this.m_listChords.setSelectedIndex(0);
/*     */ 
/*     */     
/* 101 */     ListSelectionListener listSelectionListener = new ListSelectionListener()
/*     */       {
/*     */         public void valueChanged(ListSelectionEvent param1ListSelectionEvent)
/*     */         {
/* 105 */           ChordrcEditor.this.updateControlContents();
/*     */         }
/*     */       };
/* 108 */     this.m_listChords.addListSelectionListener(listSelectionListener);
/*     */     
/* 110 */     FocusListener focusListener = new FocusListener()
/*     */       {
/*     */         public void focusGained(FocusEvent param1FocusEvent)
/*     */         {
/* 114 */           ChordrcEditor.this.m_textfieldName.requestFocusInWindow();
/*     */         }
/*     */         public void focusLost(FocusEvent param1FocusEvent) {}
/*     */       };
/* 118 */     this.m_listChords.addFocusListener(focusListener);
/*     */ 
/*     */     
/* 121 */     Dimension dimension = getControlSpacing();
/*     */     
/* 123 */     GridBagLayout gridBagLayout = new GridBagLayout();
/* 124 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 125 */     setLayout(gridBagLayout);
/* 126 */     gridBagConstraints.weightx = 0.0D;
/*     */     
/* 128 */     Insets insets = new Insets(dimension.height, dimension.width, 0, 0);
/*     */ 
/*     */ 
/*     */     
/* 132 */     JLabel jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Chords"));
/* 133 */     gridBagConstraints.anchor = 17;
/* 134 */     gridBagConstraints.fill = 0;
/* 135 */     gridBagConstraints.gridx = 0;
/* 136 */     gridBagConstraints.gridy = 0;
/* 137 */     gridBagConstraints.gridwidth = 2;
/* 138 */     gridBagConstraints.insets = insets;
/* 139 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 140 */     add(jLabel);
/*     */     
/* 142 */     JScrollPane jScrollPane = new JScrollPane(this.m_listChords);
/*     */ 
/*     */     
/* 145 */     jScrollPane.setAlignmentX(0.0F);
/* 146 */     gridBagConstraints.fill = 1;
/* 147 */     gridBagConstraints.gridy = 1;
/* 148 */     gridBagConstraints.gridwidth = 2;
/* 149 */     gridBagConstraints.gridheight = 7;
/* 150 */     gridBagConstraints.insets = insets;
/* 151 */     gridBagLayout.setConstraints(jScrollPane, gridBagConstraints);
/* 152 */     add(jScrollPane);
/* 153 */     if (SG02App.isMac) {
/*     */ 
/*     */ 
/*     */       
/* 157 */       jScrollPane.setVerticalScrollBarPolicy(22);
/* 158 */       jScrollPane.setHorizontalScrollBarPolicy(32);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 163 */     gridBagConstraints.gridy += gridBagConstraints.gridheight;
/* 164 */     gridBagConstraints.gridheight = 1;
/*     */     
/* 166 */     JButton jButton1 = new JButton();
/* 167 */     jButton1.setAction(new AbstractAction(this.m_resources
/* 168 */           .getString("Command.Chordrc.Add"))
/*     */         {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/* 172 */             Chord chord = new Chord("New chord", 0, 0, 0, 0, 0, 0, 0, (byte)2, false);
/* 173 */             ChordrcEditor.this.m_modelChords.addElement(chord);
/* 174 */             ChordrcEditor.this.m_listChords.setSelectedIndex(ChordrcEditor.this.m_modelChords.getSize() - 1);
/* 175 */             ChordrcEditor.this.m_textfieldName.requestFocusInWindow();
/*     */           }
/*     */         });
/*     */     
/* 179 */     gridBagConstraints.anchor = 17;
/* 180 */     gridBagConstraints.fill = 0;
/* 181 */     gridBagConstraints.gridx = 0;
/* 182 */     gridBagConstraints.gridwidth = 1;
/* 183 */     gridBagConstraints.insets = insets;
/* 184 */     gridBagLayout.setConstraints(jButton1, gridBagConstraints);
/* 185 */     add(jButton1);
/*     */     
/* 187 */     JButton jButton2 = new JButton();
/* 188 */     jButton2.setAction(new AbstractAction(this.m_resources
/* 189 */           .getString("Command.Chordrc.Del"))
/*     */         {
/*     */           
/*     */           public void actionPerformed(ActionEvent param1ActionEvent)
/*     */           {
/*     */             try {
/* 195 */               Object object = ChordrcEditor.this.m_listChords.getSelectedValue();
/* 196 */               ChordrcEditor.this.m_modelChords.removeElement(object);
/*     */             }
/* 198 */             catch (Exception exception) {}
/*     */           }
/*     */         });
/*     */     
/* 202 */     gridBagConstraints.gridx++;
/* 203 */     gridBagLayout.setConstraints(jButton2, gridBagConstraints);
/* 204 */     add(jButton2);
/*     */ 
/*     */ 
/*     */     
/* 208 */     gridBagConstraints.gridy = 2;
/*     */     
/* 210 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Name"));
/* 211 */     gridBagConstraints.anchor = 13;
/* 212 */     gridBagConstraints.fill = 0;
/* 213 */     gridBagConstraints.gridx = 4;
/* 214 */     gridBagConstraints.gridwidth = 1;
/* 215 */     gridBagConstraints.gridheight = 1;
/* 216 */     gridBagConstraints.insets = insets;
/* 217 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 218 */     add(jLabel);
/*     */     
/* 220 */     this.m_textfieldName = new JFormattedTextField();
/* 221 */     this.m_textfieldName.addPropertyChangeListener(this);
/* 222 */     gridBagConstraints.fill = 2;
/* 223 */     gridBagConstraints.gridx++;
/* 224 */     gridBagConstraints.gridwidth = 3;
/* 225 */     gridBagConstraints.insets = insets;
/* 226 */     gridBagLayout.setConstraints(this.m_textfieldName, gridBagConstraints);
/* 227 */     add(this.m_textfieldName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 233 */       maskFormatter = new MaskFormatter("*");
/* 234 */       maskFormatter.setValidCharacters("0123456789ox-");
/*     */     }
/* 236 */     catch (ParseException parseException) {
/*     */       
/* 238 */       parseException.printStackTrace();
/* 239 */       maskFormatter = new MaskFormatter();
/*     */     } 
/*     */     
/* 242 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Frets"));
/* 243 */     gridBagConstraints.anchor = 13;
/* 244 */     gridBagConstraints.fill = 0;
/* 245 */     gridBagConstraints.gridx = 4;
/* 246 */     gridBagConstraints.gridy++;
/* 247 */     gridBagConstraints.gridwidth = 1;
/* 248 */     gridBagConstraints.insets = insets;
/* 249 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 250 */     add(jLabel);
/*     */     
/* 252 */     this.m_textfieldFrets = new JFormattedTextField[6]; byte b;
/* 253 */     for (b = 0; b < 6; b++) {
/*     */       
/* 255 */       this.m_textfieldFrets[b] = new JFormattedTextField(maskFormatter);
/* 256 */       this.m_textfieldFrets[b].setColumns(1);
/* 257 */       this.m_textfieldFrets[b].addPropertyChangeListener(this);
/* 258 */       gridBagConstraints.fill = 2;
/* 259 */       gridBagConstraints.gridx++;
/* 260 */       gridBagConstraints.insets = insets;
/* 261 */       gridBagLayout.setConstraints(this.m_textfieldFrets[b], gridBagConstraints);
/* 262 */       add(this.m_textfieldFrets[b]);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 269 */       maskFormatter = new MaskFormatter("*");
/* 270 */       maskFormatter.setValidCharacters("12345x-");
/*     */     }
/* 272 */     catch (ParseException parseException) {
/*     */       
/* 274 */       parseException.printStackTrace();
/*     */     } 
/*     */     
/* 277 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Fings"));
/* 278 */     gridBagConstraints.anchor = 13;
/* 279 */     gridBagConstraints.fill = 0;
/* 280 */     gridBagConstraints.gridx = 4;
/* 281 */     gridBagConstraints.gridy++;
/* 282 */     gridBagConstraints.gridwidth = 1;
/* 283 */     gridBagConstraints.insets = insets;
/* 284 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 285 */     add(jLabel);
/*     */     
/* 287 */     this.m_textfieldFingers = new JFormattedTextField[6];
/* 288 */     for (b = 0; b < 6; b++) {
/*     */       
/* 290 */       this.m_textfieldFingers[b] = new JFormattedTextField(maskFormatter);
/* 291 */       this.m_textfieldFingers[b].setColumns(1);
/* 292 */       this.m_textfieldFingers[b].addPropertyChangeListener(this);
/* 293 */       gridBagConstraints.fill = 2;
/* 294 */       gridBagConstraints.gridx++;
/* 295 */       gridBagConstraints.insets = insets;
/* 296 */       gridBagLayout.setConstraints(this.m_textfieldFingers[b], gridBagConstraints);
/* 297 */       add(this.m_textfieldFingers[b]);
/*     */     } 
/*     */     
/* 300 */     if (this.m_bUkulele) {
/*     */       
/* 302 */       this.m_textfieldFrets[4].setVisible(false);
/* 303 */       this.m_textfieldFrets[5].setVisible(false);
/* 304 */       this.m_textfieldFingers[4].setVisible(false);
/* 305 */       this.m_textfieldFingers[5].setVisible(false);
/*     */     } 
/*     */ 
/*     */     
/* 309 */     NumberFormat numberFormat = NumberFormat.getIntegerInstance();
/* 310 */     numberFormat.setMaximumIntegerDigits(2);
/*     */     
/* 312 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Base"));
/* 313 */     gridBagConstraints.anchor = 17;
/* 314 */     gridBagConstraints.fill = 0;
/* 315 */     gridBagConstraints.gridx = 4;
/* 316 */     gridBagConstraints.gridy++;
/* 317 */     gridBagConstraints.gridwidth = 1;
/* 318 */     gridBagConstraints.insets = insets;
/* 319 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 320 */     add(jLabel);
/*     */     
/* 322 */     this.m_textfieldBaseFret = new JFormattedTextField();
/* 323 */     this.m_textfieldBaseFret.setColumns(2);
/* 324 */     this.m_textfieldBaseFret.addPropertyChangeListener(this);
/* 325 */     gridBagConstraints.gridx++;
/* 326 */     gridBagConstraints.gridwidth = 2;
/* 327 */     gridBagLayout.setConstraints(this.m_textfieldBaseFret, gridBagConstraints);
/* 328 */     add(this.m_textfieldBaseFret);
/*     */     
/* 330 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Key"));
/* 331 */     gridBagConstraints.anchor = 13;
/* 332 */     gridBagConstraints.fill = 0;
/* 333 */     gridBagConstraints.gridx++;
/* 334 */     gridBagConstraints.gridwidth = 3;
/* 335 */     gridBagConstraints.insets = insets;
/* 336 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 337 */     add(jLabel);
/*     */     
/* 339 */     this.m_textfieldKeySig = new JFormattedTextField();
/* 340 */     this.m_textfieldKeySig.setColumns(3);
/* 341 */     this.m_textfieldKeySig.addPropertyChangeListener(this);
/* 342 */     gridBagConstraints.fill = 2;
/* 343 */     gridBagConstraints.gridx += 3;
/* 344 */     gridBagConstraints.gridwidth = 4;
/* 345 */     gridBagLayout.setConstraints(this.m_textfieldKeySig, gridBagConstraints);
/* 346 */     add(this.m_textfieldKeySig);
/*     */ 
/*     */ 
/*     */     
/* 350 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Definition"));
/* 351 */     gridBagConstraints.anchor = 13;
/* 352 */     gridBagConstraints.fill = 0;
/* 353 */     gridBagConstraints.gridx = 4;
/* 354 */     gridBagConstraints.gridy++;
/* 355 */     gridBagConstraints.gridwidth = 1;
/* 356 */     gridBagConstraints.insets = insets;
/* 357 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 358 */     add(jLabel);
/*     */     
/* 360 */     this.m_textfieldDefinition = new JTextField();
/* 361 */     this.m_textfieldDefinition.setColumns(6);
/* 362 */     this.m_textfieldDefinition.setEditable(false);
/* 363 */     gridBagConstraints.fill = 2;
/* 364 */     gridBagConstraints.gridx++;
/* 365 */     gridBagConstraints.gridwidth = 6;
/* 366 */     gridBagConstraints.insets = insets;
/* 367 */     gridBagLayout.setConstraints(this.m_textfieldDefinition, gridBagConstraints);
/* 368 */     add(this.m_textfieldDefinition);
/*     */ 
/*     */ 
/*     */     
/* 372 */     gridBagConstraints.gridy++;
/* 373 */     jLabel = new JLabel(this.m_resources.getString("Label.Chordrc.Preview"));
/* 374 */     gridBagConstraints.anchor = 13;
/* 375 */     gridBagConstraints.fill = 0;
/* 376 */     gridBagConstraints.gridx = 4;
/* 377 */     gridBagConstraints.gridwidth = 1;
/* 378 */     gridBagConstraints.insets = insets;
/* 379 */     gridBagLayout.setConstraints(jLabel, gridBagConstraints);
/* 380 */     add(jLabel);
/*     */     
/* 382 */     this.m_gridDisplay = new GridDisplay();
/* 383 */     gridBagConstraints.fill = 1;
/*     */     
/* 385 */     gridBagConstraints.gridx = 5;
/* 386 */     gridBagConstraints.gridwidth = 6;
/* 387 */     gridBagConstraints.gridheight = 1;
/* 388 */     gridBagLayout.setConstraints(this.m_gridDisplay, gridBagConstraints);
/* 389 */     add(this.m_gridDisplay);
/*     */     
/* 391 */     updateControlContents();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/* 397 */     super.setVisible(paramBoolean);
/* 398 */     requestFocusInWindow();
/* 399 */     this.m_listChords.requestFocusInWindow();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateControlContents() {
/* 405 */     this.m_amPushingChordToTextfields = true;
/*     */     
/* 407 */     Chord chord = this.m_listChords.getSelectedValue();
/* 408 */     if (null != chord) {
/*     */       
/* 410 */       this.m_textfieldName.setText(chord.getName());
/*     */       byte b;
/* 412 */       for (b = 0; b < 6; b++) {
/*     */         
/* 414 */         byte b1 = chord.getFretForString(b);
/*     */         
/* 416 */         if (15 == b1) {
/* 417 */           this.m_textfieldFrets[b].setValue("-");
/*     */         } else {
/* 419 */           this.m_textfieldFrets[b].setValue(String.valueOf(b1));
/*     */         } 
/*     */       } 
/* 422 */       for (b = 0; b < 6; b++) {
/*     */         
/* 424 */         byte b1 = chord.getFingeringForString(b);
/*     */         
/* 426 */         if (0 == b1) {
/* 427 */           this.m_textfieldFingers[b].setValue("-");
/*     */         } else {
/* 429 */           this.m_textfieldFingers[b].setValue(String.valueOf(b1));
/*     */         } 
/*     */       } 
/* 432 */       b = chord.getBaseFret();
/* 433 */       if (b == 0)
/* 434 */         b = 1; 
/* 435 */       this.m_textfieldBaseFret.setValue(String.valueOf(b));
/*     */       
/* 437 */       this.m_textfieldKeySig.setText(chord.getKeySignature());
/* 438 */       this.m_textfieldDefinition.setText(chord.toString());
/*     */       
/* 440 */       this.m_displayChord = chord;
/*     */     }
/*     */     else {
/*     */       
/* 444 */       this.m_textfieldName.setText(""); byte b;
/* 445 */       for (b = 0; b < 6; b++)
/* 446 */         this.m_textfieldFrets[b].setText("-"); 
/* 447 */       for (b = 0; b < 6; b++)
/* 448 */         this.m_textfieldFingers[b].setText("-"); 
/* 449 */       this.m_textfieldBaseFret.setText("");
/* 450 */       this.m_textfieldKeySig.setText("");
/* 451 */       this.m_textfieldDefinition.setText("");
/*     */       
/* 453 */       this.m_displayChord = null;
/*     */     } 
/*     */     
/* 456 */     this.m_gridDisplay.repaint();
/*     */     
/* 458 */     this.m_amPushingChordToTextfields = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateChord() {
/* 464 */     Chord chord = this.m_listChords.getSelectedValue();
/* 465 */     if (null != chord) {
/*     */       
/* 467 */       chord.setName(this.m_textfieldName.getText());
/*     */       
/* 469 */       chord.setFrets(this.m_textfieldFrets[0]
/* 470 */           .getText().charAt(0), this.m_textfieldFrets[1]
/* 471 */           .getText().charAt(0), this.m_textfieldFrets[2]
/* 472 */           .getText().charAt(0), this.m_textfieldFrets[3]
/* 473 */           .getText().charAt(0), this.m_textfieldFrets[4]
/* 474 */           .getText().charAt(0), this.m_textfieldFrets[5]
/* 475 */           .getText().charAt(0));
/* 476 */       chord.setFingerings(this.m_textfieldFingers[0]
/* 477 */           .getText().charAt(0), this.m_textfieldFingers[1]
/* 478 */           .getText().charAt(0), this.m_textfieldFingers[2]
/* 479 */           .getText().charAt(0), this.m_textfieldFingers[3]
/* 480 */           .getText().charAt(0), this.m_textfieldFingers[4]
/* 481 */           .getText().charAt(0), this.m_textfieldFingers[5]
/* 482 */           .getText().charAt(0));
/*     */       
/* 484 */       byte b = Byte.valueOf(this.m_textfieldBaseFret.getText()).byteValue();
/* 485 */       if (b == 0) {
/*     */         
/* 487 */         b = 1;
/* 488 */         this.m_textfieldBaseFret.setText("1");
/*     */       } 
/* 490 */       chord.setBaseFret(b);
/* 491 */       chord.setKeySignature(this.m_textfieldKeySig.getText());
/* 492 */       chord.setUkulele(this.m_bUkulele);
/* 493 */       this.m_textfieldDefinition.setText(chord.toString());
/*     */       
/* 495 */       this.m_listChords.repaint();
/*     */     } 
/*     */     
/* 498 */     this.m_gridDisplay.repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 504 */     if (this.m_amPushingChordToTextfields) {
/*     */       return;
/*     */     }
/* 507 */     updateChord();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateSG02Chords() {
/* 515 */     if (this.m_bUkulele) {
/* 516 */       SG02App.chords.clearAllUserDefinedUkuleleChords();
/*     */     } else {
/* 518 */       SG02App.chords.clearAllUserDefinedChords();
/*     */     } 
/* 520 */     int i = this.m_modelChords.getSize();
/* 521 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 523 */       Chord chord = this.m_modelChords.elementAt(b);
/* 524 */       if (this.m_bUkulele) {
/* 525 */         SG02App.chords.addUkulele(chord);
/*     */       } else {
/* 527 */         SG02App.chords.add(chord);
/*     */       } 
/*     */     } 
/* 530 */     SG02App.writeChordrc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class GridDisplay
/*     */     extends JComponent
/*     */   {
/*     */     public Dimension getMinimumSize() {
/* 540 */       return getPreferredSize();
/*     */     }
/*     */ 
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 545 */       return new Dimension(50, 150);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 550 */       super.paintComponent(param1Graphics);
/*     */ 
/*     */       
/*     */       try {
/* 554 */         Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 555 */         graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 556 */         graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/* 557 */         graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
/* 558 */         graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
/* 559 */         graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/* 560 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*     */         
/* 562 */         Dimension dimension = getSize();
/*     */         
/* 564 */         int i = dimension.width;
/* 565 */         if (dimension.height < dimension.width) {
/* 566 */           i = dimension.height;
/*     */         }
/* 568 */         if (i > 120)
/*     */         {
/* 570 */           i = 120;
/*     */         }
/*     */         
/* 573 */         int j = (dimension.width - i) / 2;
/* 574 */         int k = (dimension.height - i) / 2;
/*     */         
/* 576 */         if (null != ChordrcEditor.this.m_displayChord)
/*     */         {
/* 578 */           GridPrinter gridPrinter = new GridPrinter((Graphics2D)param1Graphics, param1Graphics.getFont(), param1Graphics.getColor());
/* 579 */           gridPrinter.setDrawName(false);
/* 580 */           gridPrinter.drawGrid(ChordrcEditor.this.m_displayChord, j, k, i);
/* 581 */           System.err.println(ChordrcEditor.this.m_displayChord.toString());
/*     */         }
/*     */       
/* 584 */       } catch (Exception exception) {
/*     */         
/* 586 */         exception.printStackTrace();
/*     */       } 
/*     */     }
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
/*     */   protected class ChordListCellRenderer
/*     */     extends DefaultListCellRenderer
/*     */   {
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 606 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*     */       
/* 608 */       if (SG02App.isQuaqua) {
/* 609 */         setOpaque(false);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 614 */         setText(((Chord)param1Object).getName());
/*     */       }
/* 616 */       catch (Exception exception) {}
/*     */       
/* 618 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\ChordrcEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */