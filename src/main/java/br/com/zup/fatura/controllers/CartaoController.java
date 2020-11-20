package br.com.zup.fatura.controllers;

import br.com.zup.fatura.clientswebservices.CartoesClient;
import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.cartao.retornolegado.DadosCartaoRetornoLegado;
import br.com.zup.fatura.entities.fatura.Fatura;
import br.com.zup.fatura.entities.parcela.*;
import br.com.zup.fatura.entities.transacao.Transacao;
import br.com.zup.fatura.repositories.CartaoRepository;
import br.com.zup.fatura.repositories.TransacaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.text.NumberFormat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartoesClient cartoesClient;

    @PersistenceContext
    private EntityManager manager;

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


        //Definir tipo application/json
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.ok().headers(httpHeaders).body(String.format("{\"saldo\": \"%s\", \"mesFatura\": %d}",NumberFormat.getCurrencyInstance().format(saldo), fatura.getMesFatura()));
    }

    @PostMapping("/parcelas/{nCartao}/{mesFatura}/{anoFatura}")
    @Transactional
    public ResponseEntity<String> cadastroParcela(@PathVariable("nCartao") String nCartao, @PathVariable("mesFatura") int mesFatura, @PathVariable("anoFatura") int anoFatura, @RequestBody @Valid ParcelaNovoRequest novaParcela, UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> buscaCartao = cartaoRepository.findByIdLegado(nCartao);

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (mesFatura < 1 || mesFatura > 12) {
            return ResponseEntity.badRequest().body("mesFatura deve ser dos valores entre 1 e 12.");
        }

        Cartao cartao = buscaCartao.get();

        List<Transacao> transacaoList = transacaoRepository.findByCartaoOrderByEfetivadaEmDesc(cartao);

        transacaoList = transacaoList.stream()
                .filter(t ->
                        t.getEfetivadaEm().getMonthValue() == mesFatura
                        && t.getEfetivadaEm().getYear() == anoFatura
                )
                .collect(Collectors.toList());

        if (transacaoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        StatusNegociacao statusNegociacao = StatusNegociacao.NEGADO;

        try {
            logger.info("Envia para aprovação a negociação da parcela. nCartao: {};",cartao.getIdLegado());

            ParcelaRequest parcelaRequest = new ParcelaRequest(mesFatura+"/"+anoFatura, novaParcela.getQuantidade(), novaParcela.getValor());

            ParcelaRetornoLegado parcelaRetornoLegado = cartoesClient.enviarParcela(cartao.getIdLegado(), parcelaRequest);

            statusNegociacao = parcelaRetornoLegado.getResultado();

        } catch (FeignException e) {
            int resultadoEsperadoStatusCode = 422;
            String resultadoEsperadoTexto = "{\"resultado\":\"NEGADO\"}";
            if (e.status() == resultadoEsperadoStatusCode
                    && e.contentUTF8().equals(resultadoEsperadoTexto)) {
                logger.info("Negado a negociação da parcela. numeroCartaoLegado: {}", cartao.getIdLegado());
            }
            else {
                logger.warn("Erro ao solicitar negociação da parcela. nCartao: {}; statusRetorno: {}", cartao.getIdLegado(), e.status());
                return ResponseEntity.status(500).body("Erro interno. Por favor tente novamente mais tarde");
            }
        }

        Parcela parcela = new Parcela(novaParcela.getQuantidade(),novaParcela.getValor(), mesFatura, anoFatura, statusNegociacao, cartao);

        manager.persist(parcela);

        URI link = uriComponentsBuilder.path("/cartoes/parcelas/{idParcela}/").buildAndExpand(parcela.getId()).toUri();
        return ResponseEntity.created(link).build();
    }
}
