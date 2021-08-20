package com.belkin.yahack.api.dto.response;

import com.belkin.yahack.model.InteractiveImageButton;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class InteractiveImageButtonResponse extends InteractiveItemResponse {

    public InteractiveImageButtonResponse(InteractiveImageButton imageButton) {
        super(imageButton.getId(), imageButton.getTimeStart(), imageButton.getTimeEnd());
        this.imageUrl = imageButton.getImageUrl();
        this.buttonText = imageButton.getButtonText();
        this.buttonUrl = imageButton.getButtonUrl();

        String type = "";
        if (imageUrl != null)
            type += "image";
        if (buttonUrl != null && buttonText != null)
            type += "button";
        this.setType(type);
    }

    public InteractiveImageButtonResponse(String id, Integer timeStart, Integer timeEnd, String type, String imageUrl, String buttonText, String buttonUrl) {
        super(id, timeStart, timeEnd, type);
        this.imageUrl = imageUrl;
        this.buttonText = buttonText;
        this.buttonUrl = buttonUrl;
    }

    private String imageUrl;
    private String buttonText;
    private String buttonUrl;
}
