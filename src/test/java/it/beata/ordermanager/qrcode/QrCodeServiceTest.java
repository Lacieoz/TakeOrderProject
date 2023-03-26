package it.beata.ordermanager.qrcode;

import it.beata.ordermanager.orders.businesslogic.model.ItemDetail;
import it.beata.ordermanager.orders.businesslogic.model.Order;
import it.beata.ordermanager.qrcode.service.QrCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QrCodeServiceTest {

    @Autowired
    private QrCodeService qrCodeService;

    @Test
    public void testQrCode_string () throws Exception {
        String input = "input";
        BufferedImage bufferedImage = qrCodeService.generateQRCodeImage(input);
        String result = (String) qrCodeService.decodeQRCodeImage(bufferedImage, String.class);
        assertEquals(input, result);
    }

    @Test
    public void testQrCode_order () throws Exception {
        // OBJECT CREATION
        boolean isPaid = false;
        ItemDetail itemDetail = new ItemDetail("id", "name", 1, new BigDecimal("1.99"));
        List<ItemDetail> itemDetails = Arrays.asList(itemDetail);
        Order order = new Order(isPaid, itemDetails);

        // QR CODE GENERATION
        BufferedImage bufferedImage = qrCodeService.generateQRCodeImage(order);
        Order result = (Order) qrCodeService.decodeQRCodeImage(bufferedImage, Order.class);

        assertEquals(order.getIsPaid(), result.getIsPaid());
        assertEquals(order.getItemDetails().get(0).getId(), result.getItemDetails().get(0).getId());
        assertEquals(order.getItemDetails().get(0).getName(), result.getItemDetails().get(0).getName());
        assertEquals(order.getItemDetails().get(0).getUnits(), result.getItemDetails().get(0).getUnits());
        assertEquals(order.getItemDetails().get(0).getPrice(), result.getItemDetails().get(0).getPrice());
    }

    @Test
    public void testQrCode_writeToFile () throws Exception {
        // OBJECT CREATION
        boolean isPaid = false;
        ItemDetail itemDetail = new ItemDetail("id", null, 1, null);
        List<ItemDetail> itemDetails = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            itemDetails.add(itemDetail);
        }
        Order order = new Order(isPaid, itemDetails);

        // QR CODE GENERATION
        BufferedImage bufferedImage = qrCodeService.generateQRCodeImage(order);
        File outputfile = new File("tests/images/image.jpg");
        ImageIO.write(bufferedImage, "jpg", outputfile);
    }

}
