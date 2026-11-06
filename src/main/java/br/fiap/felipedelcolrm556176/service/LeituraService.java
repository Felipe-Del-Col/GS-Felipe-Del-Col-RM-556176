
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.model.Leitura;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.repository.LeituraRepository;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeituraService {

    private LeituraRepository leituraRepository;
    private TrechoRepository trechoRepository;

    public LeituraService(LeituraRepository leituraRepository,
                          TrechoRepository trechoRepository) {
        this.leituraRepository = leituraRepository;
        this.trechoRepository = trechoRepository;
    }

    public List<Leitura> listar() {
        return leituraRepository.findAll(Sort.by("dataHora"));
    }


    public Leitura buscarPorId(Long id) {
        return leituraRepository.findById(id).orElse(null);
    }


    public Leitura salvar(Leitura leitura) {


        if (leitura.getDataHora() == null ||
                leitura.getDataHora().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data e hora invalidas");
        }


        if (leitura.getLaminaCm() == null ||
                leitura.getLaminaCm() < 0 ||
                leitura.getLaminaCm() > 300) {
            throw new IllegalArgumentException("Nivel da agua invalido");
        }


        if (leitura.getTrecho() == null ||
                leitura.getTrecho().getId() == null) {
            throw new IllegalArgumentException("Trecho obrigatorio");
        }

        Trecho trecho = trechoRepository.findById(
                leitura.getTrecho().getId()).orElse(null);

        if (trecho == null) {
            throw new IllegalArgumentException("Trecho nao encontrado");
        }

        leitura.setTrecho(trecho);


        if (leitura.getLaminaCm() >= 10) {
            leitura.setStatus("ALERTA");
        } else if (leitura.getLaminaCm() >= 5) {
            leitura.setStatus("ATENCAO");
        } else {
            leitura.setStatus("NORMAL");
        }

        return leituraRepository.save(leitura);
    }

    public Leitura atualizar(Long id, Leitura dados) {

        Leitura leitura = buscarPorId(id);

        if (leitura == null) {
            throw new IllegalArgumentException("Leitura nao encontrada");
        }

        leitura.setDataHora(dados.getDataHora());
        leitura.setLaminaCm(dados.getLaminaCm());
        leitura.setTrecho(dados.getTrecho());

        return salvar(leitura);
    }

    public void excluir(Long id) {

        Leitura leitura = buscarPorId(id);

        if (leitura == null) {
            throw new IllegalArgumentException("Leitura nao encontrada");
        }

        leituraRepository.delete(leitura);
    }
}

