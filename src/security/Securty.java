package security;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class Securty {

    public static void addTetLimiter(final TextField tf, final int maxLength)
    {
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
    public static void addTetLimiter1(final TextField tf)
    {
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
    public static void addTetLimiter2(final TextField tf)
    {
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
