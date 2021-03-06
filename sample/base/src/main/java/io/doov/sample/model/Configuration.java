/*
 * Copyright 2017 Courtanet
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
package io.doov.sample.model;

import io.doov.sample.field.SampleFieldId;
import io.doov.sample.field.SamplePath;

public class Configuration {

    @SamplePath(field = SampleFieldId.CONFIGURATION_MAILING_CAMPAIGN, readable = "configuration.mailing.campaign")
    private boolean mailingCampaign;

    @SamplePath(field = SampleFieldId.CONFIGURATION_EMAIL_MAX_SIZE, readable = "configuration.max.email.size")
    private int maxEmailSize;

    @SamplePath(field = SampleFieldId.CONFIGURATION_MIN_AGE, readable = "configuration.min.age")
    private int minAge;

    @SamplePath(field = SampleFieldId.CONFIGURATION_MAX_DOUBLE, readable = "configuration.max.double")
    private double maxDouble;

    @SamplePath(field = SampleFieldId.CONFIGURATION_MAX_FLOAT, readable = "configuration.max.float")
    private float maxFloat;

    @SamplePath(field = SampleFieldId.CONFIGURATION_MAX_LONG, readable = "configuration.max.long")
    private long maxLong;

    public boolean isMailingCampaign() {
        return mailingCampaign;
    }

    public void setMailingCampaign(boolean mailingCampaign) {
        this.mailingCampaign = mailingCampaign;
    }

    public int getMaxEmailSize() {
        return maxEmailSize;
    }

    public void setMaxEmailSize(int maxEmailSize) {
        this.maxEmailSize = maxEmailSize;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public double getMaxDouble() {
        return maxDouble;
    }

    public void setMaxDouble(double maxDouble) {
        this.maxDouble = maxDouble;
    }

    public float getMaxFloat() {
        return maxFloat;
    }

    public void setMaxFloat(float maxFloat) {
        this.maxFloat = maxFloat;
    }

    public long getMaxLong() {
        return maxLong;
    }

    public void setMaxLong(long maxLong) {
        this.maxLong = maxLong;
    }

}
