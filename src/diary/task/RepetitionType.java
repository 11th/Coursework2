package diary.task;

public enum RepetitionType {
    SINGLE(1, "однократная"),
    DAILY(2, "ежедневная"),
    WEEKLY(3, "еженедельная"),
    MONTHLY(4, "ежемесячная"),
    YEARLY(5, "ежегодная");

    public static RepetitionType getById(int id){
        for (RepetitionType value : RepetitionType.values()) {
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

    private final int id;
    private final String text;

    RepetitionType(int id, String text){
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
