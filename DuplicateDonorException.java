package exception;

/**
 * ============================================================================
 * EXCEPTION: DuplicateDonorException (Checked Exception)
 * SYLLABUS TOPIC: Unit 1C - Checked Exceptions & Custom Exceptions
 * ============================================================================
 *
 * Thrown when attempting to register a donor whose ID or phone number
 * is already present in the unique tracking collections (HashSet).
 */
public class DuplicateDonorException extends Exception
{
    public DuplicateDonorException(String message)
    {
        super(message);
    }
}
