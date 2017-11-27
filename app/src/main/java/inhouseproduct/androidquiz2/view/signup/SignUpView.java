package inhouseproduct.androidquiz2.view.signup;

import java.util.List;

public interface SignUpView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();
    void setUserTypeError();
    void setMobileNumberError();

    void setEmptyFieldsError();


    void setItems(List<String> items);

    void navigateToHome();
    void stayOnPage();
}