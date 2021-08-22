package com.belkin.yahack.api.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter
@ToString
public class InteractiveItemRequest {
    private Integer timeStart;
    private Integer timeEnd;
}
