package cz.zcu.kiv.oop.uml.test.generator.jenesis4java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.jenesis4java.Access;
import net.sourceforge.jenesis4java.Annotation;
import net.sourceforge.jenesis4java.Binary;
import net.sourceforge.jenesis4java.ClassField;
import net.sourceforge.jenesis4java.ClassMethod;
import net.sourceforge.jenesis4java.Comment;
import net.sourceforge.jenesis4java.CompilationUnit;
import net.sourceforge.jenesis4java.Expression;
import net.sourceforge.jenesis4java.Invoke;
import net.sourceforge.jenesis4java.Let;
import net.sourceforge.jenesis4java.Namespace;
import net.sourceforge.jenesis4java.NewArray;
import net.sourceforge.jenesis4java.NewClass;
import net.sourceforge.jenesis4java.PackageClass;
import net.sourceforge.jenesis4java.Type;
import net.sourceforge.jenesis4java.Variable;
import net.sourceforge.jenesis4java.VirtualMachine;
import net.sourceforge.jenesis4java.jaloppy.JenesisJalopyEncoder;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import cz.zcu.kiv.oop.properties.EmptyPropertiesProvider;
import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributes;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributesNames;
import cz.zcu.kiv.oop.uml.element.UmlFont;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.junit.ImportantTest;
import cz.zcu.kiv.oop.uml.junit.TestDependencies;
import cz.zcu.kiv.oop.uml.junit.UmlAssert;
import cz.zcu.kiv.oop.uml.junit.runners.BlockJUnit4ClassRunnerWithDependencies;
import cz.zcu.kiv.oop.uml.junit.runners.BlockJUnit4ClassRunnerWithSingleFail;
import cz.zcu.kiv.oop.uml.test.generator.TestsRunnerType;
import cz.zcu.kiv.oop.uml.test.generator.UmlTestClassDescriptor;
import cz.zcu.kiv.oop.uml.test.generator.UmlTestGenerator;
import cz.zcu.kiv.oop.uml.test.generator.configuration.UmlTestGeneratorPropertyKeys;
import cz.zcu.kiv.oop.uml.test.generator.exception.UmlTestGeneratorException;
import cz.zcu.kiv.oop.uml.test.generator.util.CombinationPair;
import cz.zcu.kiv.oop.uml.test.generator.util.TestOrderNumberCounter;
import cz.zcu.kiv.oop.uml.test.generator.util.TestsDepenencies;
import cz.zcu.kiv.oop.uml.util.UmlUtils;
import cz.zcu.kiv.oop.util.Pair;
import cz.zcu.kiv.oop.uxf.UxfReaderFactory;
import cz.zcu.kiv.oop.uxf.UxfReaderFactoryImpl;
import cz.zcu.kiv.oop.uxf.exception.UxfException;
import cz.zcu.kiv.oop.uxf.reader.UxfReader;
import edu.emory.mathcs.backport.java.util.Arrays;

public class Jenesis4JavaUmlTestGenerator implements UmlTestGenerator {

  protected static final String DIAGRAM_FIELD_NAME = "diagram";
  protected static final String PERSONAL_NUMBER_FIELD_NAME = "personalNumber";
  protected static final String TEST_NUMBER_FIELD_NAME = "testNumber";

  protected static final String MAXIMAL_RELATIONS_CROSSINGS = "MAXIMAL_RELATIONS_CROSSINGS";

  protected final VirtualMachine virtualMachine;

  protected Namespace namespace;
  protected PackageClass packageClass;
  protected TestsRunnerType testsRunnerType;

  protected UmlDiagram diagram;

  protected Jenesis4JavaHelper helper;
  protected TestOrderNumberCounter counter;
  protected TestsDepenencies dependencies;

  protected PropertiesProvider propertiesProvider;

  public Jenesis4JavaUmlTestGenerator() {
    System.setProperty("jenesis.encoder", JenesisJalopyEncoder.class.getName());

    virtualMachine = VirtualMachine.getVirtualMachine();

    helper = new Jenesis4JavaHelper(virtualMachine);
    counter = new TestOrderNumberCounter();
    dependencies = new TestsDepenencies();

    // empty properties provider (default)
    propertiesProvider = new EmptyPropertiesProvider();
  }

  protected void clear() {
    namespace = null;
    packageClass = null;
    helper.set(null, null);

    diagram = null;

    counter.reset();
    dependencies.clear();
  }

