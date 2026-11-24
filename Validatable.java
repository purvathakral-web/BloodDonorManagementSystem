package interfaces;

/**
 * ============================================================================
 * INTERFACE: Validatable
 * SYLLABUS TOPIC: Unit 1A - Abstraction and Interfaces
 * ============================================================================
 *
 * Common contract implemented by all input validator utility classes.
 * Demonstrates interface-based polymorphism across specialized validators.
 */
public interface Validatable
{
    /**
     * Checks whether the provided string input satisfies this validator's rule.
     */
    boolean validate(String input);

    /**
     * Returns a human-readable description of the validation rule.
     */
    String getValidationRule();
}
