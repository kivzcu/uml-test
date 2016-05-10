package cz.zcu.kiv.oop.old;

import cz.zcu.kiv.oop.old.uml.UMLRelationType;

/**
 * Pomocne nastroje
 *
 * @author Duong Manh Hung, A09B0205P
 * @author Stepan Cais, A12P0004P
 */
public class UMLUtils {

  /**
   * Ziskani nazvu a typu elementu z atributu elementu
   *
   * @param params atributy elementu
   * @return nazev a typ elementu
   */
  public static String[] getTypeAndName(String params) {
    String[] result = new String[2];
    String type = "";
    String name = "";

    int index = params.lastIndexOf(">>"); // typ tridy je na prvni radce
                                          // mezi "<<" ">>"
    int eolIndex = params.indexOf("\n", index + 3); // od druhe radky hledej
                                                    // dalsi radku

    if (index != -1) { // na 1. radce je typ tridy
      if (eolIndex != -1) { // 1. radka typ, 2. radka nazev, 3. radka
                            // barva obdelniku
        name = params.substring(index + 3, eolIndex);
      }
      else { // 1. radky typ, 2. radka nazev
        name = params.substring(index + 3, params.length());
      }
      type = params.substring(0, index + 2);
    }
    else { // na prvni radce je nazev tridy
      eolIndex = params.indexOf("\n");
      if (eolIndex != -1) { // 1. radka nazev, 2. radka barva obdelniku
        name = params.substring(0, eolIndex);
      }
      else { // jedna radka s nazvem
        name = params;
      }
      type = "normal";
    }

    result[0] = name;
    result[1] = type;

    return result;
  }

  /**
   * Ziskani typu relace ze zapisu relace v UMLet
   *
   * @param input retezec se znakovou reprezentaci relace
   * @return typ relace
   */
  public static UMLRelationType getRelationType(String input) {
    for (UMLRelationType rt : UMLRelationType.values()) {
      for (String value : rt.getValues()) {
        if (value.equals(input)) {
          return rt;
        }
      }
    }

    return UMLRelationType.UNDEFINED;
  }

  public static boolean isFromLeftToRight(String input) {
    for (String value : UMLRelationType.RIGHTRELATIONS.getValues()) {
      if (value.equals(input)) {
        return true;
      }
    }

    return false;
  }
}
