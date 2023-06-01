/*    */ package com.tenbyten.Util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.MappedByteBuffer;
/*    */ import java.nio.channels.FileChannel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CopyFile
/*    */ {
/*    */   public static void simpleCopy(File paramFile1, File paramFile2) throws IOException {
/* 20 */     FileChannel fileChannel1 = null, fileChannel2 = null;
/*    */     
/*    */     try {
/* 23 */       fileChannel1 = (new FileInputStream(paramFile1)).getChannel();
/* 24 */       fileChannel2 = (new FileOutputStream(paramFile2)).getChannel();
/*    */       
/* 26 */       long l = fileChannel1.size();
/* 27 */       MappedByteBuffer mappedByteBuffer = fileChannel1.map(FileChannel.MapMode.READ_ONLY, 0L, l);
/*    */       
/* 29 */       fileChannel2.write(mappedByteBuffer);
/*    */     
/*    */     }
/*    */     finally {
/*    */       
/* 34 */       if (fileChannel1 != null) {
/* 35 */         fileChannel1.close();
/*    */       }
/* 37 */       if (fileChannel2 != null)
/* 38 */         fileChannel2.close(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\Util\CopyFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */