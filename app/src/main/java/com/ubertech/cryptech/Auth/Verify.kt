package com.ubertech.cryptech.Auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.vision.barcode.Barcode
import com.ubertech.cryptech.API.Models.Request.VerifyRequest
import com.ubertech.cryptech.API.Models.Response.VerifyResponse
import com.ubertech.cryptech.API.Services.ApiClient
import com.ubertech.cryptech.API.Services.ApiInterface
import com.ubertech.cryptech.Main.MainActivity
import com.ubertech.cryptech.R
import com.ubertech.cryptech.Utilities.TinyDB
import info.androidhive.barcode.BarcodeReader
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class Verify : AppCompatActivity(), BarcodeReader.BarcodeReaderListener {

    var barcodeReader: BarcodeReader? = null

    // Global data sources
    private var api: ApiInterface? = null
    private var db: TinyDB? = null

    lateinit var loader: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        // Initiating tinyDB
        db = TinyDB(this@Verify)

        loader = findViewById(R.id.loader)

        // Initiating Retrofit API
        api = ApiClient.client.create(ApiInterface::class.java)

        barcodeReader = supportFragmentManager.findFragmentById(R.id.barcode_scanner) as BarcodeReader?

    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
    }

    override fun onScannedMultiple(barcodes: MutableList<Barcode>?) {
    }

    override fun onCameraPermissionDenied() {
        Timber.e("Scan Error: Permission Denied")    }

    override fun onScanned(barcode: Barcode?) {

        // playing barcode reader beep sound
        barcodeReader!!.playBeep()

        // ticket details activity by passing barcode
        Timber.e("Values is: ${barcode!!.displayValue}")

        loader.visibility = View.VISIBLE

        api!!.requestVerification(db!!.getString("jwt"), VerifyRequest(barcode.displayValue!!))
                .enqueue(object: retrofit2.Callback<VerifyResponse>{
                    override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                        loader.visibility = View.GONE
                        Timber.e(t)
                        Toast.makeText(this@Verify, "Something Wen't wrong", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {
                        loader.visibility = View.GONE
                        when {
                            response.isSuccessful -> {

                                db!!.putBoolean("verified", true)
                                startActivity(Intent(this@Verify, MainActivity::class.java))
                                finish()

                            }
                            response.code() == 409 -> Toast.makeText(this@Verify, "Older codes are not accepted", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this@Verify, "Invalid QR code", Toast.LENGTH_SHORT).show()
                        }

                    }


                })
        
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onScanError(errorMessage: String?) {

        Timber.e("Scan Error: $errorMessage")

    }

}
