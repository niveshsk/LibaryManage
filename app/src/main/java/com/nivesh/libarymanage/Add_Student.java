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

public class Add_Student extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__student);
        final Button ad_st=findViewById(R.id.add_st);
        final SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("library", Context.MODE_PRIVATE,null);
        ad_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad_st.setEnabled(false);
                EditText st_reg_num=findViewById(R.id.stu_reg_num);
                EditText st_na=findViewById(R.id.stu_name);
                EditText st_em_id=findViewById(R.id.stu_email_id);
                EditText st_mob_num=findViewById(R.id.mobile_number);
                EditText st_dept=findViewById(R.id.dept);
                EditText st_dob=findViewById(R.id.date_stu);
                EditText st_user=findViewById(R.id.st_user);
                EditText st_pass=findViewById(R.id.stu_pass);
                EditText st_con_pass=findViewById(R.id.con_pass);
                String stu_reg_num=st_reg_num.getText().toString();
                String stu_na=st_na.getText().toString();
                String stu_em_id=st_em_id.getText().toString();
                String stu_mob_num=st_mob_num.getText().toString();
                String stu_dept=st_dept.getText().toString();
                String stu_dob=st_dob.getText().toString();
                String stu_user=st_user.getText().toString();
                String stu_pass=st_pass.getText().toString();
                String stu_con_pass=st_con_pass.getText().toString();

                if (stu_reg_num.equals("")){
                    st_reg_num.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_na.equals("")){
                    st_na.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_em_id.equals("")){
                    st_em_id.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_mob_num.equals("")){
                    st_mob_num.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_dept.equals("")){
                    st_dept.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_dob.equals("")){
                    st_dob.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_user.equals("")){
                    st_user.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (stu_pass.equals("") || stu_con_pass.equals("")){
                    st_pass.setError("must not empty");
                    st_con_pass.setError("must not empty");
                    ad_st.setEnabled(true);
                }
                if (!stu_pass.equals(stu_con_pass)){
                    st_pass.setError("must be equal with confirm password");
                    st_con_pass.setError("must be equal with password");
                    ad_st.setEnabled(true);
                }
                else {
                    ad_st.setEnabled(false);
                    Cursor exi_bo_id=sqLiteDatabase.rawQuery("select * from student where register_number='"+stu_reg_num+"'",null);
                    if(exi_bo_id.moveToNext()){
                        Toast.makeText(Add_Student.this, "already exists", Toast.LENGTH_SHORT).show();
                        ad_st.setEnabled(true);
                    }
                    else {

                        sqLiteDatabase.execSQL("insert into student values ('" + stu_reg_num + "','" + stu_na + "','" + stu_dept + "','" + stu_em_id + "'," + stu_mob_num + ",5,'" + stu_dob + "','"+stu_user+"','"+stu_pass+"')");
                        sqLiteDatabase.execSQL("insert into login values('"+stu_user+"','"+stu_pass+"','student');");


                        Toast.makeText(Add_Student.this, "student added successfully", Toast.LENGTH_SHORT).show();
                         st_reg_num.setText("");
                         st_na.setText("");
                         st_em_id.setText("");
                         st_mob_num.setText("");
                         st_dept.setText("");
                         st_dob.setText("");
                         st_user.setText("");
                         st_pass.setText("");
                         st_con_pass.setText("");
                        ad_st.setEnabled(true);
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Add_Student.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
