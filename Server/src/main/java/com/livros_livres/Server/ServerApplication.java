package com.livros_livres.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.livros_livres.Server.Services.AuthenticationService;

@EnableScheduling
@EntityScan("com.livros_livres.Server.*")
@EnableJpaRepositories("com.livros_livres.Server.*")
@ComponentScan(basePackages = { "com.livros_livres.Server.*" })
@SpringBootApplication()
public class ServerApplication {

	@Value("${livrosLivres.debug}") // Getting value from application.properties
    private boolean debug;
	@Autowired
	AuthenticationService authService;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
    public CommandLineRunner run() {
        return args -> {
			if(debug){
				authService._addDebugAuth();
			}
		};
    }

}
