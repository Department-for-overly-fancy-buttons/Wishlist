package com.example.turistguide.controller;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.service.CurrencyService;
import com.example.turistguide.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/attractions")
public class TouristController {
    //TODO:
    // lav endpoint /attractions/{nam}/tags
    // lav fail states
    private final TouristService touristService;
    private final CurrencyService currencyService;

    public TouristController(TouristService touristService, CurrencyService currencyService) {
        this.touristService = touristService;
        this.currencyService = currencyService;
    }

    @GetMapping()
    public String getAllTouristAttraction(Model model) {
        model.addAttribute("touristAttractions", touristService.getAllTouristAttraction());
        return "attraction-list";
    }

    @GetMapping("/add")
    public String showAddAttractionForm(Model model) {
        model.addAttribute("touristAttraction", new TouristAttraction());
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        return "add-attraction-form";
    }

    @PostMapping("/save")
    public String addTouristAttraction(@ModelAttribute TouristAttraction touristAttraction) {


        TouristAttraction resultingTouristAttraction = touristService.addTouristAttraction(touristAttraction);

        if (resultingTouristAttraction != null) {
            return "redirect:/attractions";
        }
        return "redirect:/"; //create failed to add page

    }

    @GetMapping("{name}")
    public String showTouristAttraction(@PathVariable(required = false) String name, Model model) {
        TouristAttraction touristAttraction = touristService.getTouristAttraction(name);
        if (touristAttraction != null) {
            model.addAttribute("touristAttraction", touristAttraction);
            model.addAttribute("billetPris", touristAttraction.getTicketPriceInDKK());
            return "show-attraction";
        }
        return "redirect:/"; //create fail state
    }

    @PostMapping("/konventer/{name}")
    public String kursKonvertering(@PathVariable String name, @RequestParam( value = "kurs") String kursNavn, Model model){
        TouristAttraction touristAttraction = touristService.getTouristAttraction(name);
        if (touristAttraction != null) {
            switch (kursNavn){
                case "DKK":
                    model.addAttribute("billetPris", touristAttraction.getTicketPriceInDKK());
                    break;
                case "EUR":
                    model.addAttribute("billetPris", currencyService.getPriceInEUR(touristAttraction.getTicketPriceInDKK()));
                break;
                case "USD":
                    model.addAttribute("billetPris", currencyService.getPriceInUSD(touristAttraction.getTicketPriceInDKK()));
                    break;

            }
            model.addAttribute("touristAttraction", touristAttraction);
            return "show-attraction";
        }
        return "redirect:/";
    }

    @GetMapping("/{name}/edit")
    public String showUpdateAttractionForm(@PathVariable String name, Model model) {
        TouristAttraction touristAttraction = touristService.getTouristAttraction(name);
        if (touristAttraction != null) {
            model.addAttribute("touristAttraction", touristAttraction);
            model.addAttribute("cities", touristService.getCities());
            model.addAttribute("tags", touristService.getTags());

            return "update-attraction-form";
        }
        return "redirect:/";
    }

    @GetMapping("/{name}/tags")
    public String showAttractionTags(@PathVariable String name, Model model) {
        TouristAttraction touristAttraction = touristService.getTouristAttraction(name);
        if (touristAttraction != null) {
            model.addAttribute("touristAttraction", touristAttraction);
            return "tags";
        }
        return "redirect:/attractions";
    }


    @PostMapping("/update")
    public String updateTouristAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.updateTouristAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/delete/{name}")
    public String deleteTouristAttraction(@PathVariable String name) {
        touristService.deleteTouristAttraction(name);
        return "redirect:/attractions";
    }

}
