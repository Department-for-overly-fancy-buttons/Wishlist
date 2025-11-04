package com.example.wishlist.service;

import com.example.wishlist.model.Account;
import com.example.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)


public class AccountCreationServiceTest
{

    @Mock
    WishlistRepository repository;

    @InjectMocks
    WishlistService service;

//    @Test
//    public void addAccount_validAccount_callsRepoAndReturnsAccount()
//    {
//        Account input = new Account(0, "Patrick", "pass123");
//        when(repository.addAccount(input)).thenReturn(input);
//
//        Account result = service.addAccount(input);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getUsername()).isEqualTo("Patrick");
//        verify(repository).addAccount(input);
//    }
//
//    @Test
//    public void addAccount_missingUsername_returnsNull_andNoRepoCall()
//    {
//        Account noUsername = new Account(0, null, "pass123");
//
//        Account result = service.addAccount(noUsername);
//
//        assertThat(result).isNull();
//        verifyNoInteractions(repository);
//    }
//
//    @Test
//    public void addAccount_missingPassword_returnsNull_andNoRepositoryCall()
//    {
//        Account noPassword = new Account(0, "Patrick", null);
//
//        Account result = service.addAccount(noPassword);
//
//        assertThat(result).isNull();
//        verifyNoInteractions(repository);
//    }
}
