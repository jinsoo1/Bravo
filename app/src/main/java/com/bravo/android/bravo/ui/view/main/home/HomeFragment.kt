package com.bravo.android.bravo.ui.view.main.home

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentHealingInfoBinding
import com.bravo.android.bravo.databinding.FragmentHomeBinding
import com.bravo.android.bravo.ui.view.main.healinginfo.HealingViewModel
import com.bravo.android.bravo.util.EventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.support.v4.toast
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : BaseVmFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {

    override val viewModel by lazy { vm as HomeViewModel }

    override fun initFragment() {

        viewModel.setObserves()

    }

    private fun HomeViewModel.setObserves(){
        action.observe(this@HomeFragment, EventObserver{
            when(it){
                HomeViewModel.HomeAction.LETTER -> {

                    runBlocking {
                        saveLocalDateTime()
                    }
                    letterCheckTrue()
                }
            }
        })

        letterTime.observe(this@HomeFragment, Observer { letterTimes->
            val sharedPreferences = requireContext().getSharedPreferences(
                "SaveTime",
                AppCompatActivity.MODE_PRIVATE
            ) // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
            sharedPreferences.getString("SaveLocalTime", "2022-12-12 08:00:00")?.let { saveTimes ->
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val save = LocalDateTime.parse(saveTimes, formatter)
                val letter = LocalDateTime.parse(letterTimes, formatter)

                when{
                    save.isAfter(letter) -> {
                        Log.d("저장된값이 큼 : ", "저장된값-$save")
                        Log.d("저장된값이 큼 : ", "비교값-$letter")
                        letterCheckTrue()
                    }
                    save.isBefore(letter) ->{
                        Log.d("저장된값이 작음 : ", "저장된값-$save")
                        Log.d("저장된값이 작음 : ", "비교값-$letter")
                    }
                    save.isEqual(letter) ->{
                        Log.d("저장된값이 같음 : ", "저장된값-$save")
                        Log.d("저장된값이 같음 : ", "비교값-$letter")
                        letterCheckTrue()
                    }
                }
            }
        })
    }

    //내일 날짜까지 저장
    private fun saveLocalDateTime(){
        val nowDate = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 08:00:00"))
        val localTime = nowDate.format(nowDate)

        val sharedPreferences = requireContext().getSharedPreferences("SaveTime", AppCompatActivity.MODE_PRIVATE) // test 이름의 기본모드 설정
        val editor = sharedPreferences.edit() //sharedPreferences를 제어할 editor를 선언
        editor.putString("SaveLocalTime", localTime) // key,value 형식으로 저장
        editor.apply() //최종 커밋. 커밋을 해야 저장이 된다.
    }
}