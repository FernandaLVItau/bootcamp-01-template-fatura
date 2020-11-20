package br.com.zup.fatura.entities.parcela;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ParcelaNovoRequest {

    @Positive
    private int quantidade;
    @NotNull
    @Positive
    private BigDecimal valor;

    public ParcelaNovoRequest(@Positive int quantidade, @NotNull @Positive BigDecimal valor) {
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
