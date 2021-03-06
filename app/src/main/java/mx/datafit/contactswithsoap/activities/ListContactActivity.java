package mx.datafit.contactswithsoap.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.adapters.ContactAdapter;
import mx.datafit.contactswithsoap.async.ContactsTask;
import mx.datafit.contactswithsoap.async.DeleteTask;
import mx.datafit.contactswithsoap.async.TaskContacts;
import mx.datafit.contactswithsoap.async.TaskDelete;
import mx.datafit.contactswithsoap.models.Contact;

public class ListContactActivity extends AppCompatActivity implements TaskContacts, TaskDelete {

    private ListView listContacts;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

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

        if (id == R.id.action_new_contact) {
            Intent intent = new Intent(this, NewContactActivity.class);
            startActivity(intent);
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
            listContacts.setOnItemClickListener(details);
            listContacts.setOnItemLongClickListener(delete);
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.no_contacts), Toast.LENGTH_LONG).show();
        }
    }

    private AdapterView.OnItemClickListener details = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent  = new Intent(ListContactActivity.this, DetailsActivity.class);
            TextView name  = (TextView) view.findViewById(R.id.name);
            Contact avatar = (Contact) contactAdapter.getItem(position);
            intent.putExtra("id", (int) id);
            intent.putExtra("name", name.getText());
            intent.putExtra("avatar", avatar.getAvatar());
            startActivity(intent);
        }
    };

    private AdapterView.OnItemLongClickListener delete = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ListContactActivity.this);
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setTitle(getString(R.string.dialog_delete_title));
            alert.setMessage(getString(R.string.dialog_delete_msg));
            alert.setNegativeButton(getString(R.string.dialog_delete_btn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setPositiveButton(getString(R.string.dialog_delete_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DeleteTask deleteTask = new DeleteTask(ListContactActivity.this);
                    deleteTask.execute((int) id);
                    contactAdapter.removeItem(position);
                    contactAdapter.notifyDataSetChanged();
                }
            });
            alert.create();
            alert.show();
            return true;
        }
    };

    @Override
    public void DeleteCallback(Boolean status) {
        if (status)
            Toast.makeText(ListContactActivity.this, getString(R.string.delete_msg), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getString(R.string.delete_msg_error), Toast.LENGTH_SHORT).show();
    }
}
