package app;

import com.google.zxing.WriterException;
import com.itextpdf.text.BadElementException;
import services.QRCodeManager;

import java.io.IOException;

public class QRCodeApp {
    public static void main(String[] args) throws BadElementException, IOException, WriterException {
        QRCodeManager manager = new QRCodeManager();
        String qrdata="Test";

        byte[] im=manager.createQrcode(qrdata,"QR_CODE");
        manager.writeToPdf(im);
        manager.saveToIpg(qrdata,"test","QR_CODE");
    }
}
