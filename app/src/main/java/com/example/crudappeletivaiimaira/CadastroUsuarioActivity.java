package com.example.crudappeletivaiimaira;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CadastroUsuarioActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText edittextEmail;
    EditText senha;
    EditText senhaConfirmar;
    Button botao;
    Button botaoLogin;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        edittextEmail = (EditText) findViewById(R.id.edittextEmail);
        senha = (EditText) findViewById(R.id.editTextSenha);
        senhaConfirmar = (EditText) findViewById(R.id.editTextConfirmarSenha);
        botao = (Button) findViewById(R.id.buttonCadastrar);
        botaoLogin = (Button) findViewById(R.id.btnFazerLogin);


        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senha = (EditText) findViewById(R.id.editTextSenha);
                senhaConfirmar = (EditText) findViewById(R.id.editTextConfirmarSenha);
                cadastrar();

            }
        });

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamarTelaLogin();

            }
        });
    }

    public void chamarTelaLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void cadastrar(){
        if (TextUtils.isEmpty(editTextNome.getText().toString())) {
            return;
        }

        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?,?,?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, editTextNome.getText().toString());
            stmt.bindString(2, edittextEmail.getText().toString());
            stmt.bindString(3, senha.getText().toString());
            stmt.executeInsert();
            bancoDados.close();
            finish();

            AlertDialog alertDialog = new AlertDialog.Builder(CadastroUsuarioActivity.this).create();
            alertDialog.setTitle("Sucesso");
            alertDialog.setMessage("Cadastro realizado com sucesso!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}