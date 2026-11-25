package utility;

import exception.InvalidDonorDetailsException;
import interfaces.Validatable;
import model.DonorEligibility;

/**
 * ============================================================================
 * CLASS: AgeValidator
 * SYLLABUS TOPIC: Unit 1A - Interfaces (implements Validatable)
 *                 Unit 1B - Wrapper Classes (Integer.parseInt, Double.parseDouble)
 *                 Unit 1C - try-catch (NumberFormatException) & Custom Exceptions
 * ============================================================================
 *
 * Validates donor age (18 to 65 years) and body weight (>= 50.0 kg).
 */
public class AgeValidator implements Validatable
{
    @Override
    public boolean validate(String ageText)
    {
        if (ageText == null || ageText.trim().isEmpty())
        {
            return false;
        }
        try
        {
            Integer parsedAge = Integer.valueOf(Integer.parseInt(ageText.trim()));
            return isValidAge(parsedAge.intValue());
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

    @Override
    public String getValidationRule()
    {
        return "Donor age must be between " + DonorEligibility.MIN_ELIGIBLE_AGE
                + " and " + DonorEligibility.MAX_ELIGIBLE_AGE + " years.";
    }

    public boolean isValidAge(int age)
    {
        return age >= DonorEligibility.MIN_ELIGIBLE_AGE
                && age <= DonorEligibility.MAX_ELIGIBLE_AGE;
    }

    public boolean isValidWeight(Double weightKg)
    {
        return weightKg != null && weightKg.doubleValue() >= DonorEligibility.MIN_ELIGIBLE_WEIGHT_KG;
    }

    public void validateAgeOrThrow(int age)
    {
        if (!isValidAge(age))
        {
            throw new InvalidDonorDetailsException(
                    "Invalid donor age (" + age + "). " + getValidationRule());
        }
    }

    public void validateWeightOrThrow(Double weightKg)
    {
        if (!isValidWeight(weightKg))
        {
            throw new InvalidDonorDetailsException(
                    "Invalid donor weight (" + weightKg + " kg). Minimum weight required is "
                            + DonorEligibility.MIN_ELIGIBLE_WEIGHT_KG + " kg.");
        }
    }
}
