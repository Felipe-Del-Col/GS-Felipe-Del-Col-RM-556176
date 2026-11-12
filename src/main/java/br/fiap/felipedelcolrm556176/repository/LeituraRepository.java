package br.fiap.felipedelcolrm556176.repository;

import br.fiap.felipedelcolrm556176.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    List<Leitura> findAllByOrderByDataHoraDesc();

    Optional<Leitura> findFirstByTrechoIdOrderByDataHoraDesc(Long trechoId);

    boolean existsByTrechoId(Long trechoId);

}

