package br.com.zup.fatura.entities.parcela;

/*
{
  "identificadorDaFatura": "string",
  "quantidade": 0,
  "valor": 0
}
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ParcelaRequest {

    @NotBlank
    private String identificadorDaFatura;
    @NotNull
    @Positive
    private int quantidade;
    @NotNull
    @Positive
    private BigDecimal valor;

    public ParcelaRequest(@NotBlank String identificadorDaFatura, @NotNull @Positive int quantidade, @NotNull @Positive BigDecimal valor) {
        this.identificadorDaFatura = identificadorDaFatura;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getIdentificadorDaFatura() {
        return identificadorDaFatura;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
