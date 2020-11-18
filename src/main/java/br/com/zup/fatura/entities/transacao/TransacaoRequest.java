package br.com.zup.fatura.entities.transacao;

import br.com.zup.fatura.entities.cartao.CartaoRequest;
import br.com.zup.fatura.entities.estabelecimento.EstabelecimentoRequest;

import java.math.BigDecimal;

public class TransacaoRequest {
    private String id;
    private BigDecimal valor;
    private String efetivadaEm;
    private EstabelecimentoRequest estabelecimento;
    private CartaoRequest cartao;


    public String getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getEfetivadaEm() {
        return efetivadaEm;
    }

    public EstabelecimentoRequest getEstabelecimento() {
        return estabelecimento;
    }

    public CartaoRequest getCartao() {
        return cartao;
    }

    @Override
    public String toString() {
        return "EventoDeTransacao{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                ", efetivadaEm=" + efetivadaEm +
                ", estabelecimento=" + estabelecimento +
                ", cartao=" + cartao +
                '}';
    }
}
