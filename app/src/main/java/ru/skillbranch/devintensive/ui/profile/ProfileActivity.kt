package ru.skillbranch.devintensive.ui.profile

import android.annotation.SuppressLint
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.TextDrawable
import ru.skillbranch.devintensive.extensions.isValidGitHubUrl
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel
import android.R.attr.data
import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import ru.skillbranch.devintensive.R


class ProfileActivity : AppCompatActivity(){

    companion object{ const val IS_EDIT_MODE = "IS_EDIT_MODE" }

    private var isEditMode = false
    private lateinit var viewFields : Map<String, TextView>
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        Log.d("M_ProfileActivity", "onCreate")
        setContentView(R.layout.activity_profile)

        initViews(savedInstanceState)
        initViewModel()
    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE,false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener{
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener{
            viewModel.switchTheme()
        }

        et_repository.afterTextChanged {
            if (!it.isValidGitHubUrl()) wr_repository.error = "Невалидный адрес репозитория"
            else wr_repository.isErrorEnabled = false
        }

    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer{ updateUI(it) })
        viewModel.getTheme().observe(this, Observer{ updateTheme(it) })

        makeAvatar()
    }

    private fun updateTheme(mode: Int) {
        delegate.setLocalNightMode(mode)
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorAvatarBg, typedValue, true)
        @ColorInt val colorBg = typedValue.data
        Log.d("M_ProfileActivity","updateTheme - colorAvatarBg=$colorBg")
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for((k,v) in viewFields){

                v.text = it[k].toString()
            }
        }
        makeAvatar()
        Log.d("M_ProfileActivity","updateUI")
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("firstName","lastName","about","repository").contains(it.key) }
        for ((_,v) in info){
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if(isEdit) 255 else 0
        }
        ic_eye.visibility = if(isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit){
            val filter: ColorFilter? = if(isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                ) }
                else null
            val icon = if (isEdit){ resources.getDrawable(R.drawable.ic_save_black_24dp, theme) }
                       else { resources.getDrawable(R.drawable.ic_edit_black_24dp, theme) }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo(){
        if (!et_repository.text.toString().isValidGitHubUrl()) et_repository.setText("")
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }

    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun makeAvatar(){
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorAvatarBg, typedValue, true)
        @ColorInt val colorBg = typedValue.data
        val initials = viewModel.getProfileData().value?.initials
        if (initials == "") iv_avatar.setImageResource(R.drawable.avatar_default)
        else {
            iv_avatar.setImageDrawable(TextDrawable
                .builder()
                .buildRound(initials, colorBg))
            iv_avatar.setupBitmap()
        }
    }

}

