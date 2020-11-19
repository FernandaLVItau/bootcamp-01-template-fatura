package br.com.zup.fatura.repositories;

import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {
    List<Transacao> findByCartaoOrderByEfetivadaEmDesc(Cartao cartao);
}
