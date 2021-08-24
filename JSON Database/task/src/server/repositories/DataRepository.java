package server.repositories;

import com.google.gson.Gson;
import server.exceptions.NoSuchKeyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataRepository {
    private final static String DB_PATH = "src/server/data/db.json";
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private Map<String, String> dataSource;
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
        parseDataFromJson();
        if (dataSource.containsKey(key)) {
            return dataSource.get(key);
        } else {
            throw new NoSuchKeyException();
        }
    }

    public void set(String key, String value) {
        dataSource.put(key, value);
        writeDataToJson();
    }

    public void delete(String key) {
        if (dataSource.containsKey(key)) {
            dataSource.remove(key);
            writeDataToJson();
        } else {
            throw new NoSuchKeyException();
        }
    }

    private void parseDataFromJson() {
        readLock.lock();
        try {
            String data = Files.readString(Paths.get(DB_PATH));
            Gson gson = new Gson();
            dataSource = gson.fromJson(data, Map.class);
        } catch (IOException ignored) {
        } finally {
            readLock.unlock();
        }
    }

    private void writeDataToJson() {
        writeLock.lock();
        try (
            FileWriter fileWriter = new FileWriter(DB_PATH);
            PrintWriter printWriter = new PrintWriter(fileWriter)
        ) {
            Gson gson = new Gson();
            String dataToWrite = gson.toJson(dataSource);
            printWriter.print(dataToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
