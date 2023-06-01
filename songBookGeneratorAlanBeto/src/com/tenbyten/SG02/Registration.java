/*     */ package com.tenbyten.SG02;
import java.util.Calendar;
/*     */ 
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Registration
/*     */ {
/*  14 */   private RegistrationData m_data = new RegistrationDataB();
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegistered() {
/*  19 */     return this.m_data.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNagTime() {
/*  24 */     return this.m_data.isNagTime();
/*     */   }
/*     */ 
/*     */   
/*     */   void setCode(String paramString) {
/*  29 */     this.m_data.setCode(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   void incrementRunCount() {
/*  34 */     this.m_data.incrementRunCount();
/*     */   }
/*     */ 
/*     */   
/*     */   void incrementOutputCount() {
/*  39 */     this.m_data.incrementOutputCount();
/*     */   }
/*     */ 
/*     */   
/*     */   private abstract class RegistrationData
/*     */   {
/*     */     private RegistrationData() {}
/*     */ 
/*     */     
/*     */     public void setCode(String param1String) {}
/*     */     
/*     */     public void incrementRunCount() {}
/*     */     
/*     */     public void incrementOutputCount() {}
/*     */     
/*     */     public abstract boolean isNagTime();
/*     */     
/*     */     public abstract boolean validate();
/*     */   }
/*     */   
/*     */   private class RegistrationDataB
/*     */     extends RegistrationData
/*     */     implements Cloneable
/*     */   {
/*     */     private static final int REGISTRATIONB_SIZEOF = 153;
/*     */     private static final int REGISTRATIONA_VERSION = 51966;
/*     */     private static final int REGISTRATIONB_VERSION = 51888;
/*     */     private static final int REGISTRATIONB_LIMIT_RUNS = 9;
/*     */     private static final int REGISTRATIONB_LIMIT_DAYS = 32;
/*     */     private static final int REGISTRATIONB_LIMIT_PRINTS = 32;
/*     */     private static final double REGISTRATIONB_UINT_MAX = 4.294967295E9D;
/*     */     private static final String REGISTRATIONB_PROPS_KEY = "registration.b";
/*     */     private long m_checksum;
/*     */     private int m_version;
/*     */     private long m_code2;
/*     */     private long m_code3;
/*     */     private long m_code4;
/*     */     private long m_timeCodeEntered;
/*     */     private long m_timeLastNag;
/*     */     private short m_runsSinceNag;
/*     */     private short m_outsSinceNag;
/*     */     private short m_random;
/*     */     
/*     */     public RegistrationDataB() {
/*  83 */       setFromProps();
/*     */       
/*  85 */       if (!isValid()) {
/*     */         
/*  87 */         Random random = new Random();
/*     */         
/*  89 */         this.m_checksum = (short)random.nextInt(32767);
/*  90 */         this.m_version = 10078896;
/*  91 */         this.m_version = (this.m_version ^ 0xFFFFFFFF) & 0xFFFFFFF;
/*  92 */         this.m_code2 = random.nextInt(2147483647);
/*  93 */         this.m_code3 = random.nextInt(2147483647);
/*  94 */         this.m_code4 = random.nextInt(2147483647);
/*  95 */         this.m_timeCodeEntered = random.nextInt(2147483647);
/*  96 */         this.m_timeLastNag = 0L;
/*  97 */         this.m_runsSinceNag = 206;
/*  98 */         this.m_outsSinceNag = 206;
/*  99 */         this.m_random = (short)random.nextInt(32767);
/*     */       } 
/*     */     }
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
/*     */     public Object clone() throws CloneNotSupportedException {
/* 113 */       return super.clone();
/*     */     }
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
/*     */     
/*     */     private void setFromProps() {
/*     */       try {
/* 135 */         String str1 = SG02App.props.getProperty("registration.b");
/*     */         
/* 137 */         int i = 0;
/* 138 */         int j = str1.indexOf('-', i);
/*     */         
/* 140 */         String str2 = str1.substring(i, j);
/* 141 */         this.m_checksum = Long.parseLong(str2, 36);
/*     */         
/* 143 */         i = j + 1;
/* 144 */         j = str1.indexOf('-', i);
/* 145 */         str2 = str1.substring(i, j);
/* 146 */         this.m_version = (int)Long.parseLong(str2, 36);
/*     */         
/* 148 */         i = j + 1;
/* 149 */         j = str1.indexOf('-', i);
/* 150 */         str2 = str1.substring(i, j);
/* 151 */         this.m_code2 = Long.parseLong(str2, 36);
/*     */         
/* 153 */         i = j + 1;
/* 154 */         j = str1.indexOf('-', i);
/* 155 */         str2 = str1.substring(i, j);
/* 156 */         this.m_code3 = Long.parseLong(str2, 36);
/*     */         
/* 158 */         i = j + 1;
/* 159 */         j = str1.indexOf('-', i);
/* 160 */         str2 = str1.substring(i, j);
/* 161 */         this.m_code4 = Long.parseLong(str2, 36);
/*     */         
/* 163 */         i = j + 1;
/* 164 */         j = str1.indexOf('-', i);
/* 165 */         str2 = str1.substring(i, j);
/* 166 */         this.m_timeCodeEntered = Long.parseLong(str2, 36);
/*     */         
/* 168 */         i = j + 1;
/* 169 */         j = str1.indexOf('-', i);
/* 170 */         str2 = str1.substring(i, j);
/* 171 */         this.m_timeLastNag = Long.parseLong(str2, 36);
/*     */         
/* 173 */         i = j + 1;
/*     */         
/* 175 */         str2 = str1.substring(i);
/* 176 */         long l = Long.parseLong(str2, 36);
/*     */         
/* 178 */         this.m_runsSinceNag = (short)(int)(l >> 24L);
/* 179 */         this.m_outsSinceNag = (short)(int)(l >> 16L & 0xFFL);
/* 180 */         this.m_random = (short)(int)(l & 0xFFFFL);
/*     */       
/*     */       }
/* 183 */       catch (Exception exception) {
/*     */         
/* 185 */         System.err.println("Registration props parsing threw: " + exception.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void putToProps() {
/* 196 */       long l = (this.m_runsSinceNag << 24 | this.m_outsSinceNag << 16 | this.m_random);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 205 */       String str = Long.toString(this.m_checksum, 36) + "-" + Long.toString(this.m_version, 36) + "-" + Long.toString(this.m_code2, 36) + "-" + Long.toString(this.m_code3, 36) + "-" + Long.toString(this.m_code4, 36) + "-" + Long.toString(this.m_timeCodeEntered, 36) + "-" + Long.toString(this.m_timeLastNag, 36) + "-" + Long.toString(l, 36);
/* 206 */       SG02App.props.setProperty("registration.b", str);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCode(String param1String) {
/*     */       try {
/* 216 */         int i = param1String.indexOf('-');
/* 217 */         int j = param1String.indexOf('-', i + 1);
/*     */         
/* 219 */         String str1 = param1String.substring(0, i);
/* 220 */         String str2 = param1String.substring(i + 1, j);
/* 221 */         String str3 = param1String.substring(j + 1);
/*     */         
/* 223 */         this.m_code4 = Long.parseLong(str1, 36);
/* 224 */         this.m_code3 = Long.parseLong(str2, 36);
/* 225 */         this.m_code2 = Long.parseLong(str3, 36);
/*     */ 
/*     */         
/* 228 */         this.m_timeCodeEntered = System.currentTimeMillis();
/*     */         
/* 230 */         computeChecksum();
/*     */         
/* 232 */         putToProps();
/*     */       
/*     */       }
/* 235 */       catch (Exception exception) {
/*     */         
/* 237 */         System.err.println("Registration code parsing threw: " + exception.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean validate() {
/* 244 */       return (isValid() && validate1() && !validate2() && validate3() && !validate4());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean isValid() {
/*     */       try {
/* 252 */         RegistrationDataB registrationDataB = (RegistrationDataB)clone();
/* 253 */         registrationDataB.computeChecksum();
/*     */         
/* 255 */         return (registrationDataB.m_checksum == this.m_checksum);
/*     */       }
/* 257 */       catch (Exception exception) {
/*     */         
/* 259 */         System.err.println("Registration validation threw: " + exception.toString());
/*     */ 
/*     */         
/* 262 */         return false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean validate1() {
/* 270 */       double d1 = this.m_code3 / 4.294967295E9D;
/* 271 */       double d2 = Math.abs(Math.sin(13.0D * d1) * Math.sin(7.0D * d1));
/* 272 */       double d3 = this.m_code2 / 4.294967295E9D;
/* 273 */       double d4 = Math.abs(d2 - d3);
/* 274 */       double d5 = 2.3283064370807974E-10D;
/*     */       
/* 276 */       return (d4 < d5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean validate2() {
    boolean bl = (~this.m_version & 0xFFFF) != 51888;
    return bl |= (~this.m_version & 0xFFFFFFF) >> 16 != 153;
}
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean validate3() {
/* 292 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean validate4() {
/* 298 */       return (this.m_code4 % 51966L != 0L);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void computeChecksum() {
/* 305 */       long l = 0L;
/* 306 */       this.m_checksum = 1L;
/*     */       
/* 308 */       l += this.m_checksum;
/* 309 */       l += this.m_version;
/* 310 */       l += this.m_code2;
/* 311 */       l += this.m_code3;
/* 312 */       l += this.m_code4;
/* 313 */       l += this.m_timeLastNag;
/* 314 */       l += this.m_runsSinceNag;
/* 315 */       l += this.m_outsSinceNag;
/* 316 */       l += this.m_random;
/*     */       
/* 318 */       this.m_checksum = l;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isNagTime() {
    boolean bl = true;
    if (this.isValid()) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(this.m_timeLastNag);
        ((Calendar)gregorianCalendar2).add(6, 32);
        bl = gregorianCalendar.after(gregorianCalendar2);
        bl |= this.m_runsSinceNag > 9;
        bl |= this.m_outsSinceNag > 32;
    }
    if (bl) {
        this.updateCounters();
    }
    return bl &= !this.validate();
}
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateCounters() {
/* 355 */       this.m_timeLastNag = System.currentTimeMillis();
/* 356 */       this.m_runsSinceNag = 0;
/* 357 */       this.m_outsSinceNag = 0;
/*     */       
/* 359 */       computeChecksum();
/*     */       
/* 361 */       putToProps();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void incrementRunCount() {
/* 367 */       if (this.m_runsSinceNag == 206) {
/* 368 */         this.m_runsSinceNag = 1;
/*     */       } else {
/* 370 */         this.m_runsSinceNag = (short)(this.m_runsSinceNag + 1);
/*     */       } 
/* 372 */       computeChecksum();
/*     */       
/* 374 */       putToProps();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void incrementOutputCount() {
/* 380 */       if (this.m_outsSinceNag == 206) {
/* 381 */         this.m_outsSinceNag = 1;
/*     */       } else {
/* 383 */         this.m_outsSinceNag = (short)(this.m_outsSinceNag + 1);
/*     */       } 
/* 385 */       computeChecksum();
/*     */       
/* 387 */       putToProps();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\Registration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */