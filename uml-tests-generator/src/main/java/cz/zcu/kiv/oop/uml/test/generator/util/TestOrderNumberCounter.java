package cz.zcu.kiv.oop.uml.test.generator.util;

public class TestOrderNumberCounter {

  protected int firstLevelCounter;
  protected int secondLevelCounter;
  protected int thirdLevelCounter;

  public void reset() {
    firstLevelCounter = 0;
    secondLevelCounter = 0;
    thirdLevelCounter = 0;
  }

  public void incrementFirstLevelCounter() {
    firstLevelCounter++;
    secondLevelCounter = 0;
    thirdLevelCounter = 0;
  }

  public void incrementSecondLevelCounter() {
    secondLevelCounter++;
    thirdLevelCounter = 0;
  }

  public void incrementThirdLevelCounter() {
    thirdLevelCounter++;
  }

  public String getOrderNumber() {
    return String.format("%02d%02d%03d", firstLevelCounter, secondLevelCounter, thirdLevelCounter);
  }

  public String getTestName(String testDescription) {
    return String.format("t%02d%02d%03d_%s", firstLevelCounter, secondLevelCounter, thirdLevelCounter, testDescription);
  }
}
