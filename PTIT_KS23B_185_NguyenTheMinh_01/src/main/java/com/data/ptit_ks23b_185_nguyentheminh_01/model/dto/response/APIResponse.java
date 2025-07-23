package com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T>{
    private String success;
    private T data;
    private String message;
    private int status;
}

