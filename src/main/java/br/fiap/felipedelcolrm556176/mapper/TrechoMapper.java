package br.fiap.felipedelcolrm556176.mapper;

import br.fiap.felipedelcolrm556176.dto.TrechoDTO;
import br.fiap.felipedelcolrm556176.domain.Trecho;
import br.fiap.felipedelcolrm556176.domain.Bairro;

import org.springframework.stereotype.Component;

@Component
public class TrechoMapper {


    public Trecho toEntity(TrechoDTO dto, Bairro bairro) {

        Trecho trecho = new Trecho();

        trecho.setId(dto.getId());
        trecho.setLogradouro(dto.getLogradouro());
        trecho.setTipoCalcada(dto.getTipoCalcada());
        trecho.setAltitude(dto.getAltitude());
        trecho.setBairro(bairro);

        return trecho;
    }

    public TrechoDTO toDTO(Trecho trecho) {

        TrechoDTO dto = new TrechoDTO();

        dto.setId(trecho.getId());
        dto.setLogradouro(trecho.getLogradouro());
        dto.setTipoCalcada(trecho.getTipoCalcada());
        dto.setAltitude(trecho.getAltitude());

        if (trecho.getBairro() != null) {

            dto.setBairroId(trecho.getBairro().getId());
            dto.setBairroNome(trecho.getBairro().getNome());
        }

        return dto;
    }

    public static Trecho paraEntidade(TrechoDTO dto) {

        Bairro bairro = null;

        if (dto.getBairroId() != null) {

            bairro = new Bairro();
            bairro.setId(dto.getBairroId());
        }

        return new TrechoMapper().toEntity(dto, bairro);
    }

    public static TrechoDTO paraDTO(Trecho trecho) {
        return new TrechoMapper().toDTO(trecho);
    }
}


