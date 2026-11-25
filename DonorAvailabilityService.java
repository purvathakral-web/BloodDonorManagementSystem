package service;

import collection.DonorListManager;
import collection.DonorMapManager;
import exception.DonorNotFoundException;
import model.BloodGroupManager;
import model.Donor;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: DonorAvailabilityService
 * SYLLABUS TOPIC: Unit 1A - Method Overloading Invocation on Donor
 *                 Unit 1B - Multidimensional Arrays (int[][] Availability Matrix)
 *                 Unit 2  - ArrayList Filtering
 * ============================================================================
 *
 * Manages donor availability statuses and builds a 2D integer array summarizing
 * [Total Donors, Available Donors, Unavailable Donors] for all 8 blood groups.
 */
public class DonorAvailabilityService
{
    private final DonorListManager listManager;
    private final DonorMapManager mapManager;

    public DonorAvailabilityService(DonorListManager listManager, DonorMapManager mapManager)
    {
        this.listManager = listManager;
        this.mapManager = mapManager;
    }

    public Donor setAvailability(String donorId, boolean available, String note)
            throws DonorNotFoundException
    {
        Donor donor = mapManager.getDonorById(donorId);
        if (donor == null)
        {
            throw new DonorNotFoundException(donorId, "Donor not found with ID: " + donorId);
        }
        if (note == null || note.trim().isEmpty())
        {
            donor.updateAvailability(available);       // Overloaded method 1
        }
        else
        {
            donor.updateAvailability(available, note); // Overloaded method 2
        }
        return donor;
    }

    public ArrayList<Donor> getAvailableDonors()
    {
        ArrayList<Donor> result = new ArrayList<Donor>();
        for (Donor donor : listManager.getAllDonors())
        {
            if (Boolean.TRUE.equals(donor.isAvailable()))
            {
                result.add(donor);
            }
        }
        return result;
    }

    public ArrayList<Donor> getUnavailableDonors()
    {
        ArrayList<Donor> result = new ArrayList<Donor>();
        for (Donor donor : listManager.getAllDonors())
        {
            if (!Boolean.TRUE.equals(donor.isAvailable()))
            {
                result.add(donor);
            }
        }
        return result;
    }

    /**
     * Builds an 8 x 3 multidimensional integer array:
     *   Row index 0..7 -> BloodGroupManager.SUPPORTED_BLOOD_GROUPS[row]
     *   Col 0 -> Total registered donors
     *   Col 1 -> Available donors
     *   Col 2 -> Unavailable donors
     */
    public int[][] buildBloodGroupAvailabilityMatrix()
    {
        int rows = BloodGroupManager.SUPPORTED_BLOOD_GROUPS.length;
        int[][] matrix = new int[rows][3];

        ArrayList<Donor> donors = listManager.getAllDonors();
        for (Donor donor : donors)
        {
            int idx = BloodGroupManager.getBloodGroupIndex(donor.getBloodGroup());
            if (idx >= 0)
            {
                matrix[idx][0]++; // Total
                if (Boolean.TRUE.equals(donor.isAvailable()))
                {
                    matrix[idx][1]++; // Available
                }
                else
                {
                    matrix[idx][2]++; // Unavailable
                }
            }
        }
        return matrix;
    }
}
