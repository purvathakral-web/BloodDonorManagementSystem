package service;

import collection.BloodGroupSetManager;
import collection.DonorMapManager;
import exception.InvalidBloodGroupException;
import model.BloodGroupManager;
import utility.BloodGroupValidator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * ============================================================================
 * CLASS: BloodGroupService
 * SYLLABUS TOPIC: Unit 1B - 1D & 2D Arrays, StringBuilder
 *                 Unit 2  - LinkedHashSet & TreeMap
 * ============================================================================
 *
 * Manages blood group metadata, donor count summaries per blood group,
 * and compatibility lookup between donor and recipient blood groups.
 */
public class BloodGroupService
{
    private final BloodGroupSetManager setManager;
    private final DonorMapManager mapManager;
    private final BloodGroupValidator validator;

    public BloodGroupService(BloodGroupSetManager setManager, DonorMapManager mapManager)
    {
        this.setManager = setManager;
        this.mapManager = mapManager;
        this.validator = new BloodGroupValidator();
    }

    public LinkedHashSet<String> getAllSupportedBloodGroups()
    {
        return setManager.getSupportedBloodGroups();
    }

    public LinkedHashSet<String> getActiveBloodGroups()
    {
        return setManager.getActiveBloodGroups();
    }

    public TreeMap<String, Integer> getSortedBloodGroupCounts()
    {
        return mapManager.getBloodGroupCountMap();
    }

    public ArrayList<String> getCompatibleDonorGroups(String recipientGroup)
            throws InvalidBloodGroupException
    {
        String normalized = validator.validateAndNormalize(recipientGroup);
        return BloodGroupManager.getCompatibleDonorGroups(normalized);
    }

    public String getFormattedCompatibilityChart()
    {
        String[][] table = BloodGroupManager.getCompatibilityTable();
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------------\n");
        sb.append(String.format("%-14s | %-44s\n", "Recipient", "Compatible Donor Blood Groups"));
        sb.append("-------------------------------------------------------------\n");
        for (int row = 0; row < table.length; row++)
        {
            StringBuilder donors = new StringBuilder();
            for (int col = 1; col < table[row].length; col++)
            {
                if (col > 1)
                {
                    donors.append(", ");
                }
                donors.append(table[row][col]);
            }
            sb.append(String.format("%-14s | %-44s\n", table[row][0], donors.toString()));
        }
        sb.append("-------------------------------------------------------------");
        return sb.toString();
    }
}
