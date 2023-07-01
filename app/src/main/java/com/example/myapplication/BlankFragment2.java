package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment {
    private String TAG = BlankFragment2.class.getSimpleName();
    private GridView gridview=null;
    private GridViewAdapter gAdapter = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        gridview = view.findViewById(R.id.gridview);
        gAdapter = new GridViewAdapter();

        gAdapter.addItem(new GridItem("230630","서경,태윤,석원,은",R.drawable.chicken));
        gAdapter.addItem(new GridItem("230630","서경,태윤",R.drawable.day2_1));

        gridview.setAdapter(gAdapter);

        return view;
    }

    class GridViewAdapter extends BaseAdapter {
        ArrayList<GridItem> items = new ArrayList<GridItem>();
        @Override
        public int getCount(){
            return items.size();
        }
        public void addItem(GridItem item){
            items.add(item);
        }
        @Override
        public Object getItem(int position){
            return items.get(position);
        }
        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup){
            final Context context = viewGroup.getContext();
            final GridItem gridItem = items.get(position);

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false);
                TextView image_num = (TextView)convertView.findViewById(R.id.image_num);
                TextView image_name = (TextView)convertView.findViewById(R.id.image_name);
                ImageView image_icon = (ImageView)convertView.findViewById(R.id.image_icon);

                image_num.setText(gridItem.getNum());
                image_name.setText(gridItem.getName());
                image_icon.setImageResource(gridItem.getIcon());
                Log.d(TAG, "getView() - [ "+position+" ]"+gridItem.getName());
            }
            return convertView;
        }

    }


}