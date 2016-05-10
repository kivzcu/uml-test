package cz.zcu.kiv.oop.uml.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks some test method as important. Important test is dependency for all others tests. If marked test fails, the whole tests
 * running will be interrupted.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ImportantTest {

}
