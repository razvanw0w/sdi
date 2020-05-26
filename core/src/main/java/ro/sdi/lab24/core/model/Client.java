package ro.sdi.lab24.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@javax.persistence.Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(name = "clientWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),
        @NamedEntityGraph(name = "clientWithRentalsAndMovies",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "rentalWithMovie"
                ),
                subgraphs = @NamedSubgraph(name = "rentalWithMovie",
                        attributeNodes = @NamedAttributeNode(value = "movie")
                )
        )
})
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "clientid"))})
public class Client extends Entity<Integer> implements Serializable {
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Column(name = "name")
    private String name;

    @Min(1)
    @Max(5)
    private Integer fidelity;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Rental> rentals;
}
