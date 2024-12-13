package com.Practica2.rest.controller.dao.services;

import com.Practica2.rest.controller.dao.InversionistaDao;
import com.Practica2.rest.controller.tda.list.Exception.ListEmptyException;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.models.Inversionista;

public class InversionistaServices {
    private InversionistaDao inversionistaDao;

    public InversionistaServices() {
        inversionistaDao = new InversionistaDao();
    }

    public Inversionista getInversionista() {
        return inversionistaDao.getInversionista();
    }

    public LinkedList<Inversionista> listAll() {
        return inversionistaDao.listAll();
    }

    public void setInversionista(Inversionista inversionista) {
        inversionistaDao.setInversionista(inversionista);
    }

    public Boolean save() throws Exception {
        return inversionistaDao.save();
    }

    public Boolean update() throws Exception {
        return inversionistaDao.update();
    }

    public Inversionista get(Integer id) throws Exception {
        return inversionistaDao.get(id);
    }

    public Boolean delete(Integer id) throws Exception {
        return inversionistaDao.delete(id);
    }

    public LinkedList<Inversionista> ordenarQuicksort(Integer type_order, String atributo) {
        return inversionistaDao.ordenarQuicksort(type_order, atributo);
    }

    public LinkedList<Inversionista> ordenarMergeSort(Integer type_order, String atributo) {
        return inversionistaDao.ordenarMergeSort(type_order, atributo);
    }

    public LinkedList<Inversionista> ordenarShellSort(Integer type_order, String atributo) {
        return inversionistaDao.ordenarShellSort(type_order, atributo);
    }

    public LinkedList<Inversionista> buscarInversionistasLineal(String criterio, String valor) throws ListEmptyException {
        return inversionistaDao.buscarLineal(criterio, valor);
    }

    public LinkedList<Inversionista> buscarInversionistasBinario(String criterio, String valor) throws ListEmptyException {
        return inversionistaDao.buscarBinario(criterio, valor);
    }
}