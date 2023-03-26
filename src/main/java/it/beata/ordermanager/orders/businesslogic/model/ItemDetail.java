package it.beata.ordermanager.orders.businesslogic.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDetail {

    @NotBlank(message = "'id' cannot be blank")
    private String id;
    @NotBlank(message = "'name' cannot be blank")
    private String name;
    @NotNull(message = "'units' cannot be null")
    @Min(value = 0, message = "'units' minimum value is 0")
    private Integer units;
    @NotNull(message = "'price' cannot be null")
    @Min(value = 0, message = "'price' minimum value is 0")
    private BigDecimal price;

    // TODO : bug swagger shows totalPrice
    public BigDecimal getTotalPrice () {
        if (units == null || price == null || units < 0 || price.compareTo(new BigDecimal(0)) < 0)
            return new BigDecimal(0);
        return price.multiply(BigDecimal.valueOf(units));
    }
}
