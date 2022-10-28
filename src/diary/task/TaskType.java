package diary.task;

public enum TaskType {
    PERSONAL(1, "персональная"),
    WORK(2, "рабочая");

    private final int id;
    private final String text;

    public static TaskType getById(int id){
        for (TaskType value : TaskType.values()) {
            if (value.getId() == id){
                return value;
            }
        }
        return null;
    }

    public static String getStringValues(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < values().length; i++) {
            stringBuilder.append(values()[i].id).append("-").append(values()[i].text).append(" ");
        }
        return stringBuilder.toString();
    }

    TaskType(int id, String text){
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
