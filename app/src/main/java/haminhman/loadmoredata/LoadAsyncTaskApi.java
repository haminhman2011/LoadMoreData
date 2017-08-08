package haminhman.loadmoredata;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by haminhman on 8/8/2017.
 */

public class LoadAsyncTaskApi extends AsyncTask<Void, Void, String> {
    private Activity activity;
    private String url;
    private ProgressDialog dialog;

    public LoadAsyncTaskApi(Activity activity, String url) {
        super();
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progress dialog
        dialog = new ProgressDialog(activity);
        // Set progress dialog title
        dialog.setTitle("Dữ liệu đang được tải");
        // Set progress dialog message
        dialog.setMessage("Tải dữ liệu...");
        dialog.setIndeterminate(false);
        // Show progress dialog
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);
        Log.i("jsonStr==>", jsonStr);
        return jsonStr;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (dialog.isShowing())
            dialog.dismiss();
        Log.i("jsonStrsss==>", result);
        ((MainActivity) activity).parseJsonResponseTickedThread(result);
    }
}
