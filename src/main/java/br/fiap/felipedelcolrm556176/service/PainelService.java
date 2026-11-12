
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.dto.PainelItemDTO;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.model.Leitura;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;
import br.fiap.felipedelcolrm556176.repository.LeituraRepository;

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


    public List<PainelItemDTO> montarPainel(Long bairroId) {

        List<PainelItemDTO> painel = new ArrayList<>();

        List<Trecho> trechos = trechoRepository.findByBairroIdOrderByLogradouroAsc(bairroId);

        for (Trecho trecho : trechos) {

            PainelItemDTO item = new PainelItemDTO();

            item.setTrechoId(trecho.getId());
            item.setLogradouro(trecho.getLogradouro());
            item.setTipoCalcada(trecho.getTipoCalcada());
            item.setAltitude(trecho.getAltitude());

            Leitura ultimaLeitura = leituraRepository.findFirstByTrechoIdOrderByDataHoraDesc(trecho.getId()).orElse(null);

            if (ultimaLeitura != null) {

                item.setDataHora(ultimaLeitura.getDataHora());

                item.setUltimaLamina(ultimaLeitura.getLaminaCm());
                item.setUltimoStatus(ultimaLeitura.getStatus());

                item.setLaminaCm(ultimaLeitura.getLaminaCm());
                item.setStatus(ultimaLeitura.getStatus());

            } else {

                item.setUltimaLamina(null);
                item.setUltimoStatus(null);

                item.setDataHora(null);
                item.setLaminaCm(null);
                item.setStatus("SEM LEITURA");
            }

            painel.add(item);
        }

        return painel;
    }

    public List<PainelItemDTO> listarPorBairro(Long bairroId) {

        return montarPainel(bairroId);
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
