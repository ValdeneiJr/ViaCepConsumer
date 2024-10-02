package edu.uea.service;

import com.google.gson.Gson;
import edu.uea.dtos.EnderecoDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientHtpp {
    private HttpClient httpClient;
    private String apiUrl;
    private Gson gson;

    public ClientHtpp(String apiUrl, Gson gson) {
        httpClient = HttpClient.newHttpClient();
        this.apiUrl = apiUrl;
        this.gson = gson;
    }

    public EnderecoDto consulta(String cep) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl + cep +"/json/")).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        EnderecoDto enderecoDto = gson.fromJson(response.body(), EnderecoDto.class);

        return enderecoDto;
    }
}
