package pages;

import DatabaseClasses.*;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static pages.LoginController.dbConnection;

import DatabaseClasses.CampType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableObjects.CampsTable;

public class AddCampsController implements Initializable{
    
    Stage s;
    
    public Stage getS() {
        return s;
    }
    
    public void setS(Stage s) {
        this.s = s;
    }
    
    @FXML
    private TextField campLoc;
    
    @FXML
    private TextField campName;
    
    @FXML
    private ComboBox campType;
    
    private EntityManager em;
    
    @FXML
    private void addCamp(MouseEvent event) {
        
        em = LoginController.dbConnection.newEntityManager();
        TypedQuery<CampType> q1 = em.createQuery("SELECT t FROM CampType t WHERE t.name ='" + campType.getValue().toString() + "'", CampType.class);
        List<CampType> l = q1.getResultList();
        
        CampSite c = new CampSite();
        c.setCampType(l.get(0).getId());
        c.setLocation(campLoc.getText());
        c.setName(campName.getText());
        c.setRequirement("requirement");
        
       
        em.persist(c);
        em.getTransaction().commit();
        em.close();
        s.close();
        LoginController lc = new LoginController();
        lc.sceneTransition("Camps.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
        // Starting connection
        em = LoginController.dbConnection.newEntityManager();
        // Filling combobox
        TypedQuery<CampType> q1 = em.createQuery("SELECT ct FROM CampType ct", CampType.class);
        List<CampType> l = q1.getResultList();
        ObservableList<String> ol = FXCollections.observableArrayList();
        for (CampType ct : l) {
            ol.add(ct.getName());
        }
        campType.setItems(ol);
        em.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
