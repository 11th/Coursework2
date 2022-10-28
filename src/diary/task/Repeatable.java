package diary.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Repeatable {
    LocalDate getNextDate();
    boolean checkDateIsAvailable(LocalDate date);
}
