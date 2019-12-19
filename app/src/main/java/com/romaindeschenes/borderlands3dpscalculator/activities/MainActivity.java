package com.romaindeschenes.borderlands3dpscalculator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.romaindeschenes.borderlands3dpscalculator.R;
import com.romaindeschenes.borderlands3dpscalculator.models.Weapon;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextDamage;
    EditText mEditTextReloadTime;
    EditText mEditTextFireRate;
    EditText mEditTextMagazineSize;

    TextView mTextViewTimeToEmptyMagazine;
    TextView mTextViewDamagePerSecond;

    Button mButton;

    private Weapon mWeapon = new Weapon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEditTextDamage = findViewById(R.id.editTextDamage);
        mEditTextReloadTime = findViewById(R.id.editTextReloadTime);
        mEditTextFireRate = findViewById(R.id.editTextFireRate);
        mEditTextMagazineSize = findViewById(R.id.editTextMagazineSize);

        mTextViewTimeToEmptyMagazine = findViewById(R.id.textViewEmptyMagazine);
        mTextViewDamagePerSecond = findViewById(R.id.textViewDamagePerSecond);

        mButton = findViewById(R.id.autoScanButton);

        initListeners();
    }

    protected void initListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateWeapon();
                updateTextViews();
            }
        };
        mEditTextDamage.addTextChangedListener(textWatcher);
        mEditTextReloadTime.addTextChangedListener(textWatcher);
        mEditTextFireRate.addTextChangedListener(textWatcher);
        mEditTextMagazineSize.addTextChangedListener(textWatcher);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                mWeapon = (Weapon)data.getSerializableExtra("weapon");
                if (mWeapon != null) {
                    mEditTextDamage.setText(String.valueOf(mWeapon.getDamage()));
                    mEditTextReloadTime.setText(String.valueOf(mWeapon.getReloadTime()));
                    mEditTextFireRate.setText(String.valueOf(mWeapon.getFireRate()));
                    mEditTextMagazineSize.setText(String.valueOf(mWeapon.getMagazineSize()));
                    updateTextViews();
                }
            }
        }
    }

    private void updateWeapon() {
        try {
            int damage = Integer.parseInt(mEditTextDamage.getText().toString());
            float reloadTime = Float.parseFloat(mEditTextReloadTime.getText().toString());
            float fireRate = Float.parseFloat(mEditTextFireRate.getText().toString());
            int magazineSize = Integer.parseInt(mEditTextMagazineSize.getText().toString());

            // Get all values
            mWeapon.setDamage(damage);
            mWeapon.setReloadTime(reloadTime);
            mWeapon.setFireRate(fireRate);
            mWeapon.setMagazineSize(magazineSize);
        } catch (NumberFormatException e) {
        }
    }

    protected void updateTextViews() {
        mTextViewDamagePerSecond.setText(String.valueOf(mWeapon.getDamagePerSecond()));
        mTextViewTimeToEmptyMagazine.setText(String.valueOf(mWeapon.getTimeToEmptyMagazine()));
    }
}