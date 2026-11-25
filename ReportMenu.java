package menu;

import model.DonationRecord;
import service.BloodGroupService;
import service.DonationService;
import service.ReportService;
import utility.ConsoleInput;
import utility.DisplayFormatter;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: ReportMenu
 * SYLLABUS TOPIC: Unit 1A - Abstraction (Using ReportService / BloodGroupService)
 *                 Unit 1B - 2D Matrix, StringBuilder, StringBuffer Output
 *                 Unit 2  - Visualizing All 8 Collections
 * ============================================================================
 *
 * Submenu displaying all reports, statistics, compatibility charts, donation
 * records, StringBuffer audit logs, and live collection inspections.
 */
public class ReportMenu
{
    private final ConsoleInput input;
    private final ReportService reportService;
    private final BloodGroupService bloodGroupService;
    private final DonationService donationService;

    public ReportMenu(ConsoleInput input,
                      ReportService reportService,
                      BloodGroupService bloodGroupService,
                      DonationService donationService)
    {
        this.input = input;
        this.reportService = reportService;
        this.bloodGroupService = bloodGroupService;
        this.donationService = donationService;
    }

    public void showReportMenu()
    {
        boolean back = false;
        while (!back)
        {
            System.out.println(DisplayFormatter.createSubHeader("Reports, Statistics & Donation Logs"));
            System.out.println("  1. System Summary Report");
            System.out.println("  2. Blood Group Donor Counts (Sorted TreeMap)");
            System.out.println("  3. Blood Group Availability Matrix (2D int[][] Array)");
            System.out.println("  4. Blood Group Compatibility Chart (2D String[][] Array)");
            System.out.println("  5. View All Donation Records & StringBuffer Audit Log");
            System.out.println("  6. Unit 2 Collections Inspection Report (All 8 Collections)");
            System.out.println("  7. Polymorphic Person Directory (Donor & Patient as Person)");
            System.out.println("  8. Back to Main Menu");

            int choice = input.readIntInRange("  Enter report choice (1-8): ", 1, 8);
            System.out.println();

            switch (choice)
            {
                case 1:
                    System.out.println(reportService.generateSummaryReport());
                    break;
                case 2:
                    System.out.println(reportService.generateBloodGroupReport());
                    break;
                case 3:
                    System.out.println(reportService.generateAvailabilityMatrixReport());
                    break;
                case 4:
                    System.out.println(bloodGroupService.getFormattedCompatibilityChart());
                    break;
                case 5:
                    displayDonationRecordsAndLog();
                    break;
                case 6:
                    System.out.println(reportService.generateCollectionsInspectionReport());
                    break;
                case 7:
                    System.out.println(reportService.generatePolymorphicPeopleDirectory());
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("  Invalid option.");
            }
        }
    }

    private void displayDonationRecordsAndLog()
    {
        System.out.println(DisplayFormatter.createSubHeader("All Donation Records"));
        ArrayList<DonationRecord> records = donationService.getAllDonationRecords();
        if (records.isEmpty())
        {
            System.out.println("  No donation records found.");
        }
        else
        {
            for (DonationRecord record : records)
            {
                System.out.println("  - " + record.getDisplaySummary());
            }
        }
        System.out.println(DisplayFormatter.createSubHeader("Donation Audit Log (StringBuffer)"));
        System.out.println(donationService.getAuditLogText());
    }
}
