package model;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: DonorHistory
 * SYLLABUS TOPIC: Unit 1A - Composition (Donor HAS-A DonorHistory)
 *                 Unit 1B - StringBuilder
 *                 Unit 2  - ArrayList Collection
 * ============================================================================
 *
 * Maintains the personal donation history for an individual Donor.
 */
public class DonorHistory
{
    private final ArrayList<DonationRecord> records;
    private String lastDonationDate;

    public DonorHistory()
    {
        this.records = new ArrayList<DonationRecord>();
        this.lastDonationDate = "Never";
    }

    public void addRecord(DonationRecord record)
    {
        if (record != null)
        {
            records.add(record);
            this.lastDonationDate = record.getDonationDate();
        }
    }

    public ArrayList<DonationRecord> getRecords()
    {
        return new ArrayList<DonationRecord>(records);
    }

    public int getTotalDonations()
    {
        return records.size();
    }

    public int getTotalUnitsDonated()
    {
        int totalUnits = 0;
        for (DonationRecord record : records)
        {
            totalUnits += record.getUnitsDonated().intValue();
        }
        return totalUnits;
    }

    public String getLastDonationDate()
    {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate)
    {
        this.lastDonationDate = (lastDonationDate != null && !lastDonationDate.trim().isEmpty())
                ? lastDonationDate.trim()
                : "Never";
    }

    public String getFormattedHistory()
    {
        if (records.isEmpty())
        {
            return "No previous donations recorded.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < records.size(); i++)
        {
            sb.append("  ").append(i + 1).append(". ")
              .append(records.get(i).getDisplaySummary()).append("\n");
        }
        return sb.toString();
    }
}
