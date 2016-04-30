package project.cse.anti;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by akshay on 29/3/16.
 */
public class ParseApplication extends Application {
    private static final String PARSE_APPLICATION_ID="HrL4uZF4bbGMEyVuTyduLW4fVphkzreulp2vN0lh";
    private static final String PARSE_CLIENT_KEY="IkbTacYj6JFSJmaWX0ATVc0lvD5FUGwics5PolPV";

    @Override
    public void onCreate()
    {
        super.onCreate();
    //Enabling the parse local database
        Parse.enableLocalDatastore(getApplicationContext());
        //Registering the parseObject class
        ParseObject.registerSubclass(ContactsDB.class);
        // Initialising parse
        Parse.initialize(getApplicationContext(), PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
        // Enabling anonymous user login into parse
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

    }

}
