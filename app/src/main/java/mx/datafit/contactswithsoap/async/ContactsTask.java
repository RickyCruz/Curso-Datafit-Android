package mx.datafit.contactswithsoap.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.models.Contact;
import mx.datafit.contactswithsoap.webservice.WebService;

public class ContactsTask extends AsyncTask<Void, Boolean, ArrayList<Contact>> {

    private ProgressDialog progress;
    private WebService webService;
    private Activity activity;

    public ContactsTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(activity);
        progress.setMessage(activity.getResources().getString(R.string.loading_contacts));
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.show();
        webService = WebService.getInstance();
    }

    @Override
    protected ArrayList<Contact> doInBackground(Void... params) {
        return webService.getAllContacts();
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        progress.dismiss();
        ((TaskContacts) this.activity).ContactsCallback(contacts);
    }

}
