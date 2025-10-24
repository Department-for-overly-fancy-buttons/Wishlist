package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest
{
    @Mock
    private WishlistRepository repository;

    @InjectMocks
    private WishlistService service;

    // Sætter det op med navn som "metode der testes" efterfulgt af betingelsen og efterfulgt af det resultat vi ønsker. Har læst det skulle være den "rigtige" opsætning
    @Test
    void addWish_success_returnsWish_andCallsRepo()
    {
        Wish input = new Wish("Billede", "En meget flot mark", "https://www.url.dk");
        when(repository.getWish("Billede")).thenReturn(new Wish("Billede", "En meget flot mark", "https://www.url.dk"));

        Wish result = service.addWish(input);

        assertThat(result).isNull();
        verify(repository).getWish("Billede");
        verify(repository).addWish(input);
    }

    @Test
    void addWish_duplicate_returnsNull_andDoesNotCallAdd()
    {
        Wish input = new Wish("Billede", "En meget flot mark", "https://www.url.dk");
        when(repository.getWish("Billede")).thenReturn(new Wish("Billede", "En meget flot mark", "https://www.url.dk"));

        Wish result = service.addWish(input);

        assertThat(result).isNull();
        verify(repository).getWish("Billede");
        verify(repository, never()).addWish(any());
    }

    @Test
    void addWish_invalidName_returnsNull_andNoRepoCalls()
    {
        Wish nullName = new Wish(null, "description", "url");
        Wish emptyName = new Wish("", "description", "url");

        verifyNoInteractions(repository);
    }
}
