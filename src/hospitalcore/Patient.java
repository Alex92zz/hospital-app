package hospitalcore;

import java.util.*;
import m256people.*;
import m256date.*;

/**
 * Patient objects represent the patients currently admitted to the hospital.
 */
public class Patient implements java.io.Serializable
{
    //attributes
    
    /**
     * the person who is a patient
     */
    private Person person; 
   
    
    //links
    
    /**
     * the linked Ward object
     */
    private Ward ward; 
    
    /**
     * the linked Team object
     */
    private Team team; 
    
    /**
     * all the linked Doctor objects
     */
    private Collection<Doctor> doctors; 
    
    
    //constructor
    
    /**
     * Initialises a new Patient object with the given attribute values and age according to aDate.
     *
     * @param aName the name of the patient
     * @param aSex the sex of the patient
     * @param aDate the date of birth of the patient
     */
    Patient(Name aName, Sex aSex, M256Date aDate)
    {
        person = new Person(aName, aSex, aDate);
        doctors = new HashSet<Doctor>();
    }
    
    
    //public protocol
    
    /**
     * Returns the name of this patient.
     *
     * @return name
     */
    public Name getName()
    {
        return person.getName();
    }
    
    
    /**
     * Returns the sex of this patient.
     *
     * @return sex
     */
    public Sex getSex()
    {
        return person.getSex();
    }
    
    
    /**
     * Returns the age of this patient.
     *
     * @return age
     */
    public int getAge()
    {
        return person.getAge();
    }
    
    
    /**
     * Returns the date of birth of this patient.
     *
     * @return dateOfBirth
     */
    public M256Date getDateOfBirth()
    {
        return person.getDateOfBirth();
    }
    
    
    /**
     * Returns a string representation of this patient's name, sex and date of birth. 
     *
     * @return a string object representing the receiver
     */
    public String toString()
    {
        return person.toString();
    }
    
   
    //package protocol
    
    /**
     * Returns the ward this patient is on.
     *
     * @return the linked Ward object
     */
    Ward getWard()
    {
        return ward;
    }
    
    
    /**
     * Returns the consultant doctor responsible for this patient.
     *
     * @return the linked ConsultantDoctor object
     */
    ConsultantDoctor getConsultantDoctor()
    {
        return team.getConsultantDoctor();
    }
    
    
    /**
     * Returns the team that cares for this patient.
     *
     * @return the linked Team object
     */
    Team getTeam()
    {
        return team;
    }
    
    
    /**
     * Returns the the doctors that have treated this patient.
     *
     * @return a collection of all the linked Doctor objects
     */
    Collection<Doctor> getDoctors()
    {
        return doctors;
    }
    
    
    /**
     * Records the treatment of this patient by the doctor.
     * Ensures that a reference to aDoctor is recorded.
     *
     * @param aDoctor a doctor
     *
     * @throws IllegalArgumentException if aDoctor and the receiver are not linked to the same Team
     * object.
     */
    void recordTreatmentBy(Doctor aDoctor)
    {
        if (team.contains(aDoctor))
        {
            doctors.add(aDoctor);
        }
        else
        {
            throw new IllegalArgumentException("doctor not in correct team");
        }
    }
    
    
    /**
     * Records the admission of this patient.
     * A reference to aWard is recorded, aWard records a reference to the receiver and numberOfFreeBeds of aWard is decremented.
     * A reference to aTeam is recorded, and aTeam records a reference to the receiver. 
     *
     * @param aWard a ward
     * @param aTeam a team
     */
    void admit(Ward aWard, Team aTeam)
    {
        ward = aWard;
        ward.addPatient(this);
        team = aTeam;
        team.addPatient(this);
    }
    
    
    /**
     * Records the discharge of this patient.
     *
     * All links with the receiver are removed.
     */
    void discharge()
    {
        ward.removePatient(this);
        ward = null;
        team.removePatient(this);
        team = null;
        doctors = new HashSet<Doctor>();
    }
}