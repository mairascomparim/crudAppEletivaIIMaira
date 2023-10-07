package com.example.crudappeletivaiimaira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewDados = (ListView) findViewById(R.id.listViewdados);
        botao = (Button) findViewById(R.id.btnCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        criarBancoDados();
        inserirDadosTemp();
        listarDados();
    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cliente(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados() {
        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM cliente",null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while (meuCursor != null){
                linhas.add(meuCursor.getString(1));
                meuCursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void inserirDadosTemp(){
        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            String sql = "INSERT INTO cliente (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1, "Maria joao");
            stmt.executeInsert();

            stmt.bindString(1, "Matheus Barros");
            stmt.executeInsert();

            stmt.bindString(1, "Maira Scomparim");
            stmt.executeInsert();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }
}