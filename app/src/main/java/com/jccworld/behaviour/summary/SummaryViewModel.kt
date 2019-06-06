package com.jccworld.behaviour.summary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccworld.behaviour.database.entity.ChildBehaviourRecordEntity
import com.jccworld.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccworld.behaviour.domain.UserSession
import com.jccworld.behaviour.what.WhatViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SummaryViewModel(private val userSession: UserSession, private val childBehaviourRecordRepository: ChildBehaviourRecordRepository) : ViewModel() {

    val state = MutableLiveData<State>()
    val summary = MutableLiveData<Map<Long,List<ChildBehaviourRecordEntity>>>()

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    fun save() {
        subscriptions.add(childBehaviourRecordRepository.add(userSession.recording ?: throw IllegalArgumentException("recording is null"))
                .flatMap{
                    val ids = userSession.recording?.children?.map { it.id } ?: throw IllegalArgumentException("children is null")
                    state.postValue(State.SAVED)
                    childBehaviourRecordRepository.thisWeek(ids)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = State.SAVING }
                .subscribe({
                    summary.value = it
                    state.value = State.FETCHED_SUMMARY
                })
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
