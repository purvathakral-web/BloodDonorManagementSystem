package model;

import interfaces.Displayable;

/**
 * ============================================================================
 * CLASS: Hospital
 * SYLLABUS TOPIC: Unit 1A - Encapsulation, Constructor Overloading, Interfaces,
 *                           Object Modeling (Association)
 * ============================================================================
 *
 * Represents a hospital or blood bank associated with Patients and BloodRequests.
 */
public class Hospital implements Displayable
{
    private String hospitalId;
    private String hospitalName;
    private String city;
    private String contactNumber;
    private boolean emergencyWardAvailable;

    public Hospital(String hospitalId, String hospitalName, String city)
    {
        this(hospitalId, hospitalName, city, "0000000000", true);
    }

    public Hospital(String hospitalId, String hospitalName, String city,
                    String contactNumber, boolean emergencyWardAvailable)
    {
        this.hospitalId = hospitalId;
        this.hospitalName = (hospitalName != null) ? hospitalName.trim() : "General Hospital";
        this.city = (city != null) ? city.trim() : "Unknown";
        this.contactNumber = (contactNumber != null) ? contactNumber.trim() : "0000000000";
        this.emergencyWardAvailable = emergencyWardAvailable;
    }

    public String getHospitalId()
    {
        return hospitalId;
    }

    public String getHospitalName()
    {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName)
    {
        this.hospitalName = hospitalName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public boolean isEmergencyWardAvailable()
    {
        return emergencyWardAvailable;
    }

    public void setEmergencyWardAvailable(boolean emergencyWardAvailable)
    {
        this.emergencyWardAvailable = emergencyWardAvailable;
    }

    @Override
    public String getDisplaySummary()
    {
        return String.format("%s | %s (%s) | Contact: %s | Emergency: %s",
                hospitalId, hospitalName, city, contactNumber,
                emergencyWardAvailable ? "Yes" : "No");
    }

    @Override
    public void displayDetails()
    {
        System.out.println(getDisplaySummary());
    }

    @Override
    public String toString()
    {
        return hospitalName + " (" + city + ")";
    }
}
