package model;

/**
 * ============================================================================
 * CLASS: DonorAddress
 * SYLLABUS TOPIC: Unit 1A - Encapsulation, Constructor Overloading, this keyword,
 *                           Object Modeling (Composition)
 *                 Unit 1B - String Manipulation & StringBuilder
 * ============================================================================
 *
 * Value object representing a postal address composed inside Person/Donor.
 */
public class DonorAddress
{
    private String street;
    private String city;
    private String state;
    private String pincode;

    // Default constructor using constructor chaining with this(...)
    public DonorAddress()
    {
        this("Not Provided", "Unknown", "Unknown", "000000");
    }

    // Overloaded 2-argument constructor
    public DonorAddress(String city, String state)
    {
        this("Main Road", city, state, "000000");
    }

    // Master 4-argument constructor
    public DonorAddress(String street, String city, String state, String pincode)
    {
        this.street = (street != null) ? street.trim() : "Not Provided";
        this.city = (city != null) ? city.trim() : "Unknown";
        this.state = (state != null) ? state.trim() : "Unknown";
        this.pincode = (pincode != null) ? pincode.trim() : "000000";
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = (street != null) ? street.trim() : "";
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = (city != null) ? city.trim() : "";
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = (state != null) ? state.trim() : "";
    }

    public String getPincode()
    {
        return pincode;
    }

    public void setPincode(String pincode)
    {
        this.pincode = (pincode != null) ? pincode.trim() : "";
    }

    /**
     * Builds a formatted single-line address using StringBuilder.
     */
    public String getFormattedAddress()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(street).append(", ")
          .append(city).append(", ")
          .append(state).append(" - ")
          .append(pincode);
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return getFormattedAddress();
    }
}
