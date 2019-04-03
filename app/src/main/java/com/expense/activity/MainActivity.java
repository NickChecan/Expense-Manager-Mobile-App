package com.expense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.expense.R;
import com.expense.adapter.ViewPagerAdapter;
import com.expense.fragment.BillFragment;
import com.expense.fragment.GroupFragment;
import com.expense.fragment.HomeFragment;
import com.expense.setting.AuthenticationManager;

public class MainActivity extends AppCompatActivity {

    private AuthenticationManager authentication;

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authentication = new AuthenticationManager(getApplicationContext());

        // Return to the Sign In screen if the user is not logged
        if (!authentication.isValid()) {
            Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new BillFragment(), "BILLS");
        adapter.addFragment(new GroupFragment(), "GROUPS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionsButtonId:
                showContextMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showContextMenu() {

        PopupMenu popUp = new PopupMenu(getApplicationContext(),
                findViewById(R.id.optionsButtonId));

        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accountDetailsButtonId:
                        // Call the account details activity
                        Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                        startActivity(accountIntent);
                        return true;

                    case R.id.logoutButtonId:

                        // Clear the authentication token that is being
                        // used to keep the current session on
                        authentication.clear();

                        // Call sign in activity and clear the intent stack
                        // by finishing the main activity
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainIntent);
                        finish();

                        return true;

                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popUp.getMenuInflater();
        inflater.inflate(R.menu.menu_context, popUp.getMenu());
        popUp.show();

    }

}
