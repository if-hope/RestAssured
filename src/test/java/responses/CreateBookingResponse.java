package responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import requests.CreateBooking;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookingResponse {

    @JsonProperty("bookingid")
    private int bookingId;
    private CreateBooking booking;
}
