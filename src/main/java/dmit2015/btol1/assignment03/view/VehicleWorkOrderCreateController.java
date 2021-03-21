package dmit2015.btol1.assignment03.view;

import dmit2015.btol1.assignment03.entity.VehicleWorkOrder;
import dmit2015.btol1.assignment03.repository.VehicleWorkOrderRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("currentVehicleWorkOrderCreateController")
@RequestScoped
public class VehicleWorkOrderCreateController {

    @Inject
    private VehicleWorkOrderRepository _vehicleworkorderRepository;

    @Getter
    private VehicleWorkOrder newVehicleWorkOrder = new VehicleWorkOrder();

    public String onCreate() {
        String nextPage = "";
        try {
            _vehicleworkorderRepository.addWorkOrder(newVehicleWorkOrder);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}