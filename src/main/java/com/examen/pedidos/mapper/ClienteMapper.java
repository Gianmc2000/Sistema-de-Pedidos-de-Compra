package com.examen.pedidos.mapper;

import com.examen.pedidos.dto.request.ClienteRequestDTO;
import com.examen.pedidos.dto.response.ClienteResponseDTO;
import com.examen.pedidos.entity.Cliente;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO dto) {
        if (dto == null) return null;

        return Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDni())
                .correo(dto.getCorreo())
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    public ClienteResponseDTO toResponse(Cliente entity) {
        if (entity == null) return null;

        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .dni(entity.getDni())
                .correo(entity.getCorreo())
                .fechaRegistro(entity.getFechaRegistro())
                .build();
    }
}