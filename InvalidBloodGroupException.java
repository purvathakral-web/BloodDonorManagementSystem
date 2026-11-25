package exception;

/**
 * ============================================================================
 * EXCEPTION: InvalidBloodGroupException (Checked Exception)
 * SYLLABUS TOPIC: Unit 1C - Checked Exceptions & Custom Exceptions
 * ============================================================================
 *
 * Thrown when a user enters a blood group that is not among the eight
 * valid groups: A+, A-, B+, B-, AB+, AB-, O+, O-.
 */
public class InvalidBloodGroupException extends Exception
{
    private final String invalidGroup;

    public InvalidBloodGroupException(String invalidGroup, String message)
    {
        super(message);
        this.invalidGroup = invalidGroup;
    }

    public InvalidBloodGroupException(String message)
    {
        super(message);
        this.invalidGroup = "";
    }

    public String getInvalidGroup()
    {
        return invalidGroup;
    }
}
