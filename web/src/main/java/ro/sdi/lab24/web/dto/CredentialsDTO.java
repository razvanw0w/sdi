package ro.sdi.lab24.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString()
@Builder
public class CredentialsDTO {
    private String type;
}
