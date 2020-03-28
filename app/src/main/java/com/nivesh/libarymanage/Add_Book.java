package com.nivesh.libarymanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__book);
        final Button ad_bo=findViewById(R.id.add_boo);
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);
        ad_bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad_bo.setEnabled(false);
                EditText bo_id=findViewById(R.id.book_id);
                EditText bo_na=findViewById(R.id.book_na);
                EditText bo_au=findViewById(R.id.author_bo);
                EditText bo_pub=findViewById(R.id.bo_pub);
                EditText bo_co=findViewById(R.id.count);
                String boo_id=bo_id.getText().toString();
                String boo_na=bo_na.getText().toString();
                String boo_au=bo_au.getText().toString();
                String boo_pub=bo_pub.getText().toString();
                Integer boo_co= Integer.valueOf(bo_co.getText().toString());
                if (boo_id.equals("")){
                    bo_id.setError("must not empty");
                    ad_bo.setEnabled(true);
                }
                if (boo_na.equals("")){
                    bo_na.setError("must not empty");
                    ad_bo.setEnabled(true);
                }
                if (boo_au.equals("")){
                    bo_au.setError("must not empty");
                    ad_bo.setEnabled(true);
                }
                if (boo_pub.equals("")){
                    bo_pub.setError("must not empty");
                    ad_bo.setEnabled(true);
                }
                if (boo_co.equals("")){
                    bo_co.setError("must not empty");
                    ad_bo.setEnabled(true);
                }
                else {
                    ad_bo.setEnabled(false);
                    Cursor exi_bo_id=sqLiteDatabase.rawQuery("select * from book where bookid='"+boo_id+"'",null);
                    if(exi_bo_id.moveToNext()){
                        Toast.makeText(Add_Book.this, "already exists", Toast.LENGTH_SHORT).show();
                        ad_bo.setEnabled(true);
                    }
                    else {
                        sqLiteDatabase.execSQL("insert into book values ('" + boo_id + "','" + boo_na + "','" + boo_au + "','" + boo_pub + "'," + boo_co + "," + boo_co + ")");


                        Toast.makeText(Add_Book.this, "book added successfully", Toast.LENGTH_SHORT).show();
                        bo_au.setText("");
                        bo_id.setText("");
                        bo_pub.setText("");
                        bo_na.setText("");
                        bo_co.setText("");
                        ad_bo.setEnabled(true);
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Add_Book.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
