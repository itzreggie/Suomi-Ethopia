    package com.example.suomiethopia;


    import android.content.Context;
    import android.os.AsyncTask;

    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.node.ObjectNode;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.ProtocolException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.HashMap;


    public class MunicipalityDataRetriever {


        static ObjectMapper objectMapper = new ObjectMapper();

        static HashMap<String, String> municipalityNamesToCodesMap = null;

        /**
         * Get municipality codes, we need to do this only once
         *
         */
        public static void getMunicipalityCodesMap() {
            if (municipalityNamesToCodesMap == null) {
                JsonNode areas = readAreaDataFromTheAPIURL(objectMapper);
                municipalityNamesToCodesMap = createMunicipalityNamesToCodesMap(areas);
            }
        }

        public ArrayList<MunicipalityData> getData(Context context, String municipalityName) {

            getMunicipalityCodesMap();


            String code = municipalityNamesToCodesMap.get(municipalityName);


            try {

                JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.query));


                ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);


                HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));


                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JsonNode municipalityData = objectMapper.readTree(response.toString());

                    ArrayList<String> years = new ArrayList<>();
                    JsonNode populations = null;



                    for (JsonNode node : municipalityData.get("dimension").get("Vuosi")
                            .get("category").get("label")) {
                        years.add(node.asText());
                    }



                    populations = municipalityData.get("value");

                    ArrayList<MunicipalityData> populationData = new ArrayList<>();


                    for (int i = 0; i < populations.size(); i++) {
                        Integer population = populations.get(i).asInt();
                        populationData.add(new MunicipalityData(Integer.parseInt(years.get(i)), population,0.0));
                    }



                    for (MunicipalityData data : populationData) {
                        System.out.print(data.getYear() + ": " + data.getPopulation() + " ");

                        for (int i = 0; i < data.getPopulation() / 10000; i++) {
                            System.out.print("*");
                        }

                        System.out.println();

                    }

                    return populationData;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return new ArrayList<>();
        }

        public ArrayList<MunicipalityData> getPopulationIncreaseData(Context context, String municipalityName) {
            getMunicipalityCodesMap();

            String code = municipalityNamesToCodesMap.get(municipalityName);

            try {
                JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.population_increase_query));
                ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JsonNode municipalityData = objectMapper.readTree(response.toString());

                    ArrayList<String> years = new ArrayList<>();
                    JsonNode populations = null;

                    for (JsonNode node : municipalityData.get("dimension").get("Vuosi")
                            .get("category").get("label")) {
                        years.add(node.asText());
                    }

                    populations = municipalityData.get("value");

                    ArrayList<MunicipalityData> populationIncreaseData = new ArrayList<>();

                    for (int i = 0; i < populations.size(); i++) {
                        Integer populationIncrease = populations.get(i).asInt();
                        populationIncreaseData.add(new MunicipalityData(Integer.parseInt(years.get(i)), populationIncrease, 0.0));
                    }

                    return populationIncreaseData;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        public ArrayList<MunicipalityData> getWorkplaceSelfSufficiencyData(Context context, String municipalityName) {
            getMunicipalityCodesMap();

            String code = municipalityNamesToCodesMap.get(municipalityName);

            try {
                JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.workplace));
                ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/tyokay/statfin_tyokay_pxt_125s.px"));

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JsonNode workplaceData = objectMapper.readTree(response.toString());

                    ArrayList<String> years = new ArrayList<>();
                    JsonNode values = null;

                    for (JsonNode node : workplaceData.get("dimension").get("Vuosi")
                            .get("category").get("label")) {
                        years.add(node.asText());
                    }

                    values = workplaceData.get("value");

                    ArrayList<MunicipalityData> workplaceSelfSufficiencyData = new ArrayList<>();

                    for (int i = 0; i < values.size(); i++) {
                        Integer value = values.get(i).asInt();
                        workplaceSelfSufficiencyData.add(new MunicipalityData(Integer.parseInt(years.get(i)), value, 0.0));
                    }

                    return workplaceSelfSufficiencyData;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        public ArrayList<MunicipalityData> getEmploymentRateData(Context context, String municipalityName) {
            getMunicipalityCodesMap();
            String code = municipalityNamesToCodesMap.get(municipalityName);

            try {

                JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.employmentrate));
                ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/tyokay/statfin_tyokay_pxt_115x.px"));

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JsonNode employmentRateData = objectMapper.readTree(response.toString());

                    ArrayList<String> years = new ArrayList<>();
                    JsonNode rates = null;

                    for (JsonNode node : employmentRateData.get("dimension").get("Vuosi")
                            .get("category").get("label")) {
                        years.add(node.asText());
                    }

                    rates = employmentRateData.get("value");

                    // This is to checkCheck if the "dimension" node exists before accessing its properties
