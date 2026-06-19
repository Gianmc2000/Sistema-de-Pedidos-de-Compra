package com.examen.pedidos.controller;

import com.examen.pedidos.dto.request.ProductoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.ProductoResponseDTO;
import com.examen.pedidos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    @Operation(
            summary = "Crear producto",
            description = "Registra un nuevo producto"
    )
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse<ProductoResponseDTO>> crearProducto(@Valid @RequestBody ProductoRequestDTO request) {
        return new ResponseEntity<>(productoService.crearProducto(request), HttpStatus.CREATED);
    }
}