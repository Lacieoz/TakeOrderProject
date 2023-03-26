package it.beata.ordermanager.items.businesslogic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("items")
@Builder
public class Item {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @Indexed
    private String category;
    private BigDecimal fullPrice;
    private BigDecimal discountedPrice;
    private String note;
    private Integer limitedQuantity;
    private String imageId;

}
