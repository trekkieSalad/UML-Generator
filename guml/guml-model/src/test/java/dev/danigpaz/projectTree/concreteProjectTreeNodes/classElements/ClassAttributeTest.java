package dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements;

import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectClass;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ModifierType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassAttributeTest {

    private ClassAttribute classAttributeUnderTest;

    @BeforeEach
    void setUp() {
        classAttributeUnderTest = new ClassAttribute();
    }

    @Test
    void testAddModifier() {
        // Setup
        // Run the test
        classAttributeUnderTest.addModifier(ModifierType.PUBLIC);
        classAttributeUnderTest.addModifier(ModifierType.STATIC);
        classAttributeUnderTest.addModifier(ModifierType.FINAL);

        assertTrue(classAttributeUnderTest.getModifiers().contains(ModifierType.PUBLIC));
        assertTrue(classAttributeUnderTest.getModifiers().contains(ModifierType.STATIC));
        assertTrue(classAttributeUnderTest.getModifiers().contains(ModifierType.FINAL));

        assertFalse(classAttributeUnderTest.getModifiers().contains(ModifierType.PRIVATE));
        assertFalse(classAttributeUnderTest.getModifiers().contains(ModifierType.PROTECTED));
        assertFalse(classAttributeUnderTest.getModifiers().contains(ModifierType.ABSTRACT));
        assertFalse(classAttributeUnderTest.getModifiers().contains(ModifierType.NATIVE));
        assertFalse(classAttributeUnderTest.getModifiers().contains(ModifierType.SYNCHRONIZED));
    }
}
