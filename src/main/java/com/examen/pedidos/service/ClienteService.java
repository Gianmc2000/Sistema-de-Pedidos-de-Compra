package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.ClienteRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.ClienteResponseDTO;
import com.examen.pedidos.entity.Cliente;

public interface ClienteService {
    BaseResponse<ClienteResponseDTO> crearCliente(ClienteRequestDTO clienteRequest);

}