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

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

/**
 * Activity which concludes fingerprint enrollment.
 */
public class FingerprintEnrollFinish extends FingerprintEnrollBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_enroll_finish);
        setHeaderText(R.string.security_settings_fingerprint_enroll_finish_title);
        findViewById(R.id.add_another_button).setOnClickListener(this);
    }

    @Override
    protected void onNextButtonClick() {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_another_button) {
            finish();
            startActivity(getEnrollingIntent());
        }
        super.onClick(v);
    }
}
