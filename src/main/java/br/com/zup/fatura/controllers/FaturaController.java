package br.com.zup.fatura.controllers;

import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.fatura.Fatura;
import br.com.zup.fatura.entities.transacao.Transacao;
import br.com.zup.fatura.repositories.CartaoRepository;
import br.com.zup.fatura.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @GetMapping("/{nCartao}")
    public ResponseEntity<Fatura> buscaFatura(@PathVariable("nCartao") String nCartao) {

        Optional<Cartao> buscaCartao = cartaoRepository.findByIdLegado(nCartao);

        if (buscaCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = buscaCartao.get();

        List<Transacao> transacaoList = transacaoRepository.findByCartaoOrderByEfetivadaEmDesc(cartao);

        Fatura fatura = new Fatura(transacaoList);

        return ResponseEntity.ok(fatura);
    }

}
