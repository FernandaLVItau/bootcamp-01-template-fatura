package br.com.zup.fatura.util;

import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.estabelecimento.Estabelecimento;
import br.com.zup.fatura.entities.transacao.Transacao;
import br.com.zup.fatura.entities.transacao.TransacaoRequest;
import br.com.zup.fatura.repositories.CartaoRepository;
import br.com.zup.fatura.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class ListenerDeTransacao {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvir(TransacaoRequest transacaoRequest) {
        System.out.println(transacaoRequest);

        String idCartaoLegado = transacaoRequest.getCartao().getId();
        String email = transacaoRequest.getCartao().getEmail();

        Optional<Cartao> verificaCartao = cartaoRepository.findByIdLegado(idCartaoLegado);
        Cartao cartao = verificaCartao.orElse(new Cartao(idCartaoLegado, email));

        Estabelecimento estabelecimento = transacaoRequest.getEstabelecimento().toModel();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(transacaoRequest.getEfetivadaEm(), formatter);

        Transacao transacao = new Transacao(transacaoRequest.getId(),transacaoRequest.getValor(),dateTime,
                estabelecimento, cartao);

        transacaoRepository.save(transacao);
    }
}
