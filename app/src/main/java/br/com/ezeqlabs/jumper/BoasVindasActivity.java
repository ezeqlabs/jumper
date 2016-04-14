package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BoasVindasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        TextView jogar = (TextView) findViewById(R.id.menu_principal_jogar);
        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(BoasVindasActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }
}
