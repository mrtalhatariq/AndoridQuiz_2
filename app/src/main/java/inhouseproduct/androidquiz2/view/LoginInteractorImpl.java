package inhouseproduct.androidquiz2.view;

import android.os.Handler;
import android.text.TextUtils;

import java.util.ArrayList;

import inhouseproduct.androidquiz2.DB.DbFunctions;
import inhouseproduct.androidquiz2.DB.models.User;
import inhouseproduct.androidquiz2.utility.StringUtil;

public class LoginInteractorImpl implements LoginInteractor {



    ArrayList<User> userList=new ArrayList<>();

    @Override
    public void login(final String email, final String password, final OnLoginFinishedListener listener) {

                boolean error = false;


                if(TextUtils.isEmpty(password) && TextUtils.isEmpty(email))
                {
                    listener.onEmptyFieldsError();
                    error = true;
                    return;
                }
                else if (TextUtils.isEmpty(email)){
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



                if (!error){

                    userList= DbFunctions.getUserByEmailAndPassword(email,password);
                    if(userList.size()>0) {
                        listener.onSuccess(userList.get(0).getId());
                    }
                    else
                    {
                        listener.onFailure();
                    }
                }

    }
}
