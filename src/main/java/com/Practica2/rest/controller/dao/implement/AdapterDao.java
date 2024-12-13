package com.Practica2.rest.controller.dao.implement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.Practica2.rest.controller.tda.list.LinkedList;
import com.google.gson.Gson;

public class AdapterDao<T> implements InterfazDao<T> {
    private Class<T> clazz;
    private Gson gson;

    public static final String FILE_PATH = "media/";

    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.gson = new Gson();
    }

    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            T[] array = gson.fromJson(data, com.google.gson.reflect.TypeToken.getArray(clazz).getType());
            list.fromArray(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            T[] array = list.toArray((T[]) java.lang.reflect.Array.newInstance(clazz, list.getSize()));
            return array[id - 1];
        }
        return null;
    }

    public void merge(T object, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, index);
        String data = gson.toJson(list.toArray((T[]) java.lang.reflect.Array.newInstance(clazz, list.getSize())));
        saveFile(data);
    }

    public void persist(T object) throws Exception {
        LinkedList<T> list = listAll();
        list.add(object);
        String data = gson.toJson(list.toArray((T[]) java.lang.reflect.Array.newInstance(clazz, list.getSize())));
        saveFile(data);
    }

    private String readFile() throws Exception {
        File file = new File(FILE_PATH + clazz.getSimpleName() + ".json");

        if (!file.exists()) {
            System.out.println("El archivo no existe, creando uno nuevo: " + file.getAbsolutePath());
            saveFile("[]");
        }

        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        }
        return sb.toString().trim();
    }

    protected void saveFile(String data) throws Exception {
        File file = new File(FILE_PATH + clazz.getSimpleName() + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            System.out.println("Creando el archivo JSON: " + file.getAbsolutePath());
            file.createNewFile();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
            writer.flush();
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}