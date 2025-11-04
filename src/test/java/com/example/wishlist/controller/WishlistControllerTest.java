package com.example.wishlist.controller;

import com.example.wishlist.model.Account;
import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

//    @Test
//    public void getAllWishlists_returnsListViewAndModel() throws Exception
//    {
//        Mockito.when(wishlistService.getAllMyWishlists(any(Account.class))).thenReturn(List.of());
//        mockMvc.perform(get("/wishes/my_wishlists"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("view_wishlists"))
//                .andExpect(model().attributeExists("wishlists"));
//
//        verify(wishlistService).getAllMyWishlists(any(Account.class));
//    }

//    @Test
//    public void showAddForm_returnsAddWishFrom_andModel() throws Exception
//    {
//        mockMvc.perform(get("/wishes/add"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("add-wish-form"))
//                .andExpect(model().attributeExists("wish"));
//    }

//    @Test
//    public void save_success_redirectsToWishes() throws Exception
//    {
//        Mockito.when(wishlistService.addWish(any(Wish.class)))
//                .thenReturn(new Wish(1, "NintendoTing", "desc", "url"));
//
//        mockMvc.perform(post("/wishes/save")
//                .param("name", "NintendoTing")
//                .param("description", "desc")
//                .param("url", "url"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/wishes"));
//
//        ArgumentCaptor<Wish> captor = ArgumentCaptor.forClass(Wish.class);
//        verify(wishlistService).addWish(captor.capture());
//        Wish capturedWish = captor.getValue();
//        //check id eller fjern id som instantsvariable
//        assertEquals("NintendoTing", capturedWish.getName());
//        assertEquals("desc", capturedWish.getDescription());
//        assertEquals("url", capturedWish.getUrl());
//    }

//    @Test
//    public void save_failure_redirectsHome() throws Exception
//    {
//        Mockito.when(wishlistService.addWish(any(Wish.class))).thenReturn(null);
//
//        mockMvc.perform(post("/wishes/save")
//                .param("name", "") //her for at få den til at fejle og returnerer null
//                .param("description", "descrip")
//                .param("url", "u"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }

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
