package com.example.houseofhope;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private Context mContext;
    private ArrayList<String> array_mountain;

    //private ViewHolder mViewHolder;

    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData) {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position) {
        return m_oData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }



        TextView oTextvisitor_name = (TextView) convertView.findViewById(R.id.visitor_name);
        TextView oTextcarNumber = (TextView) convertView.findViewById(R.id.carNumber);
        TextView oTextdate = (TextView) convertView.findViewById(R.id.date);
        TextView oTextvisit_dong = (TextView) convertView.findViewById(R.id.visit_dong);
        TextView oTextvisit_ho = (TextView) convertView.findViewById(R.id.visit_ho);
        TextView oTextvisitorPurpose = (TextView) convertView.findViewById(R.id.visitorPurpose);

        oTextvisitor_name.setText(m_oData.get(position).guest_name);
        oTextcarNumber.setText(m_oData.get(position).guest_car);
        oTextdate.setText(m_oData.get(position).date);
        oTextvisit_dong.setText(m_oData.get(position).visit_dong);
        oTextvisit_ho.setText(m_oData.get(position).visit_ho);
        oTextvisitorPurpose.setText(m_oData.get(position).visit_why);



        return convertView;
    }



}