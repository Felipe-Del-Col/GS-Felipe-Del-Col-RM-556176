
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.repository.BairroRepository;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BairroService {

    private BairroRepository bairroRepository;
    private TrechoRepository trechoRepository;

    public BairroService(BairroRepository bairroRepository, TrechoRepository trechoRepository) {
        this.bairroRepository = bairroRepository;
        this.trechoRepository = trechoRepository;
    }

    public List<Bairro> listar() {
        return bairroRepository.findAll(Sort.by("nome"));
    }

    public Bairro buscarPorId(Long id) {
        return bairroRepository.findById(id).orElse(null);
    }

    public Bairro salvar(Bairro bairro) {

        if (bairro.getNome() == null || bairro.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do bairro é obrigatório.");
        }

        if (bairro.getAreaKm2() == null || bairro.getAreaKm2() <= 0) {
            throw new IllegalArgumentException("A área do bairro deve ser maior que zero.");
        }

        String zona = bairro.getZona();

        if (zona == null || !(zona.equals("Centro") || zona.equals("Norte") || zona.equals("Sul") ||
                        zona.equals("Leste") ||
                        zona.equals("Oeste"))) {

            throw new IllegalArgumentException("Selecione uma zona válida.");
        }
        return bairroRepository.save(bairro);
    }

    public Bairro atualizar(Long id, Bairro dados) {

        Bairro bairro = buscarPorId(id);

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro não encontrado.");
        }

        bairro.setNome(dados.getNome());
        bairro.setZona(dados.getZona());
        bairro.setAreaKm2(dados.getAreaKm2());

        return salvar(bairro);
    }

    public void excluir(Long id) {

        Bairro bairro = buscarPorId(id);

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro não encontrado.");
        }

        List<Trecho> trechos = trechoRepository.findAll();
        for (Trecho trecho : trechos) {
            if (trecho.getBairro() != null && trecho.getBairro().getId().equals(id)) {
                throw new IllegalArgumentException("Não é possível excluir este bairro, pois possui trechos cadastrados.");
            }
        }
        bairroRepository.delete(bairro);
    }
}

