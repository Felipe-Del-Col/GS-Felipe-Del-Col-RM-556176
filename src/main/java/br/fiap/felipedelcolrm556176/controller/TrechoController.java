package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.TrechoDTO;
import br.fiap.felipedelcolrm556176.mapper.TrechoMapper;
import br.fiap.felipedelcolrm556176.model.Trecho;
import br.fiap.felipedelcolrm556176.service.TrechoService;
import br.fiap.felipedelcolrm556176.service.BairroService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/trechos")
public class TrechoController {

    private TrechoService trechoService;
    private BairroService bairroService;

    public TrechoController(TrechoService trechoService,
                            BairroService bairroService) {
        this.trechoService = trechoService;
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {

        List<TrechoDTO> trechos = new ArrayList<>();

        for (Trecho trecho : trechoService.listar()) {
            trechos.add(TrechoMapper.paraDTO(trecho));
        }

        model.addAttribute("trechos", trechos);

        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("trecho", new TrechoDTO());
        model.addAttribute("bairros", bairroService.listar());

        return "trechos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TrechoDTO dto,
                         RedirectAttributes redirect) {

        try {
            Trecho trecho = TrechoMapper.paraEntidade(dto);

            if (trecho.getId() == null) {
                trechoService.salvar(trecho);
            } else {
                trechoService.atualizar(trecho.getId(), trecho);
            }

            redirect.addFlashAttribute("mensagem", "Trecho salvo com sucesso!");

        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/trechos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {

        Trecho trecho = trechoService.buscarPorId(id);

        if (trecho == null) {
            redirect.addFlashAttribute("erro", "Trecho nao encontrado!");

            return "redirect:/trechos";
        }

        model.addAttribute("trecho", TrechoMapper.paraDTO(trecho));

        model.addAttribute("bairros", bairroService.listar());

        return "trechos/formulario";
    }


    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {

        try {
            trechoService.excluir(id);

            redirect.addFlashAttribute("mensagem", "Trecho excluido com sucesso!");

        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("erro", "Nao e possivel excluir trecho com leituras!");

        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/trechos";
    }
}


