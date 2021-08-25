package com.belkin.yahack.api.dto.response;

import com.belkin.yahack.model.InteractiveText;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InteractiveTextResponse extends InteractiveItemResponse {

    public InteractiveTextResponse(InteractiveText interactiveText) {
        super(interactiveText.getId(), interactiveText.getTimeStart(), interactiveText.getTimeEnd(), "text");
        this.text = interactiveText.getText();
        this.hasInputForm = interactiveText.isHasInputForm();
    }

    private String text;
    private boolean hasInputForm;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
