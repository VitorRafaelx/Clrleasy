import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class Produto implements Desconto {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String nome;
    private String lote;
    private int quantidade;
    private double precoUnitario;
    private double precoTotal;
    private Date dataFabricacao;
    private Date dataVencimento;
    private int diasParaVencer;
    private double valorFinal;

    public Produto(String nome, String lote, int quantidade, double precoUnitario, Date dataFabricacao,
            Date dataVencimento) {
        this.nome = nome;
        this.lote = lote;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = quantidade * precoUnitario;
        this.dataFabricacao = dataFabricacao;
        this.dataVencimento = dataVencimento;
        this.diasParaVencer = calcularVencimento();
        this.valorFinal = calcularDesconto();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.precoTotal = quantidade * precoUnitario;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
        this.precoTotal = quantidade * precoUnitario;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
        this.diasParaVencer = calcularVencimento();
        this.valorFinal = calcularDesconto();
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public abstract int calcularVencimento();

    public static void listaProdutosVencimento(ArrayList<Produto> produtos) {
        System.out.println("PRODUTOS PRÓXIMO AO VENCIMENTO:");
        System.out.println("");

        for (Produto produto : produtos) {

            int diasParaVencer = produto.calcularVencimento();

            if (diasParaVencer < 15 && diasParaVencer > 0) {
                System.out.println("+-----------------------------+");
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Quantidade: " + produto.getQuantidade());
                System.out.println("Dias para vencer: " + diasParaVencer);
                System.out.println("+-----------------------------+");
                System.out.println();
            }
        }
    }

    public double calcularDesconto() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date hoje = new Date();
        long diferenca = getDataVencimento().getTime() - hoje.getTime();
        long dias = diferenca / (1000 * 60 * 60 * 24);

        double desconto = 0.0;

        if (dias > 30) {

            desconto = 0.0;
        } else if (dias <= 30 && dias >= 15) {

            desconto = getPrecoUnitario() * 0.3;
        } else if (dias < 15 && dias > 0) {
            desconto = getPrecoUnitario() * 0.65;
        } else {
            desconto = 0.0;
        }

        return getPrecoUnitario() - desconto;
    }

    public void imprimirProduto(int indice) {
        DecimalFormat df = new DecimalFormat("R$ #,##0.00");

        System.out.println("+-----------------------------+");
        System.out.println("Índice: " + indice);
        System.out.println("Nome do Produto: " + nome);
        System.out.println("Lote: " + lote);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Preço Unitário: " + df.format(getPrecoUnitario()));
        System.out.println("Preço Total: " + df.format(getPrecoTotal()));
        System.out.println("Data de Fabricação: " + sdf.format(dataFabricacao));
        System.out.println("Vencimento: " + sdf.format(dataVencimento));
        System.out.println("Dias para Vencer: " + diasParaVencer);
        System.out.println("Valor Final: " + valorFinal);
        System.out.println("+-----------------------------+");
        System.out.println();
    }

    public static void atualizarProduto(ArrayList<Produto> produtos, Scanner sc, SimpleDateFormat sdf) {
        if (produtos.isEmpty()) {
            System.out.println("Lista de produtos vazia.");
            return;
        }

        System.out.println("Escolha o produto que deseja atualizar:");
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            System.out.println(i + " - " + produto.getNome());
        }
        System.out.print("Digite o índice do produto que deseja atualizar: ");
        int indiceProduto = sc.nextInt();

        if (indiceProduto < 0 || indiceProduto >= produtos.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Produto produtoParaAtualizar = produtos.get(indiceProduto);

        System.out.println("Selecione a informação a ser atualizada:");
        System.out.println("1 - Nome do Produto");
        System.out.println("2 - Lote");
        System.out.println("3 - Quantidade");
        System.out.println("4 - Preço Unitário");
        System.out.println("5 - Data de Fabricação");
        System.out.println("6 - Data de Vencimento");

        int opcaoAtualizacao = sc.nextInt();

        switch (opcaoAtualizacao) {
            case 1:
                System.out.print("Digite o novo nome do produto: ");
                sc.nextLine();
                String novoNome = sc.nextLine();
                produtoParaAtualizar.setNome(novoNome);
                break;
            case 2:
                System.out.print("Digite o novo lote: ");
                sc.nextLine();
                String novoLote = sc.nextLine();
                produtoParaAtualizar.setLote(novoLote);
                break;
            case 3:
                System.out.print("Digite a nova quantidade: ");
                int novaQuantidade = sc.nextInt();
                if (novaQuantidade == 0) {
                    produtos.remove(produtoParaAtualizar);
                    System.out.println("Produto removido com sucesso.");
                } else {
                    produtoParaAtualizar.setQuantidade(novaQuantidade);
                    System.out.println("Quantidade do produto atualizada com sucesso.");
                }
                break;
            case 4:
                System.out.print("Digite o novo preço unitário: ");
                double novoPrecoUnitario = sc.nextDouble();
                produtoParaAtualizar.setPrecoUnitario(novoPrecoUnitario);
                break;
            case 5:
                System.out.print("Digite a nova data de fabricação (dd/MM/yyyy): ");
                String novaDataFabricacaoStr = sc.next();
                try {
                    Date novaDataFabricacao = sdf.parse(novaDataFabricacaoStr);
                    produtoParaAtualizar.setDataFabricacao(novaDataFabricacao);
                } catch (ParseException e) {
                    System.out.println("Data de fabricação inválida. Data não alterada.");
                }
                break;
            case 6:
                System.out.print("Digite a nova data de vencimento (dd/MM/yyyy): ");
                String novaDataVencimentoStr = sc.next();
                try {
                    Date novaDataVencimento = sdf.parse(novaDataVencimentoStr);
                    produtoParaAtualizar.setDataVencimento(novaDataVencimento);
                } catch (ParseException e) {
                    System.out.println("Data de vencimento inválida. Data não alterada.");
                }
                break;
            default:
                System.out.println("Opção inválida. Nenhuma informação foi atualizada.");
        }
    }

}

class ProdutoPerecivel extends Produto {
    public ProdutoPerecivel(String nome, String lote, int quantidade, double precoUnitario, Date dataFabricacao,
            Date dataVencimento) {
        super(nome, lote, quantidade, precoUnitario, dataFabricacao, dataVencimento);
    }

    @Override
    public int calcularVencimento() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date hoje = new Date();
        long diferenca = getDataVencimento().getTime() - hoje.getTime();
        long dias = diferenca / (1000 * 60 * 60 * 24);
        return (int) dias;
    }
}

class ProdutoNaoPerecivel extends Produto {
    public ProdutoNaoPerecivel(String nome, String lote, int quantidade, double precoUnitario, Date dataFabricacao,
            Date dataVencimento) {
        super(nome, lote, quantidade, precoUnitario, dataFabricacao, dataVencimento);
    }

    @Override
    public int calcularVencimento() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date hoje = new Date();
        long diferenca = getDataVencimento().getTime() - hoje.getTime();
        long dias = diferenca / (1000 * 60 * 60 * 24);
        return (int) dias;
    }
}