
package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.BairroDTO;
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
@RequestMapping("/bairros")
public class BairroController {

    private BairroService bairroService;

    public BairroController(BairroService bairroService) {
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("bairros", bairroService.listar());

        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("bairro", new BairroDTO());

        return "bairros/formulario";
    }

    @GetMapping({"/{id}/editar", "/editar/{id}"})
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        try {

            BairroDTO bairro = bairroService.buscarPorId(id);

            model.addAttribute("bairro", bairro);

            return "bairros/formulario";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return "redirect:/bairros";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("bairro") BairroDTO dto, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {

            return "bairros/formulario";
        }

        try {

            boolean novo = dto.getId() == null;

            bairroService.salvar(dto);

            if (novo) {

                redirectAttributes.addFlashAttribute("mensagem", "Bairro cadastrado com sucesso.");

            } else {

                redirectAttributes.addFlashAttribute("mensagem", "Bairro atualizado com sucesso.");
            }

            return "redirect:/bairros";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            return "bairros/formulario";
        }
    }

    @PostMapping({"/{id}/excluir", "/excluir/{id}"})
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        try {

            bairroService.excluir(id);

            redirectAttributes.addFlashAttribute("mensagem", "Bairro excluído com sucesso.");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());

        } catch (DataIntegrityViolationException e) {

            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este bairro, pois possui trechos cadastrados.");
        }

        return "redirect:/bairros";
    }
}
