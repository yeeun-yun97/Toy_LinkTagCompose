package com.github.yeeun_yun97.toy.linksaver.ui.activity.basic

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.github.yeeun_yun97.toy.linksaver.R

abstract class ViewBindingBasicActivity<T : ViewBinding> : AppCompatActivity() {
    private lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding
        binding = viewBindingInflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            add(R.id.fragmentContainer, homeFragment())
            setReorderingAllowed(true)
        }
    }

    abstract fun viewBindingInflate(inflater: LayoutInflater): T
    abstract fun homeFragment():Fragment


}