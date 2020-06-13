package com.example.dbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText, editText2;
    ListView listView, listView2;

    DBHelper dbHelper;  //만들어야할 클래스 파일
    SQLiteDatabase db = null; //SQLiteDatabase에는 SQL 명령을 생성, 삭제, 실행하고 기타 일반적인 데이터베이스 관리 작업을 수행하는 메소들이있다.
    Cursor cursor; //DB에서 가져온 데이터를 쉽게 처리하기 위해서 제공하는 2차원 테이블을 가지는 인터페이스이다.
    ArrayAdapter adapter, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        listView = findViewById(R.id.listView);
        listView2 = findViewById(R.id.listView2);

        dbHelper = new DBHelper(this, 3);
        db = dbHelper.getWritableDatabase();    // 읽기/쓰기 모드로 데이터베이스를 오픈


    }

    public void listUpdate(View v) {
        cursor = db.rawQuery("SELECT * FROM tableName", null);
        startManagingCursor(cursor);    //엑티비티의 생명주기와 커서의 생명주기를 같게 한다.

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);

        while (cursor.moveToNext()) {
            adapter.add(cursor.getString(0));
            adapter2.add(cursor.getString(1));
        }

        /*
        cursor.moveToFirst();
        cursor.moveToPrevious();
        cursor.moveToPosition(2);
        */

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
    }



    public void insert(View v) {
        String name = editText.getText().toString();
        String info = editText2.getText().toString();
        db.execSQL("INSERT INTO tableName VALUES ('" + name + "', '" + info + "');");

        Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();

        editText.setText("");
        editText2.setText("");
    }

    public void delete(View v) {
        String name = editText.getText().toString();
        db.execSQL("DELETE FROM tableName WHERE name = '" + name + "';");
    }

}
