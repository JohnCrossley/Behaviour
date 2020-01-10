package com.jccworld.behaviour.debug

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccworld.behaviour.database.repository.ChildRepository
import com.jccworld.behaviour.domain.Child
import io.reactivex.disposables.CompositeDisposable

class DebugViewModel(val childRepository: ChildRepository): ViewModel() {

    val state = MutableLiveData<State>()
    val children = MutableLiveData<List<Child>>()

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    companion object {
        const val TAG = "ManageChildrenViewModel"
    }

}
