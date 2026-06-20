package com.examen.pedidos.service.impl;

import com.examen.pedidos.dto.request.PedidoDetalleRequestDTO;
import com.examen.pedidos.dto.request.PedidoRequestDTO;
import com.examen.pedidos.dto.response.BaseResponse;
import com.examen.pedidos.dto.response.PedidoResponseDTO;
import com.examen.pedidos.entity.Cliente;
import com.examen.pedidos.entity.Pedido;
import com.examen.pedidos.entity.Producto;
import com.examen.pedidos.exception.ErroresCapaDelNegocio;
import com.examen.pedidos.repository.ClienteRepository;
import com.examen.pedidos.repository.PedidoRepository;
import com.examen.pedidos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoService productoService;

    private Cliente cliente;
    private Producto producto;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .build();

        producto = Producto.builder()
                .id(10L)
                .nombre("Laptop")
                .precio(1000.0)
                .stock(5)
                .estado(true)
                .build();
    }

    // =========================
    // TEST: CREAR PEDIDO OK
    // =========================
    @Test
    void crearPedido_cuandoDatosValidos_retornaPedidoCreado() {

        // =========================
        // ARRANGE
        // =========================
        PedidoDetalleRequestDTO detalleDTO = PedidoDetalleRequestDTO.builder()
                .productoId(10L)
                .cantidad(2)
                .build();

        PedidoRequestDTO request = PedidoRequestDTO.builder()
                .clienteId(1L)
                .detalles(List.of(detalleDTO))
                .build();

        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(cliente));

        when(productoService.obtenerProductoActivo(10L))
                .thenReturn(producto);

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // =========================
        // ACT
        // =========================
        BaseResponse<PedidoResponseDTO> response =
                pedidoService.crearPedido(request);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(response);
        assertEquals(201, response.getCodigo());
        assertEquals("Pedido creado correctamente", response.getMensaje());

        PedidoResponseDTO pedido = response.getObjeto();

        assertNotNull(pedido);
        assertEquals(2000.0, pedido.getTotal());
        assertEquals(1, pedido.getDetalles().size());

        verify(clienteRepository).findById(1L);
        verify(productoService).obtenerProductoActivo(10L);
        verify(productoService).descontarStock(10L, 2);
        verify(pedidoRepository).save(any(Pedido.class));
    }

    // =========================
    // TEST: ERROR STOCK
    // =========================
    @Test
    void crearPedido_cuandoNoHayStock_lanzaExcepcion() {

        // =========================
        // ARRANGE
        // =========================
        PedidoDetalleRequestDTO detalleDTO = PedidoDetalleRequestDTO.builder()
                .productoId(10L)
                .cantidad(10)
                .build();

        PedidoRequestDTO request = PedidoRequestDTO.builder()
                .clienteId(1L)
                .detalles(List.of(detalleDTO))
                .build();

        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(cliente));

        when(productoService.obtenerProductoActivo(10L))
                .thenThrow(new ErroresCapaDelNegocio("Stock insuficiente"));

        // =========================
        // ACT + ASSERT
        // =========================
        ErroresCapaDelNegocio exception = assertThrows(
                ErroresCapaDelNegocio.class,
                () -> pedidoService.crearPedido(request)
        );

        assertEquals("Stock insuficiente", exception.getMessage());

        verify(pedidoRepository, never()).save(any());
    }
}
