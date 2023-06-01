/*     */ package com.tenbyten.SG02;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.Properties;
/*     */ import javax.swing.AbstractAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GenericPropertyAction
/*     */   extends AbstractAction
/*     */ {
/*     */   protected Properties m_propProps;
/*     */   protected String m_propName;
/*     */   protected boolean m_booleanValue;
/*     */   protected int m_intValue;
/*     */   
/*     */   public GenericPropertyAction(Properties paramProperties, String paramString) {
/*  20 */     this.m_propProps = paramProperties;
/*  21 */     this.m_propName = paramString.intern();
/*     */     
/*  23 */     this.m_booleanValue = false;
/*  24 */     this.m_intValue = 0;
/*     */     
/*  26 */     updateFromProps();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/*  32 */     updateFromProps();
/*     */   }
/*     */ 
/*     */   
/*     */   public void putValue(String paramString, Object paramObject) {
/*  37 */     if (paramString == this.m_propName || paramString.equals(this.m_propName))
/*     */     {
/*  39 */       if (((String)paramObject).length() > 0)
/*     */       {
/*     */         
/*  42 */         if ('y' == ((String)paramObject).charAt(0)) {
/*  43 */           this.m_booleanValue = true;
/*     */         }
/*     */         else {
/*     */           
/*  47 */           this.m_booleanValue = false;
/*     */           
/*  49 */           if ('n' != ((String)paramObject).charAt(0)) {
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/*  55 */               this.m_intValue = Integer.parseInt((String)paramObject);
/*     */             }
/*  57 */             catch (NumberFormatException numberFormatException) {
/*     */               
/*  59 */               this.m_intValue = 0;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*  66 */     super.putValue(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Properties getProps() {
/*  71 */     return this.m_propProps;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFromProps() {
/*  77 */     putValue(this.m_propName, this.m_propProps.getProperty(this.m_propName));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateFromNewProps(Properties paramProperties) {
/*  82 */     this.m_propProps = paramProperties;
/*  83 */     actionPerformed(new ActionEvent(this, 0, ""));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/*  88 */     return this.m_propName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStringPropertyValue() {
/*  93 */     return ((String)getValue(this.m_propName)).intern();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBooleanPropertyValue() {
/*  98 */     return this.m_booleanValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntPropertyValue() {
/* 103 */     return this.m_intValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\GenericPropertyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */