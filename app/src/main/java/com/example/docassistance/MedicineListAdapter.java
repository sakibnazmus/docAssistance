package com.example.docassistance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class MedicineListAdaptere extends ArrayAdapter<Medicine> {
    Activity mActivity;
    List<Medicine> medicineList;
    LayoutInflater inflater;

    public MedicineListAdaptere(@NonNull Activity activity, int resource, @NonNull List<Medicine> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        medicineList = objects;

        inflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MedicineHolder holder;
        if (convertView == null){

            convertView = inflater.inflate(R.layout.view_medicine, parent, false);

            holder = new MedicineHolder();

            holder.mMedicineName = convertView.findViewById(R.id.medicineName);
            holder.numDays = convertView.findViewById(R.id.numOfDays);
            holder.mComment = convertView.findViewById(R.id.comment);

            convertView.setTag(holder);
        }else
            holder = (MedicineHolder) convertView.getTag();

        Medicine medicine = medicineList.get(position);
        if(holder == null) return convertView;

        holder.mMedicineName.setText("Medicine: " + medicine.name);
        holder.numDays.setText("Days: " + medicine.days);
        holder.mComment.setText("Comment: " + medicine.note);

        return convertView;
    }


    public void updateList(ArrayList<Medicine> medicines) {
        this.medicineList = medicines;
        notifyDataSetChanged();
    }

    private class MedicineHolder{
        TextView mMedicineName;
        TextView numDays;
        TextView mComment;
    }
}
