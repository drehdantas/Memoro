package com.project.andredantas.memoro.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.project.andredantas.memoro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DialogDateFragment extends DialogFragment implements
        OnClickListener {

    private NumberPicker pickerDiaNascimento;
    private NumberPicker pickerMesNascimento;
    private TextView dataNascimentoATual;
    private TextView erroData;
    private Button positiveButton;
    private Button negativeButton;

    private DialogDatePickerListener mDialogDatePickerListener;
    private String dataNascimento;

    private Calendar c;

    public DialogDateFragment(){

    }

    public void setListenerDatePicker(DialogDatePickerListener mDialogDatePickerListener, String dataNascimento){
        this.mDialogDatePickerListener = mDialogDatePickerListener;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogData = new Dialog(getActivity());
        dialogData.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogData.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogData.setContentView(R.layout.popup_data_horario);
        dialogData.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialogData.show();

        pickerDiaNascimento = (NumberPicker) dialogData.findViewById(
                R.id.pickerDiaNascimento);
        pickerMesNascimento = (NumberPicker) dialogData.findViewById(R.id.pickerMesNascimento);
//        pickerAnoNascimento = (NumberPicker) dialogData.findViewById(R.id.pickerAnoNascimento);
        erroData = (TextView) dialogData.findViewById(R.id.errorMessage);
        dataNascimentoATual = (TextView) dialogData.findViewById(R.id.dataAtual);
        positiveButton = (Button) dialogData.findViewById(R.id.positiveButton);
        negativeButton = (Button) dialogData.findViewById(R.id.negativeButton);

        c = Calendar.getInstance();

        pickerDiaNascimento.setMinValue(1);
        pickerDiaNascimento.setMaxValue(31);
        pickerDiaNascimento.setValue(c.get(Calendar.DAY_OF_MONTH));

        pickerMesNascimento.setMinValue(1);
        pickerMesNascimento.setMaxValue(12);

        pickerMesNascimento.setValue(c.get(Calendar.MONTH)+1);

//        pickerAnoNascimento.setMinValue(1900);
//        pickerAnoNascimento.setMaxValue(2016);
//        pickerAnoNascimento.setValue(c.get(Calendar.YEAR));

        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);

        atualizar();

        return dialogData;
    }

    @Override
    public void onClick(View v) {
        c.set(Calendar.DAY_OF_MONTH, pickerDiaNascimento.getValue());
        c.set(Calendar.MONTH, pickerMesNascimento.getValue() - 1);
//        c.set(Calendar.YEAR, pickerAnoNascimento.getValue());

        if (v == negativeButton) {
            this.dismiss();
        }else if (v == positiveButton) {
//            if(validaData()) {
                erroData.setVisibility(View.GONE);
                mDialogDatePickerListener.onFinishPopupDataHorario(c);
                this.dismiss();
//            }else{
//                erroData.setVisibility(View.VISIBLE);
//            }
        }
    }

    public void atualizar() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            if(dataNascimento != null && dataNascimento.length() > 0) {
                dataNascimentoATual.setVisibility(View.VISIBLE);
                dataNascimentoATual.setText("Data atual: " +dataNascimento);
                c.setTime(sdf.parse(dataNascimento));
            }else{
                dataNascimentoATual.setVisibility(View.GONE);
            }
        }catch (ParseException e){

        }
    }

    private boolean validaData(){
        Calendar now = Calendar.getInstance();
        if((pickerDiaNascimento.getValue() > now.get(Calendar.DAY_OF_MONTH) &&
                pickerMesNascimento.getValue() == (now.get(Calendar.MONTH) + 1) &&
//                pickerAnoNascimento.getValue() >= now.get(Calendar.YEAR)) ||
                (pickerMesNascimento.getValue() > (now.get(Calendar.MONTH) + 1) )))
//                        && pickerAnoNascimento.getValue() >= now.get(Calendar.YEAR)))
        {
            return false;
        }


        switch (pickerMesNascimento.getValue()){
            case 1:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
            case 2:
                if(pickerDiaNascimento.getValue() <= 28){
                    return true;
                }else if(pickerDiaNascimento.getValue() <= 29){
//                    if(pickerAnoNascimento.getValue() % 4 == 0){
//                        return true;
//                    }
                }
                return false;
            case 3:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
            case 4:
                if(pickerDiaNascimento.getValue() <= 30){
                    return true;
                }
                return false;
            case 5:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
            case 6:
                if(pickerDiaNascimento.getValue() <= 30){
                    return true;
                }
                return false;
            case 7:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
            case 8:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
            case 9:
                if(pickerDiaNascimento.getValue() <= 30){
                    return true;
                }
                return false;
            case 10:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
            case 11:
                if(pickerDiaNascimento.getValue() <= 30){
                    return true;
                }
                return false;
            case 12:
                if(pickerDiaNascimento.getValue() <= 31){
                    return true;
                }
                return false;
        }
        return true;
    }
}