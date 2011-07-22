/*
 * Copyright (C) 2011 The Android Open Source Project
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
 * limitations under the License.
 */

package com.android.contacts.model;

import com.android.contacts.tests.R;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * Test case for {@link AccountType}.
 *
 * adb shell am instrument -w -e class com.android.contacts.model.AccountTypeTest \
       com.android.contacts.tests/android.test.InstrumentationTestRunner
 */
public class AccountTypeTest extends AndroidTestCase {
    public void testGetResourceText() {
        // In this test we use the test package itself as an external package.
        final String packageName = getTestContext().getPackageName();

        final Context c = getContext();
        final String DEFAULT = "ABC";

        // Package name null, resId -1, use the default
        assertEquals(DEFAULT, AccountType.getResourceText(c, null, -1, DEFAULT));

        // Resource ID -1, use the default
        assertEquals(DEFAULT, AccountType.getResourceText(c, packageName, -1, DEFAULT));

        // Load from an external package.  (here, we use this test package itself)
        final int externalResID = R.string.test_string;
        assertEquals(getTestContext().getString(externalResID),
                AccountType.getResourceText(c, packageName, externalResID, DEFAULT));

        // Load from the contacts package itself.
        final int internalResId = com.android.contacts.R.string.sharedUserLabel;
        assertEquals(c.getString(internalResId),
                AccountType.getResourceText(c, null, internalResId, DEFAULT));
    }

    /**
     * Verify if {@link AccountType#getInviteContactActionLabel} correctly gets the resource ID
     * from {@link AccountType#getInviteContactActionResId}
     */
    public void testGetInviteContactActionLabel() {
        final String packageName = getTestContext().getPackageName();
        final Context c = getContext();

        final int externalResID = R.string.test_string;

        AccountType accountType = new AccountType() {
            {
                resPackageName = packageName;
                summaryResPackageName = packageName;
            }
            @Override protected int getInviteContactActionResId(Context conext) {
                return externalResID;
            }

            @Override public int getHeaderColor(Context context) {
                return 0;
            }

            @Override public int getSideBarColor(Context context) {
                return 0;
            }

            @Override public boolean isGroupMembershipEditable() {
                return false;
            }
        };

        assertEquals(getTestContext().getString(externalResID),
                accountType.getInviteContactActionLabel(c));
    }
}