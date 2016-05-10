package project.cse.anti;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.LogInCallback;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_phone)
    EditText _phoneText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signup();
            }

        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Finish the registration and return to the Login Activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;

        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_DarkDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        final String phone = _phoneText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // We create a parse user object to create a new user
        final ParseUser user = new ParseUser();
        user.setUsername(phone);
        user.setPassword(password);
        user.put("name", name);
        user.put("email", email);

        // First Query to see where the a ParseUser with the given phone number exists or not
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("phone", phone);
 
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
                        // If the user does not exits the signup.
                        signupUser(user);
                    }
                } else {
                    // Unsuccessful query
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
                        // On complete call to either onSignupSuccess remove the progressive dialog
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }



        private void navigateToHome(){
            Log.e("Home","Entered home function");
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }


    private void signupUser(ParseUser user){
        Log.e("signupUser","Entered signupUser function");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    // On a successful signup
                    navigateToHome();
                } else {
                    // On a unsuccessful signup display the error message
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });




    }



    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK,null);
        finish();
    }

    public void onSignupFailed(){
        Toast.makeText(getBaseContext(),"Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String phone = _phoneText.getText().toString();

        final String PHONE_RE = "\\d{10}";

        if(name.isEmpty()|| name.length()< 3){
            _nameText.setError("at least 3 characters");
            valid = false;
        }else{
            _nameText.setError(null);
        }

        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailText.setError("enter a valid email address");
            valid= false;
        }else{
            _emailText.setError(null);
        }

        if( password.isEmpty()|| password.length()<4|| password.length() > 10){
            _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        }else
        {
            _passwordText.setError(null);
        }
        if(phone.isEmpty()||phone.length()<10||!Pattern.matches(PHONE_RE,phone)){
            _phoneText.setError("Enter a valid 10 digit phone number excluding +91");
            valid=false;

        }
        else
        {
            _phoneText.setError(null);
        }
        return valid;
    }
}




