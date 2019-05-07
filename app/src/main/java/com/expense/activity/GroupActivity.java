package com.expense.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.expense.R;
import com.expense.interceptor.AuthorizationInterceptor;
import com.expense.model.Group;
import com.expense.service.GroupService;
import com.expense.setting.AuthenticationManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class GroupActivity extends AppCompatActivity {

    private AuthenticationManager authentication;

    private Button createGroupButton;

    private EditText groupNameInput;

    private EditText groupDescriptionInput;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setTitle(R.string.group_activity_title);

        authentication = new AuthenticationManager(getApplicationContext());

        // Return to the Sign In screen if the user is not logged
        if (!authentication.isValid()) {
            Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }

        progress = new ProgressDialog(this);

        // Set back button at the application toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupNameInput = findViewById(R.id.groupNameInput);
        groupDescriptionInput = findViewById(R.id.groupDescriptionInput);

        createGroupButton = findViewById(R.id.createGroupButtonId);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGroup();
            }
        });

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.deleteGroupButtonId:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveGroup() {
        /*
        displayLoading("Wait while we create the group...");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor("client", "123"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Cannot be localhost due to the virtual machine
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        GroupService groupService = retrofit.create(GroupService.class);

        Call<Void> httpRequest = groupService.create(new Group(
                groupNameInput.getText().toString(),
                groupDescriptionInput.getText().toString()
        ));

        // Asynchronous request to create the informed group
        httpRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call call, Response response) {
                dismissLoading();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Group successfully created.", Toast.LENGTH_LONG).show();
                    finish(); // Return to the Main activity
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error in the group creation.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                dismissLoading();
                Toast.makeText(getApplicationContext(),
                        "Error in the group creation.", Toast.LENGTH_LONG).show();
            }
        });
        */

    }

    public void displayLoading(String loadingMessage) {
        progress.setTitle("Creating the group...");
        progress.setMessage(loadingMessage);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void dismissLoading() {
        // Dismiss the loading dialog
        progress.dismiss();
    }

}
