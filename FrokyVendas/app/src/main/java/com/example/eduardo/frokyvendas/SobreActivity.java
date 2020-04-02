package com.example.eduardo.frokyvendas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        Button btnvoltar1 = (Button) findViewById(R.id.btnvoltar1);
        btnvoltar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(SobreActivity.this,CatalogoActivity.class);
                startActivity(it);
            }
        });
    }
}
