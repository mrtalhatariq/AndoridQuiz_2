package inhouseproduct.androidquiz2.view;

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void setEmptyFieldsError();

    void navigateToHome(int id);

    void stayOnPage();
}