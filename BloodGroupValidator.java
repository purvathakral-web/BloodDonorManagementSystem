package utility;

import exception.InvalidBloodGroupException;
import interfaces.Validatable;
import model.BloodGroupManager;

/**
 * ============================================================================
 * CLASS: BloodGroupValidator
 * SYLLABUS TOPIC: Unit 1A - Interfaces (implements Validatable)
 *                 Unit 1B - 1D Array Lookup & String Comparison
 *                 Unit 1C - Checked Exception (throw / throws InvalidBloodGroupException)
 * ============================================================================
 *
 * Validates blood group inputs against the 8 supported groups in BloodGroupManager.
 */
public class BloodGroupValidator implements Validatable
{
    @Override
    public boolean validate(String bloodGroup)
    {
        return BloodGroupManager.isValidBloodGroup(bloodGroup);
    }

    @Override
    public String getValidationRule()
    {
        return "Supported blood groups: A+, A-, B+, B-, AB+, AB-, O+, O-.";
    }

    /**
     * Validates and returns the normalized blood group string, or throws
     * checked InvalidBloodGroupException.
     */
    public String validateAndNormalize(String bloodGroup) throws InvalidBloodGroupException
    {
        if (!validate(bloodGroup))
        {
            throw new InvalidBloodGroupException(
                    bloodGroup,
                    "Unsupported blood group '" + bloodGroup + "'. " + getValidationRule());
        }
        return BloodGroupManager.normalizeBloodGroup(bloodGroup);
    }
}
