package com.expense.fragment;

import android.content.Intent;
import android.os.Bundle;
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

public class GroupFragment extends Fragment {

    private ListView groupListView;

    private String[] items = {
            "Angra dos Reis", "Caldas Novas",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe",
            "Campos do Jordao", "Costa do Sauipe"
    };

    FloatingActionButton addGroupFloatingButton;

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

        groupListView = rootView.findViewById(R.id.groupListViewId);

        addGroupFloatingButton = rootView.findViewById(R.id.addGroupFloatingButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
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

        addGroupFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent groupIntent = new Intent(getActivity(), GroupActivity.class);
                startActivity(groupIntent);
            }
        });

        return rootView;

    }

}
