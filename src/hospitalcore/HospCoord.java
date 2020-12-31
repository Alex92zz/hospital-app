package hospitalcore;

import java.util.*;
import m256people.*;
import m256date.*;
import java.io.*;

/**
 * The coordinating class for the Hospital core system.
 */
public class HospCoord implements java.io.Serializable
{
    //links

    /**
     * a collection of all Ward objects
     */
    private Collection<Ward> wards;
    /**
     * a collection of all Team objects
     */
    private Collection<Team> teams;
    /**
     * the coordinating object
     */
    private static HospCoord hospital = null;

    //constructor
    /**
     * Initialises a new HospCoord object with default wards, teams and doctors.
     * All the wards will initially be empty .
     */
    private HospCoord()
    {
        teams = new HashSet<Team>();
        wards = new HashSet<Ward>();
        readHospitalDetails("hospital.csv");  // initialise the wards, teams and doctors using the default file.
    }

    // <editor-fold defaultstate="collapsed">
    //public protocol
    /**
     * Returns the patients that are on the ward.
     *
     * @param aWard a ward
     *
     * @return an unmodifable collection of all the Patient objects linked to aWard
     */
    public Collection<Patient> getPatients(Ward aWard)
    {
        return Collections.unmodifiableCollection(aWard.getPatients());
    }

    /**
     * Returns a map of (patient, ward) pairs where the patients are those
     * cared for by the team and each ward is the ward that the corresponding patient
     * is on.
     *
     * @param aTeam a team
     *
     * @return a map containing, for each Patient object aPatient linked to aTeam, the key-value pair
     * (aPatient, aWard) where aWard is linked to aPatient
     */
    public Map<Patient, Ward> getPatientsAndWards(Team aTeam)
    {
        return aTeam.getPatientsAndWards();
    }

    /**
     * Returns the doctors that have treated the patient.
     *
     * @param aPatient a patient
     *
     * @return an unmodifiable collection of all the Doctor objects linked to aPatient
     */
    public Collection<Doctor> getDoctors(Patient aPatient)
    {
        return Collections.unmodifiableCollection(aPatient.getDoctors());
    }

    /**
     * Returns the consultant doctor that is responsible for the patient.
     *
     * @param aPatient a patient
     *
     * @return the ConsultantDoctor object linked to aPatient
     */
    public ConsultantDoctor getConsultantDoctor(Patient aPatient)
    {
        return aPatient.getConsultantDoctor();
    }

    /**
     * Returns the team that cares for the patient.
     *
     * @param aPatient a patient
     *
     * @return the Team object linked to aPatient
     */
    public Team getTeam(Patient aPatient)
    {
        return aPatient.getTeam();
    }

    /**
     * Records the treatment of the patient by the doctor.
     * Ensures that aPatient is linked to aDoctor.
     *
     * @param aPatient a patient
     * @param aDoctor a doctor
     *
     * @throws IllegalArgumentException if aDoctor and aPatient are not linked to the same Team object
     */
    public void recordTreatment(Patient aPatient, Doctor aDoctor)
    {
        aPatient.recordTreatmentBy(aDoctor);
    }

    /**
     * Records the admission of a patient with the given attributes and cared
     * for by the given team if there is a ward of the appropriate type with
     * free beds.<p>
     * If there is no Ward object of the appropriate type with at least one free bed
     * then null is returned.<p>
     * Otherwise a new Patient object, aPatient, is created with the supplied attribute values,
     * and age according to aDate and:<p>
     * <ol>
     *   <li>
     *      aPatient is linked to aWard, where aWard is a Ward object of the appropriate type
     *      with the greatest number of free beds, and numberOfFreeBeds of aWard is decremented.
     *   </li>
     *   <li>
     *      aPatient is linked to aTeam
     *   </li>
     *   <li>
     *      aPatient is linked to the ConsultantDoctor object that is linked to aTeam
     *   </li>
     *   <li>
     *      aWard is returned.
     *   </li>
     * </ol>
     *
     * @param aName the name of the patient
     * @param aSex the sex of the patient
     * @param aDate the date of birth of the patient
     * @param aTeam a team
     *
     * @return the Ward object to which the new Patient object is linked
     *         or null if there is no suitable Ward object
     */
    public Ward admit(Name aName, Sex aSex, M256Date aDate, Team aTeam)
    {
        Ward theWard = null;
        int mostBeds = 0;
        int freeBeds = 0;
        for (Ward aWard : wards)
        {
            if (aWard.getType() == aSex)
            {
                freeBeds = aWard.getNumberOfFreeBeds();
                if (freeBeds > mostBeds)
                {
                    mostBeds = freeBeds;
                    theWard = aWard;
                }
            }
        }
        if (theWard != null)
        {
            Patient thePatient = new Patient(aName, aSex, aDate);
            thePatient.admit(theWard, aTeam);
        }
        return theWard;
    }

    /**
     * Returns the doctors that the team contains.
     *
     * @param aTeam a team
     *
     * @return an unmodifiable collection of all the Doctor objects linked to aTeam.
     */
    public Collection<Doctor> getDoctors(Team aTeam)
    {
        return Collections.unmodifiableCollection(aTeam.getDoctors());
    }

    /**
     * Records the discharge of the patient.
     * All links with aPatient are removed.
     *
     * @param aPatient a patient
     */
    public void discharge(Patient aPatient)
    {
        aPatient.discharge();
    }

