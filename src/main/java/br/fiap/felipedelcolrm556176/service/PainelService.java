package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.dto.PainelItemDTO;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.model.Leitura;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;
import br.fiap.felipedelcolrm556176.repository.LeituraRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PainelService {

    private TrechoRepository trechoRepository;
    private LeituraRepository leituraRepository;

    public PainelService(TrechoRepository trechoRepository, LeituraRepository leituraRepository) {
        this.trechoRepository = trechoRepository;
        this.leituraRepository = leituraRepository;
    }

    public List<PainelItemDTO> listarPorBairro(Long bairroId) {
        List<PainelItemDTO> painel = new ArrayList<>();
        List<Trecho> trechos = trechoRepository.findAll(Sort.by("altitude", "logradouro"));
        List<Leitura> leituras = leituraRepository.findAll();

        for (Trecho trecho : trechos) {
            if (trecho.getBairro().getId().equals(bairroId)) {
                Leitura ultimaLeitura = null;

                for (Leitura leitura : leituras) {
                    if (leitura.getTrecho().getId().equals(trecho.getId())) {
                        if (ultimaLeitura == null || leitura.getDataHora().isAfter(ultimaLeitura.getDataHora())) {
                            ultimaLeitura = leitura;
                        }
                    }
                }

                PainelItemDTO item = new PainelItemDTO();
                item.setTrechoId(trecho.getId());
                item.setLogradouro(trecho.getLogradouro());

                if (ultimaLeitura != null) {
                    item.setDataHora(ultimaLeitura.getDataHora());
                    item.setLaminaCm(ultimaLeitura.getLaminaCm());
                    item.setStatus(ultimaLeitura.getStatus());

                } else {
                    item.setStatus("SEM LEITURA");
                }
                painel.add(item);
            }
        }
        return painel;
    }

    public int contarStatus(List<PainelItemDTO> painel, String status) {
        int total = 0;
        for (PainelItemDTO item : painel) {
            if (status.equals(item.getStatus())) {
                total++;
            }
        }
        return total;
    }
}

