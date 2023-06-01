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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegerPropertyRadioButton
/*    */   extends JRadioButton
/*    */ {
/*    */   protected int m_propValue;
/*    */   
/*    */   public IntegerPropertyRadioButton(Action paramAction, int paramInt) {
/* 26 */     super(paramAction);
/* 27 */     this.m_propValue = paramInt;
/*    */     
/* 29 */     configurePropertiesFromAction(paramAction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePropertiesFromAction(Action paramAction) {
/* 34 */     super.configurePropertiesFromAction(paramAction);
/* 35 */     setSelected((((GenericPropertyAction)getAction()).getIntPropertyValue() == this.m_propValue));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 40 */     GenericPropertyAction genericPropertyAction = (GenericPropertyAction)getAction();
/* 41 */     genericPropertyAction.getProps().setProperty(genericPropertyAction.getPropertyName(), String.valueOf(this.m_propValue));
/* 42 */     super.fireActionPerformed(paramActionEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PropertyChangeListener createActionPropertyChangeListener(Action paramAction) {
/* 47 */     return new PropertyChangeListener()
/*    */       {
/*    */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*    */         {
/* 51 */           if (param1PropertyChangeEvent.getPropertyName() == ((GenericPropertyAction)IntegerPropertyRadioButton.this.getAction()).getPropertyName())
/*    */           {
/* 53 */             IntegerPropertyRadioButton.this.setSelected((((GenericPropertyAction)IntegerPropertyRadioButton.this.getAction()).getIntPropertyValue() == IntegerPropertyRadioButton.this.m_propValue));
/*    */           }
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\IntegerPropertyRadioButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */