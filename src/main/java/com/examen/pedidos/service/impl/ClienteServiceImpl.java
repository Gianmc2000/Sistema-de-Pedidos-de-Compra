package com.examen.pedidos.service.impl;

import com.examen.pedidos.dto.request.ClienteRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.ClienteResponseDTO;
import com.examen.pedidos.entity.Cliente;
import com.examen.pedidos.exception.ErroresCapaDelNegocio;
import com.examen.pedidos.mapper.ClienteMapper;
import com.examen.pedidos.repository.ClienteRepository;
import com.examen.pedidos.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public BaseResponse<ClienteResponseDTO> crearCliente(ClienteRequestDTO request) {

        if(clienteRepository.existsByDni(request.getDni())) {
            throw new ErroresCapaDelNegocio("Ya existe un cliente con el DNI proporcionado");
        }

        Cliente cliente = clienteMapper.toEntity(request);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toResponse(clienteGuardado);

        return BaseResponse.<ClienteResponseDTO>builder()
                .codigo(201)
                .mensaje("Cliente creado correctamente")
                .objeto(clienteResponseDTO)
                .build();
    }

}