package com.example.crudappeletivaiimaira;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {
    EditText editTextNome;
    Button botao;
    SQLiteDatabase bancoDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        botao = (Button) findViewById(R.id.buttonCadastro);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        if (TextUtils.isEmpty(editTextNome.getText().toString())) {
            return;
        }
        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            String sql = "INSERT INTO cliente (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, editTextNome.getText().toString());
            stmt.executeInsert();
            bancoDados.close();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}