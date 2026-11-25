package collection;

import model.BloodGroupManager;
import model.Donor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * ============================================================================
 * CLASS: DonorMapManager
 * SYLLABUS TOPIC: Unit 2 - Collections Framework
 *                          HashMap, LinkedHashMap, TreeMap
 * ============================================================================
 *
 * Provides fast key-value access and analytical summaries using three Map types:
 *   1. HashMap<String, Donor>        - O(1) instant lookup of a Donor by Donor ID.
 *   2. LinkedHashMap<String, Donor>  - Preserves donors in exact registration (insertion) order.
 *   3. TreeMap<String, Integer>      - Maintains blood-group-wise donor counts sorted alphabetically.
 */
public class DonorMapManager
{
    private final HashMap<String, Donor> donorHashMap;
    private final LinkedHashMap<String, Donor> donorInsertionMap;
    private final TreeMap<String, Integer> bloodGroupCountMap;

    public DonorMapManager()
    {
        this.donorHashMap = new HashMap<String, Donor>();
        this.donorInsertionMap = new LinkedHashMap<String, Donor>();
        this.bloodGroupCountMap = new TreeMap<String, Integer>();

        // Initialize all 8 blood groups with count 0 in sorted TreeMap order
        for (int i = 0; i < BloodGroupManager.SUPPORTED_BLOOD_GROUPS.length; i++)
        {
            bloodGroupCountMap.put(BloodGroupManager.SUPPORTED_BLOOD_GROUPS[i], Integer.valueOf(0));
        }
    }

    public void putDonor(Donor donor)
    {
        if (donor == null)
        {
            return;
        }
        String key = donor.getId().trim().toUpperCase();
        donorHashMap.put(key, donor);
        donorInsertionMap.put(key, donor);

        String group = donor.getBloodGroup();
        Integer currentCount = bloodGroupCountMap.get(group);
        int updated = (currentCount != null ? currentCount.intValue() : 0) + 1;
        bloodGroupCountMap.put(group, Integer.valueOf(updated));
    }

    public Donor getDonorById(String donorId)
    {
        if (donorId == null)
        {
            return null;
        }
        return donorHashMap.get(donorId.trim().toUpperCase());
    }

    public boolean exists(String donorId)
    {
        return donorId != null && donorHashMap.containsKey(donorId.trim().toUpperCase());
    }

    public Donor removeDonorById(String donorId)
    {
        if (donorId == null)
        {
            return null;
        }
        String key = donorId.trim().toUpperCase();
        Donor removed = donorHashMap.remove(key);
        donorInsertionMap.remove(key);

        if (removed != null)
        {
            String group = removed.getBloodGroup();
            Integer currentCount = bloodGroupCountMap.get(group);
            int updated = (currentCount != null && currentCount.intValue() > 0)
                    ? currentCount.intValue() - 1
                    : 0;
            bloodGroupCountMap.put(group, Integer.valueOf(updated));
        }
        return removed;
    }

    public void updateBloodGroupCount(String oldGroup, String newGroup)
    {
        if (oldGroup != null && !oldGroup.equalsIgnoreCase(newGroup))
        {
            Integer oldCount = bloodGroupCountMap.get(oldGroup);
            if (oldCount != null && oldCount.intValue() > 0)
            {
                bloodGroupCountMap.put(oldGroup, Integer.valueOf(oldCount.intValue() - 1));
            }
            Integer newCount = bloodGroupCountMap.get(newGroup);
            int updated = (newCount != null ? newCount.intValue() : 0) + 1;
            bloodGroupCountMap.put(newGroup, Integer.valueOf(updated));
        }
    }

    public HashMap<String, Donor> getDonorHashMap()
    {
        return new HashMap<String, Donor>(donorHashMap);
    }

    public LinkedHashMap<String, Donor> getDonorInsertionMap()
    {
        return new LinkedHashMap<String, Donor>(donorInsertionMap);
    }

    public TreeMap<String, Integer> getBloodGroupCountMap()
    {
        return new TreeMap<String, Integer>(bloodGroupCountMap);
    }
}
