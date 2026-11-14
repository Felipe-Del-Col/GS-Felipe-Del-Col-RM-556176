
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.dto.TrechoDTO;
import br.fiap.felipedelcolrm556176.mapper.TrechoMapper;
import br.fiap.felipedelcolrm556176.domain.Bairro;
import br.fiap.felipedelcolrm556176.domain.Trecho;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;
import br.fiap.felipedelcolrm556176.repository.LeituraRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrechoService {

    private TrechoRepository trechoRepository;
    private LeituraRepository leituraRepository;
    private BairroService bairroService;
    private TrechoMapper trechoMapper;

    public TrechoService(TrechoRepository trechoRepository, LeituraRepository leituraRepository, BairroService bairroService, TrechoMapper trechoMapper) {
        this.trechoRepository = trechoRepository;
        this.leituraRepository = leituraRepository;
        this.bairroService = bairroService;
        this.trechoMapper = trechoMapper;
    }

    public List<TrechoDTO> listar() {

        List<Trecho> trechos = trechoRepository.findAllByOrderByAltitudeAscLogradouroAsc();

        List<TrechoDTO> listaDTO = new ArrayList<>();

        for (Trecho trecho : trechos) {
            listaDTO.add(trechoMapper.toDTO(trecho));
        }

        return listaDTO;
    }

    public TrechoDTO buscarPorId(Long id) {

        Trecho trecho = buscarEntidade(id);

        return trechoMapper.toDTO(trecho);
    }

    public Trecho buscarEntidade(Long id) {

        Trecho trecho = trechoRepository.findById(id).orElse(null);

        if (trecho == null) {
            throw new IllegalArgumentException("Trecho não encontrado.");
        }

        return trecho;
    }

    @Transactional
    public TrechoDTO salvar(TrechoDTO dto) {

        if (dto.getLogradouro() == null || dto.getLogradouro().trim().isEmpty()) {

            throw new IllegalArgumentException("O logradouro é obrigatório.");
        }

        if (dto.getLogradouro().length() > 120) {

            throw new IllegalArgumentException("O logradouro deve ter no máximo 120 caracteres.");
        }

        String tipo = dto.getTipoCalcada();

        if (tipo == null ||
                !(tipo.equals("Regular") || tipo.equals("Irregular") || tipo.equals("Escadaria") || tipo.equals("Sem iluminação"))) {

            throw new IllegalArgumentException("Selecione um tipo de calçada válido.");
        }

        if (dto.getAltitude() == null || dto.getAltitude() < 0 || dto.getAltitude() > 2000) {

            throw new IllegalArgumentException("A altitude deve estar entre 0 e 2000 metros.");
        }

        if (dto.getBairroId() == null) {

            throw new IllegalArgumentException("Selecione um bairro.");
        }


        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }

        Bairro bairro =
                bairroService.buscarEntidade(dto.getBairroId());

        Trecho trecho = trechoMapper.toEntity(dto, bairro);

        Trecho salvo = trechoRepository.save(trecho);

        return trechoMapper.toDTO(salvo);
    }


    @Transactional
    public void excluir(Long id) {

        Trecho trecho = buscarEntidade(id);


        if (leituraRepository.existsByTrechoId(id)) {

            throw new IllegalArgumentException("Não é possível excluir este trecho, pois possui leituras cadastradas.");
        }

        trechoRepository.delete(trecho);
    }
}
