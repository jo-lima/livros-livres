package com.livros_livres.Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livros_livres.Server.Registers.Server.RetornoApi;
import com.livros_livres.Server.Repository.EmprestimoRepo;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepo emprestimoRepo;
    @Autowired
    AuthenticationService authService;

    public RetornoApi test() {
        return RetornoApi.errorForbidden();
    }
}
