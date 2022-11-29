package com.example.mvpapplication

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow
import com.example.mvpapplication.databinding.LayoutCardProfileBinding

class ProfileCardComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {
    private var binding: LayoutCardProfileBinding
    var topText: String = ""
        get() = binding.topText.text.toString()
        set(value) {
            field = value
            binding.topText.text = field
        }

    var bottomText: String = ""
        get() = binding.bottomText.text.toString()
        set(value) {
            field = value
            binding.bottomText.text = field
        }

    private var listener: ClickListener? = null

    private var endIconListener: (() -> Unit)? = null

    init {
        binding = LayoutCardProfileBinding.inflate(LayoutInflater.from(context), this)
        val layoutParam = LayoutParams(binding.root.width, binding.root.height)
        layoutParam.apply {
            val dp10 = 10.toDp(context)
            setPadding(dp10, dp10, dp10, dp10)
            clipToPadding = false
        }
        binding.root.layoutParams = layoutParam
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProfileCardComponent)
            binding.topText.text = typedArray.getString(R.styleable.ProfileCardComponent_topText)
            binding.bottomText.text = typedArray.getString(R.styleable.ProfileCardComponent_bottomText)
            val topColor = try {
                typedArray.getResourceIdOrThrow(R.styleable.ProfileCardComponent_topTextColor)
            } catch (e: IllegalArgumentException) { null }
            val bottomColor = try {
                typedArray.getResourceIdOrThrow(R.styleable.ProfileCardComponent_bottomTextColor)
            } catch (e: IllegalArgumentException) { null }
            setTextColor(topColor, bottomColor)

            val topFontFamily = try {
                typedArray.getResourceIdOrThrow(R.styleable.ProfileCardComponent_topFontFamily)
            } catch (e: IllegalArgumentException) { null }

            val bottomFontFamily = try {
                typedArray.getResourceIdOrThrow(R.styleable.ProfileCardComponent_bottomFontFamily)
            } catch (e: IllegalArgumentException) { null }

            setFontFamily(topFontFamily, bottomFontFamily)

            typedArray.recycle()
        }


        binding.endIcon.setOnClickListener {
            listener?.onClickEndIcon()
            endIconListener?.invoke()
        }
    }

    fun setTextSize(topTextSize: Float? = null, bottomTextSize: Float? = null) {
        if (topTextSize != null)
            binding.topText.setTextSize(TypedValue.COMPLEX_UNIT_SP, topTextSize)

        if (bottomTextSize != null)
            binding.bottomText.setTextSize(TypedValue.COMPLEX_UNIT_SP, bottomTextSize)
    }

    fun setTextColor(topColorId: Int? = null, bottomColorId: Int? = null) {
        if (topColorId != null)
            binding.topText.setTextColor(ContextCompat.getColor(context, topColorId))

        if (bottomColorId != null)
            binding.bottomText.setTextColor(ContextCompat.getColor(context, bottomColorId))
    }

    fun setFontFamily(topFontFamily: Int? = null, bottomFontFamily: Int? = null) {
        topFontFamily?.let {
            binding.topText.typeface = ResourcesCompat.getFont(context, it)
        }

        bottomFontFamily?.let {
            binding.bottomText.typeface = ResourcesCompat.getFont(context, it)
        }
    }

    fun setListener(listener: ClickListener) {
        this.listener = listener
    }

    fun setEndIconListener(listener: () -> Unit) {
        endIconListener = listener
    }

    interface ClickListener {
        fun onClickEndIcon()
    }
}