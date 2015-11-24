package mx.datafit.contactswithsoap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.async.CreateTask;
import mx.datafit.contactswithsoap.async.TaskCreate;

public class NewContactActivity extends AppCompatActivity implements TaskCreate {

    private EditText iName;
    private EditText iLastname;
    private EditText iCellphone;
    private EditText iPhone;
    private EditText iEmail;
    private EditText iDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initGUI();
    }

    private void initGUI() {
        iName      = (EditText) findViewById(R.id.input_name);
        iLastname  = (EditText) findViewById(R.id.input_lastname);
        iCellphone = (EditText) findViewById(R.id.input_cellphone);
        iPhone     = (EditText) findViewById(R.id.input_phone);
        iEmail     = (EditText) findViewById(R.id.input_email);
        iDesc      = (EditText) findViewById(R.id.input_desc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            String name      = iName.getText().toString();
            String lastname  = iLastname.getText().toString();
            String cellphone = iCellphone.getText().toString();
            String phone     = iPhone.getText().toString();
            String email     = iEmail.getText().toString();
            String desc      = iDesc.getText().toString();

            CreateTask createTask = new CreateTask(NewContactActivity.this);
            createTask.execute(name, lastname, cellphone, phone, email, desc);

            startActivity(new Intent(NewContactActivity.this, ListContactActivity.class));

            return true;
        }

        if (id == R.id.action_cancel) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void CreateCallback(Boolean status) {
        if (status)
            Toast.makeText(this, getString(R.string.create_msg), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getString(R.string.create_msg_error), Toast.LENGTH_SHORT).show();
    }
}