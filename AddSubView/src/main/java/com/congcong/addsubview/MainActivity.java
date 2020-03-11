package com.congcong.addsubview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private AddSubView add_sub_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_sub_view = findViewById(R.id.add_sub_view);
        add_sub_view.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                Toast.makeText(MainActivity.this, "当前产品数量=="+value, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
