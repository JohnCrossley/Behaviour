package com.jccworld.behaviour.debug

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccworld.behaviour.database.repository.BehaviourRepository
import com.jccworld.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccworld.behaviour.database.repository.ChildRepository
import com.jccworld.behaviour.domain.Behaviour
import com.jccworld.behaviour.domain.Child
import com.jccworld.behaviour.domain.Dichotomy
import com.jccworld.behaviour.domain.Recording
import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import java.lang.RuntimeException
import java.util.*
import javax.inject.Inject

class DebugViewModel @Inject constructor(
    private val behaviourRepository: BehaviourRepository,
    private val childRepository: ChildRepository,
    private val recordRepository: ChildBehaviourRecordRepository
) : ViewModel() {

    val log = ReplaySubject.create<String>()

    val state = MutableLiveData<State>()
    val children = MutableLiveData<List<Child>>()

    private val subscriptions = CompositeDisposable()

    fun addDebugData() {
        val behaviour = Behaviour(dichotomy = Dichotomy.GOOD, name = "Tidied up", stars = 1)
        val child = Child(firstName = "Emily")

        data class BehaviourChildPair(val behaviourId: Long, val childId: Long)
        data class Combo(val behaviourId: Long, val childId: Long, val date: Calendar)

        subscriptions.add(
            Observables.combineLatest(
                zip(behaviourRepository.add(behaviour).toObservable(),
                    childRepository.add(child).toObservable(),
                    BiFunction<Long, Long, BehaviourChildPair> { behaviourId, childId ->
                        BehaviourChildPair(behaviourId, childId)
                    }),
                o()
            ) { bc, time ->
                Combo(behaviourId = bc.behaviourId, childId = bc.childId, date = time)
            }.flatMap { combo ->
                recordRepository.add(
                    Recording(
                        Dichotomy.GOOD,
                        listOf(child.copy(id = combo.childId)),
                        stars = 1,
                        time = combo.date,
                        behaviourId = combo.behaviourId
                    )
                ).toObservable()
            }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { id ->
                    println("[JCC] DebugViewModel.addDebugData $id")
                },
                {

                }
            )
        )
    }

    fun o(): Observable<Calendar> {
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.MONTH, Calendar.JANUARY)

        return create { emitter ->
            while (calendar.before(now)) {
                emitter.onNext(calendar)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            emitter.onComplete()
        }
    }

    fun logChildren() {
        subscriptions.add(childRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { log.onNext("Children:") }
            .toObservable().subscribe(
                { child ->
                    log.onNext(child.toString())
                },
                { e ->
                    log.onError(RuntimeException("Read children error $e"))
                }
            )
        )
    }

    fun logBehaviours() {
        subscriptions.add(behaviourRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { log.onNext("Behaviours:") }
            .toObservable().subscribe(
                { behaviour ->
                    log.onNext(behaviour.toString())
                },
                { e ->
                    log.onError(RuntimeException("Read behaviour error $e"))
                }
            )
        )
    }

    fun logRecords() {
        subscriptions.add(recordRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { log.onNext("Records:") }
            .toObservable().subscribe(
                { record ->
                    log.onNext(record.toString())
                },
                { e ->
                    log.onError(RuntimeException("Read records error $e"))
                }
            )
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
