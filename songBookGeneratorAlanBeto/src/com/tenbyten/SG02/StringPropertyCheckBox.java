/*    */ package com.tenbyten.SG02;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JCheckBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringPropertyCheckBox
/*    */   extends JCheckBox
/*    */ {
/*    */   protected String m_propCheckedValue;
/*    */   protected String m_propUncheckedValue;
/*    */   
/*    */   public StringPropertyCheckBox(Action paramAction, String paramString1, String paramString2) {
/* 18 */     super(paramAction);
/* 19 */     this.m_propCheckedValue = paramString1.intern();
/* 20 */     this.m_propUncheckedValue = paramString2.intern();
/*    */     
/* 22 */     configurePropertiesFromAction(paramAction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePropertiesFromAction(Action paramAction) {
/* 27 */     super.configurePropertiesFromAction(paramAction);
/* 28 */     setSelected((((GenericPropertyAction)getAction()).getStringPropertyValue() == this.m_propCheckedValue));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 33 */     GenericPropertyAction genericPropertyAction = (GenericPropertyAction)getAction();
/* 34 */     if (!isSelected()) {
/* 35 */       genericPropertyAction.getProps().setProperty(genericPropertyAction.getPropertyName(), this.m_propUncheckedValue);
/*    */     } else {
/* 37 */       genericPropertyAction.getProps().setProperty(genericPropertyAction.getPropertyName(), this.m_propCheckedValue);
/* 38 */     }  super.fireActionPerformed(paramActionEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PropertyChangeListener createActionPropertyChangeListener(Action paramAction) {
/* 43 */     return new PropertyChangeListener()
/*    */       {
/*    */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*    */         {
/* 47 */           if (param1PropertyChangeEvent.getPropertyName() == ((GenericPropertyAction)StringPropertyCheckBox.this.getAction()).getPropertyName())
/*    */           {
/* 49 */             StringPropertyCheckBox.this.setSelected((((GenericPropertyAction)StringPropertyCheckBox.this.getAction()).getStringPropertyValue() == StringPropertyCheckBox.this.m_propCheckedValue));
/*    */           }
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\m335138\Downloads\SG02\!\com\tenbyten\SG02\StringPropertyCheckBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */