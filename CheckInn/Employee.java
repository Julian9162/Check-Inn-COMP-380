package CheckInn;

/**
 * Employee --- Employee entity class used to store information about an employee and verify their credentials.
 * @author      Julian Aguiar
 */
public class Employee {

    // Global variables
    private String first, last; // First name and last name of employee
    private int employeeID; // Identification Number of employee
    private String password; // Employee's password

    /**
     * Saves information from employee record into an employee object.
     * @param employeeID  Identification number of the employee
     * @param last        Last name of the employee
     * @param first       First name of the employee
     * @param password    The password for the employee's login
     * @exception         none
     * @return            no return value
     */
    Employee(int employeeID, String last, String first, String password) {

        // Saves first and last name + employee email + employee identification number
        this.employeeID = employeeID;
        this.last = last;
        this.first = first;
        this.password = password;
        
    } // End Employee(first, last, email, employeeID) constructor
    
    /**
     * Checks if user login credentials match an employees credentials.
     * @param ID   Identification number to be checked
     * @param PW   Password to be checked
     * @exception  none
     * @return     True if credentials match. False if not.
     */    
    public boolean verify(String ID, String PW) {

        @SuppressWarnings("unused")

        // Boolean values store true if there is match in employee credentials
        boolean IDVerified = false, PWVerified = false;

        // Compare inputted ID number to employee's ID number
        if (ID.compareTo("" + employeeID) == 0) IDVerified = true;

        // Compare inputted password to employee's password
        if (PW.compareTo("" + password) == 0) PWVerified = true;

        // Return true if credentials match 
        if (IDVerified = PWVerified) return true;

        // Return false otherwise
        else return false;

    } // End verify(ID, PW)
    /**
     * Returns the first name of the employee.
     * @exception  none
     * @return     First name of the employee
     */
    public String getFirstName() {
        return first;
    } // End getFirstName()

    /**
     * Returns the last name of the employee.
     * @exception  none
     * @return     Last name of the employee
     */
    public String getLastName() {
        return last;
    } // End getLastName()

    /**
     * Returns the ID number of the employee.
     * @exception  none
     * @return     ID number of the employee
     */
    public int getEmployeeID() {
        return employeeID;
    } // End getEmployeeID()

    /**
     * Returns the password of the employee.
     * @exception  none
     * @return     Password of the employee
     */
    public String getPassword() {
        return password;
    } // End getPassword()

} // End Employee class
