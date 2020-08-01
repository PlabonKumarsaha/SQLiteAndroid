package com.example.sqliteandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqliteandroid.Model.CustomerModel;

public class MainActivity extends AppCompatActivity {

    EditText nameET,ageET;
    SwitchCompat activeCustomerBTn;
    Button viewAllBTn,addBTn;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference
        nameET = findViewById(R.id.nameET);
        ageET = findViewById(R.id.ageET);
        activeCustomerBTn = findViewById(R.id.activeCustomerBTn);
        viewAllBTn = findViewById(R.id.viewAllBTn);
        addBTn = findViewById(R.id.addBTn);
        listView = findViewById(R.id.listView);

        addBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerModel customerModel;

                try{

                    customerModel = new CustomerModel(-1,nameET.getText().toString(),
                            Integer.parseInt(ageET.getText().toString()),activeCustomerBTn.isChecked());

                }catch (Exception e){
                    customerModel = new CustomerModel(-1,"null",1,false);

                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean sucess = dataBaseHelper.addOneColumn(customerModel);
                Toast.makeText(MainActivity.this,"SUcess : "+sucess,Toast.LENGTH_SHORT).show();

            }
        });

        viewAllBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}