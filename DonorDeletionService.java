package service;

import collection.BloodGroupSetManager;
import collection.DonorListManager;
import collection.DonorMapManager;
import exception.DonorNotFoundException;
import model.Donor;

/**
 * ============================================================================
 * CLASS: DonorDeletionService
 * SYLLABUS TOPIC: Unit 1C - Checked Exception (throws DonorNotFoundException)
 *                 Unit 2  - Coordinated Removal Across ArrayList, HashSet,
 *                           TreeSet, HashMap, LinkedHashMap, TreeMap, LinkedHashSet
 * ============================================================================
 *
 * Deletes a donor cleanly from all collection managers so there are no stale entries.
 */
public class DonorDeletionService
{
    private final DonorListManager listManager;
    private final DonorMapManager mapManager;
    private final BloodGroupSetManager bloodGroupSetManager;

    public DonorDeletionService(DonorListManager listManager,
                                DonorMapManager mapManager,
                                BloodGroupSetManager bloodGroupSetManager)
    {
        this.listManager = listManager;
        this.mapManager = mapManager;
        this.bloodGroupSetManager = bloodGroupSetManager;
    }

    public Donor deleteDonor(String donorId) throws DonorNotFoundException
    {
        Donor removed = mapManager.removeDonorById(donorId);
        if (removed == null)
        {
            throw new DonorNotFoundException(donorId, "Cannot delete: Donor ID " + donorId + " does not exist.");
        }
        listManager.removeDonor(removed);

        Integer remainingInGroup = mapManager.getBloodGroupCountMap().get(removed.getBloodGroup());
        int remaining = (remainingInGroup != null) ? remainingInGroup.intValue() : 0;
        bloodGroupSetManager.refreshActiveBloodGroup(removed.getBloodGroup(), remaining);

        return removed;
    }
}
