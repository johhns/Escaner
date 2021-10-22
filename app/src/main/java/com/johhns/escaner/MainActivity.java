package com.johhns.escaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button    btnScan ;
    TextView  txtResultado ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan      = findViewById( R.id.btnScan ) ;
        txtResultado = findViewById( R.id.txtResultado ) ;

        if (savedInstanceState != null ) {
            txtResultado.setText( savedInstanceState.getString("CODIGO") );
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intengrador = new IntentIntegrator( MainActivity.this ) ;
                intengrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES) ;
                intengrador.setPrompt("Lectura de Codigos de Barra");
                intengrador.setCameraId(0) ;
                intengrador.setOrientationLocked(false);
               // intengrador.setTorchEnabled(true); // enciende el flash
                intengrador.setBeepEnabled(true) ;
                intengrador.setBarcodeImageEnabled(true);
                intengrador.initiateScan();
            }
        });


    }

    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        IntentResult resultado = IntentIntegrator.parseActivityResult( requestCode , resultCode , data ) ;

        if ( resultado != null ) {
            if ( resultado.getContents() == null ) {
                Toast.makeText(this,"Lectura cancelada",Toast.LENGTH_LONG).show();
            } else {
                txtResultado.setText( resultado.getContents() );
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("CODIGO", txtResultado.getText().toString() );
        super.onSaveInstanceState(outState);
    }
}