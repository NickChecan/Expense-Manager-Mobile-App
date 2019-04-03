package com.expense.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.expense.R;
import com.expense.setting.AuthenticationManager;

public class AccountActivity extends AppCompatActivity {

    private AuthenticationManager authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setTitle(R.string.account_title);

        authentication = new AuthenticationManager(getApplicationContext());

        // Return to the Sign In screen if the user is not logged
        if (!authentication.isValid()) {
            Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }

        // Set back button at the application toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


}
