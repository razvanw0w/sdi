package ro.sdi.lab24.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class RentedMovieStatisticsDTO implements Serializable {
    private List<RentedMovieStatisticDTO> rentedMovieStatistics;
}
