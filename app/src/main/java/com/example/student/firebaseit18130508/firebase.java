package com.example.student.firebaseit18130508;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class firebase extends AppCompatActivity {

    EditText id, name , address , contactNo;
    Button save, show, update , delete;
    Student std;
    DatabaseReference dbref;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        contactNo = findViewById(R.id.number);

        save = findViewById(R.id.save);
        show = findViewById(R.id.show);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        rv = findViewById(R.id.recycleView);
        rv.setLayoutManager(new LinearLayoutManager(this));




        std = new Student();

    }

    private void clear(){
        id.setText("");
        name.setText("");
        address.setText("");
        contactNo.setText("");
    }

    public void saveStudent(View view) {
        dbref = FirebaseDatabase.getInstance().getReference().child("Student");
        try {
            if (TextUtils.isEmpty(id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter an ID", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(name.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter a Name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(address.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter a Address", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(contactNo.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please Enter a Contact No", Toast.LENGTH_SHORT).show();
            } else {
                std.setId(id.getText().toString().trim());
                std.setName(name.getText().toString().trim());
                std.setAddress(address.getText().toString().trim());
                std.setContactNo(contactNo.getText().toString().trim());

               dbref.child(std.getId()).setValue(std);
                Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                clear();
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
        }
    }

    public void getStudent(View view){
        String ID = id.getText().toString().trim();
        DatabaseReference readref = FirebaseDatabase.getInstance().getReference().child("Student").child(ID);
        readref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.hasChildren() ){
                    id.setText( dataSnapshot.child("id").getValue().toString() );
                    name.setText( dataSnapshot.child("name").getValue().toString() );
                    address.setText( dataSnapshot.child("address").getValue().toString() );
                    contactNo.setText( dataSnapshot.child("contactNo").getValue().toString() );
                }else{
                    Toast.makeText(getApplicationContext(), "NO such a data to display", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateStudent(View view){
        final String ID = id.getText().toString().trim();
        DatabaseReference updateref = FirebaseDatabase.getInstance().getReference().child("Student");
        updateref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.hasChild(ID)){
                    try {
                        std.setId(id.getText().toString().trim());
                        std.setName(name.getText().toString().trim());
                        std.setAddress(address.getText().toString().trim());
                        std.setContactNo(contactNo.getText().toString().trim());

                        dbref = FirebaseDatabase.getInstance().getReference().child("Student").child(std.getId());
                        dbref.setValue(std);
                        clear();
                        Toast.makeText(getApplicationContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                        clear();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "NO Source to Update", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteStudent(View view){
        final String ID = id.getText().toString().trim();
        DatabaseReference updateref = FirebaseDatabase.getInstance().getReference().child("Student");
        updateref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.hasChild(ID)){
                    dbref = FirebaseDatabase.getInstance().getReference().child("Student").child(ID);
                    dbref.removeValue();

                    clear();
                    Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "NO Source to Delete", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void selectAll( View view){
        Toast.makeText(getApplicationContext(), " Loading..", Toast.LENGTH_LONG).show();
        DatabaseReference allref = FirebaseDatabase.getInstance().getReference().child("Student");
        allref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.hasChildren()){

                    ArrayList<Student> array = new ArrayList<>();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        Student st = new Student();
                        st.setId( child.child("id").getValue().toString() );
                        st.setName( child.child("name").getValue().toString());
                        st.setAddress( child.child("address").getValue().toString());
                        array.add(st);
                    }

                    StudentAdapter adapter = new StudentAdapter(array);
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
