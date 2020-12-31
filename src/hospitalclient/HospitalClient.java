package hospitalclient;

import java.util.*;
import java.io.*;
import java.text.*;
import hospitalcore.*;
import m256people.*;
import m256date.*;


public class HospitalClient
{
    public static void main(String[] args)
    {
        /**
         * Part 1 -- create two HospCoord objects.
         * Check they are different
         */
        HospCoord hospital1 = HospCoord.getHospital();
        HospCoord hospital2 = HospCoord.getHospital();
        
        if(hospital1 == hospital2)
        {
            System.out.println("hospital1 and hospital2 reference the same object.");
        }
        else
        {
            System.out.println("hospital1 and hospital2 reference different objects.");
        }
        
        System.out.println("Initial details of hospital1:");
        System.out.println(hospital1);
        System.out.println("Initial details of hospital2:");
        System.out.println(hospital2);
        
        
        /**
         * Part 2 -- Print patient details of each object
         */
        System.out.println("Initial patient details of hospital1:");
        System.out.println(hospital1.getPatients());
        System.out.println("Initial patient details of hospital2:");
        System.out.println(hospital2.getPatients());
        
        
        /**
         * Part 3 -- Add a new patient to the first hospital 
         */
        Name theName = new Name("Ms", "Bet", "Lynch"); //create a patient name
        M256Date theBirthDate = null; //create a birth date
        try
        {
            theBirthDate = new M256Date("23/05/78");
        }
        catch(ParseException e)
        {
            System.exit(1);
        }
        
        // The following code gets a reference to a Team object by simply
        // calling on hospital1 to provide a collection of all
        // the Team objects,
        // converting to an array and picking the first element.
        Team theTeam = (Team) hospital1.getTeams().toArray()[0];
        System.out.println("Admitting a patient to hospital1...");
        
        // Admit the patient by calling on hospital1's admit(aName, aSex, aDate, aTeam) method.
        hospital1.admit(theName, Sex.F, theBirthDate, theTeam);
        
        
        /**
         * Part 4 -- Print patient details of each object 
         */
        System.out.println("Final patient details of hospital1:");
        System.out.println(hospital1.getPatients());
        System.out.println("Final patient details of hospital2:");
        System.out.println(hospital2.getPatients());
        
        
        /**
         * Part 5 --  Save the hospital details
         */
        hospital1.save();
        hospital2.save();        
    }    
}