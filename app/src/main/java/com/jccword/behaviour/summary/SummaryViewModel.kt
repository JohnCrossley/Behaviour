package com.jccword.behaviour.summary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccword.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccword.behaviour.domain.Recording
import com.jccword.behaviour.what.WhatViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SummaryViewModel(private val childBehaviourRecordRepository: ChildBehaviourRecordRepository) : ViewModel() {

    val state = MutableLiveData<State>()

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    fun save(recording: Recording) {
        subscriptions.add(childBehaviourRecordRepository.add(recording)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = State.SAVING }
                .subscribe({
                    state.value = State.SAVED })
                { t ->
                    state.value = State.FAIL
                    Log.e(WhatViewModel.TAG, "Failed to save record", t)
                }
        )
    }

    companion object {
        const val TAG = "SummaryViewModel"
    }
}
