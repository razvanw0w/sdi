package ro.sdi.lab24.web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class ClientDTO implements Serializable {
    private int id;
    private String name;
    private int fidelity;
}
