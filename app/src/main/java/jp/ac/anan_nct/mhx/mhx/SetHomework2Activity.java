package jp.ac.anan_nct.mhx.mhx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class SetHomework2Activity extends AppCompatActivity {

    Context context = this;

    DBOperator dbo = null;
    EditText homeworkName;
    CalendarView deadlineCalendar;

    String name = "";
    int deadline = -1;
    int tag = -1;
    String notif = "";
    String memo = "";

    final String EXTRA_IS_IMAGE = "SetHomeWorkActivity.EXTRA_IS_IMAGE";
    final String EXTRA_TAGS = "SetHomeworkDetailActivity.EXTRA_TAGS";
    final String EXTRA_NOTIF = "SetHomeworkDetailActivity.EXTRA_NOTIF";
    final String EXTRA_MEMO = "SetHomeworkDetailActivity.EXTRA_MEMO";

    final int REQUEST_DETAIL = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_set_homework2);

        Intent intent = getIntent();

        if(dbo == null) dbo = new DBOperator(this);
        String sql = "select * from (homeworks cross join (select max(homeworks.id) as maxid from homeworks)) where id = maxid";
        final Data[] d = dbo.selectDataBySQL(sql);
        Log.d(this.toString(), "id = " + d[0].id);

        final ImageView homeworkImage = findViewById(R.id.homeworkImage);
        homeworkImage.setImageBitmap(d[0].image);

        //課題名
        homeworkName = findViewById(R.id.homeworkNameText);

        //締め切り
        deadlineCalendar = findViewById(R.id.deadlineCalendar);
        //****初期表示は1週間後(にしたい)
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(deadlineCalendar.getDate());
        deadline = cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
        Log.d("deadline", String.valueOf(deadline));
        //日付更新時
        deadlineCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                deadline = year * 10000 + (month + 1) * 100 + dayOfMonth;
                Log.d("deadline", String.valueOf(deadline));
            }
        });

        //"完了"ボタン
        Button button = findViewById(R.id.completeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = homeworkName.getText().toString();
                Intent intent = new Intent(getApplication(), SetHomeworkDetailActivity.class);
                Boolean isImage = intent.getBooleanExtra(EXTRA_IS_IMAGE, false);
                if(isImage) {
                    dbo.updateData(d[0].id, new Data(name.equals("") ? ("課題" + d[0].id) : name, d[0].image, tag, deadline, 0, notif, memo));
                }else{
                    if(!name.equals("")){
                        isImage = true;
                        dbo.updateData(d[0].id, new Data(name, d[0].image, tag, deadline, 0, notif, memo));
                    }
                }

                final Boolean isName = isImage;
                //ポップアップ
                final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setMessage(isName ? "課題の登録が完了しました。" : "※課題名を入力してください。");
                dlg.setCancelable(true); //戻るキーで閉じる
                final AlertDialog alt = dlg.create();
                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        alt.dismiss();
                        if(isName) finish(); //終了
                    }
                };
                alt.show();
                handler.postDelayed(r, 3000); //3秒後に閉じる

                Log.d("********************", String.valueOf(d[0].id));
                Log.d("********************", name);
                Log.d("********************", String.valueOf(tag));
                Log.d("********************", String.valueOf(deadline));
                Log.d("********************", notif);
                Log.d("********************", memo);
            }
        });

        //"詳細設定"ボタン
        Button detailButton = findViewById(R.id.detailButton);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SetHomeworkDetailActivity.class);
                intent.putExtra(EXTRA_TAGS, tag);
                intent.putExtra(EXTRA_NOTIF, notif);
                intent.putExtra(EXTRA_MEMO, memo);
                startActivityForResult(intent, REQUEST_DETAIL);
            }
        });
    }

    //詳細設定の結果
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == REQUEST_DETAIL) {
            String tagStr = intent.getStringExtra(EXTRA_TAGS);
            //タグのString⇔int変換
            switch (tagStr) {
                case "タグ1":
                    tag = 1;
                    break;
                case "タグ2":
                    tag = 2;
                    break;
                case "タグ3":
                    tag = 3;
                    break;
            }

            notif = intent.getStringExtra(EXTRA_NOTIF);
            memo = intent.getStringExtra(EXTRA_MEMO);
        }
    }
}
