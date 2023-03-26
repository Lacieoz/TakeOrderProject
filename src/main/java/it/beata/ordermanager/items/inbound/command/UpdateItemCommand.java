package it.beata.ordermanager.items.inbound.command;

import it.beata.ordermanager.items.businesslogic.model.Item;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateItemCommand {

    private String id;
    @NotBlank(message = "'name' cannot be blank")
    private String name;
    private String category;
    @NotNull(message = "'fullPrice' cannot be null")
    @Digits(integer = 5, fraction = 2, message = "'fullPrice' must have structure like this xxxxx.yy")
    private BigDecimal fullPrice;
    @Digits(integer = 5, fraction = 2, message = "'fullPrice' must have structure like this xxxxx.yy")
    private BigDecimal discountedPrice;
    private String note;
    @Min(value = 0, message = "'limitedQuantity' minimum value is 0")
    private Integer limitedQuantity;
    private String imageId;

    public Item toItem () {
        return Item.builder()
                .id(id)
                .name(name)
                .category(category)
                .fullPrice(fullPrice)
                .discountedPrice(discountedPrice)
                .note(note)
                .limitedQuantity(limitedQuantity)
                .imageId(imageId)
                .build();
    }

}
