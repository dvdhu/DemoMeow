package com.gunaya.demo.demomeow;

import android.os.Build;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

public class IherbTestRunner extends RobolectricTestRunner{
    public IherbTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }


    @Override
    protected Config buildGlobalConfig() {
        return new Config.Builder()
                .setSdk(Build.VERSION_CODES.M)
                .setApplication(MockApplication.class)
                .build();
    }
}
