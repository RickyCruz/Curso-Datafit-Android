package mx.datafit.contactswithsoap.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.async.ContactTask;
import mx.datafit.contactswithsoap.async.TaskContact;
import mx.datafit.contactswithsoap.models.Contact;

public class DetailsActivity extends AppCompatActivity implements TaskContact {

    private int id;
    private String fullname;

    private TextView name;
    private TextView cellphone;
    private TextView phone;
    private TextView email;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initGUI();
        setToolbar();
        loadData();
    }

    private void initGUI() {
        Bundle extras    = getIntent().getExtras();
        id               = extras.getInt("id");
        fullname         = extras.getString("name");
        String urlAvatar = extras.getString("avatar");

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(fullname);

        ImageView imageParallax = (ImageView) findViewById(R.id.image_paralax);
        Picasso.with(DetailsActivity.this)
                .load(urlAvatar)
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .into(imageParallax);

        name        = (TextView) findViewById(R.id.name);
        cellphone   = (TextView) findViewById(R.id.cellphone);
        phone       = (TextView) findViewById(R.id.phone);
        email       = (TextView) findViewById(R.id.email);
        description = (TextView) findViewById(R.id.description);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, getString(R.string.edit_msg), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        ContactTask contactTask = new ContactTask(DetailsActivity.this);
        contactTask.execute(id);
    }

    @Override
    public void ContactCallback(final Contact contact) {
        name.setText(fullname);
        cellphone.setText(contact.getCellphone());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
        description.setText(contact.getDescription());

        cellphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+" + contact.getCellphone()));
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+" + contact.getPhone()));
                startActivity(intent);
            }
        });
    }
}