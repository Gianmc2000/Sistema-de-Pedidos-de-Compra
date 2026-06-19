package com.examen.pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {

    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaPedido;
    private String estado;
    private Double total;
    private Long clienteId;
    private List<PedidoDetalleResponseDTO> detalles;
}