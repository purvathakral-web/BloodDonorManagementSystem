package collection;

import model.BloodGroupManager;

import java.util.LinkedHashSet;

/**
 * ============================================================================
 * CLASS: BloodGroupSetManager
 * SYLLABUS TOPIC: Unit 2 - Collections Framework (LinkedHashSet)
 * ============================================================================
 *
 * Uses LinkedHashSet<String> to maintain:
 *   1. All 8 unique supported blood groups in predictable insertion order.
 *   2. Active blood groups (groups that currently have at least one donor registered).
 */
public class BloodGroupSetManager
{
    private final LinkedHashSet<String> supportedBloodGroups;
    private final LinkedHashSet<String> activeBloodGroups;

    public BloodGroupSetManager()
    {
        this.supportedBloodGroups = new LinkedHashSet<String>();
        this.activeBloodGroups = new LinkedHashSet<String>();

        for (int i = 0; i < BloodGroupManager.SUPPORTED_BLOOD_GROUPS.length; i++)
        {
            supportedBloodGroups.add(BloodGroupManager.SUPPORTED_BLOOD_GROUPS[i]);
        }
    }

    public boolean isSupported(String bloodGroup)
    {
        if (bloodGroup == null)
        {
            return false;
        }
        return supportedBloodGroups.contains(bloodGroup.trim().toUpperCase());
    }

    public void markBloodGroupActive(String bloodGroup)
    {
        if (bloodGroup != null && isSupported(bloodGroup))
        {
            activeBloodGroups.add(bloodGroup.trim().toUpperCase());
        }
    }

    public void refreshActiveBloodGroup(String bloodGroup, int remainingCount)
    {
        if (bloodGroup == null)
        {
            return;
        }
        String normalized = bloodGroup.trim().toUpperCase();
        if (remainingCount <= 0)
        {
            activeBloodGroups.remove(normalized);
        }
        else
        {
            activeBloodGroups.add(normalized);
        }
    }

    public LinkedHashSet<String> getSupportedBloodGroups()
    {
        return new LinkedHashSet<String>(supportedBloodGroups);
    }

    public LinkedHashSet<String> getActiveBloodGroups()
    {
        return new LinkedHashSet<String>(activeBloodGroups);
    }
}
