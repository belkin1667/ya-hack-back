package com.belkin.yahack.model;


import javax.persistence.Entity;

import com.belkin.yahack.api.dto.request.InteractiveImageButtonRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
public class InteractiveImageButton extends InteractiveItem {

    public InteractiveImageButton(InteractiveImageButtonRequest request) {
        super(request);
        this.buttonText = request.getButtonText();
        this.imageUrl = request.getImageUrl();
        this.buttonUrl = request.getButtonUrl();
    }

    private String imageUrl;
    private String buttonText;
    private String buttonUrl;

}
