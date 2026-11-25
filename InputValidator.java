package utility;

import exception.InvalidDonorDetailsException;
import interfaces.Validatable;

/**
 * ============================================================================
 * CLASS: InputValidator
 * SYLLABUS TOPIC: Unit 1A - Interfaces (implements Validatable)
 *                 Unit 1B - String Immutability, String Manipulation, Searching
 *                 Unit 1C - Unchecked Exception (throw InvalidDonorDetailsException)
 * ============================================================================
 *
 * Validates general text fields such as names, cities, and emails.
 * Also demonstrates String immutability by creating new cleaned Strings
 * rather than modifying the original input reference.
 */
public class InputValidator implements Validatable
{
    @Override
    public boolean validate(String input)
    {
        return input != null && !input.trim().isEmpty();
    }

    @Override
    public String getValidationRule()
    {
        return "Input must not be null or empty.";
    }

    /**
     * Validates that a person's name has at least 2 characters and contains
     * only letters and spaces.
     */
    public boolean isValidName(String name)
    {
        if (!validate(name))
        {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.length() < 2)
        {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++)
        {
            char ch = trimmed.charAt(i);
            if (!Character.isLetter(ch) && ch != ' ' && ch != '.')
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Basic email format check using String searching (indexOf / contains).
     */
    public boolean isValidEmail(String email)
    {
        if (!validate(email))
        {
            return false;
        }
        String clean = email.trim();
        int atIndex = clean.indexOf('@');
        int dotIndex = clean.lastIndexOf('.');
        return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < clean.length() - 1;
    }

    /**
     * Demonstrates String immutability and String manipulation:
     * trim() and replace() always return brand-new String objects.
     */
    public String sanitizeText(String rawText)
    {
        if (rawText == null)
        {
            return "";
        }
        // String immutability: calling trim() does NOT alter rawText itself.
        String trimmed = rawText.trim();
        String collapsed = trimmed.replace("  ", " ");
        return collapsed;
    }

    public void requireValidName(String name)
    {
        if (!isValidName(name))
        {
            throw new InvalidDonorDetailsException(
                    "Invalid name '" + name + "'. Name must be at least 2 alphabetic characters.");
        }
    }

    public void requireNonEmpty(String value, String fieldName)
    {
        if (!validate(value))
        {
            throw new InvalidDonorDetailsException(fieldName + " cannot be empty.");
        }
    }
}
