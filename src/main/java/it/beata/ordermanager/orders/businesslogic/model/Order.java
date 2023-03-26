package it.beata.ordermanager.orders.businesslogic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("orders")
@Builder
public class Order {

    @Id
    private String id;
    private Long dateCreation;
    private Long datePayment;
    private Boolean isPaid;
    private List<ItemDetail> itemDetails;

    public Order (String id, Boolean isPaid, List<ItemDetail> itemDetails) {
        this.id = id;
        this.dateCreation = (new Date()).getTime();
        this.isPaid = isPaid;
        if (isPaid)
            this.datePayment = (new Date()).getTime();
        this.itemDetails = itemDetails;
    }

    public Order (Boolean isPaid, List<ItemDetail> itemDetails) {
        this.dateCreation = (new Date()).getTime();
        this.isPaid = isPaid;
        if (isPaid)
            this.datePayment = (new Date()).getTime();
        this.itemDetails = itemDetails;
    }

    public BigDecimal getTotalPrice () {
        if (itemDetails == null || itemDetails.size() == 0)
            return new BigDecimal(0);

        BigDecimal totalPrice = new BigDecimal(0);
        for (ItemDetail itemDetail : itemDetails) {
            totalPrice = totalPrice.add(itemDetail.getTotalPrice());
        }
        return totalPrice;
    }

    public void payOrder () {
        if (isPaid)
            throw new RuntimeException("Order '" + id + "' already paid");
        isPaid = true;
        this.datePayment = (new Date()).getTime();
    }

}
