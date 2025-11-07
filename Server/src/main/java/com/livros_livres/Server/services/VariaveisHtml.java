package com.livros_livres.Server.services;

public class VariaveisHtml {
    
    public static String html_validarToken(String codigoVerificacao) { 
        return "<!doctype html>"
        + "<html lang=\"pt-BR\">"
        + "<head>"
        + "  <meta charset=\"utf-8\"/>"
        + "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"/>"
        + "  <title>Validar Token - Livros Livres</title>"
        + "</head>"
        + "<body style=\"font-family:Arial, sans-serif; background:#f6f6f6; margin:0; padding:20px;\">"
        + "  <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:600px; margin:0 auto; background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 6px rgba(0,0,0,0.1);\">"
        + "    <tr>"
        + "      <td style=\"padding:24px; text-align:center; background:#D4A373; color:#ffffff;\">"
        + "        <h1 style=\"margin:0; font-size:20px;\">Livros Livres</h1>"
        + "        <p style=\"margin:6px 0 0; font-size:14px;\">Validação de Token</p>"
        + "      </td>"
        + "    </tr>"
        + "    <tr>"
        + "      <td style=\"padding:24px; color:#333333;\">"
        + "        <p>Olá, dog!</p>"
        + "        <p>Use o código abaixo para validar seu e-mail:</p>"
        + "        <p style=\"text-align:center; margin:20px 0;\">"
        + "          <span style=\"display:inline-block; padding:12px 18px; font-size:18px; letter-spacing:2px; border-radius:6px; background:#f1f5f9;\">"
        +                codigoVerificacao
        + "          </span>"
        + "        </p>"
        + "        <p style=\"font-size:13px; color:#666666;\">Se você não solicitou este e-mail, favor ignorá-lo.</p>"
        + "        <p>Livros Livres</p>"
        + "      </td>"
        + "    </tr>"
        + "    <tr>"
        + "      <td style=\"padding:14px; text-align:center; font-size:12px; color:#999999; background:#fafafa;\">"
        + "        &copy; " + java.time.Year.now().getValue() + " Livros Livres. Todos os direitos reservados."
        + "      </td>"
        + "    </tr>"
        + "  </table>"
        + "</body>"
        + "</html>";

    }


    public static String html_esqueciSenha(String codigoVerificacao) { 
        return "<!doctype html>"
        + "<html lang=\"pt-BR\">"
        + "<head>"
        + "  <meta charset=\"utf-8\"/>"
        + "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"/>"
        + "  <title>Validar Token - Livros Livres</title>"
        + "</head>"
        + "<body style=\"font-family:Arial, sans-serif; background:#f6f6f6; margin:0; padding:20px;\">"
        + "  <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:600px; margin:0 auto; background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 6px rgba(0,0,0,0.1);\">"
        + "    <tr>"
        + "      <td style=\"padding:24px; text-align:center; background:#F09618; color:#00000;\">"
        + "        <h1 style=\"margin:0; font-size:20px; font-weight:bold;\">Livros Livres</h1>"
        + "        <p style=\"margin:6px 0 0; font-size:14px;\">Codigo de verificação - Esqueceu a senha</p>"
        + "      </td>"
        + "    </tr>"
        + "    <tr>"
        + "      <td style=\"padding:24px; color:#ffffff;\">"
        + "        <p>Olá, dog!</p>"
        + "        <p>Use o código abaixo para efetuar a troca de sua senha:</p>"
        + "        <p style=\"text-align:center; margin:20px 0;\">"
        + "          <span style=\"display:inline-block; padding:12px 18px; font-size:18px; letter-spacing:2px; border-radius:6px; background:#f1f5f9;\">"
        +                codigoVerificacao
        + "          </span>"
        + "        </p>"
        + "        <p style=\"font-size:13px; color:#666666;\">Se você não solicitou essa troca de senha, favor ignorá-lo.</p>"
        + "        <p>Livros Livres</p>"
        + "      </td>"
        + "    </tr>"
        + "    <tr>"
        + "      <td style=\"padding:14px; text-align:center; font-size:12px; color:#999999; background:#fafafa;\">"
        + "        &copy; " + java.time.Year.now().getValue() + " Livros Livres. Todos os direitos reservados."
        + "      </td>"
        + "    </tr>"
        + "  </table>"
        + "</body>"
        + "</html>";

    }
}
