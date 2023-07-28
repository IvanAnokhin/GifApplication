package com.example.demo6;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GifApplication extends Application {

  private static final String API_BASE_URL = "https://api.giphy.com/v1/gifs/search";
  private static final String API_KEY = "DE2i0hGcLJPibVh7GJLw5MUJINymn01p";
  private VBox root;
  private TextArea instructionTextArea;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    root = new VBox(10);
    root.setPadding(new Insets(20));
    Scene scene = new Scene(root, 1920, 900);

    instructionTextArea = new TextArea();
    instructionTextArea.setEditable(false);
    instructionTextArea.setWrapText(true);
    instructionTextArea.setText("Инструкция по использованию приложения:\n\n" +
        "1. Введите слово или фразу на английском языке для поиска GIF.\n" +
        "2. Нажмите кнопку \"Поиск\" или клавишу Enter.\n" +
        "3. После выполнения поиска, отобразятся случайные GIF-изображения, связанные с вашим запросом.\n" +
        "4. Для сохранения GIF-изображения, нажмите кнопку \"Save\" под GIF-изображением.\n" +
        "5. Выберите папку для сохранения GIF-изображения на вашем компьютере.\n\n" +
        "Приятного использования!");

    TextField searchTextField = new TextField();
    searchTextField.setPromptText("Введите слово для поиска GIF");
    ImageView gifImageView = new ImageView();
    Label messageLabel = new Label();

    FlowPane gifPane = new FlowPane(10, 10);
    gifPane.setPadding(new Insets(10));
    gifPane.setPrefWrapLength(100); // Ширина панели для GIF-изображений

    ScrollPane scrollPane = new ScrollPane(gifPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    Button searchButton = new Button("Поиск");
    searchButton.setOnAction(e -> {
      performSearch(searchTextField.getText().trim(), gifPane, messageLabel);
      hideInstruction();
    });

    // Обработка нажатия клавиши Enter
    searchTextField.setOnAction(e -> {
      performSearch(searchTextField.getText().trim(), gifPane, messageLabel);
      hideInstruction();
    });

    root.getChildren().addAll(instructionTextArea, searchTextField, searchButton, scrollPane, messageLabel);
    primaryStage.setTitle("Поиск GIF");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void hideInstruction() {
    // Удаляем инструкцию из сцены
    root.getChildren().remove(instructionTextArea);
  }


  private void performSearch(String searchTerm, FlowPane gifPane, Label messageLabel) {
    gifPane.getChildren().clear();
    messageLabel.setText("");

    if (!searchTerm.isEmpty()) {
      List<String> gifUrls = searchGIF(searchTerm, 10); // Максимум 5 GIF-изображений
      if (!gifUrls.isEmpty()) {
        Collections.shuffle(gifUrls);
        for (String gifUrl : gifUrls) {
          ImageView imageView = new ImageView(new Image(gifUrl));
          Button saveButton = createSaveButton(gifUrl);

          VBox gifBox = new VBox(imageView, saveButton);
          gifBox.setSpacing(5);
          gifPane.getChildren().add(gifBox);
        }
        messageLabel.setText("Результаты по запросу \"" + searchTerm + "\":");
      } else {
        messageLabel.setText("GIF не найден для запроса \"" + searchTerm + "\"");
      }
    } else {
      messageLabel.setText("Введите слово для поиска GIF");
    }
  }

  private Button createSaveButton(String gifUrl) {
    Button saveButton = new Button("Save");
    saveButton.setOnAction(e -> saveGIF(gifUrl));
    return saveButton;
  }
  private void showContextMenu(javafx.scene.input.ContextMenuEvent event, String gifUrl) {
    javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();
    MenuItem saveMenuItem = new MenuItem("Save");
    saveMenuItem.setOnAction(e -> saveGIF(gifUrl));
    contextMenu.getItems().addAll(saveMenuItem);
    contextMenu.show(((ImageView) event.getSource()).getScene().getWindow(), event.getScreenX(), event.getScreenY());
  }


  private List<String> searchGIF(String searchTerm, int limit) {
    List<String> gifUrls = new ArrayList<>();
    try {
      URL url = new URL(API_BASE_URL + "?api_key=" + API_KEY + "&q=" + searchTerm + "&limit=" + limit);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = in.readLine();
        in.close();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonArray data = jsonObject.getAsJsonArray("data");
        if (data != null && data.size() > 0) {
          for (JsonElement element : data) {
            JsonObject gifObject = element.getAsJsonObject();
            JsonObject imagesObject = gifObject.getAsJsonObject("images");
            JsonObject originalImageObject = imagesObject.getAsJsonObject("original");
            String gifUrl = originalImageObject.get("url").getAsString();
            gifUrls.add(gifUrl);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Collections.shuffle(gifUrls);
    return gifUrls;
  }

  private void saveGIF(String gifUrl) {
    try {
      URL url = new URL(gifUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить GIF");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
          try (InputStream in = connection.getInputStream();
               FileOutputStream out = new FileOutputStream(selectedFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
              out.write(buffer, 0, bytesRead);
            }
            System.out.println("GIF saved: " + selectedFile.getAbsolutePath());
          }
        }
      } else {
        System.out.println("Failed to save GIF: " + gifUrl);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}






