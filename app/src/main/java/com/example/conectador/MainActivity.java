package com.example.conectador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    TextView tvRegistros, tvErros;
    Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegistros= findViewById(R.id.tvResultado);
        tvErros=findViewById(R.id.tvErros);
        btnRegistro=findViewById(R.id.btnRegistros);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Task().execute();
            }
        });
    }

    class Task extends AsyncTask<Void, Void, Void>{
        String registros="", erro="";

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexao= DriverManager.getConnection("jdbc:mysql://192.168.106.50:3306/quim1","root","19twostars");
                Statement stmt = conexao.createStatement();
                ResultSet rsResultado = stmt.executeQuery("select nomealuno from testealuno");

                while(rsResultado.next()){
                    registros += rsResultado.getString("nomealuno")+"\n";

                }
            }catch(Exception e){
                erro = e.toString();
            }
            //TODO adiciona o comentário
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            tvRegistros.setText(registros);
            if(erro!=""){
                tvErros.setText(erro);
            }
            super.onPostExecute(aVoid);
        }
    }

}

