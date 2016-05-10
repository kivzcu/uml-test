package cz.zcu.kiv.oop.uml.test.generator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.zcu.kiv.oop.uml.element.UmlElement;

public class TestsDepenencies {

  protected final Map<UmlElement, List<String>> elementDependencies = new HashMap<UmlElement, List<String>>();

  public void addElementDepenency(UmlElement element, String testName) {
    List<String> dependencies = elementDependencies.get(element);
    if (dependencies == null) {
      dependencies = new ArrayList<String>();
      elementDependencies.put(element, dependencies);
    }

    if (!dependencies.contains(testName)) {
      dependencies.add(testName);
    }
  }

  public String[] getElementDependencies(UmlElement... elements) {
    if (elements == null) {
      return new String[0];
    }

    List<String> dependencies = new ArrayList<String>();
    for (int i = 0; i < elements.length; i++) {
      List<String> elementDependencies = this.elementDependencies.get(elements[i]);
      if (elementDependencies != null) {
        dependencies.addAll(elementDependencies);
      }
    }

    return dependencies.toArray(new String[dependencies.size()]);
  }

  public void clear() {
    elementDependencies.clear();
  }

}
