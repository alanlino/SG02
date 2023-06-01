/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JRadioButtonMenuItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegerPropertyRadioMenuItem
/*    */   extends JRadioButtonMenuItem
/*    */ {
/*    */   protected int m_propValue;
/*    */   
/*    */   public IntegerPropertyRadioMenuItem(Action paramAction, int paramInt) {
/* 17 */     super(paramAction);
/* 18 */     this.m_propValue = paramInt;
/*    */     
/* 20 */     configurePropertiesFromAction(paramAction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePropertiesFromAction(Action paramAction) {
/* 25 */     super.configurePropertiesFromAction(paramAction);
/* 26 */     setSelected((((GenericPropertyAction)getAction()).getIntPropertyValue() == this.m_propValue));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 31 */     GenericPropertyAction genericPropertyAction = (GenericPropertyAction)getAction();
/* 32 */     genericPropertyAction.getProps().setProperty(genericPropertyAction.getPropertyName(), String.valueOf(this.m_propValue));
/* 33 */     super.fireActionPerformed(paramActionEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PropertyChangeListener createActionPropertyChangeListener(Action paramAction) {
/* 38 */     return new PropertyChangeListener()
/*    */       {
/*    */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*    */         {
/* 42 */           if (param1PropertyChangeEvent.getPropertyName() == ((GenericPropertyAction)IntegerPropertyRadioMenuItem.this.getAction()).getPropertyName())
/*    */           {
/* 44 */             IntegerPropertyRadioMenuItem.this.setSelected((((GenericPropertyAction)IntegerPropertyRadioMenuItem.this.getAction()).getIntPropertyValue() == IntegerPropertyRadioMenuItem.this.m_propValue));
/*    */           }
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\IntegerPropertyRadioMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */