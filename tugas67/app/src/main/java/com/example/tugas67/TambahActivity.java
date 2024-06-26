package com.example.tugas67;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
public class TambahActivity extends AppCompatActivity {
    EditText editTextNama, editTextNomor, editTextTanggal, editTextAlamat;
    Button buttonSimpan, buttonbatal;
    Database dbHelper;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        dbHelper = new Database(this);
        editTextNama = findViewById(R.id.nama);
        editTextNomor = findViewById(R.id.nomor);
        editTextTanggal = findViewById(R.id.tanggal);
        editTextAlamat = findViewById(R.id.alamat);
        buttonSimpan = findViewById(R.id.simpan);
        buttonbatal = findViewById(R.id.batal);
        calendar = Calendar.getInstance();
        // Set listener untuk editTextTanggal agar muncul DatePickerDialog saat diklik
        editTextTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahKontak();
            }
        });
        buttonbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke menu utama (MainActivity)
                finish();
            }
        });
    }
    // Method untuk menampilkan DatePickerDialog
    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(TambahActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set tanggal yang dipilih ke EditText
                        editTextTanggal.setText(String.format("%02d-%02d-%02d", dayOfMonth, month + 1, year % 100));
                    }
                }, year, month, day);

        // Tampilkan DatePickerDialog
        datePickerDialog.show();
    }
    // Method untuk menambahkan kontak baru ke dalam database
    private void tambahKontak() {
        String nama = editTextNama.getText().toString().trim();
        String nomor = editTextNomor.getText().toString().trim();
        String tanggal = editTextTanggal.getText().toString().trim();
        String alamat = editTextAlamat.getText().toString().trim();

        if (!nama.isEmpty() && !nomor.isEmpty() && !tanggal.isEmpty() && !alamat.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nama", nama);
            values.put("no", nomor);
            values.put("tgl", tanggal);
            values.put("alamat", alamat);

            long newRowId = db.insert("kontak", null, values);
            if (newRowId != -1) {
                Toast.makeText(this, "Kontak berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke MainActivity setelah menambahkan kontak
            } else {
                Toast.makeText(this, "Gagal menambahkan kontak", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Silakan lengkapi semua field", Toast.LENGTH_SHORT).show();
        }
    }
}
