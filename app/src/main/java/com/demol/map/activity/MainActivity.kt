package com.demol.map.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.demol.map.R
import com.demol.map.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(view: View?) {

    }

    override fun doBusiness(mContext: Context?) {

    }

    override fun widgetClick(v: View?) {

    }

    override fun initParms(parms: Bundle?) {

    }
    @OnClick(R.id.bmapView)
    fun OnClick( v: View){
        when(v.id){
            R.id.bmapView ->{

            }
        }
    }


}
