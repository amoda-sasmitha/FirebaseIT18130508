package com.example.student.firebaseit18130508;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private ArrayList<Student> arrayList;

    public StudentAdapter(ArrayList<Student> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row , viewGroup , false   );
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder Holder, int i) {

         Student s = arrayList.get(i);
         Holder.id.setText( s.getId() );
         Holder.name.setText( s.getName() );
         Holder.address.setText( s.getAddress() );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView id, name , address;

         StudentViewHolder(@NonNull View itemView) {
             super(itemView);
             id =   itemView.findViewById(R.id.idx);
            name =   itemView.findViewById(R.id.namex);
            address =   itemView.findViewById(R.id.addressx);
        }
    }

}
