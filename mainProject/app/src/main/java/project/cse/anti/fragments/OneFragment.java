package project.cse.anti.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import project.cse.anti.R;
import project.cse.anti.addContact;
import android.support.v7.app.AppCompatActivity;


public class OneFragment extends Fragment {

    FloatingActionButton fab;
    public OneFragment(){
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

         View rootView = inflater.inflate(R.layout.activity_one_fragment, container, false);
       fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), addContact.class);
                startActivity(intent);
            }
        });
        return rootView;

    }



}




