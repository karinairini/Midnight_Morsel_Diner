package projectPS.model.reservation;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import projectPS.model.person.client.ClientEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a Reservation entity. This class encapsulates the data of a reservation including its unique
 * identifier, associated client, reservation date and status.
 * It serves as a POJO for mapping data to the "RESERVATION" table in the database.
 */
@Table(name = "RESERVATION")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_client")
    private ClientEntity client;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
