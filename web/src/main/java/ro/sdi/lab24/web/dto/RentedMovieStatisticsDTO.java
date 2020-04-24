package ro.sdi.lab24.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class RentedMovieStatisticsDTO implements Serializable {
    private Set<RentedMovieStatisticDTO> rentedMovieStatistics;
}
