package de.svenleonhard.tournamentmanager;

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
            .importPackages("de.svenleonhard.tournamentmanager");

        noClasses()
            .that()
            .resideInAnyPackage("de.svenleonhard.tournamentmanager.service..")
            .or()
            .resideInAnyPackage("de.svenleonhard.tournamentmanager.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..de.svenleonhard.tournamentmanager.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
