package haminhman.loadmoredata;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haminhman on 8/8/2017.
 */

public class InfiniteScrollAdapter<T> extends ArrayAdapter<Model> {

    private Activity context;
    private ArrayList<Model> values;
    private int count;
    private int stepNumber;
    private int startCount;

    /**
     *
     * @param context
     * @param values
     * @param startCount the initial number of views to show
     * @param stepNumber the number of additional views to show with each expansion
     */
    public InfiniteScrollAdapter(Activity context, ArrayList<Model> values, int startCount, int stepNumber) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
        this.startCount = Math.min(startCount, values.size()); //don't try to show more views than we have
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_layout, null);
            view.setTag(view.findViewById(R.id.textView));
        }else{
            view = convertView;
        }
        Model data = getItem(position);
        TextView textView = (TextView) view.getTag();
        textView.setText(data.getName());

        return view;
    }

    /**
     * Show more views, or the bottom
     * @return true if the entire data set is being displayed, false otherwise
     */
    public boolean showMore(){
        if(count == values.size()) {
            return true;
        }else{
            count = Math.min(count + stepNumber, values.size()); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
            return endReached();
        }
    }

    /**
     * @return true if then entire data set is being displayed, false otherwise
     */
    public boolean endReached(){
        return count == values.size();
    }

    /**
     * Sets the ListView back to its initial count number
     */
    public void reset(){
        count = startCount;
        notifyDataSetChanged();
    }

}
