package com.expense.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.expense.R;
import com.expense.model.User;
import com.expense.service.AccessService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpButton;

    private EditText nameInput;

    private EditText emailInput;

    private EditText passwordInput;

    private TextView signInText;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = findViewById(R.id.signUpButtonId);
        nameInput = findViewById(R.id.userNameInputId);
        emailInput = findViewById(R.id.emailInputId);
        passwordInput = findViewById(R.id.passwordInputId);
        signInText = findViewById(R.id.signInTextId);

        // Set back button at the application toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = new ProgressDialog(this);

        // Set SignUp button event
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameValid()&&isEmailValid()&&isPasswordValid()) signUp();
            }
        });

        // Set event to return to the Sign In screen
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNameValid() {
        if (!nameInput.getText().toString().isEmpty())
            return true;
        Toast.makeText(getApplicationContext(),
                "Please, inform an user name!", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean isEmailValid() {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (pattern.matcher(emailInput.getText().toString()).matches())
            return true;
        Toast.makeText(getApplicationContext(),
                "Please, inform a valid e-mail address!", Toast.LENGTH_LONG).show();
        return false;
    }

    /*
     * Regex explanations for password validation:
     * (?=.*[0-9]) A digit must occur at least once
     * (?=.*[a-z]) A lower case letter must occur at least once
     * (?=.*[A-Z]) An upper case letter must occur at least once
     * (?=.*[@#$%^&+=]) A special character must occur at least once
     * (?=\\S+$) No whitespace allowed in the entire string
     * .{8,} At least 8 characters
     */
    private boolean isPasswordValid() {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (passwordInput.getText().toString().matches(pattern)) return true;
        Toast.makeText(getApplicationContext(),
            "The e-mail must have at least 8 characters, " +
                    "lower and upper case letters, " +
                    "a special character and " +
                    "at least one digit occurrence.", Toast.LENGTH_LONG).show();
        return false;
    }

    private void signUp() {

        displayLoading("Wait while we sign you up...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Cannot be localhost due to the virtual machine
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Create the http service for access request
        AccessService accessService = retrofit.create(AccessService.class);
        Call<Void> httpRequest = accessService.signUp(new User(
                nameInput.getText().toString(),
                emailInput.getText().toString(),
                passwordInput.getText().toString()
        ));

        // Asynchronous request to sign up the informed data
        httpRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call call, Response response) {

                dismissLoading();

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "User successfully created.", Toast.LENGTH_LONG).show();
                    finish(); // Return to the Sign In activity
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error in the user creation.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                dismissLoading();
                Toast.makeText(getApplicationContext(),
                        "Error in the user creation.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void displayLoading(String loadingMessage) {
        progress.setTitle("Signing Up...");
        progress.setMessage(loadingMessage);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void dismissLoading() {
        // Dismiss the loading dialog
        progress.dismiss();
    }

}
