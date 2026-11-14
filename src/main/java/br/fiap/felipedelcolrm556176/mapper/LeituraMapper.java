package br.fiap.felipedelcolrm556176.mapper;

import br.fiap.felipedelcolrm556176.dto.LeituraDTO;
import br.fiap.felipedelcolrm556176.domain.Leitura;
import br.fiap.felipedelcolrm556176.domain.Trecho;

import org.springframework.stereotype.Component;

@Component
public class LeituraMapper {

    public Leitura toEntity(LeituraDTO dto, Trecho trecho, String status) {

        Leitura leitura = new Leitura();

        leitura.setId(dto.getId());
        leitura.setDataHora(dto.getDataHora());
        leitura.setLaminaCm(dto.getLaminaCm());
        leitura.setStatus(status);
        leitura.setTrecho(trecho);

        return leitura;
    }

    public LeituraDTO toDTO(Leitura leitura) {

        LeituraDTO dto = new LeituraDTO();

        dto.setId(leitura.getId());
        dto.setDataHora(leitura.getDataHora());
        dto.setLaminaCm(leitura.getLaminaCm());
        dto.setStatus(leitura.getStatus());

        if (leitura.getTrecho() != null) {

            dto.setTrechoId(leitura.getTrecho().getId());
            dto.setTrechoLogradouro(leitura.getTrecho().getLogradouro());
        }

        return dto;
    }

    public static Leitura paraEntidade(LeituraDTO dto) {

        Trecho trecho = null;

        if (dto.getTrechoId() != null) {
            trecho = new Trecho();
            trecho.setId(dto.getTrechoId());
        }

        return new LeituraMapper().toEntity(dto, trecho, dto.getStatus());
    }

    public static LeituraDTO paraDTO(Leitura leitura) {
        return new LeituraMapper().toDTO(leitura);
    }
}


