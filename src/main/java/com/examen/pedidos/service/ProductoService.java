package com.examen.pedidos.service;

import com.examen.pedidos.dto.request.ProductoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.ProductoResponseDTO;
import com.examen.pedidos.entity.Producto;

public interface ProductoService {

    BaseResponse<ProductoResponseDTO> crearProducto(ProductoRequestDTO productoRequest);

    Producto obtenerProductoActivo(Long id);

    void descontarStock(Long productoId, Integer cantidad);
}