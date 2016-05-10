package cz.zcu.kiv.oop.uml.test.generator.configuration;

import cz.zcu.kiv.oop.properties.keys.BooleanPropertyKey;
import cz.zcu.kiv.oop.properties.keys.IntegerPropertyKey;
import cz.zcu.kiv.oop.properties.keys.PropertyKey;

public interface UmlTestGeneratorPropertyKeys {

  BooleanPropertyKey NOTES_TESTS = new BooleanPropertyKey("uml.notes.tests", true);

  BooleanPropertyKey NOTE_WITH_CORRECT_FORMAT_TEST = new BooleanPropertyKey("uml.notes.test.note_with_correct_format", true);

  BooleanPropertyKey NOTE_WITH_CORRECT_DATA_TEST = new BooleanPropertyKey("uml.notes.test.note_with_correct_data", true);

  BooleanPropertyKey NOTE_NOT_OVERLAP_TEST = new BooleanPropertyKey("uml.notes.test.note_not_overlap", true);

  BooleanPropertyKey CLASSES_TESTS = new BooleanPropertyKey("uml.classes.tests", true);

  BooleanPropertyKey CLASSES_HAS_CORRECT_TYPE_TESTS = new BooleanPropertyKey("uml.classes.tests.has_correct_type", true);

  BooleanPropertyKey CLASSES_CORRECT_COUNT_TEST = new BooleanPropertyKey("uml.classes.test.correct_count", true);

  BooleanPropertyKey CLASSES_IS_NOT_COLORED_TESTS = new BooleanPropertyKey("uml.classes.tests.is_not_colored", true);

  BooleanPropertyKey CLASSES_NAME_HAS_CORRECT_FONT_TESTS = new BooleanPropertyKey("uml.classes.tests.name_has_correct_font", true);

  BooleanPropertyKey CLASSES_HAS_NO_ADDITIONAL_TEXT_TESTS = new BooleanPropertyKey("uml.classes.tests.has_no_additional_text", true);

  BooleanPropertyKey CLASSES_NOT_OVERLAP_TEST = new BooleanPropertyKey("uml.classes.test.not_overlap", true);

  BooleanPropertyKey RELATIONS_TESTS = new BooleanPropertyKey("uml.relations.tests", true);

  BooleanPropertyKey RELATIONS_HAS_CORRECT_ORIENTATION_TESTS = new BooleanPropertyKey("uml.relations.tests.has_correct_orientation_type", true);

  BooleanPropertyKey RELATIONS_HAS_CORRECT_TYPE_TESTS = new BooleanPropertyKey("uml.relations.tests.has_correct_type", true);

  BooleanPropertyKey RELATIONS_HAS_CORRECT_STEREOTYPE_TESTS = new BooleanPropertyKey("uml.relations.tests.has_correct_stereotype", true);

  BooleanPropertyKey RELATIONS_HAS_CORRECT_QUALIFIERS_TESTS = new BooleanPropertyKey("uml.relations.tests.has_correct_qualifiers", true);

  BooleanPropertyKey RELATIONS_HAS_CORRECT_MULTIPLICITY_TESTS = new BooleanPropertyKey("uml.relations.tests.has_correct_multiplicity", true);

  BooleanPropertyKey RELATIONS_HAS_CORRECT_ROLES_TESTS = new BooleanPropertyKey("uml.relations.tests.has_correct_roles", true);

  BooleanPropertyKey RELATIONS_NOT_BLIND_TEST = new BooleanPropertyKey("uml.relations.test.not_blind", true);

  BooleanPropertyKey RELATIONS_NOT_UNUSED_TEST = new BooleanPropertyKey("uml.relations.test.not_unused", true);

  IntegerPropertyKey RELATIONS_MAX_COUNT_OF_CROSSING = new IntegerPropertyKey("uml.relations.test.count_of_crossing.max", 3);

  BooleanPropertyKey RELATIONS_COUNT_OF_CROSSING_TEST = new BooleanPropertyKey("uml.relations.test.count_of_crossing", true);

  PropertyKey<Boolean> RELATIONS_NOT_EXTENDING_CLASS_EDGE_TEST = new BooleanPropertyKey("uml.relations.test.not_extending_class_edge", true);

  PropertyKey<Boolean> RELATIONS_NOT_CROSSING_ELEMENTS_TEST = new BooleanPropertyKey("uml.relations.test.not_crossing_elements", true);

}
