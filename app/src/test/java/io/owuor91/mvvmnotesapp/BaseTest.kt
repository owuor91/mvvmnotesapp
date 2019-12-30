package io.owuor91.mvvmnotesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

}