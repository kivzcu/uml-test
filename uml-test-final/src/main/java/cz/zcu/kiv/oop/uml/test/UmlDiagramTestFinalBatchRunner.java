package cz.zcu.kiv.oop.uml.test;

/**
 * Batch runner for final test of UML diagram.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagramTestFinalBatchRunner extends UmlDiagramTestBatchRunner {

  /**
   * Main method which sets property for directory with UXF files whose will be tested from arguments and after that initializes and
   * starts tests.
   *
   * @param args arguments of program. For tests is important only one parameter with directory with UXF files whose will be tested.
   */
  public static void main(String[] args) {
    System.setProperty(PROPERTY_DIRECTORY_NAME, ".");

    if (args.length > 0) {
      System.setProperty(PROPERTY_DIRECTORY_NAME, args[0]);
    }

    UmlDiagramTestBatchRunner runner = new UmlDiagramTestFinalBatchRunner();
    runner.run();
  }

}
