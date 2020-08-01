package com.example.sqliteandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqliteandroid.Model.CustomerModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nameET,ageET;
    SwitchCompat activeCustomerBTn;
    Button viewAllBTn,addBTn;
    ListView listView;

    ArrayAdapter customAdapter;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    List<CustomerModel>geteveryOne;


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

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,dataBaseHelper.getAllList());
        listView.setAdapter(customAdapter);


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
                Toast.makeText(MainActivity.this,"Sucess : "+sucess,Toast.LENGTH_SHORT).show();
                customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,dataBaseHelper.getAllList());
                listView.setAdapter(customAdapter);


            }
        });

        viewAllBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // geteveryOne = dataBaseHelper.getAllList();
                //Toast.makeText(getApplicationContext(),geteveryOne.toString(),Toast.LENGTH_SHORT).show();

                 customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,dataBaseHelper.getAllList());
                listView.setAdapter(customAdapter);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                CustomerModel customerModel = (CustomerModel) parent.getItemAtPosition(i);
                dataBaseHelper.deleteOne(customerModel);

                //show the datas after delete
                customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,dataBaseHelper.getAllList());
                listView.setAdapter(customAdapter);
                Toast.makeText(getApplicationContext(),"Deleted : "+customerModel.toString(),Toast.LENGTH_SHORT).show();


            }
        });


    }
}