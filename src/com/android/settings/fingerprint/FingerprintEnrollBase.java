/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.settings.fingerprint;

import android.annotation.Nullable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.android.settings.ChooseLockSettingsHelper;
import com.android.settings.R;
import com.android.setupwizardlib.SetupWizardLayout;
import com.android.setupwizardlib.view.NavigationBar;

/**
 * Base activity for all fingerprint enrollment steps.
 */
public class FingerprintEnrollBase extends Activity implements View.OnClickListener {

    /**
     * Used by the choose fingerprint wizard to indicate the wizard is
     * finished, and each activity in the wizard should finish.
     * <p>
     * Previously, each activity in the wizard would finish itself after
     * starting the next activity. However, this leads to broken 'Back'
     * behavior. So, now an activity does not finish itself until it gets this
     * result.
     */
    protected static final int RESULT_FINISHED = RESULT_FIRST_USER;

    protected byte[] mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_FingerprintEnroll);
        mToken = getIntent().getByteArrayExtra(
                ChooseLockSettingsHelper.EXTRA_KEY_CHALLENGE_TOKEN);
        if (savedInstanceState != null && mToken == null) {
            mToken = savedInstanceState.getByteArray(
                    ChooseLockSettingsHelper.EXTRA_KEY_CHALLENGE_TOKEN);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putByteArray(ChooseLockSettingsHelper.EXTRA_KEY_CHALLENGE_TOKEN, mToken);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getNavigationBar().setVisibility(View.GONE);
        Button nextButton = getNextButton();
        if (nextButton != null) {
            nextButton.setOnClickListener(this);
        }
    }

    protected NavigationBar getNavigationBar() {
        return (NavigationBar) findViewById(R.id.suw_layout_navigation_bar);
    }

    protected SetupWizardLayout getSetupWizardLayout() {
        return (SetupWizardLayout) findViewById(R.id.setup_wizard_layout);
    }

    protected void setHeaderText(int resId) {
        getSetupWizardLayout().setHeaderText(getText(resId));
    }

    protected Button getNextButton() {
        return (Button) findViewById(R.id.next_button);
    }

    @Override
    public void onClick(View v) {
        if (v == getNextButton()) {
            onNextButtonClick();
        }
    }

    protected void onNextButtonClick() {
    }

    protected Intent getEnrollingIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", FingerprintEnrollEnrolling.class.getName());
        intent.putExtra(ChooseLockSettingsHelper.EXTRA_KEY_CHALLENGE_TOKEN, mToken);
        return intent;
    }
}
