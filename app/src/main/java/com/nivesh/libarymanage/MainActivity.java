package com.nivesh.libarymanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("create table if not exists login(username varchar,password varchar,role varchar);");
        sqLiteDatabase.execSQL("create table if not exists issue(register_number varchar ,bookid varchar,take_date varchar,return_date varchar,due_date varchar,status varchar);");
        sqLiteDatabase.execSQL("create table if not exists student(register_number varchar primary key,student_name varchar,student_department varchar,emailid varchar,mobile_number varchar,available_cards int,dob varchar,username varchar,password varchar);");
        sqLiteDatabase.execSQL("create table if not exists book(bookid varchar primary key,book_name varchar,author varchar,publication varchar,available_books int,remainig int);");
        sqLiteDatabase.execSQL("insert into login values('admin','admin','librarian');");
        sqLiteDatabase.execSQL("insert into login values('librarian','librarian','librarian');");
        final Button login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                EditText user=findViewById(R.id.username);
                EditText pass=findViewById(R.id.password);
                String username=user.getText().toString();
                String password=pass.getText().toString();
                if(username.equals("")){
                    user.setError("must not empty");
                    login.setEnabled(true);
                   if(password.equals("")){
                       user.setError("must not empty");
                       login.setEnabled(true);
                   }
                }else {

                    Cursor log=sqLiteDatabase.rawQuery("select * from login where username='"+username+"'and password='"+password+"'and role='librarian'",null);
                    if(log.moveToNext()){
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        SharedPreferences cache = getSharedPreferences("cache", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=cache.edit();
                        edit.putString("username",username);
                        edit.putString("password",password);
                        edit.putString("role","librarian");
                        edit.apply();

                        Intent intent=new Intent(MainActivity.this,Choose.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                    else{
                        login.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    protected void onStart() {
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);
        SharedPreferences cache = getSharedPreferences("cache", Context.MODE_PRIVATE);
        String us_na=cache.getString("username",null);
        String us_pa=cache.getString("password",null);
        String us_ro=cache.getString("role",null);
        if(us_na != null && us_pa != null && us_ro !=null){
            Cursor log=sqLiteDatabase.rawQuery("select * from login where username='"+us_na+"'and password='"+us_pa+"'and role='"+us_ro+"'",null);
            if(log.moveToNext()){
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivity.this,Choose.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
