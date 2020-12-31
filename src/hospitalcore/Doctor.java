package hospitalcore;

import m256people.*;

/**
 * An abstract superclass for all classes representing doctors.
 */
public abstract class Doctor implements java.io.Serializable
{
    //attributes
    
    /** 
     * the person who is a doctor
     */    
    private Person person;
    
    
    //constructor
    
    /**
     * Initialises a new instance of a concrete subclass of Doctor with the given name.
     *
     * @param aName the name of the doctor
     */
    Doctor(Name aName)
    {
        person = new Person(aName);
    }
    
    
    //public protocol
    
    /**
     * Returns the name of this doctor.
     *
     * @return name
     */
    public Name getName()
    {
        return person.getName();
    }
    
    
    /**
     * Returns a string representation of this doctor's name.
     *
     * @return a string object representing the receiver
     */
    public String toString()
    {
        return person.getName().toString();
    }    
}