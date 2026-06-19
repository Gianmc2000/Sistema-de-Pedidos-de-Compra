package com.examen.pedidos.service.impl;

import com.examen.pedidos.dto.request.ProductoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.ProductoResponseDTO;
import com.examen.pedidos.entity.Producto;
import com.examen.pedidos.exception.ErroresCapaDelNegocio;
import com.examen.pedidos.mapper.ProductoMapper;
import com.examen.pedidos.repository.ProductoRepository;
import com.examen.pedidos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public BaseResponse<ProductoResponseDTO> crearProducto(ProductoRequestDTO request) {

        Producto producto = productoMapper.toEntity(request);
        producto.setEstado(true);
        Producto productoSalida = productoRepository.save(producto);
        ProductoResponseDTO productoResponseDTO = productoMapper.toResponse(productoSalida);

        return BaseResponse.<ProductoResponseDTO>builder()
                .objeto(productoResponseDTO)
                .codigo(201)
                .mensaje("Producto creado")
                .build();
    }

    @Override
    public Producto obtenerProductoActivo(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ErroresCapaDelNegocio("Producto no encontrado"));

        if (!producto.getEstado()) {
            throw new ErroresCapaDelNegocio("Producto inactivo");
        }
        return producto;
    }

    @Override
    public void descontarStock(Long productoId, Integer cantidad) {
        Producto producto = obtenerProductoActivo(productoId);

        if (producto.getStock() < cantidad) {
            throw new ErroresCapaDelNegocio("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }
}