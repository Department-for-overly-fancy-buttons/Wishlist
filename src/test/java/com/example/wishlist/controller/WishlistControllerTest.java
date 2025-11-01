package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class WishlistControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Test
    public void getAllWishlists_returnsListViewAndModel() throws Exception
    {
        Mockito.when(wishlistService.getAllWishlists()).thenReturn(List.of());
        mockMvc.perform(get("/wishes/my_wishlists"))
                .andExpect(status().isOk())
                .andExpect(view().name("view_wishlists"))
                .andExpect(model().attributeExists("wishlists"));
    }

    @Test
    public void showAddForm_returnsAddWishFrom_andModel() throws Exception
    {
        mockMvc.perform(get("/wishes/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-wish-form"))
                .andExpect(model().attributeExists("wish"));
    }

    @Test
    public void save_success_redirectsToWishes() throws Exception
    {
        Mockito.when(wishlistService.addWish(any(Wish.class)))
                .thenReturn(new Wish(1, "NintendoTing", "desc", "url"));

        mockMvc.perform(post("/wishes/save")
                .param("name", "NintendoTing")
                .param("decription", "desc")
                .param("url", "url"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishes"));
    }

    @Test
    public void save_failure_redirectsHome() throws Exception
    {
        Mockito.when(wishlistService.addWish(any(Wish.class))).thenReturn(null);

        mockMvc.perform(post("/wishes/save")
                .param("name", "") //her for at få den til at fejle og returnerer null
                .param("description", "descrip")
                .param("url", "u"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void delete_success_redirectsWithFlash() throws Exception
    {
        Mockito.when(wishlistService.deleteWish(7)).thenReturn(true);

        mockMvc.perform(post("/wishes/7/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishes"));
    }

    @Test
    void delete_notFound_redirectsWithFlash() throws Exception {
        Mockito.when(wishlistService.deleteWish(99)).thenReturn(false);

        mockMvc.perform(post("/wishes/99/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishes"));
    }
}
