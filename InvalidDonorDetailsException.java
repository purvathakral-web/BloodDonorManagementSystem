package exception;

/**
 * ============================================================================
 * EXCEPTION: InvalidDonorDetailsException (Unchecked Exception)
 * SYLLABUS TOPIC: Unit 1C - Unchecked Exceptions (RuntimeException)
 * ============================================================================
 *
 * Extends RuntimeException, making it an Unchecked Exception.
 * Thrown when donor input values (name, age, weight, phone, city) fail
 * domain validation rules.
 */
public class InvalidDonorDetailsException extends RuntimeException
{
    public InvalidDonorDetailsException(String message)
    {
        super(message);
    }
}
