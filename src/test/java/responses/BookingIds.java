package responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookingIds {
    @JsonProperty("bookingid")
    private int bookingId;

}
