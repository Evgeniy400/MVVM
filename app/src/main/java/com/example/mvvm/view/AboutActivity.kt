package com.example.mvvm.view

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.*
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.number.IntegerWidth
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.databinding.ActivityAboutBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    private lateinit var locationManager: LocationManager

    private val REQUEST_LOCATION = 1



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        setContentView(binding.root)

        binding.htmlButton.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }


        binding.myTextView.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when(event.action){
                    MotionEvent.ACTION_DOWN -> {
                        ObjectAnimator.ofFloat(v, View.ROTATION, 0f, 360f).apply {
                            duration = 1000
                            interpolator = AccelerateDecelerateInterpolator()
                            repeatCount = 1
                            repeatMode = REVERSE
                            start()
                        }
                    }
                }
                return  true
            }
        })

        binding.location.setOnClickListener {
            getCoords()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_LOCATION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    getCoords()
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCoords(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 5f) {
                binding.myTextView.setText("Coordinates by ${it.provider} ${it.altitude} ${it.latitude}")

            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION)
        }
    }
}