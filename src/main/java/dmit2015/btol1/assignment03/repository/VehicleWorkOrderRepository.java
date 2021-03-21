/*

    @author Braydon Tol
    @version 1.0.1 2021-02-24

    @description this repository class is designed to be the controller that interfaces between
                 the VehicleWorkOrder entity and the CRUD logic passed through the resource.

 */


package dmit2015.btol1.assignment03.repository;


import dmit2015.btol1.assignment03.entity.VehicleWorkOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class VehicleWorkOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void addWorkOrder(VehicleWorkOrder workOrder){
        entityManager.persist(workOrder);
    }


    public void deleteWorkOrder(Long workOrderID){
        Optional<VehicleWorkOrder> optionalVehicleWorkOrder = findWorkOrder(workOrderID);
        if(optionalVehicleWorkOrder.isPresent()){
            VehicleWorkOrder workordertodelete = optionalVehicleWorkOrder.get();
            deleteWorkOrder(workordertodelete);
        }
    }

    public void deleteWorkOrder(VehicleWorkOrder workOrder){
        entityManager.remove(entityManager.merge(workOrder));
        entityManager.flush();
    }


    public void updateWorkOrder(VehicleWorkOrder workOrder){
        Optional<VehicleWorkOrder> optionalVehicleWorkOrder = findWorkOrder(workOrder.getId());
        if(optionalVehicleWorkOrder.isPresent()){
            VehicleWorkOrder existingOrder = optionalVehicleWorkOrder.get();
            existingOrder.setLastModified(LocalDateTime.now()); //assign datetime by system
            existingOrder.setVehicleDescription(workOrder.getVehicleDescription());
            existingOrder.setCompletedStatus(workOrder.getCompletedStatus());
            existingOrder.setWorkDescription(workOrder.getWorkDescription());
            existingOrder.setLaborHours(workOrder.getLaborHours());

        }
    }


    public Optional<VehicleWorkOrder> findWorkOrder(Long workOrderID){
        Optional<VehicleWorkOrder> optionalWorkOrder = Optional.empty();
        try{
            VehicleWorkOrder querysingle = entityManager.find(VehicleWorkOrder.class, workOrderID);
            if(querysingle != null){
                optionalWorkOrder = Optional.of(querysingle);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return optionalWorkOrder;
    }


    public List<VehicleWorkOrder> findAllWorkOrders(){
        return entityManager.createQuery(
                "SELECT x FROM VehicleWorkOrder x ", VehicleWorkOrder.class
        ).getResultList();
    }
}
