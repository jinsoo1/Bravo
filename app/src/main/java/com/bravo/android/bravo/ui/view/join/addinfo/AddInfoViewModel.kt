package com.bravo.android.bravo.ui.view.join.addinfo

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.remote.source.AuthDataSource
import com.bravo.android.bravo.ui.view.join.address.AddressCityAdapter
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddInfoViewModel : BaseViewModel() {

    private val _action : MutableLiveData<Event<AddInfoAction>> = MutableLiveData()
    val action : LiveData<Event<AddInfoAction>> get() = _action

    private val _gender: MutableLiveData<Int> = MutableLiveData(2) //false이면 남자 true이면 여자
    val gender : LiveData<Int> get() = _gender

    private val _birth : MutableLiveData<List<String>> = MutableLiveData(listOf())
    val birth : LiveData<List<String>> get() = _birth

    private val _tvBirth : MutableLiveData<String> = MutableLiveData()
    val tvBirth : MutableLiveData<String> get() = _tvBirth

    init {
        val nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"))
        val year = nowDate.format(nowDate).toInt()
        val yearList = arrayListOf<String>()
        for(i in year downTo 1900 ){
            yearList.add(i.toString())
        }
        Log.d("clickGender", gender.value.toString())
        _birth.value = yearList
    }

    fun next(){
        _action.value = Event(AddInfoAction.NEXT)
    }

    fun clickGender(state : Int){
        Log.d("clickGender", state.toString())
        _gender.value = state
    }

    fun settingBirth(item : String){
        _tvBirth.value = item
    }



    enum class AddInfoAction{
        NEXT
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindYearList")
        fun bindYearList(view: RecyclerView, list: List<String>) {
            val adapter = view.adapter as YearListAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }
    }

}