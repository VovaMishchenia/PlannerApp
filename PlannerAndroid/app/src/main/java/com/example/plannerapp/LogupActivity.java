package com.example.plannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.plannerapp.dto.BedRequestDTO;
import com.example.plannerapp.dto.LoginResultDTO;
import com.example.plannerapp.dto.RegistrationDTO;
import com.example.plannerapp.network.services.AccountService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.Instant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogupActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        mToolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logup_menu,menu);

        return true;

    }
    public void onLogInClick(MenuItem view)
    {
        Intent intent = new Intent(LogupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void onLoginUpCklick(View view)
    {
        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);

        final TextInputEditText password = findViewById(R.id.textInputPassword);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);

        final TextInputEditText name = findViewById(R.id.textInputName);
        final TextInputLayout nameLayout = findViewById(R.id.textFieldName);

        RegistrationDTO dto=new RegistrationDTO(name.getText().toString(),name.getText().toString(),
                email.getText().toString(),password.getText().toString());

        AccountService.getInstance()
                .getJSONApi()
                .registration(dto).enqueue(new Callback<LoginResultDTO>() {
            @Override
            public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("server", "Good");
                } else {
                    try {
                        String json = response.errorBody().string();
                        Gson gson = new Gson();
                        BedRequestDTO result = gson.fromJson(json, BedRequestDTO.class);
                        if(result.getErrors().containsKey("UserName"))
                            nameLayout.setError(result.getErrors().get("UserName")[0]);
                        else
                            nameLayout.setError("");

                        if(result.getErrors().containsKey("Email"))
                            emailLayout.setError(result.getErrors().get("Email")[0]);
                        else
                            emailLayout.setError("");
                        if(result.getErrors().containsKey("Password"))
                            passwordLayout.setError(result.getErrors().get("Password")[0]);
                        else
                            passwordLayout.setError("");


                    } catch (IOException ex) {
                        email.setText(ex.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResultDTO> call, Throwable t) {
                Log.e("server", "Bad");
            }
        });
    }
}

