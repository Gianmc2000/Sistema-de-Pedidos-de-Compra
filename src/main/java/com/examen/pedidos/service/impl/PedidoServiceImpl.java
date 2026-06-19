package com.examen.pedidos.service.impl;

import com.examen.pedidos.dto.request.PedidoDetalleRequestDTO;
import com.examen.pedidos.dto.request.PedidoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.PedidoResponseDTO;
import com.examen.pedidos.entity.*;
import com.examen.pedidos.exception.ErroresCapaDelNegocio;
import com.examen.pedidos.mapper.PedidoDetalleMapper;
import com.examen.pedidos.mapper.PedidoMapper;
import com.examen.pedidos.repository.ClienteRepository;
import com.examen.pedidos.repository.PedidoRepository;
import com.examen.pedidos.service.PedidoService;
import com.examen.pedidos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoService productoService;
    private final PedidoMapper pedidoMapper;


    @Override
    @Transactional
    public BaseResponse<PedidoResponseDTO> crearPedido(PedidoRequestDTO request) {

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ErroresCapaDelNegocio("Cliente no encontrado"));

        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new ErroresCapaDelNegocio("El pedido debe tener al menos un detalle");
        }

        Pedido pedido = PedidoMapper.toEntity(cliente);

        List<PedidoDetalle> detalles = new ArrayList<>();
        double total = 0.0;


        for (PedidoDetalleRequestDTO dto : request.getDetalles()) {
            Producto producto = productoService.obtenerProductoActivo(dto.getProductoId());
            productoService.descontarStock(producto.getId(), dto.getCantidad());
            PedidoDetalle detalle = PedidoDetalleMapper.toEntity(dto, producto, pedido);
            total += detalle.getSubtotal();
            detalles.add(detalle);
        }

        pedido.setTotal(total);
        pedido.setDetalles(detalles);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        PedidoResponseDTO responseDTO =
                PedidoMapper.toResponseDTO(pedidoGuardado, pedidoGuardado.getDetalles());

        return BaseResponse.<PedidoResponseDTO>builder()
                .codigo(201)
                .mensaje("Pedido creado correctamente")
                .objeto(responseDTO)
                .build();
    }

    @Override
    public BaseResponse<PedidoResponseDTO> obtenerPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return BaseResponse.<PedidoResponseDTO>builder()
                .codigo(200)
                .mensaje("Pedido encontrado")
                .objeto(pedidoMapper.toResponseDTO(pedido, pedido.getDetalles()))
                .build();
    }

    @Override
    public BaseResponse<List<PedidoResponseDTO>> obtenerPedidosPorCliente(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);

        if (pedidos.isEmpty()) {
            return BaseResponse.<List<PedidoResponseDTO>>builder()
                    .codigo(200)
                    .mensaje("No se encontraron pedidos para el cliente")
                    .objeto(new ArrayList<>())
                    .build();
        }

        List<PedidoResponseDTO> response = pedidos.stream()
                .map(pedido -> pedidoMapper.toResponseDTO(pedido, pedido.getDetalles()))
                .toList();

        return BaseResponse.<List<PedidoResponseDTO>>builder()
                .codigo(200)
                .mensaje("Pedidos encontrados")
                .objeto(response)
                .build();
    }
}