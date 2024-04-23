package com.example.suomiethopia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MunicipalityFragment extends Fragment {

    private ImageButton showButton;
    private TextView municipalityNameTextView;

    private TextView txtPopulation;

    private TextView txtPopulationIncrease;

    private TextView txtWorkplaceSelfSufficiency;

    private TextView txtLivebirths;

    private TextView txtemploymentrate;

    private MunicipalityDataRetriever municipalityDataRetriever;

    public MunicipalityFragment() {

    }

    public static MunicipalityFragment newInstance(String municipalityName) {
        MunicipalityFragment fragment = new MunicipalityFragment();
        Bundle args = new Bundle();
        args.putString("municipalityName", municipalityName);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            EdgeToEdge.enable(getActivity());
        }
        municipalityDataRetriever = new MunicipalityDataRetriever();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_municipality, container, false);
        municipalityNameTextView = view.findViewById(R.id.municipalityNameTextView);
        showButton = view.findViewById(R.id.showbutton);
        txtPopulation = view.findViewById(R.id.txtPopulation);
        txtemploymentrate = view.findViewById(R.id.txtemploymentrate);


        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String municipalityName = municipalityNameTextView.getText().toString();


                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<MunicipalityData> municipalityDataArrayList = municipalityDataRetriever.getData(getContext(), municipalityName);
                        ArrayList<MunicipalityData> populationIncreaseDataArrayList = municipalityDataRetriever.getPopulationIncreaseData(getContext(), municipalityName);
                        ArrayList<MunicipalityData> workplaceSelfSufficiencyDataArrayList = municipalityDataRetriever.getWorkplaceSelfSufficiencyData(getContext(), municipalityName);
                        ArrayList<MunicipalityData> employmentRateDataArrayList = municipalityDataRetriever.getEmploymentRateData(getContext(), municipalityName);
                        ArrayList<MunicipalityData> liveBirthsDataArrayList = municipalityDataRetriever.getLiveBirthsData(getContext(), municipalityName);

                        if (municipalityDataArrayList == null) {
                            return;
                        }

                        if (employmentRateDataArrayList == null) {
                            return;
                        }


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String dataString = "Population:\n";
                                for (MunicipalityData data : municipalityDataArrayList) {
                                    dataString = dataString + data.getYear() + ": " + data.getPopulation() + "\n";
                                }
                                txtPopulation.setText(dataString);


                                TextView txtPopulationIncrease = view.findViewById(R.id.txtPopulationIncrease);
                                String increaseDataString = "Population Increase/decrease:\n";
                                for (MunicipalityData data : populationIncreaseDataArrayList) {
                                    increaseDataString = increaseDataString + data.getYear() + ": " + data.getPopulation() + "\n";
                                }
                                txtPopulationIncrease.setText(increaseDataString);

                                TextView txtWorkplaceSelfSufficiency = view.findViewById(R.id.txtWorkplaceSelfSufficiency);
                                String workplaceDataString = "Workplace Self-Sufficiency:\n";
                                for (MunicipalityData data : workplaceSelfSufficiencyDataArrayList) {
                                    workplaceDataString = workplaceDataString + data.getYear() + ": " + data.getPopulation() + "\n";
                                }
                                txtWorkplaceSelfSufficiency.setText(workplaceDataString);

                                String employmentRateDataString = "Employment Rate:\n";
                                for (MunicipalityData data : employmentRateDataArrayList) {
                                    employmentRateDataString = employmentRateDataString + data.getYear() + ": " + data.getEmploymentRate() + "\n";
                                }
                                txtemploymentrate.setText(employmentRateDataString);

                                TextView txtLiveBirths = view.findViewById(R.id.txtLivebirths);
                                String liveBirthsDataString = "Live Births:\n";
                                for (MunicipalityData data : liveBirthsDataArrayList) {
                                    liveBirthsDataString = liveBirthsDataString + data.getYear() + ": " + data.getPopulation() + "\n";
                                }
                                txtLiveBirths.setText(liveBirthsDataString);

                            }
                        });
                    }
                });
            }
        });


        Bundle bundle = getArguments();
        if (bundle != null) {
            String municipalityName = bundle.getString("municipalityName");
            municipalityNameTextView.setText(municipalityName);
        }


        ImageButton searchButton = view.findViewById(R.id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateToSearchActivity();
            }
        });

        return view;
    }




    private void navigateToSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
}
