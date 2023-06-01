/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.TreeSet;
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
/*     */ public class ChordMap
/*     */ {
/*     */   private HashMap<ChordKey, Chord> map;
/*     */   private HashMap<ChordKey, Chord> mapUkulele;
/*     */   
/*     */   public ChordMap(int paramInt, float paramFloat) {
/*  23 */     this.map = new HashMap<ChordKey, Chord>(paramInt, paramFloat);
/*  24 */     this.mapUkulele = new HashMap<ChordKey, Chord>(paramInt, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChordMap() {
/*  30 */     this.map = new HashMap<ChordKey, Chord>();
/*  31 */     this.mapUkulele = new HashMap<ChordKey, Chord>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Chord add(Chord paramChord) {
/*  37 */     ChordKey chordKey = new ChordKey(paramChord.getName(), paramChord.getKeySignature());
/*  38 */     this.map.put(chordKey, paramChord);
/*  39 */     return paramChord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Chord addUkulele(Chord paramChord) {
/*  45 */     ChordKey chordKey = new ChordKey(paramChord.getName(), paramChord.getKeySignature());
/*  46 */     this.mapUkulele.put(chordKey, paramChord);
/*  47 */     return paramChord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Chord find(String paramString, Chord paramChord) {
/*  53 */     String str = null;
/*  54 */     if (null != paramChord) {
/*  55 */       str = paramChord.getName();
/*     */     }
/*  57 */     ChordKey chordKey = new ChordKey(paramString, str);
/*     */     
/*  59 */     Chord chord = this.map.get(chordKey);
/*     */     
/*  61 */     if (null != chord) {
/*  62 */       return chord;
/*     */     }
/*     */     
/*  65 */     chordKey.key = null;
/*  66 */     return this.map.get(chordKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Chord findUkulele(String paramString, Chord paramChord) {
/*  72 */     String str = null;
/*  73 */     if (null != paramChord) {
/*  74 */       str = paramChord.getName();
/*     */     }
/*  76 */     ChordKey chordKey = new ChordKey(paramString, str);
/*     */     
/*  78 */     Chord chord = this.mapUkulele.get(chordKey);
/*     */     
/*  80 */     if (null != chord) {
/*  81 */       return chord;
/*     */     }
/*     */     
/*  84 */     chordKey.key = null;
/*  85 */     return this.mapUkulele.get(chordKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Chord> iterator() {
/*  91 */     return this.map.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Chord> iteratorUkulele() {
/*  97 */     return this.mapUkulele.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAllUserDefinedChords() {
/* 103 */     Iterator<Chord> iterator = iterator();
/* 104 */     while (iterator.hasNext()) {
/*     */       
/* 106 */       Chord chord = iterator.next();
/* 107 */       if (2 == chord.getSource()) {
/* 108 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearAllUserDefinedUkuleleChords() {
/* 115 */     Iterator<Chord> iterator = iteratorUkulele();
/* 116 */     while (iterator.hasNext()) {
/*     */       
/* 118 */       Chord chord = iterator.next();
/* 119 */       if (2 == chord.getSource()) {
/* 120 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 127 */     return "Chord Map:\n" + this.map.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stringToChordSet(String paramString, TreeSet<Chord> paramTreeSet) {
/* 133 */     String[] arrayOfString = paramString.split("\\s");
/* 134 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*     */       
/* 136 */       Chord chord = find(arrayOfString[b], null);
/* 137 */       if (null != chord) {
/* 138 */         paramTreeSet.add(chord);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stringToChordSet(String paramString, TreeSet<Chord> paramTreeSet1, TreeSet<Chord> paramTreeSet2) {
/* 145 */     String[] arrayOfString = paramString.split("\\s");
/* 146 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*     */       
/* 148 */       Chord chord = find(arrayOfString[b], null);
/* 149 */       if (null != chord)
/*     */       {
/* 151 */         if (chord.isMajor() && null != paramTreeSet1) {
/* 152 */           paramTreeSet1.add(chord);
/* 153 */         } else if (chord.isMinor() && null != paramTreeSet2) {
/* 154 */           paramTreeSet2.add(chord);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class ChordKey
/*     */   {
/*     */     public String name;
/*     */     public String key;
/*     */     
/*     */     public ChordKey(String param1String1, String param1String2) {
/* 167 */       this.name = param1String1; this.key = param1String2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 173 */       if (getClass() != param1Object.getClass()) {
/* 174 */         return false;
/*     */       }
/* 176 */       ChordKey chordKey = (ChordKey)param1Object;
/*     */       
/* 178 */       return ((chordKey.name == this.name || chordKey.name.equals(this.name)) && (chordKey.key == this.key || chordKey.key.equals(this.key)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 184 */       int i = this.name.hashCode();
/*     */       
/* 186 */       if (null != this.key) {
/* 187 */         i += this.key.hashCode();
/*     */       }
/* 189 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 194 */       return this.name + "(" + this.key + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\ChordMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */