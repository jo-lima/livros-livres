package com.livros_livres.Server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.livros_livres.Server.services.ClienteService;

@Configuration
public class ServerConfiguration {
    @Bean
    public ClienteService clienteServices(){
        return new ClienteService();
    }
}
