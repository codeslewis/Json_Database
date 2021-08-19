package server;

public class Database {

    private final String[] cells;
    private static Database instance;
    private boolean exit = false;

    {
        cells = new String[1000];
    }

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean isExit() {
        return exit;
    }

    public String handleInput(String[] commands) {
        Command command = Command.valueOf(commands[0].toUpperCase());
        if (command == Command.EXIT) {
            this.exit = true;
            return "OK";
        }
        if (indexOutOfBounds(Integer.parseInt(commands[1]))) {
            return "ERROR";
        }
        switch (command) {
            case GET:
                return get(Integer.parseInt(commands[1]));
            case SET:
                return set(Integer.parseInt(commands[1]), buildString(commands));
            case DELETE:
                return delete(Integer.parseInt(commands[1]));
            default:
                return "ERROR";
        }
    }

    private String get(int index) {
        if (cells[index - 1] == null) {
            return "ERROR";
        }
        return cells[index - 1];
    }

    private String set(int index, String value) {
        cells[index -1] = value;
        return "OK";
    }

    private String delete(int index) {
        cells[index - 1] = null;
        return "OK";
    }

    private boolean indexOutOfBounds(int index) {
        return index < 1 || index > 100;
    }

    private String buildString(String[] commands) {
        StringBuilder result = new StringBuilder();
        for (int i = 2; i < commands.length; i++) {
            result.append(commands[i]);
            result.append(" ");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
