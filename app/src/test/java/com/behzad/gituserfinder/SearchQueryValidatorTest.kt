package com.behzad.gituserfinder

import com.behzad.gituserfinder.features.user.search.isValidSearchQuery
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SearchQueryValidatorTest {
    @Test
    fun searchQueryValidator_ValidSearchQuery_ReturnTrue() {
        assertTrue("be".isValidSearchQuery())
    }
    @Test
    fun searchQueryValidator_TooShortQuery_ReturnFalse() {
        assertFalse("b".isValidSearchQuery())
    }
    @Test
    fun searchQueryValidator_TooShort_ReturnFalse() {
        assertFalse("   ".isValidSearchQuery())
    }
}