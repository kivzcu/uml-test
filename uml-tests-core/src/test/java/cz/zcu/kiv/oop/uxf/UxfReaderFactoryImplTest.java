package cz.zcu.kiv.oop.uxf;

import java.net.URISyntaxException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.uxf.exception.UxfReaderFactoryException;
import cz.zcu.kiv.oop.uxf.reader.UxfReader;

/**
 * Class contains tests which verify whether correct implementation of UxfReader has been returned (according to version of UXF
 * file)
 *
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderFactoryImplTest {

  protected static UxfReaderFactoryImpl factory;

  /**
   * Init UxfReaderFactory for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    factory = (UxfReaderFactoryImpl)UxfReaderFactoryImpl.getInstance();
  }

  /**
   * Set UxfReaderFactory to null
   */
  @AfterClass
  public static void tearDownAfterClass() {
    factory = null;
  }

  protected static String getFile(String fileName) {
    URL url = UxfReaderFactoryImplTest.class.getResource(fileName);
    if (url == null) {
      return null;
    }

    try {
      return url.toURI().getPath();
    }
    catch (URISyntaxException exc) {
      return null;
    }
  }

  /**
   * Checks if UxfReader for uxf version 11 is returned.
   *
   * @throws UxfReaderFactoryException
   *   uxf file cannot be opened
   */
  @Test
  public void testRightVersionReader11() throws UxfReaderFactoryException {
    UxfReader reader = factory.getReader(getFile("v11.uxf"));
    Assert.assertTrue(reader instanceof cz.zcu.kiv.oop.uxf.reader.v11.UxfReaderImpl);
  }

  /**
   * Checks if UxfReader for uxf version 12 is returned.
   *
   * @throws UxfReaderFactoryException
   *   uxf file cannot be opened
   */
  @Test
  public void testRightVersionReader12() throws UxfReaderFactoryException {
    UxfReader reader = factory.getReader(getFile("v12.uxf"));
    Assert.assertTrue(reader instanceof cz.zcu.kiv.oop.uxf.reader.v12.UxfReaderImpl);
  }

  /**
   * Checks if UxfReader for uxf version 13 is returned.
   *
   * @throws UxfReaderFactoryException
   *   uxf file cannot be opened
   */
  @Test
  public void testRightVersionReader13() throws UxfReaderFactoryException {
    UxfReader reader = factory.getReader(getFile("v13.uxf"));
    Assert.assertTrue(reader instanceof cz.zcu.kiv.oop.uxf.reader.v13.UxfReaderImpl);
  }

  /**
   * Checks if UxfReaderFactoryException is thrown when uxf file doesn't contain uxf version marking
   *
   * @throws UxfReaderFactoryException
   *   uxf file cannot be opened
   */
  @Test(expected = UxfReaderFactoryException.class)
  public void testReadDiagramWithoutVersion() throws UxfReaderFactoryException {
    URL uxfUrl = getClass().getResource("no_version.uxf");
    factory.getReader(uxfUrl.getFile());
  }

  /**
   * Checks if UxfReaderFactoryException is thrown when uxf file is in unsupported version
   *
   * @throws UxfReaderFactoryException
   *   uxf file cannot be opened
   */
  @Test(expected = UxfReaderFactoryException.class)
  public void testNonexistingReader() throws UxfReaderFactoryException {
    URL uxfUrl = getClass().getResource("v999.uxf");
    factory.getReader(uxfUrl.getFile());
  }

  /**
   * Checks if UxfReaderFactoryException is thrown when uxf file doesn't exist
   *
   * @throws UxfReaderFactoryException
   *   uxf file cannot be opened
   */
  @Test(expected = UxfReaderFactoryException.class)
  public void testNonexistingFile() throws UxfReaderFactoryException {
    factory.getReader("this_file_doesnt_exist.uxf");
  }

}
