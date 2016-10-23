package ch.unibe.ese.team1.controller.pojos.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BidForm {

    @NotNull(message = "Requires a number")
    @Min(value = 0, message = "In your dreams.")
    private Integer amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
