package com.examen.pedidos.mapper;

import com.examen.pedidos.dto.request.PedidoRequestDTO;
import com.examen.pedidos.dto.response.PedidoResponseDTO;
import com.examen.pedidos.entity.Cliente;
import com.examen.pedidos.entity.Pedido;
import com.examen.pedidos.entity.PedidoDetalle;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PedidoMapper {

    public static Pedido toEntity(Cliente cliente) {
        return Pedido.builder()
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .estado("CREADO")
                .total(0.0)
                .build();
    }

    public static PedidoResponseDTO toResponseDTO(Pedido pedido, List<PedidoDetalle> detalles) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.getTotal());
        dto.setClienteId(pedido.getCliente().getId());

        dto.setDetalles(detalles
                        .stream()
                        .map(PedidoDetalleMapper::toResponseDTO)
                        .toList()
        );

        return dto;
    }
}