package diary;

import diary.task.*;

import java.time.LocalDate;
import java.util.*;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();

    public void add(String title, String text, TaskType taskType, RepetitionType repetitionType) throws TaskException {
        Task task = null;
        switch (repetitionType) {
            case SINGLE:
                task = new TaskSingle(title, text, taskType);
                break;
            case DAILY:
                task = new TaskDay(title, text, taskType);
                break;
            case WEEKLY:
                task = new TaskWeek(title, text, taskType);
                break;
            case MONTHLY:
                task = new TaskMonth(title, text, taskType);
                break;
            case YEARLY:
                task = new TaskYear(title, text, taskType);
                break;
        }
        if (task != null) {
            tasks.put(task.getId(), task);
        }
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

    public Set<Task> getTasksByDate(LocalDate date) throws TaskException {
        Set<Task> resultTasks = new HashSet<>();
        for (Task task : tasks.values()) {
            if (!task.isDeleted() && task.checkDateIsAvailable(date)) {
                resultTasks.add(task);
            }
        }
        if (resultTasks.isEmpty()) {
            throw new TaskException("Задач на эту дату не найдено");
        }
        return resultTasks;
    }

    public Map<LocalDate, Set<Task>> getActiveTasks() throws TaskException {
        return getTasks(false);
    }

    public Map<LocalDate, Set<Task>> getDeletedTasks() throws TaskException {
        return getTasks(true);
    }

    private Map<LocalDate, Set<Task>> getTasks(boolean onlyDeleted) throws TaskException {
        Map<LocalDate, Set<Task>> result = new HashMap<>();
        for (Task task : tasks.values()) {
            if ((onlyDeleted && task.isDeleted()) || (!onlyDeleted && !task.isDeleted())) {
                if (!result.containsKey(task.getCreateDate().toLocalDate())) {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    result.put(task.getCreateDate().toLocalDate(), taskSet);
                } else {
                    result.get(task.getCreateDate().toLocalDate()).add(task);
                }
            }
        }
        if (result.isEmpty()) {
            throw new TaskException("Список задач пуст");
        }
        return result;
    }
}
