package edu.uea;

import com.google.gson.Gson;
import edu.uea.dtos.EnderecoDto;
import edu.uea.exceptions.CepInvalidoException;
import edu.uea.exceptions.CepNotFoundException;
import edu.uea.models.Endereco;
import edu.uea.service.ClientHtpp;
import edu.uea.service.PersistenceService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String apiUrl = "https://viacep.com.br/ws/";
    private static String dirPath = "/home/junior/Área de trabalho/dados_trab_palheta/";

    private static Gson gson = new Gson();
    private static PersistenceService persistenceService = new PersistenceService(gson, dirPath);
    private static ClientHtpp client = new ClientHtpp(apiUrl, gson);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {

        EnderecoDto enderecoDto;
        Endereco endereco;
        String cep;

        System.out.println("Bem vindo ao buscador de cep");

        while (true){
            try{
                cep = obterCep();
            }
            catch (CepInvalidoException e){
                System.out.println(e.getMessage());
                System.out.println();
                continue;
            }

            if(cep == null) break;

            if (persistenceService.existeCep(cep)){
                enderecoDto = persistenceService.getEndereco(cep);
                endereco = new Endereco(enderecoDto);

                System.out.println("Endereço já salvo localmente");
                System.out.println(endereco);
                System.out.println();

                continue;
            }

            try{
                enderecoDto = client.consulta(cep);
            }
            catch (CepNotFoundException e){
                System.out.println(e.getMessage());
                System.out.println();
                continue;
            }

            endereco = new Endereco(enderecoDto);
            System.out.println(endereco);
            if (persistenceService.persistCep(enderecoDto)) System.out.println("Salvo localmente em: " + cep + ".json");

            System.out.println();
        }
    }

    public static String obterCep() throws CepInvalidoException {
        System.out.println("Insira o CEP a buscar ou SAIR(sair do buscador)");
        String cep = scanner.nextLine();

        if(cep.toLowerCase().equals("sair")) return null;

        if (cep.contains("-")) cep = cep.replace("-", "");

        if (cep.isEmpty() || !cep.matches("[0-9]{8}"))
            throw new CepInvalidoException("Cep invalido, se atente ao formato padrão para cep!");

        return cep;
    }
}