package com.example.suomiethopia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class ComparisonActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citycompare);




        ImageButton searchButton = findViewById(R.id.searchbutton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ComparisonActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(view -> {

            Intent intent = new Intent(ComparisonActivity.this, MainActivity.class);
            startActivity(intent);
        });



        ImageButton settingsButton = findViewById(R.id.settingsbutton);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ComparisonActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton quizButton = findViewById(R.id.quizbutton);

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparisonActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });


        AutoCompleteTextView searchBar = findViewById(R.id.searchBar);
        AutoCompleteTextView searchBar2 = findViewById(R.id.searchBar2);


        List<String> dataList = MunicipalityData.getMunicipalities();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataList);


        searchBar.setAdapter(adapter);
        searchBar2.setAdapter(adapter);


        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
                searchBar.clearFocus();
            }
        });

        searchBar2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
                searchBar2.clearFocus();
            }
        });

        Button compareButton = findViewById(R.id.comlareButton);

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cityA = searchBar.getText().toString();
                String cityB = searchBar2.getText().toString();



                Fragment compareFragment = new CompareFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, compareFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                compareButton.setVisibility(View.GONE);
                searchBar.setEnabled(false);
                searchBar2.setEnabled(false);
                quizButton.setEnabled(false);
                homeButton.setEnabled(false);
                settingsButton.setEnabled(false);
                searchButton.setEnabled(false);



                Bundle bundle = new Bundle();
                bundle.putString("cityA", cityA);
                bundle.putString("cityB", cityB);

                compareFragment.setArguments(bundle);



            }
        });

        ViewGroup rootView = findViewById(android.R.id.content);


        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // this will hide keyboard and clear focus when touched
                hideKeyboard();
                searchBar.clearFocus();
                searchBar2.clearFocus();
                compareButton.clearFocus();
                searchButton.clearFocus();
                return false;
            }
        });
    }


    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

}
