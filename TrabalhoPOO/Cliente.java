
public class Cliente implements Runnable{               // Classe Cliente
    private String nome, localPartida, localChegada;
    private int tempoEstadia, minEstrelas;
    private float orcamentoMaximo;
    private boolean reservou;

    public Cliente(String nome, String localPartida, String localChegada, int tempoEstadia, int minEstrelas, float orcamentoMaximo){
        this.nome = nome;
        this.localPartida = localPartida;
        this.localChegada = localChegada;
        this.tempoEstadia = tempoEstadia;
        this.minEstrelas = minEstrelas;
        this.orcamentoMaximo = orcamentoMaximo;
        this.reservou = false;
    }

    public void setNome(String nome){                   // Gets e sets
        this.nome = nome;
    }

    public void setLocalPartida(String localPartida){
        this.localPartida = localPartida;
    }

    public void setLocalChegada(String localChegada){
        this.localChegada = localChegada;
    }

    public void setTempoEstadia(int tempoEstadia){
        this.tempoEstadia = tempoEstadia;
    }

    public void setMinEstrelas(int minEstrelas){
        this.minEstrelas = minEstrelas;
    }

    public void setOrcamentoMaximo(float orcamentoMaximo){
        this.orcamentoMaximo = orcamentoMaximo;
    }

    public void setReservou(boolean a){
        this.reservou = a;
    }

    public String getNome(){
        return nome;
    }

    public String getLocalPartida(){
        return localPartida;
    }

    public String getLocalChegada(){
        return localChegada;
    }

    public int getTempoEstadia(){
        return tempoEstadia;
    }

    public int getMinEstrelas(){
        return minEstrelas;
    }

    public float getOrcamentoMaximo(){
        return orcamentoMaximo;
    }

    public boolean getReservou(){
        return reservou;
    }
    
    @Override
    public void run(){
        Main2.tarefa(this);
    }
} 