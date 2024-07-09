import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        long tempoComeco = System.currentTimeMillis();

        // Caminhos dos arquivos
        String caminhoHoteis = "entradas_hoteis.csv";
        String caminhoVoos = "entradas_voos.csv";
        String caminhoClientes = "entradas_clientes_100.csv";
        String linha;
        List<Hotel> hoteis = new ArrayList<>();
        List<Voo> voos = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();

        // Armazenar os dados dos hotéis
        try (BufferedReader hoteisBr = new BufferedReader(new FileReader(caminhoHoteis))) {
            while ((linha = hoteisBr.readLine()) != null) {
                String[] partes = linha.split(";");
                String local = partes[0];
                String nome = partes[1];
                String[] p2 = partes[2].split(" ");
                int vagas = Integer.parseInt(p2[0]);
                String[] p3 = partes[3].split(" ");
                float preco = Float.parseFloat(p3[1].replace(",", "."));
                String[] p4 = partes[4].split(" ");
                int estrelas = Integer.parseInt(p4[0]);
                Hotel h = new Hotel(local, nome, vagas, preco, estrelas);
                hoteis.add(h);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        hoteis.sort(Comparator.comparing(Hotel::getPreco)); // Ordena pelo mais barato

        // Armazenar os dados dos voos
        try (BufferedReader voosBr = new BufferedReader(new FileReader(caminhoVoos))) {
            while ((linha = voosBr.readLine()) != null) {
                String[] partes = linha.split(";");
                String localPartida = partes[0];
                String localChegada = partes[1];
                String data = partes[2];
                String horarioPartida = partes[3];
                String[] p4v = partes[4].split(" ");
                int assentosDisponiveis = Integer.parseInt(p4v[0]);
                String[] p5v = partes[5].split(" ");
                float preco = Float.parseFloat(p5v[1].replace(",", "."));
                Voo v = new Voo(localPartida, localChegada, data, horarioPartida, assentosDisponiveis, preco);
                voos.add(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Armazenar os dados dos clientes
        try (BufferedReader clientesBr = new BufferedReader(new FileReader(caminhoClientes))) {
            while ((linha = clientesBr.readLine()) != null) {
                String[] partes = linha.split(";");
                String nome = partes[0];
                String localPartida = partes[1];
                String localChegada = partes[2];
                String[] p3c = partes[3].split(" ");
                int tempoEstadia = Integer.parseInt(p3c[0]);
                String[] p4c = partes[4].split(" ");
                int minEstrelas = Integer.parseInt(p4c[0]);
                String[] p5c = partes[5].split(" ");
                float orcamentoMaximo = Float.parseFloat(p5c[1].replace(",", "."));
                Cliente c = new Cliente(nome, localPartida, localChegada, tempoEstadia, minEstrelas, orcamentoMaximo);
                clientes.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Object> dados = new ArrayList<>();
        
        dados.add(0); // total de pedidos
        dados.add(0); // total de clientes
        dados.add(0); // total de pedidos atendidos
        dados.add(0.0); // valor total gasto pelos clientes
        dados.add(0.0); // valor total gasto em hotéis
        dados.add(0.0); // valor total gasto em voos

        for (Cliente cliente2 : clientes) { // itera lista de clientes
            dados.set(1, (int) dados.get(1) + 1); // +1 cliente
            String c = cliente2.getLocalChegada();
            int e = cliente2.getMinEstrelas();
            int t = cliente2.getTempoEstadia();
            String p = cliente2.getLocalPartida();

            for (Hotel hotel1 : hoteis) { // itera lista de hoteis
                if (hotel1.getEstrelas() < e) { // ignora hotel abaixo do numero de estrelas
                    continue;
                }
                if (hotel1.getLocal().equals(c)) {
                    if (hotel1.getPreco() * t > cliente2.getOrcamentoMaximo()) {
                        // rejeitar oferta
                        cliente2.setReservou(false);
                        break; // rejeita ofertas se a opção mais barata for acima do valor máximo
                    }
                    else{
                        dados.set(0, (int) dados.get(0) + 1); // +1 pedido
                    }
                    for (int i = 0; i < (184 - t); i++) { // checa se tem vaga
                        if (hotel1.getDias(i) > 0) {
                            for (int j = 1; j < t; j++) { // avalia se a vaga fica disponível pela duração da estadia
                                if (hotel1.getDias(i + j) > 0) {
                                    if (j == (t - 1)) {
                                        // procurar voos
                                        List<Voo> voos1 = new ArrayList<>(); // armazena voos que chegam ao destino
                                        List<List<Object>> allVoos = new ArrayList<>(); // armazena todos os voos diretos e conexões, no formato [voo1, voo2, precoTotal] (voo2 será nulo em caso de voo direto).
                                        for (int k = 0; k < 2; k++){
                                            for (Voo voo : voos) {
                                                if (voo.getDataInt() == i + 1) {
                                                    if (voo.getAssentosDisponiveis() > 0) {
                                                        if (voo.getLocalChegada().equals(c) && voo.getLocalPartida().equals(p)) { // se tanto local de chegada quanto de partida forem iguais, temos um voo direto.
                                                            List<Object> vooInfo = new ArrayList<>();
                                                            vooInfo.add(voo);
                                                            vooInfo.add(null);
                                                            vooInfo.add(voo.getPreco());
                                                            if (!allVoos.contains(vooInfo)){
                                                                allVoos.add(vooInfo);
                                                            }
                                                        }
                                                        if (voo.getLocalChegada().equals(c) && !voo.getLocalPartida().equals(p)) { // se o local de chegada for igual e o de partida diferente, armazena na lista voos1.
                                                            voos1.add(voo);
                                                        }
                                                        for (Voo vooC : voos1) {
                                                            if (vooC.getLocalPartida().equals(voo.getLocalChegada()) && !vooC.equals(voo)) { // se o local de partida de um voo da lista voos1 for igual ao local de chegada desse voo sendo analisado, temos uma conexão de "voo" até "vooC".
                                                                List<Object> vooInfo = new ArrayList<>();
                                                                vooInfo.add(voo);
                                                                vooInfo.add(vooC);
                                                                vooInfo.add(voo.getPreco() + vooC.getPreco());
                                                                if (!allVoos.contains(vooInfo)){
                                                                    allVoos.add(vooInfo);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    
                                        // sugerir o voo mais barato
                                        Collections.sort(allVoos, new Comparator<List<Object>>() { // ordena a matriz do voo mais barato ao mais caro
                                            @Override
                                            public int compare(List<Object> o1, List<Object> o2) {
                                                return Float.compare((Float) o1.get(2), (Float) o2.get(2));
                                            }
                                        });
                                        // adicionar sugestão completa para escrever no arquivo de saida, caso a soma entre o preco total do hotel e do(s) voo(s) atenda ao orcamento do cliente
                                        if (allVoos.size() > 0 && hotel1.getPreco() * t + (Float) allVoos.get(0).get(2) <= cliente2.getOrcamentoMaximo()) {
                                            float precoVooFinal = (Float) allVoos.get(0).get(2);
                                            String viagem;
                                            if (allVoos.get(0).get(1) == null) {
                                                viagem = ((Voo) allVoos.get(0).get(0)).getLocalPartida() + ";" + ((Voo) allVoos.get(0).get(0)).getLocalChegada();
                                            } else {
                                                viagem = ((Voo) allVoos.get(0).get(0)).getLocalPartida() + ";" + ((Voo) allVoos.get(0).get(0)).getLocalChegada() + " -> " + ((Voo) allVoos.get(0).get(1)).getLocalPartida() + ";" + ((Voo) allVoos.get(0).get(1)).getLocalChegada();
                                            }
                                            cliente2.setReservou(true);
                                            // atualizar vagas do hotel e assentos dos voos a serem sugeridos
                                            hotel1.setDias(i, hotel1.getDias(i));
                                            ((Voo) allVoos.get(0).get(0)).setAssentosDisponiveis(((Voo) allVoos.get(0).get(0)).getAssentosDisponiveis() - 1);
                                            if (allVoos.get(0).get(1) != null) {
                                                ((Voo) allVoos.get(0).get(1)).setAssentosDisponiveis(((Voo) allVoos.get(0).get(1)).getAssentosDisponiveis() - 1);
                                            }
                                            dados.set(2, (int) dados.get(2) + 1); // +1 pedido atendido pelo cliente
                                            dados.set(4, (double) dados.get(4) + hotel1.getPreco()); // adiciona o preco do hotel 
                                            dados.set(5, (double) dados.get(5) + precoVooFinal); // adiciona o preco do voo
                                        }

                                    }
                                } else {
                                    break; // tenta agendar outro dia
                                }
                            }
                        }
                        if (cliente2.getReservou() == true) { // para de buscar dias caso já tenha reservado
                            break;
                        }
                    }
                }

                if (cliente2.getReservou() == true) { // para de buscar hotéis caso já tenha reservado
                    break;
                }
            }
        }
        dados.set(3, (double) dados.get(4) + (double) dados.get(5)); // total gasto pelos clientes
        String nomeArquivo = "ArqSaidaSeq.txt";
        String dadosA = dados.get(0) + " " + dados.get(1) + " " + dados.get(2) + " " + dados.get(3) + " " + dados.get(4) + " " + dados.get(5);

        try (BufferedWriter b = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            b.write(dadosA);
            b.newLine();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo: " + e.getMessage());
        }
        
        long tempoFinal = System.currentTimeMillis();

        long duracao = tempoFinal - tempoComeco;

        System.out.println("Tempo de execucao: " + duracao + "ms");
    }
}
