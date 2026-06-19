package com.examen.pedidos.mapper;

import com.examen.pedidos.dto.request.PedidoDetalleRequestDTO;
import com.examen.pedidos.dto.response.PedidoDetalleResponseDTO;
import com.examen.pedidos.entity.Pedido;
import com.examen.pedidos.entity.PedidoDetalle;
import com.examen.pedidos.entity.Producto;

public class PedidoDetalleMapper {

    public static PedidoDetalle toEntity(PedidoDetalleRequestDTO dto, Producto producto, Pedido pedido) {
        double subtotal = producto.getPrecio() * dto.getCantidad();

        return PedidoDetalle.builder()
                .pedido(pedido)
                .productoId(producto.getId())
                .nombreProducto(producto.getNombre())
                .cantidad(dto.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .build();
    }

    public static PedidoDetalleResponseDTO toResponseDTO(PedidoDetalle entity) {
        PedidoDetalleResponseDTO dto = new PedidoDetalleResponseDTO();
        dto.setId(entity.getId());
        dto.setProductoId(entity.getProductoId());
        dto.setNombreProducto(entity.getNombreProducto());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }
}