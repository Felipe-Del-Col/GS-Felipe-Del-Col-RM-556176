package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.dto.LeituraDTO;
import br.fiap.felipedelcolrm556176.mapper.LeituraMapper;
import br.fiap.felipedelcolrm556176.model.Leitura;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.repository.LeituraRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeituraService {

    private LeituraRepository leituraRepository;
    private TrechoService trechoService;
    private LeituraMapper leituraMapper;

    public LeituraService(LeituraRepository leituraRepository, TrechoService trechoService, LeituraMapper leituraMapper) {
        this.leituraRepository = leituraRepository;
        this.trechoService = trechoService;
        this.leituraMapper = leituraMapper;
    }

    public List<LeituraDTO> listar() {

        List<Leitura> leituras = leituraRepository.findAllByOrderByDataHoraDesc();

        List<LeituraDTO> listaDTO = new ArrayList<>();

        for (Leitura leitura : leituras) {

            listaDTO.add(leituraMapper.toDTO(leitura));
        }

        return listaDTO;
    }


    public LeituraDTO buscarPorId(Long id) {

        Leitura leitura = leituraRepository.findById(id).orElse(null);

        if (leitura == null) {

            throw new IllegalArgumentException("Leitura não encontrada.");
        }

        return leituraMapper.toDTO(leitura);
    }

    public String calcularStatus(int laminaCm) {

        if (laminaCm >= 10) {
            return "ALERTA";
        }

        if (laminaCm >= 5) {
            return "ATENCAO";
        }

        return "NORMAL";
    }

    @Transactional
    public LeituraDTO salvar(LeituraDTO dto) {

        if (dto.getDataHora() == null ||
                dto.getDataHora().isAfter(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Data e hora inválidas.");
        }

        if (dto.getLaminaCm() == null ||
                dto.getLaminaCm() < 0 ||
                dto.getLaminaCm() > 300) {

            throw new IllegalArgumentException("A lâmina d'água deve estar entre 0 e 300 cm.");
        }

        if (dto.getTrechoId() == null) {

            throw new IllegalArgumentException("Selecione um trecho.");
        }


        if (dto.getId() != null) {

            buscarPorId(dto.getId());
        }

        Trecho trecho = trechoService.buscarEntidade(dto.getTrechoId());

        String status = calcularStatus(dto.getLaminaCm());

        Leitura leitura = leituraMapper.toEntity(dto, trecho, status);


        Leitura salva = leituraRepository.save(leitura);


        return leituraMapper.toDTO(salva);
    }


    @Transactional
    public void excluir(Long id) {

        Leitura leitura = leituraRepository.findById(id).orElse(null);

        if (leitura == null) {

            throw new IllegalArgumentException("Leitura não encontrada.");
        }

        leituraRepository.delete(leitura);
    }
}


