package br.com.zup.fatura.entities.fatura;

import br.com.zup.fatura.entities.transacao.Transacao;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Fatura {

    private List<TransacaoFatura> transacoes;
    @Min(1)
    @Max(12)
    private int mesFatura;

    @Positive
    private int anoFatura;

    public Fatura(List<Transacao> transacoes, @Min(1) @Max(12) int mesFatura, @Positive int anoFatura) {
        this.mesFatura = mesFatura;
        this.anoFatura = anoFatura;
        setTransacoes(transacoes);
    }

    public Fatura(List<Transacao> transacoes) {
        this.mesFatura = LocalDateTime.now().getMonthValue();
        this.anoFatura = LocalDateTime.now().getYear();
        setTransacoes(transacoes);
    }

    private void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes.stream()
                .filter(t ->
                        t.getEfetivadaEm().getMonthValue() == this.mesFatura
                                && t.getEfetivadaEm().getYear() == this.anoFatura
                )
                .map(TransacaoFatura::new)
                .collect(Collectors.toList());
    }

    public List<TransacaoFatura> getTransacoes() {
        return transacoes;
    }

    public int getMesFatura() {
        return mesFatura;
    }

    public int getAnoFatura() {
        return anoFatura;
    }
}
