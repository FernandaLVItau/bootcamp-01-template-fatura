package br.com.zup.fatura.entities.transacao;

import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.estabelecimento.Estabelecimento;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idLegado;
    @NotNull
    @Positive
    private BigDecimal valor;
    private LocalDateTime efetivadaEm;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "nome", column = @Column(name = "nome_estabelecimento")),
            @AttributeOverride( name = "cidade", column = @Column(name = "cidade_estabelecimento")),
            @AttributeOverride( name = "endereco", column = @Column(name = "endereco_estabelecimento"))
    })
    private Estabelecimento estabelecimento;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Transacao(){}

    public Transacao(String idLegado, @NotBlank @Positive BigDecimal valor, LocalDateTime efetivadaEm, Estabelecimento estabelecimento, Cartao cartao) {
        this.idLegado = idLegado;
        this.valor = valor;
        this.efetivadaEm = efetivadaEm;
        this.estabelecimento = estabelecimento;
        this.cartao = cartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }
}
