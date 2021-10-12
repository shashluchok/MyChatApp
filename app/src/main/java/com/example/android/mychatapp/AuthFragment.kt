package com.example.android.mychatapp

import android.animation.*
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.android.mychatapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class AuthFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    override val layoutResId: Int
        get() = R.layout.fragment_auth

    private val iconsList = intArrayOf(R.drawable.send, R.drawable.sms)
    private lateinit var iconsColorsList: Array<Int>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iconsColorsList = arrayOf(
            resources.getColor(R.color.secondaryDarkColor), resources.getColor(
                R.color.secondaryLightColor
            ), resources.getColor(R.color.secondaryColor), resources.getColor(R.color.color_white)
        )

        lifecycleScope.launch(Dispatchers.IO){
            try {
                while (true){
                    delay(kotlin.random.Random.nextInt(0, 1000).toLong())
                    withContext(Dispatchers.Main){
                        val randomX = kotlin.random.Random.nextInt(0, auth_main_cl.width)
                        val randomY = kotlin.random.Random.nextInt(0, auth_main_cl.height)
                        playStartingAnimation(randomX.toFloat(), randomY.toFloat())
                    }
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }

        }

        auth_button_auth.setOnClickListener {
            if(!auth_name_et.text.isNullOrEmpty()) {
                val extras = FragmentNavigatorExtras(
                    auth_button_auth to "shared_element"
                )

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.authFragment, true)
                    .build()


                view.findNavController().navigate(
                    R.id.action_authFragment_to_chatFragment,
                    bundleOf("user_name" to auth_name_et.text.toString()),
                    navOptions,
                    extras
                )
            }
            else{
                highlightInput()
            }
        }


        auth_main_cl.setOnTouchListener { v, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    playStartingAnimation(event.rawX, event.rawY)
                }
                MotionEvent.ACTION_POINTER_DOWN -> {


                }
                MotionEvent.ACTION_UP -> {


                }

                MotionEvent.ACTION_MOVE -> {
                    playStartingAnimation(event.rawX, event.rawY)
                }
            }

            true
        }
    }



    private fun playStartingAnimation(xPos: Float, yPos: Float){

        val random = Random()
        val widthAndHeight = random.nextInt(100) + 50
        val imageView = ImageView(requireActivity())
        imageView.setLayoutParams(ConstraintLayout.LayoutParams(widthAndHeight, widthAndHeight))
        imageView.setImageResource(iconsList.get(random.nextInt(iconsList.size)))
        imageView.setColorFilter(iconsColorsList.get(random.nextInt(iconsColorsList.size)))
        imageView.setX(xPos)
        imageView.setY(yPos)
        auth_main_cl?.addView(imageView)
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = DecelerateInterpolator()
        val objectAnimatorX: ObjectAnimator =
            if (random.nextBoolean())
                ObjectAnimator.ofFloat(
                    imageView,
                    "translationX",
                    imageView.getX(),
                    xPos + kotlin.random.Random.nextInt(
                        0,
                        200
                    )
                )
            else
                ObjectAnimator.ofFloat(
                    imageView,
                    "translationX",
                    imageView.getX(),
                    xPos - kotlin.random.Random.nextInt(
                        0,
                        200
                    )
                )

        val objectAnimatorY: ObjectAnimator = if (random.nextBoolean())
            ObjectAnimator.ofFloat(
                imageView, "translationY", imageView.getY(), yPos + kotlin.random.Random.nextInt(
                    0,
                    200
                )
            )
        else ObjectAnimator.ofFloat(
            imageView, "translationY", imageView.getY(), yPos - kotlin.random.Random.nextInt(
                0,
                200
            )
        )

        val objectAnimatorRotation: ObjectAnimator = if (random.nextBoolean()) ObjectAnimator.ofFloat(
            imageView, "rotation", 0f, kotlin.random.Random.nextInt(
                180,
                360
            ).toFloat()
        ) else ObjectAnimator.ofFloat(
            imageView, "rotation", 0f, -kotlin.random.Random.nextInt(
                180,
                360
            ).toFloat()
        )
        val objectAnimatorAlpha: ObjectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)
        objectAnimatorX.duration = (random.nextInt(3000 - 2500) + 2500).toLong()
        objectAnimatorY.duration = (random.nextInt(3000 - 2500) + 2500).toLong()
        objectAnimatorRotation.duration = (random.nextInt(3000 - 2500) + 2500).toLong()
        objectAnimatorAlpha.duration = (random.nextInt(2000 - 1500) + 1500).toLong()
        objectAnimatorAlpha.startDelay = 200
        animatorSet.playTogether(
            objectAnimatorX, objectAnimatorY, objectAnimatorRotation, objectAnimatorAlpha
        )
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                auth_main_cl?.removeView(imageView)
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        animatorSet.start()


    }

    private fun highlightInput(){
        val colorFrom = resources.getColor(R.color.secondaryDarkColor)
        val colorTo = resources.getColor(R.color.color_transparent)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.addUpdateListener { animator -> auth_name_material_til.boxBackgroundColor = ((animator.animatedValue as Int)) }
        colorAnimation.interpolator = AccelerateInterpolator()
        colorAnimation.duration = 1000
        colorAnimation.start()

    }


}