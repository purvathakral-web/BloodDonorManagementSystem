package utility;

import model.Donor;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: DisplayFormatter
 * SYLLABUS TOPIC: Unit 1B - StringBuilder, StringBuffer, String Formatting,
 *                           String Manipulation
 * ============================================================================
 *
 * Central utility for formatting banners, tables, and audit entries cleanly.
 * Demonstrates both StringBuilder (fast mutable text building) and
 * StringBuffer (synchronized mutable buffer for log/banner assembly).
 */
public class DisplayFormatter
{
    public static String createBanner(String title)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n============================================================\n");
        buffer.append("  ").append(title.toUpperCase()).append("\n");
        buffer.append("============================================================");
        return buffer.toString();
    }

    public static String createSubHeader(String title)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- ").append(title).append(" ---");
        return sb.toString();
    }

    public static String createLine(int length)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append('-');
        }
        return sb.toString();
    }

    public static String formatDonorTable(ArrayList<Donor> donors)
    {
        if (donors == null || donors.isEmpty())
        {
            return "  No donors found.";
        }
        StringBuilder sb = new StringBuilder();
        String divider = createLine(95);
        sb.append(divider).append("\n");
        sb.append(String.format("%-9s | %-18s | %-3s | %-5s | %-12s | %-11s | %-10s | %-9s\n",
                "Donor ID", "Name", "Age", "Group", "City", "Phone", "Status", "Donations"));
        sb.append(divider).append("\n");
        for (Donor donor : donors)
        {
            sb.append(String.format("%-9s | %-18s | %-3d | %-5s | %-12s | %-11s | %-10s | %-9d\n",
                    donor.getId(),
                    shorten(donor.getName(), 18),
                    donor.getAge(),
                    donor.getBloodGroup(),
                    shorten(donor.getAddress().getCity(), 12),
                    donor.getContact().getPhoneNumber(),
                    Boolean.TRUE.equals(donor.isAvailable()) ? "Available" : "Unavailable",
                    donor.getDonationCount().intValue()));
        }
        sb.append(divider);
        return sb.toString();
    }

    private static String shorten(String value, int maxLength)
    {
        if (value == null)
        {
            return "";
        }
        if (value.length() <= maxLength)
        {
            return value;
        }
        return value.substring(0, maxLength - 2) + "..";
    }
}
