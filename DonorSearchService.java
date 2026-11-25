package service;

import collection.DonorListManager;
import collection.DonorMapManager;
import exception.DonorNotFoundException;
import exception.InvalidBloodGroupException;
import interfaces.Searchable;
import model.Donor;
import utility.BloodGroupValidator;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: DonorSearchService
 * SYLLABUS TOPIC: Unit 1A - Interfaces (implements Searchable),
 *                           Method Overloading (Compile-time Polymorphism)
 *                 Unit 1B - String Searching (contains, equalsIgnoreCase)
 *                 Unit 2  - HashMap O(1) Lookup & ArrayList Traversal
 * ============================================================================
 *
 * Provides multiple ways to locate registered donors:
 *   - By Donor ID (O(1) HashMap lookup)
 *   - By Name keyword (case-insensitive String searching)
 *   - By Blood Group (validated against 8 groups)
 *   - By City
 *   - Overloaded searchDonor(...) methods demonstrating compile-time polymorphism
 */
public class DonorSearchService implements Searchable
{
    private final DonorListManager listManager;
    private final DonorMapManager mapManager;
    private final BloodGroupValidator bloodGroupValidator;

    public DonorSearchService(DonorListManager listManager, DonorMapManager mapManager)
    {
        this.listManager = listManager;
        this.mapManager = mapManager;
        this.bloodGroupValidator = new BloodGroupValidator();
    }

    @Override
    public Donor searchById(String donorId) throws DonorNotFoundException
    {
        Donor donor = mapManager.getDonorById(donorId);
        if (donor == null)
        {
            throw new DonorNotFoundException(donorId, "No donor found with ID: " + donorId);
        }
        return donor;
    }

    @Override
    public ArrayList<Donor> searchByName(String nameKeyword)
    {
        ArrayList<Donor> results = new ArrayList<Donor>();
        if (nameKeyword == null || nameKeyword.trim().isEmpty())
        {
            return results;
        }
        String keyword = nameKeyword.trim().toLowerCase();
        ArrayList<Donor> allDonors = listManager.getAllDonors();
        for (Donor donor : allDonors)
        {
            if (donor.getName().toLowerCase().contains(keyword))
            {
                results.add(donor);
            }
        }
        return results;
    }

    @Override
    public ArrayList<Donor> searchByBloodGroup(String bloodGroup) throws InvalidBloodGroupException
    {
        String normalized = bloodGroupValidator.validateAndNormalize(bloodGroup);
        ArrayList<Donor> results = new ArrayList<Donor>();
        ArrayList<Donor> allDonors = listManager.getAllDonors();
        for (Donor donor : allDonors)
        {
            if (donor.getBloodGroup().equalsIgnoreCase(normalized))
            {
                results.add(donor);
            }
        }
        return results;
    }

    @Override
    public ArrayList<Donor> searchByCity(String city)
    {
        ArrayList<Donor> results = new ArrayList<Donor>();
        if (city == null || city.trim().isEmpty())
        {
            return results;
        }
        String targetCity = city.trim();
        ArrayList<Donor> allDonors = listManager.getAllDonors();
        for (Donor donor : allDonors)
        {
            if (donor.getAddress().getCity().equalsIgnoreCase(targetCity))
            {
                results.add(donor);
            }
        }
        return results;
    }

    // Method Overloading 1: search by Donor ID
    public Donor searchDonor(String donorId) throws DonorNotFoundException
    {
        return searchById(donorId);
    }

    // Method Overloading 2: search by Blood Group AND City combined
    public ArrayList<Donor> searchDonor(String bloodGroup, String city) throws InvalidBloodGroupException
    {
        String normalizedGroup = bloodGroupValidator.validateAndNormalize(bloodGroup);
        ArrayList<Donor> results = new ArrayList<Donor>();
        ArrayList<Donor> allDonors = listManager.getAllDonors();
        for (Donor donor : allDonors)
        {
            boolean groupMatches = donor.getBloodGroup().equalsIgnoreCase(normalizedGroup);
            boolean cityMatches = city == null || city.trim().isEmpty()
                    || donor.getAddress().getCity().equalsIgnoreCase(city.trim());
            if (groupMatches && cityMatches)
            {
                results.add(donor);
            }
        }
        return results;
    }

    public ArrayList<Donor> getAllRegisteredDonors()
    {
        return listManager.getAllDonors();
    }
}
