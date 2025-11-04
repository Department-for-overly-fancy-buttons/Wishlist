package com.example.wishlist.service;

import com.example.wishlist.exceptions.AccountNotFoundException;
import com.example.wishlist.model.Account;
import com.example.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountLoginServiceTest
{
    @Mock
    WishlistRepository repository;

    @InjectMocks
    WishlistService service;

    @Test
    public void logIn_validLog_returnAcc_andCallRepo()
    {
        Account typedAccount = new Account(0, "Patrick", "pass123");
        Account storedAccount = new Account(1, "Patrick", "pass123");
        when(repository.getAccount(typedAccount)).thenReturn(storedAccount);

        Account result = service.logIn(typedAccount);

        assertThat(result).isSameAs(storedAccount);
        verify(repository).getAccount(typedAccount);
    }

    // Vi bruger en lambda her, fordi den gør det muligt at køre metoden inde i assertThrows
    // uden at den bliver kaldt med det samme. På den måde kan JUnit fange exceptionen, når den sker.
    @Test
    public void logIn_wrongPassword_throwsException()
    {
        Account typedAccount = new Account(0, "Patrick", "pass123");
        Account storedAccount = new Account(1, "Patrick", "AndetPass123");

        when(repository.getAccount(typedAccount)).thenReturn(storedAccount);

        assertThrows(AccountNotFoundException.class, () -> service.logIn(typedAccount));

        verify(repository).getAccount(typedAccount);;
    }
    // Samme som ved tidl. testmetode ift. lambda
    @Test
    public void logIn_accountNotFound_throwsException()
    {
        Account typedAccount = new Account(0, "Patrick", "pass123");
        when(repository.getAccount(typedAccount)).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> service.logIn(typedAccount));

        verify(repository).getAccount(typedAccount);
    }

}
