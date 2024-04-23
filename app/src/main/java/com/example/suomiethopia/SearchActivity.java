package com.example.suomiethopia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> dataList;
    private SearchAdapter searchAdapter;
    private SearchView searchView;

    private boolean isFragmentDisplayed = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);


        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);



        dataList = new ArrayList<>();
        initializeData();
        searchAdapter = new SearchAdapter(dataList);
        searchAdapter.setNoResultsTextView(findViewById(R.id.noResultsTextView));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(searchAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!isFragmentDisplayed) {
                    searchAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });


        ImageButton homeButton = findViewById(R.id.homeButton);
        ImageButton compareButton = findViewById(R.id.compareButton);
        ImageButton settingsButton = findViewById(R.id.settingsbutton);
        ImageButton quizButton = findViewById(R.id.quizbutton);

        homeButton.setOnClickListener(v -> navigateTo(MainActivity.class));
        compareButton.setOnClickListener(v -> navigateTo(ComparisonActivity.class));
        settingsButton.setOnClickListener(v -> navigateTo(SettingsActivity.class));
        quizButton.setOnClickListener(v -> navigateTo(QuizActivity.class));



        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String municipalityName) {
                if (!isFragmentDisplayed) {
                    onItemClickNavigateToMunicipalityFragment(municipalityName);
                }
            }
        });


    }



    private void initializeData() {
        dataList = MunicipalityData.getMunicipalities();
    }

    private void onItemClickNavigateToMunicipalityFragment(String municipalityName) {

        MunicipalityFragment fragment = MunicipalityFragment.newInstance(municipalityName);



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();

        // Disable RecyclerView interaction
        recyclerView.setEnabled(false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconified(true);



        isFragmentDisplayed = true;

    }




    private void navigateTo(Class<?> destinationClass) {
        Intent intent = new Intent(SearchActivity.this, destinationClass);
        startActivity(intent);
    }
}
