package com.bbbrrr8877.totalrecall.core

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bbbrrr8877.common.Mapper
import java.util.concurrent.atomic.AtomicBoolean

interface Communication {

    interface Update<T : Any> : Mapper.Unit<T>

    interface Observe<T : Any> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>) = Unit

        fun liveData(): LiveData<T> = throw IllegalStateException()
    }

    interface Mutable<T : Any> : Update<T>, Observe<T>

    abstract class Abstract<T : Any>(
        private val liveData: MutableLiveData<T> = SingleLiveEvent()
    ) : Mutable<T> {

        override fun map(data: T) {
            liveData.value = data
        }

        @Deprecated("use live data")
        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }

        override fun liveData(): LiveData<T> = liveData
    }
}

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T) {
        mPending.set(true)
        super.setValue(t)
    }
}