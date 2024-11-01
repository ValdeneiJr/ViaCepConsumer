package edu.uea;

import com.google.gson.Gson;
import edu.uea.dtos.EnderecoDto;
import edu.uea.exceptions.CepInvalidoException;
import edu.uea.exceptions.CepNotFoundException;
import edu.uea.models.Endereco;
import edu.uea.service.ClientHtpp;
import edu.uea.service.PersistenceService;
import java.util.Scanner;

public class Main {
    private static String apiUrl = "https://viacep.com.br/ws/";
    private static String dirPath = "C:\\Users\\tahad\\Área de Trabalho\\dados_trab_palheta\\";

    private static Gson gson = new Gson();
    private static PersistenceService persistenceService = new PersistenceService(gson, dirPath);
    private static ClientHtpp client = new ClientHtpp(apiUrl, gson);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

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
                endereco = persistenceService.getEndereco(cep);

                if(endereco == null){
                    System.out.println("O endereço esta armazenado localmente mas não foi possivel" +
                            "recupera-lo no momento");
                }
                else{
                    System.out.println("Endereço já salvo localmente");
                    System.out.println(endereco);
                    System.out.println();
                }

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

            if (enderecoDto == null){
                System.out.println("Não foi possivel obter o CEP informado, problemas na API");
                continue;
            }

            endereco = new Endereco(enderecoDto);
            System.out.println(endereco);
            if (persistenceService.persistCep(endereco))
                System.out.println("Salvo localmente em: " + cep + ".json");
            System.out.println();
        }
    }

    public static String obterCep() throws CepInvalidoException {
        System.out.println("Insira o CEP a buscar ou SAIR(sair do buscador)");
        String cep = scanner.nextLine();

        if(cep.equalsIgnoreCase("sair")) return null;

        if (cep.isEmpty() || !cep.matches("\\d{8}|\\d{5}-\\d{3}"))
            throw new CepInvalidoException("Cep invalido, se atente ao formato padrão para cep!");

        if (cep.contains("-")) cep = cep.replace("-", "");

        System.out.println();

        return cep;
    }
}