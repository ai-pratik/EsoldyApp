package com.enfield.esoldy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Addnewproductactivity extends AppCompatActivity {

    private String CategoryName,Description,Price,Pname,saveCurrentDate,saveCurrentTime;
    private Button add_new_product;
    private EditText Product_name,product_description,product_price;
    private ImageView product_image;
    private TextView categoryname;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String productRandomkey,downloadImageurl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewproductactivity);

        CategoryName=getIntent().getExtras().get("Category").toString();
        ProductImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef=FirebaseDatabase.getInstance().getReference().child("Products");
       // Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        product_image=(ImageView)findViewById(R.id.select_product_image);
        add_new_product=(Button)findViewById(R.id.add_new_product);
        Product_name=(EditText)findViewById(R.id.Product_name);
        product_description=(EditText)findViewById(R.id.product_description);
        product_price=(EditText)findViewById(R.id.product_price);
        categoryname=(TextView)findViewById(R.id.categoryname);

        loadingBar=new ProgressDialog(this);
        categoryname.setText(CategoryName);
        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }
    private  void OpenGallery(){
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            product_image.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData()
    {
        Description=product_description.getText().toString();
        Price=product_price.getText().toString();
        Pname=Product_name.getText().toString();

        if(ImageUri==null)
        {
            Toast.makeText(this, "Product Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Product DESCRIPTION is Required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Product Price is Required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Product name is Required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation(){
        loadingBar.setTitle("Adding Your Product For Sell");
        loadingBar.setMessage("Oh dear,Your Product is Adding Shortly");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss:ms a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomkey=saveCurrentDate +" "+ saveCurrentTime;

        StorageReference filePath=ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomkey + ".jpg");
        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(Addnewproductactivity.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Addnewproductactivity.this, "Product Image Uploaded Sucessfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urltask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                            downloadImageurl=filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageurl=task.getResult().toString();
                            Toast.makeText(Addnewproductactivity.this, "geting Product Image url  Sucessfully", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase()
    {

        HashMap<String,Object>productmap1=new HashMap<>();
        productmap1.put("PID",productRandomkey);
        //productmap1.put("Seller_Phone",Prevalent.OnlineUser.getPhone().toString());
        productmap1.put("Date",saveCurrentDate);
        productmap1.put("Time",saveCurrentTime);
        productmap1.put("Description",Description);
        productmap1.put("Image",downloadImageurl);
        productmap1.put("Category",CategoryName);
        productmap1.put("Price_INR",Price);
        productmap1.put("Product_Name",Pname);

        ProductRef.child(productRandomkey).updateChildren(productmap1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent addnewproductintent=new Intent(Addnewproductactivity.this, Addcategoryadminproduct.class);
                            startActivity(addnewproductintent);
                            loadingBar.dismiss();
                            Toast.makeText(Addnewproductactivity.this, "Product is added Sucessfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message=task.getException().toString();
                            Toast.makeText(Addnewproductactivity.this, "Error :"+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}