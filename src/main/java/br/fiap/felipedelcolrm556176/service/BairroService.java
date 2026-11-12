
package br.fiap.felipedelcolrm556176.service;

import br.fiap.felipedelcolrm556176.dto.BairroDTO;
import br.fiap.felipedelcolrm556176.mapper.BairroMapper;
import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.repository.BairroRepository;
import br.fiap.felipedelcolrm556176.repository.TrechoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BairroService {

    private BairroRepository bairroRepository;
    private TrechoRepository trechoRepository;
    private BairroMapper bairroMapper;

    public BairroService(BairroRepository bairroRepository, TrechoRepository trechoRepository, BairroMapper bairroMapper) {
        this.bairroRepository = bairroRepository;
        this.trechoRepository = trechoRepository;
        this.bairroMapper = bairroMapper;
    }

    public List<BairroDTO> listar() {

        List<Bairro> bairros = bairroRepository.findAllByOrderByNomeAsc();

        List<BairroDTO> listaDTO = new ArrayList<>();

        for (Bairro bairro : bairros) {
            listaDTO.add(bairroMapper.toDTO(bairro));
        }

        return listaDTO;
    }


    public BairroDTO buscarPorId(Long id) {

        Bairro bairro = buscarEntidade(id);

        return bairroMapper.toDTO(bairro);
    }

    public Bairro buscarEntidade(Long id) {

        Bairro bairro = bairroRepository.findById(id).orElse(null);

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro não encontrado.");
        }

        return bairro;
    }

    @Transactional
    public BairroDTO salvar(BairroDTO dto) {

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {

            throw new IllegalArgumentException("O nome do bairro é obrigatório.");
        }

        if (dto.getNome().length() > 80) {

            throw new IllegalArgumentException("O nome deve ter no máximo 80 caracteres.");
        }

        String zona = dto.getZona();

        if (zona == null || !(zona.equals("Centro") || zona.equals("Norte") || zona.equals("Sul") || zona.equals("Leste") || zona.equals("Oeste"))) {

            throw new IllegalArgumentException("Selecione uma zona válida.");
        }

        if (dto.getAreaKm2() == null || dto.getAreaKm2() <= 0) {

            throw new IllegalArgumentException("A área do bairro deve ser maior que zero.");
        }

        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }

        Bairro bairro = bairroMapper.toEntity(dto);

        Bairro salvo = bairroRepository.save(bairro);

        return bairroMapper.toDTO(salvo);
    }

    @Transactional
    public void excluir(Long id) {

        Bairro bairro = buscarEntidade(id);

        if (trechoRepository.existsByBairroId(id)) {

            throw new IllegalArgumentException("Não é possível excluir este bairro, pois possui trechos cadastrados.");
        }

        bairroRepository.delete(bairro);
    }
}
