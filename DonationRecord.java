package model;

import interfaces.Displayable;

/**
 * ============================================================================
 * CLASS: DonationRecord
 * SYLLABUS TOPIC: Unit 1A - Classes, Objects, Encapsulation, Interfaces
 *                 Unit 1B - Wrapper Classes (Integer) & String Formatting
 * ============================================================================
 *
 * Represents a single completed blood donation event made by a donor.
 */
public class DonationRecord implements Displayable
{
    private String recordId;
    private String donorId;
    private String donorName;
    private String bloodGroup;
    private Integer unitsDonated;   // Wrapper class
    private String donationDate;
    private String hospitalName;
    private String remarks;

    public DonationRecord(String recordId, String donorId, String donorName,
                          String bloodGroup, Integer unitsDonated,
                          String donationDate, String hospitalName, String remarks)
    {
        this.recordId = recordId;
        this.donorId = donorId;
        this.donorName = donorName;
        this.bloodGroup = bloodGroup;
        this.unitsDonated = (unitsDonated != null && unitsDonated > 0) ? unitsDonated : Integer.valueOf(1);
        this.donationDate = donationDate;
        this.hospitalName = hospitalName;
        this.remarks = (remarks != null) ? remarks : "Normal Donation";
    }

    public String getRecordId()
    {
        return recordId;
    }

    public String getDonorId()
    {
        return donorId;
    }

    public String getDonorName()
    {
        return donorName;
    }

    public String getBloodGroup()
    {
        return bloodGroup;
    }

    public Integer getUnitsDonated()
    {
        return unitsDonated;
    }

    public String getDonationDate()
    {
        return donationDate;
    }

    public String getHospitalName()
    {
        return hospitalName;
    }

    public String getRemarks()
    {
        return remarks;
    }

    @Override
    public String getDisplaySummary()
    {
        return String.format("%s | Donor: %s (%s) | Group: %-3s | Units: %d | Date: %s | Hospital: %s",
                recordId, donorName, donorId, bloodGroup, unitsDonated, donationDate, hospitalName);
    }

    @Override
    public void displayDetails()
    {
        System.out.println(getDisplaySummary());
        System.out.println("  Remarks : " + remarks);
    }

    @Override
    public String toString()
    {
        return getDisplaySummary();
    }
}
