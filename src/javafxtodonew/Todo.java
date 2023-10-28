/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtodonew;

/**
 *
 * @author nayeeemx
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Todo 
{
    private final StringProperty id;
    private final StringProperty task;
    private final StringProperty dueby;
    private final StringProperty  taskdescription;
     
    public Todo()
    {
        id = new SimpleStringProperty(this, "id");
        task = new SimpleStringProperty(this, "task");
        dueby = new SimpleStringProperty(this, "dueby");
        taskdescription = new SimpleStringProperty(this, "taskdescription");
    }
    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }
    public StringProperty taskProperty() { return task; }
    public String getTask() { return task.get(); }
    public void setTask(String newtask) { task.set(newtask); }
    public StringProperty duebyProperty() { return dueby; }
    public String getDueBy() { return dueby.get(); }
    public void setDueBy(String newDueBy) { dueby.set(newDueBy); }
    
    public StringProperty taskdescriptionProperty() { return taskdescription; }
    public String getTaskDescription() { return taskdescription.get(); }
    public void setTaskDescription(String newTaskdescription) { taskdescription.set(newTaskdescription); }
}
