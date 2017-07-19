package com.example.android6999.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button copiar = (Button) findViewById(R.id.btnCopiar);
        final EditText entrada = (EditText) findViewById(R.id.entrada);
        final TextView saida = (TextView) findViewById(R.id.saida);



        copiar.setOnClickListener(new View.OnClickListener(){

          public void onClick(View v){
              String txt = entrada.getText().toString();
              saida.setText(txt);

          }

        });

    }




}
