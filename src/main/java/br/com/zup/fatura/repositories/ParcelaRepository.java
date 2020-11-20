package br.com.zup.fatura.repositories;

import br.com.zup.fatura.entities.cartao.Cartao;
import br.com.zup.fatura.entities.parcela.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, String> {
    List<Parcela> findByCartaoAndMesFatura(Cartao cartao, int mesFatura);
}
