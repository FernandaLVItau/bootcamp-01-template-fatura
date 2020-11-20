package br.com.zup.fatura.entities.parcela;

import br.com.zup.fatura.entities.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Parcela {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Positive
    private int quantidade;
    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Min(1)
    @Max(12)
    private int mesFatura;

    private LocalDateTime cadastradoEm = LocalDateTime.now();

    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Parcela(){}

    public Parcela(@Positive int quantidade, @NotNull @Positive BigDecimal valor, @NotNull @Min(1) @Max(12) int mesFatura, @NotNull @Valid Cartao cartao) {
        this.quantidade = quantidade;
        this.valor = valor;
        this.mesFatura = mesFatura;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }
}