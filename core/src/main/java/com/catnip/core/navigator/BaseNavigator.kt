package com.catnip.core.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.parcelize.Parcelize
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Suppress("UNCHECKED_CAST")
abstract class BaseNavigator<Args : Parcelable, Result : Parcelable>(private val activityClass: Class<out Activity>) :
    ActivityResultContract<Args?, Result?>() {
    companion object {
        private const val INTENT_ARGS = "contract.intent.args"
        private const val INTENT_RESULT = "contract.intent.result"
    }


    override fun createIntent(context: Context, input: Args?): Intent =
        Intent(context, activityClass).apply {
            putExtra(INTENT_ARGS, input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Result? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.let { intent.getParcelableExtra(INTENT_RESULT) as Result? }
    }

    fun parseStartIntent(intent: Intent): Args {
        return requireNotNull(intent.getParcelableExtra(INTENT_ARGS)) as Args
    }

    fun parseOptionalStartIntent(intent: Intent): Args? {
        return intent.getParcelableExtra(INTENT_ARGS) as Args?
    }

    fun createResultIntent(result: Result): Intent {
        return Intent().apply {
            putExtra(INTENT_RESULT, result)
        }
    }
}

inline fun <reified Args : Parcelable> Activity.contractArgs(crossinline lazyContract: () -> BaseNavigator<Args, *>): ReadOnlyProperty<Activity, Args> {
    return object : ReadOnlyProperty<Any, Args> {
        private var value: Args? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Args {
            if (value == null) {
                value = lazyContract().parseStartIntent(intent)
            }
            return value ?: throw RuntimeException("Missing contract args")
        }
    }
}

@Parcelize
class NoArgs : Parcelable

@Parcelize
class NoResult : Parcelable