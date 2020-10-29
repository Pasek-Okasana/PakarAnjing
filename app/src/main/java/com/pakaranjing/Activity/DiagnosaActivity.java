package com.pakaranjing.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pakaranjing.Adapter.DiagnosaAdapter;
import com.pakaranjing.Model.Gejala;
import com.pakaranjing.Model.User;
import com.pakaranjing.MySingleton;
import com.pakaranjing.R;
import com.pakaranjing.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiagnosaActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = MainAPI.MAINAPI+"get_daftar_gejala.php";
    private static final String urldiagnosahasil = MainAPI.MAINAPI+"get_hasil_diagnosa.php";
//    private static final String url = "http://10.0.2.2/eclinic_melati/android/get_daftar_gejala.php";
//    private MyCustomAdapter dataAdapter = null;
    private ArrayList<Gejala> gejalaList = new ArrayList<Gejala>();
    private Gejala gejala;

    private RecyclerView mRecycler;
    private LinearLayoutManager mLayoutMnager;
    private DiagnosaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
//        setTitle("Pilih Gejala");

        settingToolbar();
        settingRecyclerView();
        getDaftarGejala();

        Button btn_diagnosa = findViewById(R.id.btn_diagnosa);
        btn_diagnosa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                if (!gejalaList.isEmpty()) {
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        Gejala gejala = mAdapter.getItemPosition(i);
                        if (gejala.isSelected()) {
                            responseText.append(gejala.getName() + "#");
                        }
                    }
                }

                if (responseText.toString().equals("")) {
                    Toast.makeText(DiagnosaActivity.this, "Pilih dulu gejala !", Toast.LENGTH_SHORT).show();
                } else {
                    Intent myIntent = new Intent(v.getContext(), DiagnosaHasilActivity.class);
                    myIntent.putExtra("HASIL", responseText.toString());
                    startActivity(myIntent);
//                    Toast.makeText(getApplicationContext(), responseText.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void settingRecyclerView() {
        mRecycler = findViewById(R.id.recyclerdiagnosa);


        mLayoutMnager = new LinearLayoutManager(this);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mLayoutMnager);
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(DiagnosaActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void displayGejalaView() {
//        dataAdapter = new MyCustomAdapter(this, R.layout.diagnosa_list, gejalaList);
//        ListView listView = findViewById(R.id.list);
//        listView.setAdapter(dataAdapter);
        mAdapter = new DiagnosaAdapter(gejalaList);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        Log.i("Diagnosa ", " Size Of Adapter = " + mAdapter.getItemCount());
        Log.i("Diagnosa ", " Size Of Array = " + gejalaList.size());
    }

    private void getDaftarGejala() {
        displayLoader();
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            if (response.getInt("status") == 0) {
                                gejalaList = new ArrayList<Gejala>();
                                JSONArray jsonArray = response.getJSONArray("gejala");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("nama_gejala");
                                    gejala = new Gejala(name, false);
                                    gejalaList.add(gejala);
                                }
                                displayGejalaView();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

//    TODO : Setting Toolbar
    private void settingToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_32dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });
    }



//    private class MyCustomAdapter extends ArrayAdapter<Gejala> {
//
//        private ArrayList<Gejala> gejalaList;
//
//        public MyCustomAdapter(Context context, int textViewResourceId,
//                               ArrayList<Gejala> gejalaList) {
//            super(context, textViewResourceId, gejalaList);
//            this.gejalaList = new ArrayList<Gejala>();
//            this.gejalaList.addAll(gejalaList);
//        }
//
//        private class ViewHolder {
//            CheckBox name;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null;
//            if (convertView == null) {
//                LayoutInflater vi = (LayoutInflater) getSystemService(
//                        Context.LAYOUT_INFLATER_SERVICE);
//                convertView = vi.inflate(R.layout.diagnosa_list, null);
//
//                holder = new ViewHolder();
//                holder.name = convertView.findViewById(R.id.chk_gejala);
//                convertView.setTag(holder);
//
//                holder.name.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        CheckBox cb = (CheckBox) v;
//                        Gejala gejala = (Gejala) cb.getTag();
//                        gejala.setSelected(cb.isChecked());
//                    }
//                });
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            Gejala gejala = gejalaList.get(position);
//            holder.name.setText(gejala.getName());
//            holder.name.setChecked(gejala.isSelected());
//            holder.name.setTag(gejala);
//
//            return convertView;
//        }
//    }
}
