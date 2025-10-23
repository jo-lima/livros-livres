package com.livros_livres.Server.Registers.Server;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetornoApi {

    private int statusCode;
    private String message;
    private Optional<Object> body;

    // constructors
    private RetornoApi() {
        this.body=Optional.empty();
    };

    private RetornoApi(Integer statusCode, String message, Object body){
        this.statusCode=statusCode;
        this.message=message;
        this.body=Optional.ofNullable(body);
    }
    private RetornoApi(Integer statusCode, String message){
        this.statusCode=statusCode;
        this.message=message;
        this.body=Optional.empty();
    }

    // methods
    public static RetornoApi generic(Integer statusCode, String message, Object body){
        return new RetornoApi(statusCode, message, body);
    }

    public static RetornoApi sucess(String message){
        return new RetornoApi(200, message);
    }

    public static RetornoApi sucess(String message, Object body){
        return new RetornoApi(200, message, body);
    }

    public static RetornoApi sucess(Integer statusCode, String message, Object body){
        return new RetornoApi(statusCode, message, body);
    }

    public static RetornoApi error(int statusCode, String message){
        return new RetornoApi(statusCode, message);
    }

    public static RetornoApi errorBadRequest(String message){
        return new RetornoApi(400, message);
    }

    public static RetornoApi errorNotFound(){
        return new RetornoApi(404, "Not Found");
    }

    public static RetornoApi errorNotFound(String message){
        return new RetornoApi(404, message);
    }

    public static RetornoApi errorForbidden(){
        return new RetornoApi(403, "Você não possui os privilégios necessários para acessar esta api.");
    }

    public static RetornoApi errorInternal(){
        return new RetornoApi(500, "Ocorreu um erro interno não mapeado.");
    }

    // Endpoint-specific methods.
    public static RetornoApi errorLoginNotFound(){
        return new RetornoApi(404, "Nenhum usuário encontrado para combinação de email e senha apresentados.");
    }

}
