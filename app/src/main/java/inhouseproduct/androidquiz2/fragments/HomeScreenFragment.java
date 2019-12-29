package inhouseproduct.androidquiz2.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import inhouseproduct.androidquiz2.DB.DbFunctions;
import inhouseproduct.androidquiz2.DB.DbOps;
import inhouseproduct.androidquiz2.DB.models.User;
import inhouseproduct.androidquiz2.DB.models.UserType;
import inhouseproduct.androidquiz2.MainActivity;
import inhouseproduct.androidquiz2.R;
import inhouseproduct.androidquiz2.SplashActivity;
import inhouseproduct.androidquiz2.utility.StringUtil;
import inhouseproduct.androidquiz2.view.LoginPresenter;
import inhouseproduct.androidquiz2.view.LoginPresenterImpl;
import inhouseproduct.androidquiz2.view.LoginView;

/**
 * Created by Tallha Bin Tariq on 11/22/2017.
 */

public class HomeScreenFragment extends Fragment implements View.OnClickListener {


    ArrayList<User> userDataList;
    TextView tvName,tvMobileNumber;

    Button btnEditMobileNo,btnUserType,btnLogout;
    int id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);

        tvName=view.findViewById(R.id.tvUsername);
        tvMobileNumber=view.findViewById(R.id.tvMobileNumber);
        btnEditMobileNo=view.findViewById(R.id.btnEdit);
        btnUserType=view.findViewById(R.id.btnUserType);
        btnLogout=view.findViewById(R.id.btnLogout);

        userDataList=new ArrayList<>();



        btnEditMobileNo.setOnClickListener(this);
        btnUserType.setOnClickListener(this);

        btnLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle recvBundle=getArguments();

        id=recvBundle.getInt("Id");
//        userDataList= DbFunctions.getUserById(id);
//
//        tvName.setText(userDataList.get(0).getFirstName()+" "+userDataList.get(0).getLastName());
//        tvMobileNumber.setText(userDataList.get(0).getMobileNumber());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

            case R.id.btnEdit:

                EditMobileNumber();


                break;



            case R.id.btnUserType:

                Toast.makeText(getActivity(),"Your account type is private",Toast.LENGTH_SHORT).show();
                break;


            case R.id.btnLogout:


                final AlertDialog alertDialog=new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.app_name)
                        .setMessage("Log out")
                        .setPositiveButton("", null)
                        .setNegativeButton("", null)
                        .show();

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {


                        alertDialog.dismiss();
                        getActivity().finish();
                        startActivity(new Intent(getActivity(),SplashActivity.class));


                    }
                }, 5000);


                break;


            default:
                break;

        }

    }


    void updateMobileNumber(int id,String mobileNo)
    {

        ContentValues content=new ContentValues();
        content.put("MobileNumber",mobileNo);

        DbOps.update(new User(),"id="+id,content);


        Snackbar.make(getActivity().findViewById(R.id.coordinateLayout),getString(R.string.txt_editnumber),Snackbar.LENGTH_SHORT).setAction("",null).show();


        userDataList= DbFunctions.getUserById(id);

        tvMobileNumber.setText(userDataList.get(0).getMobileNumber());
    }






    void EditMobileNumber() {

        //Get LayoutInflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflate the layout but ALSO store the returned view to allow us to call findViewById
        View viewDialog = inflater.inflate(R.layout.layout_edittext_mobile_dialog, null);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setView(viewDialog);

        final EditText edt = viewDialog.findViewById(R.id.etEditMobile);
        Button btnCancel=  viewDialog.findViewById(R.id.btnCancel);
        Button btnSave=  viewDialog.findViewById(R.id.btnSave);


        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));




        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edt.getText().toString().isEmpty())
                {

                    edt.setError(getString(R.string.txt_mobileno));
                }
                else
                {
                    if(!StringUtil.validateMobileNo(edt.getText().toString())) {

                        edt.setError(getString(R.string.mobilenumber_error));
                    }
                    else {
                        updateMobileNumber(id, edt.getText().toString());
                        dialog.dismiss();
                    }
                }

            }
        });


    }

}
