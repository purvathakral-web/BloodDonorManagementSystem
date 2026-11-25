package main;

import collection.BloodGroupSetManager;
import collection.DonorHistoryManager;
import collection.DonorListManager;
import collection.DonorMapManager;
import collection.RequestListManager;
import menu.BloodRequestMenu;
import menu.DonorMenu;
import menu.MainMenu;
import menu.ReportMenu;
import service.BloodGroupService;
import service.BloodMatchingService;
import service.BloodRequestService;
import service.DonationService;
import service.DonorAvailabilityService;
import service.DonorDeletionService;
import service.DonorRegistrationService;
import service.DonorSearchService;
import service.DonorUpdateService;
import service.ReportService;
import utility.ConsoleInput;

/**
 * ============================================================================
 * MAIN CLASS: BloodDonorApplication
 * SYLLABUS TOPIC: Unit 1 & Unit 2 - Application Entry Point & Dependency Wiring
 * ============================================================================
 *
 * Entry point of the Blood Donor Management System.
 * Instantiates the in-memory Collection Managers, Service Layer classes,
 * pre-loads sample data for immediate demonstration, and starts MainMenu.
 */
public class BloodDonorApplication
{
    public static void main(String[] args)
    {
        // 1. Initialize Unit 2 Collection Managers (In-Memory Storage)
        DonorListManager donorListManager = new DonorListManager();
        DonorMapManager donorMapManager = new DonorMapManager();
        BloodGroupSetManager bloodGroupSetManager = new BloodGroupSetManager();
        RequestListManager requestListManager = new RequestListManager();
        DonorHistoryManager donorHistoryManager = new DonorHistoryManager();

        // 2. Initialize Service Layer Classes
        DonorRegistrationService registrationService = new DonorRegistrationService(
                donorListManager, donorMapManager, bloodGroupSetManager);
        DonorSearchService searchService = new DonorSearchService(
                donorListManager, donorMapManager);
        DonorUpdateService updateService = new DonorUpdateService(
                donorListManager, donorMapManager);
        DonorDeletionService deletionService = new DonorDeletionService(
                donorListManager, donorMapManager, bloodGroupSetManager);
        DonorAvailabilityService availabilityService = new DonorAvailabilityService(
                donorListManager, donorMapManager);
        BloodGroupService bloodGroupService = new BloodGroupService(
                bloodGroupSetManager, donorMapManager);
        BloodRequestService bloodRequestService = new BloodRequestService(
                requestListManager);
        BloodMatchingService matchingService = new BloodMatchingService(
                donorListManager, bloodRequestService);
        DonationService donationService = new DonationService(
                donorMapManager, donorHistoryManager, bloodRequestService);
        ReportService reportService = new ReportService(
                donorListManager, donorMapManager, bloodGroupSetManager,
                requestListManager, donorHistoryManager, availabilityService);

        // 3. Seed Sample In-Memory Data for Easy Evaluation & Viva Demo
        registrationService.loadSampleDonors();
        bloodRequestService.loadSampleRequests();
        donationService.loadSampleDonations();

        // 4. Initialize Console Menus & Start Interactive Application
        ConsoleInput consoleInput = new ConsoleInput();
        DonorMenu donorMenu = new DonorMenu(
                consoleInput, registrationService, searchService,
                updateService, deletionService, availabilityService);
        BloodRequestMenu bloodRequestMenu = new BloodRequestMenu(
                consoleInput, bloodRequestService, matchingService, donationService);
        ReportMenu reportMenu = new ReportMenu(
                consoleInput, reportService, bloodGroupService, donationService);
        MainMenu mainMenu = new MainMenu(
                consoleInput, donorMenu, bloodRequestMenu, reportMenu);

        System.out.println("Loaded sample dataset: 8 Donors (all 8 blood groups), 3 Blood Requests, 2 Donations.");
        mainMenu.start();
    }
}
