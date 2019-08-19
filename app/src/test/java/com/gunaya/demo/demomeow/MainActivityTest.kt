package com.gunaya.demo.demomeow


import androidx.test.core.app.ActivityScenario
import com.gunaya.demo.demomeow.presentation.main.MainActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(IherbTestRunner::class)
class MainActivityTest {
    private lateinit var testObj: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        testObj = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testCompanyName() {
        testObj.onActivity {
            val size = it.viewModel.catsList.value?.size
            Assert.assertTrue(1 == size)
        }
    }
}
