
package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.TrechoDTO;
import br.fiap.felipedelcolrm556176.mapper.TrechoMapper;
import br.fiap.felipedelcolrm556176.model.Trecho;

import br.fiap.felipedelcolrm556176.service.TrechoService;
import br.fiap.felipedelcolrm556176.service.BairroService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

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

        List<Trecho> trechos = trechoService.listar();

        List<TrechoDTO> listaDTO = new ArrayList<>();

        for (Trecho trecho : trechos) {

            listaDTO.add(TrechoMapper.paraDTO(trecho));
        }

        model.addAttribute("trechos", listaDTO);

        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("trecho", new TrechoDTO());

        model.addAttribute("bairros", bairroService.listar());

        return "trechos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        Trecho trecho = trechoService.buscarPorId(id);

        if (trecho == null) {

            redirectAttributes.addFlashAttribute("erro", "Trecho não encontrado.");

            return "redirect:/trechos";
        }

        model.addAttribute("trecho", TrechoMapper.paraDTO(trecho));

        model.addAttribute("bairros", bairroService.listar());

        return "trechos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("trecho") TrechoDTO trechoDTO, RedirectAttributes redirectAttributes) {
        try {

            Trecho trecho = TrechoMapper.paraEntidade(trechoDTO);

            if (trechoDTO.getId() == null) {

                trechoService.salvar(trecho);

                redirectAttributes.addFlashAttribute("mensagem", "Trecho cadastrado com sucesso.");

            } else {

                trechoService.atualizar(trechoDTO.getId(), trecho);

                redirectAttributes.addFlashAttribute("mensagem", "Trecho atualizado com sucesso.");
            }

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/trechos";
    }

    @PostMapping("/excluir/{id}")
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
