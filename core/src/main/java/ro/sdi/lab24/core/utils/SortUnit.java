package ro.sdi.lab24.core.utils;

import lombok.*;
import ro.sdi.lab24.core.sorting.Sort;

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
