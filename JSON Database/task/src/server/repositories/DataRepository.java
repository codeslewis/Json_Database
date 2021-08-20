package server.repositories;

import server.exceptions.NoSuchKeyException;

import java.util.HashMap;
import java.util.Map;

public class DataRepository {
    private final Map<String, String> dataSource;
    private static DataRepository instance;

    {
        dataSource = new HashMap<>();
    }

    private DataRepository() {}

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public String get(String key) throws NoSuchKeyException {
        if (dataSource.containsKey(key)) {
            return dataSource.get(key);
        } else {
            throw new NoSuchKeyException();
        }
    }

    public void set(String key, String value) {
        dataSource.put(key, value);
    }

    public void delete(String key) {
        if (dataSource.containsKey(key)) {
            dataSource.remove(key);
        } else {
            throw new NoSuchKeyException();
        }
    }
}
