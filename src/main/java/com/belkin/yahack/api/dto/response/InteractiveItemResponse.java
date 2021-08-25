package com.belkin.yahack.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class InteractiveItemResponse {

    InteractiveItemResponse(String id, Integer timeStart, Integer timeEnd) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    private String id;
    private Integer timeStart;
    private Integer timeEnd;
    private String type;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InteractiveItemResponse) {
            return ((InteractiveItemResponse) obj).id.equals(id);
        }
        return false;
    }
}
