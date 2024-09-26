package com.ms;

import com.ms.util.AutomationUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;

public class WhatsAppAutomation {

    public static void main(String[] args) {
        // Configura o WebDriver Manager para baixar e configurar o ChromeDriver automaticamente
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Abra o WhatsApp Web
            driver.get("https://web.whatsapp.com/");

            // Espera até que o login seja efetuado
            while (!AutomationUtil.logou(driver)) {
                try {
                    System.out.println("Aguardando login...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

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
                    System.out.println("Buscando o número: " + phoneNumber);

                    // Esperar até o campo de mensagem aparecer
                    AutomationUtil.esperarCampoMensagemVisivel(driver);
                    System.out.println("Campo de mensagem visível!");

                    // Chama o método para fazer o upload da imagem
                    AutomationUtil.enviarArquivo(driver, imagePath);

                    // Garantir que a imagem foi enviada
                    Thread.sleep(5000);

                } catch (Exception e) {
                    System.out.println("Erro ao enviar mensagem para o número: " + phoneNumber);
                    e.printStackTrace();
                }
            }

        } finally {
            // Fechar o navegador
            driver.quit();
        }
    }
}
