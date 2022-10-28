package diary.task;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task implements Repeatable {
    private static int nextId;

    public static int getNextId() {
        return ++nextId;
    }

    private final int id;
    private final LocalDateTime createDate;
    private String title;
    private String text;
    private TaskType taskType;
    private RepetitionType repetitionType;
    private boolean isDeleted;

    public Task(String title, String text, TaskType taskType) throws TaskException {
        this(title, text, taskType, null);
    }

    public Task(String title, String text, TaskType taskType, RepetitionType repetitionType) throws TaskException {
        this.id = getNextId();
        this.createDate = LocalDateTime.now();
        setTitle(title);
        setText(text);
        setTaskType(taskType);
        setRepetitionType(repetitionType);
    }

    public final void setTitle(String title) throws TaskException {
        if (title == null || title.isEmpty()) {
            throw new TaskException("Укажите заголовок задачи");
        }
        this.title = title;
    }

    public final void setText(String text) throws TaskException {
        if (text == null || text.isEmpty()) {
            throw new TaskException("Укажите текст задачи");
        }
        this.text = text;
    }

    protected final void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    protected final void setRepetitionType(RepetitionType repetitionType) {
        this.repetitionType = repetitionType;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public RepetitionType getRepetitionType() {
        return repetitionType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Задача " +
                "ID: " + id +
                ", Дата создания: " + createDate.toLocalDate() +
                ", Заголовок: '" + title + '\'' +
                ", Текст: '" + text + '\'' +
                ", Тип: " + taskType.getText() +
                ", Вид: " + repetitionType.getText();
    }
}
