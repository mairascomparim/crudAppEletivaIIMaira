package com.example.crudappeletivaiimaira;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    EditText edittextEmail;
    EditText senha;
    Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        botao = (Button) findViewById(R.id.btnEntrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextEmail = (EditText) findViewById(R.id.editTextEmail);
                senha = (EditText) findViewById(R.id.editTextTextPassword);
                validarLogin();
            }
        });
    }

    public void validarLogin(){
        Boolean loginValido = false;
        try{
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            Cursor res = bancoDados.rawQuery( "SELECT id FROM usuario WHERE email ='"+edittextEmail.getText().toString() +"' AND senha ='"+senha.getText().toString()+"'",null);
            res.moveToFirst();
            loginValido = res.getInt(0) > 0 == true;
        }catch(Exception e){
            e.printStackTrace();
        }
        if(loginValido) {
            Intent intent = new Intent(this, ListarDadosActivity.class);
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Erro");
            alertDialog.setMessage("Email ou senha incorretos!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}