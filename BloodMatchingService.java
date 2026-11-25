package service;

import collection.DonorListManager;
import exception.BloodRequestNotFoundException;
import exception.InvalidBloodGroupException;
import model.BloodGroupManager;
import model.BloodRequest;
import model.Donor;
import utility.BloodGroupValidator;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: BloodMatchingService
 * SYLLABUS TOPIC: Unit 1A - Method Overloading (Compile-time Polymorphism)
 *                 Unit 1B - 2D Compatibility Table Lookup
 *                 Unit 2  - ArrayList Filtering & Matching
 * ============================================================================
 *
 * Matches blood requests (or blood groups) with registered donors who are
 * medically eligible, currently available, and blood-group compatible.
 */
public class BloodMatchingService
{
    private final DonorListManager donorListManager;
    private final BloodRequestService bloodRequestService;
    private final BloodGroupValidator bloodGroupValidator;

    public BloodMatchingService(DonorListManager donorListManager,
                                BloodRequestService bloodRequestService)
    {
        this.donorListManager = donorListManager;
        this.bloodRequestService = bloodRequestService;
        this.bloodGroupValidator = new BloodGroupValidator();
    }

    /**
     * Method Overloading 1: Finds matching available donors for a given BloodRequest ID.
     */
    public ArrayList<Donor> findMatchingDonorsForRequest(String requestId)
            throws BloodRequestNotFoundException, InvalidBloodGroupException
    {
        BloodRequest request = bloodRequestService.getRequestById(requestId);
        return findMatchingDonors(request.getRequiredBloodGroup(), false);
    }

    /**
     * Method Overloading 2: Finds matching available & eligible donors for a blood group.
     * If exactMatchOnly is true, returns only identical blood group donors;
     * otherwise returns all medically compatible donors (including O- universal donors).
     */
    public ArrayList<Donor> findMatchingDonors(String recipientBloodGroup, boolean exactMatchOnly)
            throws InvalidBloodGroupException
    {
        String normalizedRecipient = bloodGroupValidator.validateAndNormalize(recipientBloodGroup);
        ArrayList<Donor> matches = new ArrayList<Donor>();

        for (Donor donor : donorListManager.getAllDonors())
        {
            donor.refreshEligibility();
            if (!Boolean.TRUE.equals(donor.getEligibility().isEligible()))
            {
                continue;
            }
            if (exactMatchOnly)
            {
                if (donor.getBloodGroup().equalsIgnoreCase(normalizedRecipient))
                {
                    matches.add(donor);
                }
            }
            else
            {
                if (BloodGroupManager.canDonateTo(donor.getBloodGroup(), normalizedRecipient))
                {
                    matches.add(donor);
                }
            }
        }
        return matches;
    }

    /**
     * Method Overloading 3: Finds compatible available donors filtered by preferred city.
     */
    public ArrayList<Donor> findMatchingDonors(String recipientBloodGroup, String preferredCity)
            throws InvalidBloodGroupException
    {
        ArrayList<Donor> compatible = findMatchingDonors(recipientBloodGroup, false);
        if (preferredCity == null || preferredCity.trim().isEmpty())
        {
            return compatible;
        }
        ArrayList<Donor> cityMatches = new ArrayList<Donor>();
        for (Donor donor : compatible)
        {
            if (donor.getAddress().getCity().equalsIgnoreCase(preferredCity.trim()))
            {
                cityMatches.add(donor);
            }
        }
        return cityMatches;
    }
}
