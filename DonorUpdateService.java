package service;

import collection.DonorListManager;
import collection.DonorMapManager;
import exception.DonorNotFoundException;
import exception.DuplicateDonorException;
import model.Donor;
import utility.AgeValidator;
import utility.InputValidator;
import utility.PhoneValidator;

/**
 * ============================================================================
 * CLASS: DonorUpdateService
 * SYLLABUS TOPIC: Unit 1A - Encapsulation (Using Setters for Controlled Updates)
 *                 Unit 1C - Checked & Unchecked Exception Handling
 *                 Unit 2  - Keeping HashSet and HashMap Consistent on Update
 * ============================================================================
 *
 * Updates an existing donor's contact info, city/address, age, or weight.
 */
public class DonorUpdateService
{
    private final DonorListManager listManager;
    private final DonorMapManager mapManager;
    private final InputValidator inputValidator;
    private final PhoneValidator phoneValidator;
    private final AgeValidator ageValidator;

    public DonorUpdateService(DonorListManager listManager, DonorMapManager mapManager)
    {
        this.listManager = listManager;
        this.mapManager = mapManager;
        this.inputValidator = new InputValidator();
        this.phoneValidator = new PhoneValidator();
        this.ageValidator = new AgeValidator();
    }

    public Donor updatePhone(String donorId, String newPhone)
            throws DonorNotFoundException, DuplicateDonorException
    {
        Donor donor = getDonorOrThrow(donorId);
        phoneValidator.validateOrThrow(newPhone);

        String oldPhone = donor.getContact().getPhoneNumber();
        String cleanedNewPhone = newPhone.trim();
        if (!oldPhone.equals(cleanedNewPhone) && listManager.containsPhoneNumber(cleanedNewPhone))
        {
            throw new DuplicateDonorException("Phone number " + cleanedNewPhone + " is already used by another donor.");
        }

        donor.getContact().setPhoneNumber(cleanedNewPhone);
        listManager.updatePhoneNumber(oldPhone, cleanedNewPhone);
        return donor;
    }

    public Donor updateEmail(String donorId, String newEmail) throws DonorNotFoundException
    {
        Donor donor = getDonorOrThrow(donorId);
        inputValidator.requireNonEmpty(newEmail, "Email");
        donor.getContact().setEmail(newEmail);
        return donor;
    }

    public Donor updateCityAndAddress(String donorId, String street, String city, String state, String pincode)
            throws DonorNotFoundException
    {
        Donor donor = getDonorOrThrow(donorId);
        inputValidator.requireNonEmpty(city, "City");
        donor.getAddress().setStreet(street);
        donor.getAddress().setCity(city);
        donor.getAddress().setState(state);
        donor.getAddress().setPincode(pincode);
        return donor;
    }

    public Donor updateAgeAndWeight(String donorId, int newAge, double newWeightKg)
            throws DonorNotFoundException
    {
        Donor donor = getDonorOrThrow(donorId);
        ageValidator.validateAgeOrThrow(newAge);
        ageValidator.validateWeightOrThrow(Double.valueOf(newWeightKg));
        donor.setAge(newAge);
        donor.setWeightKg(Double.valueOf(newWeightKg));
        return donor;
    }

    private Donor getDonorOrThrow(String donorId) throws DonorNotFoundException
    {
        Donor donor = mapManager.getDonorById(donorId);
        if (donor == null)
        {
            throw new DonorNotFoundException(donorId, "Cannot update: Donor ID " + donorId + " not found.");
        }
        return donor;
    }
}
