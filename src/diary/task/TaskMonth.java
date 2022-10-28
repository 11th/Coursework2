package diary.task;

import java.time.LocalDate;

public class TaskMonth extends Task {
    public TaskMonth(String title, String description, TaskType taskType) throws TaskException {
        super(title, description, taskType);
        super.setRepetitionType(RepetitionType.MONTHLY);
    }

    @Override
    public LocalDate getNextDate() {
        return getCreateDate().toLocalDate().plusMonths(1);
    }

    @Override
    public boolean checkDateIsAvailable(LocalDate date) {
        if (getCreateDate().toLocalDate().isAfter(date)){
            return false;
        }
        return (getCreateDate().toLocalDate().getDayOfMonth() == date.getDayOfMonth());
    }
}
