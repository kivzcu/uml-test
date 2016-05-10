package cz.zcu.kiv.oop.old.xml;

import java.util.ArrayList;

/**
 * Rozhrani pro reader XML souboru
 *
 * @author Duong Manh Hung, A09B0205P
 */
public interface IUMLReader {

  /**
   * @return Seznam vsech vyparsovanych elementu
   */
  public ArrayList<UMLElement> getAllElements();

  /**
   * @return Uroven zoomu
   */
  public int getZoomLvl();

}
