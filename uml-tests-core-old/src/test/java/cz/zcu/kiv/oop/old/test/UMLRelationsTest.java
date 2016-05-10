package cz.zcu.kiv.oop.old.test;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.old.uml.UMLDiagram;
import cz.zcu.kiv.oop.old.uml.UMLRelationType;

public class UMLRelationsTest {

  // Constants for relations number checking.
  protected static final int RELATIONS_NUMBER_MAX_TO = 2;
  protected static final int RELATIONS_NUMBER_MAX_FROM = 2;
  protected static final int RELATIONS_NUMBER_AVE_TO = 1;
  protected static final int RELATIONS_NUMBER_AVE_FROM = 1;
  protected static final int RELATIONS_NUMBER_MIN_TO = 0;
  protected static final int RELATIONS_NUMBER_MIN_FROM = 0;
  protected static final int RELATIONS_TOTAL_NUMBER = 2;

  /**
   * UML diagram object.
   */
  protected static UMLDiagram ud;

  @BeforeClass
  public static void setUpBeforeClass() {
    URL url = UMLObjectsTest.class.getResource("relationsTest.uxf");
    ud = new UMLDiagram(url.getFile());
  }

  @Test
  public void testRelationsExist() {
    assertTrue("GENERALIZATION1 relation exists: ", ud.existAnyRelationBetween("PersonGENERALIZATION1", "WorkGENERALIZATION1"));
    assertTrue("GENERALIZATION2 relation exists: ", ud.existAnyRelationBetween("PersonGENERALIZATION2", "WorkGENERALIZATION2"));

    assertTrue("REALIZATION1 relation exists: ", ud.existAnyRelationBetween("PersonREALIZATION1", "WorkREALIZATION1"));
    assertTrue("REALIZATION2 relation exists: ", ud.existAnyRelationBetween("PersonREALIZATION2", "WorkREALIZATION2"));

    assertTrue("ASSOCIATION1 relation exists: ", ud.existAnyRelationBetween("PersonASSOCIATION1", "WorkASSOCIATION1"));
    assertTrue("ASSOCIATION2 relation exists: ", ud.existAnyRelationBetween("PersonASSOCIATION2", "WorkASSOCIATION2"));
    assertTrue("ASSOCIATION3 relation exists: ", ud.existAnyRelationBetween("PersonASSOCIATION3", "WorkASSOCIATION3"));
    assertTrue("ASSOCIATION4 relation exists: ", ud.existAnyRelationBetween("PersonASSOCIATION4", "WorkASSOCIATION4"));

    assertTrue("DEPENDENCY1 relation exists: ", ud.existAnyRelationBetween("PersonDEPENDENCY1", "WorkDEPENDENCY1"));
    assertTrue("DEPENDENCY2 relation exists: ", ud.existAnyRelationBetween("PersonDEPENDENCY2", "WorkDEPENDENCY2"));

    assertTrue("DEPENDENCY3 relation exists: ", ud.existAnyRelationBetween("PersonDEPENDENCY3", "WorkDEPENDENCY3"));

    assertTrue("AGGREGATION1 relation exists: ", ud.existAnyRelationBetween("PersonAGGREGATION1", "WorkAGGREGATION1"));
    assertTrue("AGGREGATION2 relation exists: ", ud.existAnyRelationBetween("PersonAGGREGATION2", "WorkAGGREGATION2"));

    assertTrue("COMPOSITION1 relation exists: ", ud.existAnyRelationBetween("PersonCOMPOSITION1", "WorkCOMPOSITION1"));
    assertTrue("COMPOSITION2 relation exists: ", ud.existAnyRelationBetween("PersonCOMPOSITION2", "WorkCOMPOSITION2"));

    assertTrue("DASHEDLINE relation exists: ", ud.existAnyRelationBetween("PersonDASHEDLINE", "WorkDASHEDLINE"));
  }

  @Test
  public void testGeneralizationDirection() {
    assertTrue("GENERALIZATION1 direction exist: ", ud.checkRelationFromTo("PersonGENERALIZATION1", "WorkGENERALIZATION1"));
    assertTrue("GENERALIZATION2 direction exist: ", ud.checkRelationFromTo("WorkGENERALIZATION2", "PersonGENERALIZATION2"));
  }

