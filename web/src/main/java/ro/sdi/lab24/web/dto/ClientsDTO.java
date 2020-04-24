package ro.sdi.lab24.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class ClientsDTO implements Serializable {
    private Set<ClientDTO> clients;
}
