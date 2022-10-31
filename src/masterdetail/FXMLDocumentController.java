/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package masterdetail;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Administrator
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txtnofaktur;
    @FXML
    private TextField txtkodelgn;
    @FXML
    private TextField txtkodebrg;
    @FXML
    private TextField txtjumlah;
    @FXML
    private Button btntambah;
    @FXML
    private Button btnhapus;
    @FXML
    private Button btnclear;
    @FXML
    private Button btnsimpan;
    @FXML
    private Button btnbatal;
    @FXML
    private Button brnkeluar;
    @FXML
    private DatePicker dpttanggal;
    
    @FXML
    private TableView<SubjualModel> tbvdetil;
    
    private boolean editmode=false;
    private DBjual dt=new DBjual();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dt.getSubjualModel().clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dpttanggal.setValue(LocalDate.parse(formatter.format(LocalDate.now()),formatter));    
        tbvdetil.getColumns().clear();
        tbvdetil.getItems().clear();
        
        TableColumn col=new TableColumn("nofaktur");
        col.setCellValueFactory(new PropertyValueFactory<SubjualModel, String>("Nofaktur"));
        tbvdetil.getColumns().addAll(col);
        
        col=new TableColumn("kode barang");
        col.setCellValueFactory(new PropertyValueFactory<SubjualModel, String>("Kodebrg"));
        tbvdetil.getColumns().addAll(col);
        
        col=new TableColumn("jumlah");
        col.setCellValueFactory(new PropertyValueFactory<SubjualModel, Integer>("Jumlah"));
        tbvdetil.getColumns().addAll(col);
    } 
    
    @FXML
    private void tambahklik(ActionEvent event) {
        
        SubjualModel tmp=new SubjualModel();
        tmp.setNofaktur(txtnofaktur.getText());
        tmp.setKodebrg(txtkodebrg.getText());
        tmp.setJumlah(Integer.parseInt(txtjumlah.getText()));
        if(dt.getSubjualModel().get(txtkodebrg.getText()) ==null){
            dt.setSubjualModel(tmp);
            tbvdetil.getItems().add(tmp);
        }  else {
                   int p=-1;
                   for(int i=0;i<tbvdetil.getItems().size();i++){
                       if(tbvdetil.getItems().get(i).getKodebrg().equalsIgnoreCase(
                               txtkodebrg.getText()))
                          p=i; 
                   }
                   if(p>=0) tbvdetil.getItems().set(p, tmp);
                   dt.getSubjualModel().remove(txtkodebrg.getText());
                   dt.setSubjualModel(tmp);
        }
        
        clearklik(event);
    }
    
    @FXML
    private void hapusklik(ActionEvent event) {
        
        SubjualModel tmp=tbvdetil.getSelectionModel().getSelectedItem();
        if(tmp!=null){
            tbvdetil.getItems().remove(tmp);
            dt.getSubjualModel().remove(tmp.getKodebrg());
            clearklik(event);        
        } else {
               Alert a=new Alert(Alert.AlertType.ERROR,"Pilih data dulu",ButtonType.OK);
               a.showAndWait(); tbvdetil.requestFocus();
        }  
    }

    @FXML
    private void clearklik(ActionEvent event) {
        
        txtkodebrg.setText("");
        txtjumlah.setText("");
        txtkodebrg.requestFocus();
    }

    @FXML
    private void simpanklik(ActionEvent event) {
        
        dt.getJualModel().setNofaktur(txtnofaktur.getText());
        dt.getJualModel().setTanggal(Date.valueOf(dpttanggal.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        dt.getJualModel().setKodelgn(txtkodelgn.getText());
        if(dt.saveall()){
        Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil disimpan ",ButtonType.OK);
               a.showAndWait(); 
        } else {
               Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal disimpan ",ButtonType.OK);
               a.showAndWait();             
        }
        batalklik(event);
    }

    @FXML
    private void batalklik(ActionEvent event) {
    
        clearklik(event);
        txtnofaktur.setText("");
        txtkodelgn.setText("");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dpttanggal.setValue(LocalDate.parse(formatter.format(LocalDate.now()),formatter));    
        tbvdetil.getItems().clear();
        dt.getSubjualModel().clear();
        txtnofaktur.setEditable(true);
        editmode=false;
        txtnofaktur.requestFocus();
    }

    @FXML
    private void keluarklik(ActionEvent event) {
        
        System.exit(0); 
    }
    
    @FXML
    private void nofakturcek(KeyEvent event) {
        if (event.getCode()== KeyCode.TAB){
            dt.getJualModel().setNofaktur(txtnofaktur.getText());
        Alert a=new Alert(Alert.AlertType.INFORMATION,String.valueOf(dt.validasi(txtnofaktur.getText())),ButtonType.OK);
               a.showAndWait(); 
            if(dt.validasi(txtnofaktur.getText())>=1){
                JualModel tmp=dt.getdata (txtnofaktur.getText());
                if(tmp.getTanggal()!=null) dpttanggal.setValue(tmp.getTanggal().toLocalDate());
                txtkodelgn.setText(tmp.getKodelgn());
                ObservableList<SubjualModel> data=dt.LoadDetil();
                if(data!=null){            
                                tbvdetil.setItems(data);
                                }
               txtnofaktur.setEditable(false);
               editmode=true;
            }
        }        
    }

    @FXML
    private void tbvdetillklik(MouseEvent event) {
        SubjualModel tmp=tbvdetil.getSelectionModel().getSelectedItem();
        if(tmp!=null){
           txtkodebrg.setText(tmp.getKodebrg());
           txtjumlah.setText(String.valueOf(tmp.getJumlah()));
        }        
    }
       
}
