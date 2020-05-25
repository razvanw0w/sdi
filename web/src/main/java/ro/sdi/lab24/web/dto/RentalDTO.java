package ro.sdi.lab24.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class RentalDTO {
    private int rentalId;
    String time;
    private int movieId;
    private int clientId;
}
