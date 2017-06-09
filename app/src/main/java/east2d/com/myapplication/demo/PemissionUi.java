package east2d.com.myapplication.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import east2d.com.myapplication.R;

public class PemissionUi extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemission_ui);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                break;
            case R.id.btn2:
                break;
        }
    }
}
