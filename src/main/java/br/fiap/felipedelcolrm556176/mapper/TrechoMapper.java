
package br.fiap.felipedelcolrm556176.mapper;

import br.fiap.felipedelcolrm556176.dto.TrechoDTO;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.model.Bairro;

public class TrechoMapper {

    public static TrechoDTO paraDTO(Trecho trecho) {

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

        Trecho trecho = new Trecho();

        trecho.setId(dto.getId());
        trecho.setLogradouro(dto.getLogradouro());
        trecho.setTipoCalcada(dto.getTipoCalcada());
        trecho.setAltitude(dto.getAltitude());

        if (dto.getBairroId() != null) {

            Bairro bairro = new Bairro();
            bairro.setId(dto.getBairroId());

            trecho.setBairro(bairro);
        }

        return trecho;
    }
}

