package com.belkin.yahack.api.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InteractiveTextRequest extends InteractiveItemRequest {
    private String text;
    private boolean hasInputForm;
}
