package com.Practica2.rest.controller.dao.services;

import com.Practica2.rest.controller.dao.ProyectoDao;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.models.Proyecto;
import com.Practica2.rest.controller.tda.list.Exception.ListEmptyException;

public class ProyectoServices {
    private ProyectoDao proyectoDao;

    public ProyectoServices() {
        proyectoDao = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return proyectoDao.save();
    }

    public Boolean update() throws Exception {
        return proyectoDao.update();
    }

    public LinkedList<Proyecto> listAll() {
        return proyectoDao.getListAll();
    }

    public Proyecto getProyecto() {
        return proyectoDao.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        proyectoDao.setProyecto(proyecto);
    }

    public Proyecto get(Integer id) throws Exception {
        return proyectoDao.get(id);
    }

    public Boolean delete(Integer id) throws Exception {
        return proyectoDao.delete(id);
    }

    public LinkedList<Proyecto> ordenarQuicksort(Integer type_order, String atributo) {
        return proyectoDao.ordenarQuicksort(type_order, atributo);
    }

    public LinkedList<Proyecto> ordenarMergeSort(Integer type_order, String atributo) {
        return proyectoDao.ordenarMergeSort(type_order, atributo);
    }

    public LinkedList<Proyecto> ordenarShellSort(Integer type_order, String atributo) {
        return proyectoDao.ordenarShellSort(type_order, atributo);
    }

    public LinkedList<Proyecto> buscarProyectosLineal(String criterio, String valor) throws ListEmptyException {
        return proyectoDao.buscarLineal(criterio, valor);
    }

    public LinkedList<Proyecto> buscarProyectosBinario(String criterio, String valor) throws ListEmptyException {
        return proyectoDao.buscarBinario(criterio, valor);
    }
}