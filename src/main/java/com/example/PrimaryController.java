package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PrimaryController {

    @FXML FlowPane flow;

    public void carregar () throws IOException{
       try {
        // var url = new URL("https://api.dicebear.com/7.x/pixel-art/svg");
        // foto com varias vvvv (ta dando erro)
        //  var url = new URL("https://api.waifu.pics/many/sfw/shinobu");
        // com uma foto  vvvv
          var url = new URL("https://api.waifu.pics/sfw/cuddle");
        var con = url.openConnection();
        con.connect();
        var is = con.getInputStream();

        var reader = new BufferedReader(new InputStreamReader(is));
        var json = reader.readLine();

        var lista =jsonParaLista(json);

        mostraFoto(lista);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        mostraMensagem("erro:"+e.getMessage());
    }
    }

    public void mostraFoto(List<Foto> lista) {
        lista.forEach(fotos->{
            var imagem = new ImageView(new Image(fotos.getUrl()));
            flow.getChildren().add(new VBox(imagem));

        });
    }

    private List<Foto> jsonParaLista(String json) throws JsonMappingException, JsonProcessingException {
        var mapper = new ObjectMapper();
        // com uma foto vv
         var files = mapper.readTree(json);
        List<Foto> lista = new ArrayList<>();
    //    foto com varias (ta dando erro)
        //  var files = mapper.readTree(json).get("files");
        

        // tentar fazer no for para pegar varias fotos
    files.forEach(fotos->{
        try {
            lista.add(mapper.readValue(json,Foto.class));
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    });

        return lista;
    }

    private void mostraMensagem (String m){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(m);
        alert.show();

    }
}
