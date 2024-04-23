package com.example.suomiethopia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;


public class CompareFragment extends Fragment {

    public CompareFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare, container, false);

        String cityA = getArguments().getString("cityA", "Default City A");
        String cityB = getArguments().getString("cityB", "Default City B");


        TextView cityATextView = view.findViewById(R.id.city_a_population);
        TextView cityBTextView = view.findViewById(R.id.city_b_population);


        cityATextView.setText(cityA);
        cityBTextView.setText(cityB);

        MunicipalityDataRetriever.getMunicipalityDataFor2022(getContext(), cityA, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationA2TextView = getView().findViewById(R.id.population_a2);
                populationA2TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getMunicipalityDataFor2022(getContext(), cityB, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationBTextView = getView().findViewById(R.id.population_b);
                populationBTextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getImmigrationDataFor2022(getContext(), cityA, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationA3TextView = getView().findViewById(R.id.population_a3);
                populationA3TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getImmigrationDataFor2022(getContext(), cityB, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationB2TextView = getView().findViewById(R.id.population_b2);
                populationB2TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getDeathDataFor2022(getContext(), cityA, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationA4TextView = getView().findViewById(R.id.population_a4);
                populationA4TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getDeathDataFor2022(getContext(), cityB, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationB3TextView = getView().findViewById(R.id.population_b3);
                populationB3TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getDivorceDataFor2022(getContext(), cityA, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationATextView = getView().findViewById(R.id.population_a);
                populationATextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getDivorceDataFor2022(getContext(), cityB, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationCTextView = getView().findViewById(R.id.population_c);
                populationCTextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getEmmigrationDataFor2022(getContext(), cityA, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationC2TextView = getView().findViewById(R.id.population_c2);
                populationC2TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getEmmigrationDataFor2022(getContext(), cityB, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationC3TextView = getView().findViewById(R.id.population_c3);
                populationC3TextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getTotalchangeDataFor2022(getContext(), cityA, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationDTextView = getView().findViewById(R.id.population_d);
                populationDTextView.setText(String.valueOf(data.getPopulation()));
            }
        });

        MunicipalityDataRetriever.getTotalchangeDataFor2022(getContext(), cityB, new MunicipalityDataRetriever.OnDataFetchedListener() {
            @Override
            public void onDataFetched(MunicipalityData data) {
                TextView populationD2TextView = getView().findViewById(R.id.population_d2);
                populationD2TextView.setText(String.valueOf(data.getPopulation()));
            }
        });


        ImageButton exitButton = view.findViewById(R.id.exitbutton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSearchActivity();
            }
        });

        return view;
    }

    private void navigateToSearchActivity() {
        Intent intent = new Intent(getActivity(), ComparisonActivity.class);
        startActivity(intent);
    }
}
