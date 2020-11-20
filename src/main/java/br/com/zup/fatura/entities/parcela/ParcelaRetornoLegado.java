package br.com.zup.fatura.entities.parcela;

public class ParcelaRetornoLegado {

    private StatusNegociacao resultado;

    @Deprecated
    public ParcelaRetornoLegado() {}

    public ParcelaRetornoLegado(StatusNegociacao resultado) {
        this.resultado = resultado;
    }

    public StatusNegociacao getResultado() {
        return resultado;
    }
}
