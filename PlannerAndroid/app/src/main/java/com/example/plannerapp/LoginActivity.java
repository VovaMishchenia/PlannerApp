package com.example.plannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.example.plannerapp.constants.Urls;
import com.example.plannerapp.dto.BedRequestDTO;
import com.example.plannerapp.dto.LoginDTO;
import com.example.plannerapp.dto.LoginResultDTO;
import com.example.plannerapp.network.ImageRequester;
import com.example.plannerapp.network.services.AccountService;
import com.example.plannerapp.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageRequester imageRequester;
    private NetworkImageView myImage;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String url = Urls.BASE + "/images/1.jpeg";
        imageRequester = imageRequester.getInstance();
        myImage = findViewById(R.id.myimg);
        imageRequester.setImageFromUrl(myImage, url);
        mToolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;

    }

    public void OnLogUpClick(MenuItem item)
    {
        Intent intent = new Intent(LoginActivity.this, LogupActivity.class);
        startActivity(intent);

    }

    public void OnClickBtn(View view) {

        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);
        final TextInputEditText password = findViewById(R.id.textInputPassword);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);
        LoginDTO dto = new LoginDTO(email.getText().toString(),
                password.getText().toString());

       emailLayout.setError("");
       passwordLayout.setError("");
        CommonUtils.showLoading(this);

        AccountService.getInstance()
                .getJSONApi()
                .login(dto)
                .enqueue(new Callback<LoginResultDTO>() {
                    @Override
                    public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            Log.d("server", "Good");
                        } else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                BedRequestDTO result = gson.fromJson(json, BedRequestDTO.class);
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


        Log.d("Click me", email.getText().toString());



    }
}