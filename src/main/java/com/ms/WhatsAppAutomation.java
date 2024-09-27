package com.ms;

import com.ms.util.AutomationUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class WhatsAppAutomation {

    public static void main(String[] args) {

        // Configura o WebDriver Manager para baixar e configurar o ChromeDriver automaticamente
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Abre o WhatsApp Web
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


            // Retorna mensagem de erro por causa de número inválido.
//            List<String> phoneNumbers = List.of("+5518998150000"); // Substitua pelos números reais

            // Mensagem a ser enviada
//            String message = "🎓 **Atenção, estudantes!** 🎓 %0A Não perca essa oportunidade incrível! %0A Estamos com *descontos especiais* em todos os nossos cursos para quem se matricular até o final deste mês! %0A **Garanta já sua vaga** e dê o próximo passo na sua carreira. %0A Entre em contato agora e saiba mais sobre nossos planos de pagamento facilitado! %0A *O futuro começa agora!* 🚀";

            // Mensagem sem emojis, pois deu o erro: org.openqa.selenium.WebDriverException: unknown error: ChromeDriver only supports characters in the BMP
            String message = "**Atenção, estudantes!** %0A Não perca essa oportunidade incrível! %0A Estamos com *descontos especiais* em todos os nossos cursos para quem se matricular até o final deste mês! %0A **Garanta já sua vaga** e dê o próximo passo na sua carreira. %0A Entre em contato agora e saiba mais sobre nossos planos de pagamento facilitado! %0A *O futuro começa agora!*";


            // Caminho da(s) imagem(s) a ser(em) enviada(s)
            List<String> imagePaths = List.of("/Users/DevIsper/Downloads/cat.jpg", "/Users/DevIsper/Downloads/cat2.jpg");

            // Loop para enviar mensagens para a lista de números
            for (String phoneNumber : phoneNumbers) {
                try {
                    // Abra a URL do WhatsApp com o número de telefone
                    driver.get("https://web.whatsapp.com/send?phone=" + phoneNumber);
                    System.out.println("Buscando o número: " + phoneNumber);

                    // Esperar até o campo de mensagem aparecer
                    AutomationUtil.esperarCampoMensagemVisivel(driver);
                    System.out.println("Campo de mensagem visível!");


                    // Chama o método para fazer o upload da imagem
                    AutomationUtil.enviarListaArquivos(driver, imagePaths);

                    // Envia a mensagem
                    AutomationUtil.enviarMensagem(driver, message);


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
