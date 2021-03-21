/*

    @author Braydon Tol
    @version 1.0.1 2021-02-24

    @description this java class is designed to be the working controlling entity to manipulate data using
                 the repository and resource classes.

 */


package dmit2015.btol1.assignment03.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "VehicleWorkOrders")
public class VehicleWorkOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public VehicleWorkOrder() {
        //initiate times and labor
        this.createdDateTime = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime lastModified;

    @Column
    private Boolean completedStatus;

    @Column(nullable = false)
    @NotBlank(message = "The vehicleDescription field is required.")
    private String vehicleDescription;

    @Column(nullable = false)
    @NotBlank(message = "The workDescription field is required.")
    private String workDescription;

    @Column(nullable = false)
    @Min(value = 1, message = "there is a minimum of 1 labor hour per work order.")
    private int laborHours;

    @PrePersist
    private void beforePersist() {
        createdDateTime = LocalDateTime.now();
        lastModified = createdDateTime;
    }

    @PreUpdate
    private void beforeUpdate() {
        lastModified = LocalDateTime.now();
    }

//    public void setCreatedDateTime(LocalDateTime createdDateTime) {
//        //effectively locked this from ever being overwritten once constructed
//        LocalDateTime holder = this.createdDateTime;
//        this.createdDateTime = holder;
//    }
}