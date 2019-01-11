package jp.ac.anan_nct.mhx.mhx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import java.sql.Blob;

public class SetHomeworkActivity extends AppCompatActivity {

    Context context = this;

    private final static int RESULT_CAMERA = 1001;
    private DBOperator dbo;

    final String EXTRA_IS_IMAGE = "SetHomeWorkActivity.EXTRA_IS_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_homework);

        Button cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_CAMERA);
            }
        });

        //"スキップ"ボタン
        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable dr = getDrawable(R.drawable.no_image);
                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();

                if(dbo == null) dbo = new DBOperator(context);
                Data d = new Data(null, bitmap, -1, -1, -1, null, null);
                dbo.insertData(d);

                Intent intent = new Intent(getApplication(), SetHomework2Activity.class);
                intent.putExtra(EXTRA_IS_IMAGE, false);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA) {
            Bitmap bitmap;
            bitmap = data.getParcelableExtra("data");

            if(dbo == null) dbo = new DBOperator(this);
            Data d = new Data(null, bitmap, -1, -1, -1, null, null);
            dbo.insertData(d);

            Intent intent = new Intent(getApplication(), SetHomework2Activity.class);
            intent.putExtra(EXTRA_IS_IMAGE, true);
            startActivity(intent);
            finish();
        }
    }
}
