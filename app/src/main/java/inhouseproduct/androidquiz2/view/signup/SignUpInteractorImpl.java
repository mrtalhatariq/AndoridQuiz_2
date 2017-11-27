package inhouseproduct.androidquiz2.view.signup;

import android.text.TextUtils;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Spinner;

import inhouseproduct.androidquiz2.DB.DbFunctions;
import inhouseproduct.androidquiz2.DB.DbOps;
import inhouseproduct.androidquiz2.DB.models.User;
import inhouseproduct.androidquiz2.DB.models.UserType;
import inhouseproduct.androidquiz2.utility.StringUtil;
import inhouseproduct.androidquiz2.view.LoginInteractor;

public class SignUpInteractorImpl implements SignUpInteractor  {


    @Override
    public void Signup(String firstname,String lastname,String email, String password, String mobileNumber, int itempos, OnSignUpFinishedListener listener) {

        boolean error = false;

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)  && TextUtils.isEmpty(mobileNumber) && itempos==0)
        {
            listener.onEmptyFieldsError();
            error = true;
            return;
        }
        else if(TextUtils.isEmpty(email))
        {
            listener.onEmailError();
            error = true;
            return;
        }

        else if(TextUtils.isEmpty(password))
        {
            listener.onPasswordError();
            error = true;
            return;
        }
        else if(TextUtils.isEmpty(mobileNumber))
        {
            listener.onMobileNumberError();
            error = true;
            return;
        }
        else if(itempos==0)
        {
            listener.onUserTypeError();
            error = true;
            return;
        }
        else if(!StringUtil.isValidEmail(email))
        {
            listener.onEmailError();
            error = true;
            return;
        }
        else if(!StringUtil.validatePassword(password))
        {
            listener.onPasswordError();
            error = true;
            return;
        }
        else if(!StringUtil.validateMobileNo(mobileNumber))
        {
            listener.onMobileNumberError();
            error = true;
            return;
        }


        if(!error)
        {


            if(DbFunctions.checkUserExists(email)) {

                listener.onFailure();
            }
            else
            {

                long insert=DbOps.insert(new User(firstname,lastname,email,password,mobileNumber,itempos));
                Log.e("user insert",""+insert);
                listener.onSuccess();
            }

        }
    }
}
