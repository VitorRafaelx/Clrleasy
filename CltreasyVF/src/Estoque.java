import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Estoque {

    public void atualizarEstoque(ArrayList<Produto> produtos, Relatorio relatorio) {
        int numeroDeProdutos = produtos.size();
        double valorTotal = 0.0;
    
        for (Produto produto : produtos) {
            valorTotal += produto.getPrecoTotal();
        }
    
        relatorio.apresentarEstoque(valorTotal, numeroDeProdutos);
    }

    public static void adicionarProduto(ArrayList<Produto> produtos, Scanner sc, SimpleDateFormat sdf) {
        try {
            System.out.println("Nome do Produto: ");
            String nome = sc.next();
            sc.nextLine();
            System.out.println("Lote: ");
            String lote = sc.nextLine();
            System.out.println("Quantidade: ");
            int quantidade = sc.nextInt();
            System.out.println("Preço Unitário: ");
            double precoUnitario = sc.nextDouble();
            System.out.println("Data de Fabricação (dd/MM/yyyy): ");
            Date dataFabricacao = sdf.parse(sc.next());
            System.out.println("Data de Vencimento (dd/MM/yyyy): ");
            Date dataVencimento = sdf.parse(sc.next());
            System.out.println("Produto Perecível (1) ou Não Perecível (2)?");
            int tipo = sc.nextInt();
            if (tipo == 1) {
                produtos.add(
                        new ProdutoPerecivel(nome, lote, quantidade, precoUnitario, dataFabricacao, dataVencimento));
            } else if (tipo == 2) {
                produtos.add(
                        new ProdutoNaoPerecivel(nome, lote, quantidade, precoUnitario, dataFabricacao, dataVencimento));
            } else {
                System.out.println("Tipo de produto inválido.");
            }
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
        }
    }

    public static void removerProduto(ArrayList<Produto> produtos, Scanner sc) {
        if (produtos.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            System.out.println(i + " - " + produto.getNome());
        }
        System.out.println("Digite o índice do produto que deseja remover: ");
        int indice = sc.nextInt();
        if (indice >= 0 && indice < produtos.size()) {
            produtos.remove(indice);
            System.out.println("Produto removido com sucesso!!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public static void buscarProduto(ArrayList<Produto> produtos, Scanner sc) {
        if (produtos.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }

        System.out.println("Escolha um filtro de busca:");
        System.out.println("1 - Nome do Produto");
        System.out.println("2 - Data de Validade");
        System.out.println("3 - Preço");

        int criterioBusca = sc.nextInt();
        sc.nextLine();
        boolean encontrado = false;

        switch (criterioBusca) {
            case 1:
                System.out.print("Digite o nome do produto que procura: ");
                String nomeBusca = sc.nextLine().toLowerCase();

                for (int i = 0; i < produtos.size(); i++) {
                    Produto produto = produtos.get(i);
                    String nomeProduto = produto.getNome().toLowerCase();

                    if (nomeProduto.contains(nomeBusca)) {
                        encontrado = true;
                        produto.imprimirProduto(i);
                        System.out.println();
                    }
                }
                break;
            case 2:
                System.out.print("Digite a data de validade (dd/MM/yyyy) que procura: ");
                String dataValidadeBuscaStr = sc.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date dataValidadeBusca = sdf.parse(dataValidadeBuscaStr);

                    for (int i = 0; i < produtos.size(); i++) {
                        Produto produto = produtos.get(i);

                        if (produto.getDataVencimento().equals(dataValidadeBusca)) {
                            encontrado = true;
                            produto.imprimirProduto(i);
                            System.out.println();
                        }
                    }
                } catch (ParseException e) {
                    System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
                }
                break;
            case 3:
                System.out.print("Digite o preço que procura: ");
                double precoBusca = sc.nextDouble();

                for (int i = 0; i < produtos.size(); i++) {
                    Produto produto = produtos.get(i);

                    if (produto.getPrecoUnitario() == precoBusca) {
                        encontrado = true;
                        produto.imprimirProduto(i);
                        System.out.println();
                    }
                }
                break;
            default:
                System.out.println("Opção de filtro inválida.");
        }

        if (!encontrado) {
            System.out.println("Nenhum produto encontrado no estoque que corresponda à busca.");
        }
    }

    public static void listaProdutos(ArrayList<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("Estoque Vazio.");
            return;
        }

        Collections.sort(produtos, new Comparator<Produto>() {
            @Override
            public int compare(Produto p1, Produto p2) {
                return p1.getDiasParaVencer() - p2.getDiasParaVencer();
            }
        });

        System.out.println("LISTA DE PRODUTOS");

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            produto.imprimirProduto(i);
            System.out.println();
        }
    }
}
