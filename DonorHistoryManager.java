package collection;

import model.DonationRecord;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ============================================================================
 * CLASS: DonorHistoryManager
 * SYLLABUS TOPIC: Unit 2 - Collections Framework (ArrayList & HashMap)
 * ============================================================================
 *
 * Stores all donation records across the system in an ArrayList and indexes
 * each donor's donation list in a HashMap<String, ArrayList<DonationRecord>>.
 */
public class DonorHistoryManager
{
    private final ArrayList<DonationRecord> allDonationRecords;
    private final HashMap<String, ArrayList<DonationRecord>> donorRecordsMap;

    public DonorHistoryManager()
    {
        this.allDonationRecords = new ArrayList<DonationRecord>();
        this.donorRecordsMap = new HashMap<String, ArrayList<DonationRecord>>();
    }

    public void addDonationRecord(DonationRecord record)
    {
        if (record == null)
        {
            return;
        }
        allDonationRecords.add(record);

        String donorKey = record.getDonorId().trim().toUpperCase();
        ArrayList<DonationRecord> donorList = donorRecordsMap.get(donorKey);
        if (donorList == null)
        {
            donorList = new ArrayList<DonationRecord>();
            donorRecordsMap.put(donorKey, donorList);
        }
        donorList.add(record);
    }

    public ArrayList<DonationRecord> getAllDonationRecords()
    {
        return new ArrayList<DonationRecord>(allDonationRecords);
    }

    public ArrayList<DonationRecord> getRecordsForDonor(String donorId)
    {
        if (donorId == null)
        {
            return new ArrayList<DonationRecord>();
        }
        ArrayList<DonationRecord> list = donorRecordsMap.get(donorId.trim().toUpperCase());
        return (list != null) ? new ArrayList<DonationRecord>(list) : new ArrayList<DonationRecord>();
    }

    public int getTotalDonationsCount()
    {
        return allDonationRecords.size();
    }

    public int getTotalUnitsCollected()
    {
        int totalUnits = 0;
        for (DonationRecord record : allDonationRecords)
        {
            totalUnits += record.getUnitsDonated().intValue();
        }
        return totalUnits;
    }
}
