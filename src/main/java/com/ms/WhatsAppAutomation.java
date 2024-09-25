package com.ms;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WhatsAppAutomation {

    public static void main(String[] args) {
        // Configura o WebDriver Manager para baixar e configurar o ChromeDriver automaticamente
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        // Abra o WhatsApp Web
        driver.get("https://web.whatsapp.com/");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); // Tempo para fazer o login manualmente

        // Lista de números para os quais enviar mensagens
        List<String> phoneNumbers = List.of("+5518998153253"); // Substitua pelos números reais

        // Mensagem a ser enviada
        String message = "Funfooooooooooowwww!!!!";

        // Caminho da imagem a ser enviada
        String imagePath = "/Users/DevIsper/Downloads/lula.jpg"; // Substitua pelo caminho da sua imagem

        // Loop para enviar mensagens para a lista de números
        for (String phoneNumber : phoneNumbers) {
            try {
                // Abra a URL do WhatsApp com o número de telefone
                driver.get("https://web.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message);

                // Esperar até o campo de mensagem aparecer
                TimeUnit.SECONDS.sleep(15); // Pode ser ajustado conforme a conexão

//                // Localize o campo de mensagem e envie a mensagem
//                WebElement messageBox = driver.findElement(By.xpath("//div[@title='Mensagem']"));
//                messageBox.sendKeys(message);
//                messageBox.sendKeys(Keys.RETURN); // Pressiona Enter para enviar a mensagem

                // Enviar imagem
                WebElement attachButton = driver.findElement(By.cssSelector("div[title='Attach'][aria-label='Attach']"));
                attachButton.click();
                TimeUnit.SECONDS.sleep(1);

                // Enviar imagem do disco local
                WebElement uploadInput = driver.findElement(By.cssSelector("input[multiple][type='file']"));
                uploadInput.sendKeys(new File(imagePath).getAbsolutePath());

                // Esperar um pouco para que a imagem seja carregada
                TimeUnit.SECONDS.sleep(5);

                // Clicar no botão de envio
                WebElement sendImageButton = driver.findElement(By.xpath("//span[@data-icon='send']"));
                sendImageButton.click();

                // Esperar para garantir que a mensagem foi enviada
                TimeUnit.SECONDS.sleep(5);

            } catch (Exception e) {
                System.out.println("Erro ao enviar mensagem para o número: " + phoneNumber);
                e.printStackTrace();
            }
        }

        // Fechar o navegador
//        driver.quit();
    }
}

