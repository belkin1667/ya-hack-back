package com.belkin.yahack.model;


import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="interactive_imagebutton")
@NoArgsConstructor
public class InteractiveImageButton extends InteractiveItem {

    private String imageUrl;
    private String buttonText;
    private String buttonUrl;

}
