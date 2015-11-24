package mx.datafit.contactswithsoap.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.adapters.ContactAdapter;
import mx.datafit.contactswithsoap.async.ContactsTask;
import mx.datafit.contactswithsoap.async.TaskContacts;
import mx.datafit.contactswithsoap.models.Contact;

public class ListContactActivity extends AppCompatActivity implements TaskContacts {

    private ListView listContacts;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listContacts = (ListView) findViewById(R.id.list_contacts);

        ContactsTask contactsTask = new ContactsTask(ListContactActivity.this);
        contactsTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ContactsCallback(ArrayList<Contact> contacts) {
        int size = contacts.size();

        if (size > 0) {
            contactAdapter = new ContactAdapter(ListContactActivity.this, contacts);
            listContacts.setAdapter(contactAdapter);
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.no_contacts), Toast.LENGTH_LONG).show();
        }
    }
}