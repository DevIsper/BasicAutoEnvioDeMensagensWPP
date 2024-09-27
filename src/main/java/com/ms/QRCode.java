package com.ms;

import org.openqa.selenium.*;

public class QRCode {

    public static String capturaQRcode(WebDriver driver) {

        try {

            // Código para aguardar o QR code estar visível
            while (!QRCode.QRAppeared(driver)){
                try {
                    System.out.println("Aguardando o QRcode aparecer...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Esperar até que o canvas do QR code esteja visível
            WebElement qrCodeCanvas = driver.findElement(By.cssSelector("canvas[aria-label='Scan this QR code to link a device!']"));

            // Captura a imagem do QR code
            String qrCodeDataUrl = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].toDataURL('image/png')", qrCodeCanvas);

            // Extrair os dados da imagem em base64
            String base64Image = qrCodeDataUrl.split(",")[1]; // Remover a parte do prefixo

            return base64Image;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean QRAppeared(WebDriver driver) {
        try {
            // Verificar se o QRCode está visível.
            return driver.findElement(By.cssSelector("canvas[aria-label='Scan this QR code to link a device!']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}