package sg.edu.ep.c346.id20029318.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    TextView txtTotalBill;
    TextView txtEachPay;
    EditText edAmt;
    EditText dis;
    EditText numPax;
    ToggleButton svs;
    ToggleButton gst;
    Button togReset;
    Button togSplit;
    RadioGroup radPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTotalBill = findViewById(R.id.txtTotalBill);
        txtEachPay = findViewById(R.id.textEachPay);
        edAmt = findViewById(R.id.editAmt);
        numPax = findViewById(R.id.editNumPax);
        dis = findViewById(R.id.edDis);
        svs = findViewById(R.id.togService);
        gst = findViewById(R.id.togGst);
        togReset = findViewById(R.id.togReset);
        togSplit = findViewById(R.id.togSplit);
        radPay = findViewById(R.id.radGroup);

        togSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //discount
                if (dis.getText().toString().trim().length() != 0) {
                    double newAmt = 0.0;
                    newAmt *= 1 - Double.parseDouble(dis.getText().toString()) / 100;

                // Service charge
                if (edAmt.getText().toString().trim().length()!= 0 && numPax.getText().toString().trim().length()!=0) {
                    double oriAmt =Double.parseDouble(edAmt.getText().toString());
                    if (!svs.isChecked() && !gst.isChecked()) {
                        newAmt = oriAmt;
                    }
                    else if (svs.isChecked() && !gst.isChecked()) {
                        newAmt = oriAmt*1.1;
                    }
                    else if (!svs.isChecked() && gst.isChecked()) {
                        newAmt = oriAmt*1.07;
                    }
                    else {
                        newAmt = oriAmt*1.17;
                    }
                }

                //payment type
                String mode = " in cash";
                if (radPay.getCheckedRadioButtonId() == R.id.radPayNow) {
                    mode = " via Paynow to 912345678";
                }
                //num of people
                txtTotalBill.setText("Total Bill: $" + String.format("%.2f", newAmt));
                int numPeople = Integer.parseInt(numPax.getText().toString());
                if (numPeople !=1) {
                    txtEachPay.setText("Each Pays: $" + String.format("%.2f", newAmt/numPeople) + mode);
                }
                else {
                    txtEachPay.setText("Each Pays: $" + newAmt + mode);
                }

                //reset
                togReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edAmt.setText("");
                        numPax.setText("");
                        svs.setChecked(false);
                        gst.setChecked(false);
                        dis.setText("");
                        radPay.check(R.id.radGroup);
                    }
                });


            }}
        });

    }
}
