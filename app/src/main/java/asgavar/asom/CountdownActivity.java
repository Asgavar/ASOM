package asgavar.asom;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/** It starts counting upon creating, needs to be given minutes and/or seconds from SetTimeActivity. */

public class CountdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        ActionBar actionBar = getSupportActionBar();
        String appSubtitle = getResources().getString(R.string.app_subtitle);
        actionBar.setSubtitle(appSubtitle);

        // receive time amount the user put
        final TextView timeRemaining = (TextView) findViewById(R.id.timeRemaining);
        final int minutes = Integer.valueOf(getIntent()
                .getStringExtra("ASOM_MINUTES"));
        final int seconds = Integer.valueOf(getIntent()
                .getStringExtra("ASOM_SECONDS"));

        final long countDownTimeInMillis = 1000 * (seconds + (minutes * 60));
        final long ONE_SECOND = 1000;

        final CountDownTimer timer = new CountDownTimer(countDownTimeInMillis, ONE_SECOND) {
            @Override
            public void onTick(long l) {
                /** Convert millis to minutes and seconds and display them. */
                int inSeconds = (int) l / 1000;
                // mfw an alghorithm is needed
                int howManyMinutes = 0;
                while (inSeconds - 60 >= 0) {
                    howManyMinutes++;
                    inSeconds -= 60;
                }
                // as in input, 9 becomes 09 etc
                String minutesAsString = howManyMinutes >= 10 ? String.valueOf(howManyMinutes)
                        :
                        "0" + String.valueOf(howManyMinutes);
                String secondsAsString = inSeconds >= 10 ? String.valueOf(inSeconds)
                        :
                        "0" + String.valueOf(inSeconds);
                String timeToShow = minutesAsString + ":" + secondsAsString;
                timeRemaining.setText(timeToShow);
            }

            @Override
            public void onFinish() {
                /** Play the sound three times */
                timeRemaining.setText("XX:XX");

                final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                if (vibrator.hasVibrator())
//                    vibrator.vibrate(2137);

                final MediaPlayer mp = MediaPlayer.create(CountdownActivity.this, R.raw.air_horn);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    int timesPlayed = 0;
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // Imperator March pattern
                        vibrator.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);
                        final int maxTimes = 3;
                        if (timesPlayed < maxTimes) {
                            mp.start();
                            timesPlayed++;
                        }
                    }
                });
                // will be played 3 times
                mp.start();
            }
        }.start();

        FloatingActionButton stopButton = (FloatingActionButton) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                String cancelled = getResources().getString(R.string.cancelled);
                Toast.makeText(CountdownActivity.this, cancelled, Toast.LENGTH_LONG).show();
                timeRemaining.setText("XX:XX");
            }
        });
    }
}
