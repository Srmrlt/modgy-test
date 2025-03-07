package ru.modgy.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.modgy.booking.model.ReasonOfStopBooking;
import ru.modgy.booking.model.StatusBooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate checkInDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate checkOutDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime checkInTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime checkOutTime;
    private StatusBooking status;
    private ReasonOfStopBooking reasonOfStop;
    @Size(max = 150, message = "Причина отмены. Длина текста не более {value} символов.")
    private String reasonOfCancel;
    @Min(value = 0, message = "Цена не может быть меньше {value}.")
    @Max(value = 999999, message = "Цена не может больше больше {value}.")
    private Double price;
    @Min(value = 0, message = "Стоимость не может быть меньше {value}.")
    @Max(value = 999999, message = "Стоимость не может больше больше {value}.")
    private Double amount;
    @Min(value = 0, message = "Сумма предоплаты не может быть меньше {value}.")
    @Max(value = 999999, message = "Сумма предоплаты не может больше больше {value}.")
    private Double prepaymentAmount;
    private Boolean isPrepaid;
    @Size(max = 150, message = "Комментарии не более {max} символов.")
    private String comment;
    private String fileUrl;
    private Long roomId;
    private List<Long> petIds;
}
