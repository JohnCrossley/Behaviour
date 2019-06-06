package com.jccworld.behaviour.addchild

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccworld.behaviour.database.repository.ChildRepository
import com.jccworld.behaviour.domain.Child
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddChildViewModel(private val childRepository: ChildRepository): ViewModel(), Observable {

    val state = MutableLiveData<State>()
    val valid = MediatorLiveData<Boolean>()
    var firstName = ObservableField<String>()
    var lastName: String? = null

    private val subscriptions = CompositeDisposable()

    @Transient
    private var callbacks: PropertyChangeRegistry? = null

    init {
        state.value = State.READY
        valid.value = false

        val validationCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                valid.value = validate()
            }
        }

        firstName.addOnPropertyChangedCallback(validationCallback)
    }

    private fun validate(): Boolean {
        with(firstName.get()) {
            if (this == null) return false
            return this.isNotBlank()
        }
    }

    fun submit() {
        subscriptions.add(childRepository.add(Child(firstName = firstName.get()!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ state.value = State.SUCCESS },
                    { t ->
                        state.value = State.FAIL
                        Log.e(TAG, "Failed to load children", t)
                    }
                )
        )
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (callbacks == null) {
                callbacks = PropertyChangeRegistry()
            }
        }
        callbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.remove(callback)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    companion object {
        const val TAG = "AddChildViewModel"
    }
}