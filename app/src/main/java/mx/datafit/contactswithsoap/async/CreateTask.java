package mx.datafit.contactswithsoap.async;

import android.app.Activity;
import android.os.AsyncTask;

import mx.datafit.contactswithsoap.webservice.WebService;

public class CreateTask extends AsyncTask<String, Boolean, Boolean> {

    private WebService webService;
    private Activity activity;

    public CreateTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        webService = WebService.getInstance();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        int status = webService.create(params[0], params[1], params[2], params[3], params[4], params[5]);
        return (status > 0);
    }

    @Override
    protected void onPostExecute(Boolean status) {
        super.onPostExecute(status);
        ((TaskCreate) this.activity).CreateCallback(status);
    }

}