package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

public class BonActivity extends AppCompatActivity {
    TextView tvWelcome, tvMember, tvTransaksi, tvBon, tvThanks;
    Button btnShare;
    String nama, tipeMember, kodeBarang, namaBarang;
    int hargaBarang, jumlahBarang, totalHarga, diskonMember, diskonHarga, jumlahBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvMember = findViewById(R.id.tvMember);
        tvTransaksi = findViewById(R.id.tvTransaksi);
        tvBon = findViewById(R.id.tvBon);
        tvThanks = findViewById(R.id.tvThanks);
        btnShare = findViewById(R.id.btnShare);

        Intent intent = getIntent();
        if (intent != null) {
            nama = intent.getStringExtra("nama");
            tipeMember = intent.getStringExtra("tipeMember");
            kodeBarang = intent.getStringExtra("kodeBarang");
            namaBarang = getNamaBarang(kodeBarang); // Mendapatkan nama barang berdasarkan kode barang
            hargaBarang = intent.getIntExtra("hargaBarang", 0);
            jumlahBarang = intent.getIntExtra("jumlahBarang", 0);
            totalHarga = intent.getIntExtra("totalHarga", 0);
            diskonHarga = intent.getIntExtra("diskonHarga", 0);
            diskonMember = intent.getIntExtra("diskonMember", 0);
            jumlahBayar = intent.getIntExtra("jumlahBayar", 0);
        }

        // Menampilkan data transaksi pada TextView
        tvWelcome.setText("Selamat Datang, " + nama);
        tvMember.setText("Membership: " + tipeMember);
        tvTransaksi.setText("Transaksi Hari Ini:");

        String bonText = "Kode Barang: " + kodeBarang + "\n" +
                "Nama Barang: " + namaBarang + "\n" +
                "Harga Barang: Rp. " + formatRupiah(hargaBarang) + "\n" +
                "Jumlah Barang: " + jumlahBarang + "\n" +
                "Total Harga: Rp. " + formatRupiah(totalHarga) + "\n" +
                "Diskon Harga: Rp. " + formatRupiah(diskonHarga) + "\n" +
                "Diskon Member: Rp. " + formatRupiah(diskonMember) + "\n" +
                "Jumlah Bayar: Rp. " + formatRupiah(jumlahBayar);

        tvBon.setText(bonText);
        tvThanks.setText("Terima Kasih telah berbelanja disini!");

        // Memberikan listener untuk tombol share
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = bonText;
                String shareSubject = "Nota Pembelian";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(shareIntent, "Bagikan nota melalui"));
            }
        });
    }

    // Metode untuk mendapatkan nama barang berdasarkan kode barang
    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "SGS":
                return "Samsung Galaxy S";
            case "AV4":
                return "Asus Vivobook 14";
            case "MP3":
                return "Macbook Pro M3";
            default:
                return "Nama Barang Tidak Diketahui";
        }
    }

    // Metode untuk memformat harga menjadi format Rupiah
    private String formatRupiah(int harga) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(harga);
    }
}
