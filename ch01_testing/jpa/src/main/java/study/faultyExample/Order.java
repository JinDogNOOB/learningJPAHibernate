package study.faultyExample;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="orders")
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;
    @Column(name="customer_id")
    private Long customerId;
    @Column(name="orderdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    
}
