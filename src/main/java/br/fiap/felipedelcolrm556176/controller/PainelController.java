
package br.fiap.felipedelcolrm556176.controller;

import br.fiap.felipedelcolrm556176.dto.BairroDTO;
import br.fiap.felipedelcolrm556176.dto.PainelItemDTO;
import br.fiap.felipedelcolrm556176.service.BairroService;
import br.fiap.felipedelcolrm556176.service.PainelService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PainelController {

    private PainelService painelService;
    private BairroService bairroService;

    public PainelController(PainelService painelService, BairroService bairroService) {
        this.painelService = painelService;
        this.bairroService = bairroService;
    }

    @GetMapping("/painel")
    public String mostrarPainel(@RequestParam(required = false) Long bairroId, Model model) {

        return carregarPainel(bairroId, model);
    }

    @GetMapping("/bairros/{id}/painel")
    public String painelDoBairro(@PathVariable Long id,
                                 Model model) {

        return carregarPainel(id, model);
    }

    private String carregarPainel(Long bairroId, Model model) {

        model.addAttribute("bairros", bairroService.listar());

        if (bairroId != null) {

            try {

                BairroDTO bairro = bairroService.buscarPorId(bairroId);


                List<PainelItemDTO> itens = painelService.montarPainel(bairroId);

                model.addAttribute("bairroSelecionado", bairro);
                model.addAttribute("itens", itens);

                model.addAttribute("normal", painelService.contarStatus(itens, "NORMAL"));

                model.addAttribute("atencao", painelService.contarStatus(itens, "ATENCAO"));

                model.addAttribute("alerta", painelService.contarStatus(itens, "ALERTA"));

                model.addAttribute("semLeitura", painelService.contarStatus(itens, "SEM LEITURA"));

            } catch (IllegalArgumentException e) {

                model.addAttribute("erro", e.getMessage());
            }
        }

         return "bairros/painel";
    }
}