//                    if (employmentRateData.has("dimension")) {
//                        JsonNode dimensionNode = employmentRateData.get("dimension");
//                        if (dimensionNode.has("Vuosi")) {
//                            JsonNode vuosiNode = dimensionNode.get("Vuosi");
//                            if (vuosiNode.has("category")) {
//                                JsonNode categoryNode = vuosiNode.get("category");
//                                if (categoryNode.has("label")) {
//                                    for (JsonNode node : categoryNode.get("label")) {
//                                        years.add(node.asText());
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    // Check if the "value" node exists before accessing its properties
//                    if (employmentRateData.has("value")) {
//                        rates = employmentRateData.get("value");
//                    }

                    ArrayList<MunicipalityData> employmentRateList = new ArrayList<>();

                    for (int i = 0; i < rates.size(); i++) {
                        double employmentRate = rates.get(i).asDouble();
                        employmentRateList.add(new MunicipalityData(Integer.parseInt(years.get(i)), null, employmentRate));
                    }

                    return employmentRateList;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }


        public ArrayList<MunicipalityData> getLiveBirthsData(Context context, String municipalityName) {
            getMunicipalityCodesMap();
            String code = municipalityNamesToCodesMap.get(municipalityName);

            try {
                JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.livebirth));
                ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JsonNode liveBirthsData = objectMapper.readTree(response.toString());

                    ArrayList<String> years = new ArrayList<>();
                    JsonNode liveBirths = null;

                    for (JsonNode node : liveBirthsData.get("dimension").get("Vuosi")
                            .get("category").get("label")) {
                        years.add(node.asText());
                    }

                    liveBirths = liveBirthsData.get("value");

                    ArrayList<MunicipalityData> liveBirthsList = new ArrayList<>();

                    for (int i = 0; i < liveBirths.size(); i++) {
                        Integer liveBirthsCount = liveBirths.get(i).asInt();
                        liveBirthsList.add(new MunicipalityData(Integer.parseInt(years.get(i)), liveBirthsCount, 0.0));
                    }

                    return liveBirthsList;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }


        private static HttpURLConnection connectToAPIAndSendPostRequest(ObjectMapper objectMapper, JsonNode jsonQuery, URL url)
                throws MalformedURLException, IOException, ProtocolException, JsonProcessingException {

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = objectMapper.writeValueAsBytes(jsonQuery);
                os.write(input, 0, input.length);
            }
            return con;
        }


        private static HashMap<String, String> createMunicipalityNamesToCodesMap(JsonNode areas) {
            JsonNode codes = null;
            JsonNode names = null;




            for (JsonNode node : areas.findValue("variables")) {
                if (node.findValue("text").asText().equals("Area")) {
                    codes = node.findValue("values");
                    names = node.findValue("valueTexts");
                }
            }


            HashMap<String, String> municipalityNamesToCodesMap = new HashMap<>();

            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i).asText();
                String code = codes.get(i).asText();
                municipalityNamesToCodesMap.put(name, code);

            }
            return municipalityNamesToCodesMap;
        }


        private static JsonNode readAreaDataFromTheAPIURL(ObjectMapper objectMapper) {
            JsonNode areas = null;
            try {
                areas = objectMapper.readTree(new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return areas;
        }


        public static void getMunicipalityDataFor2022(final Context context, final String municipalityName, final OnDataFetchedListener listener) {
            new AsyncTask<Void, Void, MunicipalityData>() {
                @Override
                protected MunicipalityData doInBackground(Void... voids) {
                    getMunicipalityCodesMap();
                    String code = municipalityNamesToCodesMap.get(municipalityName);

                    try {
                        JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.query));
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).put("year", "2022");
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                        HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }

                            JsonNode municipalityData = objectMapper.readTree(response.toString());
                            int population = municipalityData.get("value").get(32).asInt();
                            return new MunicipalityData(2022, population, 0.0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(MunicipalityData cityAData) {
                    if (cityAData != null) {
                        listener.onDataFetched(cityAData);
                    }
                }
            }.execute();
        }

        public static void getImmigrationDataFor2022(final Context context, final String municipalityName, final OnDataFetchedListener listener) {
            new AsyncTask<Void, Void, MunicipalityData>() {
                @Override
                protected MunicipalityData doInBackground(Void... voids) {
                    getMunicipalityCodesMap();
                    String code = municipalityNamesToCodesMap.get(municipalityName);

                    try {
                        JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.immigrationquery));
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).put("year", "2022");
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                        HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }

                            JsonNode municipalityData = objectMapper.readTree(response.toString());
                            int population = municipalityData.get("value").get(32).asInt();
                            return new MunicipalityData(2022, population, 0.0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(MunicipalityData cityAData) {
                    if (cityAData != null) {
                        listener.onDataFetched(cityAData);
                    }
                }
            }.execute();
        }

        public static void getDeathDataFor2022(final Context context, final String municipalityName, final OnDataFetchedListener listener) {
            new AsyncTask<Void, Void, MunicipalityData>() {
                @Override
                protected MunicipalityData doInBackground(Void... voids) {
                    getMunicipalityCodesMap();
                    String code = municipalityNamesToCodesMap.get(municipalityName);

                    try {
                        JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.death));
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).put("year", "2022");
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                        HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }

                            JsonNode municipalityData = objectMapper.readTree(response.toString());
                            int population = municipalityData.get("value").get(32).asInt();
                            return new MunicipalityData(2022, population, 0.0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(MunicipalityData cityAData) {
                    if (cityAData != null) {
                        listener.onDataFetched(cityAData);
                    }
                }
            }.execute();
        }

        public static void getDivorceDataFor2022(final Context context, final String municipalityName, final OnDataFetchedListener listener) {
            new AsyncTask<Void, Void, MunicipalityData>() {
                @Override
                protected MunicipalityData doInBackground(Void... voids) {
                    getMunicipalityCodesMap();
                    String code = municipalityNamesToCodesMap.get(municipalityName);

                    try {
                        JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.divorce));
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).put("year", "2022");
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                        HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }

                            JsonNode municipalityData = objectMapper.readTree(response.toString());
                            int population = municipalityData.get("value").get(32).asInt();
                            return new MunicipalityData(2022, population, 0.0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(MunicipalityData cityAData) {
                    if (cityAData != null) {
                        listener.onDataFetched(cityAData);
                    }
                }
            }.execute();
        }

        public static void getEmmigrationDataFor2022(final Context context, final String municipalityName, final OnDataFetchedListener listener) {
            new AsyncTask<Void, Void, MunicipalityData>() {
                @Override
                protected MunicipalityData doInBackground(Void... voids) {
                    getMunicipalityCodesMap();
                    String code = municipalityNamesToCodesMap.get(municipalityName);

                    try {
                        JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.emmigration));
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).put("year", "2022");
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                        HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }

                            JsonNode municipalityData = objectMapper.readTree(response.toString());
                            int population = municipalityData.get("value").get(32).asInt();
                            return new MunicipalityData(2022, population, 0.0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(MunicipalityData cityAData) {
                    if (cityAData != null) {
                        listener.onDataFetched(cityAData);
                    }
                }
            }.execute();
        }

        public static void getTotalchangeDataFor2022(final Context context, final String municipalityName, final OnDataFetchedListener listener) {
            new AsyncTask<Void, Void, MunicipalityData>() {
                @Override
                protected MunicipalityData doInBackground(Void... voids) {
                    getMunicipalityCodesMap();
                    String code = municipalityNamesToCodesMap.get(municipalityName);

                    try {
                        JsonNode jsonQuery = objectMapper.readTree(context.getResources().openRawResource(R.raw.totalchange));
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).put("year", "2022");
                        ((ObjectNode) jsonQuery.findValue("query").get(0).get("selection")).putArray("values").add(code);

                        HttpURLConnection con = connectToAPIAndSendPostRequest(objectMapper, jsonQuery, new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }

                            JsonNode municipalityData = objectMapper.readTree(response.toString());
                            int population = municipalityData.get("value").get(32).asInt();
                            return new MunicipalityData(2022, population, 0.0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(MunicipalityData cityAData) {
                    if (cityAData != null) {
                        listener.onDataFetched(cityAData);
                    }
                }
            }.execute();
        }


        public interface OnDataFetchedListener {
            void onDataFetched(MunicipalityData data);
        }



    }

