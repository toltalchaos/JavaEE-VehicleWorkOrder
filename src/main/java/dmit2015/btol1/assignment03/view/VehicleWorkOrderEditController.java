package dmit2015.btol1.assignment03.view;

import dmit2015.btol1.assignment03.entity.VehicleWorkOrder;
import dmit2015.btol1.assignment03.repository.VehicleWorkOrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentVehicleWorkOrderEditController")
@ViewScoped
public class VehicleWorkOrderEditController implements Serializable {

    @Inject
    private VehicleWorkOrderRepository _vehicleworkorderRepository;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private VehicleWorkOrder existingVehicleWorkOrder;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            Optional<VehicleWorkOrder> optionalEntity = _vehicleworkorderRepository.findWorkOrder(editId);
            optionalEntity.ifPresent(entity -> existingVehicleWorkOrder = entity);
        }
    }

    public String onUpdate() {
        String nextPage = "";
        try {
            _vehicleworkorderRepository.updateWorkOrder(existingVehicleWorkOrder);
            Messages.addFlashGlobalInfo("Update was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Update was not successful.");
        }
        return nextPage;
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _vehicleworkorderRepository.deleteWorkOrder(existingVehicleWorkOrder.getId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}