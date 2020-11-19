package br.com.zup.fatura.controllers;

import br.com.zup.fatura.clientswebservices.CartoesClient;
import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.cartao.retornolegado.DadosCartaoRetornoLegado;
import br.com.zup.fatura.entities.fatura.Fatura;
import br.com.zup.fatura.entities.transacao.Transacao;
import br.com.zup.fatura.repositories.CartaoRepository;
import br.com.zup.fatura.repositories.TransacaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartoesClient cartoesClient;

    @Autowired
    private TransacaoRepository transacaoRepository;

    private final Logger logger = LoggerFactory.getLogger(CartaoController.class);

    @GetMapping("/saldo/{nCartao}")
    public ResponseEntity<String> buscaLimite(@PathVariable("nCartao") String nCartao) {

        Optional<Cartao> buscaCartao = cartaoRepository.findByIdLegado(nCartao);

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = buscaCartao.get();
        double saldo = 0.0;

        try {
            logger.info("Busca dos dados do cartão. nCartao: {};",cartao.getIdLegado());

            DadosCartaoRetornoLegado dadosCartao = cartoesClient.dadosCartaoPeloId(cartao.getIdLegado());

            logger.info("Cartao encontrado. nCartao: {};",cartao.getIdLegado());

            saldo += dadosCartao.getLimite();

        } catch (FeignException e) {
            logger.warn("Não localizado os dados do cartão. nCartao: {}; statusRetorno: {}",cartao.getIdLegado(), e.status());
            return ResponseEntity.status(500).body("Erro interno. Por favor tente novamente mais tarde");
        }

        List<Transacao> transacaoList = transacaoRepository.findByCartaoOrderByEfetivadaEmDesc(cartao);
        Fatura fatura = new Fatura(transacaoList);
        saldo -= fatura.getTransacoes().stream().mapToDouble(f -> f.getValor().doubleValue()).sum();


        return ResponseEntity.ok(String.format("saldo: %.2f",saldo));
    }

}
