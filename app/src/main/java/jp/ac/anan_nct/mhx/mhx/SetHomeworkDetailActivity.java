package jp.ac.anan_nct.mhx.mhx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class SetHomeworkDetailActivity extends AppCompatActivity {

    Spinner tagsSpinner;
    CheckBox[] notifArray;
    EditText memoText;
    Button compButton;

    String tag;

    final String EXTRA_TAGS = "SetHomeworkDetailActivity.EXTRA_TAGS";
    final String EXTRA_NOTIF = "SetHomeworkDetailActivity.EXTRA_NOTIF";
    final String EXTRA_MEMO = "SetHomeworkDetailActivity.EXTRA_MEMO";

    final int RESULT_DETAIL = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_set_homework_detail);

        Intent intent = new Intent(getApplication(), SetHomework2Activity.class);

        tagsSpinner = findViewById(R.id.tagsSpinner);
        notifArray = new CheckBox[]{findViewById(R.id.notif7), findViewById(R.id.notif3), findViewById(R.id.notif2),
                findViewById(R.id.notif1), findViewById(R.id.notif0)};
        memoText = findViewById(R.id.memoText);

        //タグ設定
        String[] tags = new String[]{"タグ1", "タグ2", "タグ3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagsSpinner.setAdapter(adapter);
        int index = intent.getIntExtra(EXTRA_TAGS, -1);
        if(index != -1) tagsSpinner.setSelection(index);

        //タグ取得
        tagsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int position,long id) {
                Spinner spinner = (Spinner)parent;
                tag = spinner.getSelectedItem().toString();
            }
            // 何も選択されなかった時の動作
            public void onNothingSelected(AdapterView parent) {
            }
        });

        //"完了"ボタン
        compButton = findViewById(R.id.completeDetailButton);
        compButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SetHomework2Activity.class);

                String notif = (notifArray[0].isChecked() ? "7," : "") +
                        (notifArray[1].isChecked() ? "3," : "") +
                        (notifArray[2].isChecked() ? "2," : "") +
                        (notifArray[3].isChecked() ? "1," : "") +
                        (notifArray[4].isChecked() ? "0," : "");

                intent.putExtra(EXTRA_TAGS, tag);
                intent.putExtra(EXTRA_NOTIF, notif);
                intent.putExtra(EXTRA_MEMO, memoText.getText().toString());
                setResult(RESULT_DETAIL, intent);
                finish();
            }
        });
    }
}
