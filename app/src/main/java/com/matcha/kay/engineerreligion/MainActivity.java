package com.matcha.kay.engineerreligion;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "メッセージを入力してください", Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("er");
                    Comment comment= new Comment();
                    comment.setComment(et.getText().toString());
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH時mm分");
                    String time = sdf.format(date);
                    comment.setTime(time);
                    sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    long order = Long.parseLong(sdf.format(date));
                    order = - order;
                    comment.setOrder(order);
                    myRef.push().setValue(comment);
                    et.setText("");

                    Toast.makeText(MainActivity.this, "投稿完了", Toast.LENGTH_LONG).show();
                }
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dr = database.getReference("er");
        dr.orderByChild("order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinearLayout layout = findViewById(R.id.linearLayout);
                //view削除
                layout.removeAllViews();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.my_card, null);
                    TextView tv_comment = linearLayout.findViewById(R.id.comment);
                    TextView tv_time = linearLayout.findViewById(R.id.time);
                    tv_comment.setText((String)data.child("comment").getValue());
                    tv_time.setText(String.valueOf(data.child("time").getValue()));
                    layout.addView(linearLayout);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // データの取得に失敗
            }
        });
    }
}
