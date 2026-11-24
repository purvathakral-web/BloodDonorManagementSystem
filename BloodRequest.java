package model;

import interfaces.Displayable;

/**
 * ============================================================================
 * CLASS: BloodRequest
 * SYLLABUS TOPIC: Unit 1A - Encapsulation, Composition/Association, Interfaces
 *                 Unit 1B - Wrapper Classes (Integer) & String Formatting
 * ============================================================================
 *
 * Represents a hospital/patient request for blood units.
 * Stored and processed using a LinkedList in RequestListManager.
 */
public class BloodRequest implements Displayable
{
    private String requestId;
    private Patient patient;           // Association
    private Hospital hospital;         // Association
    private String requiredBloodGroup;
    private Integer unitsRequired;     // Wrapper class
    private String urgencyLevel;       // NORMAL, HIGH, CRITICAL
    private String status;             // PENDING, FULFILLED, CANCELLED
    private String requestDate;

    public BloodRequest(String requestId, Patient patient, Hospital hospital,
                        String requiredBloodGroup, Integer unitsRequired,
                        String urgencyLevel, String requestDate)
    {
        this.requestId = requestId;
        this.patient = patient;
        this.hospital = hospital;
        this.requiredBloodGroup = (requiredBloodGroup != null) ? requiredBloodGroup.trim().toUpperCase() : "O+";
        this.unitsRequired = (unitsRequired != null && unitsRequired.intValue() > 0)
                ? unitsRequired
                : Integer.valueOf(1);
        this.urgencyLevel = (urgencyLevel != null) ? urgencyLevel.trim().toUpperCase() : "NORMAL";
        this.status = "PENDING";
        this.requestDate = (requestDate != null) ? requestDate.trim() : "Today";
    }

    public String getRequestId()
    {
        return requestId;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public Hospital getHospital()
    {
        return hospital;
    }

    public String getRequiredBloodGroup()
    {
        return requiredBloodGroup;
    }

    public Integer getUnitsRequired()
    {
        return unitsRequired;
    }

    public String getUrgencyLevel()
    {
        return urgencyLevel;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        if (status != null)
        {
            this.status = status.trim().toUpperCase();
        }
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public boolean isUrgent()
    {
        return "CRITICAL".equalsIgnoreCase(urgencyLevel) || "HIGH".equalsIgnoreCase(urgencyLevel);
    }

    @Override
    public String getDisplaySummary()
    {
        String patientName = (patient != null) ? patient.getName() : "Unknown";
        String hospitalName = (hospital != null) ? hospital.getHospitalName() : "Unknown";
        return String.format("%-8s | Patient: %-16s | Group: %-3s | Units: %d | Urgency: %-8s | Status: %-9s | Hospital: %s",
                requestId, patientName, requiredBloodGroup, unitsRequired.intValue(),
                urgencyLevel, status, hospitalName);
    }

    @Override
    public void displayDetails()
    {
        System.out.println(getDisplaySummary());
        System.out.println("  Request Date : " + requestDate);
        if (hospital != null)
        {
            System.out.println("  Hospital Info: " + hospital.getDisplaySummary());
        }
    }

    @Override
    public String toString()
    {
        return getDisplaySummary();
    }
}
