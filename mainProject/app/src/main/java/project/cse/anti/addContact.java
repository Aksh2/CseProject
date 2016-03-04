package project.cse.anti;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class addContact extends AppCompatActivity {
    private Toolbar toolbar;
    private Switch messageSwitch;
    private TextView messageLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.addContactToolBar);
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Activity activity = this;
        activity.setTitle("Add Contacts");


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

                }
                else
                {
                    message.setVisibility(View.GONE);
                    messageLabel.setVisibility(View.VISIBLE);
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

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }

}
