/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepart2.pkg1;

/**
 *
 * @author Student
 */
public class Login extends Registration {
    
    public boolean loginUser(String name, String password) {
        return name.equals(userName) && password.equals(passWord);
    }

    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Login success";
        } else {
            return "Login failed";
        }
    }
}

