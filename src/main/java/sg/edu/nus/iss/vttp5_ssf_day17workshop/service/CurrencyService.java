package sg.edu.nus.iss.vttp5_ssf_day17workshop.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.vttp5_ssf_day17workshop.constants.Url;
import sg.edu.nus.iss.vttp5_ssf_day17workshop.model.Currency;

@Service
public class CurrencyService {
    
    RestTemplate restTemplate = new RestTemplate();

    public List<Currency> getCurrencyList(String apiKey){

        String completedCurrencyUrl = UriComponentsBuilder.fromUriString(Url.currencyUrl).queryParam("apiKey", apiKey).toUriString();
        ResponseEntity<String> currencyEntity = restTemplate.getForEntity(completedCurrencyUrl, String.class);
        String currencyBody = currencyEntity.getBody();

        StringReader sr = new StringReader(currencyBody);
        JsonReader jr = Json.createReader(sr);

        JsonObject overallJsonObject = jr.readObject();
        JsonObject resultsJsonObject = overallJsonObject.getJsonObject("results");
        
        List<Currency> currencyList = new ArrayList<>();
        for(Entry<String,JsonValue> countryKeyValue : resultsJsonObject.entrySet()){
            JsonObject countryValue = countryKeyValue.getValue().asJsonObject();
            String currencyName = countryValue.getString("currencyName");
            String currencySymbol = countryValue.getString("currencySymbol");
            String currencyId = countryValue.getString("currencyId");
            Currency currency = new Currency(currencyName, currencySymbol, currencyId);
            currencyList.add(currency);
        }

        return currencyList;
    }

    public Double getConvertedCurrency(String currencyFrom, String currencyTo, Double amount, String apiKey){
        String completedCurrencyConvertUrl = UriComponentsBuilder.fromUriString("https://free.currconv.com/api/v7/convert")
                                                                .queryParam("q",currencyFrom + "_" + currencyTo)
                                                                .queryParam("compact","ultra")
                                                                .queryParam("apiKey", apiKey)
                                                                .toUriString();
        ResponseEntity<String> conversionConstantEntity = restTemplate.getForEntity(completedCurrencyConvertUrl,String.class);
        String conversionConstantBody = conversionConstantEntity.getBody();
        
        StringReader sr = new StringReader(conversionConstantBody);
        JsonReader jr = Json.createReader(sr);

        JsonObject jsonResponse = jr.readObject();
        Double conversionConstant = jsonResponse.getJsonNumber(currencyFrom + "_" + currencyTo).doubleValue();

        Double convertedAmount = amount * conversionConstant;

        return convertedAmount;
    }

    public Currency findById(String id, String apiKey){
        List<Currency> currencyList = getCurrencyList(apiKey);

        Currency currency = currencyList.stream().filter(a -> a.getCurrencyId().equals(id)).findAny().get();

        return currency;

    }
}
