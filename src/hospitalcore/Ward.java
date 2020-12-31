package hospitalcore;

import java.util.*;
import m256people.*;

/**
 * Ward objects represent the wards in the hospital.
 */
public class Ward implements java.io.Serializable
{    
    //attributes
    
    /**
     * the unique name of the ward
     */
    private String name; 
    
    /**
     * whether the ward is for male or female (M or F) patients
     */
    private Sex type; 
    
    /**
     * the maximum number of patients that can be on the ward at any one time
     */
    private int capacity; 
    
    
    //links
    
    /**
     * all the linked Patient objects
     */
    private Collection<Patient> patients; 
    
    
    //constructor
    
    /**
     * Initialises a new Ward object with the given attribute values.
     *
     * @param aName the name of the ward
     * @param aSex the type of the ward
     * @param aCapacity the capacity of the ward
     */
    Ward(String aName, Sex aSex, int aCapacity)
    {
        name = aName;
        type = aSex;
        capacity = aCapacity;
        patients = new HashSet<Patient>();
    }
    
    
    //public protocol
    
    /**
     * Returns the name of this ward.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
    
    /**
     * Returns the type of this ward.
     *
     * @return type
     */
    public Sex getType()
    {
        return type;
    }
    
    
    /**
     * Returns the capacity of this ward.
     *
     * @return capacity
     */
    public int getCapacity()
    {
        return capacity;
    }
    
    
    /**
     * Returns the number of free beds in this ward.
     *
     * @return numberOfFreeBeds
     */
    public int getNumberOfFreeBeds()
    {
        return capacity - patients.size();
    }
    
    
    /**
     * Returns a string representation of this ward's name, type, capacity and patients.
     *
     * @return a String object representing the receiver
     */
    public String toString()
    {
        return name + ": " + type + ": " + capacity + ": " + patients;
    }
    
    
    //package protocol
    
    /**
     * Returns all the patients on this ward.
     *
     * @return a collection of all the linked Patient objects
     */
    Collection<Patient> getPatients()
    {
        return patients;
    }
    
    
    /**
     * Adds the patient to those on this ward. 
     * A reference to aPatient is recorded; numberOfFreeBeds is decremented.
     *
     * @param aPatient a patient
     */
    void addPatient(Patient aPatient)
    {
        patients.add(aPatient);
    }
    
    
    /**
     * Removes the patient from this ward.
     * The reference to aPatient is removed; numberOfFreeBeds is incremented.
     *
     * @param aPatient a patient 
     */
    void removePatient(Patient aPatient)
    {
        patients.remove(aPatient);
    }
}