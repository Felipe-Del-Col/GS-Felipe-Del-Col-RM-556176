
package br.fiap.felipedelcolrm556176.mapper;

import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.dto.BairroDTO;

public class BairroMapper {

    public static BairroDTO paraDTO(Bairro bairro) {

        BairroDTO dto = new BairroDTO();

        dto.setId(bairro.getId());
        dto.setNome(bairro.getNome());
        dto.setZona(bairro.getZona());
        dto.setAreaKm2(bairro.getAreaKm2());

        return dto;
    }

    public static Bairro paraEntidade(BairroDTO dto) {

        Bairro bairro = new Bairro();

        bairro.setId(dto.getId());
        bairro.setNome(dto.getNome());
        bairro.setZona(dto.getZona());
        bairro.setAreaKm2(dto.getAreaKm2());

        return bairro;
    }
}

