package requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBooking {

    private String firstname;
    private String lastname;
    @JsonProperty("totalprice")
    private int totalPrice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

}
