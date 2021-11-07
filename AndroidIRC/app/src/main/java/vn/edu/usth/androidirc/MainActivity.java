package vn.edu.usth.androidirc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText CreateServer, OpenServer;
    private Button CreateServer_Cancel, CreateServer_Submit, OpenServer_cancel, OpenServer_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            Toast.makeText(getApplicationContext(), "you click exit", Toast.LENGTH_SHORT).show();
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
}