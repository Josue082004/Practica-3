package com.Practica2.rest.controller.dao.services;

import com.Practica2.rest.controller.dao.TransaccionDao;
import com.Practica2.rest.controller.tda.list.Exception.ListEmptyException;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.models.Transaccion;

public class TransaccionServices {
    private TransaccionDao transaccionDao;

    public TransaccionServices() {
        transaccionDao = new TransaccionDao();
    }

    public Boolean save() throws Exception {
        return transaccionDao.save();
    }

    public Boolean update() throws Exception {
        return transaccionDao.update();
    }

    public LinkedList<Transaccion> listAll() {
        return transaccionDao.getListAll();
    }

    public Transaccion getTransaccion() {
        return transaccionDao.getTransaccion();
    }

    public void setTransaccion(Transaccion transaccion) {
        transaccionDao.setTransaccion(transaccion);
    }

    public Transaccion get(Integer id) throws Exception {
        return transaccionDao.get(id);
    }

    public Boolean delete(Integer id) throws Exception {
        return transaccionDao.delete(id);
    }

    public LinkedList<Transaccion> getTransaccionesByInversionista(String inversionista) {
        return transaccionDao.getTransaccionesByInversionista(inversionista);
    }
}