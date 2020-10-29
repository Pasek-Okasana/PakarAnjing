package com.pakaranjing;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.pakaranjing.Activity.DiagnosaActivity;
import com.pakaranjing.Activity.LoginActivity;
import com.pakaranjing.Activity.PenyakitActivity;
import com.pakaranjing.Activity.RiwayatActivity;
import com.pakaranjing.Adapter.CustomListAdapter;

public class MainActivity extends Activity {
    private GridView mGridView;
    private ImageView gambar;
    private String[] mColors = {
            "#AB47BC",
            "#26A69A",
            "#26C6DA",
            "#FFA726",
            "#EC407A",
            "#9CCC65"
    };
    private String[] itemname = {
            "Diagnosa",
            "Daftar Penyakit",
            "Riwayat Diagnosa",
            "Bantuan",
            "About",
            "Logout"
    };
    private Integer[] imgid = {
            R.drawable.stethoscope,
            R.drawable.list,
            R.drawable.riwayat,
            R.drawable.question,
            R.drawable.info,
            R.drawable.logout
    };

    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Menu Utama User");
        gambar = findViewById(R.id.gambar);
        gambar.setImageResource(R.drawable.pets);
        session = new SessionHandler(getApplicationContext());
        mGridView = findViewById(R.id.categories_list);
        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid,mColors);
//        ListView list = findViewById(R.id.list);
        mGridView.setAdapter(adapter);
//        list.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, DiagnosaActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, PenyakitActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, RiwayatActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(MainActivity.this, BantuanUserActivity.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                } else if (position == 5) {
                    logout();
                }
            }
        });
    }

    //    jika tombol back ditekan
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //    fungsi untuk menampilkan alert jika tombol back ditekan
    protected void exitByBackKey() {
        new AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Anda yakin mau keluar ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    protected void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Anda yakin mau logout ?")
                .setPositiveButton("Ya, Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        session.logoutUser();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }
}
