package br.com.zup.fatura.entities.transacao;

import br.com.zup.fatura.entities.estabelecimento.EstabelecimentoRetorno;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoRetorno {

    private BigDecimal valor;
    private LocalDateTime efetivadaEm;
    private EstabelecimentoRetorno estabelecimento;

    public TransacaoRetorno(Transacao transacao) {
        this.valor = transacao.getValor();
        this.efetivadaEm = transacao.getEfetivadaEm();
        this.estabelecimento = new EstabelecimentoRetorno(transacao.getEstabelecimento());
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    public EstabelecimentoRetorno getEstabelecimento() {
        return estabelecimento;
    }
}
