package com.bravo.android.bravo.ui.view.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.HomeHealingData
import com.bravo.android.bravo.data.remote.source.HealingDataSource
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val healingDataSource: HealingDataSource
): BaseViewModel() {

    private val _action : MutableLiveData<Event<HomeAction>> = MutableLiveData()
    val action : LiveData<Event<HomeAction>> get() = _action

    private val _actionHealing : MutableLiveData<Event<HomeHealingData>> = MutableLiveData()
    val actionHealing : LiveData<Event<HomeHealingData>> get() = _actionHealing

    private val _letterTime : MutableLiveData<String> = MutableLiveData()
    val letterTime : LiveData<String> get() = _letterTime

    private val _letterCheck : MutableLiveData<Boolean> = MutableLiveData(false)
    val letterCheck : LiveData<Boolean> get() = _letterCheck

    private val _homeHealingList : MutableLiveData<List<HomeHealingData>> = MutableLiveData()
    val homeHealingList : LiveData<List<HomeHealingData>> get() = _homeHealingList

    init {
        val nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        _letterTime.value = nowDate.format(nowDate)

        getHomeHealingData()
    }



    private fun getHomeHealingData(){
        healingDataSource.getHomeHealingData()
            .map {
                it.map {
                    HomeHealingData(
                        it.infoToken,
                        it.title,
                        it.img
                    )
                }
            }
            .subscribe({
                _homeHealingList.value = it
            },{
                it.toString()
            })
            .addTo(compositeDisposable)
    }

    fun onClickLetter(){
        _action.value = Event(HomeAction.LETTER)
    }

    fun letterCheckTrue(){
        _letterCheck.value = true
    }

//    fun onClickHealing(item : HomeHealingData){
//        _actionHealing.value = Event(item)
//    }

    enum class HomeAction{
        LETTER
    }
}