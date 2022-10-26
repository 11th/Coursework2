package diary.task;

import java.time.LocalDate;

public class TaskSingle extends Task {
    public TaskSingle(String title, String description, TaskType taskType) throws TaskException {
        super(title, description, taskType);
        super.setRepetitionType(RepetitionType.SINGLE);
    }

    @Override
    public LocalDate getNextDate() {
        return getCreateDate().toLocalDate();
    }

    @Override
    public boolean checkDateIsAvailable(LocalDate date) {
        return getCreateDate().toLocalDate().equals(date);
    }
}
