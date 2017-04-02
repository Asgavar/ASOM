package asgavar.asom;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;

/** User sets the time here. */

public class SetTimeActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_set_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about_me:
                Toast.makeText(this, "Artur Juraszek, 2017", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_set_time);

        ActionBar actionBar = getSupportActionBar();
        String appSubtitle = getResources().getString(R.string.app_subtitle);
        actionBar.setSubtitle(appSubtitle);
        final TextView minutes = (TextView) findViewById(R.id.minutes);
        final TextView seconds = (TextView) findViewById(R.id.seconds);

        ArrayList<ImageButton> arrowButtons = new ArrayList<>(4);
        arrowButtons.add((ImageButton) findViewById(R.id.minutesArrowUp));
        arrowButtons.add((ImageButton) findViewById(R.id.minutesArrowDown));
        arrowButtons.add((ImageButton) findViewById(R.id.secondsArrowUp));
        arrowButtons.add((ImageButton) findViewById(R.id.secondsArrowDown));

        SetTimeButtonsOnClickListener clickListener = new SetTimeButtonsOnClickListener(minutes, seconds);
        for (ImageButton arrow : arrowButtons)
            arrow.setOnClickListener(clickListener);

        FloatingActionButton startButton = (FloatingActionButton) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Proceed to CountdownActivity and send it the time amount user put */
                Intent intentStartCountdown = new Intent(SetTimeActivity.this, CountdownActivity.class);
                intentStartCountdown.putExtra("ASOM_MINUTES", minutes
                        .getText()
                        .toString());
                intentStartCountdown.putExtra("ASOM_SECONDS", seconds
                        .getText()
                        .toString());
                // don't allow to go back
                finish();
                startActivity(intentStartCountdown);
            }
        });
    }
}
