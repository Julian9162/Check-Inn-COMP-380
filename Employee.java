package checkinn;

// Class for employee entity
public class Employee {

    // Global variables
    private String first, last; // First name and last name of employee
    private int employeeID; // Identification Number of employee
    private String password;

    // Employee constructor that creates employee object based off existing exmployee data
    Employee(int employeeID, String last, String first, String password) {

        // Saves first and last name + employee email + employee identification number
        this.employeeID = employeeID;
        this.last = last;
        this.first = first;
        this.password = password;
        
    } // End Employee(first, last, email, employeeID) constructor

    // getFirstName() returns first name of employee
    public String getFirstName() {
        return first;
    } // End getFirstName()

    // getLastName() returns first name of employee
    public String getLastName() {
        return last;
    } // End getLastName()

    // getEmployeeID() returns identification number of employee
    public int getEmployeeID() {
        return employeeID;
    } // End getEmployeeID()

    // getPassword() returns the password of the employee account
    public String getPassword() {
        return password;
    } // End getPassword()

    public boolean verify(String ID, String PW) {

        boolean IDVerified = false, PWVerified = false;

        if (ID.compareTo("" + employeeID) == 0) IDVerified = true;

        if (PW.compareTo("" + password) == 0) PWVerified = true;

        if (IDVerified = PWVerified) return true;

        else return false;

    }

} // End Employee class