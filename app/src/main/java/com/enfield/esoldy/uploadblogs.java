package com.enfield.esoldy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enfield.esoldy.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class uploadblogs extends AppCompatActivity {

    private EditText blogtitle;
    private EditText blog;
    private Button uploadbtn;
    private String productRandomkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadblogs);

        blogtitle=findViewById(R.id.titleblog);
        blog=findViewById(R.id.blog_description);
        uploadbtn=findViewById(R.id.uploadbtn);


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadblog();
            }
        });
    }

    private void uploadblog() {

        String savecurrenttime,savecurrentdate;
        Calendar calfordate= Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss");



        savecurrenttime=currenttime.format(calfordate.getTime());


        productRandomkey=savecurrentdate +" "+ savecurrenttime;

        DatabaseReference blogref= FirebaseDatabase.getInstance().getReference().child("Facts And Blogs");
        final HashMap<String,Object> blogmap=new HashMap<>();
        blogmap.put("Blog_Title",blogtitle.getText().toString());
        blogmap.put("Blog_Content",blog.getText().toString());
        blogmap.put("Blog_Date",savecurrentdate);
        blogmap.put("Blog_time",savecurrenttime);
        blogref.child("User Blog Uploads").child(Prevalent.OnlineUser.getPhone())
                .child(productRandomkey)
                .updateChildren(blogmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(uploadblogs.this, NavgiationhomeActivity.class));
                            finish();
                            Toast.makeText(uploadblogs.this, "Blog updated Sucessfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(uploadblogs.this, "Try again...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}