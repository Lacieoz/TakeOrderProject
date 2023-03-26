package it.beata.ordermanager.orders.businesslogic.service;

import it.beata.ordermanager.orders.businesslogic.model.Order;
import it.beata.ordermanager.orders.outbound.OrderRepository;
import it.beata.ordermanager.qrcode.service.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final QrCodeService qrCodeService;

    @Autowired
    public OrderService (OrderRepository orderRepository, QrCodeService qrCodeService) {
        this.orderRepository = orderRepository;
        this.qrCodeService = qrCodeService;
    }

    public Order getOrder (String id) {
        log.info("GET ORDER id = '{}'", id);

        try {
            return orderRepository.findById(id).orElse(null);
        } catch (Exception exception) {
            log.error("ERRORE IN GET : ", exception);
        }

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public Order createOrder(Order order) {
        log.info("CREATE NEW ORDER, '{}'", order);

        return orderRepository.insert(order);
    }

    public Order updateOrder (String id, Order order) {

        log.info("UPDATE ORDER id = '{}' , '{}'", id, order);
        order.setId(id);
        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        log.info("DELETE ORDER id = '{}'", id);

        try {
            orderRepository.deleteById(id);
            return;
        } catch (Exception exception) {
            log.error("ERRORE IN DELETE : ", exception);
        }

        throw new RuntimeException("Errore di sistema, riprovare!");
    }

    public Order payOrder(String id) {
        log.info("PAY ORDER id '{}'", id);

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("NO ORDER found id '" + id + "'"));
        order.payOrder();
        return orderRepository.save(order);
    }

    public List<Order> getLastOrders () {
        // TODO
        return null;
    }

    public BufferedImage getQrCode (String id) {
        Order order = getOrder(id);
        return getQrCode(order);
    }

    public BufferedImage getQrCode (Order order) {
        try {
            return qrCodeService.generateQRCodeImage(order);
        } catch (Exception ex) {
            log.error("ERRORE NELLA GENERAZIONE DEL QR CODE : ", ex);
            return null;
        }
    }
}
