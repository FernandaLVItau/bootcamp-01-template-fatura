package br.com.zup.fatura.repositories;

import br.com.zup.fatura.entities.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartaoRepository extends JpaRepository<Cartao, String> {
    Optional<Cartao> findByIdLegado(String idLegado);
}
