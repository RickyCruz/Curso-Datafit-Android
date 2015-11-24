package mx.datafit.contactswithsoap.async;

import android.app.Activity;
import android.os.AsyncTask;

import mx.datafit.contactswithsoap.webservice.WebService;

public class DeleteTask extends AsyncTask<Integer, Boolean, Boolean> {

    private WebService webService;
    private Activity activity;

    public DeleteTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        webService = WebService.getInstance();
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        int status = webService.delete(params[0]);
        return (status == 1);
    }

    @Override
    protected void onPostExecute(Boolean status) {
        super.onPostExecute(status);
        ((TaskDelete) this.activity).DeleteCallback(status);
    }

}