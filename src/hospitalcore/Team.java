package hospitalcore;

import java.util.*;

/**
 * Team objects represent the teams in the hospital.
 */
public class Team implements java.io.Serializable
{
    //attributes
    
    /**
     * the unique code of the team
     */
    private String code;
    
    
    //links
    
    /**
     * all the linked Patient objects
     */
    private Collection<Patient> patients; 
    
    /**
     * the linked ConsultantDoctor object
     */
    private ConsultantDoctor consultantDoctor; 
    
    /**
     * all the linked Doctor objects 
     */
    private Collection<Doctor> doctors; 
    
    
    //constructor
    
    /**
     * Initialises a new Team object with the given code, containing the given
     * doctors.
     *
     * @param aCode the code of the team
     * @param aDoctorsCollection the doctors that are in the team. At least one must be a
     * junior doctor with a grade of 1.
     * @param aConsultantDoctor the consultant doctor that heads the team
     */
    Team(String aCode, Collection<Doctor> aDoctorsCollection, ConsultantDoctor aConsultantDoctor)
    {
        code = aCode;
        doctors = aDoctorsCollection;
        consultantDoctor = aConsultantDoctor;
        patients = new HashSet<Patient>();
    }
    
    
    //public protocol
    
    /**
     * Returns the code of this team.
     *
     * @return code
     */
    public String getCode()
    {
        return code;
    }
    
    
    /**
     * Returns a string representation of this team's code, doctors and patients
     *
     * @return a string object representing the receiver
     */
    public String toString()
    {
        return code + ": " + doctors + ":" + patients;
    }
    
    
    //package protocol
    
    /**
     * Returns a map of (patient, ward) pairs where the patients are those
     * cared for by this team and each ward is the ward that the corresponding patient
     * is on.
     *
     * @return a map containing, for each  Patient object aPatient linked to the receiver the key-value pair
     * (aPatient, aWard) where aWard is linked to aPatient
     */
    Map<Patient, Ward> getPatientsAndWards()
    {
        Map<Patient, Ward> results = new HashMap<Patient,Ward>();
        for (Patient aPatient : patients)
        {
            results.put(aPatient, aPatient.getWard());
        }
        return results;
    }
    
    
    /**
     * Adds the patient to those cared for by this team.
     * A reference to aPatient is recorded.
     *
     *
     * @param aPatient a patient
     */
    void addPatient(Patient aPatient)
    {
        patients.add(aPatient);
    }
    
    
    /**
     * Returns the consultant doctor that heads this team.
     *
     * @return the linked ConsultantDoctor object
     */
    ConsultantDoctor getConsultantDoctor()
    {
        return consultantDoctor;
    }
    
    
    /**
     * Returns true if this team contains the doctor, false otherwise.
     *
     * @param aDoctor a doctor
     *
     * @return true if aDoctor is linked to the receiver, false otherwise
     */
    boolean contains(Doctor aDoctor)
    {
        return doctors.contains(aDoctor);
    }
    
    
    /**
     * Returns all the doctors this team contains.
     *
     * @return a collection of all the linked Doctor objects
     */
    Collection<Doctor> getDoctors()
    {
        return doctors;
    }
    
        
    /**
     * Removes the patient from those cared for by this team.
     * The reference to aPatient is removed.
     *
     * @param aPatient a patient
     */
    void removePatient(Patient aPatient)
    {
        patients.remove(aPatient);
    }
}