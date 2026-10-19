/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepart2.pkg1;

/**
 *
 * @author Student
 */
public class Registration {
    String userName;
    String passWord;
    String cellPhone;

    /**
     * A valid username must contain an underscore and be no more than
     * five characters long.
     */
    public boolean checkUserName(String username) { // start of username method
        return username.contains("_")
                && username.length() <= 5;
    } // end of username method

    /**
     * A valid password must be at least 8 characters long and contain
     * at least one capital letter, one number and one special character.
     */
    public boolean checkPasswordComplexity(String password) { // start of password method
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[^a-zA-Z0-9].*");
    } // end of password method

    /**
     * A valid South African cellphone number is either in the
     * international format (+27 followed by 9 digits) or the local
     * format (0 followed by 9 digits).
     *
     *
     */
    public boolean checkCellPhoneNumber(String cellphone) { // start of cellphone method
        return cellphone.matches("^\\+27[0-9]{9}$")
                || cellphone.matches("^0[0-9]{9}$");
    } // end of cellphone method

    public String registerUser(String username,
            String password,
            String cellphone) {

        if (checkUserName(username)
                && checkPasswordComplexity(password)
                && checkCellPhoneNumber(cellphone)) {

            this.userName = username;
            this.passWord = password;
            this.cellPhone = cellphone;

            return "Registration was a success";
        } else {
            // FIX: corrected spelling ("unsucessfull" -> "unsuccessful")
            return "Registration was unsuccessful";
        }
    }

    public boolean registerStatus(String username, String password, String cellphone) {
        return checkUserName(username)
                && checkPasswordComplexity(password)
                && checkCellPhoneNumber(cellphone);
    }

} // end of class
