package com.jccworld.behaviour

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccworld.behaviour.database.repository.ChildRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BootViewModel(childRepository: ChildRepository): ViewModel() {
    private val subscriptions = CompositeDisposable()

    val state = MutableLiveData<State>()

    init {
        state.value = State.BOOTING

        subscriptions.add(
                childRepository.needToAddChildren()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ needToAddChildren: Boolean ->
                                when {
                                    needToAddChildren -> state.value = State.NO_CHILDREN_IN_DB
                                    else -> state.value = State.CHILDREN_IN_DB
                                }
                            },
                            { state.value = State.FAILED_TO_DETERMINE }
                        )
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}
