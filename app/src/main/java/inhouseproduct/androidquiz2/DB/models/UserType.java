package inhouseproduct.androidquiz2.DB.models;

import java.io.Serializable;

/**
 * Created by Tallha Bin Tariq on 11/25/2017.
 */

public class UserType implements Serializable {

    public int id;
    public String userType;


    public UserType() {
    }

    public UserType( String userType) {
        this.userType = userType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
