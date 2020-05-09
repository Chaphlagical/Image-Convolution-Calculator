/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dip;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.exp;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author 41819
 */
public class setPane{

    /**
     *index: index of operation 
     * index=0:convolution
     * index=1:median filter
     */
    public int color_mode=0;//0:RGB,1:gray
    public int option=0;//0:convolution
    public int index=0; 
    public int SIZE;
    public float[][] kernel={{}};
    public String file_path;
    public Image img;//original image
    ImageView imageView = new ImageView();
    WritableImage image;//the processing image
    FileChooser fileChooser = new FileChooser();
    Stage filestage=new Stage();
    Button filebutton=new Button("Choose file");
    DIP dip=new DIP();
    String s=array2string(kernel);
    Text category = new Text(s);
    
    ChoiceBox<Object> grad = new ChoiceBox<>();
    ChoiceBox<Object> blur = new ChoiceBox<>();
    ChoiceBox<Object> color = new ChoiceBox<>(); 
    
    
    /**
     * kernel
     */
     
    public HBox addHBox(){
        HBox hbox=new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10)); //节点到边缘的距离
        hbox.setSpacing(13); //节点之间的间距
       
/**
 * Button
 */
        Button btn1 = new Button("set kernel");
        btn1.setPrefSize(100, 20);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextArea textarea=new TextArea();
                Stage stage_text=new Stage();
                stage_text.setTitle("setting");
                Button btn_text1=new Button("Yes");
                Button btn_text2=new Button("Cancel");
                btn_text1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try{
                            grad.getSelectionModel().select(0);
                            kernel=string2array(textarea.getText());
                            if(kernel.length%2==1&&kernel[0].length%2==1&&kernel.length>1){
                                if (kernel.length<5&&kernel[0].length<5){
                                    stage_text.close();
                                    s=array2string(kernel);
                                    category.setText(s);
                                }
                                else{
                                    category.setText("kernel size:"+String.valueOf(kernel.length)+"*"+String.valueOf(kernel[0].length));
                                }
                            }
                            else{
                                NegativeArraySizeException error1=new NegativeArraySizeException();
                                throw error1;
                            }
                        }
                    catch(Exception exception){
                        Alert alert_text = new Alert(Alert.AlertType.WARNING);
                        alert_text.setTitle("Warning");
                        alert_text.setHeaderText("Input warning!");
                        alert_text.setContentText("Please Check your input");
                        alert_text.showAndWait();
                    }
                   }
                });
            btn_text2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage_text.close();
                    }
                });
                        
                VBox vbox_text=new VBox();
                BorderPane Border_text=new BorderPane();
                vbox_text.getChildren().add(textarea);
                Border_text.setLeft(btn_text1);
                Border_text.setRight(btn_text2);
                vbox_text.getChildren().add(Border_text);
                Scene scene2=new Scene(vbox_text,160,160);
                stage_text.setScene(scene2);
                stage_text.show();

            }
        });
        Button btn2 = new Button("Start");
        btn2.setPrefSize(100, 20);
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    switch(option){
                        case 0: image=dip.convolution(image,kernel,color_mode);break;
                        case 1: image=dip.Median(image, SIZE, color_mode);break;
                    }
                    dip.Img_View(image,imageView);
                }
                catch(Exception exception){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Calculate Error!");
                    alert.setContentText("Error occur! Please check!");
                    alert.showAndWait();
                }
            }
        });
        
