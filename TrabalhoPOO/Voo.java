import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Voo{                                  // Classe Voo

    private String localPartida, localChegada, data, horarioPartida;
    private int assentosDisponiveis, dataInt;
    private float preco;

    public Voo(String localPartida, String localChegada, String data, String horarioPartida, int assentosDisponiveis, float preco){
        this.localPartida = localPartida;
        this.localChegada = localChegada;
        this.data = data;
        this.dataInt = transformarData(data);
        this.horarioPartida = horarioPartida;
        this.assentosDisponiveis = assentosDisponiveis;
        this.preco = preco;
    }

    private int transformarData(String data){            // Transformar a data em dias de 1 a 184 (começando do dia 01/07/2024)
        List<Integer> diasMesesAnos = new ArrayList<>();
        String[] partes = data.split("/");
        for (String parte : partes){
            diasMesesAnos.add(Integer.parseInt(parte));
        }
        if (diasMesesAnos.get(1) < 9){
            return diasMesesAnos.get(0) + (diasMesesAnos.get(1)-7)*31;
        }
        if (diasMesesAnos.get(1) == 9 || diasMesesAnos.get(1) == 10){
            return diasMesesAnos.get(0) + (diasMesesAnos.get(1)-7)*31 - 1;
        }
        else{
            return diasMesesAnos.get(0) + (diasMesesAnos.get(1)-7)*31 - 2;
        }
    }

    public void setLocalPartida(String localPartida){    // Gets e sets
        this.localPartida = localPartida;
    }

    public void setLocalChegada(String localChegada){
        this.localChegada = localChegada;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setHorarioPartida(String horarioPartida){
        this.horarioPartida = horarioPartida;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis){
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public void setPreco(float preco){
        this.preco = preco;
    }

    public String getLocalPartida(){
        return localPartida;
    }

    public String getLocalChegada(){
        return localChegada;
    }

    public String getData(){
        return data;
    }

    public int getDataInt(){
        return dataInt;
    }

    public String getHorarioPartida(){
        return horarioPartida;
    }

    public int getAssentosDisponiveis(){
        return assentosDisponiveis;
    }

    public float getPreco(){
        return preco;
    }
}