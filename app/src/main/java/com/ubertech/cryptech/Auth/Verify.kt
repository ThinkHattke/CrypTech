package com.ubertech.cryptech.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import com.google.android.gms.vision.barcode.Barcode
import com.ubertech.cryptech.R
import info.androidhive.barcode.BarcodeReader
import timber.log.Timber

class Verify : AppCompatActivity(), BarcodeReader.BarcodeReaderListener {

    var barcodeReader: BarcodeReader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

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
        
    }

    override fun onScanError(errorMessage: String?) {

        Timber.e("Scan Error: $errorMessage")

    }

}
