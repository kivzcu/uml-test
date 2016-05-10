package cz.zcu.kiv.oop.uml.test.generator;

import java.io.File;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "test-generate-tests", defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES)
public class TestGenerateTestsMojo extends AbstractGenerateTestsMojo {

  @Parameter(name = "outputTestJavaDirectory", defaultValue = "${project.build.directory}/generated-test-sources/java", required = true, readonly = true)
  protected File outputTestJavaDirectory;

  @Override
  protected File getOutputDirectory() {
    return outputTestJavaDirectory;
  }

  @Override
  protected void addOutputDirectoryToCompileSourceRoot() {
    mavenProject.addTestCompileSourceRoot(getOutputDirectoryAbsolutePath());
  }

  @Override
  protected TestsRunnerType getTestsRunnerType() {
    return TestsRunnerType.WITH_DEPENDENCIES;
  }

}
