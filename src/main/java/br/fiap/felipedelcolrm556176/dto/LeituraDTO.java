
package br.fiap.felipedelcolrm556176.dto;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class LeituraDTO {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHora;

    private Integer laminaCm;
    private String status;
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

