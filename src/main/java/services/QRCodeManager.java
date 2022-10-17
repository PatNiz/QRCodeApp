package services;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import com.google.zxing.common.BitMatrix;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
public class QRCodeManager {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public  byte[] createQrcode(String qrCodeData, String type) throws WriterException, IOException, BadElementException {
        String charset = "UTF-8";
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        // Generate BitMatrix
        BitMatrix matrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.valueOf(type), 300, 300,hints);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Get QR code
        BufferedImage bi = MatrixToImageWriter.toBufferedImage(matrix);
        ImageIO.write(bi, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
    public BufferedImage getBufferredImageLoop(BitMatrix matrix){
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;

    }
    public BufferedImage getBufferredImage(BitMatrix matrix) {
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
    public void writeToPdf(byte[] imageCode){
        Document document = new Document();
        try {
            // Get file
            PdfWriter.getInstance(document, new FileOutputStream("qrcode.pdf"));
            document.open();
            String qrdata = "Patryk Niziolek";
            String charset = "UTF-8"; // or "ISO-8859-1"
            String filePath="qrcode.pdf";
            Image image = Image.getInstance(imageCode);
            // Add image to PDF document
            document.add(image);
            document.close();
            System.out.println(new File(filePath).getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void saveToIpg(String qrdata,String fileName,String type) throws WriterException, IOException {

        int width = 300;
        int height = 300;
        String suffix = "jpg";
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        //CODE_128 // QR_CODE
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrdata, BarcodeFormat.valueOf(type), width, height, hints);
        Path path = new File(fileName+".jpg").toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, suffix, path);

    }
}
