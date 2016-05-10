package cz.zcu.kiv.oop.uml.test.generator;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class UmlTestClassDescriptor implements Serializable {

  private static final long serialVersionUID = 998512772778354186L;

  protected String pckge;
  protected String className;
  protected TestsRunnerType testsRunnerType;

  public UmlTestClassDescriptor(String pckge, String className, TestsRunnerType testsRunnerType) {
    setPckge(pckge);
    setClassName(className);
    setTestsRunnerType(testsRunnerType);
  }

  public String getPckge() {
    return pckge;
  }

  public void setPckge(String pckge) {
    if (!StringUtils.hasText(pckge)) {
      throw new IllegalArgumentException("Package name cannot be null or empty");
    }

    this.pckge = pckge;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    if (!StringUtils.hasText(className)) {
      throw new IllegalArgumentException("Class name cannot be null or empty");
    }

    this.className = className;
  }

  public TestsRunnerType getTestsRunnerType() {
    return testsRunnerType;
  }

  public void setTestsRunnerType(TestsRunnerType testsRunnerType) {
    this.testsRunnerType = testsRunnerType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((className == null) ? 0 : className.hashCode());
    result = prime * result + ((pckge == null) ? 0 : pckge.hashCode());
    result = prime * result + ((testsRunnerType == null) ? 0 : testsRunnerType.hashCode());

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof UmlTestClassDescriptor)) {
      return false;
    }

    UmlTestClassDescriptor other = (UmlTestClassDescriptor)obj;
    if (className == null) {
      if (other.className != null) {
        return false;
      }
    }
    else if (!className.equals(other.className)) {
      return false;
    }

    if (pckge == null) {
      if (other.pckge != null) {
        return false;
      }
    }
    else if (!pckge.equals(other.pckge)) {
      return false;
    }

    if (testsRunnerType != other.testsRunnerType) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return getClass().getName() + " [pckge=" + pckge + ", className=" + className + ", testsRunnerType=" + testsRunnerType + "]";
  }

}