  @Test
  public void testRealizationDirection() {
    assertTrue("REALIZATION1 direction exist: ", ud.checkRelationFromTo("PersonREALIZATION1", "WorkREALIZATION1"));
    assertTrue("REALIZATION2 direction exist: ", ud.checkRelationFromTo("WorkREALIZATION2", "PersonREALIZATION2"));
  }

  @Test
  public void testAssociationDirection() {
    assertTrue("ASSOCIATION1 direction exist: ", ud.checkRelationFromTo("PersonASSOCIATION1", "WorkASSOCIATION1"));
    assertTrue("ASSOCIATION2 direction exist: ", ud.checkRelationFromTo("WorkASSOCIATION2", "PersonASSOCIATION2"));
  }

  @Test
  public void testDependencyDirection() {
    assertTrue("DEPENDENCY1 direction exist: ", ud.checkRelationFromTo("PersonDEPENDENCY1", "WorkDEPENDENCY1"));
    assertTrue("DEPENDENCY2 direction exist: ", ud.checkRelationFromTo("WorkDEPENDENCY2", "PersonDEPENDENCY2"));
  }

  @Test
  public void testAggregationDirection() {
    assertTrue("AGGREGATION1 direction exist: ", ud.checkRelationFromTo("PersonAGGREGATION1", "WorkAGGREGATION1"));
    assertTrue("AGGREGATION2 direction exist: ", ud.checkRelationFromTo("WorkAGGREGATION2", "PersonAGGREGATION2"));
  }

  @Test
  public void tesCompositionDirection() {
    assertTrue("COMPOSITION1 direction exist: ", ud.checkRelationFromTo("PersonCOMPOSITION1", "WorkCOMPOSITION1"));
    assertTrue("COMPOSITION2 direction exist: ", ud.checkRelationFromTo("WorkCOMPOSITION2", "PersonCOMPOSITION2"));
  }

