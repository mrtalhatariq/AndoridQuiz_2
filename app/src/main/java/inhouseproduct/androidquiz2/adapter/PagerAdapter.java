package inhouseproduct.androidquiz2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import inhouseproduct.androidquiz2.fragments.LoginFragment;
import inhouseproduct.androidquiz2.fragments.SignUpFragment;

/**
 * Created by Talha TBT on 11/22/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:

                frag=new LoginFragment();
//
                break;
            case 1:
                frag=new SignUpFragment();
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="";
                break;
            case 1:
                title="";
                break;


        }

        return title;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);

        return fragment;
    }
}

