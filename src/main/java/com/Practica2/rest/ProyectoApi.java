package com.Practica2.rest;

import java.util.Arrays;
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
import com.Practica2.rest.controller.dao.services.ProyectoServices;
import com.Practica2.rest.models.Proyecto;
import com.google.gson.Gson;

@Path("/proyecto")
public class ProyectoApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyectos() {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        LinkedList<Proyecto> proyectos = ps.listAll();
        map.put("msg", "OK");
        map.put("data", proyectos != null ? proyectos.toArray(new Proyecto[0]) : new Object[] {});
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        ProyectoServices ps = new ProyectoServices();
        try {
            ps.getProyecto().setNombre(map.get("nombre").toString());
            ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
            ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
            ps.getProyecto().setInversionTotal(Double.parseDouble(map.get("inversionTotal").toString()));
            ps.getProyecto().setCosto(Double.parseDouble(map.get("costo").toString()));
            ps.getProyecto().setProduccionDiaria(Double.parseDouble(map.get("produccionDiaria").toString()));
            ps.getProyecto().setEstado(map.get("estado").toString());
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto guardado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al guardar el proyecto: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{idProyecto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyecto(@PathParam("idProyecto") Integer idProyecto) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        try {
            Proyecto p = ps.get(idProyecto);
            map.put("msg", "OK");
            map.put("data", p);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener el proyecto: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/update/{idProyecto}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProyecto(@PathParam("idProyecto") Integer idProyecto, HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        ProyectoServices ps = new ProyectoServices();
        try {
            Proyecto p = ps.get(idProyecto);
            p.setNombre(map.get("nombre").toString());
            p.setFechaFin(map.get("fechaFin").toString());
            p.setFechaInicio(map.get("fechaInicio").toString());
            p.setInversionTotal(Double.parseDouble(map.get("inversionTotal").toString()));
            p.setCosto(Double.parseDouble(map.get("costo").toString()));
            p.setProduccionDiaria(Double.parseDouble(map.get("produccionDiaria").toString()));
            p.setEstado(map.get("estado").toString());
            ps.setProyecto(p);
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Proyecto actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar el proyecto: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{idProyecto}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProyecto(@PathParam("idProyecto") Integer idProyecto) {
        HashMap<String, Object> res = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        try {
            ps.delete(idProyecto);
            res.put("msg", "OK");
            res.put("data", "Proyecto eliminado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar el proyecto: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/ordenar/{metodo}/{atributo}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ordenarProyectos(@PathParam("metodo") String metodo, @PathParam("atributo") String atributo, @PathParam("orden") Integer orden) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        LinkedList<Proyecto> listaOrdenada = new LinkedList<>();
        try {
            LinkedList<Proyecto> listaProyectos = ps.listAll();
            if (listaProyectos.isEmpty()) {
                map.put("msg", "ERROR");
                map.put("data", "No hay proyectos para ordenar");
                return Response.status(Response.Status.NOT_FOUND).entity(map).build();
            }
            System.out.println("Lista original de proyectos: " + Arrays.toString(listaProyectos.toArray(new Proyecto[0])));
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
            if (listaOrdenada.isEmpty()) {
                map.put("msg", "ERROR");
                map.put("data", "La lista ordenada está vacía");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
            }
            System.out.println("Lista ordenada de proyectos: " + Arrays.toString(listaOrdenada.toArray(new Proyecto[0])));
            map.put("msg", "OK");
            map.put("data", listaOrdenada.toArray(new Proyecto[0]));
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al ordenar los proyectos: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/buscar/{metodo}/{criterio}/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProyectos(@PathParam("metodo") String metodo, @PathParam("criterio") String criterio, @PathParam("valor") String valor) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        LinkedList<Proyecto> lista = new LinkedList<>();
        try {
            switch (metodo.toLowerCase()) {
                case "lineal":
                    lista = ps.buscarProyectosLineal(criterio, valor);
                    break;
                case "binario":
                    lista = ps.buscarProyectosBinario(criterio, valor);
                    break;
                default:
                    throw new IllegalArgumentException("Método de búsqueda no válido: " + metodo);
            }
            if (lista.isEmpty()) {
                map.put("msg", "ERROR");
                map.put("data", "No se encontraron proyectos");
                return Response.status(Response.Status.NOT_FOUND).entity(map).build();
            }
            System.out.println("Lista de proyectos encontrados: " + Arrays.toString(lista.toArray(new Proyecto[0])));
            map.put("msg", "OK");
            map.put("data", lista.toArray(new Proyecto[0]));
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al buscar los proyectos: " + e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}
