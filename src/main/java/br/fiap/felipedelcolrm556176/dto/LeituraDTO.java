package br.fiap.felipedelcolrm556176.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

public class LeituraDTO {

    private Long id;

    @NotNull(message = "A data e hora são obrigatórias.")
    @PastOrPresent(message = "A data e hora não podem estar no futuro.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHora;

    @NotNull(message = "A lâmina de água é obrigatória.")
    @Min(value = 0, message = "A lâmina mínima é 0 cm.")
    @Max(value = 300, message = "A lâmina máxima é 300 cm.")
    private Integer laminaCm;

    private String status;

    @NotNull(message = "O trecho é obrigatório.")
    private Long trechoId;

    private String trechoLogradouro;

    public LeituraDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getLaminaCm() {
        return laminaCm;
    }

    public void setLaminaCm(Integer laminaCm) {
        this.laminaCm = laminaCm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTrechoId() {
        return trechoId;
    }

    public void setTrechoId(Long trechoId) {
        this.trechoId = trechoId;
    }

    public String getTrechoLogradouro() {
        return trechoLogradouro;
    }

    public void setTrechoLogradouro(String trechoLogradouro) {
        this.trechoLogradouro = trechoLogradouro;
    }
}
