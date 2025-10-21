package com.example.turistguide.service;

import com.example.turistguide.model.CurrencyRates;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class CurrencyService {

    private CurrencyRates currencyRates;

    public CurrencyService() throws IOException {
        getRates();
    }

    private void getRates() throws IOException {
        //Se evt. mere her: https://forexvalutaomregner.dk/pages/api);
        URL url = new URL("https://cdn.moneyconvert.net/api/latest.json");

        // Indlæsning af valutakurser
        BufferedReader inputFromUrl = new BufferedReader(new InputStreamReader(url.openStream()));

        //Mapning af JSON data til Java objekt vha. Gson
        // Husk dependency i pom.xml og import i denne klasse
        CurrencyRates currencyRates = new Gson().fromJson(inputFromUrl, CurrencyRates.class);
        //Close stream
        inputFromUrl.close();
        this.currencyRates = currencyRates;
    }

    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }

    public double getPriceInUSD(double priceInDKK) {
        return priceInDKK / currencyRates.getDKK();
    }

    public double getPriceInEUR(double priceInDKK) {
        return (priceInDKK / currencyRates.getDKK()) * currencyRates.getEUR();
    }


}
