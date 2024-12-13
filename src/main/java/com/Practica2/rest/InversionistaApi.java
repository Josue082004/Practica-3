package com.Practica2.rest;

import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.Practica2.rest.controller.tda.list.LinkedList;
import com.Practica2.rest.controller.dao.services.InversionistaServices;
import com.Practica2.rest.models.Inversionista;
import com.google.gson.Gson;

@Path("/investor")
public class InversionistaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            InversionistaServices ps = new InversionistaServices();
            LinkedList<Inversionista> inversionistas = ps.listAll();
            map.put("msg", "OK");
            map.put("data", inversionistas != null ? inversionistas.toArray(new Inversionista[0]) : new Object[] {});
            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener los inversionistas: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        InversionistaServices ps = new InversionistaServices();
        try {
            ps.getInversionista().setNombre(map.get("nombre").toString());
            ps.getInversionista().setApellido(map.get("apellido").toString());
            ps.getInversionista().setCorreo(map.get("correo").toString());
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Inversionista guardado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al guardar el inversionista: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{idInversionista}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("idInversionista") Integer idInversionista) {
        HashMap<String, Object> map = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();
        try {
            System.out.println("Obteniendo Inversionista con id: " + idInversionista);
            Inversionista p = ps.get(idInversionista);
            System.out.println("Inversionista: " + p);
            map.put("msg", "OK");
            map.put("data", p);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener el inversionista: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/update/{idInversionista}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("idInversionista") Integer idInversionista, HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        InversionistaServices ps = new InversionistaServices();
        try {
            Inversionista p = ps.get(idInversionista);
            p.setNombre(map.get("nombre").toString());
            p.setApellido(map.get("apellido").toString());
            p.setCorreo(map.get("correo").toString());
            ps.setInversionista(p);
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Inversionista actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar el inversionista: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @SuppressWarnings("unchecked")
    @Path("/delete/{idInversionista}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("idInversionista") Integer idInversionista) {
        HashMap<String, Object> res = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();
        try {
            ps.delete(idInversionista);
            res.put("msg", "OK");
            res.put("data", "Inversionista eliminado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar el inversionista: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Path("/sort/{metodo}/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortPerson(@PathParam("metodo") String metodo, @PathParam("atributo") String atributo,
            @PathParam("orden") Integer orden) {
        HashMap<String, Object> map = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();
        System.out.println("Ordenando por: " + metodo + " " + atributo + " " + orden);
        LinkedList<Inversionista> listaOrdenada = new LinkedList<>();
        try {
            switch (metodo.toLowerCase()) {
                case "quicksort":
                    listaOrdenada = ps.ordenarQuicksort(orden, atributo);
                    break;
                case "mergesort":
                    listaOrdenada = ps.ordenarMergeSort(orden, atributo);
                    break;
                case "shellsort":
                    listaOrdenada = ps.ordenarShellSort(orden, atributo);
                    break;
                default:
                    throw new IllegalArgumentException("Método de ordenación no válido: " + metodo);
            }
            map.put("msg", "OK");
            map.put("data", listaOrdenada.toArray(new Inversionista[0]));
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al ordenar los inversionistas: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/search/{criterio}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPerson(@PathParam("criterio") String criterio, @PathParam("valor") String valor) {
        HashMap<String, Object> map = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();
        try {
            LinkedList<Inversionista> lista = ps.buscarInversionistasLineal(criterio, valor);
            map.put("msg", "OK");
            map.put("data", lista.toArray(new Inversionista[0]));
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al buscar los inversionistas: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}