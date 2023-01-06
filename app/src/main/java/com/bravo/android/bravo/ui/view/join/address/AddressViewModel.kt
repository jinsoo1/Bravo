package com.bravo.android.bravo.ui.view.join.address

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.Region
import com.bravo.android.bravo.data.remote.source.UserDataSource
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo

class AddressViewModel(
    private val userDataSource: UserDataSource
) : BaseViewModel() {

    val action: MutableLiveData<Event<AddressActions>> = MutableLiveData()

    private val _addressCity : MutableLiveData<List<String>> = MutableLiveData(listOf())
    val addressCity : LiveData<List<String>> get() = _addressCity

    private val _city : MutableLiveData<String> = MutableLiveData()
    val city : MutableLiveData<String> get() = _city

    private val _addressRegion : MutableLiveData<List<Region>> = MutableLiveData(listOf())
    val addressRegion : LiveData<List<Region>> get() = _addressRegion

    private val _region : MutableLiveData<Region> = MutableLiveData()
    val region : MutableLiveData<Region> get() = _region

    init {
        _addressCity.value = listOf(
            "서울특별시",
            "부산광역시",
            "대구광역시",
            "인천광역시",
            "광주광역시",
            "대전광역시",
            "울산광역시",
            "세종특별자치시",
            "경기도",
            "강원도",
            "충청북도",
            "충청남도",
            "전라북도",
            "전라남도",
            "경상북도",
            "경상남도",
            "제주특별자치도"
        )
    }

    fun onNext(){
        action.value = Event(AddressActions.NEXT)
    }

    fun settingCity(item : String){
        _city.value = item
    }

    fun getRegionList(){
        userDataSource.getRegion(city.value!!)
            .map {
                it.map {
                    Region(
                        it.regionToken,
                        it.region
                    )
                }
            }
            .subscribe({
                _addressRegion.value = it
            },{
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun settingRegion(item : Region){
        _region.value = item
    }

    enum class AddressActions {
        NEXT
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindCityList")
        fun bindCityList(view: RecyclerView, list: List<String>) {
            val adapter = view.adapter as AddressCityAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }

        @JvmStatic
        @BindingAdapter("bindRegionList")
        fun bindRegionList(view: RecyclerView, list: List<Region>) {
            val adapter = view.adapter as AddressRegionAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }

    }

}