package ru.practicum.ewm.stats.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsDto {

    private List<ViewStats> viewStats;

}
