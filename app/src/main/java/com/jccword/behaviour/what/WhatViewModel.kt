package com.jccword.behaviour.what

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccword.behaviour.database.InitDatabase
import com.jccword.behaviour.database.repository.BehaviourRepository
import com.jccword.behaviour.domain.Behaviour
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WhatViewModel(behaviourRepository: BehaviourRepository, private val initDatabase: InitDatabase) : ViewModel() {

    val state = MediatorLiveData<State>()
    val valid = MediatorLiveData<Boolean>()
    val behaviours = MutableLiveData<List<Behaviour>>()
    val selected = MutableLiveData<Long>()

    private val subscriptions = CompositeDisposable()

    init {
        valid.value = false

        subscriptions.add(behaviourRepository.all()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = State.LOADING }
                .subscribe({
                    if (it.isEmpty()) {
                        state.value = State.NO_BEHAVIOURS_IN_DB
                    } else {
                        this.behaviours.value = it
                        state.value = State.READY
                    }
                })
                { t ->
                    state.value = State.FAIL
                    Log.e(TAG, "Failed to load behaviours", t)
                }
        )

        valid.addSource(selected) { valid.value = true }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    fun addDefaultBehaviours() {
        subscriptions.add(initDatabase.addDefaultBehaviours()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = State.LOADING }
                .subscribe({ state.value = State.READY })
                { t ->
                    state.value = State.FAIL
                    Log.e(TAG, "Failed to add default behaviours", t)
                }
            )
    }

    companion object {
        const val TAG = "WhatViewModel"
    }
}
