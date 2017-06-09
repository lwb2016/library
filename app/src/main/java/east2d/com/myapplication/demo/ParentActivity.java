package east2d.com.myapplication.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import east2d.com.myapplication.R;
import east2d.com.myapplication.view.ParentScrollFrameLayout;

public class ParentActivity extends Activity {

    ParentScrollFrameLayout mParentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_scroll_view);
        mParentActivity= (ParentScrollFrameLayout) findViewById(R.id.p_root);
        mParentActivity.setScale(0.7f);
        mParentActivity.setRound(200);
        mParentActivity.setDuration(400);
        mParentActivity.setOnFrontChildClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ParentActivity.this, "vvvv", Toast.LENGTH_SHORT).show();
            }
        });
        mParentActivity.initData();
    }
}
