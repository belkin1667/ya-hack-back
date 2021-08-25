package com.belkin.yahack.model;

import javax.persistence.Entity;

import com.belkin.yahack.api.dto.request.InteractiveTextRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class InteractiveText extends InteractiveItem {

    public InteractiveText(InteractiveTextRequest request) {
        super(request);
        this.text = request.getText();
        this.hasInputForm = request.isHasInputForm();
    }

    private String text;
    private boolean hasInputForm;
}
