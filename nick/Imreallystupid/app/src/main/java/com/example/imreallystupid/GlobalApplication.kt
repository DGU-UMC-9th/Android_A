package com.example.imreallystupid

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "f4dc4468b7fc42b10f052d84a73e7a79")
    }
}