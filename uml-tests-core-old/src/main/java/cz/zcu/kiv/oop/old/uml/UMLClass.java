package cz.zcu.kiv.oop.old.uml;

/**
 * Reprezentace UML tridy
 *
 * @author Duong Manh Hung, A09B0205P
 */
public class UMLClass {

  private String name;

  private String type;

  public UMLClass(String name, String type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String toString() {
    return "UMLClass [name=" + name + ", type=" + type + "]";
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

}
