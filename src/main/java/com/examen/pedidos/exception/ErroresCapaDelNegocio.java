package com.examen.pedidos.exception;

public class ErroresCapaDelNegocio  extends RuntimeException{

    public ErroresCapaDelNegocio(String mensaje) {
        super(mensaje);
    }
}
