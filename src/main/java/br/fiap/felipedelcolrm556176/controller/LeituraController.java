package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.LeituraDTO;
import br.fiap.felipedelcolrm556176.mapper.LeituraMapper;
import br.fiap.felipedelcolrm556176.model.Leitura;
import br.fiap.felipedelcolrm556176.service.LeituraService;
import br.fiap.felipedelcolrm556176.service.TrechoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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

        List<LeituraDTO> leituras = new ArrayList<>();

        for (Leitura leitura : leituraService.listar()) {
            leituras.add(LeituraMapper.paraDTO(leitura));
        }

        model.addAttribute("leituras", leituras);

        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("leitura", new LeituraDTO());
        model.addAttribute("trechos", trechoService.listar());

        return "leituras/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute LeituraDTO dto, RedirectAttributes redirect) {

        try {

            Leitura leitura = LeituraMapper.paraEntidade(dto);

            if (leitura.getId() == null) {
                leituraService.salvar(leitura);
            } else {
                leituraService.atualizar(leitura.getId(), leitura);
            }

            redirect.addFlashAttribute("mensagem", "Leitura salva com sucesso!");

        } catch (IllegalArgumentException e) {

            redirect.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/leituras";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {

        Leitura leitura = leituraService.buscarPorId(id);

        if (leitura == null) {

            redirect.addFlashAttribute("erro",
                    "Leitura nao encontrada!");

            return "redirect:/leituras";
        }

        model.addAttribute("leitura", LeituraMapper.paraDTO(leitura));

        model.addAttribute("trechos", trechoService.listar());

        return "leituras/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {

        try {

            leituraService.excluir(id);

            redirect.addFlashAttribute("mensagem", "Leitura excluida com sucesso!");

        } catch (IllegalArgumentException e) {

            redirect.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/leituras";
    }
}

