
package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.BairroDTO;
import br.fiap.felipedelcolrm556176.mapper.BairroMapper;
import br.fiap.felipedelcolrm556176.model.Bairro;
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
@RequestMapping("/bairros")
public class BairroController {

    private BairroService bairroService;

    public BairroController(BairroService bairroService) {
        this.bairroService = bairroService;
    }

    @GetMapping
    public String listar(Model model) {

        List<Bairro> bairros = bairroService.listar();

        List<BairroDTO> listaDTO = new ArrayList<>();

        for (Bairro bairro : bairros) {
            listaDTO.add(BairroMapper.paraDTO(bairro));
        }

        model.addAttribute("bairros", listaDTO);

        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("bairro", new BairroDTO());

        return "bairros/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        Bairro bairro = bairroService.buscarPorId(id);

        if (bairro == null) {

            redirectAttributes.addFlashAttribute("erro", "Bairro não encontrado.");

            return "redirect:/bairros";
        }

        model.addAttribute("bairro", BairroMapper.paraDTO(bairro));

        return "bairros/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("bairro") BairroDTO bairroDTO, RedirectAttributes redirectAttributes) {

        try {

            Bairro bairro = BairroMapper.paraEntidade(bairroDTO);

            if (bairroDTO.getId() == null) {

                bairroService.salvar(bairro);

                redirectAttributes.addFlashAttribute("mensagem", "Bairro cadastrado com sucesso.");

            } else {

                bairroService.atualizar(bairroDTO.getId(), bairro);

                redirectAttributes.addFlashAttribute("mensagem", "Bairro atualizado com sucesso.");
            }

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/bairros";
    }

    @PostMapping("/excluir/{id}")
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