  @Override
  public void setPropertiesProvider(PropertiesProvider propertiesProvider) {
    this.propertiesProvider = propertiesProvider;
  }

  @Override
  public void generateTest(UmlDiagram diagram, UmlTestClassDescriptor testClassDescriptor, String outputDirectory) throws UmlTestGeneratorException {
    clear(); // clear generator before generating of new test

    // Init diagram
    this.diagram = diagram;

    // Instantiate a new CompilationUnit. The argument to the compilation unit is the "codebase" or directory where the
    // compilation unit should be written. Make a new compilation unit rooted to the given sourcepath.
    CompilationUnit unit = virtualMachine.newCompilationUnit(outputDirectory);

    // Set the package namespace.
    namespace = unit.setNamespace(testClassDescriptor.getPckge());
    // Comment the package with a javadoc (DocumentationComment).
    namespace.setComment(Comment.D, "Auto-Generated using the Jenesis Syntax API");

    // Make a new class.
    packageClass = unit.newClass(testClassDescriptor.getClassName());
    testsRunnerType = testClassDescriptor.getTestsRunnerType();

    // init helper
    helper.set(namespace, packageClass);

    // Creates a new class
    createClass();

    // Write the java file.
    try {
      unit.encode();
    }
    catch (IOException exc) {
      throw new UmlTestGeneratorException("Cannot save generated test class", exc);
    }
  }

  protected void createClass() {
    // Make it a public class.
    packageClass.setAccess(Access.PUBLIC);
    // Comment the class with a javadoc (DocumentationComment).
    packageClass.setComment(Comment.D, "Generated testing class of UML diagram");

    // Add annotation for Fix method order
    Annotation annotation = helper.newAnnotation(FixMethodOrder.class);
    annotation.addAnntationAttribute("value", helper.newFieldAccess(MethodSorters.class, "NAME_ASCENDING"));
    packageClass.addAnnotation(annotation);

    // Add annotation for Run With
    annotation = helper.newAnnotation(RunWith.class);
    switch (testsRunnerType) {
      case SINGLE_FAIL :
        // annotation.addAnntationAttribute("value", helper.newClassLiteral(BlockJUnit4ClassRunnerWithSingleFail.class));
        annotation.addAnntationAttribute("value", helper.newFieldAccess(BlockJUnit4ClassRunnerWithSingleFail.class, "class"));
        break;

      case WITH_DEPENDENCIES :
        // annotation.addAnntationAttribute("value", helper.newClassLiteral(BlockJUnit4ClassRunnerWithDependencies.class));
        annotation.addAnntationAttribute("value", helper.newFieldAccess(BlockJUnit4ClassRunnerWithDependencies.class, "class"));
        break;
    }

    packageClass.addAnnotation(annotation);

    // creates test class fields
    createsFields();

    // creates method for before class
    createSetUpBeforeClass();
    // creates method for after class
    createTearDownAfterClass();

    // creates tests of notes
    createNotesTests();
    // creates tests of classes
    createClassesTests();
    // creates tests of relations
    createRelationsTests();
  }

