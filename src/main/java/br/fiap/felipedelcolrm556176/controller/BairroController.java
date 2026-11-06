package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.BairroDTO;
import br.fiap.felipedelcolrm556176.mapper.BairroMapper;
import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.service.BairroService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bairros")
public class BairroController {

    private BairroService bairroService;

    public BairroController(BairroService bairroService) {
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {

        List<BairroDTO> bairros = new ArrayList<>();

        for (Bairro bairro : bairroService.listar()) {
            bairros.add(BairroMapper.paraDTO(bairro));
        }

        model.addAttribute("bairros", bairros);

        return "bairros/lista";
    }


    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("bairro", new BairroDTO());

        return "bairros/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute BairroDTO dto, RedirectAttributes redirect) {

        try {
            Bairro bairro = BairroMapper.paraEntidade(dto);

            if (bairro.getId() == null) {
                bairroService.salvar(bairro);
            } else {
                bairroService.atualizar(bairro.getId(), bairro);
            }

            redirect.addFlashAttribute("mensagem", "Bairro salvo com sucesso!");

        } catch (IllegalArgumentException e) {

            redirect.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/bairros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {

        Bairro bairro = bairroService.buscarPorId(id);

        if (bairro == null) {
            redirect.addFlashAttribute("erro", "Bairro nao encontrado!");

            return "redirect:/bairros";
        }

        model.addAttribute("bairro", BairroMapper.paraDTO(bairro));

        return "bairros/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {

        try {

            bairroService.excluir(id);

            redirect.addFlashAttribute("mensagem", "Bairro excluido com sucesso!");

        } catch (DataIntegrityViolationException e) {

            redirect.addFlashAttribute("erro", "Nao e possivel excluir bairro com trechos!");

        } catch (IllegalArgumentException e) {

            redirect.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/bairros";
    }
}



