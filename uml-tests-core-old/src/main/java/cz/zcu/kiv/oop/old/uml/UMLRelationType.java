package cz.zcu.kiv.oop.old.uml;

import java.util.Arrays;
import java.util.List;

/**
 * Typy relaci
 *
 * @author Duong Manh Hung, A09B0205P
 */
public enum UMLRelationType {

  /**
   * "<-", "->", "<->", "-", "<", ">"
   */
  ASSOCIATION(Arrays.asList("<-", "->", "<->", "-", "<", ">")),
  /**
   * "<.", ".>"
   */
  DEPENDENCY(Arrays.asList("<.", ".>", "<.>")),
  /**
   * "<<<<-", "->>>>"
   */
  COMPOSITION(Arrays.asList("<<<<-", "->>>>")),
  /**
   * "<<<-", "->>>"
   */
  AGGREGATION(Arrays.asList("<<<-", "->>>")),
  /**
   * "<<.", ".>>"
   */
  REALIZATION(Arrays.asList("<<.", ".>>")),
  /**
   * "<<-", "->>"
   */
  GENERALIZATION(Arrays.asList("<<-", "->>")),
  /**
   * "."
   */
  DASHEDLINE(Arrays.asList(".")),

  /**
   * ">", "->", ".>", "->>", ".>>", "->>>", "->>>>"
   */
  RIGHTRELATIONS(Arrays.asList(">", "->", ".>", "->>", ".>>", "->>>", "->>>>")),
  /**
   * "<", "<-", "<.", "<<-", "<<.", "<<<-", "<<<<-"
   */
  LEFTRELATIONS(Arrays.asList("<", "<-", "<.", "<<-", "<<.", "<<<-", "<<<<-")),
  /**
   * ".", "-", "<->","<.>", ""
   */
  UNIRELATIONS(Arrays.asList(".", "-", "", "<->", "<.>")),

  UNDEFINED(Arrays.asList("")),

  ;

  private final List<String> values;

  private UMLRelationType(List<String> values) {
    this.values = values;
  }

  public List<String> getValues() {
    return values;
  }

}
