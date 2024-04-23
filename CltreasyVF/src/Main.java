import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();
        Relatorio relatorio = new Relatorio();

        try {
            produtos.add(new ProdutoPerecivel("Leite Ninho", "198147", 10, 5.0, sdf.parse("01/01/2023"),
                    sdf.parse("19/02/2023")));
            produtos.add(new ProdutoNaoPerecivel("Sabonete Garden", "19824", 20, 3.0, sdf.parse("01/02/2023"),
                    sdf.parse("18/03/2023")));
            produtos.add(new ProdutoPerecivel("Frango Seara", "19864", 15, 6.0, sdf.parse("01/03/2023"),
                    sdf.parse("01/12/2023")));
            produtos.add(new ProdutoNaoPerecivel("Sabonete Eva", "18775", 30, 2.5, sdf.parse("01/04/2023"),
                    sdf.parse("25/10/2023")));
            produtos.add(new ProdutoPerecivel("Iogurte Nestle", "745698", 8, 4.0, sdf.parse("01/05/2023"),
                    sdf.parse("01/10/2023")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Produto.listaProdutosVencimento(produtos);

        int opcao = 0;

        do {
            System.out.println("Bem-vindo ao CltrEasy! Seu controle fácil de validade de produtos");
            System.out.println();
            System.out.println("================================");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Remover produto");
            System.out.println("3 - Atualizar produto");
            System.out.println("4 - Buscar produto");
            System.out.println("5 - Listar produtos");
            System.out.println("6 - Relatórios");
            System.out.println("7 - Sair");
            System.out.println("================================");
            System.out.println();

            System.out.print("Digite a opção desejada: ");
            System.out.println();
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    Estoque.adicionarProduto(produtos, sc, sdf);
                    break;

                case 2:
                    Estoque.removerProduto(produtos, sc);
                    break;

                case 3:
                    Produto.atualizarProduto(produtos, sc, sdf);
                    break;

                case 4:
                    Estoque.buscarProduto(produtos, sc);
                    break;

                case 5:
                    Estoque.listaProdutos(produtos);
                    break;
                case 6:
                    Estoque estoque = new Estoque();
                    estoque.atualizarEstoque(produtos, relatorio);
                    relatorio.imprimirRelatorio(produtos);
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 7);

        System.out.println("+-----------------------------+");
        System.out.println("Obrigado por usar o Cltreasy!");
        System.out.println("+-----------------------------+");

        sc.close();
    }

}
