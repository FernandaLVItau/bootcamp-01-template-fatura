package br.com.zup.fatura.entities.cartao.retornolegado;

import java.time.LocalDate;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class AvisoCartaoRetornoLegado {

    private LocalDate validoAte;
    private String destino;

    @Deprecated
    public AvisoCartaoRetornoLegado(){}

    public AvisoCartaoRetornoLegado(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
