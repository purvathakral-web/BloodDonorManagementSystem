package utility;

/**
 * ============================================================================
 * CLASS: IDGenerator
 * SYLLABUS TOPIC: Unit 1A - static keyword (static fields, static methods,
 *                           static initialization block)
 *                 Unit 1B - String Formatting (String.format)
 * ============================================================================
 *
 * Generates unique prefixed identifiers for Donors, Patients, Hospitals,
 * Blood Requests, and Donation Records without needing a database.
 */
public class IDGenerator
{
    private static int donorCounter;
    private static int patientCounter;
    private static int hospitalCounter;
    private static int requestCounter;
    private static int donationCounter;

    // Static initialization block
    static
    {
        donorCounter = 1000;
        patientCounter = 2000;
        hospitalCounter = 3000;
        requestCounter = 4000;
        donationCounter = 5000;
    }

    public static String nextDonorId()
    {
        donorCounter++;
        return String.format("DNR-%d", donorCounter);
    }

    public static String nextPatientId()
    {
        patientCounter++;
        return String.format("PAT-%d", patientCounter);
    }

    public static String nextHospitalId()
    {
        hospitalCounter++;
        return String.format("HSP-%d", hospitalCounter);
    }

    public static String nextRequestId()
    {
        requestCounter++;
        return String.format("REQ-%d", requestCounter);
    }

    public static String nextDonationId()
    {
        donationCounter++;
        return String.format("DON-%d", donationCounter);
    }
}
