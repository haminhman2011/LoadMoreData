package haminhman.loadmoredata;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnScrollListener {

    private ListView listView;
    private ProgressBar progressBar;
    private InfiniteScrollAdapter<Model> adapter;
    private Handler mHandler;
    private ArrayList<Model> dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        View footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);

        listView = (ListView) findViewById(R.id.listView);
        listView.addFooterView(footer);

        String[] vals = TribonacciCalculator.getTribonacci();
        dataModel = new ArrayList<>();
        adapter = new InfiniteScrollAdapter<>(this, dataModel, 20, 10);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this); //listen for a scroll movement to the bottom
        progressBar.setVisibility((5 < vals.length)? View.VISIBLE : View.GONE);
        getDataTickedThreadUrl("http://demo.cloudteam.vn:8080/test_service_android/index.php");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_reset:
                adapter.reset(); //reset the adapter to its initial configuration
                listView.setSelection(0); //go to the top
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && !adapter.endReached() && !hasCallback){ //check if we've reached the bottom
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }

    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            boolean noMoreToShow = adapter.showMore(); //show more views and find out if
            progressBar.setVisibility(noMoreToShow? View.GONE : View.VISIBLE);
            hasCallback = false;
        }
    };

    // calling asynctask to get json data from internet
    private void getDataTickedThreadUrl(String url) {
        new LoadAsyncTaskApi(this, url).execute();
    }

    public void parseJsonResponseTickedThread(String result) {

        try {
//            JSONObject json = new JSONObject(result);
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                Model data = new Model();
                data.setName(!jObject.getString("name").equals("null") ? jObject.getString("name") : "");
                dataModel.add(data);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.i("A", "Json parsing error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
