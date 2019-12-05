package com.bashirabdi.pokemongame

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        chechPermission()
        LoadPokemon ()

    }
      var ACCESSLOCATION=123
     fun chechPermission()
     {
         if(Build.VERSION.SDK_INT>=23)
         {
           if(ActivityCompat.
                   checkSelfPermission(this,
                       android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
           {
               requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESSLOCATION)
               return;
           }
         }
         GetUserLocation()
     }

     fun GetUserLocation()
     {
         Toast.makeText(this, "User location access is on ", Toast.LENGTH_LONG).show()
         var mylocation = myLocationListener()
         var locationMagager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
         locationMagager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,mylocation)
         var mythread = myThread()
         mythread.start()
     }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode)
        {
            ACCESSLOCATION->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    GetUserLocation()
                }else{
                    Toast.makeText(this, "We can not access your Location!! ",Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

    }

    //get user location


    var location:Location?=null
    inner  class  myLocationListener:LocationListener {


        constructor()
        {
            OldLocation = Location("Start")
            OldLocation!!.longitude=0.0
            OldLocation!!.latitude=0.0
        }
        override fun onLocationChanged(p0: Location?) {
              location=p0
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


 var OldLocation:Location?=null
inner  class  myThread:Thread{


constructor():super(){

}

    override fun run() {
        while(true)
        {
            try {
      if(OldLocation!!.distanceTo(location)==0f)
      {
          continue
      }
                OldLocation=location
             runOnUiThread()  {
                 mMap!!.clear()
                 val sydney = LatLng(location!!.latitude, location!!.longitude)
                 mMap.addMarker(MarkerOptions()
                     .position(sydney)
                     .title("Me")
                     .snippet("her is My Location")
                     .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))    )
                 mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,2f))

                 //pokemons

                 for(i in 0..pokemoneList.size-1)
                 {
                     var newPokemon =pokemoneList[i]
                     if(newPokemon.isCatch==false)
                     {
                         val pokemonLoc = LatLng(newPokemon.location!!.latitude,
                             newPokemon.location!!.longitude)
                         mMap.addMarker(MarkerOptions()
                             .position(pokemonLoc)
                             .title(newPokemon.name!!)
                             .snippet(newPokemon.des!! +", Power "+newPokemon!!.pow)
                             .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!))   )
                         if(location!!.distanceTo(newPokemon.location)<2)
                         {
                             newPokemon.isCatch=true
                             pokemoneList[i] = newPokemon
                             playerPower+=newPokemon.pow!!
                             Toast.makeText(applicationContext,
                                 "You catch new Pokemon Your new Power is " +playerPower,
                                 Toast.LENGTH_LONG).show()


                         }
                     }
                 }
             }
               Thread.sleep(1000)

            }catch (Ex:Exception){}
        }
    }

}
    var playerPower = 0.0
    var pokemoneList = ArrayList<Pokemon>()
    fun LoadPokemon ()
    {
        pokemoneList.add(
            Pokemon(R.drawable.charmander,"Charmander",
            "Charmander is living jaban ", 55.0, 37.7789994893035,-122.401846647263)
        )
        pokemoneList.add(Pokemon(R.drawable.bulbasaur,"Bulbasour",
            "Bulbasour is living Usa ", 90.5, 37.7949568502667,-122.410494089127))
        pokemoneList.add(Pokemon(R.drawable.squirtle,"Squirtle",
            "Squirtle is living Iraq ", 33.5, 37.7816621152613,-122.41225361824))

    }

}
