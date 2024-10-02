package edu.uea;

import com.google.gson.Gson;
import edu.uea.dtos.EnderecoDto;
import edu.uea.models.Endereco;
import edu.uea.service.ClientHtpp;
import edu.uea.service.PersistenceService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static String apiUrl = "https://viacep.com.br/ws/";
    private static String dirPath = "/home/junior/Área de trabalho/dados_trab_palheta/";

    private static Gson gson = new Gson();
    private static PersistenceService persistenceService = new PersistenceService(gson, dirPath);
    private static ClientHtpp client = new ClientHtpp(apiUrl, gson);

    public static void main(String[] args) throws IOException, InterruptedException {

        EnderecoDto enderecoDto;
        Endereco endereco;

        String cep = "69086669";

        if (persistenceService.existeCep(cep)){
            enderecoDto = persistenceService.getEndereco(cep);
            endereco = new Endereco(enderecoDto);

            System.out.println(endereco);
        }

        enderecoDto = client.consulta(cep);
        endereco = new Endereco(enderecoDto);
        System.out.println(endereco);
        if (persistenceService.persistCep(enderecoDto)) System.out.println("Arquivo: " + enderecoDto.cep()
                .replace("-", "") + ".json");
        else System.out.println("Não foi possivel salvar seu arquivo");
    }
}