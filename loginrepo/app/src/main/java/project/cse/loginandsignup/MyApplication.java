package project.cse.loginandsignup;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by akshay on 19/1/16.
 */
public class MyApplication extends Application {
    private static final String PARSE_APPLICATION_ID="HrL4uZF4bbGMEyVuTyduLW4fVphkzreulp2vN0lh";
    private static final String PARSE_CLIENT_KEY="IkbTacYj6JFSJmaWX0ATVc0lvD5FUGwics5PolPV";
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(getApplicationContext(), PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }

}
