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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Issue_book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        final Button issue=findViewById(R.id.bo_is);
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issue.setEnabled(false);
                EditText st_id=findViewById(R.id.st_id_is);
                EditText bo_id=findViewById(R.id.bo_id_is);
                String stu_id=st_id.getText().toString();
                String boo_id=bo_id.getText().toString();

                if (stu_id.equals("")){
                    st_id.setError("must not empty");
                    issue.setEnabled(true);
                }
                if (boo_id.equals("")){
                    bo_id.setError("must not empty");
                    issue.setEnabled(true);
                }
                else{
                    Cursor st_ch=sqLiteDatabase.rawQuery("select * from student where register_number='"+stu_id+"'",null);
                    String st_re_ch="STUDENT_NOT_OK";
                    if(st_ch.moveToNext()){


                        int cards=st_ch.getInt(st_ch.getColumnIndex("available_cards"));
                            if(cards>0){


                                st_re_ch="STUDENT_OK";
                            }
                            else{
                                st_id.setError("Limit Reached");
                            }
                    }
                    st_ch.close();
                    Cursor boo_ch=sqLiteDatabase.rawQuery("select * from book where bookid='"+boo_id+"'",null);
                    String bo_re_ch="BOOK_UNAVAILABLE";
                    if(boo_ch.moveToNext()){

                        int boo_re=boo_ch.getInt(boo_ch.getColumnIndex("remainig"));
                        if(boo_re>0){
                            bo_re_ch="BOOK_AVAILABLE";
                        }
                        else{
                            bo_id.setError("Book ");
                        }
                    }
                    System.out.println(st_re_ch+bo_re_ch);
                    if(st_re_ch.equals("STUDENT_OK") && bo_re_ch.equals("BOOK_AVAILABLE")){

                        sqLiteDatabase.execSQL("update student set available_cards=available_cards-1 where register_number='"+stu_id+"'");
                        sqLiteDatabase.execSQL("update book set remainig=remainig-1 where bookid='"+boo_id+"'");
                        Calendar today=Calendar.getInstance();
                        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");
                        String to_da=format.format(today.getTime());
                        today.add(Calendar.DAY_OF_MONTH,60);
                        String due_date=format.format(today.getTime());
                        sqLiteDatabase.execSQL("insert into issue values('"+stu_id+"','"+boo_id+"','"+to_da+"','-','"+due_date+"','pending')");
                        Toast.makeText(Issue_book.this, "ISSUED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        st_id.setText("");
                        bo_id.setText("");
                        issue.setEnabled(true);

                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Issue_book.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
