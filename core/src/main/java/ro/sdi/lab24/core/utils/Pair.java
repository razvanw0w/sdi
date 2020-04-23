package ro.sdi.lab24.core.utils;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class Pair<K, V> implements Serializable {
    private K key;
    private V value;
}
