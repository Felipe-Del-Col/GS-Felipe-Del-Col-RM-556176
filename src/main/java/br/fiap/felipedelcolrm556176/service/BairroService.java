
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.repository.BairroRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BairroService {

    private BairroRepository bairroRepository;

    public BairroService(BairroRepository bairroRepository) {
        this.bairroRepository = bairroRepository;
    }

    public List<Bairro> listar() {
        return bairroRepository.findAll(Sort.by("nome"));
    }

    public Bairro buscarPorId(Long id) {
        return (Bairro) bairroRepository.findById(id).orElse(null);
    }

    public Bairro salvar(Bairro bairro) {

        if (bairro.getNome() == null || bairro.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatorio");
        }

        if (bairro.getAreaKm2() == null || bairro.getAreaKm2() <= 0) {
            throw new IllegalArgumentException("Area deve ser maior que zero");
        }

        if (bairro.getZona() == null ||
                !(bairro.getZona().equals("Centro") ||
                        bairro.getZona().equals("Norte") ||
                        bairro.getZona().equals("Sul") ||
                        bairro.getZona().equals("Leste") ||
                        bairro.getZona().equals("Oeste"))) {
            throw new IllegalArgumentException("Zona invalida");
        }

        return (Bairro) bairroRepository.save(bairro);
    }

    public Bairro atualizar(Long id, Bairro dados) {

        Bairro bairro = buscarPorId(id);

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro nao encontrado");
        }

        bairro.setNome(dados.getNome());
        bairro.setZona(dados.getZona());
        bairro.setAreaKm2(dados.getAreaKm2());

        return salvar(bairro);
    }

    public void excluir(Long id) {

        Bairro bairro = buscarPorId(id);

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro nao encontrado");
        }

        bairroRepository.delete(bairro);
    }
}


