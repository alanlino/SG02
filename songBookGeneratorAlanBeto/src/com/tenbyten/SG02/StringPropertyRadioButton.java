/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JRadioButton;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringPropertyRadioButton
/*    */   extends JRadioButton
/*    */ {
/*    */   protected String m_propValue;
/*    */   
/*    */   public StringPropertyRadioButton(Action paramAction, String paramString) {
/* 17 */     super(paramAction);
/* 18 */     this.m_propValue = paramString.intern();
/*    */     
/* 20 */     configurePropertiesFromAction(paramAction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePropertiesFromAction(Action paramAction) {
/* 25 */     super.configurePropertiesFromAction(paramAction);
/* 26 */     setSelected((((GenericPropertyAction)getAction()).getStringPropertyValue() == this.m_propValue));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 31 */     GenericPropertyAction genericPropertyAction = (GenericPropertyAction)getAction();
/* 32 */     genericPropertyAction.getProps().setProperty(genericPropertyAction.getPropertyName(), this.m_propValue);
/* 33 */     super.fireActionPerformed(paramActionEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PropertyChangeListener createActionPropertyChangeListener(Action paramAction) {
/* 38 */     return new PropertyChangeListener()
/*    */       {
/*    */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*    */         {
/* 42 */           if (param1PropertyChangeEvent.getPropertyName() == ((GenericPropertyAction)StringPropertyRadioButton.this.getAction()).getPropertyName())
/*    */           {
/* 44 */             StringPropertyRadioButton.this.setSelected((((GenericPropertyAction)StringPropertyRadioButton.this.getAction()).getStringPropertyValue() == StringPropertyRadioButton.this.m_propValue));
/*    */           }
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\StringPropertyRadioButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */