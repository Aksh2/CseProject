package project.cse.anti;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.cse.anti.fragments.OneFragment;

/**
 * Created by akshay on 13/3/16.
 */
public class MyCustomAdapter extends ArrayAdapter<String> {
    private Context mContext;
    ArrayList<String> mContacts;


    public MyCustomAdapter(Context context,ArrayList<String> names/*ArrayList<ContactDatabase> contactNames*/){
        super(context, R.layout.contacts_list, names);
        this.mContext=context;
        this.mContacts=names;
    }

    public class ViewHolder{
        TextView namesv;
    }

    public View getView(int position,View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.contacts_list,null);
        }

       // List contacts = mContacts.getAll(Position);



        final ViewHolder holder = new ViewHolder();

        holder.namesv=(TextView) convertView.findViewById(R.id.contactName);
        holder.namesv.setText(mContacts.get(position));

        return  convertView;
    }


}

