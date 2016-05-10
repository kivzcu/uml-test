package cz.zcu.kiv.oop.properties.keys;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.ComparisonFailure;

/**
 * Ancestor for all tests of property keys. Contains utilities and constants for all tests.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public abstract class PropertyKeyTestAncestor {

  public static final String PROPERTIES_WITH_EMPTY_VALUES = "empty_values.properties";
  public static final String PROPERTIES_WITH_CORRECT_VALUES = "correct_values.properties";
  public static final String PROPERTIES_WITH_WRONG_VALUES = "wrong_values.properties";

  protected static InputStream getResourceAsInputStream(String resource) throws IOException {
    URL url = BooleanPropertyKeyTest.class.getResource(resource);
    if (url == null) {
      url = ClassLoader.getSystemResource(resource);
    }

    if (url == null) {
      throw new IOException("Cannot read properties for test");
    }

    return url.openStream();
  }

  public static <E> void assertCollectionsEquals(Collection<E> expected, Collection<E> actual) {
    boolean equals = true;
    if (expected == null) {
      if (actual == null) {
        return;
      }

      equals = false;
    }
    else if (actual == null) {
      equals = false;
    }
    else if (actual.size() != expected.size()) {
      equals = false;
    }
    else {
      List<E> first = new ArrayList<E>(expected);
      List<E> second = new ArrayList<E>(actual);
      for (int i = 0; i < first.size(); i++) {
        if (!first.equals(second)) {
          equals = false;
          break;
        }
      }
    }

    if (!equals) {
      throw new ComparisonFailure("Collections are not equal", expected + "", actual + "");
    }
  }
}
