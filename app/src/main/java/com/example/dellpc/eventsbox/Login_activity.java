package com.example.dellpc.eventsbox;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Login_activity extends AppCompatActivity implements StudentLoginFragment.IntentListener{
private ViewPager mviewPager;
    private TabsAdapter mtabsAdapter;
    private TabLayout mtabLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mviewPager=(ViewPager)findViewById(R.id.container2);

        TabsAdapter tabsAdapter=new TabsAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(tabsAdapter);
        mtabLayout=(TabLayout)findViewById(R.id.tabs);
        mtabLayout.setupWithViewPager(mviewPager);
    }

    @Override
    public void sendDataBackToHome(String email, String uid) {
        /*Intent intent =new Intent();
        intent.putExtra("email",email);
        intent.putExtra("uid",uid);
        setResult(RESULT_OK,intent);*/
        finish();
    }

    private class TabsAdapter extends FragmentStatePagerAdapter{

        public TabsAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch(position){
               case 0:
                   return new StudentLoginFragment();
               case 1:
                   return RegistrationFragment.newInstance(null,null);

           }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "LOGIN";
                case 1:
                    return "REGISTER";

            }
            return null;
        }
    }
}
