package cz.zcu.kiv.oop.uml.junit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks some test method as dependent on one or more tests. All dependent tests have to be finished successfully to run of marked
 * test. If one or more dependent tests failed or was skipped the marked test will be ignored. If one or more dependent tests will
 * not run before this test, the exception is raised.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestDependencies {

  /**
   * One or more dependent test(s).
   *
   * @return Array of dependent tests.
   */
  String[] value();

}
