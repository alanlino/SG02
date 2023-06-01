/*      */ package com.tenbyten.SG02;

/*      */
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.LineNumberReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.text.StringCharacterIterator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeSet;

/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */ public class SongFile/*      */ implements Cloneable
/*      */ {
	/*      */ private String							m_tag;
	


	/*      */ private String							m_strTitle;
	/*      */ private String							m_strSubtitle;
	/*      */ private String							m_strDisplayName;
	/*      */ private String							m_strCopyright;
	/*      */ private boolean							m_bParsedOnce;
	/*      */ private ArrayList<String>				m_artists;
	/*      */ private ArrayList<String>				m_tags;
	/*      */ private HashMap<String, String>			m_mapDataKeysToValues;
	/*      */ private int								m_nCapo;
	/*      */ private File								m_filePath;
	/*      */ private int								m_nTranspose;
	/*      */ private boolean							m_bPrintChords;
	/* 40 */ private final String						kKeyFooterFormat	= "ftrfmtsg02";
	private boolean										m_bPrintGrids;
	private boolean										m_bAutoSpace;
	private boolean										m_bEncodingDefault;
	private boolean										m_bEncodingUTF8;
	private boolean										m_bEncodingUTF16;
	private int											m_nCurrentKeyIndex;
	private ArrayList<Chord>							m_keys;
	private TreeSet<Chord>								m_setChordsSong;
	private TreeSet<Chord>								m_setKeysFriendly;
	/*      */ private TreeSet<Chord>					m_setChordsNoGrids;
	/*      */ private ChordMap							m_mapChordsSongDefined;
	/*      */ private ArrayList<SongOutput.Paragraph>	m_paragraphs;
	
	/*      */
	/*      */ public SongFile(File paramFile) {
		/* 46 */ this.m_filePath = paramFile;
		/* 47 */ this.m_setChordsSong = new TreeSet<Chord>();
		/* 48 */ this.m_setKeysFriendly = new TreeSet<Chord>();
		/* 49 */ this.m_setChordsNoGrids = new TreeSet<Chord>();
		/* 50 */ this.m_mapChordsSongDefined = new ChordMap();
		/* 51 */ this.m_mapDataKeysToValues = new HashMap<String, String>();
		/* 52 */ this.m_artists = new ArrayList<String>();
		
		/* 52 */ this.m_tags = new ArrayList<String>();
		/*      */
		/* 54 */ this.m_bEncodingDefault = false;
		/* 55 */ this.m_bEncodingUTF8 = false;
		/* 56 */ this.m_bEncodingUTF16 = false;
		/*      */
		/* 58 */ setTranspose(0);
		/* 59 */ setCapo(0);
		/*      */
		/* 61 */ this.m_nCurrentKeyIndex = -1;
		/*      */
		/* 63 */ this.m_bParsedOnce = false;
		/* 64 */ parseTitlesAndKey();
		/* 65 */ this.m_bParsedOnce = true;
		/*      */
		/* 67 */ setTranspose(Integer.parseInt(SG02App.props.getProperty("transpose")));
		/* 68 */ setPrintChords(('y' == SG02App.props.getProperty("print.chords").charAt(0)));
		/* 69 */ setPrintGrids(('y' == SG02App.props.getProperty("grids.print").charAt(0)));
		/* 70 */ setAutoSpace(('y' == SG02App.props.getProperty("print.autospace").charAt(0)));
		/*      */
		/* 72 */ if (SG02App.isDebug) {
			/* 73 */ System.err.println("Data Key/Value Map: " + this.m_mapDataKeysToValues.toString());
			/*      */ }
		/*      */ }
		
	/*      */
	/*      */
	/*      */ public Object clone() throws CloneNotSupportedException {
		/* 79 */ return super.clone();
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ public String toString() {
		/* 85 */ if (null == this.m_strDisplayName)/* 86 */ this.m_strDisplayName = SongTOC.makeTitleString(this);
		/* 87 */ return this.m_strDisplayName;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public ChordMap getSongDefinedChords() {
		/* 95 */ return this.m_mapChordsSongDefined;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public Map.Entry<String, String>[] getKeyValueMapEntries() {
		/* 102 */ return (Map.Entry<String, String>[]) this.m_mapDataKeysToValues.entrySet().toArray((Object[]) new Map.Entry[0]);
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ public void setTranspose(int paramInt) {
		/* 108 */ this.m_nTranspose = paramInt;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void setTranspose(Chord chord) {
		if (null == this.m_keys) {
			throw new NullPointerException();
		}
		this.m_nTranspose = 0;
		int n = this.m_nCurrentKeyIndex < 0 ? 0 : this.m_nCurrentKeyIndex;
		int n2 = this.getKeySignature(n).getTransposeValue();
		int n3 = chord.getTransposeValue();
		n3 = n3 < 0 ? (n3 -= 12) : (n3 += 12);
		this.setTranspose(n3 - n2);
	}
	
	/*      */
	/* 134 */ public void setCapo(int paramInt) {
		this.m_nCapo = paramInt;
	}
	
	/* 135 */ public void setPrintChords(boolean paramBoolean) {
		this.m_bPrintChords = paramBoolean;
	}
	
	/* 136 */ public void setAutoSpace(boolean paramBoolean) {
		this.m_bAutoSpace = paramBoolean;
	}
	
	public void setPrintGrids(boolean paramBoolean) {
		/* 137 */ this.m_bPrintGrids = paramBoolean;
		/*      */ }
		
	public boolean isSongFile() {
		/* 139 */ return (null != this.m_strTitle && 0 != this.m_strTitle.length());
		/*      */ }
		
	/* 141 */ public String getTitle() {
		return this.m_strTitle;
	}
	
	public ArrayList<String> getTags() {
		/* 244 */ return this.m_tags;
		/*      */ }
	
	
	public String getTag() {
		/* 248 */ if (this.m_tags.size() > 0) {
			/*      */
			/* 250 */ String str = new String();
			/* 251 */ boolean bool = true;
			/* 252 */ Enumeration<String> enumeration = Collections.enumeration(this.m_tags);
			/* 253 */ while (enumeration.hasMoreElements()) {
				/*      */
				/* 255 */ if (bool) {
					/* 256 */ bool = false;
					/*      */ } else {
					/* 258 */ str = str + ", ";
					/* 259 */ }
				str = str + (String) enumeration.nextElement();
				/*      */ }
			/* 261 */ return str;
			/*      */ }
		/* 263 */ return null;
		/*      */ }

	public void setTag(String m_tag) {
		this.m_tag = m_tag;
	}
	
	/* 142 */ public String getSubtitle() {
		return this.m_strSubtitle;
	}
	
	/* 143 */ public String getCopyright() {
		return this.m_strCopyright;
	}
	
	public File getPath() {
		/* 144 */ return this.m_filePath;
		/*      */ }
		
	/*      */
	public int getTranspose() {
        boolean bl;
        boolean bl2 = bl = 'y' == SG02App.props.getProperty("capo.use").charAt(0);
        if (bl) {
            int n = Integer.parseInt(SG02App.props.getProperty("capo.max"));
            int n2 = this.m_nTranspose + this.m_nCapo;
            if ((n2 %= 12) < 0) {
                return n2;
            }
            if (n2 > n) {
                return n2;
            }
            return 0;
        }
        int n = this.m_nTranspose + this.m_nCapo;
        if (null != this.m_keys && 0 != this.m_keys.size()) {
            int n3;
            String string;
            Chord chord;
            int n4 = this.m_nCurrentKeyIndex < 0 ? 0 : this.m_nCurrentKeyIndex;
            Chord chord2 = this.m_keys.get(n4);
            int n5 = this.getCapo();
            if (n5 > 0 && (chord = SG02App.chords.find(string = Chord.transposeChord(chord2.getName(), n5), null)) != null) {
                chord2 = chord;
            }
            if (0 != n && (chord = SG02App.chords.find(string = Chord.transposeChord(chord2.getName(), n), null)) != null) {
                chord2 = chord;
            }
            if ((n3 = chord2.getTransposeValue()) < 0) {
                n -= 12;
            }
        }
        return n;
    }
		
	/*      */
	/*      */
	/*      */
	/*      */ public int getRawTranspose() {
		/* 206 */ return this.m_nTranspose;
		/*      */ }
		
	/*      */
	/*      */
	/*      */ public int getCapo() {
		/* 211 */ boolean bool = ('y' == SG02App.props.getProperty("capo.use").charAt(0)) ? true : false;
		/* 212 */ if (bool) {
			/*      */
			/*      */
			/* 215 */ if (this.m_nTranspose != 0) {
				/*      */
				/* 217 */ int i = Integer.parseInt(SG02App.props.getProperty("capo.max"));
				/* 218 */ int j = this.m_nTranspose + this.m_nCapo;
				/*      */
				/* 220 */ j %= 12;
				/*      */
				/*      */
				/* 223 */ if (j < 0)
				/*      */ {
					/* 225 */ return 0;
					/*      */ }
				/* 227 */ if (j > i)
				/*      */ {
					/* 229 */ return 0;
					/*      */ }
				/*      */
				/*      */
				/* 233 */ return j;
				/*      */ }
			/*      */
			/* 236 */ return this.m_nCapo;
			/*      */ }
		/* 238 */ return 0;
		/*      */ }
		
	/*      */
	/* 241 */ public boolean getPrintChords() {
		return this.m_bPrintChords;
	}
	
	/* 242 */ public boolean getPrintGrids() {
		return this.m_bPrintGrids;
	}
	
	/* 243 */ public boolean getAutoSpace() {
		return this.m_bAutoSpace;
	}
	
	
	
	public ArrayList<String> getArtists() {
		/* 244 */ return this.m_artists;
		/*      */ }
	
		
	/*      */
	/*      */ public String getArtist() {
		/* 248 */ if (this.m_artists.size() > 0) {
			/*      */
			/* 250 */ String str = new String();
			/* 251 */ boolean bool = true;
			/* 252 */ Enumeration<String> enumeration = Collections.enumeration(this.m_artists);
			/* 253 */ while (enumeration.hasMoreElements()) {
				/*      */
				/* 255 */ if (bool) {
					/* 256 */ bool = false;
					/*      */ } else {
					/* 258 */ str = str + ", ";
					/* 259 */ }
				str = str + (String) enumeration.nextElement();
				/*      */ }
			/* 261 */ return str;
			/*      */ }
		/* 263 */ return null;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public Chord getKeySignature() {
			
		 int n = this.m_nCurrentKeyIndex < 0 ? 0 : this.m_nCurrentKeyIndex;
	        return this.getKeySignature(n);
	}
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public Chord getKeySignature(int paramInt) {
		/* 279 */ if (null != this.m_keys && 0 != this.m_keys.size()) {
			/*      */
			/* 281 */ if (paramInt >= this.m_keys.size() || paramInt < 0) {
				/* 282 */ paramInt = this.m_keys.size() - 1;
				/*      */ }
			/* 284 */ Chord chord = this.m_keys.get(paramInt);
			/*      */
			/*      */
			/* 287 */ int i = getTranspose();
			/* 288 */ int j = getCapo();
			/*      */
			/* 290 */ if (j > 0) {
				/*      */
				/* 292 */ String str = Chord.transposeChord(chord.getName(), j);
				/* 293 */ Chord chord1 = SG02App.chords.find(str, null);
				/* 294 */ if (chord1 != null) {
					/* 295 */ chord = chord1;
					/*      */ }
				/*      */ }
			/* 298 */ if (0 != i) {
				/*      */
				/* 300 */ String str = Chord.transposeChord(chord.getName(), i);
				/* 301 */ Chord chord1 = SG02App.chords.find(str, null);
				/* 302 */ if (chord1 != null) {
					/* 303 */ chord = chord1;
					/*      */ }
				/*      */ }
			/* 306 */ return chord;
			/*      */ }
		/*      */
		/* 309 */ return null;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public int getKeySignatureCount() {
		/* 316 */ if (null != this.m_keys)/* 317 */ return this.m_keys.size();
		/* 318 */ return 0;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ public String getKeySignaturesString() {
		/* 324 */ String str = "";
		/*      */
		/* 326 */ int i = getTranspose();
		/*      */
		/* 328 */ int j = getKeySignatureCount();
		/* 329 */ if (0 != j) {
			/*      */
			/* 331 */ Chord chord = getKeySignature();
			/* 332 */ if (chord != null) {
				/*      */
				/* 334 */ int k = getCapo();
				/*      */
				/* 336 */ boolean bool = ('y' == SG02App.props.getProperty("print.chords.doremi").charAt(0)) ? true : false;
				/* 337 */ str = bool ? chord.getDoReMiName() : chord.getName();
				/*      */
				/* 339 */ for (byte b = 1; b < j; b++)
				/*      */ {
					/* 341 */ str = str + "→" + getKeySignature(b).getName();
					/*      */ }
				/*      */
				/* 344 */ if (k > 0)
				/*      */ {
					/* 346 */ ResourceBundle resourceBundle = ResourceBundle.getBundle("com.tenbyten.SG02.SG02Bundle");
					/* 347 */ String str1 = resourceBundle.getString("Label.KeyColumn.Capo.Begin");
					/* 348 */ String str2 = resourceBundle.getString("Label.KeyColumn.Capo.End");
					/* 349 */ str = str + str1 + String.valueOf(k) + str2;
					/*      */ }
				/*      */
				/*      */ }
			/* 353 */ } else if (0 != i) {
			/*      */
			/* 355 */ if (0 < i) {
				/* 356 */ str = "+" + String.valueOf(i);
				/*      */ } else {
				/* 358 */ str = String.valueOf(i);
				/*      */ }
			/*      */ }
		/* 361 */ return str;
		/*      */ }
		
	/*      */
	/* 364 */ public boolean isUTF8() {
		return this.m_bEncodingUTF8;
	}
	
	public boolean isUTF16() {
		/* 365 */ return this.m_bEncodingUTF16;
		/*      */ }
		
	/*      */
	/*      */
	/*      */ public int printSong(SongOutput paramSongOutput) {
		/* 370 */ if (SG02App.isDebug) {
			/* 371 */ System.err.println(this.m_strDisplayName + " printing...");
			/*      */ }
		/* 373 */ this.m_setChordsSong.clear();
		/*      */
		/* 375 */ this.m_nCurrentKeyIndex = -1;
		/*      */
		/* 377 */ int i = 2;
		/*      */
		/*      */
		/*      */ try {
			/* 381 */ paramSongOutput.setParagraphs(this.m_paragraphs);
			/* 382 */ i = parseSong(paramSongOutput, /* 383 */ ('y' == SG02App.props.getProperty("print.autospace").charAt(0)), /* 384 */ getPrintChords(), false, false);
			/*      */
			/*      */
			/* 387 */ if (SG02App.isDebug)
			/*      */ {
				/* 389 */ String str = "Defined chords:\n";
				/* 390 */ System.err.println(str + this.m_setChordsSong.toString());
				/*      */ }
			/*      */
			/* 393 */ } catch (Exception exception) {
			/*      */
			/* 395 */ i = 0;
			/*      */ }
		/*      */
		/* 398 */ if (0 == i) {
			/* 399 */ paramSongOutput.error("Error in " + this.m_filePath.toString());
			/*      */ }
		/* 401 */ return i;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ protected LineNumberReader openFileDetectEncoding(File paramFile) throws FileNotFoundException, IOException {
		/* 408 */ if (!this.m_bEncodingDefault && !this.m_bEncodingUTF8 && !this.m_bEncodingUTF16) {
			/*      */
			/* 410 */ FileInputStream fileInputStream = new FileInputStream(paramFile);
			/* 411 */ int i = fileInputStream.read();
			/* 412 */ int j = fileInputStream.read();
			/* 413 */ int k = fileInputStream.read();
			/* 414 */ fileInputStream.close();
			/*      */
			/* 416 */ this.m_bEncodingUTF8 = (0 == SG02App.props.getProperty("songs.encoding").compareTo("UTF-8") || (i == 239 && j == 187 && k == 191));
			/* 417 */ this.m_bEncodingUTF16 = (0 == SG02App.props.getProperty("songs.encoding").compareTo("UTF-16") || (i == 254 && j == 255) || (i == 255 && j == 254));
			/* 418 */ this.m_bEncodingDefault = (!this.m_bEncodingUTF8 && !this.m_bEncodingUTF16);
			/*      */ }
		/*      */
		/* 421 */ return openFileWithEncoding(paramFile);
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ protected LineNumberReader openFileWithEncoding(File paramFile) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		/*      */ InputStreamReader inputStreamReader;
		/* 430 */ if (this.m_bEncodingUTF16) {
			/* 431 */ inputStreamReader = new InputStreamReader(new FileInputStream(this.m_filePath), "UTF-16");
			/* 432 */ } else if (this.m_bEncodingUTF8) {
			/* 433 */ inputStreamReader = new InputStreamReader(new FileInputStream(this.m_filePath), "UTF-8");
			/*      */ }
		/*      */ else {
			/*      */
			/* 437 */ inputStreamReader = new FileReader(this.m_filePath);
			/*      */ }
		/*      */
		/* 440 */ return new LineNumberReader(inputStreamReader);
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ protected boolean parseTitlesAndKey() {
		/* 447 */ boolean bool = false;
		/*      */
		/*      */
		/*      */ try {
			/* 451 */ SongPreprocessor songPreprocessor = new SongPreprocessor();
			/*      */
			/* 453 */ int i = parseSong(songPreprocessor, true, true, false, false);
			/*      */
			/*      */
			/*      */
			/* 457 */ bool = (1 == i) ? true : false;
			/*      */
			/* 459 */ if (bool)
			/*      */ {
				/* 461 */ this.m_strTitle = songPreprocessor.getTitle();
				/* 462 */ this.m_strSubtitle = songPreprocessor.getSubtitle();
				/* 463 */ this.m_keys = songPreprocessor.getKeys();
				/* 464 */ this.m_paragraphs = songPreprocessor.getParagraphs();
				/*      */ }
			/*      */
			/* 467 */ } catch (Exception exception) {
		}
		/*      */
		/* 469 */ return bool;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ protected int parseSong(SongOutput paramSongOutput, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
		/* 475 */ int i = 2;
		/*      */
		/* 477 */ setupFriendlyChordsForGrids();
		/*      */
		/*      */
		/*      */ try {
			/* 481 */ boolean bool1 = false;
			/* 482 */ boolean bool2 = false;
			/* 483 */ boolean bool = true;
			/* 484 */ boolean bool3 = ('y' == SG02App.props.getProperty("print.chords.ukulele").charAt(0)) ? true : false;
			/*      */
			/* 486 */ boolean bool4 = ('y' == SG02App.props.getProperty("warn.chords.unknown").charAt(0)) ? true : false;
			/*      */
			/* 488 */ StringBuffer stringBuffer = new StringBuffer(100);
			/*      */
			/* 490 */ LineNumberReader lineNumberReader = openFileDetectEncoding(this.m_filePath);
			/* 491 */ String str = lineNumberReader.readLine();
			/* 492 */ StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(str);
			/*      */
			/*      */
			/*      */ do {
				/* 496 */ stringBuffer.setLength(0);
				/*      */
				/* 498 */ int j = -1;
				/* 499 */ boolean bool5 = false;
				/* 500 */ boolean bool6 = false;
				/* 501 */ boolean bool7 = false;
				/*      */
				/* 503 */ stringCharacterIterator.setText(str);
				/* 504 */ char c = stringCharacterIterator.first();
				/*      */
				/* 506 */ while (Character.MAX_VALUE != c && 2 == i) {
					
					try{
						
					
					/*      */
					/* 508 */ if ('[' == c && !paramBoolean4) {
						/*      */
						/* 510 */ if (bool2)
						/*      */ {
							/* 512 */ paramSongOutput.error("Opening a chord within a chord!");
							/*      */ }
						/*      */ else
						/*      */ {
							/* 516 */ bool2 = true;
							/* 517 */ j = stringCharacterIterator.getIndex() + 1;
							/*      */ }
						/*      */
						/* 520 */ } else if ('{' == c) {
						/*      */
						/* 522 */ bool1 = true;
						/* 523 */ j = stringCharacterIterator.getIndex() + 1;
						/*      */ }
					/* 525 */ else if (']' == c || '}' == c) {
						/*      */
						/* 527 */ if (bool2)
						/*      */ {
							/* 529 */ bool2 = false;
							/*      */
							/* 531 */ if (bool) {
								/*      */
								/* 533 */ i = paramSongOutput.printNormalSpace();
								/* 534 */ bool = false;
								/*      */ }
							/*      */
							/* 537 */ if (paramBoolean2) {
								/*      */
								/* 539 */ if (!bool5) {
									/* 540 */ i = paramSongOutput.printChordSpaceAbove();
									/*      */ }
								/* 542 */ int k = getTranspose();
								/* 543 */ String str1 = Chord.transposeChord(str, j, stringCharacterIterator.getIndex() - j, k);
								/*      */
								/*      */
								/* 546 */ Chord chord = this.m_mapChordsSongDefined.find(str1, getKeySignature());
								/*      */
								/*      */
								/* 549 */ if (null == chord)
								/*      */ {
									/* 551 */ if (bool3) {
										/* 552 */ chord = SG02App.chords.findUkulele(str1, getKeySignature());
										/*      */ } else {
										/* 554 */ chord = SG02App.chords.find(str1, getKeySignature());
										/*      */ }
									/*      */ }
								/* 557 */ if (null == chord) {
									/*      */
									/*      */
									/* 560 */ Chord chord1 = new Chord(str1, 0, 0, 0, 0, 0, 0, 0, (byte) 4, false);
									/* 561 */ char c1 = str1.charAt(0);
									/* 562 */ if ('/' != c1 && '|' != c1 && bool4)/* 563 */ paramSongOutput.error("Undefined chord: " + str1);
									/* 564 */ chord = chord1;
									/*      */
									/*      */ }
								/*      */ else {
									/*      */
									/* 569 */ this.m_setChordsSong.add(chord);
									/*      */ }
								/*      */
								/*      */
								/*      */
								/*      */
								/* 575 */ i = paramSongOutput.printChord(chord, stringBuffer.toString());
								/*      */ }
							/*      */
							/* 578 */ bool5 = true;
							/* 579 */ j = -1;
							/*      */ }
						/* 581 */ else if (bool1)
						/*      */ {
							/* 583 */ bool1 = false;
							/*      */
							/* 585 */ String str1 = str.substring(j, stringCharacterIterator.getIndex());
							/*      */
							/* 587 */ DirectiveStruct directiveStruct = doDirective(paramSongOutput, str1, paramBoolean3, paramBoolean4, bool, paramBoolean2);
							/*      */
							/* 589 */ i = directiveStruct.m_nParseStatus;
							/* 590 */ paramBoolean3 = directiveStruct.m_bInChorus;
							/* 591 */ paramBoolean4 = directiveStruct.m_bInTab;
							/* 592 */ bool = directiveStruct.m_bFirstLine;
							/*      */
							/* 594 */ bool7 = true;
							/*      */ }
						/*      */
						/*      */ }
					/* 598 */ else if (0 == stringCharacterIterator.getIndex() && '#' == c) {
						/*      */
						/*      */
						/* 601 */ stringCharacterIterator.setIndex(stringCharacterIterator.getEndIndex());
						/* 602 */ bool7 = true;
						/*      */
						/*      */
						/*      */ }
					/* 606 */ else if (!bool2 && !bool1) {
						/*      */
						/* 608 */ if (!paramBoolean2 && ' ' == c) {
							/*      */
							/* 610 */ if (0 != stringBuffer.length() && ' ' != stringBuffer.charAt(stringBuffer.length() - 1)) {
								/*      */
								/* 612 */ stringBuffer.append(c);
								/* 613 */ if (!Character.isWhitespace(c)) {
									/* 614 */ bool6 = true;
									/*      */ }
								/*      */ }
							/*      */ } else {
							/*      */
							/* 619 */ stringBuffer.append(c);
							/* 620 */ if (!Character.isWhitespace(c)) {
								/* 621 */ bool6 = true;
								/*      */ }
							/*      */ }
						/*      */ }
					/*      */
					/*      */
					/* 627 */ c = stringCharacterIterator.next();
					
				}catch(Exception e){
					e.printStackTrace();
				}
					
					/*      */ }
				/*      */
				/* 630 */ if (bool1)/* 631 */ paramSongOutput.error("Line ends while in a directive!");
				/* 632 */ if (bool2) {
					/* 633 */ paramSongOutput.error("Line ends while in a chord!");
					/*      */ }
				/* 635 */ if (!bool7) {
					/*      */
					/* 637 */ if ((paramBoolean2 && bool5) || !paramBoolean1) {
						/* 638 */ i = paramSongOutput.printChordNewLine();
						/*      */ }
					/* 640 */ if (bool && ((paramBoolean2 && paramBoolean4) || !paramBoolean4)) {
						/*      */
						/* 642 */ i = paramSongOutput.printNormalSpace();
						/* 643 */ bool = false;
						/*      */ }
					/* 645 */ if ((!bool5 || bool6 || !paramBoolean1) && ((paramBoolean2 && paramBoolean4) || !paramBoolean4)) {
						/* 646 */ i = paramSongOutput.printLyric(stringBuffer.toString());
						/*      */ }
					/*      */ }
				/* 649 */ bool1 = false;
				/* 650 */ bool2 = false;
				/*      */ }
			/* 652 */ while (2 == i && (str = lineNumberReader.readLine()) != null);
			/*      */
			/* 654 */ lineNumberReader.close();
			/*      */ }
		/* 656 */ catch (Exception exception) {
			/*      */
			/* 658 */ System.err.println("SongFile.parseSong(): caught exception " + exception.toString());
			/* 659 */ if (SG02App.isDebug) {
				/* 660 */ exception.printStackTrace();
				/*      */ }
			/*      */ }
		/* 663 */ switch (i) {
			/*      */
			/*      */ case 2:
				/* 666 */ if (getPrintGrids()) {
					/*      */
					/*      */ try {
						/*      */
						/* 670 */ TreeSet<Chord> treeSet = new TreeSet();
						/* 671 */ for (Chord chord1 : this.m_setChordsSong) {
							/*      */
							/*      */
							/* 674 */ if (!this.m_setChordsNoGrids.contains(chord1)) {
								/* 675 */ treeSet.add(chord1);
								/*      */ }
							/*      */ }
						/* 678 */ Chord chord = getKeySignature();
						/*      */
						/* 680 */ if (0 != this.m_setKeysFriendly.size() && null != chord) {
							/*      */
							/* 682 */ if (this.m_setKeysFriendly.contains(chord)) {
								/* 683 */ i = paramSongOutput.printFinalChordGrid(treeSet);
								/*      */ }
							/*      */ } else {
							/* 686 */ i = paramSongOutput.printFinalChordGrid(treeSet);
							/*      */ }
						/* 688 */ } catch (Exception exception) {
						/*      */
						/* 690 */ i = 0;
						/*      */ }
					/*      */ }
				/*      */
				/* 694 */ this.m_nCurrentKeyIndex = 0;
				/*      */
				/* 696 */ i = 1;
				/*      */ break;
			/*      */ }
		/*      */
		/*      */
		/*      */
		/*      */
		/* 703 */ return i;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ private DirectiveStruct doDirective(SongOutput paramSongOutput, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
		/* 709 */ DirectiveStruct directiveStruct = new DirectiveStruct(paramBoolean1, paramBoolean2, paramBoolean3);
		/*      */
		/* 711 */ StringTokenizer stringTokenizer = new StringTokenizer(paramString, "{:}\n");
		/* 712 */ String str = stringTokenizer.nextToken().toLowerCase();
		/*      */
		/* 714 */ if (str.equals("st") || str.equals("su") || str.startsWith("subti")) {
			/*      */
			/* 716 */ directiveStruct.m_nParseStatus = parseSubtitle(paramSongOutput, stringTokenizer);
			/*      */ }
		/* 718 */ else if (str.equals("soc") || str.startsWith("start_of_cho")) {
			/*      */
			/* 720 */ if (paramBoolean3) {
				/*      */
				/* 722 */ directiveStruct.m_nParseStatus = paramSongOutput.printNormalSpace();
				/* 723 */ directiveStruct.m_bFirstLine = false;
				/*      */ }
			/* 725 */ if (2 == directiveStruct.m_nParseStatus)
			/*      */ {
				/* 727 */ directiveStruct.m_nParseStatus = paramSongOutput.markStartOfChorus();
				/* 728 */ directiveStruct.m_bInChorus = true;
				/*      */ }
			/*      */
			/* 731 */ } else if (str.equals("eoc") || str.startsWith("end_of_cho")) {
			/*      */
			/* 733 */ if (paramBoolean1) {
				/*      */
				/* 735 */ directiveStruct.m_nParseStatus = paramSongOutput.markEndOfChorus();
				/* 736 */ directiveStruct.m_bInChorus = false;
				/*      */ } else {
				/*      */
				/* 739 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Not in a chorus.");
				/*      */ }
			/* 741 */ } else if (str.equals("sot") || str.startsWith("start_of_ta")) {
			/*      */
			/* 743 */ if (paramBoolean3 && paramBoolean4) {
				/*      */
				/* 745 */ directiveStruct.m_nParseStatus = paramSongOutput.printNormalSpace();
				/* 746 */ directiveStruct.m_bFirstLine = false;
				/*      */ }
			/* 748 */ if (2 == directiveStruct.m_nParseStatus) {
				/*      */
				/* 750 */ if (paramBoolean4)/* 751 */ directiveStruct.m_nParseStatus = paramSongOutput.markStartOfTab();
				/* 752 */ directiveStruct.m_bInTab = true;
				/*      */ } else {
				/*      */
				/* 755 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Not in a chorus.");
				/*      */ }
			/* 757 */ } else if (str.equals("eot") || str.startsWith("end_of_ta")) {
			/*      */
			/* 759 */ if (paramBoolean2) {
				/*      */
				/* 761 */ if (paramBoolean4)/* 762 */ directiveStruct.m_nParseStatus = paramSongOutput.markEndOfTab();
				/* 763 */ directiveStruct.m_bInTab = false;
				/*      */ } else {
				/*      */
				/* 766 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Not in a tab.");
				/*      */ }
			/* 768 */ } else if (str.equals("colb") || str.startsWith("column_break")) {
			/*      */
			/* 770 */ directiveStruct.m_nParseStatus = paramSongOutput.newColumn();
			/*      */ }
		/* 772 */ else if (str.equals("c") || str.startsWith("commen")) {
			/*      */
			/* 774 */ if (paramBoolean3) {
				/*      */
				/* 776 */ directiveStruct.m_nParseStatus = paramSongOutput.printNormalSpace();
				/* 777 */ directiveStruct.m_bFirstLine = false;
				/*      */ }
			/* 779 */ if (2 == directiveStruct.m_nParseStatus)
			/*      */ {
				/* 781 */ str = stringTokenizer.nextToken("}\n");
				/* 782 */ str = str.substring(1);
				/* 783 */ directiveStruct.m_nParseStatus = paramSongOutput.printComment(str);
				/*      */ }
			/*      */
			/* 786 */ } else if (str.equals("gc") || str.startsWith("guitar_commen")) {
			/*      */
			/* 788 */ if (paramBoolean4) {
				/*      */
				/* 790 */ if (paramBoolean3) {
					/*      */
					/* 792 */ directiveStruct.m_nParseStatus = paramSongOutput.printNormalSpace();
					/* 793 */ directiveStruct.m_bFirstLine = false;
					/*      */ }
				/* 795 */ if (2 == directiveStruct.m_nParseStatus)
				/*      */ {
					/* 797 */ str = stringTokenizer.nextToken("}\n");
					/* 798 */ str = str.substring(1);
					/* 799 */ directiveStruct.m_nParseStatus = paramSongOutput.printGuitarComment(str);
					/*      */ }
				/*      */
				/*      */ }
			/* 803 */ } else if (str.equals("t") || str.startsWith("title")) {
			/*      */
			/* 805 */ directiveStruct.m_nParseStatus = parseTitle(paramSongOutput, stringTokenizer);
			/*      */ }else if (str.equals("tag")) {
				/*      */
				
				/* 783 */ directiveStruct.m_nParseStatus = parseTag(paramSongOutput, stringTokenizer);
				/*      */ }
		/* 807 */ else if (str.equals("ns") || str.startsWith("new_song")) {
			/*      */
			/* 809 */ if (getPrintGrids())/* 810 */ directiveStruct.m_nParseStatus = paramSongOutput.printFinalChordGrid(this.m_setChordsSong);
			/* 811 */ this.m_setChordsSong.clear();
			/*      */
			/*      */
			/* 814 */ directiveStruct.m_nParseStatus = paramSongOutput.markNewSong();
			/*      */ }
		/* 816 */ else if (str.equals("npp") || str.startsWith("new_physical_page")) {
			/*      */
			/* 818 */ directiveStruct.m_nParseStatus = paramSongOutput.newPhysicalPage();
			/*      */ }
		/* 820 */ else if (str.equals("np") || str.startsWith("new_page")) {
			/*      */
			/* 822 */ directiveStruct.m_nParseStatus = paramSongOutput.newPage();
			/*      */ }
		/* 824 */ else if (str.startsWith("data_") || str.startsWith("d_")) {
			/*      */
			/* 826 */ char[] arrayOfChar = str.toCharArray();
			/* 827 */ byte b = 1;
			/* 828 */ for (; b < arrayOfChar.length && '_' != arrayOfChar[b]; b++)
				;
			/* 829 */ if (b + 1 < arrayOfChar.length) {
				/*      */
				/* 831 */ b++;
				/* 832 */ String str1 = str.substring(b).trim().toLowerCase();
				/* 833 */ directiveStruct.m_nParseStatus = parseMetaValue(str1, stringTokenizer);
				/*      */ } else {
				/*      */
				/* 836 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Invalid data key");
				/*      */ }
			/* 838 */ } else if (str.startsWith("footer") || str.equals("f")) {
			/*      */
			/* 840 */ str = stringTokenizer.nextToken("}\n").substring(1).trim();
			/*      */
			/*      */
			/* 843 */ this.m_mapDataKeysToValues.put("ftrfmtsg02", str);
			/*      */
			/* 845 */ directiveStruct.m_nParseStatus = 2;
			/*      */ }
		/* 847 */ else if (str.startsWith("def")) {
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
			/* 860 */ stringTokenizer = new StringTokenizer(paramString, "{:}\n");
			/* 861 */ str = stringTokenizer.nextToken();
			/*      */
			/*      */
			/* 864 */ char[] arrayOfChar = str.toCharArray();
			/*      */
			/* 866 */ byte b = 1;
			/*      */
			/* 868 */ for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++)
				;
			/* 869 */ for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++)
				;
			/*      */
			/* 871 */ String str1 = str.substring(b).intern();
			/*      */
			/* 873 */ if (stringTokenizer.hasMoreTokens()) {
				/*      */
				/* 875 */ str = stringTokenizer.nextToken("}\n");
				/* 876 */ str = str.substring(1).trim();
				/*      */
				/*      */ }
			/*      */ else {
				/*      */
				/* 881 */ str = "";
				/*      */ }
			/*      */
			/*      */
			/* 885 */ if (0 == str1.length()) {
				/*      */
				/* 887 */ arrayOfChar = str.toCharArray();
				/* 888 */ b = 0;
				/* 889 */ for (; b < arrayOfChar.length && !Character.isWhitespace(arrayOfChar[b]); b++)
					;
				/* 890 */ for (; b < arrayOfChar.length && Character.isWhitespace(arrayOfChar[b]); b++)
					;
				/*      */
				/* 892 */ str1 = str.substring(0, b).trim().intern();
				/* 893 */ str = str.substring(b);
				/*      */ }
			/*      */
			/* 896 */ if (!str1.isEmpty()) {
				/*      */ try
				/*      */ {
					/*      */
					/* 900 */ Chord chord = new Chord(str1, str, (byte) 4, false);
					/*      */
					/* 902 */ this.m_mapChordsSongDefined.add(chord);
					/* 903 */ directiveStruct.m_nParseStatus = 2;
					/*      */ }
				/* 905 */ catch (Exception exception)
				/*      */ {
					/*      */
					/* 908 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Invalid chord definition : [" + paramString + "]");
					/*      */ }
				/*      */
				/*      */ }
			/* 912 */ } else if (str.equals("key") || str.equals("k")) {
			/*      */
			/* 914 */ str = stringTokenizer.nextToken().trim();
			/* 915 */ if (str == null || str.length() == 0) {
				/* 916 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Invalid key signature : " + paramString);
				/*      */ } else {
				/* 918 */ directiveStruct.m_nParseStatus = parseKey(paramSongOutput, str);
				/*      */ }
			/* 920 */ } else if (str.equals("artist")) {
			/*      */
			/* 922 */ 		directiveStruct.m_nParseStatus = parseArtist(paramSongOutput, stringTokenizer);
			/*      */ }else if (str.equals("tag")) {
				/*      */
				
				/* 783 */ directiveStruct.m_nParseStatus = parseTag(paramSongOutput, stringTokenizer);
				/*      */ }
		/* 924 */ else if (str.equals("copyright")) {
			/*      */
			/* 926 */ directiveStruct.m_nParseStatus = parseCopyright(paramSongOutput, stringTokenizer);
			/*      */ }
		/* 928 */ else if (str.equals("capo")) {
			/*      */
			/* 930 */ directiveStruct.m_nParseStatus = parseCapo(paramSongOutput, stringTokenizer);
			/*      */ }
		/* 932 */ else if (str.equals("meta")) {
			/*      */
			/* 934 */ str = stringTokenizer.nextToken(": ").trim();
			/* 935 */ String str1 = str;
			/* 936 */ if (str1.equals("artist"))
			/*      */ {
				/* 938 */ directiveStruct.m_nParseStatus = parseArtist(paramSongOutput, stringTokenizer);
				/*      */ }
			/* 940 */ else if (str.equals("capo"))
			/*      */ {
				/* 942 */ directiveStruct.m_nParseStatus = parseCapo(paramSongOutput, stringTokenizer);
				/*      */ }
			/* 944 */ else if (str.equals("copyright"))
			/*      */ {
				/* 946 */ directiveStruct.m_nParseStatus = parseCopyright(paramSongOutput, stringTokenizer);
				/*      */ }
			/* 948 */ else if (str.equals("key"))
			/*      */ {
				/* 950 */ String str2 = stringTokenizer.nextToken("}\n");
				/* 951 */ str2 = str.substring(1).trim();
				/* 952 */ directiveStruct.m_nParseStatus = parseKey(paramSongOutput, str2);
				/*      */ }
			/* 954 */ else if (str.equals("subtitle"))
			/*      */ {
				/* 956 */ directiveStruct.m_nParseStatus = parseSubtitle(paramSongOutput, stringTokenizer);
				/*      */ }
			/* 958 */ else if (str.equals("title"))
			/*      */ {
				/* 960 */ directiveStruct.m_nParseStatus = parseTitle(paramSongOutput, stringTokenizer);
				/*      */ }
			/*      */ else
			/*      */ {
				/* 964 */ directiveStruct.m_nParseStatus = parseMetaValue(str1, stringTokenizer);
				/*      */ }
			/*      */
			/*      */ } else {
			/*      */
			/* 969 */ directiveStruct.m_nParseStatus = paramSongOutput.error("Invalid Directive: [" + str + "]");
			/*      */ }
		/*      */
		/*      */
		/* 973 */ return directiveStruct;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ private int parseTitle(SongOutput paramSongOutput, StringTokenizer paramStringTokenizer) {
		/* 979 */ String str = paramStringTokenizer.nextToken("}\n");
		/* 980 */ str = str.substring(1).trim();
		/* 981 */ return paramSongOutput.printTitle(str);
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ private int parseSubtitle(SongOutput paramSongOutput, StringTokenizer paramStringTokenizer) {
		/* 987 */ String str = paramStringTokenizer.nextToken("}\n");
		/* 988 */ str = str.substring(1).trim();
		/* 989 */ if (0 != str.length())/* 990 */ return paramSongOutput.printSubtitle(str);
		/* 991 */ return 2;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ private int parseArtist(SongOutput paramSongOutput, StringTokenizer paramStringTokenizer) {
		/* 997 */ if (!this.m_bParsedOnce) {
			/*      */
			/* 999 */ String str = paramStringTokenizer.nextToken("}\n");
			/* 1000 */ str = str.substring(1).trim();
			/* 1001 */ this.m_artists.add(str);
			/* 1002 */ return paramSongOutput.printArtist(str);
			/*      */ }
		/* 1004 */ return 2;
		/*      */ }
	
	private int parseTag(SongOutput paramSongOutput, StringTokenizer paramStringTokenizer) {
		try{
			/* 997 */// if (!this.m_bParsedOnce) {
			/*      */ this.m_tags.clear();
			/* 999 */ String str = paramStringTokenizer.nextToken("}\n");
			/* 1000 */ str = str.substring(1).trim();
			/* 1001 */ this.m_tags.add(str);
//			/* 1002 */ return paramSongOutput.printTag(str);
			/*      */ //}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		/* 1004 */ return 2;
		/*      */ }
		
		
		
	/*      */
	/*      */
	/*      */
	/*      */ private int parseCapo(SongOutput paramSongOutput, StringTokenizer paramStringTokenizer) {
		/* 1010 */ String str = paramStringTokenizer.nextToken("}\n");
		/* 1011 */ str = str.substring(1).trim();
		/* 1012 */ setCapo(Integer.valueOf(str).intValue());
		/* 1013 */ return 2;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */ private int parseCopyright(SongOutput paramSongOutput, StringTokenizer paramStringTokenizer) {
		/* 1019 */ String str = paramStringTokenizer.nextToken("}\n");
		/* 1020 */ str = str.substring(1).trim();
		/* 1021 */ this.m_strCopyright = str;
		/* 1022 */ return paramSongOutput.printCopyright(str);
		/*      */ }
		
	/*      */
	/*      */
	/*      */ private int parseKey(SongOutput paramSongOutput, String paramString) {
		/* 1027 */ Chord chord = SG02App.chords.find(paramString, null);
		/*      */
		/* 1029 */ if (chord != null) {
			/*      */
			/*      */
			/*      */
			/* 1033 */ int i = this.m_nTranspose;
			/* 1034 */ if (0 != i)
			/*      */ {
				/*      */
				/* 1037 */ String str = Chord.transposeChord(chord.getName(), i);
				/* 1038 */ chord = SG02App.chords.find(str, null);
				/*      */ }
			/*      */
			/*      */ } else {
			/*      */
			/* 1043 */ chord = new Chord(paramString, 0, 0, 0, 0, 0, 0, 0, (byte) 4, false);
			/*      */ }
		/*      */
		/* 1046 */ this.m_nCurrentKeyIndex++;
		/* 1047 */ return paramSongOutput.printKey(chord);
		/*      */ }
		    
		    /*      */
		    /*      */
		    /*      */ int parseMetaValue(String paramString, StringTokenizer paramStringTokenizer) {
		/* 1052 */ if (!this.m_bParsedOnce) {
			/*      */
			/* 1054 */ String str = paramStringTokenizer.nextToken("}\n").substring(1).trim();
			/*      */
			/*      */
			/* 1057 */ this.m_mapDataKeysToValues.put(paramString, str);
			/*      */ }
		/*      */
		/* 1060 */ return 2;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ private void setupFriendlyChordsForGrids() {
		/* 1067 */ this.m_setKeysFriendly.clear();
		/*      */
		/* 1069 */ if ('y' != SG02App.props.getProperty("grids.print.unfriendly").charAt(0)) {
			/* 1070 */ SG02App.chords.stringToChordSet(SG02App.props.getProperty("keys.grids.friendly"), this.m_setKeysFriendly);
			/*      */ }
		/* 1072 */ this.m_setChordsNoGrids.clear();
		/* 1073 */ SG02App.chords.stringToChordSet(SG02App.props.getProperty("grids.chords.no.grids"), this.m_setChordsNoGrids);
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public String getValueForKey(String paramString) {
		/* 1081 */ String str1 = paramString.toLowerCase();
		/* 1082 */ String str2 = this.m_mapDataKeysToValues.get(str1);
		/*      */
		/* 1084 */ if (null == str2) {
			/* 1085 */ return SG02App.getValueForKey(paramString);
			/*      */ }
		/* 1087 */ return str2;
		/*      */ }
		
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public String getFooterFormatString() {
		/* 1094 */ return getValueForKey("ftrfmtsg02");
		/*      */ }
		
	/*      */ class DirectiveStruct {
		int					m_nParseStatus;
		/*      */ boolean	m_bInChorus;
		/*      */ boolean	m_bInTab;
		/*      */ boolean	m_bFirstLine;
		
		/*      */
		/*      */ DirectiveStruct(boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
			/* 1102 */ this.m_nParseStatus = 2;
			/* 1103 */ this.m_bInChorus = param1Boolean1;
			/* 1104 */ this.m_bInTab = param1Boolean2;
			/* 1105 */ this.m_bFirstLine = param1Boolean3;
			/*      */ }
	}
	/*      */
	/*      */ }
	
/*
 * Location: C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\SongFile.class
 * Java compiler version: 8 (52.0) JD-Core Version: 1.1.3
 */