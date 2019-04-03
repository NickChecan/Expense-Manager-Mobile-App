package com.expense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.expense.R;
import com.expense.interceptor.AuthorizationInterceptor;
import com.expense.model.TokenResponse;
import com.expense.service.AccessService;
import com.expense.setting.AuthenticationManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private Button signInButton;

    private TextView signUpText;

    private EditText emailInput;

    private EditText passwordInput;

    AuthenticationManager authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInButton = findViewById(R.id.signInButtonId);
        signUpText = findViewById(R.id.signUpTextId);
        emailInput = findViewById(R.id.emailInputId);
        passwordInput = findViewById(R.id.passwordInputId);

        // Prepare the shared preferences for token management operations
        authentication = new AuthenticationManager(getApplicationContext());

        // Remove top bar application
        getSupportActionBar().hide();

        // Call Sign Up activity
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        // Request access token and proceed with the sign in functionality
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor("client", "123"))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Cannot be localhost due to the virtual machine
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        // Create the http service for access request
        AccessService accessService = retrofit.create(AccessService.class);
        Call<TokenResponse> httpRequest = accessService.requestAccessToken(
                "client", emailInput.getText().toString(),
                passwordInput.getText().toString(), "password");

        httpRequest.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                if (response.isSuccessful()) {

                    // Set token at the shared preferences
                    authentication.setToken(response.body());

                    // Call main app activity and clear the intent stack
                    // by finishing the sign in activity
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Invalid credentials.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Invalid credentials.", Toast.LENGTH_LONG).show();
            }

        });

    }

}
