package br.com.zup.fatura.clientswebservices;

import br.com.zup.fatura.entities.cartao.retornolegado.DadosCartaoRetornoLegado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "busca-cartoes", url = "${url.cartoes}")
public interface CartoesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/cartoes/{id}")
    DadosCartaoRetornoLegado dadosCartaoPeloId(@PathVariable("id") String idCartao);
}
