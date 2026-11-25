package service;

import collection.BloodGroupSetManager;
import collection.DonorHistoryManager;
import collection.DonorListManager;
import collection.DonorMapManager;
import collection.RequestListManager;
import interfaces.Reportable;
import model.BloodGroupManager;
import model.BloodRequest;
import model.Donor;
import model.Person;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * ============================================================================
 * CLASS: ReportService
 * SYLLABUS TOPIC: Unit 1A - Interfaces (implements Reportable),
 *                           Runtime Polymorphism (Person -> Donor & Patient)
 *                 Unit 1B - 2D Array Traversal (int[][]), StringBuilder, StringBuffer
 *                 Unit 2  - All 8 Collections (ArrayList, LinkedList, HashSet,
 *                           LinkedHashSet, TreeSet, HashMap, LinkedHashMap, TreeMap)
 * ============================================================================
 *
 * Generates formatted statistical, availability, collection, and polymorphism reports.
 */
public class ReportService implements Reportable
{
    private final DonorListManager listManager;
    private final DonorMapManager mapManager;
    private final BloodGroupSetManager setManager;
    private final RequestListManager requestListManager;
    private final DonorHistoryManager historyManager;
    private final DonorAvailabilityService availabilityService;

    public ReportService(DonorListManager listManager,
                         DonorMapManager mapManager,
                         BloodGroupSetManager setManager,
                         RequestListManager requestListManager,
                         DonorHistoryManager historyManager,
                         DonorAvailabilityService availabilityService)
    {
        this.listManager = listManager;
        this.mapManager = mapManager;
        this.setManager = setManager;
        this.requestListManager = requestListManager;
        this.historyManager = historyManager;
        this.availabilityService = availabilityService;
    }

    @Override
    public String generateSummaryReport()
    {
        int totalDonors = listManager.getDonorCount();
        int availableDonors = availabilityService.getAvailableDonors().size();
        int unavailableDonors = totalDonors - availableDonors;

        StringBuilder sb = new StringBuilder();
        sb.append("============================================================\n");
        sb.append("             SYSTEM SUMMARY & STATISTICS REPORT             \n");
        sb.append("============================================================\n");
        sb.append(String.format("  Organization              : %s\n", Person.SYSTEM_ORGANIZATION));
        sb.append(String.format("  Total Person Objects      : %d\n", Person.getTotalPersonsCreated()));
        sb.append(String.format("  Registered Donors         : %d\n", totalDonors));
        sb.append(String.format("  Available Donors          : %d\n", availableDonors));
        sb.append(String.format("  Unavailable Donors        : %d\n", unavailableDonors));
        sb.append(String.format("  Active Blood Groups       : %d / %d\n",
                setManager.getActiveBloodGroups().size(),
                setManager.getSupportedBloodGroups().size()));
        sb.append(String.format("  Total Blood Requests      : %d (Pending: %d)\n",
                requestListManager.getTotalRequestCount(),
                requestListManager.getPendingRequestCount()));
        sb.append(String.format("  Total Donations Recorded  : %d (Units Collected: %d)\n",
                historyManager.getTotalDonationsCount(),
                historyManager.getTotalUnitsCollected()));
        sb.append("============================================================");
        return sb.toString();
    }

