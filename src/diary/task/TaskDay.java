package diary.task;

import java.time.LocalDate;

public class TaskDay extends Task {
    public TaskDay(String title, String description, TaskType taskType) throws TaskException {
        super(title, description, taskType);
        super.setRepetitionType(RepetitionType.DAILY);
    }

    @Override
    public LocalDate getNextDate() {
        return getCreateDate().toLocalDate().plusDays(1);
    }

    @Override
    public boolean checkDateIsAvailable(LocalDate date) {
        return true;
    }
}
