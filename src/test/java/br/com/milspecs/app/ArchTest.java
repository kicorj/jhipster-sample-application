package br.com.milspecs.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.com.milspecs.app");

        noClasses()
            .that()
            .resideInAnyPackage("br.com.milspecs.app.service..")
            .or()
            .resideInAnyPackage("br.com.milspecs.app.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..br.com.milspecs.app.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}