package com.ms.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

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
//            try {
                // Usar um XPath para localizar o input de arquivo que deve aparecer após clicar em "anexar"
            WebElement uploadInput = driver.findElement(By.cssSelector("input[multiple][type='file']"));
            if (uploadInput != null) {
                    return uploadInput; // Retorna o input se estiver visível
                }
//            } catch (NoSuchElementException e) {
//                // O elemento ainda não está presente
//            }
            System.out.println("Aguardando input de arquivo...");
            Thread.sleep(3000); // Esperar 3 segundos antes de tentar de novo
            attempts++;
        }
        throw new RuntimeException("O input de arquivo não apareceu após várias tentativas.");
    }

    public static void enviarArquivo(WebDriver driver, String caminhoArquivo) throws InterruptedException {
        // Clicar no botão de anexar
        WebElement attachButton = esperarBotaoAnexarClicavel(driver);
        attachButton.click(); // Clica no botão de anexar
        System.out.println("Botão de anexar clicado!");

        // Esperar um tempo após o clique para o input de arquivo ser carregado
        Thread.sleep(3000); // Pode ajustar esse tempo conforme necessário

        // Aguardar até o input de arquivo
        WebElement uploadInput = esperarInputArquivoPresente(driver);
        uploadInput.sendKeys(new File(caminhoArquivo).getAbsolutePath()); // Envia o arquivo
        System.out.println("Arquivo carregado!");

        // Esperar até o botão de enviar a mensagem estar clicável
        esperarBotaoEnviarMensagemClicavel(driver);
        System.out.println("Arquivo enviado!");
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
}
