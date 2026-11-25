# Blood Donor Management System (Java OOP Console Project)

A well-organized, viva-ready, console-based **Blood Donor Management System** developed in core Java strictly following **Unit 1** (Object-Oriented Programming, Arrays, Strings, Wrapper Classes, Exception Handling) and **Unit 2** (Java Collections Framework) of the university Java syllabus.

---

## Key Project Metrics

- **Total Java Source Files**: 48
- **Actual Java Classes**: 44 (1 Abstract Class + 43 Concrete Classes) — exceeds the 30-class minimum
- **Interfaces**: 4 (`Displayable`, `Searchable`, `Validatable`, `Reportable`)
- **Packages**: 8 (`main`, `model`, `interfaces`, `service`, `collection`, `utility`, `exception`, `menu`)
- **Supported Blood Groups**: All 8 (`A+`, `A-`, `B+`, `B-`, `AB+`, `AB-`, `O+`, `O-`)
- **Data Storage**: 100% In-Memory Java Objects & Collections (Zero SQL/JDBC, Zero File I/O, Zero Multithreading, Zero Stream API/Lambdas, Zero External Libraries)

---

## Complete Project Folder Structure

```text
BloodDonorManagementSystem/
├── README.md
├── run.ps1
├── run.bat
├── docs/
│   ├── Class_Responsibilities.md
│   ├── Class_Relationship_Diagram.md
│   └── Syllabus_Mapping.md
├── out/                                  (Generated .class files after compilation)
└── src/
    ├── main/
    │   └── BloodDonorApplication.java
    ├── interfaces/
    │   ├── Displayable.java
    │   ├── Reportable.java
    │   ├── Searchable.java
    │   └── Validatable.java
    ├── model/
    │   ├── BloodGroupManager.java
    │   ├── BloodRequest.java
    │   ├── DonationRecord.java
    │   ├── Donor.java
    │   ├── DonorAddress.java
    │   ├── DonorContact.java
    │   ├── DonorEligibility.java
    │   ├── DonorHistory.java
    │   ├── DonorProfile.java
    │   ├── Hospital.java
    │   ├── Patient.java
    │   └── Person.java
    ├── collection/
    │   ├── BloodGroupSetManager.java
    │   ├── DonorHistoryManager.java
    │   ├── DonorListManager.java
    │   ├── DonorMapManager.java
    │   └── RequestListManager.java
    ├── exception/
    │   ├── BloodRequestNotFoundException.java
    │   ├── DonorNotFoundException.java
    │   ├── DuplicateDonorException.java
    │   ├── InvalidBloodGroupException.java
    │   └── InvalidDonorDetailsException.java
    ├── utility/
    │   ├── AgeValidator.java
    │   ├── BloodGroupValidator.java
    │   ├── ConsoleInput.java
    │   ├── DisplayFormatter.java
    │   ├── IDGenerator.java
    │   ├── InputValidator.java
    │   └── PhoneValidator.java
    ├── service/
    │   ├── BloodGroupService.java
    │   ├── BloodMatchingService.java
    │   ├── BloodRequestService.java
    │   ├── DonationService.java
    │   ├── DonorAvailabilityService.java
    │   ├── DonorDeletionService.java
    │   ├── DonorRegistrationService.java
    │   ├── DonorSearchService.java
    │   ├── DonorUpdateService.java
    │   └── ReportService.java
    └── menu/
        ├── BloodRequestMenu.java
        ├── DonorMenu.java
        ├── MainMenu.java
        └── ReportMenu.java
```

---

## Functional Modules

1. **Donor Registration**: Validates name, age (18-65), weight (>= 50 kg), 10-digit Indian phone number, and blood group; prevents duplicates using `HashSet`.
2. **Donor Information Management**: View all donors in tabular format, inspect formatted `DonorProfile` cards, update phone/email/address/age/weight, and delete donors cleanly across all collections.
3. **Donor Searching**: Search by Donor ID (`HashMap` O(1)), Name keyword (`String.contains`), Blood Group (`equalsIgnoreCase`), City, or combined Blood Group + City (method overloading).
4. **Blood Group Management**: All 8 blood groups stored in 1D `String[]`, `LinkedHashSet`, and 2D `String[][]` medical compatibility matrix.
5. **Donor Availability Management**: Toggle availability with optional reason notes (method overloading), filter available/unavailable donors, and compute an 8x3 `int[][]` availability matrix.
6. **Blood Request Management**: Register hospital patient requests; `LinkedList` places `CRITICAL`/`HIGH` urgency requests at the front (`addFirst`) and `NORMAL` requests at the back (`addLast`).
7. **Matching Donors with Blood Requests**: Matches pending requests or blood groups against eligible, available, and medically compatible donors (including `O-` universal donors).
8. **Donation Records**: Records completed donations, updates `DonorHistory`, optionally marks a compatible `BloodRequest` as `FULFILLED`, and appends to a `StringBuffer` audit log.
9. **Reports & Statistics**: System summary, sorted `TreeMap` blood-group counts, 2D `int[][]` availability matrix, 2D compatibility chart, live inspection of all 8 Unit 2 collections, and polymorphic `Person` directory.

---

## How to Compile and Run

### Option 1: PowerShell (Windows)

```powershell
cd BloodDonorManagementSystem
.\run.ps1
```

Or manually:

```powershell
mkdir out -Force
$sources = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $sources
java -cp out main.BloodDonorApplication
```

### Option 2: Command Prompt (Windows)

```cmd
cd BloodDonorManagementSystem
run.bat
```

### Option 3: IntelliJ IDEA / Eclipse / VS Code

1. Open the `BloodDonorManagementSystem` folder as a Java project.
2. Mark `src` as the Sources Root.
3. Open `src/main/BloodDonorApplication.java` and click **Run**.

---

## Pre-Loaded Sample Data

On startup, the application automatically loads:
- **8 Sample Donors** (`DNR-1001` through `DNR-1008`) covering all 8 blood groups (`O-`, `A+`, `B+`, `AB+`, `O+`, `A-`, `B-`, `AB-`).
- **3 Sample Blood Requests** (`REQ-4001`, `REQ-4002`, `REQ-4003`) demonstrating urgency prioritization in `LinkedList`.
- **2 Sample Donation Records** (`DON-5001`, `DON-5002`) populating `DonorHistory` and the `StringBuffer` audit log.

---

## Additional Documentation

- [Class Responsibilities Guide](docs/Class_Responsibilities.md)
- [Class Relationship & Architecture Diagrams (Mermaid)](docs/Class_Relationship_Diagram.md)
- [Unit 1 & Unit 2 Syllabus-to-Class Mapping](docs/Syllabus_Mapping.md)
