package com.example.bookworm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private EditText name, number;
    private Button btn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.button);
        name = (EditText)findViewById(R.id.name);
        listView = (ListView)findViewById(R.id.listView);
         number = (EditText)findViewById(R.id.number);


         // Data Write , Update , delete part


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = name.getText().toString().trim();
                String s2 = number.getText().toString().trim();

if(s.isEmpty()||s2.isEmpty()){
    Toast.makeText(MainActivity.this, "Enter a valid input", Toast.LENGTH_LONG).show();
}else {

    HashMap<String,Object> hm = new HashMap<String,Object>();
hm.put("Name ",s); hm.put("Phone Number ",s2);
    FirebaseDatabase.getInstance().getReference().child("Database").child(s).updateChildren(hm);
    Toast.makeText(MainActivity.this, "Data Added", Toast.LENGTH_LONG).show();
}

            }
        });



        // Data Read part



        ArrayList<String> al = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listitem,al);
        listView.setAdapter(adapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Database");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Datasnapshot) {
                al.clear();
                for(DataSnapshot dataSnapshot : Datasnapshot.getChildren()){

                    String [] ok = dataSnapshot.getValue().toString().split(",");
                    al.add(ok[1].substring(1,ok[1].length()-1));
                    al.add(ok[0].substring(1,ok[0].length()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}