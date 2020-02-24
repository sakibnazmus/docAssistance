# docAssistance

package com.samsung.dexwifi;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by arthonsystechnologiesllp on 10/03/17.
 */

public class CustomAdapter extends BaseAdapter {

    Activity activity;
    List<UserModel> users;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public CustomAdapter(Activity activity) {
        this.activity = activity;
    }

    public CustomAdapter(Activity activity, List<UserModel> users) {
        this.activity   = activity;
        this.users      = users;

        inflater        = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null){

            view = inflater.inflate(R.layout.list_view_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = view.findViewById(R.id.tv_user_name);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        UserModel model = users.get(i);
        if(holder == null) return view;

        holder.tvUserName.setText(model.getScanResult().BSSID + " " + model.getScanResult().SSID);
        holder.tvUserName.setTextColor(Color.BLACK);
        holder.tvUserName.setGravity(Gravity.CENTER);

        if (model.isSelected())
            holder.tvUserName.setBackgroundColor(Color.GRAY);

        else
            holder.tvUserName.setBackgroundColor(Color.WHITE);
        return view;

    }

    public void updateRecords(List<UserModel>  users){
        this.users = users;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
    }
}






ListView listView = findViewById(R.id.listView);

        List<ScanResult> mScanResults = mWifiManager.getScanResults();
        Log.v("DeX wifi", "Got available: " + mScanResults.size());
        for(ScanResult result: mScanResults){
            UserModel userModel = new UserModel(false, result);
            users.add(userModel);
        }

        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserModel model = users.get(i);

                model.setSelected(true);

                users.set(i, model);

                if (preSelectedIndex > -1){

                    UserModel preRecord = users.get(preSelectedIndex);
                    preRecord.setSelected(false);
                    users.set(preSelectedIndex, preRecord);
                }

                preSelectedIndex = i;

                adapter.updateRecords(users);
            }
        });
