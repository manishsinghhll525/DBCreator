package com.example.techelogy2.dbcreator;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import database.DatabaseHelper;
import database.SuburbModel;

public class MainActivity extends AppCompatActivity implements SuburbAdapter.DropdownSelectionListener {
    DatabaseHelper helper;

    AutoCompleteTextView auto_complete_text_suburb;
    private boolean isDropdownSelect = false;
    private ArrayList<SuburbModel> suburbList = new ArrayList<>();
    private SuburbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupForautoCompleteTextView();


        helper = new DatabaseHelper(MainActivity.this);
        processAndCreateSqliteFile();
    }

    private void setupForautoCompleteTextView() {
        auto_complete_text_suburb = (AutoCompleteTextView) findViewById(R.id.auto_complete_text_suburb);

        auto_complete_text_suburb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isDropdownSelect) {
                    isDropdownSelect = false;
                } else {
                    callPtSuburbApi(auto_complete_text_suburb.getText().toString());
                    /*if (s.length() % 2 == 0) {
                        callPtSuburbApi(auto_complete_text_suburb.getText().toString());
                    }*/
                }

            }
        });
    }

    private void newMethod(){

    }


    private void callPtSuburbApi(String suburb) {
        suburbList.clear();
        Cursor cursor = helper.searchForMatchingSuburbEnteries(suburb);
        if (cursor.getCount() > 0) {
            System.out.println("matching records found===" + cursor.getCount());
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                SuburbModel model = new SuburbModel();

                model.setId(cursor.getString(0));
                model.setPost_code(cursor.getString(3));
                model.setSuburbs(cursor.getString(1));
                model.setCountry_id(cursor.getString(6));
                model.setState_id(cursor.getString(2));
                model.setDelete_status(cursor.getString(7));
                model.setLat(cursor.getString(4));
                model.setLng(cursor.getString(5));
                model.setCountry_name(cursor.getString(8));
                model.setDisplay_title(cursor.getString(9));
                model.setInsertionTime(cursor.getString(10));
                suburbList.add(model);
                cursor.moveToNext();

            }

            if (suburbList.size() < 30) {
                Cursor cursor2 = helper.searchForOtherMatchingSuburbEnteries(suburb);

                if (cursor2.getCount() > 0) {
                    cursor2.moveToFirst();


                    for (int i = 0; i < cursor2.getCount(); i++) {
                        SuburbModel model = new SuburbModel();
                        model.setId(cursor2.getString(0));
                        if (suburbList.contains(model)) {

                        } else {
                            model.setPost_code(cursor2.getString(3));
                            model.setSuburbs(cursor2.getString(1));
                            model.setCountry_id(cursor2.getString(6));
                            model.setState_id(cursor2.getString(2));
                            model.setDelete_status(cursor2.getString(7));
                            model.setLat(cursor2.getString(4));
                            model.setLng(cursor2.getString(5));
                            model.setCountry_name(cursor2.getString(8));
                            model.setDisplay_title(cursor2.getString(9));
                            model.setInsertionTime(cursor2.getString(10));
                            suburbList.add(model);
                        }

                        cursor2.moveToNext();

                    }

                }
            }


            adapter = new SuburbAdapter(MainActivity.this, suburbList, suburbList);
            auto_complete_text_suburb.setAdapter(adapter);
            adapter.getFilter().filter(auto_complete_text_suburb.getText(), auto_complete_text_suburb);
            auto_complete_text_suburb.showDropDown();

        } else {
            System.out.println("no matching records found===" + cursor.getCount());
        }


    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("fl_suburbs.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void processAndCreateSqliteFile() {

        if (!(helper.isDatabaseExist())) {
            ArrayList<SuburbModel> suburbList = new ArrayList<>();
            String response = loadJSONFromAsset();
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject obj = jsonArray.getJSONObject(i);
                    SuburbModel model = new SuburbModel();
                    model.setId(obj.getString("id"));
                    model.setPost_code(obj.getString("post_code"));
                    model.setSuburbs(obj.getString("suburbs"));
                    model.setCountry_id(obj.getString("country_id"));
                    model.setState_id(obj.getString("state_id"));
                    model.setDelete_status(obj.getString("delete_status"));
                    model.setLat(obj.getString("lat"));
                    model.setLng(obj.getString("lng"));
                    model.setCountry_name(obj.getString("country_name"));
                    model.setDisplay_title(model.getSuburbs() + "," + " " + model.getState_id() + " " + model.getPost_code());
                    model.setInsertionTime(obj.getString("insertion_datetime"));
                    suburbList.add(model);


                }

                helper.insertBulkRecordsIntoDatabase(suburbList);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onDropDownSelect(SuburbModel model) {
        isDropdownSelect = true;
        auto_complete_text_suburb.setText(model.getSuburbs());
        auto_complete_text_suburb.dismissDropDown();
      /*  et_postcode.setText(model.getPost_code());
        et_state_territory.setText(model.getState_id());
        suburbId = model.getId();*/
    }
}
