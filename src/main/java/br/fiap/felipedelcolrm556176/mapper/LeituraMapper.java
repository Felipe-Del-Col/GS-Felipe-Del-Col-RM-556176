package br.fiap.felipedelcolrm556176.mapper;

import br.fiap.felipedelcolrm556176.dto.LeituraDTO;
import br.fiap.felipedelcolrm556176.model.Leitura;
import br.fiap.felipedelcolrm556176.model.Trecho;

public class LeituraMapper {

    public static LeituraDTO paraDTO(Leitura leitura) {

        LeituraDTO dto = new LeituraDTO();

        dto.setId(leitura.getId());
        dto.setDataHora(leitura.getDataHora());
        dto.setLaminaCm(leitura.getLaminaCm());
        dto.setStatus(leitura.getStatus());

        if (leitura.getTrecho() != null) {
            dto.setTrechoId(leitura.getTrecho().getId());
            dto.setTrechoLogradouro(
                    leitura.getTrecho().getLogradouro());
        }

        return dto;
    }

    public static Leitura paraEntidade(LeituraDTO dto) {

        Leitura leitura = new Leitura();

        leitura.setId(dto.getId());
        leitura.setDataHora(dto.getDataHora());
        leitura.setLaminaCm(dto.getLaminaCm());
        leitura.setStatus(dto.getStatus());

        if (dto.getTrechoId() != null) {

            Trecho trecho = new Trecho();
            trecho.setId(dto.getTrechoId());

            leitura.setTrecho(trecho);
        }

        return leitura;
    }
}

