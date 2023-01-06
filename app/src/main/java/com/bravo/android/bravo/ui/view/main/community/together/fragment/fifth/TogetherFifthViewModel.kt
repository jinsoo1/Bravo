package com.bravo.android.bravo.ui.view.main.community.together.fragment.fifth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.fourth.TogetherFourthViewModel
import com.bravo.android.bravo.util.Event
import io.reactivex.Single
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TogetherFifthViewModel : BaseViewModel() {

    private val _action : MutableLiveData<Event<TogetherFifthAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherFifthAction>> get() = _action

    private val _month : MutableLiveData<String> = MutableLiveData()
    val month : LiveData<String> get() = _month

    private val _day : MutableLiveData<String> = MutableLiveData()
    val day : LiveData<String> get() = _day

    private val _time : MutableLiveData<String> = MutableLiveData("1700")
    val time : LiveData<String> get() = _time

    //false = 일회성, true = 정기활동
    private val _activityState : MutableLiveData<Boolean> = MutableLiveData(false)
    val activityState : LiveData<Boolean> get() = _activityState

    //false = 매일, true = 요일선택
    private val _dayState : MutableLiveData<Boolean> = MutableLiveData(false)
    val dayState : LiveData<Boolean>get() = _dayState

    private val _dayList : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val dayList : LiveData<MutableList<String>> get() = _dayList

    private val _dayListState : MutableLiveData<String> = MutableLiveData()
    val dayListState : LiveData<String> get() = _dayListState

    init {
        val current = LocalDateTime.now()
        val formatterM = DateTimeFormatter.ofPattern("MM")
        val formatterD = DateTimeFormatter.ofPattern("dd")

        _month.value = current.format(formatterM)
        _day.value = current.format(formatterD)
    }

    fun setOnActivityState(state : Boolean){
        _activityState.value = state
    }

    fun setOnDayState(state : Boolean){
        _dayState.value = state
    }

    fun setOnDayList(day : String){

        if(_dayList.value!!.contains(day)){
            _dayList.value!!.remove(day)
        }else{
            if(_dayList.value!!.size == 5){
                toast("요일선택은 최대 5개입니다")?.show()
                return
            }
            _dayList.value!!.add(day)
        }

        _dayList.value = _dayList.value
        Log.d("asdasdsdaf", _dayList.value.toString())
    }

    fun settingAllDayList(state : String){
        _dayListState.value = state
    }

    fun sortDayList(){
        _dayListState.value = "0000000"
        val orgStr = StringBuffer(_dayListState.value!!)
        _dayList.value!!.forEach {
            when(it){
                "월" -> _dayListState.value = orgStr.also { it.setCharAt(0, '월') }.toString()
                "화" -> _dayListState.value = orgStr.also { it.setCharAt(1, '화') }.toString()
                "수" -> _dayListState.value = orgStr.also { it.setCharAt(2, '수') }.toString()
                "목" -> _dayListState.value = orgStr.also { it.setCharAt(3, '목') }.toString()
                "금" -> _dayListState.value = orgStr.also { it.setCharAt(4, '금') }.toString()
                "토" -> _dayListState.value = orgStr.also { it.setCharAt(5, '토') }.toString()
                "일" -> _dayListState.value = orgStr.also { it.setCharAt(6, '일') }.toString()
            }
        }
        _dayListState.value = _dayListState.value!!.replace("0","")

    }


    fun onMonthPlus(){
        if(_month.value!!.toInt() < 12) {
            _month.value = _month.value?.toInt()?.plus(1).toString()
        }
        val mm = _month.value!!.toInt()
        val dd = _day.value!!.toInt()
        if(mm == 4 || mm == 6 || mm == 9 || mm == 11){
            if(dd == 31){
                _day.value = "30"
            }
        } else if(mm == 2){
            if(dd > 28){
                _day.value = "28"
            }
        }
    }
    fun onMonthMinus(){
        if(_month.value!!.toInt() > 1){
            _month.value = _month.value?.toInt()?.minus(1).toString()
        }
        val mm = _month.value!!.toInt()
        val dd = _day.value!!.toInt()
        if(mm == 4 || mm == 6 || mm == 9 || mm == 11){
            if(dd == 31){
                _day.value = "30"
            }
        } else if(mm == 2){
            if(dd > 28){
                _day.value = "28"
            }
        }
    }

    fun onDayPlus(){
        val mm = _month.value!!.toInt()
        if(mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12){
            if(_day.value!!.toInt() < 31){
                _day.value = _day.value?.toInt()?.plus(1).toString()
            }
        } else if(mm == 2){
            if(_day.value!!.toInt() < 28){
                _day.value = _day.value?.toInt()?.plus(1).toString()
            }
        }else{
            if(_day.value!!.toInt() < 30){
                _day.value = _day.value?.toInt()?.plus(1).toString()
            }
        }
    }

    fun onDayMinus(){
        if(_day.value!!.toInt() > 1){
            _day.value = _day.value?.toInt()?.minus(1).toString()
        }
    }

    fun onTimePlus(){
        if(_time.value!!.toInt() < 1000){
            if(_time.value!!.toInt() < 100){
                _time.value = "00" + _time.value
            }else {
                _time.value = "0" + _time.value
            }
        }
        if(_time.value!!.toInt() < 2330){
            if(_time.value!!.substring(2 until 4).toInt() == 30){
                _time.value = _time.value!!.toInt().plus(70).toString()
            }else{
                _time.value = _time.value!!.toInt().plus(30).toString()
            }
        }

        //substring(2 until 4)
    }

    fun onTimeMinus(){
        if(_time.value!!.toInt() < 1000){
            if(_time.value!!.toInt() < 100){
                _time.value = "00" + _time.value
            }else{
                _time.value = "0" + _time.value
            }

        }
        if(_time.value!!.toInt() > 0){
            if(_time.value!!.substring(2 until 4).toInt() == 30){
                if(_time.value == "0030"){
                    _time.value = "0000"
                }else{
                    _time.value = _time.value!!.toInt().minus(30).toString()
                }

            }else{
                _time.value = _time.value!!.toInt().minus(70).toString()
            }
        }

    }

    fun setOnNextAction(){
        if(!_activityState.value!!){
            _action.value = Event(TogetherFifthAction.NEXT_ONE)
        }else{
            _action.value = Event(TogetherFifthAction.NEXT_TWO)
        }

    }

    enum class TogetherFifthAction{
        NEXT_ONE, NEXT_TWO
    }

}