package org.yang.zhang;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

/**
* @describe��table֮����ק
* @author: YUMAO
* @version: 
* 2015��10��23�� ����3:53:50
 */
public class TableDragAndDropSample extends Application {
    @SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	private void init(Stage primaryStage) {
        HBox box = new HBox();
        primaryStage.setScene(new Scene(box));
        final ObservableList<Person> data1 = FXCollections.observableArrayList(
            new Person("Jacob",     "Smith",    "jacob.smith@example.com" ),
            new Person("Isabella",  "Johnson",  "isabella.johnson@example.com" ),
            new Person("Ethan",     "Williams", "ethan.williams@example.com" ),
            new Person("Emma",      "Jones",    "emma.jones@example.com" ),
            new Person("Michael",   "Brown",    "michael.brown@example.com" )

        );
        final ObservableList<Person> data2 = FXCollections.observableArrayList(
                new Person("Jacob",     "Smith",    "jacob.smith@example.com" ),
                new Person("Isabella",  "Johnson",  "isabella.johnson@example.com" ),
                new Person("Ethan",     "Williams", "ethan.williams@example.com" ),
                new Person("Emma",      "Jones",    "emma.jones@example.com" ),
                new Person("Michael",   "Brown",    "michael.brown@example.com" )

            );
        //---------------- ��ʼ�����ݿ�ʼ 
        TableColumn firstNameCol1 = new TableColumn();
        firstNameCol1.setText("First");
        firstNameCol1.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn lastNameCol1 = new TableColumn();
        lastNameCol1.setText("Last");
        lastNameCol1.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn emailCol1 = new TableColumn();
        emailCol1.setText("Email");
        emailCol1.setMinWidth(200);
        emailCol1.setCellValueFactory(new PropertyValueFactory("email"));
        
        TableColumn firstNameCol2 = new TableColumn();
        firstNameCol2.setText("First");
        firstNameCol2.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn lastNameCol2 = new TableColumn();
        lastNameCol2.setText("Last");
        lastNameCol2.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn emailCol2 = new TableColumn();
        emailCol2.setText("Email");
        emailCol2.setMinWidth(200);
        emailCol2.setCellValueFactory(new PropertyValueFactory("email"));
       
        TableView<Person> table1 = new TableView();
        table1.setItems(data1);
        table1.getColumns().addAll(firstNameCol1, lastNameCol1, emailCol1);
        TableView<Person> table2 = new TableView();
        table2.setItems(data2);
        table2.getColumns().addAll(firstNameCol2, lastNameCol2, emailCol2);
        
        box.setMargin(table1, new Insets(20));
        box.setMargin(table2, new Insets(20));
        
        box.getChildren().addAll(table1,table2);
        // -------------  ��ʼ�����ݽ���

        // -------------  �����帳ֵ
        table1.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Dragboard dragboard = table1.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				int i = table1.getSelectionModel().getFocusedIndex();	
				String result = table1.getItems().get(i).firstNameProperty().getValue();
				content.putString(result);
				dragboard.setContent(content);
			}
		});
        
        // -------------- table2��ֵ
        firstNameCol2.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
			@Override
			public TableCell<Person, String> call(TableColumn<Person, String> param) {
				EditingCell cell = new EditingCell();
				cell.setOnDragOver(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent event) {
						event.acceptTransferModes(TransferMode.ANY);
					}
				});
				cell.setOnDragDropped(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent event) {
						Dragboard dragboard = event.getDragboard();
						int i = cell.getTableRow().getIndex();
						Person p = table2.getItems().get(i);
						p.firstName.set(dragboard.getString());
						p.email.set(dragboard.getString());
						p.lastName.set(dragboard.getString());
					}
				});
				return cell;
			}
		});
        
        
    }

    /**
	* @describe���ٷ�����,���Ա༭����
	* @author: Andy_yu /�Ĵ����ն���Ϣ�����Ƽ����޹�˾ 
	* @version: 
	* 2015��4��6�� ����10:43:47
	 */
    public static class EditingCell extends TableCell<Person, String> {
        private TextField textField;

        public EditingCell() {
        }
        @Override 
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
        @Override 
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }
        @Override 
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {                
                @Override public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    } 
    
    // -------  
    public static class Person {
        private StringProperty firstName;
        private StringProperty lastName;
        private StringProperty email;
        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }
        public StringProperty firstNameProperty() { return firstName; }
        public StringProperty lastNameProperty() { return lastName; }
        public StringProperty emailProperty() { return email; }
    }

    

    @Override 
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

}