  @Test
  public void testRelationsTypes() {
    assertTrue("GENERALIZATION1 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.GENERALIZATION, "PersonGENERALIZATION1", "WorkGENERALIZATION1"));
    assertTrue("GENERALIZATION2 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.GENERALIZATION, "WorkGENERALIZATION2", "PersonGENERALIZATION2"));

    assertTrue("REALIZATION1 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.REALIZATION, "PersonREALIZATION1", "WorkREALIZATION1"));
    assertTrue("REALIZATION2 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.REALIZATION, "WorkREALIZATION2", "PersonREALIZATION2"));

    assertTrue("ASSOCIATION1 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.ASSOCIATION, "PersonASSOCIATION1", "WorkASSOCIATION1"));
    assertTrue("ASSOCIATION2 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.ASSOCIATION, "WorkASSOCIATION2", "PersonASSOCIATION2"));

    assertTrue("ASSOCIATION3 relation type: ", ud.checkRelationType(UMLRelationType.ASSOCIATION, "PersonASSOCIATION3", "WorkASSOCIATION3"));
    assertTrue("ASSOCIATION4 relation type: ", ud.checkRelationType(UMLRelationType.ASSOCIATION, "WorkASSOCIATION4", "PersonASSOCIATION4"));

    assertTrue("DEPENDENCY1 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.DEPENDENCY, "PersonDEPENDENCY1", "WorkDEPENDENCY1"));
    assertTrue("DEPENDENCY2 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.DEPENDENCY, "WorkDEPENDENCY2", "PersonDEPENDENCY2"));

    assertTrue("DEPENDENCY3 relation type: ", ud.checkRelationType(UMLRelationType.DEPENDENCY, "WorkDEPENDENCY3", "PersonDEPENDENCY3"));

    assertTrue("COMPOSITION1 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.COMPOSITION, "PersonCOMPOSITION1", "WorkCOMPOSITION1"));
    assertTrue("COMPOSITION2 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.COMPOSITION, "WorkCOMPOSITION2", "PersonCOMPOSITION2"));

    assertTrue("AGGREGATION1 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.AGGREGATION, "PersonAGGREGATION1", "WorkAGGREGATION1"));
    assertTrue("AGGREGATION2 relation type: ", ud.checkRelationTypeFromTo(UMLRelationType.AGGREGATION, "WorkAGGREGATION2", "PersonAGGREGATION2"));

    assertTrue("DASHEDLINE relation exists: ", ud.checkRelationType(UMLRelationType.DASHEDLINE, "PersonDASHEDLINE", "WorkDASHEDLINE"));
  }

  @Test
  public void testRelationsNumber() {
    // GENERALIZATION
    assertTrue("NumberGENERALIZATION1 RELATIONS_NUMBER_MAX_TO bad relations number",
        RELATIONS_NUMBER_MAX_TO == ud.getNumberOfRelationsTo("NumberGENERALIZATION1"));
    assertTrue("NumberGENERALIZATION1 RELATIONS_NUMBER_MIN_TO bad relations number",
        RELATIONS_NUMBER_MIN_FROM == ud.getNumberOfRelationFrom("NumberGENERALIZATION1"));
    assertTrue("NumberGENERALIZATION1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberGENERALIZATION1"));

    assertTrue("NumberGENERALIZATION2", RELATIONS_NUMBER_MIN_TO == ud.getNumberOfRelationsTo("NumberGENERALIZATION2"));
    assertTrue("NumberGENERALIZATION2", RELATIONS_NUMBER_MAX_FROM == ud.getNumberOfRelationFrom("NumberGENERALIZATION2"));
    assertTrue("NumberGENERALIZATION2", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberGENERALIZATION2"));

    assertTrue("NumberGENERALIZATION3", RELATIONS_NUMBER_AVE_TO == ud.getNumberOfRelationsTo("NumberGENERALIZATION3"));
    assertTrue("NumberGENERALIZATION3", RELATIONS_NUMBER_AVE_FROM == ud.getNumberOfRelationFrom("NumberGENERALIZATION3"));
    assertTrue("NumberGENERALIZATION3", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberGENERALIZATION3"));

    // REALIZATION
    assertTrue("NumberREALIZATION1 RELATIONS_NUMBER_MAX_TO bad relations number", RELATIONS_NUMBER_MAX_TO == ud.getNumberOfRelationsTo("NumberREALIZATION1"));
    assertTrue("NumberREALIZATION1 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_FROM == ud.getNumberOfRelationFrom("NumberREALIZATION1"));
    assertTrue("NumberREALIZATION1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberREALIZATION1"));

    assertTrue("NumberREALIZATION2", RELATIONS_NUMBER_MIN_TO == ud.getNumberOfRelationsTo("NumberREALIZATION2"));
    assertTrue("NumberREALIZATION2", RELATIONS_NUMBER_MAX_FROM == ud.getNumberOfRelationFrom("NumberREALIZATION2"));
    assertTrue("NumberREALIZATION2", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberREALIZATION2"));

    assertTrue("NumberREALIZATION3", RELATIONS_NUMBER_AVE_TO == ud.getNumberOfRelationsTo("NumberREALIZATION3"));
    assertTrue("NumberREALIZATION3", RELATIONS_NUMBER_AVE_FROM == ud.getNumberOfRelationFrom("NumberREALIZATION3"));
    assertTrue("NumberREALIZATION3", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberREALIZATION3"));

    // ASSOCIATION
    assertTrue("NumberASSOCIATION1 RELATIONS_NUMBER_MAX_TO bad relations number", RELATIONS_NUMBER_MAX_TO == ud.getNumberOfRelationsTo("NumberASSOCIATION1"));
    assertTrue("NumberASSOCIATION1 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_FROM == ud.getNumberOfRelationFrom("NumberASSOCIATION1"));
    assertTrue("NumberASSOCIATION1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberASSOCIATION1"));

    assertTrue("NumberASSOCIATION2 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_TO == ud.getNumberOfRelationsTo("NumberASSOCIATION2"));
    assertTrue("NumberASSOCIATION2 RELATIONS_NUMBER_MAX_FROM bad relations number",
        RELATIONS_NUMBER_MAX_FROM == ud.getNumberOfRelationFrom("NumberASSOCIATION2"));
    assertTrue("NumberASSOCIATION2 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberASSOCIATION2"));

    assertTrue("NumberASSOCIATION3 RELATIONS_NUMBER_AVE_TO bad relations number", RELATIONS_NUMBER_AVE_TO == ud.getNumberOfRelationsTo("NumberASSOCIATION3"));
    assertTrue("NumberASSOCIATION3 RELATIONS_NUMBER_AVE_FROM bad relations number",
        RELATIONS_NUMBER_AVE_FROM == ud.getNumberOfRelationFrom("NumberASSOCIATION3"));
    assertTrue("NumberASSOCIATION3 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberASSOCIATION3"));

    // ASSOCIATION
    assertTrue("NumberDEPENDENCY1 RELATIONS_NUMBER_MAX_TO bad relations number", RELATIONS_NUMBER_MAX_TO == ud.getNumberOfRelationsTo("NumberDEPENDENCY1"));
    assertTrue("NumberDEPENDENCY1 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_FROM == ud.getNumberOfRelationFrom("NumberDEPENDENCY1"));
    assertTrue("NumberDEPENDENCY1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDEPENDENCY1"));

    assertTrue("NumberDEPENDENCY2 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_TO == ud.getNumberOfRelationsTo("NumberDEPENDENCY2"));
    assertTrue("NumberDEPENDENCY2 RELATIONS_NUMBER_MAX_FROM bad relations number", RELATIONS_NUMBER_MAX_FROM == ud.getNumberOfRelationFrom("NumberDEPENDENCY2"));
    assertTrue("NumberDEPENDENCY2 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDEPENDENCY2"));

    assertTrue("NumberDEPENDENCY3 RELATIONS_NUMBER_AVE_TO bad relations number", RELATIONS_NUMBER_AVE_TO == ud.getNumberOfRelationsTo("NumberDEPENDENCY3"));
    assertTrue("NumberDEPENDENCY3 RELATIONS_NUMBER_AVE_FROM bad relations number", RELATIONS_NUMBER_AVE_FROM == ud.getNumberOfRelationFrom("NumberDEPENDENCY3"));
    assertTrue("NumberDEPENDENCY3 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDEPENDENCY3"));

    // AGGREGATION
    assertTrue("NumberAGGREGATION1 RELATIONS_NUMBER_MAX_TO bad relations number", RELATIONS_NUMBER_MAX_TO == ud.getNumberOfRelationsTo("NumberAGGREGATION1"));
    assertTrue("NumberAGGREGATION1 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_FROM == ud.getNumberOfRelationFrom("NumberAGGREGATION1"));
    assertTrue("NumberAGGREGATION1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberAGGREGATION1"));

    assertTrue("NumberAGGREGATION2 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_TO == ud.getNumberOfRelationsTo("NumberAGGREGATION2"));
    assertTrue("NumberAGGREGATION2 RELATIONS_NUMBER_MAX_FROM bad relations number",
        RELATIONS_NUMBER_MAX_FROM == ud.getNumberOfRelationFrom("NumberAGGREGATION2"));
    assertTrue("NumberAGGREGATION2 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberAGGREGATION2"));

    assertTrue("NumberAGGREGATION3 RELATIONS_NUMBER_AVE_TO bad relations number", RELATIONS_NUMBER_AVE_TO == ud.getNumberOfRelationsTo("NumberAGGREGATION3"));
    assertTrue("NumberAGGREGATION3 RELATIONS_NUMBER_AVE_FROM bad relations number",
        RELATIONS_NUMBER_AVE_FROM == ud.getNumberOfRelationFrom("NumberAGGREGATION3"));
    assertTrue("NumberAGGREGATION3 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberAGGREGATION3"));

    // COMPOSITION
    assertTrue("NumberCOMPOSITION1 RELATIONS_NUMBER_MAX_TO bad relations number", RELATIONS_NUMBER_MAX_TO == ud.getNumberOfRelationsTo("NumberCOMPOSITION1"));
    assertTrue("NumberCOMPOSITION1 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_FROM == ud.getNumberOfRelationFrom("NumberCOMPOSITION1"));
    assertTrue("NumberCOMPOSITION1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberCOMPOSITION1"));

    assertTrue("NumberCOMPOSITION2 RELATIONS_NUMBER_MIN_TO bad relations number", RELATIONS_NUMBER_MIN_TO == ud.getNumberOfRelationsTo("NumberCOMPOSITION2"));
    assertTrue("NumberCOMPOSITION2 RELATIONS_NUMBER_MAX_FROM bad relations number",
        RELATIONS_NUMBER_MAX_FROM == ud.getNumberOfRelationFrom("NumberCOMPOSITION2"));
    assertTrue("NumberCOMPOSITION2 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberCOMPOSITION2"));

    assertTrue("NumberCOMPOSITION3 RELATIONS_NUMBER_AVE_TO bad relations number", RELATIONS_NUMBER_AVE_TO == ud.getNumberOfRelationsTo("NumberCOMPOSITION3"));
    assertTrue("NumberCOMPOSITION3 RELATIONS_NUMBER_AVE_FROM bad relations number",
        RELATIONS_NUMBER_AVE_FROM == ud.getNumberOfRelationFrom("NumberCOMPOSITION3"));
    assertTrue("NumberCOMPOSITION3 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberCOMPOSITION3"));

    // ASSOCIATION WITHOUT ARROWS
    assertTrue("NumberASSOCIATION21 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberASSOCIATION21"));
    assertTrue("NumberASSOCIATION22", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberASSOCIATION22"));
    assertTrue("NumberASSOCIATION23", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberASSOCIATION33"));

    // DEPENDENCY WITH TWO ARROWS
    assertTrue("NumberDEPENDENCY11 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDEPENDENCY11"));
    assertTrue("NumberDEPENDENCY22", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDEPENDENCY22"));
    assertTrue("NumberDEPENDENCY33", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDEPENDENCY33"));

    // DASHED LINE
    assertTrue("NumberDASHEDLINE1 RELATIONS_TOTAL_NUMBER bad relations number", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDASHEDLINE1"));
    assertTrue("NumberDASHEDLINE2", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDASHEDLINE2"));
    assertTrue("NumberDASHEDLINE3", RELATIONS_TOTAL_NUMBER == ud.getNumberOfRelations("NumberDASHEDLINE3"));
  }

  @Test
  public void testGetRelationType() {
    assertTrue("Bad relation: " + ud.getRelationTypeBetween("GetRelationType1", "GetRelationType2"),
        ud.getRelationTypeBetween("GetRelationType1", "GetRelationType2") == UMLRelationType.GENERALIZATION);
    assertTrue("Bad unknown relation: " + ud.getRelationTypeBetween("GetRelationType3", "GetRelationType2"),
        ud.getRelationTypeBetween("GetRelationType3", "GetRelationType2") == UMLRelationType.UNDEFINED);
  }

  @Test
  public void testEmptyPropertiesAtLines() {
    // this test is useless, it's function is only to mark out the problem.
    // If there are some empty lines
    // in line properties, all tests result in error.
    assertTrue("Empty properties at lines", ud.existAnyRelationBetween("BadConnection1", "BadConnection2"));
  }

  @Test
  public void testConnectedElements() {
    List<String> connectionsTest1 = Arrays.asList(new String[] {"ConnectionsTest2", "ConnectionsTest3", "ConnectionsTest4"});
    Collection<String> connectedElements1 = ud.getAllConnectedElements("ConnectionsTest1");
    connectedElements1.removeAll(connectionsTest1);
    assertTrue("ConnectionsTest1 has invalid connections: " + connectedElements1, connectedElements1.isEmpty());

    List<String> connectionsTest2 = Arrays.asList(new String[] {"ConnectionsTest1", "ConnectionsTest4"});
    Collection<String> connectedElements2 = ud.getAllConnectedElements("ConnectionsTest2");
    connectedElements2.removeAll(connectionsTest2);
    assertTrue("ConnectionsTest2 has invalid connections: " + connectedElements2, connectedElements2.size() == 1);

    List<String> connectionsTest3 = Arrays.asList(new String[] {"ConnectionsTest1", "ConnectionsTest4"});
    Collection<String> connectedElements3 = ud.getAllConnectedElements("ConnectionsTest3");
    connectedElements3.removeAll(connectionsTest3);
    assertTrue("ConnectionsTest3 has invalid connections: " + connectedElements3, connectedElements3.isEmpty());

    List<String> connectionsTest4 = Arrays.asList(new String[] {"ConnectionsTest2", "ConnectionsTest3", "ConnectionsTest1"});
    Collection<String> connectedElements4 = ud.getAllConnectedElements("ConnectionsTest4");
    connectedElements4.removeAll(connectionsTest4);
    assertTrue("ConnectionsTest4 has invalid connections: " + connectedElements4, connectedElements4.size() == 2);
  }

}
