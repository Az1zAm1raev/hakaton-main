package com.example.hakaton.util.bot;

import com.example.hakaton.config.BotConfig;
import com.example.hakaton.service.entity.DocumentService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final DocumentService documentService;

    @Autowired
    public TelegramBot(BotConfig botConfig, DocumentService documentService) {
        this.botConfig = botConfig;
        this.documentService = documentService;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
//            String caption = getCaption(content);

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    try {
                        final PDDocument document = documentService.export(messageText);
                        final File file = File.createTempFile("result", ".pdf");
                        document.save(file);
                        InputFile inputFile = new InputFile(file);
                        sendDocument(chatId, inputFile);
                    } catch (Exception e) {
                    }
            }
        }

    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Приветствуем, " + name + ", добро пожаловать на портал!" + "\n" +
                        "Здесь вы сможете просматривать ваши инструментальные исследования," + "\n" +
                        "проведенные в частных клиниках, лабораториях и почих ОЗ" + "\n" +
                        "Для получения инструментальные исследования пожалуйста введите его id" + "\n" +
                        "Для примера:" + "\n" +
                        "9863b482-6055-4aaa-864b-8505f98722c5";

        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        }
    }

    private void sendDocument(Long chatId, InputFile save) {
        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(chatId);
        sendDocumentRequest.setDocument(save);
//        sendDocumentRequest.setCaption(caption);
        try {
            execute(sendDocumentRequest);
        } catch (Exception e) {
        }
    }
}