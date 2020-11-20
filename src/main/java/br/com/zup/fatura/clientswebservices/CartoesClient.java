package br.com.zup.fatura.clientswebservices;

import br.com.zup.fatura.entities.cartao.retornolegado.DadosCartaoRetornoLegado;
import br.com.zup.fatura.entities.parcela.ParcelaRequest;
import br.com.zup.fatura.entities.parcela.ParcelaRetornoLegado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "busca-cartoes", url = "${url.cartoes}")
public interface CartoesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/cartoes/{id}")
    DadosCartaoRetornoLegado dadosCartaoPeloId(@PathVariable("id") String idCartao);


    @RequestMapping(method = RequestMethod.POST, value = "/cartoes/{id}/parcelas")
    ParcelaRetornoLegado enviarParcela(@PathVariable("id") String idCartao, ParcelaRequest parcelaRequest);
}
