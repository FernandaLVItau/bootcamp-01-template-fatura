package br.com.zup.fatura.entities.cartao.retornolegado;

import java.math.BigDecimal;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class ParcelaCartaoRetornoLegado {
    private String id;
    private int quantidade;
    private BigDecimal valor;

    @Deprecated
    public ParcelaCartaoRetornoLegado(){}

    public ParcelaCartaoRetornoLegado(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
