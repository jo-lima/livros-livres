package com.livros_livres.Server.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class DebugService {

    // methods
    public static void log(String message){
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = String.format("%02d/%02d - %02d:%02d:%02d",
            currentDate.getDayOfMonth(),
            currentDate.getMonthValue(),
            currentDate.getHour(),
            currentDate.getMinute(),
            currentDate.getSecond());

        System.out.println("LIVROS-LIVRES LOG: (%s) # '%s'".formatted(formattedDate, message));
    }

    public static void log(String message, Object objetct){
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = String.format("%02d/%02d - %02d:%02d:%02d",
            currentDate.getDayOfMonth(),
            currentDate.getMonthValue(),
            currentDate.getHour(),
            currentDate.getMinute(),
            currentDate.getSecond());

        System.out.println("LIVROS-LIVRES LOG: (%s) # '%s'".formatted(formattedDate, message) + objetct);
    }

}
