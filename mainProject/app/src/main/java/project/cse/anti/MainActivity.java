package project.cse.anti;



import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.squareup.seismic.ShakeDetector;


import java.util.ArrayList;
import java.util.List;

import project.cse.anti.R;
import project.cse.anti.fragments.OneFragment;
import project.cse.anti.fragments.TwoFragment;



public class MainActivity extends AppCompatActivity {

    private ParseQueryAdapter<ContactsDB> ContactsAdapter;
    //private static final String PARSE_APPLICATION_ID="HrL4uZF4bbGMEyVuTyduLW4fVphkzreulp2vN0lh";
    //private static final String PARSE_CLIENT_KEY="IkbTacYj6JFSJmaWX0ATVc0lvD5FUGwics5PolPV";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ContactsDB contacts = new ContactsDB();
    LocationManager mLocationManager;
    String latitude,longitude,url;

    private SensorManager mSensorManager;
    private float mAccel;// acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast;// last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast= mAccelCurrent;
            mAccelCurrent =(float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel*0.9f+delta;
            ArrayList<String> listNames = new ArrayList<String>();
            ArrayList<String> listNumbers = new ArrayList<String>();


            if(mAccel > 25){
                url = "https://www.google.com/maps/dir/Current+Location/"+latitude+","+longitude;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");

                sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.emergencyMessage)+"\n"+url);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);



               /* try {
                    SmsManager sms = SmsManager.getDefault();
                        PendingIntent sendPI;
                        String SENT = "SMS_SENT";
                        //sendPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT), 0);
                        sms.sendTextMessage("+919742155869", null, getResources().getString(R.string.emergencyMessage) + "\n" + url, null, null);
                        Toast.makeText(getApplicationContext(), "Sms sent successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception a){
                    Toast.makeText(getApplicationContext(), "Sms failed to send:"+a.getMessage(), Toast.LENGTH_SHORT).show();
                }*/
                /*ParseQuery<ContactsDB> query = ContactsDB.getQuery();
                query.fromLocalDatastore();
                query.whereNotEqualTo("Name", " ");
                query.findInBackground(new FindCallback<ContactsDB>() {
                    @Override
                    public void done(List<ContactsDB> objects, ParseException e) {
                        if (!isFinishing()) {
                            for (int i=0;i<objects.size();i++) {
                                contacts = objects.get(i);
                                Toast toast = Toast.makeText(getApplicationContext(), contacts.getName(), Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT);
                            toast.show();


                        }
                    }

                });*/

                ParseQuery<ContactsDB> query = ContactsDB.getQuery();
                query.fromLocalDatastore();
                query.whereNotEqualTo("Message", " ");
                query.findInBackground(new FindCallback<ContactsDB>() {
                    @Override
                    public void done(List<ContactsDB> objects, ParseException e) {
                        if (!isFinishing()) {
                            for (int i = 0; i < objects.size(); i++) {
                                contacts = objects.get(i);
                                try {
                                    SmsManager sms = SmsManager.getDefault();
                                    //PendingIntent sendPI;
                                    //String SENT = "SMS_SENT";
                                    //sendPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT), 0);
                                    sms.sendTextMessage(contacts.getNumber(), null, contacts.getMessage() + "\n" + url, null, null);
                                    Toast.makeText(getApplicationContext(), "Sms sent successfully to"+contacts.getName(), Toast.LENGTH_SHORT).show();
                                   // ........ Test Code .....
                                   // Toast.makeText(getApplicationContext(), contacts.getNumber(), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(), contacts.getName(), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(), contacts.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception a){
                                    Toast.makeText(getApplicationContext(), "Sms failed to send:"+a.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                /*Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                                smsIntent.setData(Uri.parse("smsto:"));
                                smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.putExtra("address", new String("9535920887"));
                                smsIntent.putExtra("sms_body","Hi");
                                try{
                                    startActivity(smsIntent);
                                    Toast.makeText(getApplicationContext(),"Sms sent successfully",Toast.LENGTH_SHORT).show();
                                }
                                catch (android.content.ActivityNotFoundException ex){
                                    Toast.makeText(getApplicationContext(),"Sms failed",Toast.LENGTH_SHORT).show();
                                }*/


                                /*try {
                                    //Toast toast = Toast.makeText(getApplicationContext(), contacts.getMessage(), Toast.LENGTH_SHORT);
                                    //toast.show();
                                    url = "https://www.google.com/maps/dir/Current+Location/"+latitude+","+longitude;

                                   SmsManager smsManager = SmsManager.getDefault();
                                    //sendSMS("9535920887","hi");
                                    smsManager.sendTextMessage("+919742155869",null, url, null, null);

                                    Toast.makeText(getApplicationContext(), "Sms sent sucessfully", Toast.LENGTH_SHORT).show();
                                } catch (Exception a) {
                                    Toast.makeText(getApplicationContext(), "Sms failed to send" + a.getMessage(), Toast.LENGTH_SHORT).show();
                                }*/


                            }

                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT);
                            toast.show();


                        }
                    }

                });



                //triggerEvent();

            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }


    /*The accelometer should be deactivated onPause and activated onResume to save resources (CPU, Battery).*/


    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);

    }

    protected void onRestoreInstanceState(Bundle savedState){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = mLocationManager.getBestProvider(criteria,true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //requestPermissions(INITIAL_PERM,INITIAL_REQUEST);
        }
        mLocationManager.requestLocationUpdates(provider, 1, 1, mLocationListener);

        // Code for accelerating the accelerometer

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent=SensorManager.GRAVITY_EARTH;
        mAccelLast=SensorManager.GRAVITY_EARTH;



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
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //Toast.makeText(getApplicationContext(),
            //        "Location changed:Lat:" + location.getLatitude() + "Lng" + location.getLongitude(),Toast.LENGTH_SHORT).show();
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());

            //String s = longitude +"\n"+ latitude;



        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
