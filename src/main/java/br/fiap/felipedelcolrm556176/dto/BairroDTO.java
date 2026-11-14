
package br.fiap.felipedelcolrm556176.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class BairroDTO {

    private Long id;

    @NotBlank(message = "O nome do bairro é obrigatório.")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres.")
    private String nome;

    @NotBlank(message = "A zona é obrigatória.")
    @Size(max = 10, message = "A zona deve ter no máximo 10 caracteres.")
    private String zona;

    @NotNull(message = "A área do bairro é obrigatória.")
    @Positive(message = "A área deve ser maior que zero.")
    private Double areaKm2;

    public BairroDTO() {
    }

    public BairroDTO(Long id, String nome, String zona, Double areaKm2) {
        this.id = id;
        this.nome = nome;
        this.zona = zona;
        this.areaKm2 = areaKm2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Double getAreaKm2() {
        return areaKm2;
    }

    public void setAreaKm2(Double areaKm2) {
        this.areaKm2 = areaKm2;
    }
}
