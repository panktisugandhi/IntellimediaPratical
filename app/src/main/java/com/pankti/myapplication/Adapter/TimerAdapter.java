package com.pankti.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pankti.myapplication.Model.CountDownTimer;
import com.pankti.myapplication.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder> {

    private ArrayList<CountDownTimer> arrayList;

    public TimerAdapter(ArrayList<CountDownTimer> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTimer;
        private final EditText etSec;
        private final Button btnAction;
        public ViewHolder(View itemView) {
            super(itemView);

            etSec = (EditText) itemView.findViewById(R.id.etSec);
            tvTimer = (TextView) itemView.findViewById(R.id.tvTimer);
            btnAction = (Button) itemView.findViewById(R.id.btnAction);
        }

        public void onBind(CountDownTimer countDownTimer) {
            tvTimer.setText(getFormattedTime(countDownTimer.secondsInMillis));

            if (countDownTimer.buttonCurrentState == CountDownTimer.STATE_RESUME) {
                pauseTimer(countDownTimer);
            }

            else if (countDownTimer.buttonCurrentState == CountDownTimer.STATE_PAUSE) {
                startTimer(countDownTimer);
            }

            btnAction.setOnClickListener(view -> {
                if (countDownTimer.buttonCurrentState == CountDownTimer.STATE_PAUSE) {
                    pauseTimer(countDownTimer);
                    return;
                }

                if (countDownTimer.buttonCurrentState == CountDownTimer.STATE_RESUME) {
                    startTimer(countDownTimer);
                    return;
                }

                if (etSec.getText() == null || etSec.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Please enter seconds", Toast.LENGTH_SHORT).show();
                    return;
                }

                countDownTimer.secondsInMillis = Long.parseLong(etSec.getText().toString().trim()) * 1000;
                tvTimer.setText(getFormattedTime(countDownTimer.secondsInMillis));
                startTimer(countDownTimer);
            });
        }

        public void startTimer(CountDownTimer countDownTimer) {
            if (countDownTimer.timer != null) {
                countDownTimer.timer.cancel();
            }

            countDownTimer.timer = new android.os.CountDownTimer(countDownTimer.secondsInMillis, 1000) {

                public void onTick(long millisUntilFinished) {
                    countDownTimer.secondsInMillis = millisUntilFinished;
                    tvTimer.setText(getFormattedTime(millisUntilFinished));
                    setButtonState(CountDownTimer.STATE_PAUSE, countDownTimer);
                }

                public void onFinish() {
                    tvTimer.setText("00:00:00");
                    setButtonState(CountDownTimer.STATE_START, countDownTimer);
                }

            }.start();
        }

        public void pauseTimer(CountDownTimer countDownTimer) {
            if (countDownTimer.timer != null) {
                countDownTimer.timer.cancel();
                setButtonState(CountDownTimer.STATE_RESUME, countDownTimer);
            }
        }


        public void setButtonState(int state, CountDownTimer countDownTimer) {
            countDownTimer.buttonCurrentState = state;

            switch (state) {
                case CountDownTimer.STATE_START:
                    btnAction.setText("Start");
                    break;
                case CountDownTimer.STATE_PAUSE:
                    btnAction.setText("Pause");
                    break;
                case CountDownTimer.STATE_RESUME:
                    btnAction.setText("Resume");
                    break;
            }
        }
    }
    private String getFormattedTime(Long millis) {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}

