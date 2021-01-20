package com.example.audientesprojekt.presetlogik;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.audientesprojekt.R;
import org.florescu.android.rangeseekbar.RangeSeekBar;
import java.util.Formatter;
import java.util.Locale;

/* Den her klasse er en dialog, som bruges, så brugeren kan redigerer den valgte lydfil, og
 * bestemme hvornår lyden skal starte. */

public class SoundInputDialog extends Dialog implements View.OnClickListener{

    private SoundInput soundInput; // den valgte lydfil
    private RangeSeekBar<Float> rangeSeekBar; // bruges til at redigerer/trimme lyden
    private TextView start, end; // varighedstart og varighedslut
    private Button confirmBtn;
    private EditText editStartTime;
    private long minimum, maximum;
    private float offset;
    private boolean isDialogClosed; // skal bruges til at vide når dialogen lukker


    public SoundInputDialog(Context context, SoundInput soundInput) {
        super(context);
        this.soundInput = soundInput;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_input_dialog);

        start = findViewById(R.id.startMin);
        end = findViewById(R.id.endMin);
        confirmBtn = findViewById(R.id.confirmBtn);
        editStartTime = findViewById(R.id.editStartText);
        rangeSeekBar = findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setRangeValues(0f, 1f);
        rangeSeekBar.setNotifyWhileDragging(true);
        confirmBtn.setOnClickListener(this);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Float>() {
            @Override
            //
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Float minValue, Float maxValue) {
                trim(minValue, maxValue);
            }
        });
        rangeSeekBar.setSelectedMinValue(0f);
        rangeSeekBar.setSelectedMaxValue(1f);
        trim(0f, 1f);
    }


    /* Nedestående metode bruges til at vise længde af lydefilen, og vise status på
     *  lydfilens varigheden, når der rykkes på rangebaren.
     *  Som der ses i koden så henter vi alt information om lydfilen, og
     *  sætter dens værdier inde i vores TextViews (lydfilens varighed)*/
    private void trim(float min, float max) {
        minimum = (long) (min * soundInput.getSoundDuration());
        maximum =  (long) (max * soundInput.getSoundDuration());
        String startTime = stringDuration((long)(min * soundInput.getSoundDuration()));
        String endTime = stringDuration((long)(max * soundInput.getSoundDuration()));
        start.setText(startTime);
        end.setText(endTime);
    }

    /* Denne her metode bliver brugt i trim metoden. Den bruges bare at lave en format
     *  Lydfilens varighed er konvereret til mikrosekunder før vi sætter den i objektet, derfor
     *  konventerer vi varigheden fra mikrosekunder til sekunder, minutter og timer fra. */

    /*  Et eksempel: Antag vi har en lydfil, som i alt varer 15 sekunder.
     *   I stedet for at vi viser mikrosekunderne af lydfilen i vores TextViews, så
     *   viser vi et format, hvor den første TextView indeholder 0:00, og den anden
     *  indeholder 0:15. Værdierne ændrer sig når der rykkes i rangebaren*/

    private static String stringDuration(long time) {
        int timeMs = (int)(time / 1000);

        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter formatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /*  Det der sker når brugeren trykker på "Confirm"-knappen
     *   Brugeren kan valgfrit skrive i en TextView hvornår lydfilen skal starte
     *   i den mixet lydfil. Det skrives i sekunder, som lægges i variablen offset*/
    @Override
    public void onClick(View v) {
        try{
            offset = Float.parseFloat(((EditText)findViewById(R.id.editStartText)).getText().toString());
            /*
            input.setStartTime((long)(offset*1000000));
            input.setStart(minimum);
            input.setEnd(maximum);
             */
        }catch (Exception e){
        }
        isDialogClosed = true;

        dismiss();
    }

    public boolean isDialogClosed() {
        return isDialogClosed;
    }

    public long getMinimum() {
        return minimum;
    }

    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }

    public long getMaximum() {
        return maximum;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }
}




