package menu;

import exception.DonorNotFoundException;
import exception.DuplicateDonorException;
import exception.InvalidBloodGroupException;
import exception.InvalidDonorDetailsException;
import model.Donor;
import model.DonorProfile;
import service.DonorAvailabilityService;
import service.DonorDeletionService;
import service.DonorRegistrationService;
import service.DonorSearchService;
import service.DonorUpdateService;
import utility.ConsoleInput;
import utility.DisplayFormatter;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: DonorMenu
 * SYLLABUS TOPIC: Unit 1C - try, catch, multiple catch, multi-catch, finally
 *                 Unit 1A - Delegation to Service Layer
 * ============================================================================
 *
 * Handles all donor-focused console menus and user interactions:
 * Register, View All, Search, Update, Delete, Search By Blood Group,
 * Manage Availability, and View Detailed Donor Profile.
 */
public class DonorMenu
{
    private final ConsoleInput input;
    private final DonorRegistrationService registrationService;
    private final DonorSearchService searchService;
    private final DonorUpdateService updateService;
    private final DonorDeletionService deletionService;
    private final DonorAvailabilityService availabilityService;

    public DonorMenu(ConsoleInput input,
                     DonorRegistrationService registrationService,
                     DonorSearchService searchService,
                     DonorUpdateService updateService,
                     DonorDeletionService deletionService,
                     DonorAvailabilityService availabilityService)
    {
        this.input = input;
        this.registrationService = registrationService;
        this.searchService = searchService;
        this.updateService = updateService;
        this.deletionService = deletionService;
        this.availabilityService = availabilityService;
    }

    public void handleRegisterDonor()
    {
        System.out.println(DisplayFormatter.createSubHeader("Register New Blood Donor"));
        try
        {
            String name = input.readNonEmptyLine("  Full Name                      : ");
            int age = input.readInt("  Age (18-65)                    : ");
            String gender = input.readNonEmptyLine("  Gender (Male/Female/Other)     : ");
            String bloodGroup = input.readNonEmptyLine("  Blood Group (A+,A-,B+,B-,AB+,AB-,O+,O-): ");
            double weight = input.readDouble("  Weight in kg (>= 50.0)         : ");
            String phone = input.readNonEmptyLine("  10-Digit Mobile Number         : ");
            String email = input.readNonEmptyLine("  Email Address                  : ");
            String emergencyPhone = input.readLine("  Emergency Contact (optional)   : ");
            String street = input.readLine("  Street / Area                  : ");
            String city = input.readNonEmptyLine("  City                           : ");
            String state = input.readNonEmptyLine("  State                          : ");
            String pincode = input.readLine("  Pincode                        : ");
            boolean available = input.readYesNo("  Available to donate right now?");

            Donor created = registrationService.registerDonor(
                    name, age, gender, bloodGroup, weight,
                    phone, email, emergencyPhone, street, city, state, pincode, available);

            System.out.println("\n  [Success] Donor registered successfully!");
            System.out.println(new DonorProfile(created).buildProfileCard());
        }
        catch (InvalidBloodGroupException ex)
        {
            System.out.println("  [Blood Group Error] " + ex.getMessage());
        }
        catch (DuplicateDonorException ex)
        {
            System.out.println("  [Duplicate Error] " + ex.getMessage());
        }
        catch (InvalidDonorDetailsException ex)
        {
            System.out.println("  [Validation Error] " + ex.getMessage());
        }
        finally
        {
            System.out.println("  -- Returning to main menu --");
        }
    }

    public void handleViewAllDonors()
    {
        System.out.println(DisplayFormatter.createSubHeader("All Registered Donors"));
        ArrayList<Donor> donors = searchService.getAllRegisteredDonors();
        System.out.println(DisplayFormatter.formatDonorTable(donors));
        System.out.println("  Total Registered Donors: " + donors.size());
    }

    public void handleSearchDonor()
    {
        System.out.println(DisplayFormatter.createSubHeader("Search Donor"));
        System.out.println("  1. Search by Donor ID (HashMap O(1) Lookup)");
        System.out.println("  2. Search by Name Keyword (String Searching)");
        System.out.println("  3. Search by City");
        System.out.println("  4. Search by Blood Group & City (Method Overloading)");
        System.out.println("  5. View Full Donor Profile Card");
        int choice = input.readIntInRange("  Choose search option (1-5): ", 1, 5);

        try
        {
            if (choice == 1)
            {
                String donorId = input.readNonEmptyLine("  Enter Donor ID (e.g., DNR-1001): ");
                Donor donor = searchService.searchDonor(donorId); // Overloaded method 1
                donor.displayDetails();
            }
            else if (choice == 2)
            {
                String keyword = input.readNonEmptyLine("  Enter Name Keyword: ");
                ArrayList<Donor> results = searchService.searchByName(keyword);
                System.out.println(DisplayFormatter.formatDonorTable(results));
            }
            else if (choice == 3)
            {
                String city = input.readNonEmptyLine("  Enter City Name: ");
                ArrayList<Donor> results = searchService.searchByCity(city);
                System.out.println(DisplayFormatter.formatDonorTable(results));
            }
            else if (choice == 4)
            {
                String group = input.readNonEmptyLine("  Enter Blood Group: ");
                String city = input.readLine("  Enter City (leave blank for all cities): ");
                ArrayList<Donor> results = searchService.searchDonor(group, city); // Overloaded method 2
                System.out.println(DisplayFormatter.formatDonorTable(results));
            }
            else
            {
                String donorId = input.readNonEmptyLine("  Enter Donor ID for Profile Card: ");
                Donor donor = searchService.searchById(donorId);
                System.out.println(new DonorProfile(donor).buildProfileCard());
            }
        }
        catch (DonorNotFoundException | InvalidBloodGroupException ex)
        {
            // Multi-catch block (Unit 1C)
            System.out.println("  [Search Error] " + ex.getMessage());
        }
    }

