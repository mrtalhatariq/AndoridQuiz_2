

package inhouseproduct.androidquiz2.view.signup;

import android.util.Log;

import java.util.List;

public class SignUpPresenterImpl implements SignUpPresenter, SignUpInteractor.OnSignUpFinishedListener,ItemsInteractor.OnFinishedListener {

    private SignUpView signUpView;
    private SignUpInteractor signupInteractor;

    ItemsInteractorImpl itemsInteractor;

    public SignUpPresenterImpl(SignUpView signupView) {
        this.signUpView = signupView;
        this.signupInteractor = new SignUpInteractorImpl() ;
        itemsInteractor=new ItemsInteractorImpl();
    }






    @Override
    public void validateUserCredential(String firstname,String lastname,String email, String password, String mobileNumber, int itempos) {
        if (signUpView != null) {
            signUpView.showProgress();
        }

        signupInteractor.Signup(firstname,lastname,email, password,mobileNumber,itempos, this);


    }

    @Override
    public void onItemClicked(int position) {

        Log.e("positon",""+position);

    }

    @Override
    public void onResume() {
        itemsInteractor.setItems(this);
    }


    @Override
    public void onDestroy() {
        signUpView = null;
    }




    @Override
    public void onEmailError() {
        if (signUpView != null) {
            signUpView.setEmailError();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (signUpView != null) {
            signUpView.setPasswordError();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onUserTypeError() {
        signUpView.setUserTypeError();
    }



    @Override
    public void onMobileNumberError() {
        signUpView.setMobileNumberError();
    }

    @Override
    public void onEmptyFieldsError() {
        if (signUpView != null) {
            signUpView.setEmptyFieldsError();
            signUpView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (signUpView != null) {
            signUpView.navigateToHome();
        }
    }

    @Override
    public void onFailure() {
        if (signUpView != null) {
            signUpView.stayOnPage();
        }
    }

    @Override
    public void onFinished(List<String> items) {


        if (signUpView != null) {
            signUpView.setItems(items);

        }
    }
}
