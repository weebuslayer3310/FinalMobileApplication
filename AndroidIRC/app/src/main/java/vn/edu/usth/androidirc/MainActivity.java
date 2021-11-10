package vn.edu.usth.androidirc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText CreateServer, OpenServer, ee;
    private Button WelcomeMessage, CreateServer_Cancel, CreateServer_Submit, OpenServer_cancel, OpenServer_submit;

    String name;

    DatabaseReference reference;
    ListView l1;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request_name();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        l1 = findViewById(R.id.ViewServer);
        list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        l1.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().getRoot();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> myset = new HashSet<String>();
                Iterator i = snapshot.getChildren().iterator();
                while(i.hasNext()){
                    myset.add(((DataSnapshot)i.next()).getKey());
                }
                list.clear();
                list.addAll(myset);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Sorry, there are some problems with network....", Toast.LENGTH_SHORT).show();
            }
        });

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.about){
            Toast.makeText(getApplicationContext(), "you click about", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.setting){
            Toast.makeText(getApplicationContext(), "you click setting", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.exit){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            // Toast.makeText(getApplicationContext(), "you click exit", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.existing){

            OpenServer_Window();
            // Toast.makeText(getApplicationContext(), "you click existing server..", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.CreateNew){
            CreateServer_Window();
            // Toast.makeText(getApplicationContext(), "you click Create new server.....", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void WelcomeMessage_Window(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View WelcomeMessagePopup = getLayoutInflater().inflate(R.layout.wmpopup, null);
        WelcomeMessage = WelcomeMessagePopup.findViewById(R.id.WelcomeButton);

        dialogBuilder.setView(WelcomeMessagePopup);
        dialog = dialogBuilder.create();
        dialog.show();

        WelcomeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void CreateServer_Window(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View CreateServerPopup = getLayoutInflater().inflate(R.layout.cspopup, null);
        CreateServer = CreateServerPopup.findViewById(R.id.CreateServer);
        CreateServer_Cancel = CreateServerPopup.findViewById(R.id.csCancel);
        CreateServer_Submit = CreateServerPopup.findViewById(R.id.csSubmit);

        dialogBuilder.setView(CreateServerPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        CreateServer_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        CreateServer_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(CreateServer.getText().toString(), "chatroom");
                reference.updateChildren(hashMap);

                list.add(CreateServer.getText().toString());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    public void OpenServer_Window(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View OpenServerPopup = getLayoutInflater().inflate(R.layout.ospopup, null);
        OpenServer = OpenServerPopup.findViewById(R.id.OpenServer);
        OpenServer_cancel = OpenServerPopup.findViewById(R.id.osCancel);
        OpenServer_submit = OpenServerPopup.findViewById(R.id.osSubmit);

        dialogBuilder.setView(OpenServerPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        OpenServer_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        OpenServer_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void request_name(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Enter your name?");
        ee = new EditText(this);
        alertDialog.setView(ee);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = ee.getText().toString();
            }
        });
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
                   request_name();
            }
        });
        alertDialog.show();
    }
}