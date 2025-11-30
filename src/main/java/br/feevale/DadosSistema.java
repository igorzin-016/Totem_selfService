package br.feevale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.feevale.model.Pedido;

public class DadosSistema {
    
    // Nome do arquivo onde os dados ficarão salvos
    private static final String ARQUIVO_DB = "banco_pedidos.bin";
    
    private static List<Pedido> pedidos;

    // Bloco estático (roda assim que o programa inicia)
    static {
        pedidos = new ArrayList<>();
        carregarDados(); // Verifica com a versão com a atual
    }

    public static List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public static void adicionarPedido(Pedido p) {
        pedidos.add(p);
        salvarDados(); // SALVA NO DISCO SEMPRE QUE ADICIONA
    }
    
    // Método auxiliar para salvar mudanças (usado quando muda status)
    public static void atualizarBanco() {
        salvarDados();
    }

    // --- MÉTODOS DE PERSISTÊNCIA ---

    private static void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DB))) {
            oos.writeObject(pedidos);
            // System.out.println("Dados salvos com sucesso!"); Teste 
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void carregarDados() {
        File arquivo = new File(ARQUIVO_DB);
        if (!arquivo.exists()) {
            return; // Se não tem arquivo, começa com lista vazia
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            pedidos = (List<Pedido>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar banco de dados: " + e.getMessage());
            // Se der erro (arquivo corrompido), recria a lista para não travar o app
            pedidos = new ArrayList<>();
        }
    }
}