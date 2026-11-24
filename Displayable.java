package interfaces;

/**
 * ============================================================================
 * INTERFACE: Displayable
 * SYLLABUS TOPIC: Unit 1A - Abstraction and Interfaces
 * ============================================================================
 *
 * Defines a common contract for domain objects that can present formatted
 * information on the console. Enables runtime polymorphism when displaying
 * different entities (Donor, Patient, Hospital, BloodRequest, DonationRecord).
 */
public interface Displayable
{
    /**
     * Returns a concise single-line or multi-line formatted summary.
     */
    String getDisplaySummary();

    /**
     * Prints detailed information about the object to the console.
     */
    void displayDetails();
}
