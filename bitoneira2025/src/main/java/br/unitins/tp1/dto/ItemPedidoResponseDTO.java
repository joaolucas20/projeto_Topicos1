package br.unitins.tp1.dto;

public class ItemPedidoResponseDTO {

    private Long id;
    private Integer quantidade;
    private Double preco;
    private Long pedidoId;
    private Long betoneiraId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getBetoneiraId() {
        return betoneiraId;
    }

    public void setBetoneiraId(Long betoneiraId) {
        this.betoneiraId = betoneiraId;
    }
}