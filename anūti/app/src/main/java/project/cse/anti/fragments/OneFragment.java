package project.cse.anti.fragments;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import project.cse.anti.MyCustomAdapter;
import project.cse.anti.ContactsDB;
import project.cse.anti.ParseAdapter;
import project.cse.anti.R;
import project.cse.anti.addContact;

import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


public class OneFragment extends Fragment {


    // Layout Inflation for the parseAdapter
    private LayoutInflater inflater;
    private ParseQueryAdapter<ContactsDB> contactsAdapter;
    int EDIT_ACTIVITY_CODE=10;

    FloatingActionButton fab;
    ListView lv;
    public OneFragment(){
        //Required empty constructor
    }

    

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

         View rootView = inflater.inflate(R.layout.activity_one_fragment, container, false);


        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        lv = (ListView)rootView.findViewById(R.id.listView1);



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
       /* lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ContactsDB contacts = contactsAdapter.getItem(position);
                modifyContacts(contacts);
            }
        });*/
        //contactsAdapter.notifyDataSetChanged();
        /*try {
            Toast.makeText(getActivity(), "is the database empty" + isEmpty("ContactsDB"), Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return rootView;
    }

    /*private void modifyContacts(ContactsDB contactsDB){
        Intent i = new Intent(getActivity().NewContacts.class);
        i.putExtra("ID",contactsDB.getUuidString());
        startActivityForResult(i,EDIT_ACTIVITY_CODE);
    }*/

   public void onResume(){
        super.onResume();

            if(contactsAdapter == null)
                lv.setAdapter(contactsAdapter);
            else
               contactsAdapter.loadObjects();
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














