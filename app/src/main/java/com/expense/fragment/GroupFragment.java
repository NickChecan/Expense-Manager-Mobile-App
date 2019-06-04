package com.expense.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.expense.R;
import com.expense.activity.GroupActivity;
import com.expense.model.Group;
import com.expense.setting.AuthenticationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GroupFragment extends Fragment {

    private ListView groupListView;

    private String[] items = {};

    FloatingActionButton addGroupFloatingButton;

    private AuthenticationManager authentication;

    ArrayAdapter<String> adapter;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_group, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        authentication = new AuthenticationManager(rootView.getContext());

        groupListView = rootView.findViewById(R.id.groupListViewId);

        addGroupFloatingButton = rootView.findViewById(R.id.addGroupFloatingButton);

        //items = getItems();

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                rootView.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                items
        );


        adapter = new ArrayAdapter<String>(
                rootView.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                items
        );

        groupListView.setAdapter(adapter);

        this.groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items[position];
                Toast.makeText(rootView.getContext(),
                        selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
           */
        addGroupFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent groupIntent = new Intent(getActivity(), GroupActivity.class);
                startActivity(groupIntent);
            }
        });

        return rootView;

    }

    @Override
    public void onResume() {

        items = getItems();

        if (adapter == null) {
            adapter = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    items
            );

            groupListView.setAdapter(adapter);

            this.groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = items[position];
                    Toast.makeText(getContext(),
                            selectedItem, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            adapter.notifyDataSetChanged();
            groupListView.invalidateViews();
            groupListView.refreshDrawableState();
        }


        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("------------------------------ test");
        super.onStart();
    }

    public String[] getItems() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/group")
                .get()
                .addHeader("Authorization", "Bearer " + authentication.getToken().getAccessToken())
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();

            try {
                JSONArray jsonData = new JSONArray(response.body().string());

                String[] selectedItems = new String[jsonData.length()];

                for (int index = 0; index < jsonData.length(); index++) {
                    JSONObject jsonObject = jsonData.getJSONObject(index);
                    selectedItems [index] = jsonObject.getString("name");
                }

                return selectedItems;


            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
