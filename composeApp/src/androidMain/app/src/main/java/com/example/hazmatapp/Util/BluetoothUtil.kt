package com.example.hazmatapp.Util

// Use this file to add all the bluetooth methods needed

class BluetoothUtil {
    //These private values may need to be moved into a place where the functions have access to them
    //Due to my inexperience in Kotlin, I am unsure if all function calls from the app will share the same address space
    //This code uses some app specific UI code from the tutorial which must be changed for our app

    //Maximum transmission size
    private const val GATT_MAX_MTU_SIZE = 517

    private const val RUNTIME_PERMISSION_REQUEST_CODE = 2

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }


    val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .build()

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1) { // A scan result already exists with the same address
                scanResults[indexQuery] = result
                scanResultAdapter.notifyItemChanged(indexQuery)
            } else {
                with(result.device) {
                    Log.i(“ScanCallback”, "Found BLE device! Name: ${name ?: "Unnamed"}, address: $address")
                }
                scanResults.add(result)
                scanResultAdapter.notifyItemInserted(scanResults.size - 1)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e(“ScanCallback”, "onScanFailed: code $errorCode")
        }
    }

    private var isScanning = false

    private val scanResults = mutableListOf<ScanResult>()
    private val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) { result ->
            // User tapped on a scan result
            if (isScanning) {
                stopBleScan()
            }
            with(result.device) {
                Log.w("ScanResultAdapter", "Connecting to $address")
                connectGatt(context, false, gattCallback)
                // TO DO: Create key for HMAC verification and send
            }
        }
    }


    //Gatt definitions
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully connected to $deviceAddress")
                    bluetoothGatt = gatt
                    Handler(Looper.getMainLooper()).post {
                    bluetoothGatt?.discoverServices()

                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully disconnected from $deviceAddress")
                    gatt.close()
                }
            } else {
                Log.w("BluetoothGattCallback", "Error $status encountered for $deviceAddress! Disconnecting...")
                gatt.close()
            }
        }
            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        with(gatt) {
            Log.w("BluetoothGattCallback", "Discovered ${services.size} services for ${device.address}")
            printGattTable() // See implementation just above this section
            // Consider connection setup as complete here
        }
    }
    }


    fun Context.hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionType) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun Context.hasRequiredRuntimePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
                    hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun startBleScan() {
        if (!hasRequiredRuntimePermissions()) {
            requestRelevantRuntimePermissions()
        } else {
            scanResults.clear()
            scanResultAdapter.notifyDataSetChanged()
            bleScanner.startScan(null, scanSettings, scanCallback)
            isScanning = true
        }
    }

    private fun stopBleScan() {
        bleScanner.stopScan(scanCallback)
        isScanning = false
    }

    private fun Activity.requestRelevantRuntimePermissions() {
        if (hasRequiredRuntimePermissions()) { return }
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
                requestLocationPermission()
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                requestBluetoothPermissions()
            }
        }
    }

    private fun requestLocationPermission() {
        runOnUiThread {
            alert {
                title = "Location permission required"
                message = "Starting from Android M (6.0), the system requires apps to be granted " +
                        "location access in order to scan for BLE devices."
                isCancelable = false
                positiveButton(android.R.string.ok) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        RUNTIME_PERMISSION_REQUEST_CODE
                    )
                }
            }.show()
        }
    }

    private fun requestBluetoothPermissions() {
        runOnUiThread {
            alert {
                title = "Bluetooth permissions required"
                message = "Starting from Android 12, the system requires apps to be granted " +
                        "Bluetooth access in order to scan for and connect to BLE devices."
                isCancelable = false
                positiveButton(android.R.string.ok) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ),
                        RUNTIME_PERMISSION_REQUEST_CODE
                    )
                }
            }.show()
        }
    }

    //Service discovery for BLE device functionality
    private fun BluetoothGatt.printGattTable() {
    if (services.isEmpty()) {
        Log.i("printGattTable", "No service and characteristic available, call discoverServices() first?")
        return
    }
    services.forEach { service ->
        val characteristicsTable = service.characteristics.joinToString(
            separator = "\n|--",
            prefix = "|--"
        ) { it.uuid.toString() }
        Log.i("printGattTable", "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
        )
    }
}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RUNTIME_PERMISSION_REQUEST_CODE -> {
                val containsPermanentDenial = permissions.zip(grantResults.toTypedArray()).any {
                    it.second == PackageManager.PERMISSION_DENIED &&
                            !ActivityCompat.shouldShowRequestPermissionRationale(this, it.first)
                }
                val containsDenial = grantResults.any { it == PackageManager.PERMISSION_DENIED }
                val allGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                when {
                    containsPermanentDenial -> {
                        // TODO: Handle permanent denial (e.g., show AlertDialog with justification)
                        // Note: The user will need to navigate to App Settings and manually grant
                        // permissions that were permanently denied
                    }
                    containsDenial -> {
                        requestRelevantRuntimePermissions()
                    }
                    allGranted && hasRequiredRuntimePermissions() -> {
                        startBleScan()
                    }
                    else -> {
                        // Unexpected scenario encountered when handling permissions
                        recreate()
                    }
                }
            }
        }
    }
    private val scanResults = mutableListOf<ScanResult>()
    private val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) {
            // TODO: Implement
        }
    }


    //Gatt Connectivity
    //Use this to request 517 byte transmission size gatt.requestMtu(GATT_MAX_MTU_SIZE)

    fun BluetoothGattCharacteristic.isReadable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)

    fun BluetoothGattCharacteristic.isWritable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)

    fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

    fun BluetoothGattCharacteristic.containsProperty(property: Int): Boolean {
        return properties and property != 0
    }

    // Read Methane Level Function
    // TODO: Replace first 8 characters of UUID strings with
    // designated ones meant for use with our sensor package
    private fun readMethaneLevel() {
        val methaneServiceUuid = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")
        val methaneLevelCharUuid = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")
        val methaneLevelChar = gatt
            .getService(methaneServiceUuid)?.getCharacteristic(methaneLevelCharUuid)
        if (methaneLevelChar?.isReadable() == true) {
            gatt.readCharacteristic(methaneLevelChar)
        }
    }