    /**
     * Returns all patients in the hospital.
     *
     * @return a collection of all the Patient objects
     */
    public Collection<Patient> getPatients()
    {
        Collection<Patient> allPatients = new HashSet<Patient>();
        for (Ward aWard : wards)
        {
            allPatients.addAll(aWard.getPatients());
        }
        return allPatients;
    }

    /**
     * Returns all the wards in the hospital.
     *
     * @return an unmodifiable collection of all the Ward objects
     */
    public Collection<Ward> getWards()
    {
        return Collections.unmodifiableCollection(wards);
    }

    /**
     * Returns all the teams in the hospital.
     *
     * @return an unmodifiable collection of all the Team objects
     */
    public Collection<Team> getTeams()
    {
        return Collections.unmodifiableCollection(teams);
    }

    /**
     * Returns a string representation of all teams and wards.
     *
     * @return a String object representing the receiver
     */
    public String toString()
    {
        return teams.toString() + wards.toString();
    }

    // </editor-fold>
   /**
     * Creates and returns a new HospCoord object.
     * Reads in the state of the object from the
     * file Hospital.data; if there is no such file,
     * or if it is not compatible, returns the object
     * in its initial state.
    *
     * @return a new HospCoord object
     */
    public static HospCoord getHospital()
    {
        FileInputStream fis = null;
        if (hospital == null) //if a coordinating object does not already exist
        {
            try
            {
                fis = new FileInputStream("Hospital.data");
                ObjectInputStream ois = new ObjectInputStream(fis);
                hospital = (HospCoord) ois.readObject();
            }
            catch (Exception ex)
            {
                // let user know that previous data file does not exist or is not compatible
                System.out.println("Data file does not exist or is incompatible with this version of the software.");
                System.out.println("Hospital will be initialised to default state");
                hospital = new HospCoord(); // initialise hospital to default state.
                hospital.save(); //and save it
            }
            finally //as we are not exiting make sure the fis stream is closed.
            {
                try
                {
                    if (fis != null)
                    {
                        fis.close();
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("Error closing file.");
                }
            }

        }
        return hospital;
    }

    /**
     * Saves the state of the receiver
     * to the file Hospital.data.
     */
    public void save()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("Hospital.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        }
        catch (Exception ex)
        {
            System.out.println("Problem storing state of hospital");
            System.exit(1);
        }
    }

    //private protocol
    /**
     * Sets up a hospital with teams, doctors, wards and patients detailed in setupFile. This should be invoked
     * only by the constructor.
     */
    private void readHospitalDetails(String setupFile)
    {
        Scanner fileScanner = null;
        String lineDetails;
        String fieldName;
        String teamName = null;
        Scanner lineScanner;
        ConsultantDoctor cd1 = null;
        Collection<Doctor> doctors = new HashSet<Doctor>();
        List<Object> patientInfo = new ArrayList<Object>();
        Team aTeam;
        try
        {
            fileScanner = new Scanner(new BufferedReader(new FileReader(setupFile)));

            while (fileScanner.hasNextLine())
            {
                lineDetails = fileScanner.nextLine();
                lineScanner = new Scanner(lineDetails);
                lineScanner.useDelimiter(",");
                try
                {
                    fieldName = lineScanner.next();
                    if (fieldName.compareToIgnoreCase("Ward") == 0)
                    {
                        wards.add(new Ward(lineScanner.next(), Sex.valueOf(lineScanner.next()), Integer.parseInt(lineScanner.next())));
                    }
                    else if (fieldName.compareToIgnoreCase("Team") == 0)
                    {
                        if (teamName != null)
                        {
                            aTeam = new Team(teamName, doctors, cd1);
                            teams.add(aTeam);
                            addPatients(patientInfo, aTeam);
                        }
                        teamName = lineScanner.next();
                        doctors = new HashSet<Doctor>();
                        patientInfo = new ArrayList<Object>();
                    }
                    else if (fieldName.compareToIgnoreCase("Consultant") == 0)
                    {
                        cd1 = new ConsultantDoctor(new Name(lineScanner.next(), lineScanner.next(), lineScanner.next()));
                        doctors.add(cd1);
                    }
                    else if (fieldName.compareToIgnoreCase("Junior") == 0)
                    {
                        doctors.add(new JuniorDoctor(new Name(lineScanner.next(), lineScanner.next(), lineScanner.next()), Grade.valueOf(lineScanner.next())));
                    }
                    else if (fieldName.compareToIgnoreCase("Patient") == 0)
                    {
                        patientInfo.add(new Name(lineScanner.next(), lineScanner.next(), lineScanner.next()));
                        patientInfo.add(lineScanner.next());
                        patientInfo.add(new M256Date(lineScanner.next()));
                    }
                }
                catch (Exception anException)
                {
                    System.out.println(anException + ": Data corrupted");
                }
            }
            if (teamName != null)
            {
                aTeam = new Team(teamName, doctors, cd1);
                teams.add(aTeam);
                addPatients(patientInfo, aTeam);
            }
        }
        catch (Exception anException)
        {
            System.out.println("Error: " + anException);
        }
        finally
        {
            fileScanner.close();
        }
    }

    private void addPatients(List pL, Team aTeam)
    {
        Name aName;
        M256Date aDate;
        Sex aSex;
        while (!pL.isEmpty())
        {
            aName = (Name) pL.remove(0);
            if (((String) pL.remove(0)).compareToIgnoreCase("F") == 0)
            {
                aSex = Sex.F;
            }
            else
            {
                aSex = Sex.M;
            }
            aDate = (M256Date) pL.remove(0);
            admit(aName, aSex, aDate, aTeam);
        }
    }
}
