package ro.sdi.lab24.core.utils;

import lombok.*;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class SortUnit implements Serializable {
    private Sort.Direction direction;
    private String field;
}
