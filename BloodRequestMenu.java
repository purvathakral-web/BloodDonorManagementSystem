package menu;

import exception.BloodRequestNotFoundException;
import exception.DonorNotFoundException;
import exception.InvalidBloodGroupException;
import exception.InvalidDonorDetailsException;
import model.BloodRequest;
import model.DonationRecord;
import model.Donor;
import service.BloodMatchingService;
import service.BloodRequestService;
import service.DonationService;
import utility.ConsoleInput;
import utility.DisplayFormatter;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ============================================================================
 * CLASS: BloodRequestMenu
 * SYLLABUS TOPIC: Unit 1C - Multi-Catch & Exception Propagation Handling
 *                 Unit 2  - LinkedList Request Queue & ArrayList Matching Output
 * ============================================================================
 *
 * Handles console flows for Blood Requests, Donor Matching, and Recording Donations.
 */
public class BloodRequestMenu
{
    private final ConsoleInput input;
    private final BloodRequestService requestService;
    private final BloodMatchingService matchingService;
    private final DonationService donationService;

    public BloodRequestMenu(ConsoleInput input,
                            BloodRequestService requestService,
                            BloodMatchingService matchingService,
                            DonationService donationService)
    {
        this.input = input;
        this.requestService = requestService;
        this.matchingService = matchingService;
        this.donationService = donationService;
    }

    public void handleRegisterBloodRequest()
    {
        System.out.println(DisplayFormatter.createSubHeader("Register Blood Request"));
        try
        {
            String patientName = input.readNonEmptyLine("  Patient Name                    : ");
            int patientAge = input.readIntInRange("  Patient Age (1-120)             : ", 1, 120);
            String gender = input.readNonEmptyLine("  Patient Gender                  : ");
            String bloodGroup = input.readNonEmptyLine("  Required Blood Group            : ");
            String reason = input.readNonEmptyLine("  Medical Reason                  : ");
            String hospitalName = input.readNonEmptyLine("  Hospital Name                   : ");
            String hospitalCity = input.readNonEmptyLine("  Hospital City                   : ");
            String hospitalPhone = input.readNonEmptyLine("  Hospital Contact Number         : ");
            int units = input.readIntInRange("  Units Required (1-10)           : ", 1, 10);
            String urgency = input.readNonEmptyLine("  Urgency (NORMAL/HIGH/CRITICAL)  : ");
            String date = input.readNonEmptyLine("  Request Date (YYYY-MM-DD)       : ");

            BloodRequest request = requestService.registerRequest(
                    patientName, patientAge, gender, bloodGroup, reason,
                    hospitalName, hospitalCity, hospitalPhone, units, urgency, date);

            System.out.println("\n  [Success] Blood Request registered!");
            request.displayDetails();
            if (request.isUrgent())
            {
                System.out.println("  [Priority] Urgent request placed at the FRONT of the LinkedList queue.");
            }
        }
        catch (InvalidBloodGroupException | InvalidDonorDetailsException ex)
        {
            System.out.println("  [Request Error] " + ex.getMessage());
        }
    }

    public void handleViewBloodRequests()
    {
        System.out.println(DisplayFormatter.createSubHeader("Blood Requests (LinkedList Priority Queue Order)"));
        LinkedList<BloodRequest> requests = requestService.getAllRequests();
        if (requests.isEmpty())
        {
            System.out.println("  No blood requests registered.");
            return;
        }
        int position = 1;
        for (BloodRequest request : requests)
        {
            System.out.println("  " + position + ". " + request.getDisplaySummary());
            position++;
        }
        System.out.println("  Total Requests: " + requests.size());
    }

    public void handleFindMatchingDonors()
    {
        System.out.println(DisplayFormatter.createSubHeader("Find Matching Available Donors"));
        System.out.println("  1. Match by Blood Request ID");
        System.out.println("  2. Match by Blood Group (All Medically Compatible Donors)");
        System.out.println("  3. Match by Blood Group (Exact Group Match Only)");
        System.out.println("  4. Match by Blood Group & Preferred City");
        System.out.println("  5. Record a Blood Donation (Optionally Fulfill Request)");
        int choice = input.readIntInRange("  Choose option (1-5): ", 1, 5);

        try
        {
            if (choice == 1)
            {
                String requestId = input.readNonEmptyLine("  Enter Blood Request ID (e.g., REQ-4001): ");
                BloodRequest req = requestService.getRequestById(requestId);
                System.out.println("  Target Request: " + req.getDisplaySummary());
                ArrayList<Donor> matches = matchingService.findMatchingDonorsForRequest(requestId);
                System.out.println(DisplayFormatter.formatDonorTable(matches));
                System.out.println("  Compatible Eligible Donors Found: " + matches.size());
            }
            else if (choice == 2)
            {
                String group = input.readNonEmptyLine("  Enter Recipient Blood Group: ");
                ArrayList<Donor> matches = matchingService.findMatchingDonors(group, false);
                System.out.println(DisplayFormatter.formatDonorTable(matches));
                System.out.println("  Compatible Eligible Donors Found: " + matches.size());
            }
            else if (choice == 3)
            {
                String group = input.readNonEmptyLine("  Enter Exact Blood Group: ");
                ArrayList<Donor> matches = matchingService.findMatchingDonors(group, true);
                System.out.println(DisplayFormatter.formatDonorTable(matches));
                System.out.println("  Exact Match Eligible Donors Found: " + matches.size());
            }
            else if (choice == 4)
            {
                String group = input.readNonEmptyLine("  Enter Recipient Blood Group: ");
                String city = input.readNonEmptyLine("  Enter Preferred City: ");
                ArrayList<Donor> matches = matchingService.findMatchingDonors(group, city);
                System.out.println(DisplayFormatter.formatDonorTable(matches));
                System.out.println("  Compatible Eligible Donors in " + city + ": " + matches.size());
            }
            else
            {
                handleRecordDonation();
            }
        }
        catch (BloodRequestNotFoundException | InvalidBloodGroupException ex)
        {
            System.out.println("  [Matching Error] " + ex.getMessage());
        }
    }

    public void handleRecordDonation()
    {
        System.out.println(DisplayFormatter.createSubHeader("Record Blood Donation"));
        try
        {
            String donorId = input.readNonEmptyLine("  Donor ID (e.g., DNR-1001)         : ");
            int units = input.readIntInRange("  Units Donated (1-2)               : ", 1, 2);
            String date = input.readNonEmptyLine("  Donation Date (YYYY-MM-DD)        : ");
            String hospital = input.readNonEmptyLine("  Hospital / Blood Bank Name        : ");
            String remarks = input.readLine("  Remarks (optional)                : ");
            String reqId = input.readLine("  Blood Request ID to Fulfill (optional, press Enter to skip): ");

            DonationRecord record = donationService.recordDonation(
                    donorId, units, date, hospital, remarks, reqId);

            System.out.println("\n  [Success] Donation recorded successfully!");
            record.displayDetails();
        }
        catch (DonorNotFoundException | BloodRequestNotFoundException ex)
        {
            System.out.println("  [Record Error] " + ex.getMessage());
        }
        catch (InvalidDonorDetailsException ex)
        {
            System.out.println("  [Eligibility / Compatibility Error] " + ex.getMessage());
        }
    }
}
