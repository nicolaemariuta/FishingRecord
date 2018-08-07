package nicolae.fishingrecord.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Data.FishSpecie;
import nicolae.fishingrecord.Utils.StringUtils;
import nicolae.fishingrecord.R;
import timber.log.Timber;

public class UpdateFishPhotoDataDialog extends DialogFragment {

    public final static String TAG = "UPDATE_FISH_PHOTO_DATA_DIALOG";
    public final static String CURRENT_FISH_PHOTO = "CurrentFishPhoto";

    private FishPicture mFishPicture;

    private Spinner mSpecieSpinner;
    private ArrayAdapter mSpecieSpinnerAdapter;

    private EditText mCatchLocation;
    private EditText mCatchDate;
    private EditText mCatchLength;
    private EditText mCatchWeight;
    private Button mSaveCatch;

    private Calendar myCalendar;

    private UpdateFishPhotoDialogListener listener;


    public static UpdateFishPhotoDataDialog newInstance(FishPicture fishPicture) {
        UpdateFishPhotoDataDialog dialog = new UpdateFishPhotoDataDialog();

        Bundle args = new Bundle();

        args.putSerializable(CURRENT_FISH_PHOTO, fishPicture);
        dialog.setArguments(args);

        return dialog;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFishPicture = (FishPicture) getArguments().getSerializable(CURRENT_FISH_PHOTO);


        try {
            listener = (UpdateFishPhotoDialogListener) getActivity();
        } catch (ClassCastException e) {
            Timber.e(e, "Calling activity must implement UpdateFishPhotoDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog);
        builder.setView(R.layout.dlg_update_photo_data);
        final Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();

        myCalendar = Calendar.getInstance();

        mSpecieSpinner = (Spinner) getDialog().findViewById(R.id.editFishPicture_specie);
        mSpecieSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,FishSpecie.getAllFishNamesList(getContext()));
        mSpecieSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpecieSpinner.setAdapter(mSpecieSpinnerAdapter);


        mCatchLocation = (EditText) getDialog().findViewById(R.id.editFishPicture_location);
        mCatchDate = (EditText) getDialog().findViewById(R.id.editFishPicture_date);
        mCatchLength = (EditText) getDialog().findViewById(R.id.editFishPicture_length);
        mCatchWeight = (EditText) getDialog().findViewById(R.id.editFishPicture_weight);

        mSaveCatch = (Button) getDialog().findViewById(R.id.editFishPicture_save);
        mSaveCatch.setOnClickListener(saveButtonClicked);

        initDataForAlreadyRegisteredFishPicture();

        setupCatchDatePicker();
    }


    private void initDataForAlreadyRegisteredFishPicture() {
        if(mFishPicture.getSpecie() != null)
            mSpecieSpinner.setSelection(mFishPicture.getSpecie().ordinal());

        if(!StringUtils.isEmpty(mFishPicture.getCatchLocation()))
            mCatchLocation.setText(mFishPicture.getCatchLocation());


        if(mFishPicture.getCatchDate() != null){
            mFishPicture.getCatchDate().toCalendar(getResources().getConfiguration().locale);
            updateLabel();
        }

        if(!StringUtils.isEmpty(mFishPicture.getLength()))
            mCatchLength.setText(mFishPicture.getLength());

        if(!StringUtils.isEmpty(mFishPicture.getWeight()))
            mCatchWeight.setText(mFishPicture.getWeight());




    }


    private void setupCatchDatePicker() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mCatchDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        mCatchDate.setText(sdf.format(myCalendar.getTime()));
    }


    private View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mSaveCatch.setActivated(false);

            if(mSpecieSpinner.getSelectedItem() != null)
                mFishPicture.setSpecie(FishSpecie.values()[mSpecieSpinnerAdapter.getPosition(mSpecieSpinner.getSelectedItem())]);

            mFishPicture.setCatchDate(new DateTime(myCalendar));
            mFishPicture.setCatchLocation(mCatchLocation.getText().toString());
            mFishPicture.setLength(mCatchLength.getText().toString());
            mFishPicture.setWeight(mCatchWeight.getText().toString());

            dismiss();

            listener.fishDataSaved(mFishPicture);
        }
    };


    public interface UpdateFishPhotoDialogListener {
        void fishDataSaved(FishPicture fishPicture);
    }


}
