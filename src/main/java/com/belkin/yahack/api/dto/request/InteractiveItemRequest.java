package com.belkin.yahack.api.dto.request;

import com.belkin.yahack.model.ItemType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InteractivePollRequest.class, name = "poll"),
        @JsonSubTypes.Type(value = InteractiveImageButtonRequest.class, name = "imagebutton"),
        @JsonSubTypes.Type(value = InteractiveTextRequest.class, name = "text")
})
public class InteractiveItemRequest {
    private Integer timeStart;
    private Integer timeEnd;

    private ItemType type;
}
