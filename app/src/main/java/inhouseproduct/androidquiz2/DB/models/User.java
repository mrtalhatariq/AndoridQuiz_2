package inhouseproduct.androidquiz2.DB.models;

import java.io.Serializable;

/**
 * Created by Tallha Bin Tariq on 11/25/2017.
 */

public class User implements Serializable {

    public int id;
    public String FirstName;
    public String LastName;
    public String Email;
    public String Password;
    public String MobileNumber;
    public int UserTypeId;


    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String mobileNumber, int userType) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        MobileNumber = mobileNumber;
        UserTypeId = userType;
    }


    public User(int id,String firstName, String lastName,String mobileNumber, int userType) {
        this.id=id;

        FirstName = firstName;
        LastName = lastName;
        MobileNumber = mobileNumber;
        UserTypeId = userType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public int getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        UserTypeId = userTypeId;
    }
}
