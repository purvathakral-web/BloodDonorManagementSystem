package service;

import collection.RequestListManager;
import exception.BloodRequestNotFoundException;
import exception.InvalidBloodGroupException;
import model.BloodRequest;
import model.DonorAddress;
import model.DonorContact;
import model.Hospital;
import model.Patient;
import utility.BloodGroupValidator;
import utility.IDGenerator;
import utility.InputValidator;

import java.util.LinkedList;

/**
 * ============================================================================
 * CLASS: BloodRequestService
 * SYLLABUS TOPIC: Unit 1A - Object Collaboration (Patient, Hospital, BloodRequest)
 *                 Unit 1C - Custom Checked Exceptions & Propagation
 *                 Unit 2  - LinkedList Request Queue Management
 * ============================================================================
 *
 * Registers blood requests, manages urgency ordering via RequestListManager,
 * and updates request status (PENDING -> FULFILLED / CANCELLED).
 */
public class BloodRequestService
{
    private final RequestListManager requestListManager;
    private final BloodGroupValidator bloodGroupValidator;
    private final InputValidator inputValidator;

    public BloodRequestService(RequestListManager requestListManager)
    {
        this.requestListManager = requestListManager;
        this.bloodGroupValidator = new BloodGroupValidator();
        this.inputValidator = new InputValidator();
    }

    public BloodRequest registerRequest(String patientName, int patientAge, String gender,
                                        String requiredBloodGroup, String medicalReason,
                                        String hospitalName, String hospitalCity,
                                        String hospitalPhone, int unitsRequired,
                                        String urgencyLevel, String requestDate)
            throws InvalidBloodGroupException
    {
        String cleanPatientName = inputValidator.sanitizeText(patientName);
        inputValidator.requireValidName(cleanPatientName);
        inputValidator.requireNonEmpty(hospitalName, "Hospital Name");
        inputValidator.requireNonEmpty(hospitalCity, "Hospital City");

        String normalizedGroup = bloodGroupValidator.validateAndNormalize(requiredBloodGroup);

        Hospital hospital = new Hospital(
                IDGenerator.nextHospitalId(),
                hospitalName,
                hospitalCity,
                hospitalPhone,
                true);

        Patient patient = new Patient(
                IDGenerator.nextPatientId(),
                cleanPatientName,
                patientAge,
                gender,
                new DonorContact(hospitalPhone, "patient@hospital.org", hospitalPhone),
                new DonorAddress("Hospital Campus", hospitalCity, "India", "000000"),
                normalizedGroup,
                medicalReason,
                hospital);

        BloodRequest request = new BloodRequest(
                IDGenerator.nextRequestId(),
                patient,
                hospital,
                normalizedGroup,
                Integer.valueOf(unitsRequired > 0 ? unitsRequired : 1),
                urgencyLevel,
                requestDate);

        requestListManager.addRequest(request);
        return request;
    }

    public LinkedList<BloodRequest> getAllRequests()
    {
        return requestListManager.getAllRequests();
    }

    public BloodRequest getRequestById(String requestId) throws BloodRequestNotFoundException
    {
        BloodRequest request = requestListManager.findByRequestId(requestId);
        if (request == null)
        {
            throw new BloodRequestNotFoundException("Blood request not found with ID: " + requestId);
        }
        return request;
    }

    public void markRequestFulfilled(String requestId) throws BloodRequestNotFoundException
    {
        BloodRequest request = getRequestById(requestId);
        request.setStatus("FULFILLED");
    }

    public void loadSampleRequests()
    {
        try
        {
            registerRequest("Suresh Patel", 45, "Male", "A+", "Surgery Requirement",
                    "City Care Hospital", "Pune", "9820011223", 2, "HIGH", "2026-11-22");
            registerRequest("Kavita Joshi", 34, "Female", "O-", "Emergency Trauma",
                    "Apollo Health Center", "Mumbai", "9820044556", 1, "CRITICAL", "2026-11-23");
            registerRequest("Deepak Mehta", 52, "Male", "AB+", "Anemia Treatment",
                    "Manipal Hospital", "Bengaluru", "9820077889", 2, "NORMAL", "2026-11-23");
        }
        catch (InvalidBloodGroupException ex)
        {
            System.out.println("Warning while loading sample blood requests: " + ex.getMessage());
        }
    }
}
