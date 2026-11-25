package menu;

import utility.ConsoleInput;
import utility.DisplayFormatter;

/**
 * ============================================================================
 * CLASS: MainMenu
 * SYLLABUS TOPIC: Unit 1A - Modular Console UI Controller & Object Composition
 *                 Unit 1C - Defensive Menu Loop & finally Resource Cleanup
 * ============================================================================
 *
 * Main 12-option console menu coordinating DonorMenu, BloodRequestMenu,
 * and ReportMenu.
 */
public class MainMenu
{
    private final ConsoleInput input;
    private final DonorMenu donorMenu;
    private final BloodRequestMenu bloodRequestMenu;
    private final ReportMenu reportMenu;

    public MainMenu(ConsoleInput input,
                    DonorMenu donorMenu,
                    BloodRequestMenu bloodRequestMenu,
                    ReportMenu reportMenu)
    {
        this.input = input;
        this.donorMenu = donorMenu;
        this.bloodRequestMenu = bloodRequestMenu;
        this.reportMenu = reportMenu;
    }

    public void start()
    {
        boolean running = true;
        try
        {
            while (running)
            {
                printMenuOptions();
                int choice = input.readIntInRange("Enter your choice (1-12): ", 1, 12);

                switch (choice)
                {
                    case 1:
                        donorMenu.handleRegisterDonor();
                        break;
                    case 2:
                        donorMenu.handleViewAllDonors();
                        break;
                    case 3:
                        donorMenu.handleSearchDonor();
                        break;
                    case 4:
                        donorMenu.handleUpdateDonor();
                        break;
                    case 5:
                        donorMenu.handleDeleteDonor();
                        break;
                    case 6:
                        donorMenu.handleSearchByBloodGroup();
                        break;
                    case 7:
                        donorMenu.handleManageAvailability();
                        break;
                    case 8:
                        bloodRequestMenu.handleRegisterBloodRequest();
                        break;
                    case 9:
                        bloodRequestMenu.handleViewBloodRequests();
                        break;
                    case 10:
                        bloodRequestMenu.handleFindMatchingDonors();
                        break;
                    case 11:
                        reportMenu.showReportMenu();
                        break;
                    case 12:
                        running = false;
                        System.out.println("\nThank you for using the Blood Donor Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select between 1 and 12.");
                }
            }
        }
        finally
        {
            input.close();
        }
    }

    private void printMenuOptions()
    {
        System.out.println(DisplayFormatter.createBanner("BLOOD DONOR MANAGEMENT SYSTEM"));
        System.out.println(" 1. Register Donor");
        System.out.println(" 2. View All Donors");
        System.out.println(" 3. Search Donor");
        System.out.println(" 4. Update Donor");
        System.out.println(" 5. Delete Donor");
        System.out.println(" 6. Search Donor By Blood Group");
        System.out.println(" 7. Manage Donor Availability");
        System.out.println(" 8. Register Blood Request");
        System.out.println(" 9. View Blood Requests");
        System.out.println("10. Find Matching Donors / Record Donation");
        System.out.println("11. View Reports & Statistics");
        System.out.println("12. Exit");
        System.out.println("------------------------------------------------------------");
    }
}
