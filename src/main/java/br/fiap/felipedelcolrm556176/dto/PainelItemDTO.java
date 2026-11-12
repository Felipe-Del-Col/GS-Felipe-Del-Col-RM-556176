package br.fiap.felipedelcolrm556176.dto;

import java.time.LocalDateTime;

public class PainelItemDTO {

    private Long trechoId;
    private String logradouro;
    private String tipoCalcada;
    private Integer altitude;

    private Integer ultimaLamina;
    private String ultimoStatus;

    private LocalDateTime dataHora;

    private Integer laminaCm;
    private String status;

    public PainelItemDTO() {
    }

    public Long getTrechoId() {
        return trechoId;
    }

    public void setTrechoId(Long trechoId) {
        this.trechoId = trechoId;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getTipoCalcada() {
        return tipoCalcada;
    }

    public void setTipoCalcada(String tipoCalcada) {
        this.tipoCalcada = tipoCalcada;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getUltimaLamina() {
        return ultimaLamina;
    }

    public void setUltimaLamina(Integer ultimaLamina) {
        this.ultimaLamina = ultimaLamina;
    }

    public String getUltimoStatus() {
        return ultimoStatus;
    }

    public void setUltimoStatus(String ultimoStatus) {
        this.ultimoStatus = ultimoStatus;
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
}