  protected void createsFields() {
    // adds static field for diagram
    ClassField field = null;

    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_COUNT_OF_CROSSING_TEST)) {
      field = helper.newField(virtualMachine.newType(Type.INT), MAXIMAL_RELATIONS_CROSSINGS);
      field.setExpression(virtualMachine.newInt(propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_MAX_COUNT_OF_CROSSING, true)));
      field.isFinal(true);
      field.isStatic(true);
    }

    field = helper.newField(UmlDiagram.class, DIAGRAM_FIELD_NAME);
    field.isStatic(true);

    field = helper.newField(String.class, TEST_NUMBER_FIELD_NAME);
    field.isStatic(true);

    field = helper.newField(String.class, PERSONAL_NUMBER_FIELD_NAME);
    field.isStatic(true);
  }

  protected void createSetUpBeforeClass() {
    // creates method
    ClassMethod method = helper.newMethod(Type.VOID, "setUpBeforeClass");
    method.addAnnotation(helper.newAnnotation(BeforeClass.class));
    method.isStatic(true);
    method.addThrows(helper.getImportedClass(UxfException.class).getSimpleName());

    // creates local variable file
    Invoke propertiesInvoke = helper.newInvoke(System.class, "getProperty");
    propertiesInvoke.addArg("fileName");
    Let file = helper.newLocalVariable(method, String.class, "file", propertiesInvoke);

    // gets last index of _
    Invoke lastIndexOf = helper.newInvoke(file, "lastIndexOf");
    lastIndexOf.addArg("_");
    Let index1 = helper.newLocalVariable(method, Type.INT, "index1", lastIndexOf);

    // gets last index of .
    lastIndexOf = helper.newInvoke(file, "lastIndexOf");
    lastIndexOf.addArg(".");
    Let index2 = helper.newLocalVariable(method, Type.INT, "index2", lastIndexOf);

    // assign of test number
    Variable testNumber = virtualMachine.newVar(TEST_NUMBER_FIELD_NAME);
    Invoke substring = helper.newInvoke(file, "substring");
    substring.addArg(virtualMachine.newBinary(Binary.SUB, helper.getSimpleLocalVariable(index1), virtualMachine.newInt(2)));
    substring.addArg(helper.getSimpleLocalVariable(index1));
    method.newStmt(virtualMachine.newAssign(testNumber, substring));

    // assign of personal number
    Variable parsonalNumber = virtualMachine.newVar(PERSONAL_NUMBER_FIELD_NAME);
    substring = helper.newInvoke(file, "substring");
    substring.addArg(virtualMachine.newBinary(Binary.ADD, helper.getSimpleLocalVariable(index1), virtualMachine.newInt(1)));
    substring.addArg(helper.getSimpleLocalVariable(index2));
    method.newStmt(virtualMachine.newAssign(parsonalNumber, substring));

    // getting instance of factory
    Invoke getInstance = helper.newInvoke(UxfReaderFactoryImpl.class, "getInstance");
    Let factory = helper.newLocalVariable(method, UxfReaderFactory.class, "factory", getInstance);

    // getting instance of reader
    Invoke getReader = helper.newInvoke(factory, "getReader");
    getReader.addVarriableArg(helper.getSimpleLocalVariableName(file));
    Let reader = helper.newLocalVariable(method, UxfReader.class, "reader", getReader);

    // reading of diagram
    Variable diagram = virtualMachine.newVar(DIAGRAM_FIELD_NAME);
    Invoke readDiagram = helper.newInvoke(reader, "readDiagram");
    readDiagram.addVarriableArg(helper.getSimpleLocalVariableName(file));
    method.newStmt(virtualMachine.newAssign(diagram, readDiagram));
  }

  protected void createTearDownAfterClass() {
    // creates method
    ClassMethod method = helper.newMethod(Type.VOID, "tearDownAfterClass");
    method.addAnnotation(helper.newAnnotation(AfterClass.class));
    method.isStatic(true);

    // clean personalNumber variable
    Variable personalNumber = virtualMachine.newVar(PERSONAL_NUMBER_FIELD_NAME);
    method.newStmt(virtualMachine.newAssign(personalNumber, virtualMachine.newNull()));

    // clean testNumber variable
    Variable testNumber = virtualMachine.newVar(TEST_NUMBER_FIELD_NAME);
    method.newStmt(virtualMachine.newAssign(testNumber, virtualMachine.newNull()));

    // clean diagram variable
    Variable diagram = virtualMachine.newVar(DIAGRAM_FIELD_NAME);
    method.newStmt(virtualMachine.newAssign(diagram, virtualMachine.newNull()));
  }

  protected Invoke createAssertion(String assertionName, Expression... expressions) {
    Invoke assertion = helper.newInvoke(UmlAssert.class, assertionName);
    assertion.addVarriableArg(DIAGRAM_FIELD_NAME);
    if (expressions != null && expressions.length > 0) {
      for (Expression expression : expressions) {
        assertion.addArg(expression);
      }
    }

    return assertion;
  }

  protected ClassMethod createTestMethod(String methodName, Invoke assertion) {
    return createTestMethod(methodName, null, assertion);
  }

  protected ClassMethod createTestMethod(String methodName, String[] dependencies, Invoke assertion) {
    ClassMethod testMethod = helper.newMethod(Type.VOID, counter.getTestName(methodName));
    testMethod.addAnnotation(helper.newAnnotation(Test.class));

    if (dependencies != null && dependencies.length > 0) {
      Annotation testDependencies = helper.newAnnotation(TestDependencies.class);
      testDependencies.addAnntationAttribute("value", virtualMachine.newArrayInit(helper.toExpressionArray(dependencies)));
      testMethod.addAnnotation(testDependencies);
    }

    testMethod.newStmt(assertion);

    return testMethod;
  }

  protected void createNotesTests() {
    // increment first level for classes
    counter.incrementFirstLevelCounter();

    if (!propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.NOTES_TESTS)) {
      return;
    }

    // used only for getting and setting dependencies, could be used diagram.getNotes().get(0)
    UmlNote note = new UmlNote();

    // Tests correct notes count
    counter.incrementSecondLevelCounter();
    createNoteExistenceTest(note);
    String[] noteExistenceDependency = dependencies.getElementDependencies(note);

    counter.incrementSecondLevelCounter();
    // Tests note has correct format
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.NOTE_WITH_CORRECT_FORMAT_TEST)) {
      createNoteWithCorrectFormatTest(note);
    }

    // Tests note has correct data
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.NOTE_WITH_CORRECT_DATA_TEST)) {
      createNoteWithCorrectDataTest(note);
    }

    // Tests note not overlap other elements
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.NOTE_NOT_OVERLAP_TEST)) {
      createNoteNotOverlapTest(noteExistenceDependency);
    }
  }

  protected void createNoteExistenceTest(UmlNote note) {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertContainsOneNote");
    ClassMethod testMethod = createTestMethod("containsOneNote", assertion);
    dependencies.addElementDepenency(note, testMethod.getName());
  }

  protected void createNoteWithCorrectFormatTest(UmlNote note) {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertNoteHasCorrectFormat");
    ClassMethod testMethod = createTestMethod("hasNoteWithCorrectFormat", dependencies.getElementDependencies(note), assertion);
    dependencies.addElementDepenency(note, testMethod.getName());
  }

  protected void createNoteWithCorrectDataTest(UmlNote note) {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertNoteHasCorrectData");
    assertion.addVarriableArg(PERSONAL_NUMBER_FIELD_NAME);
    assertion.addVarriableArg(TEST_NUMBER_FIELD_NAME);
    createTestMethod("hasNoteWithCorrectData", dependencies.getElementDependencies(note), assertion);
  }

  protected void createNoteNotOverlapTest(String[] noteExistenceDependency) {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertNotesNotOverlap");
    createTestMethod("noteNotOverlap", noteExistenceDependency, assertion);
  }

  protected void createClassesTests() {
    // increment first level for classes
    counter.incrementFirstLevelCounter();

    if (!propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_TESTS)) {
      return;
    }

    // list of classes for whose will be generated tests
    List<UmlClass> classes = diagram.getClasses();

    // increment second level for classes names
    counter.incrementSecondLevelCounter();
    // Tests classes contains unique names
    createClassesWithUniqueNamesTest();
    // Tests class existence
    createClassesExistenceTests(classes);

    // Tests of class type
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_HAS_CORRECT_TYPE_TESTS)) {
      createClassesHasCorrectTypeTests(classes);
    }

    // Tests of correct classes count
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_CORRECT_COUNT_TEST)) {
      createClassesCorrectCountTest();
    }

    // Tests of class background/foreground
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_IS_NOT_COLORED_TESTS)) {
      createClassesIsNotColoredTests(classes);
    }

    // Tests of font for class name
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_NAME_HAS_CORRECT_FONT_TESTS)) {
      createClassesNameHasCorrectFontTests(classes);
    }

    // Tests of additional text in class
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_HAS_NO_ADDITIONAL_TEXT_TESTS)) {
      createClassesHasNoAdditionalTextTests(classes);
    }

    // Test of classes overlapping
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.CLASSES_HAS_NO_ADDITIONAL_TEXT_TESTS)) {
      createClassesNotOverlap();
    }
  }

  protected void createClassesWithUniqueNamesTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertContainsClassesWithUniqueNames");
    ClassMethod testMethod = createTestMethod("containsClassesWithUniqueNames", assertion);
    testMethod.addAnnotation(helper.newAnnotation(ImportantTest.class));
  }

  protected void createClassesExistenceTests(List<UmlClass> classes) {
    for (UmlClass clazz : classes) {
      counter.incrementThirdLevelCounter();
      Invoke assertion = createAssertion("assertClassExists", virtualMachine.newString(clazz.getName()));
      ClassMethod testMethod = createTestMethod("existsClass_" + clazz.getName(), assertion);
      dependencies.addElementDepenency(clazz, testMethod.getName());
    }
  }

  protected void createClassesHasCorrectTypeTests(List<UmlClass> classes) {
    for (UmlClass clazz : classes) {
      counter.incrementThirdLevelCounter();
      Invoke assertion = createAssertion("assertCorrectClassType", virtualMachine.newString(clazz.getName()),
          helper.newAccess(UmlClassType.class, clazz.getClassType()));
      createTestMethod("hasCorrectType_" + clazz.getName(), dependencies.getElementDependencies(clazz), assertion);
    }
  }

  protected void createClassesCorrectCountTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertClassesCount", virtualMachine.newInt(diagram.getClassesCount()));
    createTestMethod("hasCorrectClassesCount", assertion);
  }

  protected void createClassesIsNotColoredTests(List<UmlClass> classes) {
    for (UmlClass clazz : classes) {
      counter.incrementThirdLevelCounter();
      Invoke assertion = createAssertion("assertClassIsNotColored", virtualMachine.newString(clazz.getName()));
      createTestMethod("isNotColored_" + clazz.getName(), dependencies.getElementDependencies(clazz), assertion);
    }
  }

  protected void createClassesNameHasCorrectFontTests(List<UmlClass> classes) {
    for (UmlClass clazz : classes) {
      counter.incrementThirdLevelCounter();

      UmlFont font = clazz.getAttributes().getAttribute(UmlElementAttributesNames.CLASS_NAME_FONT, UmlFont.class);
      NewClass umlFont = helper.newClass(UmlFont.class);
      umlFont.addArg(virtualMachine.newInt((font == null) ? 0 : font.getStyle()));

      Invoke assertion = createAssertion("assertClassNameHasCorrectFont", virtualMachine.newString(clazz.getName()), umlFont);
      createTestMethod("nameHasCorrectFont_" + clazz.getName(), dependencies.getElementDependencies(clazz), assertion);
    }
  }

  protected void createClassesHasNoAdditionalTextTests(List<UmlClass> classes) {
    for (UmlClass clazz : classes) {
      counter.incrementThirdLevelCounter();
      Invoke assertion = createAssertion("assertClassHasNoAdditionalText", virtualMachine.newString(clazz.getName()));
      createTestMethod("hasNoAdditionalText_" + clazz.getName(), dependencies.getElementDependencies(clazz), assertion);
    }
  }

  protected void createClassesNotOverlap() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertClassesNotOverlap");
    createTestMethod("classesNotOverlap", assertion);
  }

  protected void createRelationsTests() {
    // increment first level for relations
    counter.incrementFirstLevelCounter();

    if (!propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_TESTS)) {
      return;
    }

    // Tests classes contains unique names
    counter.incrementSecondLevelCounter();
    Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs = getRelatedClassesPairs();
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      if (relations.size() == 0) {
        Invoke assertion = createAssertion("assertNoRelationBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName));
        createTestMethod("hasNoRelationsBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(from, to), assertion);
      }
      else {
        Invoke assertion = createAssertion("assertRelationsCountBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName),
            virtualMachine.newInt(related.getValue().size()));
        ClassMethod testMethod = createTestMethod("hasCorrectRelationsCountBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(from, to),
            assertion);
        dependencies.addElementDepenency(relations.get(0), testMethod.getName());
      }
    }

    // Tests of correct relation orientation type
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_HAS_CORRECT_ORIENTATION_TESTS)) {
      createRelationsHasCorrectOrientationTests(relatedClassesPairs);
    }

    // Tests of correct relation type
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_HAS_CORRECT_TYPE_TESTS)) {
      createRelationsHasCorrectTypeTests(relatedClassesPairs);
    }

    // Tests of correct relation stereotypes
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_HAS_CORRECT_STEREOTYPE_TESTS)) {
      createRelationsHasCorrectStereotypeTests(relatedClassesPairs);
    }

    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_HAS_CORRECT_QUALIFIERS_TESTS)) {
      createRelationsHasCorrectQualifiersTests(relatedClassesPairs);
    }

    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_HAS_CORRECT_MULTIPLICITY_TESTS)) {
      createRelationsHasCorrectMultiplicityTests(relatedClassesPairs);
    }

    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_HAS_CORRECT_ROLES_TESTS)) {
      createRelationsHasCorrectRolesTests(relatedClassesPairs);
    }

    // Tests if there are blind or unused relations.
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_NOT_BLIND_TEST)) {
      createRelationsNotBlindTest();
    }

    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_NOT_UNUSED_TEST)) {
      createRelationsNotUnusedTest();
    }

    // Tests of relation geometry
    counter.incrementSecondLevelCounter();
    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_COUNT_OF_CROSSING_TEST)) {
      createRelationsCountOfCrossingTest();
    }

    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_NOT_EXTENDING_CLASS_EDGE_TEST)) {
      createRelationsNotExtendingClassEdgeTest();
    }

    if (propertiesProvider.getValue(UmlTestGeneratorPropertyKeys.RELATIONS_NOT_CROSSING_ELEMENTS_TEST)) {
      createRelationsNotCrossingElementsTest();
    }
  }

  protected void createRelationsHasCorrectOrientationTests(Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs) {
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      if (relations.size() == 0) {
        continue;
      }

      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      UmlRelationOrientationType[] types = UmlUtils.getRelationsOrientationTypes(diagram, relations.toArray(new UmlRelation[relations.size()]), from, to);

      NewArray array = helper.newArray(UmlRelationOrientationType.class, types);
      Invoke assertion = createAssertion("assertRelationsOrientationTypesBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName), array);
      createTestMethod("hasCorrectRelationsOrientationTypesBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)),
          assertion);
    }
  }

  protected void createRelationsHasCorrectTypeTests(Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs) {
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      if (relations.size() == 0) {
        continue;
      }

      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      UmlRelationType[] types = new UmlRelationType[relations.size()];
      for (int i = 0; i < relations.size(); i++) {
        types[i] = relations.get(i).getRelationType();
      }

      NewArray array = helper.newArray(UmlRelationType.class, types);
      Invoke assertion = createAssertion("assertRelationsTypesBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName), array);
      createTestMethod("hasCorrectRelationsTypesBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
    }
  }

  protected void createRelationsHasCorrectStereotypeTests(Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs) {
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      if (relations.size() == 0) {
        continue;
      }

      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      String[] stereotypes = new String[relations.size()];
      for (int i = 0; i < relations.size(); i++) {
        UmlElementAttributes attributes = relations.get(i).getAttributes();
        stereotypes[i] = attributes.getAttribute(UmlElementAttributesNames.STEREOTYPE, String.class);
      }

      if (Arrays.equals(stereotypes, new String[relations.size()])) {
        Invoke assertion = createAssertion("assertRelationsWithNoStereotypeBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName));
        createTestMethod("hasRelationsWithNoStereotypesBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
        continue;
      }

      NewArray array = helper.newArray(stereotypes);
      Invoke assertion = createAssertion("assertRelationsStereotypesBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName), array);
      createTestMethod("hasCorrectRelationsStereotypesBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
    }
  }

  protected void createRelationsHasCorrectQualifiersTests(Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs) {
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      if (relations.size() == 0) {
        continue;
      }

      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      String[][] qualifiers = new String[relations.size()][2];
      for (int i = 0; i < relations.size(); i++) {
        UmlElementAttributes attributes = relations.get(i).getAttributes();
        qualifiers[i][0] = attributes.getAttribute(UmlElementAttributesNames.QUALIFIER_FROM, String.class);
        qualifiers[i][1] = attributes.getAttribute(UmlElementAttributesNames.QUALIFIER_TO, String.class);
      }

      boolean empty = true;
      for (int i = 0; i < qualifiers.length; i++) {
        empty &= Arrays.equals(qualifiers[i], new String[2]);
      }

      if (empty) {
        Invoke assertion = createAssertion("assertRelationsWithNoQualifiersBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName));
        createTestMethod("hasRelationsWithNoQualifiersBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
        continue;
      }

      NewArray array = helper.newArray(qualifiers);
      Invoke assertion = createAssertion("assertRelationsQualifiersBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName), array);
      createTestMethod("hasCorrectRelationsQualifiersBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
    }
  }

  protected void createRelationsHasCorrectMultiplicityTests(Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs) {
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      if (relations.size() == 0) {
        continue;
      }

      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      String[][] multiplicity = new String[relations.size()][2];
      for (int i = 0; i < relations.size(); i++) {
        UmlElementAttributes attributes = relations.get(i).getAttributes();
        multiplicity[i][0] = attributes.getAttribute(UmlElementAttributesNames.MULTIPLICITY_FROM, String.class);
        multiplicity[i][1] = attributes.getAttribute(UmlElementAttributesNames.MULTIPLICITY_TO, String.class);
      }

      boolean empty = true;
      for (int i = 0; i < multiplicity.length; i++) {
        empty &= Arrays.equals(multiplicity[i], new String[2]);
      }

      if (empty) {
        Invoke assertion = createAssertion("assertRelationsWithNoMultiplicityBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName));
        createTestMethod("hasRelationsWithNoMultiplicityBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
        continue;
      }

      NewArray array = helper.newArray(multiplicity);
      Invoke assertion = createAssertion("assertRelationsMultiplicityBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName), array);
      createTestMethod("hasCorrectRelationsMultiplicityBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
    }
  }

  protected void createRelationsHasCorrectRolesTests(Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relatedClassesPairs) {
    for (Entry<Pair<UmlClass, UmlClass>, List<UmlRelation>> related : relatedClassesPairs.entrySet()) {
      counter.incrementThirdLevelCounter();

      List<UmlRelation> relations = related.getValue();
      if (relations.size() == 0) {
        continue;
      }

      UmlClass from = related.getKey().getLeft();
      String fromName = from.getName();

      UmlClass to = related.getKey().getRight();
      String toName = to.getName();

      String[][] roles = new String[relations.size()][2];
      for (int i = 0; i < relations.size(); i++) {
        UmlElementAttributes attributes = relations.get(i).getAttributes();
        roles[i][0] = attributes.getAttribute(UmlElementAttributesNames.ROLE_FROM, String.class);
        roles[i][1] = attributes.getAttribute(UmlElementAttributesNames.ROLE_TO, String.class);
      }

      boolean empty = true;
      for (int i = 0; i < roles.length; i++) {
        empty &= Arrays.equals(roles[i], new String[2]);
      }

      if (empty) {
        Invoke assertion = createAssertion("assertRelationsWithNoRolesBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName));
        createTestMethod("hasRelationsWithNoRolesBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
        continue;
      }

      NewArray array = helper.newArray(roles);
      Invoke assertion = createAssertion("assertRelationsRolesBetween", virtualMachine.newString(fromName), virtualMachine.newString(toName), array);
      createTestMethod("hasCorrectRelationsRolesBetween__" + fromName + "_" + toName, dependencies.getElementDependencies(relations.get(0)), assertion);
    }
  }

  protected void createRelationsNotBlindTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertContainsNoBlindRelations");
    createTestMethod("containsNoBlindRelations", assertion);
  }

  protected void createRelationsNotUnusedTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertContainsNoUnusedRelations");
    createTestMethod("containsNoUnusedRelations", assertion);
  }

  protected void createRelationsCountOfCrossingTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertCountOfRelationsCrossings");
    assertion.addVarriableArg(MAXIMAL_RELATIONS_CROSSINGS);
    createTestMethod("countOfRelationsCrossings", assertion);
  }

  protected void createRelationsNotExtendingClassEdgeTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertRelationsNotExtendingClassEdge");
    createTestMethod("relationsNotExtendingClassEdge", assertion);
  }

  protected void createRelationsNotCrossingElementsTest() {
    counter.incrementThirdLevelCounter();
    Invoke assertion = createAssertion("assertRelationsNotCrossingElements");
    createTestMethod("relationsNotCrossingElements", assertion);
  }

  protected Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> getRelatedClassesPairs() {
    Map<Pair<UmlClass, UmlClass>, List<UmlRelation>> relations = new LinkedHashMap<Pair<UmlClass, UmlClass>, List<UmlRelation>>();
    List<UmlClass> classes = diagram.getClasses();
    for (int i = 0; i < classes.size() - 1; i++) {
      for (int j = i + 1; j < classes.size(); j++) {
        UmlClass class1 = classes.get(i);
        UmlClass class2 = classes.get(j);

        CombinationPair<UmlClass, UmlClass> pair = new CombinationPair<UmlClass, UmlClass>(class1, class2);
        relations.put(pair, new ArrayList<UmlRelation>());
      }
    }

    for (UmlRelation relation : diagram.getRelations()) {
      Pair<UmlClass, UmlClass> pair = new CombinationPair<UmlClass, UmlClass>(relation.getFrom(), relation.getTo());
      List<UmlRelation> relationsList = relations.get(pair);
      if (relationsList == null) {
        relationsList = new ArrayList<UmlRelation>();
        relations.put(pair, relationsList);
      }

      relationsList.add(relation);
    }

    return relations;
  }

}
