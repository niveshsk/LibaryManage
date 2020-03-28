package com.nivesh.libarymanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button add_student=findViewById(R.id.addstudent);
        Button add_book=findViewById(R.id.addbook);
        Button issue=findViewById(R.id.issue);
        Button return_b=findViewById(R.id.return_book);
        Button view=findViewById(R.id.view_data);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Choose.this,ViewData.class);
                startActivity(intent);
            }
        });
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Choose.this,Add_Student.class);

                startActivity(intent);
            }
        });
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Choose.this,Add_Book.class);

                startActivity(intent);
            }
        });
issue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Choose.this,Issue_book.class);

        startActivity(intent);
    }
});
return_b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Choose.this,Return_book.class);
        startActivity(intent);
    }
});
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        super.onBackPressed();
    }
}
