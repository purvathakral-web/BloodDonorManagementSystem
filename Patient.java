package model;

/**
 * ============================================================================
 * CLASS: Patient
 * SYLLABUS TOPIC: Unit 1A - Inheritance (extends Person), Method Overriding,
 *                           Runtime Polymorphism, Association with Hospital
 * ============================================================================
 *
 * Represents a patient requesting blood. Extends Person alongside Donor to
 * demonstrate hierarchical inheritance and runtime polymorphism.
 */
public class Patient extends Person
{
    private String requiredBloodGroup;
    private String medicalReason;
    private Hospital hospital; // Association: Patient is admitted at a Hospital

    public Patient(String id, String name, int age, String requiredBloodGroup, Hospital hospital)
    {
        this(id, name, age, "Not Specified", new DonorContact(), new DonorAddress(),
                requiredBloodGroup, "Medical Requirement", hospital);
    }

    public Patient(String id, String name, int age, String gender,
                   DonorContact contact, DonorAddress address,
                   String requiredBloodGroup, String medicalReason, Hospital hospital)
    {
        super(id, name, age, gender, contact, address);
        this.requiredBloodGroup = (requiredBloodGroup != null) ? requiredBloodGroup.trim().toUpperCase() : "O+";
        this.medicalReason = (medicalReason != null && !medicalReason.trim().isEmpty())
                ? medicalReason.trim()
                : "Medical Requirement";
        this.hospital = hospital;
    }

    // Method Overriding (Runtime Polymorphism)
    @Override
    public String getRole()
    {
        return "PATIENT";
    }

    public String getRequiredBloodGroup()
    {
        return requiredBloodGroup;
    }

    public void setRequiredBloodGroup(String requiredBloodGroup)
    {
        this.requiredBloodGroup = requiredBloodGroup.trim().toUpperCase();
    }

    public String getMedicalReason()
    {
        return medicalReason;
    }

    public void setMedicalReason(String medicalReason)
    {
        this.medicalReason = medicalReason;
    }

    public Hospital getHospital()
    {
        return hospital;
    }

    public void setHospital(Hospital hospital)
    {
        this.hospital = hospital;
    }

    @Override
    public String getDisplaySummary()
    {
        String hospitalInfo = (hospital != null) ? hospital.getHospitalName() : "N/A";
        return String.format("[PATIENT] %-8s | %-18s | Age: %-2d | Needs: %-3s | Hospital: %s",
                getId(), getName(), getAge(), requiredBloodGroup, hospitalInfo);
    }

    @Override
    public void displayDetails()
    {
        super.displayDetails();
        System.out.println("  Required Group : " + requiredBloodGroup);
        System.out.println("  Medical Reason : " + medicalReason);
        System.out.println("  Hospital       : " + (hospital != null ? hospital.toString() : "Not Assigned"));
    }
}
