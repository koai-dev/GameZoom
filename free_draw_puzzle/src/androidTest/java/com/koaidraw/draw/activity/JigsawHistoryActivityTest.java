/*
 * Copyright (c) 2018. Jay Paulynice (jay.paulynice@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.koaidraw.draw.activity;


import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class JigsawHistoryActivityTest {
    private JigsawHistoryActivity activity;

    @Rule
    public ActivityTestRule<JigsawHistoryActivity> mActivityRule = new ActivityTestRule<>(JigsawHistoryActivity.class);

    @Before
    public void init() {
        activity = mActivityRule.getActivity();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }
}