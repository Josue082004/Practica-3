package com.Practica2.rest;

import com.Practica2.rest.controller.dao.services.ProyectoServices;
import com.Practica2.rest.models.Proyecto;
import com.Practica2.rest.controller.tda.list.LinkedList;
import java.util.Random;

public class tiempoBusqueda {

    public static void main(String[] args) throws Exception {
        int size = 25000;
        Random random = new Random();
        ProyectoServices proyectoServices = new ProyectoServices();
        Proyecto[] proyectos = new Proyecto[size];
        for (int i = 0; i < size; i++) {
            proyectos[i] = new Proyecto(i, "Proyecto" + i, "2023-01-01", "2023-12-31", "Activo", random.nextDouble() * 250000, random.nextDouble() * 25000, random.nextDouble() * 250000);
        }
        LinkedList<Proyecto> listaProyectos = new LinkedList<>();
        listaProyectos.fromArray(proyectos);
        proyectoServices.ordenarQuicksort(1, "nombre");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            listaProyectos.binarySearch("nombre", "Proyecto" + (size - 1));
        }
        long endTime = System.currentTimeMillis();
        long tiempoBusqueda = endTime - startTime;
        System.out.println("Tamano: " + size);
        System.out.println("Tiempo de busqueda binaria : " + tiempoBusqueda + " ms");
    }
}