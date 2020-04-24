package ro.sdi.lab24.web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class RentedMovieStatisticDTO implements Serializable {
    String movieName;
    Long numberOfRentals;
}
