package diary;

import diary.task.*;

import java.time.LocalDate;
import java.util.*;

public class TaskManager<T extends Task> {
    private final Map<Integer, T> tasks = new HashMap<>();

    public void add(String title, String text, TaskType taskType, RepetitionType repetitionType) throws TaskException {
        T task = null;
        switch (repetitionType) {
            case SINGLE:
                task = (T) new TaskSingle(title, text, taskType);
                break;
            case DAILY:
                task = (T) new TaskDay(title, text, taskType);
                break;
            case WEEKLY:
                task = (T) new TaskWeek(title, text, taskType);
                break;
            case MONTHLY:
                task = (T) new TaskMonth(title, text, taskType);
                break;
            case YEARLY:
                task = (T) new TaskYear(title, text, taskType);
                break;
        }
        tasks.put(task.getId(), task);
    }

    public void change(Integer id, String title, String text) throws TaskException {
        if (!tasks.containsKey(id)) {
            throw new TaskException("Задачи с таким ID не найдено");
        }
        var task = tasks.get(id);
        task.setTitle(title);
        task.setText(text);
    }

    public void delete(Integer id) throws TaskException {
        if (!tasks.containsKey(id)) {
            throw new TaskException("Задачи с таким ID не найдено");
        }
        tasks.get(id).setDeleted(true);
    }

    public Set<T> getTasksByDate(LocalDate date) throws TaskException {
        Set<T> resultTasks = new HashSet<>();
        for (Task task : tasks.values()) {
            if (!task.isDeleted() && task.checkDateIsAvailable(date)) {
                resultTasks.add((T) task);
            }
        }
        if (resultTasks.isEmpty()) {
            throw new TaskException("Задач на эту дату не найдено");
        }
        return resultTasks;
    }

    public Map<LocalDate, Set<T>> getActiveTasks() throws TaskException {
        return getTasks(false);
    }

    public Map<LocalDate, Set<T>> getDeletedTasks() throws TaskException {
        return getTasks(true);
    }

    private Map<LocalDate, Set<T>> getTasks(boolean onlyDeleted) throws TaskException {
        Map<LocalDate, Set<T>> result = new HashMap<>();
        for (Task task : tasks.values()) {
            if ((onlyDeleted && task.isDeleted()) || (!onlyDeleted && !task.isDeleted())) {
                if (!result.containsKey(task.getCreateDate().toLocalDate())){
                    Set<T> taskSet = new HashSet<>();
                    taskSet.add((T) task);
                    result.put(task.getCreateDate().toLocalDate(), taskSet);
                } else {
                    result.get(task.getCreateDate().toLocalDate()).add((T) task);
                }
            }
        }
        if (result.isEmpty()) {
            throw new TaskException("Список задач пуст");
        }
        return result;
    }
}
