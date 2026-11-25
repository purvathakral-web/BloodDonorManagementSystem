package exception;

/**
 * ============================================================================
 * EXCEPTION: BloodRequestNotFoundException (Checked Exception)
 * SYLLABUS TOPIC: Unit 1C - Checked Exceptions & Custom Exceptions
 * ============================================================================
 *
 * Thrown when searching for or updating a BloodRequest ID that does not exist.
 */
public class BloodRequestNotFoundException extends Exception
{
    public BloodRequestNotFoundException(String message)
    {
        super(message);
    }
}
