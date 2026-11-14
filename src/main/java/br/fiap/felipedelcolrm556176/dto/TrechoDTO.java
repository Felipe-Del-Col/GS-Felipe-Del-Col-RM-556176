package br.fiap.felipedelcolrm556176.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class TrechoDTO {

    private Long id;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(max = 120, message = "O logradouro deve ter no máximo 120 caracteres.")
    private String logradouro;

    @NotBlank(message = "O tipo de calçada é obrigatório.")
    @Size(max = 20, message = "O tipo de calçada deve ter no máximo 20 caracteres.")
    @Pattern(
            regexp = "Regular|Irregular|Escadaria|Sem iluminação",
            message = "Selecione um tipo de calçada válido."
    )
    private String tipoCalcada;

    @NotNull(message = "A altitude é obrigatória.")
    @Min(value = 0, message = "A altitude mínima é 0 metro.")
    @Max(value = 2000, message = "A altitude máxima é 2000 metros.")
    private Integer altitude;

    @NotNull(message = "O bairro é obrigatório.")
    private Long bairroId;

    private String bairroNome;

    public TrechoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getBairroId() {
        return bairroId;
    }

    public void setBairroId(Long bairroId) {
        this.bairroId = bairroId;
    }

    public String getBairroNome() {
        return bairroNome;
    }

    public void setBairroNome(String bairroNome) {
        this.bairroNome = bairroNome;
    }
}
