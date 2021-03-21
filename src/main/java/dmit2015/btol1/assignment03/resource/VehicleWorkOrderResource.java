/*

    @author Braydon Tol
    @version 1.0.1 2021-02-24

    @description this resource controller class is designed to be the interface to create
                    POST, GET, PUT, DELETE commands that communicate with the repository class
                    to do CRUD operations with the VehicleWorkOrder entity objects stores in the API

                    *curl commands included at bottom of this class page*

 */




package dmit2015.btol1.assignment03.resource;

import dmit2015.btol1.assignment03.entity.VehicleWorkOrder;
import dmit2015.btol1.assignment03.repository.VehicleWorkOrderRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;


@ApplicationScoped
// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("workorder")	        // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class VehicleWorkOrderResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private VehicleWorkOrderRepository vehicleWorkOrderRepository;

    @POST // CREATE THIS
    public Response postVehicleWorkOrder(@Valid VehicleWorkOrder newWorkOrder){
        if(newWorkOrder == null){
            throw new BadRequestException();
        }
        else {
            vehicleWorkOrderRepository.addWorkOrder(newWorkOrder);
            URI workorderuri = uriInfo.getAbsolutePathBuilder().path(newWorkOrder.getId().toString()).build();
            return Response.created(workorderuri).build();
        }
    }


    @GET //GET THIS ID
    @Path("{id}")
    public Response getSingleVehicleWorkOrder(@PathParam("id") Long id){
        Optional<VehicleWorkOrder> workOrderOptional = vehicleWorkOrderRepository.findWorkOrder(id);
        if (workOrderOptional.isEmpty()){
            throw new NotFoundException();
        }
        else {
            VehicleWorkOrder existingorder = workOrderOptional.get();
            return Response.ok(existingorder).build();
        }
    }


    @GET //GET ALL
    public Response getAllVehicleWorkOrders(){
        return Response.ok(vehicleWorkOrderRepository.findAllWorkOrders()).build();
    }


    @PUT //UPDATE THIS ID
    @Path("{id}")
    public Response updateVehicleWorkOrder(@PathParam("id") Long id, @Valid VehicleWorkOrder newWorkOrder){
        if (!id.equals(newWorkOrder.getId())){
            throw new BadRequestException();
        }
        else{
            Optional<VehicleWorkOrder> workOrderOptional = vehicleWorkOrderRepository.findWorkOrder(id);

            if (workOrderOptional.isEmpty()){
                throw new NotFoundException();
            }
            else{
                vehicleWorkOrderRepository.updateWorkOrder(newWorkOrder);
                //change this return to GET by the ID to see the new changes officially
                return Response.ok(newWorkOrder).build();
            }
        }

    }


    @DELETE //REMOVE THID ID
    @Path("{id}")
    public Response deleteVehicleWorkOrder(@PathParam("id") Long id){
        Optional<VehicleWorkOrder> workOrderOptional = vehicleWorkOrderRepository.findWorkOrder(id);
        if (workOrderOptional.isEmpty()){
            throw new NotFoundException();
        }
        else {
            vehicleWorkOrderRepository.deleteWorkOrder(id);
            return Response.noContent().build();
        }


    }

}

/*
curl commands / planning content
 - read all [code 200]
        curl -i -X GET http://localhost:8080/dmit2015-assignment03-BraydonTol/webapi/workorder


 - create new (x3) [code 201]
         curl -i -X POST http://localhost:8080/dmit2015-assignment02-BraydonTol/webapi/workorder
                    -d'{"completedStatus":true,"createdDateTime":"2021-01-23T19:24:38.959625","laborHours":50,"lastModified":"2021-02-23T19:24:38.959625","vehicleDescription":"Accura TL","workDescription":"engine rebuild"}'
                    -d'{"completedStatus":false,"createdDateTime":"2020-10-23T19:24:38.959625","laborHours":2,"lastModified":"2020-11-23T19:24:38.959625","vehicleDescription":"ford ranger","workDescription":"oil change"}'
                    -d'{"completedStatus":true,"createdDateTime":"2021-03-23T19:24:38.959625","laborHours":50,"lastModified":"2021-02-23T19:24:38.959625","vehicleDescription":"honda civic","workDescription":"fluid flush"}'
                    -H 'Content-Type:application/json'


 - read one [code 200]
        curl -i -X GET http://localhost:8080/dmit2015-assignment02-BraydonTol/webapi/workorder/1

 -update entity by id (x3) [code 200]
        curl -i -X PUT http://localhost:8080/dmit2015-assignment02-BraydonTol/webapi/workorder/1
                -d '{"id":1,"completedStatus":true,"laborHours":5,"vehicleDescription":"honda accord","workDescription":"oil change"}'
                 -d '{"completedStatus":true,"laborHours":20,"vehicleDescription":"honda accord","workDescription":"oil change"}'
                  -d '{"completedStatus":true,"laborHours":20,"vehicleDescription":"toytoa trash box","workDescription":"oil change"}'
                -H 'Content-Type:application/json'


 -delete entity by id [code 204]
         curl -i -X DELETE http://localhost:8080/dmit2015-assignment02-BraydonTol/webapi/workorder/1





 */
