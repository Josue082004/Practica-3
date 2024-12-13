package com.Practica2.rest.controller.dao;

import com.Practica2.rest.controller.dao.implement.AdapterDao;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.models.Transaccion;
import com.google.gson.Gson;

public class TransaccionDao extends AdapterDao<Transaccion> {
    private Transaccion transaccion;
    private LinkedList<Transaccion> listAll;
    private Gson gson = new Gson(); 
    
    public TransaccionDao() {
        super(Transaccion.class);
    }

    public Transaccion getTransaccion() {
        if (transaccion == null) {
            transaccion = new Transaccion();
        }
        return this.transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public LinkedList<Transaccion> getListAll() {
        if (listAll == null) {
            this.listAll = super.listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        transaccion.setIdTransaccion(id);
        this.persist(this.transaccion);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getTransaccion(), getTransaccion().getIdTransaccion() - 1);
        this.listAll = super.listAll();
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Transaccion> list = getListAll();
        list.delete(id - 1);
        String data = gson.toJson(list.toArray(new Transaccion[0]));
        saveFile(data);
        return true;
    }

    public void addTransaccion(Transaccion transaccion) {
        this.listAll.add(transaccion);
    }

    public LinkedList<Transaccion> getTransaccionesByInversionista(String inversionista) {
        LinkedList<Transaccion> transacciones = new LinkedList<>();
        for (int i = 0; i < listAll.getSize(); i++) {
            try {
                Transaccion transaccion = listAll.get(i);
                if (transaccion.getInversionista().equals(inversionista)) {
                    transacciones.add(transaccion);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transacciones;
    }
}