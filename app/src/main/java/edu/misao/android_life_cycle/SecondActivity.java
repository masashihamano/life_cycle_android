package edu.misao.android_life_cycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SecondActivity";

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );

        button = findViewById( R.id.btn1 );

        button.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent( SecondActivity.this,MainActivity.class );
        startActivity( intent );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i( TAG, "ストップ: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i( TAG, "リズーム: " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i( TAG, "パース: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i( TAG,"リスタート" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"デストロイ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"スタート");
    }

}
