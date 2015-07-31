package pt.iscte_iul.socialsports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cserrao on 19/07/15.
 */
public class FilterAdapter extends ArrayAdapter<Filter> {
    private final Context context;
    private final ArrayList<Filter> itemsArrayList;

    public FilterAdapter(Context context, ArrayList<Filter> itemsArrayList) {
        super(context, R.layout.filter_custom_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.filter_custom_row, parent, false);

        TextView tvfiltername = (TextView) rowView.findViewById(R.id.lv_filtername);
        TextView tvfilterdetails = (TextView) rowView.findViewById(R.id.lv_filterdetails);

        tvfiltername.setText(itemsArrayList.get(position).getFiltername());
        tvfilterdetails.setText(itemsArrayList.get(position).getFilterdescription());

        return rowView;
    }
}
