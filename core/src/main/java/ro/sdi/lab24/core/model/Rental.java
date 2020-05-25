package ro.sdi.lab24.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@javax.persistence.Entity
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "rentalid"))})
public class Rental extends Entity<Integer> implements Serializable {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "clientid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Client client;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movieid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Movie movie;
    @Past
    @Column(name = "time")
    private LocalDateTime time;
}
