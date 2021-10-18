package vn.edu.usth.irc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InterfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);

        ImageView buttonLogout = findViewById(R.id.buttonLogout);
        LinearLayout user1 = findViewById(R.id.user1);
        LinearLayout user2 = findViewById(R.id.user2);
        LinearLayout user3 = findViewById(R.id.user3);
        LinearLayout user4 = findViewById(R.id.user4);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialoge=new Dialog(InterfaceActivity.this,R.style.Dialoge);

                dialoge.setContentView(R.layout.dialoge_layout);

                TextView buttonYes = dialoge.findViewById(R.id.buttonYes);
                TextView buttonNo = dialoge.findViewById(R.id.buttonNo);

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(InterfaceActivity.this, LoginActivity.class));
                    }
                });

                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialoge.dismiss();
                    }
                });

                dialoge.show();
            }
        });
        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterfaceActivity.this, ChatActivity.class));
            }
        });
        user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterfaceActivity.this, ChatActivity.class));
            }
        });
        user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterfaceActivity.this, ChatActivity.class));
            }
        });
        user4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterfaceActivity.this, ChatActivity.class));
            }
        });


    }
}