package model;

import interfaces.Displayable;

/**
 * ============================================================================
 * ABSTRACT CLASS: Person
 * SYLLABUS TOPIC: Unit 1A - Abstraction, Abstract Classes, Inheritance,
 *                           Encapsulation, Constructor Overloading,
 *                           this keyword, static keyword & static block,
 *                           Interfaces (implements Displayable)
 * ============================================================================
 *
 * Abstract parent class for both Donor and Patient.
 * Cannot be instantiated directly; subclasses must implement getRole().
 */
public abstract class Person implements Displayable
{
    private String id;
    private String name;
    private int age;
    private String gender;
    private DonorContact contact;   // Composition: Person HAS-A DonorContact
    private DonorAddress address;   // Composition: Person HAS-A DonorAddress

    // Static fields shared across all Person subclasses
    private static int totalPersonsCreated;
    public static final String SYSTEM_ORGANIZATION;

    // Static initialization block executed once when the class is loaded
    static
    {
        totalPersonsCreated = 0;
        SYSTEM_ORGANIZATION = "National Blood Donor Registry (College Edition)";
    }

    // Overloaded constructor (minimal details) using this(...) chaining
    public Person(String id, String name, int age)
    {
        this(id, name, age, "Not Specified", new DonorContact(), new DonorAddress());
    }

    // Master constructor
    public Person(String id, String name, int age, String gender,
                  DonorContact contact, DonorAddress address)
    {
        this.id = id;
        this.name = (name != null) ? name.trim() : "Unnamed";
        this.age = age;
        this.gender = (gender != null) ? gender.trim() : "Not Specified";
        this.contact = (contact != null) ? contact : new DonorContact();
        this.address = (address != null) ? address : new DonorAddress();
        totalPersonsCreated++;
    }

    // Abstract method - forces subclasses (Donor, Patient) to provide their own role
    public abstract String getRole();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = (name != null) ? name.trim() : this.name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public DonorContact getContact()
    {
        return contact;
    }

    public void setContact(DonorContact contact)
    {
        this.contact = contact;
    }

    public DonorAddress getAddress()
    {
        return address;
    }

    public void setAddress(DonorAddress address)
    {
        this.address = address;
    }

    public static int getTotalPersonsCreated()
    {
        return totalPersonsCreated;
    }

    /**
     * Base implementation of Displayable.getDisplaySummary(); overridden by subclasses.
     */
    @Override
    public String getDisplaySummary()
    {
        return String.format("[%s] %s | %s (Age: %d, %s)",
                getRole(), id, name, age, gender);
    }

    @Override
    public void displayDetails()
    {
        System.out.println(getDisplaySummary());
        System.out.println("  Contact : " + contact.toString());
        System.out.println("  Address : " + address.getFormattedAddress());
    }
}
