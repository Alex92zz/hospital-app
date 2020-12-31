package hospitalcore;

import m256people.*;

/**
 * ConsultantDoctor objects represent the consultants in the hospital.
 */
public class ConsultantDoctor extends Doctor
{
    //constructor
    
    /**
     * Initialises a new ConsultantDoctor object with the given name.
     *
     * @param aName the name of the doctor
     */
    ConsultantDoctor(Name aName)
    {
        super(aName);
    }
}