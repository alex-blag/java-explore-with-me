package ru.practicum.emw.main.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.request.dto.RequestStatusUpdate;

import java.util.List;

@Data
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;

    private RequestStatusUpdate status;

}
