package ro.sdi.lab24.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@javax.persistence.Entity
@Data
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),
        @NamedEntityGraph(name = "movieWithRentalsAndClients",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "rentalWithClient"
                ),
                subgraphs = @NamedSubgraph(name = "rentalWithClient",
                        attributeNodes = @NamedAttributeNode(value = "client")
                )
        )
})
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "movieid"))})
public class Movie extends Entity<Integer> implements Serializable {
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[a-zA-Z]+$")
    @Column(name = "genre")
    private String genre;

    @Min(0)
    @Max(100)
    @Column(name = "rating")
    private int rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Rental> rentals;
}
