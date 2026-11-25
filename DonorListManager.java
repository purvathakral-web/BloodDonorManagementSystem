package collection;

import model.Donor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * ============================================================================
 * CLASS: DonorListManager
 * SYLLABUS TOPIC: Unit 2 - Collections Framework
 *                          ArrayList, HashSet, TreeSet
 * ============================================================================
 *
 * Manages registered donors using three complementary collections:
 *   1. ArrayList<Donor>  - Sequential indexed storage of all registered donors.
 *   2. HashSet<String>   - Fast O(1) uniqueness enforcement for Donor IDs & phone numbers.
 *   3. TreeSet<String>   - Maintains all Donor IDs automatically in sorted ascending order.
 */
public class DonorListManager
{
    private final ArrayList<Donor> donorList;
    private final HashSet<String> uniqueDonorIds;
    private final HashSet<String> uniquePhoneNumbers;
    private final TreeSet<String> sortedDonorIds;

    public DonorListManager()
    {
        this.donorList = new ArrayList<Donor>();
        this.uniqueDonorIds = new HashSet<String>();
        this.uniquePhoneNumbers = new HashSet<String>();
        this.sortedDonorIds = new TreeSet<String>();
    }

    public boolean containsDonorId(String donorId)
    {
        return donorId != null && uniqueDonorIds.contains(donorId.trim().toUpperCase());
    }

    public boolean containsPhoneNumber(String phoneNumber)
    {
        return phoneNumber != null && uniquePhoneNumbers.contains(phoneNumber.trim());
    }

    public void addDonor(Donor donor)
    {
        if (donor == null)
        {
            return;
        }
        String normalizedId = donor.getId().trim().toUpperCase();
        donorList.add(donor);
        uniqueDonorIds.add(normalizedId);
        sortedDonorIds.add(normalizedId);
        if (donor.getContact() != null && !donor.getContact().getPhoneNumber().isEmpty())
        {
            uniquePhoneNumbers.add(donor.getContact().getPhoneNumber());
        }
    }

    public boolean removeDonor(Donor donor)
    {
        if (donor == null)
        {
            return false;
        }
        String normalizedId = donor.getId().trim().toUpperCase();
        boolean removed = donorList.remove(donor);
        if (removed)
        {
            uniqueDonorIds.remove(normalizedId);
            sortedDonorIds.remove(normalizedId);
            if (donor.getContact() != null)
            {
                uniquePhoneNumbers.remove(donor.getContact().getPhoneNumber());
            }
        }
        return removed;
    }

    public void updatePhoneNumber(String oldPhone, String newPhone)
    {
        if (oldPhone != null)
        {
            uniquePhoneNumbers.remove(oldPhone.trim());
        }
        if (newPhone != null && !newPhone.trim().isEmpty())
        {
            uniquePhoneNumbers.add(newPhone.trim());
        }
    }

    public ArrayList<Donor> getAllDonors()
    {
        return new ArrayList<Donor>(donorList);
    }

    public HashSet<String> getUniqueDonorIds()
    {
        return new HashSet<String>(uniqueDonorIds);
    }

    public TreeSet<String> getSortedDonorIds()
    {
        return new TreeSet<String>(sortedDonorIds);
    }

    public int getDonorCount()
    {
        return donorList.size();
    }
}
