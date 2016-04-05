package project.cse.anti;



import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;


import java.util.ArrayList;
import java.util.List;

import project.cse.anti.R;
import project.cse.anti.fragments.OneFragment;
import project.cse.anti.fragments.TwoFragment;



public class MainActivity extends AppCompatActivity {

    private ParseQueryAdapter<ContactsDB> ContactsAdapter;
    private static final String PARSE_APPLICATION_ID="HrL4uZF4bbGMEyVuTyduLW4fVphkzreulp2vN0lh";
    private static final String PARSE_CLIENT_KEY="IkbTacYj6JFSJmaWX0ATVc0lvD5FUGwics5PolPV";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    GlobalClass global = new GlobalClass();
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        //Enabling the parse local database
//        Parse.enableLocalDatastore(getApplicationContext());
        //Registering the parseObject class
  //      ParseObject.registerSubclass(ContactsDB.class);
        // Initialising parse
    //    Parse.initialize(getApplicationContext(), PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
        // Enabling anonymous user login into parse
      //  ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        //ParseACL.setDefaultACL(defaultACL,true);
        outstate.putStringArrayList("namesList", global.names);
    }

    protected void onRestoreInstanceState(Bundle savedState){
        global.names = savedState.getStringArrayList("namesList");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
          getSupportActionBar().setIcon(R.drawable.logo);
          getSupportActionBar().setDisplayShowTitleEnabled(false);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new OneFragment(),"Contacts");
        adapter.addFragment(new TwoFragment(),"Location");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }


    private void setSupportActionBar(Toolbar toolbar) {

    }
    }
}
