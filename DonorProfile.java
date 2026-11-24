package model;

/**
 * ============================================================================
 * CLASS: DonorProfile
 * SYLLABUS TOPIC: Unit 1A - Object Modeling (Composition & Aggregation)
 *                 Unit 1B - StringBuilder & String Formatting
 * ============================================================================
 *
 * Aggregates a Donor, their DonorEligibility, and their DonorHistory to build
 * a clean, formatted profile sheet for console display.
 */
public class DonorProfile
{
    private final Donor donor;

    public DonorProfile(Donor donor)
    {
        this.donor = donor;
    }

    public Donor getDonor()
    {
        return donor;
    }

    /**
     * Builds a comprehensive multi-line donor profile card using StringBuilder.
     */
    public String buildProfileCard()
    {
        if (donor == null)
        {
            return "No donor profile available.";
        }
        donor.refreshEligibility();
        StringBuilder sb = new StringBuilder();
        sb.append("+----------------------------------------------------------+\n");
        sb.append(String.format("| %-56s |\n", "DONOR PROFILE CARD - " + donor.getId()));
        sb.append("+----------------------------------------------------------+\n");
        sb.append(String.format("| Name          : %-40s |\n", donor.getName()));
        sb.append(String.format("| Age / Gender  : %-40s |\n", donor.getAge() + " yrs / " + donor.getGender()));
        sb.append(String.format("| Blood Group   : %-40s |\n", donor.getBloodGroup()));
        sb.append(String.format("| Weight        : %-40s |\n", String.format("%.1f kg", donor.getWeightKg())));
        sb.append(String.format("| Phone (Masked): %-40s |\n", donor.getContact().getMaskedPhoneNumber()));
        sb.append(String.format("| Email         : %-40s |\n", donor.getContact().getEmail()));
        sb.append(String.format("| Address       : %-40s |\n", truncate(donor.getAddress().getFormattedAddress(), 40)));
        sb.append(String.format("| Availability  : %-40s |\n",
                (Boolean.TRUE.equals(donor.isAvailable()) ? "Available" : "Unavailable")
                        + " (" + truncate(donor.getAvailabilityNote(), 22) + ")"));
        sb.append(String.format("| Eligibility   : %-40s |\n", truncate(donor.getEligibility().toString(), 40)));
        sb.append(String.format("| Total Units   : %-40s |\n",
                donor.getHistory().getTotalUnitsDonated() + " unit(s) across "
                        + donor.getDonationCount() + " donation(s)"));
        sb.append("+----------------------------------------------------------+\n");
        sb.append("Donation History:\n");
        sb.append(donor.getHistory().getFormattedHistory());
        return sb.toString();
    }

    private String truncate(String text, int maxLen)
    {
        if (text == null)
        {
            return "";
        }
        if (text.length() <= maxLen)
        {
            return text;
        }
        return text.substring(0, maxLen - 3) + "...";
    }
}
