package com.belkin.yahack.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InteractiveImageButtonRequest extends InteractiveItemRequest {
    private String imageUrl;
    private String buttonText;
    private String buttonUrl;
}
