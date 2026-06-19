package com.examen.pedidos.mapper;

import com.examen.pedidos.dto.request.ProductoRequestDTO;
import com.examen.pedidos.dto.response.ProductoResponseDTO;
import com.examen.pedidos.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequestDTO dto) {
        if (dto == null) return null;

        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .estado(dto.getEstado())
                .build();
    }

    public ProductoResponseDTO toResponse(Producto entity) {
        if (entity == null) return null;

        return ProductoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .stock(entity.getStock())
                .estado(entity.getEstado())
                .build();
    }
}