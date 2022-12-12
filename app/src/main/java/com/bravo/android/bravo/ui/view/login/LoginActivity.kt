package com.bravo.android.bravo.ui.view.login

import android.util.Log
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.data.local.UserLoginLocalDataSource
import com.bravo.android.bravo.data.remote.model.response.UserResponse
import com.bravo.android.bravo.data.remote.source.AuthDataSource
import com.bravo.android.bravo.databinding.ActivityLoginBinding
import com.bravo.android.bravo.ui.view.join.JoinActivity
import com.bravo.android.bravo.ui.view.main.MainActivity
import com.bravo.android.bravo.util.EventObserver
import com.kakao.sdk.auth.network.RxAuthOperations
import com.kakao.sdk.common.model.ApiError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.inject


class LoginActivity : BaseVmActivity<ActivityLoginBinding>(
    R.layout.activity_login,
    LoginViewModel::class.java
) {
    override val viewModel by lazy { vm as LoginViewModel }
    override val toolbarId = 0

    private val authDataSource: AuthDataSource by inject()
    private val userLoginLocalDataSource: UserLoginLocalDataSource by inject()
    override fun initActivity() {

        viewModel.setObserves()
        val sharedPreferences = getSharedPreferences(
            "login",
            MODE_PRIVATE
        ) // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.

        sharedPreferences.getString("loginType", "")?.let { viewModel.getLoginData(it) }

        var keyHash = Utility.getKeyHash(this)
        Log.d("hashkey", "keyhash : $keyHash")
    }

    fun LoginViewModel.setObserves(){

        action.observe(lifecycleOwner, EventObserver{
            when(it){
                LoginViewModel.LoginActions.KAKAO -> {
                    //카카오 로그인
                    loginKakao()
                    Log.d("테스트", "테스트")
                }
                LoginViewModel.LoginActions.MAIN -> {
                    startActivity(
                        intentFor<MainActivity>()
                    )
                    finish()
                }
            }
        })
    }


    private fun loginKakao(){
        Log.d("테스트", "테스트123")
        Single.just(UserApiClient.instance.isKakaoTalkLoginAvailable(this))
            .flatMap { available ->
                if (available) UserApiClient.rx.loginWithKakaoTalk(this)
                else UserApiClient.rx.loginWithKakaoAccount(this)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                UserApiClient.rx.me()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        user.kakaoAccount?.email?.run {
                            Log.d("kakaoEmail", user.kakaoAccount!!.email!!)
                            authDataSource
                                .loginByKaKao(user.id.toString())
                                .subscribe({
                                    Log.d("kakaoAccount1", it.toString())
                                    onLoginSuccess(it.data)
                                },{
                                    startActivity(
                                        intentFor<JoinActivity>(
                                            "id" to user.id.toString(),
                                            "email" to user.kakaoAccount!!.email,
                                            "type" to "kakao"
                                        )
                                    )
                                    finish()
                                    Log.d("kakaoAccount1 E", it.toString())
                                })
                                .addTo(viewModel.compositeDisposable)
                        } ?: scopesKakao()
                    }, { error ->
                        error.printStackTrace()
                    })
                    .addTo(viewModel.compositeDisposable)
            }, { error ->
                Log.d("kakaoE", error.toString())
                error.printStackTrace()
            })
            .addTo(viewModel.compositeDisposable)

    }

    // 카카오 미설치 기기에서 이메일 수집 추가하는 함수
    fun scopesKakao(){
        UserApiClient.rx.me()
            .flatMap { user ->

                var scopes = mutableListOf<String>()

                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
                if (user.kakaoAccount?.birthdayNeedsAgreement == true) { scopes.add("birthday") }
                if (user.kakaoAccount?.birthyearNeedsAgreement == true) { scopes.add("birthyear") }
                if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
                if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) { scopes.add("phone_number") }
                if (user.kakaoAccount?.profileNeedsAgreement == true) { scopes.add("profile") }
                if (user.kakaoAccount?.ageRangeNeedsAgreement == true) { scopes.add("age_range") }
                if (user.kakaoAccount?.ciNeedsAgreement == true) { scopes.add("account_ci") }

                if (scopes.count() > 0) {
                    Log.d("scopes", "사용자에게 추가 동의를 받아야 합니다.")

                    // OpenID Connect 사용 시
                    // scope 목록에 "openid" 문자열을 추가하고 요청해야 함
                    // 해당 문자열을 포함하지 않은 경우, ID 토큰이 재발급되지 않음
                    // scopes.add("openid")

                    // scope 목록을 전달하여 InsufficientScope 에러 생성
                    Single.error(ApiError.fromScopes(scopes))
                }
                else {
                    Single.just(user)
                }
            }
            .retryWhen(
                // InsufficientScope 에러에 대해 추가 동의 후 재요청
                RxAuthOperations.instance.incrementalAuthorizationRequired(this)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                user.kakaoAccount?.email?.run {
                    Log.d("kakaoEmail", user.kakaoAccount!!.email!!)
                    authDataSource
                        .loginByKaKao(user.id.toString())
                        .subscribe({
                            Log.d("kakaoAccount1", it.toString())
                            onLoginSuccess(it.data)
                        },{
                            startActivity(
                                intentFor<JoinActivity>(
                                    "id" to user.id.toString(),
                                    "email" to user.kakaoAccount!!.email,
                                    "type" to "kakao"
                                )
                            )
                            finish()
                            Log.d("kakaoAccount1 E", it.toString())
                        })
                        .addTo(viewModel.compositeDisposable)

                } ?: toast("카카오 로그인 실패")?.show()
            }, { error ->
                Log.e("scopes", "사용자 정보 요청 실패", error)
            })
            .addTo(viewModel.compositeDisposable)
    }

    //기기에 유저정보 저장
    private fun onLoginSuccess(response: UserResponse) {
        userLoginLocalDataSource.saveLoginInfo(response.user)
        userLoginLocalDataSource.saveAccessToken(response.accessToken)
        userLoginLocalDataSource.saveRefreshToken(response.refreshToken)

        viewModel.successLogin()

        val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE) // test 이름의 기본모드 설정
        val editor = sharedPreferences.edit() //sharedPreferences를 제어할 editor를 선언
        editor.putString("loginType", response.user.userType) // key,value 형식으로 저장
        editor.apply() //최종 커밋. 커밋을 해야 저장이 된다.

    }
}