package com.steadfastinnovation.androidsvg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DemoRenderer extends AppCompatActivity {

    RenderView mRenderView;

    private static float ZOOM_IN = 0.75f;
    private static float ZOOM_OUT = 1f/0.75f;
    private static float TRANSLATE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_renderer);

        mRenderView = (RenderView) findViewById(R.id.svg_renderer);

        Button button = (Button) findViewById(R.id.zoom_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderView.scale(ZOOM_IN);
            }
        });

        button = (Button) findViewById(R.id.zoom_out_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderView.scale(ZOOM_OUT);
            }
        });


        button = (Button) findViewById(R.id.up_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderView.translateY(TRANSLATE);
            }
        });

        button = (Button) findViewById(R.id.down_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderView.translateY(-TRANSLATE);
            }
        });

        button = (Button) findViewById(R.id.left_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderView.translateX(TRANSLATE);
            }
        });

        button = (Button) findViewById(R.id.right_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderView.translateX(-TRANSLATE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo_renderer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
