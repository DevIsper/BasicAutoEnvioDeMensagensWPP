package com.ms.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AutomationUtil {

    public static boolean logou(WebDriver driver) {
        try {
            // Verificar se a caixa de pesquisa está visível após o login
            return driver.findElement(By.cssSelector("div.x9f619.x78zum5.x6s0dn4.x1s1d1n7.xqmdsaz.x1lj6vcq.x1bmedo.x1lq5wgf.xgqcy7u.x30kzoy.x9jhf4c")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void esperarCampoMensagemVisivel(WebDriver driver) throws InterruptedException {
        while (true) {
            try {
                // Usar contenteditable e role="textbox" como seletor para o campo de mensagem
                WebElement messageBox = driver.findElement(By.xpath("//div[@contenteditable='true' and @role='textbox']"));
                if (messageBox.isDisplayed()) {
                    break; // Campo de mensagem está visível, sair do loop
                }
            } catch (NoSuchElementException e) {
                // O elemento ainda não está presente
            }
            System.out.println("Aguardando campo de mensagem aparecer...");
            Thread.sleep(3000); // Espera 3 segundos antes de tentar novamente
        }
    }

    public static WebElement esperarBotaoAnexarClicavel(WebDriver driver) throws InterruptedException {
        while (true) {
            try {
                // Procurar o botão de anexar usando aria-label e role
                WebElement attachButton = driver.findElement(By.xpath("//div[@aria-label='Anexar' and @role='button']"));
                if (attachButton.isDisplayed() && attachButton.isEnabled()) {
                    System.out.println("Botão de anexar está visível e habilitado.");
                    return attachButton; // Botão encontrado, retornar
                } else {
                    System.out.println("Botão de anexar encontrado, mas não está visível ou habilitado.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Aguardando o botão de anexar aparecer...");
            }
            Thread.sleep(3000); // Espera 3 segundos antes de tentar novamente
        }
    }

    public static WebElement esperarInputArquivoPresente(WebDriver driver) throws InterruptedException {
        // Aguardar até o input de arquivo ser adicionado ao DOM
        int attempts = 0;
        while (attempts < 10) { // Limitar a 10 tentativas
            try {
                // Usar um XPath para localizar o input de arquivo que deve aparecer após clicar em "anexar"
                WebElement uploadInput = driver.findElement(By.xpath("//input[@type='file' and @accept='image/*,video/mp4,video/3gpp,video/quicktime']"));


                if (uploadInput != null) {
                        return uploadInput; // Retorna o input se estiver visível
                }
            } catch (NoSuchElementException e) {
                // O elemento ainda não está presente
            }
            System.out.println("Aguardando input de arquivo...");
            Thread.sleep(3000); // Esperar 3 segundos antes de tentar de novo
            attempts++;
        }
        throw new RuntimeException("O input de arquivo não apareceu após várias tentativas.");
    }

    public static void enviarListaArquivos(WebDriver driver, List<String> caminhoArquivo) throws InterruptedException {

        for (int i = 0; i < caminhoArquivo.size(); i++) {

            // Clicar no botão de anexar
            WebElement attachButton = esperarBotaoAnexarClicavel(driver);
            attachButton.click(); // Clica no botão de anexar
            System.out.println("Botão de anexar clicado!");

            // Esperar um tempo após o clique para o input de arquivo ser carregado
            Thread.sleep(AutomationUtil.randomTimeBetween(500, 5000));

            // Aguardar até o input de arquivo
            WebElement uploadInput = esperarInputArquivoPresente(driver);
            uploadInput.sendKeys(new File(caminhoArquivo.get(i)).getAbsolutePath()); // Envia o arquivo
            System.out.println("Arquivo carregado!");

            // Esperar até o botão de enviar a mensagem estar clicável
            esperarBotaoEnviarMensagemClicavel(driver);
            System.out.println("Arquivo enviado!");

            // Garantir que a imagem foi enviada
            Thread.sleep(AutomationUtil.randomTimeBetween(1000, 20000));
        }
    }


    public static void esperarBotaoEnviarMensagemClicavel(WebDriver driver) throws InterruptedException {
        while (true) {
            try {
                // Localiza o botão de enviar a imagem
                WebElement sendImageButton = driver.findElement(By.xpath("//div[@aria-label='Enviar']"));
                if (sendImageButton.isDisplayed() && sendImageButton.isEnabled()) {
                    sendImageButton.click(); // Clica no botão quando ele está visível e habilitado
                    System.out.println("Botão de enviar mensagem clicado!");
                    return; // Sai do método após clicar
                }
            } catch (NoSuchElementException e) {
                // O botão ainda não está presente
                System.out.println("Aguardando o botão de enviar aparecer...");
            }
            Thread.sleep(1000); // Espera 1 segundo antes de tentar novamente
        }
    }

    public static void enviarMensagem(WebDriver driver, String message) throws InterruptedException {


        WebElement messageBox = AutomationUtil.caixaDeTextoVisivel(driver);
        messageBox.sendKeys(message);

        Thread.sleep(AutomationUtil.randomTimeBetween(1000, 20000));

        WebElement sendButton = AutomationUtil.BotaoEnviarClicavel(driver);
        sendButton.click();
        Thread.sleep(AutomationUtil.randomTimeBetween(1000, 5000));
    }

    private static WebElement BotaoEnviarClicavel(WebDriver driver) {


        try {

            while (true) {
                if (driver.findElement(By.xpath("//button[@aria-label='Enviar']")).isDisplayed())
                    return driver.findElement(By.xpath("//button[@aria-label='Enviar']"));

                else {
                    System.out.println("Aguardando botão de enviar aparecer...");

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (NoSuchElementException e) {

        }
        return null;

    }

    private static WebElement caixaDeTextoVisivel(WebDriver driver) {


        try {

            while (true) {
                if (driver.findElement(By.xpath("//div[@role='textbox' and @aria-placeholder='Digite uma mensagem']")).isDisplayed())
                    return driver.findElement(By.xpath("//div[@role='textbox' and @aria-placeholder='Digite uma mensagem']"));

                else {
                    System.out.println("Aguardando caixa de texto aparecer...");

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (NoSuchElementException e) {

        }
        return null;

    }

    public static int randomTimeBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static Boolean isNumeroInvalido(WebDriver driver) {

        int max = 5;

        try {

            for (int i = 0; i < max; i++) {

                Thread.sleep(randomTimeBetween(4000, 10000));

                // Localiza o popup com a mensagem de "número de telefone inválido"
                WebElement popup = driver.findElement(By.xpath("//div[text()='O número de telefone compartilhado por url é inválido.']"));

                // Se o popup for encontrado e estiver visível, retorna true
                if (popup.isDisplayed()) {
                    System.out.println("Popup de número inválido detectado.");
                    return true;
                }
            }


        } catch (NoSuchElementException e) {

            // Se o popup não for encontrado, retorna false
            return false;
        } catch (InterruptedException e) {

        }

        return false;
    }


    public static void clicarNoBotaoOk(WebDriver driver) {
        try {
            // Localiza o botão "OK" dentro do popup

//            WebElement botaoOk = driver.findElement(By.xpath("//button//div[text()='OK']"));

            WebElement botaoOk = driver.findElement(By.xpath("//button[.//div[text()='OK']]"));


            // Verifica se o botão está visível e clica
            if (botaoOk.isDisplayed()) {
                botaoOk.click();
                System.out.println("Botão OK foi clicado.");
            }
        } catch (NoSuchElementException e) {
            // Se o botão "OK" não for encontrado
            System.out.println("Botão OK não foi encontrado.");
        }
    }

}
