
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.model.Leitura;

import br.fiap.felipedelcolrm556176.repository.BairroRepository;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;
import br.fiap.felipedelcolrm556176.repository.LeituraRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrechoService {

    private TrechoRepository trechoRepository;
    private BairroRepository bairroRepository;
    private LeituraRepository leituraRepository;

    public TrechoService(TrechoRepository trechoRepository, BairroRepository bairroRepository, LeituraRepository leituraRepository) {
        this.trechoRepository = trechoRepository;
        this.bairroRepository = bairroRepository;
        this.leituraRepository = leituraRepository;
    }

    public List<Trecho> listar() {

        return trechoRepository.findAll(Sort.by("altitude", "logradouro"));
    }

    public Trecho buscarPorId(Long id) {

        return trechoRepository.findById(id).orElse(null);
    }

    public Trecho salvar(Trecho trecho) {

        if (trecho.getLogradouro() == null || trecho.getLogradouro().trim().isEmpty()) {

            throw new IllegalArgumentException("O logradouro é obrigatório.");
        }

        String tipo = trecho.getTipoCalcada();

        if (tipo == null ||
                !(tipo.equals("Regular") || tipo.equals("Irregular") || tipo.equals("Escadaria") || tipo.equals("Sem iluminação"))) {

            throw new IllegalArgumentException("Selecione um tipo de calçada válido.");
        }

        if (trecho.getAltitude() == null || trecho.getAltitude() < 0 || trecho.getAltitude() > 2000) {

            throw new IllegalArgumentException("A altitude deve estar entre 0 e 2000 metros.");
        }

        if (trecho.getBairro() == null || trecho.getBairro().getId() == null) {

            throw new IllegalArgumentException("Selecione um bairro.");
        }

        Long bairroId = trecho.getBairro().getId();

        Bairro bairro = bairroRepository.findById(bairroId).orElse(null);

        if (bairro == null) {

            throw new IllegalArgumentException("Bairro não encontrado.");
        }

        trecho.setBairro(bairro);

        return trechoRepository.save(trecho);
    }

    public Trecho atualizar(Long id, Trecho dados) {

        Trecho trecho = buscarPorId(id);

        if (trecho == null) {

            throw new IllegalArgumentException("Trecho não encontrado.");
        }

        trecho.setLogradouro(dados.getLogradouro());
        trecho.setTipoCalcada(dados.getTipoCalcada());
        trecho.setAltitude(dados.getAltitude());
        trecho.setBairro(dados.getBairro());

        return salvar(trecho);
    }

    public void excluir(Long id) {

        Trecho trecho = buscarPorId(id);

        if (trecho == null) {

            throw new IllegalArgumentException("Trecho não encontrado.");
        }

        List<Leitura> leituras = leituraRepository.findAll();

        for (Leitura leitura : leituras) {

            if (leitura.getTrecho() != null && leitura.getTrecho().getId().equals(id)) {

                throw new IllegalArgumentException("Não é possível excluir este trecho, pois possui leituras cadastradas.");
            }
        }

        trechoRepository.delete(trecho);
    }
}
