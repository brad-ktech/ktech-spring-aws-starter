package com.ktech.starter.clio.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class FieldList {

  public static FieldList of(String fieldName) {
    FieldList fieldList = new FieldList();
    fieldList.addField(fieldName);
    return fieldList;
  }

  @Getter @Setter
  public abstract class FieldComponent {
    
    private String name;
    
    public FieldComponent() {      
    }
    
    public FieldComponent(String aName) {
      this.setName(aName);
    }    
    
  }
  
  @Getter @Setter
  public class Field extends FieldComponent {
    
    public Field(String aName) {
      super(aName);
    }
    
    public String toString() {
      return this.getName();
    }
  }
  
  @Getter @Setter
  public class FieldSet extends FieldComponent {
    
    List<FieldComponent> fieldComponents = new ArrayList<FieldComponent>();
    
    public FieldSet() {      
    }

    public FieldSet(String aName) {
      super(aName);
    }
    
    public FieldSet(String aName, String[] aNameArray) {
      super(aName);
      this.addFields(aNameArray);
    }
    
    public String toString() {
      
      boolean isBase = (this.getName() == null || this.getName().length() == 0);
      String template = (isBase) ? "<<fields>>" : 
        this.getName() + ((fieldComponents.size() == 0) ? "" : "{<<fields>>}");
      
      if (fieldComponents.size() > 0) {
        StringBuffer sb = new StringBuffer();
        for (FieldComponent fc: fieldComponents) {
          sb.append((sb.length() > 0) ? ",":"").append(fc.toString());          
        }
        template = template.replace("<<fields>>",  sb.toString());
      }
      
      return template;
    }
    
    public void add(FieldComponent aFieldComponent) {
      this.fieldComponents.add(aFieldComponent);
    }
    
    public void addField(String aName) {
      this.add(new Field(aName));
    }
    
    public void addFields(String[] aNameArray) {
      for (int i=0; i<aNameArray.length; i++) {
        this.addField(aNameArray[i]);
      }
    }    
  }
  
  private FieldSet fields = new FieldSet(); 
  
  public Field addField(String aName) {
    Field field = new Field(aName);
    fields.add(field);
    return field;
  }
  
  public void addFields(String[] aNameArray) {
    this.getFields().addFields(aNameArray);
  }
  
  public FieldSet addFieldSet(String aName, String[] aNameArray) {
    FieldSet fieldSet = new FieldSet(aName, aNameArray);
    this.getFields().add(fieldSet);
    return fieldSet;
  }
  
  public FieldSet addFieldSet(String aName) {
    FieldSet fieldSet = new FieldSet(aName);
    this.getFields().add(fieldSet);
    return fieldSet;
  }    
  
  public String toString() {
    return this.getFields().toString();
  }
  
  public String toEncodedString() {
    return this.toString().replace("{", "%7B").replace("}", "%7D");
  }
  
}
