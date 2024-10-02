package edu.uea.service;

import com.google.gson.Gson;
import edu.uea.dtos.EnderecoDto;
import edu.uea.models.Endereco;

import javax.imageio.IIOException;
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

    public boolean persistCep(EnderecoDto enderecoDto) {
        Path path = Paths.get(dirPath + enderecoDto.cep().replace("-", "") + ".json");
        String json = gson.toJson(enderecoDto);
        try{
            Files.write(path, json.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public EnderecoDto getEndereco(String cep) {
        Path path = Paths.get(dirPath + cep + ".json");
        try{
            String json = new String(Files.readAllBytes(path));
            EnderecoDto enderecoDto = gson.fromJson(json, EnderecoDto.class);
            return enderecoDto;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
