package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PedidoRequestDTO {

    @NotNull(message = "Data do Pedido cannot be null")
    private LocalDate dataPedido;

    @NotNull(message = "Cliente ID cannot be null")
    private Long clienteId;

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}