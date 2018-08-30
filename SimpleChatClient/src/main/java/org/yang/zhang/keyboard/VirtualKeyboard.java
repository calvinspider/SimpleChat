package org.yang.zhang.keyboard;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.PolygonBuilder;

public class VirtualKeyboard {

  private final VBox root ;

  public VirtualKeyboard(TextField target) {
    this.root = new VBox(5);
    root.setPadding(new Insets(10));
    root.getStyleClass().add("virtual-keyboard");
    final Modifiers modifiers = new Modifiers();

    final String[][] unshifted = new String[][] {
        { "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=" },
        { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]", "\\" },
        { "a", "s", "d", "f", "g", "h", "j", "k", "l", ";", "'" },
        { "z", "x", "c", "v", "b", "n", "m", ",", ".", "/" } };

    final String[][] shifted = new String[][] {
        { "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+" },
        { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "{", "}", "|" },
        { "A", "S", "D", "F", "G", "H", "J", "K", "L", ":", "\"" },
        { "Z", "X", "C", "V", "B", "N", "M", "<", ">", "?" } };

    final KeyCode[][] codes = new KeyCode[][] {
        { KeyCode.BACK_QUOTE, KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3,
            KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7,
            KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0, KeyCode.SUBTRACT,
            KeyCode.EQUALS },
        { KeyCode.Q, KeyCode.W, KeyCode.E, KeyCode.R, KeyCode.T, KeyCode.Y,
            KeyCode.U, KeyCode.I, KeyCode.O, KeyCode.P, KeyCode.OPEN_BRACKET,
            KeyCode.CLOSE_BRACKET, KeyCode.BACK_SLASH },
        { KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.F, KeyCode.G, KeyCode.H,
            KeyCode.J, KeyCode.K, KeyCode.L, KeyCode.SEMICOLON, KeyCode.QUOTE },
        { KeyCode.Z, KeyCode.X, KeyCode.C, KeyCode.V, KeyCode.B, KeyCode.N,
            KeyCode.M, KeyCode.COMMA, KeyCode.PERIOD, KeyCode.SLASH } };

    final Button backspace = createNonshiftableButton("Backspace", KeyCode.BACK_SPACE, modifiers, target);

    for (int row = 0; row < unshifted.length; row++) {
      HBox hbox = new HBox(5);
      hbox.setAlignment(Pos.CENTER);
      root.getChildren().add(hbox);

      for (int k = 0; k < unshifted[row].length; k++) {
        hbox.getChildren().add( createShiftableButton(unshifted[row][k], shifted[row][k], codes[row][k], modifiers, target));
      }
      if(row==unshifted.length-2){
        hbox.getChildren().add(modifiers.shiftKey());
      }
      if(row==unshifted.length-1){
        hbox.getChildren().add(backspace);
      }
    }
    final Button spaceBar = createNonshiftableButton(" ", KeyCode.SPACE, modifiers, target);
    spaceBar.setMaxWidth(Double.POSITIVE_INFINITY);
    HBox.setHgrow(spaceBar, Priority.ALWAYS);
  }

  public VirtualKeyboard() {
    this(null);
  }

  public Node view() {
    return root ;
  }

  private Button createShiftableButton(final String unshifted, final String shifted,
      final KeyCode code, Modifiers modifiers, final TextField target) {
      final StringBinding text = Bindings.when(modifiers.shiftDown()).then(shifted).otherwise(unshifted);
      Button button = createButton(text, code, modifiers, target);
      return button;
  }

  private Button createNonshiftableButton(final String text, final KeyCode code, final Modifiers modifiers, final TextField target) {
    StringProperty textProperty = new SimpleStringProperty(text);
    Button button = createButton(textProperty, code, modifiers, target);
    return button;
  }

  private Button createButton(final ObservableStringValue text, final KeyCode code, final Modifiers modifiers, final TextField target) {
    final Button button = new Button();
    button.textProperty().bind(text);
    button.setFocusTraversable(false);
    button.getStyleClass().add("virtual-keyboard-button");
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        final Node targetNode ;
        if (target != null) {
          targetNode = target;
        } else {
          targetNode = view().getScene().getFocusOwner();
        }
        
        if (targetNode != null) {
          final String character;
          if (text.get().length() == 1) {
            character = text.get();
          } else {
            character = KeyEvent.CHAR_UNDEFINED;
          }
          final KeyEvent keyPressEvent = createKeyEvent(button, targetNode, KeyEvent.KEY_PRESSED, character, code, modifiers);
          targetNode.fireEvent(keyPressEvent);
          final KeyEvent keyReleasedEvent = createKeyEvent(button, targetNode, KeyEvent.KEY_RELEASED, character, code, modifiers);
          targetNode.fireEvent(keyReleasedEvent);
          if (character != KeyEvent.CHAR_UNDEFINED) {
            final KeyEvent keyTypedEvent = createKeyEvent(button, targetNode, KeyEvent.KEY_TYPED, character, code, modifiers);
            targetNode.fireEvent(keyTypedEvent);
          }
          modifiers.releaseKeys();
        }
      }
    });
    return button;
  }

  private KeyEvent createKeyEvent(Object source, EventTarget target,
      EventType<KeyEvent> eventType, String character, KeyCode code,
      Modifiers modifiers) {
      return new KeyEvent(source, target, eventType, character, code.toString(),
        code, modifiers.shiftDown().get(), false, false, false);
  }

  private Button createCursorKey(KeyCode code, Modifiers modifiers, TextField target, Double... points) {
    Button button = createNonshiftableButton("", code, modifiers, target);
    final Node graphic = PolygonBuilder.create().points(points).build();
    graphic.setStyle("-fx-fill: -fx-mark-color;");
    button.setGraphic(graphic);
    button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    return button ;
  }
  
  private static class Modifiers {

    private final ToggleButton shift;

    Modifiers() {
      this.shift = createToggle("Shift");
    }

    private ToggleButton createToggle(final String text) {
      final ToggleButton tb = new ToggleButton(text);
      tb.setFocusTraversable(false);
      return tb;
    }

    public Node shiftKey() {
      return shift;
    }


    public BooleanProperty shiftDown() {
      return shift.selectedProperty();
    }


    public void releaseKeys() {
      shift.setSelected(false);
    }
  }  
}
