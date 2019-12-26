package com.example.bluetooth_again

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context,private val resource: Int, private val applications : List<BluetoothDevice>)
    : ArrayAdapter<BluetoothDevice>(context, resource ){

    private val TAG  = "DEVICE"
    private  val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(resource, parent, false)

        val bltName: TextView = view.findViewById(R.id.bltName)
        val bltMacAddress: TextView = view.findViewById(R.id.bltMacAddress)

        val currentApp = applications[position]

        bltName.text = currentApp.name
        bltMacAddress.text = currentApp.address

        return view
    }
}