/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javafxtodonew;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author nayeeemx
 */
public class FXMLDocumentController implements Initializable {
    
   
    @FXML
    private TableColumn<Todo, String> DueByColmn;

    @FXML
    private TableColumn<Todo, String> IDColmn;

    @FXML
    private Label KeepTodoTitle;

    @FXML
    private TableView<Todo> table;

    @FXML
    private TableColumn<Todo, String> TaskColmn;

    @FXML
    private TableColumn<Todo, String> TaskDescriptionColmn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label label;

    @FXML
    private TextField txtDueBy;

    @FXML
    private TextField txtTask;

    @FXML
    private TextField txtTaskDescription;
    
    
    
    
    
    
    int myIndex;
    int id;
    
    
        Connection con;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root","password");
                PreparedStatement pst1 = null;
            pst1 = con.prepareStatement("select 1 from dual");
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                System.out.println("here");

            }
            pst1.close();

        } catch (ClassNotFoundException ex) {

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    
    public void table() {
        Connect();

        ObservableList<Todo> todos = FXCollections.observableArrayList();
        System.out.println("here");
        try {
              PreparedStatement pst;

            pst = con.prepareStatement("select * from todo");
            ResultSet rs = pst.executeQuery();
            
            {
                while (rs.next()) {
                    Todo td = new Todo();
                    td.setId(rs.getString("id"));
                    td.setTask(rs.getString("task"));
                    td.setDueBy(rs.getString("dueby"));
                    td.setTaskDescription(rs.getString("taskdescription"));
                    todos.add(td);
                }
            }

 /*
                    Todo td = new Todo();
                    td.setId("1");
                    td.setTask("task");
                    td.setDueBy("dueby");
                    td.setTaskDescription("taskdescription");
                    todos.add(td);*/

            table.setItems(todos);
            IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
            TaskColmn.setCellValueFactory(f -> f.getValue().taskProperty());
            DueByColmn.setCellValueFactory(f -> f.getValue().duebyProperty());
            TaskDescriptionColmn.setCellValueFactory(f -> f.getValue().taskdescriptionProperty());

        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setRowFactory(tv -> {
            TableRow<Todo> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event
                    -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();

                    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtTask.setText(table.getItems().get(myIndex).getTask());
                    txtDueBy.setText(table.getItems().get(myIndex).getDueBy());
                    txtTaskDescription.setText(table.getItems().get(myIndex).getTaskDescription());

                }
            });
            return myRow;
        });

    }

        @FXML
    void Add(ActionEvent event) {
        String task, dueby, taskdescription;
        task = txtTask.getText();
        dueby = txtDueBy.getText();
        taskdescription = txtTaskDescription.getText();
        try {
            PreparedStatement pst2;
            pst2 = con.prepareStatement("insert into todo(task,dueby,taskdescription)values(?,?,?)");
            pst2.setString(1, task);
            pst2.setString(2, dueby);
            pst2.setString(3, taskdescription);
            pst2.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Todo Regitdation");

            alert.setHeaderText("Todo Regitdation");
            alert.setContentText("Record Addedddd!");
            alert.showAndWait();
            table();

            txtTask.setText("");
            txtDueBy.setText("");
            txtTaskDescription.setText("");
            txtTask.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Delete(ActionEvent event) {
                myIndex = table.getSelectionModel().getSelectedIndex();
         
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                     
        try 
        {
              PreparedStatement pst;

            pst = con.prepareStatement("delete from todo where id =? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Todo Registationn");
        
        alert.setHeaderText("Todo Registation");
        alert.setContentText("Deletedd!");
        alert.showAndWait();
                  table();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Update(ActionEvent event) {
        String task,dueby,taskdescription;
        
         myIndex = table.getSelectionModel().getSelectedIndex();
         
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
           
            task = txtTask.getText();
            dueby = txtDueBy.getText();
            taskdescription = txtTaskDescription.getText();
        try 
        {
            PreparedStatement pst;
            pst = con.prepareStatement("update todo set task = ?,dueby = ? ,taskdescription = ? where id = ? ");
            pst.setString(1, task);
            pst.setString(2, dueby);
            pst.setString(3, taskdescription);
            pst.setInt(4, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Todo Registationn");
        
        alert.setHeaderText("Todo Registation");
        alert.setContentText("Updateddd!");
        alert.showAndWait();
                table();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                Connect();
                table();


    }    
    
}
