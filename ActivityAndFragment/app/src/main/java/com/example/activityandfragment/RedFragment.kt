package com.example.activityandfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class RedFragment : Fragment() {

    override fun onCreateView(      // Fragment 의 layout 을 연결할 때 쓰는 callback
        inflater: LayoutInflater,   // 뷰를 생성하는 객체
        container: ViewGroup?,      // 생성할 뷰(자식 뷰)가 들어갈 부모 뷰
        saveInstanceState: Bundle?  // 이전 Fragment 객체에서 전달된 데이터(Bundle)
    ): View? {
        return inflater.inflate(R.layout.fragment_red, container, false);
    }
}