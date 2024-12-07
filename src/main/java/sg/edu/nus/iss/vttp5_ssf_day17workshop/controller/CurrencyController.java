package sg.edu.nus.iss.vttp5_ssf_day17workshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.nus.iss.vttp5_ssf_day17workshop.constants.Url;
import sg.edu.nus.iss.vttp5_ssf_day17workshop.model.Currency;
import sg.edu.nus.iss.vttp5_ssf_day17workshop.service.CurrencyService;

@Controller
@RequestMapping("")
public class CurrencyController {
    
    @Autowired
    CurrencyService currencyService;

    @GetMapping("")
    public String home(){
        return "home";
    }

    @GetMapping("/currencyconverter")
    public String currencyConverter(@RequestParam(name="apiKey", defaultValue = Url.apiKey) String apiKey, Model model){
        List<Currency> currencyList = currencyService.getCurrencyList(apiKey);
        model.addAttribute("currencyList", currencyList);
        return "currencyconverter";
    }

    @PostMapping("/currencyconverter")
    public String currencyConverterPOST(@RequestParam(name="apiKey", defaultValue = Url.apiKey) String apiKey, @RequestBody MultiValueMap<String, String> currencyConversionInfo,Model model){
        
        String currencyFromId = currencyConversionInfo.get("currencyFrom").getFirst();
        String currencyToId = currencyConversionInfo.get("currencyTo").getFirst();
        Double amount = Double.parseDouble(currencyConversionInfo.get("amount").getFirst());

        Double convertedAmount = currencyService.getConvertedCurrency(currencyFromId, currencyToId, amount, apiKey);
        
        Currency currencyFrom = currencyService.findById(currencyFromId, apiKey);
        Currency currencyTo = currencyService.findById(currencyToId, apiKey);

        model.addAttribute("currencyFrom", currencyFrom);
        model.addAttribute("currencyTo", currencyTo);
        model.addAttribute("amount", amount);
        model.addAttribute("convertedAmount", convertedAmount);

        return "convertedcurrency";
        
    }
}
