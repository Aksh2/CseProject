package project.cse.anti;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;


import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import project.cse.anti.fragments.OneFragment;


public class addContact extends AppCompatActivity {
    private Toolbar toolbar;
    private Switch messageSwitch;
    private TextView messageLabel;
    private Button saveButton;
    private EditText mName,mPhone,mMessage;
    String[] names={};
    private boolean defaultMessage=true;
    ArrayList<String> listNames = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
//        ActiveAndroid.initialize(this);

        // To create the toolbar
        Toolbar myToolBar = (Toolbar) findViewById(R.id.addContactToolBar);
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Adding the heading to Activity
        final Activity activity = this;
        activity.setTitle("Add Contacts");

        // Switch action for default message and text field appearence
        messageSwitch = (Switch) findViewById(R.id.messageSwitch);
        messageSwitch.setChecked(true);

        final EditText message = (EditText) findViewById(R.id.editMessage);
        messageLabel = (TextView)findViewById(R.id.emergencyMessage);



        messageSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){
                    message.setVisibility(View.VISIBLE);
                    messageLabel.setVisibility(View.GONE);
                    defaultMessage=false;

                }
                else
                {
                    message.setVisibility(View.GONE);
                    messageLabel.setVisibility(View.VISIBLE);
                    defaultMessage=true;

                }
            }


        });

        // Inflating the edit text views
        mName=(EditText)findViewById(R.id.editName);
        mPhone=(EditText)findViewById(R.id.editPhone);
        mMessage=(EditText)findViewById(R.id.editMessage);
        saveButton=(Button)findViewById(R.id.button);
        // Adding the listener to the button when the button is pressed

        //ListView lv = (ListView)findViewById(R.id.listView1);
        //final ArrayList<String> names = new ArrayList<String>();
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(addContact.this, R.layout.contacts_list, R.id.contactName, names);
        //lv.setAdapter(adapter);





        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    ContactsDB contacts = new ContactsDB();
                    contacts.setName(mName.getText().toString());
                    if (defaultMessage) {
                        String Message = getResources().getString(R.string.emergencyMessage);
                        contacts.setMessage(Message);
                    } else {
                        contacts.setMessage(mMessage.getText().toString());
                    }
                    contacts.setNumber(mPhone.getText().toString());
                    Toast.makeText(getApplicationContext(), contacts.getMessage(), Toast.LENGTH_LONG).show();
                    contacts.pinInBackground("SaveContacts", new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (isFinishing()) {
                                return;
                            }
                            if (e == null) {
                                setResult(Activity.RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Saving:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                    //listNames.add(mName.getText().toString());
                    //adapter.notifyDataSetChanged();
                    //Intent intent = new Intent(getApplicationContext(), OneFragment.getA);

                    //String[]names = new String[listNames.size()];
                    //listNames.toArray(names);

                    //intent.putExtra("Nameslist", listNames);
                    //startActivity(intent);

                /*
                ContactDatabase contact = new ContactDatabase();
                contact.name=mName.getText().toString();
                contact.phone=Integer.parseInt(mPhone.getText().toString());
                // If switch is On assign the default text or else assign the text from the text field
                if(messageSwitch.isChecked()){
                    contact.message=messageLabel.getText().toString();
                }
                else
                {
                    contact.message=message.getText().toString();
                }
                contact.save();*/


                }
            }
        });




        //message.setText("I am in an emergency. I need help as soon as possible. Please find my location:");

        /*message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //message.setText("I am in an emergency. I need help as soon as possible. Please find my location:");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredMessage = message.getText().toString();
                message.setText(enteredMessage);

            }
        });*/
    }

    public boolean validate(){
        boolean valid = true;
        final String PHONE_RE = "\\d{10}";
        if(mPhone.getText().toString().isEmpty()||mPhone.getText().toString().length()<10||!Pattern.matches(PHONE_RE, mPhone.getText().toString())){
            mPhone.setError("Enter a valid 10 digit phone number excluding +91");
            valid= false;
        }
        else
        {
            mPhone.setError(null);
        }
        if(mName.getText().toString().isEmpty()){
            mName.setError("Please Enter a valid name");
            valid= false;
        }
        else
        {
            mName.setError(null);
        }


        return valid;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }

}


