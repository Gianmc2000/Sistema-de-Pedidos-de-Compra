package com.examen.pedidos.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    private Integer codigo;
    private String mensaje;
    private T objeto;
}