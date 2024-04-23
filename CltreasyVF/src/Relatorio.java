
import java.util.ArrayList;
import java.util.Date;

public class Relatorio {

    public void apresentarEstoque(double valorTotal, int numeroDeProdutos) {

        System.out.println("Dados do Estoque:");
        System.out.println("Número de Produtos em Estoque: " + numeroDeProdutos);
        System.out.printf("Valor Total de Produtos no Estoque: R$ %.2f%n", valorTotal);
        
    }

    public int contVencidos(ArrayList<Produto> produtos) {
        int produtosVencidos = 0;
        Date dataAtual = new Date(); 
    
        for (Produto produto : produtos) {
            if (produto.getDataVencimento().before(dataAtual)) {
                produtosVencidos++;
            }
        }
    
        return produtosVencidos;
    }

    public double porcentagemVencidos(ArrayList<Produto> produtos) {
        int totalProdutos = produtos.size();
        int produtosVencidos = contVencidos(produtos);

        if (totalProdutos > 0) {
            return ((double) produtosVencidos / totalProdutos) * 100;
        } else {
            return 0.0;
        }
    }

    public double prejuizoVencidos(ArrayList<Produto> produtos) {
        double prejuizo = 0.0;
        Date dataAtual = new Date(); 
    
        for (Produto produto : produtos) {
            if (produto.getDataVencimento().before(dataAtual)) {
                prejuizo += produto.getPrecoTotal();
            }
        }
    
        return prejuizo;
    }

    public void imprimirRelatorio(ArrayList<Produto> produtos) {
        int produtosVencidos = contVencidos(produtos);
        double porcentagemVencidos = porcentagemVencidos(produtos);
        double prejuizo = prejuizoVencidos(produtos);

        System.out.println("Produtos Vencidos: " + produtosVencidos);
        System.out.println("Porcentagem de Produtos Vencidos: " + porcentagemVencidos + "%");
        System.out.printf("Prejuízo causado pelos produtos vencidos: R$ %.2f%n", prejuizo);
    }


    
}
