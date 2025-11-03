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
    // Laver opdeling efter AAA, Arrange, Act og Assert princippet
    @Test
    void addWish_success_returnsWish_andCallsRepo()
    {
            // arranger her
            Wish input = new Wish(0, "Billede", "En meget flot mark", "https://www.url.dk");
            when(repository.getWishByName("Billede")).thenReturn(null);
            when(repository.addWish(input)).thenReturn(input);

            // act
            Wish result = service.addWish(input);

            // assert (samme med  andre tests)
            assertThat(result).isNotNull();
            verify(repository).getWishByName("Billede");
            verify(repository).addWish(input);
    }

    @Test
    void addWish_duplicateName_returnsNull_andDoesNotCallAdd() {
        Wish input = new Wish(0, "Billede", "desc", "url");
        when(repository.getWishByName("Billede"))
                .thenReturn(new Wish(1, "Billede", "desc", "url"));

        Wish result = service.addWish(input);

        assertThat(result).isNull();
        verify(repository).getWishByName("Billede");
        verify(repository, never()).addWish(any());
    }

    @Test
    void addWish_invalidName_returnsNull_andNoRepoCalls() {
        Wish nullName = new Wish(0, null, "desc", "url");
        Wish emptyName = new Wish(0, "", "desc", "url");

        assertThat(service.addWish(nullName)).isNull();
        assertThat(service.addWish(emptyName)).isNull();

        verifyNoInteractions(repository);
    }
}