    @Override
    public String generateBloodGroupReport()
    {
        TreeMap<String, Integer> sortedCounts = mapManager.getBloodGroupCountMap();
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------------------\n");
        sb.append("     BLOOD GROUP COUNTS (Stored in Sorted TreeMap)          \n");
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("  %-15s | %-15s\n", "Blood Group", "Donor Count"));
        sb.append("------------------------------------------------------------\n");
        for (Map.Entry<String, Integer> entry : sortedCounts.entrySet())
        {
            sb.append(String.format("  %-15s | %-15d\n", entry.getKey(), entry.getValue().intValue()));
        }
        sb.append("------------------------------------------------------------");
        return sb.toString();
    }

    @Override
    public String generateAvailabilityMatrixReport()
    {
        int[][] matrix = availabilityService.buildBloodGroupAvailabilityMatrix();
        String[] groups = BloodGroupManager.SUPPORTED_BLOOD_GROUPS;

        StringBuffer sb = new StringBuffer();
        sb.append("------------------------------------------------------------\n");
        sb.append("   BLOOD GROUP AVAILABILITY MATRIX (2D int[][] Array)       \n");
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("  %-12s | %-10s | %-12s | %-12s\n",
                "Blood Group", "Total", "Available", "Unavailable"));
        sb.append("------------------------------------------------------------\n");
        for (int row = 0; row < groups.length; row++)
        {
            sb.append(String.format("  %-12s | %-10d | %-12d | %-12d\n",
                    groups[row], matrix[row][0], matrix[row][1], matrix[row][2]));
        }
        sb.append("------------------------------------------------------------");
        return sb.toString();
    }

    /**
     * Shows a live snapshot of all 8 Unit 2 Collections in action for viva evaluation.
     */
    public String generateCollectionsInspectionReport()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("============================================================\n");
        sb.append("       UNIT 2 COLLECTIONS FRAMEWORK INSPECTION REPORT       \n");
        sb.append("============================================================\n");

        sb.append("1. ArrayList<Donor> (Registered Donors Count): ")
          .append(listManager.getDonorCount()).append("\n");

        sb.append("2. LinkedList<BloodRequest> (Request Queue Order): ")
          .append(requestListManager.getTotalRequestCount()).append(" request(s)\n");

        sb.append("3. HashSet<String> (Unique Donor IDs): ")
          .append(listManager.getUniqueDonorIds()).append("\n");

        LinkedHashSet<String> supported = setManager.getSupportedBloodGroups();
        sb.append("4. LinkedHashSet<String> (8 Blood Groups in Insertion Order): ")
          .append(supported).append("\n");

        TreeSet<String> sortedIds = listManager.getSortedDonorIds();
        sb.append("5. TreeSet<String> (Sorted Donor IDs): ")
          .append(sortedIds).append("\n");

        sb.append("6. HashMap<String, Donor> (O(1) Lookup Keys Count): ")
          .append(mapManager.getDonorHashMap().size()).append("\n");

        LinkedHashMap<String, Donor> insertionMap = mapManager.getDonorInsertionMap();
        sb.append("7. LinkedHashMap<String, Donor> (Registration Order): ");
        sb.append(insertionMap.keySet()).append("\n");

        TreeMap<String, Integer> groupCounts = mapManager.getBloodGroupCountMap();
        sb.append("8. TreeMap<String, Integer> (Sorted Blood Group Counts): ")
          .append(groupCounts).append("\n");

        sb.append("============================================================");
        return sb.toString();
    }

    /**
     * Demonstrates Runtime Polymorphism by storing both Donor and Patient objects
     * inside an ArrayList<Person> and calling overridden getDisplaySummary().
     */
    public String generatePolymorphicPeopleDirectory()
    {
        ArrayList<Person> people = new ArrayList<Person>();
        for (Donor donor : listManager.getAllDonors())
        {
            people.add(donor); // Upcasting Donor -> Person
        }
        for (BloodRequest request : requestListManager.getAllRequests())
        {
            if (request.getPatient() != null)
            {
                people.add(request.getPatient()); // Upcasting Patient -> Person
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("============================================================\n");
        sb.append("    POLYMORPHIC PERSON DIRECTORY (Donor & Patient as Person)\n");
        sb.append("============================================================\n");
        for (Person person : people)
        {
            // Dynamic method dispatch invokes Donor.getDisplaySummary() or Patient.getDisplaySummary()
            sb.append("  ").append(person.getDisplaySummary()).append("\n");
        }
        sb.append("============================================================");
        return sb.toString();
    }
}
