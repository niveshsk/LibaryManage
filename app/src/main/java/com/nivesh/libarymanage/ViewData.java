package com.nivesh.libarymanage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);
        Button st_det=findViewById(R.id.stu_data);
        Button bo_de=findViewById(R.id.boo_det);
        Button issue_det=findViewById(R.id.iss_de);
        Button stude_det=findViewById(R.id.stu_de_bu);
        final Button boo_det=findViewById(R.id.book_deta);
        boo_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText book_de=findViewById(R.id.book_det_bu);
                String boo_d=book_de.getText().toString();
                if(boo_d.equals("")){
                    boo_det.setError("enter book id");
                }
                else{
                Cursor det_st=sqLiteDatabase.rawQuery("select bookid,book_name,author,publication from book where bookid='"+boo_d+"'",null);
                StringBuffer stringBuffer=new StringBuffer();
                if (det_st.moveToNext()){
                    stringBuffer.append("\n Book ID :\t "+det_st.getString(0));
                    stringBuffer.append("\n Book Name :\t "+det_st.getString(1));
                    stringBuffer.append("\n Author :\t "+det_st.getString(2));
                    stringBuffer.append("\n Publication :\t "+det_st.getString(3)+"\n\n");
                }
                else{
                    stringBuffer.append("not exists");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(ViewData.this);
                builder.setTitle("Book details");
                builder.setMessage(stringBuffer);
                builder.setCancelable(true);

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                }
            }
        });
        stude_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText st_de=findViewById(R.id.stu_deta);
                String st_d=st_de.getText().toString();
                if(st_d.equals("")){
                    st_de.setError("enter student reg number");
                }
                else{
                    Cursor det_st=sqLiteDatabase.rawQuery("select register_number,student_name,available_cards from student where register_number='"+st_d+"'",null);
                    StringBuffer stringBuffer=new StringBuffer();

                    if (det_st.moveToNext()){
                        stringBuffer.append("\n Register Number :\t "+det_st.getString(0));
                        stringBuffer.append("\n Student Name :\t "+det_st.getString(1));
                        stringBuffer.append("\n Available cards :\t" +det_st.getString(2)+"\n\n");
                    }
                    else{
                        stringBuffer.append("Not exist");
                    }
                    AlertDialog.Builder builder=new AlertDialog.Builder(ViewData.this);
                    builder.setTitle("Student details");
                    builder.setMessage(stringBuffer);
                    builder.setCancelable(true);

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
            }
        });
        st_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor det_st=sqLiteDatabase.rawQuery("select register_number,student_name,available_cards from student",null);
                StringBuffer stringBuffer=new StringBuffer();
                while (det_st.moveToNext()){
                    stringBuffer.append("\n Register Number :\t "+det_st.getString(0));
                    stringBuffer.append("\n Student Name :\t "+det_st.getString(1));
                    stringBuffer.append("\n Available cards :\t "+det_st.getString(2)+"\n\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(ViewData.this);
                builder.setTitle("Student details");
                builder.setMessage(stringBuffer);
                builder.setCancelable(true);

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        bo_de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor det_st=sqLiteDatabase.rawQuery("select bookid,book_name,author,publication from book",null);
                StringBuffer stringBuffer=new StringBuffer();
                while (det_st.moveToNext()){
                    stringBuffer.append("\n Book ID :\t "+det_st.getString(0));
                    stringBuffer.append("\n Book Name :\t "+det_st.getString(1));
                    stringBuffer.append("\n Author :\t "+det_st.getString(2));
                    stringBuffer.append("\n Publication :\t "+det_st.getString(3));
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(ViewData.this);
                builder.setTitle("Book details");
                builder.setMessage(stringBuffer);
                builder.setCancelable(true);

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        issue_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor det_st=sqLiteDatabase.rawQuery("select * from issue",null);
                StringBuffer stringBuffer=new StringBuffer();
                while (det_st.moveToNext()){
                    stringBuffer.append("\n Register Number :\t "+det_st.getString(0));
                    stringBuffer.append("\n Book ID :\t "+det_st.getString(1));
                    stringBuffer.append("\n Book Taken Date :\t "+det_st.getString(2));
                    stringBuffer.append("\n Return Date :\t "+det_st.getString(3));
                    stringBuffer.append("\n Due Date :\t "+det_st.getString(4));
                    stringBuffer.append("\n Status :\t "+det_st.getString(5)+"\n\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(ViewData.this);
                builder.setTitle("Book details");
                builder.setMessage(stringBuffer);
                builder.setCancelable(true);

                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }
}
