package com.examen.pedidos.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDetalleResponseDTO {

    private Long id;
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}