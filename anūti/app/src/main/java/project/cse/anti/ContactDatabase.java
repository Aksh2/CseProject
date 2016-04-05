package project.cse.anti;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by akshay on 5/3/16.
 */

// Creating the data base schema
@Table(name = "Contacts")
public class ContactDatabase extends Model {


    //@Column(name="remote_id", index = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    //public long remoteId;
    @Column(name = "Name")
    public String name;

    @Column(name = "Phone")
    public int phone;

    @Column(name = "Message")
    public String message;

    public ContactDatabase() {
        super();
    }

    public ContactDatabase(String name, int phone, String message) {
        super();
        this.name = name;
        this.phone=phone;
        this.message = message;
    }

    public static List<ContactDatabase> getAll(){
        // Querying all the Names from the database

        return new Select()
                .from(ContactDatabase.class)
                .orderBy("name ASC")
                .execute();
    }
}

