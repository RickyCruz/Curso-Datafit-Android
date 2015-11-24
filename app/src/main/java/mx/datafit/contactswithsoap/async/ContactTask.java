package mx.datafit.contactswithsoap.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.models.Contact;
import mx.datafit.contactswithsoap.webservice.WebService;

public class ContactTask extends AsyncTask<Integer, Boolean, Contact> {

    private ProgressDialog progress;
    private WebService webService;
    private Activity activity;

    public ContactTask(Activity activity) {
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
    protected Contact doInBackground(Integer... params) {
        Contact contact = webService.getById(params[0]);
        return (contact != null) ? contact : null;
    }

    @Override
    protected void onPostExecute(Contact contact) {
        super.onPostExecute(contact);
        progress.dismiss();
        ((TaskContact) this.activity).ContactCallback(contact);
    }

}
