package cz.zcu.kiv.oop.old.xml;

/**
 * Reprezentace elementu z XML souboru pro zpracovani v pameti
 *
 * @author Duong Manh Hung, A09B0205P
 */
public class UMLElement {

  private String type;
  private int x, y, w, h;
  private String panelAttributes;
  private String adtlAttributes;

  public UMLElement(String type, int x, int y, int w, int h, String panelAttributes, String adtlAttributes) {
    this.type = type;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.panelAttributes = panelAttributes;
    this.adtlAttributes = adtlAttributes;
  }

  @Override
  public String toString() {
    return "UmlElement [type=" + type + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", panelAttributes=" + panelAttributes + ", adtlAttributes="
        + adtlAttributes + "]";
  }

  /**
   * @return Typ elementu
   */
  public String getType() {
    return type;
  }

  /**
   * @param type Nastaveni typu elementu
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return x souradnice elementu
   */
  public int getX() {
    return x;
  }

  /**
   * @param x nova x souradnice elementu
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * @return y souradnice elementu
   */
  public int getY() {
    return y;
  }

  /**
   * @param y nova y souradnice elementu
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * @return sirka elementu
   */
  public int getW() {
    return w;
  }

  /**
   * @param w nova sirka elementu
   */
  public void setW(int w) {
    this.w = w;
  }

  /**
   * @return vyska elementu
   */
  public int getH() {
    return h;
  }

  /**
   * @param h nova vyska elementu
   */
  public void setH(int h) {
    this.h = h;
  }

  /**
   * @return atributy elementu
   */
  public String getPanelAttributes() {
    return panelAttributes;
  }

  /**
   * @param panelAttributes nove atributy elementu
   */
  public void setPanelAttributes(String panelAttributes) {
    this.panelAttributes = panelAttributes;
  }

  /**
   * @return dodatecne atributy
   */
  public String getAdtlAttributes() {
    return adtlAttributes;
  }

  /**
   * @param adtlAttributes nove dodatecne atributy
   */
  public void setAdtlAttributes(String adtlAttributes) {
    this.adtlAttributes = adtlAttributes;
  }

}
