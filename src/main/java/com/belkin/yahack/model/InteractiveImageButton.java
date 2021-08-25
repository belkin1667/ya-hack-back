package com.belkin.yahack.model;


import javax.persistence.Column;
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

    @Column(name = "image_url", columnDefinition = "VARCHAR")
    private String imageUrl;
    @Column(name = "button_text", columnDefinition = "VARCHAR")
    private String buttonText;
    @Column(name = "button_url", columnDefinition = "VARCHAR")
    private String buttonUrl;

}
