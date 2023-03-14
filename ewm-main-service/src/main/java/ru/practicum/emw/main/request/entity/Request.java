package ru.practicum.emw.main.request.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.emw.main.event.entity.Event;
import ru.practicum.emw.main.request.dto.RequestStatus;
import ru.practicum.emw.main.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(name = "status", nullable = false, length = 120)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

}
