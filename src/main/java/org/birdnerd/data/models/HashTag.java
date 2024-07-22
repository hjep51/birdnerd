package org.birdnerd.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
public class HashTag extends AbstractEntity {

    private String name;
    private int weight;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private HashTagGroup hashTagGroup;

    public String getAsHashTag() {
        return "#" + name.toLowerCase().replace(" ", "");
    }
}
