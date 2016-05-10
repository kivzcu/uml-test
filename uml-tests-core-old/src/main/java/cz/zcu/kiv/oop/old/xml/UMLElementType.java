package cz.zcu.kiv.oop.old.xml;

public enum UMLElementType {

  CLASS("com.umlet.element.Class"),
  RELATION("com.umlet.element.Relation"),
  NOTE("com.umlet.element.Note"),
  ;

  private String value;

  private UMLElementType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

}
