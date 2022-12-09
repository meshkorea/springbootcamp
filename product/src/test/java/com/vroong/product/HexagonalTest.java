package com.vroong.product;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

public class HexagonalTest {

  static final JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("com.vroong.product");

  @Test
  public void domainShouldNotDependOnAnyOtherPackages() {
    noClasses()
        .that()
        .resideInAnyPackage("com.vroong.product.domain..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("com.vroong.product.application..", "com.vroong.product.adapter..")
        .because("Domain should not depend on any other packages")
        .check(importedClasses);
  }

  @Test
  public void applicationShouldNotDependOnAdapter() {
    noClasses()
        .that()
        .resideInAnyPackage("com.vroong.product.application..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("com.vroong.product.adapter..")
        .because("Application should not depend on any adapter packages")
        .check(importedClasses);
  }
}
