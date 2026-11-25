package service;

import collection.DonorHistoryManager;
import collection.DonorMapManager;
import exception.BloodRequestNotFoundException;
import exception.DonorNotFoundException;
import exception.InvalidDonorDetailsException;
import model.BloodGroupManager;
import model.BloodRequest;
import model.DonationRecord;
import model.Donor;
import utility.IDGenerator;

import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS: DonationService
 * SYLLABUS TOPIC: Unit 1B - StringBuffer (Mutable Audit Log), Wrapper Classes
 *                 Unit 1C - Checked & Unchecked Exceptions
 *                 Unit 2  - ArrayList & HashMap via DonorHistoryManager
 * ============================================================================
 *
 * Records completed blood donations, updates DonorHistory, optionally fulfills
 * an open BloodRequest, and logs every donation event into a StringBuffer.
 */
public class DonationService
{
    private final DonorMapManager donorMapManager;
    private final DonorHistoryManager historyManager;
    private final BloodRequestService bloodRequestService;

    // StringBuffer explicitly demonstrated for accumulating donation audit entries
    private final StringBuffer donationAuditLog;

    public DonationService(DonorMapManager donorMapManager,
                           DonorHistoryManager historyManager,
                           BloodRequestService bloodRequestService)
    {
        this.donorMapManager = donorMapManager;
        this.historyManager = historyManager;
        this.bloodRequestService = bloodRequestService;
        this.donationAuditLog = new StringBuffer();
    }

    public DonationRecord recordDonation(String donorId, int unitsDonated,
                                         String donationDate, String hospitalName,
                                         String remarks, String optionalRequestId)
            throws DonorNotFoundException, BloodRequestNotFoundException
    {
        Donor donor = donorMapManager.getDonorById(donorId);
        if (donor == null)
        {
            throw new DonorNotFoundException(donorId, "Donor not found with ID: " + donorId);
        }
        if (unitsDonated <= 0 || unitsDonated > 2)
        {
            throw new InvalidDonorDetailsException("Units donated in a single session must be 1 or 2.");
        }
        donor.refreshEligibility();
        if (!Boolean.TRUE.equals(donor.getEligibility().isEligible()))
        {
            throw new InvalidDonorDetailsException(
                    "Donor " + donor.getId() + " is not currently eligible: " + donor.getEligibility().getReason());
        }

        if (optionalRequestId != null && !optionalRequestId.trim().isEmpty())
        {
            BloodRequest request = bloodRequestService.getRequestById(optionalRequestId);
            if (!BloodGroupManager.canDonateTo(donor.getBloodGroup(), request.getRequiredBloodGroup()))
            {
                throw new InvalidDonorDetailsException(
                        "Incompatible blood group! Donor (" + donor.getBloodGroup()
                                + ") cannot donate to Request " + request.getRequestId()
                                + " (" + request.getRequiredBloodGroup() + ").");
            }
            bloodRequestService.markRequestFulfilled(request.getRequestId());
        }

        DonationRecord record = new DonationRecord(
                IDGenerator.nextDonationId(),
                donor.getId(),
                donor.getName(),
                donor.getBloodGroup(),
                Integer.valueOf(unitsDonated),
                (donationDate != null && !donationDate.trim().isEmpty()) ? donationDate.trim() : "2026-11-23",
                (hospitalName != null && !hospitalName.trim().isEmpty()) ? hospitalName.trim() : "General Blood Bank",
                remarks);

        donor.recordDonation(record);
        historyManager.addDonationRecord(record);

        // Append to StringBuffer audit log
        donationAuditLog.append("[LOG] ")
                .append(record.getRecordId())
                .append(" | ")
                .append(record.getDonationDate())
                .append(" | Donor: ")
                .append(record.getDonorId())
                .append(" (")
                .append(record.getBloodGroup())
                .append(") donated ")
                .append(record.getUnitsDonated())
                .append(" unit(s) at ")
                .append(record.getHospitalName())
                .append("\n");

        return record;
    }

    public ArrayList<DonationRecord> getAllDonationRecords()
    {
        return historyManager.getAllDonationRecords();
    }

    public String getAuditLogText()
    {
        if (donationAuditLog.length() == 0)
        {
            return "  No donation audit log entries yet.";
        }
        return donationAuditLog.toString();
    }

    public void loadSampleDonations()
    {
        try
        {
            recordDonation("DNR-1001", 1, "2026-10-15", "Apollo Health Center",
                    "Voluntary Blood Camp", null);
            recordDonation("DNR-1002", 1, "2026-11-05", "Manipal Hospital",
                    "Regular Donor", null);
        }
        catch (DonorNotFoundException | BloodRequestNotFoundException ex)
        {
            System.out.println("Warning while loading sample donations: " + ex.getMessage());
        }
    }
}
