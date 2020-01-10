package com.jccworld.behaviour.managechildren

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccworld.behaviour.database.repository.ChildRepository
import com.jccworld.behaviour.domain.Child
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ManageChildrenViewModel @Inject constructor(childRepository: ChildRepository): ViewModel() {

    val state = MutableLiveData<State>()
    val children = MutableLiveData<List<Child>>()

    private val subscriptions = CompositeDisposable()

    init {
        subscriptions.add(childRepository.all()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {state.postValue(State.LOADING) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        this.children.value = it
                        state.value = State.READY
                    })
                    { t ->
                        state.value = State.FAIL
                        Log.e(TAG, "JCC Couldn't load children", t)
                    }
                )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    companion object {
        const val TAG = "ManageChildrenViewModel"
    }

}
