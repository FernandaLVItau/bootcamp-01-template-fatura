package br.com.zup.fatura.entities.fatura;

import br.com.zup.fatura.entities.transacao.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoFatura {

    private BigDecimal valor;
    private LocalDateTime efetivadaEm;
    private String nomeEstabelecimento;
    private String cidadeEstabelecimento;

    public TransacaoFatura(Transacao transacao) {
        this.valor = transacao.getValor();
        this.efetivadaEm = transacao.getEfetivadaEm();
        this.nomeEstabelecimento = transacao.getEstabelecimento().getNome();
        this.cidadeEstabelecimento = transacao.getEstabelecimento().getCidade();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    public String getCidadeEstabelecimento() {
        return cidadeEstabelecimento;
    }
}
