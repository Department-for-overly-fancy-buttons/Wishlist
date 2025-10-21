package com.example.turistguide.controller;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.service.CurrencyService;
import com.example.turistguide.service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
public class TouristControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    @MockBean
    private CurrencyService currencyService;

    // Test ved endpointet /attractions viser listen rigtigt
    @Test
    public void getAllAttractions_returnsListViewAndModel() throws Exception
    {
        Mockito.when(touristService.getAllTouristAttraction()).thenReturn(List.of(new TouristAttraction("Tivoli", "Desc", "København", 170)));

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-list"))
                .andExpect(model().attributeExists("touristAttractions"));
    }

    //Test /attractions/add
    @Test
    public void showAddForm() throws Exception
    {
        Mockito.when(touristService.getCities()).thenReturn(List.of("København", "Aarhus"));
        Mockito.when(touristService.getTags()).thenReturn(List.of("GRATIS_INDGANG"));

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-attraction-form"))
                .andExpect(model().attributeExists("touristAttraction", "cities", "tags"));
    }
    // test af /attractions/save endpoint
    @Test
    public void save_success_redirects() throws Exception
    {
        var input = new TouristAttraction("NyAttraktion", "Desc", "Kbh", 170);
        Mockito.when(touristService.addTouristAttraction(any())).thenReturn(input);

        mockMvc.perform(post("/attractions/save")
                .param("name", "NyAttraktion")
                .param("description", "Desc")
                .param("city", "Kbh")
                .param("ticketPrice","170"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }

    // test hvis ovenstående test skulle fejle ved save
    @Test
    void save_failure_redirects() throws Exception
    {
        Mockito.when(touristService.addTouristAttraction(any())).thenReturn(null);

        mockMvc.perform(post("/attractions/save")
                        .param("name", "")
                        .param("ticketPriceInDKK", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // Test til vores attractions/{name}
    @Test
    public void showAttraction_found() throws Exception
    {
        var tivoli = new TouristAttraction("Tivoli", "Desc", "København", 170);
        Mockito.when(touristService.getTouristAttraction("Tivoli")).thenReturn(tivoli);

        mockMvc.perform(get("/attractions/Tivoli"))
                .andExpect(status().isOk())
                .andExpect(view().name("show-attraction"))
                .andExpect(model().attributeExists("touristAttraction", "billetPris"));
    }

    // Test til /attractions/{name} ved fejl
    @Test
    void showAttraction_notFound() throws Exception
    {
        Mockito.when(touristService.getTouristAttraction("Ukendt")).thenReturn(null);

        mockMvc.perform(get("/attractions/Ukendt"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // Test af vores POST-metode /attracions/konverter/{name} ift. valutapris
    @Test
    public void konvertering_setsPrice() throws Exception
    {
        var tivoli = new TouristAttraction("Tivoli","Desc","København",170);
        Mockito.when(touristService.getTouristAttraction("Tivoli")).thenReturn(tivoli);
        Mockito.when(currencyService.getPriceInEUR(170)).thenReturn(22.5);

        mockMvc.perform(post("/attractions/konventer/Tivoli").param("kurs","EUR"))
                .andExpect(status().isOk())
                .andExpect(view().name("show-attraction"))
                .andExpect(model().attributeExists("touristAttraction","billetPris"));
    }

    // Test til tags på en attraktion /attractions/{name}/tags
    @Test
    public void showTags_found() throws Exception
    {
        var tivoli = new TouristAttraction("Tivoli","Desc","København",170);
        Mockito.when(touristService.getTouristAttraction("Tivoli")).thenReturn(tivoli);

        try
        {
            mockMvc.perform(get("/attractions/Tivoli/tags"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("tags"))
                    .andExpect(model().attributeExists("touristAttraction"));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    // Hvis ovenstående med tags fejler /attractions/{name}/tags
    @Test
    public void showTags_notFound() throws Exception
    {
        Mockito.when(touristService.getTouristAttraction("Ukendt")).thenReturn(null);

        mockMvc.perform(get("/attractions/Ukendt/tags"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }
}
