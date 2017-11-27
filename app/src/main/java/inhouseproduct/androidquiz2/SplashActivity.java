package inhouseproduct.androidquiz2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import inhouseproduct.androidquiz2.DB.DbOps;

/**
 * Created by Talha TBT on 11/22/2017.
 */


public class SplashActivity extends Activity {


    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);


        context=this;
        activity=this;


        new DbOps(activity);





        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Activity. */


                startActivity(new Intent(context,MainActivity.class));


                finish();


            }
        }, 3000);



    }



}
