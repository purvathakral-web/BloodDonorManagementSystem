package model;

/**
 * ============================================================================
 * CLASS: DonorEligibility
 * SYLLABUS TOPIC: Unit 1A - Encapsulation, Static Constants, Object Modeling
 *                 Unit 1B - Wrapper Classes (Integer, Double, Boolean)
 * ============================================================================
 *
 * Encapsulates blood donation eligibility rules:
 *   - Age must be between 18 and 65 years (inclusive)
 *   - Body weight must be at least 50.0 kg
 *   - Donor must be marked available
 */
public class DonorEligibility
{
    public static final int MIN_ELIGIBLE_AGE = 18;
    public static final int MAX_ELIGIBLE_AGE = 65;
    public static final double MIN_ELIGIBLE_WEIGHT_KG = 50.0;

    private Boolean eligible;
    private String reason;

    public DonorEligibility()
    {
        this.eligible = Boolean.FALSE;
        this.reason = "Not evaluated yet";
    }

    public DonorEligibility(Integer age, Double weightKg, Boolean available)
    {
        evaluate(age, weightKg, available);
    }

    /**
     * Evaluates eligibility using Wrapper Classes (Integer, Double, Boolean).
     */
    public boolean evaluate(Integer age, Double weightKg, Boolean available)
    {
        if (age == null || age.intValue() < MIN_ELIGIBLE_AGE || age.intValue() > MAX_ELIGIBLE_AGE)
        {
            this.eligible = Boolean.FALSE;
            this.reason = "Age must be between " + MIN_ELIGIBLE_AGE + " and " + MAX_ELIGIBLE_AGE + " years.";
            return false;
        }
        if (weightKg == null || weightKg.doubleValue() < MIN_ELIGIBLE_WEIGHT_KG)
        {
            this.eligible = Boolean.FALSE;
            this.reason = "Weight must be at least " + MIN_ELIGIBLE_WEIGHT_KG + " kg.";
            return false;
        }
        if (available == null || !available.booleanValue())
        {
            this.eligible = Boolean.FALSE;
            this.reason = "Donor is currently marked unavailable.";
            return false;
        }
        this.eligible = Boolean.TRUE;
        this.reason = "Eligible to donate blood.";
        return true;
    }

    public Boolean isEligible()
    {
        return eligible;
    }

    public String getReason()
    {
        return reason;
    }

    @Override
    public String toString()
    {
        return (Boolean.TRUE.equals(eligible) ? "ELIGIBLE" : "NOT ELIGIBLE") + " (" + reason + ")";
    }
}
