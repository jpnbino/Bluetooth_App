package com.example.bluetooth_again

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.select_device_layout.*

class SelectDeviceActivity : AppCompatActivity() {


   private var m_bluetoothAdapter: BluetoothAdapter? = null
   private lateinit var m_pairedDevices: Set<BluetoothDevice>
   private val REQUEST_ENABLE_BLUETOOTH = 1

   companion object {
       val EXTRA_ADDRESS: String = "Device_address"
   }

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.select_device_layout)

       m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
       if(m_bluetoothAdapter == null) {
           //toast("this device doesn't support bluetooth")
           return
       }
       if(!m_bluetoothAdapter!!.isEnabled) {
           val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
           startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
       }

       select_device_refresh.setOnClickListener{ pairedDeviceList() }
   }


    private fun pairedDeviceList() {
          m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
          val list : ArrayList<BluetoothDevice> = ArrayList()

        //If the bluetooth adapter of paired devices is not empty
          if (!m_pairedDevices.isEmpty()) {
              for (device: BluetoothDevice in m_pairedDevices) {
                  list.add(device)
                  Log.d("DEVICE", "("+device+") "+device.name)
              }
          } else {
              //toast("no paired bluetooth devices found")
          }

            val adapter = ListAdapter(this,R.layout.blt_device_item,list)
            select_device_list.adapter = adapter

          // Checks for a chosen device and try to start a new activity
          select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
              val device: BluetoothDevice = list[position]
              val address: String = device.address

              val intent = Intent(this, ControlActivity::class.java)
              intent.putExtra(EXTRA_ADDRESS, address)
              startActivity(intent)
          }
      }



}
