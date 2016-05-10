package cz.zcu.kiv.oop.uxf.version;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.oop.uxf.exception.UxfVersionFormatException;

/**
 * Class contains method for testing correct version parsing. There are many examples of right/wrong version writing.
 * <ol>
 * <li>empty string</li>
 * <li>version contains character</li>
 * <li>null</li>
 * <li>version ends on '.'</li>
 * <li>version is too long</li>
 * </ol>
 * <p>
 * Only correct form of version String is 'major.minor.plugin'. Minor and plugin are optional.
 * </p>
 *
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersionTest {

  /**
   * Checks if UxfVersionFormatException is thrown when parsing version contains a letter
   */
  @Test(expected = UxfVersionFormatException.class)
  public void testParseIllegalCharacterInVersion() {
    UxfVersion.parseVersion("12.1.a");
  }

  /**
   * Checks if UxfVersionFormatException is thrown when parsing version is empty string
   */
  @Test(expected = UxfVersionFormatException.class)
  public void testParseNoVersion() {
    UxfVersion.parseVersion("");
  }

  /**
   * Checks if UxfVersionFormatException is thrown when parsing version is null
   */
  @Test(expected = UxfVersionFormatException.class)
  public void testParseNullAsVersion() {
    UxfVersion.parseVersion(null);
  }

  /**
   * Checks if UxfVersionFormatException is thrown when parsing version ends by dot
   */
  @Test(expected = UxfVersionFormatException.class)
  public void testParseVersionFormatEndsByDot() {
    UxfVersion.parseVersion("1.");
  }

  /**
   * Checks if UxfVersionFormatException is thrown when parsing version contains to much numbers for example 1.2.3.4.5
   */
  @Test(expected = UxfVersionFormatException.class)
  public void testParseLongVersionFormat() {
    UxfVersion.parseVersion("1.2.3.4.5");
  }

  /**
   * Tests if correct UxfVersion is returned if version string is in correct format and contains Major, Minor, and Plugin mark
   */
  @Test
  public void testParseCorrectMajorMinorPluginVersionFormat() {
    UxfVersion version = UxfVersion.parseVersion("1.2.3");
    Assert.assertEquals(new UxfVersion(1, 2, 3), version);
  }

  /**
   * Tests if correct UxfVersion is returned if version string is in correct format and contains Major and Minor mark
   */
  @Test
  public void testParseCorrectMajorMinorVersionFormat() {
    UxfVersion version = UxfVersion.parseVersion("1.2");
    Assert.assertEquals(new UxfVersion(1, 2), version);
  }

  /**
   * Tests if correct UxfVersion is returned if version string is in correct format and contains only Major mark
   */
  @Test
  public void testParseCorrectMajorVersionFormat() {
    UxfVersion version = UxfVersion.parseVersion("1");
    Assert.assertEquals(new UxfVersion(1), version);
  }

}
