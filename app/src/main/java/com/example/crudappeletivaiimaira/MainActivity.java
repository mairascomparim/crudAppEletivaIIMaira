package com.example.crudappeletivaiimaira;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView listViewDados;
    public Button botao;
    private UserAdapter adapter;
    Repositorio repositorio = new Repositorio();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewDados = (ListView) findViewById(R.id.listViewdados);
        botao = (Button) findViewById(R.id.btnCadastrar);
        adapter = new UserAdapter(listViewDados.getContext(), new ArrayList<UserLine>());
        listViewDados.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        repositorio.createBanco();
        listarDados();
    }

    public void listarDados() {
        try {
            Cursor meuCursor = repositorio.listarRegistros();

            ArrayList<UserLine> linhas = new ArrayList<UserLine>();

            adapter = new UserAdapter(listViewDados.getContext(), linhas);
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
            //bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void excluir(Integer idDelete){
//        try {
//            criarBancoDados();
//            bancoDados = openOrCreateDatabase("crudappmaira", MODE_PRIVATE, null);
//            bancoDados.execSQL("DELETE FROM CLIENTE WHERE id = "+ idDelete);
//            listarDados();
//            bancoDados.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

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