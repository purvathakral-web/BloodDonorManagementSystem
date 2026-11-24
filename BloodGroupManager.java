package model;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: BloodGroupManager
 * SYLLABUS TOPIC: Unit 1B - One-Dimensional Arrays (String[]) &
 *                           Multidimensional Arrays (String[][]),
 *                           String Comparison (equalsIgnoreCase)
 * ============================================================================
 *
 * Manages all 8 standard blood groups and defines the medical blood donation
 * compatibility matrix using 1D and 2D String arrays.
 */
public class BloodGroupManager
{
    // 1D Array storing the 8 supported blood groups
    public static final String[] SUPPORTED_BLOOD_GROUPS = {
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
    };

    // 2D Multidimensional Array: each row maps a RECIPIENT blood group (index 0)
    // to all compatible DONOR blood groups (remaining columns).
    private static final String[][] COMPATIBILITY_TABLE = {
            {"A+",  "A+", "A-", "O+", "O-"},
            {"A-",  "A-", "O-"},
            {"B+",  "B+", "B-", "O+", "O-"},
            {"B-",  "B-", "O-"},
            {"AB+", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}, // Universal Recipient
            {"AB-", "A-", "B-", "AB-", "O-"},
            {"O+",  "O+", "O-"},
            {"O-",  "O-"}                                               // Only receives from O-
    };

    /**
     * Checks if a blood group string is valid using a 1D array scan.
     */
    public static boolean isValidBloodGroup(String bloodGroup)
    {
        if (bloodGroup == null)
        {
            return false;
        }
        String trimmed = bloodGroup.trim();
        for (int i = 0; i < SUPPORTED_BLOOD_GROUPS.length; i++)
        {
            if (SUPPORTED_BLOOD_GROUPS[i].equalsIgnoreCase(trimmed))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Normalizes blood group text to standard uppercase format (e.g., "a+" -> "A+").
     */
    public static String normalizeBloodGroup(String bloodGroup)
    {
        if (bloodGroup == null)
        {
            return "";
        }
        String trimmed = bloodGroup.trim();
        for (int i = 0; i < SUPPORTED_BLOOD_GROUPS.length; i++)
        {
            if (SUPPORTED_BLOOD_GROUPS[i].equalsIgnoreCase(trimmed))
            {
                return SUPPORTED_BLOOD_GROUPS[i];
            }
        }
        return trimmed.toUpperCase();
    }

    /**
     * Returns the index (0..7) of a blood group in SUPPORTED_BLOOD_GROUPS, or -1 if invalid.
     */
    public static int getBloodGroupIndex(String bloodGroup)
    {
        if (bloodGroup == null)
        {
            return -1;
        }
        String trimmed = bloodGroup.trim();
        for (int i = 0; i < SUPPORTED_BLOOD_GROUPS.length; i++)
        {
            if (SUPPORTED_BLOOD_GROUPS[i].equalsIgnoreCase(trimmed))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks whether a donor with donorGroup can safely donate to recipientGroup
     * by scanning the 2D COMPATIBILITY_TABLE.
     */
    public static boolean canDonateTo(String donorGroup, String recipientGroup)
    {
        if (donorGroup == null || recipientGroup == null)
        {
            return false;
        }
        String dGroup = donorGroup.trim();
        String rGroup = recipientGroup.trim();

        for (int row = 0; row < COMPATIBILITY_TABLE.length; row++)
        {
            if (COMPATIBILITY_TABLE[row][0].equalsIgnoreCase(rGroup))
            {
                for (int col = 1; col < COMPATIBILITY_TABLE[row].length; col++)
                {
                    if (COMPATIBILITY_TABLE[row][col].equalsIgnoreCase(dGroup))
                    {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Returns all compatible donor blood groups for a given recipient blood group.
     */
    public static ArrayList<String> getCompatibleDonorGroups(String recipientGroup)
    {
        ArrayList<String> compatible = new ArrayList<String>();
        if (recipientGroup == null)
        {
            return compatible;
        }
        for (int row = 0; row < COMPATIBILITY_TABLE.length; row++)
        {
            if (COMPATIBILITY_TABLE[row][0].equalsIgnoreCase(recipientGroup.trim()))
            {
                for (int col = 1; col < COMPATIBILITY_TABLE[row].length; col++)
                {
                    compatible.add(COMPATIBILITY_TABLE[row][col]);
                }
                break;
            }
        }
        return compatible;
    }

    public static String[][] getCompatibilityTable()
    {
        return COMPATIBILITY_TABLE;
    }
}
