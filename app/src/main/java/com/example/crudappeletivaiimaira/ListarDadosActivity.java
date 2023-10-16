package com.example.crudappeletivaiimaira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListarDadosActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public Button botao;
    private UserAdapter adapter;
    public TextView textemailusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_dados);
        criarBancoDados();

        criarBancoDados();
        listViewDados = (ListView) findViewById(R.id.listViewdados);
        botao = (Button) findViewById(R.id.btnCadastrar);
        adapter = new UserAdapter(listViewDados.getContext(), new ArrayList<UserLine>(), this);
        textemailusuario = (TextView) findViewById(R.id.txtEmailAcesso);
        listViewDados.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        String email;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email= null;
            } else {
                email= extras.getString("email");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
        }
        textemailusuario.setText("Usuário:"+email);;

        listarDados();

    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cliente(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR)");
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS usuario(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR, email VARCHAR, senha VARCHAR)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados() {
        try {
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM cliente",null);

            ArrayList<UserLine> linhas = new ArrayList<UserLine>();

            adapter = new UserAdapter(listViewDados.getContext(), linhas, this);
            listViewDados.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            listViewDados.setAdapter(adapter);
            meuCursor.moveToFirst();
            while (meuCursor != null){
                UserLine userLine= new UserLine();
                userLine.setId(meuCursor.getInt(0));
                userLine.setName(meuCursor.getString(1));
                linhas.add(userLine);
                meuCursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int Id){
        try {
            SQLiteDatabase bancoDados;
            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
            String table = "cliente";
            String whereClause = "id=?";
            String[] args = new String[]{String.valueOf(Id)};
            bancoDados.delete(table, whereClause, args);
            bancoDados.close();
            listarDados();
        } catch (Exception e) {
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

    public void abrirTelaAlterar(int id){
        Intent intent = new Intent(this, AlterarActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}