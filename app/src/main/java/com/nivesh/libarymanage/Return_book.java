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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Return_book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);
        final Button ret_bo=findViewById(R.id.ret_bo);
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);

        ret_bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ret_bo.setEnabled(false);
                EditText st_id=findViewById(R.id.re_st_id);
                EditText bo_id=findViewById(R.id.re_bo_id);
                String stu_id=st_id.getText().toString();
                String boo_id=bo_id.getText().toString();

                if (stu_id.equals("")){
                    st_id.setError("must not empty");
                    ret_bo.setEnabled(true);
                }
                if (boo_id.equals("")){
                    bo_id.setError("must not empty");
                    ret_bo.setEnabled(true);
                }
                else{
                    Cursor st_ch=sqLiteDatabase.rawQuery("select * from student where register_number='"+stu_id+"'",null);
                    String st_re_ch="STUDENT_NOT_OK";
                    if(st_ch.moveToNext()){
                        int cards=st_ch.getInt(st_ch.getColumnIndex("available_cards"));
                        if(cards<5){
                            st_re_ch="STUDENT_OK";
                        }
                        else{
                            st_id.setError("Don't taken any books");
                            ret_bo.setEnabled(true);
                        }
                    }
                    st_ch.close();
                    Cursor boo_ch=sqLiteDatabase.rawQuery("select * from book where bookid='"+boo_id+"'",null);
                    String bo_re_ch="BOOK_TAKEN";
                    if(st_ch.moveToNext()){
                        int boo_re=boo_ch.getInt(st_ch.getColumnIndex("remainig"));
                        int boo_tot=boo_ch.getInt(st_ch.getColumnIndex("available_books"));
                        if(boo_re == boo_tot){
                            bo_re_ch="BOOK_NOT_TAKEN";
                            bo_id.setError("Book not taken");
                            ret_bo.setEnabled(true);
                        }
                        else{

                        }
                    }
                    Cursor is_ch=sqLiteDatabase.rawQuery("select * from issue where register_number='"+stu_id+"'and bookid='"+boo_id+"'",null);
                    String bo_tak_no="NOT_TAKEN";
                    if(is_ch.moveToNext()){
                         bo_tak_no="TAKEN";
                    }
                    if(st_re_ch.equals("STUDENT_OK") && bo_re_ch.equals("BOOK_TAKEN") && bo_tak_no.equals("TAKEN")){
                        sqLiteDatabase.execSQL("update student set available_cards=available_cards+1 where register_number='"+stu_id+"'");
                        sqLiteDatabase.execSQL("update book set remainig=remainig+1 where bookid='"+boo_id+"'");
                        Calendar today=Calendar.getInstance();
                        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");
                        String to_da=format.format(today.getTime());

                        sqLiteDatabase.execSQL("update issue set return_date='"+to_da+"', status='submit' where register_number='"+stu_id+"'and bookid='"+boo_id+"'");
                        Toast.makeText(Return_book.this, "RETURNED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        st_id.setText("");
                        bo_id.setText("");
                        ret_bo.setEnabled(true);

                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Return_book.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}
