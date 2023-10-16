package com.example.crudappeletivaiimaira;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UserLine> mUserData;
    private ListarDadosActivity listarDadosActivity;

    public UserAdapter(Context context, ArrayList userData, ListarDadosActivity activity) {
        super();
        mContext = context;
        mUserData = userData;
        listarDadosActivity = activity;
    }

    public int getCount() {
        // return the number of records
        return mUserData.size();
    }

    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_listview_row, parent, false);


        // get the reference of textView and button
        TextView textTitle = (TextView) view.findViewById(R.id.row_text);
        Button btnEdit = (Button) view.findViewById(R.id.row_edit_button);
        Button btnDelete = (Button) view.findViewById(R.id.row_delete_button);

        // Set the title and button name
        textTitle.setText(mUserData.get(position).getName());

        // Click listener of button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarDadosActivity.abrirTelaAlterar(mUserData.get(position).getId());

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(mContext);
                deleteBuilder.setMessage("Deseja excluir?");
                deleteBuilder.setCancelable(true);

                deleteBuilder.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                listarDadosActivity.excluir(mUserData.get(position).getId());
                            }

                        });

                deleteBuilder.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertEdit = deleteBuilder.create();
                alertEdit.show();
            }
        });

        return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }}