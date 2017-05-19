package com.example.costellom3761.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editAge, editAddress, editSearch;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        //add the layout vars
        editName = (EditText) findViewById(R.id.editText_name);
        editAge = (EditText) findViewById(R.id.editText_age);
        editAddress = (EditText) findViewById(R.id.editText_address);

        editSearch = (EditText) findViewById(R.id.editText_search);
    }

    public void addData(View v) {
        boolean isInserted = myDb.insertData(editName.getText().toString(),
                editAge.getText().toString(), editAddress.getText().toString());

        if(isInserted == true) {
            Log.d("MyContact", "Success inserting data");
            //insert toast message here...
            Toast.makeText(getApplicationContext(), "SUCCESS",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d("MyContact", "Failure inserting data");
            //insert toast message here...
            Toast.makeText(getApplicationContext(), "FAILURE",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void viewData(View v) {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            showMessage("Error", "No data found in database");

            //output message using log.d and toast
            Log.d("MyContact", "Unable to show data because there is none");
            Toast.makeText(getApplicationContext(), "Cannot viewData, no data in database",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //setup a loop with cursor(res) using moveToNext
        //append each Col to the buffer
        //display message using showMessage


        while (res.moveToNext()) {
            String idString = res.getString(0);
            String nameString = res.getString(1);
            String ageString = res.getString(2);
            String addressString = res.getString(3);

            buffer.append("ID: " + idString + "\n" + "name: " + nameString + "\n" + "age: " + ageString + "\n" +
                "address: " + addressString + "\n\n");

        }

        showMessage("Data", buffer.toString());

    }

    public void searchData(View v) {

        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();
        String match =  editSearch.getText().toString();

        while (res.moveToNext()) {
            String idString = res.getString(0);
            String nameString = res.getString(1);
            String ageString = res.getString(2);
            String addressString = res.getString(3);

            if (match.equals(nameString)) {

                buffer.append("ID: " + idString + "\n" + "name: " + nameString + "\n" + "age: " + ageString + "\n" +
                        "address: " + addressString + "\n\n");

            }
        }
        if(buffer.length()==0) {
            showMessage("No Results Found", buffer.toString());
        }
        else {
            showMessage("Search Results ", buffer.toString());
            Log.d("MyContact", "results found");
        }
        }

    private void showMessage(String title, String message) {
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true); //cancel using back button
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
