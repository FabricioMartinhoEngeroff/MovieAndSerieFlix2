package com.DvFabricio.movieFlix2.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
public class QueryChatgpt {

    public static String getTranslation(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_KEY"));


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

    try {
        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    } catch (
    OpenAiHttpException e) {
        if (e.getMessage().contains("You exceeded your current quota")) {
            System.err.println("Quota exceeded. Please check your plan and billing details.");
            return "Erro: Quota excedida.";
        } else {
            throw e;
        }
    }
}


}
