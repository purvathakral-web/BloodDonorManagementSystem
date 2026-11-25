package exception;

/**
 * ============================================================================
 * EXCEPTION: DonorNotFoundException (Checked Exception)
 * SYLLABUS TOPIC: Unit 1C - Checked Exceptions & Custom Exceptions
 * ============================================================================
 *
 * Extends java.lang.Exception, making it a Checked Exception.
 * Thrown when a requested donor ID does not exist in the system.
 */
public class DonorNotFoundException extends Exception
{
    private final String donorId;

    public DonorNotFoundException(String message)
    {
        super(message);
        this.donorId = "UNKNOWN";
    }

    public DonorNotFoundException(String donorId, String message)
    {
        super(message);
        this.donorId = donorId;
    }

    public String getDonorId()
    {
        return donorId;
    }
}
