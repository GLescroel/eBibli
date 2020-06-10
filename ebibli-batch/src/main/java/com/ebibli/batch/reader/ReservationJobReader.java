package com.ebibli.batch.reader;

import com.ebibli.dto.ReservationDto;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

public class ReservationJobReader extends ListItemReader<ReservationDto> {

    public ReservationJobReader(List list) {
        super(list);
    }

}
