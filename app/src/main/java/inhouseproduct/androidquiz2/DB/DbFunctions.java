package inhouseproduct.androidquiz2.DB;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import inhouseproduct.androidquiz2.DB.models.User;
import inhouseproduct.androidquiz2.DB.models.UserType;

public class DbFunctions {

	private static Activity c;
	private String current;

	public DbFunctions(Activity con) {
		c = con;
	}


	public static ArrayList<User> getUserByEmailAndPassword(String email,String password) {

		ArrayList<User> userList = new ArrayList<User>();

		List<Object> userObject = DbOps.select("Email='" + email+"' AND Password='"+password+"'",
				new User());

		for (int i = 0; i < userObject.size(); i++) {

			User user = (User) userObject.get(i);

			userList.add(new User(user.getId(),user.getFirstName(), user.getLastName(),user.getMobileNumber(),user.getUserTypeId()));
		}

		return userList;

	}

	public static ArrayList<User> getUserById(int id) {

		ArrayList<User> userList = new ArrayList<User>();

		List<Object> userObject = DbOps.select("id=" + id,
				new User());

		for (int i = 0; i < userObject.size(); i++) {

			User user = (User) userObject.get(i);

			userList.add(new User(user.getId(),user.getFirstName(), user.getLastName(),user.getMobileNumber(),user.getUserTypeId()));
		}

		return userList;

	}

	public static boolean checkUserExists(String  email) {

		List<Object> userObject = DbOps.select("Email='" + email+"'",
				new User());

		if(userObject.size()>0)
			return true;
		else
			return false;

	}


	public static ArrayList<UserType> getUserType(int userTypeId) {

		ArrayList<UserType> userTypeList = new ArrayList<UserType>();

		List<Object> userObject = DbOps.select("id=" + userTypeId,
				new UserType());

		for (int i = 0; i < userObject.size(); i++) {

			UserType userType = (UserType) userObject.get(i);

			userTypeList.add(new UserType(userType.getUserType()));
		}

		return userTypeList;

	}


}
