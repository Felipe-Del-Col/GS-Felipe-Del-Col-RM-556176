
package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.TrechoDTO;
import br.fiap.felipedelcolrm556176.service.TrechoService;
import br.fiap.felipedelcolrm556176.service.BairroService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
@RequestMapping("/trechos")
public class TrechoController {
    private TrechoService trechoService;
    private BairroService bairroService;

    public TrechoController(TrechoService trechoService, BairroService bairroService) {
        this.trechoService = trechoService;
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("trechos", trechoService.listar());

        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("trecho", new TrechoDTO());

        model.addAttribute("bairros", bairroService.listar());

        return "trechos/formulario";
    }

    @GetMapping({"/{id}/editar", "/editar/{id}"})
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        try {

            TrechoDTO trecho = trechoService.buscarPorId(id);

            model.addAttribute("trecho", trecho);

            model.addAttribute("bairros", bairroService.listar());

            return "trechos/formulario";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return "redirect:/trechos";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("trecho") TrechoDTO dto, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {

            model.addAttribute("bairros", bairroService.listar());

            return "trechos/formulario";
        }

        try {

            boolean novo = dto.getId() == null;

            trechoService.salvar(dto);

            if (novo) {

                redirectAttributes.addFlashAttribute("mensagem", "Trecho cadastrado com sucesso.");

            } else {

                redirectAttributes.addFlashAttribute("mensagem", "Trecho atualizado com sucesso.");
            }

            return "redirect:/trechos";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            model.addAttribute("bairros", bairroService.listar());

            return "trechos/formulario";
        }
    }

    @PostMapping({"/{id}/excluir", "/excluir/{id}"})
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        try {

            trechoService.excluir(id);

            redirectAttributes.addFlashAttribute("mensagem", "Trecho excluído com sucesso.");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());

        } catch (DataIntegrityViolationException e) {

            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este trecho, pois possui leituras cadastradas.");
        }

        return "redirect:/trechos";
    }
}
