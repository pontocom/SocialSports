package pt.iscte_iul.socialsports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import java.util.ArrayList;

/**
 * Created by cserrao on 17/07/15.
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {
    private final Context context;
    private final ArrayList<Tweet> itemsArrayList;

    public TweetAdapter(Context context, ArrayList<Tweet> itemsArrayList) {
        super(context, R.layout.tweet_custom_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;

        //File cacheDir = StorageUtils.getCacheDirectory(this.context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.context).build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tweet_custom_row, parent, false);

        TextView tvTweetUsername = (TextView) rowView.findViewById(R.id.tvTweetUsername);
        TextView tvTweetContent = (TextView) rowView.findViewById(R.id.tvTweetContent);
        ImageView ivTweetUserPicture = (ImageView) rowView.findViewById(R.id.ivTweetUsername);
        ImageView ivTweetOptionalContent = (ImageView) rowView.findViewById(R.id.ivOptionalImageContent);

        tvTweetUsername.setText(itemsArrayList.get(position).getTweetUsername());
        tvTweetContent.setText(itemsArrayList.get(position).getTweetContent());
        ImageLoader.getInstance().displayImage(itemsArrayList.get(position).getTweetUserPictureURL(), ivTweetUserPicture);
        ImageLoader.getInstance().displayImage(itemsArrayList.get(position).getTweetOptionalContentURL(), ivTweetOptionalContent);

        return rowView;
    }
}
