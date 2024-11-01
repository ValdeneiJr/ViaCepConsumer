package edu.uea.service;

import com.google.gson.Gson;
import edu.uea.models.Endereco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenceService {
    private Gson gson;
    private String dirPath;

    public PersistenceService(Gson gson, String dirPath) {
        this.gson = gson;
        this.dirPath = dirPath;
    }

    public boolean existeCep(String cep) {
        Path path = Paths.get(dirPath + cep + ".json");

        return Files.exists(path);
    }

    public boolean persistCep(Endereco endereco) {
        Path path = Paths.get(dirPath + endereco.getCep().replace("-", "") + ".json");
        String json = gson.toJson(endereco);
        try{
            Files.write(path, json.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Endereco getEndereco(String cep) {
        Path path = Paths.get(dirPath + cep + ".json");
        try{
            String json = new String(Files.readAllBytes(path));
            Endereco endereco = gson.fromJson(json, Endereco.class);
            return endereco;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
