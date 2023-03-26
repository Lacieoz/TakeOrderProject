package it.beata.ordermanager.orders.inbound.command;

import it.beata.ordermanager.orders.businesslogic.model.ItemDetail;
import it.beata.ordermanager.orders.businesslogic.model.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateOrderCommand {

    private String id;
    @NotNull(message = "'isPaid' cannot be null")
    private Boolean isPaid;
    @NotEmpty(message = "'itemDetails' cannot be null")
    private List<ItemDetail> itemDetails;

    public Order toOrder () {
        return new Order(id, isPaid, itemDetails);
    }

}
