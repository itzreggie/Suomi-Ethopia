package com.example.suomiethopia;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityData {

    private int year;
    private int population;
    private double employmentRate;


    public MunicipalityData(int year, Integer population, double employmentRate) {
        this.year = year;
        this.population = population != null ? population : 0;
        this.employmentRate = employmentRate;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getPopulation() {
        return population;
    }

    public double getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(double employmentRate) {
        this.employmentRate = employmentRate;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public static List<String> getMunicipalities() {
        List<String> dataList = new ArrayList<>();
        dataList.add("Akaa");
        dataList.add("Alajärvi");
        dataList.add("Alavieska");
        dataList.add("Alavus");
        dataList.add("Asikkala");
        dataList.add("Askola");
        dataList.add("Aura");
        dataList.add("Brändö");
        dataList.add("Eckerö");
        dataList.add("Enonkoski");
        dataList.add("Enontekiö");
        dataList.add("Espoo");
        dataList.add("Eura");
        dataList.add("Eurajoki");
        dataList.add("Evijärvi");
        dataList.add("Finström");
        dataList.add("Forssa");
        dataList.add("Föglö");
        dataList.add("Geta");
        dataList.add("Haapajärvi");
        dataList.add("Haapavesi");
        dataList.add("Hailuoto");
        dataList.add("Halsua");
        dataList.add("Hamina");
        dataList.add("Hammarland");
        dataList.add("Hankasalmi");
        dataList.add("Hanko");
        dataList.add("Harjavalta");
        dataList.add("Hartola");
        dataList.add("Hattula");
        dataList.add("Hausjärvi");
        dataList.add("Heinola");
        dataList.add("Heinävesi");
        dataList.add("Helsinki");
        dataList.add("Hirvensalmi");
        dataList.add("Hollola");
        dataList.add("Huittinen");
        dataList.add("Humppila");
        dataList.add("Hyrynsalmi");
        dataList.add("Hyvinkää");
        dataList.add("Hämeenkyrö");
        dataList.add("Hämeenlinna");
        dataList.add("Ii");
        dataList.add("Iisalmi");
        dataList.add("Iitti");
        dataList.add("Ikaalinen");
        dataList.add("Ilmajoki");
        dataList.add("Ilomantsi");
        dataList.add("Imatra");
        dataList.add("Inari");
        dataList.add("Ingå");
        dataList.add("Isojoki");
        dataList.add("Isokyrö");
        dataList.add("Janakkala");
        dataList.add("Joensuu");
        dataList.add("Jokioinen");
        dataList.add("Jomala");
        dataList.add("Joroinen");
        dataList.add("Joutsa");
        dataList.add("Juuka");
        dataList.add("Juupajoki");
        dataList.add("Juva");
        dataList.add("Jyväskylä");
        dataList.add("Jämijärvi");
        dataList.add("Jämsä");
        dataList.add("Järvenpää");
        dataList.add("Kaarina");
        dataList.add("Kaavi");
        dataList.add("Kajaani");
        dataList.add("Kalajoki");
        dataList.add("Kangasala");
        dataList.add("Kangasniemi");
        dataList.add("Kankaanpää");
        dataList.add("Kannonkoski");
        dataList.add("Kannus");
        dataList.add("Karijoki");
        dataList.add("Karkkila");
        dataList.add("Karstula");
        dataList.add("Karvia");
        dataList.add("Kaskinen");
        dataList.add("Kauhajoki");
        dataList.add("Kauhava");
        dataList.add("Kauniainen");
        dataList.add("Kaustinen");
        dataList.add("Keitele");
        dataList.add("Kemi");
        dataList.add("Kemijärvi");
        dataList.add("Keminmaa");
        dataList.add("Kimitoön");
        dataList.add("Kempele");
        dataList.add("Kerava");
        dataList.add("Keuruu");
        dataList.add("Kihniö");
        dataList.add("Kinnula");
        dataList.add("Kirkkonummi");
        dataList.add("Kitee");
        dataList.add("Kittilä");
        dataList.add("Kiuruvesi");
        dataList.add("Kivijärvi");
        dataList.add("Kokemäki");
        dataList.add("Kokkola");
        dataList.add("Kolari");
        dataList.add("Konnevesi");
        dataList.add("Kontiolahti");
        dataList.add("Korsnäs");
        dataList.add("Koski Tl");
        dataList.add("Kotka");
        dataList.add("Kouvola");
        dataList.add("Kristinestad");
        dataList.add("Kronoby");
        dataList.add("Kuhmo");
        dataList.add("Kuhmoinen");
        dataList.add("Kumlinge");
        dataList.add("Kuopio");
        dataList.add("Kuortane");
        dataList.add("Kurikka");
        dataList.add("Kustavi");
        dataList.add("Kuusamo");
        dataList.add("Kyyjärvi");
        dataList.add("Kärkölä");
        dataList.add("Kärsämäki");
        dataList.add("Kökar");
        dataList.add("Lahti");
        dataList.add("Laihia");
        dataList.add("Laitila");
        dataList.add("Lapinjärvi");
        dataList.add("Lapinlahti");
        dataList.add("Lappajärvi");
        dataList.add("Lappeenranta");
        dataList.add("Lapua");
        dataList.add("Laukaa");
        dataList.add("Lemi");
        dataList.add("Lemland");
        dataList.add("Lempäälä");
        dataList.add("Leppävirta");
        dataList.add("Lestijärvi");
        dataList.add("Lieksa");
        dataList.add("Lieto");
        dataList.add("Liminka");
        dataList.add("Liperi");
        dataList.add("Lohja");
        dataList.add("Loimaa");
        dataList.add("Loppi");
        dataList.add("Loviisa");
        dataList.add("Luhanka");
        dataList.add("Lumijoki");
        dataList.add("Lumparland");
        dataList.add("Larsmo");
        dataList.add("Luumäki");
        dataList.add("Malax");
        dataList.add("Mariehamn");
        dataList.add("Marttila");
        dataList.add("Masku");
        dataList.add("Merijärvi");
        dataList.add("Merikarvia");
        dataList.add("Miehikkälä");
        dataList.add("Mikkeli");
        dataList.add("Muhos");
        dataList.add("Multia");
        dataList.add("Muonio");
        dataList.add("Korsholm");
        dataList.add("Muurame");
        dataList.add("Mynämäki");
        dataList.add("Myrskylä");
        dataList.add("Mäntsälä");
        dataList.add("Mänttä-Vilppula");
        dataList.add("Mäntyharju");
        dataList.add("Naantali");
        dataList.add("Nakkila");
        dataList.add("Nivala");
        dataList.add("Nokia");
        dataList.add("Nousiainen");
        dataList.add("Nurmes");
        dataList.add("Nurmijärvi");
        dataList.add("Närpes");
        dataList.add("Orimattila");
        dataList.add("Oripää");
        dataList.add("Orivesi");
        dataList.add("Oulainen");
        dataList.add("Oulu");
        dataList.add("Outokumpu");
        dataList.add("Padasjoki");
        dataList.add("Paimio");
        dataList.add("Paltamo");
        dataList.add("Pargas");
        dataList.add("Parikkala");
        dataList.add("Parkano");
        dataList.add("Pedersöre");
        dataList.add("Pelkosenniemi");
        dataList.add("Pello");
        dataList.add("Perho");
        dataList.add("Pertunmaa");
        dataList.add("Petäjävesi");
        dataList.add("Pieksämäki");
        dataList.add("Pielavesi");
        dataList.add("Jakobstad");
        dataList.add("Pihtipudas");
        dataList.add("Pirkkala");
        dataList.add("Polvijärvi");
        dataList.add("Pomarkku");
        dataList.add("Pori");
        dataList.add("Pornainen");
        dataList.add("Porvoo");
        dataList.add("Posio");
        dataList.add("Pudasjärvi");
        dataList.add("Pukkila");
        dataList.add("Punkalaidun");
        dataList.add("Puolanka");
        dataList.add("Puumala");
        dataList.add("Pyhtää");
        dataList.add("Pyhäjoki");
        dataList.add("Pyhäjärvi");
        dataList.add("Pyhäntä");
        dataList.add("Pyhäranta");
        dataList.add("Pälkäne");
        dataList.add("Pöytyä");
        dataList.add("Raahe");
        dataList.add("Raseborg");
        dataList.add("Raisio");
        dataList.add("Rantasalmi");
        dataList.add("Ranua");
        dataList.add("Rauma");
        dataList.add("Rautalampi");
        dataList.add("Rautavaara");
        dataList.add("Rautjärvi");
        dataList.add("Reisjärvi");
        dataList.add("Riihimäki");
        dataList.add("Ristijärvi");
        dataList.add("Rovaniemi");
        dataList.add("Ruokolahti");
        dataList.add("Ruovesi");
        dataList.add("Rusko");
        dataList.add("Rääkkylä");
        dataList.add("Saarijärvi");
        dataList.add("Salla");
        dataList.add("Salo");
        dataList.add("Saltvik");
        dataList.add("Sastamala");
        dataList.add("Sauvo");
        dataList.add("Savitaipale");
        dataList.add("Savonlinna");
        dataList.add("Savukoski");
        dataList.add("Seinäjoki");
        dataList.add("Sievi");
        dataList.add("Siikainen");
        dataList.add("Siikajoki");
        dataList.add("Siikalatva");
        dataList.add("Siilinjärvi");
        dataList.add("Simo");
        dataList.add("Sipoo");
        dataList.add("Siuntio");
        dataList.add("Sodankylä");
        dataList.add("Soini");
        dataList.add("Somero");
        dataList.add("Sonkajärvi");
        dataList.add("Sotkamo");
        dataList.add("Sottunga");
        dataList.add("Sulkava");
        dataList.add("Sund");
        dataList.add("Suomussalmi");
        dataList.add("Suonenjoki");
        dataList.add("Sysmä");
        dataList.add("Säkylä");
        dataList.add("Taipalsaari");
        dataList.add("Taivalkoski");
        dataList.add("Taivassalo");
        dataList.add("Tammela");
        dataList.add("Tampere");
        dataList.add("Tervo");
        dataList.add("Tervola");
        dataList.add("Teuva");
        dataList.add("Tohmajärvi");
        dataList.add("Toholampi");
        dataList.add("Toivakka");
        dataList.add("Tornio");
        dataList.add("Turku");
        dataList.add("Tuusniemi");
        dataList.add("Tuusula");
        dataList.add("Tyrnävä");
        dataList.add("Ulvila");
        dataList.add("Urjala");
        dataList.add("Utajärvi");
        dataList.add("Utsjoki");
        dataList.add("Uurainen");
        dataList.add("Nykarleby");
        dataList.add("Uusikaupunki");
        dataList.add("Vaala");
        dataList.add("Vaasa");
        dataList.add("Valkeakoski");
        dataList.add("Vantaa");
        dataList.add("Varkaus");
        dataList.add("Vehmaa");
        dataList.add("Vesanto");
        dataList.add("Vesilahti");
        dataList.add("Veteli");
        dataList.add("Vieremä");
        dataList.add("Vihti");
        dataList.add("Viitasaari");
        dataList.add("Vimpeli");
        dataList.add("Virolahti");
        dataList.add("Virrat");
        dataList.add("Vårdö");
        dataList.add("Vörå");
        dataList.add("Ylitornio");
        dataList.add("Ylivieska");
        dataList.add("Ylöjärvi");
        dataList.add("Ypäjä");
        dataList.add("Ähtäri");
        dataList.add("Äänekoski");
        return dataList;
    }
}