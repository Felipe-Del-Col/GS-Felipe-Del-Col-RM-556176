package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.LeituraDTO;
import br.fiap.felipedelcolrm556176.service.LeituraService;
import br.fiap.felipedelcolrm556176.service.TrechoService;
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

@Controller
@RequestMapping("/leituras")
public class LeituraController {

    private LeituraService leituraService;
    private TrechoService trechoService;

    public LeituraController(LeituraService leituraService, TrechoService trechoService) {
        this.leituraService = leituraService;
        this.trechoService = trechoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("leituras", leituraService.listar());

        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("leitura", new LeituraDTO());

        model.addAttribute("trechos", trechoService.listar());

        return "leituras/formulario";
    }

    @GetMapping({"/{id}/editar", "/editar/{id}"})
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        try {

            LeituraDTO leitura = leituraService.buscarPorId(id);

            model.addAttribute("leitura", leitura);

            model.addAttribute("trechos", trechoService.listar());

            return "leituras/formulario";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return "redirect:/leituras";
        }
    }

    // Salvar ou atualizar leitura
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("leitura") LeituraDTO dto, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {

            model.addAttribute("trechos", trechoService.listar());

            return "leituras/formulario";
        }

        try {

            boolean novo = dto.getId() == null;

            leituraService.salvar(dto);

            if (novo) {

                redirectAttributes.addFlashAttribute("mensagem", "Leitura cadastrada com sucesso.");

            } else {

                redirectAttributes.addFlashAttribute("mensagem", "Leitura atualizada com sucesso.");
            }

            return "redirect:/leituras";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            model.addAttribute("trechos", trechoService.listar());

            return "leituras/formulario";
        }
    }


    @PostMapping({"/{id}/excluir", "/excluir/{id}"})
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        try {
            leituraService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Leitura excluída com sucesso.");

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/leituras";
    }
}
