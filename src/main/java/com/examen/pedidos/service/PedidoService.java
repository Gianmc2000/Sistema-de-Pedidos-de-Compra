package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.PedidoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {
    BaseResponse<PedidoResponseDTO> crearPedido(PedidoRequestDTO pedidoRequest);
    BaseResponse<PedidoResponseDTO> obtenerPedidoPorId(Long id);
    BaseResponse<List<PedidoResponseDTO>> obtenerPedidosPorCliente(Long id);
}
