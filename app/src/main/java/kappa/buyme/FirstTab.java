package kappa.buyme;
import android.app.Activity;



        import android.os.Bundle;
        import android.widget.TextView;

public class FirstTab extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/* First Tab Content */
        TextView textView = new TextView(this);
        textView.setText("Next");
        setContentView(textView);

    }
}
