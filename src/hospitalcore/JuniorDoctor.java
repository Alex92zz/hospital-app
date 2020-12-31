package hospitalcore;

import m256people.*;

/**
 * JuniorDoctor objects represent the junior doctors in the hospital.
 */
public class JuniorDoctor extends Doctor
{
    //attributes
    
    /**
     * the grade (1, 2 or 3) of the junior doctor
     */
    private Grade grade;
    
    
    //constructor
    
    /**
     * Initialises a new JuniorDoctor object with the given name and grade.
     *
     * @param aName  the name of the junior doctor
     * @param aGrade the grade of the junior doctor
     */
    JuniorDoctor(Name aName, Grade aGrade)
    {
        super(aName);
        grade = aGrade;
    }
    
    
    //public protocol
    
    /**
     * Returns the grade of this junior doctor.
     *
     * @return grade
     */
    public Grade getGrade()
    {
        return grade;
    }
    
    
    /**
     * Returns a string representation of this junior doctor's name and grade.
     *
     * @return a string object representing the receiver
     */
    public String toString()
    {
        return super.toString() + "(" + grade + ")";
    }    
}