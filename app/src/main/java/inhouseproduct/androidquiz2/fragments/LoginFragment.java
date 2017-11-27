package inhouseproduct.androidquiz2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import inhouseproduct.androidquiz2.DB.DbOps;
import inhouseproduct.androidquiz2.DB.models.User;
import inhouseproduct.androidquiz2.DB.models.UserType;
import inhouseproduct.androidquiz2.R;
import inhouseproduct.androidquiz2.view.LoginPresenter;
import inhouseproduct.androidquiz2.view.LoginPresenterImpl;
import inhouseproduct.androidquiz2.view.LoginView;

/**
 * Created by Tallha Bin Tariq on 11/22/2017.
 */

public class LoginFragment extends Fragment implements LoginView,View.OnClickListener {

    private ProgressBar progressBar;
    private EditText etEmail;
    private EditText etPassword;
    private LoginPresenter presenter;
    private Button btnLogin;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);


        etEmail=view.findViewById(R.id.etEmail);
        etPassword=view.findViewById(R.id.etPassword);
        btnLogin=view.findViewById(R.id.btnLogin);

        progressBar=view.findViewById(R.id.progress);

        presenter=new LoginPresenterImpl(this);

        btnLogin.setOnClickListener(this);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Create Table in DB

        DbOps.createTable(new User());
        DbOps.createTable(new UserType());


    }


    //implemented for future work

    @Override
    public void showProgress() {

//        progressBar.setVisibility(View.VISIBLE);

    }



    //implemented for future work
    @Override
    public void hideProgress() {
//        progressBar.setVisibility(View.INVISIBLE);
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
    public void setEmptyFieldsError() {

        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.error_completion),Snackbar.LENGTH_SHORT).setAction("",null).show();

   }

    @Override
    public void navigateToHome(int id) {

        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.txt_userlogin),Snackbar.LENGTH_SHORT).setAction("",null).show();

        Bundle bundle=new Bundle();
        bundle.putInt("Id",id);

        Fragment fragmenttab = new HomeScreenFragment();
        fragmenttab.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmenttab).commit();

    }

    @Override
    public void stayOnPage() {


        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.userlogin_error),Snackbar.LENGTH_SHORT).setAction("",null).show();

    }

    @Override
    public void onClick(View view) {

        presenter.validateCredentials(etEmail.getText().toString(),etPassword.getText().toString());
    }
}