//    These method calls can indeed return null if a given UUID doesn’t exist
//    among the discovered services or characteristics,we can (and should) append a “?”
//    so we get null-safety while calling those methods

    override fun onCharacteristicRead(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        status: Int
    ) {
        with(characteristic) {
            when (status) {
                BluetoothGatt.GATT_SUCCESS -> {
                    Log.i("BluetoothGattCallback", "Read characteristic $uuid:\n${value.toHexString()}")
                }
                BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                    Log.e("BluetoothGattCallback", "Read not permitted for $uuid!")
                }
                else -> {
                    Log.e("BluetoothGattCallback", "Characteristic read failed for $uuid, error: $status")
                }
            }
        }
    }

    // Read Methane ByteArray Function
    fun ByteArray.readMethane() {
        val hexString = this.joinToString(separator = " ", prefix = "0x") { String.format("%02X", it) }
        val methaneLevel = this.first().toInt()

        // Use hexString and methaneLevel as needed
        Log.i("Hex representation: $hexString")
        Log.i("Methane Level: $methaneLevel")
        return methaneLevel
    }

    // Writing ByteArray Function
    fun writeByteArray() {
        // TODO: Implement
    }

    //Bluetooth write to the sensor package functionality
    //AKA Bluetooth transmit function
    fun writeSensorCommand(characteristic: BluetoothGattCharacteristic, payload: ByteArray) {
    val writeType = when {
        characteristic.isWritable() -> BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
        characteristic.isWritableWithoutResponse() -> {
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        }
        else -> error("Characteristic ${characteristic.uuid} cannot be written to")
    }

 //Need to specify the type fo value being written
 //After that specify the value to be written then write it to the GATT server
    bluetoothGatt?.let { gatt ->
        characteristic.writeType = writeType
        characteristic.value = payload
        gatt.writeCharacteristic(characteristic)
    } ?: error("Not connected to a BLE device!")
}

    override fun onCharacteristicWrite(
    gatt: BluetoothGatt,
    characteristic: BluetoothGattCharacteristic,
    status: Int
) {
    with(characteristic) {
        when (status) {
            BluetoothGatt.GATT_SUCCESS -> {
                Log.i("BluetoothGattCallback", "Wrote to characteristic $uuid | value: ${value.toHexString()}")
            }
            BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH -> {
                Log.e("BluetoothGattCallback", "Write exceeded connection ATT MTU!")
            }
            BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> {
                Log.e("BluetoothGattCallback", "Write not permitted for $uuid!")
            }
            else -> {
                Log.e("BluetoothGattCallback", "Characteristic write failed for $uuid, error: $status")
            }
        }
    }
}
}
