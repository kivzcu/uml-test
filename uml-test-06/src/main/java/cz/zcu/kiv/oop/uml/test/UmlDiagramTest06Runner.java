package cz.zcu.kiv.oop.uml.test;

/**
 * Runner for 6th test of UML diagram.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagramTest06Runner extends UmlDiagramTestRunner {

  /**
   * Main method which sets name of UXF file which will be tested from arguments and after that initializes and starts tests.
   *
   * @param args arguments of program. For tests is important only one parameter with name of UXF file which will be tested.
   */
  public static void main(String[] args) {
    System.setProperty(PROPERTY_UXF_FILE_NAME, ""); // cleaning of property for preventing manual set
    // System.setProperty(PROPERTY_UXF_FILE_NAME, "./src/main/uxf/06_A00B0000P.uxf");

    if (args.length > 0) {
      System.setProperty(PROPERTY_UXF_FILE_NAME, args[0]);
    }

    UmlDiagramTestRunner runner = new UmlDiagramTest06Runner();
    runner.run();
    System.exit(UmlDiagramTest06Runner.mainResult);
  }

}
