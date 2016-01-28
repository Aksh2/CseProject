package project.cse.loginandsignup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;


import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static final String PARSE_APPLICATION_ID="HrL4uZF4bbGMEyVuTyduLW4fVphkzreulp2vN0lh";
    private static final String PARSE_CLIENT_KEY="IkbTacYj6JFSJmaWX0ATVc0lvD5FUGwics5PolPV";

    @Bind(R.id.input_phone) EditText _phoneText;
    @Bind(R.id.input_password)EditText _passwordText;
    @Bind(R.id.btn_login)Button _loginButton;
    @Bind(R.id.link_signup)TextView _signupLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        ButterKnife.bind(this);



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivityForResult(intent,REQUEST_SIGNUP);
            }
        });


    }




    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_DarkDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating....");
        progressDialog.show();

        final String phone = _phoneText.getText().toString();
        final String password = _passwordText.getText().toString();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", phone);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                Log.e("done", "Entered done function");
                if (e == null) {
                    // Successful query

                    // If a user already exists then login
                    if (parseUsers.size() > 0) {
                        loginUser(phone, password);
                    } else {
                        Toast.makeText(getBaseContext(),"This User does not Exist. Please Signup",Toast.LENGTH_LONG).show();
                        // If the user does not exits the signup.
                        Intent in = new Intent(getApplicationContext(), SignupActivity.class);
                        startActivity(in);
                    }
                } else {
                    // Unsuccessful query
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });




        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        OnLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void loginUser(String username, String password) {
        Log.e("loginuser", "Entered loginUser function");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // On a Successful login navigate to the home
                    navigateToHome();

                } else {
                    // Unsuccessful login
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }
    private void navigateToHome(){
        Log.e("Home","Entered home function");
        //Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);

        // Go to the Main Activity

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_SIGNUP){
            if(resultCode == RESULT_OK){

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();

            }
        }
    }

    @Override
    public void onBackPressed(){
        // disable goinf back to the MainActivity

        moveTaskToBack(true);
    }


    private void OnLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;

        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        final String PHONE_RE = "\\d{10}";



        if(phone.isEmpty()||phone.length()<10||!Pattern.matches(PHONE_RE, phone)){
            _phoneText.setError("Enter a valid 10 digit phone number excluding +91");
            valid=false;

        }
        else
        {
            _phoneText.setError(null);
        }

        if(password.isEmpty()||password.length()<4|| password.length()>10){
            _passwordText.setError("Password Should be between 4 to 10 alphanumeric characters");
            valid = false;
        }
        else{
            _passwordText.setError(null);
        }
        return valid;
    }


}
