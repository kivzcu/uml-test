package cz.zcu.kiv.oop.uml.factory;

import java.awt.Color;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.AbstractPropertiesProvider;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributes;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributesNames;
import cz.zcu.kiv.oop.uml.element.UmlElementType;
import cz.zcu.kiv.oop.uml.element.UmlFont;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.exception.UmlElementException;
import cz.zcu.kiv.oop.uml.geometry.UmlPolyline;
import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;
import cz.zcu.kiv.oop.uxf.reader.sax.UmlElementDescriptor;

/**
 * Test of Default UML Element Factory.
 *
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class DefaultUmlElementFactoryTest {

  protected static DefaultUmlElementFactory factory;

  protected static final int X = 10;
  protected static final int Y = 11;
  protected static final int WIDTH = 110;
  protected static final int HEIGHT = 111;

  /**
   * Method initialize DefaultUmlElementFactory
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    factory = new DefaultUmlElementFactory(new MockPropertiesProvider());
    factory.init();
  }

  /**
   * Method sets DefaultUmlElementFactory back to null.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    factory = null;
  }

  /**
   * Method create UmlRectangle.
   *
   * @return - UmlRectangle
   */
  protected UmlRectangle createCoordinates() {
    return new UmlRectangle(X, Y, WIDTH, HEIGHT);
  }

  protected String createClassPanelAttributes(String stereotype, String className) {
    StringBuilder sb = new StringBuilder();
    sb.append("// comment").append("\n")
      .append(stereotype == null ? "" : stereotype + "\n")
      .append("bg=blue").append("\n")
      .append("fg=white").append("\n")
      .append(className).append("\n")
      .append("-----------").append("\n")
      .append("fontsize=20");

    return sb.toString();
  }

  /**
   * Method create String for panel attributes in uxf representation of UmlRelation.
   *
   * @param type type of UmlRelation
   * @return String for panel attributes
   */
  protected String createRelationPanelAttributes(String type) {
    StringBuilder sb = new StringBuilder();
    sb.append("// comment").append("\n")
      .append("bg=blue").append("\n")
      .append("fg=white").append("\n")
      .append("fontsize=20").append("\n")
      .append("lt=" + type);

    return sb.toString();
  }

  /**
   * Method create String for panel attributes in uxf representation of UmlNote.
   *
   * @param text text of note
   * @return String for panel attributes
   */
  protected String createNotePanelAttributes(String text) {
    StringBuilder sb = new StringBuilder();
    sb.append(text).append("\n")
      .append("// comment").append("\n")
      .append("bg=blue").append("\n")
      .append("fg=white").append("\n")
      .append("fontsize=20").append("\n");

    return sb.toString();
  }

  /**
   * Method sets coordinates into provided descriptor
   *
   * @param descriptor descriptor, coordinates will be set to
   */
  protected void setCoordinates(UmlElementDescriptor descriptor) {
    descriptor.setX(X);
    descriptor.setY(Y);
    descriptor.setWidth(WIDTH);
    descriptor.setHeight(HEIGHT);
  }

  protected UmlRelationType getRelationType(String value) {
    return factory.getType(UmlRelationType.class, value, DefaultUmlElementFactoryPropertyKeys.RELATION_PROPERTY_SUFFIX, factory.relationTypePropertiesCache);
  }

  protected UmlRelationOrientationType getRelationOrientationType(String value) {
    return factory.getType(UmlRelationOrientationType.class, value, DefaultUmlElementFactoryPropertyKeys.RELATION_PROPERTY_SUFFIX,
        factory.relationOrientationTypePropertiesCache);
  }

  /**
   * Checks if null is returned when user want to retrieve element according to descriptor which is null
   */
  @Test
  public void testGetNullUmlElement() throws UmlElementException {
    UmlElement element = factory.getUmlElement(null);

    Assert.assertEquals(element, null);
  }

  /**
   * Checks if UmlElementException is thrown when UmlElementDescriptor has unknown element type set.
   */
  @Test(expected = UmlElementException.class)
  public void testGetUmlElementWithUnknownElementType() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType("UnknownElementType");

    factory.getUmlElement(descriptor);
  }

  /**
   * Checks if UmlElementException is thrown when element type is set to null in UmlElementDescriptor
   */
  @Test(expected = UmlElementException.class)
  public void testGetUmlElementWithNullElementType() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType(null);

    factory.getUmlElement(descriptor);
  }

  /**
   * Checks if element with correct type is returned.
   */
  @Test
  public void testGetCorrectUmlElement() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();

    descriptor.setElementType("com.umlet.element.Class");
    Assert.assertEquals(UmlElementType.CLASS, factory.getUmlElement(descriptor).getElementType());

    descriptor.setElementType("com.umlet.element.Note");
    Assert.assertEquals(UmlElementType.NOTE, factory.getUmlElement(descriptor).getElementType());

    descriptor.setElementType("com.umlet.element.Relation");
    Assert.assertEquals(UmlElementType.RELATION, factory.getUmlElement(descriptor).getElementType());
  }

  /**
   * Checks if correct relation types are returned.
   */
  @Test
  public void testGetRelationType() {
    Assert.assertEquals(UmlRelationType.ASSOCIATION, getRelationType("->"));
    Assert.assertEquals(UmlRelationType.DEPENDENCY, getRelationType(".>"));
    Assert.assertEquals(UmlRelationType.COMPOSITION, getRelationType("->>>>"));
    Assert.assertEquals(UmlRelationType.AGGREGATION, getRelationType("->>>"));
    Assert.assertEquals(UmlRelationType.IMPLEMENTATION, getRelationType("<<."));
    Assert.assertEquals(UmlRelationType.INHERITANCE, getRelationType("<<-"));
    Assert.assertEquals(UmlRelationType.DASHEDLINE, getRelationType("."));
    Assert.assertEquals(null, getRelationType("<<->>")); // UNDEFINED
  }

  /**
   * Checks if correct relation orientation is returned.
   */
  @Test
  public void testGetRelationOrientationType() {
    Assert.assertEquals(UmlRelationOrientationType.RIGHT, getRelationOrientationType("->"));
    Assert.assertEquals(UmlRelationOrientationType.LEFT, getRelationOrientationType("<."));
    Assert.assertEquals(UmlRelationOrientationType.UNI, getRelationOrientationType("<->"));
    Assert.assertEquals(null, getRelationOrientationType("<<->>")); // UNDEFINED
  }

  /**
   * Checks if correct instance of UmlClass is returned according to provided UmlElementDescriptor. Method checks correctness of
   * Additional attributes too.
   */
  @Test
  public void testGetUmlClass1() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType("com.umlet.element.Class");
    setCoordinates(descriptor);

    String className = "_*/AbstractClass/*_";
    String panelAttributes = createClassPanelAttributes("<<abstract>>", className);
    String additionalAttributes = "";
    descriptor.setPanelAttributes(panelAttributes);
    descriptor.setAdditionalAttributes(additionalAttributes);

    UmlClass clazz = factory.getUmlClass(descriptor);
    Assert.assertEquals(UmlElementType.CLASS, clazz.getElementType());
    Assert.assertEquals(UmlClassType.ABSTRACT_CLASS, clazz.getClassType());
    Assert.assertEquals("AbstractClass", clazz.getName());
    Assert.assertEquals(createCoordinates(), clazz.getCoordinates());

    UmlElementAttributes attributes = clazz.getAttributes();
    Assert.assertEquals(panelAttributes, attributes.getAttribute(UmlElementAttributesNames.PANEL_ATTRIBUTES));
    Assert.assertEquals(additionalAttributes, attributes.getAttribute(UmlElementAttributesNames.ADDITIONAL_ATTRIBUTES));
    Assert.assertEquals(Color.BLUE, attributes.getAttribute(UmlElementAttributesNames.BACKGROUND));
    Assert.assertEquals(Color.WHITE, attributes.getAttribute(UmlElementAttributesNames.FOREGROUND));
    Assert.assertEquals(20.0, attributes.getAttribute(UmlElementAttributesNames.FONT_SIZE));
    Assert.assertEquals(new UmlFont(UmlFont.BOLD | UmlFont.ITALIC | UmlFont.UNDERLINED), attributes.getAttribute(UmlElementAttributesNames.CLASS_NAME_FONT));
    Assert.assertEquals(Arrays.asList(new String[] {"// comment"}), attributes.getAttribute(UmlElementAttributesNames.COMMENTED_LINES));
    Assert.assertEquals(Arrays.asList(new String[] {"-----------"}), attributes.getAttribute(UmlElementAttributesNames.ADDITIONAL_LINES));
    Assert.assertEquals(Arrays.asList(new String[] {"bg=blue", "fg=white", "-----------", "fontsize=20"}),
        attributes.getAttribute(UmlElementAttributesNames.EXTRA_LINES));
  }

  /**
   * Checks if correct instance of UmlClass is returned.
   */
  @Test
  public void testGetUmlClass2() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType("com.umlet.element.Class");

    String className = "SimpleClass";
    String panelAttributes = createClassPanelAttributes(null, className);
    descriptor.setPanelAttributes(panelAttributes);

    UmlClass clazz = factory.getUmlClass(descriptor);
    Assert.assertEquals(UmlClassType.CLASS, clazz.getClassType());
    Assert.assertEquals(className, clazz.getName());

    UmlElementAttributes attributes = clazz.getAttributes();
    Assert.assertEquals(new UmlFont(UmlFont.PLAIN), attributes.getAttribute(UmlElementAttributesNames.CLASS_NAME_FONT));
  }

  /**
   * Checks if correct instance of UmlClass is returned.
   */
  @Test
  public void testGetUmlClass3() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType("com.umlet.element.Class");
    descriptor.setPanelAttributes(createClassPanelAttributes("<<nope>>", "SimpleClass"));

    UmlClass clazz = factory.getUmlClass(descriptor);
    Assert.assertEquals(UmlClassType.CLASS, clazz.getClassType());
  }

  /**
   * Checks if correct instance of UmlRelation is returned. It checks additional attributes too.
   */
  @Test
  public void testGetUmlRelation() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType("com.umlet.element.Relation");
    setCoordinates(descriptor);

    String panelAttributes = createRelationPanelAttributes("<-");
    String additionalAttributes = "1.0;2;11;12.0";
    descriptor.setPanelAttributes(panelAttributes);
    descriptor.setAdditionalAttributes(additionalAttributes);

    UmlRelation relation = factory.getUmlRelation(descriptor);
    Assert.assertEquals(UmlElementType.RELATION, relation.getElementType());
    Assert.assertEquals(UmlRelationType.ASSOCIATION, relation.getRelationType());
    Assert.assertEquals(UmlRelationOrientationType.LEFT, relation.getRelationOrientationType());
    Assert.assertEquals(new UmlPolyline(new double[] {X + 1.0, Y + 2.0, X + 11.0, Y + 12.0}), relation.getRelationLine());
    Assert.assertEquals(createCoordinates(), relation.getCoordinates());

    UmlElementAttributes attributes = relation.getAttributes();
    Assert.assertEquals(panelAttributes, attributes.getAttribute(UmlElementAttributesNames.PANEL_ATTRIBUTES));
    Assert.assertEquals(additionalAttributes, attributes.getAttribute(UmlElementAttributesNames.ADDITIONAL_ATTRIBUTES));
    Assert.assertEquals(Arrays.asList(new String[] {"// comment"}), attributes.getAttribute(UmlElementAttributesNames.COMMENTED_LINES));
    Assert.assertEquals(Arrays.asList(new String[] {"bg=blue", "fg=white", "fontsize=20"}), attributes.getAttribute(UmlElementAttributesNames.ADDITIONAL_LINES));
    Assert.assertNull(attributes.getAttribute(UmlElementAttributesNames.CLASS_NAME_FONT));
  }

  /**
   * Checks if correct instance of UmlNote is returned.
   */
  @Test
  public void testGetUmlNote() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setElementType("com.umlet.element.Note");
    setCoordinates(descriptor);

    String text = "Note...\nLorem Ipsum";
    String panelAttributes = createNotePanelAttributes(text);
    String additionalAttributes = "";
    descriptor.setPanelAttributes(panelAttributes);
    descriptor.setAdditionalAttributes(additionalAttributes);

    UmlNote note = factory.getUmlNote(descriptor);
    Assert.assertEquals(UmlElementType.NOTE, note.getElementType());
    Assert.assertEquals(text, note.getText());
    Assert.assertEquals(createCoordinates(), note.getCoordinates());

    UmlElementAttributes attributes = note.getAttributes();
    Assert.assertEquals(panelAttributes, attributes.getAttribute(UmlElementAttributesNames.PANEL_ATTRIBUTES));
    Assert.assertEquals(additionalAttributes, attributes.getAttribute(UmlElementAttributesNames.ADDITIONAL_ATTRIBUTES));
    Assert.assertEquals(Color.BLUE, attributes.getAttribute(UmlElementAttributesNames.BACKGROUND));
    Assert.assertEquals(Color.WHITE, attributes.getAttribute(UmlElementAttributesNames.FOREGROUND));
    Assert.assertEquals(20.0, attributes.getAttribute(UmlElementAttributesNames.FONT_SIZE));
    Assert.assertEquals(Arrays.asList(new String[] {"bg=blue", "fg=white", "fontsize=20"}), attributes.getAttribute(UmlElementAttributesNames.EXTRA_LINES));
    Assert.assertNull(attributes.getAttribute(UmlElementAttributesNames.CLASS_NAME_FONT));
    Assert.assertNull(attributes.getAttribute(UmlElementAttributesNames.ADDITIONAL_LINES));
  }

  /**
   * Checks if correct UmlElementType is returned.
   *
   * @throws UmlElementException
   */
  @Test
  public void testGetUmlElementType() throws UmlElementException {
    UmlElementDescriptor desc = new UmlElementDescriptor();

    desc.setElementType("com.umlet.element.Class");
    Assert.assertEquals(UmlElementType.CLASS, factory.getUmlElementType(desc));

    desc.setElementType("com.umlet.element.Note");
    Assert.assertEquals(UmlElementType.NOTE, factory.getUmlElementType(desc));

    desc.setElementType("com.umlet.element.Relation");
    Assert.assertEquals(UmlElementType.RELATION, factory.getUmlElementType(desc));
  }

  /**
   * Checks if correct coordinates are returned.
   *
   * @throws UmlElementException
   */
  @Test
  public void testGetCoordinates() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    setCoordinates(descriptor);

    Assert.assertEquals(createCoordinates(), factory.getUmlCoordinates(descriptor));
  }

  /**
   * Checks if correct relation line is returned.
   *
   * @throws UmlElementException
   */
  @Test
  public void testGetRelationLine() throws UmlElementException {
    UmlElementDescriptor descriptor = new UmlElementDescriptor();
    descriptor.setAdditionalAttributes("1.0;2;11;12.0");

    Assert.assertEquals(new UmlPolyline(new double[] {1.0, 2.0, 11.0, 12.0}), factory.getUmlRelationLine(descriptor));
  }

  protected static class MockPropertiesProvider extends AbstractPropertiesProvider {

    @Override
    protected void initProperties() throws InitException {
      loadPropertiesFromClasspath("default_uml_element_factory.properties");
    }

  }
}
