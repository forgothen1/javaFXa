package security;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
/*for now  class only restrict  imput in text fields */

/**
 * for now  class only restrict  imput in text fields
 */
public class Securty {
    /**
     * method that restricts number of chars /keystrokes in textfields  its  flexible for all textfields
     * @param tf field that u wanna restrict
     * @param maxLength number with how much u wanna restrict
     */
    public void addTetLimiter(final TextField tf, final int maxLength)
    {       //here is listener that is on key press
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (tf.getText().length() >maxLength) {
                    s= tf.getText().substring(0,maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    /**
     * method that restricts only on numbers
     * @param tf field that u wanna restirct
     */
    public  void addTetLimiter1(final TextField tf)
    { //here is listener that is on key press
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!tf.getText().isEmpty()) {
                    s=tf.getText().replaceAll( "[^0-9]+"," ").trim();

                    tf.setText(s);
                }
            }
        });
    }

    /**
     *  this is for fields where they cach float numbers
     * @param tf field that u wanna restirct
     */
    public  void addTetLimiter2(final TextField tf)
    { //here is listener that is on key press
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!tf.getText().isEmpty()) {

                    s=tf.getText().replaceAll( "^\\d+(\\.\\d+)?"," ");

                    tf.setText(s);
                }
            }
        });
    }
}
