package com.example.lab5_2;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "index2")
public class MyView2 extends HorizontalLayout {
    private TextField addWord, addSentence;
    private TextArea goodSentences, badSentences;
    private Button addGoodWord, addBadWord, addSen, showSen;
    private ComboBox<String> goodWords, badWords;
    private VerticalLayout v1, v2;

    private Word words = new Word();

    public MyView2(){
        addWord = new TextField();
        addWord.setLabel("Add Word");
        addWord.setWidthFull();
        addSentence = new TextField();
        addSentence.setLabel("Add Sentence");
        addSentence.setWidthFull();
        goodSentences = new TextArea();
        goodSentences.setLabel("Good Sentences");
        goodSentences.setWidthFull();
        badSentences = new TextArea();
        badSentences.setLabel("Bad Sentences");
        badSentences.setWidthFull();
        addGoodWord = new Button("Add Good Word");
        addGoodWord.setWidthFull();
        addBadWord = new Button("Add Bad Word");
        addBadWord.setWidthFull();
        addSen = new Button("Add Sentence");
        addSen.setWidthFull();
        showSen = new Button("Show Sentence");
        showSen.setWidthFull();
        goodWords = new ComboBox<>("Good Words");
        goodWords.setWidthFull();
        goodWords.setItems(words.goodWords);
        badWords = new ComboBox<>("Bad Words");
        badWords.setWidthFull();
        badWords.setItems(words.badWords);
        v1 = new VerticalLayout(addWord, addGoodWord, addBadWord, goodWords, badWords);
        v2 = new VerticalLayout(addSentence, addSen, goodSentences, badSentences, showSen);
        add(v1, v2);

        addGoodWord.addClickListener(event -> {
            String input = addWord.getValue();
            ArrayList<String> out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/" + input)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            new Notification("Insert " + input + " to Good Word Lists Complete.", 2000).open();
            goodWords.setItems(out);
        });

        addBadWord.addClickListener(event -> {
            String input = addWord.getValue();
            ArrayList<String> out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/" + input)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            new Notification("Insert " + input + " to Bad Word Lists Complete.", 2000).open();
            badWords.setItems(out);
        });

        addSen.addClickListener(event -> {
            String input = addSentence.getValue();
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/" + input)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            new Notification(out, 2000).open();
        });

        showSen.addClickListener(event -> {
            Sentence sens = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            goodSentences.setValue(sens.goodSentences + "");
            badSentences.setValue(sens.badSentences + "");
        });
    }
}
