package ru.skillbranch.devintensive.ui.custom

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ImageView(context, attrs) {


    private var mBitmapShader: Shader? = null
    private val mShaderMatrix: Matrix

    private val mBitmapDrawBounds: RectF
    private val mBorderBounds: RectF

    private var mBitmap: Bitmap? = null

    private val mBitmapPaint: Paint
    private val mBorderPaint: Paint
    private val mPressedPaint: Paint

    private val mInitialized: Boolean

    private var borderColor: Int
        /*@ColorInt
        get() = mBorderPaint.color
        set(@ColorInt color) {
            mBorderPaint.color = color
            invalidate()
        }*/

    private var borderWidth: Float
        /*@Dimension
        get() = mBorderPaint.strokeWidth
        set(@Dimension width) {
            mBorderPaint.strokeWidth = width
            invalidate()
        }*/

    @Dimension fun getBorderWidth(): Int = mBorderPaint.strokeWidth.toInt()

    fun setBorderWidth(@Dimension dp:Int) {
        mBorderPaint.strokeWidth = dp.toFloat()
        invalidate()}

    fun getBorderColor(): Int = mBorderPaint.color

    fun setBorderColor(hex:String) {
        mBorderPaint.color = Color.parseColor(hex)
        invalidate()}

    @SuppressLint("ResourceAsColor")
    fun setBorderColor(@ColorRes colorId: Int) {
        mBorderPaint.color = colorId
        invalidate()}


    init {

        borderColor = DEFAULT_BORDER_COLOR
        borderWidth = DEFAULT_BORDER_WIDTH

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0)

            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH.toInt()).toFloat()

            a.recycle()
        }

        mShaderMatrix = Matrix()
        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBorderBounds = RectF()
        mBitmapDrawBounds = RectF()
        mBorderPaint.color = borderColor
        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.strokeWidth = borderWidth

        mPressedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPressedPaint.style = Paint.Style.FILL

        mInitialized = true

        setupBitmap()
    }

    override fun onDraw(canvas: Canvas) {
        drawBitmap(canvas)
        drawStroke(canvas)
        super.setImageDrawable(drawable)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val halfStrokeWidth = mBorderPaint.strokeWidth / 2f
        updateCircleDrawBounds(mBitmapDrawBounds)
        mBorderBounds.set(mBitmapDrawBounds)
        mBorderBounds.inset(halfStrokeWidth, halfStrokeWidth)

        updateBitmapSize()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = CircleImageViewOutlineProvider(mBorderBounds)
        }
    }

    private fun drawStroke(canvas: Canvas) {
        if (mBorderPaint.strokeWidth > 0f) {
            canvas.drawOval(mBorderBounds, mBorderPaint)
        }
    }

    private fun drawBitmap(canvas: Canvas) {
        canvas.drawOval(mBitmapDrawBounds, mBitmapPaint)
    }

    private fun updateCircleDrawBounds(bounds: RectF) {
        val contentWidth = (width - paddingLeft - paddingRight).toFloat()
        val contentHeight = (height - paddingTop - paddingBottom).toFloat()

        var left = paddingLeft.toFloat()
        var top = paddingTop.toFloat()
        if (contentWidth > contentHeight) {
            left += (contentWidth - contentHeight) / 2f
        } else {
            top += (contentHeight - contentWidth) / 2f
        }

        val diameter = Math.min(contentWidth, contentHeight)
        bounds.set(left, top, left + diameter, top + diameter)
    }

    private fun setupBitmap() {
        if (!mInitialized) {
            return
        }
        mBitmap = getBitmapFromDrawable(drawable)
        if (mBitmap == null) {
            return
        }

        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBitmapPaint.shader = mBitmapShader

        updateBitmapSize()
    }

    private fun updateBitmapSize() {
        if (mBitmap == null) return

        val dx: Float
        val dy: Float
        val scale: Float

        // scale up/down with respect to this view size and maintain aspect ratio
        // translate bitmap position with dx/dy to the center of the image
        if (mBitmap!!.width < mBitmap!!.height) {
            scale = mBitmapDrawBounds.width() / mBitmap!!.width.toFloat()
            dx = mBitmapDrawBounds.left
            dy = mBitmapDrawBounds.top - mBitmap!!.height * scale / 2f + mBitmapDrawBounds.width() / 2f
        } else {
            scale = mBitmapDrawBounds.height() / mBitmap!!.height.toFloat()
            dx = mBitmapDrawBounds.left - mBitmap!!.width * scale / 2f + mBitmapDrawBounds.width() / 2f
            dy = mBitmapDrawBounds.top
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(dx, dy)
        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    inner class CircleImageViewOutlineProvider internal constructor(rect: RectF) : ViewOutlineProvider() {

        private val mRect: Rect = Rect(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )

        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(mRect)
        }

    }

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 2f
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }
}
