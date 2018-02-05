package practice.pre.accball;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//インターフェイスを追加(implements) //
public class MainActivity extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback{

    SensorManager mSensorManager;
    Sensor mAccSensor;
    SurfaceHolder mHolder;
    int mSurfaceWidth; //サーフェスビューの幅
    int mSurfaceHeight; //サーフェスビューの高さ

    //変数を用意
    static final float RADIUS = 50.0f;  //ボールを描画する時の半径を表す定数
    static final float COEF = 1000.0f;  //ボールの移動量を調整するための係数

    float mBallX;   //ボールの現在のx座標
    float mBallY;   //ボールの現在のy座標
    float mVX;  //ボールのx軸方向への速度
    float mVY;  //ボールのy軸方向への速度

    long mFrom; //前回、センサーから加速度を取得した時間
    long mTo;   //今回、センサーから加速度を取得した時間



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        setContentView( R.layout.activity_main );

        //センサーマネージャーとセンサーを取得する
        mSensorManager = (SensorManager)getSystemService( Context.SENSOR_SERVICE );
        mAccSensor = mSensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );

        SurfaceView surfaceView = (SurfaceView) findViewById( R.id.surfaceView );
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);  //イベントリスナーを登録

    }

    @Override //onSensorChangedを実装する
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.d("MainActivity",
                    "x="+String.valueOf(event.values[0]) +
                            "y=" + String.valueOf( event.values[1]) +
                            "z=" + String.valueOf(event.values[2]));

            //ボールの位置を計算する
            //センサーから取得したx,y,z方向の加速度データ変数を代入
            float x = -event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //経過時間を得る
            mTo = System.currentTimeMillis();
            float t = (float)(mTo - mFrom);
            t = t / 1000.0f;

            //ボールの移動距離dを求める
            float dx = mVX * t + x * t * t / 2.0f;
            float dy = mVY * t + y * t * t /2.0f;

            //移動後のボールの座標
            mBallX = mBallX + dx * COEF;
            mBallY = mBallY + dy * COEF;

            //速度をmVX,mVYに保存
            mVX = mVX + x * t;
            mVY = mVY + y * t;

            //ボールを画面内に収める
            if (mBallX - RADIUS < 0 && mVX < 0) {
                mVX = -mVX / 1.5f;
                mBallX = RADIUS;
            } else if (mBallX + RADIUS > mSurfaceWidth && mVX > 0) {
                mVX = -mVX / 1.5f;
                mBallX = mSurfaceWidth - RADIUS;
            }

            if (mBallY - RADIUS < 0 && mVY < 0){
                mVY = -mVY / 1.5f;
                mBallY = RADIUS;
            } else if (mBallY + RADIUS > mSurfaceHeight && mVY > 0){
                mVY = -mVY / 1.5f;
                mBallY = mSurfaceHeight - RADIUS;
            }

            //ボール移動後の現在時間を変数に取得
            mFrom = mTo;

            drawCanvas();
            //

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


//    センサーの監視を開始する
//    protected void onResume(){
//        super.onResume();
//        mSensorManager.registerListener( this, mAccSensor, SensorManager.SENSOR_DELAY_GAME );
//    }
//
//    センサーの監視を終了する
//
//    protected void onPause(){
//        super.onPause();
//        mSensorManager.unregisterListener( this );
//    }

    @Override //サーフェスが最初に作成された後に実行する処理を定義
    // 経過時間の初期設定
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mFrom = System.currentTimeMillis();
        mSensorManager.registerListener( this, mAccSensor, SensorManager.SENSOR_DELAY_GAME );
    }

    @Override   //サーフェスが変更された時に呼ばれる、サーフェスの幅と高さを格納
    //ボール位置の初期設定
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
        //ボールの初期位置を画面中央に設定
        mBallX = width / 2;
        mBallY = height / 2;
        //最初の速度は0で初期化
        mVX = 0;
        mVY = 0;
    }

    @Override   //サーフェスが破棄される直前に実行する処理を定義
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSensorManager.unregisterListener( this );

    }

    private void drawCanvas() {
        Canvas c = mHolder.lockCanvas(); //インスタンス取得
        c.drawColor( Color.YELLOW );    //画面を黄色に塗りつぶし
        Paint paint = new Paint();  //マゼンダを指定
        paint.setColor(Color.MAGENTA);
        c.drawCircle( mBallX, mBallY, RADIUS, paint );  //円を描く
        mHolder.unlockCanvasAndPost(c);   //アンロックする
    }

}
