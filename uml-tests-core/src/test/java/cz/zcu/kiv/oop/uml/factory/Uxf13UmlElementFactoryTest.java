package cz.zcu.kiv.oop.uml.factory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.AbstractPropertiesProvider;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.exception.UmlElementException;
import cz.zcu.kiv.oop.uxf.reader.sax.UmlElementDescriptor;

/**
 * Test of UML Element Provider for reader of UXF version 13.
 *
 * @author Karel Zíbar
 */
public class Uxf13UmlElementFactoryTest {

  protected static Uxf13UmlElementFactory factory;

  /**
   * Method prepare Uxf13UmlElementFactory for testing.
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    factory = new Uxf13UmlElementFactory(new MockPropertiesProvider());
    factory.init();
  }

  /**
   * Method sets Uxf13UmlElementFactory to null.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    factory = null;
  }

  /**
   * Method checks if correct RelationTypes are returned for Uxf version 13.
   *
   * @throws UmlElementException
   */
  @Test
  public void testGetUmlRelation() throws UmlElementException {
    UmlElementDescriptor desc = new UmlElementDescriptor();

    desc.setElementType("Relation");
    desc.setPanelAttributes("lt=<<<<<-");
    UmlRelation rel = factory.getUmlRelation(desc);
    Assert.assertEquals(UmlRelationType.COMPOSITION, rel.getRelationType());
    Assert.assertEquals(UmlRelationOrientationType.LEFT, rel.getRelationOrientationType());

    desc.setPanelAttributes("lt=->>>>");
    rel = factory.getUmlRelation(desc);
    Assert.assertEquals(UmlRelationType.AGGREGATION, rel.getRelationType());
    Assert.assertEquals(UmlRelationOrientationType.RIGHT, rel.getRelationOrientationType());

    desc.setElementType("com.umlet.element.Relation");
    desc.setPanelAttributes("lt=<<<<-");
    rel = factory.getUmlRelation(desc);
    Assert.assertEquals(UmlRelationType.COMPOSITION, rel.getRelationType());
    Assert.assertEquals(UmlRelationOrientationType.LEFT, rel.getRelationOrientationType());

    desc.setPanelAttributes("lt=->>>");
    rel = factory.getUmlRelation(desc);
    Assert.assertEquals(UmlRelationType.AGGREGATION, rel.getRelationType());
    Assert.assertEquals(UmlRelationOrientationType.RIGHT, rel.getRelationOrientationType());

  }

  protected static class MockPropertiesProvider extends AbstractPropertiesProvider {

    @Override
    protected void initProperties() throws InitException {
      loadPropertiesFromClasspath("uxf13_uml_element_factory.properties");
    }

  }

}