/*
 *ChoiceBox grad
*/
        grad.setItems(FXCollections.observableArrayList(
                            "Differential",new Separator(),  
                            "Sobel_X","Sobel_Y",new Separator(), 
                            "Roberts_x","Roberts_y",new Separator(), 
                            "Prewitt_x","Prewitt_y",new Separator(),
                            "Kirsch_N","Kirsch_NE","Kirsch_E","Kirsch_SE",
                            "Kirsch_S","Kirsch_SW","Kirsch_W","Kirsch_NW",new Separator(),
                            "Robinson_N","Robinson_NE","Robinson_E","Robinson_SE",
                            "Robinson_S","Robinson_SW","Robinson_W","Robinson_NW")
                        );

        grad.getSelectionModel().select(0);
	grad.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number oldv, Number newv) -> {
                    option=0;
                    blur.getSelectionModel().select(0);
                    switch(newv.intValue()){
                        case 0: kernel=new float[0][0];
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 2: kernel=new float[3][3];
                                kernel[0][0]=-1;kernel[0][1]=0;kernel[0][2]=1;
                                kernel[1][0]=-2;kernel[1][1]=0;kernel[1][2]=2;
                                kernel[2][0]=-1;kernel[2][1]=0;kernel[2][2]=1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 3: kernel=new float[3][3];
                                kernel[0][0]=1;kernel[0][1]=2;kernel[0][2]=1;
                                kernel[1][0]=0;kernel[1][1]=0;kernel[1][2]=0;
                                kernel[2][0]=-1;kernel[2][1]=-2;kernel[2][2]=-1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 5: kernel=new float[2][2];
                                kernel[0][0]=1;kernel[0][1]=0;
                                kernel[1][0]=0;kernel[1][1]=-1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;        
                        case 6: kernel=new float[2][2];
                                kernel[0][0]=0;kernel[0][1]=-1;
                                kernel[1][0]=1;kernel[1][1]=0;
                                s=array2string(kernel);
                                category.setText(s);
                                break;             
                        case 8: kernel=new float[3][3];
                                kernel[0][0]=-1;kernel[0][1]=0;kernel[0][2]=1;
                                kernel[1][0]=-1;kernel[1][1]=0;kernel[1][2]=1;
                                kernel[2][0]=-1;kernel[2][1]=0;kernel[2][2]=1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 9: kernel=new float[3][3];
                                kernel[0][0]=1;kernel[0][1]=1;kernel[0][2]=1;
                                kernel[1][0]=0;kernel[1][1]=0;kernel[1][2]=0;
                                kernel[2][0]=-1;kernel[2][1]=-1;kernel[2][2]=-1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 11:kernel=new float[3][3];
                                kernel[0][0]=5;kernel[0][1]=5;kernel[0][2]=5;
                                kernel[1][0]=-3;kernel[1][1]=0;kernel[1][2]=3;
                                kernel[2][0]=-3;kernel[2][1]=-3;kernel[2][2]=-3;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 12:kernel=new float[3][3];
                                kernel[0][0]=-3;kernel[0][1]=5;kernel[0][2]=5;
                                kernel[1][0]=-3;kernel[1][1]=0;kernel[1][2]=5;
                                kernel[2][0]=-3;kernel[2][1]=-3;kernel[2][2]=-3;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 13:kernel=new float[3][3];
                                kernel[0][0]=-3;kernel[0][1]=-3;kernel[0][2]=5;
                                kernel[1][0]=-3;kernel[1][1]=0;kernel[1][2]=5;
                                kernel[2][0]=-3;kernel[2][1]=-3;kernel[2][2]=5;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 14:kernel=new float[3][3];
                                kernel[0][0]=-3;kernel[0][1]=-3;kernel[0][2]=-3;
                                kernel[1][0]=-3;kernel[1][1]=0;kernel[1][2]=5;
                                kernel[2][0]=-3;kernel[2][1]=5;kernel[2][2]=5;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 15:kernel=new float[3][3];
                                kernel[0][0]=-3;kernel[0][1]=-3;kernel[0][2]=-3;
                                kernel[1][0]=-3;kernel[1][1]=0;kernel[1][2]=-3;
                                kernel[2][0]=5;kernel[2][1]=5;kernel[2][2]=5;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 16:kernel=new float[3][3];
                                kernel[0][0]=-3;kernel[0][1]=-3;kernel[0][2]=-3;
                                kernel[1][0]=5;kernel[1][1]=0;kernel[1][2]=-3;
                                kernel[2][0]=5;kernel[2][1]=5;kernel[2][2]=-3;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 17:kernel=new float[3][3];
                                kernel[0][0]=5;kernel[0][1]=-3;kernel[0][2]=-3;
                                kernel[1][0]=5;kernel[1][1]=0;kernel[1][2]=-3;
                                kernel[2][0]=5;kernel[2][1]=-3;kernel[2][2]=-3;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 18:kernel=new float[3][3];
                                kernel[0][0]=5;kernel[0][1]=5;kernel[0][2]=-3;
                                kernel[1][0]=5;kernel[1][1]=0;kernel[1][2]=-3;
                                kernel[2][0]=-3;kernel[2][1]=-3;kernel[2][2]=-3;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 20:kernel=new float[3][3];
                                kernel[0][0]=1;kernel[0][1]=2;kernel[0][2]=1;
                                kernel[1][0]=0;kernel[1][1]=0;kernel[1][2]=0;
                                kernel[2][0]=-1;kernel[2][1]=-2;kernel[2][2]=-1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 21:kernel=new float[3][3];
                                kernel[0][0]=0;kernel[0][1]=1;kernel[0][2]=2;
                                kernel[1][0]=-1;kernel[1][1]=0;kernel[1][2]=1;
                                kernel[2][0]=-2;kernel[2][1]=-1;kernel[2][2]=0;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 22:kernel=new float[3][3];
                                kernel[0][0]=-1;kernel[0][1]=0;kernel[0][2]=1;
                                kernel[1][0]=-2;kernel[1][1]=0;kernel[1][2]=2;
                                kernel[2][0]=-1;kernel[2][1]=0;kernel[2][2]=1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 23:kernel=new float[3][3];
                                kernel[0][0]=-2;kernel[0][1]=-1;kernel[0][2]=0;
                                kernel[1][0]=-1;kernel[1][1]=0;kernel[1][2]=1;
                                kernel[2][0]=0;kernel[2][1]=1;kernel[2][2]=2;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 24:kernel=new float[3][3];
                                kernel[0][0]=-1;kernel[0][1]=-2;kernel[0][2]=-1;
                                kernel[1][0]=0;kernel[1][1]=0;kernel[1][2]=0;
                                kernel[2][0]=1;kernel[2][1]=2;kernel[2][2]=1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 25:kernel=new float[3][3];
                                kernel[0][0]=0;kernel[0][1]=-1;kernel[0][2]=-2;
                                kernel[1][0]=1;kernel[1][1]=0;kernel[1][2]=-1;
                                kernel[2][0]=2;kernel[2][1]=1;kernel[2][2]=0;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 26:kernel=new float[3][3];
                                kernel[0][0]=1;kernel[0][1]=0;kernel[0][2]=-1;
                                kernel[1][0]=2;kernel[1][1]=0;kernel[1][2]=-2;
                                kernel[2][0]=1;kernel[2][1]=0;kernel[2][2]=-1;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                        case 27:kernel=new float[3][3];
                                kernel[0][0]=2;kernel[0][1]=1;kernel[0][2]=0;
                                kernel[1][0]=1;kernel[1][1]=0;kernel[1][2]=-1;
                                kernel[2][0]=-0;kernel[2][1]=-1;kernel[2][2]=-2;
                                s=array2string(kernel);
                                category.setText(s);
                                break;
                    }
                    
        });
	grad.setTooltip(new Tooltip("Differential"));
        blur.setItems(FXCollections.observableArrayList(
                            "Blur",new Separator(),"Mean","Median","Gaussian")
                        );
       
        blur.getSelectionModel().select(0);
	blur.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number oldv, Number newv) -> {
                    grad.getSelectionModel().select(0);
                    kernel=new float[1][0];
                    s=array2string(kernel);
                    category.setText(s);
                    System.out.println(newv.intValue());
                    switch(newv.intValue()){
                        case 0: kernel=new float[0][0];
                                s=array2string(kernel);
                                category.setText(s);break;
                        case 2: option=0;
                                Stage stage1=new Stage();
                                stage1.setTitle("Setting");
                                GridPane grid1=new GridPane();
                                grid1.setVgap(2);
                                grid1.setHgap(2);
                                grid1.setPadding(new Insets(5, 5, 5, 5));
                                TextField textfield1=new TextField();
                                textfield1.setPrefColumnCount(2);
                                Button button1=new Button("Yes");
                                Button button2=new Button("Cancel");
                                button1.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                int size=Integer.parseInt(textfield1.getText());
                                                if(size%2==1){
                                                    kernel=new float[size][size];
                                                    for(int i=0;i<size;i++)
                                                        for(int j=0;j<size;j++)
                                                            kernel[i][j]=(float)1.0/(size*size);
                                                    category.setText("Mean Filter\nkernel size:"+String.valueOf(size));
                                                    stage1.close();
                                                }
                                                else{
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setTitle("Warning");
                                                    alert.setHeaderText("Input warning!");
                                                    alert.setContentText("Please input odd number");
                                                    alert.showAndWait();
                                                }
                                            }
                                        });
                                button2.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                blur.getSelectionModel().select(0);
                                                stage1.close();
                                            }
                                        });
                                
                                grid1.add(new Label("Set Kernel Size:"),0,0);
                                
                                grid1.add(button1,0,1);
                                grid1.add(button2,1,1);
                                grid1.add(textfield1,1,0);
                                Scene scene2=new Scene(grid1,160,60);
                                stage1.setScene(scene2);
                                stage1.show();
                                break;
                        case 3: option=1;
                                Stage stage2=new Stage();
                                stage2.setTitle("Setting");
                                GridPane grid2=new GridPane();
                                grid2.setVgap(2);
                                grid2.setHgap(2);
                                grid2.setPadding(new Insets(5, 5, 5, 5));
                                TextField textfield2=new TextField();
                                textfield2.setPrefColumnCount(2);
                                Button button3=new Button("Yes");
                                Button button4=new Button("Cancel");
                                button3.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                SIZE=Integer.parseInt(textfield2.getText());
                                                index=1;
                                                category.setText("Median Filter \nkernel size:"+String.valueOf(SIZE));
                                                stage2.close();
                                            }
                                        });
                                button4.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                blur.getSelectionModel().select(0);
                                                stage2.close();
                                            }
                                        });
                                
                                grid2.add(new Label("Set Kernel Size:"),0,0);
                                grid2.add(button3,0,1);
                                grid2.add(button4,1,1);
                                grid2.add(textfield2,1,0);
                                Scene scene3=new Scene(grid2,160,60);
                                stage2.setScene(scene3);
                                stage2.show();
                                break;
                        case 4: option=0;
                                Stage stage3=new Stage();
                                stage3.setTitle("Setting");
                                GridPane grid3=new GridPane();
                                grid3.setVgap(2);
                                grid3.setHgap(2);
                                grid3.setPadding(new Insets(5, 5, 5, 5));
                                TextField textfield3=new TextField();
                                textfield3.setPrefColumnCount(2);
                                TextField textfield4=new TextField();
                                textfield3.setPrefColumnCount(5);
                                Button button5=new Button("Yes");
                                Button button6=new Button("Cancel");
                                button5.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                int size=Integer.parseInt(textfield3.getText());
                                                float sigma=Float.parseFloat(textfield4.getText());
                                                
                                                if(size%2==1){
                                                    kernel=new float[size][size];
                                                    int mid=size/2;
                                                    float sum=0;
                                                    for(int i=0;i<size;i++)
                                                        for(int j=0;j<size;j++){
                                                            kernel[i][j]=(float)exp(-((i-mid)*(i-mid)+(j-mid)*(j-mid))/(2*sigma*sigma));
                                                            sum+=kernel[i][j];
                                                        }
                                                    for(int i=0;i<size;i++)
                                                        for(int j=0;j<size;j++)
                                                            kernel[i][j]/=sum;
                                                    category.setText("Mean Filter\nkernel size:"+String.valueOf(size)+"\nSigma="+String.valueOf(sigma));
                                                    stage3.close();
                                                }
                                                else{
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setTitle("Warning");
                                                    alert.setHeaderText("Input warning!");
                                                    alert.setContentText("Please input odd number");
                                                    alert.showAndWait();
                                                }
                                            }
                                        });
                                button6.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                blur.getSelectionModel().select(0);
                                                stage3.close();
                                            }
                                        });
                                
                                BorderPane border_guassian=new BorderPane();
                                border_guassian.setLeft(button5);
                                border_guassian.setRight(button6);
                                grid3.add(new Label("Set Size:"),0,0);
                                grid3.add(new Label("Set Sigma:"),0,1);
                                grid3.add(textfield3,1,0);
                                grid3.add(textfield4,1,1);
                                grid3.add(border_guassian,1,2);
                                Scene scene4=new Scene(grid3,250,90);
                                stage3.setScene(scene4);
                                stage3.show();
                                break;
                    }    
        });
	blur.setTooltip(new Tooltip("blur"));
        
 
        Button btn3=new Button("Reset");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    color_mode=0;
                    image=dip.RGB2RGB(img);
                    dip.Img_View(image,imageView);
                    color.setItems(FXCollections.observableArrayList(
                                "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                            );
                    color.getSelectionModel().select(0);
                }
        });
       

        color.setItems(FXCollections.observableArrayList(
                            "Color")
                        );
        color.getSelectionModel().select(0);
	color.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number oldv, Number newv) -> {
            switch(newv.intValue()){
                case 1: Stage stage=new Stage();
                        stage.setTitle("Setting");
                        GridPane grid=new GridPane();
                        grid.setVgap(2);
                        grid.setHgap(2);
                        grid.setPadding(new Insets(5, 5, 5, 5));
                        TextField textfield=new TextField();
                        textfield.setPrefColumnCount(2);
                        Button button1=new Button("Yes");
                        Button button2=new Button("Cancel");
                        button1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                            public void handle(ActionEvent event) {
                                color_mode=1;
                                try{
                                    image=dip.Binary(image,Double.parseDouble(textfield.getText()));
                                    dip.Img_View(image,imageView);
                                    color.setItems(FXCollections.observableArrayList(
                                    "unavailable")
                                    );
                                    color.getSelectionModel().select(0);}
                                catch(Exception exception){
                                    Alert alert_save = new Alert(Alert.AlertType.WARNING);
                                    alert_save.setTitle("Warning");
                                    alert_save.setHeaderText("Error Occur!");
                                    alert_save.setContentText("Operation Failed!");
                                    alert_save.showAndWait();
                                }
                                stage.close();
                            }
                        });
                        button2.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                            public void handle(ActionEvent event) {
                                blur.getSelectionModel().select(0);
                                stage.close();
                            }
                        });
                                
                        grid.add(new Label("Set threshold:"),0,0);
                        grid.add(button1,0,1);
                        grid.add(button2,1,1);
                        grid.add(textfield,1,0);
                        Scene scene3=new Scene(grid,160,60);
                        stage.setScene(scene3);
                        stage.show();
                        
                        break;
                case 2: color_mode=1;
                        image=dip.RGB2Gray(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "Gray","Binary")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 3: color_mode=2;
                        image=dip.RGB2HSV(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "unavailable")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 4: color_mode=1;
                        image=dip.RGB2BGR(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "unavailable")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 5: color_mode=1;
                        image=dip.RGB2GBR(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "unavailable")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 6: color_mode=1;
                        image=dip.RGB2RBG(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "unavailable")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 7: color_mode=1;
                        image=dip.RGB2BRG(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "unavailable")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 8: color_mode=1;
                        image=dip.RGB2GRB(image);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "unavailable")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 9: color_mode=1;
                        image=dip.image_process(image,0);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 10: color_mode=1;
                        image=dip.image_process(image,1);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 11: color_mode=1;
                        image=dip.image_process(image,2);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 12: color_mode=1;
                        image=dip.image_process(image,3);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                        );
                        color.getSelectionModel().select(0);
                        break;
                case 13: color_mode=1;
                        image=dip.image_process(image,4);
                        dip.Img_View(image,imageView);
                        color.setItems(FXCollections.observableArrayList(
                            "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                        );
                        color.getSelectionModel().select(0);
                        break;
            }
        });
       
        Button btn5=new Button("Save");
        btn5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage stage_save=new Stage();
                    stage_save.setTitle("save");
                    FileChooser saver=new FileChooser();
                    File file = saver.showSaveDialog(stage_save);
                    file=new File(file.getPath()+".png");
                    System.out.println(file.getPath());
                    if (file != null) {
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        } 
                        catch (IOException e) {
                            Alert alert_save = new Alert(Alert.AlertType.WARNING);
                            alert_save.setTitle("Warning");
                            alert_save.setHeaderText("Output warning!");
                            alert_save.setContentText("Fail to save!");
                            alert_save.showAndWait();
                        }
	}

                }
        });
       
        hbox.getChildren().add(filebutton);
        hbox.getChildren().add(btn1);
        hbox.getChildren().add(grad);
        hbox.getChildren().add(blur);
        hbox.getChildren().add(color);
        hbox.getChildren().add(btn2);
        hbox.getChildren().add(btn5);
        hbox.getChildren().add(btn3);
        return hbox;
        
    }
    
    
    
    public VBox addVBoxPane(){
        VBox vbox=new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10)); //节点到边缘的距离
        vbox.setSpacing(10); //节点之间的间距

        /*
        file chooser
        */
        fileChooser.setTitle("Open Resource File");
        //fileChooser.showOpenDialog(stage);
        
        filebutton.setPrefSize(100, 20);
        filebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                color_mode=0;
                file_path=fileChooser.showOpenDialog(filestage).getPath();
                img=new Image("file:"+file_path);
                image=dip.RGB2RGB(img);
                dip.Img_View(image,imageView);
                color.setItems(FXCollections.observableArrayList(
                            "Color",new Separator(),"Gray","HSV","BGR","GBR","RBG","BRG","GRB","Invert","Brighter","Darker","Saturate","Desaturate")
                        );
                color.getSelectionModel().select(0);
            }
        });
         
        HBox hbox=addHBox();
        HBox hbox1=new HBox();
        hbox1.setPadding(new Insets(10, 10, 10, 10)); //节点到边缘的距离
        hbox1.setSpacing(10); //节点之间的间距
        vbox.getChildren().add(hbox);
        
        category.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        hbox1.getChildren().add(category);
        hbox1.getChildren().add(imageView);
        vbox.getChildren().add(hbox1);
        
        
        return vbox;
        
    }
    
    private String array2string(float[][] array){
        String s="";
        if(array.length!=1)
            s="Kernel:\n\n";
        for (float[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                s += String.valueOf(array1[j]);
                s+="  ";
            }
            s+="\n";
        }
        return s;
    }
    
    private float[][] string2array(String s){
        String[] strarray=s.split("\n");
        String[] str=strarray[0].split(" ");
        float [][] array=new float[strarray.length][str.length];
        for(int i=0;i<strarray.length;i++){
            str=strarray[i].split(" ");
            for(int j=0;j<str.length;j++){
                array[i][j]=Float.parseFloat(str[j]);
            }
        }
        return array;
    }

    
}
