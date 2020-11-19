package br.com.zup.fatura.entities.cartao.retornolegado;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

@Embeddable
public class CarteiraCartaoRetornoLegado {

    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    @Deprecated
    public CarteiraCartaoRetornoLegado(){}

    public CarteiraCartaoRetornoLegado(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }
}
