package service;

import collection.BloodGroupSetManager;
import collection.DonorListManager;
import collection.DonorMapManager;
import exception.DuplicateDonorException;
import exception.InvalidBloodGroupException;
import model.Donor;
import model.DonorAddress;
import model.DonorContact;
import utility.AgeValidator;
import utility.BloodGroupValidator;
import utility.IDGenerator;
import utility.InputValidator;
import utility.PhoneValidator;

/**
 * ============================================================================
 * CLASS: DonorRegistrationService
 * SYLLABUS TOPIC: Unit 1A - Object Collaboration & Service Layer Architecture
 *                 Unit 1C - Exception Propagation (throw / throws)
 *                 Unit 2  - Synchronizing ArrayList, HashSet, TreeSet, HashMap,
 *                           LinkedHashMap, TreeMap, LinkedHashSet on Insert
 * ============================================================================
 *
 * Handles validation and registration of new blood donors into all backing
 * collection managers, preventing duplicate donor IDs or phone numbers.
 */
public class DonorRegistrationService
{
    private final DonorListManager listManager;
    private final DonorMapManager mapManager;
    private final BloodGroupSetManager bloodGroupSetManager;

    private final InputValidator inputValidator;
    private final PhoneValidator phoneValidator;
    private final AgeValidator ageValidator;
    private final BloodGroupValidator bloodGroupValidator;

    public DonorRegistrationService(DonorListManager listManager,
                                    DonorMapManager mapManager,
                                    BloodGroupSetManager bloodGroupSetManager)
    {
        this.listManager = listManager;
        this.mapManager = mapManager;
        this.bloodGroupSetManager = bloodGroupSetManager;

        this.inputValidator = new InputValidator();
        this.phoneValidator = new PhoneValidator();
        this.ageValidator = new AgeValidator();
        this.bloodGroupValidator = new BloodGroupValidator();
    }

    /**
     * Validates inputs, creates a Donor object, and stores it across all collections.
     */
    public Donor registerDonor(String name, int age, String gender, String bloodGroup,
                               double weightKg, String phone, String email,
                               String emergencyContact, String street, String city,
                               String state, String pincode, boolean available)
            throws InvalidBloodGroupException, DuplicateDonorException
    {
        String cleanName = inputValidator.sanitizeText(name);
        inputValidator.requireValidName(cleanName);
        ageValidator.validateAgeOrThrow(age);
        ageValidator.validateWeightOrThrow(Double.valueOf(weightKg));
        phoneValidator.validateOrThrow(phone);
        inputValidator.requireNonEmpty(city, "City");

        String normalizedGroup = bloodGroupValidator.validateAndNormalize(bloodGroup);

        if (listManager.containsPhoneNumber(phone.trim()))
        {
            throw new DuplicateDonorException(
                    "A donor with phone number " + phone.trim() + " is already registered.");
        }

        String donorId = IDGenerator.nextDonorId();
        if (listManager.containsDonorId(donorId))
        {
            throw new DuplicateDonorException("Duplicate Donor ID detected: " + donorId);
        }

        DonorContact contact = new DonorContact(phone, email, emergencyContact);
        DonorAddress address = new DonorAddress(street, city, state, pincode);
        Donor donor = new Donor(
                donorId,
                cleanName,
                age,
                gender,
                contact,
                address,
                normalizedGroup,
                Double.valueOf(weightKg),
                Boolean.valueOf(available));

        listManager.addDonor(donor);
        mapManager.putDonor(donor);
        bloodGroupSetManager.markBloodGroupActive(normalizedGroup);

        return donor;
    }

    /**
     * Seeds initial sample donors across all 8 blood groups so the console app
     * is immediately ready for testing and viva demonstrations.
     */
    public void loadSampleDonors()
    {
        try
        {
            registerDonor("Aarav Sharma", 24, "Male", "O-", 68.5, "9876543210",
                    "aarav.sharma@mail.com", "9876500001", "MG Road", "Mumbai", "Maharashtra", "400001", true);
            registerDonor("Priya Nair", 28, "Female", "A+", 56.0, "9811223344",
                    "priya.nair@mail.com", "9811200002", "Indiranagar", "Bengaluru", "Karnataka", "560038", true);
            registerDonor("Rohan Verma", 32, "Male", "B+", 74.0, "9822334455",
                    "rohan.verma@mail.com", "9822300003", "FC Road", "Pune", "Maharashtra", "411004", true);
            registerDonor("Sneha Kulkarni", 22, "Female", "AB+", 54.5, "9833445566",
                    "sneha.k@mail.com", "9833400004", "Kothrud", "Pune", "Maharashtra", "411038", false);
            registerDonor("Vikram Singh", 35, "Male", "O+", 79.0, "9844556677",
                    "vikram.singh@mail.com", "9844500005", "Connaught Place", "Delhi", "Delhi", "110001", true);
            registerDonor("Ananya Das", 26, "Female", "A-", 58.0, "9855667788",
                    "ananya.das@mail.com", "9855600006", "Salt Lake", "Kolkata", "West Bengal", "700091", true);
            registerDonor("Karthik Reddy", 29, "Male", "B-", 71.0, "9866778899",
                    "karthik.r@mail.com", "9866700007", "Banjara Hills", "Hyderabad", "Telangana", "500034", false);
            registerDonor("Meera Iyer", 31, "Female", "AB-", 60.0, "9877889900",
                    "meera.iyer@mail.com", "9877800008", "Adyar", "Chennai", "Tamil Nadu", "600020", true);
        }
        catch (InvalidBloodGroupException | DuplicateDonorException ex)
        {
            System.out.println("Warning while loading sample donors: " + ex.getMessage());
        }
    }
}
