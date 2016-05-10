package cz.zcu.kiv.oop.uxf.version;

import java.net.URISyntaxException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.uxf.exception.UxfVersionFormatException;
import cz.zcu.kiv.oop.uxf.exception.UxfVersionReaderException;

/**
 * Class contains method for testing correct loading of uxf version from file.
 *
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersionReaderImplTest {

  protected static UxfVersionReader reader;

  /**
   * Init UxfReader for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    reader = UxfVersionReaderImpl.getInstance();
  }

  /**
   * Set UxfReader to null
   */
  @AfterClass
  public static void tearDownAfterClass() {
    reader = null;
  }

  protected static String getFile(String fileName) {
    URL url = UxfVersionReaderImplTest.class.getResource(fileName);
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
   * Tests if UxfVersionReaderException is thrown when trying to read from file with incorrect filename
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test(expected = UxfVersionReaderException.class)
  public void testReadEmptyFileName() throws UxfVersionReaderException {
    reader.readVersion("");
  }

  /**
   * Tests if UxfVersionReaderException is thrown when trying to read from nonexistent file
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test(expected = UxfVersionReaderException.class)
  public void testReadNonexistingFile() throws UxfVersionReaderException {
    reader.readVersion("this-file-doesnt-exist.uxf");
  }

  /**
   * Tests if correct uxf version is read
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test
  public void testReadFileVersion11() throws UxfVersionReaderException {
    UxfVersion version = reader.readVersion(getFile("v11.uxf"));
    Assert.assertEquals(new UxfVersion(11, 5, 1), version);
  }

  /**
   * Tests if UxfVersionReaderException is thrown when trying to read version in incorrect format (12.5.1.2)
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test(expected = UxfVersionFormatException.class)
  public void testReadFileVersion12() throws UxfVersionReaderException {
    reader.readVersion(getFile("v12.uxf"));
  }

  /**
   * Tests if correct uxf version is read
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test
  public void testReadFileVersion13() throws UxfVersionReaderException {
    UxfVersion version = reader.readVersion(getFile("v13.uxf"));
    Assert.assertEquals(new UxfVersion(13, 2), version);
  }

  /**
   * Tests if UxfVersionReaderException is thrown when trying to read version from uxf file which doesn't contain it.
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test(expected = UxfVersionReaderException.class)
  public void testReadInvalidFile() throws UxfVersionReaderException {
    reader.readVersion(getFile("invalid_uxf.uxf"));
  }

  /**
   * Tests if UxfVersionReaderException is thrown when trying to read version from uxf file which doesn't contain it.
   *
   * @throws UxfVersionReaderException
   *    uxf file cannot be opened
   */
  @Test(expected = UxfVersionReaderException.class)
  public void testReadFileWithNoVersion() throws UxfVersionReaderException {
    reader.readVersion(getFile("no_version.uxf"));
  }
}
