package com.example.crudappeletivaiimaira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    public boolean validarLogin(){
        Boolean loginValido = false;
        try{
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            Cursor res = bancoDados.rawQuery( "SELECT id FROM usuario WHERE email ='"+edittextEmail.getText().toString() +"' AND senha ='"+senha.getText().toString()+"'",null);
            res.moveToFirst();
            loginValido = res.isAfterLast() == true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return loginValido;
    }
}