package edu.misao.android_life_cycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        button = findViewById( R.id.btn1 );

        button.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent( MainActivity.this,SecondActivity.class );
        startActivity( intent );

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i( TAG, "スタート: called " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i( TAG, "リスタート: called " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i( TAG, "デストロイ: called" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i( TAG, "パ-ス: called" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i( TAG, "リズーム: called" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i( TAG, "ストップ: called " );
        
    }
}


