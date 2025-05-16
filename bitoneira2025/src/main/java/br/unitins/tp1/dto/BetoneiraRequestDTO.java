package br.unitins.tp1.dto;
 
 import br.unitins.tp1.model.TipoBetoneiraEnum;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.Positive;
 import jakarta.validation.constraints.Size;
 import java.util.Objects;
 
 public class BetoneiraRequestDTO {
 
     @NotBlank(message = "Marca cannot be blank")
     @Size(max = 255, message = "Marca cannot exceed 255 characters")
     private String marca;
 
     @NotBlank(message = "Modelo cannot be blank")
     @Size(max = 255, message = "Modelo cannot exceed 255 characters")
     private String modelo;
 
     @NotNull(message = "Capacidade is required")
     @Positive(message = "Capacidade must be a positive value")
     private double capacidade;
 
     @NotNull(message = "Tipo cannot be null")
     private TipoBetoneiraEnum tipo;
 
     private Long fabricanteId;
 
     public BetoneiraRequestDTO() {
     }
 
     public BetoneiraRequestDTO(String marca, String modelo, double capacidade, TipoBetoneiraEnum tipo, Long fabricanteId) {
         this.marca = marca;
         this.modelo = modelo;
         this.capacidade = capacidade;
         this.tipo = tipo;
         this.fabricanteId = fabricanteId;
     }
 
     public String getMarca() {
         return marca;
     }
 
     public void setMarca(String marca) {
         this.marca = marca;
     }
 
     public String getModelo() {
         return modelo;
     }
 
     public void setModelo(String modelo) {
         this.modelo = modelo;
     }
 
     public double getCapacidade() {
         return capacidade;
     }
 
     public void setCapacidade(double capacidade) {
         this.capacidade = capacidade;
     }
 
     public TipoBetoneiraEnum getTipo() {
         return tipo;
     }
 
     public void setTipo(TipoBetoneiraEnum tipo) {
         this.tipo = tipo;
     }
 
     public Long getFabricanteId() {
         return fabricanteId;
     }
 
     public void setFabricanteId(Long fabricanteId) {
         this.fabricanteId = fabricanteId;
     }
 
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         BetoneiraRequestDTO that = (BetoneiraRequestDTO) o;
         return Double.compare(that.capacidade, capacidade) == 0 &&
                 Objects.equals(marca, that.marca) &&
                 Objects.equals(modelo, that.modelo) &&
                 tipo == that.tipo &&
                 Objects.equals(fabricanteId, that.fabricanteId);
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(marca, modelo, capacidade, tipo, fabricanteId);
     }
 }