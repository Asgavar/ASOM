package asgavar.asom;

import android.view.View;
import android.widget.TextView;

/**
 * Arrows' behaviour is defined here.
 */

class SetTimeButtonsOnClickListener implements android.view.View.OnClickListener {

    private TextView minutes;
    private TextView seconds;

    public SetTimeButtonsOnClickListener(TextView pMinutes, TextView pSeconds) {
        minutes = pMinutes;
        seconds = pSeconds;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.minutesArrowUp:
                increaseByOne(minutes);
                break;
            case R.id.minutesArrowDown:
                decreaseByOne(minutes);
                break;
            case R.id.secondsArrowUp:
                increaseByOne(seconds);
                break;
            case R.id.secondsArrowDown:
                decreaseByOne(seconds);
        }
    }

    private void increaseByOne(TextView minutesOrSeconds) {
        String currentValueAsString = minutesOrSeconds
                .getText()
                .toString();
        int currentValue = Integer.valueOf(currentValueAsString);
        currentValue++;
        String valueToSet = String.valueOf(currentValue);
        // show e.g. 5 as 05, looks way nicer imho
        valueToSet = currentValue < 10 ? "0" + valueToSet : valueToSet;
        minutesOrSeconds.setText(valueToSet);
    }

    private void decreaseByOne(TextView minutesOrSeconds) {
        String currentValueAsString = minutesOrSeconds
                .getText()
                .toString();
        int currentValue = Integer.valueOf(currentValueAsString);
        // prevent reaching below 0
        if (currentValue > 0)
            currentValue--;
        String valueToSet = String.valueOf(currentValue);
        // as in increaseByOne(TextView)
        valueToSet = currentValue < 10 ? "0" + valueToSet : valueToSet;
        minutesOrSeconds.setText(valueToSet);
    }
}
