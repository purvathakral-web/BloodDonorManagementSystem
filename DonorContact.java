package model;

/**
 * ============================================================================
 * CLASS: DonorContact
 * SYLLABUS TOPIC: Unit 1A - Encapsulation, Constructor Overloading, this keyword
 *                 Unit 1B - String Manipulation & String Formatting
 * ============================================================================
 *
 * Encapsulates contact details (mobile number, email, emergency phone)
 * composed inside Person/Donor.
 */
public class DonorContact
{
    private String phoneNumber;
    private String email;
    private String emergencyContact;

    public DonorContact()
    {
        this("0000000000", "notprovided@example.com", "Not Provided");
    }

    public DonorContact(String phoneNumber, String email)
    {
        this(phoneNumber, email, "Not Provided");
    }

    public DonorContact(String phoneNumber, String email, String emergencyContact)
    {
        this.phoneNumber = (phoneNumber != null) ? phoneNumber.trim() : "";
        this.email = (email != null) ? email.trim().toLowerCase() : "";
        this.emergencyContact = (emergencyContact != null) ? emergencyContact.trim() : "Not Provided";
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = (phoneNumber != null) ? phoneNumber.trim() : "";
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = (email != null) ? email.trim().toLowerCase() : "";
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = (emergencyContact != null) ? emergencyContact.trim() : "Not Provided";
    }

    /**
     * Masks the middle digits of the phone number using String substring manipulation.
     */
    public String getMaskedPhoneNumber()
    {
        if (phoneNumber.length() == 10)
        {
            return phoneNumber.substring(0, 2) + "XXXXXX" + phoneNumber.substring(8);
        }
        return phoneNumber;
    }

    @Override
    public String toString()
    {
        return String.format("Phone: %s | Email: %s | Emergency: %s",
                phoneNumber, email, emergencyContact);
    }
}
