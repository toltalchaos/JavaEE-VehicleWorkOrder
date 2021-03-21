package dmit2015.btol1.assignment03.view;

import dmit2015.btol1.assignment03.entity.VehicleWorkOrder;
import dmit2015.btol1.assignment03.repository.VehicleWorkOrderRepository;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentVehicleWorkOrderListController")
@ViewScoped
public class VehicleWorkOrderListController implements Serializable {

    @Inject
    private VehicleWorkOrderRepository _vehicleworkorderRepository;

    @Getter
    private List<VehicleWorkOrder> vehicleworkorderList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            vehicleworkorderList = _vehicleworkorderRepository.findAllWorkOrders();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}