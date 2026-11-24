package model;

/**
 * ============================================================================
 * CLASS: Donor
 * SYLLABUS TOPIC: Unit 1A - Inheritance (extends Person), Constructor Overloading,
 *                           Method Overloading (Compile-time Polymorphism),
 *                           Method Overriding (Runtime Polymorphism),
 *                           super & this keywords, Composition
 *                 Unit 1B - Wrapper Classes (Double, Boolean, Integer)
 * ============================================================================
 *
 * Represents a registered blood donor. Inherits common attributes from Person
 * and adds blood group, weight, availability, eligibility, and donation history.
 */
public class Donor extends Person
{
    private String bloodGroup;
    private Double weightKg;              // Wrapper class
    private Boolean available;            // Wrapper class
    private String availabilityNote;
    private Integer donationCount;        // Wrapper class
    private DonorEligibility eligibility; // Composition
    private DonorHistory history;         // Composition

    // Overloaded Constructor 1 (minimal details - chains via this)
    public Donor(String id, String name, int age, String bloodGroup)
    {
        this(id, name, age, "Not Specified", new DonorContact(), new DonorAddress(),
                bloodGroup, Double.valueOf(60.0), Boolean.TRUE);
    }

    // Master Constructor using super(...) to initialize parent Person fields
    public Donor(String id, String name, int age, String gender,
                 DonorContact contact, DonorAddress address,
                 String bloodGroup, Double weightKg, Boolean available)
    {
        super(id, name, age, gender, contact, address);
        this.bloodGroup = (bloodGroup != null) ? bloodGroup.trim().toUpperCase() : "O+";
        this.weightKg = (weightKg != null) ? weightKg : Double.valueOf(55.0);
        this.available = (available != null) ? available : Boolean.TRUE;
        this.availabilityNote = Boolean.TRUE.equals(this.available)
                ? "Ready to donate"
                : "Temporarily unavailable";
        this.donationCount = Integer.valueOf(0);
        this.history = new DonorHistory();
        this.eligibility = new DonorEligibility(Integer.valueOf(age), this.weightKg, this.available);
    }

    // Method Overriding (Runtime Polymorphism)
    @Override
    public String getRole()
    {
        return "DONOR";
    }

    public String getBloodGroup()
    {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup)
    {
        if (bloodGroup != null)
        {
            this.bloodGroup = bloodGroup.trim().toUpperCase();
        }
    }

    public Double getWeightKg()
    {
        return weightKg;
    }

    public void setWeightKg(Double weightKg)
    {
        this.weightKg = weightKg;
        refreshEligibility();
    }

    @Override
    public void setAge(int age)
    {
        super.setAge(age);
        refreshEligibility();
    }

    public Boolean isAvailable()
    {
        return available;
    }

    public String getAvailabilityNote()
    {
        return availabilityNote;
    }

    // Method Overloading 1 (Compile-time Polymorphism)
    public void updateAvailability(boolean status)
    {
        this.available = Boolean.valueOf(status);
        this.availabilityNote = status ? "Ready to donate" : "Temporarily unavailable";
        refreshEligibility();
    }

    // Method Overloading 2 (Compile-time Polymorphism - extra reason parameter)
    public void updateAvailability(boolean status, String note)
    {
        this.available = Boolean.valueOf(status);
        this.availabilityNote = (note != null && !note.trim().isEmpty())
                ? note.trim()
                : (status ? "Ready to donate" : "Temporarily unavailable");
        refreshEligibility();
    }

    public Integer getDonationCount()
    {
        return donationCount;
    }

    public DonorEligibility getEligibility()
    {
        return eligibility;
    }

    public DonorHistory getHistory()
    {
        return history;
    }

    public void recordDonation(DonationRecord record)
    {
        history.addRecord(record);
        this.donationCount = Integer.valueOf(this.donationCount.intValue() + 1);
    }

    public void refreshEligibility()
    {
        if (this.eligibility == null)
        {
            this.eligibility = new DonorEligibility();
        }
        this.eligibility.evaluate(Integer.valueOf(getAge()), this.weightKg, this.available);
    }

    // Method Overriding (Runtime Polymorphism)
    @Override
    public String getDisplaySummary()
    {
        return String.format("%-8s | %-18s | Age: %-2d | Group: %-3s | City: %-12s | Status: %-11s | Donations: %d",
                getId(),
                getName(),
                getAge(),
                bloodGroup,
                getAddress().getCity(),
                Boolean.TRUE.equals(available) ? "Available" : "Unavailable",
                donationCount.intValue());
    }

    @Override
    public void displayDetails()
    {
        super.displayDetails();
        System.out.println("  Blood Group  : " + bloodGroup);
        System.out.println("  Weight       : " + String.format("%.1f kg", weightKg.doubleValue()));
        System.out.println("  Availability : " + (Boolean.TRUE.equals(available) ? "Available" : "Unavailable")
                + " (" + availabilityNote + ")");
        System.out.println("  Eligibility  : " + eligibility.toString());
        System.out.println("  Donations    : " + donationCount + " (Last: " + history.getLastDonationDate() + ")");
    }

    @Override
    public String toString()
    {
        return getDisplaySummary();
    }
}
