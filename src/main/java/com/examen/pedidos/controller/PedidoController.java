package com.examen.pedidos.controller;

import com.examen.pedidos.dto.request.PedidoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.PedidoResponseDTO;
import com.examen.pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(
            summary = "Crear pedido",
            description = "Registra un pedido con uno o más productos"
    )
    @ApiResponse(responseCode = "201", description = "Pedido creado correctamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse<PedidoResponseDTO>> crearPedido(@Valid @RequestBody PedidoRequestDTO request) {
        return new ResponseEntity<>(pedidoService.crearPedido(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener pedido por ID",
            description = "Devuelve un pedido según su ID"
    )
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PedidoResponseDTO>> obtenerPedidoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.obtenerPedidoPorId(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Listar pedidos por cliente",
            description = "Devuelve todos los pedidos de un cliente"
    )
    @ApiResponse(responseCode = "200", description = "Pedidos encontrados")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<BaseResponse<List<PedidoResponseDTO>>> obtenerPedidosPorCliente(
            @PathVariable Long clienteId
    ) {
        return new ResponseEntity<>(pedidoService.obtenerPedidosPorCliente(clienteId), HttpStatus.OK);
    }
}