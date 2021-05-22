package exercise.find.roots;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DisplayResultsActivity extends AppCompatActivity {
    public static final String RESULT_PREFIX = "Two roots combining this number:";
    public static final String RUNTIME_PREFIX = "Calculation time:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view to display mode layout
        setContentView(R.layout.activity_display_results);
        Intent intent = getIntent();
        TextView outputNumbers = findViewById(R.id.textViewDisplayOutputNumbers);
        TextView calculationTime = findViewById(R.id.textViewDisplayCalculationTime);
        long originalNumber = intent.getLongExtra("original_number", 0);
        long root1 = intent.getLongExtra("root1", 0);
        long root2 = intent.getLongExtra("root2", 0);
        String outputString = String.format(Locale.ENGLISH, "%s %d=%d*%d", RESULT_PREFIX, originalNumber, root1, root2);
        outputNumbers.setText(outputString);
        long timeToCalculate = intent.getLongExtra("time_until_calculation_seconds", 0);
        String runtimeString = String.format(Locale.ENGLISH, "%s %d", RUNTIME_PREFIX, timeToCalculate);
        calculationTime.setText(runtimeString);
        outputNumbers.setVisibility(View.VISIBLE);
        calculationTime.setVisibility(View.VISIBLE);
    }
}
