package inhouseproduct.androidquiz2.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import inhouseproduct.androidquiz2.R;
import inhouseproduct.androidquiz2.view.signup.SignUpPresenterImpl;
import inhouseproduct.androidquiz2.view.signup.SignUpView;

/**
 * Created by Tallha Bin Tariq on 11/22/2017.
 */

public class SignUpFragment extends Fragment implements SignUpView,View.OnClickListener,AdapterView.OnItemSelectedListener{

    SignUpPresenterImpl signUpPresenterImpl;
    int position;

    Spinner spinnerUserType;
    EditText etEmail,etPassword,etMobileNumber,etFirstname,etLastname;

    Button btnSignUp;
    List<String> itemsList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup , container, false);



        spinnerUserType=view.findViewById(R.id.spinnerUserType);

        etEmail=view.findViewById(R.id.etEmail_Signup);
        etPassword=view.findViewById(R.id.etPassword_Signup);
        etMobileNumber=view.findViewById(R.id.etMobileNo_Signup);
        etFirstname=view.findViewById(R.id.etFirstnameSignup);
        etLastname=view.findViewById(R.id.etLastname_Signup);
        btnSignUp=view.findViewById(R.id.btSignUp);


        signUpPresenterImpl=new SignUpPresenterImpl(this);


        spinnerUserType.setOnItemSelectedListener(this);
        btnSignUp.setOnClickListener(this);




        return view;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setEmailError() {

        etEmail.setError(getString(R.string.email_error));
    }

    @Override
    public void setPasswordError() {

        etPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void setUserTypeError() {

        TextView errorText = (TextView) spinnerUserType.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText(getString(R.string.error_usertype));

    }


    @Override
    public void setMobileNumberError() {

        etMobileNumber.setError(getString(R.string.mobilenumber_error));
    }

    @Override
    public void setEmptyFieldsError() {

        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.error_completion),Snackbar.LENGTH_SHORT).setAction("",null).show();

    }

    @Override
    public void setItems(List<String> items) {

        spinnerUserType.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void navigateToHome() {


        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.txt_usercreated),Snackbar.LENGTH_SHORT).setAction("",null).show();

    }

    @Override
    public void stayOnPage() {
        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.txt_user_exists),Snackbar.LENGTH_SHORT).setAction("",null).show();


    }

    @Override
    public void onClick(View view) {

        signUpPresenterImpl.validateUserCredential(etFirstname.getText().toString(),etLastname.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString(),etMobileNumber.getText().toString(),position);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        signUpPresenterImpl.onItemClicked(i);
        position=i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onResume() {
        super.onResume();

        signUpPresenterImpl.onResume();
    }
}
