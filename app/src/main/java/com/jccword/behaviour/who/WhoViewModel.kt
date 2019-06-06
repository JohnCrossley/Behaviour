package com.jccword.behaviour.who

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccword.behaviour.database.repository.ChildRepository
import com.jccword.behaviour.domain.Child
import com.jccword.behaviour.domain.Dichotomy
import com.jccword.behaviour.domain.Recording
import com.jccword.behaviour.domain.UserSession
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.IllegalArgumentException

class WhoViewModel(private val userSession: UserSession, childRepository: ChildRepository) : ViewModel() {

    val state = MutableLiveData<State>()
    val valid = MediatorLiveData<Boolean>()
    var dichotomy = MutableLiveData<Dichotomy>()
    val children = MutableLiveData<List<Child>>()
    val selected = MutableLiveData<List<Child>>()
    val good = MutableLiveData<Boolean>()
    val bad = MutableLiveData<Boolean>()

    private val subscriptions = CompositeDisposable()

    init {
        valid.value = false

        subscriptions.add(childRepository.all()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = State.LOADING }
                .subscribe({
                    this.children.value = it
                    state.value = State.READY
                })
                { t ->
                    state.value = State.FAIL
                    Log.e(TAG, "Failed to load children", t)
                }
            )

        dichotomy.observeForever {
            when (it) {
                Dichotomy.GOOD -> {
                    good.value = true
                    bad.value = false

                }
                Dichotomy.BAD -> {
                    bad.value = true
                    good.value = false
                }
                else -> IllegalArgumentException("Invalid dichotomy $it")
            }
        }

        valid.addSource(selected) { valid.value = validate() }
        valid.addSource(dichotomy) { valid.value = validate() }
    }

    @Synchronized
    private fun validate(): Boolean {
        val sel = selected.value
        val dic = dichotomy.value

        if (dic == null || sel == null || sel.isEmpty())
            return false

        return true
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    fun save(body: () -> Unit) {
        userSession.recording = Recording(dichotomy.value ?: throw IllegalArgumentException("Dichotomy shouldn't be null"), selected.value ?: throw IllegalArgumentException("Children IDs shouldn't be null"))
        body.invoke()
    }

    companion object {
        const val TAG = "WhoViewModel"
    }
}
