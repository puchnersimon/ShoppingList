package at.fhhgb.mc.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class ListViewAdapter4 extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    // The ListViewAdapter Constructor
    public ListViewAdapter4(Context context, ArrayList<String> items) {
        super(context, R.layout.list_row, items);
        this.context = context;
        list = items;
    }

    // method override to provide own layout for each View (row) in ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row, null);
            TextView name = convertView.findViewById(R.id.name);
            ImageView remove = convertView.findViewById(R.id.remove);

            name.setText(list.get(position));

            // Listeners for removing an item.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity_List4.removeItem(position);
                }
            });
        }
        return convertView;
    }

}