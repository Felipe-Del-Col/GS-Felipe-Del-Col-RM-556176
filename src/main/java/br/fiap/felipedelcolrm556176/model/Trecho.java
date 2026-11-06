
package br.fiap.felipedelcolrm556176.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GS128_TRECHO")
public class Trecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGRADOURO", nullable = false)
    private String logradouro;

    @Column(name = "TIPOCALCADA", nullable = false)
    private String tipoCalcada;

    @Column(name = "ALTITUDE", nullable = false)
    private Integer altitude;

    @ManyToOne
    @JoinColumn(name = "BAIRRO_ID", nullable = false)
    private Bairro bairro;

    public Trecho() {
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

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
}

