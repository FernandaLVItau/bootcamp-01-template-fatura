package br.com.zup.fatura.entities.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Cartao {

    @NotBlank
    @Id
    private String idLegado;
    @NotBlank
    private String email;

    @Deprecated
    public Cartao(){}

    public Cartao(@NotBlank String idLegado, @NotBlank String email) {
        this.idLegado = idLegado;
        this.email = email;
    }

    public String getIdLegado() {
        return idLegado;
    }
}
