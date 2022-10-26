package diary.task;

import java.time.LocalDate;

public class TaskYear extends Task {
    public TaskYear(String title, String description, TaskType taskType) throws TaskException {
        super(title, description, taskType);
        super.setRepetitionType(RepetitionType.YEARLY);
    }

    @Override
    public LocalDate getNextDate() {
        return getCreateDate().toLocalDate().plusYears(1);
    }

    @Override
    public boolean checkDateIsAvailable(LocalDate date) {
        if (getCreateDate().toLocalDate().isAfter(date)){
            return false;
        }
        return (getCreateDate().toLocalDate().getDayOfYear() == date.getDayOfYear());
    }
}
