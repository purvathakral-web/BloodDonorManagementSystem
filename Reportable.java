package interfaces;

/**
 * ============================================================================
 * INTERFACE: Reportable
 * SYLLABUS TOPIC: Unit 1A - Abstraction and Interfaces
 * ============================================================================
 *
 * Defines the reporting contract for generating textual statistics and
 * availability summaries in the Blood Donor Management System.
 */
public interface Reportable
{
    String generateSummaryReport();

    String generateBloodGroupReport();

    String generateAvailabilityMatrixReport();
}
