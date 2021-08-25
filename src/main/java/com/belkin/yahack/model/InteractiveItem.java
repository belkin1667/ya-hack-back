package com.belkin.yahack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.belkin.yahack.api.dto.request.InteractiveItemRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "interactive_item")
public class InteractiveItem {

    public InteractiveItem(InteractiveItemRequest request) {
        this.timeStart = request.getTimeStart();
        this.timeEnd = request.getTimeEnd();
    }

    @Id
    @GenericGenerator(name = "item_base64_id", strategy = "com.belkin.yahack.model.generator.Base64Generator")
    @GeneratedValue(generator = "item_base64_id")
    private String id;
    private Integer timeStart;
    private Integer timeEnd;

    @ManyToOne
    @JoinColumn(name="episode_guid", nullable = false)
    private Episode episode;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InteractiveItem) {
            return ((InteractiveItem) obj).id.equals(id);
        }
        return false;
    }
}
