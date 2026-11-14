
package br.fiap.felipedelcolrm556176.repository;

import br.fiap.felipedelcolrm556176.domain.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BairroRepository extends JpaRepository<Bairro, Long> {

    List<Bairro> findAllByOrderByNomeAsc();

}



