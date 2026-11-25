package utility;

import exception.InvalidDonorDetailsException;
import interfaces.Validatable;

/**
 * ============================================================================
 * CLASS: PhoneValidator
 * SYLLABUS TOPIC: Unit 1A - Interfaces (implements Validatable)
 *                 Unit 1B - String Manipulation & Character Wrapper
 *                 Unit 1C - Unchecked Exception Throwing
 * ============================================================================
 *
 * Validates 10-digit Indian mobile phone numbers starting with 6, 7, 8, or 9.
 */
public class PhoneValidator implements Validatable
{
    @Override
    public boolean validate(String phone)
    {
        if (phone == null)
        {
            return false;
        }
        String cleaned = phone.trim();
        if (cleaned.length() != 10)
        {
            return false;
        }
        char first = cleaned.charAt(0);
        if (first != '6' && first != '7' && first != '8' && first != '9')
        {
            return false;
        }
        for (int i = 0; i < cleaned.length(); i++)
        {
            if (!Character.isDigit(cleaned.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getValidationRule()
    {
        return "Phone number must be 10 digits starting with 6, 7, 8, or 9.";
    }

    public void validateOrThrow(String phone)
    {
        if (!validate(phone))
        {
            throw new InvalidDonorDetailsException("Invalid phone number: " + phone + ". " + getValidationRule());
        }
    }
}
