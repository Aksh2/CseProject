package project.cse.anti.fragments;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.cse.anti.ContactDatabase;
//import project.cse.anti.MyCustomAdapter;
import project.cse.anti.ContactsDB;
import project.cse.anti.GlobalClass;
import project.cse.anti.MyCustomAdapter;
import project.cse.anti.ParseAdapter;
import project.cse.anti.R;
import project.cse.anti.addContact;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;



public class OneFragment extends Fragment {
    private static final String PARSE_APPLICATION_ID="HrL4uZF4bbGMEyVuTyduLW4fVphkzreulp2vN0lh";
    private static final String PARSE_CLIENT_KEY="IkbTacYj6JFSJmaWX0ATVc0lvD5FUGwics5PolPV";

    //String[] names={"Please add contacts","Hyagriva"};
    // Layout Inflation for the parseAdapter
    private LayoutInflater inflater;
    private ParseQueryAdapter<ContactsDB> contactsAdapter;

    FloatingActionButton fab;
    public OneFragment(){
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

         View rootView = inflater.inflate(R.layout.activity_one_fragment, container, false);

        //Enabling the parse local database
        //Parse.enableLocalDatastore(getActivity());
        //Registering the parseObject class
        //ParseObject.registerSubclass(ContactsDB.class);
        // Initialising parse
        //Parse.initialize(getActivity(), PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
        // Enabling anonymous user login into parse
        //ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        //ParseACL.setDefaultACL(defaultACL, true);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        ListView lv = (ListView)rootView.findViewById(R.id.listView1);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), addContact.class);
                startActivity(intent);
            }
        });




        //ArrayList<String> names = new ArrayList<String>();

        //names.add("Please Add contacts");
        //Intent i = getActivity().getIntent();

        //names= i.getStringArrayListExtra("Nameslist");

        /*GlobalClass global = new GlobalClass();


        if(global.names!=null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.contacts_list, R.id.contactName, global.names);

            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }*/



        ParseQueryAdapter.QueryFactory<ContactsDB> factory = new ParseQueryAdapter.QueryFactory<ContactsDB>(){
            public ParseQuery<ContactsDB> create() {
                ParseQuery<ContactsDB> query = ContactsDB.getQuery();
                query.orderByAscending("Name");
                query.fromLocalDatastore();
                return query;
            }
        };

        //inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contactsAdapter = new ParseAdapter(getActivity(),factory);

        lv.setAdapter(contactsAdapter);
        //contactsAdapter.notifyDataSetChanged();
        /*try {
            Toast.makeText(getActivity(), "is the database empty" + isEmpty("ContactsDB"), Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return rootView;
    }



    /*public boolean isEmpty(String className) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.fromLocalDatastore();
        return query.count() == 0;*/
    }


    //ArrayList<ContactDatabase> contactItem = new ArrayList<ContactDatabase>();

    //MyCustomAdapter adapterContactItems = new MyCustomAdapter(this/*OneFragment.this.getActivity()*/ ,contactItem);

    //List<ContactDatabase> contactsQuery = new Select()
      //          .from(ContactDatabase.class)
        //        .orderBy("name ASC").execute();

    //adapterContactItems.addAll(contactsQuery);














