package com.thaddeus.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class UserLoginDTO {

    @JsonProperty("js_code")
    private String code;

}