    public void handleSearchByBloodGroup()
    {
        System.out.println(DisplayFormatter.createSubHeader("Search Donor By Blood Group"));
        String bloodGroup = input.readNonEmptyLine("  Enter Blood Group (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
        try
        {
            ArrayList<Donor> results = searchService.searchByBloodGroup(bloodGroup);
            System.out.println(DisplayFormatter.formatDonorTable(results));
            System.out.println("  Matching Donors Found: " + results.size());
        }
        catch (InvalidBloodGroupException ex)
        {
            System.out.println("  [Error] " + ex.getMessage());
        }
    }

    public void handleUpdateDonor()
    {
        System.out.println(DisplayFormatter.createSubHeader("Update Donor Information"));
        String donorId = input.readNonEmptyLine("  Enter Donor ID to Update (e.g., DNR-1001): ");
        System.out.println("  1. Update Phone Number");
        System.out.println("  2. Update Email");
        System.out.println("  3. Update Address / City");
        System.out.println("  4. Update Age & Weight");
        int choice = input.readIntInRange("  Choose field to update (1-4): ", 1, 4);

        try
        {
            Donor updated;
            if (choice == 1)
            {
                String newPhone = input.readNonEmptyLine("  Enter New 10-Digit Phone: ");
                updated = updateService.updatePhone(donorId, newPhone);
            }
            else if (choice == 2)
            {
                String newEmail = input.readNonEmptyLine("  Enter New Email: ");
                updated = updateService.updateEmail(donorId, newEmail);
            }
            else if (choice == 3)
            {
                String street = input.readNonEmptyLine("  New Street : ");
                String city = input.readNonEmptyLine("  New City   : ");
                String state = input.readNonEmptyLine("  New State  : ");
                String pincode = input.readNonEmptyLine("  New Pincode: ");
                updated = updateService.updateCityAndAddress(donorId, street, city, state, pincode);
            }
            else
            {
                int newAge = input.readInt("  New Age (18-65)         : ");
                double newWeight = input.readDouble("  New Weight in kg (>=50) : ");
                updated = updateService.updateAgeAndWeight(donorId, newAge, newWeight);
            }
            System.out.println("  [Success] Donor updated successfully:");
            updated.displayDetails();
        }
        catch (DonorNotFoundException | DuplicateDonorException ex)
        {
            System.out.println("  [Update Error] " + ex.getMessage());
        }
        catch (InvalidDonorDetailsException ex)
        {
            System.out.println("  [Validation Error] " + ex.getMessage());
        }
    }

    public void handleDeleteDonor()
    {
        System.out.println(DisplayFormatter.createSubHeader("Delete Donor"));
        String donorId = input.readNonEmptyLine("  Enter Donor ID to Delete: ");
        boolean confirm = input.readYesNo("  Are you sure you want to delete donor " + donorId + "?");
        if (!confirm)
        {
            System.out.println("  Deletion cancelled.");
            return;
        }
        try
        {
            Donor removed = deletionService.deleteDonor(donorId);
            System.out.println("  [Success] Deleted donor: " + removed.getName() + " (" + removed.getId() + ")");
        }
        catch (DonorNotFoundException ex)
        {
            System.out.println("  [Error] " + ex.getMessage());
        }
    }

    public void handleManageAvailability()
    {
        System.out.println(DisplayFormatter.createSubHeader("Manage Donor Availability"));
        System.out.println("  1. Update a Donor's Availability Status");
        System.out.println("  2. View All Available Donors");
        System.out.println("  3. View All Unavailable Donors");
        int choice = input.readIntInRange("  Choose option (1-3): ", 1, 3);

        if (choice == 1)
        {
            String donorId = input.readNonEmptyLine("  Enter Donor ID: ");
            boolean status = input.readYesNo("  Mark donor as AVAILABLE?");
            String note = input.readLine("  Availability Note (optional): ");
            try
            {
                Donor updated = availabilityService.setAvailability(donorId, status, note);
                System.out.println("  [Success] Availability updated:");
                updated.displayDetails();
            }
            catch (DonorNotFoundException ex)
            {
                System.out.println("  [Error] " + ex.getMessage());
            }
        }
        else if (choice == 2)
        {
            ArrayList<Donor> available = availabilityService.getAvailableDonors();
            System.out.println(DisplayFormatter.formatDonorTable(available));
            System.out.println("  Available Donors: " + available.size());
        }
        else
        {
            ArrayList<Donor> unavailable = availabilityService.getUnavailableDonors();
            System.out.println(DisplayFormatter.formatDonorTable(unavailable));
            System.out.println("  Unavailable Donors: " + unavailable.size());
        }
    }
}
