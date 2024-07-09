import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Hotel{                        // Classe Hotel

    private String local, nome;
    private int vagas, estrelas;
    private float preco;
    private List<Integer> dias;

    public Hotel(String local, String nome, int vagas, float preco, int estrelas){
        this.local = local;
        this.nome = nome;
        this.vagas = vagas;
        this.preco = preco;
        this.estrelas = estrelas;
        this.dias = new ArrayList<>(Collections.nCopies(184, vagas));
    }

    public void setLocal(String local){   // Gets e sets
        this.local = local;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setVagas(int vagas){
        this.vagas = vagas;
    }

    public void setPreco(float preco){
        this.preco = preco;
    }

    public void setEstrelas(int estrelas){
        this.estrelas = estrelas;
    }

    public void setDias(int i, int d){
        this.dias.set(i, d);
    }

    public String getLocal(){
        return local;
    }

    public String getNome(){
        return nome;
    }

    public int getVagas(){
        return vagas;
    }

    public float getPreco(){
        return preco;
    }

    public int getEstrelas(){
        return estrelas;
    }

    public int getDias(int i){
        return dias.get(i);
    }
}