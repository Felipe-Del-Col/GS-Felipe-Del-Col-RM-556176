
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.repository.BairroRepository;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrechoService {

    private TrechoRepository trechoRepository;
    private BairroRepository bairroRepository;

    public TrechoService(TrechoRepository trechoRepository, BairroRepository bairroRepository) {
        this.trechoRepository = trechoRepository;
        this.bairroRepository = bairroRepository;
    }

    public List<Trecho> listar() {
        return trechoRepository.findAll(
                Sort.by("altitude", "logradouro"));
    }

    public Trecho buscarPorId(Long id) {
        return trechoRepository.findById(id).orElse(null);
    }

    public Trecho salvar(Trecho trecho) {

        if (trecho.getLogradouro() == null || trecho.getLogradouro().isBlank()) {
            throw new IllegalArgumentException("Logradouro obrigatorio");
        }

        if (trecho.getAltitude() == null || trecho.getAltitude() < 0 || trecho.getAltitude() > 2000) {
            throw new IllegalArgumentException("Altitude invalida");
        }

        String tipo = trecho.getTipoCalcada();

        if (tipo == null ||
                !(tipo.equals("Regular") || tipo.equals("Irregular") || tipo.equals("Escadaria") || tipo.equals("Sem iluminação"))) {
            throw new IllegalArgumentException("Tipo de calcada invalido");
        }

        if (trecho.getBairro() == null || trecho.getBairro().getId() == null) {
            throw new IllegalArgumentException("Bairro obrigatorio");
        }

        Bairro bairro = bairroRepository.findById(trecho.getBairro().getId()).orElse(null);

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro nao encontrado");
        }

        trecho.setBairro(bairro);

        return trechoRepository.save(trecho);
    }

    public Trecho atualizar(Long id, Trecho dados) {

        Trecho trecho = buscarPorId(id);

        if (trecho == null) {
            throw new IllegalArgumentException("Trecho nao encontrado");
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
            throw new IllegalArgumentException("Trecho nao encontrado");
        }

        trechoRepository.delete(trecho);
    }
}

