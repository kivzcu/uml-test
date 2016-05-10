package cz.zcu.kiv.oop.uml.test.generator;

import java.io.File;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "generate-tests", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GenerateTestsMojo extends AbstractGenerateTestsMojo {

  @Parameter(name = "testsRunnerType", defaultValue = "WITH_DEPENDENCIES", required = true)
  protected TestsRunnerType testsRunnerType;
  @Parameter(name = "outputJavaDirectory", defaultValue = "${project.build.directory}/generated-sources/java", required = true, readonly = true)
  protected File outputJavaDirectory;

  @Override
  protected File getOutputDirectory() {
    return outputJavaDirectory;
  }

  @Override
  protected void addOutputDirectoryToCompileSourceRoot() {
    mavenProject.addCompileSourceRoot(getOutputDirectoryAbsolutePath());
  }

  @Override
  protected TestsRunnerType getTestsRunnerType() {
    return testsRunnerType;
  }

}
