package com.consultorioMedicoJava.util;

import com.consultorioMedicoJava.model.Endereco;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViaCepClient {

    public static Endereco lookup(String cep) {
        if (cep == null) return null;
        String digits = cep.replaceAll("\\D", "");
        if (digits.length() != 8) return null;
        String url = "https://viacep.com.br/ws/" + digits + "/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            if (body == null || body.isEmpty()) return null;
            if (body.toLowerCase().contains("\"erro\"")) return null;

            String estadoFull = extract(body, "estado");
            String uf = extract(body, "uf");
            String localidade = extract(body, "localidade");
            String logradouro = extract(body, "logradouro");
            String bairro = extract(body, "bairro");
            String cepRet = extract(body, "cep");

            Endereco e = new Endereco();

            String estadoToUse = (estadoFull != null && !estadoFull.isBlank()) ? estadoFull : (uf != null ? uf : "");
            if (!estadoToUse.isBlank()) e.setEstado(estadoToUse);

            if (localidade != null && !localidade.isBlank()) e.setCidade(localidade);

            String ruaToUse = "";
            if (logradouro != null && !logradouro.isBlank()) ruaToUse = logradouro;
            if (ruaToUse.isBlank() && bairro != null && !bairro.isBlank()) ruaToUse = bairro;
            if (!ruaToUse.isBlank()) e.setRua(ruaToUse);

            if (cepRet != null && !cepRet.isBlank()) {
                String digitsRet = cepRet.replaceAll("\\D", "");
                if (digitsRet.length() == 8) {
                    String formatted = digitsRet.substring(0,5) + "-" + digitsRet.substring(5);
                    e.setCep(formatted);
                } else {
                    e.setCep(cepRet);
                }
            } else {
                String formatted = digits.substring(0,5) + "-" + digits.substring(5);
                e.setCep(formatted);
            }

            return e;
        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }

    private static String extract(String json, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\\\"([^\\\"]*)\\\"", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
