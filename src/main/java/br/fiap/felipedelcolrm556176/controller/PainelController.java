
package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.PainelItemDTO;
import br.fiap.felipedelcolrm556176.model.Bairro;
import br.fiap.felipedelcolrm556176.service.BairroService;
import br.fiap.felipedelcolrm556176.service.PainelService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/painel")
public class PainelController {

    private PainelService painelService;
    private BairroService bairroService;

    public PainelController(PainelService painelService, BairroService bairroService) {
        this.painelService = painelService;
        this.bairroService = bairroService;
    }

    @GetMapping
    public String mostrarPainel(@RequestParam(required = false) Long bairroId, Model model) {
        model.addAttribute("bairros", bairroService.listar());
        if (bairroId != null) {
            Bairro bairro = bairroService.buscarPorId(bairroId);
            if (bairro == null) {
                model.addAttribute("erro", "Bairro nao encontrado");
            } else {

                List<PainelItemDTO> itens = painelService.listarPorBairro(bairroId);

                model.addAttribute("bairroSelecionado", bairro);
                model.addAttribute("itens", itens);

                model.addAttribute("normal", painelService.contarStatus(itens, "NORMAL"));

                model.addAttribute("atencao", painelService.contarStatus(itens, "ATENCAO"));

                model.addAttribute("alerta", painelService.contarStatus(itens, "ALERTA"));

                model.addAttribute("semLeitura", painelService.contarStatus(itens, "SEM LEITURA"));
            }
        }
        return "painel";
    }
}
