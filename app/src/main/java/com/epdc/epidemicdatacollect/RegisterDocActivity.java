package com.epdc.epidemicdatacollect;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterDocActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editDocTextName;
    private EditText editDocTextUsername;
    private EditText editDocTextPassword;
    private EditText editDocTextEmail;

    private Button buttonDocRegister;

    private static final String REGISTER_URL = "http://192.168.8.101/Android/UserRegistration/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doc);

        editDocTextName = (EditText) findViewById(R.id.editTextDocName);
        editDocTextUsername = (EditText) findViewById(R.id.editTextDocUserName);
        editDocTextPassword = (EditText) findViewById(R.id.editTextDocPassword);
        editDocTextEmail = (EditText) findViewById(R.id.editTextDocEmail);

        buttonDocRegister = (Button) findViewById(R.id.buttonDocRegister);

        buttonDocRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonDocRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String name = editDocTextName.getText().toString().trim().toLowerCase();
        String username = editDocTextUsername.getText().toString().trim().toLowerCase();
        String password = editDocTextPassword.getText().toString().trim().toLowerCase();
        String email = editDocTextEmail.getText().toString().trim().toLowerCase();

        register(name,username,password,email);
    }

    private void register(String name, String username, String password, String email) {
        String urlSuffix = "?name="+name+"&username="+username+"&password="+password+"&email="+email;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterDocActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }
}
