package com.enfield.esoldy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Addcategoryadminproduct extends AppCompatActivity {
    private ImageView computer,laptop,smartphone,camera;
    private ImageView gameconsole,headphone,router,printer;
    private ImageView ac,fridge,iron,tv;
    private ImageView kettle,microwave,mixer,washingmachine;
    private ImageView vaccumcleaner,grinder,scale,fan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategoryadminproduct);

        computer=(ImageView)findViewById(R.id.computer);
        laptop=(ImageView)findViewById(R.id.laptop);
        smartphone=(ImageView)findViewById(R.id.smartphones);
        camera=(ImageView)findViewById(R.id.camera);

        gameconsole=(ImageView)findViewById(R.id.gameconsole);
        headphone=(ImageView)findViewById(R.id.headphones);
        router=(ImageView)findViewById(R.id.Router);
        printer=(ImageView)findViewById(R.id.printer);

        ac=(ImageView)findViewById(R.id.AC);
        fridge=(ImageView)findViewById(R.id.fridge);
        iron=(ImageView)findViewById(R.id.iron);
        tv=(ImageView)findViewById(R.id.TV);

        kettle=(ImageView)findViewById(R.id.kettel);
        microwave=(ImageView)findViewById(R.id.microwave);
        mixer=(ImageView)findViewById(R.id.mixer);
        washingmachine=(ImageView)findViewById(R.id.washingmachine);

        vaccumcleaner=(ImageView)findViewById(R.id.vaccum_cleaner);
        grinder=(ImageView)findViewById(R.id.grinder);
        scale=(ImageView)findViewById(R.id.weightscale);
        fan=(ImageView)findViewById(R.id.fan);


        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Computer and Accesories");
                startActivity(intent);
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Laptop");
                startActivity(intent);
            }
        });
        smartphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Smartphones");
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Camera And Equipments");
                startActivity(intent);
            }
        });

        gameconsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Game console");
                startActivity(intent);
            }
        });
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Headphone");
                startActivity(intent);
            }
        });

        router.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Wifi router");
                startActivity(intent);
            }
        });

        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Printers and Equipments");
                startActivity(intent);
            }
        });

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","AC");
                startActivity(intent);
            }
        });

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Fridge");
                startActivity(intent);
            }
        });

        iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Iron");
                startActivity(intent);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","tv");
                startActivity(intent);
            }
        });
        kettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Kettle");
                startActivity(intent);
            }
        });
        microwave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Microwave");
                startActivity(intent);
            }
        });
        mixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Mixer");
                startActivity(intent);
            }
        });
        washingmachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","washingmachine");
                startActivity(intent);
            }
        });

        vaccumcleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Vaccumcleaner");
                startActivity(intent);
            }
        });

        grinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Grinder");
                startActivity(intent);
            }
        });
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Scale");
                startActivity(intent);
            }
        });
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcategoryadminproduct.this,Addnewproductactivity.class);
                intent.putExtra("Category","Fan");
                startActivity(intent);
            }
        });
    }
}