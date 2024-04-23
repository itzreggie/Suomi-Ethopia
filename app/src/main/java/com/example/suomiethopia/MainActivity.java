package com.example.suomiethopia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView textViewGreeting;
    TextView textViewTemperature;
    TextView textViewCityName;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGreeting = findViewById(R.id.textViewGreeting);
        textViewGreeting.setText(getGreeting());
        textViewCityName = findViewById(R.id.textViewCityName);

        textViewTemperature = findViewById(R.id.textViewTemperature);

        textViewCityName.setText("Lahti");

        fetchWeatherDataForLahti();

        fetchCountryData();


        ImageButton compareButton = findViewById(R.id.comparebutton);

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ComparisonActivity.class);
                startActivity(intent);
            }
        });

        ImageButton quizButton = findViewById(R.id.quizbutton);

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });


        ImageButton searchButton = findViewById(R.id.searchbutton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        ImageButton settingsButton = findViewById(R.id.settingsbutton);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        tvResult = findViewById(R.id.tvResult);
    }

    private String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            return "Good Morning,";
        } else if (timeOfDay < 18) {
            return "Good Afternoon,";
        } else {
            return "Good Evening,";
        }
    }

    public void getWeatherDetails(View view) {
        Log.d("MainActivity", "getWeatherDetails method called");

        EditText etCity = findViewById(R.id.etCity);
        String city = etCity.getText().toString().trim();


        String formattedCity = adjustCityName(city);


        if (!formattedCity.isEmpty()) {

            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + formattedCity + "&appid=a52075ba958987a1111ebb7b89eb8499";


            fetchWeatherData(url);
            String capitalizedCity = Character.toUpperCase(city.charAt(0)) + city.substring(1);
            textViewCityName.setText(capitalizedCity);
        } else {

            Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
        }

        hideKeyboard();
    }

    private String adjustCityName(String cityName) {

        // For example, replacing "Lahti" with "Lahtis"
        //this is because this API recognizes lahti as another city in Russia
        if (cityName.equalsIgnoreCase("Lahti")) {
            return "Lahtis";
        }
        return cityName;
    }


    private void fetchWeatherDataForLahti() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Lahtis&appid=a52075ba958987a1111ebb7b89eb8499";


        fetchWeatherData(url);
    }

    private void fetchWeatherData(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);


                            displayWeatherDetails(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });


        queue.add(stringRequest);
    }

    private void displayWeatherDetails(JSONObject jsonResponse) {
        try {
            if (jsonResponse.has("message")) {

                Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
            double temp = jsonObjectMain.getDouble("temp") - 273.15;
            double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
            float pressure = jsonObjectMain.getInt("pressure");
            int humidity = jsonObjectMain.getInt("humidity");

            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
            String wind = jsonObjectWind.getString("speed");

            int roundedTemp = (int) Math.round(temp);
            textViewTemperature.setText(roundedTemp + "°C");

            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
            String clouds = jsonObjectClouds.getString("all");

            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
            String countryName = jsonObjectSys.getString("country");
            String cityName = jsonResponse.getString("name");

            String output = "Current weather of " + cityName + " (" + countryName + ")"
                    + "\n Feels Like: " + new DecimalFormat("#.##").format(feelsLike) + " °C"
                    + "\n Humidity: " + humidity + "%"
                    + "\n Description: " + jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description")
                    + "\n Wind Speed: " + wind + "m/s"
                    + "\n Cloudiness: " + clouds + "%"
                    + "\n Pressure: " + pressure + " hPa";
            tvResult.setText(output);

            String description = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

            ImageView weatherImage = findViewById(R.id.weatherimage);
            if (description.toLowerCase().contains("clear")) {
                weatherImage.setImageResource(R.drawable.cloudy_day_svgrepo_com);
            }
            else if (description.toLowerCase().contains("rain")) {
                weatherImage.setImageResource(R.drawable.rain_svgrepo_com);
            }
            else if (description.toLowerCase().contains("drizzle")) {
                weatherImage.setImageResource(R.drawable.cloud_drizzle_svgrepo_com);
            }
            else if (description.toLowerCase().contains("light rain")) {
                weatherImage.setImageResource(R.drawable.cloud_drizzle_svgrepo_com);
            }
            else if (description.toLowerCase().contains("snow")) {
                weatherImage.setImageResource(R.drawable.snow_cloud_svgrepo_com);
            }
            else if (description.toLowerCase().contains("cloud")) {
                weatherImage.setImageResource(R.drawable.overcast_day_svgrepo_com);
            }
            else if (description.toLowerCase().contains("thunder")) {
                weatherImage.setImageResource(R.drawable.thunder_svgrepo_com);
            }
            else if (description.toLowerCase().contains("sunny")) {
                weatherImage.setImageResource(R.drawable.sunny_sun_svgrepo_com);
            }
            else {
                weatherImage.setImageResource(R.drawable.sun_svgrepo_com);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(this, "Error fetching weather data", Toast.LENGTH_SHORT).show();
        }
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    private void fetchCountryData() {
        new FetchCountryDataTask(this).execute();
    }

    private static class FetchCountryDataTask extends AsyncTask<Void, Void, JSONArray> {
        private WeakReference<Context> contextRef;

        FetchCountryDataTask(Context context) {
            contextRef = new WeakReference<>(context);
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            Context context = contextRef.get();
            if (context == null) {
                return null;
            }

            String url = "https://api.api-ninjas.com/v1/country?name=Finland";
            try {
                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestProperty("X-Api-Key", "32NfelVfdnexEYpUuo8YnA==L1ctuqol5z9lmwoa");
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    connection.disconnect();
                    return new JSONArray(content.toString());
                } else {
                    Log.e("MainActivity", "API request failed with response code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("MainActivity", "Error fetching country data", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            Context context = contextRef.get();
            if (context == null || result == null) {
                return;
            }

            try {
                JSONObject countryData = result.getJSONObject(0);


                TextView tvGDP = ((Activity) context).findViewById(R.id.tvGDP);
                if (countryData.has("gdp")) {
                    tvGDP.setText("GDP: " + countryData.getInt("gdp"));
                }

                TextView tvPopulation = ((Activity) context).findViewById(R.id.tvPopulation);
                if (countryData.has("population")) {
                    tvPopulation.setText("Population: 5,549,068 " );
                }

                TextView tvCurrency = ((Activity) context).findViewById(R.id.tvCurrency);
                if (countryData.has("currency")) {
                    tvCurrency.setText("Currency: £, EUR, EURO " );
                }


                TextView tvSurfaceArea = ((Activity) context).findViewById(R.id.tvSurfaceArea);
                if (countryData.has("surface_area")) {
                    tvSurfaceArea.setText("Surface Area: " + countryData.getInt("surface_area"));
                }

                TextView tvLifeExpectancyMale = ((Activity) context).findViewById(R.id.tvLifeExpectancyMale);
                if (countryData.has("life_expectancy_male")) {
                    tvLifeExpectancyMale.setText("Life Expectancy (Male): " + countryData.getDouble("life_expectancy_male"));
                }

                TextView tvUnemployment = ((Activity) context).findViewById(R.id.tvUnemployment);
                if (countryData.has("unemployment")) {
                    tvUnemployment.setText("Unemployment Rate: " + countryData.getDouble("unemployment"));
                }

                TextView tvImports = ((Activity) context).findViewById(R.id.tvImports);
                if (countryData.has("imports")) {
                    tvImports.setText("Imports: " + countryData.getInt("imports"));
                }

                TextView tvHomicideRate = ((Activity) context).findViewById(R.id.tvHomicideRate);
                if (countryData.has("homicide_rate")) {
                    tvHomicideRate.setText("Homicide Rate: " + countryData.getDouble("homicide_rate"));
                }

                TextView tvEmploymentServices = ((Activity) context).findViewById(R.id.tvEmploymentServices);
                if (countryData.has("employment_services")) {
                    tvEmploymentServices.setText("Employment Services: " + countryData.getDouble("employment_services"));
                }

                TextView tvEmploymentIndustry = ((Activity) context).findViewById(R.id.tvEmploymentIndustry);
                if (countryData.has("employment_industry")) {
                    tvEmploymentIndustry.setText("Employment Industry: " + countryData.getDouble("employment_industry"));
                }

                TextView tvUrbanPopulationGrowth = ((Activity) context).findViewById(R.id.tvUrbanPopulationGrowth);
                if (countryData.has("urban_population_growth")) {
                    tvUrbanPopulationGrowth.setText("Urban Population Growth: " + countryData.getDouble("urban_population_growth"));
                }

                TextView tvSecondarySchoolEnrollmentFemale = ((Activity) context).findViewById(R.id.tvSecondarySchoolEnrollmentFemale);
                if (countryData.has("secondary_school_enrollment_female")) {
                    tvSecondarySchoolEnrollmentFemale.setText("Secondary School Enrollment (Female): " + countryData.getDouble("secondary_school_enrollment_female"));
                }

                TextView tvEmploymentAgriculture = ((Activity) context).findViewById(R.id.tvEmploymentAgriculture);
                if (countryData.has("employment_agriculture")) {
                    tvEmploymentAgriculture.setText("Employment Agriculture: " + countryData.getDouble("employment_agriculture"));
                }

            } catch (JSONException e) {
                Log.e("MainActivity", "Error parsing JSON", e);
            }
        }

    }
}
