package diary.task;

import java.time.LocalDate;

public class TaskWeek extends Task {
    public TaskWeek(String title, String description, TaskType taskType) throws TaskException {
        super(title, description, taskType);
        super.setRepetitionType(RepetitionType.WEEKLY);
    }

    @Override
    public LocalDate getNextDate() {
        return getCreateDate().toLocalDate().plusWeeks(1);
    }

    @Override
    public boolean checkDateIsAvailable(LocalDate date) {
        if (getCreateDate().toLocalDate().isAfter(date)){
            return false;
        }
        return getCreateDate().toLocalDate().getDayOfWeek().equals(date.getDayOfWeek());
    }
}
